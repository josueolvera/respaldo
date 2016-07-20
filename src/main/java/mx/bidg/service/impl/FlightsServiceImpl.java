package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.dao.FlightsDao;
import mx.bidg.dao.PlaneTicketsDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.CTravelCities;
import mx.bidg.model.Flights;
import mx.bidg.model.PlaneTickets;
import mx.bidg.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@Service
@Transactional
public class FlightsServiceImpl implements FlightsService {

    @Autowired
    private FlightsDao flightsDao;

    @Autowired
    private PlaneTicketsDao planeTicketsDao;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @Override
    public List<Flights> findAll() {
        return flightsDao.findAll();
    }

    @Override
    public Flights findById(Integer id) {
        return flightsDao.findById(id);
    }

    @Override
    public Flights save(String data, Integer idPlaneTicket) throws IOException {
        PlaneTickets planeTicket = planeTicketsDao.findById(idPlaneTicket);
        if (planeTicket != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            JsonNode flightNode = mapper.readTree(data);

            Flights flight = new Flights();
            CTravelCities cityOrigin = mapper.treeToValue(flightNode.get("cityOrigin"),CTravelCities.class);
            CTravelCities cityDestination = mapper.treeToValue(flightNode.get("cityDestination"),CTravelCities.class);
            flight.setCityOrigin(cityOrigin);
            flight.setCityDestination(cityDestination);
            String departureDate = flightNode.get("departureDate").asText();
            flight.setDepartureDate(LocalDateTime.parse(departureDate + " 00:00",formatter));
            flight.setPlaneTicket(planeTicket);

            if (flightNode.hasNonNull("returnDate")) {

                String returnDate = flightNode.get("returnDate").asText();

                Flights flight1 = new Flights();
                flight1.setCityOrigin(cityDestination);
                flight1.setCityDestination(cityOrigin);
                flight1.setDepartureDate(LocalDateTime.parse(returnDate + " 00:00",formatter));
                flight1.setPlaneTicket(planeTicket);
                flightsDao.save(flight1);
            }
            
            flight = flightsDao.save(flight);
            
            return flight;
        } else {
            throw new ValidationException("No existe", "No existe el boleto de avion."); 
        }
    }

    @Override
    public Flights update(Flights flight) {
        return flightsDao.update(flight);
    }

    @Override
    public Boolean delete(Flights flight) {
        return flightsDao.delete(flight);
    }
}
