/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mx.bidg.dao.BudgetMonthBranchDao;
import mx.bidg.dao.BudgetsDao;
import mx.bidg.dao.CMonthsDao;
import mx.bidg.dao.CProductTypesDao;
import mx.bidg.dao.CRequestCategoriesDao;
import mx.bidg.dao.CRequestTypesDao;
import mx.bidg.dao.RequestProductsDao;
import mx.bidg.dao.RequestTypesProductDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.dao.UsersDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.BudgetMonthBranch;
import mx.bidg.model.Budgets;
import mx.bidg.model.CMonths;
import mx.bidg.model.CProductTypes;
import mx.bidg.model.CProducts;
import mx.bidg.model.CRequestStatus;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.CRequestsCategories;
import mx.bidg.model.CTables;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.RequestProducts;
import mx.bidg.model.RequestTypesProduct;
import mx.bidg.model.Requests;
import mx.bidg.model.Users;
import mx.bidg.service.FoliosService;
import mx.bidg.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequestsServiceImpl implements RequestsService {
    
    @Autowired
    RequestsDao requestsDao;
    
    @Autowired
    RequestTypesProductDao requestTypesProductDao;
    
    @Autowired
    CRequestCategoriesDao cRequestCategoriesDao;
    
    @Autowired
    CRequestTypesDao cRequestTypesDao;
    
    @Autowired
    CProductTypesDao cProductTypesDao;
    
    @Autowired
    UsersDao usersDao;
    
    @Autowired
    BudgetsDao budgetsDao;
    
    @Autowired
    BudgetMonthBranchDao budgetMonthBranchDao;
    
    @Autowired
    CMonthsDao cMonthsDao;
    
    @Autowired
    FoliosService foliosService;
    
    @Autowired
    RequestProductsDao requestProductsDao;
    
    ObjectMapper map = new ObjectMapper();
    
    @Override
    public HashMap<String, Object> getBudgetMonthProductType(String data) throws Exception {
        
        JsonNode jsonRequest = map.readTree(data);
        HashMap<String, Object> hashMap = new HashMap<>();
        
        CRequestsCategories cRequestsCategory = cRequestCategoriesDao
                .findById(jsonRequest.get("idRequestCategory").asInt());
        CRequestTypes cRequestType = cRequestTypesDao
                .findByIdFetchBudgetCategory(jsonRequest.get("idRequestType").asInt());
        CProductTypes cProductType = cProductTypesDao
                .findByIdFetchBudgetSubcategory(jsonRequest.get("idProductType").asInt());
        Users userResponsable = usersDao.findByIdFetchDwEmployee(jsonRequest.get("idUserResponsable").asInt());
        LocalDateTime date = LocalDateTime.parse(jsonRequest.get("applyingDate").asText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        CMonths month = cMonthsDao.findById(date.getMonthValue());
        Integer year = date.getYear();
        
        if(month == null) {
            throw new ValidationException("No existe el mes", "Error al obtener el mes");
        }
        
        DwEnterprises dwEnterprise = userResponsable.getDwEmployee().getDwEnterprise();        
        
        Budgets budget = budgetsDao.findByCombination(dwEnterprise.getGroup(), dwEnterprise.getArea(), 
                cRequestType.getBudgetCategory(), cProductType.getBudgetSubcategory());
        
        if(budget == null) {
            throw new ValidationException("No existe el Presupuesto", "No existe un presupuesto para esta solicitud");
        }
        
        RequestTypesProduct requestTypesProduct = requestTypesProductDao.findByCombination(cRequestsCategory, 
                cRequestType, cProductType);
        
        if(requestTypesProduct == null) {
            throw new ValidationException("No existe el RequestTypesProduct", "No existe un tipo de producto "
                    + "asociado a esta solicitud");
        }
        
        hashMap.put("requestTypesProduct", requestTypesProduct);
        BudgetMonthBranch budgetMonthBranch = budgetMonthBranchDao.findByCombination(budget, month, dwEnterprise, year);
        
        if(budgetMonthBranch == null) {
            throw new ValidationException("No existe Presupuesto para la fecha solicitada", "No existe Presupuesto para la fecha solicitada");
        }
        
        hashMap.put("budgetMonthBranch", budgetMonthBranch);
        return hashMap;
    }

    @Override
    public Requests saveData(String data, Users user) throws Exception {
        
        JsonNode jsonRequest = map.readTree(data);        
        Requests request = new Requests();
        request.setDescription(jsonRequest.get("request").get("description").asText());
        request.setPurpose(jsonRequest.get("request").get("purpose").asText());
        request.setRequestTypeProduct(new RequestTypesProduct(jsonRequest.get("request").get("idRequestTypesProduct").asInt()));
        request.setBudgetMonthBranch(new BudgetMonthBranch(jsonRequest.get("request").get("idBudgetMonthBranch").asInt()));
        //51 es el id de Requests en CTables
        request.setFolio(foliosService.createNew(new CTables(51)));
        request.setUserRequest(user);
        request.setRequestStatus(new CRequestStatus(CRequestStatus.PENDIENTE));
        request.setUserResponsible(new Users(jsonRequest.get("request").get("idUserResponsable").asInt()));
        request.setCreationDate(LocalDateTime.now());
        request.setApplyingDate(LocalDateTime.parse(jsonRequest.get("request").get("applyingDate").asText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        request.setIdAccessLevel(1);
        List<RequestProducts> requestProducts = new ArrayList<>();
        
        for(JsonNode jsonProducts : jsonRequest.get("products")) {
            CProducts product = new CProducts(jsonProducts.get("idProduct").asInt());
            RequestProducts requestProduct = new RequestProducts();
            requestProduct.setProduct(product);
            requestProduct.setRequest(request);
            requestProducts.add(requestProduct);
        }
        
        request.setRequestProductsList(requestProducts);
        request = requestsDao.save(request);
        
        return request;
    }

    @Override
    public Requests authorization(Integer idRequest) {
        
        Requests request = requestsDao.findByIdFetchStatus(idRequest);
        
        return null;
    }
    
}
