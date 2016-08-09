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

            var vm= new Vue({
                el: '#contenidos',
                created: function(){
                    var now = new Date(this.now);
                    this.selected.year = now.getFullYear();
                    this.currentYear = now.getFullYear();
                    this.maxYear = this.currentYear + 5;
                    this.$http.get(ROOT_URL + "/areas")
                            .success(function (data)
                            {
                                this.catalogoAreas= data;
                                this.bandera1ernivel= true;
                            });

                    this.$http.get(ROOT_URL + "/budget-categories")
                            .success(function (data)
                            {
                                this.catalogoRubros= data;
                                this.bandera2donivel= true;
                            });

                    this.$http.get(ROOT_URL + "/budget-subcategories")
                            .success(function (data)
                            {
                                this.catalogoSubRubros= data;
                                this.bandera3ernivel= true;
                            });

                    this.$http.get(ROOT_URL + "/distributors")
                            .success(function (data)
                            {
                                this.catalogoDistribuidor= data;
                                //this.bandera3ernivel= true;
                            });

                    this.$http.get(ROOT_URL + "/branchs")
                            .success(function (data)
                            {
                                this.catalogoSucursales= data;
                                //this.bandera3ernivel= true;
                            });
                    this.$http.get(ROOT_URL + "/groups")
                            .success(function (data)
                            {
                                this.catalogoGrupo= data;
                                //this.bandera3ernivel= true;
                            });
                },
                ready: function ()
                {},
                data: {
                    meses: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
                        'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
                    catalogoGrupo: {},
                    datosPresupuesto: {},
                    catalogoDistribuidor: {},
                    catalogoRegion: {},
                    catalogoSucursales: {},
                    catalogoAreas: {},
                    catalogoRubros: {},
                    catalogoSubRubros: {},
                    idBranchSelected: '',
                    arbolNiveles: {},
                    contenido: {},
                    sucursales: [],
                    budgets: [],
                    areas: [],
                    budgetCategories: [],
                    currentYear: null,
                    maxYearmaxYear: null,
                    now: "${now}",
                    selected:{
                        distributor:null,
                        dwEnterprise:null,
                        branch: null,
                        area:null,
                        budgetCategory:{},
                        year:null
                    },
                    select:{
                        dwEnterprises:[]
                    },
                    branches: false,
                    flag: true,
                    cargando: false,
                    butgetAllOption: {
                        budgetCategory:'TODOS',
                        idBudgetCategory:0
                    },
                    bandera1ernivel: false,
                    bandera2donivel: false,
                    bandera3ernivel: false,
                    banderacontenido: false,
                    lastkeysearch: '',
                    area: '',
                    totalArea: '',
                    newSearch: false,
                    year: 0,
                    isAutorized: false,
                    autorizacion: {
                        idGroup: '',
                        idArea: '',
                        year: 0
                    },
                    showInfo: false,
                    sucursal: {},
                    distributors: {},
                    conceptoProrrateo: '',
                    distributorChecked: [],
                    prorrateoOpcion: '',
                    monthChecked: [],
                    idAreaforModal: 0,
                    monthsOfConcept: {},
                    budgetConceptShare: null,
                    idConcepto: 0,
                    totalIngresos: 0,
                    utilidad: 0
                },
                computed: {
                    totalPorcentaje: function()
                    {
                        var total= 0;
                        this.distributorChecked.forEach(function(element)
                        {
                            total += (isNaN(parseFloat(element.percent))) ? 0 : parseFloat(element.percent);
                        });
                        return (isNaN(total)) ? 0 : total.toFixed(2);
                    }
                },
                methods:
                {
                    groupBy: function (array, filter)
                    {
                        var groups = {};
                        array.forEach(function (element)
                        {
                            var group = JSON.stringify(filter(element));
                            groups[group] = groups[group] || [];
                            groups[group].push(element);
                        });
                        return Object.keys(groups).map(function (group) {
                            return groups[group];
                        });
                    },
                    searchBudget : function () {
                        this.getBudgetsByDistributorAndArea();
                    },
                    prepareList: function(event)
                    {
                        $(event.target).toggleClass('expanded');
                        $(event.target).children('ul').toggle('medium');
                        event.stopPropagation();
                        this.navigation(event.target.id);
                    },
                    prepareList1: function(event)
                    {
                        $(event.target).toggleClass('expanded');
                        $(event.target).children('ul').toggle('medium');
                        var ids= event.target.id.substr(2, event.target.id.length);
                        var p = $("#"+ids);
                        var position = p.position();
                        var posicion= position.top;
                        $("#page-content-wrapper").scrollTop(posicion);
                        event.stopPropagation();
                    }
                    ,
                    navigation: function(key)
                    {
                        var ids= key.substr(2, key.length);
                        var res = ids.split("-");
                        if (this.lastkeysearch !== key)
                        {
                            this.newSearch= true;
                            this.year= '';
                            this.showInfo= false;
                            this.sucursales= [];
                            this.contenido= {};
                            this.$http.get(ROOT_URL + "/dw-enterprises/"+res[0]+"/"+res[1])
                                    .success(function (data)
                                    {
                                        var self= this;
                                        var count = Object.keys(data).length;
                                        if (count > 1)
                                        {
                                            this.branches= true;
                                            var agrupados = this.groupBy(data, function (item)
                                            {
                                                return item.iddistributor;
                                            });

                                            this.sucursal = agrupados.map(function(ele)
                                            {
                                                return self.groupBy(ele, function(item)
                                                {
                                                    return item.idregion;
                                                });
                                            });

                                        }
                                        else {

                                            this.sucursales= data;
                                            this.branches= false;
                                        }
                                    });

                            this.$http.get(ROOT_URL + "/budgets/"+res[0]+"/"+res[1])
                                    .success(function (data)
                                    {
                                        this.cargando= false;
                                        this.contenido = data;
                                    });
                            this.group = res[0];
                            this.area = res[1];
                            this.lastkeysearch = key;
                        }


                    },
                    createConcept: function ()
                    {
                        var objeto = {
                            idConcept: 0,
                            idBudget: 0,
                            dwEnterprise: 0,
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
                        return objeto;
                    },
                    createTotalMonths: function()
                    {
                        var objeto =
                                [
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
                        return objeto;
                    },
                    searchConcepts: function()
                    {
                        this.showInfo = false;
                        var self= this;
                        var distributor= this.selected.distributor.idDistributor;
                        var area= this.selected.area.idArea;
                        var year= this.selected.year;
                        var dwEnterprise;
                        if (this.selected.dwEnterprise != null) {
                            dwEnterprise = this.selected.dwEnterprise.idDwEnterprise;
                        } else {
                            dwEnterprise = 0;
                        }
                        this.isAutorized= false;
                        this.cargando= true;
                        this.$http.get(ROOT_URL + "/budget-concepts/group-area/"+distributor+"/"+area+"/"+dwEnterprise+"/"+year)
                                .success(function (data)
                                {
                                    this.datosPresupuesto = data;

                                    $.each(this.datosPresupuesto, function(index, el)
                                    {
                                        if (el.isAuthorized == 1)
                                        {
                                            self.isAutorized= true;
                                        }
                                        $.each(el.conceptos, function(indexs, ele)
                                        {
                                            $.each(ele.conceptMonth, function(indexss, elem)
                                            {
                                                self.moneyFormat(elem, ele, el);
                                            });
                                        });
                                    });
                                    this.mixedArrays();
                                });
                    }
                    ,
                    seteoInfo: function(idDwEnterprise, budget, event)
                    {
                        event.preventDefault();
                        var concepto= this.createConcept();
                        var totalMeses= this.createTotalMonths();
                        concepto.dwEnterprise = idDwEnterprise;
                        concepto.idBudget= budget.idBudget;
                        concepto.year= this.selected.year;
                        if (! budget.conceptos)
                        {
                            Vue.set(budget,"conceptos", []);
                            Vue.set(budget,"granTotal", '');
                            Vue.set(budget,"totalMonth", totalMeses);
                        }
                        budget.conceptos.push(concepto);
                    },
                    mixedArrays: function() {
                        var self= this;
                        $.each(this.datosPresupuesto, function(index, el)
                        {
                            var BudgetTem = el;
                            $.each(self.contenido, function(indexs, ele)
                            {   var isentry = ele.isentry;

                                if (BudgetTem.idBudget == ele.idBudget)
                                {
                                    Vue.set(BudgetTem,"isentry", isentry);
                                    self.contenido.$remove(ele);
                                    self.contenido.push(BudgetTem);
                                }
                            });
                        });
                        self.groupArray();
                    },
                    groupArray: function()
                    {
                        this.contenido = this.groupBy(this.contenido, function (item)
                        {
                            return item.idBudgetCategory;
                        });
                        this.banderacontenido = true;
                        this.showInfo = true;
                        this.obtainGranTotal();
                    }
                    ,
                    deleteObject: function(budget, concepto)
                    {
                        if (concepto.idConcept> 0)
                        {
                            this.$http.delete(ROOT_URL + "/budget-concepts/"+concepto.idConcept)
                                    .success(function (data)
                                    {
                                        showAlert(data);
                                        this.getBudgetsByDistributorAndArea();
                                    });
                        }
                        else{
                            budget.conceptos.$remove(concepto);
                            this.obtainTotalConcept(concepto, budget);
                        }
                    },
                    moneyFormat: function(mes, concepto, budget)
                    {
                        var total= accounting.formatMoney(mes.amountConcept);
                        mes.amountConcept= total;
                        this.obtainTotalConcept(concepto, budget);
                    },
                    equalsImport: function(concepto, budget)
                    {
                        if (concepto.equals)
                        {
                            if (concepto.conceptMonth[0].amountConcept)
                            {

                                $.each(concepto.conceptMonth, function(index, el)
                                {
                                    el.amountConcept= concepto.conceptMonth[0].amountConcept;

                                });
                            }
                            else{
                                alert("Debes ingresar un monto en el primer mes para esta acción");
                                concepto.equals= false;
                            }
                        }
                        else
                        {
                            $.each(concepto.conceptMonth, function(index, el)
                            {
                                if (el.month> 1)
                                {
                                    el.amountConcept= '';
                                }
                                else{
                                    concepto.total= accounting.unformat(el.amountConcept);
                                }
                            });
                        }
                        this.obtainTotalConcept(concepto, budget);
                    },
                    obtainTotalConcept: function(concepto, budget)
                    {
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
                    getDwEnterprisesByDistributorAndArea: function () {
                        this.$http.get(ROOT_URL + '/dw-enterprises/distributor/' + this.selected.distributor.idDistributor + '/area/' + this.selected.area.idArea)
                                .success(function (data) {
                                    this.select.dwEnterprises = data;
                                    if(this.select.dwEnterprises.length > 0) {
                                        this.selected.dwEnterprise = this.select.dwEnterprises[0];
                                    }
                                });
                    },
                    arrayObjectIndexOf : function(myArray, searchTerm, property) {
                        for(var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    getBudgetsByDistributor: function () {
                        this.$http.get(ROOT_URL + '/budgets/distributor/' + this.selected.distributor.idDistributor)
                                .success(function (data) {
                                    var self = this;
                                    this.budgets = data;
                                    this.areas = [];
                                    var index;
                                    data.forEach(function (budget) {
                                        index = self.arrayObjectIndexOf(self.areas,budget.idArea,'idArea');
                                        if (index == -1) {
                                            self.areas.push(budget.area)
                                        }
                                    });
                                });
                    },
                    getBudgetsByDistributorAndArea: function () {
                        this.$http.get(ROOT_URL + '/budgets/distributor/' + this.selected.distributor.idDistributor + '/area/' + this.selected.area.idArea)
                                .success(function (data) {
                                    var self = this;
                                    this.showInfo = false;
                                    this.contenido = data;
                                    this.budgets = data;
                                    this.searchConcepts();
                                    this.budgetCategories = [];
                                    var index;
                                    data.forEach(function (budget) {
                                        index = self.arrayObjectIndexOf(self.budgetCategories,budget.idBudgetCategory,'idBudgetCategory');
                                        if (index == -1) {
                                            self.budgetCategories.push(budget.budgetCategory)
                                        }
                                    });
                                });
                    },
                    obtainGranTotal: function()
                    {
                        this.totalArea= 0;
                        this.totalIngresos = 0;
                        this.utilidad = 0;
                        var self= this;
                        vm.contenido.forEach(function(budgetagrupado){
                            budgetagrupado.forEach(function(budget){
                                if ( typeof budget.granTotal != "undefined")
                                {
                                    if (budget.isentry == 0) {
                                        var granTotal= accounting.unformat(budget.granTotal);
                                        self.totalArea += granTotal;
                                    }
                                    else {
                                        var granTotal= accounting.unformat(budget.granTotal);
                                        self.totalIngresos += granTotal;
                                    }
                                }
                            });
                        });
                        this.utilidad = this.totalIngresos - this.totalArea;
                        this.totalArea = accounting.formatMoney(this.totalArea);
                        this.totalIngresos = accounting.formatMoney(this.totalIngresos);
                        this.utilidad = accounting.formatMoney(this.utilidad);
                        this.cargando= false;
                    },
                    obtainConceptsYear: function()
                    {
                        this.getBudgetsByDistributorAndArea();
                    },
                    autorizar: function()
                    {
                        this.autorizacion.idDistributor = this.selected.distributor.idDistributor;
                        this.autorizacion.idArea = this.selected.area.idArea;
                        this.autorizacion.year = this.selected.year;

                        this.$http.post(ROOT_URL + "/budget-month-branch/authorize", JSON.stringify(this.autorizacion)).
                        success(function(data)
                        {
                            showAlert(data);
                            this.getBudgetsByDistributorAndArea();
                        }).error(function(data){
                            showAlert("Ha habido un error con la solicitud, intente nuevamente");
                        });

                    }
                    ,
                    copyBranch: function()
                    {
                        this.cargando= true;
                        this.sucursales= [];
                        var self= this;
                        this.sucursal.forEach(function (element)
                        {
                            element.forEach(function (ele)
                            {
                                ele.forEach(function (el)
                                {

                                    if (self.idBranchSelected == el.idDwEnterprise)
                                    {
                                        vm.sucursales.push(el);
                                    }
                                });
                            });
                        });
                        this.getBudgetsByDistributorAndArea();
                    }
                    ,
                    saveBudget: function(eventoconcepto)
                    {
                        this.$http.post(ROOT_URL + "/budget-month-concepts", JSON.stringify(eventoconcepto)).
                        success(function(data)
                        {
                            showAlert(data);
                            this.getBudgetsByDistributorAndArea();
                        }).error(function(){
                            showAlert("Ha habido un error con la solicitud, intente nuevamente");
                        });
                    },
                    getDistributors: function()
                    {
                        this.$http.get(ROOT_URL + "/distributors")
                                .success(function (data)
                                {
                                    this.distributors = data;
                                });
                    },
                    showModalProrrateo: function(concepto, idArea)
                    {
                        this.distributorChecked= [];
                        this.monthChecked= [];
                        this.monthsOfConcept= {};
                        this.conceptoProrrateo = concepto;
                        this.idAreaforModal= idArea;
                        this.getDistributors();
                        this.getMonthsConcept(concepto.idConcept);
                        this.fetchBudgetConceptShare(concepto.idConcept);
                        $("#prorrateo").modal("show");
                    },
                    getMonthsConcept : function(idConcept)
                    {
                        this.idConcepto = idConcept;
                        this.$http.get(ROOT_URL + "/budget-month-concepts/"+idConcept)
                                .success(function (data)
                                {
                                    this.monthsOfConcept= data;
                                });
                    },
                    fetchBudgetConceptShare: function (idConcept) {
                        this.$http.get(ROOT_URL + "/budget-concept-distributor/concept/" + idConcept).then(function (response) {
                            this.budgetConceptShare = response.data;
                        });
                    },
                    checkAllMonthsProrrateo: function () {
                        if(this.monthChecked.length == 12) {
                            this.monthChecked = [];
                        } else {
                            this.monthChecked = [];
                            this.monthChecked = this.monthsOfConcept.concat();
                        }
                    },
                    saveBudgetShare: function () {
                        var self = this;
                        var request = [];
                        this.monthChecked.forEach(function (month) {

                            var distributorShare = [];
                            self.distributorChecked.forEach(function (distributor) {

                                distributorShare.push({
                                    idBudgetMonthConcept: month.idBudgetMonthConcept,
                                    idDistributor: distributor.idDistributor,
                                    percent: parseFloat((parseFloat(distributor.percent) / 100).toFixed(4))
                                });
                            });
                            request.push(distributorShare);
                        });
                        this.$http.post(ROOT_URL + "/budget-concept-distributor", JSON.stringify(request)).then(function (response)
                        {
                            showAlert("Información almacenada correctamente");
                            this.monthChecked= [];
                            this.fetchBudgetConceptShare(this.idConcepto);
                        }, function (response) {
                            console.log(response);
                        });
                    },
                    copyBudgetOtherYear: function()
                    {
                        alert("Hola");
                    },
                    getBudgets: function () {
                        var self = this;
                        this.contenido = [];
                        if (this.selected.budgetCategory.idBudgetCategory === 0) {
                            this.contenido = this.budgets;
                            this.mixedArrays();
                        } else {
                            this.budgets.forEach(function (butget) {
                                if (butget.idBudgetCategory === self.selected.budgetCategory.idBudgetCategory) {
                                    self.contenido.push(butget);
                                }
                            });
                            this.mixedArrays();
                        }
                    }
                },
                filters: {
                    areaName: function (argument)
                    {
                        var name;
                        this.catalogoAreas.forEach(function(elemento)
                        {
                            if (argument == elemento.idArea)
                            {
                                name = elemento.areaName;
                            }
                        });
                        return name;
                    },
                    budgetCategory: function(argument)
                    {
                        var name;
                        this.catalogoRubros.forEach(function(elemento)
                        {
                            if (argument == elemento.idBudgetCategory)
                            {
                                name = elemento.budgetCategory;
                            }
                        });
                        return name;
                    },
                    BudgetSubcategory: function(argument)
                    {
                        var name;
                        this.catalogoSubRubros.forEach(function(elemento)
                        {
                            if (argument == elemento.idBudgetSubcategory)
                            {
                                name = elemento.budgetSubcategory;
                            }
                        });
                        return name;
                    },
                    DistributorFilter: function (argument)
                    {
                        var name;
                        this.catalogoDistribuidor.forEach(function(elemento)
                        {
                            if (argument == elemento.idDistributor)
                            {
                                name = elemento.distributorName;
                            }
                        });
                        return name;
                    },
                    SucursalFilter: function (argument)
                    {
                        var name;
                        this.catalogoSucursales.forEach(function(elemento)
                        {
                            if (argument == elemento.idBranch)
                            {
                                name = elemento.branchShort;
                            }
                        });
                        return name;
                    },
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
        <div id="contenidos">
            <div class="container-fluid">
                <br>
                <h2>Presupuesto</h2>
                <br>
                <div class="row">
                    <form v-on:submit.prevent="searchBudget">
                        <div class="col-md-2">
                            <label>Distribuidor</label>
                            <select v-model="selected.distributor" class="form-control" @change="getBudgetsByDistributor">
                                <option v-for="distributor in catalogoDistribuidor" :value="distributor">{{distributor.distributorName}}</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Area</label>
                            <select v-model="selected.area" class="form-control" @change="getDwEnterprisesByDistributorAndArea">
                                <option v-for="area in areas" :value="area">{{area.areaName}}</option>
                            </select>
                        </div>
                        <div class="col-md-2" v-if="select.dwEnterprises.length > 1">
                            <label>Sucursal</label>
                            <select v-model="selected.branch" class="form-control">
                                <option v-for="dwEnterprise in select.dwEnterprises" :value="dwEnterprise.branch">
                                    {{dwEnterprise.branch.branchShort}}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Año</label>
                            <input type="number" :min="currentYear" :max="maxYear" minlength="4" maxlength="4"
                                   placeholder="Año" class="form-control" v-model="selected.year">
                        </div>
                        <div class="col-md-1">
                            <label style="visibility: hidden">search</label>
                            <button class="btn btn-default">Buscar</button>
                        </div>
                    </form>
                </div>
                <div class="row" v-if="budgetCategories.length > 0">
                    <div class="col-md-2">
                        <label>Rubro</label>
                        <select v-model="selected.budgetCategory" class="form-control" @change="getBudgets">
                            <option :value="butgetAllOption">{{butgetAllOption.budgetCategory}}</option>
                            <option v-for="budgetCategory in budgetCategories" :value="budgetCategory">{{budgetCategory.budgetCategory}}</option>
                        </select>
                    </div>
                </div>

                <div class="row" v-for="sucss in select.dwEnterprises" v-if="showInfo" style="margin-left: 0px; margin-right: 0px">
                    <!--  <div class="col-xs-12"> -->
                    <div class="row" style="margin-left: 0px; margin-right: 0px">
                        <div class="col-xs-4 text-left" style="padding-left: 0">
                            <h2 style="font-weight: bold">{{sucss.idDistributor | DistributorFilter}}<small>&nbsp;{{sucss.idBranch | SucursalFilter}}</small></h2>
                        </div>
                        <div class="col-xs-8 text-right">
                            <h3>{{sucss.idArea | areaName}}</h3>
                        </div>
                    </div>
                    <hr>
                    <div class="row" v-for="cont in contenido" style="margin-left: 0px; margin-right: 0px" id="1-{{sucss.idArea}}-{{cont[0].idBudgetCategory}}">
                        <!--  <div class="col-xs-12" style="padding-left: -10px"> -->
                        <div class="bs-callout bs-callout-default">
                            <h4>{{cont[0].idBudgetCategory | budgetCategory }}</h4>
                            <div class="row" v-for="conte in cont" id="1-{{sucss.idArea}}-{{cont[0].idBudgetCategory}}-{{conte.idBudgetSubcategory}}"
                                 style="margin-left: 0px; margin-right: 0px">
                                <div class="row" style="margin-left: 0px; margin-right: 0px">
                                    <div class="col-xs-4">
                                        <h5>{{conte.idBudgetSubcategory | BudgetSubcategory }}</h5>
                                        <div class="input-group">
                                            <span class="input-group-addon">$</span>
                                            <input type="text" class="form-control" placeholder="" disabled="true" v-model=conte.granTotal>
                                        </div>
                                    </div>
                                    <div class="col-xs-1 text-left">
                                        <button type="button" class="btn btn-default" id="g-{{cont[0].idBudgetCategory}}-{{conte.idBudgetSubcategory}}"
                                                @click="seteoInfo(sucss.idDwEnterprise, conte, $event)"
                                                style="margin-top: 40px" :disabled="isAutorized">
                                            <span class="glyphicon glyphicon-plus"></span>
                                        </button>
                                    </div>
                                    <div class="col-xs-6 text left" v-if="conte.conceptos.length > 0">
                                        <button type="button" class="btn btn-default" id="s-{{cont[0].idBudgetCategory}}-{{conte.idBudgetSubcategory}}"
                                                @click="saveBudget(conte.conceptos)" style="margin-top: 40px" :disabled="isAutorized">
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
                                        <%--<div class="col-xs-1" style="padding-left: 0px; padding-right: 1px">--%>
                                            <%--<button type="button" class="btn btn-default" title="Prorrateo"--%>
                                                    <%--@click="showModalProrrateo(concepto, sucss.idArea)" v-if="concepto.idConcept>0">--%>
                                                <%--<span class="glyphicon glyphicon-align-left"></span>--%>
                                            <%--</button>--%>
                                        <%--</div>--%>
                                        <div class="col-xs-1" v-if="!isAutorized">
                                            <label style="visibility: hidden">delete</label>
                                            <button type="button" class="btn btn-default"
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
                                        <div class="col-xs-1" v-for="mes in meses"
                                             style="padding-left: 0px; padding-right: 1px">
                                            <label>
                                                {{mes}}
                                            </label>
                                        </div>
                                    </div>

                                    <div class="row" style="margin-left: 0px">
                                        <div class="col-xs-1" v-for="mess in concepto.conceptMonth"
                                             style="padding-left: 0px; padding-right: 1px">
                                            <input type="text" class="form-control input-sm" placeholder=""
                                                   id="{{mess.month}}" v-model="mess.amountConcept" @change="moneyFormat(mess, concepto, conte)"
                                                   style="font-size: 10px" onkeypress="return validateFloatKeyPress(this,event)" :disabled="isAutorized">
                                        </div>
                                    </div>
                                    <br>
                                </div>
                                <br>

                                <div class="row" style="margin-left: 0px" v-if="conte.conceptos.length > 0">
                                    <div class="col-xs-2 text-right">
                                    </div>

                                    <div class="col-xs-9">
                                        <div class="col-xs-1 text-center" v-for="totalmes in conte.totalMonth"
                                             style="padding-left: 0px; padding-right: 1px">
                                        </div>
                                    </div>

                                    <div class="col-xs-1 text-left" style="padding-left: 0px; padding-right: 1px">
                                    </div>
                                </div>

                            </div>
                        </div>
                        <!--  </div> -->
                        <!--  </div> -->
                    </div>
                    <div class="row">
                        <div class="col-xs-12 text-center">
                            <button class="btn btn-success" @click="autorizar">Autorizar</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="prorrateo" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="">Prorrateo</h4>
                        </div>
                        <div class="modal-body">
                            <ul id="tabs" class="nav nav-tabs" data-tabs="tabs" role="tablist">
                                <li class="active" role="presentation">
                                    <a href="#tab-save" role="tab" data-toggle="tab">Creación / Modificación</a>
                                </li>
                                <li role="presentation">
                                    <a href="#tab-view" role="tab" data-toggle="tab">Consultar actual</a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div id="tab-save" class="tab-pane fade in active" role="tabpanel">
                                    <div class="row">
                                        <div class="col-xs-4">
                                            <label>Área</label>
                                            <input class="form-control" disabled="true" value="{{idAreaforModal | areaName}}">
                                        </div>
                                        <div class="col-xs-4">
                                            <label>Concepto</label>
                                            <input class="form-control" disabled="true" v-model="conceptoProrrateo.conceptName">
                                        </div>
                                        <div class="col-xs-4">
                                            <label>Monto Anual</label>
                                            <input class="form-control" disabled="true" v-model="conceptoProrrateo.total">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <label>Empresas</label>
                                            <div class="checkbox" v-for="distributor in distributors" v-if="distributor.budgetShare">
                                                <label>
                                                    <input type="checkbox" value="{{distributor}}" v-model="distributorChecked" >
                                                    <span v-if="distributor.budgetShare">{{distributor.distributorName}}</span>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-xs-4" v-if="distributorChecked.length>0">
                                            <label>Período</label>
                                            <div class="row">
                                                <div class="col-xs-3" v-for="meses in monthsOfConcept">
                                                    <div class="btn-group" style="margin-bottom: 5px">
                                                        <label class="btn btn-default" style="width: 60px">
                                                            <input type="checkbox" value="{{meses}}" v-model="monthChecked">
                                                            {{meses.budgetMonthBranch.month.month | shortName}}
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-xs-12 text-left">
                                                    <label @click.prevent="checkAllMonthsProrrateo">
                                                        <input :checked="monthChecked.length == 12" type="checkbox">
                                                        Seleccionar todos
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-5" v-if="distributorChecked.length>0 && monthChecked.length > 0 ">
                                            <label>Porcentajes</label>
                                            <div class="row" v-for="distributor in distributorChecked">
                                                <div class="col-xs-7">
                                                    <label>{{distributor.distributorName}}</label>
                                                </div>
                                                <div class="col-xs-5">
                                                    <div class="input-group">
                                                        <span class="input-group-addon">%</span>
                                                        <input v-model="distributor.percent" type="text" class="form-control">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-xs-12 text-left">
                                                    <label> %  Total:  {{totalPorcentaje}}</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="tab-view" class="tab-pane fade horizontal-scroll">
                                    <table class="table">
                                        <tr v-for="(index, monthShare) in budgetConceptShare
                                                | orderBy '[0].budgetMonthConcept.budgetMonthBranch.idMonth'">
                                            <td>{{ monthShare[0].budgetMonthConcept.budgetMonthBranch.month.month }}</td>
                                            <td v-for="distributorShare in monthShare | orderBy 'idDistributor'">
                                                <small>{{ distributorShare.distributor.acronyms }}</small>
                                                <p>{{ (distributorShare.percent * 100).toFixed(2) }} % : $ {{ distributorShare.amount }}</p>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                                <button @click="saveBudgetShare" type="button"
                                        class="btn btn-success" v-if="distributorChecked.length>0 && monthChecked.length > 0 && (totalPorcentaje == 100 ) ">Guardar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div> <!-- #contenidos -->
    </jsp:body>
</t:template>
