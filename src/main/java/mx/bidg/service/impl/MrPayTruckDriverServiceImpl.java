package mx.bidg.service.impl;

import mx.bidg.dao.MrPayTruckDriverDao;
import mx.bidg.model.MrPayTruckDriver;
import mx.bidg.pojos.PdfAternaPojo;
import mx.bidg.service.MrPayTruckDriverService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Salvador on 19/01/2017.
 */
@Transactional
@Service
public class MrPayTruckDriverServiceImpl implements MrPayTruckDriverService{

    @Autowired
    private MrPayTruckDriverDao mrPayTruckDriverDao;

    @Autowired
    private Environment env;

    @Override
    public MrPayTruckDriver findById(Integer id) {
        return mrPayTruckDriverDao.findById(id);
    }

    @Override
    public List<MrPayTruckDriver> findAll() {
        return mrPayTruckDriverDao.findAll();
    }

    @Override
    public MrPayTruckDriver save(MrPayTruckDriver mrPayTruckDriver) {
        return mrPayTruckDriverDao.save(mrPayTruckDriver);
    }

    @Override
    public MrPayTruckDriver update(MrPayTruckDriver mrPayTruckDriver) {
        return mrPayTruckDriverDao.update(mrPayTruckDriver);
    }

    @Override
    public boolean delete(MrPayTruckDriver mrPayTruckDriver) {
        return mrPayTruckDriverDao.delete(mrPayTruckDriver);
    }

    @Override
    public MrPayTruckDriver findAuthorizationNumber(String authorizationNumber) {
        return mrPayTruckDriverDao.findAuthorizationNumber(authorizationNumber);
    }

    @Override
    public void readCsv(String fileName) {
        String [] FILE_HEADER_MAPPING = {"No Autorizacion","Tipo_Operacion","Fecha_Registro","Hora_Registro","Referencia","Detalles","Importe_Cobrado","Comisiones","IVA"};

        String READ_PATH = env.getRequiredProperty("mr_pay_truckDriver.documents_dir");

        FileReader fileReader = null;

        CSVParser csvParser = null;

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

        try {
            fileReader = new FileReader(READ_PATH+fileName);
            csvParser = new CSVParser(fileReader, csvFileFormat);
            List csvRecords = csvParser.getRecords();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

            for (int i = 1; i < csvRecords.size(); i++){
                CSVRecord record = (CSVRecord) csvRecords.get(i);
                if (!record.get("No Autorizacion").isEmpty()){
                    MrPayTruckDriver mrPayTruckDriver = new MrPayTruckDriver();
                    mrPayTruckDriver.setAuthorizationNumber(record.get("No Autorizacion"));
                    mrPayTruckDriver.setPaymentType(record.get("Tipo_Operacion"));
                    mrPayTruckDriver.setPaymentDate(LocalDate.parse(record.get("Fecha_Registro"),formatter));
                    mrPayTruckDriver.setPaymentHour(LocalTime.parse(record.get("Hora_Registro")));
                    mrPayTruckDriver.setRefrence(record.get("Referencia"));
                    mrPayTruckDriver.setDetails(record.get("Detalles"));
                    mrPayTruckDriver.setAmountCost(new BigDecimal(record.get("Importe_Cobrado")));
                    mrPayTruckDriver.setCommissionTransaction(new BigDecimal(record.get("Comisiones")));
                    mrPayTruckDriver.setIva(new BigDecimal(record.get("IVA")));
                    mrPayTruckDriver.setCreationDate(LocalDateTime.now());
                    mrPayTruckDriverDao.save(mrPayTruckDriver);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
                csvParser.close();
            } catch (IOException e) {
                System.out.println("Error !!!");
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<MrPayTruckDriver> findByPaymentDate(LocalDate paymentDate) {
        return mrPayTruckDriverDao.findByPaymentDate(paymentDate);
    }

    @Override
    public List<MrPayTruckDriver> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate) {
        return mrPayTruckDriverDao.findByPaymentDateBetween(startDate, endDate);
    }

    @Override
    public List<String> findNoAutorizationByDStartValidityBetween(LocalDate starDate, LocalDate finalDate) {
        return mrPayTruckDriverDao.findNoAutizationByDStartValidity(starDate, finalDate);
    }

    @Override
    public PdfAternaPojo conciliationByAutorizationNumber(List<String> noAutorizationMrPay, List<String> noAutorizationApp, LocalDate starDate, LocalDate finalDate) {
        List<String> foliosExistentes = new ArrayList<>();
        List<String> foliosNoEncontadosD = new ArrayList<>();
        List<String> foliosNoEncontadosM = new ArrayList<>();


        for (String folioM : noAutorizationMrPay){
            if (noAutorizationApp.contains(folioM)){
                foliosExistentes.add(folioM);
            }else {
                foliosNoEncontadosM.add(folioM);
            }
        }

        for (String folioD : noAutorizationApp){
            if (!noAutorizationMrPay.contains(folioD)){
                foliosNoEncontadosD.add(folioD);
            }
        }

        PdfAternaPojo aternaPojo = new PdfAternaPojo();

        if ((foliosExistentes.size() == noAutorizationMrPay.size()) && (foliosExistentes.size() == noAutorizationApp.size())){
            List<MrPayTruckDriver> mrPayTruckDrivers = mrPayTruckDriverDao.findByPaymentDateBetween(starDate, finalDate);

            BigDecimal sumMSubtotal = new BigDecimal(0.00);
            BigDecimal sumMIva = new BigDecimal(0.00);
            BigDecimal sumMTotal = new BigDecimal(0.00);

            for (MrPayTruckDriver payTruckDriver : mrPayTruckDrivers){
                if (payTruckDriver != null){
                    sumMSubtotal = sumMSubtotal.add(payTruckDriver.getCommissionTransaction());
                    sumMIva = sumMIva.add(payTruckDriver.getIva());
                    sumMTotal = sumMTotal.add(payTruckDriver.getCommissionTransaction().add(payTruckDriver.getIva()));
                }
            }

            aternaPojo.setNumSecures(noAutorizationMrPay.size());
            aternaPojo.setConciliation(true);
            aternaPojo.setFoliosEncontrados(foliosExistentes);
            aternaPojo.setFoliosNoEncontradoD(foliosNoEncontadosD);
            aternaPojo.setFoliosNoEncontradoM(foliosNoEncontadosM);
            aternaPojo.setSubtotalD(new BigDecimal(0.00));
            aternaPojo.setIvaD(new BigDecimal(0.00));
            aternaPojo.setTotalD(new BigDecimal(0.00));
            aternaPojo.setSubtotalM(sumMSubtotal);
            aternaPojo.setIvaM(sumMIva);
            aternaPojo.setTotalM(sumMTotal);
        }else {
            aternaPojo.setConciliation(false);
            aternaPojo.setFoliosNoEncontradoD(foliosNoEncontadosD);
            aternaPojo.setFoliosNoEncontradoM(foliosNoEncontadosM);
            aternaPojo.setFoliosEncontrados(foliosExistentes);
            aternaPojo.setSubtotalD(new BigDecimal(0.00));
            aternaPojo.setIvaD(new BigDecimal(0.00));
            aternaPojo.setTotalD(new BigDecimal(0.00));
            aternaPojo.setSubtotalM(new BigDecimal(0.00));
            aternaPojo.setIvaM(new BigDecimal(0.00));
            aternaPojo.setTotalM(new BigDecimal(0.00));
            aternaPojo.setNumSecures(0);
        }


        return aternaPojo;
    }
}
