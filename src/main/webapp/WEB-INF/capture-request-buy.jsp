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
            var vm = new Vue({
                el: '#content',
                created: function () {

                },
                ready: function () {
                    this.getUserInSession();
                    this.getCurrencies();
                },
                data: {
                    roleCostCenterList: [],
                    costCenterList: [],
                    budgetCategories: [],
                    budgetSubcategories: [],
                    products: [],
                    conceptBuys: [],
                    providers: [],
                    providerAccounts: [],
                    currencies: [],
                    //PRUEBA
                    requestProductsBuy: [],
                    requestCaptureProduct: [],
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
                            purpose: '',
                            userResponsible: '',
                            idCostCenter: '',
                            idAccountingAccount: ''
                        },
                        products: []
                    },
                    estimations: [],
                    selectProducts: {},
                    selected: {
                        costCenter: null,
                        budgetCategory: null,
                        budgetSubcategory: null,
                        product: null,
                        //PRUEBA
                        concept: null,
                        productBuy: null
                    },
                    newEstimationFormActive: false,
                    amount1: '',
                    amount2: '',
                    amount3: '',
                    button1: false,
                    button2: false,
                    button3: false
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

                                if (data.length < 2) {
                                    this.selected.budgetNature = data[0].budgetNature;
                                }

                                data.forEach(function (item) {
                                    index = self.arrayObjectIndexOf(self.costCenterList, item.costCenter.idCostCenter, 'idCostCenter');
                                    if (index == -1) self.costCenterList.push(item.costCenter);
                                });

                            })
                            .error(function (data) {

                            });
                    },
                    //PRUEBA
                    getBudgetSubcategories: function () {
                        this.$http.get(ROOT_URL + '/distributor-cost-center/category/' + this.selected.costCenter.idCostCenter)
                            .success(function (data) {
                                this.requestProductsBuy = data;
                            });
                    },
                    getProductCaptureBuy: function () {
                        this.$http.get(ROOT_URL + '/role-product-request/product/' +
                            this.selected.costCenter.idCostCenter + '/' + this.selected.concept.idAccountingAccount)
                            .success(function (data) {
                                this.requestCaptureProduct = data;
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
                    addProduct: function () {
                        var self = this;
                        if(this.selected.costCenter != null && this.selected.concept != null && this.selected.productBuy != null){
                            var productQuantity = {
                                productBuy: this.selected.productBuy,
                                quantity: 1
                            };
                            var aux = this.requestBody.products.filter(function (product) {
                                if(self.selected.productBuy.cProductsRequest.idProductRequest == product.productBuy.cProductsRequest.idProductRequest){
                                    return product;
                                }
                            });

                            if (aux == 0){
                                this.requestBody.products.push(productQuantity);
                                this.requestCaptureProduct.$remove(this.selected.productBuy);
                            }
                        }else{
                            showAlert("Es necesario seleccionar un producto", {type:3});
                        }
                    },
                    removeProduct: function (product) {
                        this.requestBody.products.$remove(product);
                        this.requestCaptureProduct.push(product.productBuy);

                        if(this.requestBody.products.length == 0){
                            this.requestBody.request.purpose = "";
                        }
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


                        if (this.requestBody.products.length == 0 || this.selected.costCenter == null || this.selected.concept == null || this.requestBody.request.purpose == "") {
                            showAlert("Debes agregar un producto y llenar la justificación de la solicitud", {type: 3});
                            return;
                        }

                        this.requestBody.request.idCostCenter = this.selected.costCenter.idCostCenter;
                        this.requestBody.request.idAccountingAccount = this.selected.concept.idAccountingAccount;
                        this.requestBody.request.userResponsible = this.user;

                        if(this.estimations.length == 3){
                            this.$http.post(ROOT_URL + '/requests', this.requestBody)
                                .success(function (data) {
                                    USER_VM.fetchApp();
                                    $("#modalSolicitud").modal("hide");
                                    this.saveEstimations(data);
                                    this.clearRequest();
                                })
                                .error(function (data) {
                                    showAlert("Error al generar la solicitud", {type: 3});
                                });
                        }else {
                            showAlert("Es necesario llenar todas las cotizaciones", {type: 3});
                        }

                    }
                    ,
                    saveEstimations: function (data) {
                        var self = this;
                        this.estimations.forEach(function (estimation) {
                            console.log(estimation);
                            self.saveEstimation(estimation, data);
                        });
                        showAlert("Solicitud enviada");
                        location.href = ROOT_URL + "/siad/request-spending";
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
                    setFile1: function (event) {
                        this.clearEstimation();
                        var self = this;
                        var file = event.target.files[0];
                        var object = {
                            identificador: '',
                            file: "",
                            amount: ""
                        };

                        if (this.validateFile(file)) {

                            var reader = new FileReader();

                            reader.onload = (function (theFile) {
                                return function (e) {
                                    object.file = {
                                        name: theFile.name,
                                        size: theFile.size,
                                        type: theFile.type,
                                        dataUrl: e.target.result
                                    };
                                };
                            })(file);
                            reader.readAsDataURL(file);
                            object.identificador = 1;
                            object.amount = this.amount1;

                            this.estimations.push(object);
                            this.button1 = true;
                        }
                    },
                    setFile2: function (event) {
                        this.clearEstimation();
                        var self = this;
                        var file = event.target.files[0];
                        var object = {
                            identificador: '',
                            file: "",
                            amount: ""
                        };

                        if (this.validateFile(file)) {

                            var reader = new FileReader();

                            reader.onload = (function (theFile) {
                                return function (e) {
                                    object.file = {
                                        name: theFile.name,
                                        size: theFile.size,
                                        type: theFile.type,
                                        dataUrl: e.target.result
                                    };
                                };
                            })(file);
                            reader.readAsDataURL(file);
                            object.identificador = 2;
                            object.amount = this.amount2;

                            this.estimations.push(object);
                            this.button2 = true;
                        }
                    },
                    setFile3: function (event) {
                        this.clearEstimation();
                        var self = this;
                        var file = event.target.files[0];
                        var object = {
                            identificador: '',
                            file: "",
                            amount: ""
                        };

                        if (this.validateFile(file)) {

                            var reader = new FileReader();

                            reader.onload = (function (theFile) {
                                return function (e) {
                                    object.file = {
                                        name: theFile.name,
                                        size: theFile.size,
                                        type: theFile.type,
                                        dataUrl: e.target.result
                                    };
                                };
                            })(file);
                            reader.readAsDataURL(file);
                            object.identificador = 3;
                            object.amount = this.amount3;

                            this.estimations.push(object);
                            this.button3 = true;
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
                    removeDocument: function (id) {
                        var self = this;
                        this.estimations.forEach(function (fileEstimations) {
                            if(id == fileEstimations.identificador){
                                self.estimations.$remove(fileEstimations);
                            }
                        });

                        switch (id){
                            case 1:
                                this.button1 =  false;
                                break;
                            case 2:
                                this.button2 =  false;
                                break;
                            case 3:
                                this.button3 =  false;
                                break;
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

                if(obj.value == "" || obj.value == null)
                {
                    obj.value = "";
                }else{
                    if (max >= 1 && max < 40) {
                        var extraer = contar.substring(min, max);
                        if (extraer == '.00') {
                            contar = contar.replace('.,', ',');
                            contar = contar.replace(',.', ',');
                            format(input);
                        }else
                        {
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
                <div class="col-md-12">
                    <div class="col-md-3">
                        <label>Centro de costos</label>
                        <select v-model="selected.costCenter" class="form-control"
                                @change="getBudgetSubcategories()" :disabled="requestBody.products.length > 0"
                                required>
                            <option v-for="costCenter in costCenterList" :value="costCenter">
                                {{costCenter.name}}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label>Concepto</label>
                        <select v-model="selected.concept" class="form-control"
                                @change="getProductCaptureBuy()" :disabled="requestBody.products.length > 0"
                                required>
                            <option v-for="concept in requestProductsBuy" :value="concept">
                                {{concept.budgetSubcategory.budgetSubcategory}}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label>Producto</label>
                        <select v-model="selected.productBuy" class="form-control"
                                required>
                            <option v-for="productBuy in requestCaptureProduct" :value="productBuy">
                                {{productBuy.cProductsRequest.productRequestName}}
                            </option>
                        </select>
                    </div>
                    <div class="col-md-1">
                        <button style="margin-top: 25px" class="btn btn-default" @click="addProduct()">Agregar</button>
                    </div>
                </div>
            </div>
                <%--tabla de cotenido de productos--%>
            <div class="row" v-if="requestBody.products.length > 0">
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
                                    <textarea class="form-control" rows="4" v-model="requestBody.request.purpose"></textarea>
                                </div>
                            </td>
                        </tr>
                        <tr v-for="product in requestBody.products">
                            <td class="col-md-3">{{product.productBuy.cProductsRequest.productRequestName}}</td>
                            <td class="col-md-1"><input class="form-control" maxlength="3" type="text"
                                                        onclick="return cleanFieldProduct(this)"
                                                        onkeypress="return validateFloatKeyPress(this,event)"
                                                        onInput="format(this)" onblur="ponerCeros(this)"
                                                        placeholder="0" v-model="product.quantity" required/></td>
                            <td class="col-md-2">
                                <center>
                                    <button @click="removeProduct(product)" class="btn btn-danger">Eliminar
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
            <div class="panel panel-default" v-if="requestBody.products.length > 0">
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
                                        <input v-model="amount1 | currencyDisplay" class="form-control" type="text" placeholder="$"
                                               onclick="return cleanField(this)"
                                               onkeypress="return validateFloatKeyPress(this,event)"
                                               oninput="format(this)" onblur="ponerCeros(this)"
                                               maxlength="14" :disabled="button1 == true" />
                                    </td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2">Documento</td>
                                    <td class="col-md-6">
                                        <input @change="setFile1($event)" type="file"
                                               class="form-control" :disabled="amount1 == '' || button1 == true"
                                               required />
                                    </td>
                                    <td class="col-md-3"></td>
                                    <td class="col-md-1" v-if="button1 == true">
                                        <button type="button" class="btn btn-warning" @click="removeDocument(1)">Cambiar factura</button>
                                    </td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2"><b>Cotización 2</b></td>
                                    <td class="col-md-5"></td>
                                    <td class="col-md-3">Monto cotización sin IVA</td>
                                    <td class="col-md-2">
                                        <input v-model="amount2 | currencyDisplay" class="form-control" type="text" placeholder="$"
                                               onclick="return cleanField(this)"
                                               onkeypress="return validateFloatKeyPress(this,event)"
                                               oninput="format(this)" onblur="ponerCeros(this)"
                                               maxlength="14" :disabled="button2 == true"/></td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2">Documento</td>
                                    <td class="col-md-6">
                                        <input @change="setFile2($event)" type="file"
                                               class="form-control" :disabled="amount2 == '' || button2 == true"
                                               required />
                                    </td>
                                    <td class="col-md-3"></td>
                                    <td class="col-md-1" v-if="button2 == true">
                                        <button type="button" class="btn btn-warning" @click="removeDocument(2)">Cambiar factura</button>
                                    </td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2"><b>Cotización 3</b></td>
                                    <td class="col-md-5"></td>
                                    <td class="col-md-3">Monto cotización sin IVA</td>
                                    <td class="col-md-2">
                                        <input v-model="amount3 | currencyDisplay" class="form-control" type="text" placeholder="$"
                                               onclick="return cleanField(this)"
                                               onkeypress="return validateFloatKeyPress(this,event)"
                                               oninput="format(this)" onblur="ponerCeros(this)"
                                               maxlength="14" :disabled="button3 == true"/></td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2">Documento</td>
                                    <td class="col-md-6">
                                        <input @change="setFile3($event)" type="file"
                                               class="form-control" :disabled="amount3 == '' || button3 == true"
                                               required />
                                    </td>
                                    <td class="col-md-3"></td>
                                    <td class="col-md-1" v-if="button3 == true">
                                        <button type="button" class="btn btn-warning" @click="removeDocument(3)">Cambiar factura</button>
                                    </td>
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
                            <button type="button" class="btn btn-success" @click="sendRequest()">Aceptar</button>
                            <button type="button" class="btn btn-default" class="close" data-dismiss="modal" aria-hidden="true" >Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </jsp:body>
</t:template>

