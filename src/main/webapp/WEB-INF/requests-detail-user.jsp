<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>

<t:template pageTitle="BID Group: Solicitud">
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
                    idRequest: ${idRequest},
                    user: {},
                    icon: false,
                    request:{},
                    downloadUrl: ROOT_URL + '/estimations/attachment/download/'
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
                    showModalSolicitud: function () {
                        $('#modalSolicitud').modal('show');
                    },
                    getRequestInformation: function () {
                        this.$http.get(ROOT_URL + "/requests/" + this.idRequest)
                            .success(function (data) {
                                this.request = data;
                            })
                            .error(function (data) {

                            });
                    },
                    downloadEstimationDocument: function (idEstimation) {
                        this.$http.get(ROOT_URL)
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
        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="row">
                <div class="col-md-8">
                    <h2>Detalle de solicitud/vigente <label class="circlegre"></label></h2>
                </div>
                <div class="col-md-4 text-right" style="margin-top: 10px">
                    <label>Solicitante</label>
                    <p>
                        <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                    </p>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-3">
                        <label>Centro de costos</label><br>
                        <u>{{request.distributorCostCenter.costCenter.name}}</u>
                    </div>
                    <div class="col-md-4">
                        <label>Concepto</label><br>
                        <u>{{request.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</u>
                    </div>
                    <div class="col-md-4">

                    </div>
                </div>
            </div>
                <%--tabla de cotenido de productos--%>
            <div class="row">
                <div class="col-md-12">
                    <table class="table">
                        <thead>
                        <th class="col-md-3">Lista de productos</th>
                        <th class="col-md-1">Cantidad</th>
                        <th class="col-md-2"></th>
                        <th class="col-md-4">Justificación</th>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="col-md-3"></td>
                            <td class="col-md-1"></td>
                            <td class="col-md-2"></td>
                            <td class="col-md-2" rowspan="8">
                                <div class="form-group">
                                    <textarea class="form-control" rows="4" v-model="request.reason"
                                              readonly>

                                    </textarea>
                                </div>
                            </td>
                        </tr>
                        <tr v-for="productRequest in request.requestProductsList">
                            <td class="col-md-3">{{productRequest.roleProductRequest.cProductsRequest.productRequestName}}</td>
                            <td class="col-md-1"><input class="form-control" maxlength="3" type="text"
                                                        onclick="return cleanField(this)"
                                                        onkeypress="return validateFloatKeyPress(this,event)"
                                                        onInput="format(this)" onblur="ponerCeros(this)"
                                                        placeholder="0" v-model="productRequest.quantity" required/></td>
                            <td class="col-md-2">

                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
                <%--tabla de contenidos de productos--%>
            <br>
                <%-- informacion del usuario--%>
            <div class="panel panel-default">
                <div class="panel-heading">Información</div>
                <div class="panel-body">
                    <div class="col-md-12">
                        <div class="row">
                            <table class="table table-striped">
                                <tr>
                                    <td class="col-md-4"><b>Usuario</b></td>
                                    <td class="col-md-4"><b>Fecha</b></td>
                                    <td class="col-md-4"><b>Estado</b></td>
                                </tr>
                                <tr>
                                    <td class="col-md-4">{{request.employees.fullName}}</td>
                                    <td class="col-md-4">{{request.creationDateFormats.dateNumber}}</td>
                                    <td class="col-md-4">{{request.requestStatus.requestStatus}}</td>
                                </tr>
                            </table>

                        </div>
                        <div style="margin-left: 80%">
                            <button @click="showModalSolicitud()" class="btn btn-danger">Cancelar Solicitud</button>
                            <a href="javascript:window.history.back();">
                                <button type="button" class="btn btn-default">Salir</button>
                            </a>
                        </div>
                        <br>

                    </div>
                </div>
            </div>
            <div id="accordion" role="tablist" aria-multiselectable="true">
                <div class="card">
                    <div class="card-header" role="tab" id="headingThree">
                        <div style="margin-left: 45%">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree"
                               @click="icon = !icon"
                               aria-expanded="false" aria-controls="collapseThree">
                                <div>
                                    <b>Detalle</b><br>
                                    <span class="glyphicon glyphicon-chevron-down" v-if="icon == false"
                                          style="margin-left: 2.5%"></span>
                                    <span class="glyphicon glyphicon-chevron-up" v-if="icon == true"
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
                                                    <td class="col-md-4" align="left"><b>Documento Cotización</b></td>
                                                    <td class="col-md-4" align="center"><b>Monto cotización sin IVA</b></td>
                                                    <td class="col-md-4" align="right"><b>Descargar</b></td>
                                                </tr>
                                                <tr v-for="estimationsRequest in request.priceEstimationsList">
                                                    <td class="col-md-4" align="left">{{estimationsRequest.fileName}}</td>
                                                    <td class="col-md-4" align="center">
                                                        <input class="form-control" type="text"
                                                               placeholder="$" style="width: 40%" v-model="estimationsRequest.amount | currency"
                                                               maxlength="14" readonly />
                                                    </td>
                                                    <td class="col-md-4" align="right">
                                                        <a class="btn btn-default" :href="downloadUrl + estimationsRequest.idPriceEstimation"
                                                           data-toggle="tooltip" data-placement="top" title="Descargar">
                                                            <span style="margin-left: 20%" class="glyphicon glyphicon-download-alt"></span>
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
                <%--modal solicitar--%>
            <div class="modal fade" id="modalSolicitud" tabindex="-1" role="dialog" aria-labelledby=""
                 aria-hidden="true">
                <div class="modal-dialog modal-ms">
                    <div class="modal-content modal-ms">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <div class="alert">
                                <h4 class="modal-title" id="" style="text-align: center"><label>Enviar solicitud</label>
                                </h4>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <p align="center" style="font-size: 16px">Se cancelará la solicitud y se borrará<br>
                                    el registro de la misma.</p>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="sendRequest()">Aceptar</button>
                            <button type="button" class="btn btn-default" class="close" data-dismiss="modal"
                                    aria-hidden="true">Cancelar
                            </button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </jsp:body>
</t:template>

