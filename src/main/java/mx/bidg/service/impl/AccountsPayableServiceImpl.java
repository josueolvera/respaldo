/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import mx.bidg.dao.*;
import mx.bidg.model.*;
import mx.bidg.service.AccountsPayableService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountsPayableServiceImpl implements AccountsPayableService {
    
    @Autowired
    private AccountsPayableDao accountsPayableDao;
    
    @Autowired
    private RequestsDao requestsDao;

    @Autowired
    private CPeriodsDao periodsDao;

    @Autowired
    private BudgetMonthBranchDao budgetMonthBranchDao;

    @Autowired
    private BalancesDao balancesDao;

    @Autowired
    private TransactionsDao transactionsDao;
    
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public List<AccountsPayable> findByFolio(String folio) {
        return accountsPayableDao.findByFolio(folio);
    }

    @Override
    public AccountsPayable findById(Integer idAccountPayable) {
        return accountsPayableDao.findById(idAccountPayable);
    }

    @Override
    public List<AccountsPayable> updatePeriodic(String folio, String data) throws IOException {
        List<AccountsPayable> accountsPayables = findByFolio(folio);
        for (AccountsPayable accountPayable : accountsPayables) {
            if (accountPayable.getAccountPayableStatus().equals(CAccountsPayableStatus.INACTIVA)) {
                accountsPayableDao.delete(accountPayable);
            }
        }

        accountsPayables = new ArrayList<>();
        JsonNode node = mapper.readTree(data);
        LocalDateTime initialDate = LocalDateTime.parse(node.get("initialDate").asText());
        CPeriods period = periodsDao.findById(node.get("idPeriod").asInt());
        BigDecimal amount = node.get("amount").decimalValue();
        CCurrencies currency = new CCurrencies(node.get("idCurrency").asInt());
        BigDecimal rate = node.get("rate").decimalValue();
        Integer totalPayments = node.get("totalPayments").asInt();
        Period datePeriod = Period.of(period.getYears(), period.getMonths(), period.getDays());

        for (Integer paymentNumber = 0; paymentNumber < totalPayments; paymentNumber++) {
            AccountsPayable accountPayable = new AccountsPayable();
            Period currentPeriod = datePeriod.multipliedBy(paymentNumber);
            accountPayable.setFolio(folio);
            accountPayable.setAmount(amount);
            accountPayable.setPaidAmount(BigDecimal.ZERO);
            accountPayable.setPayNum(paymentNumber + 1);
            accountPayable.setTotalPayments(totalPayments);
            accountPayable.setCreationDate(LocalDateTime.now());
            accountPayable.setDueDate(initialDate.plus(currentPeriod));
            accountPayable.setAccountPayableStatus(CAccountsPayableStatus.INACTIVA);
            accountPayable.setOperationType(COperationTypes.EGRESO);
            accountPayable.setCurrency(currency);
            accountPayable.setRate(rate);
            accountPayable.setIdAccessLevel(1);

            accountsPayableDao.save(accountPayable);
            accountsPayables.add(accountPayable);
        }

        return accountsPayables;
    }

    @Override
    public boolean delete(AccountsPayable accountPayable) {
        accountsPayableDao.delete(accountPayable);
        return true;
    }

    @Override
    public AccountsPayable save(AccountsPayable accountPayable) {
        accountsPayableDao.save(accountPayable);
        return accountPayable;
    }

    @Override
    public List<AccountsPayable> update(String folio, String data) throws Exception {
        List<AccountsPayable> accounts = findByFolio(folio);
        for (AccountsPayable payable : accounts) {
            if (payable.getAccountPayableStatus().equals(CAccountsPayableStatus.INACTIVA)) {
                accountsPayableDao.delete(payable);
            }
        }

        AccountsPayable accountsPayable;
        accounts = new ArrayList<>();
        JsonNode jsonList = mapper.readTree(data);
        for(JsonNode json : jsonList) {

            BigDecimal amount = json.get("amount").decimalValue();
            BigDecimal rate = json.get("rate").decimalValue();
            Integer payNum = json.get("payNum").asInt();
            Integer totalPayments = json.get("totalPayments").asInt();
            LocalDateTime dueDate = (json.get("dueDate") == null || json.findValue("dueDate").asText().equals("")) ? null :
                    LocalDateTime.parse(json.get("dueDate").asText(), DateTimeFormatter.ISO_DATE_TIME);
            CCurrencies currency = new CCurrencies(json.get("idCurrency").asInt());

            accountsPayable = new AccountsPayable();
            accountsPayable.setFolio(folio);
            accountsPayable.setAmount(amount);
            accountsPayable.setRate(rate);
            accountsPayable.setPaidAmount(new BigDecimal(BigInteger.ZERO));
            accountsPayable.setPayNum(payNum);
            accountsPayable.setTotalPayments(totalPayments);
            //Tipo de operacion Egreso = 1
            accountsPayable.setOperationType(new COperationTypes(1));
            accountsPayable.setCreationDate(LocalDateTime.now());
            accountsPayable.setDueDate(dueDate);
            //Estatus de AccountPayable Inactiva = 1
            accountsPayable.setAccountPayableStatus(CAccountsPayableStatus.INACTIVA);
            accountsPayable.setIdAccessLevel(1);
            accountsPayable.setCurrency(currency);
            accountsPayable = accountsPayableDao.save(accountsPayable);
            accounts.add(accountsPayable);

        }
        return accounts;
    }

    @Override
    public List<AccountsPayable> findAll() {
        return accountsPayableDao.findAll();
    }

    @Override
        public List<AccountsPayable> findAccountsNow() {
        return accountsPayableDao.findAccountsofDay();
    }

    @Override
    public List<AccountsPayable> findByReschedule() {
        return accountsPayableDao.findByReschedule();
    }

    @Override
    public Transactions payAccount(Integer idAccountPayable, String data, Users user) throws IOException {
        AccountsPayable accountsPayable = accountsPayableDao.findById(idAccountPayable);

        JsonNode node = mapper.readTree(data);

        Transactions transactions = new Transactions();
        transactions.setAccountsPayable(accountsPayable);
        transactions.setAmount(node.get("amount").decimalValue());
        Balances balances = balancesDao.findById(node.get("idBalance").asInt());
        transactions.setBalances(balances);
        transactions.setCurrencies(new CCurrencies(node.get("idCurrency").asInt()));
        transactions.setOperationTypes(COperationTypes.EGRESO);
        transactions.setTransactionsStatus(CTransactionsStatus.PAGADA);
        transactions.setTransactionNumber(node.get("transactionNumber").asText());
        transactions.setCreationDate(LocalDateTime.now());
        transactions.setUser(user);

        Requests requests = requestsDao.findByFolio(accountsPayable.getFolio());

        BudgetMonthBranch budgetMonthBranch = requests.getBudgetMonthBranch();
        BigDecimal addAmountTransaction = budgetMonthBranch.getExpendedAmount().add(transactions.getAmount());
        budgetMonthBranch.setExpendedAmount(addAmountTransaction);
        budgetMonthBranchDao.update(budgetMonthBranch);


        BigDecimal substractAmountTransaction = balances.getCurrentAmount().subtract(transactions.getAmount());
        balances.setCurrentAmount(substractAmountTransaction);
        balancesDao.update(balances);
        accountsPayable.setAccountPayableStatus(CAccountsPayableStatus.FINALIZADA);
        accountsPayableDao.update(accountsPayable);
        transactionsDao.save(transactions);
        return transactions;
    }

    @Override
    public void changeDate(Integer idAccountPayable, String data) throws IOException {
        JsonNode json = mapper.readTree(data);
        LocalDateTime dueDate = (json.get("dueDate") == null || json.findValue("dueDate").asText().equals("")) ? null :
                LocalDateTime.parse(json.get("dueDate").asText(), DateTimeFormatter.ISO_DATE_TIME);
        AccountsPayable accountsPayable = accountsPayableDao.findById(idAccountPayable);
        LocalDateTime currentDate = accountsPayable.getDueDate();

        if (dueDate.toLocalDate().isAfter(currentDate.toLocalDate())){
            accountsPayable.setDueDate(dueDate);
            accountsPayable.setAccountPayableStatus(CAccountsPayableStatus.REPROGRAMADA);
            accountsPayableDao.update(accountsPayable);
        }
    }

    @Override
    public void accountsPayableReport (LocalDateTime ofDate, LocalDateTime untilDate , OutputStream stream) throws IOException {

        List<AccountsPayable> accountsPayables = accountsPayableDao.findByDueDate(ofDate,untilDate);

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

        if(accountsPayables.size()>0) {
            //Se crea la fila que contiene la cabecera
            Row row = hoja.createRow(0);

            row.createCell(0).setCellValue("CONCEPTO");
            row.createCell(1).setCellValue("MONTO");
            row.createCell(2).setCellValue("EMPRESA");
            row.createCell(3).setCellValue("REGION");
            row.createCell(4).setCellValue("SUCURSAL");
            row.createCell(5).setCellValue("PROVEEDOR");
            row.createCell(6).setCellValue("FECHA DE PAGO");

            //Implementacion del estilo
            for (Cell celda : row) {
                celda.setCellStyle(style);
            }

            int aux = 1;

            for (AccountsPayable accountsPayable : accountsPayables) {
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
                row.createCell(5).setCellValue(provider.getProviderName().replace(':', ' '));
                row.createCell(6).setCellValue(accountsPayable.getDueDateFormats().getDateNumber());


                aux++;
            }

            //Autoajustar al contenido
            hoja.autoSizeColumn(0);
            hoja.autoSizeColumn(1);
            hoja.autoSizeColumn(2);
            hoja.autoSizeColumn(3);
            hoja.autoSizeColumn(4);
            hoja.autoSizeColumn(5);
            hoja.autoSizeColumn(6);

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
            CellUtil.createCell(hoja.createRow(1), 5, "CUENTAS POR PAGAR", style2);

            wb.write(stream);
        }else {
            Row row = hoja.createRow(0);

            row.createCell(0).setCellValue("NO HAY REGISTROS PARA ESTAS FECHAS");

            //Implementacion del estilo
            for (Cell celda : row) {
                celda.setCellStyle(style);
            }
            wb.write(stream);
        }
    }


}
