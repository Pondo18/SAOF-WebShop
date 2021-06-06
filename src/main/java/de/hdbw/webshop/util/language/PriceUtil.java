package de.hdbw.webshop.util.language;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static org.apache.commons.math3.util.Precision.round;

@Component
public class PriceUtil {

    public double getPriceInCorrectCurrencyInputIsInEUR (double priceInEur) {
        Locale locale = LocaleContextHolder.getLocale();
        return locale.equals(Locale.ENGLISH) ?
                getWith1Digit(priceInEur * 1.22) : getWith1Digit(priceInEur);
    }

    public double getPriceInEURInputIsUnknown (double priceUnknownCurrency) {
        Locale locale = LocaleContextHolder.getLocale();
        return locale.equals(Locale.ENGLISH)?
                getWith2Digits(priceUnknownCurrency / 1.22) : getWith2Digits(priceUnknownCurrency);
    }

    public double getWith2Digits (double price) {
        return round(price, 2);
    }

    public double getWith1Digit (double price) {
        return round(price, 1);
    }

//    public double getEuroToDollarExchangeRate () throws IOException {
//        String apiUrl = "http://www.google.com/ig/calculator?hl=en&amp;amp;q=";
//        String baseCurrency = "EUR";
//        String termCurrency = "USD";
//        String charset = "UTF-8";
//        URL url = new URL(apiUrl + baseCurrency + "%3D%3F" + termCurrency);
//        Reader reader = new InputStreamReader(url.openStream(), charset);
//        Result result = new Gson().fromJson(reader, Result.class);
//        String t = result.toString();
////        String amount = result.getRhs().split("\\s+")[0];
////        System.out.println(amount);
//        return 0;
//    }
}
