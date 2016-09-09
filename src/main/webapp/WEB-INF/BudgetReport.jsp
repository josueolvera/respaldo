<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../assets/css/barralateral.css">
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Presupuestos">

    <jsp:attribute name="scripts">

        <script type="text/javascript">

            var vm = new Vue({
                el: '#content',
                created: function(){
                    this.setYears();
                },
                ready : function () {
                    this.getUserInSession();
                    this.getMonths();
                },
                data: {
                    now: "${now}",
                    minYear:null,
                    maxYear:null,
                    years: [],
                    months:[],
                    selected : {
                        costCenter: {},
                        year: null
                    },
                    budgets: [],
                    user:{},
                    searching: false,
                    rolesCostCenter:[],
                    role: null,
                    searchUrl: ROOT_URL,
                    downloadReportUrl: ROOT_URL + '/budgets?download=true'
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
                    setYears: function () {
                        var now = new Date(this.now);
                        this.selected.year = now.getFullYear();
                        this.minYear = this.selected.year - 3;
                        this.maxYear = this.selected.year + 5;
                        for (var i = this.minYear; i <= this.maxYear; i++) {
                            this.years.push(i)
                        }
                    },
                    getRolesCostCenter: function (idRole) {
                        this.$http.get(ROOT_URL + '/roles-cost-center/role/' + idRole)
                                .success(function (data) {
                                    var self = this;
                                    var index;
                                    this.rolesCostCenter = data;
                                })
                                .error(function (data) {

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
                    searchBudget: function () {
                        this.searching = true;
                        this.getBudgets();
                    },
                    getBudgets: function () {
                        this.onChangeCriteria();
                        this.searchUrl += '/budgets?cost_center=' + this.selected.costCenter.idCostCenter +
                                '&year=' + this.selected.year;
                        this.downloadReportUrl += '&cost_center=' + this.selected.costCenter.idCostCenter +
                                '&year=' + this.selected.year;
                        this.$http.get(this.searchUrl)
                                .success(function (data) {
                                    var self = this;
                                    this.budgets = data;
                                    this.searching = false;
                                    this.getConcepts(self);
                                })
                                .error(function (data) {

                                });
                    },
                    onChangeCriteria : function () {
                        this.searchUrl = ROOT_URL;
                        this.downloadReportUrl = ROOT_URL + '/budgets?download=true';
                        this.budgets = [];
                    }
                },
                filters: {
                    shortName: function(nombre)
                    {
                        var name;
                        name = nombre.substring(0, 3);
                        return name;
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="container-fluid">
                <h2>Reporte de presupuesto</h2>
                <br>
                <div class="row">
                    <form v-on:submit.prevent="searchBudget">
                        <div class="col-md-2">
                            <label>Centro de costos</label>
                            <select v-model="selected.costCenter" class="form-control" @change="onChangeCriteria" required>
                                <option v-for="roleCostCenter in rolesCostCenter" :value="roleCostCenter.costCenter">{{roleCostCenter.costCenter.name}}</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Año</label>
                            <select v-model="selected.year" class="form-control" @change="onChangeCriteria" required>
                                <option v-for="year in years" :value="year">
                                    {{year}}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <button style="margin-top: 25px" class="btn btn-info form-control" >Buscar</button>
                        </div>
                        <div class="col-md-2" v-if="budgets.length > 0">
                            <a style="margin-top: 25px" :href="downloadReportUrl"
                                    class="btn btn-success form-control">
                                Descargar reporte
                            </a>
                        </div>
                    </form>
                </div>
                <br>
                <div class="panel panel-default" v-if="budgets.length > 0">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-3 text-center">
                                    <b class="text-primary">{{selected.costCenter.name}}</b>
                                </div>
                                <div class="col-md-8 text-center">
                                    <div class="col-md-1" v-for="month in months">
                                        <b>{{month.month | shortName}}</b>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <b>Acumulado</b>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12" style="background-color: #e5e5e5;max-height:68%;overflow:auto;">
                        <div class="row" v-for="(indexOfBudget, budget) in budgets" style="background-color: #e5e5e5">
                            <div class="col-md-12">
                                <div class="col-md-3">
                                    <h5><b>{{budget.name}}</b></h5>
                                </div>
                                <div class="col-md-8">
                                    <div class="col-md-1">
                                        <b>{{budget.januaryCategoryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{budget.februaryCategoryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{budget.marchCategoryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{budget.aprilCategoryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{budget.mayCategoryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{budget.juneCategoryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{budget.julyCategoryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{budget.augustCategoryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{budget.septemberCategoryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{budget.octoberCategoryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{budget.novemberCategoryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{budget.decemberCategoryAmount | currency}}</b>
                                    </div>
                                </div>
                                <div class="col-md-1 text-center">
                                    <b class="text-primary">{{budget.totalCategoryAmount | currency}}</b>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="row" v-for="(indexOfBudgetSubcategory, budgetSubcategory) in budget.budgetSubcategories" style="background-color: #f5f5f5">
                                    <div class="col-md-12">
                                        <div class="col-md-3">
                                            <b class="text-muted">&nbsp;&nbsp;{{budgetSubcategory.name}}</b>
                                        </div>
                                        <div class="col-md-8">
                                            <div class="col-md-1">
                                                <p class="text-muted">{{budgetSubcategory.januarySubcategoryAmount | currency}}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <p class="text-muted">{{budgetSubcategory.februarySubcategoryAmount | currency}}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <p class="text-muted">{{budgetSubcategory.marchSubcategoryAmount | currency}}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <p class="text-muted">{{budgetSubcategory.aprilSubcategoryAmount | currency}}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <p class="text-muted">{{budgetSubcategory.maySubcategoryAmount | currency}}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <p class="text-muted">{{budgetSubcategory.juneSubcategoryAmount | currency}}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <p class="text-muted">{{budgetSubcategory.julySubcategoryAmount | currency}}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <p class="text-muted">{{budgetSubcategory.augustSubcategoryAmount | currency}}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <p class="text-muted">{{budgetSubcategory.septemberSubcategoryAmount | currency}}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <p class="text-muted">{{budgetSubcategory.octoberSubcategoryAmount | currency}}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <p class="text-muted">{{budgetSubcategory.novemberSubcategoryAmount | currency}}</p>
                                            </div>
                                            <div class="col-md-1">
                                                <p class="text-muted">{{budgetSubcategory.decemberSubcategoryAmount | currency}}</p>
                                            </div>
                                        </div>
                                        <div class="col-md-1 text-center">
                                            <b class="text-info">{{budgetSubcategory.totalSubcategoryAmount | currency}}</b>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="row" v-for="(indexOfBudgetYearConcept, budgetYearConcept) in budgetSubcategory.budgetYearConceptList"  style="background-color: #ffffff">
                                            <div class="col-md-12">
                                                <div class="col-md-3">
                                                    <p>&nbsp;&nbsp;&nbsp;&nbsp;{{budgetYearConcept.budgetConcept.budgetConcept}}</p>
                                                </div>
                                                <div class="col-md-8">
                                                    <div class="col-md-1">
                                                        <p>{{budgetYearConcept.januaryAmount | currency}}</p>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <p>{{budgetYearConcept.februaryAmount | currency}}</p>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <p>{{budgetYearConcept.marchAmount | currency}}</p>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <p>{{budgetYearConcept.aprilAmount | currency}}</p>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <p>{{budgetYearConcept.mayAmount | currency}}</p>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <p>{{budgetYearConcept.juneAmount | currency}}</p>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <p>{{budgetYearConcept.julyAmount | currency}}</p>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <p>{{budgetYearConcept.augustAmount | currency}}</p>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <p>{{budgetYearConcept.septemberAmount | currency}}</p>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <p>{{budgetYearConcept.octoberAmount | currency}}</p>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <p>{{budgetYearConcept.novemberAmount | currency}}</p>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <p>{{budgetYearConcept.decemberAmount | currency}}</p>
                                                    </div>
                                                </div>
                                                <div class="col-md-1 text-center">
                                                    <b class="text-muted">{{budgetYearConcept.totalAmount | currency}}</b>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
    </jsp:body>
</t:template>
