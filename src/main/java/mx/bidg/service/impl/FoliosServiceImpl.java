package mx.bidg.service.impl;

import mx.bidg.dao.AuthorizationsDao;
import mx.bidg.dao.CFoliosDao;
import mx.bidg.model.CFolios;
import mx.bidg.model.CTables;
import mx.bidg.service.FoliosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @author Rafael Viveros
 * Created on 2/12/15.
 */
@Service
@Transactional
public class FoliosServiceImpl implements FoliosService {

    @Autowired
    private CFoliosDao foliosDao;

    @Autowired
    private AuthorizationsDao authorizationsDao;

    @Override
    public String createNew(CTables table) {
        String folio = nextRandomFolio(3, 5);

        while (isDuplicated(folio)) {
            folio = nextRandomFolio(3, 5);
        }

        foliosDao.save(new CFolios(folio, table));

        return folio;
    }

    @Override
    public CFolios findByFolio(String folio) {
        CFolios cfolio = foliosDao.findByFolio(folio);
        cfolio.setAuthorizations(authorizationsDao.findByFolio(folio));
        return cfolio;
    }

    @Override
    public List<CFolios> findAll() {
        return foliosDao.findAll();
    }

    private boolean isDuplicated(String folio) {
        CFolios f = foliosDao.findByFolio(folio);
        return f != null;
    }

    private String nextRandomFolio(int lettersLength, int numbersLength) {
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] result = new char[lettersLength + numbersLength];

        Random random = new Random();

        for (int i = 0; i < lettersLength; i++) {
            result[i] = letters[random.nextInt(letters.length)];
        }

        for (int i = lettersLength; i < numbersLength + lettersLength; i++) {
            result[i] = numbers[random.nextInt(numbers.length)];
        }

        return String.valueOf(result);
    }
}
