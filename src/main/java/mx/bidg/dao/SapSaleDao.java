package mx.bidg.dao;

import mx.bidg.model.GroupsAgreements;
import mx.bidg.model.SapSale;
import mx.bidg.pojos.PojoSiscom;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by gerardo8 on 29/04/16.
 */
public interface SapSaleDao extends InterfaceDao<SapSale>  {
    List<SapSale> findAllByIdSale(String idSale);
    SapSale findByIdSale(String idSale);
    List findByAgreementGroup(List<GroupsAgreements> groupsAgreementsList, LocalDateTime fromDate, LocalDateTime toDate);
    List findByBranchGroup(List<GroupsAgreements>groupsAgreementsList, LocalDateTime fromDate, LocalDateTime toDate);
    List findByZonaGroup(List<GroupsAgreements>groupsAgreementsList, LocalDateTime fromDate, LocalDateTime toDate);
    List findByRegionGroup(List<GroupsAgreements>groupsAgreementsList, LocalDateTime fromDate, LocalDateTime toDate);
    List findByDistributorGroup(List<GroupsAgreements>groupsAgreementsList, LocalDateTime fromDate, LocalDateTime toDate);
}
