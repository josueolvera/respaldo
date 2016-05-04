package mx.bidg.service.impl;


import mx.bidg.dao.SapSaleDao;
import mx.bidg.model.SapSale;
import mx.bidg.service.SapSaleService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gerardo8 on 29/04/16.
 */
@Transactional
@Service
public class SapSaleServiceImpl implements SapSaleService {

    @Autowired
    private SapSaleDao sapSaleDao;

    @Override
    public List<SapSale> findAll() {
        return sapSaleDao.findAll();
    }

    @Override
    public SapSale findById(Integer id) {
        return sapSaleDao.findById(id);
    }

    @Override
    public List<SapSale> saveFromExcel(MultipartFile file) throws IOException, InvalidFormatException,NullPointerException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        List<SapSale> sapSales = sapSaleDao.findAll();
        SapSale sapSale = new SapSale();
        SapSale sapSaleFromDB;

        for (int i=1;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell idSale = currentRow.getCell(0);
            Cell creationDate = currentRow.getCell(1);
            Cell interlocCom = currentRow.getCell(2);
            Cell clientParentLast = currentRow.getCell(3);
            Cell clientMotherLast = currentRow.getCell(4);
            Cell clientName = currentRow.getCell(5);
            Cell clientSecName = currentRow.getCell(6);
            Cell clientSingleLast = currentRow.getCell(7);
            Cell clientId = currentRow.getCell(8);
            Cell imssNum = currentRow.getCell(9);
            Cell agreementName = currentRow.getCell(10);
            Cell product = currentRow.getCell(11);
            Cell dependency = currentRow.getCell(12);
            Cell statusSale = currentRow.getCell(13);
            Cell lastUpdate = currentRow.getCell(14);
            Cell approvalDate = currentRow.getCell(15);
            Cell requestedAmount = currentRow.getCell(16);
            Cell payments = currentRow.getCell(17);
            Cell depositAmount = currentRow.getCell(18);
            Cell comissionableAmount = currentRow.getCell(19);
            Cell purchaseDate = currentRow.getCell(20);
            Cell companyName = currentRow.getCell(21);
            Cell distributorName = currentRow.getCell(22);
            Cell claveSap = currentRow.getCell(23);
            Cell branchName = currentRow.getCell(24);
            Cell region = currentRow.getCell(25);
            Cell bonification = currentRow.getCell(26);
            Cell liquidation = currentRow.getCell(27);

//            imssNum.setCellType(1);
//            interlocCom.setCellType(1);
//            idSale.setCellType(1);


            if (agreementName != null) {
                sapSale.setAgreementName(agreementName.getStringCellValue());
            }
            if (approvalDate != null) {
                sapSale.setApprovalDate(approvalDate.getDateCellValue());
            }
            if (bonification != null) {
                BigDecimal bdBonification = new BigDecimal(bonification.getNumericCellValue());
                sapSale.setBonification(bdBonification);
            }
            if (branchName != null) {
                sapSale.setBranchName(branchName.getStringCellValue());
            }
            if (claveSap != null) {
                sapSale.setClaveSap(claveSap.getStringCellValue());
            }
            if (clientId != null) {
                sapSale.setClientId(clientId.getStringCellValue());
            }
            if (clientMotherLast != null) {
                sapSale.setClientMotherLast(clientMotherLast.getStringCellValue());
            }
            if (clientName != null) {
                sapSale.setClientName(clientName.getStringCellValue());
            }
            if (clientSecName != null) {
                sapSale.setClientSecName(clientSecName.getStringCellValue());
            }
            if (clientSingleLast != null) {
                sapSale.setClientSingleLast(clientSingleLast.getStringCellValue());
            }
            if (clientParentLast != null) {
                sapSale.setClientParentLast(clientParentLast.getStringCellValue());
            }
            if (comissionableAmount != null) {
                BigDecimal bdComissionableAmount = new BigDecimal(comissionableAmount.getNumericCellValue());
                sapSale.setComissionableAmount(bdComissionableAmount);
            }
            if (companyName != null) {
                sapSale.setCompanyName(companyName.getStringCellValue());
            }
            if (creationDate != null) {
                sapSale.setCreationDate(creationDate.getDateCellValue());
            }
            if (dependency != null) {
                sapSale.setDependency(dependency.getStringCellValue());
            }
            if (depositAmount != null) {
                BigDecimal bdDepositAmount = new BigDecimal(depositAmount.getNumericCellValue());
                sapSale.setDepositAmount(bdDepositAmount);
            }
            if (distributorName != null) {
                sapSale.setDistributorName(distributorName.getStringCellValue());
            }
            if (idSale != null) {
                sapSale.setIdSale(idSale.getStringCellValue());
            }
            if (imssNum != null) {
                sapSale.setImssNum(imssNum.getStringCellValue());
            }
            if (interlocCom != null) {
                sapSale.setInterlocCom(interlocCom.getStringCellValue());
            }
            if (liquidation != null) {
                BigDecimal bdLiquidation = new BigDecimal(liquidation.getNumericCellValue());
                sapSale.setLiquidation(bdLiquidation);
            }
            if (payments != null) {
                sapSale.setPayments(payments.getStringCellValue());
            }
            if (product != null) {
                sapSale.setProduct(product.getStringCellValue());
            }
            if (region != null) {
                sapSale.setRegion(region.getStringCellValue());
            }
            if (requestedAmount != null) {
                BigDecimal bdRequestedAmount = new BigDecimal(requestedAmount.getNumericCellValue());
                sapSale.setRequestedAmount(bdRequestedAmount);
            }
            if (statusSale != null) {
                sapSale.setStatusSale(statusSale.getStringCellValue());
            }
            if (purchaseDate != null) {
                sapSale.setPurchaseDate(purchaseDate.getDateCellValue());
            }
            if (lastUpdate != null) {
                sapSale.setLastUpdate(lastUpdate.getDateCellValue());
            }

            if (sapSales.isEmpty()) {
                sapSaleDao.save(sapSale);
            } else {
                sapSaleFromDB = sapSaleDao.findByIdSale(sapSale.getIdSale());

                if (sapSaleFromDB == null) {
                    sapSaleDao.save(sapSale);
//                    gjhghjgj
                } else {

                    sapSaleFromDB.setClaveSap(sapSale.getClaveSap());
                    sapSaleFromDB.setClientSingleLast(sapSale.getClientSingleLast());
                    sapSaleFromDB.setApprovalDate(sapSale.getApprovalDate());
                    sapSaleFromDB.setAgreementName(sapSale.getAgreementName());
                    sapSaleFromDB.setClientSecName(sapSale.getClientSecName());
                    sapSaleFromDB.setClientName(sapSale.getClientName());
                    sapSaleFromDB.setClientId(sapSale.getClientId());
                    sapSaleFromDB.setBonification(sapSale.getBonification());
                    sapSaleFromDB.setBranchName(sapSale.getBranchName());
                    sapSaleFromDB.setClientMotherLast(sapSale.getClientMotherLast());
                    sapSaleFromDB.setComissionableAmount(sapSale.getComissionableAmount());
                    sapSaleFromDB.setCompanyName(sapSale.getCompanyName());
                    sapSaleFromDB.setClientParentLast(sapSale.getClientParentLast());
                    sapSaleFromDB.setDependency(sapSale.getDependency());
                    sapSaleFromDB.setDepositAmount(sapSale.getDepositAmount());
                    sapSaleFromDB.setDistributorName(sapSale.getDistributorName());
                    sapSaleFromDB.setIdSale(sapSale.getIdSale());
                    sapSaleFromDB.setImssNum(sapSale.getImssNum());
                    sapSaleFromDB.setInterlocCom(sapSale.getInterlocCom());
                    sapSaleFromDB.setLiquidation(sapSale.getLiquidation());
                    sapSaleFromDB.setPayments(sapSale.getPayments());
                    sapSaleFromDB.setLastUpdate(sapSale.getLastUpdate());
                    sapSaleFromDB.setProduct(sapSale.getProduct());
                    sapSaleFromDB.setCreationDate(sapSale.getCreationDate());
                    sapSaleFromDB.setPurchaseDate(sapSale.getPurchaseDate());
                    sapSaleFromDB.setRegion(sapSale.getRegion());
                    sapSaleFromDB.setRequestedAmount(sapSale.getRequestedAmount());
                    sapSaleFromDB.setStatusSale(sapSale.getStatusSale());

                    sapSaleDao.update(sapSaleFromDB);
                }

//                    if (sale.getIdSale().equals(sapSale.getIdSale())) {
//
//
//                    } else {
//                        sapSaleDao.save(sapSale);
//                    }
            }
        }
        return sapSaleDao.findAll();
    }

    @Override
    public boolean existsSales(MultipartFile file) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        List<SapSale> sapSales = sapSaleDao.findAll();
        SapSale sapSale = new SapSale();
        SapSale sapSaleFromDB;
        boolean existsSale = false;

        for (int i=1;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell idSale = currentRow.getCell(0);


            if (idSale != null) {
                sapSale.setIdSale(idSale.getStringCellValue());
            }

                sapSaleFromDB = sapSaleDao.findByIdSale(sapSale.getIdSale());

                if (sapSaleFromDB != null) {
                    existsSale = true;
//                    gjhghjgj
                }
        }
//        return sapSaleDao.findAll();
        return existsSale;
    }
}
