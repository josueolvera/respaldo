package mx.bidg.service.impl;

import mx.bidg.dao.CAmountsSecureDao;
import mx.bidg.dao.CTypeSecureDao;
import mx.bidg.dao.InsurancePremiumDao;
import mx.bidg.dao.PolicyTruckdriverMonthlyDao;
import mx.bidg.model.*;
import mx.bidg.pojos.FolioAmountTruckdriverPojo;
import mx.bidg.pojos.PdfAternaPojo;
import mx.bidg.service.PolicyTruckdriverMonthlyService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desarrollador on 18/01/2017.
 */
@Service
@Transactional
public class PolicyTruckdriverMonthlyServiceImpl implements PolicyTruckdriverMonthlyService {

    @Autowired
    PolicyTruckdriverMonthlyDao policyTruckdriverMonthlyDao;

    @Autowired
    private Environment env;

    @Autowired
    CTypeSecureDao cTypeSecureDao;

    @Autowired
    CAmountsSecureDao cAmountsSecureDao;

    @Autowired
    InsurancePremiumDao insurancePremiumDao;

    @Override
    public PolicyTruckdriverMonthly save(PolicyTruckdriverMonthly policyTruckdriverMonthly) {
        return policyTruckdriverMonthlyDao.save(policyTruckdriverMonthly);
    }

    @Override
    public PolicyTruckdriverMonthly update(PolicyTruckdriverMonthly policyTruckdriverMonthly) {
        return policyTruckdriverMonthlyDao.update(policyTruckdriverMonthly);
    }

    @Override
    public PolicyTruckdriverMonthly findById(Integer idPolicyTruckdriverMonthly) {
        return policyTruckdriverMonthlyDao.findById(idPolicyTruckdriverMonthly);
    }

    @Override
    public List<PolicyTruckdriverMonthly> findAll() {
        return policyTruckdriverMonthlyDao.findAll();
    }

    @Override
    public boolean delete(PolicyTruckdriverMonthly policyTruckdriverMonthly) {
        policyTruckdriverMonthlyDao.delete(policyTruckdriverMonthly);
        return true;
    }

    @Override
    public boolean existsPolicyTruckDriverRecord(MultipartFile file, Integer idUser) throws IOException {

        String [] FILE_HEADER_MAPPING = {"Placas","Folio","Hora","Fecha Inicio","Fecha Fin","Monto","Nombre conductor","Correo","Beneficiario","Tipo seguro"};

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

        CSVParser csvParser = new CSVParser(new InputStreamReader(file.getInputStream()), csvFileFormat);
        List csvRecords = csvParser.getRecords();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        boolean existsPolicyTruckDriver = false;

        for (int i = 1; i < csvRecords.size(); i++){
            CSVRecord record = (CSVRecord) csvRecords.get(i);
            PolicyTruckdriverMonthly policyTruckdriverMonthly;

            policyTruckdriverMonthly = policyTruckdriverMonthlyDao.findByLicensePlateUserAndDate(
                    record.get("Placas"),
                    idUser,
                    LocalDate.parse(record.get("Fecha Inicio"),formatter)
            );

            if (policyTruckdriverMonthly != null){
                existsPolicyTruckDriver = true;
            }
        }
        return existsPolicyTruckDriver;
    }

    @Override
    public List<PolicyTruckdriverMonthly> saveFromCsv(MultipartFile file, Integer idUser) throws IOException {

//        String [] FILE_HEADER_MAPPING = {"Placas","Folio","Hora","Fecha Inicio","Fecha Fin","Monto","Nombre conductor","Correo","Beneficiario","Tipo seguro"};
//
//        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
//
//        CSVParser csvParser = new CSVParser(new InputStreamReader(file.getInputStream()), csvFileFormat);
//        List csvRecords = csvParser.getRecords();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
//
//        for (int i = 1; i < csvRecords.size(); i++){
//            CSVRecord record = (CSVRecord) csvRecords.get(i);
//            PolicyTruckdriverMonthly policyTruckdriverMonthly;
//
//            policyTruckdriverMonthly = policyTruckdriverMonthlyDao.findByLicensePlateUserAndDate(
//                    record.get("Placas"),
//                    idUser,
//                    LocalDate.parse(record.get("Fecha Inicio"),formatter)
//            );
//            if (policyTruckdriverMonthly == null){
//                PolicyTruckdriverMonthly policyMonthly = new PolicyTruckdriverMonthly();
//                policyMonthly.setNumLicensePlate(record.get("Placas"));
//                policyMonthly.setNumFolio(record.get("Folio"));
//                policyMonthly.sethContracting(LocalTime.parse(record.get("Hora")));
//                policyMonthly.setdStartValidity(LocalDate.parse(record.get("Fecha Inicio"),formatter));
//                policyMonthly.setdEndValidity(LocalDate.parse(record.get("Fecha Fin"),formatter));
//                policyMonthly.setInsuranceAmount(BigDecimal.valueOf(Double.parseDouble(String.valueOf(record.get("Monto")))));
//                policyMonthly.setNameCconductor(record.get("Nombre conductor"));
//                policyMonthly.setEmail(record.get("Correo"));
//                policyMonthly.setNameBeneficiary(record.get("Beneficiario"));
//                CTypeSecure cTypeSecure = cTypeSecureDao.findByName(record.get("Tipo seguro"));
//                if (cTypeSecure != null){
//                    policyMonthly.setcTypeSecure(cTypeSecure);
//                }
//                policyMonthly.setCreationDate(LocalDateTime.now());
//                policyMonthly.setIdUser(idUser);
//                policyTruckdriverMonthlyDao.save(policyMonthly);
//            }else {
//                policyTruckdriverMonthly.setNumLicensePlate(record.get("Placas"));
//                policyTruckdriverMonthly.setNumFolio(record.get("Folio"));
//                policyTruckdriverMonthly.sethContracting(LocalTime.parse(record.get("Hora")));
//                policyTruckdriverMonthly.setdStartValidity(LocalDate.parse(record.get("Fecha Inicio"),formatter));
//                policyTruckdriverMonthly.setdEndValidity(LocalDate.parse(record.get("Fecha Fin"),formatter));
//                policyTruckdriverMonthly.setInsuranceAmount(BigDecimal.valueOf(Double.parseDouble(String.valueOf(record.get("Monto")))));
//                policyTruckdriverMonthly.setNameCconductor(record.get("Nombre conductor"));
//                policyTruckdriverMonthly.setEmail(record.get("Correo"));
//                policyTruckdriverMonthly.setNameBeneficiary(record.get("Beneficiario"));
//                CTypeSecure cTypeSecure = cTypeSecureDao.findByName(record.get("Tipo seguro"));
//                if (cTypeSecure != null){
//                    policyTruckdriverMonthly.setcTypeSecure(cTypeSecure);
//                }
//                policyTruckdriverMonthly.setCreationDate(LocalDateTime.now());
//                policyTruckdriverMonthly.setIdUser(idUser);
//                policyTruckdriverMonthlyDao.update(policyTruckdriverMonthly);
//            }
//        }

        return policyTruckdriverMonthlyDao.findAll();
    }

    @Override
    public void readCsvAlterna(String fileName) {
        String [] FILE_HEADER_MAPPING = {"Placas","Folio","Hora","Inicio","Fin","Suma Asegurada","Usuario","Edad","Email","Beneficiario","Edad Beneficiario","Dias","Pago Id","No. Autorizacion"};

        String READ_PATH = env.getRequiredProperty("aterna_truckDriver.documents_dir");

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
                    PolicyTruckdriverMonthly policyTruckdriver = new PolicyTruckdriverMonthly();
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
                    policyTruckdriver.setAuthorizationNumber(record.get("No. Autorizacion"));
                    policyTruckdriver.setCreationDate(LocalDateTime.now());
                    policyTruckdriverMonthlyDao.save(policyTruckdriver);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
                csvParser.close();
            } catch (IOException e) {
                System.out.println("Error csvFileParser !!!");
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<PolicyTruckdriverMonthly> findDStartValidityBetween(LocalDate starDate, LocalDate finalDate) {
        return policyTruckdriverMonthlyDao.findDStartValidityBetween(starDate, finalDate);
    }

    @Override
    public PdfAternaPojo validateAmounts(List listaApp, List listaAterna) {

        List<FolioAmountTruckdriverPojo> folioAmountAppList = new ArrayList<>();
        List<FolioAmountTruckdriverPojo> folioAmountAternaList = new ArrayList<>();


        for (Object data : listaApp){
            Object[] projection = (Object[]) data;
            FolioAmountTruckdriverPojo folioAmountPojoApp = new FolioAmountTruckdriverPojo();

            String folio = (String) projection[0];
            BigDecimal commission = (BigDecimal) projection[1];
            BigDecimal iva = (BigDecimal) projection[2];
            BigDecimal total = commission.add(iva, new MathContext(3));

            folioAmountPojoApp.setFolio(folio);
            folioAmountPojoApp.setCommission(commission);
            folioAmountPojoApp.setIva(iva);
            folioAmountPojoApp.setTotal(total);

            folioAmountAppList.add(folioAmountPojoApp);
        }

        for (Object data : listaAterna){
            Object[] projection = (Object[]) data;
            FolioAmountTruckdriverPojo folioAmountPojoAterna = new FolioAmountTruckdriverPojo();

            String folio = (String) projection[0];
            BigDecimal commission = (BigDecimal) projection[1];
            BigDecimal iva = (BigDecimal) projection[2];
            BigDecimal total = commission.add(iva, new MathContext(3));

            folioAmountPojoAterna.setFolio(folio);
            folioAmountPojoAterna.setCommission(commission);
            folioAmountPojoAterna.setIva(iva);
            folioAmountPojoAterna.setTotal(total);

            folioAmountAternaList.add(folioAmountPojoAterna);
        }

        PdfAternaPojo aternaPojo = new PdfAternaPojo();

        if (folioAmountAppList.size() > 0){
            List<FolioAmountTruckdriverPojo> existentes = new ArrayList<>();
            List<FolioAmountTruckdriverPojo> noEncontadosApp = new ArrayList<>();
            List<FolioAmountTruckdriverPojo> noEncontadosAterna = new ArrayList<>();


            for (FolioAmountTruckdriverPojo aternaM : folioAmountAternaList){
                if (folioAmountAppList.contains(aternaM)){
                    existentes.add(aternaM);
                }else {
                    noEncontadosAterna.add(aternaM);
                }
            }

            for (FolioAmountTruckdriverPojo appD : folioAmountAppList){
                if (!folioAmountAternaList.contains(appD)){
                    noEncontadosApp.add(appD);
                }
            }


            if ((existentes.size() == folioAmountAppList.size()) && (existentes.size() == folioAmountAternaList.size())) {

                BigDecimal sumMonSubtotal = new BigDecimal(0.00);
                BigDecimal sumMonIva = new BigDecimal(0.00);
                BigDecimal sumMonTotal = new BigDecimal(0.00);

                for (FolioAmountTruckdriverPojo amountTruckdriverPojo : folioAmountAternaList) {
                    if (amountTruckdriverPojo != null) {
                        sumMonSubtotal = sumMonSubtotal.add(amountTruckdriverPojo.getCommission());
                        sumMonIva = sumMonIva.add(amountTruckdriverPojo.getIva());
                        sumMonTotal = sumMonTotal.add(amountTruckdriverPojo.getTotal());
                    }
                }


                aternaPojo.setSubtotalD(new BigDecimal(0.00));
                aternaPojo.setIvaD(new BigDecimal(0.00));
                aternaPojo.setTotalD(new BigDecimal(0.00));
                aternaPojo.setSubtotalM(sumMonSubtotal);
                aternaPojo.setIvaM(sumMonIva);
                aternaPojo.setTotalM(sumMonTotal);
                aternaPojo.setConciliation(true);
                aternaPojo.setNumSecures(folioAmountAternaList.size());
                aternaPojo.setFoliosMNoEncontradosD(noEncontadosApp);
                aternaPojo.setFoliosMNoEncontradosM(noEncontadosAterna);

            }else {
                aternaPojo.setFoliosMNoEncontradosD(noEncontadosApp);
                aternaPojo.setFoliosMNoEncontradosM(noEncontadosAterna);
                aternaPojo.setSubtotalD(new BigDecimal(0.00));
                aternaPojo.setIvaD(new BigDecimal(0.00));
                aternaPojo.setTotalD(new BigDecimal(0.00));
                aternaPojo.setSubtotalM(new BigDecimal(0.00));
                aternaPojo.setIvaM(new BigDecimal(0.00));
                aternaPojo.setTotalM(new BigDecimal(0.00));
                aternaPojo.setConciliation(false);
                aternaPojo.setNumSecures(0);
            }
        }else {
            aternaPojo.setFoliosMNoEncontradosD(folioAmountAppList);
            aternaPojo.setFoliosMNoEncontradosM(folioAmountAternaList);
            aternaPojo.setSubtotalD(new BigDecimal(0.00));
            aternaPojo.setIvaD(new BigDecimal(0.00));
            aternaPojo.setTotalD(new BigDecimal(0.00));
            aternaPojo.setSubtotalM(new BigDecimal(0.00));
            aternaPojo.setIvaM(new BigDecimal(0.00));
            aternaPojo.setTotalM(new BigDecimal(0.00));
            aternaPojo.setConciliation(false);
            aternaPojo.setNumSecures(0);
        }

        return aternaPojo;
    }

    @Override
    public List<String> findNoAutizationByDStartValidity(LocalDate startDate, LocalDate endDate) {
        return policyTruckdriverMonthlyDao.findNoAutizationByDStartValidity(startDate, endDate);
    }

    @Override
    public List findFoliosCommissionIvaByDStartValidity(LocalDate startDate, LocalDate endDate) {
        return policyTruckdriverMonthlyDao.findFoliosCommissionIvaByDStartValidity(startDate, endDate);
    }
}
