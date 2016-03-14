package mx.bidg.events;

import mx.bidg.model.Requests;

/**
 * @author Rafael Viveros
 * Created on 14/03/16.
 */
public class RequestCreatedEvent implements CreationEvent<Requests> {
    private Requests request;

    public RequestCreatedEvent(Requests request) {
        this.request = request;
    }

    public Requests getResource() {
        return request;
    }
}
