<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Managment Roster">

    <jsp:attribute name="scripts">
        <script type="text/javascript">
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>

        <script>
            function isLetterKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 32 ||
                        charCode === 13 ||
                        (charCode > 64 && charCode < 91) ||
                        (charCode > 96 && charCode < 123) ||
                        charCode === 8
                ) {
                    return true;
                }
                else {
                    return false;
                }
            }
            function isNumberKeyAndLetterKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 13 ||
                        (charCode > 64 && charCode < 91) ||
                        (charCode > 47 && charCode < 58) ||
                        (charCode > 96 && charCode < 123) ||
                        charCode === 8
                ) {
                    return true;
                }
                else {
                    return false;
                }

            }
        </script>

        <script type="text/javascript">
            var vm = new Vue({
                el: '#contenidos',
                created: function () {

                },
                ready: function () {
                    this.getAllReportsSendedAndNotAuthorized();
                    this.getUserInSession();
                },
                data: {
                    downloadUrl: ROOT_URL + '/calculation-reports/download/',
                    reportsNotAuthorizeds:[],
                    user: {},
                    reportReject:{
                        reportRole:{},
                        reason: ''
                    }
                },
                methods: {
                    getUserInSession: function () {
                        this.$http.get(ROOT_URL + "/user")
                                .success(function (data) {
                                    this.user = data;
                                })
                                .error(function (data) {
                                    showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                                });
                    },
                    getAllReportsSendedAndNotAuthorized: function () {
                        this.$http.get(ROOT_URL + "/calculation-reports/sended-not-authorized").success(function (data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (rA) {
                                if (isNaN(rA.calculationReport)) {
                                    jsonObjectIndex[rA.calculationReport._id] = rA.calculationReport;
                                } else {
                                    rA.calculationReport = jsonObjectIndex[rA.calculationReport];
                                }
                            });
                            data.forEach(function (rA) {
                                if (isNaN(rA.sqlQueries)) {
                                    jsonObjectIndex[rA.sqlQueries._id] = rA.sqlQueries;
                                } else {
                                    rA.sqlQueries = jsonObjectIndex[rA.sqlQueries];
                                }
                            });
                            data.forEach(function (rA) {
                                if (isNaN(rA.cRoles)) {
                                    jsonObjectIndex[rA.cRoles._id] = rA.cRoles;
                                } else {
                                    rA.cRoles = jsonObjectIndex[rA.cRoles];
                                }
                            });
                            this.reportsNotAuthorizeds = data;
                        }).error(function () {

                        })
                    },
                    rejectReportModal: function (report) {
                        this.reportReject.reason = '';
                        this.reportReject.reportRole = report;
                        $('#modalReject').modal('show');
                    },
                    reject: function () {

                        if(this.reportReject.reason.length > 0){
                            this.reportsNotAuthorizeds = [];

                            this.$http.post(ROOT_URL + "/authorization-reports/reject",JSON.stringify(this.reportReject)).success(function (data) {
                                this.getAllReportsSendedAndNotAuthorized();
                                $('#modalReject').modal('hide');
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            });
                        }else{
                            showAlert("Es necesario escribir el motivo del rechazo", {type: 3});
                        }
                    },
                    authorize: function (report) {

                        this.reportReject.reportRole = report;

                        this.$http.post(ROOT_URL + "/authorization-reports/authorize",JSON.stringify(this.reportReject)).success(function (data) {
                            this.reportsNotAuthorizeds = [];
                            this.getAllReportsSendedAndNotAuthorized();
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type: 3});
                        });
                    }
                },
                filters: {}
            });


        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            textarea{
                resize: none;
            }

        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos">

            <div class="row">
                <div class="col-xs-8">
                    <h2>Autorización de nóminas</h2>
                </div>
                <div class="col-xs-4">
                </div>
            </div>
            <br>
            <div class="row" v-if="reportsNotAuthorizeds.length>0">
                <div class="col-xs-12">
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading"><b><center>Reportes de nómina</center></b></div>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Nombre del archivo</th>
                                    <th>Descargar</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="report in reportsNotAuthorizeds" v-if="user.dwEmployee.role.idRole == report.cRoles.idRole || user.dwEmployee.role.idRole == 19 || user.dwEmployee.role.idRole == 17">
                                    <td>
                                        {{report.calculationReport.fileName}}.xlsx
                                    </td>
                                    <td>
                                        <a style="margin-left: 10px"  class="btn btn-default" :href="downloadUrl + report.calculationReport.idCalculationReport"
                                           data-toggle="tooltip" data-placement="top" title="Descargar">
                                            <span class="glyphicon glyphicon-cloud-download"></span>
                                        </a>
                                    </td>
                                    <td>
                                        <button v-if="report.authorization == 0" class="btn btn-success" @click="authorize(report)"
                                                data-toggle="tooltip" data-placement="top"
                                                title="Autorizar reporte">
                                            Autorizar
                                        </button>
                                        <label v-if="report.authorization == 1">
                                            Fecha de autorización: {{report.authorizationDateFormats.dateTextLong}}
                                            Hora de autorizaión: {{report.authorizationDateFormats.time12}}
                                        </label>
                                    </td>
                                    <td>
                                        <button v-if="report.authorization == 0" class="btn btn-danger" @click="rejectReportModal(report)">
                                            Rechazar
                                        </button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="modalReject" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
                <div class="modal-dialog ">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">
                                Mótivo del rechazo
                            </h4>
                        </div>
                        <div class="modal-body">
                            <div class="col-xs-12">
                               <textarea v-model="reportReject.reason" cols="500" rows="3"
                                         class="form-control"></textarea>
                            </div>
                        </div>
                        <br>
                        <br>
                        <br>
                        <br>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="reject()">
                                Aceptar
                            </button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
