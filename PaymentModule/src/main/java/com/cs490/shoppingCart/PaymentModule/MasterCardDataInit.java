package com.cs490.shoppingCart.PaymentModule;

import com.cs490.shoppingCart.PaymentModule.model.MasterCard;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;
import com.cs490.shoppingCart.PaymentModule.repository.MasterCardRepository;
import com.cs490.shoppingCart.PaymentModule.repository.VisaCardRepository;
import com.cs490.shoppingCart.PaymentModule.service.MasterCardService;
import com.cs490.shoppingCart.PaymentModule.service.VisaCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Component
public class MasterCardDataInit implements CommandLineRunner {

    @Autowired
    private VisaCardRepository visaCardRepository;

    @Autowired
    private MasterCardRepository masterCardRepository;

    @Override
    public void run(String... args)  {
        init();
    }

    public void init() {

//        LocalDate currentDate = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(currentDate);
//        calendar.add(Calendar.YEAR, 2);
//        Date expiryDate = calendar.getTime();

        LocalDate currentDate = LocalDate.now();
        LocalDate expiryDate = currentDate.plusYears(2);

        MasterCard mCardOne = new MasterCard(null,"5567567867897890", "Nqobane", "328", 8000.0, 9000.0, expiryDate, true);
        MasterCard mCardTwo = new MasterCard(null,"5567567867897891", "Selam", "344", 8000.0, 9000.0, expiryDate, true);
        MasterCard mCardThr = new MasterCard(null,"5567567867897892", "Hiwot", "667", 8000.0, 9000.0, expiryDate, true);
        MasterCard mCardFou = new MasterCard(null,"5567567867897893", "Gebre", "890", 8000.0, 9000.0, expiryDate, true);
        MasterCard mCardFiv =new MasterCard(null,"5567567867897894", "Godwin", "452", 8000.0, 9000.0, expiryDate, true);

        VisaCard vCardOne = new VisaCard(null,"4567567867897891", "Sonyta", "674", 8000.0, 9000.0, expiryDate, true);
        VisaCard vCardTwo = new VisaCard(null,"4567567867897892", "Sopheary", "832", 8000.0, 9000.0, expiryDate, true);
        VisaCard vCardThr = new VisaCard(null,"4567567867897893", "Mahlet", "222", 8000.0, 9000.0, expiryDate, true);
        VisaCard vCardFour = new VisaCard(null,"4567567867897894", "Megdi", "454", 8000.0, 9000.0, expiryDate, true);
        VisaCard vCardFive = new VisaCard(null,"4567567867897895", "Barra", "545", 8000.0, 9000.0, expiryDate, true);


        saveMCard(mCardOne);
        saveMCard(mCardTwo);
        saveMCard(mCardThr);
        saveMCard(mCardFou);
        saveMCard(mCardFiv);

        saveVCard(vCardOne);
        saveVCard(vCardTwo);
        saveVCard(vCardThr);
        saveVCard(vCardFour);
        saveVCard(vCardFive);
    }

    private void saveVCard(VisaCard card){
       if(visaCardRepository.getVisaCardByCardNumber(card.getCardNumber()) == null){
           visaCardRepository.save(card);
       }
    }

    private void saveMCard(MasterCard card){
        if(masterCardRepository.getMasterCardByCardNumber(card.getCardNumber()) == null){
            masterCardRepository.save(card);
        }
    }
}
