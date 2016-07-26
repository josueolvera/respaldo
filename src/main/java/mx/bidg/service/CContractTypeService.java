package mx.bidg.service;

import mx.bidg.model.CContractType;

import java.util.List;

/**
 * Created by josueolvera on 19/07/16.
 */
public interface CContractTypeService {
    CContractType findById(Integer idContractType);
    List<CContractType> findAll();
    CContractType findByContractTypeName(String contractTypeName);
}
