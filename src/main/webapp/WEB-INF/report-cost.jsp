<%--
  Created by IntelliJ IDEA.
  User: Kevin Salvador
  Date: 26/12/2016
  Time: 12:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>
<t:template pageTitle="BID Group: Generacion de reportes">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.getCostReport();
                },
                data: {
                    reports:{},
                    selected : {
                        report: null,
                        startDate: '',
                        endDate: '',
                        fileName: ''
                    },
                    dateTimePickerStart: '',
                    dateTimePickerEnd: '',
                    createReportUrl: '',
                    valueIdReport: '',
                    reportURL:''
                },
                methods : {
                    getCostReport: function () {
                        this.$http.get(ROOT_URL+'/sql-queries/report-cost').success(function (data) {
                            this.reports=data;
                        })
                    },
                    destroyDateTimePickerStart: function () {
                        this.activateDateTimePickerStart();
                        $("#startDate").on("dp.change", function (e) {
                            $('#endDate').data("DateTimePicker").minDate(e.date);
                        });
                        $("#endDate").on("dp.change", function (e) {
                            $('#startDate').data("DateTimePicker").maxDate(e.date);
                        });
                    },
                    activateDateTimePickerEnd: function (startDate) {

                        var minDate = moment(startDate, 'DD-MM-YYYY')
                                .format('YYYY-MM-DD');

                        var date = new Date();
                        var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();

                        this.dateTimePickerEnd = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'YYYY-MM-DD',
                            useCurrent: false,
                            minDate: minDate,
                            maxDate: currentDate
                        }).data();
                    },
                    destroyDateTimePickerStart: function () {
                        this.activateDateTimePickerStart();
                        $("#startDate").on("dp.change", function (e) {
                            $('#endDate').data("DateTimePicker").minDate(e.date);
                        });
                        $("#endDate").on("dp.change", function (e) {
                            $('#startDate').data("DateTimePicker").maxDate(e.date);
                        });
                    },
                    onExportButton: function () {
                        $("#exportModal").modal("show");
                    },
                    createReport: function () {
                        if (this.selected.reportFileName != '') {
                            $("#exportModal").modal("hide");
                            this.selected.typeQuery = this.selected.report.sqlQuery;
                            if(this.selected.report.idQuery!=null && this.selected.fileName!=null && this.selected.startDate!=null && this.selected.endDate!=null)
                            {
                                this.reportURL = ROOT_URL + '/sql-queries/execute-report/' + this.selected.report.idQuery + "?fileName=" + this.selected.fileName + "&startDate=" + this.selected.startDate + "&endDate=" + this.selected.endDate;
                                location.href = this.reportURL;
                                showAlert("Transaccion exitosa");
                                this.reportFileName = '';
                            }else {
                                showAlert("Ha ocurrido un problema al generar el reporte, intentelo nuevamente.", {type: 3});
                            }
                        } else {
                            showAlert("Debe asignar un nombre de archivo", {type: 3});
                            return;
                        }
                    },
                    activateDateTimePickerStart: function () {

                        var date = new Date();
                        var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();

                        this.dateTimePickerStart = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'YYYY-MM-DD',
                            useCurrent: false,
                            maxDate: currentDate
                        }).data();

                    }
                }
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div id="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-8">
                        <h2>Generador de reportes</h2>
                    </div>
                </div>
                <div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <label>Selecciona tipo de reporte</label>
                                <select class="form-control" v-model="selected.report">
                                    <option></option>
                                    <option v-for="report in reports" value="{{report}}" >
                                        {{report.queryName}}
                                    </option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-2">
                                <label>Fecha inicial</label>
                                <div class="form-group">
                                    <div class="input-group date" id="startDate">
                                        <input type="text" class="form-control" v-model="selected.startDate">
                                        <span class="input-group-addon" @click="destroyDateTimePickerStart">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <label>Fecha final</label>
                                <div class="form-group">
                                    <div class="input-group date" id="endDate">
                                        <input type="text" class="form-control" v-model="selected.endDate">
                                        <span class="input-group-addon"
                                              @click="activateDateTimePickerEnd(selected.startDate)">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <a style="margin-top: 25px" class="btn btn-success" @click="onExportButton">
                                    Exportar
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Modal Exportar -->
                <div class="modal fade" id="exportModal" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-sm" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">Exportar reporte</h4>
                            </div>
                            <div class="modal-body">
                                <br>
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label for="reportFileName">Asigne un nombre al archivo</label>
                                        <input type="text" id="reportFileName" class="form-control"
                                               placeholder="Nombre del Archivo" v-model="selected.fileName">
                                    </div>
                                </div>
                                <br>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Salir</button>
                                <button type="button" class="btn btn-success" @click="createReport">Aceptar</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </jsp:body>
</t:template>

