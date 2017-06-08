package mx.bidg.dao;

import mx.bidg.model.Payroll;

import java.util.List;

/**
 * Created by Desarrollador on 19/11/2016.
 */
public interface PayrollDao extends InterfaceDao<Payroll> {
    Object sumEfectivoEdmon();
    Object sumGmtNec();
    List<Payroll> findByDistributor(Integer idDistributor);
    Object sumDistributorNec(Integer idDistributor);
    List<Payroll> findAllByAmountPositives();
    List<Payroll> findAllByAmountPositivesNotExtranjeros(List<Integer> idEmployeesE);
    Object sumGmtNecNotExtranjeros(List<Integer> idEmployeesE);
    Object sumCommissionGmtNotExtranjeros(List<Integer> idEmployeesE);
    Object sumPagoGmtNotExtranjeros(List<Integer> idEmployeesE);
    Object sumCommissionDistributorNec(Integer idDistributor);
    Object sumPagoDistributorNec(Integer idDistributor);
}
