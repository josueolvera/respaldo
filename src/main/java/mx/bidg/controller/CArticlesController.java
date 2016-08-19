package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CArticles;
import mx.bidg.service.CArticlesService;
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
 * Created by gerardo8 on 08/05/16.
 */
@Controller
@RequestMapping("articles")
public class CArticlesController {

    @Autowired
    CArticlesService cArticlesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException {
        List<CArticles> cArticlesList = cArticlesService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cArticlesList), HttpStatus.OK);
    }

    @RequestMapping(value = "/article-category/{idArticleCategory}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByArticleCategory(@PathVariable Integer idArticleCategory) throws IOException {
        List<CArticles> cArticlesList = cArticlesService.findByArticleCategory(idArticleCategory);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cArticlesList), HttpStatus.OK);
    }
}
