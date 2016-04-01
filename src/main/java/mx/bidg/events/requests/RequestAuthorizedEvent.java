package mx.bidg.events.requests;

import mx.bidg.events.ModificationEvent;
import mx.bidg.model.Requests;

/**
 * @author Rafael Viveros
 * Created on 31/03/16.
 */
public class RequestAuthorizedEvent implements ModificationEvent<Requests> {

    private Requests request;

    public RequestAuthorizedEvent(Requests request) {
        this.request = request;
    }

    @Override
    public Requests getResource() {
        return request;
    }
}
