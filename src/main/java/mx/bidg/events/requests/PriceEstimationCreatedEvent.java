package mx.bidg.events.requests;

import mx.bidg.events.ModificationEvent;
import mx.bidg.model.Requests;

/**
 * @author Rafael Viveros
 * Created on 22/03/16.
 */
public class PriceEstimationCreatedEvent implements ModificationEvent<Requests> {

    private Requests request;

    public PriceEstimationCreatedEvent(Requests request) {
        this.request = request;
    }

    @Override
    public Requests getResource() {
        return request;
    }
}
