package mx.bidg.controller.siad;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by desarrollador on 1/06/17.
 */
@Controller
@RequestMapping("request-history")
public class RequestHistoryController {

    @Autowired
    RequestHistoryService requestHistoryService;

    @Autowired
    RequestsService requestsService;

    @Autowired
    EmployeesService employeesService;

    @Autowired
    PriceEstimationsService priceEstimationsService;

    @Autowired
    DistributorCostCenterService distributorCostCenterService;

    @Autowired
    UsersService usersService;

    @Autowired
    CRequestStatusService cRequestStatusService;

    @Autowired
    CRequestTypesService cRequestTypesService;

    @Autowired
    CRequestCategoriesService cRequestCategoriesService;

    @Autowired
    ObjectMapper mapper;

    @RequestMapping(value = "/request/{idRequest}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAllByRequest(@PathVariable Integer idRequest)throws IOException{

        List<RequestHistory> requestHistories = requestHistoryService.findAllForRequest(idRequest);

        if (!requestHistories.isEmpty()){
            for (RequestHistory requestHistory : requestHistories){
                Requests request = requestsService.findById(requestHistory.getIdRequest());

                if (request != null){
                    requestHistory.setRequest(request);
                }

                CRequestsCategories cRequestsCategories = cRequestCategoriesService.findById(requestHistory.getIdRequestCategory());

                if (cRequestsCategories != null){
                    requestHistory.setcRequestCategory(cRequestsCategories);
                }

                CRequestTypes cRequestTypes = cRequestTypesService.findById(requestHistory.getIdRequestType());

                if (cRequestTypes != null){
                    requestHistory.setcRequestType(cRequestTypes);
                }

                CRequestStatus cRequestStatus = cRequestStatusService.findById(requestHistory.getIdRequestStatus());

                if (cRequestStatus != null){
                    requestHistory.setcRequestStatus(cRequestStatus);
                }

                Employees employee = employeesService.findById(requestHistory.getIdEmployee());

                if (employee != null){
                    requestHistory.setEmployee(employee);
                }

                if (requestHistory.getIdPriceEstimation() != null){
                    PriceEstimations priceEstimation = priceEstimationsService.findById(requestHistory.getIdPriceEstimation());

                    if (priceEstimation != null){
                        requestHistory.setPriceEstimations(priceEstimation);
                    }
                }


                DistributorCostCenter distributorCostCenter = distributorCostCenterService.findById(requestHistory.getIdDistributorCostCenter());

                if (distributorCostCenter != null){
                    requestHistory.setDistributorCostCenter(distributorCostCenter);
                }

                Users userRequest = usersService.findById(requestHistory.getIdRequestUser());

                if (userRequest != null){
                    requestHistory.setUserRequest(userRequest);
                }

                Users userResponsible = usersService.findById(requestHistory.getIdResponsibleUser());

                if (userResponsible != null){
                    requestHistory.setUserResponsible(userResponsible);
                }

            }

        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestHistories), HttpStatus.OK);

    }
}
