package mx.bidg.service;

import mx.bidg.model.RequestConcept;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
public interface RequestConceptService {
    RequestConcept save (RequestConcept requestConcept);
    RequestConcept update(RequestConcept requestConcept);
    RequestConcept findById (Integer idRequestConcept);
    List<RequestConcept> findAll();
    Boolean delete (RequestConcept requestConcept);
}
