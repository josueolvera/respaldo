<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../assets/css/barralateral.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>

<t:template pageTitle="BID Group: Presupuestos">

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
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
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
        <script>
            $('.selectpicker').selectpicker({
                style: 'btn-info',
                size: 4
            });

        </script>
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                created: function () {
                    this.setYears();
                },
                ready: function () {
                    this.getUserInSession();
                    this.getBudgetTypes();
                },
                data: {
                    now: "${now}",
                    minYear: null,
                    maxYear: null,
                    totalCostCenter: null,
                    years: [],
                    budgets: [],
                    budgetsReports: [],
                    concepts: [],
                    budgetTypes: [],
                    user: {},
                    selected: {
                        budgetNature: null,
                        budgetType: null,
                        costCenter: null,
                        budgetCategory: null,
                        year: null,
                        yearFromCopy: null,
                        yearToCopy: null,
                        budget: null,
                        subbudget: null,
                        concept: ''
                    },
                    emails: {},
                    searching: false,
                    rolesCostCenter: [],
                    costCenterList: [],
                    budgetNatureList: [],
                    budgetCategories: [],
                    role: null,
                    authorizationBudget: {},
                    errorMessage: '',
                    validation: false,
                    authorization: false,
                    annual: false
                },
                methods: {
                    getUserInSession: function () {
                        this.$http.get(ROOT_URL + "/user")
                            .success(function (data) {
                                this.user = data;
                                this.role = this.user.dwEmployee.idRole;
                                this.getRolesCostCenter(this.role);
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
                                data.forEach(function (item) {
                                    index = self.arrayObjectIndexOf(self.budgetNatureList, item.budgetNature.idBudgetNature, 'idBudgetNature');
                                    if (index == -1) self.budgetNatureList.push(item.budgetNature);
                                });
                            })
                            .error(function (data) {
                            });
                    },
                    getBudgetTypes: function () {
                        this.$http.get(ROOT_URL + '/budget-types')
                            .success(function (data) {
                                this.budgetTypes = data;
                            });
                    },
                    setYears: function () {
                        var now = new Date(this.now);
                        this.selected.year = now.getFullYear();
                        this.minYear = this.selected.year - 3;
                        this.maxYear = this.selected.year + 5;
                        for (var i = this.minYear; i <= this.maxYear; i++) {
                            this.years.push(i)
                        }
                    },
                    searchBudget: function (year, budgetType, budgetNature, budgetCategory) {
                        this.searching = true;
                        this.getBudgets(year, budgetType, budgetNature, budgetCategory);
                    },
                    getBudgets: function (year, budgetType, budgetNature, budgetCategory) {
                        var url = ROOT_URL +
                            '/budgets?cost_center=' + this.selected.costCenter.idCostCenter;
                        if (year != null) {
                            url += '&year=' + this.selected.year;
                        }
                        if (budgetType != null) {
                            url += '&budget_type=' + this.selected.budgetType.idBudgetType;
                        }
                        if (budgetNature != null) {
                            url += '&budget_nature=' + this.selected.budgetNature.idBudgetNature;
                        }
                        this.$http.get(url)
                            .success(function (data) {
                                var self = this;
                                this.budgets = data;
                                this.searching = false;
                                this.getAuthorizationBudget();
                                this.getTotalCostCenter();
                                this.getBudgetsReport();
                                if (data.length <= 0) {
                                    showAlert('Sin rubros asignados');
                                }
                            })
                            .error(function (data) {
                            });
                    },
                    getAuthorizationBudget: function () {
                        this.$http.get(ROOT_URL + '/authorizathion-costcenter/cost?cost_center=' + this.selected.costCenter.idCostCenter + '&year=' + this.selected.year).success(function (data) {
                            this.authorizationBudget = data;
                            if (this.authorizationBudget.validation) {
                                this.validation = true;
                            }
                            if (this.authorizationBudget.authorization) {
                                this.authorization = true;
                            }
                        }).error(function (data) {
                        });
                    },
                    getBudgetsReport: function () {
                        this.$http.get(ROOT_URL + '/budgets/authorized?cost_center=' + this.selected.costCenter.idCostCenter + '&year=' + this.selected.year).success(function (data) {
                            this.budgetsReports = data;
                        });
                    },
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    deleteBudget: function (budget, idBudget) {
                        var budgetYearConceptListToSave = [];
                        budgetYearConceptListToSave.push(budget);
                        var isValid = false;
                        budgetYearConceptListToSave.every(function (budget, index, _ary) {
                            budget.budgetConcept = {};
                            isValid = true;
                        });
                        if (isValid) {
                            budgetYearConceptListToSave.forEach(function (budget) {
                            });
                            this.$http.post(ROOT_URL + '/budget-year-concept/update/' + idBudget, JSON.stringify(budgetYearConceptListToSave))
                                .success(function (data) {
                                    showAlert('Presupuesto eliminado');
                                    this.getBudgets(this.selected.year, this.selected.budgetType, this.selected.budgetNature, this.selected.budgetCategory);
                                })
                                .error(function (data) {
                                    showAlert('Ha habido un error con la solicitud, intente nuevamente');
                                });
                        }
                    },
                    saveBudget: function (budget, idBudget) {
                        var budgetYearConceptListToSave = [];
                        budgetYearConceptListToSave.push(budget);
                        var isValid = false;
                        budgetYearConceptListToSave.every(function (budget, index, _ary) {
                            budget.budgetConcept = {};
                            isValid = true;
                        });
                        if (isValid) {
                            budgetYearConceptListToSave.forEach(function (budget) {
                            });
                            this.$http.post(ROOT_URL + '/budget-year-concept/' + idBudget, JSON.stringify(budgetYearConceptListToSave))
                                .success(function (data) {
                                    showAlert('Presupuesto guardado');
                                    this.getBudgets(this.selected.year, this.selected.budgetType, this.selected.budgetNature, this.selected.budgetCategory);
                                })
                                .error(function (data) {
                                    showAlert('Ha habido un error con la solicitud, intente nuevamente');
                                });
                        }
                    },
                    equalsImport: function (indexOfBudget, budget) {
                        if (budget.equals) {
                            if (budget.januaryBudgetAmount != '') {
                                budget.februaryBudgetAmount = budget.januaryBudgetAmount;
                                budget.marchBudgetAmount = budget.januaryBudgetAmount;
                                budget.aprilBudgetAmount = budget.januaryBudgetAmount;
                                budget.mayBudgetAmount = budget.januaryBudgetAmount;
                                budget.juneBudgetAmount = budget.januaryBudgetAmount;
                                budget.julyBudgetAmount = budget.januaryBudgetAmount;
                                budget.augustBudgetAmount = budget.januaryBudgetAmount;
                                budget.septemberBudgetAmount = budget.januaryBudgetAmount;
                                budget.octoberBudgetAmount = budget.januaryBudgetAmount;
                                budget.novemberBudgetAmount = budget.januaryBudgetAmount;
                                budget.decemberBudgetAmount = budget.januaryBudgetAmount;
                                this.getBudgetYearConcept(indexOfBudget, budget);
                            } else {
                                showAlert('Debes ingresar un monto en el primer mes para esta acción', {type: 2});
                                subbudget.equals = false;
                            }
                        } else {
                            budget.februaryBudgetAmount = 0;
                            budget.marchBudgetAmount = 0;
                            budget.aprilBudgetAmount = 0;
                            budget.mayBudgetAmount = 0;
                            budget.juneBudgetAmount = 0;
                            budget.julyBudgetAmount = 0;
                            budget.augustBudgetAmount = 0;
                            budget.septemberBudgetAmount = 0;
                            budget.octoberBudgetAmount = 0;
                            budget.novemberBudgetAmount = 0;
                            budget.decemberBudgetAmount = 0;
                            this.getBudgetYearConcept(indexOfBudget, budget);
                        }
                    },
                    copyBudget: function (overwrite) {
                        var self = this;
                        var url = '/budget-year-concept/copy-budget?cost_center=' + this.selected.costCenter.idCostCenter +
                            '&year_from_copy=' + this.selected.yearFromCopy + '&budget_type=' + this.selected.budgetType.idBudgetType +
                            '&budget_nature=' + this.selected.budgetNature.idBudgetNature +
                            '&year_to_copy=' + this.selected.yearToCopy;
                        if (this.selected.yearFromCopy == this.selected.yearToCopy) {
                            showAlert("No se puede copiar presupesto en el mismo año", {type: 3});
                        } else {
                            self.$http.post(ROOT_URL + url).success(function (element) {
                                this.closeCopyBudgetModal();
                                showAlert('Transaccion exitosa');
                                showAlert(data.success.message);
                            }).error(function (data) {
                                showAlert(data.error.message, {type: 3});
                            });
                        }
                    },
                    getEmails: function () {
                        this.$http.get(ROOT_URL + '/budgets/get-emails?cost_center=' + this.selected.costCenter.idCostCenter).success(function (data) {
                            this.emails = data;
                        }).error(function (data) {
                            showAlert(data.error.message, {type: 3});
                        });
                    },
                    getEmailsModify: function () {
                        this.emails= '';
                        this.$http.get(ROOT_URL + '/budgets/get-emails-modify?cost_center=' + this.selected.costCenter.idCostCenter).success(function (data) {
                            this.emails = data;
                        }).error(function (data) {
                            showAlert(data.error.message, {type: 3});
                        });
                    },
                    onClickAcept: function () {
                        this.copyBudget(false);
                    },
                    onClickOverwrite: function () {
                        this.copyBudget(true);
                    },
                    showCopyBudgetModal: function () {
                        $('#copyBudgetModal').modal('show');
                    },
                    closeCopyBudgetModal: function () {
                        $('#copyBudgetModal').modal('hide');
                    },
                    showSendValidation: function () {
                        this.getEmails();
                        $('#sendValidation').modal('show');
                    },
                    closeSendValidation: function () {
                        $('#sendValidation').modal('hide');
                    },
                    showOverwriteModal: function () {
                        $('#overwriteModal').modal('show');
                    },
                    closeOverwriteModal: function () {
                        $('#overwriteModal').modal('hide');
                    },
                    showBudget: function () {
                        this.closeSendValidation();
                        $('#budgetInformation').modal('show');
                    },
                    closeBudget: function () {
                        $('#budgetInformation').modal('hide');
                    },
                    showConfirm: function () {
                        this.closeBudget();
                        this.closeSendValidation();
                        $('#confirmBudget').modal('show');
                    },
                    showRequestModification: function () {
                        this.getEmailsModify();
                        $('#requestModification').modal('show');
                    },
                    closeRequestModification: function () {
                        $('#requestModification').modal('hide');
                    },
                    closeConfirm: function () {
                        $('#confirmBudget').modal('hide');
                    },
                    getBudgetYearConcept: function (indexOfBudget, budget) {
                        budget.totalAmount = 0;
                        budget.totalAmount += budget.januaryBudgetAmount;
                        budget.totalAmount += budget.februaryAmount;
                        budget.totalAmount += budget.marchAmount;
                        budget.totalAmount += budget.aprilAmount;
                        budget.totalAmount += budget.mayAmount;
                        budget.totalAmount += budget.juneAmount;
                        budget.totalAmount += budget.julyAmount;
                        budget.totalAmount += budget.augustAmount;
                        budget.totalAmount += budget.septemberAmount;
                        budget.totalAmount += budget.octoberAmount;
                        budget.totalAmount += budget.novemberAmount;
                        budget.totalAmount += budget.decemberAmount;
                    },
                    getTotalCostCenter: function () {
                        var self = this;
                        this.totalCostCenter = 0;
                        this.budgets.totalCategoryAmount = 0;
                        this.budgets.forEach(function (budget) {
                            if (budget == null) {
                            } else {
                                self.totalCostCenter += budget.totalBudgetAmount;
                            }
                        });
                    },
                    onChangeFilter: function () {
                        this.budgets = [];
                    },
                    submitValidation: function () {
                        this.$http.post(ROOT_URL + '/budgets/authorization?cost_center=' + this.selected.costCenter.idCostCenter + '&year=' + this.selected.year).success(function (data) {
                            this.closeConfirm();
                            this.budgets = data;
                            showAlert('Presupuesto enviado');
                            this.getBudgets(this.selected.year, this.selected.budgetType, this.selected.budgetNature, this.selected.budgetCategory);
                        }).error(function (data) {
                            showAlert('Ha habido un error con la solicitud, intente nuevamente');
                        });
                    },
                    sendModifyBudget: function () {
                        this.closeRequestModification();
                        this.$http.post(ROOT_URL + '/authorizathion-costcenter/modify?cost_center=' + this.selected.costCenter.idCostCenter + '&year=' + this.selected.year).success(function (data) {
                            showAlert('Solicitud de modificacion enviada');
                            this.getBudgets();
                        }).error(function (data) {
                            showAlert('Ha habido un error con la solicitud, intente nuevamente');
                        });
                    }
                },
                filters: {
                    currencyDisplay: {
                        read: function (val) {
                            return val.formatMoney(2, '');
                        },
                        write: function (val, oldVal) {
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
            @media (min-width: 992px) {
                .container-scroll {
                    overflow-x: auto;
                }

                .container-scroll > .row {
                    width: 2025px;
                }
            }
        </style>
        <script>
            $(function () {
                $('.multiselect-ui').multiselect({
                    includeSelectAllOption: true
                });
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div id="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-5">
                        <h2>CAPTURA DE PRESUPUESTO</h2>
                    </div>
                    <div class="col-md-2 text-center" v-if="authorizationBudget.modify<=1 && budgets.length > 0"
                         style="color: #1b6d85">
                        <h2>ANUAL</h2>
                    </div>
                    <div class="col-md-2 text-center" v-if="authorizationBudget.modify == 2 && budgets.length > 0"
                         style="color: #1b6d85">
                        <h2>AJUSTADO</h2>
                    </div>
                    <div class="col-md-3 pull-right" v-if="budgets.length > 0">
                        <label>Buscar por concepto</label>
                        <input class="form-control" v-model="selected.concept" required>
                    </div>
                </div>
                <br>
                <div class="row">
                    <form v-on:submit.prevent="getBudgets">
                        <div class="row">
                            <div class="col-md-3">
                                <label>Centro de costos</label>
                                <select v-model="selected.costCenter" class="form-control" @change="onChangeFilter"
                                        required>
                                    <option v-for="costCenter in costCenterList" :value="costCenter">
                                        {{costCenter.name}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <label>Clasificación</label>
                                <select v-model="selected.budgetType" class="form-control multiselect-ui"
                                        @change="onChangeFilter"
                                        required>
                                    <option v-for="budgetType in budgetTypes" :value="budgetType">
                                        {{budgetType.budgetType}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label>Tipo de gasto</label>
                                <select v-model="selected.budgetNature" class="form-control" @change="onChangeFilter"
                                        required>
                                    <option v-for="budgetNature in budgetNatureList" :value="budgetNature">
                                        {{budgetNature.budgetNature}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <label>Año</label>
                                <select v-model="selected.year" class="form-control" @change="onChangeFilter" required>
                                    <option v-for="year in years" :value="year">
                                        {{year}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <button style="margin-top: 25px" class="btn btn-info">Seleccionar</button>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-md-5" v-if="budgets.length > 0 && authorization==false">
                                <h2 style="color: #d58512">
                                    <span class="glyphicon glyphicon-info-sign ">Presupuesto no autorizado</span></h2>
                            </div>
                            <div class="col-md-4" v-if="budgets.length > 0 && authorization">
                                <h2 style="color: #449d44">
                                    <span class="glyphicon glyphicon-ok-sign">Presupuesto autorizado</span></h2>
                            </div>
                            <div class="col-md-2 pull-right"
                                 v-if="budgets.length > 0 && authorization && validation && authorizationBudget.modify==0">
                                <button style="margin-top: 25px" class="btn btn-success"
                                        @click="showRequestModification">
                                    Solicitar modificación
                                </button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-8" v-if="budgets.length > 0">
                                <h3>Total centro de costo: <b class="text-primary">{{totalCostCenter | currency}}</b>
                                </h3>
                            </div>
                            <div class="col-md-2" v-if="budgets.length > 0 && authorization">
                                <button style="margin-top: 25px" class="btn btn-default" @click="showCopyBudgetModal">
                                    Copiar presupuesto
                                </button>
                            </div>
                            <div class="col-md-2" v-if="budgets.length > 0 && validation==false">
                                <button style="margin-top: 25px" class="btn btn-success" @click="showSendValidation">
                                    Enviar a validación
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <div v-if="searching" class="col-md-12"
                     style="height: 6rem; padding: 2rem 0;">
                    <div class="loader">Cargando...</div>
                </div>
                <div v-else="!searching">
                    <div class="row" v-for="(indexOfBudget, budget) in budgets | filterBy selected.concept in 'budget'"
                         style="margin-left: 0px; margin-right: 0px" v-if="budgets.length > 0 && budget!=null">
                        <div class="bs-callout bs-callout-default" style="background: darkslategrey">
                            <div class="row">
                                <div class="col-md-7">
                                    <h4><b>{{budget.budget.conceptBudget.nameConcept}}</b></h4>
                                </div>
                                <div class="col-md-5 text-right">
                                    <h4 style="color: black">Total: <b class="text-primary" style="color: black">{{budget.totalBudgetAmount
                                        |currency}}</b></h4>
                                </div>
                            </div>
                            <div class="row"
                                 style="margin-left: 0px; margin-right: 0px">
                                <div class="well">
                                    <div class="row" style="margin-left: 0px">
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation==false">
                                            <label>Ene</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.januaryBudgetAmount"
                                                   required
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation">
                                            <label>Ene</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.januaryBudgetAmount"
                                                   disabled
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation==false">
                                            <label>Feb</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.februaryBudgetAmount"
                                                   required
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation">
                                            <label>Feb</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.februaryBudgetAmount"
                                                   disabled
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation==false">
                                            <label>Mar</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.marchBudgetAmount" required
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation">
                                            <label>Mar</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.marchBudgetAmount" disabled
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation==false">
                                            <label>Abr</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.aprilBudgetAmount" required
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation">
                                            <label>Abr</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.aprilBudgetAmount" disabled
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation==false">
                                            <label>May</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.mayBudgetAmount" required
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation">
                                            <label>May</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.mayBudgetAmount" disabled
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation==false">
                                            <label>Jun</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.juneBudgetAmount" required
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation">
                                            <label>Jun</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.juneBudgetAmount" disabled
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation==false">
                                            <label>Jul</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.julyBudgetAmount" required
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation">
                                            <label>Jul</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.julyBudgetAmount" disabled
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation==false">
                                            <label>Ago</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.augustBudgetAmount" required
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation">
                                            <label>Ago</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.augustBudgetAmount" disabled
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation==false">
                                            <label>Sep</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.septemberBudgetAmount"
                                                   required
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation">
                                            <label>Sep</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.septemberBudgetAmount"
                                                   disabled
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation==false">
                                            <label>Oct</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.octoberBudgetAmount" required
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation">
                                            <label>Oct</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.octoberBudgetAmount" disabled
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation==false">
                                            <label>Nov</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.novemberBudgetAmount"
                                                   required
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation">
                                            <label>Nov</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.novemberBudgetAmount"
                                                   disabled
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation==false">
                                            <label>Dic</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.decemberBudgetAmount"
                                                   required
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px"
                                             v-if="validation">
                                            <label>Dic</label>
                                            <input type="text" class="form-control"
                                                   v-model="budget.decemberBudgetAmount"
                                                   disabled
                                                   @change="getBudgetYearConcept(indexOfBudget,budget)"
                                                   onkeypress="return validateFloatKeyPress(this,event)">
                                        </div>
                                        <br>
                                        <div class="col-md-8" v-if="budgets.length > 0 && validation==false">
                                            <div class="checkbox">
                                                <label style="margin-top: 27px">
                                                    <input type="checkbox" v-model="budget.equals"
                                                           @change="equalsImport(indexOfBudget,budget)"> Aplicar a
                                                    todos
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-md-2" v-if="budgets.length > 0 && validation==false">
                                            <button style="margin-top: 27px" type="button" class="btn btn-warning"
                                                    @click="deleteBudget(budget, budget.budget.idBudget)">
                                                <!--@click="deleteBudget(subbudget, subbudget.budget.idBudget)">-->
                                                Borrar datos
                                            </button>
                                        </div>
                                        <div class="col-md-2" aria-hidden="true"
                                             v-if="budgets.length > 0 && validation==false">
                                            <button style="margin-top: 27px" type="button" class="btn btn-success"
                                                    @click="saveBudget(budget, budget.budget.idBudget)">
                                                Guardar
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <!--Dialogs-->
                <div class="modal fade" id="copyBudgetModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <form v-on:submit.prevent="onClickAcept">
                                <div class="modal-header">
                                    <button type="button" class="close" aria-label="Close"
                                            @click="closeCopyBudgetModal">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="copyBudgetModalLabel">Copiar presupuesto</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-md-6 text-center"><p>Años anteriores</p></div>
                                        <div class="col-md-6 text-center"><p>Años posteriores</p></div>
                                        <br>
                                        <div class="col-md-1">
                                            <p>De</p>
                                        </div>
                                        <div class="col-md-5">
                                            <select v-model="selected.yearFromCopy" class="form-control" required>
                                                <option v-for="year in years" :value="year">
                                                    {{year}}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-md-1">
                                            <p>a</p>
                                        </div>
                                        <div class="col-md-5">
                                            <select v-model="selected.yearToCopy" class="form-control" required>
                                                <option v-for="year in years" :value="year">
                                                    {{year}}
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button class="btn btn-success">Guardar</button>
                                    <button type="button" class="btn btn-danger" @click="closeCopyBudgetModal">
                                        Cancelar
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="overwriteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" aria-label="Close"
                                        @click="closeOverwriteModal"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="overwriteModalLabel">Aviso</h4>
                            </div>
                            <div class="modal-body">
                                {{errorMessage}}
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" @click="closeOverwriteModal">Cancelar
                                </button>
                                <button type="button" class="btn btn-success" @click="onClickOverwrite">Aceptar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="sendValidation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background: darkslategrey; color: black">
                                <button type="button" class="close" aria-label="Close" @click="closeSendValidation">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="sendValidationLabel"><b>Revisar presupuesto</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div>
                                        <p>&nbsp ¿Deseas revisar presupuesto antes de enviar?</p>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <!--<button class="btn btn-success" @click="submitValidation">Guardar</button>-->
                                <button class="btn btn-success" @click="showBudget">Si</button>
                                <button class="btn btn-warning" @click="showConfirm">No</button>
                                <button class="btn btn-default" @click="closeSendValidation">Cancelar</button>
                                <!--<button type="button" class="btn btn-default" @click="closeSendValidation">Cancelar</button>-->
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="budgetInformation" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background: darkslategrey; color: black">
                                <button type="button" class="close" aria-label="Close" @click="closeBudget">
                                    <span aria-hidden="true">&times;</span></button>
                            </div>
                            <div class="modal-body">
                                <div class="container-fluid container-scroll">
                                    <div class="row table-header">
                                        <div class="col-md-4"><b>Centro de costos: {{selected.costCenter.name}}</b>
                                        </div>
                                        <div class="col-md-1"><b>Año: {{selected.year}}</b></div>
                                        <div class="col-md-5"><b>TOTAL CENTRO DE COSTOS: {{totalCostCenter
                                            |currency}}</b></div>
                                        <div class="col-md-1"><b></b></div>
                                        <div class="col-md-1"><b></b></div>
                                    </div>
                                    <div class="row table-header" style="background: #23527c ; color: white">
                                        <div class="col-md-12">
                                            <div class="col-md-2"></div>
                                            <div class="col-md-1">Tipo de Gasto</div>
                                            <div class="col-md-1">Clasificación</div>
                                            <div class="col-md-8 text-center">
                                                <div class="col-xs-1">Enero</div>
                                                <div class="col-xs-1">Febrero</div>
                                                <div class="col-xs-1">Marzo</div>
                                                <div class="col-xs-1">Abril</div>
                                                <div class="col-xs-1">Mayo</div>
                                                <div class="col-xs-1">Junio</div>
                                                <div class="col-xs-1">Julio</div>
                                                <div class="col-xs-1">Agosto</div>
                                                <div class="col-xs-1">Septiembre</div>
                                                <div class="col-xs-1">Octubre</div>
                                                <div class="col-xs-1">Noviembre</div>
                                                <div class="col-xs-1">Diciembre</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row table-header"
                                         v-for="(indexOfBudgetReport, budgetsReport) in budgetsReports"
                                         style="background: #31708f; color: white">
                                        <div class="col-md-12">
                                            <div class="col-md-2">{{budgetsReport.budget.conceptBudget.nameConcept}}
                                            </div>
                                            <div class="col-md-1">{{budgetsReport.budget.budgetNature.budgetNature}}
                                            </div>
                                            <div class="col-md-1">{{budgetsReport.budget.budgetType.budgetType}}</div>
                                            <div class="col-md-8 text-center">
                                                <div class="col-xs-1">{{budgetsReport.januaryBudgetAmount|currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetsReport.februaryBudgetAmount|currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetsReport.marchBudgetAmount|currency}}</div>
                                                <div class="col-xs-1">{{budgetsReport.aprilBudgetAmount|currency}}</div>
                                                <div class="col-xs-1">{{budgetsReport.mayBudgetAmount|currency}}</div>
                                                <div class="col-xs-1">{{budgetsReport.juneBudgetAmount|currency}}</div>
                                                <div class="col-xs-1">{{budgetsReport.julyBudgetAmount|currency}}</div>
                                                <div class="col-xs-1">{{budgetsReport.augustBudgetAmount|currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetsReport.septemberBudgetAmount|currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetsReport.octoberBudgetAmount|currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetsReport.novemberBudgetAmount|currency}}
                                                </div>
                                                <div class="col-xs-1">{{budgetsReport.decemberBudgetAmount|currency}}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <br>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="showConfirm">Aceptar</button>
                                <button class="btn btn-warning" @click="closeBudget">Modificar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="confirmBudget" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background: darkslategrey; color: black">
                                <button type="button" class="close" aria-label="Close" @click="closeConfirm">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="confirmBudgetLabel"><b>Enviar presupuesto</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div>
                                        <p>&nbsp El presupuesto para el centro de costos
                                            {{selected.costCenter.name}} será enviado a:</p>
                                    </div>
                                </div>
                                <div class="row" v-for="email in emails.emailRecipientsList">
                                    <p>&nbsp &nbsp{{email.emailAddress}}</p>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="submitValidation">Aceptar</button>
                                <button class="btn btn-default" @click="closeConfirm">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="requestModification" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background: darkslategrey; color: black">
                                <button type="button" class="close" aria-label="Close"
                                        @click="closeRequestModification">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="requestModificationLabel"><b>Modificar presupuesto</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div>
                                        <p>&nbsp El presupuesto para el centro de costos {{selected.costCenter.name}}será enviado a: </p>
                                        <div class="row" v-for="email in emails.emailRecipientsList">
                                            <p>&nbsp &nbsp &nbsp &nbsp{{email.emailAddress}}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="sendModifyBudget">Aceptar</button>
                                <button class="btn btn-default" @click="closeRequestModification">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </jsp:body>
</t:template>
