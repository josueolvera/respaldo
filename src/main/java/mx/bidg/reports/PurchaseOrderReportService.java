package mx.bidg.reports;

import mx.bidg.dao.ProviderAddressDao;
import mx.bidg.dao.ProvidersContactDao;
import mx.bidg.dao.RequestOrderDetailDao;
import mx.bidg.dao.RequestOrderDocumentsDao;
import mx.bidg.model.ProviderAddress;
import mx.bidg.model.ProvidersContact;
import mx.bidg.model.RequestOrderDetail;
import mx.bidg.model.RequestOrderDocuments;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 06/07/2017.
 */
@Service
@Transactional
public class PurchaseOrderReportService {
    @Autowired
    RequestOrderDocumentsDao requestOrderDocumentsDao;

    @Autowired
    ProviderAddressDao providerAddressDao;

    @Autowired
    ProvidersContactDao providersContactDao;

    @Autowired
    RequestOrderDetailDao requestOrderDetailDao;

    public byte [] getReportPurchaseOrder() {
        try {
            InputStream resource = this.getClass().getResourceAsStream("/reports/Orden_Compra.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(resource);
            HashMap<String, Object> params = new HashMap<String, Object>();
            RequestOrderDocuments request = requestOrderDocumentsDao.findByIdRequest(45);

            String url1 = getClass().getResource("/BID_GROUP.png").toString();
            params.put("logo",  url1);
            Date date = new Date();
            DateFormat hourdateFormat = new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss");
            DateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");
            params.put("fecha", dateFormat.format(date));
            params.put("numOrden", request.getIdRequestOrderDocument().toString());
            //Emite
            params.put("empresaEmite", request.getcDistributor().getDistributorName());
            params.put("direccionEmite", request.getcDistributor().getAddress());
            params.put("ciudadEmite", request.getcDistributor().getCity());
            params.put("cpEmite", request.getcDistributor().getPostcode());
            params.put("rfcEmite", request.getcDistributor().getRfc());
            //Emitido para
            ProviderAddress providerAddress = providerAddressDao.findByIdProvider(request.getIdProvider());
            ProvidersContact providersContact = providersContactDao.findByIdProvider(request.getIdProvider());
            params.put("empresaEmitido", request.getProvider().getProviderName());
            params.put("direccionEmitido", providerAddress.getStreet());
            params.put("ciudadEmitido", providerAddress.getAsentamiento().getNombreAsentamiento());
            params.put("cpEmitido", providerAddress.getAsentamiento().getCodigoPostal());
            params.put("rfcEmitido", request.getProvider().getRfc());
            //Contacto del emisor
            params.put("nombreEmisor", request.getContactNameTransmitter());
            params.put("puestoEmisor", request.getContactRoleTransmitter());
            params.put("telEmisor", request.getContactTelTransmitter());
            //Contacto del receptor
            params.put("nombreReceptor", providersContact.getName());
            params.put("puestoReceptor", providersContact.getPost());
            params.put("telReceptor", providersContact.getPhoneNumber());
            //Llena tabla de productos
            List<RequestOrderDetail> requestOrderDetailList = requestOrderDetailDao.findByidReqOrderDocument(request.getIdRequestOrderDocument());
            List<String> listaDescripcion = new ArrayList<String>();
            List<String> listaCantidad = new ArrayList<String>();
            List<String> listaPrecio = new ArrayList<String>();
            for(RequestOrderDetail detail : requestOrderDetailList){
                listaDescripcion.add(detail.getDescription());
                listaCantidad.add(detail.getQuantity().toString());
                listaPrecio.add(detail.getPrice().toString());
            }
            params.put("descripcion", listaDescripcion);
            params.put("cantidad", listaCantidad);
            params.put("precio", listaPrecio);
            //Totales
            params.put("subTotal", request.getAmount().toString());
            params.put("iva", request.getIva().toString());
            params.put("total", request.getTotalAmount().toString());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            //JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\User\\Desktop\\OrdenCompra_"+hourdateFormat.format(date)+".pdf");
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
