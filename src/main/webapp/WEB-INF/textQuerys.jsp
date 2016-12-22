<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Text Querys">

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
                    this.sqlQueriesAvailables();
                    this.reportsCalculated();
                },
                data: {
                    sqlQueries: [],
                    sqlQuery: {},
                    idQuery: '',
                    name: '',
                    dateTimePickerStartDate : null,
                    dateTimePickerEndDate : null,
                    startDate: '',
                    endDate: '',
                    applicationDate1: '',
                    applicationDate2: '',
                    sqlQuerys: null,
                    reportsGenerated: []
                },
                methods: {
                    sqlQueriesAvailables: function () {
                        this.sqlQueries = [];
                        this.$http.get(ROOT_URL + "/sql-queries/querys").success(function (data) {
                            this.sqlQueries = data;
                        });
                    },
                    buildReport: function () {
                        this.sqlQueries = [];
                        this.$http.get(ROOT_URL + "/sql-queries/" + this.sqlQuerys.idQuery).success(function (data) {
                            this.sqlQuery = data;
                            window.location = ROOT_URL + "/sql-queries/execute/"+this.sqlQuerys.idQuery+"?file_name="
                                    + this.sqlQuerys.queryName +"_"+ this.startDate +"_" + this.endDate +"&startDate=" + this.startDate + "&endDate=" + this.endDate
                                    + "&applicationDate1=" + this.applicationDate1 + '&applicationDate2=' + this.applicationDate2
                                    + "&file_name_nec="+ this.sqlQuerys.queryName +"_"+ this.startDate +"_" + this.endDate +"_NEC";
                            $('#modalNombre').modal('hide');
                            this.name = '';
                            setInterval(function () {
                                location.reload();
                            }, 40000);
                        }).error(function () {
                            showAlert("Error al generar el archivo", {type: 3})
                        });
                    },
                    nameReport: function () {
                        if (this.startDate == '' && this.endDate == '' && this.sqlQuerys.name.length > 0) {
                            showAlert("Selecciona un reporte", {type: 3})
                        } else {
                            $('#modalNombre').modal('show');
                            this.applicationDate1 = this.dateTimePickerStartDate.DateTimePicker.date().toISOString().slice(0, -1);
                            this.applicationDate2 = this.dateTimePickerEndDate.DateTimePicker.date().toISOString().slice(0, -1);
                        }
                    },
                    activateDateTimePickerApplicationDate: function () {

                        this.dateTimePickerStartDate = $('#applicationDate').datetimepicker({
                            locale: 'es',
                            format: 'YYYY-MM-DD',
                            useCurrent: false
                        }).data();
                    },
                    activateDateTimePickerEndApplicationDate: function (fechaInicial) {

                        this.dateTimePickerEndDate = $('#applicationDateEnd').datetimepicker({
                            locale: 'es',
                            format: 'YYYY-MM-DD',
                            useCurrent: false
//                            minDate: fechaInicial
                        }).data();
                    },
                    sendNotification: function (report) {
                        this.sqlQueries = [];
                        this.$http.post(ROOT_URL + "/sql-queries/notification?file_name="+report.fileName+"&file_name_nec="+report.fileName+"_NEC").success(function (data) {
                            showAlert("El cálculo se guardo y se envío las notificaciones correspondientes");
                            setInterval(function () {
                                location.reload();
                            }, 5000);
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type: 3});
                        });
                    },
                    deleteReports: function (report) {
                        this.sqlQueries = [];
                        this.$http.get(ROOT_URL + "/sql-queries/delete-reports?file_name="+report.fileName+"&file_name_nec="+report.fileName+"_NEC").success(function (data) {
                            this.reportsGenerated = data;
                            setInterval(function () {
                                location.reload();
                            }, 5000);
                            showAlert("El reporte se elimino correctamente");
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type:3});
                        })
                    },
                    reportsCalculated: function () {
                        this.reportsGenerated = [];
                        this.$http.get(ROOT_URL + "/calculation-reports/not-sended").success(function (data) {
                            this.reportsGenerated = data;
                            this.sqlQueriesAvailables();
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type:3});
                        })
                    }
                },
                filters: {}
            });


        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos">
            <br>
            <div>
                <div class="row">
                    <div class="col-xs-4"></div>
                    <div class="col-xs-7">
                        <h2>Reporte de nómina</h2>
                    </div>
                </div>
                <div class="loading" v-if="sqlQueries.length==0">
                </div>
                <br>
                <form id="attachments-form" method="post" enctype="multipart/form-data"
                      v-on:submit.prevent="nameReport">

                    <div class="row">
                        <div class="col-xs-4">
                            <label>Generar reporte</label>
                            <select class="form-control" name="" v-model="sqlQuerys" required>
                                <option></option>
                                <option v-for="query in sqlQueries"
                                        :value="query">
                                    {{query.queryName}}
                                </option>
                            </select>
                        </div>
                        <div class="col-xs-3">
                            <label>Fecha de aplicación inicial</label>
                            <div class="form-group">
                                <div class="input-group date" id="applicationDate">
                                    <input type="text" class="form-control" v-model="startDate" required>
                                    <span class="input-group-addon" @click="activateDateTimePickerApplicationDate()">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-3">
                            <label>Fecha de aplicación final</label>
                            <div class="form-group">
                                <div class="input-group date" id="applicationDateEnd">
                                    <input type="text" class="form-control" v-model="endDate" required>
                                    <span class="input-group-addon" @click="activateDateTimePickerEndApplicationDate(startDate)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-2">
                            <button type="submit" style="margin-top: 25px" data-placement="bottom"
                                    class="btn btn-success">
                                Guardar
                            </button>
                        </div>

                    </div>
                    </form>
                <br>
                <div class="row" style="margin-left: 300px" v-if="reportsGenerated.length>0">
                    <div class="col-xs-8">
                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading"><b><center>Reportes generados</center></b></div>
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>Nombre del reporte</th>
                                        <th>Enviar</th>
                                        <th>Cancelar</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="report in reportsGenerated">
                                        <td>
                                            {{report.fileName}}.xlsx
                                        </td>
                                        <td>
                                            <button class="btn btn-info" @click="sendNotification(report)"
                                                    data-toggle="tooltip" data-placement="top"
                                                    title="Enviar reporte">
                                                <span class="glyphicon glyphicon-send"></span>
                                            </button>
                                        </td>
                                        <td>
                                            <button class="btn btn-danger" @click="deleteReports(report)"
                                                    data-toggle="tooltip" data-placement="top"
                                                    title="Eliminar reporte">
                                                <span class="glyphicon glyphicon-trash"></span>
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- container-fluid -->
            </div>
            <div class="modal fade" id="modalNombre" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
                <div class="modal-dialog ">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">
                                Nombre del archivo
                            </h4>
                        </div>
                        <div class="modal-body">
                            <div class="col-xs-12">
                                <label>{{sqlQuerys.queryName}}_{{startDate}}_{{endDate}}</label>
                            </div>
                        </div>
                        <br>
                        <br>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="buildReport">
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
