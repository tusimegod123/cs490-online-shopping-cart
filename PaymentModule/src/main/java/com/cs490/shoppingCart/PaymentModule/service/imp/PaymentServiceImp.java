package com.cs490.shoppingCart.PaymentModule.service.imp;

import com.cs490.shoppingCart.PaymentModule.DTO.BankResponse;
import com.cs490.shoppingCart.PaymentModule.DTO.PaymentRequest;
import com.cs490.shoppingCart.PaymentModule.DTO.PaymentType;
import com.cs490.shoppingCart.PaymentModule.DTO.RegistrationPayment;
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

            char cardStartWith = request.getCardNumber().charAt(0);
//            System.out.println(re);
            if(request.getCardNumber().length() == 16 && (cardStartWith == '4' || cardStartWith == '5')){

                Transaction lastTransaction = transactionRepository.findTransactionsByCardNumberOrderByTransactionDate(request.getCardNumber());
                Double currentBalance = lastTransaction.getCardBalance();
                PaymentType type = lastTransaction.getPaymentType();

                if(lastTransaction.getCardNumber().isEmpty()){
                    BankResponse response = bankService.processCard(request.getCardDetail());
                    currentBalance = response.getCurrentBalance();
                    type = response.getPaymentType();
                }

                Transaction newTransaction = new Transaction();

                newTransaction.setUserId(request.getUserId());
                newTransaction.setOrderId(request.getOrderId());
                newTransaction.setCardNumber(request.getCardNumber());
                newTransaction.setCardBalance(currentBalance);
                newTransaction.setTransactionValue(request.getAmount());
                newTransaction.setTransactionNumber(this.generateTransactionNo());
                newTransaction.setTransactionDate(new Date());
                newTransaction.setPaymentType(type);
                newTransaction.setTransactionType(TransactionType.OrderPayment);

                if(currentBalance >= request.getAmount()){
                    //add transaction with TS
                    //call notification service
                    //call profit sharing service

                    newTransaction.setTransactionStatus(TransactionStatus.TS);
                    newTransaction.setCardBalance(currentBalance - request.getAmount());
                    transactionRepository.save(newTransaction);
                    return TransactionStatus.TS;

                } else {
                    //add transaction with TF
                    newTransaction.setTransactionStatus(TransactionStatus.TF);
                    transactionRepository.save(newTransaction);
                    return TransactionStatus.TF;
                }
            }
        }

        throw new Exception("The system only support 16 digit card number and MASTER/VISA card type only!");
    }

    @Override
    public TransactionStatus processRegistrationPayment(RegistrationPayment request) throws Exception {
        if(!request.getCardNumber().isEmpty()){

            //check if user exists

            char cardStartWith = request.getCardNumber().charAt(0);

            if(request.getCardNumber().length() == 16 && (cardStartWith == '4' || cardStartWith == '5')){
                Transaction lastTransaction = transactionRepository.findTransactionsByCardNumberOrderByTransactionDate(request.getCardNumber());
                Double currentBalance = lastTransaction.getCardBalance();
                PaymentType type = lastTransaction.getPaymentType();

                if(lastTransaction.getCardNumber().isEmpty()){
                    BankResponse response = bankService.processCard(request.getCardDetail());
                    currentBalance = response.getCurrentBalance();
                    type = response.getPaymentType();
                }

                Transaction newTransaction = new Transaction();

                newTransaction.setUserId(request.getUserId());
                newTransaction.setCardNumber(request.getCardNumber());
                newTransaction.setCardBalance(currentBalance);
                newTransaction.setTransactionValue(request.getAmount());
                newTransaction.setTransactionNumber(this.generateTransactionNo());
                newTransaction.setTransactionDate(new Date());
                newTransaction.setPaymentType(type);
                newTransaction.setTransactionType(TransactionType.RegistrationFee);

                if(currentBalance >= request.getAmount()){
                    //call notification service
                    //call user service - update user status

                    newTransaction.setTransactionStatus(TransactionStatus.TS);
                    newTransaction.setCardBalance(currentBalance - request.getAmount());
                    transactionRepository.save(newTransaction);
                    return TransactionStatus.TS;

                } else {

                    newTransaction.setTransactionStatus(TransactionStatus.TF);
                    transactionRepository.save(newTransaction);
                    return TransactionStatus.TF;
                }
            }
        }

        throw new Exception("The system only support 16 digit card number and MASTER/VISA card type only!");

    }


    private String generateTransactionNo(){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);

        return "TRNX-" + datetime;
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
