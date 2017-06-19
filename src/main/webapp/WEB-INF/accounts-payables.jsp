<%--
  Created by IntelliJ IDEA.
  User: jcesar
  Date: 07/06/2017
  Time: 02:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>
<t:template pageTitle="BID Group: Cuentas por pagar">
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
                    this.getCurrencies();
                    this.getPurchaseInvoice();
                },
                data: {
                    roleCostCenterList: [],
                    costCenterList: [],
                    budgetCategories: [],
                    budgetSubcategories: [],
                    products: [],
                    providers: [],
                    providerAccounts: [],
                    currencies: [],
                    requestProducts: [],
                    user: {},
                    icon1: false,
                    icon2: false,
                    icon3: false,
                    icon4: false,
                    icon5: false,
                    icon6: false,
                    icon7: false,
                    icon8: false,
                    icon9: false,
                    icon10: false,
                    icon11: false,
                    icon12: false,
                    icon13: false,
                    icon14: false,
                    icon15: false,
                    purchaseInvoice: [],
                    detailUrl: ROOT_URL + "/siad/accounts-payables-detail?idRequest=" ,
                    detailTwoUrl: "&idProvider=" ,
                    detailThreeUrl: "&idPurchaseInvoices="
                },
                methods: {
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    GenerarDe: function () {
                        var fecha = new Date();
                        var fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
                        this.timePickerDe = $('#generarDe').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            defaultDate: fecha_actual
                        }).data();
                    },
                    GenerarA: function () {
                        var fechaDos = new Date();
                        this.timePickerA = $('#generarA').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                        }).data();
                    },
                    getUserInSession: function () {
                        this.$http.get(ROOT_URL + "/user")
                            .success(function (data) {
                                this.user = data;
                                this.getRolesCostCenter(this.user.dwEmployee.idRole);
                            })
                            .error(function (data) {
                                showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                            });
                    },
                    getRolesCostCenter: function (idRole) {
                        this.$http.get(ROOT_URL + '/roles-cost-center/role/' + idRole)
                            .success(function (data) {
                                var self = this;
                                var index;
                                this.rolesCostCenter = data;

                                if (data.length < 2) {
                                    this.selected.budgetNature = data[0].budgetNature;
                                }

                                data.forEach(function (item) {
                                    index = self.arrayObjectIndexOf(self.costCenterList, item.costCenter.idCostCenter, 'idCostCenter');
                                    if (index == -1) self.costCenterList.push(item.costCenter);
                                });

                                this.selected.costCenter = data[0].costCenter;

                                this.getBudgetCategories();

                            })
                            .error(function (data) {

                            });
                    },
                    //requests dates
                    getPurchaseInvoice: function () {
                        this.$http.get(ROOT_URL + '/purchase-invoice').success(function (data) {
                                this.purchaseInvoice = data;
                            });
                    },
                    onChangeCostCenter: function () {
                        this.budgetCategories = [];
                        this.budgetSubcategories = [];
                        this.products = [];
                        this.getBudgetCategories();
                    },
                    getBudgetCategories: function () {
                        this.$http.get(
                            ROOT_URL +
                            '/budget-categories?cost_center=' +
                            this.selected.costCenter.idCostCenter +
                            '&request_category=1'
                        ).success(function (data) {
                            this.budgetCategories = data;
                        });
                    },
                    getBudgetSubcategories: function () {
                        this.$http.get(ROOT_URL + '/budget-subcategories/category/' + this.selected.budgetCategory.idBudgetCategory)
                            .success(function (data) {
                                this.budgetSubcategories = data;
                            });
                    },
                    getProducts: function () {
                        this.$http.get(ROOT_URL + '/products/subcategory/' + this.selected.budgetSubcategory.idBudgetSubcategory)
                            .success(function (data) {
                                this.products = data;
                                this.selectProducts = this.createSelectForConcept(data);
                            });
                    },
                    getProviders: function () {
                        this.$http.get(ROOT_URL + '/providers/product-type/' + this.selected.budgetSubcategory.idBudgetSubcategory)
                            .success(function (data) {
                                this.providers = data;
                            });
                    },
                    onChangeBudgetCategory: function () {
                        this.budgetSubcategories = [];
                        this.products = [];
                        this.getBudgetSubcategories();
                    },
                    onChangeBudgetSubcategory: function () {
                        this.products = [];
                        this.getProducts();
                        this.getProviders();
                    },
                    clearRequest: function () {

                        this.requestBody = {
                            request: {
                                description: '',
                                purpose: '',
                                userResponsible: '',
                                idCostCenter: '',
                                idBudgetCategory: '',
                                idBudgetSubcategory: '',
                                idRequestCategory: ''
                            },
                            products: []
                        };

                        this.estimations = [];
                    },
                    showModalSolicitud: function () {
                        $('#modalSolicitud').modal('show');
                    },
                    showNewEstimationModal: function () {
                        this.newEstimationFormActive = true;
                        $('#newEstimationModal').modal('show');
                    },
                    hideNewEstimationModal: function () {
                        this.clearEstimation();
                        this.newEstimationFormActive = false;
                        $('#newEstimationModal').modal('hide');
                    },
                    getCurrencies: function () {
                        this.$http.get(ROOT_URL + '/currencies')
                            .success(function (data) {
                                this.currencies = data;
                            })
                            .error(function (data) {

                            });
                    },
                    onChangeCurrency: function () {
                        this.estimation.rate = (this.estimation.currency.idCurrency == 1) ? 1 : '';
                    },
                    setFile: function (event) {
                        var self = this;
                        var file = event.target.files[0];

                        if (this.validateFile(file)) {

                            var reader = new FileReader();

                            reader.onload = (function (theFile) {
                                return function (e) {
                                    self.estimation.file = {
                                        name: theFile.name,
                                        size: theFile.size,
                                        type: theFile.type,
                                        dataUrl: e.target.result
                                    };
                                };
                            })(file);
                            reader.readAsDataURL(file);
                        }
                    },
                    validateFile: function (file) {
                        if (file.type !== 'application/pdf') {
                            event.target.value = null;
                            showAlert("Tipo de archivo no admitido", {type: 3});
                            return false;
                        }

                        return true;
                    },
                    clearEstimation: function () {
                        this.estimation = {
                            amount: '',
                            provider: '',
                            account: '',
                            currency: '',
                            rate: '',
                            file: ''
                        };
                    },
                    addEstimation: function () {
                        var estimation = this.estimation;
                        this.estimations.push(estimation);
                        this.hideNewEstimationModal();
                    },
                    deleteEstimationFile: function (e) {
                        this.estimation.file = '';
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
                <div class="col-md-5">
                    <h2>Cuentas por pagar</h2>
                </div>

                <div class="col-md-1 text-right">

                </div>
                <div class="col-md-3">
                    <form style="margin-top: 5%">
                        <div class="col-md-8">
                            <label>Buscar por folio</label>
                            <input class="form-control" type="text" placeholder="folio" maxlength="30" required/>
                        </div>

                    </form>
                </div>
                <div class="col-md-3 text-right" style="margin-top: 1.5%">
                    <label>Nombre de usuario</label>
                    <p>
                        <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                    </p>
                </div>
            </div>
        </div>
        <br>
        <div class="row">
            <b>Cuentas</b>
            <table class="table table-striped">
                <tr>
                    <td class="col-md-2 text-center">
                        <select class="form-control">
                            <option value=""></option>
                            <option>Cuentas por pagar</option>
                            <option>Cuentas pagadas</option>
                        </select>
                    </td>
                    <td class="col-md-1 text-right"><label style="margin-top: 8%;">De </label>
                    </td>
                    <td class="col-md-3">
                        <div class='input-group date' id='generarDe'>
                            <input type='text' class="form-control" required>
                            <span class="input-group-addon" @click="GenerarDe()">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                        </div>
                    </td>
                    <td class="col-md-1 text-right"><label style="margin-top: 8%;">a </label>
                    </td>
                    <td class="col-md-3">
                        <div class='input-group date' id='generarA'>
                            <input type='text' class="form-control" required>
                            <span class="input-group-addon" @click="GenerarA()">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                        </div>
                    </td>
                    <td class="col-md-2 text-center">
                        <button type="button" style="width: 80%" class="btn btn-success">Generar</button>
                    </td>
                </tr>
            </table>
        </div>
        <br>
        <%-- start colapso--%>
        <div class="panel panel-success">
            <div class="panel-heading text-center">
                <b>Vigentes</b>
            </div>
            <div id="accordion1" role="tablist" aria-multiselectable="true">
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingThree">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion1" href="#collapseThree"
                               @click="icon1 = !icon1" aria-expanded="false" aria-controls="collapseThree">
                                <div class="col-md-12" style="color: #3C763D">
                                    <div class="col-md-3">
                                        <b>Compras de bienes</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon1 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon1 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr v-for="purchose in purchaseInvoice"
                                                v-if="purchose.request.requestCategory.idRequestCategory == 1
                                                        && purchose.request.requestType.idRequestType == 1">
                                                <td class="col-md-1 text-center">
                                                    {{purchose.request.requestCategory.requestCategoryName}}
                                                </td>
                                                <td class="col-md-3 text-center">
                                                    {{purchose.request.creationDateFormats.dateNumber}}
                                                </td>
                                                <td class="col-md-3 text-center">
                                                    {{}}
                                                </td>
                                                <td class="col-md-2 text-center">{{purchose.request.folio}}</td>
                                                <td class="col-md-2 text-center">
                                                    {{purchose.request.totalExpended | currency}}
                                                </td>
                                                <td class="col-md-1 text-center">
                                                    <a class="glyphicon glyphicon-new-window"
                                                       :href="detailUrl + purchose.idRequest +
                                                              detailTwoUrl + purchose.idProvider +
                                                              detailThreeUrl + purchose.idPurchaseInvoices"
                                                       data-toggle="tooltip" data-placement="top" title="Detalle">
                                                    </a>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingpayServices">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion1" href="#payServices"
                               @click="icon2 = !icon2" aria-expanded="false" aria-controls="payServices">
                                <div class="col-md-12" style="color: #3C763D">
                                    <div class="col-md-3">
                                        <b>Pago de servicios</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon2 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon2 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="payServices" class="collapse" role="tabpanel" aria-labelledby="headingpayServices">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingViatics">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion1" href="#Viatics"
                               @click="icon3 = !icon3" aria-expanded="false" aria-controls="Viatics">
                                <div class="col-md-12" style="color: #3C763D">
                                    <div class="col-md-3">
                                        <b>Viáticos</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon3 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon3 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="Viatics" class="collapse" role="tabpanel" aria-labelledby="headingViatics">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr v-for="purchose in purchaseInvoice"
                                                v-if="purchose.request.requestCategory.idRequestCategory == 2
                                                        && purchose.request.requestType.idRequestType == 1">
                                                <td class="col-md-1 text-center">
                                                    {{purchose.request.requestCategory.requestCategoryName}}
                                                </td>
                                                <td class="col-md-3 text-center">
                                                    {{purchose.request.creationDateFormats.dateNumber}}
                                                </td>
                                                <td class="col-md-3 text-center">
                                                    {{}}
                                                </td>
                                                <td class="col-md-2 text-center">{{purchose.request.folio}}</td>
                                                <td class="col-md-2 text-center">
                                                    {{purchose.request.totalExpended | currency}}
                                                </td>
                                                <td class="col-md-1 text-center">
                                                    <a class="glyphicon glyphicon-new-window"
                                                       :href="detailUrl + purchose.idRequest +
                                                              detailTwoUrl + purchose.idProvider +
                                                              detailThreeUrl + purchose.idPurchaseInvoices"
                                                       data-toggle="tooltip" data-placement="top" title="Detalle">
                                                    </a>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingPlaneTickes">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion1" href="#planeTickes"
                               @click="icon4 = !icon4" aria-expanded="false" aria-controls="planeTickes">
                                <div class="col-md-12" style="color: #3C763D">
                                    <div class="col-md-3">
                                        <b>Boletos de avión</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon4 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon4 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="planeTickes" class="collapse" role="tabpanel" aria-labelledby="headingPlaneTickes">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr v-for="purchose in purchaseInvoice"
                                                v-if="purchose.request.requestCategory.idRequestCategory == 3
                                                        && purchose.request.requestType.idRequestType == 1">
                                                <td class="col-md-1 text-center">
                                                    {{purchose.request.requestCategory.requestCategoryName}}
                                                </td>
                                                <td class="col-md-3 text-center">
                                                    {{purchose.request.creationDateFormats.dateNumber}}
                                                </td>
                                                <td class="col-md-3 text-center">
                                                    {{}}
                                                </td>
                                                <td class="col-md-2 text-center">{{purchose.request.folio}}</td>
                                                <td class="col-md-2 text-center">
                                                    {{purchose.request.totalExpended | currency}}
                                                </td>
                                                <td class="col-md-1 text-center">
                                                    <a class="glyphicon glyphicon-new-window"
                                                       :href="detailUrl + purchose.idRequest +
                                                              detailTwoUrl + purchose.idProvider +
                                                              detailThreeUrl + purchose.idPurchaseInvoices"
                                                       data-toggle="tooltip" data-placement="top" title="Detalle">
                                                    </a>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingRefounds">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion1" href="#refounds"
                               @click="icon5 = !icon5" aria-expanded="false" aria-controls="refounds">
                                <div class="col-md-12" style="color: #3C763D">
                                    <div class="col-md-3">
                                        <b>Reembolsos</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon5 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon5 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="refounds" class="collapse" role="tabpanel" aria-labelledby="headingRefounds">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-3 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr v-for="purchose in purchaseInvoice"
                                                v-if="purchose.request.requestCategory.idRequestCategory == 5
                                                        && purchose.request.requestType.idRequestType == 1">
                                                <td class="col-md-1 text-center">
                                                    {{purchose.request.requestCategory.requestCategoryName}}
                                                </td>
                                                <td class="col-md-3 text-center">
                                                    {{purchose.request.creationDateFormats.dateNumber}}
                                                </td>
                                                <td class="col-md-3 text-center">
                                                    {{}}
                                                </td>
                                                <td class="col-md-2 text-center">{{purchose.request.folio}}</td>
                                                <td class="col-md-2 text-center">
                                                    {{purchose.request.totalExpended | currency}}
                                                </td>
                                                <td class="col-md-1 text-center">
                                                    <a class="glyphicon glyphicon-new-window"
                                                       :href="detailUrl + purchose.idRequest +
                                                              detailTwoUrl + purchose.idProvider +
                                                              detailThreeUrl + purchose.idPurchaseInvoices"
                                                       data-toggle="tooltip" data-placement="top" title="Detalle">
                                                    </a>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
            </div>
        </div>
        <%-- finish colapso--%>

        <%-- start colapso--%>
        <div class="panel panel-warning">
            <div class="panel-heading text-center">
                <b>Reprogramadas en proceso de pago</b>
            </div>
            <div id="accordion2" role="tablist" aria-multiselectable="true">
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingThreeR">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion2" href="#collapseThreeR"
                               @click="icon6 = !icon6" aria-expanded="false" aria-controls="collapseThreeR">
                                <div class="col-md-12" style="color: #8A6D3B">
                                    <div class="col-md-3">
                                        <b>Compras de bienes</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon6 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon6 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="collapseThreeR" class="collapse" role="tabpanel" aria-labelledby="headingThreeR">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha Re-programada</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="col-md-1 text-center">Compra</td>
                                                <td class="col-md-2 text-center">16-03-2017</td>
                                                <td class="col-md-2 text-center">30-05-2017</td>
                                                <td class="col-md-2 text-center">05-06-2017</td>
                                                <td class="col-md-2 text-center">ABC123</td>
                                                <td class="col-md-2 text-center">$ 300.00</td>
                                                <td class="col-md-1 text-center">
                                                    <button class="glyphicon glyphicon-new-window"></button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingpayServicesR">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion2" href="#payServicesR"
                               @click="icon7 = !icon7" aria-expanded="false" aria-controls="payServicesR">
                                <div class="col-md-12" style="color: #8A6D3B">
                                    <div class="col-md-3">
                                        <b>Pago de servicios</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon7 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon7 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="payServicesR" class="collapse" role="tabpanel" aria-labelledby="headingpayServicesR">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha Re-programada</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="col-md-1 text-center">Compra</td>
                                                <td class="col-md-2 text-center">16-03-2017</td>
                                                <td class="col-md-2 text-center">30-05-2017</td>
                                                <td class="col-md-2 text-center">05-06-2017</td>
                                                <td class="col-md-2 text-center">ABC123</td>
                                                <td class="col-md-2 text-center">$ 300.00</td>
                                                <td class="col-md-1 text-center">
                                                    <button class="glyphicon glyphicon-new-window"></button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingViaticsR">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion2" href="#ViaticsR"
                               @click="icon8 = !icon8" aria-expanded="false" aria-controls="ViaticsR">
                                <div class="col-md-12" style="color: #8A6D3B">
                                    <div class="col-md-3">
                                        <b>Viáticos</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon8 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon8 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="ViaticsR" class="collapse" role="tabpanel" aria-labelledby="headingViaticsR">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha Re-programada</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="col-md-1 text-center">Compra</td>
                                                <td class="col-md-2 text-center">16-03-2017</td>
                                                <td class="col-md-2 text-center">30-05-2017</td>
                                                <td class="col-md-2 text-center">05-06-2017</td>
                                                <td class="col-md-2 text-center">ABC123</td>
                                                <td class="col-md-2 text-center">$ 300.00</td>
                                                <td class="col-md-1 text-center">
                                                    <button class="glyphicon glyphicon-new-window"></button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingPlaneTickesR">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion2" href="#planeTickesR"
                               @click="icon9 = !icon9" aria-expanded="false" aria-controls="planeTickesR">
                                <div class="col-md-12" style="color: #8A6D3B">
                                    <div class="col-md-3">
                                        <b>Boletos de avión</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon9 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon9 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="planeTickesR" class="collapse" role="tabpanel" aria-labelledby="headingPlaneTickesR">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha Re-programada</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="col-md-1 text-center">Compra</td>
                                                <td class="col-md-2 text-center">16-03-2017</td>
                                                <td class="col-md-2 text-center">30-05-2017</td>
                                                <td class="col-md-2 text-center">05-06-2017</td>
                                                <td class="col-md-2 text-center">ABC123</td>
                                                <td class="col-md-2 text-center">$ 300.00</td>
                                                <td class="col-md-1 text-center">
                                                    <button class="glyphicon glyphicon-new-window"></button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingRefoundsR">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion2" href="#refoundsR"
                               @click="icon10 = !icon10" aria-expanded="false" aria-controls="refoundsR">
                                <div class="col-md-12" style="color: #8A6D3B">
                                    <div class="col-md-3">
                                        <b>Reembolsos</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon10 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon10 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="refoundsR" class="collapse" role="tabpanel" aria-labelledby="headingRefoundsR">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha Re-programada</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="col-md-1 text-center">Compra</td>
                                                <td class="col-md-2 text-center">16-03-2017</td>
                                                <td class="col-md-2 text-center">30-05-2017</td>
                                                <td class="col-md-2 text-center">05-06-2017</td>
                                                <td class="col-md-2 text-center">ABC123</td>
                                                <td class="col-md-2 text-center">$ 300.00</td>
                                                <td class="col-md-1 text-center">
                                                    <button class="glyphicon glyphicon-new-window"></button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
            </div>
        </div>
        <%-- finish colapso--%>

        <%-- start colapso--%>
        <div class="panel panel-info">
            <div class="panel-heading text-center">
                <b>Vigentes en proceso de pago</b>
            </div>
            <div id="accordion" role="tablist" aria-multiselectable="true">
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingThreeV">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThreeV"
                               @click="icon11 = !icon11" aria-expanded="false" aria-controls="collapseThreeV">
                                <div class="col-md-12">
                                    <div class="col-md-3">
                                        <b>Compras de bienes</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon11 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon11 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="collapseThreeV" class="collapse" role="tabpanel" aria-labelledby="headingThreeV">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha programada</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="col-md-1 text-center">Compra</td>
                                                <td class="col-md-2 text-center">16-03-2017</td>
                                                <td class="col-md-2 text-center">30-05-2017</td>
                                                <td class="col-md-2 text-center">05-06-2017</td>
                                                <td class="col-md-2 text-center">ABC123</td>
                                                <td class="col-md-2 text-center">$ 300.00</td>
                                                <td class="col-md-1 text-center">
                                                    <button class="glyphicon glyphicon-new-window"></button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingpayServicesV">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#payServicesV"
                               @click="icon12 = !icon12" aria-expanded="false" aria-controls="payServicesV">
                                <div class="col-md-12">
                                    <div class="col-md-3">
                                        <b>Pago de servicios</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon12 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon12 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="payServicesV" class="collapse" role="tabpanel" aria-labelledby="headingpayServicesV">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha programada</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="col-md-1 text-center">Compra</td>
                                                <td class="col-md-2 text-center">16-03-2017</td>
                                                <td class="col-md-2 text-center">30-05-2017</td>
                                                <td class="col-md-2 text-center">05-06-2017</td>
                                                <td class="col-md-2 text-center">ABC123</td>
                                                <td class="col-md-2 text-center">$ 300.00</td>
                                                <td class="col-md-1 text-center">
                                                    <button class="glyphicon glyphicon-new-window"></button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingViaticsV">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#ViaticsV"
                               @click="icon13 = !icon13" aria-expanded="false" aria-controls="ViaticsV">
                                <div class="col-md-12">
                                    <div class="col-md-3">
                                        <b>Viáticos</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon13 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon13 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="ViaticsV" class="collapse" role="tabpanel" aria-labelledby="headingViaticsV">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha programada</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="col-md-1 text-center">Compra</td>
                                                <td class="col-md-2 text-center">16-03-2017</td>
                                                <td class="col-md-2 text-center">30-05-2017</td>
                                                <td class="col-md-2 text-center">05-06-2017</td>
                                                <td class="col-md-2 text-center">ABC123</td>
                                                <td class="col-md-2 text-center">$ 300.00</td>
                                                <td class="col-md-1 text-center">
                                                    <button class="glyphicon glyphicon-new-window"></button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
                <!-- one acordding -->
                <div class="card">
                    <div class="card-header" role="tab" id="headingPlaneTickesV">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#planeTickesV"
                               @click="icon14 = !icon14" aria-expanded="false" aria-controls="planeTickesV">
                                <div class="col-md-12">
                                    <div class="col-md-3">
                                        <b>Boletos de avión</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon14 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon14 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="planeTickesV" class="collapse" role="tabpanel" aria-labelledby="headingPlaneTickesV">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha programada</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="col-md-1 text-center">Compra</td>
                                                <td class="col-md-2 text-center">16-03-2017</td>
                                                <td class="col-md-2 text-center">30-05-2017</td>
                                                <td class="col-md-2 text-center">05-06-2017</td>
                                                <td class="col-md-2 text-center">ABC123</td>
                                                <td class="col-md-2 text-center">$ 300.00</td>
                                                <td class="col-md-1 text-center">
                                                    <button class="glyphicon glyphicon-new-window"></button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
                <!-- one acordding -->
                <div class="card" >
                    <div class="card-header" role="tab" id="headingRefoundsV">
                        <div class="panel-body">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#refoundsV"
                               @click="icon15 = !icon15" aria-expanded="false" aria-controls="refoundsV">
                                <div class="col-md-12">
                                    <div class="col-md-3">
                                        <b>Reembolsos</b>
                                    </div>
                                    <div class="col-md-6 text-center">
                                        <span class="glyphicon glyphicon-chevron-down" v-if="icon15 == false"></span>
                                        <span class="glyphicon glyphicon-chevron-up" v-if="icon15 == true"></span>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div id="refoundsV" class="collapse" role="tabpanel" aria-labelledby="headingRefoundsV">
                        <div class="card-block">
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="row">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <td class="col-md-1 text-center"><b>Solicitud</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha de compra</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha limite de pago</b></td>
                                                <td class="col-md-2 text-center"><b>Fecha programada</b></td>
                                                <td class="col-md-2 text-center"><b>Folio</b></td>
                                                <td class="col-md-2 text-center"><b>Monto</b></td>
                                                <td class="col-md-1 text-center"><b>Detalle</b></td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="col-md-1 text-center">Compra</td>
                                                <td class="col-md-2 text-center">16-03-2017</td>
                                                <td class="col-md-2 text-center">30-05-2017</td>
                                                <td class="col-md-2 text-center">05-06-2017</td>
                                                <td class="col-md-2 text-center">ABC123</td>
                                                <td class="col-md-2 text-center">$ 300.00</td>
                                                <td class="col-md-1 text-center">
                                                    <button class="glyphicon glyphicon-new-window"></button>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- one acordding -->
            </div>
        </div>
        <br>
        <%-- finish colapso--%>
    </jsp:body>
</t:template>

