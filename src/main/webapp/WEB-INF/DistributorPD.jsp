<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Distributor incidencias">

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
                    this.obtainDistributors();
                },
                data: {
                    distributorsPD: [],
                    distributorSelected: {},
                    distributors: [],
                    allCheck: false,
                    pD:{
                        namePD: '',
                        idOperation: ''
                    },
                    pDs: []
                },
                methods: {
                    obtainDistributors: function () {
                        this.distributors = [];
                        this.$http.get(ROOT_URL + "/distributors").success(function (data) {
                            this.distributors = data;
                        });
                    },
                    changedDistributors: function (distributor) {
                        this.allCheck = false;
                        this.$http.get(ROOT_URL + "/distributor-pd/distributor-selected/"+distributor.idDistributor).success(function (data) {
                            this.distributorsPD = data;
                        });
                    },
                    assignPDToDistributor: function (pd) {
                        this.$http.post(ROOT_URL + "/distributor-pd/change-status", JSON.stringify(pd)).success(function (data) {
                        }).error(function () {
                            showAlert("Hubo un error en la solicitud", {type: 3});
                        });
                    },
                    all: function () {
                        var self = this;
                        if (this.allCheck == true){
                            this.distributorsPD.forEach(function (pd) {
                                pd.hasPd = true;
                                self.assignPDToDistributor(pd);
                            })
                        }else{
                            this.distributorsPD.forEach(function (pd) {
                                pd.hasPd = false;
                                self.assignPDToDistributor(pd);
                            })
                        }
                    },
                    savePD: function () {
                        if(this.pD.namePD != "" && this.pD.idOperation != null){
                            this.$http.post(ROOT_URL+ "/perceptions-deductions/save", JSON.stringify(this.pD)).success(function (data) {
                                this.pDs = data;
                                this.obtainDistributors();
                                showAlert("Percecciòn y deducciòn dada de alta correctamente");
                                this.cancelar();
                            })
                        }else {
                            showAlert("Debes llenar los campos nombre de la perceccion o deduccion y tipo de operaciòn", {type: 3});
                        }
                    },
                    cancelar: function () {
                        this.pD.namePD = '';
                        this.pD.idOperation = "";
                        $("#modalAlta").modal("hide");
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
                    <div class="col-xs-10 text-header">
                        <h2>Gestiòn de compensaciones</h2>
                    </div>
                    <div class="col-xs-2">
                        <button type="button" class="btn btn-default" name="button"
                                style="margin-left: 70px; margin-top: 25px" data-toggle="modal" data-target="#modalAlta">
                            Nueva compensacion
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
                                <div class="col-xs-12" style="font-size: 18px"><b>Distribuidor/Empresa</b></div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row" v-for="distributor in distributors">
                                    <div class="col-xs-10">
                                        <input type="radio" :value="distributor" v-model="distributorSelected" @change="changedDistributors(distributorSelected)">
                                        <label>{{distributor.distributorName}}</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6" v-if="distributorsPD.length > 0">
                    <div style="background: #ddd" class="panel panel-default">
                        <!-- Default panel contents -->
                        <!-- Table -->
                        <div class="flex-box container-fluid">
                            <div class="row table-header active">
                                <div class="col-xs-12" style="font-size: 18px"><b>Selecciona las compensaciones</b></div>
                                <div class="col-xs-12" style="font-size: 14px">
                                    <input type="checkbox" v-model="allCheck" @change="all()">
                                    <label>Seleccionar todos</label>
                                </div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row" v-for="pd in distributorsPD">
                                    <div class="col-xs-12">
                                        <input type="checkbox" :value="pd" v-model="pd.hasPd" @change="assignPDToDistributor(pd)">
                                        <label>{{pd.cPerceptionsDeductions.namePD}}</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="modalAlta" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Registro de compensacion</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row" >
                                <div class="col-xs-6">
                                    <label>Nombre de la compensacion</label>
                                    <input class="form-control" name="name" v-model="pD.namePD" onkeypress="return isLetterKey(event)">
                                </div>
                                <div class="col-xs-2"></div>
                                <div class="col-xs-4">
                                    <label>Tipo de operaciòn:</label>
                                    <select class="form-control" v-model="pD.idOperation">
                                        <option value="1">
                                            DEDUCCIÓN
                                        </option>
                                        <option value="2">
                                            PERCEPCIÓN
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" @click="cancelar">Cancelar</button>
                            <button type="button" class="btn btn-success" @click="savePD">
                                Guardar
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
