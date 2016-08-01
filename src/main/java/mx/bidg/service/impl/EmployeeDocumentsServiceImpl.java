package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.EmployeeDocumentsDao;
import mx.bidg.dao.EmployeesDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.CEmployeeDocumentsTypes;
import mx.bidg.model.EmployeeDocuments;
import mx.bidg.model.Employees;
import mx.bidg.pojos.FilePojo;
import mx.bidg.service.EmployeeDocumentsService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Created by jolvera on 28/06/16.
 */
@Service
@Transactional
public class EmployeeDocumentsServiceImpl implements EmployeeDocumentsService {

    @Autowired
    EmployeeDocumentsDao employeeDocumentsDao;

    @Autowired
    EmployeesDao employeesDao;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Environment env;

    @Override
    public List<EmployeeDocuments> findByIdEmployee(Integer idEmployee) {
        return employeeDocumentsDao.findByIdEmployee(idEmployee);
    }

    @Override
    public EmployeeDocuments findBy(Employees employee, CEmployeeDocumentsTypes documentType) {
        return employeeDocumentsDao.findBy(employee, documentType);
    }

    @Override
    public List<EmployeeDocuments> findRecordBy(Employees employee) {
        return employeeDocumentsDao.findRecordBy(employee);
    }

    @Override
    public EmployeeDocuments save(String data, Integer idEmployee) throws IOException {
        String SAVE_PATH = env.getRequiredProperty("employees.documents_dir");
        String[] fileMediaTypes = env.getRequiredProperty("employees.attachments.media_types").split(",");

        LocalDateTime now = LocalDateTime.now();

        JsonNode node = mapper.readTree(data);

        CEmployeeDocumentsTypes cEmployeeDocumentType = mapper.treeToValue(node.get("documentType"),CEmployeeDocumentsTypes.class);
        FilePojo file = mapper.treeToValue(node.get("file"),FilePojo.class);

        Employees employee = employeesDao.findById(idEmployee);
        EmployeeDocuments employeeDocument = new EmployeeDocuments();

        employeeDocument.setcDocumentType(cEmployeeDocumentType);
        employeeDocument.setCurrentDocument(1);
        employeeDocument.setDocumentName(file.getName());
        employeeDocument.setEmployee(employee);
        employeeDocument.setUploadingDate(now);
        employeeDocument.setIdAccessLevel(1);

        employeeDocument = employeeDocumentsDao.save(employeeDocument);

        boolean isValidMediaType = false;

        for (String mediaType : fileMediaTypes) {
            if (file.getType().equals(mediaType)) {
                isValidMediaType = true;
                break;
            }
        }

        if (! isValidMediaType) {
            throw new ValidationException("Tipo de archivo no admitido", "Tipo de archivo no admitido");
        }

        String destDir = "/employee_" + employee.getIdEmployee();
        String destFile = destDir + "/Documento." + employeeDocument.getIdDocument() +
                "." + now.toInstant(ZoneOffset.UTC).getEpochSecond() +
                "." + cEmployeeDocumentType.getIdDocumentType();

        employeeDocument.setDocumentUrl(destFile);
        employeeDocument = employeeDocumentsDao.update(employeeDocument);

        File dir = new File(SAVE_PATH + destDir);
        if (! dir.exists()) {
            dir.mkdir();
        }

        String encodingPrefix = "base64,";
        int contentStartIndex = file.getDataUrl().indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] byteArreyData = Base64.decodeBase64(file.getDataUrl().substring(contentStartIndex));

        FileOutputStream out = new FileOutputStream(new File(SAVE_PATH + destFile));
        out.write(byteArreyData);
        out.close();

        return employeeDocument;
    }

    @Override
    public EmployeeDocuments findById(int id) {
        return employeeDocumentsDao.findById(id);
    }

    @Override
    public List<EmployeeDocuments> findAll() {
        return employeeDocumentsDao.findAll();
    }

    @Override
    public EmployeeDocuments update(EmployeeDocuments entity) {
        return employeeDocumentsDao.update(entity);
    }

    @Override
    public EmployeeDocuments updateDocument(String data, Integer idEmployee) throws IOException {
        String SAVE_PATH = env.getRequiredProperty("employees.documents_dir");
        String[] fileMediaTypes = env.getRequiredProperty("employees.attachments.media_types").split(",");

        LocalDateTime now = LocalDateTime.now();

        JsonNode jsonNode = mapper.readTree(data);

        Employees employee = employeesDao.findById(idEmployee);
        EmployeeDocuments currentEmployeeDocument = null;
        EmployeeDocuments employeeDocument = new EmployeeDocuments();

        FilePojo file = mapper.treeToValue(jsonNode.get("file"), FilePojo.class);

        if (jsonNode.has("idDocument")) {
            currentEmployeeDocument = employeeDocumentsDao.findById(jsonNode.get("idDocument").asInt());
            employeeDocument.setcDocumentType(currentEmployeeDocument.getcDocumentType());
            currentEmployeeDocument.setCurrentDocument(0);
            employeeDocumentsDao.update(currentEmployeeDocument);
        }

        if (currentEmployeeDocument == null && jsonNode.has("documentType")) {
            CEmployeeDocumentsTypes documentType = mapper.treeToValue(jsonNode.get("documentType"),CEmployeeDocumentsTypes.class);
            employeeDocument.setcDocumentType(documentType);
        }

        employeeDocument.setEmployee(employee);
        employeeDocument.setUploadingDate(now);
        employeeDocument.setCurrentDocument(1);
        employeeDocument.setIdAccessLevel(1);
        employeeDocument.setDocumentName(file.getName());

        employeeDocument = employeeDocumentsDao.save(employeeDocument);

        boolean isValidMediaType = false;

        for (String mediaType : fileMediaTypes) {
            if (file.getType().equals(mediaType)) {
                isValidMediaType = true;
                break;
            }
        }

        if (! isValidMediaType) {
            throw new ValidationException("Tipo de archivo no admitido", "Tipo de archivo no admitido");
        }

        String destDir = "/employee_" + employee.getIdEmployee();
        String destFile = destDir + "/Documento." + employeeDocument.getIdDocument() +
                "." + now.toInstant(ZoneOffset.UTC).getEpochSecond() +
                "." + employeeDocument.getcDocumentType().getIdDocumentType();

        employeeDocument.setDocumentUrl(destFile);
        employeeDocumentsDao.update(employeeDocument);

        File dir = new File(SAVE_PATH + destDir);
        if (! dir.exists()) {
            dir.mkdir();
        }

        String encodingPrefix = "base64,";
        int contentStartIndex = file.getDataUrl().indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] byteArreyData = Base64.decodeBase64(file.getDataUrl().substring(contentStartIndex));

        FileOutputStream out = new FileOutputStream(new File(SAVE_PATH + destFile));
        out.write(byteArreyData);
        out.close();

        return employeeDocument;
    }

    @Override
    public boolean delete(EmployeeDocuments entity) {
        employeeDocumentsDao.delete(entity);
        return true;
    }
}
