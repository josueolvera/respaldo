package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CArticlesCategories;
import mx.bidg.service.CArticlesCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 02/06/16.
 */
@Controller
@RequestMapping("articles-categories")
public class CArticlesCategoriesController {

    @Autowired
    private CArticlesCategoriesService cArticlesCategoriesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/{idArticleCategory}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@PathVariable Integer idArticleCategory) throws IOException {
        CArticlesCategories articlesCategory = cArticlesCategoriesService.findById(idArticleCategory);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(articlesCategory),
                HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<CArticlesCategories> articlesCategories = cArticlesCategoriesService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(articlesCategories),
                HttpStatus.OK
        );
    }
}
