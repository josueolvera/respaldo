package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.dao.PassengersDao;
import mx.bidg.dao.PlaneTicketsDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.CPlaneSeatsTypes;
import mx.bidg.model.Passengers;
import mx.bidg.model.PlaneTickets;
import mx.bidg.service.PassengersService;
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
public class PassengersServiceImpl implements PassengersService {

    @Autowired
    private PassengersDao passengersDao;

    @Autowired
    private PlaneTicketsDao planeTicketsDao;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<Passengers> findAll() {
        return passengersDao.findAll();
    }

    @Override
    public Passengers findById(Integer id) {
        return passengersDao.findById(id);
    }

    @Override
    public Passengers save(String data, Integer idPlaneTicket) throws IOException {
        PlaneTickets planeTicket = planeTicketsDao.findById(idPlaneTicket);
        if (planeTicket != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            JsonNode passengerNode = mapper.readTree(data);
            
            Passengers passenger = new Passengers();

            String birthdate = passengerNode.get("birthdate").asText();
            CPlaneSeatsTypes planeSeatType = mapper.treeToValue(passengerNode.get("planeSeatType"),CPlaneSeatsTypes.class);
            passenger.setBirthdate(LocalDateTime.parse(birthdate + " 00:00",formatter));
            passenger.setCompany(passengerNode.get("company").asText());

            if (passengerNode.hasNonNull("frequentPartner")) {
                passenger.setFrequentPartner(passengerNode.get("frequentPartner").asText());
            }

            passenger.setFullName(passengerNode.get("fullName").asText());
            passenger.setJob(passengerNode.get("job").asText());
            passenger.setPhoneNumber(passengerNode.get("phoneNumber").asText());
            passenger.setPlaneSeatType(planeSeatType);
            passenger.setPlaneTicket(planeTicket);
            passenger = passengersDao.save(passenger);
                
            return passenger;
        } else {
            throw new ValidationException("No existe", "No existe el boleto de avion.");
        }
    }

    @Override
    public Passengers update(Passengers passenger) {
        return passengersDao.update(passenger);
    }

    @Override
    public Boolean delete(Passengers passenger) {
        return passengersDao.delete(passenger);
    }
}
