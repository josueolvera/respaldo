package mx.bidg.config;

/**
 * @author Rafael Viveros
 * Created on 6/11/15.
 */
public class JsonViews {
    public static class Root {}
    public static class ConceptsEmbedded extends Root {} 
    public static class Embedded extends Root {} 
    public static class RootExtras extends Root {}
    public static class EmbeddedBudget extends Root{}
    public static class EmbeddedDwEnterprises{}
    public static class IdsEnterprises{}
    public static class EmbeddedAccounts extends Root {}
    public static class EmbeddedRequestCategory extends Root {}
}
