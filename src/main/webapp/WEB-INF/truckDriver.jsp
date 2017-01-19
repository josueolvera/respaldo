<%--
  Created by IntelliJ IDEA.
  User: Kevin Salvador
  Date: 10/01/2017
  Time: 05:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>
<t:template pageTitle="BID Group: Consulta de polizas">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                },
                data: {
                    isThereItems: false,
                    Polycys: [],
                    searching: false,
                    precioVenta:0,
                    costo:0,
                    totales:0,
                    diferencia:0,
                    selected:{
                        startDate:'',
                        policy:null
                    },
                    registerNumber: 0,
                    var1: 0,
                    var2: 0,
                    var3: 0
                },
                methods : {
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    activateDateTimePickerStart: function () {
                        var date = new Date();
                        var dd = new Date().getDate()-1;
                        var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + dd;
                        this.dateTimePickerStart = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'YYYY-MM-DD',
                            useCurrent: false,
                            maxDate: currentDate
                        }).data();
                    },
                    destroyDateTimePickerStart: function () {
                        this.activateDateTimePickerStart();
                        $("#startDate").on("dp.change", function (e) {
                        });
                    },
                    getPolicysByDate: function () {
                        var self = this;
                        this.$http.get(
                                ROOT_URL + '/policy-truckdriver/get-by-date?startDate='+this.selected.startDate
                        ).success(function (data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (policy) {
                                if (isNaN(policy.cTypeSecure)) {
                                    jsonObjectIndex[policy.cTypeSecure._id] = policy.cTypeSecure;
                                } else {
                                    policy.cTypeSecure = jsonObjectIndex[policy.cTypeSecure];
                                }
                            });
                            this.Polycys = data;
                            this.sumTotals();
                            this.var1 = this.totales.toFixed(2);
                            this.var2 = this.precioVenta.toFixed(2);
                            this.var3 = this.costo.toFixed(2);
                            if (this.Polycys.length > 0) {
                                this.registerNumber = this.Polycys.length;
                                this.isThereItems = true;
                            } else {
                                showAlert("No hay datos para esa busqueda, intente con otra combinaciòn", {type: 3});
                                setInterval(function () {
                                    location.reload();
                                }, 3000);
                            }
                        }).error(function (data) {
                            showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
                        });
                    },
                    startDateChanged: function () {
                        this.isThereItems = false;
                    },
                    endDateChanged: function () {
                        this.isThereItems = false;
                    },

                    searchPolize: function (policy,startDate) {
                      this.getPolicysByDate();

                    },
                    sumTotals: function () {
                        var self = this;
                        this.Polycys.forEach(function (policy) {
                            self.precioVenta += policy.priceSale;
                            self.costo+=policy.cost;
                            self.totales+=policy.total;
                        });
                    }
                }
            });
        </script>
    </jsp:attribute>
    <jsp:attribute name="styles">
        <style>
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
                background: black;
                color: white;

            }
            .table-body .table-row:nth-child(2n+1) {
                background: white;
                overflow: auto;
                border:solid 1px;
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
        <div id="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-8">
                        <h2>Conciliación camionero</h2>
                    </div>
                    <div class="col-md-2" v-if="Polycys.length > 0"  style="margin-top: 35px">
                        <label><p style="color: darkblue">Nùmero de registros: {{registerNumber}}</p></label>
                    </div>
                </div>
                <div class="row">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-2">
                                    <label>Fecha de aplicacion</label>
                                    <div class="form-group">
                                        <div class="input-group date" id="startDate">
                                            <input type="text" class="form-control" v-model="selected.startDate">
                                            <span class="input-group-addon" @click="activateDateTimePickerStart">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <button style="margin-top: 25px" class="btn btn-info" @Click="getPolicysByDate">Consultar</button>
                                </div>
                            </div>
                        </div>
                </div>
                <div class="col-md-12">
                    <br>
                    <div v-if="!isThereItems && searching == true"
                         style="height: 6rem; padding: 2rem 0;">
                        <div class="loader">Cargando...</div>
                    </div>
                    <div style="background: #ddd" class="panel panel-default" v-if="isThereItems">
                        <!-- Default panel contents -->
                        <!-- Table de contenidos -->
                        <div class="flex-box container-fluid" v-if="Polycys.length > 0" style="width:100%">
                            <div class="row table-header active">
                                <div class="col-md-1"><b>No de placa</b></div>
                                <div class="col-md-2"><b>No de folio</b></div>
                                <div class="col-md-2"><b>Inicio vigencia</b></div>
                                <div class="col-md-2"><b>Fin vigencia</b></div>
                                <div class="col-md-1"><b>Monto asegurado</b></div>
                                <div class="col-md-1"><b>Tipo seguro</b></div>
                                <div class="col-md-1"><b>Precio venta</b></div>
                                <div class="col-md-1"><b>Comisión</b></div>
                                <div class="col-md-1"><b>Diferencia</b></div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row" v-for="policy in Polycys">
                                    <div class="col-md-1">{{policy.numLicensePlate}}</div>
                                    <div class="col-md-2">{{policy.numFolio}}</div>
                                    <div class="col-md-2">{{policy.dStartValidity.dayOfMonth}}-{{policy.dStartValidity.monthValue}}-{{policy.dStartValidity.year}}</div>
                                    <div class="col-md-2">{{policy.dEndValidity.dayOfMonth}}-{{policy.dEndValidity.monthValue}}-{{policy.dEndValidity.year}}</div>
                                    <div class="col-md-1">{{policy.insuranceAmount}}</div>
                                    <div class="col-md-1">{{policy.cTypeSecure.name}}</div>
                                    <div class="col-md-1">{{policy.priceSale}}</div>
                                    <div class="col-md-1">{{policy.cost}}</div>
                                    <div class="col-md-1">{{policy.total}}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-body flex-row flex-content"  v-if="Polycys.length > 0" style="width:100%">
                        <div class="row table-row">
                            <div class="col-md-1">Totales:</div>
                            <div class="col-md-8"></div>
                            <div class="col-md-1">{{var2}}</div>
                            <div class="col-md-1">{{var3}}</div>
                            <div class="col-md-1">{{var1}}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
