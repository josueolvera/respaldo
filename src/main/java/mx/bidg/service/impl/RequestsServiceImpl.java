/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
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
    public Requests save(String data, Users user) throws Exception {
        
        JsonNode jsonRequest = map.readTree(data);
        CRequestsCategories cRequestsCategory = cRequestCategoriesDao
                .findById(jsonRequest.get("idRequestCategory").asInt());
        CRequestTypes cRequestType = cRequestTypesDao
                .findById(jsonRequest.get("idRequestType").asInt());
        CProductTypes cProductType = cProductTypesDao
                .findById(jsonRequest.get("idProductType").asInt());
        Users userResponsable = usersDao.findByIdFetchDwEmployee(jsonRequest.get("idUser").asInt());
        LocalDate date = LocalDate.now();
        
        CMonths month = cMonthsDao.findById(date.getMonthValue());
        
        if(month == null) {
            throw new ValidationException("No existe el mes");
        }
        
        DwEnterprises dwEnterprise = userResponsable.getDwEmployee().getDwEnterprise();
        
        Budgets budget = budgetsDao.findByCombination(dwEnterprise.getGroup(), dwEnterprise.getArea(), 
                cRequestType.getIdBudgetCategory(), cProductType.getIdBudgetSubcategory());
        
        if(budget == null) {
            throw new ValidationException("No existe el Presupuesto");
        }
        
        RequestTypesProduct requestTypesProduct = requestTypesProductDao.findByCombination(cRequestsCategory, 
                cRequestType, cProductType);
        
        if(requestTypesProduct == null) {
            throw new ValidationException("No existe el RequestTypesProduct");
        }
        
        BudgetMonthBranch budgetMonthBranch = budgetMonthBranchDao.findByCombination(budget, month, dwEnterprise, date.getYear());
        
        if(budgetMonthBranch == null) {
            throw new ValidationException("No existe Presupuesto para la fecha solicitada");
        }
        
        Requests request = new Requests();
        request.setRequestTypeProduct(requestTypesProduct);
        request.setBudgetMonthBranch(budgetMonthBranch);
        //51 es el id de Requests en CTables
        request.setFolio(foliosService.createNew(new CTables(51)));
        request.setUserRequest(user);
        request.setUserResponsable(userResponsable);
        request.setDescription(jsonRequest.get("description").asText());
        request.setPurpose(jsonRequest.get("purpose").asText());
        //1 es el id de Pendiente en CRequestStatus
        request.setRequestStatus(new CRequestStatus(1));
        request.setIdAccessLevel(1);
        request = requestsDao.save(request);
        request = requestsDao.findByIdFetchBudgetMonthBranch(request.getIdRequest());
        
        for(JsonNode jsonProducts : jsonRequest.get("products")) {
            CProducts product = new CProducts(jsonProducts.get("idProduct").asInt());
            RequestProducts requestProduct = new RequestProducts();
            requestProduct.setIdProduct(product);
            requestProduct.setIdRequest(request);
            requestProduct = requestProductsDao.save(requestProduct);
        }
        
        return request;
    }
    
}
