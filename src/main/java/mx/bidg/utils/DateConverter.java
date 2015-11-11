/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.sql.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author sistemask
 */
@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<LocalDate, Date>{

    @Override
    public Date convertToDatabaseColumn(LocalDate x) {
        return (x == null)? null : Date.valueOf(x);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date y) {
        return (y == null)? null : y.toLocalDate();
    }
    
}
