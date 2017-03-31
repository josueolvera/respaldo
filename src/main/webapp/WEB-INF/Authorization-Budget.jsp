<%--
  Created by IntelliJ IDEA.
  User: Kevin Salvador
  Date: 09/03/2017
  Time: 10:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../assets/css/barralateral.css">
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
                if (number.length > 1 && charCode == 46) {
                    return false;
                }
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
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                created: function () {
                    this.setYears();
                },
                ready: function () {
                    this.getUserInSession();
                    this.getBussinessLine();
                    this.getDistributors();
                    this.getMonths();
                },
                data: {
                    now: "${now}",
                    minYear: null,
                    maxYear: null,
                    totalCostCenter: null,
                    months: [],
                    years: [],
                    yearbefore: null,
                    budgets: [],
                    concepts: [],
                    user: {},
                    selected: {
                        bussiness: null,
                        distributor: null,
                        costCenter: null,
                        year: null,
                        budgetCategory: null,
                        budget: null,
                        subbudget: null,
                        coment: ''
                    },
                    bussinessLines: {},
                    distributors: {},
                    searching: false,
                    rolesCostCenter: [],
                    costCenterList: [],
                    budgetNatureList: [],
                    budgetCategories: [],
                    role: null,
                    errorMessage: '',
                    authorizationBudget: {},
                    valida: false,
                    authoriza: false,
                    modifica: false,
                    secondLevel: 0
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
                    getMonths: function () {
                        this.$http.get(ROOT_URL + '/months')
                            .success(function (data) {
                                this.months = data;
                            })
                            .error(function (data) {

                            });
                    },
                    getBussinessLine: function () {
                        this.$http.get(ROOT_URL + '/bussiness-line').success(function (data) {
                            this.bussinessLines = data;
                        }).error(function (data) {
                            showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                        });
                    },
                    getDistributors: function () {
                        this.$http.get(ROOT_URL + '/distributors/find-budget').success(function (data) {
                            this.distributors = data;
                        }).error(function (data) {
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
                    setYears: function () {
                        var now = new Date(this.now);
                        this.selected.year = now.getFullYear();
                        this.minYear = this.selected.year - 3;
                        this.maxYear = this.selected.year + 5;
                        for (var i = this.minYear; i <= this.maxYear; i++) {
                            this.years.push(i)
                        }
                    },
                    searchBudget: function () {
                        this.searching = true;
                        this.getBudgets();
                    },
                    getBudgets: function () {
                        var anioanterior = this.selected.year;
                        this.yearbefore = anioanterior - 1;
                        var url = ROOT_URL +
                            '/budgets/get-budget-levels?bussinessline=' + this.selected.bussiness.idBusinessLine + '&distributor='
                            + this.selected.distributor.idDistributor + '&cost_center=' + this.selected.costCenter.idCostCenter
                            + '&year=' + this.selected.year;
                        this.$http.get(url).success(function (data) {
                            var self = this;
                            this.budgets = data;
                            this.searching = false;
                            this.getAuthorizationBudget();
                            if (data.length <= 0) {
                                showAlert('No existen presupuestos por autorizar');
                            }
                            //this.getTotalCostCenter();
                        }).error(function (data) {
                            showAlert("Hola");
                        });
                    },
                    getAuthorizationBudget: function () {
                        this.$http.get(ROOT_URL + '/authorizathion-costcenter/cost?cost_center=' + this.selected.costCenter.idCostCenter + '&year=' + this.selected.year).success(function (data) {
                            this.authorizationBudget = data;
                            if (this.authorizationBudget.validation) {
                                this.valida = true;
                            }
                            if (this.authorizationBudget.authorization) {
                                this.authoriza = true;
                            }
                            if (this.authorizationBudget.modify) {
                                this.modifica = true;
                            }
                        }).error(function (data) {
                        });
                    },
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    getTotalCostCenter: function () {
                        var self = this;
                        this.totalCostCenter = 0;
                        this.budgets.totalCategoryAmount = 0;
                        this.budgets.forEach(function (budget) {
                            budget.totalCategoryAmount = 0;
                        });
                        this.budgets.forEach(function (budget) {
                            budget.realBudgetSpendings.forEach(function (subbudget) {
                                budget.totalCategoryAmount += subbudget.totalBudgetAmount;
                            });
                        });
                        this.budgets.forEach(function (budget) {
                            self.totalCostCenter += budget.totalCategoryAmount;
                        });
                    },
                    onChangeFilter: function () {
                        this.budgets = [];
                    },
                    showSendAuthorizationOrDenies: function () {
                        $('#sendAuthorizationOrDenies').modal('show');
                    },
                    closeSendAuthorizationOrDenies: function () {
                        $('#sendAuthorizationOrDenies').modal('hide');
                    },
                    showAuthorizationBudget: function () {
                        this.closeSendAuthorizationOrDenies();
                        $('#authorizationBudget').modal('show');
                    },
                    closeAuthorizationBudget: function () {
                        $('#authorizationBudget').modal('hide');
                    },
                    showDeniesBudget: function () {
                        this.closeSendAuthorizationOrDenies();
                        $('#deniesBudget').modal('show');
                    },
                    closeDeniesBudget: function () {
                        $('#deniesBudget').modal('hide');
                    },
                    confirmDenisAuthorization: function () {
                        this.closeDeniesBudget();
                        this.$http.post(ROOT_URL + '/budgets/center-year?cost_center=' + this.selected.costCenter.idCostCenter + '&year=' + this.selected.year).success(function (data) {
                            this.getBudgets(this.selected.year);
                            showAlert('Presupuesto rechazado');
                        }).error(function (data) {
                            showAlert('Error en la solicitud, intentelo nuevamente', {type: 3});
                        });
                    },
                    confirmAuthorization: function () {
                        this.closeAuthorizationBudget();
                        this.$http.post(ROOT_URL + '/budgets/validated?cost_center=' + this.selected.costCenter.idCostCenter + '&year=' + this.selected.year).success(function (data) {
                            this.getBudgets(this.selected.year);
                            showAlert('Presupuesto autorizado');
                        }).error(function (data) {
                            showAlert('Error en la solicitud, intentelo nuevamente', {type: 3});
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
    </jsp:attribute>
    <jsp:body>
        <div id="content">
            <div class="container">
                <div class="row">
                    <div class="col-md-6">
                        <h2>VALIDACIÓN DE PRESUPUESTO</h2>
                    </div>
                </div>
                <br>
                <form v-on:submit.prevent="searchBudget()">
                    <div class="row">
                        <div class="col-md-4">
                            <label>Linea de negocio</label>
                            <select v-model="selected.bussiness" class="form-control" @change="onChangeFilter"
                                    required>
                                <option v-for="bussiness in bussinessLines" :value="bussiness">
                                    {{bussiness.name}}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label>Empresa</label>
                            <select v-model="selected.distributor" class="form-control" @change="onChangeFilter"
                                    required>
                                <option v-for="distributor in distributors" :value="distributor">
                                    {{distributor.distributorName}}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Centro de costos</label>
                            <select v-model="selected.costCenter" class="form-control" @change="onChangeFilter"
                                    required>
                                <option v-for="costCenter in costCenterList" :value="costCenter">
                                    {{costCenter.name}}
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
                        <div class="col-md-1">
                            <button style="margin-top: 25px" class="btn btn-info">Buscar</button>
                        </div>
                    </div>
                </form>
                <br>
                <div class="row">
                    <div class="col-md-5" v-if="budgets.length > 0 && authoriza==false">
                        <h2 style="color: #d58512">
                            <span class="glyphicon glyphicon-info-sign ">Presupuesto no autorizado</span></h2>
                    </div>
                    <div class="col-md-4" v-if="budgets.length > 0 && authoriza">
                        <h2 style="color: #449d44">
                            <span class="glyphicon glyphicon-ok-sign">Presupuesto autorizado</span></h2>
                    </div>
                    <div class="col-md-2 pull-right" v-if="budgets.length > 0 && modifica==1">
                        <button style="margin-top: 25px" class="btn btn-warning" @click="showSendAuthorizationOrDenies">
                            Autorizar modificacion
                        </button>
                    </div>
                    <div class="col-md-2 pull-right" v-if="budgets.length > 0 && valida && authoriza==false">
                        <button style="margin-top: 25px" class="btn btn-success" @click="showSendAuthorizationOrDenies">
                            Autorizar o Rechazar
                        </button>
                    </div>
                </div>
                <br>
                <!--<div class="row">
                    <div class="col-md-8 text-right" v-if="budgets.length > 0">
                        <h3>Total centro de costo: <b class="text-primary">{{totalCostCenter | currency}}</b></h3>
                    </div>
                </div>-->
                <!--<div v-if="searching" class="col-md-12" style="height: 6rem; padding: 2rem 0;">
                    <div class="loader">Cargando...</div>
                </div>-->

                <div class="container-fluid container-scroll" v-if="budgets.length > 0">
                    <div class="row" style="background-color: darkslategrey">
                        <div class="col-md-12" style="color: black">
                            <div class="row" style="height: 50px">
                                <div class="col-md-2 text-center">
                                    <h5><b class="text-primary">{{selected.bussiness.name}}</b></h5>
                                </div>
                                <div class="col-md-1 text-center">
                                    <h5><b>Total de: {{yearbefore}}</b></h5>
                                </div>
                                <div class="col-md-8 text-center">
                                    <div class="col-md-1" v-for="month in months">
                                        <h5><b>{{month.month | uppercase}}</b></h5>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <h5><b>Total de: {{selected.year}}</b></h5>
                                </div>
                            </div>
                            <div class="row" style="background-color: #cfcfcf">
                                <div class="col-md-12">
                                    <div class="col-md-2">
                                        <h5><b>Empresa: {{selected.distributor.distributorName}}</b></h5>
                                    </div>
                                    <div class="col-md-3">
                                        <b>Centro de costos: {{selected.costCenter.name}}</b>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <div class="col-md-12" style="height:70%;overflow-y: auto;">
                            <div class="row" v-for="budget in budgets" style="background-color: #dfdfdf">
                                <!--Cuenta contable Nivel Uno-->
                                <div class="col-md-12" v-for="subBudget in budget.levelOne">
                                    <div class="col-md-12" style="background: #333333; color: white">
                                        <div class="col-md-2">
                                            <h5><b>{{subBudget.budget.distributorCostCenter.accountingAccounts.budgetCategory.budgetCategory}}</b>
                                            </h5>
                                        </div>
                                        <div v-for="subBudgetBefore in budget.levelOneYearBefore">
                                            <div class="col-md-1">
                                                <b>{{subBudgetBefore.totalBudgetAmount | currency}}</b>
                                            </div>
                                        </div>
                                        <div class="col-md-8 text-center">
                                            <div class="col-md-1">
                                                <b>{{subBudget.januaryBudgetAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.februaryBudgetAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.marchBudgetAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.aprilBudgetAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.mayBudgetAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.juneBudgetAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.julyBudgetAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.augustBudgetAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.septemberBudgetAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.octoberBudgetAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.novemberBudgetAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.decemberBudgetAmount | currency}}</b>
                                            </div>
                                        </div>
                                        <div class="col-md-1">
                                            <b>{{subBudget.totalBudgetAmount | currency}}</b>
                                        </div>
                                    </div>
                                    <div class="col-md-12" style="background: white; color: black">
                                        <div class="col-md-2">
                                            <h5><b><span class="pull-right">{{subBudget.budget.conceptBudget.nameConcept}}</span></b>
                                            </h5>
                                        </div>
                                        <div class="col-md-1">
                                            <!--Aqui va el año anterior -->
                                        </div>
                                        <div class="col-md-8 text-center">
                                            <div class="col-md-1">
                                                <b>{{subBudget.januaryBudgetAmount| currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.februaryBudgetAmount| currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.marchBudgetAmount| currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.aprilBudgetAmount| currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.mayBudgetAmount| currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.juneBudgetAmount| currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.julyBudgetAmount| currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.augustBudgetAmount| currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.septemberBudgetAmount| currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.octoberBudgetAmount| currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.novemberBudgetAmount| currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.decemberBudgetAmount| currency}}</b>
                                            </div>
                                        </div>
                                        <div class="col-md-1">
                                            <b>{{subBudget.totalBudgetAmount | currency}}</b>
                                        </div>
                                    </div>
                                </div>
                                <!--Cuenta contable nivel dos-->
                                <div class="col-md-12" v-for="subBudget in budget.secondLevel">
                                    <div v-for="subBudgetSecond in subBudget.secondLevel">
                                        <div class="col-md-12" style="background: #333333; color: white">
                                            <div class="col-md-2 align-right">
                                                <h5><b>{{subBudgetSecond.budget.distributorCostCenter.accountingAccounts.budgetCategory.budgetCategory}}</b>
                                                </h5>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.totalBudgetBeforeYear | currency}}</b>
                                            </div>
                                            <div class="col-md-8 text-center">
                                                <div class="col-md-1">
                                                    <b>{{subBudget.januaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudget.februaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudget.marchBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudget.aprilBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudget.mayBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudget.juneBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudget.julyBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudget.augustBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudget.septemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudget.octoberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudget.novemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudget.decemberBudgetAmount | currency}}</b>
                                                </div>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.totalBudgetAmount | currency}}</b>
                                            </div>
                                        </div>

                                        <div class="col-md-12" style="background: #666666; color: white">
                                            <div class="col-md-2 text-center">
                                                <h5><b>{{subBudgetSecond.budget.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</b>
                                                </h5>
                                            </div>
                                            <div class="col-md-1"
                                                 v-for="subBudgetSecondYearBefore in subBudget.secondLevelBeforeYear">
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecondYearBefore.totalBudgetAmount | currency}}</b>
                                                </div>
                                            </div>
                                            <div class="col-md-8 text-center">
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.januaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.februaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.marchBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.aprilBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.mayBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.juneBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.julyBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.augustBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.septemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.octoberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.novemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.decemberBudgetAmount | currency}}</b>
                                                </div>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.totalBudgetAmount | currency}}</b>
                                            </div>
                                        </div>

                                        <div class="col-md-12" style="background: white; color: black">
                                            <div class="col-md-2 text-center">
                                                <h5><b><span class="pull-right">{{subBudgetSecond.budget.conceptBudget.nameConcept}}</span></b>
                                                </h5>
                                            </div>
                                            <div class="col-md-1"
                                                 v-for="subBudgetSecondYearBefore in subBudget.secondLevelBeforeYear">
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecondYearBefore.totalBudgetAmount | currency}}</b>
                                                </div>
                                            </div>
                                            <div class="col-md-8 text-center">
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.januaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.februaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.marchBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.aprilBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.mayBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.juneBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.julyBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.augustBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.septemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.octoberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.novemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetSecond.decemberBudgetAmount | currency}}</b>
                                                </div>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.totalBudgetAmount | currency}}</b>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!--Cuenta contable nivel tres -->
                                <div class="col-md-12" v-for="subBudget in budget.secondLevel">
                                    <div v-for="subBudgetThird in subBudget.thirdLevel">
                                        <div class="col-md-12" v-for="thirdLevel in subBudgetThird.findLevel"
                                             style="background: #333333; color: white">
                                            <div class="col-md-2">
                                                <h5><b>{{thirdLevel.budget.distributorCostCenter.accountingAccounts.budgetCategory.budgetCategory}}</b>
                                                </h5>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.totalBeforeAmount| currency}}</b>
                                            </div>
                                            <div class="col-md-8 text-center">
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.januaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.februaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.marchBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.aprilBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.mayBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.juneBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.julyBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.augustBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.septemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.octoberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.novemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.decemberBudgetAmount | currency}}</b>
                                                </div>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudget.totalBudgetAmount | currency}}</b>
                                            </div>
                                        </div>

                                        <div class="col-md-12" v-for="thirdLevel in subBudgetThird.findLevel"
                                             style="background: #666666; color: white">
                                            <div class="col-md-2 text-center">
                                                <h5><b>{{thirdLevel.budget.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</b>
                                                </h5>
                                            </div>
                                            <div class="col-md-1"
                                                 v-for="thirdLevelBefore in subBudgetThird.findLevelBeforeYear">
                                                <div class="col-md-1">
                                                    <b>{{}}</b>
                                                </div>
                                            </div>
                                            <div class="col-md-8 text-center">
                                                <div class="col-md-1">
                                                    <b>{{subBudgetThird.januaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetThird.februaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetThird.marchBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetThird.aprilBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetThird.mayBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetThird.juneBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetThird.julyBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetThird.augustBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetThird.septemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetThird.octoberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetThird.novemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{subBudgetThird.decemberBudgetAmount | currency}}</b>
                                                </div>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{subBudgetThird.totalBudgetAmount | currency}}</b>
                                            </div>
                                        </div>

                                        <div class="col-md-12" v-for="thirdLevel in subBudgetThird.findLevel"
                                             style="background: #999999; color: black">
                                            <div class="col-md-2 text-center">
                                                <h5><b>{{thirdLevel.budget.distributorCostCenter.accountingAccounts.cBudgetSubSubcategories.name}}</b>
                                                </h5>
                                            </div>
                                            <div class="col-md-1">
                                                <!--Aqui va el año anterior -->
                                            </div>
                                            <div class="col-md-8 text-center">
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.januaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.februaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.marchBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.aprilBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.mayBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.juneBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.julyBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.augustBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.septemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.octoberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.novemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.decemberBudgetAmount | currency}}</b>
                                                </div>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{thirdLevel.totalBudgetAmount | currency}}</b>
                                            </div>
                                        </div>

                                        <div class="col-md-12" v-for="thirdLevel in subBudgetThird.findLevel"
                                             style="background: white; color: black">
                                            <div class="col-md-2 text-rigth">
                                                <h5><b><span class="pull-right">{{thirdLevel.budget.conceptBudget.nameConcept}}</span></b>
                                                </h5>
                                            </div>
                                            <div class="col-md-1">
                                                <!--Aqui va el año anterior -->
                                            </div>
                                            <div class="col-md-8 text-center">
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.januaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.februaryBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.marchBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.aprilBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.mayBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.juneBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.julyBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.augustBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.septemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.octoberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.novemberBudgetAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <b>{{thirdLevel.decemberBudgetAmount | currency}}</b>
                                                </div>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{thirdLevel.totalBudgetAmount | currency}}</b>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <!--dialogs-->
                <div class="modal fade" id="sendAuthorizationOrDenies" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <form v-on:submit.prevent="showAuthorizationBudget">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header" style="background: darkslategrey; color: black">
                                    <button type="button" class="close" aria-label="Close"
                                            @click="closeSendAuthorizationOrDenies">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="sendValidationLabel"><b>Añadir un comentario</b></h4>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <textarea class="form-control" rows="5" v-model="selected.coment" required></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button class="btn btn-success" @click="showAuthorizationBudget">Autorizar</button>
                                    <button class="btn btn-warning" @click="showDeniesBudget">Rechazar</button>
                                    <button class="btn btn-default" @click="closeSendAuthorizationOrDenies">Cancelar
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="modal fade" id="deniesBudget" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background: darkslategrey; color: black">
                                <button type="button" class="close" aria-label="Close" @click="closeDeniesBudget">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="deniesBudgetLabel"><b>Rechazar presupuesto</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div>
                                        <p>&nbsp El presupuesto para el centro de costos {{selected.costCenter.name}}
                                            será
                                            modificado y se</p>
                                        <p>&nbsp notificará a a kjuarez@bidg.com</p>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="confirmDenisAuthorization">Aceptar</button>
                                <button type="button" class="btn btn-default" @click="closeDeniesBudget">Cancelar
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="authorizationBudget" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background: darkslategrey; color: black">
                                <button type="button" class="close" aria-label="Close"
                                        @click="closeAuthorizationBudget">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="authorizationBudgetLabel"><b>Autorizar presupuesto</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div>
                                        <p>&nbsp El presupuesto para el centro de costos {{selected.costCenter.name}}
                                            será
                                            autorizado y se</p>
                                        <p>&nbsp notificará a a kjuarez@bidg.com</p>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="confirmAuthorization">Aceptar</button>
                                <button type="button" class="btn btn-default" @click="closeAuthorizationBudget">Cancelar
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="authorizationModify" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background: darkslategrey; color: black">
                                <button type="button" class="close" aria-label="Close"
                                        @click="closeAuthorizationBudget">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="authorizationModifyLabel"><b>Realizar modificación</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div>
                                        <p>&nbsp El presupuesto para el centro de costos {{selected.costCenter.name}}
                                            será
                                            autorizado y se</p>
                                        <p>&nbsp notificará a a kjuarez@bidg.com</p>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="confirmAuthorization">Aceptar</button>
                                <button type="button" class="btn btn-default" @click="closeAuthorizationBudget">Cancelar
                                </button>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </div>
    </jsp:body>
</t:template>