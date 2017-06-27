<%--
  Created by IntelliJ IDEA.
  User: Josue
  Date: 18/05/2017
  Time: 12:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>
<t:template pageTitle="BID Group: Bandeja de entrada solicitante">
     <jsp:attribute name="scripts">

        <script type="text/javascript">
            function validateFloatKeyPress(el, evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                var number = el.value.split('.');
                if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                //just one dot
                if (number.length > 1 && charCode == 46) {
                    return false;
                }
                //get the carat position
                var caratPos = getSelectionStart(el);
                var dotPos = el.value.indexOf(".");
                if (caratPos > dotPos && dotPos > -1 && (number[1].length > 1)) {
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
        </script>
         <script>
             Number.prototype.formatMoney = function (places, symbol, thousand, decimal) {
                 places = !isNaN(places = Math.abs(places)) ? places : 2;
                 symbol = symbol !== undefined ? symbol : "$";
                 thousand = thousand || ",";
                 decimal = decimal || ".";
                 var number = this,
                     negative = number < 0 ? "-" : "",
                     i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
                     j = (j = i.length) > 3 ? j % 3 : 0;
                 return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
             };
         </script>
         <script type="text/javascript">
             function isNumberKey(evt) {
                 var charCode = (evt.which) ? evt.which : event.keyCode;
                 if (charCode > 31 && (charCode < 48 || charCode > 57))
                     return false;
                 return true;
             }
         </script>
<script type="text/javascript">
    var vm = new Vue({
        el: '#content',
        created: function () {

        },
        ready: function () {
            this.getUserInSession();
            this.getRequestInformation();
            this.getRequestHistory();
        },
        data: {
            user: {},
            idRequest: ${idRequest},
            request: {},
            requestBoxUrl: ROOT_URL + "/siad/requests-level-authorization",
            downloadUrl: ROOT_URL + '/estimations/attachment/download/',
            selectedEstimation: {
                idEstimation: '',
                idRequest: '',
                justify: '',
                rejectJustify: ''
            },
            amount: 0
        },
        methods: {
            arrayObjectIndexOf: function (myArray, searchTerm, property) {
                for (var i = 0, len = myArray.length; i < len; i++) {
                    if (myArray[i][property] === searchTerm) return i;
                }
                return -1;
            },
            getUserInSession: function () {
                this.$http.get(ROOT_URL + "/user")
                    .success(function (data) {
                        this.user = data;
                    })
                    .error(function (data) {
                        showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                    });
            },
            getRequestInformation: function () {
                this.$http.get(ROOT_URL + "/requests/" + this.idRequest)
                    .success(function (data) {
                        this.request = data;
                        this.getAmountByUser();
                    })
                    .error(function (data) {

                    });
            },
            getAmountByUser: function () {
                this.$http.get(ROOT_URL + "/distributor-area-rol/by-user").success(function (data) {
                    this.amount = data.amountRequest;
                }).error(function () {
                    showAlert("Error al obtener el monto de presupuestado", {type: 3});
                });
            },
            rejectRequest: function () {
                this.selectedEstimation.idRequest = this.idRequest;
                if (this.selectedEstimation.rejectJustify.length > 0) {
                    this.$http.post(ROOT_URL + "/requests/reject", JSON.stringify(this.selectedEstimation)).success(function (data) {
                        $("#justificarrechazo").modal("hide");
                        showAlert("Solicitud rechazada con exito");
                        this.getRequestInformation();
                    }).error(function () {
                        showAlert("Error al generar la solicitud", {type: 3});
                    })

                } else {
                    showAlert("Es necesario llenar la justificación del rechazo", {type: 3});
                }
            },
            sendToValidation: function () {
                this.$http.get(ROOT_URL + "/estimations/validate-by-request/" + this.idRequest).success(function (data) {

                    console.log(data);

                    if(data == 1){

                    }else if(data == 2){
                        $("#modalSendValidationFinantialPlaning").modal("show");
                    }else if(data == 3){
                        $("#botonenviar").modal("show");
                    }
                }).error(function () {
                    showAlert("Error al generar la solicitud", {type: 3});
                })
            },
            sendToBuyManagement: function () {
                this.$http.get(ROOT_URL + "/requests/send-to-buy-management/" + this.idRequest)
                    .success(function (data) {
                        $("#botonenviar").modal("hide");
                        showAlert("Solicitud autorizada correctamente");
                        this.getRequestInformation();
                    }).error(function (data) {
                    showAlert("Error al enviar al administrador de compras", {type: 3});
                });
            },
            sendToFiancialPlaning: function () {
                this.$http.get(ROOT_URL + "/requests/send-to-financial-planing/" + this.idRequest)
                    .success(function (data) {
                        $("#modalSendValidationFinantialPlaning").modal("hide");
                        showAlert("Solicitud autorizada correctamente");
                        this.getRequestInformation();
                    }).error(function (data) {
                    showAlert("Error al enviar al administrador de compras", {type: 3});
                });
            },
            openModalRechazar: function () {
                this.selectedEstimation.rejectJustify = "";
                $("#justificarrechazo").modal("show");
            }
        },
        filters: {
            separate: function (value) {
                return value.replace(/:/g, ' ');
            },
            currencyDisplay: {
                read: function (val) {
                    if (typeof val == 'number') {
                        return val.formatMoney(2, '');
                    }
                },
                write: function (val, oldVal) {
                    var number = +val.replace(/[^\d.]/g, '');
                    return isNaN(number) ? 0 : parseFloat(number.toFixed(2));
                }
            }
        }
    });

</script>

        <script type="text/javascript">
            function format(input) {
                var num = input.value.replace(/\,/g, '');
                if (!isNaN(num)) {
                    num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g, '$1,');
                    num = num.split('').reverse().join('').replace(/^[\,]/, '');
                    num = num.split('').join('').replace(/^[\,]/, '');
                    var inicio = num.substring(0, 1);
                    if (inicio == '0') {
                        showAlert("Corregir cantidad", {type: 3});
                        input.value = '';
                    } else {
                        input.value = num.split('').join('').replace(/^[\,]/, '');
                    }
                }

                else {
                    showAlert("Solo se permiten números", {type: 3});
                    input.value = '';
                }
            }

            function cleanField(obj) {
                var inicial = obj.value;
                if (obj.value == '0' || obj.value == '0.00') {
                    obj.value = '';
                } else {
                    return false;
                }
            }
            function cleanFieldProduct(obj) {
                var inicial = obj.value;
                if (obj.value == '1') {
                    obj.value = '';
                } else {
                    return false;
                }
            }
            function onlyNumbers(e) {
                var key = window.Event ? e.which : e.keyCode
                return (key >= 48 && key <= 57)
            }
            function backHistory() {
                history.back();
            }
            function ponerCeros(obj) {
                var contar = obj.value;
                var min = contar.length - 3;
                var max = contar.length;

                if (obj.value == "" || obj.value == null) {
                    obj.value = "";
                } else {
                    if (max >= 1 && max < 40) {
                        var extraer = contar.substring(min, max);
                        if (extraer == '.00') {
                            contar = contar.replace('.,', ',');
                            contar = contar.replace(',.', ',');
                            format(input);
                        } else {
                            contar = contar.replace('.,', ',');
                            contar = contar.replace(',.', ',');
                            format(input);
                        }
                    }
                }
            }

        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            textarea {
                resize: none;
            }
        </style>
        <style>
            textarea {
                resize: none;
            }

            label.circlered {
                width: 20px;
                height: 20px;
                -moz-border-radius: 50%;
                -webkit-border-radius: 50%;
                border-radius: 50%;
                background: #FF0000;
            }

            label.circleyel {
                width: 20px;
                height: 20px;
                -moz-border-radius: 50%;
                -webkit-border-radius: 50%;
                border-radius: 50%;
                background: #FFFF06;
            }

            label.circlegre {
                width: 20px;
                height: 20px;
                -moz-border-radius: 50%;
                -webkit-border-radius: 50%;
                border-radius: 50%;
                background: #00FF00;
            }

            #detalleGreen {
                color: green;
                font-size: 30px;
            }

            #detalleGreen1 {
                color: green;
                font-size: 30px;
            }

            #detalleGreen2 {
                color: green;
                font-size: 30px;
            }

            hr {
                height: 100px;
            }
        </style>
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
    <jsp:body>
        <!--INICIA PANTALLA DE SOLICITUD/VIGENTE PAGINA 25-->

        <div id="content">
            <div class="loading" v-if="request.priceEstimationsList.length==0">
            </div>
            <div class="row">
                <div class="col-md-8">
                    <h2 v-if="request.requestType.idRequestType == 1">Detalle de solicitud/Vigente
                        <label class="circleyel" ></label>
                    </h2>
                    <h2 v-if="request.requestType.idRequestType == 2">Detalle de solicitud/Finalizada
                        <label class="circlegre" ></label>
                    </h2>
                    <h2 v-if="request.requestType.idRequestType == 3">Detalle de solicitud/Rechazada
                        <label class="circlered" ></label>
                    </h2>
                </div>
                <div class="col-md-4 text-right" style="margin-top: 10px">
                    <label>Nombre de usuario</label>
                    <p>
                        <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                    </p>
                </div>
            </div>
            <br>

            <div class="row">
                <div class="col-md-5">
                    <h3>Solicitud fuera de monto autorizado <span class="glyphicon glyphicon-triangle-bottom"
                                                             style="color: #EE0909; font-size: 100%"></span></h3>
                </div>
                <div class="col-md-4 text-right" >
                        <h3>Monto autorizado: {{amount | currency}}</h3>
                </div>
            </div>

            <br>
            <div class="panel panel-default" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos del solicitante</b></h5>
                            <table class="table table-striped">
                                <thead style="background-color: #BDBDBD">
                                <th class="col-md-4">Nombre</th>
                                <th class="col-md-4">Area</th>
                                <th class="col-md-4">Fecha de solicitud</th>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="col-md-3"><u>{{request.employees.fullName}}</u></td>
                                    <td class="col-md-3"><u>{{request.dwEmployees.dwEnterprise.area.areaName}}</u></td>
                                    <td class="col-md-3"><u>{{request.creationDateFormats.simpleDate}}</u></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel default" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos de solicitud</b></h5>
                            <table class="table table-striped">
                                <thead style="background-color: #BDBDBD">
                                <th class="col-md-4">Centro de costos</th>
                                <th class="col-md-4">Concepto</th>
                                <th class="col-md-4"></th>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="col-md-3"><u>{{request.distributorCostCenter.costCenter.name}}</u></td>
                                    <td class="col-md-1"><u>{{request.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</u>
                                    </td>
                                    <td class="col-md-8"></td>
                                </tr>
                                </tbody>
                            </table>
                            <table class="table table-striped">
                                <thead style="background-color: #BDBDBD">
                                <th class="col-md-4">
                                    <div class="col-md-8 text-center">Lista de productos</div>
                                    <div class="col-md-4 text-center">Cantidad</div>
                                </th>
                                <th class="col-md-8">Justificacion</th>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="col-md-4">
                                        <div class="col-md-12" v-for="product in request.requestProductsList">
                                            <div class="col-md-8 text-center">
                                                {{product.roleProductRequest.cProductsRequest.productRequestName}}
                                            </div>
                                            <div class="col-md-4 text-center">{{product.quantity}}</div>
                                        </div>
                                    </td>
                                    <td class="col-md-8" rowspan="8">
                                        <textarea class="form-control" rows="4" v-model="request.reason"
                                                  readonly disabled>
                                            </textarea>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div v-for="(index, estimation) in request.priceEstimationsList" v-if="estimation.idEstimationStatus != 3">
                <div class="panel panel-default" style="background-color: #F2F2F2">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-8"><br>
                                <h5><b style="color: black">Cotizacion {{index+1}}</b></h5>
                            </div>
                            <div class="col-md-2">
                                <label style="margin-top: 30px">Monto cotizado sin IVA </label>
                            </div>
                            <div class="col-md-2">
                                <input class="form-control" type="text" placeholder="$" style=" margin-top: 15px"
                                       v-model="estimation.amount | currency" maxlength="14" disabled/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-12" style="background-color: #BDBDBD">
                                <h6><b>Documento</b></h6>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-3"><br>
                                <h6>{{estimation.fileName}}</h6>
                            </div>
                            <div class="col-md-2"><br>
                                <a class="btn btn-primary btn-sm"
                                   :href="downloadUrl + estimation.idPriceEstimation"
                                   data-toggle="tooltip" data-placement="top" title="Descargar">
                                    Descargar
                                </a>
                            </div>
                            <div class="col-md-6">
                                <br>
                            </div>
                            <div class="col-md-1">
                                <br>
                                <button class="btn btn-success btn-sm"
                                        v-if="estimation.idEstimationStatus == 1 && request.idRequestStatus != 7"
                                        @click="openModalSelectEstimation(estimation)">
                                    Elegir
                                </button>
                                <span id="detalleGreen" v-if="estimation.idEstimationStatus == 2"
                                      class="glyphicon glyphicon-ok" style="margin-left: 65%"></span>
                            </div>
                            <br>
                        </div>
                    </div>
                    <br>
                </div>
            </div>
            <div class="panel panel-default" style="background-color: #F2F2F2"
                 v-if="request.reasonResponsible.length > 0">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #BDBDBD">
                            <h6><b>Justificacion</b></h6>
                        </div>
                        <br>
                        <div class="col-md-12">
                            <p>
                                {{request.reasonResponsible}}
                            </p>
                        </div>
                        <br>
                    </div>
                </div>
            </div>


            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-9"></div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-success btn-sm" v-if="request.idRequestStatus == 5"
                                @click="sendToValidation()">
                            Aceptar
                        </button>
                    </div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-danger btn-sm" v-if="request.idRequestStatus == 5"
                                @click="openModalRechazar()">Rechazar
                        </button>
                    </div>
                    <div class="col-md-1">
                        <a class="btn btn-default btn-sm"
                           :href="requestBoxUrl"
                           data-toggle="tooltip" data-placement="top" title="Cancelar"
                        >
                            Cancelar
                        </a>
                    </div>
                </div>
            </div>

            <br>




            <div class="modal fade" id="justificarrechazo" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Justificar rechazo</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                <textarea class="form-control" rows="4"
                                          v-model="selectedEstimation.rejectJustify"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" @click="rejectRequest()">Aceptar
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>





            <div class="modal fade" id="botonenviar" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Enviar solicitud</h4>
                        </div>
                        <div class="modal-body">
                            <p>La solicitud sera enviada a compras</p>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="sendToBuyManagement()">Aceptar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>



            <div class="modal fade" id="modalSendValidationFinantialPlaning" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Detalle de solicitud</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <label>Solicitud fuera de presupuesto</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <p>Se enviará a planeación financiera para su validación</p>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" data-toggle="collapse"
                                    @click="sendToFiancialPlaning()">
                                Aceptar
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>




        </div>
    </jsp:body>
</t:template>