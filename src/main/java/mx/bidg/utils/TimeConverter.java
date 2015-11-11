/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.utils;

import java.sql.Time;
import java.time.LocalTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author sistemask
 */
@Converter(autoApply = true)
public class TimeConverter implements AttributeConverter<LocalTime, Time> {
    
    @Override
    public Time convertToDatabaseColumn(LocalTime x) {
        return (x == null)? null : Time.valueOf(x);
    }

    @Override
    public LocalTime convertToEntityAttribute(Time y) {
        return (y == null)? null : y.toLocalTime();
    }
    
}
