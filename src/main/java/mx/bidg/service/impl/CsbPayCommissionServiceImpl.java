package mx.bidg.service.impl;

import mx.bidg.dao.CsbPayCommissionDao;
import mx.bidg.dao.SapSaleDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.CsbPayCommission;
import mx.bidg.model.SapSale;
import mx.bidg.model.Users;
import mx.bidg.service.CsbPayCommissionService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

/**
 * Created by Serch on 29/06/2017.
 */
@Service
@Transactional
public class CsbPayCommissionServiceImpl implements CsbPayCommissionService {

    @Autowired
    private CsbPayCommissionDao csbPayCommissionDao;

    @Autowired
    private SapSaleDao sapSaleDao;

    @Override
    public CsbPayCommission save(CsbPayCommission csbPayCommission) {
        return csbPayCommissionDao.save(csbPayCommission);
    }

    @Override
    public CsbPayCommission update(CsbPayCommission csbPayCommission) {
        return csbPayCommissionDao.update(csbPayCommission);
    }

    @Override
    public CsbPayCommission findById(Integer idCsbPayCommission) {
        return csbPayCommissionDao.findById(idCsbPayCommission);
    }

    @Override
    public List<CsbPayCommission> findAll() {
        return csbPayCommissionDao.findAll();
    }

    @Override
    public boolean delete(CsbPayCommission csbPayCommission) {
        return csbPayCommissionDao.delete(csbPayCommission);
    }

    @Override
    public Boolean existsSales(MultipartFile file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Row headerRow = sheet.getRow(4);
        String[] headersToSkip = {
                "RFC Promotor", "Id Solicitud","Fecha de Pago",
                "Porcentaje Aplicado","Monto Comisión"
        };

        if (headerRow.getPhysicalNumberOfCells() != 5) {
            throw new ValidationException("Tipo de formato no compatible.",
                    "Los datos de este archivo no son los correctos o no cumplen con los datos de venta.");
        } else {
            for (int i = 0 ; i < 5 ;i++) {
                if (!headerRow.getCell(i).getStringCellValue().equals(headersToSkip[i])) {
                    throw new ValidationException("Tipo de formato no compatible.",
                            "Los datos de este archivo no son los correctos o no cumplen con los datos de venta.");
                }
            }
        }


        boolean existsSale = false;

        for (int i=5;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell idSale = currentRow.getCell(1);

            List<CsbPayCommission> csbPayCommissions = csbPayCommissionDao.findAllByIdSale(idSale.getStringCellValue());

            if (csbPayCommissions.size() > 0) {
                existsSale = true;
                break;
            }
        }
        return existsSale;
    }

    @Override
    public List<CsbPayCommission> saveFromExcel(MultipartFile file, Users user) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i=5;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell rfcPromotor = currentRow.getCell(0);
            Cell idSale = currentRow.getCell(1);
            Cell datePayment = currentRow.getCell(2);
            Cell percentajeSale = currentRow.getCell(3);
            Cell amountCommission = currentRow.getCell(4);

            CsbPayCommission csbPayCommission = new CsbPayCommission();

            if (rfcPromotor != null){
                csbPayCommission.setClaveSap(rfcPromotor.getStringCellValue());
            }

            if (idSale != null){
                csbPayCommission.setIdSale(idSale.getStringCellValue());

                SapSale sapSale = sapSaleDao.findByIdSale(idSale.getStringCellValue());

                if (sapSale != null){
                    csbPayCommission.setSapSale(sapSale);
                }
            }

            if (datePayment != null){
                Date date = datePayment.getDateCellValue();

                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() ;
                csbPayCommission.setPaymentDate(localDate);
            }

            if (percentajeSale != null){
                BigDecimal percentage = new BigDecimal(percentajeSale.getNumericCellValue());
                csbPayCommission.setPercentage(percentage);
            }

            if (amountCommission != null){
                BigDecimal amount = new BigDecimal(amountCommission.getNumericCellValue());
                csbPayCommission.setAmountCommission(amount);
            }

            csbPayCommission.setCreationDate(LocalDateTime.now());
            csbPayCommission.setUsername(user.getUsername());

            csbPayCommissionDao.save(csbPayCommission);
        }

        return csbPayCommissionDao.findAll();
    }

    @Override
    public List<CsbPayCommission> updateFromExcel(MultipartFile file, Users user) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i=5;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell rfcPromotor = currentRow.getCell(0);
            Cell idSale = currentRow.getCell(1);
            Cell datePayment = currentRow.getCell(2);
            Cell percentajeSale = currentRow.getCell(3);
            Cell amountCommission = currentRow.getCell(4);

            CsbPayCommission csbPayCommission;

            if (idSale != null){
                csbPayCommission = csbPayCommissionDao.findByIdSale(idSale.getStringCellValue());

                if (csbPayCommission != null){

                    SapSale sapSale = sapSaleDao.findByIdSale(idSale.getStringCellValue());

                    if (sapSale != null){
                        csbPayCommission.setSapSale(sapSale);
                    }

                    if (rfcPromotor != null){
                        csbPayCommission.setClaveSap(rfcPromotor.getStringCellValue());
                    }

                    if (datePayment != null){
                        Date date = datePayment.getDateCellValue();

                        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() ;
                        csbPayCommission.setPaymentDate(localDate);
                    }

                    if (percentajeSale != null){
                        BigDecimal percentage = new BigDecimal(percentajeSale.getNumericCellValue());
                        csbPayCommission.setPercentage(percentage);
                    }

                    if (amountCommission != null){
                        BigDecimal amount = new BigDecimal(amountCommission.getNumericCellValue());
                        csbPayCommission.setAmountCommission(amount);
                    }

                    csbPayCommission.setCreationDate(LocalDateTime.now());
                    csbPayCommission.setUsername(user.getUsername());

                    csbPayCommissionDao.update(csbPayCommission);

                }else {
                    CsbPayCommission newCsbPayCommission = new CsbPayCommission();

                    newCsbPayCommission.setIdSale(idSale.getStringCellValue());

                    SapSale sapSale = sapSaleDao.findByIdSale(idSale.getStringCellValue());

                    if (sapSale != null){
                        newCsbPayCommission.setSapSale(sapSale);
                    }

                    if (rfcPromotor != null){
                        newCsbPayCommission.setClaveSap(rfcPromotor.getStringCellValue());
                    }

                    if (datePayment != null){
                        Date date = datePayment.getDateCellValue();

                        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() ;
                        newCsbPayCommission.setPaymentDate(localDate);
                    }

                    if (percentajeSale != null){
                        BigDecimal percentage = new BigDecimal(percentajeSale.getNumericCellValue());
                        newCsbPayCommission.setPercentage(percentage);
                    }

                    if (amountCommission != null){
                        BigDecimal amount = new BigDecimal(amountCommission.getNumericCellValue());
                        newCsbPayCommission.setAmountCommission(amount);
                    }

                    newCsbPayCommission.setCreationDate(LocalDateTime.now());
                    newCsbPayCommission.setUsername(user.getUsername());

                    csbPayCommissionDao.save(newCsbPayCommission);
                }
            }

        }


        return csbPayCommissionDao.findAll();
    }
}
