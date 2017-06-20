<%--
  Created by IntelliJ IDEA.
  User: jcesar
  Date: 15/05/2017
  Time: 02:00 PM
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
                },
                data: {
                    requestInForce: [],
                    requestRejected: [],
                    requestFinished: [],
                    user: {},
                    detailUrl: ROOT_URL + "/siad/requests-detail?idRequest=",
                    folio: '',
                    request: null
                },
                methods: {
                    getUserInSession: function () {
                        this.$http.get(ROOT_URL + "/user")
                            .success(function (data) {
                                this.user = data;
                                this.getRequestInForce();
                                this.getRequestRejected();
                                this.getRequestFinished();
                            })
                            .error(function (data) {
                                showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                            });
                    },
                    getRequestInForce: function () {
                        this.$http.get(ROOT_URL + "/requests/category/"+1+"/type/"+1+"/employee/"+this.user.dwEmployee.idEmployee).success(function (data) {
                            var jsonObjectIndex = {};

                            data.forEach(function (requests) {
                                if (isNaN(requests.distributorCostCenter)) {
                                    jsonObjectIndex[requests.distributorCostCenter._id] = requests.distributorCostCenter;
                                } else {
                                    requests.distributorCostCenter = jsonObjectIndex[requests.distributorCostCenter];
                                }
                            });

                            this.requestInForce = data;
                        }).error(function () {
                            showAlert("Error al obtener información de s. vigentes", {type: 3});
                        });
                    },
                    getRequestRejected: function () {
                        this.$http.get(ROOT_URL + "/requests/category/"+1+"/type/"+3+"/employee/"+this.user.dwEmployee.idEmployee).success(function (data) {

                            var jsonObjectIndex = {};

                            data.forEach(function (requests) {
                                if (isNaN(requests.distributorCostCenter)) {
                                    jsonObjectIndex[requests.distributorCostCenter._id] = requests.distributorCostCenter;
                                } else {
                                    requests.distributorCostCenter = jsonObjectIndex[requests.distributorCostCenter];
                                }
                            });

                            this.requestRejected = data;
                        }).error(function () {
                            showAlert("Error al obtener información de s. rechazadas", {type: 3});
                        });
                    },
                    getRequestFinished: function () {
                        this.$http.get(ROOT_URL + "/requests/category/"+1+"/type/"+2+"/employee/"+this.user.dwEmployee.idEmployee).success(function (data) {

                            var jsonObjectIndex = {};

                            data.forEach(function (requests) {
                                if (isNaN(requests.distributorCostCenter)) {
                                    jsonObjectIndex[requests.distributorCostCenter._id] = requests.distributorCostCenter;
                                } else {
                                    requests.distributorCostCenter = jsonObjectIndex[requests.distributorCostCenter];
                                }
                            });

                            this.requestFinished = data;
                        }).error(function () {
                            showAlert("Error al obtener información de s. finalizadas", {type: 3});
                        });
                    },
                    findByFolio: function () {
                        if(this.folio.length > 0){
                            this.$http.get(ROOT_URL + "/requests/folio?folio=" + this.folio).success(function (data) {
                                this.request = data;
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            })
                        }
                    },
                    openModalFindByFolio: function () {
                        $("#modalFolio").modal("show");
                        this.folio = "";
                        this.request = null;
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
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
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
            <div class="col-md-12">
                <div class="col-md-3">
                    <h2>Compras</h2>
                </div>

                <div class="col-md-2 text-right">
                    <a href="../siad/capture-request-buy">
                        <button class="btn btn-warning" style="margin-top: 25px">Capturar una solicitud</button>
                    </a>
                </div>
                <div class="col-md-4">
                        <div class="col-md-8">
                            <button class="btn btn-info" @click="openModalFindByFolio()" style="margin-top: 25px">Buscar por folio</button>
                        </div>
                </div>
                <div class="col-md-3 text-right" style="margin-top: 10px">
                    <label>Solicitante</label>
                    <p>
                        <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                    </p>
                </div>
            </div>
        </div>
        <br>
        <%-- vigentes--%>
        <div id="accordion" role="tablist" aria-multiselectable="true">
            <div class="panel panel-default">
                <div class="card">
                    <div class="card-header" role="tab" id="headingThree">
                        <div class="panel-heading" style="background-color: #7AC5CD">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree"
                               aria-expanded="false" aria-controls="collapseThree">
                                <div class="col-md-11 text-center">
                                    <b style="color: black">Vigentes</b>
                                </div>
                            </a>
                            <div class="col-md-1 text-right">
                                <label class="circleyel"></label>
                            </div>
                            <br>
                        </div>
                    </div>
                    <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <tr>
                                                <td class="col-md-3 text-center"><b>Concepto de solicitud</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha de solicitud</b></td>
                                                <td class="col-md-3 text-center"><b>Folio</b></td>
                                                <td class="col-md-3 text-center"><b>Detalle</b></td>
                                            </tr>
                                            <tr v-for="request in requestInForce">
                                                <td class="col-md-3 text-center">{{request.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</td>
                                                <td class="col-md-3 text-center">{{request.creationDateFormats.dateNumber}}</td>
                                                <td class="col-md-3 text-center">{{request.folio}}</td>
                                                <td class="col-md-3 text-center">
                                                    <a class="btn btn-default btn-sm"
                                                       :href="detailUrl + request.idRequest"
                                                       data-toggle="tooltip" data-placement="top" title="Detalle">
                                                        <span class="glyphicon glyphicon-new-window"></span>
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
            <div class="panel panel-default">
                <div class="card">
                    <div class="card-header" role="tab" id="headingTwo">
                        <div class="panel-heading" style="background-color: #7AC5CD">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"
                               aria-expanded="false" aria-controls="collapseThree">
                                <div class="col-md-11 text-center">
                                    <b style="color: black">Finalizadas</b>
                                </div>
                            </a>
                            <div class="col-md-1 text-right">
                                <label class="circlegre"></label>
                            </div>
                            <br>
                        </div>
                    </div>
                    <div id="collapseTwo" class="collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <tr>
                                                <td class="col-md-3 text-center"><b>Concepto de solicitud 1</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha de solicitud</b></td>
                                                <td class="col-md-3 text-center"><b>Folio</b></td>
                                                <td class="col-md-3 text-center"><b>Detalle</b></td>
                                            </tr>
                                            <tr v-for="request in requestFinished">
                                                <td class="col-md-3 text-center">{{request.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</td>
                                                <td class="col-md-3 text-center">{{request.creationDateFormats.dateNumber}}</td>
                                                <td class="col-md-3 text-center">{{request.folio}}</td>
                                                <td class="col-md-3 text-center">
                                                    <a class="btn btn-default btn-sm"
                                                       :href="detailUrl + request.idRequest"
                                                       data-toggle="tooltip" data-placement="top" title="Detalle">
                                                        <span class="glyphicon glyphicon-new-window"></span>
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

            <div class="panel panel-default">
                <div class="card">
                    <div class="card-header" role="tab" id="headingOne">
                        <div class="panel-heading" style="background-color: #7AC5CD">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                               aria-expanded="false" aria-controls="collapseOne">
                                <div class="col-md-11 text-center">
                                    <b style="color: black">Rechazadas</b>
                                </div>
                            </a>
                            <div class="col-md-1 text-right">
                                <label class="circlered"></label>
                            </div>
                            <br>
                        </div>
                    </div>
                    <div id="collapseOne" class="collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <tr>
                                                <td class="col-md-3 text-center"><b>Concepto de solicitud 1</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha de solicitud</b></td>
                                                <td class="col-md-3 text-center"><b>Folio</b></td>
                                                <td class="col-md-3 text-center"><b>Detalle</b></td>
                                            </tr>
                                            <tr v-for="request in requestRejected">
                                                <td class="col-md-3 text-center">{{request.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</td>
                                                <td class="col-md-3 text-center">{{request.creationDateFormats.dateNumber}}</td>
                                                <td class="col-md-3 text-center">{{request.folio}}</td>
                                                <td class="col-md-3 text-center">
                                                    <a class="btn btn-default btn-sm"
                                                       :href="detailUrl + request.idRequest"
                                                       data-toggle="tooltip" data-placement="top" title="Detalle">
                                                        <span class="glyphicon glyphicon-new-window"></span>
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




            <div class="modal fade" id="modalFolio" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Buscar por folio</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-3">
                                        <label>Ingresa un folio</label>
                                        <input class="form-group" v-model="folio" @input="findByFolio()">
                                    </div>
                                </div>
                            </div>
                            <div class="row" v-if="request != null">
                                <div class="col-md-12">
                                    <div class="col-md-12">
                                        <table class="table table-striped">
                                            <thead>
                                                <th class="col-md-3 text-center"><b>Concepto de solicitud</b></th>
                                                <th class="col-md-3 text-center"><b>Fecha de solicitud</b></th>
                                                <th class="col-md-3 text-center"><b>Folio</b></th>
                                                <th class="col-md-3 text-center"><b>Detalle</b></th>
                                            </thead>
                                            <tr>
                                                <td class="col-md-3 text-center">{{request.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</td>
                                                <td class="col-md-3 text-center">{{request.creationDateFormats.dateNumber}}</td>
                                                <td class="col-md-3 text-center">{{request.folio}}</td>
                                                <td class="col-md-3 text-center">
                                                    <a class="btn btn-default btn-sm"
                                                       :href="detailUrl + request.idRequest"
                                                       data-toggle="tooltip" data-placement="top" title="Detalle">
                                                        <span class="glyphicon glyphicon-new-window"></span>
                                                    </a>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Salir</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <%--termina archivos de cotizacion--%>
    </jsp:body>
</t:template>

