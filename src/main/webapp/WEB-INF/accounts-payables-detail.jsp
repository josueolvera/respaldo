<%--
  Created by IntelliJ IDEA.
  User: jcesar
  Date: 08/06/2017
  Time: 12:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>
<t:template pageTitle="BID Group: Detalle de cuentas por pagar">
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
                    this.getRequestsDateProgrammer();
                    this.getProContact();
                    this.getProvidersReque();
                    this.getPurchaseInvoice();
                    this.getdwEmpleados();
                    this.getRedocuments();
                    this.getBranchDis();
                    this.getEmployeeReqDates();
                    this.getBranchDis();
                    this.getRolesE();
                    this.descargarArchivos();
                },
                data: {
                    idRequ: ${idRequest},
                    idProv: ${idProvider},
                    idPurcha: ${idPurchaseInvoices},
                    idEmployee: ${idEmployee},
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
                    requestsDateProg: [],
                    descargaAr: [],
                    requestsDateProgrammer: [],
                    fileUrl: ROOT_URL +'/purchase-invoices-files/attachment/download/',
                    branchDistributor: [],
                    rolesEmplo: [],
                    proveContact: [],
                    providersReq: [],
                    employeeFind: [],
                    requestInformation: [],
                    purchaseInvoicex: [],
                    dwEmpleado: [],
                    reqDocuments: [],
                    payables: [],
                    payable: {
                        scheDate: '',
                        requestId: '',
                        countUpdate: ''
                    }
                },
                methods: {
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    proFecha: function () {
                        var fecha = new Date();
                        var fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
                        this.timePickerDe = $('#proFecha').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            defaultDate: fecha_actual,
                            minDate: fecha_actual
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
                    descargarArchivos: function () {
                        this.$http.get(ROOT_URL + "/purchase-invoices-files").success(function (data) {
                                this.descargaAr = data;
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
                            var jsonObjectIndex = {};
                            data.forEach(function (request) {
                                if (isNaN(request.request.distributorCostCenter.costCenter)) {
                                    jsonObjectIndex[request.request.distributorCostCenter.costCenter._id] = request.request.distributorCostCenter.costCenter;
                                } else {
                                    request.request.distributorCostCenter.costCenter = jsonObjectIndex[request.request.distributorCostCenter.costCenter];
                                }
                            });
                            this.purchaseInvoicex = data;
                        });
                    },
                    getEmployeeReqDates: function () {
                        this.$http.get(ROOT_URL + '/dw-employees/employee-find?idEmployee=' + this.idEmployee).success(function (data) {
                            this.employeeFind = data;
                        });
                    },
                    getRequestsDateProgrammer: function () {
                        this.$http.get(ROOT_URL + '/accounts-payables-dates').success(function (data) {
                            this.requestsDateProgrammer = data;
                        });
                    },
                    getProContact: function () {
                        this.$http.get(ROOT_URL + '/provider-contact').success(function (data) {
                            this.proveContact = data;
                        });
                    },
                    getProvidersReque: function () {
                        this.$http.get(ROOT_URL + '/providers').success(function (data) {
                            this.providersReq = data;
                        });
                    },
                    getdwEmpleados: function () {
                        this.$http.get(ROOT_URL + '/dw-employees').success(function (data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (request) {
                                if (isNaN(request.employee)) {
                                    jsonObjectIndex[request.employee._id] = request.employee;
                                } else {
                                    request.employee = jsonObjectIndex[request.employee];
                                }
                            });
                            data.forEach(function (request) {
                                if (isNaN(request.dwEnterprise)) {
                                    jsonObjectIndex[request.dwEnterprise._id] = request.dwEnterprise;
                                } else {
                                    request.dwEnterprise = jsonObjectIndex[request.dwEnterprise];
                                }
                            });
                            data.forEach(function (request) {
                                if (isNaN(request.dwEnterprise.area)) {
                                    jsonObjectIndex[request.dwEnterprise.area._id] = request.dwEnterprise.area;
                                } else {
                                    request.dwEnterprise.area = jsonObjectIndex[request.dwEnterprise.area];
                                }
                            });
                            data.forEach(function (request) {
                                if (isNaN(request.dwEnterprise.region)) {
                                    jsonObjectIndex[request.dwEnterprise.region._id] = request.dwEnterprise.region;
                                } else {
                                    request.dwEnterprise.region = jsonObjectIndex[request.dwEnterprise.region];
                                }
                            });
                            this.dwEmpleado = data;
                        });
                    },
                    getRedocuments: function () {
                        this.$http.get(ROOT_URL + '/order-documents-request').success(function (data) {
                            this.reqDocuments = data;
                        });
                    },
                    getBranchDis: function () {
                        this.$http.get(ROOT_URL + '/dw-enterprises').success(function (data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (request) {
                                if (isNaN(request.branch)) {
                                    jsonObjectIndex[request.branch._id] = request.branch;
                                } else {
                                    request.branch = jsonObjectIndex[request.branch];
                                }
                            });
                            this.branchDistributor = data;
                        });
                    },
                    getRolesE: function () {
                        this.$http.get(ROOT_URL + '/roles').success(function (data) {
                            this.rolesEmplo = data;
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
                    //guardar payables ***
                    showModalReprogram: function () {
                        $("#modalRepro").modal("show");
                    },
                    savePayables: function () {
                        if (this.payable.scheDate == null || this.payable.scheDate == "") {
                            showAlert("Seleccionar una fecha", {type: 3});
                            $("#modalRepro").modal("hide");
                        } else if (this.payable.countUpdate > 0) {
                            this.payable.scheDate = this.timePickerDe.DateTimePicker.date().toISOString().slice(0, -1);
                            this.payable.requestId = this.idRequ;
                            console.log(this.payable.scheDate);
                            this.$http.post(ROOT_URL + "/accounts-payables-dates/update-rdates", JSON.stringify(this.payable)).success(function (data) {
                                this.payables = [];
                                this.payables = data;
                                $("#modalRepro").modal("hide");
                                showAlert("Registro guardado con exito");
                                setTimeout("location.href = '../siad/accounts-payables'",2000);

                            }).error(function () {
                                showAlert("Fecha invalida.", {type: 3});
                            })
                        } else if (this.payable.scheDate != null || this.payable.scheDate != "") {
                            this.payable.scheDate = this.timePickerDe.DateTimePicker.date().toISOString().slice(0, -1);
                            this.payable.requestId = this.idRequ;
                            this.$http.post(ROOT_URL + "/accounts-payables-dates/save-requestdates", JSON.stringify(this.payable)).success(function (data) {
                                this.payables = [];
                                this.payables = data;
                                $("#modalRepro").modal("hide");
                                showAlert("Registro guardado con exito");
                                setTimeout("location.href = '../siad/accounts-payables'",2000);
                            }).error(function () {
                                showAlert("Fecha invalida", {type: 3});
                            })
                        }
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
            function backBandeja() {
                window.history.back();
            }
            function keyCaracteres(obj){
                var inicial = obj.value;
                if (obj.value.length > 0){
                    obj.value = '';
                    showAlert("Debes seleccionar una fecha del calendario.", {type: 3});
                }else {
                    showAlert("Debes seleccionar una fecha del calendario.", {type: 3});
                    return false;
                }
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
            .btn-hover {
                font-weight: normal;
                color: #333333;
                cursor: pointer;
                background-color: inherit;
                border-color: transparent;
            }
            .btn-circle {
                width: 40px;
                height: 38px;
                text-align: center;
                padding: 6px 0;
                font-size: 14px;
                line-height: 1.828571429;
                border-radius: 19px;
            }

        </style>
    </jsp:attribute>
    <jsp:body>
        <div id="content">
        <div class="loading" v-if="dwEmpleado.length==0">
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="col-md-5">
                    <h2>Detalle de cuentas por pagar</h2>
                </div>
                <div class="col-md-2 text-right">
                </div>
                <div class="col-md-2">

                </div>
                <div class="col-md-3 text-right" style="margin-top: 10px">
                    <label>Nombre de usuario</label>
                    <p>
                        <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                    </p>
                </div>
            </div>
        </div>
        <br>
        <div class="panel panel-default">
            <div class="panel-heading"><b>Información de proveedor</b></div>
            <div class="panel-body">
                <div class="col-md-12">
                    <div class="row">
                        <table class="table table-striped">
                            <tr>
                                <td class="col-md-3"><b>Nombre o razón social</b></td>
                                <td class="col-md-3"><b>RFC</b></td>
                                <td class="col-md-3"><b>Días de crédito</b></td>
                                <td class="col-md-3"><b>Fecha de corte</b></td>
                            </tr>
                            <tr v-for="provider in providersReq" v-if="this.idProv == provider.idProvider">
                                <td class="col-md-3">{{provider.providerName}}</td>
                                <td class="col-md-3">{{provider.rfc}}</td>
                                <td class="col-md-3">{{provider.creditDays}}</td>
                                <td class="col-md-3">{{provider.cuttingDate}}</td>
                            </tr>
                            <tr>
                                <td class="col-md-3"><b>Nombre del contacto</b></td>
                                <td class="col-md-3"><b>Puesto</b></td>
                                <td class="col-md-3"><b>Teléfono</b></td>
                                <td class="col-md-3"><b>Correo</b></td>
                            </tr>
                            <tr v-for="pcontact in proveContact" v-if="this.idProv == pcontact.idProvider">
                                <td class="col-md-3">{{pcontact.name}}</td>
                                <td class="col-md-3">{{pcontact.post}}</td>
                                <td class="col-md-3">{{pcontact.phoneNumber}}</td>
                                <td class="col-md-3">{{pcontact.email}}</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading"><b>Información de pago</b></div>
            <div class="panel-body">
                <div class="col-md-12">
                    <div class="row" style="height: 120px">
                        <table class="table table-striped">
                            <tr>
                                <td class="col-md-2"><b>Banco</b></td>
                                <td class="col-md-3"><b>Cuenta bancaria</b></td>
                                <td class="col-md-2"><b>Monto total</b></td>
                                <td class="col-md-2 text-center"><b>Nombre del archivo</b></td>
                                <td class="col-md-2 text-center"><b>Descargar</b></td>
                            </tr>
                            <tr v-for="purch in purchaseInvoicex" v-if="this.idPurcha == purch.idPurchaseInvoices">
                                <td class="col-md-2" rowspan="2" style="margin-top: 10px">{{purch.account.bank.acronyms}}</td>
                                <td class="col-md-3" rowspan="2" style="margin-top: 10px">{{purch.account.accountNumber}}</td>
                                <td class="col-md-2" rowspan="2" style="margin-top: 10px">{{purch.totalAmount | currency}}</td>
                                <td class="col-md-2" style="margin-top: 3px">
                                    <div class="col-md-12 text-center" style="margin-top: 1px"
                                         v-for="files in descargaAr" v-if="this.idPurcha == files.idPurchaseInvoices">
                                            {{files.fileName}} <br><br>
                                    </div>
                                </td>
                                <td class="col-md-2">
                                    <div class="col-md-12 text-center" v-for="files in descargaAr"
                                         v-if="this.idPurcha == files.idPurchaseInvoices">
                                            <a class="btn btn-md btn-info" data-toggle="tooltip" data-placement="top" title="Descargar"
                                               :href="fileUrl + files.idPurchaseInvoicesFiles" style="margin-top: 2px">
                                                <span class="glyphicon glyphicon-download-alt"></span>
                                            </a>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel panel-default" v-model="employeeFind.length > 0">
            <div class="panel-heading"><b>Información de solicitante</b></div>
            <div class="panel-body">
                <div class="col-md-12">
                    <div class="row">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <td class="col-md-3"><b>Nombre</b></td>
                                <td class="col-md-3"><b>Puesto</b></td>
                                <td class="col-md-3"><b>Empresa</b></td>
                                <td class="col-md-3"><b>Región</b></td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="dw in dwEmpleado" v-if="dw.idEmployee == this.idEmployee">
                                <td class="col-md-3">
                                    {{dw.employee.fullName}}
                                </td>
                                <td class="col-md-3" v-for="rol in rolesEmplo" v-if="dw.idRole == rol.idRole">
                                    {{rol.roleName}}
                                </td>
                                <td class="col-md-3" v-for="bran in branchDistributor" v-if="dw.idDwEnterprise == bran.idDwEnterprise">
                                    {{bran.distributor.acronyms}}
                                </td>
                                <td>
                                    {{dw.dwEnterprise.region.regionName}}
                                </td>
                            </tr>
                            <tr>
                                <td class="col-md-3"><b>Sucursal</b></td>
                                <td class="col-md-3"><b>Área</b></td>
                                <td class="col-md-3"><b>Centro de costos</b></td>
                                <td class="col-md-3"></td>
                            </tr>
                            <tr v-for="dw in dwEmpleado" v-if="dw.idEmployee == this.idEmployee">
                                <td class="col-md-3" v-for="bran in branchDistributor" v-if="dw.idDwEnterprise == bran.idDwEnterprise">
                                    {{bran.branch.branchName}}
                                </td>
                                <td class="col-md-3">
                                    {{dw.dwEnterprise.area.areaName}}
                                </td>
                                <td class="col-md-3" v-for="purchasex in purchaseInvoicex" v-if="this.idPurcha == purchasex.idPurchaseInvoices">
                                    {{purchasex.request.distributorCostCenter.costCenter.name}}
                                </td>
                                <td class="col-md-3"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading"><b>Información de solicitud</b></div>
            <div class="panel-body">
                <div class="col-md-12">
                    <div class="row">
                        <table class="table table-striped">
                            <tr>
                                <td class="col-md-2"><b>Concepto</b></td>
                                <td class="col-md-3"><b>Fecha de solicitud</b></td>
                                <td class="col-md-3"><b>Fecha límite de pago</b></td>
                                <td class="col-md-2"><b>Programar fecha de pago</b></td>
                            </tr>
                            <tr v-for="purchasex in purchaseInvoicex" v-if="this.idPurcha == purchasex.idPurchaseInvoices">
                                <td class="col-md-3">{{purchasex.conceptName}}</td>
                                <td class="col-md-3">{{purchasex.request.creationDateFormats.dateNumber}}</td>
                                <td class="col-md-3">{{purchasex.paydayLimitFormats.dateNumber}}</td>
                                <td class="col-md-3">
                                <div class='input-group date' id='proFecha'>
                                    <input type="text" id="fechaPayable" v-model="payable.scheDate" class="form-control"
                                           maxlength="10" required onkeypress="return keyCaracteres(this)">
                                    <span class="input-group-addon" @click="proFecha()">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                                    <input type="text" style="display: none" disabled
                                           :value="idRequ" v-model="payable.requestId" />
                                    <input type="text" style="display: none" disabled
                                            v-for="rdp in requestsDateProgrammer" v-if="rdp.idRequest == purchasex.idRequest"
                                           :value="rdp.countUpdate" v-model="payable.countUpdate" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="text-right">
                    <button class="btn btn-success" @click="showModalReprogram()">Guardar</button> &nbsp;&nbsp;
                    <a href="../siad/accounts-payables">
                        <button class="btn btn-default">Salir</button>
                    </a>
                </div>
            </div>
        </div><br>
        <%--modal enviar solicitud a tesoreria--%>
        <div class="modal fade" id="modalRepro" tabindex="-1" role="dialog" aria-labelledby=""
             aria-hidden="true">
            <div class="modal-dialog modal-ms">
                <div class="modal-content modal-ms">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id=""><label>Enviar solicitud</label></h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12">
                                <p align="center" style="font-size: 16px">La solicitud será enviada a tesorería<br>
                                    para realizar el pago.</p>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success" @click="savePayables()">Aceptar</button>
                        <button type="button" class="btn btn-default" class="close" data-dismiss="modal"
                                aria-hidden="true">Cancelar
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <br>
    </jsp:body>
</t:template>

