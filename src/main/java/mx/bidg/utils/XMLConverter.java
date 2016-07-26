package mx.bidg.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * Created by gerardo8 on 20/07/16.
 */
@Component
public class XMLConverter {

    @Autowired
    private Unmarshaller unmarshaller;

    public Object convertFromXMLToObject(InputStream is) throws IOException {

        try {
            return this.unmarshaller.unmarshal(new StreamSource(is));
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

}