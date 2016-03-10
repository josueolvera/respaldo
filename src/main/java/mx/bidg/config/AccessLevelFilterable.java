package mx.bidg.config;

/**
 * @author Rafael Viveros
 * Created on 24/02/16.
 *
 * Los model (entities) que necesiten ser filtrados por Access Level,
 * deben implementar esta interfaz
 */
public interface AccessLevelFilterable {
    Integer getIdAccessLevel();
}
