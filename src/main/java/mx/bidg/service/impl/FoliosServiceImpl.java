package mx.bidg.service.impl;

import mx.bidg.service.FoliosService;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author Rafael Viveros
 * Created on 2/12/15.
 */
@Service
public class FoliosServiceImpl implements FoliosService {
    @Override
    public String createNew() {
        return nextRandomFolio(3, 5);
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
