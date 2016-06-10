package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.BalancesDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.dao.TransactionsDao;
import mx.bidg.model.*;
import mx.bidg.service.TransactionsService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
@Service
@Transactional
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    TransactionsDao transactionsDao;

    @Autowired
    BalancesDao balancesDao;

    @Autowired
    RequestsDao requestsDao;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Transactions findById(Integer id) {
        return transactionsDao.findById(id);
    }

    @Override
    public Transactions save(Transactions transaction) {
        transactionsDao.save(transaction);
        return transaction;
    }

    @Override
    public Transactions update(Transactions transaction) {
        transactionsDao.update(transaction);
        return transaction;
    }

    @Override
    public boolean delete(Transactions transaction) {
        transactionsDao.delete(transaction);
        return true;
    }

    @Override
    public List<Transactions> findAll() {
        return transactionsDao.findAll();
    }

    @Override
    public void entryPayAccount(String data, Users user) throws IOException {

            JsonNode node = mapper.readTree(data);

            Transactions transactions = new Transactions();
            transactions.setAccountsPayable(null);
            transactions.setAmount(node.get("amount").decimalValue());
            Balances balances = balancesDao.findById(node.get("idBalance").asInt());
            transactions.setCurrencies(new CCurrencies(node.get("idCurrency").asInt()));
            transactions.setOperationTypes(COperationTypes.INGRESO);
            transactions.setTransactionsStatus(CTransactionsStatus.PAGADA);
            transactions.setTransactionNumber(node.get("transactionNumber").asText());
            transactions.setUser(user);
            transactions.setCreationDate(LocalDateTime.now());
            transactions.setIdAccessLevel(1);


            BigDecimal addAmountTransaction = balances.getCurrentAmount().add(transactions.getAmount());
            balances.setCurrentAmount(addAmountTransaction);
            balances.setModificationDate(LocalDateTime.now());
            balancesDao.update(balances);

            transactions.setBalances(balances);
            transactionsDao.save(transactions);

    }

    @Override
    public void transacctionsByDayReport(LocalDateTime ofDate, LocalDateTime untilDate, OutputStream stream) throws IOException {

            List<Transactions> transactions = transactionsDao.findTransactionByDate(ofDate,untilDate);

            Workbook wb = new HSSFWorkbook();
            //Definicion del estilo de la cabecera
            Font font = wb.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 10);
            font.setFontName("Arial");
            font.setColor(IndexedColors.WHITE.getIndex());
            CellStyle style = wb.createCellStyle();
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

            Sheet hoja = wb.createSheet();
            //Se crea la fila que contiene la cabecera
            Row row = hoja.createRow(0);

            row.createCell(0).setCellValue("CONCEPTO");
            row.createCell(1).setCellValue("MONTO");
            row.createCell(2).setCellValue("FECHA DE TRANSACCION");

            //Implementacion del estilo
            for (Cell celda : row) {
                celda.setCellStyle(style);
            }

            int aux = 1;

            for (Transactions transaction : transactions){

                row = hoja.createRow(aux);
                // Create a cell and put a value in it.
                row.createCell(0).setCellValue(transaction.getUser().getDwEmployee().getEmployee().getFullName());
                row.createCell(1).setCellValue(transaction.getAmount().toString());
                row.createCell(2).setCellValue(transaction.getCreationDateFormats().getDateNumber());

                aux ++;
            }

            //Autoajustar al contenido
            hoja.autoSizeColumn(0);
            hoja.autoSizeColumn(1);
            hoja.autoSizeColumn(2);

            //Mover filas hacia abajo para colocar la imagen corporativa: Altura 4 filas, Anchura 4 columnas
            hoja.shiftRows(0, hoja.getLastRowNum(), 4);

            //Definicion del estilo del titulo
            Font font2 = wb.createFont();
            font2.setBold(true);
            font2.setFontHeightInPoints((short) 14);
            CellStyle style2 = wb.createCellStyle();
            style2.setFont(font);
            style2.setAlignment(CellStyle.ALIGN_CENTER);

            //Titulo del reporte
            CellUtil.createCell(hoja.createRow(1), 5, "TRANSACCIONES", style2);

            wb.write(stream);
        }

    @Override
    public void transacctionsByDayReportAndExit(LocalDateTime ofDate, LocalDateTime untilDate, OutputStream stream) throws IOException {

        List<Transactions> transactions = transactionsDao.findTransactionByDateAndExit(ofDate,untilDate);

        Workbook wb = new HSSFWorkbook();
        //Definicion del estilo de la cabecera
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = wb.createCellStyle();
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

        Sheet hoja = wb.createSheet();
        //Se crea la fila que contiene la cabecera
        Row row = hoja.createRow(0);

        row.createCell(0).setCellValue("CONCEPTO");
        row.createCell(1).setCellValue("MONTO");
        row.createCell(2).setCellValue("EMPRESA");
        row.createCell(3).setCellValue("REGION");
        row.createCell(4).setCellValue("SUCURSAL");
        row.createCell(5).setCellValue("PROVEEDOR");
        row.createCell(6).setCellValue("FECHA DE RECEPCIÓN");
        row.createCell(7).setCellValue("FECHA DE APLICACIÓN");

        //Implementacion del estilo
        for (Cell celda : row) {
            celda.setCellStyle(style);
        }

        int aux = 1;

        for (Transactions transaction : transactions){
            AccountsPayable accountsPayable = transaction.getAccountsPayable();
            Requests requests = requestsDao.findByFolio(accountsPayable.getFolio());
            RequestTypesProduct requestTypesProduct = requests.getRequestTypeProduct();
            CProductTypes productType = requestTypesProduct.getProductType();
            Providers provider = productType.getProvider();
            BudgetMonthBranch budgetMonthBranch = requests.getBudgetMonthBranch();
            DwEnterprises dwEnterprise = budgetMonthBranch.getDwEnterprise();
            CDistributors cDistributors = dwEnterprise.getDistributor();
            CRegions cRegions = dwEnterprise.getRegion();
            CBranchs cBranchs = dwEnterprise.getBranch();

            row = hoja.createRow(aux);
            // Create a cell and put a value in it.
            row.createCell(0).setCellValue(productType.getProductType());
            row.createCell(1).setCellValue(accountsPayable.getAmount().toString());
            row.createCell(2).setCellValue(cDistributors.getAcronyms());
            row.createCell(3).setCellValue(cRegions.getRegionName());
            row.createCell(4).setCellValue(cBranchs.getBranchShort());
            row.createCell(5).setCellValue(provider.getProviderName().replace(':',' '));
            row.createCell(6).setCellValue(accountsPayable.getCreationDateFormats().getDateNumber());
            row.createCell(7).setCellValue(transaction.getCreationDateFormats().getDateNumber());


            aux ++;
        }

        //Autoajustar al contenido
        hoja.autoSizeColumn(0);
        hoja.autoSizeColumn(1);
        hoja.autoSizeColumn(2);
        hoja.autoSizeColumn(3);
        hoja.autoSizeColumn(4);
        hoja.autoSizeColumn(5);
        hoja.autoSizeColumn(6);
        hoja.autoSizeColumn(7);

        //Mover filas hacia abajo para colocar la imagen corporativa: Altura 4 filas, Anchura 4 columnas
        hoja.shiftRows(0, hoja.getLastRowNum(), 4);

        //Definicion del estilo del titulo
        Font font2 = wb.createFont();
        font2.setBold(true);
        font2.setFontHeightInPoints((short) 14);
        CellStyle style2 = wb.createCellStyle();
        style2.setFont(font);
        style2.setAlignment(CellStyle.ALIGN_CENTER);

        //Titulo del reporte
        CellUtil.createCell(hoja.createRow(1), 5, "CUENTAS PAGADAS", style2);

        wb.write(stream);
    }

}
