package mx.bidg.service;

import mx.bidg.model.TravelExpenseConcept;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
public interface TravelExpenseConceptService {
    TravelExpenseConcept save (TravelExpenseConcept travelExpenseConcept);
    TravelExpenseConcept update(TravelExpenseConcept travelExpenseConcept);
    TravelExpenseConcept findById (Integer idRequestConcept);
    List<TravelExpenseConcept> findAll();
    Boolean delete (TravelExpenseConcept travelExpenseConcept);
}
