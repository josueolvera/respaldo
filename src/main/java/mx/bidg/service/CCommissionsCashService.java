package mx.bidg.service;

import mx.bidg.model.CCommissionsCash;

import java.util.List;

/**
 * Created by PC_YAIR on 15/11/2016.
 */
public interface CCommissionsCashService {
    List<CCommissionsCash> findAll();
    CCommissionsCash update (CCommissionsCash cCommissionsCash);
    CCommissionsCash findById(Integer idCommissionsCash);
    boolean delete (CCommissionsCash cCommissionsCash);
}
