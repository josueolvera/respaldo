package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CEstados;
import mx.bidg.model.CMunicipios;
import mx.bidg.service.CEstadosService;
import mx.bidg.service.CMunicipiosService;
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
 * Created by jolvera on 7/05/16.
 */
@Controller
@RequestMapping("municipalities")
public class CMunicipiosController {

        @Autowired
        private CMunicipiosService service;

        @Autowired
        private CEstadosService estadosService;

        private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

        @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<String> findAll() throws IOException {
            List<CMunicipios> municipios = service.findAll();
            return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(municipios), HttpStatus.OK);
        }

        @RequestMapping(value = "/{idEstado}/{idMunicipio}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<String> findByMunicipio(@PathVariable int idEstado,@PathVariable int idMunicipio) throws IOException{
            CEstados estados = estadosService.findById(idEstado);
            CMunicipios municipio = service.findMunicipio(estados,idMunicipio);
            return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(municipio), HttpStatus.OK);
        }
}
