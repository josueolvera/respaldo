package mx.bidg.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 *
 * @author rubens
 */
public class MoneyConverter {

   public static BigDecimal obtainNumber(String value) throws ParseException
   {
       String[] numero = value.split("\\$");
       DecimalFormat df = new DecimalFormat("#,###");
       BigDecimal decimal = new BigDecimal(df.parse(numero[1]).longValue());
       return decimal;
   }
    
}

