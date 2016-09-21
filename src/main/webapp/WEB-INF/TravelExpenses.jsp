<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 05/07/16
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Gastos de viaje">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>
        <script type="text/javascript">
            var vm= new Vue({
                el: '#content',
                ready: function () {
                    this.getUserInSession();
                    this.activateDateTimePickerStart();
                    this.getConcepts();
                },
                data: {
                    errorData:{},
                    userInSession:{},
                    userIdRole:'',
                    employeeAccount:{},
                    roleTravelTypeList:[],
                    concepts:[],
                    selected:{
                        travelType:null
                    },
                    requestBody:{
                        request:
                        {
                            purpose: ''
                        },
                        travelExpense:{
                            startDate:'',
                            endDate:'',
                            destination:'',
                            estimatedKm:'',
                            travelType:null
                        },
                        requestConceptList:[],
                        currency:{},
                        idDwEnterprise:''
                    },
                    dateDifference : 0,
                    roleConceptList:[],
                    authorizedAmount:{
                        hospedaje:0,
                        alimentos:0,
                        transporte:0
                    },
                    casetas : ''
                },
                computed : {
                    casetasAmount : function () {
                        var amount = 0;

                        if (this.casetas != '') {
                            amount = parseFloat(this.casetas);
                        } else {
                            amount = 0;
                        }

                        return amount;
                    },
                    hospedajeAmount: function () {
                        var amount = 0;

                        if (this.dateDifference > 1) {
                            amount = this.authorizedAmount.hospedaje * this.dateDifference;
                        } else {
                            amount = this.authorizedAmount.hospedaje;
                        }

                        return amount;
                    },
                    alimentosAmount: function () {
                        var amount = 0;

                        if (this.dateDifference > 1) {
                            amount = this.authorizedAmount.alimentos * this.dateDifference;
                        } else {
                            amount = this.authorizedAmount.alimentos;
                        }

                        return amount;
                    },
                    transporteAmount: function () {
                        var amount = 0;

                        if (this.dateDifference > 1) {
                            amount = this.authorizedAmount.transporte * this.dateDifference;
                        } else {
                            amount = this.authorizedAmount.transporte;
                        }

                        return amount;
                    },
                    gasolinaAmount: function () {
                        var amount = 0;

                        if (this.requestBody.travelExpense.estimatedKm != '') {
                            amount = parseFloat(this.requestBody.travelExpense.estimatedKm) * 1.5;
                        } else {
                            amount = 0;
                        }

                        return amount;
                    },
                    totalTravelExpenses: function() {
                        var total;
                        total = this.hospedajeAmount + this.alimentosAmount +
                                this.transporteAmount + this.gasolinaAmount + this.casetasAmount;
                        return (isNaN(total)) ? 0 : total;
                    }
                },
                methods: {
                    getConcepts : function () {
                        this.$http.get(ROOT_URL + '/budget-concepts?category=3')
                                .success(function (data) {
                                    this.concepts = data;
                                })
                                .error(function (data) {

                                });
                    },
                    getUserInSession: function()
                    {
                        this.$http.get(ROOT_URL + "/user").
                        success(function (data) {
                            this.userInSession = data;
                            this.userIdRole = this.userInSession.dwEmployee.role.idRole;
                            this.requestBody.request.userRequest = this.userInSession;
                            this.requestBody.idDwEnterprise = this.userInSession.dwEmployee.idDwEnterprise;
                            this.getTravelTypes(this.userIdRole);

                            if (this.userInSession.dwEmployee.employee.employeesAccountsList.length > 0) {
                                this.employeeAccount = this.userInSession.dwEmployee.employee.employeesAccountsList[0];
                            }

                        }).error(function(data) {
                            showAlert("Ha habido un error al obtener al usuario en sesion");
                        });

                    },
                    getTravelTypes : function (idRole) {
                        this.$http.get(ROOT_URL + "/role-travel-type/role/" + idRole)
                                .success(function (data) {
                                    this.roleTravelTypeList = data;
                                })
                                .error(function (data) {

                                });
                    },
                    getRoleConcepts : function () {
                        this.$http.get(ROOT_URL + "/role-concept/role/" + this.userIdRole + "/travel-type/" + this.selected.travelType.idTravelType)
                                .success(function (data) {
                                    var self = this;

                                    if (data.length > 0) {
                                        this.roleConceptList = data;
                                        this.requestBody.currency = this.roleConceptList[0].currency;
                                        this.roleConceptList.forEach(function (roleConcept) {
                                            if (roleConcept.idBudgetConcept == 1) {
                                                self.authorizedAmount.hospedaje = roleConcept.authorizedAmount;
                                            }
                                            if (roleConcept.idBudgetConcept == 2) {
                                                self.authorizedAmount.alimentos = roleConcept.authorizedAmount;
                                            }
                                            if (roleConcept.idBudgetConcept == 3) {
                                                self.authorizedAmount.transporte = roleConcept.authorizedAmount;
                                            }
                                        });
                                    }
                                })
                                .error(function (data) {

                                });
                    },
                    onDateChanged:function () {
                        var startDate;
                        var endDate;

                        var startDateISOString = this.dateTimePickerStart.DateTimePicker.date().toISOString();
                        var endDateISOString = this.dateTimePickerEnd.DateTimePicker.date().toISOString();

                        startDate = new Date(startDateISOString);
                        endDate = new Date(endDateISOString);

                        if (startDate instanceof Date && endDate instanceof Date) {
                            var timeDiff = Math.abs(startDate.getTime() - endDate.getTime());
                            this.dateDifference = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;
                        }
                    },
                    activateDateTimePickerStart: function () {

                        var currentDate = new Date();

                        this.dateTimePickerStart = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: currentDate
                        }).data();

                        var self = this;

                        $('#startDate').on('dp.change', function (e) {
                            self.onDateChanged();
                        });

                    },
                    activateDateTimePickerEnd: function(startDate){

                        var minDate= moment(startDate, 'DD-MM-YYYY')
                                .format('YYYY-MM-DD');

                        this.dateTimePickerEnd = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: minDate
                        }).data();

                        var self = this;

                        $('#endDate').on('dp.change', function (e) {
                            self.onDateChanged();
                        });
                    },
                    destroyDateTimePickerStart: function(){
                        $('#startDate').on('dp.change', function (e) {
                            $('#endDate').data('DateTimePicker').minDate(e.date);
                        });

                        $('#endDate').on('dp.change', function (e) {
                            $('#startDate').data('DateTimePicker').maxDate(e.date);
                        });
                    },
                    clearRequestValues : function () {
                        this.requestBody.request.purpose = '';
                        this.requestBody.travelExpense.startDate = '';
                        this.requestBody.travelExpense.endDate = '';
                        this.requestBody.travelExpense.destination = '';
                        this.requestBody.travelExpense.estimatedKm = '';
                        this.requestBody.travelExpense.travelType = null;
                        this.requestBody.requestConceptList = [];
                        this.requestBody.currency = [];
                        this.casetas = '';
                        this.authorizedAmount.hospedaje = 0;
                        this.authorizedAmount.alimentos = 0;
                        this.authorizedAmount.transporte = 0;
                        this.dateDifference = 0;
                        this.selected.travelType = null;
                    },
                    saveRequest: function () {

                        this.setRequestBody();

                        if (!this.requestValidation()) {
                            return;
                        }

                        this.$http.post(ROOT_URL + '/travel-expenses',this.requestBody)
                                .success(function (data) {
                                    this.clearRequestValues();
                                    showAlert("Se ha guardado la solicitud");
                                })
                                .error(function (data) {
                                    this.errorData = data;
                                    this.clearRequestValues();
                                    showAlert(this.errorData.error.message,{type:3});
                                });
                    },
                    requestValidation:function () {

                        if (this.requestBody.travelExpense.travelType === null) {
                            showAlert('Seleccione un tipo de viaje', {type:3});
                            return false;
                        }

                        if (this.requestBody.request.purpose === '') {
                            showAlert('El campo "Motivo de viaje" es requerido', {type:3});
                            return false;
                        }

                        if (this.requestBody.travelExpense.startDate === '') {
                            showAlert('Seleccione una fecha de inicial', {type:3});
                            return false;
                        }

                        if (this.requestBody.travelExpense.endDate === '') {
                            showAlert('Seleccione una fecha de final', {type:3});
                            return false;
                        }

                        if (this.requestBody.travelExpense.destination === '') {
                            showAlert('El campo "Destino" es requerido', {type:3});
                            return false;
                        }

                        if (this.requestBody.travelExpense.estimatedKm === '') {
                            showAlert('El campo "KM es estimados" requerido', {type:3});
                            return false;
                        }

                        if (this.casetas === '') {
                            showAlert('El campo "Casetas" es requerido', {type:3});
                            return false;
                        }

                        return true;
                    },
                    setRequestBody: function () {
                        
                        var self = this;

                        this.requestBody.travelExpense.travelType = this.selected.travelType;
                        this.concepts.forEach(function (concept) {

                            var requestConcept = {
                                concept:concept,
                                amount:0
                            };

                            switch (concept.budgetConcept) {
                                case 'HOSPEDAJE':
                                        if (self.dateDifference > 1 || self.dateDifference === 0) {
                                            requestConcept.amount = self.hospedajeAmount;
                                            self.requestBody.requestConceptList.push(requestConcept);
                                        }

                                    break;
                                case 'ALIMENTOS':
                                    requestConcept.amount = self.alimentosAmount;
                                    self.requestBody.requestConceptList.push(requestConcept);
                                    break;
                                case 'TRANSPORTE':
                                    requestConcept.amount = self.transporteAmount;
                                    self.requestBody.requestConceptList.push(requestConcept);
                                    break;
                                case 'CASETAS':
                                    requestConcept.amount = self.casetasAmount;
                                    self.requestBody.requestConceptList.push(requestConcept);
                                    break;
                                case 'GASOLINA':
                                    requestConcept.amount = self.gasolinaAmount;
                                    self.requestBody.requestConceptList.push(requestConcept);
                            }
                        });
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
            }
            .table-body .table-row:nth-child(2n+1) {
                background: #ddd;
            }
            .table-row {
                padding: 1rem;
            }
            .flex-content {
                overflow-x: hidden;
            }

            textarea{
                resize: vertical;
            }

            .underline {
                border-bottom: 1px solid grey;
            }

        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="container">
                <div class="col-md-12">
                    <h2>Viaticos</h2>
                    <div class="row">
                        <div class="col-md-8">
                            <h3>Solicitud de Viáticos</h3>
                        </div>
                        <div class="col-md-4 text-right">
                            <label>Solicitante</label>
                            <p>
                                <span class="label label-default">{{userInSession.dwEmployee.employee.fullName}}</span>
                            </p>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Informacón de solicitud</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-3">
                                    <label>Viaje</label>
                                    <br>
                                    <label class="radio-inline" v-for="roleTravelType in roleTravelTypeList">
                                        <input v-model="selected.travelType" type="radio" @change="getRoleConcepts"
                                               :value="roleTravelType.travelType">{{roleTravelType.travelType.typeName}}
                                    </label>
                                </div>
                                <div class="col-md-3">
                                    <label>
                                        Fecha inicial
                                    </label>
                                    <div class="form-group">
                                        <div class="input-group date" id="startDate">
                                            <input type="text" class="form-control"
                                                   v-model="requestBody.travelExpense.startDate">
                                       <span class="input-group-addon" @click="destroyDateTimePickerStart">
                                           <span class="glyphicon glyphicon-calendar"></span>
                                       </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <label>
                                        Fecha final
                                    </label>
                                    <div class="form-group">
                                        <div class="input-group date" id="endDate">
                                            <input type="text" class="form-control"
                                                   v-model="requestBody.travelExpense.endDate">
                                   <span class="input-group-addon" @click="activateDateTimePickerEnd(requestBody.travelExpense.startDate)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <label>Destino</label>
                                    <input v-model="requestBody.travelExpense.destination" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-2">
                                    <label>KMm estimados</label>
                                    <div class="input-group">
                                        <input v-model="requestBody.travelExpense.estimatedKm" type="text" maxlength="4"
                                               class="form-control" onkeypress="return isNumberKey(event)">
                                        <span class="input-group-addon">KM</span>
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <label>Casetas</label>
                                    <div class="input-group">
                                        <span class="input-group-addon">$</span>
                                        <input v-model="casetas" type="text" maxlength="4"
                                               class="form-control" onkeypress="return isNumberKey(event)">
                                    </div>

                                </div>
                                <div class="col-md-8">
                                    <label>Motivo de viaje</label>
                                    <textarea v-model="requestBody.request.purpose" cols="500" rows="1" class="form-control"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Comceptos autorizados</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-5">
                                    <dl class="dl-horizontal">
                                        <div v-if="dateDifference > 1 || dateDifference === 0">
                                            <dt>Hospedaje</dt>
                                            <dd>
                                                <p class="underline">
                                                    {{hospedajeAmount | currency}} {{currency.acronym}}
                                                </p>
                                            </dd>
                                        </div>
                                        <dt>Alimentos</dt>
                                        <dd>
                                            <p class="underline">
                                                {{alimentosAmount | currency}} {{currency.acronym}}
                                            </p>
                                        </dd>
                                        <dt>Transporte</dt>
                                        <dd>
                                            <p class="underline">
                                                {{transporteAmount | currency}} {{currency.acronym}}
                                            </p>
                                        </dd>
                                    </dl>
                                    <dl class="dl-horizontal">
                                        <dt>Casetas</dt>
                                        <dd>
                                            <p class="underline">
                                                {{casetasAmount | currency}} {{currency.acronym}}
                                            </p>
                                        </dd>
                                        <dt>Gasolina</dt>
                                        <dd>
                                            <p class="underline">
                                                {{gasolinaAmount | currency}} {{currency.acronym}}
                                            </p>
                                        </dd>
                                    </dl>
                                    <dl class="dl-horizontal">
                                        <dt>Total</dt>
                                        <dd>
                                            <p class="underline">
                                                {{totalTravelExpenses | currency}} {{currency.acronym}}
                                            </p>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Deposito</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-3">
                                    <label>Destino</label>
                                    <p class="underline">{{employeeAccount.account.bank.acronyms}}</p>
                                </div>
                                <div class="col-md-3">
                                    <label>Destino</label>
                                    <p class="underline">{{employeeAccount.account.accountNumber}}</p>
                                </div>
                                <div class="col-md-3">
                                    <label>Destino</label>
                                    <p class="underline">{{employeeAccount.account.accountClabe}}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-offset-8 col-md-4">
                            <div class="row">
                                <div class="col-md-6">
                                    <button class="btn btn-success form-control" @click="saveRequest">Enviar</button>
                                </div>
                                <div class="col-md-6">
                                    <button class="btn btn-default form-control" @click="">Salir</button>
                                </div>
                            </div>
                            <br>
                            <br>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
