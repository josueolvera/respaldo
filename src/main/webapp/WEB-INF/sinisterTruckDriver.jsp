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
<t:template pageTitle="BID Group: Conciliacion Interprotección">
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
                    sinisters: [],
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
                            useCurrent: false,
                            maxDate: currentDate
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
                        this.sinisters.forEach(function (sinister) {
                            self.commission += sinister.insurancePremium.commissionInterpro;
                            self.iva += sinister.insurancePremium.ivaInterpro;
                            self.total += sinister.insurancePremium.commissionInterpro + sinister.insurancePremium.ivaInterpro;
                        });
                    },
                    findAmouts: function () {
                        this.commission = 0;
                        this.iva = 0;
                        this.total = 0;
                        this.$http.get(ROOT_URL + "/sinister-truck-driver/get-by-dates?startDate="+this.startDate+"&endDate="+this.endDate).success(function (data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (sinister) {
                                if (isNaN(sinister.startDateFormats)) {
                                    jsonObjectIndex[sinister.startDateFormats._id] = sinister.startDateFormats;
                                } else {
                                    sinister.startDateFormats = jsonObjectIndex[sinister.startDateFormats];
                                }
                            });

                            data.forEach(function (sinister) {
                                if (isNaN(sinister.endDateFormats)) {
                                    jsonObjectIndex[sinister.endDateFormats._id] = sinister.endDateFormats;
                                } else {
                                    sinister.endDateFormats = jsonObjectIndex[sinister.endDateFormats];
                                }
                            });


                            data.forEach(function (sinister) {
                                if (isNaN(sinister.insurancePremium)) {
                                    jsonObjectIndex[sinister.insurancePremium._id] = sinister.insurancePremium;
                                } else {
                                    sinister.insurancePremium = jsonObjectIndex[sinister.insurancePremium];
                                }
                            });

                            this.sinisters = data;
                            this.sumTotals();
                            this.var1 = this.commission.toFixed(2);
                            this.var2 = this.iva.toFixed(2);
                            this.var3 = this.total.toFixed(2);
                            if (this.sinisters.length > 0){
                                this.registerNumber = this.sinisters.length;
                                this.isThereItems = true;
                            }else {
                                showAlert("No hay datos para esa busqueda, intente con otra combinaciòn", {type: 3});
                                setInterval(function () {
                                    location.reload();
                                }, 3000);
                            }

                        }).error(function () {
                            showAlert("Error", {type: 3});
                        })

                    },
                    conciliateAmounts: function () {
                        this.conciliation.subtotal = '';
                        this.conciliation.iva = '';
                        this.conciliation.total = '';
                        this.conciliation.reason = '';
                        $('#modalConciliar').modal('show');
                    },
                    sendConciliation: function () {
                        if (this.conciliation.subtotal.length > 0 && this.conciliation.iva.length > 0 && this.conciliation.total.length > 0 && this.conciliation.reason.length > 0){
                            this.$http.post(ROOT_URL + "/sinister-truck-driver/conciliation-file", JSON.stringify(this.conciliation)).success(function (data) {
                                showAlert("El documento de conciliación sera enviado a las personas correspondientes");
                                $('#modalConciliar').modal('hide');
                            }).error(function (data) {
                                showAlert("Error al generar la conciliación", {type: 3});
                            });
                        }else {
                            showAlert("Es necesario llenar los campos: Subtotal, Iva, Total y Justificación",{type: 3});
                        }
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
                        <h2>Conciliación Interprotección</h2>
                    </div>
                    <div class="col-md-2" v-if="sinisters.length > 0"  style="margin-top: 35px">
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
                                <button style="margin-top: 25px" class="btn btn-info" @click="findAmouts()">Consultar</button>
                            </div>
                            <div class="col-md-1">
                                <button style="margin-top: 25px" class="btn btn-success" @click="conciliateAmounts">Conciliar</button>
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
                    <div class="panel panel-default" v-if="sinisters.length > 0">
                        <!-- Default panel contents -->
                        <!-- Table de contenidos -->
                        <div class="flex-box container-fluid" style="width:100%">
                            <div class="row table-header active">
                                <div class="col-md-2"><b>Placas</b></div>
                                <div class="col-md-2"><b>Folio</b></div>
                                <div class="col-md-2"><b>Fecha inicio vigencia</b></div>
                                <div class="col-md-2"><b>Fecha fin vigencia</b></div>
                                <div class="col-md-1"><b>Importe cobrado</b></div>
                                <div class="col-md-1"><b>Comisiones</b></div>
                                <div class="col-md-1"><b>IVA</b></div>
                                <div class="col-md-1"><b>Total</b></div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row " v-for="sinister in sinisters">
                                    <div class="col-md-2">{{sinister.numLicensePlate}}</div>
                                    <div class="col-md-2">{{sinister.numFolio}}</div>
                                    <div class="col-md-2">{{sinister.startDateFormats.simpleDate}}</div>
                                    <div class="col-md-2">{{sinister.endDateFormats.simpleDate}}</div>
                                    <div class="col-md-1">{{sinister.amountOfCoverage}}</div>
                                    <div class="col-md-1">{{sinister.insurancePremium.commissionInterpro}}</div>
                                    <div class="col-md-1">{{sinister.insurancePremium.ivaInterpro}}</div>
                                    <div class="col-md-1">{{sinister.insurancePremium.commissionInterpro + sinister.insurancePremium.ivaInterpro}}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-body flex-row flex-content"  v-if="sinisters.length > 0" style="width:100%">
                        <div class="row table-row" style="font-weight: bold;">
                            <div class="col-md-1">Totales:</div>
                            <div class="col-md-8"></div>
                            <div class="col-md-1">{{var1}}</div>
                            <div class="col-md-1">{{var2}}</div>
                            <div class="col-md-1">{{var3}}</div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- container-fluid -->
            <div class="modal fade" id="modalConciliar" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title"><b>Conciliar</b></h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    <label>Ingresa los montos para la generación de factura de conciliación</label>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>Subtotal</label>
                                    <div class="form-group">
                                        <div class="input-group">
                                            <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-usd"></span>
                                            </span>
                                            <input class="form-control" v-model="conciliation.subtotal" onkeypress="return validateFloatKeyPress(this, event)">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-4">
                                    <label>Iva</label>
                                    <div class="form-group">
                                        <div class="input-group">
                                            <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-usd"></span>
                                            </span>
                                            <input class="form-control" v-model="conciliation.iva" onkeypress="return validateFloatKeyPress(this, event)">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-4">
                                    <label>Total</label>
                                    <div class="form-group">
                                        <div class="input-group">
                                            <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-usd"></span>
                                            </span>
                                            <input class="form-control" v-model="conciliation.total" onkeypress="return validateFloatKeyPress(this, event)">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12">
                                    <label>Justificación</label>
                                    <textarea v-model="conciliation.reason" cols="500" rows="3"
                                              class="form-control"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-success" @click="sendConciliation()">
                                Generar conciliación
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>