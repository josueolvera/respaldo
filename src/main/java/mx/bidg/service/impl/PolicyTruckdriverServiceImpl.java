package mx.bidg.service.impl;

import mx.bidg.dao.CAmountsSecureDao;
import mx.bidg.dao.CTypeSecureDao;
import mx.bidg.dao.InsurancePremiumDao;
import mx.bidg.dao.PolicyTruckdriverDao;
import mx.bidg.model.CAmountsSecure;
import mx.bidg.model.CTypeSecure;
import mx.bidg.model.InsurancePremium;
import mx.bidg.model.PolicyTruckdriver;
import mx.bidg.service.PolicyTruckdriverService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.exolab.castor.types.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */

@Service
@Transactional
public class PolicyTruckdriverServiceImpl implements PolicyTruckdriverService {

    @Autowired
    private PolicyTruckdriverDao policyTruckdriverDao;

    @Autowired
    private Environment env;

    @Autowired
    private CTypeSecureDao cTypeSecureDao;

    @Autowired
    private CAmountsSecureDao cAmountsSecureDao;

    @Autowired
    private InsurancePremiumDao insurancePremiumDao;

    @Override
    public PolicyTruckdriver save(PolicyTruckdriver policyTruckdriver) {
        return policyTruckdriverDao.save(policyTruckdriver);
    }

    @Override
    public PolicyTruckdriver update(PolicyTruckdriver policyTruckdriver) {
        return policyTruckdriverDao.update(policyTruckdriver);
    }

    @Override
    public PolicyTruckdriver findByid(Integer idPTD) {
        return policyTruckdriverDao.findById(idPTD);
    }

    @Override
    public List<PolicyTruckdriver> findByDStartValidity(LocalDate startdate) {
        return policyTruckdriverDao.findDStartValidity(startdate);
    }

    @Override
    public List<PolicyTruckdriver> findAll() {
        return policyTruckdriverDao.findAll();
    }

    @Override
    public boolean delete(PolicyTruckdriver policyTruckdriver) {
        policyTruckdriverDao.delete(policyTruckdriver);
        return true;
    }

    @Override
    public void readCsvPolicya(String fileName) {

        String [] FILE_HEADER_MAPPING = {"Placas","Folio","Hora","Inicio","Fin","Suma Asegurada","Usuario","Edad","Email","Beneficiario","Edad Beneficiario","Dias","Pago Id","Gateway Pago"};

        String READ_PATH = env.getRequiredProperty("policy_truckDriver.documents_dir");

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
                    PolicyTruckdriver policyTruckdriver = new PolicyTruckdriver();
                    policyTruckdriver.setNumLicensePlate(record.get("Placas"));
                    policyTruckdriver.setNumFolio(record.get("Folio"));
                    policyTruckdriver.sethContracting(LocalTime.parse(record.get("Hora")));
                    policyTruckdriver.setdStartValidity(LocalDate.parse(record.get("Inicio"),formatter));
                    policyTruckdriver.setdEndValidity(LocalDate.parse(record.get("Fin"),formatter));
                    policyTruckdriver.setInsuranceAmount(BigDecimal.valueOf(Double.parseDouble(String.valueOf(record.get("Suma Asegurada")))));
                    CAmountsSecure cAmountsSecure = cAmountsSecureDao.findByRode(BigDecimal.valueOf(Double.parseDouble(String.valueOf(record.get("Suma Asegurada")))));
                    policyTruckdriver.setNameCconductor(record.get("Usuario"));
                    if (!record.get("Edad").isEmpty()) {
                        policyTruckdriver.setDriverAge(Integer.parseInt(record.get("Edad")));
                    }
                    policyTruckdriver.setEmail(record.get("Email"));
                    policyTruckdriver.setNameBeneficiary(record.get("Beneficiario"));
                    if (!record.get("Edad Beneficiario").isEmpty()) {
                        policyTruckdriver.setBeneficiaryAge(Integer.parseInt(record.get("Edad Beneficiario")));
                    }
                    policyTruckdriver.setDays(record.get("Dias"));
                    CTypeSecure cTypeSecure = cTypeSecureDao.findByName(record.get("Dias"));
                    if (cTypeSecure != null && cAmountsSecure != null){
                        InsurancePremium insurancePremium = insurancePremiumDao.findByTypeSecureAndAmountSecure(cTypeSecure.getIdTypeSecure(), cAmountsSecure.getIdAmountsSecure());
                        if (insurancePremium != null){
                            policyTruckdriver.setInsurancePremium(insurancePremium);
                        }
                    }
                    policyTruckdriver.setPagoId(record.get("Pago Id"));
                    policyTruckdriver.setAuthorizationNumber(record.get("Gateway Pago"));
                    policyTruckdriver.setCreationDate(LocalDateTime.now());
                    policyTruckdriverDao.save(policyTruckdriver);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
                csvParser.close();
            } catch (IOException e) {
                System.out.println("Error while closing fileReader/csvFileParser !!!");
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<PolicyTruckdriver> findDStartValidityBetween(LocalDate starDate, LocalDate finalDate) {
        return policyTruckdriverDao.findDStartValidityBetween(starDate, finalDate);
    }

    @Override
    public List<String> findNoAutorizationByDStartValidityBetween(LocalDate starDate, LocalDate finalDate) {
        return policyTruckdriverDao.findNoAutizationByDStartValidity(starDate, finalDate);
    }

    @Override
    public PolicyTruckdriver findByFolio(String folio) {
        return policyTruckdriverDao.findByFolio(folio);
    }

    @Override
    public List findFoliosCommissionIvaByDStartValidity(LocalDate startDate, LocalDate endDate) {
        return policyTruckdriverDao.findFoliosCommissionIvaByDStartValidity(startDate, endDate);
    }

    @Override
    public List<String> getNoAutorizationByDStartValidityBetween(LocalDate starDate, LocalDate finalDate) {
        return policyTruckdriverDao.getNoAutizationByDStartValidity(starDate, finalDate);
    }


}
