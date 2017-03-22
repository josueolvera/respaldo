package mx.bidg.service.impl;

import mx.bidg.dao.SinisterTruckdriverDao;
import mx.bidg.model.SinisterTruckdriver;
import mx.bidg.pojos.FolioAmountTruckdriverPojo;
import mx.bidg.pojos.PdfAternaPojo;
import mx.bidg.service.SinisterTruckdriverService;
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
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */
@Service
@Transactional
public class SinisterTruckdriverServiceImpl implements SinisterTruckdriverService {

    @Autowired
    SinisterTruckdriverDao sinisterTruckdriverDao;

    @Autowired
    private Environment env;

    @Override
    public SinisterTruckdriver save(SinisterTruckdriver sinisterTruckdriver) {
        return sinisterTruckdriverDao.save(sinisterTruckdriver);
    }

    @Override
    public SinisterTruckdriver update(SinisterTruckdriver sinisterTruckdriver) {
        return sinisterTruckdriverDao.update(sinisterTruckdriver);
    }
    @Override
    public SinisterTruckdriver findByid(Integer idST) {
        return sinisterTruckdriverDao.findById(idST);
    }

    @Override
    public List<SinisterTruckdriver> findAll() {
        return sinisterTruckdriverDao.findAll();
    }
    @Override
    public boolean delete(SinisterTruckdriver sinisterTruckdriver) {
        sinisterTruckdriverDao.delete(sinisterTruckdriver);
        return true;
    }

    @Override
    public List<SinisterTruckdriver> findByCreationDate(LocalDateTime creationDate) {
        return sinisterTruckdriverDao.findByCreationDate(creationDate);
    }

    @Override
    public void readCsv(String fileName) {
        String [] FILE_HEADER_MAPPING = {"Placas","Folio","Inicio","Fin","Importe Cobertura","No Autorizacion"};

        String READ_PATH = env.getRequiredProperty("sinister_truckDriver.documents_dir");

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
                if (!record.get("Folio").isEmpty()){
                    SinisterTruckdriver sinisterTruckdriver = new SinisterTruckdriver();
                    sinisterTruckdriver.setNumLicensePlate(record.get("Placas"));
                    sinisterTruckdriver.setNumFolio(record.get("Folio"));
                    sinisterTruckdriver.setdStartValidity(LocalDate.parse(record.get("Inicio"),formatter));
                    sinisterTruckdriver.setdEndValidity(LocalDate.parse(record.get("Fin"),formatter));
                    sinisterTruckdriver.setAmountOfCoverage(BigDecimal.valueOf(Double.parseDouble(String.valueOf(record.get("Importe Cobertura")))));
                    sinisterTruckdriver.setAuthorizationNumber(record.get("No Autorizacion"));
                    sinisterTruckdriver.setCreationDate(LocalDateTime.now());
                    sinisterTruckdriverDao.save(sinisterTruckdriver);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
                csvParser.close();
            } catch (IOException e) {
                System.out.println("Error csv!!!");
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> findNoAutizationByDStartValidity(LocalDate startDate, LocalDate endDate) {
        return sinisterTruckdriverDao.findNoAutizationByDStartValidity(startDate, endDate);
    }

    @Override
    public List<SinisterTruckdriver> findByDStartValidity(LocalDate startDate, LocalDate endDate) {
        return sinisterTruckdriverDao.findByDStartValidity(startDate, endDate);
    }

    @Override
    public PdfAternaPojo conciliationByFolio(List<String> foliosApp, List<String> foliosImpro, LocalDate startDate, LocalDate endDate) {

        List<String> foliosExistentes = new ArrayList<>();
        List<String> foliosNoEncontadosD = new ArrayList<>();
        List<String> foliosNoEncontadosM = new ArrayList<>();


        for (String folioM : foliosImpro){
            if (foliosApp.contains(folioM)){
                foliosExistentes.add(folioM);
            }else {
                foliosNoEncontadosM.add(folioM);
            }
        }

        for (String folioD : foliosApp){
            if (!foliosImpro.contains(folioD)){
                foliosNoEncontadosD.add(folioD);
            }
        }

//        for (String foliosD : foliosNoEncontadosD){
//            if (foliosNoEncontadosM.contains(foliosD)){
//                foliosNoEncontados.add(foliosD);
//            }else {
//                foliosNoEncontados.add(foliosD);
//            }
//        }


        PdfAternaPojo aternaPojo = new PdfAternaPojo();

        if ((foliosExistentes.size() == foliosImpro.size()) && (foliosExistentes.size() == foliosApp.size())){
            List<SinisterTruckdriver>  sinisterTruckdriverList = sinisterTruckdriverDao.findByDStartValidity(startDate, endDate);


            BigDecimal sumMSubtotal = new BigDecimal(0.00);
            BigDecimal sumMIva = new BigDecimal(0.00);
            BigDecimal sumMTotal = new BigDecimal(0.00);

            for (SinisterTruckdriver sinisterTruckdriver : sinisterTruckdriverList){
                if (sinisterTruckdriver != null){
                    if (sinisterTruckdriver.getInsurancePremium() != null){
                        sumMSubtotal = sumMSubtotal.add(sinisterTruckdriver.getInsurancePremium().getCommissionInterpro());
                        sumMIva = sumMIva.add(sinisterTruckdriver.getInsurancePremium().getIvaInterpro());
                        sumMTotal = sumMTotal.add(sinisterTruckdriver.getInsurancePremium().getCommissionInterpro().add(sinisterTruckdriver.getInsurancePremium().getIvaInterpro()));
                    }
                }
            }

            aternaPojo.setNumSecures(foliosImpro.size());
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
        }

        return aternaPojo;
    }

    @Override
    public List<FolioAmountTruckdriverPojo> getFoliosComisionIvaByDStartValidity(LocalDate startDate, LocalDate endDate) {
        List lista = sinisterTruckdriverDao.getFoliosComisionIvaByDStartValidity(startDate, endDate);
        List<FolioAmountTruckdriverPojo> folioAmountTruckdriverPojos = new ArrayList<>();
        for (Object data : lista){
            Object[] projection = (Object[]) data;
            FolioAmountTruckdriverPojo folioAmountTruckdriverPojo = new FolioAmountTruckdriverPojo();

            String folio = (String) projection[0];
            BigDecimal commission = (BigDecimal) projection[1];
            BigDecimal iva = (BigDecimal) projection[2];
            BigDecimal total = commission.add(iva, new MathContext(3));

            folioAmountTruckdriverPojo.setFolio(folio);
            folioAmountTruckdriverPojo.setCommission(commission);
            folioAmountTruckdriverPojo.setIva(iva);
            folioAmountTruckdriverPojo.setTotal(total);

            folioAmountTruckdriverPojos.add(folioAmountTruckdriverPojo);
        }
        return folioAmountTruckdriverPojos;
    }
}
