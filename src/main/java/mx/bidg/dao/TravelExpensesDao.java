package mx.bidg.dao;

import mx.bidg.model.TravelExpenses;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
public interface TravelExpensesDao extends InterfaceDao<TravelExpenses> {
    List<TravelExpenses> getTravelExpenses(Integer idUser);
}
