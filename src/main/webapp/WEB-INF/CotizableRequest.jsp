<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />


<t:template pageTitle="BID Group: Solicitud">
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

            //thanks: http://javascript.nwbox.com/cursor_position/
            function getSelectionStart(o) {
                if (o.createTextRange) {
                    var r = document.selection.createRange().duplicate()
                    r.moveEnd('character', o.value.length)
                    if (r.text == '') return o.value.length
                    return o.value.lastIndexOf(r.text)
                } else return o.selectionStart
            }
        </script>

        <script type="text/javascript">
            var vm= new Vue({
                el: '#contenidos',
                created: function(){

                },
                ready: function ()
                {
                    this.getUserInSession();
                    this.getCostCenter();


                },
                data:
                {
                    RequestCategory: ${cat},
                    idRequest: ${idRequest},
                    Rubros: {},
                    Productos: [],
                    obtainRequestInformation:
                    {
                        idBudgetCategory: '',
                        idAccountingAccount: '',
                        idProductType: '',
                        idUserResponsable: '',
                        applyingDate: ''
                    },
                    tipoProducto: {},
                    userInSession: {},
                    userRequest: '',
                    costCenters: {},
                    costCenter: '',
                    budgetsYearConcept: '',
                    producto: '',
                    productosSeleccionados: [],
                    objectRequest: {
                        request: {
                            idRequest: '',
                            folio: '',
                            description: '',
                            purpose: '',
                            creationDate: '',
                            applyingDate: '',
                            userRequest: '',
                            userResponsible: '',
                            budgetYear: '',
                            month: '',
                            requestStatus: '',
                            idUserResponsable: '',
                            idBudget: ''
                        },
                        products: []
                    }
                },
                methods:
                {
                    getRequestTypeProducts: function(){
                        this.$http.get(ROOT_URL+"/budgets/cost-center/"+ this.costCenter)
                                .success(function (data)
                                {
                                    this.Rubros= data;
                                });
                    },
                    getProductType: function(){
                        this.Productos= [];
                        var self = this;
                        this.Rubros.forEach(function(element){
                            if (element.accountingAccounts.idBudgetCategory == self.obtainRequestInformation.idBudgetCategory ) {
                                self.Productos.push(element);
                            }
                        });
                    },
                    getProducts: function(){
                        this.tipoProducto= {};
                        this.$http.get(ROOT_URL+"/products/product-type/"+this.obtainRequestInformation.idAccountingAccount)
                                .success(function (data)
                                {
                                    this.tipoProducto= data;
                                });
                        //this.obtainSuppliers(this.obtainRequestInformation.idProductType);
                    },
                    saveProduct: function(){
                        var productos = JSON.parse(JSON.stringify(this.producto));
                        this.productosSeleccionados.push(productos);
                    },
                    getUserInSession: function(){
                        this.$http.get(ROOT_URL + "/user").
                        success(function (data)
                        {
                            this.userInSession = data;
                            this.userRequest = data.dwEmployee.employee.fullName;
                            this.getCostCenter();

                        }).error(function(data)
                        {
                            showAlert("Ha habido un error al obtener al usuario en sesion");
                        });

                    },
                    getCostCenter: function(){

                        var idRole = this.userInSession.dwEmployee.idRole;
                        this.$http.get(ROOT_URL + "/roles-cost-center/role/"+ idRole).
                        success(function (data)
                        {
                            this.costCenters = data;
                        }).error(function(data)
                        {
                            showAlert("Ha habido un error los centros de costos asociados a los usuarios");
                        });
                    },
                    getAmountOfBudget: function(){
                        this.$http.get(ROOT_URL+"/budget-year-concept/find/"+ this.costCenter+ "/"+ this.obtainRequestInformation.idAccountingAccount)
                                .success(function (data)
                                {
                                    if (data !== null) {
                                        this.budgetsYearConcept = data;
                                        this.getProducts();
                                    }
                                    else{
                                        showAlert("No existe presupuesto para esta solicitud");
                                        this.obtainRequestInformation.idAccountingAccount= '';
                                    }
                                });
                    },
                    deleteProduct: function(product){
                        this.productosSeleccionados.$remove(product);
                    },
                    saveRequest: function(){
                        this.objectRequest.products = this.productosSeleccionados;
                        this.objectRequest.request.idUserResponsable = this.userInSession.idUser;
                        this.objectRequest.request.idBudget = this.budgetsYearConcept.idBudget;

                        this.$http.post(ROOT_URL+"/requests", JSON.stringify(this.objectRequest))
                                .success(function (data)
                                {
                                    showAlert("Registro de solicitud exitoso");
                                });
                    }
                },
                filters: {

                }
            });

        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos">

            <div class="container-fluid">

                <form v-on:submit.prevent="saveRequest">
                    <div class="row">
                        <div class="col-xs-4">
                            <h1>Registro de Solicitud</h1>
                        </div>
                        <div class="col-xs-4 col-xs-offset-4">
                            <label>
                                Solicitante
                            </label>
                            <input class="form-control" type="text" name="name" value="" disabled="true" v-model="userRequest">
                        </div>
                    </div>
                    <br>
                    <div class="row">

                        <div class="col-xs-3">
                            <label>
                                Centro de Costos
                            </label>
                            <select class="form-control" v-model="costCenter" @change="getRequestTypeProducts" :disabled="productosSeleccionados.length > 0">
                                <option></option>
                                <option v-for="costCenter in costCenters" value="{{costCenter.costCenter.idCostCenter}}" >
                                    {{costCenter.costCenter.name}}
                                </option>
                            </select>

                        </div>

                        <div class="col-xs-3">
                            <label>
                                Rubro
                            </label>
                            <select class="form-control" v-model="obtainRequestInformation.idBudgetCategory" :disabled="productosSeleccionados.length > 0 || isUpdate" @change="getProductType" required>
                                <option v-for="Rubro in Rubros"
                                        value="{{Rubro.accountingAccounts.idBudgetCategory}}">{{Rubro.accountingAccounts.budgetCategory.budgetCategory}}
                                </option>
                            </select>
                        </div>

                        <div class="col-xs-3">
                            <label>
                                Producto
                            </label>
                            <select class="form-control" v-model="obtainRequestInformation.idAccountingAccount" :disabled="productosSeleccionados.length > 0 || isUpdate"
                                    @change="getAmountOfBudget" id="productTypesin" required>
                                <option></option>
                                <option v-for="Producto in Productos" value="{{Producto.idAccountingAccount}}">
                                    {{Producto.accountingAccounts.budgetSubcategory.budgetSubcategory}}
                                </option>
                            </select>
                        </div>

                        <div class="col-xs-2">
                            <label>
                                Tipo de Producto
                            </label>
                            <select class="form-control" v-model="producto" :disabled="isUpdate" required>
                                <option></option>
                                <option v-for="Product in tipoProducto" value="{{Product}}">
                                    {{Product.product}}
                                </option>
                            </select>
                        </div>

                        <div class="col-xs-1">
                            <div class="col-xs-6">
                                <button type="button" class="btn btn-default" style="margin-top: 25px; margin-left: -33px"
                                        v-on:click="saveProduct" :disabled="isUpdate" data-toggle="tooltip" data-placement="top" title="Agregar Producto">
                                    <span class="glyphicon glyphicon-plus"></span>
                                </button>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-xs-4">
                            <label>
                                Productos Seleccionados
                            </label>
                            <table class="table table-striped">
                                <thead>
                                <th>
                                    Concepto
                                </th>
                                <th>

                                </th>
                                <tbody>
                                <tr v-for="product in productosSeleccionados">
                                    <td>
                                        {{product.product}}
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-danger" @click="deleteProduct(product)">
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </button>
                                    </td>
                                </tr>
                                </tbody>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-xs-12">
                            <label>
                                Descripción de la Solicitud
                            </label>
                            <textarea class="form-control" rows="3" cols="50" v-model="objectRequest.request.description"
                                      :disabled="isUpdate" required></textarea>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-xs-12">
                            <label>
                                Justificación de la Solicitud
                            </label>
                            <textarea class="form-control" rows="3" cols="50" v-model="objectRequest.request.purpose"
                                      :disabled="isUpdate" required></textarea>
                        </div>
                    </div>

                    <br>
                    <div class="row">
                        <div class="col-md-2 col-md-offset-10 text-right">
                            <button class="btn btn-success" :disabled="productosSeleccionados.length < 1 || isSavingNow">Guardar</button>
                        </div>
                        <div class="col-md-4 col-md-offset-8 text-right" v-if="!desaparecer">
                            <button type="button" class="btn btn-default" @click="newCotizacion"
                                    v-if="objectRequest.request.isSaved || isUpdate ">Agregar Cotización
                            </button>
                            <button type="button" class="btn btn-default" @click="exit">Enviar</button>
                            <button type="button" class="btn btn-default" @click="exit">Salir</button>
                        </div>
                    </div>

                </form>
                <br>
                <div class="row" v-for="cotizacion in estimations | orderBy 'idEstimationStatus'">
                    <form v-on:submit.prevent="saveEstimations(cotizacion)" id="form-{{cotizacion.indexOfForm}}">
                        <div class="col-xs-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <div class="row">
                                        <div href="#collapse{{cotizacion.indexOfForm}}">
                                            <div class="col-xs-4 text-left">
                                                <div class="col-xs-4">
                                                    <h3 class="panel-title"> Cotización
                                                    </h3>
                                                </div>
                                                <div class="col-xs-8">
                                                    <h4 class="panel-title" v-if="cotizacion.idCurrency> 0">Monto MXN: {{cotizacion.amount * cotizacion.rate | filterMoney}} <br><span v-if="cotizacion.idCurrency != 1"> Monto en {{cotizacion.idCurrency | filterCurrency}}: {{cotizacion.amount | filterMoney}}</span></h4>
                                                </div>
                                            </div>
                                            <div class="col-xs-2" >
                                                <span class="label label-danger" v-if="cotizacion.outOfBudget == 1">Fuera de Presupuesto</span>
                                            </div>
                                            <div class="col-xs-2 text-right">
                                                <label class="label label-primary" v-if="cotizacion.idEstimationStatus== 2">Cotización Propuesta</label>
                                            </div>
                                        </div>
                                        <div>
                                            <div class="col-xs-4 text-right">
                                                <button type="submit" class="btn btn-sm btn-default" v-if="cotizacion.idEstimation == 0"
                                                        :disabled="isSavingNow" data-toggle="tooltip" data-placement="bottom" title="Guardar Cotización">
                                                    <span class="glyphicon glyphicon-floppy-disk"></span>
                                                </button>
                                                <button v-if="cotizacion.idEstimation > 0" type="button" class="btn btn-sm btn-default"
                                                        @click="deleteCotizacion(cotizacion)" :disabled="isSavingNow" data-toggle="tooltip"
                                                        data-placement="bottom" title="Eliminar cotización">
                                                    <span class="glyphicon glyphicon-trash"></span>
                                                </button>
                                                <button v-if="cotizacion.idEstimation == ''" type="button" class="btn btn-sm btn-default"
                                                        @click="deleteCotizacion(cotizacion)" :disabled="isSavingNow" data-toggle="tooltip"
                                                        data-placement="bottom" title="Cancelar">
                                                    <span class="glyphicon glyphicon-remove"></span>
                                                </button>
                                                <button v-if="cotizacion.idEstimationStatus > 0 && cotizacion.idEstimationStatus < 2 && cotizacion.isCollapsed == true"
                                                        type="submit" class="btn btn-sm btn-default" :disabled="isSavingNow" data-toggle="tooltip"
                                                        data-placement="bottom" title="Modificar Cotización">
                                                    <span class="glyphicon glyphicon-pencil"></span>
                                                </button>
                                                <button href="#collapse{{cotizacion.indexOfForm}}" @click="setIsCollapsed(cotizacion)"
                                                        data-toggle="collapse" class="btn btn-sm btn-default" type="button">
                        <span :class="{ 'glyphicon-menu-down': ! cotizacion.isCollapsed, 'glyphicon-menu-up': cotizacion.isCollapsed }"
                              class="glyphicon" ></span>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="collapse{{cotizacion.indexOfForm}}" class="panel-body collapse"
                                     :class="{ 'in' : cotizacion.expanded || cotizacion.idEstimationStatus == 2  }">
                                    <div class="row">
                                        <div class="col-xs-2">
                                            <label>
                                                Proveedor
                                            </label>
                                            <select class="form-control" v-model="cotizacion.idSupplier"
                                                    @change="obtainAccounts(cotizacion)" required="true" :disabled="cotizacion.idEstimationStatus > 1">
                                                <option></option>
                                                <option v-for="supplier in suppliers" value="{{supplier.providers.idProvider}}">
                                                    {{supplier.providers.providerName | separate}}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-xs-2">
                                            <label>
                                                Cuenta Bancaria
                                            </label>
                                            <select class="form-control" v-model="cotizacion.idAccount" required="true"
                                                    :disabled="cotizacion.idEstimationStatus > 1">
                                                <option></option>
                                                <option v-for="accounts in cotizacion.accountSupplier" value="{{accounts.idAccount}}">
                                                    {{accounts.account.accountNumber}}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-xs-2">
                                            <label>
                                                Tipo de Moneda
                                            </label>
                                            <select class="form-control" v-model="cotizacion.idCurrency" required="true"
                                                    @change="validateCurrency(cotizacion)" :disabled="cotizacion.idEstimationStatus > 1">
                                                <option></option>
                                                <option v-for="curr in currencies" value="{{curr.idCurrency}}">
                                                    {{curr.currency}}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-xs-2">
                                            <label>
                                                Monto
                                            </label>
                                            <div class="input-group">
                                                <span class="input-group-addon">$</span>
                                                <input number class="form-control" placeholder="" v-model="cotizacion.amount"
                                                       @change="validateAmount(cotizacion)" required="true" onkeypress="return validateFloatKeyPress(this,event)"
                                                       :disabled="cotizacion.idEstimationStatus > 1">
                                            </div>
                                        </div>
                                        <div class="col-xs-2">
                                            <label>
                                                Tipo de Cambio
                                            </label>
                                            <div class="input-group">
                                                <span class="input-group-addon">$</span>
                                                <input number class="form-control" :disabled="flagrate  || cotizacion.idEstimationStatus > 1"
                                                       v-model="cotizacion.rate" @change="validateRate(cotizacion)"
                                                       onkeypress="return validateFloatKeyPress(this,event)" required="true">
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-xs-4">
                                            <label>
                                                Archivo de la Cotización
                                            </label>
                                            <input type="file" name="file" class="form-control" :disabled="cotizacion.idEstimationStatus > 1"
                                                   v-model="cotizacion.fileName" required="{{cotizacion.requiredFile}}"
                                                   accept="application/pdf">
                                        </div>
                                        <div class="col-xs-2">
                                            <label>
                                                Archivo Actual
                                            </label>
                                            <p>
                                                {{cotizacion.fileNameActual}}
                                            </p>
                                        </div>
                                        <div class="col-xs-1" v-if="cotizacion.idEstimation > 0">
                                            <p style="margin-top: 25px">
                                                <a :href="attachment + cotizacion.idEstimation" class="btn btn-default" title="Descargar">
                                                    <span class="glyphicon glyphicon-download" style="font-size: 17px"></span>
                                                </a>
                                            </p>
                                        </div>
                                        <div class="col-xs-3">
                                            <button type="button" class="btn btn-default" @click="prepareModalPeriodicPayment(cotizacion)"
                                                    style="margin-top: 25px" v-if="cotizacion.idEstimationStatus== 2">Agregar Informacion de Pago
                                            </button>
                                        </div>
                                        <div class="col-xs-2 text-right">
                                            <button type="button" class="btn btn-default" name="button"
                                                    v-if="cotizacion.idEstimationStatus== 1" style="margin-top:25px"
                                                    @click="autorizarCotizacion(cotizacion)">
                                                Proponer cotización
                                            </button>
                                            <button type="button" class="btn btn-default" name="button"
                                                    v-if="cotizacion.idEstimationStatus== 2 && isAutoriced" style="margin-top:25px"
                                                    @click="cancelarAutorizacion(cotizacion)">
                                                Rechazar
                                            </button>
                                            <button type="button" class="btn btn-default" name="button"
                                                    v-if="!(isAutoriced)" style="margin-top:25px"
                                                    @click="autorizarCotizacion(cotizacion)">
                                                Autorizar
                                            </button>

                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <label>
                            Autorizaciones de la Solicitud.
                        </label>
                        <table class="table table-striped">
                            <thead>
                            <th>
                                Nombre
                            </th>
                            <th>
                                Estatus
                            </th>
                            <th>
                                Autorizar
                            </th>
                            <th>
                                Detalles
                            </th>
                            </thead>
                            <tbody>
                            <tr v-for="info in infoAutorization.authorizations">
                                <td>
                                    {{info.users.dwEmployee.employee.fullName}}
                                </td>
                                <td>
                                    <span class="label label-success" v-if="info.idAuthorizationStatus == 2">Autorizado</span>
                                    <span class="label label-warning" v-if="info.idAuthorizationStatus == 1">Pendiente</span>
                                    <span class="label label-danger" v-if="info.idAuthorizationStatus == 3">Rechazado</span>
                                </td>
                                <td>
                                    <button type="button" class="btn btn-success btn-sm" name="button" @click="autorizarSolicitudIndividual(info)"
                                            v-if="info.idAuthorizationStatus == 1 & info.idUser == userInSession.idUser">Autorizar</button>
                                    <button type="button" class="btn btn-danger btn-sm" name="button" @click="rechazarSolicitudIndividual(info)"
                                            v-if="info.idAuthorizationStatus == 1 & info.idUser == userInSession.idUser">Rechazar</button>

                                </td>
                                <td>
                        <textarea name="name" rows="3" cols="40" v-model="info.details" v-if="info.idAuthorizationStatus == 1">

                        </textarea>
                                    <textarea name="name" rows="3" cols="40" v-model="info.details | filterNull"
                                              v-if="info.idAuthorizationStatus == 3 || info.idAuthorizationStatus == 2" disabled="true" >

                        </textarea>
                                </td>
                            </tr>
                            </tbody>

                        </table>

                    </div>
                </div>
            </div>
            <div class="modal fade" id="periodicPayment" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Introduzca el esquema de Pagos</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>
                                        Monto de la Cotizacion:
                                    </label>
                                    <div class="input-group">
                                        <span class="input-group-addon">$</span>
                                        <input type="text" class="form-control" placeholder="" v-model="periodicPayment.amount"
                                               disabled="true">
                                    </div>
                                </div>

                                <div class="col-xs-4">
                                    <label>
                                        Tipo de Moneda
                                    </label>
                                    <select class="form-control" v-model="periodicPayment.idCurrency" disabled="true">
                                        <option></option>
                                        <option v-for="curr in currencies" value="{{curr.idCurrency}}">
                                            {{curr.currency}}
                                        </option>
                                    </select>
                                </div>

                                <div class="col-xs-4">
                                    <label>
                                        Tipo de Cambio
                                    </label>
                                    <div class="input-group">
                                        <span class="input-group-addon">$</span>
                                        <input number class="form-control" placeholder="" v-model="periodicPayment.rate"
                                               disabled="true">
                                    </div>
                                </div>

                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-12">
                                    <input type="radio" id="pagoFijo" value="1" v-model="optionPago" @change="emptyEsquema(optionPago)">
                                    <label>Pagos Fijos</label>
                                    <input type="radio" id="pagoVariable" value="2" v-model="optionPago" @change="emptyEsquema(optionPago)">
                                    <label>Pagos Variables</label>
                                    <input type="radio" id="pagounico" value="3" v-model="optionPago" @change="emptyEsquema(optionPago)">
                                    <label>Pago Único</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12" v-if="optionPago==1">
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <label>
                                                Numero de Pagos
                                            </label>
                                            <div class="input-group">
                                                <span class="input-group-addon">#</span>
                                                <input number class="form-control" placeholder="" v-model="pagofijo.totalPayments">
                                            </div>
                                        </div>
                                        <div class="col-xs-3">
                                            <label>
                                                Monto
                                            </label>
                                            <div class="input-group has-success">
                                                <span class="input-group-addon">$</span>
                                                <input number class="form-control" placeholder="" v-model="pagofijo.amount">
                                            </div>
                                        </div>
                                        <div class="col-xs-3">
                                            <label>
                                                Periodicidad
                                            </label>
                                            <select class="form-control" name="" v-model="pagofijo.idPeriod">
                                                <option></option>
                                                <option v-for="periodo in Periods" value="{{periodo.idPeriod}}">
                                                    {{ periodo.period}}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-xs-3">
                                            <label>
                                                Fecha Inicial de pago
                                            </label>
                                            <div class="form-group">
                                                <div class='input-group date' id='datetimepicker1' @click= "activarTimePickerPagosFijos">
                                                    <input type='text' class="form-control"
                                                           v-model="pagofijo.initialDateView">
                                                    <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                </div>
                                <div class="col-xs-8" v-if="optionPago==2">
                                    <div class="col-xs-6">
                                        <label>
                                            Monto de Pago
                                        </label>
                                        <div class="input-group">
                                            <span class="input-group-addon">$</span>
                                            <input number class="form-control" placeholder="" v-model="amountOfPay">
                                        </div>
                                    </div>
                                    <div class="col-xs-6">
                                        <button type="button" class="btn btn-default" name="button"
                                                style="margin-top: 25px" @click="generarPagosVariables" data-toggle="tooltip" data-placement="top" title="Agregar Monto">
                                            <span class="glyphicon glyphicon-plus"></span>
                                        </button>
                                    </div>
                                </div>

                                <div class="col-xs-12" v-if="optionPago==3">

                                    <div class="col-xs-4">
                                        <label>
                                            Monto del Pago
                                        </label>
                                        <input number class="form-control" placeholder="" v-model="accountPayableUnico.amount" disabled="true">
                                    </div>

                                    <div class="col-xs-4">
                                        <label>
                                            Fecha de Vencimiento
                                        </label>
                                        <div class='input-group date' id='datetimepick' >
                                            <input type='text' class="form-control" v-model="accountPayableUnico.dueDate">
                                            <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                                        </div>
                                    </div>


                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-7" v-if="AccountsPayables.length> 0 && optionPago !=3">
                                    <label>
                                        Informacion de Pagos
                                    </label>

                                    <div class="row">
                                        <div class="col-xs-4">
                                            <label>
                                                Monto de Pago
                                            </label>
                                        </div>
                                        <div class="col-xs-4">
                                            <label>
                                                Fecha de Vencimiento
                                            </label>
                                        </div>
                                        <div class="col-xs-2">

                                        </div>
                                    </div>

                                    <div class="row" v-for="ap in AccountsPayables">
                                        <div class="col-xs-4">
                                            <div class="input-group">
                                                <span class="input-group-addon">$</span>
                                                <input number class="form-control"
                                                       v-model="ap.amount" disabled="true">
                                            </div>
                                        </div>
                                        <div class="col-xs-4">
                                            <label>
                                                <div class="form-group">
                                                    <div class='input-group date' id='datetimepickerEsquema{{ap.idDatePicker}}'>
                                                        <input type='text' class="form-control"
                                                               @change="dateValidation(ap.dueDate,ap.idDatePicker)"
                                                               @click="activarTimePicker(ap.idDatePicker,ap.dueDate)"
                                                               v-model="ap.dueDate">
                                                        <span class="input-group-addon">
                                      <span class="glyphicon glyphicon-calendar"></span>
                                  </span>
                                                    </div>
                                                </div>
                                            </label>
                                        </div>
                                        <div class="col-xs-2">

                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-3 text-left">
                                            <b>Total a Pagar:</b>
                                        </div>
                                        <div class="col-xs-9 text-left">
                                            <b>$ {{totalApagar}}</b>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-xs-5" v-if="showAsignacionAnterior">
                                    <label>
                                        Asignacion Anterior
                                    </label>
                                    <table class="table table-striped">
                                        <thead>
                                        <th>
                                            #
                                        </th>
                                        <th>
                                            Monto de Pago
                                        </th>
                                        <th>
                                            Fecha de Vencimiento
                                        </th>
                                        </thead>
                                        <tbody>
                                        <tr v-for="accounts in AccountsPayablesInfo">
                                            <td>
                                                {{$index + 1}}
                                            </td>
                                            <td>
                                                {{accounts.amount}}
                                            </td>
                                            <td>
                                                {{accounts.dueDate}}
                                            </td>

                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 text-right">
                                    <button type="button" class="btn btn-success" @click="saveAccountPayable(optionPago)" :disabled="isSavingNow">
                                        Guardar
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="auth-confirmation-modal" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">¿Confirma que desea realizar esta acción?</h4>
                        </div>
                        <div class="modal-body">
                            <p></p>
                        </div>
                        <div class="modal-footer">
                            <button @click="commitAuthorization(authModal.authorization, authModal.authorize)" class="btn btn-default">Aceptar</button>
                            <button class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div> <!-- container-fluid -->
        </div> <!-- #contenidos -->
        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
