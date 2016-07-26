package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.RefundsDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.FoliosService;
import mx.bidg.service.RefundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by gerardo8 on 25/07/16.
 */
@Transactional
@Service
public class RefundsServiceImpl implements RefundsService {

    @Autowired
    private RefundsDao refundsDao;

    @Autowired
    FoliosService foliosService;

    @Autowired
    RequestsDao requestsDao;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public Refunds save(String data, Users user) throws IOException {

        LocalDateTime now = LocalDateTime.now();

        JsonNode requestNode = mapper.readTree(data);

        Requests request = new Requests();
        request.setPurpose(requestNode.get("purpose").asText());
        request.setFolio(foliosService.createNew(new CTables(51)));
        request.setUserRequest(user);
        request.setCreationDate(now);
        request.setIdAccessLevel(1);

        request = requestsDao.save(request);

        Refunds refund = new Refunds();
        refund.setRefundTotal(requestNode.get("refundTotal").decimalValue());
        refund.setRequest(request);
        refund.setCreationDate(now);

        refund = refundsDao.save(refund);

        return refund;
    }

    @Override
    public Refunds findById(Integer idRefund) {
        return refundsDao.findById(idRefund);
    }
}
