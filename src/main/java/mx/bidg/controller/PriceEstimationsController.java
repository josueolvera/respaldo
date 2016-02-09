/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

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
import mx.bidg.exceptions.InvalidFileException;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.PriceEstimations;
import mx.bidg.model.Users;
import mx.bidg.service.PriceEstimationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
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
    PriceEstimationsService estimationsService;
    
    ObjectMapper mapper = new ObjectMapper();
    
    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json; charset=UTF-8"},
            produces = "application/json; charset=UTF-8")
    public @ResponseBody ResponseEntity<String> save(@RequestBody String data, HttpSession session) throws Exception {
        
        Users user = (Users) session.getAttribute("user");
        List<PriceEstimations> estimations = estimationsService.saveData(data, user);
        
        if(estimations.isEmpty())
            return new ResponseEntity<>("Error al guardar la cotizacion", HttpStatus.CONFLICT);
        
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(estimations), 
                HttpStatus.OK);
        
    }
    
    
    @RequestMapping(value = "/{idEstimation}/attachment", method = RequestMethod.POST, 
            headers = {"Accept=application/json; charset=UTF-8"})
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
        String SAVE_PATH = env.getRequiredProperty("stock.documents_dir");
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
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list), HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/authorization/{idEstimation}", method = RequestMethod.POST)
    public @ResponseBody String estimationAuthorization(@PathVariable int idEstimation, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        estimationsService.estimationAuthorization(idEstimation, user);
        return "Cotizacion autorizada";
    }
    
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST, headers = {"Accept=application/json;charset=UTF-8"})
    public @ResponseBody String modify(@RequestBody PriceEstimations estimation) throws Exception {
        estimationsService.update(estimation);
        return "Modificacion exitosa";
    }
    
}
