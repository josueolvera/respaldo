<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../assets/css/barralateral.css">
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

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
            function isNumberKey(evt)
            {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>

        <script type="text/javascript">

            var vm = new Vue({
                el: '#contenidos',
                created: function(){
                    this.setYears();
                },
                ready : function () {
                    this.getUserInSession();
                    this.getMonths();
                },
                data: {
                    now: '${now}',
                    minYear:null,
                    maxYear:null,
                    years: [],
                    months: [],
                    budgets: [],
                    user:{},
                    selected : {
                        roleCostCenter:{},
                        budgetType: {},
                        year: null
                    },
                    rolesCostCenter:[],
                    budgetCategories: [],
                    budgetAllOption: {
                        budgetCategory:'TODOS',
                        idBudgetCategory:0
                    },
                    role:null
                },
                methods: {
                    getUserInSession: function () {
                        this.$http.get(ROOT_URL + '/user')
                                .success(function (data) {
                                    this.user = data;
                                    this.role = this.user.dwEmployee.idRole;
                                    this.getRolesCostCenter(this.role);
                                })
                                .error(function (data) {
                                    showAlert('Ha habido un error al obtener al usuario en sesion', {type: 3});
                                });
                    },
                    getRolesCostCenter: function (idRole) {
                        this.$http.get(ROOT_URL + '/roles-cost-center/role/' + idRole)
                                .success(function (data) {
                                    this.rolesCostCenter = data;
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
                        this.contenido = [];
                        this.getBudgets();
                    },
                    getBudgets: function () {
                        this.$http.get(
                                ROOT_URL +
                                '/budgets/cost-center/' + this.selected.roleCostCenter.costCenter.idCostCenter +
                                '/budget-type/' + this.selected.budgetType.idBudgetType +
                                '/budget-nature/' + this.selected.roleCostCenter.budgetNature.idBudgetNature)
                                .success(function (data) {
                                    var self = this;
                                    this.budgetCategories = [];
                                    this.contenido = data;
                                    this.budgets = data;
                                    this.getConcepts();
                                    var index;
                                    data.forEach(function (budget) {
                                        index = self.arrayObjectIndexOf(self.budgetCategories,budget.idBudgetCategory,'idBudgetCategory');
                                        if (index == -1) {
                                            self.budgetCategories.push(budget.budgetCategory)
                                        }
                                    });
                                })
                                .error(function (data) {

                                });
                    },
                    getConcepts:function () {
                        this.$http.get(
                                ROOT_URL +
                                '/budget-concepts/cost-center/' + this.selected.roleCostCenter.costCenter.idCostCenter +
                                '/budget-type/' + this.selected.budgetType.idBudgetType +
                                '/budget-nature/' + this.selected.roleCostCenter.budgetNature.idBudgetNature +
                                '/year/' + this.selected.year)
                                .success(function (data) {
                                    var self = this;
                                    this.datosPresupuesto = data;
                                    this.datosPresupuesto.forEach(function (item) {
                                        if (item.authorized) {
                                            self.isAutorized = true;
                                        }
                                        item.conceptos.forEach(function (concepto) {
                                            concepto.conceptMonth.forEach(function (elem) {
                                                self.moneyFormat(elem, concepto, item);
                                            });
                                        });
                                    });
                                    this.getContenido();
                                })
                                .error(function (data) {

                                });
                    },
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos">
            <div class="container-fluid">
                <br>
                <h2>Reporte de presupuesto</h2>
                <br>
        </div> <!-- #contenidos -->
    </jsp:body>
</t:template>
