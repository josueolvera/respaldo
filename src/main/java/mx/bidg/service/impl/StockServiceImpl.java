package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import mx.bidg.dao.StockDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 9/12/15.
 */
@Service
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private CArticlesService articlesService;

    @Autowired
    private DwEmployeesService dwEmployeesService;

    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private StockEmployeeAssignmentsService assignmentsService;

    @Override
    public Stocks save(Stocks article) {
        stockDao.save(article);
        return article;
    }

    @Override
    public Stocks findById(int id) {
        return stockDao.findById(id);
    }

    @Override
    public Stocks findSimpleById(int idStock) {
        return stockDao.findSimpleById(idStock);
    }

    @Override
    public List<Stocks> findByDistributor(Integer idDistributor) {
        return stockDao.findByDistributor(idDistributor);
    }

    @Override
    public List<Stocks> findAll() {
        return stockDao.findAll();
    }

    @Override
    public List<Stocks> addStockArticlesFromRequest(Requests request) {
        List<Stocks> stocksList = new ArrayList<>();

        for (RequestProducts product : request.getRequestProductsList()) {
            Stocks stockArticle = new Stocks();
            stockArticle.setArticle(articlesService.findByProduct(product.getProduct()));
            stockArticle.setArticleStatus(new CArticleStatus(1));
            stockArticle.setCreationDate(LocalDateTime.now());
            stockArticle.setFolio(request.getFolio());
            this.save(stockArticle);
            stocksList.add(stockArticle);
        }

        return stocksList;
    }

    @Override
    public Stocks update(Stocks stock) {
        stockDao.update(stock);
        return stock;
    }

    @Override
    public Stocks updateEntity(Stocks stock) {
        return stockDao.updateEntity(stock);
    }

    @Override
    public Stocks update(Integer idStock, JsonNode jnode) {
        Employees employee = employeesService.findById(jnode.get("employee").get("idEmployee").asInt());

        Stocks stock = findSimpleById(idStock);
        DwEmployees dwEmployee = dwEmployeesService.findBy(
                new Employees(jnode.get("employee").get("idEmployee").asInt()),
                new DwEnterprises(stock.getIdDwEnterprises())
        );

        StockEmployeeAssignments assignment = assignmentsService.getAssignmentFor(stock);

        stock.setStockFolio(jnode.get("stockFolio").asText());
        stock.setArticleStatus(new CArticleStatus(jnode.get("articleStatus").get("idArticleStatus").asInt()));
        stock.setInvoiceNumber(jnode.get("invoiceNumber").asText());

        if (employee != null) {
            if (assignment == null || !assignment.getIdEmmployee().equals(employee.getIdEmployee())) {
                if (dwEmployee == null && assignment != null) {
                    throw new ValidationException(
                            "No existe DwEmployees: No se permite resignación de área",
                            "No se permite resignación de área",
                            HttpStatus.FORBIDDEN
                    );
                }

                StockEmployeeAssignments newAssignment = new StockEmployeeAssignments();
                newAssignment.setStocks(stock);
                newAssignment.setDwEnterprises(stock.getDwEnterprises());
                newAssignment.setEmployee(employee);
                newAssignment.setAssignmentDate(LocalDateTime.now());
                newAssignment.setCurrentAssignment(1);
                newAssignment.setIdAccessLevel(1);

                if (assignment != null) {
                    assignment.setCurrentAssignment(0);
                    assignmentsService.update(assignment);
                }
                assignmentsService.saveAssignment(newAssignment);
            }
        }
        return update(stock);
    }
}
