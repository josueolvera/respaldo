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
                    animation: true,
                    transactions: [],
                    amountCost:0,
                    commission:0,
                    iva:0,
                    selected:{
                        paymentDate:'',
                        policy:null
                    },
                    registerNumber: 0,
                    endDate: '',
                    var1: 0,
                    var2: 0,
                    var3: 0,
                    conciliation: {
                        type: '',
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
                        var dd = new Date().getDate()-1;
                        var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + dd;
                        this.dateTimePickerStart = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'YYYY-MM-DD',
                            useCurrent: false,
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
                    getTransactionsByDate: function () {
                        this.transactions = [];
                        var self = this;
                        this.amountCost = 0;
                        this.commission = 0;
                        this.iva = 0;
                        if (this.conciliation.type == 1){
                            this.$http.get(
                                    ROOT_URL + '/mr-pay/get-by-payment-date?paymentDate='+this.selected.paymentDate
                            ).success(function (data) {
                                var jsonObjectIndex = {};
                                data.forEach(function (transactiion) {
                                    if (isNaN(transactiion.paymentDateFormats)) {
                                        jsonObjectIndex[transactiion.paymentDateFormats._id] = transactiion.paymentDateFormats;
                                    } else {
                                        transactiion.paymentDateFormats = jsonObjectIndex[transactiion.paymentDateFormats];
                                    }
                                });
                                this.transactions = data;
                                this.sumTotals();
                                this.var1 = this.amountCost.toFixed(2);
                                this.var2 = this.commission.toFixed(2);
                                this.var3 = this.iva.toFixed(2);
                                if (this.transactions.length > 0) {
                                    this.registerNumber = this.transactions.length;
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
                        }else if (this.conciliation.type == 2){
                            this.$http.get(
                                    ROOT_URL + '/mr-pay/get-by-dates?startDate='+this.selected.paymentDate+'&endDate='+this.endDate
                            ).success(function (data) {
                                var jsonObjectIndex = {};
                                data.forEach(function (transactiion) {
                                    if (isNaN(transactiion.paymentDateFormats)) {
                                        jsonObjectIndex[transactiion.paymentDateFormats._id] = transactiion.paymentDateFormats;
                                    } else {
                                        transactiion.paymentDateFormats = jsonObjectIndex[transactiion.paymentDateFormats];
                                    }
                                });
                                this.transactions = data;
                                this.sumTotals();
                                this.var1 = this.amountCost.toFixed(2);
                                this.var2 = this.commission.toFixed(2);
                                this.var3 = this.iva.toFixed(2);
                                if (this.transactions.length > 0) {
                                    this.registerNumber = this.transactions.length;
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
                        }
                    },
                    sumTotals: function () {
                        var self = this;
                        this.transactions.forEach(function (transaction) {
                            self.amountCost += transaction.amountCost;
                            self.commission += transaction.commissionTransaction;
                            self.iva += transaction.Iva;
                        });
                    },
                    typeChanged: function () {
                        this.transactions = [];
                        this.selected.paymentDate = '';
                        this.endDate = '';
                    },
                    conciliateAmounts: function () {
                        this.conciliation.subtotal = '';
                        this.conciliation.iva = '';
                        this.conciliation.total = '';
                        this.conciliation.reason = '';
                        $('#modalConciliar').modal('show');
                    },
                    sendConciliation: function () {
                        this.animation = false;
                        if (this.conciliation.subtotal.length > 0 && this.conciliation.iva.length > 0 && this.conciliation.total.length > 0 && this.conciliation.reason.length > 0){
                            this.$http.post(ROOT_URL + "/mr-pay/conciliation-file", JSON.stringify(this.conciliation)).success(function (data) {
                                showAlert("El documento de conciliación sera enviado a las personas correspondientes");
                                this.animation = true;
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
                background: black;
                color: white;

            }
            .table-body .table-row:nth-child(2n+1){
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
            .table-hover {
                background-color: #2ba6cb;
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
                        <h2>Conciliación Sr. Pago</h2>
                    </div>
                    <div class="col-md-2" v-if="transactions.length > 0"  style="margin-top: 35px">
                        <label><p style="color: darkblue">Número de registros: {{registerNumber}}</p></label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-3">
                            <label>Tipos de conciliación</label>
                            <select v-model="conciliation.type"
                                    class="form-control" @change="typeChanged()">
                                <option value="1">Diaria</option>
                                <option value="2">Mensual</option>
                            </select>
                        </div>
                    </div>
                </div>
                <br>
                <div class="row" v-if="conciliation.type.length > 0">
                    <div class="col-md-12">
                        <div class="col-md-3">
                            <label v-if="conciliation.type == 1">Fecha de aplicación</label>
                            <label v-if="conciliation.type == 2">Fecha de aplicación inicial</label>
                            <div class="form-group">
                                <div class="input-group date" id="startDate">
                                    <input type="text" class="form-control" v-model="selected.paymentDate">
                                    <span class="input-group-addon" @click="activateDateTimePickerStart">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3" v-if="conciliation.type == 2">
                            <label>Fecha de aplicación final</label>
                            <div class="form-group">
                                <div class="input-group date" id="endDate">
                                    <input type="text" class="form-control" v-model="endDate">
                                    <span class="input-group-addon" @click="activateDateTimePickerEnd(selected.paymentDate)">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-1">
                            <button style="margin-top: 25px" class="btn btn-info" @click="getTransactionsByDate">Consultar</button>
                        </div>
                        <div class="col-md-1" v-if="conciliation.type == 2">
                            <button style="margin-top: 25px" class="btn btn-success" @click="conciliateAmounts">Conciliar</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <br>
                    <div class="loading" v-if="!animation">
                    </div>
                    <div style="background: #ddd" class="panel panel-default" v-if="isThereItems">
                        <!-- Default panel contents -->
                        <!-- Table de contenidos -->
                        <div class="flex-box container-fluid" v-if="transactions.length > 0" style="width:100%">
                            <div class="row table-header active">
                                <div class="col-md-1"><b>No de autorización</b></div>
                                <div class="col-md-1"><b>Tipo operación</b></div>
                                <div class="col-md-1"><b>Fecha registro</b></div>
                                <div class="col-md-1"><b>Hora registro</b></div>
                                <div class="col-md-2"><b>Referencia</b></div>
                                <div class="col-md-3"><b>Detalles</b></div>
                                <div class="col-md-1"><b>Importe cobrado</b></div>
                                <div class="col-md-1"><b>Comisiones</b></div>
                                <div class="col-md-1"><b>IVA</b></div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row " v-for="transaction in transactions" onmouseover='this.style.background="#2ba6cb"' onmouseout='this.style.background="#DDDDDD"'>
                                    <div class="col-md-1">{{transaction.authorizationNumber}}</div>
                                    <div class="col-md-1">{{transaction.paymentType}}</div>
                                    <div class="col-md-1">{{transaction.paymentDateFormats.simpleDate}}</div>
                                    <div class="col-md-1">{{transaction.paymentHour.hour}}:{{transaction.paymentHour.minute}}:{{transaction.paymentHour.second}}</div>
                                    <div class="col-md-2">{{transaction.refrence}}</div>
                                    <div class="col-md-3">{{transaction.details}}</div>
                                    <div class="col-md-1">{{transaction.amountCost}}</div>
                                    <div class="col-md-1">{{transaction.commissionTransaction}}</div>
                                    <div class="col-md-1">{{transaction.Iva}}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-body flex-row flex-content"  v-if="transactions.length > 0" style="width:100%">
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
