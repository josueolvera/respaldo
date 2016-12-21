package mx.bidg.pojos;

import mx.bidg.model.AuthorizationReports;
import mx.bidg.model.CalculationReport;

/**
 * Created by Desarrollador on 16/12/2016.
 */
public class RejectReportPojo {
    private CalculationReport calculationReport;
    private String rejectReason;
    private AuthorizationReports authorizationReports;

    public RejectReportPojo() {
    }

    public RejectReportPojo(CalculationReport calculationReport, String rejectReason, AuthorizationReports authorizationReports) {
        this.calculationReport = calculationReport;
        this.rejectReason = rejectReason;
        this.authorizationReports = authorizationReports;
    }

    public CalculationReport getCalculationReport() {
        return calculationReport;
    }

    public void setCalculationReport(CalculationReport calculationReport) {
        this.calculationReport = calculationReport;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public AuthorizationReports getAuthorizationReports() {
        return authorizationReports;
    }

    public void setAuthorizationReports(AuthorizationReports authorizationReports) {
        this.authorizationReports = authorizationReports;
    }
}
