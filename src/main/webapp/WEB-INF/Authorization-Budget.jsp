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
                },
                data: {
                    now: "${now}",
                    minYear: null,
                    maxYear: null,
                    totalCostCenter: null,
                    years: [],
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
                    },
                    bussinessLines:{},
                    distributors:{},
                    searching: false,
                    rolesCostCenter: [],
                    costCenterList: [],
                    budgetNatureList: [],
                    budgetCategories: [],
                    role: null,
                    errorMessage: '',
                    coment:'',
                    authorizationBudget: {},
                    valida: false,
                    authoriza: false,
                    modifica: false
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
                    searchBudget: function (bussinessline,distributor,costCenter,year) {
                        this.searching = true;
                        this.getBudgets(bussinessline,distributor,costCenter,year);
                    },
                    getBudgets: function (bussinessline,distributor,costCenter,year) {
                        console.log(this.selected.bussiness);
                        console.log(this.selected.distributor);
                        var url = ROOT_URL +
                            '/budgets/authorized?bussinessline='+this.selected.bussiness.idBusinessLine+'&distributor='
                            + this.selected.distributor.idDistributor+'&cost_center=' + this.selected.costCenter.idCostCenter
                            +'&year=' + this.selected.year;
                        this.$http.get(url)
                            .success(function (data) {
                                var self = this;
                                this.budgets = data;
                                this.searching = false;
                                this.getAuthorizationBudget();
                                if (data.length <= 0) {
                                    showAlert('No existen presupuestos por autorizar');
                                }
                                this.getTotalCostCenter();
                            })
                            .error(function (data) {
                                showAlert('Error en la solicitud', {type: 3});
                            });
                    },
                    getAuthorizationBudget:function () {
                        this.$http.get(ROOT_URL + '/authorizathion-costcenter/cost?cost_center='+this.selected.costCenter.idCostCenter+ '&year='+this.selected.year).success(function (data) {
                            this.authorizationBudget = data;
                            if(this.authorizationBudget.validation){
                                this.valida = true;
                            }
                            if(this.authorizationBudget.authorization){
                                this.authoriza= true;
                            }
                            if(this.authorizationBudget.modify){
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
                        this.$http.post(ROOT_URL + '/budgets/validated?cost_center='+ this.selected.costCenter.idCostCenter + '&year=' + this.selected.year).success(function (data) {
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
    <jsp:body>
        <div id="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-6">
                        <h2>VALIDACIÓN DE PRESUPUESTO</h2>
                    </div>
                </div>
                <div class="row">
                    <form v-on:submit.prevent="searchBudget(selected.bussiness,selected.distributor,selected.costCenter,selected.year)">
                        <div class="row">
                            <div class="col-md-4">
                                <label>Linea de negocio</label>
                                <select v-model="selected.bussiness" class="form-control" @change="onChangeFilter" required>
                                    <option v-for="bussiness in bussinessLines" :value="bussiness">
                                        {{bussiness.name}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label>Empresa</label>
                                <select v-model="selected.distributor" class="form-control" @change="onChangeFilter" required>
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
                        <!--<div class="row">
                            <div class="col-md-5" v-if="budgets.length > 0 && authoriza==false">
                                <h2 style="color: #d58512">
                                    <span class="glyphicon glyphicon-info-sign ">Presupuesto no autorizado</span></h2>
                            </div>
                            <div class="col-md-4" v-if="budgets.length > 0 && authoriza">
                                <h2 style="color: #449d44">
                                    <span class="glyphicon glyphicon-ok-sign">Presupuesto autorizado</span></h2>
                            </div>
                            <div class="col-md-2 pull-right" v-if="budgets.length > 0 && modifica==1">
                                <button style="margin-top: 25px" class="btn btn-warning" @click="showSendAuthorizationOrDenies" >Autorizar modificacion</button>
                            </div>
                            <div class="col-md-2 pull-right" v-if="budgets.length > 0 && valida && authoriza==false">
                                <button style="margin-top: 25px" class="btn btn-success" @click="showSendAuthorizationOrDenies" >Autorizar o Rechazar</button>
                            </div>
                        </div>-->
                        <!--<div class="row">
                            <div class="col-md-8 text-right" v-if="budgets.length > 0">
                                <h3>Total centro de costo: <b class="text-primary">{{totalCostCenter | currency}}</b></h3>
                            </div>
                        </div>-->
                    </form>
                </div>
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
                        <button style="margin-top: 25px" class="btn btn-warning" @click="showSendAuthorizationOrDenies" >Autorizar modificacion</button>
                    </div>
                    <div class="col-md-2 pull-right" v-if="budgets.length > 0 && valida && authoriza==false">
                        <button style="margin-top: 25px" class="btn btn-success" @click="showSendAuthorizationOrDenies" >Autorizar o Rechazar</button>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8 text-right" v-if="budgets.length > 0">
                        <h3>Total centro de costo: <b class="text-primary">{{totalCostCenter | currency}}</b></h3>
                    </div>
                </div>
                <div v-if="searching" class="col-md-12" style="height: 6rem; padding: 2rem 0;">
                    <div class="loader">Cargando...</div>
                </div>
                <div v-else="!searching">
                    <div v-if="budget.name != null" class="row" v-for="(indexOfBudget, budget) in budgets"
                         style="margin-left: 0px; margin-right: 0px">
                        <div class="bs-callout bs-callout-default" style="background: #204d74">
                            <div class="row">
                                <div class="col-md-7">
                                    <h4><b>{{budget.name}}</b></h4>
                                </div>
                                <div class="col-md-5 text-right">
                                    <h4>Total rubro: <b class="text-primary" style="color: black">{{budget.totalCategoryAmount
                                        |currency}}</b></h4>
                                </div>
                            </div>
                            <div class="row" v-for="(indexOfSubBudget,subbudget) in budget.realBudgetSpendings"
                                 style="margin-left: 0px; margin-right: 0px">
                                <div class="well">
                                    <div class="row" style="margin-left: 0px">
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Ene</label>
                                            <label class="form-control">{{subbudget.januaryBudgetAmount}}</label>
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Feb</label>
                                            <label class="form-control">{{subbudget.februaryBudgetAmount}}</label>
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Mar</label>
                                            <label class="form-control">{{subbudget.marchBudgetAmount}}</label>
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Abr</label>
                                            <label class="form-control">{{subbudget.aprilBudgetAmount}}</label>
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>May</label>
                                            <label class="form-control">{{subbudget.mayBudgetAmount}}</label>
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Jun</label>
                                            <label class="form-control">{{subbudget.juneBudgetAmount}}</label>
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Jul</label>
                                            <label class="form-control">{{subbudget.julyBudgetAmount}}</label>
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Ago</label>
                                            <label class="form-control">{{subbudget.augustBudgetAmount}}</label>
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Sep</label>
                                            <label class="form-control">{{subbudget.septemberBudgetAmount}}</label>
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Oct</label>
                                            <label class="form-control">{{subbudget.octoberBudgetAmount}}</label>
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Nov</label>
                                            <label class="form-control">{{subbudget.novemberBudgetAmount}}</label>
                                        </div>
                                        <div class="col-md-1" style="padding-left: 0px; padding-right: 1px">
                                            <label>Dic</label>
                                            <label class="form-control">{{subbudget.decemberBudgetAmount}}</label>
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
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background: darkslategrey; color: black">
                                <button type="button" class="close" aria-label="Close" @click="closeSendAuthorizationOrDenies">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="sendValidationLabel"><b>Añadir un comentario</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <textarea class="form-control" rows="5" v-model="coment"></textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="showAuthorizationBudget">Autorizar</button>
                                <button class="btn btn-warning" @click="showDeniesBudget">Rechazar</button>
                                <button class="btn btn-default" @click="closeSendAuthorizationOrDenies">Cancelar</button>
                            </div>
                        </div>
                    </div>
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
                                        <p>&nbsp El presupuesto para el centro de costos {{selected.costCenter.name}} será modificado y se</p>
                                        <p>&nbsp notificará a  a kjuarez@bidg.com</p>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="confirmDenisAuthorization">Aceptar</button>
                                <button type="button" class="btn btn-default" @click="closeDeniesBudget">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="authorizationBudget" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background: darkslategrey; color: black">
                                <button type="button" class="close" aria-label="Close" @click="closeAuthorizationBudget">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="authorizationBudgetLabel"><b>Autorizar presupuesto</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div>
                                        <p>&nbsp El presupuesto para el centro de costos {{selected.costCenter.name}} será autorizado y se</p>
                                        <p>&nbsp notificará a  a kjuarez@bidg.com</p>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="confirmAuthorization">Aceptar</button>
                                <button type="button" class="btn btn-default" @click="closeAuthorizationBudget">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="authorizationModify" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="background: darkslategrey; color: black">
                                <button type="button" class="close" aria-label="Close" @click="closeAuthorizationBudget">
                                    <span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="authorizationModifyLabel"><b>Realizar modificación</b></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div>
                                        <p>&nbsp El presupuesto para el centro de costos {{selected.costCenter.name}} será autorizado y se</p>
                                        <p>&nbsp notificará a  a kjuarez@bidg.com</p>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-success" @click="confirmAuthorization">Aceptar</button>
                                <button type="button" class="btn btn-default" @click="closeAuthorizationBudget">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </div>
    </jsp:body>
</t:template>