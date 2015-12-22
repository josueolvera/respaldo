package mx.bidg.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 *
 * @author rubens
 */
public class MoneyConverter {

   public static Long obtainNumber(String value) throws ParseException
   {
       String[] numero = value.split("\\$");
       DecimalFormat df = new DecimalFormat("#,###");
       return df.parse(numero[1]).longValue();
   }
    
}

