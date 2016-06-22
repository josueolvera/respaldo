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
                    this.obtainAgreementsDistributor();
                    this.obtainDistributors();
                    this.fetchHierarchy();
                    this.obtainAgreements();
                },
                data: {
                    dwEnterpriseAgreements:{
                        idDistributor:"",
                        agreementName:"",
                        idRegion:"",
                        idBranch:"",
                        dwEnterprises:[],
                    },
                    enterpriseAgreements: [],
                    distributors: [],
                    regions: [],
                    dwEnterprise:{
                        idBranch:'',
                        idRegion:'',
                        idDistributor:'',
                    },
                    selectOptions: {
                        distributors: [],
                        areas: [],
                        hierarchy: [],
                    },
                    defaultDistributor: {
                        id:0,
                        name:''
                    },
                    selectedOptions: {
                        distributor: {
                            id:0
                        },
                        region: {
                            id:0
                        },
                        branch: {
                            id:0
                        }
                    },
                    defaultRegion: {
                        id:0,
                        name:''
                    },
                    defaultBranch: {
                        id:0,
                        name:''
                    },
                    agreements:[],
                    modalEliminar: {
                        agreement: {
                            agreementName: "",
                        },
                    },
                },
                methods: {
                    obtainAgreementsDistributor: function () {
                        this.$http.get(ROOT_URL + "/enterprises-agreements" ).success(function (data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (agreements) {
                                if (isNaN(agreements.dwEnterprise)) {
                                    jsonObjectIndex[agreements.dwEnterprise._id] = agreements.dwEnterprise;
                                } else {
                                    agreements.dwEnterprise = jsonObjectIndex[agreements.dwEnterprise];
                                }
                            });
                            this.enterpriseAgreements = data;
                        });
                    },
                    obtainDistributors: function () {
                      this.$http.get(ROOT_URL + "/distributors/agreement").success(function (data) {
                         this.distributors = data;
                      });
                    },
                    obtainRegions: function () {
                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor/" + this.dwEnterprise.idDistributor).success(function (data) {
                           this.regions = data;
                        });
                    },
                    fetchHierarchy: function () {
                        this.$http.get(ROOT_URL + "/dw-enterprises/hierarchy-agreements").success(function (data) {
                            this.selectOptions.hierarchy = data;
                        });
                    },
                    selectedOptionsDistributorChanged: function () {
                        this.selectedOptions.region = this.defaultRegion;
                        this.selectedOptions.branch = this.defaultBranch;
                    },
                    selectedOptionsRegionChanged: function () {
                        this.selectedOptions.branch = this.defaultBranch;
                        this.selectedOptions.area = this.defaultArea;
                    },
                    selectedOptionsBranchChanged: function () {
                        this.selectedOptions.area = this.defaultArea;
                    },
                    obtainAgreements: function () {
                      this.$http.get(ROOT_URL + "/agreements").success(function (data) {
                         this.agreements = data;
                      });
                    },
                    saveAgreement: function () {
                        this.$http.post(ROOT_URL + "/agreements/new", JSON.stringify(this.dwEnterpriseAgreements)).success(function (data) {
                            showAlert("Registro de convenio exitoso");
                            $('#modalAlta').modal('hide');
                            this.dwEnterpriseAgreements.agreementName = "";
                            this.dwEnterpriseAgreements.idDistributor = "";
                            this.obtainAgreements();
                        }).error(function () {
                            showAlert("El nombre de convenio ya existe", {type: 3});
                        });
                    },
                    cancelar: function () {
                      this.dwEnterpriseAgreements.agreementName = "";
                      this.dwEnterpriseAgreements.idDistributor = "";
                      $('#modalAlta').modal('hide');
                    },
                    question: function (agreement) {
                        this.modalEliminar.agreement = agreement;
                        $('#modalEliminar').modal('show');
                    },
                    deleteAgreement: function () {
                        this.$http.post(ROOT_URL + "/agreements/low-date/" + this.modalEliminar.agreement.idAgreement)
                                .success(function (data) {
                                    this.obtainAgreements();
                                    $('#modalEliminar').modal('hide');
                                    showAlert("Convenio eliminado");
                                });
                    },
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
                    <div class="col-xs-8 text-header">
                        <h2>Convenios</h2>
                    </div>

                    <div class="col-xs-4 text-right" style="padding: 0px">
                        <div class="col-xs-7 text-left" style="padding: 0px">
                            <label>Buscar Convenio</label>
                            <input class="form-control" maxlength="13" v-model="search">
                        </div>


                        <button type="button" class="btn btn-default" name="button"
                                style="margin-top: 25px" data-toggle="modal" data-target="#modalAlta">
                            Nuevo convenio
                        </button>
                    </div>
                </div>


                <div>
                    <div class="row table-header">
                        <div class="col-xs-11"><b>Convenio</b></div>
                        <div class="col-xs-1"><b>Eliminar</b></div>
                    </div>
                </div>
            </div>
            <br>
            <div class="table-body flex-row flex-content">
                <div class="row table-row" v-for="agreement in agreements | filterBy search in 'agreementName'" v-if="agreement.lowDate == null">
                    <div class="col-xs-11">{{agreement.agreementName}}</div>
                    <div class="col-xs-1">
                        <button type="button" class="btn btn-danger" name="button" data-toggle="tooltip"
                                data-placement="bottom" title="Eliminar"
                                @click="question(agreement)"><span class="glyphicon glyphicon-trash"></span></button>
                    </div>
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
                                    <input class="form-control" name="name" v-model="dwEnterpriseAgreements.agreementName">
                                </div>
                            </div>
                            </div>
                        <div class="modal-footer">

                            <button type="button" class="btn btn-success" @click="saveAgreement">
                                Guardar
                            </button>

                            <button type="button" class="btn btn-default" @click="cancelar">Cancelar</button>
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
                                Eliminar Convenio
                            </h4>
                        </div>
                        <div class="modal-body">
                            <p>El convenio {{modalEliminar.agreement.agreementName}} será
                                eliminado</p>
                        </div>
                        <div class="modal-footer">
                            <button id="btnFlag" type="button" class="btn btn-default" @click="deleteAgreement">
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
