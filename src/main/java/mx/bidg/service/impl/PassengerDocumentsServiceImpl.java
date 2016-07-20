package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.dao.PassengerDocumentsDao;
import mx.bidg.dao.PassengersDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.CPassengerDocumentsTypes;
import mx.bidg.model.CPlaneSeatsTypes;
import mx.bidg.model.PassengerDocuments;
import mx.bidg.model.Passengers;
import mx.bidg.pojos.FilePojo;
import mx.bidg.service.PassengerDocumentsService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by gerardo8 on 13/07/16.
 */
@Service
@Transactional
public class PassengerDocumentsServiceImpl implements PassengerDocumentsService {

    @Autowired
    private PassengerDocumentsDao passengerDocumentsDao;

    @Autowired
    private PassengersDao passengersDao;

    @Autowired
    private Environment env;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @Override
    public List<PassengerDocuments> findAll() {
        return passengerDocumentsDao.findAll();
    }

    @Override
    public PassengerDocuments findById(Integer id) {
        return passengerDocumentsDao.findById(id);
    }

    @Override
    public PassengerDocuments save(String data, Integer idPassenger) throws IOException {

        String SAVE_PATH = env.getRequiredProperty("travel_expenses.documents_dir");
        String[] fileMediaTypes = env.getRequiredProperty("travel_expenses.attachments.media_types").split(",");

        Passengers passenger = passengersDao.findById(idPassenger);
        
        if (passenger != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime now = LocalDateTime.now();

            FilePojo file = mapper.treeToValue(mapper.readTree(data),FilePojo.class);

            CPassengerDocumentsTypes passengerDocumentType = new CPassengerDocumentsTypes(1);

            List<PassengerDocuments> passengerDocuments
                    = passengerDocumentsDao.findByIdDocumentTypeAndIdPassenger(
                    passengerDocumentType.getIdPassengerDocumentType(),
                    passenger.getIdPassenger()
            );

            for(PassengerDocuments currentPassengerDocument : passengerDocuments) {
                currentPassengerDocument.setCurrentDocument(0);
                passengerDocumentsDao.update(currentPassengerDocument);
            }

            PassengerDocuments passengerDocument = new PassengerDocuments();
            passengerDocument.setDocumentName(file.getName());
            passengerDocument.setPassenger(passenger);
            passengerDocument.setPassengerDocumentType(passengerDocumentType);
            passengerDocument.setUploadingDate(now);
            passengerDocument.setCurrentDocument(1);

            passengerDocument = passengerDocumentsDao.save(passengerDocument);

            boolean isValidMediaType = false;

            for (String mediaType : fileMediaTypes) {
                if (file.getType().equals(mediaType)) {
                    isValidMediaType = true;
                    break;
                }
            }

            if (! isValidMediaType) {
                throw new ValidationException("Tipo de archivo no admitido", "Tipo de archivo no admitido");
            }

            String destDir = "/plane_tickets/passenger_" + passenger.getIdPassenger();
            String destFile = destDir + "/Documento." + passengerDocument.getIdPassengerDocument() +
                    "." + now.toInstant(ZoneOffset.UTC).getEpochSecond();

            passengerDocument.setDocumentUrl(destFile);
            passengerDocumentsDao.update(passengerDocument);

            File dir = new File(SAVE_PATH + destDir);
            if (! dir.exists()) {
                dir.mkdir();
            }

            String encodingPrefix = "base64,";
            int contentStartIndex = file.getDataUrl().indexOf(encodingPrefix) + encodingPrefix.length();
            byte[] byteArreyData = Base64.decodeBase64(file.getDataUrl().substring(contentStartIndex));

            FileOutputStream out = new FileOutputStream(new File(SAVE_PATH + destFile));
            out.write(byteArreyData);
            out.close();

            return passengerDocument;
            
        } else {
            throw new ValidationException("No existe", "No existe el boleto de avion.");
        }
    }

    @Override
    public PassengerDocuments update(PassengerDocuments passengerDocument) {
        return passengerDocumentsDao.update(passengerDocument);
    }

    @Override
    public Boolean delete(PassengerDocuments passengerDocument) {
        return passengerDocumentsDao.delete(passengerDocument);
    }
}
