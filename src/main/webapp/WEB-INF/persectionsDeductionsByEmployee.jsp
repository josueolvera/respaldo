<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Percecciones y Deducciones">

    <jsp:attribute name="styles">
        <style>
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
            }

            .table-body .table-row:nth-child(2n+1) {
                background: #ddd;
            }

            .table-row {
                padding: 1rem;
            }

            .flex-content {
                overflow-x: hidden;
            }
        </style>
    </jsp:attribute>

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
                    this.obtainEmployeesWithPD();
                },
                data: {
                    employeesPD: [],
                    pD: {
                        employeesSelected: []
                    },
                    search: '',
                    arregloPd: [],
                    application: '',
                    timePickerApplicationDate: '',
                    perceccionDeduccion:{},
                    reschedule:{
                        applicationDate:''
                    }
                },
                methods: {
                    obtainEmployeesWithPD: function () {
                        this.$http.get(ROOT_URL + "/perceptions-deductions-employee/actives").success(function (data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (pD) {
                                if (isNaN(pD.employee)) {
                                    jsonObjectIndex[pD.employee._id] = pD.employee;
                                } else {
                                    pD.employee = jsonObjectIndex[pD.employee];
                                }
                            });

                            data.forEach(function (pD) {
                                if (isNaN(pD.cPerceptionsDeductions)) {
                                    jsonObjectIndex[pD.cPerceptionsDeductions._id] = pD.cPerceptionsDeductions;
                                } else {
                                    pD.cPerceptionsDeductions = jsonObjectIndex[pD.cPerceptionsDeductions];
                                }
                            });
                            this.employeesPD = data;
                        }).error(function () {
                            showAlert("Error al obtener informaciòn", {type: 3});
                        });
                    },
                    deleteSelected: function () {
                        this.$http.post(ROOT_URL + "/perceptions-deductions-employee/delete-selected", JSON.stringify(this.pD)).success(function (data) {
                            this.arregloPd = data;
                            showAlert("Se eliminaron las percecciones y deducciones");
                            $("#modalEliminar").modal("hide");
                            this.obtainEmployeesWithPD();
                        }).error(function () {
                            showAlert("Error en la solicitud vuelva a intentarlo", {type: 3});
                        });
                    },
                    activarTimePickerApplicationDate: function () {
                        var fecha = new Date();
                        var fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();

                        this.timePickerApplicationDate = $('#dateApplication').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: fecha_actual
                        }).data();
                    },
                    getRescheduleData: function (pd) {
                        this.perceccionDeduccion = (JSON.parse(JSON.stringify(pd)));
                        $("#modalReprogramar").modal("show");
                    },
                    reschedulePD: function () {
                        if(this.application != ''){
                            this.reschedule.applicationDate = this.timePickerApplicationDate.DateTimePicker.date().toISOString().slice(0, -1);
                            this.$http.post(ROOT_URL + "/perceptions-deductions-employee/reschedule/" + this.perceccionDeduccion.idPerceptionDeduction, JSON.stringify(this.reschedule)).success(function (data) {
                                this.arregloPd = data;
                                showAlert("Reprogramacion exitosa");
                                $("#modalReprogramar").modal("hide");
                                this.obtainEmployeesWithPD();
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            });
                        }else {
                            showAlert("Debes ingresar la fecha de aplicaciòn", {type: 3});
                        }
                    }
                },
                filters: {}
            });


        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos" class="flex-box container-fluid">
            <br>
            <div>
                <div class="row">
                    <div class="col-xs-7 text-header">
                        <h2>Percecciones y deducciones</h2>
                    </div>

                    <div class="col-xs-5 text-right" style="padding: 0px">
                        <div class="col-xs-9 text-left" style="padding: 0px">
                            <label>Buscar por fecha de aplicaciòn</label>
                            <input class="form-control" v-model="search">
                        </div>
                        <div class="col-xs-3">
                            <button type="button" class="btn btn-danger" name="button"
                                    style="margin-top: 25px" data-toggle="modal" data-target="#modalEliminar">
                                Eliminar
                            </button>
                        </div>
                    </div>
                </div>

                <div class="loading" v-if="employeesPD.length==0">
                </div>

                <div>
                    <div class="row table-header">
                        <div class="col-xs-3"><b>Nombre</b></div>
                        <div class="col-xs-2"><b>RFC</b></div>
                        <div class="col-xs-2"><b>Percepcion/Deduccion</b></div>
                        <div class="col-xs-1"><b>Monto</b></div>
                        <div class="col-xs-2"><b>Fecha de aplicaciòn</b></div>
                        <div class="col-xs-1" style="margin-left: -30px"><b>Reprogramar</b></div>
                        <div class="col-xs-1" style="margin-left: 1px"><b>Eliminar</b></div>
                    </div>
                </div>
            </div>
            <br>
            <div class="table-body flex-row flex-content">
                <div class="row table-row"
                     v-for="pd in employeesPD | filterBy search in ''"
                     v-if="pd.status == true">
                    <div class="col-xs-3">{{pd.employee.fullName}}</div>
                    <div class="col-xs-2">{{pd.employee.rfc}}</div>
                    <div class="col-xs-2">{{pd.cPerceptionsDeductions.namePD}}</div>
                    <div class="col-xs-1">{{pd.amount}}</div>
                    <div class="col-xs-2">{{pd.aplicationDateFormats.dateNumber}}</div>
                    <div class="col-xs-1">
                        <button class="btn btn-default btn-sm"
                                                   data-toggle="tooltip" data-placement="top" title="Reprogramar"
                                                   @click="getRescheduleData(pd)">
                        <span class="glyphicon glyphicon-refresh"></span>
                        </button>
                    </div>
                    <div class="col-xs-1"><input type="checkbox" :value="pd" v-model="pD.employeesSelected"></div>
                </div>
            </div>


            <div class="modal fade" id="modalEliminar" tabindex="-1" role="dialog" aria-labelledby=""
                 aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Eliminacion de percecciones y deducciones</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <label>¿Estas seguro que deseas eliminar todas las percecciones y deducciones
                                        seleccionados?</label>
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-danger" @click="deleteSelected">Eliminar</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="modalReprogramar" tabindex="-1" role="dialog" aria-labelledby=""
                 aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Reprogramar</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-6">
                                    <label>Fecha para reprogramar</label>
                                    <div class='input-group date' id='dateApplication'>
                                        <input type='text' class="form-control" v-model="application">
                                        <span class="input-group-addon" @click="activarTimePickerApplicationDate()">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                    </div>
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-success" @click="reschedulePD">Reprogramar</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
