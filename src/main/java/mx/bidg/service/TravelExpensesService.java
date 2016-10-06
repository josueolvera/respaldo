package mx.bidg.service;

import mx.bidg.model.TravelExpenses;
import mx.bidg.model.Users;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
public interface TravelExpensesService {

    TravelExpenses save (String data, Users user) throws IOException;

    TravelExpenses update (TravelExpenses travelExpenses);

    TravelExpenses findById (Integer idTravelExpenses);

    List<TravelExpenses> findAll();

    Boolean delete (TravelExpenses travelExpenses);

    List<TravelExpenses> getTravelExpenses(Integer idUser);

    TravelExpenses changeRequestStatus(Integer idTravelExpense, String data) throws IOException;
}
