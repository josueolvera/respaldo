package mx.bidg.service.impl;

import mx.bidg.dao.RequestHistoryDao;
import mx.bidg.dao.UsersDao;
import mx.bidg.model.CRequestStatus;
import mx.bidg.model.RequestHistory;
import mx.bidg.model.Requests;
import mx.bidg.model.Users;
import mx.bidg.service.RequestHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by desarrollador on 31/05/17.
 */
@Service
@Transactional
public class RequestHistoryServiceImpl implements RequestHistoryService {

    @Autowired
    RequestHistoryDao requestHistoryDao;

    @Autowired
    UsersDao usersDao;

    @Override
    public RequestHistory save(RequestHistory requestHistory) {
        return requestHistoryDao.save(requestHistory);
    }

    @Override
    public RequestHistory findById(Integer idRequestHistory) {
        return requestHistoryDao.findById(idRequestHistory);
    }

    @Override
    public RequestHistory update(RequestHistory requestHistory) {
        return requestHistoryDao.update(requestHistory);
    }

    @Override
    public List<RequestHistory> findAll() {
        return requestHistoryDao.findAll();
    }

    @Override
    public boolean delete(RequestHistory requestHistory) {
        return requestHistoryDao.delete(requestHistory);
    }

    @Override
    public RequestHistory saveRequest(Requests requests, Users user) {

        RequestHistory requestHistory = new RequestHistory();

        if (requests != null){

            if (!requests.getRequestStatus().equals(CRequestStatus.ENVIADA_PENDIENTE)){
                List<RequestHistory> requestHistories = requestHistoryDao.findAllByRequest(requests.getIdRequest());

                for (RequestHistory history : requestHistories){
                    history.setrStatus(0);
                    requestHistoryDao.update(history);
                }
            }

            requestHistory.setrStatus(1);
            requestHistory.setHigthDate(requests.getCreationDate());
            requestHistory.setCreationDate(LocalDateTime.now());
            requestHistory.setFolio(requests.getFolio());
            requestHistory.setIdAccessLevel(1);


            Users users = usersDao.findByUsername(requests.getUserName());

            if (users != null){
                requestHistory.setIdRequestUser(users.getIdUser());
            }

            requestHistory.setIdResponsibleUser(user.getIdUser());
            requestHistory.setIdDistributorCostCenter(requests.getDistributorCostCenter().getIdDistributorCostCenter());
            requestHistory.setIdRequest(requests.getIdRequest());
            requestHistory.setIdRequestCategory(requests.getRequestCategory().getIdRequestCategory());
            requestHistory.setIdRequestStatus(requests.getRequestStatus().getIdRequestStatus());
            requestHistory.setIdRequestType(requests.getRequestType().getIdRequestType());
            requestHistory.setIdEmployee(requests.getEmployees().getIdEmployee());
            requestHistory.setReasonRequest(requests.getReason());
            requestHistory.setReasonResponsible(requests.getReasonResponsible());
            requestHistory.setUsername(user.getUsername());

            requestHistory = requestHistoryDao.save(requestHistory);
        }

        return requestHistory;
    }

    @Override
    public List<RequestHistory> findAllForRequest(Integer idRequest) {
        return requestHistoryDao.findAllByRequest(idRequest);
    }
}
