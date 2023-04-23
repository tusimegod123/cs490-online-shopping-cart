package com.cs490.shoppingCart.PaymentModule.service.imp;

import com.cs490.shoppingCart.PaymentModule.DTO.*;
import com.cs490.shoppingCart.PaymentModule.model.Transaction;
import com.cs490.shoppingCart.PaymentModule.model.TransactionStatus;
import com.cs490.shoppingCart.PaymentModule.model.TransactionType;
import com.cs490.shoppingCart.PaymentModule.repository.TransactionRepository;
import com.cs490.shoppingCart.PaymentModule.service.BankService;
import com.cs490.shoppingCart.PaymentModule.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImp implements PaymentService {
    @Autowired
    BankService bankService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentServiceImp.class);

    @Override
    public TransactionStatus processOrderPayment(PaymentRequest request) throws Exception {
        if(!request.getCardNumber().isEmpty()){

            //check if user & order or only order exists
            //call user service & order service

            char cardStartWith = request.getCardNumber().charAt(0);

            if(request.getCardNumber().length() == 16 && (cardStartWith == '4' || cardStartWith == '5')){

                BankResponse response = getLastTransaction(request.getCardDetail());

                Transaction newTransaction = new Transaction();
                newTransaction.setUserId(request.getUserId());
                newTransaction.setOrderId(request.getOrderId());
                newTransaction.setCardNumber(request.getCardNumber());
                newTransaction.setCardBalance(response.getCurrentBalance());
                newTransaction.setTransactionValue(request.getAmount());
                newTransaction.setTransactionNumber(this.generateTransactionNo());
                newTransaction.setTransactionDate(new Date());
                newTransaction.setPaymentType(response.getPaymentType());
                newTransaction.setTransactionType(TransactionType.OrderPayment);

                if(response.getCurrentBalance() >= request.getAmount()){
                    //order payment here
                    newTransaction.setTransactionStatus(TransactionStatus.TS);
                    newTransaction.setCardBalance(response.getCurrentBalance() - request.getAmount());
                    Transaction savedTransaction = transactionRepository.save(newTransaction);

                    sendNotification(savedTransaction.getNotificationRequest());
                    sendProfitCalculator(savedTransaction.getProfitSharingRequest());

                    return TransactionStatus.TS;
                } else {
                    //add transaction with TF - Done
                    newTransaction.setTransactionStatus(TransactionStatus.TF);
                    transactionRepository.save(newTransaction);
                    throw new Exception("Transaction failed, the card doesn't have enough balance to perform the transaction.");
                }
            }

            throw new Exception("The system only support 16 digit card number and MASTER/VISA card type only.");

        }

        throw new Exception("Payment information is not provided.");
    }

    @Override
    public TransactionStatus processRegistrationPayment(RegistrationPayment request) throws Exception {
        if(!request.getCardNumber().isEmpty()){

            char cardStartWith = request.getCardNumber().charAt(0);

            if(request.getCardNumber().length() == 16 && (cardStartWith == '4' || cardStartWith == '5')){
                BankResponse response = getLastTransaction(request.getCardDetail());

                Transaction newTransaction = new Transaction();
                newTransaction.setUserId(request.getUserId());
                newTransaction.setCardNumber(request.getCardNumber());
                newTransaction.setCardBalance(response.getCurrentBalance());
                newTransaction.setTransactionValue(request.getAmount());
                newTransaction.setTransactionNumber(this.generateTransactionNo());
                newTransaction.setTransactionDate(new Date());
                newTransaction.setPaymentType(response.getPaymentType());
                newTransaction.setTransactionType(TransactionType.RegistrationFee);

                if(response.getCurrentBalance() >= request.getAmount()){
                    newTransaction.setTransactionStatus(TransactionStatus.TS);
                    newTransaction.setCardBalance(response.getCurrentBalance() - request.getAmount());
                    Transaction savedTransaction = transactionRepository.save(newTransaction);

                    sendNotification(savedTransaction.getNotificationRequest());
                    //verifyVendor(request);

                    return TransactionStatus.TS;

                } else {

                    newTransaction.setTransactionStatus(TransactionStatus.TF);
                    transactionRepository.save(newTransaction);
                    throw new Exception("Transaction failed, the card doesn't have enough balance to perform the transaction.");
                }
            }

            throw new Exception("The system only support 16 digit card number and MASTER/VISA card type only.");
        }

        throw new Exception("Payment information is not provided.");

    }


    private String generateTransactionNo(){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);

        return "TRNX-" + datetime;
    }

    private BankResponse getLastTransaction(CardDetail cardDetail) throws Exception {
        BankResponse response = bankService.processCard(cardDetail);
        System.out.println(cardDetail.getCardNumber());

        Transaction lastTransaction = transactionRepository.findFirstByCardNumberOrderByIdDesc(cardDetail.getCardNumber());

        if(lastTransaction != null){
            response.setCurrentBalance(lastTransaction.getCardBalance());
            response.setPaymentType(lastTransaction.getPaymentType());
        }

        return response;
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findByUserId(Long id) {
        return transactionRepository.findTransactionsByUserId(id);
    }

    @Override
    public Transaction findByOrderId(Long id) {
        return transactionRepository.findTransactionByOrderId(id);
    }

    private void sendNotification(NotificationRequest request){

        WebClient client = WebClient.create("http://notification-service:8088");

        Mono<String> response = client.post()
                .uri("/notification-service/notification/email/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);

        response.subscribe(result -> {
            logger.info("Confirmation email is sent for transaction : " + request.getTransactionNumber());
        }, error -> {
            logger.error("Failed to send confirmation email for transaction : " + request.getTransactionNumber());
        });
    }

    private void sendProfitCalculator(ProfitShareRequest request){
        WebClient client = WebClient.create("http://profit-service:8087");

        Mono<String> response = client.post()
                .uri("/api/v1/profit/processProfit")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);

        response.subscribe(result -> {
            //2. call user service - update user status
            logger.info("Profit sharing process is done for transaction" + request.getTransactionNumber());
        }, error -> {
            logger.error("Failed to process profit for transaction : " + request.getTransactionNumber());
        });
    }
    private void verifyVendor(RegistrationPayment request){
        Long userId = request.getUserId();
        Map<String, Long> pathVariables = new HashMap<>();
        pathVariables.put("id", userId);

        WebClient client = WebClient.create("http://user-service:8082");


        Mono<String> response = client.put()
                .uri("/api/v1/users/vendor/fullyVerify/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);

        response.subscribe(result -> {
            //2. call user service - update user status
            logger.info("Vendor with id " + userId + " is fully verified!");
        }, error -> {
            logger.error("Failed to fully verify a vendor with id: " + userId);
        });

    }

}
