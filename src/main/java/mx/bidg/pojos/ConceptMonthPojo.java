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
public class ConceptMonthPojo {
    
    private int month;
    private String name;
    private BigDecimal amountConcept;

    public ConceptMonthPojo() {
    }

    public ConceptMonthPojo(int month, String name, BigDecimal amountConcept) {
        this.month = month;
        this.name = name;
        this.amountConcept = amountConcept;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmountConcept() {
        return amountConcept;
    }

    public void setAmountConcept(BigDecimal amountConcept) {
        this.amountConcept = amountConcept;
    }
    
}
