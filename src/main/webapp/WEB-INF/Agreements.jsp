<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Convenios">

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
                    this.obtainGroups();
                    this.obtainAgreements();
                },
                data: {
                    agreements: [],
                    groups: [],
                    agreementsObject: {
                        agreementName: ""
                    },
                    modalEliminar: {
                        agreement: {
                            agreementName: ""
                        }
                    },
                    groupAgreement: {},
                    name: ''
                },
                methods: {
                    obtainAgreements: function () {
                        this.$http.get(ROOT_URL + "/agreements").success(function (data) {
                            this.agreements = data;
                        });
                    },
                    obtainGroups: function () {
                        this.groups = [];
                        this.$http.get(ROOT_URL + "/c-agreements-groups").success(function (data) {
                            this.groups = data;
                        });
                    },
                    saveAgreement: function () {
                        if(this.agreementsObject.agreementName.length == 0){
                            showAlert("Es necesario llenar el campo: Nombre del convenio", {type: 3});
                        } else {
                            this.$http.post(ROOT_URL + "/agreements/new", JSON.stringify(this.agreementsObject)).success(function (data) {
                                showAlert("Registro de convenio exitoso");
                                $('#modalAlta').modal('hide');
                                this.agreementsObject.agreementName = '';
                                this.obtainAgreements();

                            }).error(function () {
                                showAlert("Hubo un error al generar la solicitud intente de nuevo", {type: 3});
                            });
                        }
                    },
                    cancelar: function () {
                        this.agreementsObject.agreementName = '';
                      $('#modalAlta').modal('hide');
                    },
                    question: function (agreement) {
                        this.modalEliminar.agreement = agreement;
                        $('#modalEliminar').modal('show');
                    },
                    deleteAgreement: function () {
                        this.$http.post(ROOT_URL + "/agreements/low-date/" + this.modalEliminar.agreement.idAgreement)
                                .success(function (data) {
                                    this.obtainAgreementsGroups();
                                    $('#modalEliminar').modal('hide');
                                    showAlert("Convenio eliminado");
                                });
                    },
                    modifyAgreements: function (idGa) {
                        this.$http.get(ROOT_URL + "/groups-agreements/" + idGa).success(function (data) {
                            this.groupAgreement = data;
                            this.obtainGroups();
                            $("#modalReasignar").modal("show");
                        });
                    },
                    reasignAgreement: function () {
                        this.$http.post(ROOT_URL + "/groups-agreements/re-asign/" + this.groupAgreement.idGa, JSON.stringify(this.groupAgreement)).success(function (data) {
                            showAlert("Convenio reasignado con exito");
                            $('#modalReasignar').modal('hide');
                            this.groupAgreement = {};
                            this.obtainAgreementsGroups();
                        }).error(function (data) {
                           showAlert("Error al reasignar el convenio", {type: 3});
                        });
                    },
                    buildReport: function () {
                        if(this.name.length > 0) {
                            window.location = ROOT_URL + "/agreements/report-agreements?file_name=" + this.name;
                            $('#modalNombre').modal('hide');
                            this.name = '';
                        } else {
                            showAlert("Ingresa un nombre al reporte", {type: 3})
                        }
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
                        <h2>Registro de convenios</h2>
                    </div>

                    <div class="col-xs-6 text-right" style="padding: 0px">
                        <div class="col-xs-7 text-left" style="padding: 0px">
                            <label>Buscar Convenio</label>
                            <input class="form-control" v-model="search">
                        </div>
                        <div class="col-xs-3">
                            <button type="button" class="btn btn-default" name="button"
                                    style="margin-top: 25px" data-toggle="modal" data-target="#modalAlta">
                                Nuevo convenio
                            </button>
                        </div>
                        <div class="col-xs-2">
                            <button type="button" class="btn btn-success" name="button"
                                    style="margin-top: 25px" data-toggle="modal" data-target="#modalNombre">
                                Exportar
                            </button>
                        </div>
                    </div>
                </div>


                <div>
                    <div class="row table-header">
                        <div class="col-xs-1"><b>Id</b></div>
                        <div class="col-xs-5"><b>Convenio</b></div>
                    </div>
                </div>
            </div>
            <br>
            <div class="table-body flex-row flex-content">
                <div class="row table-row" v-for="agreement in agreements | filterBy search in 'agreementName'" v-if="agreement.status == 1">
                    <div class="col-xs-1">{{agreement.idAgreement}}</div>
                    <div class="col-xs-5">{{agreement.agreementName}}</div>
                </div>
            </div>
            <!-- container-fluid -->
            <div class="modal fade" id="modalAlta" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Registro de convenio</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row" >
                                <div class="col-xs-5">
                                    <label>Nombre del convenio</label>
                                    <input class="form-control" name="name" v-model="agreementsObject.agreementName">
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" @click="cancelar">Cancelar</button>
                            <button type="button" class="btn btn-success" @click="saveAgreement">
                                Guardar
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- container-fluid -->
            <div class="modal fade" id="modalReasignar" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Reasignar convenio</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row" >
                                <div class="col-xs-5">
                                    <label>Nombre del convenio</label>
                                    <input class="form-control" name="name" v-model="groupAgreement.agreement.agreementName" disabled>
                                </div>
                                <div class="col-xs-3">
                                    <label>Grupo del convenio</label>
                                    <select class="form-control" name="" v-model="groupAgreement.agreementGroup.idAg">
                                        <option></option>
                                        <option v-for="group in groups" :value="group.idAg">
                                            {{group.agreementGroupName}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-success" @click="reasignAgreement">
                                Asignar
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="modalEliminar" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
                <div class="modal-dialog ">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">
                                Confirmaciòn de baja de convenio
                            </h4>
                        </div>
                        <div class="modal-body">
                            <p>El convenio <label>{{modalEliminar.agreement.agreementName}}</label> será
                                dado de baja</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button id="btnFlag" type="button" class="btn btn-danger" @click="deleteAgreement">
                                Aceptar
                            </button>
                        </div>
                    </div>
                </div>
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
                                <input class="form-control" name="name" v-model="name">
                            </div>
                        </div>
                        <br>
                        <br>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-success" @click="buildReport">
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
