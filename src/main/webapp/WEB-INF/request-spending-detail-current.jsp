<%--
  Created by IntelliJ IDEA.
  User: Leonardo
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
<script type="text/javascript">
    var vm = new Vue({
        el: '#content',
        created: function () {

        },
        ready: function () {
            this.getUserInSession();
            this.getRequestInformation();
        },
        data: {
            user: {},
            idRequest: ${idRequest},
            request: {},
            downloadUrl: ROOT_URL + '/estimations/attachment/download/',
            priceEstimations:{},
            selectedEstimation:{
                idEstimation:'',
                idRequest:'',
                justify: '',
                rejectJustify: ''
            },
            requestBoxUrl: ROOT_URL + "/siad/buy"
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
                    })
                    .error(function (data) {

                    });
            },
            openModalSelectEstimation: function(estimation){
                this.priceEstimations = JSON.parse(JSON.stringify(estimation));
                this.selectedEstimation.idEstimation = estimation.idPriceEstimation;
                this.selectedEstimation.idRequest = this.idRequest;
                this.selectedEstimation.justify = '';
                $("#modalSelectEstimation").modal("show");
            },
            selectedEstimations: function () {
                if (this.selectedEstimation.justify.length > 0){
                    this.$http.post(ROOT_URL + "/estimations/validate", JSON.stringify(this.selectedEstimation)).success(function (data) {
                        if(data == true){

                            $("#modalSelectEstimation").modal("hide");
                            $("#modalSendValidationFinantialPlaning").modal("show");

                        }else{

                            this.$http.post(ROOT_URL + "/estimations/authorize-estimation", JSON.stringify(this.selectedEstimation)).success(function (data) {
                                showAlert("Solicitud autorizada");
                                $("#modalSelectEstimation").modal("hide");
                                this.getRequestInformation();
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            });

                        }
                    }).error(function () {
                        showAlert("Error al generar la solicitud", {type: 3});
                    })
                }else {
                    showAlert("Es necesario llenar una justificacion", {type:3});
                }
            },
            rejectReuquestModal: function () {
                this.selectedEstimation.idRequest = this.idRequest;
                this.selectedEstimation.rejectJustify = '';
                $("#justificarrechazo").modal("show");
            },
            rejectRequest: function () {
                if(this.selectedEstimation.rejectJustify.length > 0){
                    this.$http.post(ROOT_URL + "/requests/reject", JSON.stringify(this.selectedEstimation)).success(function (data) {
                        $("#justificarrechazo").modal("hide");
                        showAlert("Solicitud rechazada con exito");
                        this.getRequestInformation();
                    }).error(function () {
                        showAlert("Error al generar la solicitud", {type: 3});
                    })

                }else {
                    showAlert("Es necesario llenar la justificación del rechazo", {type: 3});
                }
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
    </jsp:attribute>
    <jsp:body>
        <!--INICIA PANTALLA DE SOLICITUD/VIGENTE PAGINA 25-->

        <div id="content">
            <div class="loading" v-if="request.priceEstimationsList.length==0">
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-6">
                        <p></p>
                        <h2>DETALLE DE SOLICITUD/VIGENTE</h2>
                    </div>
                    <br>
                    <div class="col-md-1" style="margin-top: -4px; margin-left: -7%">
                        <p></p>
                        <label class="circleyel"></label>
                    </div>
                    <div class="col-md-5 text-right" style="margin-left: 60px;">
                        <label>Nombre de usuario</label>
                        <p>
                            <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                        </p>
                    </div>
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

            <br>

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
                                <button class="btn btn-success btn-sm" v-if="estimation.idEstimationStatus == 1 && request.idRequestStatus != 7" @click="openModalSelectEstimation(estimation)">
                                    Elegir
                                </button>
                                <span id="detalleGreen" v-if="estimation.idEstimationStatus == 2" class="glyphicon glyphicon-ok" style="margin-left: 65%"></span>
                            </div>
                            <br>
                        </div>
                    </div>
                    <br>
                </div>
            </div>
            <div class="panel panel-default" style="background-color: #F2F2F2" v-if="request.reasonResponsible.length > 0">
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
                    <div class="col-md-7"></div>
                    <div class="col-md-2" >
                        <button class="btn btn-info btn-sm" data-toggle="modal" data-target="#ordendecompra" v-if="request.idRequestStatus == 2">
                            Generar orden de compra
                        </button>
                    </div>
                    <div class="col-md-1"  >
                        <button type="button" class="btn btn-warning btn-sm" v-if="request.idRequestStatus == 3">
                            Agregar Comprobante
                        </button>
                    </div>
                    <div class="col-md-1"  >
                        <button type="button" class="btn btn-danger btn lg-active" @click="rejectReuquestModal()" v-if="request.idRequestStatus == 1">Rechazar
                        </button>
                    </div>
                    <div class="col-md-1">
                        <a class="btn btn-default btn-sm"
                           :href="requestBoxUrl"
                           data-toggle="tooltip" data-placement="top" title="Detalle">
                            Cancelar
                        </a>
                    </div>
                </div>
            </div>

            <br>
            <br>

            <!--TERMINA PANTALLA DE SOLICITUD/VIGENTE PAGINA 25-->
            <!--EMPIEZA MODAL 1 PARA LA OPCION ELEGIR DE LA PANTALLA DE SOLICITUD/VIGENTE PAGINA 26-->

            <div class="modal fade" id="modalSelectEstimation" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Justificar</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <textarea class="form-control" rows="3" v-model="selectedEstimation.justify"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" data-toggle="collapse" @click="selectedEstimations()">
                                Aceptar
                            </button>
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
                            <button type="button" class="btn btn-success" data-toggle="collapse" @click="selectedEstimations()">
                                Aceptar
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

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

            <!--EMPIEZA MODAL DE LA PAGINA 27 A LA 28 ORDEN DE COMPRA-->

            <div class="modal fade" id="ordendecompra" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <div class="alert">
                                <img style="width: 30px; height: 30px">
                                <h3 class="modal-title" style="text-align: center"><label>ORDEN DE COMPRA</label>
                                </h3>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <form>
                                        <div class="col-md-12">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <label>Fecha:</label><br>
                                                            <select class="form-control">
                                                                <option></option>
                                                                <option value="{{incidence}}">
                                                                    {{incidence.incidenceName}}
                                                                </option>
                                                            </select>
                                                            <h5 style="text-align: center"><B><I>EMPRESA QUE
                                                                EMITE</I></B></h5>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label>Numero de orden:</label><br>
                                                            <select class="form-control">
                                                                <option></option>
                                                                <option value="{{incidence}}">
                                                                    {{incidence.incidenceName}}
                                                                </option>
                                                            </select>
                                                            <h5 style="text-align: center"><B><I>EMITIDO PARA</I></B>
                                                            </h5>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-md-3">
                                                            <label>Empresa:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="input-group">
                                                                <label></label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label>Empresa:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="input-group">
                                                                    <label></label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-3">
                                                            <label>Dirección:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="input-group">
                                                                <label></label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label>Dirección:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="input-group">
                                                                    <label></label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-3">
                                                            <label>Ciudad:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="input-group">
                                                                <label></label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label>Ciudad:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="input-group">
                                                                    <label></label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-3">
                                                            <label>Código postal:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="input-group">
                                                                <label></label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label>Código postal:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="input-group">
                                                                    <label></label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-3">
                                                            <label>RFC:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="input-group">
                                                                <label></label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label>RFC:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <div class="input-group">
                                                                    <label></label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- segunda tabla contactos -->
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <h5 style="text-align: center"><b><i>CONTACTO DEL EMISOR</i></b>
                                                            </h5>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <h5 style="text-align: center"><b><i>CONTACTO DEL
                                                                RECEPTOR</i></b></h5>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <label>Nombre:</label>
                                                            <input class="form-control">
                                                        </div>
                                                        <div class="col-md-1">
                                                            <div class="input-group">
                                                                <label></label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-5">
                                                            <label>Nombre:</label>
                                                            <input class="form-control">
                                                        </div>
                                                        <div class="col-md-1">
                                                            <div class="form-group">
                                                                <div class="input-group">
                                                                    <label></label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <label>Puesto:</label>
                                                            <input class="form-control">
                                                        </div>
                                                        <div class="col-md-1">
                                                            <div class="input-group">
                                                                <label></label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-5">
                                                            <label>Puesto:</label>
                                                            <input class="form-control">
                                                        </div>
                                                        <div class="col-md-1">
                                                            <div class="form-group">
                                                                <div class="input-group">
                                                                    <label></label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <label>Télefono:</label>
                                                            <input class="form-control" type="text" maxlength="14">
                                                        </div>
                                                        <div class="col-md-1">
                                                            <div class="input-group">
                                                                <label></label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-5">
                                                            <label>Télefono:</label>
                                                            <input class="form-control" type="text" maxlength="14">
                                                        </div>
                                                        <div class="col-md-1">
                                                            <div class="form-group">
                                                                <div class="input-group">
                                                                    <label></label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="#onemore">
                                            <div class="col-md-12">
                                                <div class="panel panel-default">
                                                    <div class="panel-body">
                                                        <div class="row">
                                                            <table class="table">
                                                                <thead>
                                                                <tr>
                                                                    <th class="col-md-8">Descripción</th>
                                                                    <th>Cantidad</th>
                                                                    <th>Precio</th>
                                                                </tr>
                                                                </thead>
                                                                <tbody>
                                                                <tr>
                                                                    <td><textarea class="form-control"
                                                                                  rows="5"></textarea>
                                                                    </td>
                                                                    <td><input class="form-control" type="number"
                                                                               maxlength="3" id="cantidad"/>
                                                                    </td>
                                                                    <td><input class="form-control" type="number"
                                                                               maxlength="14" id="precio"/><br><br>
                                                                        <button class="btn btn-info btn-sm"
                                                                        ><a href="onemore">Agregar</a></button>
                                                                    </td>
                                                                </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="panel panel-default">
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-md-6"></div>
                                                        <div class="col-md-6" style="margin-left: 475px">
                                                            <form name="facturaSiad">
                                                                <dl class="dl-horizontal">
                                                                    <dt style="margin: 5px">
                                                                    <p>Subtotal</p></dt>
                                                                    <input class="form-control" type="text"
                                                                           placeholder="$" style="width: 45%"
                                                                           name="subtotal" maxlength="14" readonly
                                                                           disabled/>
                                                                    <dt style="margin: 5px">
                                                                    <p>IVA</p></dt>
                                                                    <input class="form-control"
                                                                           type="text"
                                                                           placeholder="$" style="width: 45%"
                                                                           name="iva" readonly disabled/>
                                                                    <dt style="margin: 5px"><b style="color: black">Total</b>
                                                                    </dt>
                                                                    <input class="form-control"
                                                                           type="text"
                                                                           placeholder="$" style="width: 45%"
                                                                           name="total" maxlength="14" readonly
                                                                           disabled/>
                                                                </dl>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    <hr class="firma" color="black" size="150" width="270" style="height: 2px;">
                                    <h6 style="text-align: center"><b>Nombre del solicitante de orden de compra</b></h6>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">

                            <button type="button" class="btn btn-success">Guardar
                            </button>
                            <button type="button" class="btn btn-default"
                                    onclick="parent.location='http://localhost:8080/BIDGroupLines/siad/request-spending-detail-current'">
                                Cancelar
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!--TERMINA MODAL DE LA PAGINA 27 A LA 28 ORDEN DE COMPRA-->

            <!--EMPIEZA VISTA DETALLE DE SOLICITUD PAGINA 29-->
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
                                    <td class="col-md-3"><u>Gustavo Morales</u></td>
                                    <td class="col-md-3"><u>Desarrollo</u></td>
                                    <td class="col-md-3"><u>30/05/2017</u></td>
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
                                    <td class="col-md-4"><u>Administracion</u></td>
                                    <td class="col-md-4"><u>Equipo de computo</u></td>
                                    <td class="col-md-4"></td>
                                </tr>
                                </tbody>
                                <thead style="background-color: #BDBDBD">
                                <th class="col-md-4">Lista de productos</th>
                                <th class="col-md-4">Cantidad</th>
                                <th class="col-md-4">Justificacion</th>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="col-md-4"></td>
                                    <td class="col-md-2"></td>
                                    <td class="col-md-6" rowspan="8">
                                        <div class="form-group">
                                    <textarea class="form-control" rows="4" v-model="requestBody.request.purpose"
                                              readonly disabled>
                                    </textarea>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-8"><br>
                            <h5><b style="color: black">Cotizacion 1</b></h5>
                        </div>
                        <form><br>
                            <div class="col-md-4">
                                <label>Monto cotizado sin IVA</label>
                                <input class="form-control" type="text" placeholder="$" value="1500" style="width: 40%"
                                       v-model="estimationsRequest.amount | currency" maxlength="14" disabled/>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #AFAFAF">
                            <h6><b>Documento</b></h6>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-2"><br>
                            <h6>Nombre del archivo</h6>
                        </div>
                        <div class="col-md-7"><br>
                            <a href="C:\Users\Leonardo\Downloads\facturandum.pdf" download="facturandum.pdf">
                                <button class="btn btn-primary btn-sm">DESCARGAR</button>
                            </a>
                        </div>
                        <div class="col-md-3"><br>
                            <span id="detalleGreen1" class="glyphicon glyphicon-ok" style="margin-left: 65%"></span>
                        </div>
                        <br>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #AFAFAF">
                            <h6><b>Justificacion</b></h6>
                        </div>
                        <br>
                        <div class="col-md-12"><br>
                            <h6>
                                sjfhaskjldfhkjlsfhgsfkjgdfsasdkjagsfkjghjgfhjdgshjdgsfjhdgsfjhdgsfjsdgfjdfsgjf</h6>
                        </div>
                        <br><br><br>
                    </div>
                </div>
                <br>
            </div>
            <div class="panel panel default" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Orden de compra</b></h5>
                            <table class="table table-striped">
                                <thead>
                                <th class="col-md-4" style="background-color: #aaaaaa">Numero de orden de
                                    compra
                                </th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Monto total</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Fecha de generacion
                                </th>
                                <th style="background-color: #aaaaaa"></th>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="col-md-3">4039</td>
                                    <td class="col-md-3">$10,000,000</td>
                                    <td class="col-md-3">30/05/2017</td>
                                    <td class="col-md-3">
                                        <button type="button" class="btn btn-danger btn-sm">Cancelar
                                        </button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-11">
                            <button type="button" class="btn btn-warning btn-sm" data-toggle="modal"
                                    data-target="#activadatos">Agregar Comprobante
                            </button>
                        </div>
                        <div class="col-md-1">
                            <button type="button" class="btn btn-default btn-sm"
                                    onclick="parent.location='http://localhost:8080/BIDGroupLines/siad/request-spending-detail-current'">
                                Cancelar
                            </button>
                        </div>
                    </div>
                </div>
            </div>
                <%--</div>--%>

            <!--TERMINA VISTA DETALLE DE SOLICITUD PAGINA 29-->
            <!--EMPIEZA MODAL DE ACTIVAR CAMPOS PAGINA 29-->

            <div class="modal fade" id="activadatos" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content" style="background-color: #f2f2f2">
                        <div class="modal-body">
                            <div class="panel panel-default" style="background-color: #f2f2f2">
                                <div class="row">
                                    <div class="col-md-12"><br>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Concepto</b></h6></label><br><br>
                                            <p><b><h6><b>Equipo de computo</b></h6></b></p>
                                        </div>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Número de factura</b></h6>
                                            </label><br><br>
                                            <input class="form-control" type="text" placeholder="$" value="1500"
                                                   style="width: 90%"
                                                   v-model="estimationsRequest.amount | currency" maxlength="14"
                                                   disabled/>
                                        </div>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Concepto factura</b></h6></label><br><br>
                                            <input type="text" disabled value="Computadora" class="form-control">
                                        </div>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Monto con iva</b></h6></label><br><br>
                                            <input class="form-control" type="text" placeholder="$" value="1500"
                                                   style="width: 90%"
                                                   v-model="estimationsRequest.amount | currency" maxlength="14"
                                                   disabled/>
                                        </div>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Monto sin iva</b></h6></label><br><br>
                                            <input class="form-control" type="text" placeholder="$" value="1500"
                                                   style="width: 90%"
                                                   v-model="estimationsRequest.amount | currency" maxlength="14"
                                                   disabled/>
                                        </div>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Comprobante</b></h6></label><br><br>
                                            <button class="btn btn-primary btn-xs" @click="getEmployees"
                                                    data-toggle="modal"
                                                    data-target="#pdfyxml">Carga de documentos
                                            </button>
                                        </div>
                                    </div>
                                    <br>
                                    <br>
                                    <br>
                                    <div class="col-md-12"><br>
                                        <div class="col-xs-3">
                                            <label><h6><b style="color: black">Proveedor</b></h6></label><br><br>
                                            <select class="form-control" v-model="ticket.incidence">
                                                <option></option>
                                                <option v-for="incidence in incidences" value="{{incidence}}">
                                                    {{incidence.incidenceName}}
                                                </option>
                                            </select><br><br>
                                        </div>
                                        <div class="col-xs-3">
                                            <label><h6><b style="color: black">Cuenta bancaria</b></h6></label><br><br>
                                            <select class="form-control" v-model="ticket.incidence">
                                                <option></option>
                                                <option v-for="incidence in incidences" value="{{incidence}}">
                                                    {{incidence.incidenceName}}
                                                </option>
                                            </select><br><br>
                                        </div>
                                        <div class="col-xs-2">
                                            <label><h6><b style="color: black">Tipo de moneda</b></h6></label><br><br>
                                            <input class="form-control" type="text" placeholder="$" value="1500"
                                                   style="width: 90%"
                                                   v-model="estimationsRequest.amount | currency" maxlength="14"
                                                   disabled/><br><br>
                                        </div>
                                        <div class="col-xs-2">
                                            <label><h6><b style="color: black">Tipo de cambio</b></h6></label><br><br>
                                            <input class="form-control" type="text" placeholder="$" value="1500"
                                                   style="width: 90%"
                                                   v-model="estimationsRequest.amount | currency" maxlength="14"
                                                   disabled/><br><br>
                                        </div>
                                        <div class="col-xs-2">
                                            <label><h6><b style="color: black">Monto total</b></h6></label><br><br>
                                            <input class="form-control" type="text" placeholder="$" value="1500"
                                                   style="width: 90%"
                                                   v-model="estimationsRequest.amount | currency" maxlength="14"
                                                   disabled/><br><br>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" data-dismiss="modal">Guardar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

            <!--TERMINA MODAL DE ACTIVAR CAMPOS PAGINA 29-->
            <!--EMPIEZA MODAL CARGAR DOCUMENTOS PDF, XML, PAGINA 30-->

            <div class="modal fade" id="pdfyxml" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <div>
                                <h5 class="modal-title">Carga de documentos</h5>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="col-md-12">
                                            <form class="form-inline" enctype="multipart/form-data"
                                                  v-on:submit.prevent="saveFile">
                                                <div class="form-group">
                                                    <label>Factura PDF</label>
                                                    <input @change="setFile($event, docType)" type="file"
                                                           class="form-control"
                                                           :disabled="isSaving"
                                                           :name="'file-type-' + docType.documentType.idDocumentType"
                                                           accept="application/pdf" required/>
                                                </div>
                                            </form>
                                        </div>
                                        <br><br><br>
                                        <div class="col-md-12">
                                            <form class="form-inline" enctype="multipart/form-data"
                                                  v-on:submit.prevent="saveFile">
                                                <div class="form-group">
                                                    <label>Factura XML</label>
                                                    <input @change="setFile($event, docType)" type="file"
                                                           class="form-control"
                                                           :disabled="isSaving"
                                                           :name="'file-type-' + docType.documentType.idDocumentType"
                                                           accept=".xml" required/>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-success">Guardar
                                </button>
                                <button type="button" class="btn btn-default"
                                        onclick="parent.location='http://localhost:8080/BIDGroupLines/siad/request-spending-detail-current'">
                                    Cancelar
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!--TERMINA MODAL CARGAR DOCUMENTOS PDF, XML, PAGINA 30-->
            <!--EMPIEZA MODAL FACTURA PAGINA 31-->

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
                                    <td class="col-md-3"><u>Gustavo Morales</u></td>
                                    <td class="col-md-3"><u>Desarrollo</u></td>
                                    <td class="col-md-3"><u>30/05/2017</u></td>
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
                                    <td class="col-md-3"><u>Adminsitracion</u></td>
                                    <td class="col-md-1"><u>Adminsitracion</u></td>
                                    <td class="col-md-8"></td>
                                </tr>
                                </tbody>
                                <thead style="background-color: #BDBDBD">
                                <th class="col-md-3">Lista de productos</th>
                                <th class="col-md-1">Cantidad</th>
                                <th class="col-md-8">Justificacion</th>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="col-md-4"></td>
                                    <td class="col-md-2"></td>
                                    <td class="col-md-6" rowspan="8">
                                        <div class="form-group">
                                    <textarea class="form-control" rows="4" v-model="requestBody.request.purpose"
                                              readonly disabled>
                                    </textarea>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-9"><br>
                            <h5><b style="color: black">Cotizacion 1</b></h5>
                        </div>
                        <form><br>
                            <div class="col-md-3">
                                <label>Monto cotizado sin IVA</label>
                                <input class="form-control" type="text" placeholder="$" value="1500" style="width: 40%"
                                       v-model="estimationsRequest.amount | currency" maxlength="14" disabled/>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #AFAFAF">
                            <h6><b>Documento</b></h6>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-2"><br>
                            <h6>Nombre del archivo</h6>
                        </div>
                        <div class="col-md-7"><br>
                            <a href="C:\Users\Leonardo\Downloads\facturandum.pdf" download="facturandum.pdf">
                                <button class="btn btn-primary btn-sm">DESCARGAR</button>
                            </a>
                        </div>
                        <div class="col-md-3"><br>
                            <span id="detalleGreen2" class="glyphicon glyphicon-ok" style="margin-left: 65%"></span>
                        </div>
                        <br>
                    </div>
                </div>
                <br>
                <br>
            </div>
            <div class="panel panel default" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Orden de compra</b></h5>
                            <table class="table table-striped">
                                <div class="col-md-12">
                                    <thead>
                                    <th class="col-md-3" style="background-color: #aaaaaa">Numero de orden de compra
                                    </th>
                                    <th class="col-md-3" style="background-color: #aaaaaa">Montototal</th>
                                    <th class="col-md-3" style="background-color: #aaaaaa">Fecha degeneracion</th>
                                    <th class="col-md-3" style="background-color: #aaaaaa"></th>
                                    </thead>
                                </div>
                                <div class="col-md-12">
                                    <tbody>
                                    <tr>
                                        <td class="col-md-3">4093</td>
                                        <td class="col-md-3">$20,000</td>
                                        <td class="col-md-3">30/05/2017</td>
                                        <td class="col-md-3">
                                            <a href="C:\Users\Leonardo\Downloads\facturandum.pdf"
                                               download="facturandum.pdf">
                                                <button class="btn btn-primary btn-sm">DESCARGAR</button>
                                            </a>
                                        </td>
                                    </tr>
                                    </tbody>
                                </div>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel default" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Comprobante</b></h5>
                            <div class="col-xs-6">
                                <label>Proveedor</label><br><br>
                                <select class="form-control">
                                    <option></option>
                                    <option value="{{incidence}}">
                                        {{incidence.incidenceName}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-5">
                                <label>Monto total</label><br><br>
                                <input type="number" class="form-control" placeholder="$"
                                       onpaste="alert('Acceso Denegado');return false">
                            </div>
                            <div class="col-xs-1">
                                <label></label><br><br>
                                <button class="btn btn-danger btn-sm" style="margin-top: 10%">
                                    Eliminar
                                </button>
                                <br><br>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-11">
                        <button type="button" class="btn btn-success btn-sm"
                                style="margin-left: 97%" data-toggle="modal" data-target="#botonenviar">Enviar
                        </button>
                    </div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-default btn-sm">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
        <!--TERMINA MODAL FACTURA PAGINA 31-->
        <!--EMPIEZA MODAL PAGINA 32 DEL BOTON ENVIAR-->

        <div class="modal fade" id="botonenviar" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Enviar solicitud</h4>
                    </div>
                    <div class="modal-body">
                        <p>La solicitud sera enviada al adminsitrador de pagos,</p>
                        <p>para procesar el pago la factura</p>
                        <br>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success" data-dismiss="modal">Aceptar</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>

        <!--TERMINA MODAL PAGINA 32 DEL BOTON ENVIAR-->

        <%--<div class="container">--%>
        <%--<h2>Simple Collapsible</h2>--%>
        <%--<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#demo">1</button>--%>
        <%--<div id="demo" class="collapse">--%>
        <%--h--%>
        <%--</div>--%>
        <%--</div>--%>

        <!--EMPIEZA DETALLE DE SOLICITUD VIGENTE PAGINA 40-->

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
                                <td class="col-md-3"><u>Gustavo Morales</u></td>
                                <td class="col-md-3"><u>Desarrollo</u></td>
                                <td class="col-md-3"><u>30/05/2017</u></td>
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
                                <td class="col-md-3"><u>Adminsitracion</u></td>
                                <td class="col-md-1"><u>Adminsitracion</u></td>
                                <td class="col-md-8"></td>
                            </tr>
                            </tbody>
                            <thead style="background-color: #BDBDBD">
                            <th class="col-md-3">Lista de productos</th>
                            <th class="col-md-1">Cantidad</th>
                            <th class="col-md-8">Justificacion</th>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="col-md-4"></td>
                                <td class="col-md-2"></td>
                                <td class="col-md-6" rowspan="8">
                                    <div class="form-group">
                                    <textarea class="form-control" rows="4" v-model="requestBody.request.purpose"
                                              readonly disabled>
                                    </textarea>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="col-md-12">
                            <h5><b style="color: black">Informacion</b></h5>
                            <table class="table table-striped">
                                <thead style="background-color: #BDBDBD">
                                <th class="col-md-2">Usuario</th>
                                <th class="col-md-3">Fecha</th>
                                <th class="col-md-1"></th>
                                <th class="col-md-3">Estado</th>
                                </thead>
                                <tbody>
                                <tr v-for="buy in buys">
                                    <td class="col-md-2">Guillermo Pita</td>
                                    <td class="col-md-3">30/05/2017</td>
                                    <td class="col-md-1"><span class="glyphicon glyphicon-triangle-bottom"
                                                               style="color: #58FA58; font-size: 200%"
                                                               v-if="request.requestStatus == 1"></span>
                                    <td class="col-md-3">En proceso de validacion</td>
                                </tr>
                                </tbody>
                                <!--ELIMINAR ESTE SOLO ES PROVISIONAL PARA EL CASCARON-->
                                <tr v-for="buy in buys">
                                    <td class="col-md-2">Anita Pincay</td>
                                    <td class="col-md-3">30/05/2017</td>
                                    <td class="col-md-1"><span class="glyphicon glyphicon-triangle-bottom"
                                                               style="color: #B40404; font-size: 200%"
                                                               v-if="request.requestStatus == 1"></span></td>
                                    <td class="col-md-3">Rechazada</td>
                                </tr>
                                <!--ELIMINAR ESTE SOLO ES PROVISIONAL PARA EL CASCARON-->
                            </table>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-12" style="background-color: #BDBDBD">
                                    <h6><b>Justificacion</b></h6>
                                </div>
                                <br>
                                <div class="col-md-12"><br>
                                    <h6>
                                        sjfhaskjldfhkjlsfhgsfkjgdfsasdkjagsfkjghjgfhjdgshjdgsfjhdgsfjhdgsfjsdgfjdfsgjf
                                    </h6>
                                </div>
                                <br><br><br>

                            </div>
                        </div>
                        <div id="accordion" role="tablist" aria-multiselectable="true">
                            <div class="card">
                                <div class="card-header" role="tab" id="headingThree">
                                    <div style="margin-left: 45%">
                                        <a class="collapsed" data-toggle="collapse" data-parent="#accordion"
                                           href="#collapseThree"
                                           @click="icon = !icon"
                                           aria-expanded="false" aria-controls="collapseThree">
                                            <div style="color: #000000">
                                                <b>Detalle</b><br>
                                                <span class="glyphicon glyphicon-chevron-down" v-if="icon == false"
                                                      style="margin-left: 2.5%"></span>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                                <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="headingThree">
                                    <div class="card-block">
                                            <%-- subir arhivos de cotizacion--%>
                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <div class="col-md-12">
                                                    <div class="row">
                                                        <table class="table table-striped">
                                                            <tr>
                                                                <td class="col-md-4" align="left"><b>Documento
                                                                    Cotización</b></td>
                                                                <td class="col-md-4" align="center"><b>Monto cotización
                                                                    sin IVA</b></td>
                                                                <td class="col-md-4" align="right"><b>Descargar</b></td>
                                                            </tr>
                                                            <tr v-for="estimationsRequest in request.priceEstimationsList">
                                                                <td class="col-md-4" align="left">
                                                                    {{estimationsRequest.fileName}}
                                                                </td>
                                                                <td class="col-md-4" align="center">
                                                                    <input class="form-control" type="text"
                                                                           placeholder="$" style="width: 40%"
                                                                           v-model="estimationsRequest.amount | currency"
                                                                           maxlength="14" disabled/>
                                                                </td>
                                                                <td class="col-md-4" align="right">
                                                                    <a class="btn btn-default"
                                                                       :href="downloadUrl + estimationsRequest.idPriceEstimation"
                                                                       data-toggle="tooltip" data-placement="top"
                                                                       title="Descargar">
                                                                        <span style="margin-left: 20%"
                                                                              class="glyphicon glyphicon-download-alt"></span>
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default">Salir</button>
                        </div>
                    </div>
                </div>
                <br>
                <br>
                <!--TERMINA DETALLE DE SOLICITUD VIGENTE PAGINA 40-->
            </div>
        </div>
    </jsp:body>
</t:template>