package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.PriceEstimations;
import mx.bidg.model.PurchaseInvoicesFiles;
import mx.bidg.service.PurchaseInvoicesFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by desarrollador on 15/06/17.
 */
@Controller
@RequestMapping("purchase-invoices-files")
public class PurchaseInvoicesFilesController {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Environment env;

    @Autowired
    private PurchaseInvoicesFilesService purchaseInvoicesFilesService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll()throws IOException {
        List<PurchaseInvoicesFiles> purchaseInvoicesFiles = purchaseInvoicesFilesService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(purchaseInvoicesFiles), HttpStatus.OK);
    }

    @RequestMapping(value = "/attachment/download/{idPurchaseInvoicesFiles}", method = RequestMethod.GET)
    public void readFile(@PathVariable int idPurchaseInvoicesFiles, HttpServletResponse response) throws IOException {
        String SAVE_PATH = env.getRequiredProperty("purchaseInvoice.documents_dir");
        PurchaseInvoicesFiles purchaseInvoicesFile = purchaseInvoicesFilesService.findById(idPurchaseInvoicesFiles);
        File file = new File(SAVE_PATH + purchaseInvoicesFile.getFilePath());

        if (! file.canRead()) {
            throw new IOException("El archivo "+ SAVE_PATH + purchaseInvoicesFile.getFilePath() +" no existe");
        }

        FileInputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + purchaseInvoicesFile.getFileName() + "\"");
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
}
