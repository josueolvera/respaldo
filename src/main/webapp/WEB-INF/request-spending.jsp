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
                        <a href="../siad/cotizable">
                            <button class="btn btn-warning" style="margin-top: 25px">Capturar una solicitud</button>
                        </a>
                    </div>
                    <div class="col-md-4">
                        <form>
                            <div class="col-md-8">
                                <label>Búsqueda por folio</label>
                                <input class="form-control" type="text" placeholder="folio" maxlength="30" required/>
                            </div>

                        </form>
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
                <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0">
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
                                            <td class="col-md-3 text-center"><b>Concepto de solicitud 1</b></td>
                                            <td class="col-md-3 text-center"><b>Fecha de solicitud</b></td>
                                            <td class="col-md-3 text-center"><b>Folio</b></td>
                                            <td class="col-md-3 text-center"><b>Detalle</b></td>
                                        </tr>
                                        <tr>
                                        <td class="col-md-3 text-center">Equipo de computo</td>
                                        <td class="col-md-3 text-center">16-03-2017</td>
                                        <td class="col-md-3 text-center">ABC123</td>
                                        <td class="col-md-3 text-center">
                                            <button class="glyphicon glyphicon-new-window"></button>
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
                <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0">
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
                                                <tr>
                                                    <td class="col-md-3 text-center">Equipo de computo</td>
                                                    <td class="col-md-3 text-center">16-03-2017</td>
                                                    <td class="col-md-3 text-center">ABC123</td>
                                                    <td class="col-md-3 text-center">
                                                        <button class="glyphicon glyphicon-new-window"></button>
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

                <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0">
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
                                                <tr>
                                                    <td class="col-md-3 text-center">Equipo de computo</td>
                                                    <td class="col-md-3 text-center">16-03-2017</td>
                                                    <td class="col-md-3 text-center">ABC123</td>
                                                    <td class="col-md-3 text-center">
                                                        <button class="glyphicon glyphicon-new-window"></button>
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
        <%--termina archivos de cotizacion--%>
    </jsp:body>
</t:template>

