package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.RefundConceptsDao;
import mx.bidg.model.CBudgetConcepts;
import mx.bidg.model.CVoucherTypes;
import mx.bidg.model.RefundConcepts;
import mx.bidg.model.Refunds;
import mx.bidg.pojos.FilePojo;
import mx.bidg.pojos.xml_bill.Comprobante;
import mx.bidg.service.RefundConceptsService;
import mx.bidg.service.RefundsService;
import mx.bidg.utils.XMLConverter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gerardo8 on 22/07/16.
 */
@Transactional
@Service
public class RefundConceptsServiceImpl implements RefundConceptsService {

    @Autowired
    private RefundConceptsDao refundConceptsDao;

    @Autowired
    private RefundsService refundsService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private XMLConverter xmlConverter;

    @Override
    public Comprobante getVoucherXmlData(String data) throws IOException {

        FilePojo file = mapper.treeToValue(mapper.readTree(data),FilePojo.class);

        String encodingPrefix = "base64,";
        int contentStartIndex = file.getDataUrl().indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] byteArreyData = Base64.decodeBase64(file.getDataUrl().substring(contentStartIndex));
        InputStream inputStream = new ByteArrayInputStream(byteArreyData);

        return (Comprobante) xmlConverter.convertFromXMLToObject(inputStream);
    }

    @Override
    public RefundConcepts save(String data, Integer idRefund) throws IOException {

        Refunds refund = refundsService.findById(idRefund);

        JsonNode jsonNode = mapper.readTree(data);
        CBudgetConcepts concept = mapper.treeToValue(jsonNode.get("concept"),CBudgetConcepts.class);
        CVoucherTypes voucherType = mapper.treeToValue(jsonNode.get("voucherType"),CVoucherTypes.class);

        RefundConcepts refundConcept = new RefundConcepts();
        refundConcept.setRefund(refund);
        refundConcept.setBudgetConcept(concept);
        refundConcept.setVoucherType(voucherType);
        refundConcept.setVoucherFolio(jsonNode.get("voucherFolio").asText());
        refundConcept.setVoucherTotal(jsonNode.get("voucherTotal").decimalValue());

        refundConceptsDao.save(refundConcept);

        return refundConcept;
    }
}
