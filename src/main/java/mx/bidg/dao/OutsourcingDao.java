package mx.bidg.dao;

import mx.bidg.model.Outsourcing;

import java.time.LocalDateTime;

/**
 * Created by gerardo8 on 16/05/16.
 */
public interface OutsourcingDao extends InterfaceDao<Outsourcing> {
    Outsourcing finfByidEmployee(int idEmployee,LocalDateTime applicationDate);
}
