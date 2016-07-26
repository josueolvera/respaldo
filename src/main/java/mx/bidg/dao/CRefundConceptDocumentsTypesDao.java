package mx.bidg.dao;

import mx.bidg.model.CRefundConceptDocumentsTypes;

import java.util.List;

/**
 * Created by gerardo8 on 22/07/16.
 */
public interface CRefundConceptDocumentsTypesDao extends InterfaceDao<CRefundConceptDocumentsTypes> {
    List<CRefundConceptDocumentsTypes> findByIdVoucherType(Integer idVoucherType);

}

