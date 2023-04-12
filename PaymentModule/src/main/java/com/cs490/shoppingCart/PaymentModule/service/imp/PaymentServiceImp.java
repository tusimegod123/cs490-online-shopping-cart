package com.cs490.shoppingCart.PaymentModule.service.imp;

import com.cs490.shoppingCart.PaymentModule.DTO.PaymentRequestDTO;
import com.cs490.shoppingCart.PaymentModule.model.Transaction;
import com.cs490.shoppingCart.PaymentModule.model.TransactionStatus;
import com.cs490.shoppingCart.PaymentModule.repository.TransactionRepository;
import com.cs490.shoppingCart.PaymentModule.service.BankService;
import com.cs490.shoppingCart.PaymentModule.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PaymentServiceImp implements PaymentService {
    @Autowired
    BankService bankService;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public TransactionStatus processPayment(PaymentRequestDTO request) throws Exception {
        if(!request.getCardNumber().isEmpty()){
            char cardStartWith = request.getCardNumber().charAt(0);
            if(request.getCardNumber().length() == 16 && (cardStartWith == 4 || cardStartWith ==5)){
                Double currentBalance = bankService.processCard(request.getCardDetail());

                //check balance from transaction table too

                Transaction newTransaction = new Transaction();

                newTransaction.setUserId(request.getUserId());
                newTransaction.setOrderId(request.getOrderId());
                newTransaction.setCardNumber(request.getCardNumber());
                newTransaction.setCardBalance(currentBalance);
                newTransaction.setTransactionValue(request.getAmount());
                newTransaction.setTransactionNumber(this.generateTransactionNo());
                newTransaction.setTransactionDate(new Date());

                if(currentBalance >= request.getAmount()){
                    //add transaction with TS
                    //call notification service
                    //call profit sharing service

                    newTransaction.setTransactionStatus(TransactionStatus.TS);
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

    private String generateTransactionNo(){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);

        return "TRNX-" + datetime;
    }
}
