package mx.bidg.service;

import mx.bidg.model.CGenders;

import java.util.List;

/**
 * Created by josueolvera on 19/07/16.
 */
public interface CGendersService {
    CGenders findById(Integer idGender);
    List<CGenders> findAll();
    CGenders findByGenderName (String genderName);
}
