package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.PayRequestsHistoryDao;
import mx.bidg.model.PayRequestsHistory;
import mx.bidg.model.Users;
import mx.bidg.service.PayRequestsHistoryService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 21/06/2017.
 */
@Service
@Transactional
public class PayRequestsHistoryServiceImpl implements PayRequestsHistoryService{

    @Autowired
    PayRequestsHistoryDao payRequestsHistoryDao;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<PayRequestsHistory> findAll() {
        return payRequestsHistoryDao.findAll();
    }

    @Override
    public List<PayRequestsHistory> saveData(String data, Users user) throws IOException {
        JsonNode node = mapper.readTree(data);

        List<PayRequestsHistory> payRequestsHistories = new ArrayList<>();
        for(JsonNode jsonNode : node.get("requestsSelected")){
            PayRequestsHistory payRequestsHistory = new PayRequestsHistory();

            payRequestsHistory.setIdRequest(jsonNode.get("idRequest").asInt());
            payRequestsHistory.setIdCostCenter(jsonNode.get("distributorCostCenter").get("idCostCenter").asInt());
            payRequestsHistory.setIdRequestCategory(jsonNode.get("idRequestCategory").asInt());
            payRequestsHistory.setIdProvider(jsonNode.get("purchaseInvoices").get("idProvider").asInt());
            payRequestsHistory.setIdAccount(jsonNode.get("purchaseInvoices").get("account").get("idAccount").asInt());
            payRequestsHistory.setIdPurchaseInvoices(jsonNode.get("purchaseInvoices").get("idPurchaseInvoices").asInt());
            payRequestsHistory.setIdRequestsDates(jsonNode.get("requestsDates").get("idRequestsDates").asInt());
            payRequestsHistory.setIdDistributorDetailBank(jsonNode.get("bank").get("idDistributorDetailBank").asInt());
            payRequestsHistory.setIdDistributor(jsonNode.get("distributorCostCenter").get("idDistributor").asInt());

            payRequestsHistory.setDistributor(jsonNode.get("distributorCostCenter").get("distributors").get("distributorName").asText());
            payRequestsHistory.setFolio(jsonNode.get("folio").asText());
            payRequestsHistory.setCostCenter(jsonNode.get("distributorCostCenter").get("costCenter").get("name").asText());
            payRequestsHistory.setRequestCategory(jsonNode.get("requestCategory").get("requestCategoryName").asText());
            payRequestsHistory.setProvider(jsonNode.get("purchaseInvoices").get("provider").get("providerName").asText());
            payRequestsHistory.setBankProvider(jsonNode.get("purchaseInvoices").get("account").get("bank").get("bankName").asText());
            payRequestsHistory.setAccountNumber(jsonNode.get("purchaseInvoices").get("account").get("accountNumber").asText());
            payRequestsHistory.setAccountClabe(jsonNode.get("purchaseInvoices").get("account").get("accountClabe").asText());
            payRequestsHistory.setPurchaseInvoiceFolio(jsonNode.get("purchaseInvoices").get("folio").asText());
            payRequestsHistory.setAmountWithIva(jsonNode.get("purchaseInvoices").get("amountWithIva").decimalValue());
            LocalDateTime requestDate = LocalDateTime.parse(jsonNode.get("requestsDates").get("scheduledDateFormats").get("iso").asText(), DateTimeFormatter.ISO_DATE_TIME);
            payRequestsHistory.setRequestDate(requestDate);
            payRequestsHistory.setBankDistributor(jsonNode.get("bank").get("banks").get("bankName").asText());
            payRequestsHistory.setUsername(user.getUsername());
            payRequestsHistory.setCreationDate(LocalDateTime.now());

            payRequestsHistories.add(payRequestsHistory);
        }
        for(PayRequestsHistory payRequest : payRequestsHistories){
            payRequest = payRequestsHistoryDao.save(payRequest);
        }

        return payRequestsHistories;
    }

    @Override
    public void payRequestsReport(OutputStream stream, LocalDateTime fromDate, LocalDateTime toDate) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        List<PayRequestsHistory> payRequestsHistoryList = payRequestsHistoryDao.findBetweenDates(fromDate, toDate);

        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cellDateStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        Sheet hoja = workbook.createSheet("Cuentas_Pagadas");
        Row row = hoja.createRow(0);

        row.createCell(0).setCellValue("EMPRESA");
        row.createCell(1).setCellValue("FOLIO");
        row.createCell(2).setCellValue("CENTRO DE COSTOS");
        row.createCell(3).setCellValue("TIPO DE SOLICITUD");
        row.createCell(4).setCellValue("BENEFICIARIO");
        row.createCell(5).setCellValue("BANCO");
        row.createCell(6).setCellValue("CUENTA");
        row.createCell(7).setCellValue("CLABE");
        row.createCell(8).setCellValue("NÚMERO DE FACTURA");
        row.createCell(9).setCellValue("MONTO CON IVA");
        row.createCell(10).setCellValue("FECHA DE PAGO");
        row.createCell(11).setCellValue("BANCO");

        //Implementacion del estilo
        for (Cell celda : row) {
            celda.setCellStyle(style);
        }

        int aux = 1;

        for (PayRequestsHistory request : payRequestsHistoryList){
            row = hoja.createRow(aux);

            row.createCell(0).setCellValue(request.getDistributor());
            row.createCell(1).setCellValue(request.getFolio());
            row.createCell(2).setCellValue(request.getCostCenter());
            row.createCell(3).setCellValue(request.getRequestCategory());
            row.createCell(4).setCellValue(request.getProvider());
            row.createCell(5).setCellValue(request.getBankProvider());
            row.createCell(6).setCellValue(request.getAccountNumber());
            row.createCell(7).setCellValue(request.getAccountClabe());
            row.createCell(8).setCellValue(request.getPurchaseInvoiceFolio());
            row.createCell(9).setCellValue(request.getAmountWithIva().doubleValue());
            if (request.getCreationDate() != null){
                Date fecha = Date.from(request.getRequestDate().atZone(ZoneId.systemDefault()).toInstant());

                if (fecha != null){
                    row.createCell(10);
                    row.getCell(10).setCellValue(fecha);
                    row.getCell(10).setCellStyle(cellDateStyle);

                }
            }
            row.createCell(11).setCellValue(request.getBankDistributor());
            aux++;
        }
        workbook.write(stream);
    }

    @Override
    public  List<PayRequestsHistory> findBetweenDates (LocalDateTime fromDate, LocalDateTime toDate){
        return payRequestsHistoryDao.findBetweenDates(fromDate, toDate);
    }
}
