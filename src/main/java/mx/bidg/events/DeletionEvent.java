package mx.bidg.events;

/**
 * @author Rafael Viveros
 * Created on 15/03/16.
 */
public interface DeletionEvent<T> {
    T getResource();
}
