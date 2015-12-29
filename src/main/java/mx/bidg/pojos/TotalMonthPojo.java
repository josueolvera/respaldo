/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.pojos;

import java.math.BigDecimal;

/**
 *
 * @author sistemask
 */
public class TotalMonthPojo {
    
    private int month;
    private BigDecimal montoConcept;

    public TotalMonthPojo() {
    }

    public TotalMonthPojo(int month, BigDecimal montoConcept) {
        this.month = month;
        this.montoConcept = montoConcept;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getMontoConcept() {
        return montoConcept;
    }

    public void setMontoConcept(BigDecimal montoConcept) {
        this.montoConcept = montoConcept;
    }
    
}
