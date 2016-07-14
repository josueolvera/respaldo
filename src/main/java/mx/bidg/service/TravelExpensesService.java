package mx.bidg.service;

import mx.bidg.model.TravelExpenses;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
public interface TravelExpensesService {
    TravelExpenses save (TravelExpenses travelExpenses);
    TravelExpenses update (TravelExpenses travelExpenses);
    TravelExpenses findById (Integer idTravelExpenses);
    List<TravelExpenses> findAll();
    Boolean delete (TravelExpenses travelExpenses);
}
