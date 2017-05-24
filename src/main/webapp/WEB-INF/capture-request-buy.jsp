<%--
  Created by IntelliJ IDEA.
  User: jcesar
  Date: 24/05/2017
  Time: 12:31 PM
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
                    selected: {
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
                    createSelectForConcept: function (products) {
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
                                }).success(function (data) {
                                    showAlert('Producto guardado');
                                    self.getProducts();
                                    callback(data);
                                }).error(function () {
                                    callback();
                                });
                            },
                            render: {
                                option_create: function (data, escape) {
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

                        if (this.requestBody.products.length == 0 || this.selected.costCenter == null || this.selected.budgetCategory == null || this.selected.budgetSubcategory == null) {
                            showAlert("Debes agregar un producto", {type: 3});
                            return;
                        } else if (this.estimations.length < 3) {
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
            function backHistory() {
                history.back();
            }
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
                    <h2>Capturar solicitud/compras</h2>
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
                <center><h3>Presupuesto mensual: </h3></center>
                <form class="col-md-12">
                    <div class="col-md-3">
                        <label>Centro de costos</label>
                        <select v-model="selected.costCenter" class="form-control selectpicker"
                                @change="onChangeCostCenter"
                                :disabled="requestBody.products.length > 0" required>
                            <option v-for="costCenter in costCenterList" :value="costCenter">{{costCenter.name}}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label>Concepto</label>
                        <select v-model="selected.budgetCategory" class="form-control selectpicker"
                                @change="onChangeBudgetCategory"
                                :disabled="requestBody.products.length > 0" required>
                            <option v-for="budgetCategory in budgetCategories" :value="budgetCategory">
                                {{budgetCategory.budgetCategory}}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label>Producto</label>
                        <select v-model="selected.budgetSubcategory" class="form-control"
                                @change="onChangeBudgetSubcategory"
                                :disabled="requestBody.products.length > 0" required>
                            <option v-for="budgetSubcategory in budgetSubcategories" :value="budgetSubcategory">
                                {{budgetSubcategory.budgetSubcategory}}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-1">
                        <button style="margin-top: 25px" class="btn btn-default">Agregar</button>
                    </div>
                </form>
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
                                    <textarea class="form-control" rows="4"></textarea>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-md-3">computadora</td>
                            <td class="col-md-1"><input class="form-control" maxlength="3" type="text"
                                                        onKeyPress="return onlyNumbers(event)"
                                                        placeholder="0" required/></td>
                            <td class="col-md-2">
                                <center>
                                    <button @click="cleanFields()" class="btn btn-danger">Eliminar
                                    </button>
                                </center>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

                <%--tabla de contenidos de productos--%>
            <br>
                <%-- subir arhivos de cotizacion--%>
            <div class="panel panel-default">
                <div class="panel-heading">Documentación</div>
                <div class="panel-body">
                    <div class="col-md-12">
                        <div class="row">
                            <table class="table table-striped">
                                <tr class="col-md-12">
                                    <td class="col-md-2"><b>Cotización 1</b></td>
                                    <td class="col-md-5"></td>
                                    <td class="col-md-3">Monto cotización sin IVA</td>
                                    <td class="col-md-2">
                                        <input class="form-control" type="text" placeholder="$"
                                               onclick="return cleanField(this)"
                                               onkeypress="return validateFloatKeyPress(this,event)"
                                               onInput="format(this)" maxlength="14" />
                                    </td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2">Documento</td>
                                    <td class="col-md-6">
                                        <input @change="setFile($event, docType)" type="file"
                                               class="form-control"
                                               :disabled="isSaving"
                                               :name="'file-type-' + docType.documentType.idDocumentType"
                                               accept="text/xml" required />
                                    </td>
                                    <td class="col-md-4"></td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2"><b>Cotización 2</b></td>
                                    <td class="col-md-5"></td>
                                    <td class="col-md-3">Monto cotización sin IVA</td>
                                    <td class="col-md-2"><input class="form-control" type="text" placeholder="$"
                                                                onclick="return cleanField(this)"
                                                                onkeypress="return validateFloatKeyPress(this,event)"
                                                                onInput="format(this)" maxlength="14"/></td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2">Documento</td>
                                    <td class="col-md-6">
                                        <input @change="setFile($event, docType)" type="file"
                                               class="form-control"
                                               :disabled="isSaving"
                                               :name="'file-type-' + docType.documentType.idDocumentType"
                                               accept=".xml" accept="application/pdf" required />
                                    </td>
                                    <td class="col-md-4"></td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2"><b>Cotización 3</b></td>
                                    <td class="col-md-5"></td>
                                    <td class="col-md-3">Monto cotización sin IVA</td>
                                    <td class="col-md-2"><input class="form-control" type="text" placeholder="$"
                                                                onclick="return cleanField(this)"
                                                                onkeypress="return validateFloatKeyPress(this,event)"
                                                                onInput="format(this)" maxlength="14"/></td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2">Documento</td>
                                    <td class="col-md-6">
                                        <input @change="setFile($event, docType)" type="file"
                                               class="form-control"
                                               :disabled="isSaving"
                                               :name="'file-type-' + docType.documentType.idDocumentType"
                                               accept="application/pdf" required />
                                    </td>
                                    <td class="col-md-4"></td>
                                </tr>

                            </table>

                        </div>
                        <div style="margin-left: 84%">
                            <button @click="showModalSolicitud()" class="btn btn-success">Solicitar</button>
                            <a href="javascript:window.history.back();">
                                <button type="button" class="btn btn-default" >Cancelar</button>
                            </a>
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
                                    <p align="center" style="font-size: 16px">La solicitud será enviada a compras para continuar<br> con el proceso de compra de
                                        bienes.</p>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="saveBussinessLine()">Aceptar</button>
                            <a href="javascript:window.history.back();">
                                <button type="button" class="btn btn-default" >Cancelar</button>
                            </a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </jsp:body>
</t:template>

