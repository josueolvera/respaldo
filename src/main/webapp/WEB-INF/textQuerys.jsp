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
                    applicationDate2: ''
                },
                methods: {
                    sqlQueriesAvailables: function () {
                        this.$http.get(ROOT_URL + "/sql-queries/querys").success(function (data) {
                            this.sqlQueries = data;
                        });
                    },
                    buildReport: function () {
                        if (this.name.length > 0) {
                            this.$http.get(ROOT_URL + "/sql-queries/" + this.idQuery).success(function (data) {
                                this.sqlQuery = data;
                                window.location = ROOT_URL + "/sql-queries/execute/"+this.idQuery+"?file_name="
                                        + this.name +"&startDate=" + this.startDate + "&endDate=" + this.endDate
                                        + "&applicationDate1=" + this.applicationDate1 + '&applicationDate2=' + this.applicationDate2;
                                $('#modalNombre').modal('hide');
                                this.name = '';
                            }).error(function () {
                                showAlert("Error al generar el archivo", {type: 3})
                            });
                        }
                        else {
                            showAlert("Ingresa un nombre al reporte", {type: 3})
                        }
                    },
                    nameReport: function () {
                        if (this.idQuery.length == 0) {
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
                        <h2>Generador de reportes</h2>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-4">
                        <label>Reporte</label>
                        <select class="form-control" name="" v-model="idQuery">
                            <option></option>
                            <option v-for="query in sqlQueries"
                                    value="{{query.idQuery}}">
                                {{query.queryName}}
                            </option>
                        </select>
                    </div>
                    <div class="col-xs-3">
                        <label>Fecha de aplicación inicial</label>
                        <div class="form-group">
                            <div class="input-group date" id="applicationDate">
                                <input type="text" class="form-control" v-model="startDate">
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
                        <button type="button" class="btn btn-success" data-placement="bottom" style="margin-top: 25px"
                                @click="nameReport">
                            Generar
                        </button>
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
                            <div class="col-xs-6">
                                <input class="form-control" name="name" v-model="name"
                                       onkeypress="return isLetterKey(event)">
                            </div>
                        </div>
                        <br>
                        <br>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" @click="buildReport">
                                Aceptar
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
