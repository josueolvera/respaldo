<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 10/10/16
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />


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
        </script>

        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                created: function(){

                },
                ready: function () {
                    this.getUserInSession();
                    this.getCurrencies();
                },
                data: {
                    requestCategory: ${cat},
                    idRequest: ${idRequest},
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
                    estimation: {
                        amount: '',
                        provider: '',
                        account: '',
                        currency: '',
                        rate: '',
                        file: ''
                    },
                    requestBody: {
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
                    },
                    estimations: [],
                    selectProducts: {},
                    selected : {
                        costCenter: null,
                        budgetCategory: null,
                        budgetSubcategory: null,
                        product: null
                    },
                    newEstimationFormActive: false
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
                    onChangeCostCenter : function () {
                        this.budgetCategories = [];
                        this.budgetSubcategories = [];
                        this.products = [];
                        this.getBudgetCategories();
                    },
                    getBudgetCategories : function () {
                        this.$http.get(
                                ROOT_URL +
                                '/budget-categories?cost_center=' +
                                this.selected.costCenter.idCostCenter +
                                '&request_category=1'
                        ).success(function (data) {
                            this.budgetCategories = data;
                        });
                    },
                    getBudgetSubcategories : function () {
                        this.$http.get(ROOT_URL + '/budget-subcategories/category/' + this.selected.budgetCategory.idBudgetCategory)
                                .success(function (data) {
                                    this.budgetSubcategories = data;
                                });
                    },
                    getProducts : function () {
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
                    onChangeBudgetSubcategory : function () {
                        this.products = [];
                        this.getProducts();
                        this.getProviders();
                    },
                    createSelectForConcept : function (products) {
                        var self = this;
                        return $('#select-products').selectize({
                            maxItems: 1,
                            valueField: 'idProduct',
                            labelField: 'product',
                            searchField: 'product',
                            options: products,
                            create: function (input, callback) {
                                self.$http.post(ROOT_URL + '/products/subcategory/' + self.selected.budgetSubcategory.idBudgetSubcategory, {
                                    product: input
                                }).success(function (data){
                                    showAlert('Producto guardado');
                                    self.getProducts();
                                    callback(data);
                                }).error(function (){
                                    callback();
                                });
                            },
                            render: {
                                option_create: function(data, escape) {
                                    return '<div data-selectable class="create">' +
                                            'Agregar <strong>' + escape(data.input) + '</strong>' +
                                            '</div>'
                                }
                            }
                        });
                    },
                    addProduct: function () {
                        var product = {};
                        product.idProduct = this.selectProducts[0].selectize.getValue();
                        product.product = this.selectProducts[0].selectize.getOption(product.idProduct).text();

                        this.requestBody.products.push(product);
                    },
                    removeProduct: function (product) {
                        this.requestBody.products.$remove(product);
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
                    removeEstimation: function (estimation) {
                        this.estimations.$remove(estimation);
                    },
                    sendRequest: function () {

                        if (this.requestBody.products.length == 0 || this.selected.costCenter == null || this.selected.budgetCategory == null || this.selected.budgetSubcategory == null ) {
                            showAlert("Debes agregar un producto", {type:3});
                            return;
                        }else if (this.estimations.length < 3){
                            showAlert("Debes agregar al menos tres cotizaciones", {type: 3});
                            return;
                        }

                        this.requestBody.request.idCostCenter = this.selected.costCenter.idCostCenter;
                        this.requestBody.request.idBudgetCategory = this.selected.budgetCategory.idBudgetCategory;
                        this.requestBody.request.idBudgetSubcategory = this.selected.budgetSubcategory.idBudgetSubcategory;
                        this.requestBody.request.idRequestCategory = 1;

                        this.$http.post(ROOT_URL + '/requests', this.requestBody)
                                .success(function (data) {
                                    USER_VM.fetchApp();
                                    this.saveEstimations(data);
                                    this.clearRequest();
                                })
                                .error(function (data) {
                                    showAlert("Error al generar la solicitud", {type: 3});
                                });
                    }
                    ,
                    saveEstimations: function (data) {
                        var self = this;
                        this.estimations.forEach(function (estimation) {
                            self.saveEstimation(estimation, data);
                        });
                        showAlert("Solicitud enviada");
                    },
                    saveEstimation: function (estimation, data) {
                        this.$http.post(ROOT_URL + '/estimations/request/' + data.idRequest, estimation).success(function (data) {
                        }).error(function () {
                            showAlert("Error al agregar cotización", {type: 3});
                        })
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
                    getProviderAccounts: function () {
                        this.$http.get(ROOT_URL + '/providers-accounts/provider/' + this.estimation.provider.idProvider)
                                .success(function (data) {
                                    this.providerAccounts = data;
                                })
                                .error(function (data) {

                                });
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

                            reader.onload = (function(theFile) {
                                return function(e) {
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
                            showAlert("Tipo de archivo no admitido", {type:3});
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
                    currencyDisplay : {
                        read: function(val) {
                            if (typeof val == 'number') {
                                return val.formatMoney(2, '');
                            }
                        },
                        write: function(val, oldVal) {
                            var number = +val.replace(/[^\d.]/g, '');
                            return isNaN(number) ? 0 : parseFloat(number.toFixed(2));
                        }
                    }
                }
            });

        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>

            textarea {
                resize: none;
            }

        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="row">
                <div class="col-md-8">
                    <h2>Registro de solicitud</h2>
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
                <form v-on:submit.prevent="addProduct">
                    <div class="col-md-3">
                        <label>Centro de costos</label>
                        <select v-model="selected.costCenter" class="form-control" @change="onChangeCostCenter"
                                :disabled="requestBody.products.length > 0" required>
                            <option v-for="costCenter in costCenterList" :value="costCenter">{{costCenter.name}}</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label>Rubro</label>
                        <select v-model="selected.budgetCategory" class="form-control" @change="onChangeBudgetCategory"
                                :disabled="requestBody.products.length > 0" required>
                            <option v-for="budgetCategory in budgetCategories" :value="budgetCategory">{{budgetCategory.budgetCategory}}</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label>Producto</label>
                        <select v-model="selected.budgetSubcategory" class="form-control" @change="onChangeBudgetSubcategory"
                                :disabled="requestBody.products.length > 0" required>
                            <option v-for="budgetSubcategory in budgetSubcategories" :value="budgetSubcategory">{{budgetSubcategory.budgetSubcategory}}</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <label>Tipo de producto</label>
                        <select id="select-products" class="form-control" required></select>
                    </div>
                    <div class="col-md-1">
                        <button style="margin-top: 25px" class="btn btn-default">Agregar</button>
                    </div>
                </form>
            </div>
            <br>
            <form v-on:submit.prevent="sendRequest">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-md-8">
                                        <h5><b>Productos seleccionados</b></h5>
                                    </div>
                                    <div class="col-md-4 text-right">
                                        <button type="button" class="btn btn-default" :disabled="requestBody.products.length < 1" @click="showNewEstimationModal">Agregar cotización</button>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-md-3" v-if="requestBody.products.length > 0">
                                        <div class="table-responsive">
                                            <table class="table">
                                                <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>PRODUCTO</th>
                                                    <th></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr v-for="product in requestBody.products | orderBy 'product'">
                                                    <td><b>{{$index + 1}}</b></td>
                                                    <td>{{product.product}}</td>
                                                    <td>
                                                        <button class="btn btn-danger" type="button" @click="removeProduct(product)"
                                                                data-toggle="tooltip" data-placement="top" title="Eliminar">
                                                            <span class="glyphicon glyphicon-trash"></span>
                                                        </button>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="col-md-9" v-if="estimations.length > 0">
                                        <div class="table-responsive">
                                            <table class="table">
                                                <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>PROVEEDOR</th>
                                                    <th>CUENTA BANCARIA</th>
                                                    <th>TIPO DE MONEDA</th>
                                                    <th>MONTO</th>
                                                    <th>TIPO DE CAMBIO</th>
                                                    <th></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr v-for="estimation in estimations">
                                                    <td><b>{{$index + 1}}</b></td>
                                                    <td>{{estimation.provider.providerName | separate}}</td>
                                                    <td>{{estimation.account.accountNumber}}</td>
                                                    <td>{{estimation.currency.currency}}</td>
                                                    <td>{{estimation.amount | currency}}</td>
                                                    <td>{{estimation.rate | currency}}</td>
                                                    <td>
                                                        <button class="btn btn-danger" type="button" @click="removeEstimation(estimation)"
                                                                data-toggle="tooltip" data-placement="top" title="Eliminar">
                                                            <span class="glyphicon glyphicon-trash"></span>
                                                        </button>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <label>Descripción de la solicitud</label>
                                        <textarea rows="5" class="form-control" v-model="requestBody.request.description" required></textarea>
                                    </div>
                                    <div class="col-md-6">
                                        <label>Justificación de la solicitud</label>
                                        <textarea rows="5" class="form-control" v-model="requestBody.request.purpose" required></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-10"></div>
                    <div class="col-md-2">
                        <button class="btn btn-success form-control" :disabled="requestBody.products.length < 1">Enviar</button>
                    </div>
                </div>
            </form>
            <br>
            <div class="modal fade" id="newEstimationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog modal-lg" role="document">
                    <form v-on:submit.prevent="addEstimation">
                        <div class="modal-content">
                            <div class="modal-header">
                                <div class="row">
                                    <div class="col-md-4">
                                        <h4 class="modal-title" id="myModalLabel">Nueva cotización</h4>
                                    </div>
                                    <div class="col-md-4 text-center">
                                        <div v-if="estimation.amount && estimation.rate">
                                            <p>
                                                <b> Monto MXN: </b>{{estimation.amount | currency}}
                                            </p>
                                            <p>
                                                <b>Monto {{estimation.currency.acronym}}: </b> {{estimation.amount * estimation.rate | currency}}
                                            </p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <button type="button" class="close" @click="hideNewEstimationModal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-body"  v-if="newEstimationFormActive">
                                <div class="row">
                                    <div class="col-md-4">
                                        <label>Proveedor</label>
                                        <select class="form-control" v-model="estimation.provider" @change="getProviderAccounts" required>
                                            <option v-for="provider in providers" :value="provider">
                                                {{provider.providerName | separate}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <label>Cuenta Bancaria</label>
                                        <select class="form-control" v-model="estimation.account" :disabled="estimation.provider == ''" required>
                                            <option v-for="providerAccount in providerAccounts" :value="providerAccount.account">
                                                {{providerAccount.account.accountNumber}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <label>Tipo de Moneda</label>
                                        <select class="form-control" v-model="estimation.currency" @change="onChangeCurrency" required>
                                            <option v-for="currency in currencies" :value="currency">
                                                {{currency.currency}}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <label>Monto</label>
                                        <div class="input-group">
                                            <span class="input-group-addon">$</span>
                                            <input type="text" class="form-control" v-model="estimation.amount | currencyDisplay"
                                                   onkeypress="return validateFloatKeyPress(this,event)" required>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <label>Tipo de cambio</label>
                                        <div class="input-group">
                                            <span class="input-group-addon">$</span>
                                            <input type="text" class="form-control" v-model="estimation.rate | currencyDisplay" :disabled="estimation.currency.idCurrency == 1"
                                                   onkeypress="return validateFloatKeyPress(this,event)" required>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <label>Archivo de la cotización</label>
                                        <input type="file" class="form-control" @change="setFile($event)" required>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-primary">Agregar</button>
                                <button type="button" class="btn btn-default" @click="hideNewEstimationModal">Cancelar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>

