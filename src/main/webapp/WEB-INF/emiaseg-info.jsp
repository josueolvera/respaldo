<%--
  Created by IntelliJ IDEA.
  User: Desarrollador
  Date: 10/03/2017
  Time: 11:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>
<t:template pageTitle="BID Group: EMIASEG">
    <jsp:attribute name="scripts">
        <script type="text/javascript">

            function validateFloatKeyPress(el, evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                var number = el.value.split('.');
                if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                //just one dot
                if(number.length>1 && charCode == 46){
                    return false;
                }
                //get the carat position
                var caratPos = getSelectionStart(el);
                var dotPos = el.value.indexOf(".");
                if( caratPos > dotPos && dotPos>-1 && (number[1].length > 1)){
                    return false;
                }
                return true;
            }

            function getSelectionStart(o) {
                if (o.createTextRange) {
                    var r = document.selection.createRange().duplicate();
                    r.moveEnd('character', o.value.length);
                    if (r.text == '') return o.value.length;
                    return o.value.lastIndexOf(r.text)
                } else return o.selectionStart
            }
            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>

        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                },
                data: {
                    isThereItems: false,
                    animation: false,
                    polizes: [],
                    total:0,
                    commission:0,
                    iva:0,
                    startDate: '',
                    endDate: '',
                    registerNumber: 0,
                    var1: 0,
                    var2: 0,
                    var3: 0,
                    conciliation: {
                        subtotal: '',
                        iva: '',
                        total: '',
                        reason: ''
                    }
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
                        var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                        this.dateTimePickerStart = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'YYYY-MM-DD',
                            useCurrent: false
                        }).data();
                    },
                    activateDateTimePickerEnd: function (startDate) {
                        this.dateTimePickerStart = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'YYYY-MM-DD',
                            useCurrent: false,
                            minDate: startDate
                        }).data();
                    },
                    sumTotals: function () {
                        var self = this;
                        this.polizes.forEach(function (polize) {
                            self.commission += polize.insurancePremium.commissionAlterna;
                            self.iva += polize.insurancePremium.ivaAlterna;
                            self.total += polize.insurancePremium.commissionAlterna + polize.insurancePremium.ivaAlterna;
                        });
                    },
                    findAmounts: function () {
                        this.commission = 0;
                        this.iva = 0;
                        this.total = 0;
                        this.$http.get( ROOT_URL + "/policy-truckdriver/get-by-dates?startDate=" + this.startDate + "&endDate=" + this.endDate).success(function (data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (polize) {
                                if (isNaN(polize.startDateFormats)) {
                                    jsonObjectIndex[polize.startDateFormats._id] = polize.startDateFormats;
                                } else {
                                    polize.startDateFormats = jsonObjectIndex[polize.startDateFormats];
                                }
                            });

                            data.forEach(function (polize) {
                                if (isNaN(polize.endDateFormats)) {
                                    jsonObjectIndex[polize.endDateFormats._id] = polize.endDateFormats;
                                } else {
                                    polize.endDateFormats = jsonObjectIndex[polize.endDateFormats];
                                }
                            });

                            data.forEach(function (polize) {
                                if (isNaN(polize.insurancePremium)) {
                                    jsonObjectIndex[polize.insurancePremium._id] = polize.insurancePremium;
                                } else {
                                    polize.insurancePremium = jsonObjectIndex[polize.insurancePremium];
                                }
                            });

                            this.polizes = data;
                            this.sumTotals();
                            this.var1 = this.commission.toFixed(2);
                            this.var2 = this.iva.toFixed(2);
                            this.var3 = this.total.toFixed(2);
                            if (this.polizes.length > 0){
                                this.registerNumber = this.polizes.length;
                                this.isThereItems = true;
                            }else {
                                showAlert("No hay datos para esa busqueda, intente con otra combinaciòn", {type: 3});
                                setInterval(function () {
                                    location.reload();
                                }, 3000);
                            }
                        }).error(function (data) {
                            showAlert("error", {type : 3});
                        });
                    }
                },
                filters:{
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
                background: #ddd;
                border:solid 1px;
            }
            .table-row {
                padding: 1rem;
            }
            .flex-content {
                overflow-x: hidden;
            }
        </style>
        <style>
            textarea{
                resize: none;
            }

        </style>
    </jsp:attribute>
    <jsp:body>
        <div id="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-10">
                        <h2>Conciliación EMIASEG</h2>
                    </div>
                    <div class="col-md-2" v-if="polizes.length > 0"  style="margin-top: 35px">
                        <label><p style="color: darkblue">Número de registros: {{registerNumber}}</p></label>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-2">
                                <label>Fecha de inicial</label>
                                <div class="form-group">
                                    <div class="input-group date" id="startDate">
                                        <input type="text" class="form-control" v-model="startDate">
                                        <span class="input-group-addon" @click="activateDateTimePickerStart">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <label>Fecha de final</label>
                                <div class="form-group">
                                    <div class="input-group date" id="endDate">
                                        <input type="text" class="form-control" v-model="endDate">
                                        <span class="input-group-addon" @click="activateDateTimePickerEnd(startDate)">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-1">
                                <button style="margin-top: 25px" class="btn btn-info" @click="findAmounts()">Consultar</button>
                            </div>
                            <div class="col-md-1">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <br>
                    <div v-if="!isThereItems && animation == true"
                         style="height: 6rem; padding: 2rem 0;">
                        <div class="loader">Cargando...</div>
                    </div>
                    <div class="panel panel-default" v-if="polizes.length > 0">
                        <!-- Default panel contents -->
                        <!-- Table de contenidos -->
                        <div class="flex-box container-fluid" style="width:100%">
                            <div class="row table-header active">
                                <div class="col-md-1"><b>Placas</b></div>
                                <div class="col-md-2"><b>Folio</b></div>
                                <div class="col-md-1"><b>Hora</b></div>
                                <div class="col-md-1"><b>Fecha inicio vigencia</b></div>
                                <div class="col-md-1"><b>Fecha fin vigencia</b></div>
                                <div class="col-md-1"><b>Suma asegurada</b></div>
                                <div class="col-md-1"><b>Usuario</b></div>
                                <div class="col-md-1"><b>Edad</b></div>
                                <div class="col-md-1"><b>Email</b></div>
                                <div class="col-md-1"><b>Beneficirario</b></div>
                                <div class="col-md-1"><b>Dias</b></div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row " v-for="policy in polizes">
                                    <div class="col-md-1">{{policy.numLicensePlate}}</div>
                                    <div class="col-md-2">{{policy.numFolio}}</div>
                                    <div class="col-md-1">{{policy.hContracting.hour}}:{{policy.hContracting.minute}}:{{policy.hContracting.second}}</div>
                                    <div class="col-md-1">{{policy.startDateFormats.simpleDate}}</div>
                                    <div class="col-md-1">{{policy.endDateFormats.simpleDate}}</div>
                                    <div class="col-md-1">$ {{policy.insuranceAmount}}</div>
                                    <div class="col-md-1">{{policy.nameCconductor}}</div>
                                    <div class="col-md-1">{{policy.driverAge}}</div>
                                    <div class="col-md-1">{{policy.email}}</div>
                                    <div class="col-md-1">{{policy.nameBeneficiary}}</div>
                                    <div class="col-md-1">{{policy.days}}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>