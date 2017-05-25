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
                showModalSolicituddos: function () {
                    $('#modalSolicituddos').modal('show');

                },
                hiddencotizacionUnoArticle: function () {
                    document.getElementById('cotizacionUnoArticle').style.display ='block';
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
                    showFactura: function () {
                        $('#modalFactura').modal('show');
                    },
                    showEnviarsolicitudm: function () {
                        $('#modalEnviarsolicitudm').modal('show');
                    },
                    showNewEstimationModal: function () {
                        this.newEstimationFormActive = true;
                        $('#newEstimationModal').modal('show');
                    },
                    showJustificarechazo: function () {
                        $('#modalJustificarechazo').modal('show');
                    },
                    showSolicitudmodaL: function () {
                        $('#modalSolicitudmodaL').modal('show');
                    },
                    showDetallemodal: function () {
                        $('#modalDetallemodal').modal('show');
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
             function hiddencotizacionUnoArticle(obj) {
                document.getElementById('xxx').style.display ='block';
            }
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            textarea {
                resize: none;
            }

            label.circleyel {
                width: 15px;
                height: 15px;
                -moz-border-radius: 50%;
                -webkit-border-radius: 50%;
                border-radius: 50%;
                background: #FFFF06;
            }

        </style>
    </jsp:attribute>

    <jsp:body>

        <!--INICIA PANTALLA DE SOLICITUD/VIGENTE PAGINA 25-->

        <div id="content">
        <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="col-md-4">
                    <h4>DETALLE DE SOLICITUD/VIGENTE</h4>
                </div>
                <div class="col-md-2" style="margin: 1%">
                    <label class="circleyel"></label>
                </div>
                <div class="col-md-5 text-right" style="margin-top: 10px">
                    <label>Nombre de usuario</label>
                    <p>
                        <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                    </p>
                </div>
            </div>
        </div>
        <div  class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #F2F2F2">
            <div class="row">
                <div class="col-md-12">
                   <div class="col-md-12">
                       <h5><b style="color: black">Datos del solicitante</b></h5>
                       <table class="table table-striped">
                       <thead style="background-color: #aaaaaa">
                       <th class="col-md-4">Nombre</th>
                       <th class="col-md-4">Area</th>
                       <th class="col-md-4">Fecha de solicitud</th>
                       </thead>
                       <tbody>
                       <tr v-for="compra in compra">
                           <td class="col-md-4">{{Name}}</td>
                           <td class="col-md-4">{{Area}}</td>
                           <td class="col-md-4">{{date}}</td>
                       </tr>
                       </tbody>
                   </table>
                   </div>
                </div>
            </div>
        </div>
            <div class="panel panel default" v-if="selectionOptions.role.idRole> 0" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos de solicitud</b></h5>
                            <table class="table table-striped">
                                <thead>
                                <th class="col-md-6" style="background-color: #aaaaaa">Centro de costos</th>
                                <th class="col-md-6" style="background-color: #aaaaaa">Centro de concepto</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-6">{{Centro de costos}}</td>
                                    <td class="col-md-6">{{Centro de concepto</td>
                                </tr>
                                </tbody>
                                <thead style="background-color: #aaaaaa">
                                <th class="col-md-4">Lista de productos</th>
                                <th class="col-md-4">Cantidad</th>
                                <th class="col-md-4">Justificacion</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-4">{{Computadoras}}</td>
                                    <td class="col-md-4">{{Cantidad}}</td>
                                    <td class="col-md-4">{{Justificacion}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #F2F2F2">
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-7"><br>
                        <h5><b  style="color: black">Cotizacion 1</b></h5>
                    </div>
                    <form><br>
                        <div class="col-md-5">
                            <label>Monto cotizado sin IVA</label>
                            <label>$<input type="number"></label>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-12" style="background-color: #aaaaaa">
                        <h7><b>Documento</b></h7><br><br>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-2"><br>
                        <h6>Nombre del archivo</h6>
                    </div>
                    <div class="col-md-7"><br>
                        <button class="btn btn-primary btn-sm">DESCARGAR</button>
                    </div>
                    <div class="col-md-3"><br>
                        <button class="btn btn-success btn-sm" data-toggle="modal" data-target="#justificaruno">ELEGIR</button></div><br>
                    </div>
                </div>
            <br>
            </div>
            <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #F2F2F2">
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-7"><br>
                        <h5><b style="color: black">Cotizacion 2</b></h5>
                    </div>
                    <form><br>
                        <div class="col-md-5">
                            <label>Monto cotizado sin IVA</label>
                            <label>$<input type="number"></label>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-12" style="background-color: #aaaaaa">
                        <h7><b>Documento</b></h7><br><br>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-2"><br>
                        <h6>Nombre del archivo</h6>
                    </div>
                    <div class="col-md-7"><br>
                        <button class="btn btn-primary btn-sm">DESCARGAR</button>
                    </div>
                    <div class="col-md-3"><br>
                        <button class="btn btn-success btn-sm" data-toggle="modal" data-target="#justificardos">ELEGIR</button>
                    </div>
                </div>
            </div>
            <br>
            </div>
            <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #F2F2F2">
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-7"><br>
                        <h5><b  style="color: black">Cotizacion 3</b></h5>
                    </div>
                    <form><br>
                        <div class="col-md-5">
                            <label>Monto cotizado sin IVA</label>
                            <label>$<input type="number"></label>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-12" style="background-color: #aaaaaa">
                        <h7><b>Documento</b></h7><br><br>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-2"><br>
                        <h6>Nombre del archivo</h6>
                    </div>
                    <div class="col-md-7"><br>
                        <button class="btn btn-primary btn-sm">DESCARGAR</button>
                    </div>
                    <div class="col-md-3"><br>
                        <button class="btn btn-success btn-sm" data-toggle="modal" data-target="#justificartres">ELEGIR</button>
                    </div>
                </div>
            </div>
            <br>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-10">
                        <button type="button" class="btn btn-danger"data-toggle="modal" data-target="#Justificarechazo" style="margin-left: 800px">Rechazar </button>
                    </div>
                    <div class="col-md-2">
                        <button type="button" class="btn btn-default">Cancelar</button>
                    </div>
                </div>
            </div> <br><br>

            <!--TERMINA PANTALLA DE SOLICITUD/VIGENTE PAGINA 25-->

            <!--EMPIEZA MODAL 1 PARA LA OPCION ELEGIR DE LA PANTALLA DE SOLICITUD/VIGENTE PAGINA 26-->

                <div class="modal fade" id="justificaruno" role="dialog">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title">Justificar</h4>
                            </div>
                            <div class="modal-body">
                                <form>
                                    <div class="form-group">
                                        <textarea class="form-control" rows="3"></textarea>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" onclick="parent.location=' http://localhost:8080/BIDGroupLines/siad/request-spending-detail-current'">Aceptar</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </div>

            <!--TERMINA MODAL 1 PARA LA OPCION ELEGIR DE LA PANTALLA DE SOLICITUD/VIGENTE PAGINA 26-->

            <!--EMPIEZA MODAL 2 PARA LA OPCION ELEGIR DE LA PANTALLA DE SOLICITUD/VIGENTE PAGINA 26-->

            <div class="modal fade" id="justificardos" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Justificar</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <textarea class="form-control" rows="3"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" data-dismiss="modal">Aceptar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

            <!--TERMINA MODAL 2 PARA LA OPCION ELEGIR DE LA PANTALLA DE SOLICITUD/VIGENTE PAGINA 26-->

            <!--EMPIEZA MODAL 3 PARA LA OPCION ELEGIR DE LA PANTALLA DE SOLICITUD/VIGENTE PAGINA 26-->

            <div class="modal fade" id="justificartres" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Justificar</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <textarea class="form-control" rows="3"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" data-dismiss="modal" >Aceptar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

            <!--TERMINA MODAL 3 PARA LA OPCION ELEGIR DE LA PANTALLA DE SOLICITUD/VIGENTE PAGINA 26-->

            <!--EMPIEZA VISTA DE COMPRA PAGINA 27-->


            <div  class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos del solicitante</b></h5>
                            <table class="table table-striped">
                                <thead style="background-color: #aaaaaa">
                                <th class="col-md-4">Nombre</th>
                                <th class="col-md-4">Area</th>
                                <th class="col-md-4">Fecha de solicitud</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-4">{{Name}}</td>
                                    <td class="col-md-4">{{Area}}</td>
                                    <td class="col-md-4">{{date}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel default" v-if="selectionOptions.role.idRole> 0" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos de solicitud</b></h5>
                            <table class="table table-striped">
                                <thead style="background-color: #aaaaaa">
                                <th class="col-md-6">Centro de costos</th>
                                <th class="col-md-6">Concepto</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-6">{{Centro de costos}}</td>
                                    <td class="col-md-6">{{Centro de concepto</td>
                                </tr>
                                </tbody>
                                <thead style="background-color: #aaaaaa">
                                <th class="col-md-4">Lista de productos</th>
                                <th class="col-md-4">Cantidad</th>
                                <th class="col-md-4">Justificacion</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-4">{{Computadoras}}</td>
                                    <td class="col-md-4">{{Cantidad}}</td>
                                    <td class="col-md-4">{{Justificacion}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-7"><br>
                            <h5><b  style="color: black">Cotizacion 1</b></h5>
                        </div>
                        <form><br>
                            <div class="col-md-5">
                                <label>Monto cotizado sin IVA</label>
                                <label>$<input type="number"></label>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #AFAFAF">
                            <h7><b>Documento</b></h7><br><br>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-2"><br>
                            <h6>Nombre del archivo</h6>
                        </div>
                        <div class="col-md-7"><br>
                            <button class="btn btn-primary btn-sm">DESCARGAR</button>
                        </div>
                        <div class="col-md-3"><br>
                            <button class="btn btn-success btn-sm"><span class="glyphicon glyphicon-ok"></span></button>
                        </div><br>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #AFAFAF">
                            <h6><b>Justificacion</b></h6>
                        </div><br>
                        <div class="col-md-12"><br>
                            <h7>sjfhaskjldfhkjlsfhgsfkjgdfsasdkjagsfkjghjgfhjdgshjdgsfjhdgsfjhdgsfjsdgfjdfsgjf</h7>
                        </div><br><br><br>
                    </div>
                </div><br>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-9">
                        <button class="btn btn-primary btn-sm" style="margin-left: 80%" data-toggle="modal" data-target="#modalFactura">
                            Generar orden de compra</button>
                    </div>
                    <div class="col-md-2">
                        <button type="button" class="btn btn-danger btn-sm" style="margin-left: 10%">Agregar Comprobante</button>
                    </div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-default btn-sm">Cancelar</button>
                    </div>
                </div>
            </div>

            <!--TERMINA VISTA DE COMPRA PAGINA 27-->

            <!--EMPIEZA MODAL QUE AL PRESIONAR GENERAR ORDEN DE COMPRA SE MUESTRA LA ORDEN DE COMPRA PAGINA 28-->

            <div class="modal fade" id="modalFactura" tabindex="-1" role="dialog" aria-labelledby=""
                 aria-hidden="true">
                <div class="modal-dialog modal-ms">
                    <div class="modal-content modal-ms">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <div class="alert">
                                <img style="width: 30px; height: 30px">
                                <h4 class="modal-title" style="text-align: center"><label>Orden de compra</label>
                                </h4>
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
                                                            <h3 class="panel-title">Fecha: dd/mm/yyyy</h3>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <h3 class="panel-title">Número de orden:</h3>
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
                                                            <h3 class="panel-title">Contacto del emisor</h3>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <h3 class="panel-title">Contacto del receptor</h3>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-md-3">
                                                            <label>Nombre:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="input-group">
                                                                <label></label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label>Nombre:</label>
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
                                                            <label>Puesto:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="input-group">
                                                                <label></label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label>Puesto:</label>
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
                                                            <label>Télefono:</label>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="input-group">
                                                                <label></label>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label>Télefono:</label>
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
                                        </div>
                                        <div class="col-md-12">
                                            <div class="panel panel-default">
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <table class="table">
                                                            <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>Descripción</th>
                                                                <th>Cantidad</th>
                                                                <th>Precio</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <tr>
                                                                <th scope="row">1</th>
                                                                <td>Cuca</td>
                                                                <td>2</td>
                                                                <td>$ 38.90</td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h3 class="panel-title">Conceptos autorizados</h3>
                                                </div>
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <div class="col-md-4"></div>
                                                        <div class="col-md-8">
                                                            <dl class="dl-horizontal">
                                                                <dt>IVA</dt>
                                                                <dd>
                                                                    <p class="underline">
                                                                        {{casetasAmount | currency}} {{currency.acronym}}
                                                                    </p>
                                                                </dd>
                                                                <dt>Subtotal</dt>
                                                                <dd>
                                                                    <p class="underline">
                                                                        {{gasolinaAmount | currency}} {{currency.acronym}}
                                                                    </p>
                                                                </dd>
                                                            </dl>
                                                            <dl class="dl-horizontal">
                                                                <dt>Total</dt>
                                                                <dd>
                                                                    <p class="underline">
                                                                        {{totalTravelExpenses | currency}} {{currency.acronym}}
                                                                    </p>
                                                                </dd>
                                                            </dl>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="row">
                                                <div class="col-md-offset-6 col-md-6">
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <button type="submit" class="btn btn-success form-control">Guardar</button>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <button type="button" class="btn btn-default form-control" @click="">Cancelar
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <br>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <br>
                    </div>
                </div>
            </div>

            <!--TERMINA MODAL QUE AL PRESIONAR GENERAR ORDEN DE COMPRA SE MUESTRA LA ORDEN DE COMPRA PAGINA 28-->



            <!--EMPIEZA VISTA DE UNA ORDEN DE COMPRA PAGINA 29-->

            <br><br>
            <div  class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos del solicitante</b></h5>
                            <table class="table table-striped">
                                <thead style="background-color: #aaaaaa">
                                <th class="col-md-4">Nombre</th>
                                <th class="col-md-4">Area</th>
                                <th class="col-md-4">Fecha de solicitud</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-4">{{Name}}</td>
                                    <td class="col-md-4">{{Area}}</td>
                                    <td class="col-md-4">{{date}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel default" v-if="selectionOptions.role.idRole> 0" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos de solicitud</b></h5>
                            <table class="table table-striped">
                                <thead>
                                <th class="col-md-6" style="background-color: #aaaaaa">Centro de costos</th>
                                <th class="col-md-6" style="background-color: #aaaaaa">Centro de concepto</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-6">{{Centro de costos}}</td>
                                    <td class="col-md-6">{{Centro de concepto</td>
                                </tr>
                                </tbody>
                                <thead>
                                <th class="col-md-4" style="background-color: #aaaaaa">Lista de productos</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Cantidad</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Justificacion</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-4">{{Computadoras}}</td>
                                    <td class="col-md-4">{{Cantidad}}</td>
                                    <td class="col-md-4">{{Justificacion}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-7"><br>
                            <h5><b  style="color: black">Cotizacion 1</b></h5>
                        </div>
                        <form><br>
                            <div class="col-md-5">
                                <label>Monto cotizado sin IVA</label>
                                <label>$<input type="number"></label>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #AFAFAF">
                            <h7><b>Documento</b></h7><br><br>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-2"><br>
                            <h6>Nombre del archivo</h6>
                        </div>
                        <div class="col-md-7"><br>
                            <button class="btn btn-primary btn-sm">DESCARGAR</button>
                        </div>
                        <div class="col-md-3"><br>
                            <button class="btn btn-success btn-sm"><span class="glyphicon glyphicon-ok"></span></button>
                        </div><br>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #AFAFAF">
                            <h6><b>Justificacion</b></h6>
                        </div><br>
                        <div class="col-md-12"><br>
                            <h7>sjfhaskjldfhkjlsfhgsfkjgdfsasdkjagsfkjghjgfhjdgshjdgsfjhdgsfjhdgsfjsdgfjdfsgjf</h7>
                        </div><br><br><br>
                    </div>
                </div><br>
            </div>
            <div class="panel panel default" v-if="selectionOptions.role.idRole> 0" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Orden de compra</b></h5>
                            <table class="table table-striped">
                                <thead>
                                <th class="col-md-4" style="background-color: #aaaaaa">Numero de orden de compra</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Monto total</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Fecha de generacion</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-3">{{name}}</td>
                                    <td class="col-md-3">{{Monto total}}</td>
                                    <td class="col-md-3">{{fecha}}</td>
                                    <div class="col-md-3">
                                    <button type="button" class="btn btn-default btn-sm">Cancelar</button>
                                    </div>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-11">
                        <button type="button" class="btn btn-warning btn-sm" style="margin-left: 85%" data-toggle="modal" data-target="#Mimodal">Agregar Comprobante</button>
                    </div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-default btn-sm">Cancelar</button>
                    </div>
                </div>
            </div>

            <!--TERMINA VISTA DE UNA ORDEN DE COMPRA PAGINA 29-->

            <!--EMPIEZA MODAL DE AGREGAR COMPROBANTE PAGINA 29-->

                <div class="modal fade" id="Mimodal" role="dialog" style="background-color: #aaaaaa">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content" style="background-color: #f2f2f2">
                            <div class="modal-body">
                                <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #f2f2f2">
                                    <div class="row">
                                        <div class="col-md-12"><br>
                                        <div class="col-md-2">
                                            <label>Concepto</label><br><br>
                                            <p><b>Equipo computo</b></p>
                                        </div>
                                        <div class="col-md-2">
                                            <label>Numero factura</label><br><br>
                                            <input v-model="rfcSearch" type="text" class="form-control">
                                        </div>
                                        <div class="col-md-2">
                                            <label>Concepto Factra</label><br><br>
                                            <input v-model="numEmployeeSearch" type="text" class="form-control">
                                        </div>
                                            <div class="col-md-2">
                                            <label>Monto con iva</label><br><br>
                                            <input v-model="numEmployeeSearch" type="text" class="form-control">
                                        </div>
                                            <div class="col-md-2">
                                            <label>Monto sin iva</label><br><br>
                                            <input v-model="numEmployeeSearch" type="text" class="form-control">
                                        </div>
                                        <div class="col-md-2">
                                            <label>Comprobante</label><br><br>
                                            <button class="btn btn-primary btn-xs" @click="getEmployees" data-toggle="modal" data-target="#MImodal">Carga de documentos</button>
                                        </div>
                                    </div>
                                        <br>
                                        <br>
                                        <br>
                                        <div class="col-md-12"><br>
                                        <div class="col-xs-2">
                                            <label>Proveedor</label><br><br>
                                            <select class="form-control" v-model="ticket.incidence">
                                                <option></option>
                                                <option v-for="incidence in incidences" value="{{incidence}}">
                                                    {{incidence.incidenceName}}
                                                </option>
                                            </select>
                                        </div>
                                            <div class="col-xs-2">
                                            <label>Cuenta bancaria</label><br><br>
                                            <select class="form-control" v-model="ticket.incidence">
                                                <option></option>
                                                <option v-for="incidence in incidences" value="{{incidence}}">
                                                    {{incidence.incidenceName}}
                                                </option>
                                            </select>
                                        </div>
                                            <div class="col-xs-2">
                                                <label>Tipo de moneda</label><br><br>
                                                <select class="form-control" v-model="ticket.incidence">
                                                    <option></option>
                                                    <option v-for="incidence in incidences" value="{{incidence}}">
                                                        {{incidence.incidenceName}}
                                                    </option>
                                                </select>
                                            </div>
                                            <div class="col-xs-3">
                                                <label>Tipo de cambio</label><br><br>
                                                <input class="form-control" type="number" placeholder="$" onpaste="alert('Acceso Denegado');return false">
                                            </div>
                                            <div class="col-xs-3">
                                                <label>Monto total</label><br><br>
                                                <input type="number" class="form-control" placeholder="$" onpaste="alert('Acceso Denegado');return false">
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

            <!--TERMINA MODAL DE AGREGAR COMPROBANTE PAGINA 29-->

            <!--EMPIEZA CARGA DE DOCUMENTOS EN (PDF Y XML)-->

                <div class="modal fade" id="MImodal" role="dialog">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                                <div class="panel panel-default">
                                    <div class="row">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <div>
                                                <h5 class="modal-title">Carga de documentos</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            <div class="panel panel-default" v-if="typeFile != ''">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="col-md-12">
                                            <form class="form-inline" id="" enctype="multipart/form-data" v-on:submit.prevent="saveFile">
                                                <div class="form-group">
                                                    <label>Factura PDF</label>
                                                    <input @change="setFile($event, docType)" type="file"
                                                           class="form-control"
                                                           :disabled="isSaving"
                                                           :name="'file-type-' + docType.documentType.idDocumentType"
                                                           accept="application/pdf" required />
                                                </div>
                                            </form>
                                            </div><br><br><br>
                                            <div class="col-md-12">
                                                <form class="form-inline" id="" enctype="multipart/form-data" v-on:submit.prevent="saveFile">
                                                    <div class="form-group">
                                                        <label>Factura XML</label>
                                                        <input @change="setFile($event, docType)" type="file"
                                                               class="form-control"
                                                               :disabled="isSaving"
                                                               :name="'file-type-' + docType.documentType.idDocumentType"
                                                               accept=".xml" required />
                                                    </div>
                                                </form>
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

            <!--TERMINA CARGA DE DOCUMENTOS EN (PDF Y XML)-->

            <!--EMPIEZA SOLICITUD VALORANDO LOS ARCHIVOS (PDF Y XML) PAGINA 31-->

                <br><br>
                <div  class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #F2F2F2">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-12">
                                <h5><b style="color: black">Datos del solicitante</b></h5>
                                <table class="table table-striped">
                                    <thead style="background-color: #aaaaaa">
                                    <th class="col-md-4">Nombre</th>
                                    <th class="col-md-4">Area</th>
                                    <th class="col-md-4">Fecha de solicitud</th>
                                    </thead>
                                    <tbody>
                                    <tr v-for="compra in compra">
                                        <td class="col-md-4">{{Name}}</td>
                                        <td class="col-md-4">{{Area}}</td>
                                        <td class="col-md-4">{{date}}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel default" v-if="selectionOptions.role.idRole> 0" style="background-color: #f2f2f2">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-12">
                                <h5><b style="color: black">Datos de solicitud</b></h5>
                                <table class="table table-striped">
                                    <thead>
                                    <th class="col-md-6" style="background-color: #aaaaaa">Centro de costos</th>
                                    <th class="col-md-6" style="background-color: #aaaaaa">Centro de concepto</th>
                                    </thead>
                                    <tbody>
                                    <tr v-for="compra in compra">
                                        <td class="col-md-6">{{Centro de costos}}</td>
                                        <td class="col-md-6">{{Centro de concepto</td>
                                    </tr>
                                    </tbody>
                                    <thead>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Lista de productos</th>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Cantidad</th>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Justificacion</th>
                                    </thead>
                                    <tbody>
                                    <tr v-for="compra in compra">
                                        <td class="col-md-4">{{Computadoras}}</td>
                                        <td class="col-md-4">{{Cantidad}}</td>
                                        <td class="col-md-4">{{Justificacion}}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #f2f2f2">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-7"><br>
                                <h5><b  style="color: black">Cotizacion 1</b></h5>
                            </div>
                            <form><br>
                                <div class="col-md-5">
                                    <label>Monto cotizado sin IVA</label>
                                    <label>$<input type="number"></label>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-12" style="background-color: #AFAFAF">
                                <h7><b>Documento</b></h7><br><br>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-2"><br>
                                <h6>Nombre del archivo</h6>
                            </div>
                            <div class="col-md-7"><br>
                                <button class="btn btn-primary btn-sm">DESCARGAR</button>
                            </div>
                            <div class="col-md-3"><br>
                                <button class="btn btn-success btn-sm"><span class="glyphicon glyphicon-ok"></span></button>
                            </div><br>
                        </div>
                    </div>
                    <br>
                    <br>
                </div>
                <div class="panel panel default" v-if="selectionOptions.role.idRole> 0" style="background-color: #f2f2f2">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-12">
                                <h5><b style="color: black">Orden de compra</b></h5>
                                <table class="table table-striped">
                                    <div class="col-md-12">
                                    <thead>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Numero de orden de compra</th>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Monto total</th>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Fecha de generacion</th>
                                    </thead>
                                    </div>
                                    <div class="col-md-12">
                                    <tbody>
                                    <tr v-for="compra in compra">
                                        <td class="col-md-3">{{name}}</td>
                                        <td class="col-md-3">{{Monto total}}</td>
                                        <td class="col-md-3">{{fecha}}</td>
                                        <td class="col-md-3">
                                            <button class="btn btn-primary btn-sm">DESCARGAR</button>
                                        </td>
                                    </tr>
                                    </tbody>
                                    </div>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="panel panel default" style="background-color: #f2f2f2">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-12">
                                    <h5><b style="color: black">Comprobante</b></h5>
                                    <div class="col-xs-6">
                                            <label>Proveedor</label><br><br>
                                            <select class="form-control" v-model="ticket.incidence">
                                                <option></option>
                                                <option v-for="incidence in incidences" value="{{incidence}}">
                                                    {{incidence.incidenceName}}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-xs-5">
                                            <label>Monto total</label><br><br>
                                            <input type="number" class="form-control" placeholder="$" onpaste="alert('Acceso Denegado');return false">
                                        </div>
                                    <div class="col-xs-1">
                                        <label></label><br><br>
                                        <button class="btn btn-danger btn-sm" style="margin-top: 10%">Eliminar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-11">
                            <button type="button" class="btn btn-success btn-sm" style="margin-left: 97%" data-toggle="modal" data-target="#Enviarsolicitudm">Enviar</button>
                        </div>
                        <div class="col-md-1">
                            <button type="button" class="btn btn-default btn-sm">Cancelar</button>
                        </div>
                    </div>
                </div>


            <!--TERMINA SOLICITUD VALORANDO LOS ARCHIVOS (PDF Y XML) PAGINA 31-->

            <!--EMPIEZA MODAL DEL BOTON DE ENVIAR SOLICITUD PAGINA 32-->

            <div class="modal fade" id="Enviarsolicitudm" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">x</button>
                            <h4 class="modal-title">Enviar solicitud</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <p>La solicitud sera enviada al administrador de pagos, para procesar el pago de la factura</p>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button"  onclick="parent.location='https://mail.google.com/mail/u/1/#inbox?compose=15c3be08b0b2647a'" class="btn btn-success">Aceptar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

            <!--TERMINA MODAL DEL BOTON DE ENVIAR SOLICITUD PAGINA 32-->

            <!--EMPIEZA MODAL DE JUSTIFICAR RECHAZO PAGINA 33-->

            <div class="modal fade" id="Justificarechazo" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">x</button>
                            <h4 class="modal-title">Justificar rechazo</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <textarea class="form-control" rows="3">{{rejected request}}</textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" onclick="parent.location='https://mail.google.com/mail/u/1/?tab=wm#inbox?compose=15c3ce4fb65c6704'">Aceptar</button>
                            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#SolicitudmodaL">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

            <!--EMPIEZA MODAL DE JUSTIFICAR RECHAZO PAGINA 33-->

            <!--EMPIEZA VENTANA MODAL DETALLE DE SOLLCITUD-->

            <div class="modal fade" id="SolicitudmodaL" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">x</button>
                            <h4 class="modal-title">Detalle de solicitud</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <p>Solicitud fuera de presupuesto</p>
                                    <p>Se enviara a planeacion financiera para su validacion.</p>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" onclick="parent.location='https://mail.google.com/mail/u/1/?tab=wm#inbox?compose=15c3c687ca95b2dc'">Aceptar</button>
                            <button type="button" class="btn btn-default">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

            <!--TERMINA VENTANA MODAL DETALLE DE SOLiCITUD-->

            <!--EMPIEZA DETALLE DE SOLICITUD DE COMPRA PAGINA 35-->
            <br><br>
            <div  class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos del solicitante</b></h5>
                            <table class="table table-striped">
                                <thead style="background-color: #aaaaaa">
                                <th class="col-md-4">Nombre</th>
                                <th class="col-md-4">Area</th>
                                <th class="col-md-4">Fecha de solicitud</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-4">{{Name}}</td>
                                    <td class="col-md-4">{{Area}}</td>
                                    <td class="col-md-4">{{date}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel default" v-if="selectionOptions.role.idRole> 0" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos de solicitud</b></h5>
                            <table class="table table-striped">
                                <thead>
                                <th class="col-md-6" style="background-color: #aaaaaa">Centro de costos</th>
                                <th class="col-md-6" style="background-color: #aaaaaa">Centro de concepto</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-6">{{Centro de costos}}</td>
                                    <td class="col-md-6">{{Centro de concepto</td>
                                </tr>
                                </tbody>
                                <thead>
                                <th class="col-md-4" style="background-color: #aaaaaa">Lista de productos</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Cantidad</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Justificacion</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-4">{{Computadoras}}</td>
                                    <td class="col-md-4">{{Cantidad}}</td>
                                    <td class="col-md-4">{{Justificacion}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel default" v-if="selectionOptions.role.idRole> 0" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Informacion</b></h5>
                            <table class="table table-striped">
                                <div class="col-md-12">
                                    <thead>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Usuario</th>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Fecha</th>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Estado</th>
                                    </thead>
                                </div>
                                <div class="col-md-12">
                                    <tbody>
                                    <tr v-for="compra in compra">
                                        <td class="col-md-4">{{name}}</td>
                                        <td class="col-md-4">{{Monto total}}</td>
                                        <td class="col-md-4">{{fecha}}</td>
                                    </tr>
                                    </tbody>
                                </div>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-7"><br>
                            <h5><b  style="color: black">Cotizacion 1</b></h5>
                        </div>
                        <form><br>
                            <div class="col-md-5">
                                <label>Monto cotizado sin IVA</label>
                                <label>$<input type="number"></label>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #AFAFAF">
                            <h7><b>Documento</b></h7><br><br>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-2"><br>
                            <h6>Nombre del archivo</h6>
                        </div>
                        <div class="col-md-7"><br>
                            <button class="btn btn-primary btn-sm">DESCARGAR</button>
                        </div>
                        <div class="col-md-3"><br>
                            <button class="btn btn-success btn-sm"><span class="glyphicon glyphicon-ok"></span></button>
                        </div><br>
                    </div>
                </div>
                <br>
                <br>
                <div class="row">
            <div class="col-md-12">
                <div class="col-md-12" style="background-color: #aaaaaa">
                    <h6><b>Justificacion</b></h6>
                </div><br>
                <div class="col-md-12"><br>
                    <h7>873465tg4573gf387fh48346d589347h6d5465347g5f87d5h3478888888888888888888888888888888888888888888888</h7>
                </div>
            </div>
            </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-12">
                        <button type="button" class="btn btn-default btn-sm" style="margin-left: 97%" onclick="parent.location='http://localhost:8080/BIDGroupLines/siad/request-spending-detail-current'">Salir</button>
                    </div>
                </div>
            </div>

            <!--TERMINA DETALLE DE SOLICITUD DE COMPRA PAGINA 35-->

            <!--EMPIEZA PLANEACION FINANCIERA DE QUE AUTORIZO LA SOLICITUD PAGINA 35-->

            <br><br>
            <div  class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos del solicitante</b></h5>
                            <table class="table table-striped">
                                <thead style="background-color: #aaaaaa">
                                <th class="col-md-4">Nombre</th>
                                <th class="col-md-4">Area</th>
                                <th class="col-md-4">Fecha de solicitud</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-4">{{Name}}</td>
                                    <td class="col-md-4">{{Area}}</td>
                                    <td class="col-md-4">{{date}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel default" v-if="selectionOptions.role.idRole> 0" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos de solicitud</b></h5>
                            <table class="table table-striped">
                                <thead>
                                <th class="col-md-6" style="background-color: #aaaaaa">Centro de costos</th>
                                <th class="col-md-6" style="background-color: #aaaaaa">Centro de concepto</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-6">{{Centro de costos}}</td>
                                    <td class="col-md-6">{{Centro de concepto</td>
                                </tr>
                                </tbody>
                                <thead>
                                <th class="col-md-4" style="background-color: #aaaaaa">Lista de productos</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Cantidad</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Justificacion</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-4">{{Computadoras}}</td>
                                    <td class="col-md-4">{{Cantidad}}</td>
                                    <td class="col-md-4">{{Justificacion}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel default" v-if="selectionOptions.role.idRole> 0" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Informacion</b></h5>
                            <table class="table table-striped">
                                <div class="col-md-12">
                                    <thead>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Usuario</th>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Fecha</th>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Estado</th>
                                    </thead>
                                </div>
                                <div class="col-md-12">
                                    <tbody>
                                    <tr v-for="compra in compra">
                                        <td class="col-md-4">{{name}}</td>
                                        <td class="col-md-4">{{Monto total}}</td>
                                        <td class="col-md-4">{{fecha}}</td>
                                    </tr>
                                    </tbody>
                                </div>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-7"><br>
                            <h5><b  style="color: black">Cotizacion 1</b></h5>
                        </div>
                        <form><br>
                            <div class="col-md-5">
                                <label>Monto cotizado sin IVA</label>
                                <label>$<input type="number"></label>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #AFAFAF">
                            <h7><b>Documento</b></h7><br><br>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-2"><br>
                            <h6>Nombre del archivo</h6>
                        </div>
                        <div class="col-md-7"><br>
                            <button class="btn btn-primary btn-sm">DESCARGAR</button>
                        </div>
                        <div class="col-md-3"><br>
                            <button class="btn btn-success btn-sm"><span class="glyphicon glyphicon-ok"></span></button>
                        </div><br>
                    </div>
                </div>
                <br>
                <br>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #aaaaaa">
                            <h6><b>Justificacion</b></h6>
                        </div><br>
                        <div class="col-md-12"><br>
                            <h7>873465tg4573gf387fh48346d589347h6d5465347g5f87d5h3478888888888888888888888888888888888888888888888</h7>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-default" style="background-color: #f2f2f2">
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-12">
                        <h5><b style="color: black">Orden de compra</b></h5>
                        <table class="table table-striped">
                            <div class="col-md-12">
                                <thead>
                                <th class="col-md-4" style="background-color: #aaaaaa">Numero de orden de compra</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Monto total</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Fecha de generacion</th>
                                </thead>
                            </div>
                            <div class="col-md-12">
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-3">{{name}}</td>
                                    <td class="col-md-3">{{Monto total}}</td>
                                    <td class="col-md-3">{{fecha}}</td>
                                    <td class="col-md-3">
                                        <button class="btn btn-primary btn-sm">DESCARGAR</button>
                                    </td>
                                </tr>
                                </tbody>
                            </div>
                        </table>
                    </div>
                </div>
            </div>
            </div>
            <div class="row">
            <div class="col-md-12">
                <div class="col-md-9">
                    <button class="btn btn-primary btn-sm" style="margin-left: 80%" data-toggle="modal" data-target="#Detallemodal" >Generar orden de compra</button>
                </div>
                <div class="col-md-2">
                    <button type="button" class="btn btn-danger btn-sm" style="margin-left: 10%">Agregar Comprobante</button>
                </div>
                <div class="col-md-1">
                    <button type="button" class="btn btn-default btn-sm">Cancelar</button>
                </div>
            </div>
        </div>


            <!--TERMINA PLANEACION FINANCIERA DE QUE AUTORIZO LA SOLICITUD PAGINA 35-->

            <!--EMPIEZA VENTANA MODAL DETALLE DE SOLICITUD PAGINA 36-->

            <div class="modal fade" id="Detallemodal" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">x</button>
                            <h4 class="modal-title">Detalle de solicitud</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <p>Solicitud fuera del monto autorizado</p>
                                    <p>Se enviara a "CORREO" para su validacion</p>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" onclick="parent.location='https://mail.google.com/mail/u/1/?tab=wm#inbox?compose=15c3cb6147788c11'">Aceptar</button>
                            <button type="button" class="btn btn-default">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

            <!--TERMINA VENTANA MODAL DETALLE DE SOLICITUD PAGINA 36 -->

            <!--EMPIEZA VENTANA DE FINALAZADAS PAGINA 40-->

            <div  class="panel panel-default" v-if="selectedOptions.role.idRole > 0" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos del solicitante</b></h5>
                            <table class="table table-striped">
                                <thead style="background-color: #aaaaaa">
                                <th class="col-md-4">Nombre</th>
                                <th class="col-md-4">Area</th>
                                <th class="col-md-4">Fecha de solicitud</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-4">{{Name}}</td>
                                    <td class="col-md-4">{{Area}}</td>
                                    <td class="col-md-4">{{date}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel default" v-if="selectionOptions.role.idRole> 0" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos de solicitud</b></h5>
                            <table class="table table-striped">
                                <thead>
                                <th class="col-md-6" style="background-color: #aaaaaa">Centro de costos</th>
                                <th class="col-md-6" style="background-color: #aaaaaa">Centro de concepto</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-6">{{Centro de costos}}</td>
                                    <td class="col-md-6">{{Centro de concepto</td>
                                </tr>
                                </tbody>
                                <thead>
                                <th class="col-md-4" style="background-color: #aaaaaa">Lista de productos</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Cantidad</th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Justificacion</th>
                                </thead>
                                <tbody>
                                <tr v-for="compra in compra">
                                    <td class="col-md-4">{{Computadoras}}</td>
                                    <td class="col-md-4">{{Cantidad}}</td>
                                    <td class="col-md-4">{{Justificacion}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel default" v-if="selectionOptions.role.idRole> 0" style="background-color: #f2f2f2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Informacion</b></h5>
                            <table class="table table-striped">
                                <div class="col-md-12">
                                    <thead>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Usuario</th>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Fecha</th>
                                    <th class="col-md-4" style="background-color: #aaaaaa">Estado</th>
                                    </thead>
                                </div>
                                <div class="col-md-12">
                                    <tbody>
                                    <tr v-for="compra in compra">
                                        <td class="col-md-4">{{name}}</td>
                                        <td class="col-md-4">{{Monto total}}</td>
                                        <td class="col-md-4">{{fecha}}</td>
                                    </tr>
                                    </tbody>
                                </div>
                            </table>
                        </div>
                    </div>
                </div>
            </div><br>
            <div id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default" v-if="selectedOptions.role.idRole > 0">
                    <div class="card">
                        <div class="card-header" role="tab" id="headingThree">
                            <div class="panel-heading" style="background-color: #f2f2f2">
                                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree"
                                   aria-expanded="false" aria-controls="collapseThree">
                                    <div class="col-md-11 text-center">
                                        <b style="color: black"><span class=".glyphicon .glyphicon-chevron-down"></span>Detalle</b>
                                    </div>
                                </a>
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
                                                    <td class="col-md-3 text-center"><b>Usuario</b></td>
                                                    <td class="col-md-3 text-center"><b>Fecha</b></td>
                                                    <td class="col-md-3 text-center"><b>Estado</b></td>
                                                </tr>
                                                <tr>
                                                    <td class="col-md-3 text-center">{{leo}}</td>
                                                    <td class="col-md-3 text-center">{{16-03-2017}}</td>
                                                    <td class="col-md-3 text-center">{{pagada}}</td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <button type="button" class="btn btn-default btn-sm" style="margin-left: 97%">Salir</button>
                        </div>
                    </div>
                </div>
                <!--TERMINA VENTANA DE FINALAZADAS PAGINA 40-->
            </div>
        </div>
    </jsp:body>
</t:template>
