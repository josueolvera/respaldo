package mx.bidg.service;

import mx.bidg.model.CEmployeeType;

import java.util.List;

/**
 * Created by josueolvera on 19/07/16.
 */
public interface CEmployeeTypeService {

    CEmployeeType findById (Integer idEmployeeType);
    List<CEmployeeType> findAll();
    CEmployeeType findByEmployeeTypeName (String employeeTypeName);
}
