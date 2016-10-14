<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Gestor de Bonos">

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
            function isNumberKeyAndLetterKey(evt){
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 13 ||
                        (charCode > 64 && charCode < 91) ||
                        (charCode > 47 && charCode < 58) ||
                        (charCode > 96 && charCode < 123) ||
                        charCode === 8
                )  {
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
                    this.obtainEmployeesBonds();
                },
                data: {
                    employeesBonus: [],
                    search: '',
                    modalEliminar:{
                        employeeBonus:{}
                    }
                },
                methods: {
                    obtainEmployeesBonds: function () {
                        this.$http.get(ROOT_URL + "/employee-bonus").success(function (data) {

                            var jsonObjectIndex = {};
                            data.forEach(function (employeeBonus) {
                                if (isNaN(employeeBonus.cCommissionBonus)) {
                                    jsonObjectIndex[employeeBonus.cCommissionBonus._id] = employeeBonus.cCommissionBonus;
                                } else {
                                    employeeBonus.cCommissionBonus = jsonObjectIndex[employeeBonus.cCommissionBonus];
                                }
                            });
                            this.employeesBonus = data;
                        });
                    },
                    question: function (employeeBonus) {
                        this.modalEliminar.employeeBonus = employeeBonus;
                        $('#modalEliminar').modal('show');
                    },
                    deleteEmployeeBonus: function () {
                        this.$http.post(ROOT_URL + "/employee-bonus/delete/" + this.modalEliminar.employeeBonus.idBonusCommissionableEmployee)
                                .success(function (data) {
                                    this.employeesBonus = [];
                                    this.obtainEmployeesBonds();
                                    $('#modalEliminar').modal('hide');
                                    showAlert("El bono se ha eliminado");
                                }).error(function () {
                            showAlert("Error al generar la solicitud", {type: 3});
                        });
                    }
                },
                filters: {

                }
            });


        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos" class="flex-box container-fluid">
            <br>
            <div>
                <div class="row">
                    <div class="col-xs-6 text-header">
                        <h2>Gestor de Bonos</h2>
                    </div>

                    <div class="col-xs-6 text-right" style="padding: 0px">
                        <div class="col-xs-5">
                        </div>
                        <div class="col-xs-7 text-left" style="padding: 0px">
                            <label>Buscar por Empleado</label>
                            <input class="form-control" v-model="search">
                        </div>
                    </div>
                </div>

                <div v-if="!employeesBonus.length > 0"
                     style="height: 6rem; padding: 2rem 0;">
                    <div class="loader">Cargando...</div>
                </div>


                <div  v-if="employeesBonus.length > 0">
                    <div class="row table-header">
                        <div class="col-xs-4"><b>Nombre de empleado</b></div>
                        <div class="col-xs-2"><b>RFC</b></div>
                        <div class="col-xs-2"><b>Bono</b></div>
                        <div class="col-xs-3"><b>Tipo de bono</b></div>
                        <div class="col-xs-1"><b>Eliminar</b></div>
                    </div>
                </div>
            </div>
            <br>
            <div class="table-body flex-row flex-content">
                <div class="row table-row" v-for="employeeBonus in employeesBonus | filterBy search in 'employees'">
                    <div class="col-xs-4">{{employeeBonus.employees.fullName}}</div>
                    <div class="col-xs-2">{{employeeBonus.employees.rfc}}</div>
                    <div class="col-xs-2">{{employeeBonus.bonusAmount}}</div>
                    <div class="col-xs-3">{{employeeBonus.cCommissionBonus.typeBonus}}</div>
                    <div class="col-xs-1">
                        <button type="button" class="btn btn-danger" name="button" data-toggle="tooltip"
                                                  data-placement="bottom" title="Eliminar"
                                                  @click="question(employeeBonus)"><span class="glyphicon glyphicon-trash"></span></button>
                    </div>
                </div>
            </div>
            <!-- container-fluid -->
            <div class="modal fade" id="modalEliminar" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
                <div class="modal-dialog ">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">
                                Eliminar bono
                            </h4>
                        </div>
                        <div class="modal-body">
                            <p>El <label>{{modalEliminar.employeeBonus.cCommissionBonus.typeBonus}}</label> del
                                empleado <label>{{modalEliminar.employeeBonus.employees.fullName}}</label>
                                sera eliminado</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button id="btnFlag" type="button" class="btn btn-danger" @click="deleteEmployeeBonus">
                                Aceptar
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
