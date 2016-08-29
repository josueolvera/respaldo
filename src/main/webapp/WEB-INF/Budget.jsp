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
                    this.getBudgetTypes();
                    this.getMonths();
                },
                data: {
                    now: "${now}",
                    minYear:null,
                    maxYear:null,
                    years: [],
                    months: [],
                    budgets: [],
                    budgetTypes: [],
                    user:{},
                    selected : {
                        roleCostCenter:{},
                        budgetType: {},
                        year: null
                    },
                    rolesCostCenter:[],
                    budgetCategories: [],
                    datosPresupuesto: [],
                    contenido: [],
                    budgetAllOption: {
                        budgetCategory:'TODOS',
                        idBudgetCategory:0
                    },
                    isAutorized: false
                },
                methods: {
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
                                    this.rolesCostCenter = data;
                                })
                                .error(function (data) {

                                });
                    },
                    getBudgetTypes: function () {
                        this.$http.get(ROOT_URL + '/budget-types')
                                .success(function (data) {
                                    this.budgetTypes = data;
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
                    },
                    saveBudget: function (eventoconcepto) {
                        this.$http.post(ROOT_URL + "/budget-month-concepts", JSON.stringify(eventoconcepto))
                                .success(function (data) {
                                    showAlert(data);
                                    this.getBudgets();
                                })
                                .error(function (data) {
                                    showAlert("Ha habido un error con la solicitud, intente nuevamente");
                                });
                    },
                    getContenido: function () {
                        var self = this;
                        this.contenido = [];
                        if (this.selected.budgetCategory.idBudgetCategory === 0) {
                            this.contenido = this.budgets;
                            this.mixedArrays();
                        } else {
                            this.budgets.forEach(function (budget) {
                                if (budget.idBudgetCategory === self.selected.budgetCategory.idBudgetCategory) {
                                    self.contenido.push(budget);
                                }
                            });
                            this.mixedArrays();
                        }
                    },
                    mixedArrays: function() {
                        var self = this;
                        $.each(this.datosPresupuesto, function(index, el)
                        {
                            var BudgetTem = el;
                            $.each(self.contenido, function(indexs, ele) {
                                if (BudgetTem.idBudget == ele.idBudget)
                                {
                                    self.contenido.$remove(ele);
                                    self.contenido.push(BudgetTem);
                                }
                            });
                        });
                        self.groupArray();
                    },
                    groupArray: function() {
                        this.contenido = this.groupBy(this.contenido, function (item) {
                            return item.idBudgetCategory;
                        });
                    },
                    groupBy: function (array, filter) {
                        var groups = {};
                        array.forEach(function (element) {
                            var group = JSON.stringify(filter(element));
                            groups[group] = groups[group] || [];
                            groups[group].push(element);
                        });
                        return Object.keys(groups).map(function (group) {
                            return groups[group];
                        });
                    },
                    moneyFormat: function(mes, concepto, budget) {
                        var total= accounting.formatMoney(mes.amountConcept);
                        mes.amountConcept= total;
                        this.obtainTotalConcept(concepto, budget);
                    },
                    equalsImport: function(concepto, budget) {
                        if (concepto.equals) {
                            if (concepto.conceptMonth[0].amountConcept) {

                                $.each(concepto.conceptMonth, function(index, el) {
                                    el.amountConcept= concepto.conceptMonth[0].amountConcept;

                                });
                            } else {
                                alert("Debes ingresar un monto en el primer mes para esta acción");
                                concepto.equals= false;
                            }
                        } else {
                            $.each(concepto.conceptMonth, function(index, el) {
                                if (el.month> 1) {
                                    el.amountConcept= '';
                                } else {
                                    concepto.total= accounting.unformat(el.amountConcept);
                                }
                            });
                        }
                        this.obtainTotalConcept(concepto, budget);
                    },
                    deleteObject: function(budget, concepto) {
                        if (concepto.idConcept> 0) {
                            this.$http.delete(ROOT_URL + "/budget-concepts/" + concepto.idConcept)
                                    .success(function (data) {
                                        showAlert(data);
                                        this.getBudgets();
                                    });
                        } else {
                            budget.conceptos.$remove(concepto);
                            this.obtainTotalConcept(concepto, budget);
                        }
                    },
                    obtainTotalConcept: function(concepto, budget) {
                        concepto.total= 0;
                        budget.granTotal= 0;

                        $.each(budget.totalMonth, function(index, elemento)
                        {
                            elemento.montoConcept='';
                            var totalMes=0;
                            var key= index;
                            $.each(budget.conceptos, function(index, element)
                            {
                                totalMes+=accounting.unformat(element.conceptMonth[key].amountConcept);
                            });
                            elemento.montoConcept= accounting.formatMoney(totalMes);
                        });

                        $.each(concepto.conceptMonth, function(index, el)
                        {
                            var totals= accounting.unformat(el.amountConcept);
                            concepto.total+= parseFloat(totals);
                        });

                        concepto.total= accounting.formatMoney(concepto.total);

                        $.each(budget.conceptos, function(index, el)
                        {
                            var total= accounting.unformat(el.total);
                            budget.granTotal += total;
                        });

                        budget.granTotal= accounting.formatMoney(budget.granTotal);

                    },
                    seteoInfo: function(budget, event) {
                        event.preventDefault();
                        var concepto = this.createConcept();
                        var totalMeses = this.createTotalMonths();
                        concepto.idBudget= budget.idBudget;
                        concepto.year= this.selected.year;
                        if (! budget.conceptos) {
                            Vue.set(budget,"conceptos", []);
                            Vue.set(budget,"granTotal", '');
                            Vue.set(budget,"totalMonth", totalMeses);
                        }
                        budget.conceptos.push(concepto);
                    },
                    createConcept: function () {
                        return {
                            idConcept: 0,
                            idBudget: 0,
                            year: 0,
                            conceptName: '',
                            conceptMonth: [
                                {name: 'Enero', month: 1, amountConcept: ''},
                                {name: 'Febrero', month: 2 , amountConcept: ''},
                                {name: 'Marzo', month: 3, amountConcept: ''},
                                {name: 'Abril', month: 4, amountConcept: ''},
                                {name: 'Mayo', month: 5, amountConcept: ''},
                                {name: 'Junio', month: 6, amountConcept: ''},
                                {name: 'Julio', month: 7, amountConcept: ''},
                                {name: 'Agosto', month: 8 , amountConcept: ''},
                                {name: 'Septiembre', month: 9, amountConcept: ''},
                                {name: 'Octubre', month: 10, amountConcept: ''},
                                {name: 'Noviembre', month: 11, amountConcept: ''},
                                {name: 'Diciembre', month: 12, amountConcept: ''}
                            ],
                            total: 0,
                            equals: false
                        };
                    },
                    createTotalMonths: function() {
                        return [
                            {month: 1, montoConcept: ''},
                            {month: 2 ,montoConcept: ''},
                            {month: 3, montoConcept: ''},
                            {month: 4, montoConcept: ''},
                            {month: 5, montoConcept: ''},
                            {month: 6, montoConcept: ''},
                            {month: 7, montoConcept: ''},
                            {month: 8 ,montoConcept: ''},
                            {month: 9, montoConcept: ''},
                            {month: 10,montoConcept: ''},
                            {month: 11,montoConcept: ''},
                            {month: 12,montoConcept: ''}
                        ];
                    },
                    autorizar: function() {
                        var autorizacion = {};
                        autorizacion.idCostCenter = this.budgets[0].idCostCenter;
                        autorizacion.idBudgetType = this.budgets[0].idBudgetType;
                        autorizacion.idBudgetNature = this.budgets[0].idBudgetNature;
                        autorizacion.year = this.budgets[0].year;

                        this.$http.post(ROOT_URL + "/budget-month-branch/authorize", JSON.stringify(autorizacion))
                                .success(function(data) {
                                    this.getBudgets();
                                    showAlert('Presupuesto autorizado');
                                })
                                .error(function(data){
                                    showAlert('Ha habido un error con la solicitud, intente nuevamente');
                                });
                    }
                },
                filters: {
                    shortName: function(nombre) {
                        var name;
                        name = nombre.substring(0, 3);
                        return name;
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos">
            <div class="container-fluid">
                <br>
                <h2>Presupuesto</h2>
                <br>
                <div class="row">
                    <form v-on:submit.prevent="searchBudget">
                        <div class="col-md-2">
                            <label>Centro de costos</label>
                            <select v-model="selected.roleCostCenter" class="form-control" required>
                                <option v-for="roleCostCenter in rolesCostCenter" :value="roleCostCenter">{{roleCostCenter.costCenter.name}}</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Tipo</label>
                            <select v-model="selected.budgetType" class="form-control" required>
                                <option v-for="budgetType in budgetTypes" :value="budgetType">
                                    {{budgetType.budgetType}}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Año</label>
                            <select v-model="selected.year" class="form-control" required>
                                <option v-for="year in years" :value="year">
                                    {{year}}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-1">
                            <label style="visibility: hidden">search</label>
                            <button class="btn btn-info">Buscar</button>
                        </div>
                    </form>
                </div>
                <div class="row">
                    <div class="col-md-2" v-if="budgetCategories.length > 0">
                        <label>Rubro</label>
                        <select v-model="selected.budgetCategory" class="form-control" @change="getContenido">
                            <option selected :value="budgetAllOption">{{budgetAllOption.budgetCategory}}</option>
                            <option v-for="budgetCategory in budgetCategories" :value="budgetCategory">{{budgetCategory.budgetCategory}}</option>
                        </select>
                    </div>
                </div>
                <div class="row" v-for="cont in contenido"  style="margin-left: 0px; margin-right: 0px">
                    <div class="bs-callout bs-callout-default">
                        <h4>{{cont[0].budgetCategory.budgetCategory}}</h4>
                        <div class="row" v-for="conte in cont" style="margin-left: 0px; margin-right: 0px">
                            <div class="row" style="margin-left: 0px; margin-right: 0px">
                                <div class="col-xs-4">
                                    <h5>{{conte.budgetSubcategory.budgetSubcategory}}</h5>
                                    <div class="input-group">
                                        <span class="input-group-addon">$</span>
                                        <input type="text" class="form-control" placeholder="" disabled="true" v-model=conte.granTotal>
                                    </div>
                                </div>
                                <div class="col-xs-1 text-left">
                                    <button type="button" class="btn btn-default" :disabled="isAutorized"
                                            style="margin-top: 40px" @click="seteoInfo(conte, $event)">
                                        <span class="glyphicon glyphicon-plus"></span>
                                    </button>
                                </div>
                                <div class="col-xs-6 text left" v-if="conte.conceptos.length > 0">
                                    <button type="button" class="btn btn-default" :disabled="isAutorized"
                                            style="margin-top: 40px" @click="saveBudget(conte.conceptos)">
                                        <span class="glyphicon glyphicon-floppy-disk"></span>
                                    </button>
                                </div>
                            </div>
                            <br>
                            <div v-for="concepto in conte.conceptos">
                                <div class="row">
                                    <div class="col-xs-2">
                                        <label>Concepto</label>
                                        <input type="text" name="name" class="form-control input-sm"
                                               v-model="concepto.conceptName" :disabled="isAutorized">

                                    </div>
                                    <div class="col-xs-1" v-if="!isAutorized">
                                        <button style="margin-top: 27px" type="button" class="btn btn-default"
                                                @click="deleteObject(conte, concepto)">
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </button>
                                    </div>
                                    <div class="col-xs-2" v-if="!isAutorized">
                                        <label style="visibility: hidden">checkbox</label>
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" v-model="concepto.equals"
                                                       @change="equalsImport(concepto, conte)"> Copiar monto
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="row" style="margin-left: 0px" v-if="conte.conceptos.length > 0">
                                    <div class="col-xs-1" v-for="month in months"
                                         style="padding-left: 0px; padding-right: 1px">
                                        <label>
                                            {{month.month}}
                                        </label>
                                    </div>
                                </div>

                                <div class="row" style="margin-left: 0px">
                                    <div class="col-xs-1" v-for="mess in concepto.conceptMonth"
                                         style="padding-left: 0px; padding-right: 1px">
                                        <input type="text" class="form-control input-sm" v-model="mess.amountConcept"
                                               @change="moneyFormat(mess, concepto, conte)"  :disabled="isAutorized"
                                               style="font-size: 10px" onkeypress="return validateFloatKeyPress(this,event)">
                                    </div>
                                </div>
                                <br>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row" v-if="contenido.length > 0">
                    <div class="col-xs-12 text-center">
                        <button class="btn btn-success" @click="autorizar">Autorizar</button>
                    </div>
                </div>
                <br>
            </div>
        </div> <!-- #contenidos -->
    </jsp:body>
</t:template>
