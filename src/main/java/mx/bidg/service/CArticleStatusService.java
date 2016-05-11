package mx.bidg.service;

import mx.bidg.model.CArticleStatus;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 25/01/16.
 */
public interface CArticleStatusService {
    List<CArticleStatus> findAll();

    CArticleStatus findById(Integer id);
}
