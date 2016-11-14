package mx.bidg.service;

import mx.bidg.model.CVacationPayTab;

import java.util.List;

/**
 * Created by PC_YAIR on 14/11/2016.
 */
public interface CVacationPayTabService {

    CVacationPayTab save (CVacationPayTab cVacationPayTab);
    CVacationPayTab update(CVacationPayTab cVacationPayTab);
    CVacationPayTab findById(Integer idCVacationPayTab);
    boolean delete (CVacationPayTab cVacationPayTab);
    List<CVacationPayTab> findAll();
}
