package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.DistributorsDetailBanksHistoryDao;
import mx.bidg.model.DistributorsDetailBanksHistory;
import mx.bidg.model.Users;
import mx.bidg.service.DistributorsDetailBanksHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by Leonardo on 04/07/2017.
 */
@Service
@Transactional
public class DistributorsDetailBanksHistoryServiceImpl implements DistributorsDetailBanksHistoryService{

    @Autowired
    DistributorsDetailBanksHistoryDao distributorsDetailBanksHistoryDao;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public DistributorsDetailBanksHistory saveData(String data, Users user, Integer idDistributor) throws IOException {
        JsonNode node = mapper.readTree(data);
        DistributorsDetailBanksHistory distributorsDetailBanksHistory = new DistributorsDetailBanksHistory();

            distributorsDetailBanksHistory.setIdDistributorDetailBank(idDistributor);
            distributorsDetailBanksHistory.setAmount(node.get("amount").decimalValue());
            distributorsDetailBanksHistory.setUsername(user.getUsername());
            distributorsDetailBanksHistory.setCreationDate(LocalDateTime.now());

        distributorsDetailBanksHistory = distributorsDetailBanksHistoryDao.save(distributorsDetailBanksHistory);
        return distributorsDetailBanksHistory;
    }
}
