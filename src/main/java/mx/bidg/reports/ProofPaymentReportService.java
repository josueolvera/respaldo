package mx.bidg.reports;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 02/07/2017.
 */
@Service
@Transactional
public class ProofPaymentReportService {

    @Autowired
    private ObjectMapper mapper;

    public byte [] getReportePrueba(String data) {
        try {
            JsonNode node = mapper.readTree(data);
            InputStream resource = this.getClass().getResourceAsStream("/reports/ComprobantePago.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(resource);

            HashMap<String, Object> params = new HashMap<String, Object>();

            String url1 = getClass().getResource("/BID_GROUP.png").toString();
            params.put("logo",  url1);
            Date date = new Date();
            DateFormat hourdateFormat = new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss");
            //String fechaLocal = LocalDate.now().toString();
            List<String> listaFolio = new ArrayList<String>();
            List<String> listaCostCenter = new ArrayList<String>();
            List<String> listaRequestCategory = new ArrayList<String>();
            List<String> listaProvider = new ArrayList<String>();
            List<String> listaBankProvider = new ArrayList<String>();
            List<String> listaCuenta = new ArrayList<String>();
            List<String> listaClabe = new ArrayList<String>();
            List<String> listaFolioFactura = new ArrayList<String>();
            List<String> listaMonto = new ArrayList<String>();
            List<String> listaFechaPago = new ArrayList<String>();
            List<String> listaBankDistributor = new ArrayList<String>();
            for(JsonNode jsonNode : node.get("requestsSelected")) {
                listaFolio.add(jsonNode.get("folio").asText());
                listaCostCenter.add(jsonNode.get("distributorCostCenter").get("costCenter").get("name").asText());
                listaRequestCategory.add(jsonNode.get("requestCategory").get("requestCategoryName").asText());
                listaProvider.add(jsonNode.get("purchaseInvoices").get("provider").get("providerName").asText());
                listaBankProvider.add(jsonNode.get("purchaseInvoices").get("account").get("bank").get("acronyms").asText());
                listaCuenta.add(jsonNode.get("purchaseInvoices").get("account").get("accountNumber").asText());
                listaClabe.add(jsonNode.get("purchaseInvoices").get("account").get("accountClabe").asText());
                listaFolioFactura.add(jsonNode.get("purchaseInvoices").get("folio").asText());
                listaMonto.add(jsonNode.get("purchaseInvoices").get("amountWithIva").asText());
                listaFechaPago.add(jsonNode.get("requestsDates").get("scheduledDateFormats").get("dateNumber").asText());
                listaBankDistributor.add(jsonNode.get("bank").get("banks").get("acronyms").asText());
            }
            params.put("folio", listaFolio);
            params.put("centroCostos", listaCostCenter);
            params.put("tipoSolicitud", listaRequestCategory);
            params.put("beneficiario", listaProvider);
            params.put("bancoBeneficiario", listaBankProvider);
            params.put("cuenta", listaCuenta);
            params.put("clabe", listaClabe);
            params.put("numFactura", listaFolioFactura);
            params.put("monto", listaMonto);
            params.put("fechaPago", listaFechaPago);
            params.put("bancoProveedor", listaBankDistributor);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\User\\Desktop\\comprobantePago_"+hourdateFormat.format(date)+".pdf");
            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\User\\Documents\\comprobantePago_"+hourdateFormat.format(date)+".pdf");
            JasperViewer.viewReport(jasperPrint,true);
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception ex) {
            System.out.println("error");
            System.err.println("Exception ReportesService.getReporteFichaTecnica");
            //errorMessage = ResponseHTTP.regresaMensajeExcepcion("Exception ReportesService.getReporteFichaTecnica", ex);
            ex.printStackTrace();
            return null;
        }
    }
}
