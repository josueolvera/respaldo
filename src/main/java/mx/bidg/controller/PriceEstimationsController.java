/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.bidg.config.JsonViews;
import mx.bidg.events.requests.PriceEstimationAuthorizedEvent;
import mx.bidg.exceptions.InvalidFileException;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.PriceEstimations;
import mx.bidg.model.Users;
import mx.bidg.service.PriceEstimationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/estimations")
@PropertySource(value = {"classpath:application.properties"})
public class PriceEstimationsController {

    @Autowired
    private Environment env;

    @Autowired
    private PriceEstimationsService estimationsService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/request/{idRequest}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> save(@RequestBody String data, @PathVariable Integer idRequest,HttpSession session) throws Exception {

        Users user = (Users) session.getAttribute("user");
        PriceEstimations estimation = estimationsService.saveData(data, idRequest, user);

        if(estimation == null) {
            return new ResponseEntity<>("Error al guardar la cotizacion", HttpStatus.CONFLICT);
        }

//        eventPublisher.publishEvent(new PriceEstimationCreatedEvent(estimation.getRequest()));
        return new ResponseEntity<>(mapper.writeValueAsString(estimation),
                HttpStatus.OK);

    }


    @RequestMapping(value = "/{idEstimation}/attachment", method = RequestMethod.POST,
            produces = "application/json; charset=UTF-8")
    public @ResponseBody ResponseEntity<String> saveFile(@PathVariable int idEstimation,
                                                         @RequestParam("file") MultipartFile filePart) throws Exception {

        String SAVE_PATH = env.getRequiredProperty("estimations.documents_dir");
        String[] fileMediaTypes = env.getRequiredProperty("estimations.attachments.media_types").split(",");
        boolean isValidMediaType = false;

        if(!filePart.isEmpty()) {

            for (String mediaType : fileMediaTypes) {
                if (filePart.getContentType().equals(mediaType)) {
                    isValidMediaType = true;
                    break;
                }
            }

            if (! isValidMediaType) {
                throw new InvalidFileException("Tipo de archivo no admitido");
            }

            String destDir = "/estimation_" + idEstimation;
            String destFile = destDir + "/Documento." + Calendar.getInstance().getTimeInMillis();

            File dir = new File(SAVE_PATH + destDir);
            if (! dir.exists()) {
                if(!dir.mkdir()) {
                    throw new ValidationException("Error al crear el directorio: " + dir, "Error al crear el directorio");
                }
            }

            OutputStream outputStream = new FileOutputStream(new File(SAVE_PATH + destFile));
            InputStream inputStream = filePart.getInputStream();
            int read;
            byte[] buffer = new byte[1024];
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            PriceEstimations estimation = estimationsService.saveFileData(idEstimation, filePart.getOriginalFilename(),
                    destFile);

            return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(estimation),
                    HttpStatus.OK);

        } else {
            return new ResponseEntity<>("El archivo se encuentra vacio", HttpStatus.NOT_ACCEPTABLE);
        }

    }


    @RequestMapping(value = "/attachment/download/{idEstimation}", method = RequestMethod.GET)
    public void readFile(@PathVariable int idEstimation, HttpServletResponse response) throws IOException {
        String SAVE_PATH = env.getRequiredProperty("estimations.documents_dir");
        PriceEstimations estimation = estimationsService.findById(idEstimation);
        File file = new File(SAVE_PATH + estimation.getFilePath());

        if (! file.canRead()) {
            throw new IOException("El archivo "+ SAVE_PATH + estimation.getFilePath() +" no existe");
        }

        FileInputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + estimation.getFileName() + "\"");
        response.setContentLengthLong(file.length());

        byte[] buffer = new  byte[1024];
        int len;

        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
    
    
    @RequestMapping(value = "/request/{idRequest}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getEstimationsByRequest(@PathVariable int idRequest) throws Exception {
        List<PriceEstimations> list = estimationsService.findByIdRequest(idRequest);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(list), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/request/{idRequest}/authorized", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> findEstimationAuthorized(@PathVariable int idRequest) throws Exception {
        PriceEstimations estimation = estimationsService.findAuthorized(idRequest);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(estimation), HttpStatus.OK);
    }  
    
    @RequestMapping(value = "/authorization/{idEstimation}", method = RequestMethod.POST)
    public @ResponseBody String estimationAuthorization(@PathVariable int idEstimation, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        PriceEstimations estimation = estimationsService.estimationAuthorization(idEstimation, user);
        eventPublisher.publishEvent(new PriceEstimationAuthorizedEvent(estimation.getRequest()));
        return "Cotizacion autorizada";
    }

    @RequestMapping(value = "/reject/{idEstimation}", method = RequestMethod.POST)
    public ResponseEntity<String> rejectAuthorization(@PathVariable int idEstimation) {
        estimationsService.reject(idEstimation);
        return new ResponseEntity<>("Cotizacion cancelada", HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/{idEstimation}", method = RequestMethod.POST, headers = {"Accept=application/json;charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> modify(@PathVariable Integer idEstimation, @RequestBody String data, HttpSession seesion) throws Exception {
        Users user = (Users) seesion.getAttribute("user");
        PriceEstimations estimation = estimationsService.update(idEstimation, data, user);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(estimation),
                HttpStatus.OK
        );
    }


    @RequestMapping(value = "/{idEstimation}", method = RequestMethod.DELETE, headers = {"Accept=text/html;charset=UTF-8"})
    public @ResponseBody String delete(@PathVariable Integer idEstimation) throws Exception {
        if(estimationsService.delete(idEstimation)) {
            return "Cotizacion eliminada";
        } else {
            throw new ValidationException("La operacion de eliminacion fallo", "Error al eliminar la cotizacion", HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/authorize-estimation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> modify(@RequestBody String data, HttpSession seesion) throws Exception {
        Users user = (Users) seesion.getAttribute("user");

        JsonNode node = mapper.readTree(data);

        boolean outBudget = estimationsService.authorizePriceEstimations(node.get("idRequest").asInt(), node.get("idEstimation").asInt(), node.get("justify").asText(), user);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(outBudget),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> valiadteAmount(@RequestBody String data) throws Exception {

        JsonNode node = mapper.readTree(data);

        int outBudget = estimationsService.validatePriceEstimations(node.get("idRequest").asInt(), node.get("idEstimation").asInt());
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(outBudget),
                HttpStatus.OK
        );
    }

}
