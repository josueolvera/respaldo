<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Convenios">

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
                },
                data: {
                    groupsAgreements: [],
                    agreementsGroups: {
                        agreementName: "",
                        idAg: ""
                    },
                    modalEliminar: {
                        group: {
                            agreementGroupName: ""
                        }
                    },
                    groupAgreement: {},
                    name: '',
                    groupSelected: {},
                    groups: [],
                    arrayAgreement: {
                        agreements: []
                    },
                    idAg: '',
                    group: {
                        agreementGroupName: ''
                    }
                },
                methods: {
                    obtainGroups: function () {
                        this.groups = [];
                        this.$http.get(ROOT_URL + "/c-agreements-groups").success(function (data) {
                            this.groups = data;
                        });
                    },
                    changedGroup: function (group) {
                        this.idAg = group.idAg;
                        this.$http.get(ROOT_URL + "/groups-agreements/groups/"+group.idAg).success(function (data) {
                            this.groupsAgreements = data;
                        });
                    },
                    assignAgreementsToGroup: function (groupAgreement) {
                        this.$http.post(ROOT_URL + "/groups-agreements/has-agreement", JSON.stringify(groupAgreement)).success(function (data) {
                            showAlert("Asignaciòn satisfactorìa");
                        }).error(function () {
                           showAlert("Hubo un error en la solicitud", {type: 3});
                        });
                    },
                    cancelar: function () {
                        this.group.agreementGroupName = '';
                        $('#modalAlta').modal('hide');
                    },
                    saveAgreementGroup: function () {
                        if(this.group.agreementGroupName == ''){
                            showAlert("Debes ingresar un nombre al grupo", {type: 3});
                        }else {
                            this.$http.post(ROOT_URL + "/c-agreements-groups/new", JSON.stringify(this.group)).success(function (data) {
                                showAlert("Grupo dado de alta correctamente");
                                $('#modalAlta').modal('hide');
                                this.obtainGroups();
                            }).error(function () {
                                showAlert("Error en la solicitud", {type: 3});
                            });
                        }
                    },
                    question: function (group) {
                        this.modalEliminar.group = group;
                        $('#modalEliminar').modal('show');
                    },
                    deleteGroup: function () {
                        this.$http.post(ROOT_URL + "/c-agreements-groups/low-group/" + this.modalEliminar.group.idAg)
                                .success(function (data) {
                                    this.obtainGroups();
                                    $('#modalEliminar').modal('hide');
                                    showAlert("Grupo eliminado");
                                }).error(function (data) {
                           showAlert("Error en la solicitud",{type: 3});
                        });
                    }
                },
                filters: {

                }
            });


        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
            }

            .table-body .table-row:nth-child(2n+1) {
                background: white;
            }

            .table-row {
                padding: 1rem;
            }

            .flex-content {
                overflow-x: hidden;
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos" class="flex-box container-fluid">
            <br>
            <div>
                <div class="row">
                    <div class="col-xs-6 text-header">
                        <h2>Grupo de convenios</h2>
                    </div>
                    <div class="col-xs-4">
                        
                    </div>
                    <div class="col-xs-2">
                        <button type="button" class="btn btn-default" name="button"
                                style="margin-top: 25px" data-toggle="modal" data-target="#modalAlta">
                            Nuevo grupo
                        </button>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-xs-6">
                    <div style="background: #ddd" class="panel panel-default">
                        <!-- Default panel contents -->
                        <!-- Table -->
                        <div class="flex-box container-fluid">
                            <div class="row table-header active">
                                <div class="col-xs-12"><b>Selecciona un grupo</b></div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row" v-for="group in groups" v-if="group.status == 1">
                                    <div class="col-xs-10">
                                        <input type="radio" :value="group" v-model="groupSelected" @change="changedGroup(groupSelected)">
                                        <label>{{group.agreementGroupName}}</label>
                                    </div>
                                    <div class="col-xs-2">
                                        <button type="button" class="btn btn-danger" name="button" data-toggle="tooltip"
                                                data-placement="bottom" title="Eliminar"
                                                @click="question(group)"><span class="glyphicon glyphicon-trash"></span></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6" v-if="groupsAgreements.length > 0">
                    <div style="background: #ddd" class="panel panel-default">
                        <!-- Default panel contents -->
                        <!-- Table -->
                        <div class="flex-box container-fluid">
                            <div class="row table-header active">
                                <div class="col-xs-12"><b>Selecciona el o los convenios</b></div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row" v-for="agreement in groupsAgreements">
                                    <div class="col-xs-12">
                                        <input type="checkbox" :value="agreement" v-model="agreement.hasAgreement" @change="assignAgreementsToGroup(agreement)">
                                        <label>{{agreement.agreement.idAgreement}} {{agreement.agreement.agreementName}}</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- container-fluid -->
            <div class="modal fade" id="modalAlta" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Alta de grupo</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row" >
                                <div class="col-xs-5">
                                    <label>Nombre del grupo</label>
                                    <input class="form-control" name="name" v-model="group.agreementGroupName">
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" @click="cancelar">Cancelar</button>
                            <button type="button" class="btn btn-success" @click="saveAgreementGroup">
                                Guardar
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
                                Confirmaciòn de baja de grupo
                            </h4>
                        </div>
                        <div class="modal-body">
                            <p>El grupo <label>{{modalEliminar.group.agreementGroupName}}</label> será
                                dado de baja</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button id="btnFlag" type="button" class="btn btn-danger" @click="deleteGroup">
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
