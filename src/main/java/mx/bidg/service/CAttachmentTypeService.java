package mx.bidg.service;

import mx.bidg.model.CAttachmentType;

import java.util.List;

/**
 * Created by Kevin Salvador on 17/01/2017.
 */
public interface CAttachmentTypeService {
    List<CAttachmentType>findAll();
    CAttachmentType findById(Integer id);
}
