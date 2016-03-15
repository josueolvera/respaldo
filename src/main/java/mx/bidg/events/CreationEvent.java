package mx.bidg.events;

/**
 * @author Rafael Viveros
 * Created on 14/03/16.
 */
public interface CreationEvent<T> {
    T getResource();
}
