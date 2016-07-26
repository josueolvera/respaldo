package mx.bidg.service;

import mx.bidg.model.CRefundConceptDocumentsTypes;

import java.util.List;

/**
 * Created by gerardo8 on 22/07/16.
 */
public interface CRefundConceptDocumentsTypesService {
    List<CRefundConceptDocumentsTypes> findByIdVoucherType(Integer idVoucherType);
    List<CRefundConceptDocumentsTypes> findAll();
}
