<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Liberación de calculo de comisiones">

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
                    this.activateDateTimePickerStartDate();

                },
                ready: function () {
                    this.getAllSaleStatus();
                },
                data: {
                    saleStatus: [],
                    selectAll: {
                        selectAllStatus: 'Seleccionar todos'
                    },
                    selectValues:{
                        selectedStatus:[],
                        startDate: '',
                        endDate: ''
                    },
                    status: '',
                    dateTimePickerStartDate: '',
                    sales: [],
                    total: 0.00,
                    totalTruncate: 0.00,
                    commissionReports: [],
                    reportSelected: {},
                    search: ''
                },
                methods: {
                    getAllSaleStatus: function () {
                        this.$http.get(ROOT_URL + "/sap-sale/get-status").success(function (data) {
                            this.saleStatus = data;
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type: 3});
                        });
                    },
                    selectStatus: function (status) {
                        var self = this;

                        var cadena = status.localeCompare(this.selectAll.selectAllStatus);

                        if (cadena == 0){
                            this.saleStatus.forEach(function (sStatus) {
                                var aux = self.selectValues.selectedStatus.includes(sStatus);

                                if (aux == false) {
                                    self.selectValues.selectedStatus.push(sStatus);
                                }

                            });
                            this.saleStatus = [];
                        }else{
                            var aux = this.selectValues.selectedStatus.includes(status);

                            if (aux == false) {
                                self.selectValues.selectedStatus.push(status);
                                self.saleStatus.$remove(status);
                            }
                        }
                    },
                    activateDateTimePickerStartDate: function () {
                        this.dateTimePickerStartDate = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false
                        }).data();
                    },
                    activateDateTimePickerEndDate: function (fechaInicial) {

                        var fecha = moment(fechaInicial, 'DD-MM-YYYY').format('YYYY-MM-DD');

                        this.dateTimePickerEndDate = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: fecha
                        }).data();
                    },
                    getSales: function () {
                        this.$http.post(ROOT_URL + "/sap-sale/get-sales", JSON.stringify(this.selectValues)).success(function (data) {

                            var jsonObjectIndex = {};
                            data.forEach(function (sapSale) {
                                if (isNaN(sapSale.agreement)) {
                                    jsonObjectIndex[sapSale.agreement._id] = sapSale.agreement;
                                } else {
                                    sapSale.agreement = jsonObjectIndex[sapSale.agreement];
                                }
                            });

                            data.forEach(function (sapSale) {
                                if (isNaN(sapSale.branch)) {
                                    jsonObjectIndex[sapSale.branch._id] = sapSale.branch;
                                } else {
                                    sapSale.branch = jsonObjectIndex[sapSale.branch];
                                }
                            });

                            data.forEach(function (sapSale) {
                                if (isNaN(sapSale.distributor)) {
                                    jsonObjectIndex[sapSale.distributor._id] = sapSale.distributor;
                                } else {
                                    sapSale.distributor = jsonObjectIndex[sapSale.distributor];
                                }
                            });

                            data.forEach(function (sapSale) {
                                if (isNaN(sapSale.dwEnterprise)) {
                                    jsonObjectIndex[sapSale.dwEnterprise._id] = sapSale.dwEnterprise;
                                } else {
                                    sapSale.dwEnterprise = jsonObjectIndex[sapSale.dwEnterprise];
                                }
                            });

                            data.forEach(function (sapSale) {
                                if (isNaN(sapSale.employee)) {
                                    jsonObjectIndex[sapSale.employee._id] = sapSale.employee;
                                } else {
                                    sapSale.employee = jsonObjectIndex[sapSale.employee];
                                }
                            });

                            data.forEach(function (sapSale) {
                                if (isNaN(sapSale.region)) {
                                    jsonObjectIndex[sapSale.region._id] = sapSale.region;
                                } else {
                                    sapSale.region = jsonObjectIndex[sapSale.region];
                                }
                            });

                            data.forEach(function (sapSale) {
                                if (isNaN(sapSale.role)) {
                                    jsonObjectIndex[sapSale.role._id] = sapSale.role;
                                } else {
                                    sapSale.role = jsonObjectIndex[sapSale.role];
                                }
                            });

                            data.forEach(function (sapSale) {
                                if (isNaN(sapSale.zona)) {
                                    jsonObjectIndex[sapSale.zona._id] = sapSale.zona;
                                } else {
                                    sapSale.zona = jsonObjectIndex[sapSale.zona];
                                }
                            });


                            this.sales = data;


                            this.sumSale();
                            this.totalTruncate = this.total.toFixed(2);
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type: 3});
                        });
                    },
                    sumSale: function () {
                        var self = this;
                        this.sales.forEach(function (element) {
                            self.total += element.comissionableAmount;
                        })
                    },
                    openModalAuthorizeModuleCommission: function () {
                        this.obtainDateType();
                        $("#authorizationCommission").modal("show");
                    },
                    changeClaveSap: function (sale) {
                        this.$http.post(ROOT_URL + "/sap-sale/assign-employee", JSON.stringify(sale)).success(function (data) {
                            this.sales = [];
                            this.getSales();
                        }).error(function () {
                           showAlert("Error al generar la solicitud", {type: 3});
                        });
                    },
                    authorize: function () {
                        if(this.reportSelected.idDateCalculation > 0){
                            this.$http.post(ROOT_URL + "/date-calculation/auhtorized-commission", JSON.stringify(this.reportSelected)).success(function (data) {
                                $("#authorizationCommission").modal("hide");
                                showAlert("Las ventas selecciondas fueron autorizadas exitosamente");
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            });
                        }else{
                            showAlert("Es necesario seleccionar un periodo a calcular", {type: 3});
                        }
                    },
                    cleanFields: function () {
                        location.reload();
                    },
                    obtainDateType: function () {
                        this.$http.get(ROOT_URL + "/date-calculation/status/2").success(function (data) {
                            this.commissionReports = data;
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
                    <div class="col-xs-12">
                        <div class="col-xs-10 text-header">
                            <h2>Liberación de cálculo de comisiones</h2>
                        </div>
                        <div class="col-xs-2">
                            <button style="margin-top: 25px; margin-left: 60px" class="btn btn-warning" @click="openModalAuthorizeModuleCommission()">Liberar comisiones</button>
                        </div>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-12">
                        <div class="col-xs-3">
                            <label>Estatus de la venta</label>
                            <select v-model="status" class="form-control" @change="selectStatus(status)">
                                <option :value="selectAll.selectAllStatus">{{selectAll.selectAllStatus}}</option>
                                <option v-for="status in saleStatus" :value="status">
                                    {{status}}
                                </option>
                            </select>
                            <br>
                            <div v-for="status in selectValues.selectedStatus">
                                <div class="col-md-12">
                                    <label style="background: #122b40; border-radius: 5px; color: white; font-family: 'Helvetica Neue'">
                                        &nbsp;{{status}}&nbsp;</label>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-2">
                            <label>Fecha de inicio</label>
                            <div class="form-group">
                                <div class="input-group date" id="startDate">
                                    <input type="text" class="form-control" v-model="selectValues.startDate" required>
                                    <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-2">
                            <label>Fecha de final</label>
                            <div class="form-group">
                                <div class="input-group date" id="endDate">
                                    <input type="text" class="form-control" v-model="selectValues.endDate" required>
                                    <span class="input-group-addon"  @click="activateDateTimePickerEndDate(selectValues.startDate)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-1">
                            <button class="btn btn-info" style="margin-top: 25px" @click="getSales()">Buscar</button>
                        </div>
                        <div class="col-xs-2" v-if="sales.length > 0"><button @click="cleanFields()" style="margin-top: 25px" class="btn btn-danger">Limpiar campos
                        </button></div>
                        <div class="col-xs-2" v-if="sales.length > 0">
                            <label>Buscar por clave sap</label>
                            <input class="form-control" type="text" v-model="search">
                        </div>
                    </div>
                </div>

                <div v-if="sales.length > 0">
                    <div class="row table-header">
                        <div class="col-xs-1"><b>Número de operación</b></div>
                        <div class="col-xs-1"><b>Fecha de compra</b></div>
                        <div class="col-xs-2"><b>Nombre del convenio</b></div>
                        <div class="col-xs-1"><b>Estatus</b></div>
                        <div class="col-xs-2"><b>Nombre del Vendedor</b></div>
                        <div class="col-xs-2"><b>Clave Sap</b></div>
                        <div class="col-xs-2"><b>Monto comisionable</b></div>
                        <div class="col-xs-1"><b>Modificar Clave Sap</b></div>
                    </div>
                </div>
            </div>
            <div class="table-body flex-row flex-content">
                <div class="row table-row" v-for="sale in sales | filterBy search in 'claveSap' ">
                    <div class="col-xs-1">{{sale.idSale}}</div>
                    <div class="col-xs-1">{{sale.purchaseDate}}</div>
                    <div class="col-xs-2">{{sale.agreement.agreementName}}</div>
                    <div class="col-xs-1">{{sale.statusSale}}</div>
                    <div class="col-xs-2">{{sale.employee.fullName}}</div>
                    <div class="col-xs-2"><input class="form-control" type="text" v-model="sale.claveSap"></div>
                    <div class="col-xs-2">{{sale.comissionableAmount | currency}}</div>
                    <div class="col-xs-1">
                        <button class="btn btn-default" @click="changeClaveSap(sale)"
                                data-toggle="tooltip" data-placement="top" title="Modificar">
                            <span class="glyphicon glyphicon-ok"></span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="row table-row" v-if="sales.length > 0">
                <div class="col-xs-9"><label>TOTALES</label></div>
                <div class="col-xs-2"><label>{{totalTruncate | currency}}</label></div>
            </div>



            <div class="modal fade" id="authorizationCommission" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
                <div class="modal-dialog ">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">
                                Liberación de calculo de comisiones
                            </h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="col-xs-5">
                                        <label>Selecciona un calculo:</label>
                                        <select v-model="reportSelected" class="form-control">
                                            <option v-for="commissionReport in commissionReports" :value="commissionReport">
                                                {{commissionReport.nameDate}}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-success" @click="authorize()">
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
