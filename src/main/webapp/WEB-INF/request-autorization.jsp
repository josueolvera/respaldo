<%--
  Created by IntelliJ IDEA.
  User: jcesar
  Date: 01/06/2017
  Time: 11:46 AM
  To change this template use File | Settings | File Templates.
--%>
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
                },
                data: {
                    requestInForce: [],
                    user: {},
                    detailUrl: ROOT_URL + "/siad/requests-off-budget?idRequest=",
                    folio: '',
                    request: null
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
                                this.getRequestInForce();
                            })
                            .error(function (data) {
                                showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                            });
                    },
                    getRequestInForce: function () {
                        this.$http.get(ROOT_URL + "/requests/by-status/" + 4).success(function (data) {
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
                    findByFolio: function () {
                        if (this.folio.length > 0) {
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
                <div class="col-md-5" style="margin-top: 20px">
                    <h2>Validación de solicitud</h2>
                </div>
                <div class="col-md-3" style="margin-top: 35px">
                    <button class="btn btn-info" @click="openModalFindByFolio()">Buscar por folio</button>
                </div>
                <div class="col-md-4 text-right" style="margin-top: 20px">
                    <label>Nombre de Usuario</label>
                    <p>
                        <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                    </p>
                </div>
            </div>
            <br>
            <br>
            <div id="accordion" role="tablist" aria-multiselectable="true">
                <div class="card">
                    <div class="card-header" role="tab" id="headingThree">
                        <div class="panel-heading" style="background-color: #7AC5CD">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree"
                               aria-expanded="false" aria-controls="collapseThree">
                                <div class="col-md-11 text-center">
                                    <b style="color: black">VIGENTES</b>
                                </div>
                            </a>
                            <div class="col-md-1 text-right">
                            </div>
                            <br>
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
                                                <thead>
                                                <th class="col-md-2 text-center"><b>Empresa</b></th>
                                                <th class="col-md-2 text-center"><b>Centro de costos</b></th>
                                                <th class="col-md-3 text-center"><b>Concepto de solicitud</b></th>
                                                <th class="col-md-2 text-center"><b>Fecha de solicitud</b></th>
                                                <th class="col-md-2 text-center"><b>Folio</b></th>
                                                <th class="col-md-1 text-center"><b>Detalle</b></th>
                                                </thead>
                                                <tr v-for="request in requestInForce">
                                                    <td class="col-md-2 text-center">{{request.distributorCostCenter.distributors.acronyms}}</td>
                                                    <td class="col-md-2 text-center">{{request.distributorCostCenter.costCenter.name}}</td>
                                                    <td class="col-md-3 text-center">{{request.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</td>
                                                    <td class="col-md-2 text-center">{{request.creationDateFormats.dateNumber}}</td>
                                                    <td class="col-md-2 text-center">{{request.folio}}</td>
                                                    <td class="col-md-1 text-center">
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
                                            <th class="col-md-2 text-center"><b>Empresa</b></th>
                                            <th class="col-md-2 text-center"><b>Centro de costos</b></th>
                                            <th class="col-md-3 text-center"><b>Concepto de solicitud</b></th>
                                            <th class="col-md-2 text-center"><b>Fecha de solicitud</b></th>
                                            <th class="col-md-2 text-center"><b>Folio</b></th>
                                            <th class="col-md-1 text-center"><b>Detalle</b></th>
                                            </thead>
                                            <tr>
                                                <td class="col-md-2 text-center">{{request.distributorCostCenter.distributors.acronyms}}</td>
                                                <td class="col-md-2 text-center">{{request.distributorCostCenter.costCenter.name}}</td>
                                                <td class="col-md-3 text-center">{{request.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</td>
                                                <td class="col-md-2 text-center">{{request.creationDateFormats.dateNumber}}</td>
                                                <td class="col-md-2 text-center">{{request.folio}}</td>
                                                <td class="col-md-1 text-center">
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

    </jsp:body>
</t:template>

