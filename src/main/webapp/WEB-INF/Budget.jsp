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
                    this.getBudgetCategories();
                },
                data: {
                    now: "${now}",
                    minYear:null,
                    maxYear:null,
                    years: [],
                    budgets: [],
                    concepts: [],
                    budgetTypes: [],
                    user:{},
                    selected : {
                        budgetNature:{},
                        budgetType: {},
                        costCenter: {},
                        budgetCategory: {},
                        year: null,
                        yearFromCopy: null,
                        yearToCopy: null
                    },
                    searching: false,
                    rolesCostCenter:[],
                    costCenterList:[],
                    budgetNatureList:[],
                    budgetCategories: [],
                    datosPresupuesto: [],
                    budgetAllOption: {
                        budgetCategory:'TODOS',
                        idBudgetCategory:0
                    },
                    role:null,
                    errorMessage: ''
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
                    getConcepts : function (self) {
                        this.$http.get(ROOT_URL + '/budget-concepts')
                                .success(function (data) {
                                    self.concepts = data;
                                    self.budgets.forEach(function (budget, indexOfBudget) {
                                        budget.budgetSubcategories.forEach(function (budgetSubcategory, indexOfBudgetSubcategory) {
                                            budgetSubcategory.budgetYearConceptList.forEach(function (budgetYearConcept, indexOfBudgetYearConcept) {
                                                if ('selectConcepts' in budgetYearConcept) {
                                                    budgetYearConcept.selectConcepts[0].selectize.destroy();
                                                    delete budgetYearConcept.selectConcepts;
                                                }
                                                budgetYearConcept.selectConcepts = self.createSelectForConcept(indexOfBudget, indexOfBudgetSubcategory, indexOfBudgetYearConcept, data);
                                                budgetYearConcept.selectConcepts[0].selectize.setValue(budgetYearConcept.budgetConcept.idBudgetConcept);
                                            });
                                        });
                                    });
                                })
                                .error(function (data) {

                                });
                    },
                    createSelectForConcept : function (indexOfBudget, indexOfBudgetSubcategory, indexOfBudgetYearConcept, concepts) {
                        var self = this;
                        return $('#select-concepts-' + indexOfBudget + '-' + indexOfBudgetSubcategory + '-' + indexOfBudgetYearConcept).selectize({
                            maxItems: 1,
                            valueField: 'idBudgetConcept',
                            labelField: 'budgetConcept',
                            searchField: 'budgetConcept',
                            options: concepts,
                            create: function (input, callback) {
                                self.$http.post(ROOT_URL + '/budget-concepts', {
                                    budgetConcept: input
                                }).success(function (data){
                                    showAlert('Concepto guardado');
                                    self.getConcepts(self);
                                    callback(data);
                                }).error(function (){
                                    callback();
                                });
                            },
                            render: {
                                option_create: function(data, escape) {
                                    return '<div data-selectable class="create">' +
                                            'Agregar <strong>' + escape(data.input) + '</strong>' +
                                            '</div>'
                                }
                            }
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
                        var url = ROOT_URL +
                                '/budgets?cost_center=' + this.selected.costCenter.idCostCenter +
                                '&year=' + this.selected.year +
                                '&budget_type=' + this.selected.budgetType.idBudgetType +
                                '&budget_nature=' + this.selected.budgetNature.idBudgetNature;
                        if (this.selected.budgetCategory.idBudgetCategory != 0) {
                            url += '&category=' + this.selected.budgetCategory.idBudgetCategory;
                        }
                        this.$http.get(url)
                                .success(function (data) {
                                    var self = this;
                                    this.budgets = data;
                                    this.searching = false;
                                    this.getConcepts(self);
                                })
                                .error(function (data) {

                                });
                    },
                    getBudgetCategories : function () {
                        this.$http.get(ROOT_URL + '/budget-categories')
                                .success(function (data) {
                                    this.budgetCategories = data;
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
                    saveBudget: function (budgetYearConceptList, idBudget) {

                        var budgetYearConceptListToSave = budgetYearConceptList;

                        var isValid = false;

                        budgetYearConceptListToSave.every(function (budgetYearConcept, index, _ary) {
                            budgetYearConcept.budgetConcept = {};

                            if (budgetYearConcept.selectConcepts[0].selectize.getValue() != '') {
                                budgetYearConcept.budgetConcept.idBudgetConcept = budgetYearConcept.selectConcepts[0].selectize.getValue();
                                budgetYearConcept.budgetConcept.budgetConcept = budgetYearConcept.selectConcepts[0].selectize.getOption(budgetYearConcept.budgetConcept.idBudgetConcept).text();
                                isValid = true;

                            } else {
                                showAlert('Selecciona un concepto', {type:3});
                                budgetYearConcept.selectConcepts[0].selectize.focus();
                                isValid = false;
                            }

                            return budgetYearConcept.selectConcepts[0].selectize.getValue() != '';

                        });

                        if (isValid) {

                            budgetYearConceptListToSave.forEach(function (budgetYearConcept) {
                                delete budgetYearConcept.selectConcepts;
                            });

                            this.$http.post(ROOT_URL + '/budget-year-concept/' + idBudget, JSON.stringify(budgetYearConceptListToSave))
                                    .success(function (data) {
                                        showAlert('Presupuesto guardado');
                                        this.getBudgets();
                                    })
                                    .error(function (data) {
                                        showAlert('Ha habido un error con la solicitud, intente nuevamente');
                                    });
                        }
                    },
                    equalsImport: function(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept) {
                        if (budgetYearConcept.equals) {
                            if (budgetYearConcept.januaryAmount != '') {
                                budgetYearConcept.februaryAmount = budgetYearConcept.januaryAmount;
                                budgetYearConcept.marchAmount = budgetYearConcept.januaryAmount;
                                budgetYearConcept.aprilAmount = budgetYearConcept.januaryAmount;
                                budgetYearConcept.mayAmount = budgetYearConcept.januaryAmount;
                                budgetYearConcept.juneAmount = budgetYearConcept.januaryAmount;
                                budgetYearConcept.julyAmount = budgetYearConcept.januaryAmount;
                                budgetYearConcept.augustAmount = budgetYearConcept.januaryAmount;
                                budgetYearConcept.septemberAmount = budgetYearConcept.januaryAmount;
                                budgetYearConcept.octoberAmount = budgetYearConcept.januaryAmount;
                                budgetYearConcept.novemberAmount = budgetYearConcept.januaryAmount;
                                budgetYearConcept.decemberAmount = budgetYearConcept.januaryAmount;

                                this.getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept);

                            } else {
                                showAlert('Debes ingresar un monto en el primer mes para esta acción', {type:2});
                                budgetYearConcept.equals = false;
                            }
                        } else {
                            budgetYearConcept.februaryAmount = 0;
                            budgetYearConcept.marchAmount = 0;
                            budgetYearConcept.aprilAmount = 0;
                            budgetYearConcept.mayAmount = 0;
                            budgetYearConcept.juneAmount = 0;
                            budgetYearConcept.julyAmount = 0;
                            budgetYearConcept.augustAmount = 0;
                            budgetYearConcept.septemberAmount = 0;
                            budgetYearConcept.octoberAmount = 0;
                            budgetYearConcept.novemberAmount = 0;
                            budgetYearConcept.decemberAmount = 0;

                            this.getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept);
                        }
                    },
                    addConcept: function(indexOfBudget, indexOfBudgetSubcategory, budgetSubcategory, concepts) {
                        var self = this;
                        var budgetYearConcept = this.createNewConcept();
                        budgetSubcategory.budgetYearConceptList.push(budgetYearConcept);
                        var indexOfBudgetYearConcept = budgetSubcategory.budgetYearConceptList.length - 1;
                        setTimeout(
                                function() {
                                    self.budgets[indexOfBudget]
                                            .budgetSubcategories[indexOfBudgetSubcategory]
                                            .budgetYearConceptList[indexOfBudgetYearConcept]
                                            .selectConcepts = self.createSelectForConcept(indexOfBudget, indexOfBudgetSubcategory, indexOfBudgetYearConcept, concepts);
                                },
                                100,
                                indexOfBudget,
                                indexOfBudgetSubcategory,
                                indexOfBudgetYearConcept,
                                concepts
                        );
                    },
                    removeConcept: function(budgetSubcategory, concept) {

                        if ('idBudgetYearConcept' in concept) {
                            this.$http.delete(ROOT_URL + "/budget-year-concept/" + concept.idBudgetYearConcept)
                                    .success(function (data) {
                                        showAlert('Concepto eliminado');
                                        this.getBudgets();
                                    });
                        } else {
                            budgetSubcategory.budgetYearConceptList.$remove(concept);
                        }
                    },
                    createNewConcept : function () {
                        return {
                            januaryAmount: 0,
                            februaryAmount: 0,
                            marchAmount: 0,
                            aprilAmount: 0,
                            mayAmount: 0,
                            juneAmount: 0,
                            julyAmount: 0,
                            augustAmount: 0,
                            septemberAmount: 0,
                            octoberAmount: 0,
                            novemberAmount: 0,
                            decemberAmount: 0,
                            totalAmount: 0,
                            budgetConcept: {},
                            year: this.selected.year,
                            equals: false
                        };
                    },
                    authorize: function() {
                        var autorizacion = {};
                        autorizacion.idCostCenter = this.budgets[0].budgetSubcategories[0].costCenter.idCostCenter;
                        autorizacion.idBudgetType = this.budgets[0].budgetSubcategories[0].budgetType.idBudgetType;
                        autorizacion.idBudgetNature = this.budgets[0].budgetSubcategories[0].budgetNature.idBudgetNature;
                        autorizacion.year = this.selected.year;

                        if (this.selected.budgetCategory.idBudgetCategory != 0) {
                            autorizacion.idBudgetCategory = this.selected.budgetCategory.idBudgetCategory;
                        }

                        this.$http.post(ROOT_URL + '/budget-year-concept/authorize', JSON.stringify(autorizacion))
                                .success(function(data) {
                                    this.getBudgets();
                                    showAlert('Presupuesto autorizado');
                                })
                                .error(function(data){
                                    showAlert('Ha habido un error con la solicitud, intente nuevamente', {type:3});
                                });
                    },
                    copyBudget: function (overwrite) {
                        var url = '/budget-year-concept/copy-budget?cost_center=' + this.budgets[0].budgetSubcategories[0].costCenter.idCostCenter +
                                '&nature=' + this.budgets[0].budgetSubcategories[0].budgetNature.idBudgetNature +
                                '&budget_type=' + this.budgets[0].budgetSubcategories[0].budgetType.idBudgetType +
                                '&year_from_copy=' + this.selected.yearFromCopy +
                                '&year_to_copy=' + this.selected.yearToCopy;

                        if (this.selected.budgetCategory.idBudgetCategory != 0) {
                            url += '&category=' + this.selected.budgetCategory.idBudgetCategory;
                        }

                        if (overwrite) {
                            url += '&overwrite=' + overwrite;
                        }

                        this.$http.post(ROOT_URL + url,{})
                                .success(function(data) {
                                    this.getBudgets();
                                    this.closeCopyBudgetModal();
                                    this.closeOverwriteModal();
                                    showAlert('Presupuesto copiado');
                                })
                                .error(function(data){
                                    this.errorMessage = data.error.message;
                                    this.closeCopyBudgetModal();
                                    this.showOverwriteModal();
                                });
                    },
                    onClickAcept : function () {
                        this.copyBudget(false);
                    },
                    onClickOverwrite : function () {
                        this.copyBudget(true);
                    },
                    showCopyBudgetModal: function () {
                        $('#copyBudgetModal').modal('show');
                    },
                    closeCopyBudgetModal: function () {
                        $('#copyBudgetModal').modal('hide');
                    },
                    showOverwriteModal: function () {
                        $('#overwriteModal').modal('show');
                    },
                    closeOverwriteModal: function () {
                        $('#overwriteModal').modal('hide');
                    },
                    getBudgetYearConcept : function (indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept) {

                        console.log(budgetYearConcept);

                        budgetYearConcept.totalAmount = 0;
                        
                        budgetYearConcept.totalAmount +=  budgetYearConcept.januaryAmount;
                        budgetYearConcept.totalAmount +=  budgetYearConcept.februaryAmount;
                        budgetYearConcept.totalAmount +=  budgetYearConcept.marchAmount;
                        budgetYearConcept.totalAmount +=  budgetYearConcept.aprilAmount;
                        budgetYearConcept.totalAmount +=  budgetYearConcept.mayAmount;
                        budgetYearConcept.totalAmount +=  budgetYearConcept.juneAmount;
                        budgetYearConcept.totalAmount +=  budgetYearConcept.julyAmount;
                        budgetYearConcept.totalAmount +=  budgetYearConcept.augustAmount;
                        budgetYearConcept.totalAmount +=  budgetYearConcept.septemberAmount;
                        budgetYearConcept.totalAmount +=  budgetYearConcept.octoberAmount;
                        budgetYearConcept.totalAmount +=  budgetYearConcept.novemberAmount;
                        budgetYearConcept.totalAmount +=  budgetYearConcept.decemberAmount;

                        this.getTotalSubRubro(indexOfBudget, indexOfBudgetSubcategory);

                    },
                    getTotalSubRubro : function (indexOfBudget, indexOfBudgetSubcategory) {
                        var self = this;

                        this.budgets[indexOfBudget].budgetSubcategories[indexOfBudgetSubcategory].totalSubcategoryAmount = 0;

                        this.budgets[indexOfBudget]
                                .budgetSubcategories[indexOfBudgetSubcategory]
                                .budgetYearConceptList.forEach(function (budgetYearConcept) {
                                    self.budgets[indexOfBudget].budgetSubcategories[indexOfBudgetSubcategory].totalSubcategoryAmount += budgetYearConcept.totalAmount;
                                });
                    },
                    onChangeFilter : function () {
                        this.budgets = [];
                    }
                },
                filters: {
                    shortName: function(nombre) {
                        var name;
                        name = nombre.substring(0, 3);
                        return name;
                    },
                    currencyDisplay : {
                        read: function(val) {
                            return '$'+val.toFixed(2);
                        },
                        write: function(val, oldVal) {
                            var number = +val.replace(/[^\d.]/g, '');
                            return isNaN(number) ? 0 : parseFloat(number.toFixed(2));
                        }
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos">
            <div class="container-fluid">
                <br>
                <h2>Captura de presupuesto</h2>
                <br>
                <div class="row">
                    <form v-on:submit.prevent="searchBudget">
                        <div class="row">
                            <div class="col-md-2">
                                <label>Centro de costos</label>
                                <select v-model="selected.costCenter" class="form-control" @change="onChangeFilter" required>
                                    <option v-for="costCenter in costCenterList" :value="costCenter">{{costCenter.name}}</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <label>Tipo de gasto</label>
                                <select v-model="selected.budgetNature" class="form-control" @change="onChangeFilter" required>
                                    <option v-for="budgetNature in budgetNatureList" :value="budgetNature">{{budgetNature.budgetNature}}</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <label>Tipo</label>
                                <select v-model="selected.budgetType" class="form-control" @change="onChangeFilter" required>
                                    <option v-for="budgetType in budgetTypes" :value="budgetType">
                                        {{budgetType.budgetType}}
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
                                <label>Rubro</label>
                                <select v-model="selected.budgetCategory" class="form-control" @change="onChangeFilter" required>
                                    <option selected :value="budgetAllOption">{{budgetAllOption.budgetCategory}}</option>
                                    <option v-for="budgetCategory in budgetCategories" :value="budgetCategory">{{budgetCategory.budgetCategory}}</option>
                                </select>
                            </div>
                            <div class="col-md-1">
                                <button style="margin-top: 25px" class="btn btn-info">Buscar</button>
                            </div>
                        </div>
                    </form>
                    <div class="row" v-if="budgets.length > 0 && !searching">
                        <div class="col-md-2">
                            <button type="button" style="margin-top: 15px"
                                    class="btn btn-default" @click="showCopyBudgetModal">
                                Copiar presupuesto
                            </button>
                        </div>
                        <div class="col-md-2" v-if="budgets.length > 0 && role == 19">
                            <button type="button" style="margin-top: 15px"
                                    class="btn btn-success" @click="authorize">
                                Autorizar
                            </button>
                        </div>
                    </div>
                </div>
                <br>
                <div v-if="searching" class="col-xs-12"
                     style="height: 6rem; padding: 2rem 0;">
                    <div class="loader">Cargando...</div>
                </div>
                <div v-else="!searching">
                    <div class="row" v-for="(indexOfBudget, budget) in budgets"  style="margin-left: 0px; margin-right: 0px">
                        <div class="bs-callout bs-callout-default">
                            <h3><b>{{budget.name}}</b></h3>
                            <div class="row" v-for="(indexOfBudgetSubcategory, budgetSubcategory) in budget.budgetSubcategories" style="margin-left: 0px; margin-right: 0px">
                                <div class="well">
                                    <form v-on:submit.prevent="saveBudget(budgetSubcategory.budgetYearConceptList, budgetSubcategory.idBudget)">
                                        <div class="row" style="margin-left: 0px; margin-right: 0px">
                                            <div class="col-xs-4">
                                                <h4>{{budgetSubcategory.name}}</h4>
                                            </div>
                                        </div>
                                        <br>
                                        <div class="row" style="margin-left: 0px; margin-right: 0px">
                                            <div class="col-xs-2">
                                                <h4>Total:&nbsp;&nbsp;&nbsp;<b class="text-primary">{{budgetSubcategory.totalSubcategoryAmount | currency}}</b></h4>
                                            </div>
                                            <div class="col-xs-1 text-left">
                                                <button type="button" class="btn btn-default" :disabled="budgetYearConcept.authorized"
                                                        @click="addConcept(indexOfBudget, indexOfBudgetSubcategory, budgetSubcategory, concepts)">
                                                    <span class="glyphicon glyphicon-plus"></span>
                                                </button>
                                            </div>
                                            <div class="col-xs-1 text left" v-if="budgetSubcategory.budgetYearConceptList.length > 0">
                                                <button type="submit" class="btn btn-default" :disabled="budgetYearConcept.authorized">
                                                    <span class="glyphicon glyphicon-floppy-disk"></span>
                                                </button>
                                            </div>
                                        </div>
                                        <br>
                                        <div v-for="(indexOfBudgetYearConcept, budgetYearConcept) in budgetSubcategory.budgetYearConceptList">
                                            <div class="row">
                                                <div class="col-xs-3">
                                                    <label>Concepto</label>
                                                    <select id="select-concepts-{{indexOfBudget}}-{{indexOfBudgetSubcategory}}-{{indexOfBudgetYearConcept}}"
                                                            class="form-control" :disabled="budgetYearConcept.authorized">
                                                    </select>

                                                </div>
                                                <div class="col-xs-1" v-if="!budgetYearConcept.authorized">
                                                    <button style="margin-top: 27px" type="button" class="btn btn-default"
                                                            @click="removeConcept(budgetSubcategory, budgetYearConcept)">
                                                        <span class="glyphicon glyphicon-trash"></span>
                                                    </button>
                                                </div>
                                                <div class="col-xs-2" v-if="!budgetYearConcept.authorized">
                                                    <div class="checkbox">
                                                        <label style="margin-top: 27px">
                                                            <input type="checkbox" v-model="budgetYearConcept.equals" @change="equalsImport(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)"> Copiar monto
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="col-xs-4" style="margin-top: 27px">
                                                    <h5>Total concepto:&nbsp;&nbsp;&nbsp;<b class="text-primary">{{budgetYearConcept.totalAmount | currency}}</b></h5>
                                                </div>
                                            </div>
                                            <br>
                                            <div class="row" style="margin-left: 0px">
                                                <div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">
                                                    <label>Ene</label>
                                                    <input type="text" class="form-control" v-model="budgetYearConcept.januaryAmount | currencyDisplay" required
                                                           @change="getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)" :disabled="budgetYearConcept.authorized" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                                <div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">
                                                    <label>Feb</label>
                                                    <input type="text" class="form-control" v-model="budgetYearConcept.februaryAmount | currencyDisplay" required
                                                           @change="getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)" :disabled="budgetYearConcept.authorized" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                                <div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">
                                                    <label>Mar</label>
                                                    <input type="text" class="form-control" v-model="budgetYearConcept.marchAmount | currencyDisplay" required
                                                           @change="getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)" :disabled="budgetYearConcept.authorized" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                                <div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">
                                                    <label>Abr</label>
                                                    <input type="text" class="form-control" v-model="budgetYearConcept.aprilAmount | currencyDisplay" required
                                                           @change="getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)" :disabled="budgetYearConcept.authorized" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                                <div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">
                                                    <label>May</label>
                                                    <input type="text" class="form-control" v-model="budgetYearConcept.mayAmount | currencyDisplay" required
                                                           @change="getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)" :disabled="budgetYearConcept.authorized" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                                <div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">
                                                    <label>Jun</label>
                                                    <input type="text" class="form-control" v-model="budgetYearConcept.juneAmount | currencyDisplay" required
                                                           @change="getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)" :disabled="budgetYearConcept.authorized" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                                <div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">
                                                    <label>Jul</label>
                                                    <input type="text" class="form-control" v-model="budgetYearConcept.julyAmount | currencyDisplay" required
                                                           @change="getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)" :disabled="budgetYearConcept.authorized" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                                <div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">
                                                    <label>Ago</label>
                                                    <input type="text" class="form-control" v-model="budgetYearConcept.augustAmount | currencyDisplay" required
                                                           @change="getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)" :disabled="budgetYearConcept.authorized" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                                <div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">
                                                    <label>Sep</label>
                                                    <input type="text" class="form-control" v-model="budgetYearConcept.septemberAmount | currencyDisplay" required
                                                           @change="getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)" :disabled="budgetYearConcept.authorized" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                                <div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">
                                                    <label>Oct</label>
                                                    <input type="text" class="form-control" v-model="budgetYearConcept.octoberAmount | currencyDisplay" required
                                                           @change="getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)" :disabled="budgetYearConcept.authorized" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                                <div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">
                                                    <label>Nov</label>
                                                    <input type="text" class="form-control" v-model="budgetYearConcept.novemberAmount | currencyDisplay" required
                                                           @change="getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)" :disabled="budgetYearConcept.authorized" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                                <div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">
                                                    <label>Dic</label>
                                                    <input type="text" class="form-control" v-model="budgetYearConcept.decemberAmount | currencyDisplay" required
                                                           @change="getBudgetYearConcept(indexOfBudget, indexOfBudgetSubcategory, budgetYearConcept)" :disabled="budgetYearConcept.authorized" onkeypress="return validateFloatKeyPress(this,event)">
                                                </div>
                                            </div>
                                            <hr style="border: 1px solid #ccc">
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="modal fade" id="copyBudgetModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <form v-on:submit.prevent="onClickAcept">
                                <div class="modal-header">
                                    <button type="button" class="close" aria-label="Close" @click="closeCopyBudgetModal"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="copyBudgetModalLabel">Copiar presupuesto</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-xs-1">
                                            <p>De</p>
                                        </div>
                                        <div class="col-xs-5">
                                            <select v-model="selected.yearFromCopy" class="form-control" required>
                                                <option v-for="year in years" :value="year">
                                                    {{year}}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-xs-1">
                                            <p>a</p>
                                        </div>
                                        <div class="col-xs-5">
                                            <select v-model="selected.yearToCopy" class="form-control" required>
                                                <option v-for="year in years" :value="year">
                                                    {{year}}
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" @click="closeCopyBudgetModal">Cancelar</button>
                                    <button class="btn btn-success">Aceptar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="overwriteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" aria-label="Close" @click="closeOverwriteModal"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="overwriteModalLabel">Aviso</h4>
                            </div>
                            <div class="modal-body">
                                {{errorMessage}}
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" @click="closeOverwriteModal">Cancelar</button>
                                <button type="button" class="btn btn-success" @click="onClickOverwrite">Aceptar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div> <!-- #contenidos -->
    </jsp:body>
</t:template>
