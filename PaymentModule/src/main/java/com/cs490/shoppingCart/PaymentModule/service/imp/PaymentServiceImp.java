package com.cs490.shoppingCart.PaymentModule.service.imp;

import com.cs490.shoppingCart.PaymentModule.DTO.*;
import com.cs490.shoppingCart.PaymentModule.model.Transaction;
import com.cs490.shoppingCart.PaymentModule.model.TransactionStatus;
import com.cs490.shoppingCart.PaymentModule.model.TransactionType;
import com.cs490.shoppingCart.PaymentModule.repository.TransactionRepository;
import com.cs490.shoppingCart.PaymentModule.service.BankService;
import com.cs490.shoppingCart.PaymentModule.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PaymentServiceImp implements PaymentService {
    @Autowired
    BankService bankService;

    @Autowired
    TransactionRepository transactionRepository;

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
                    //add transaction with TS - Done

                    //rest call notification service
                    //rest call profit sharing service

                    NotificationRequest notificationRequest = new NotificationRequest();
                    notificationRequest.setFromSystemType(0);
                    notificationRequest.setOrderId(request.getOrderId());

                    //String response = restTemplate.postForObject("http://notification-service:8084/", notificationRequest, String.class);
                    // what response should I expect?

                    newTransaction.setTransactionStatus(TransactionStatus.TS);
                    newTransaction.setCardBalance(response.getCurrentBalance() - request.getAmount());
                    transactionRepository.save(newTransaction);
                    return TransactionStatus.TS;

                } else {
                    //add transaction with TF - Done
                    newTransaction.setTransactionStatus(TransactionStatus.TF);
                    transactionRepository.save(newTransaction);
                    return TransactionStatus.TF;
                }
            }

            throw new Exception("The system only support 16 digit card number and MASTER/VISA card type only!");

        }

        throw new Exception("Payment information is not provided!");
    }

    @Override
    public TransactionStatus processRegistrationPayment(RegistrationPayment request) throws Exception {
        if(!request.getCardNumber().isEmpty()){

            //check if user exists
            //call user service
            //Boolean userExist = restTemplate.getForObject("http://user-service:9091/users/getById/" + request.getUserId(), Boolean.class);

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
                    //call notification service
                    //call user service - update user status

                    NotificationRequest notificationRequest = new NotificationRequest();
                    notificationRequest.setFromSystemType(0);
                    notificationRequest.setOrderId(request.getUserId());

                    //String response = restTemplate.postForObject("http://notification-service:8084/", notificationRequest, String.class);
                    // what response should I expect?

                    newTransaction.setTransactionStatus(TransactionStatus.TS);
                    newTransaction.setCardBalance(response.getCurrentBalance() - request.getAmount());
                    transactionRepository.save(newTransaction);
                    return TransactionStatus.TS;

                } else {

                    newTransaction.setTransactionStatus(TransactionStatus.TF);
                    transactionRepository.save(newTransaction);
                    return TransactionStatus.TF;
                }
            }

            throw new Exception("The system only support 16 digit card number and MASTER/VISA card type only!");
        }

        throw new Exception("Payment information is not provided!");

    }


    private String generateTransactionNo(){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);

        return "TRNX-" + datetime;
    }

    private BankResponse getLastTransaction(CardDetail cardDetail) throws Exception {
        BankResponse response = new BankResponse();
        System.out.println(cardDetail.getCardNumber());

        Transaction lastTransaction = transactionRepository.findFirstByCardNumberOrderByIdDesc(cardDetail.getCardNumber());

        if(lastTransaction == null){
            response = bankService.processCard(cardDetail);
        } else {
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
    public List<Transaction> findByUserId(Integer id) {
        return transactionRepository.findTransactionsByUserId(id);
    }

    @Override
    public Transaction findByOrderId(Integer id) {
        return transactionRepository.findTransactionByOrderId(id);
    }

}
