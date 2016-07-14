package mx.bidg.service;

import mx.bidg.model.CTravelExpensesConcepts;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
public interface CTravelExpensesConceptsService {

    CTravelExpensesConcepts save (CTravelExpensesConcepts cTravelExpensesConcepts);
    CTravelExpensesConcepts update (CTravelExpensesConcepts cTravelExpensesConcepts);
    CTravelExpensesConcepts findById (Integer idTravelExpensesConcepts);
    List<CTravelExpensesConcepts> findAll ();
    Boolean delete (CTravelExpensesConcepts cTravelExpensesConcepts);
}
