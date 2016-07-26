package mx.bidg.service;

import mx.bidg.model.RefundConcepts;
import mx.bidg.pojos.xml_bill.Comprobante;

import java.io.IOException;

/**
 * Created by gerardo8 on 22/07/16.
 */
public interface RefundConceptsService {
    Comprobante getVoucherXmlData(String data) throws IOException;
    RefundConcepts save(String data, Integer idRefund) throws IOException;
}
