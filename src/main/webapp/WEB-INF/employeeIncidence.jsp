<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 23/06/16
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>

<t:template pageTitle="BID Group: Incidencia de empleados">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>

        <script>
            function isLetterKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 32 ||
                        charCode === 13 ||
                        (charCode > 64 && charCode < 91) ||
                        (charCode > 96 && charCode < 123) ||
                        charCode === 8
                ) {
                    return true;
                }
                else {
                    return false;
                }
            }
            function isNumberKeyAndLetterKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 13 ||
                        (charCode > 64 && charCode < 91) ||
                        (charCode > 47 && charCode < 58) ||
                        (charCode > 96 && charCode < 123) ||
                        charCode === 8
                ) {
                    return true;
                }
                else {
                    return false;
                }

            }

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
        </script>
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.activateDateTimePickerStart();
                    this.commissionEffective();
                },
                data: {
                    status: null,
                    dwEmployees: [],
                    dPD: [],
                    searching: false,
                    searchSelectedOptions: {
                        area: null,
                        distributor: null,
                        region: null,
                        zona: null,
                        branch: null,
                        role: null,
                        startDate: '',
                        endDate: ''
                    },
                    selectedArea: {},
                    allStatus: {
                        name: 'TODOS',
                        value: 0
                    },
                    activeStatus: {
                        name: 'ACTIVO',
                        value: 1
                    },
                    inactiveStatus: {
                        name: 'INACTIVO',
                        value: 2
                    },
                    defaultArea: {
                        idArea: 0,
                        name: 'TODOS'
                    },
                    defaultDistributor: {
                        idDistributor: 0,
                        name: 'TODOS'
                    },
                    defaultRegion: {
                        idRegion: 0,
                        name: 'TODOS'
                    },
                    defaultZona: {
                        idZonas: 0,
                        name: 'TODOS'
                    },
                    defaultBranch: {
                        idBranch: 0,
                        name: 'TODOS'
                    },
                    defaultRole: {
                        idRole: 0,
                        roleName: 'TODOS'
                    },
                    createReportUrl: '',
                    employeesUrl: null,
                    dateTimePickerStart: '',
                    dateTimePickerEnd: '',
                    reportFileName: '',
                    isThereItems: false,
                    currentDwEmployee: {},
                    nameSearch: '',
                    rfcSearch: '',
                    dwEnterprise: {},
                    role: {},
                    selectedOptions: {
                        area: {
                            idArea: 0
                        },
                        distributor: {
                            idDistributor: 0
                        },
                        region: {
                            idRegion: 0
                        },
                        zona: {
                            idZonas: 0
                        },
                        branch: {
                            idBranch: 0
                        },
                        role: {
                            idRole: 0
                        }
                    },
                    branchs: [],
                    selectOptions: {
                        distributors: [],
                        areas: [],
                        roles: [],
                        dwEnterprises: []
                    },
                    employeeH: {},
                    newAssignamentDwEnterprises: {
                        idEH: 0,
                        dwEnterprise: {},
                        role: {}
                    },
                    defaultAreas: {
                        idArea: 0,
                        name: ''
                    },
                    defaultBranchs: {
                        idBranch: 0,
                        name: ''
                    },
                    defaultRoles: {
                        idRole: 0,
                        name: ''
                    },
                    checkLastAssign: false,
                    rolesRespaldo: {},
                    branchRespaldo: [],
                    areaRespaldo: {},
                    rolesRespaldos: [],
                    branchRespaldos: [],
                    findByRfcStatus: true,
                    distributors: [],
                    areas: [],
                    regions: [],
                    zonas: [],
                    branchch: [],
                    registerNumber: 0,
                    application: '',
                    
                    resultCommission: 0.0,
                    commissions:{},
                  
                    timePickerApplicationDate: '',
                    perceptionDeduction: {
                        rode:'',
                        employee: {},
                        idCPd: '',
                        amount: '',
                        pdReason: '',
                        applicationDate: '',
                        days: ''
                    },
                    pd: {},
                    division: 0.0,
                    idCommissionCash: 1
                },
                methods: {
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    activateDateTimePickerStart: function () {

                        var date = new Date();
                        var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();

                        this.dateTimePickerStart = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            maxDate: currentDate
                        }).data();

                    },
                    activateDateTimePickerEnd: function (startDate) {

                        var minDate = moment(startDate, 'DD-MM-YYYY')
                                .format('YYYY-MM-DD');

                        var date = new Date();
                        var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();

                        this.dateTimePickerEnd = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: minDate,
                            maxDate: currentDate
                        }).data();
                    },
                    destroyDateTimePickerStart: function () {
                        this.activateDateTimePickerStart();
                        $("#startDate").on("dp.change", function (e) {
                            $('#endDate').data("DateTimePicker").minDate(e.date);
                        });
                        $("#endDate").on("dp.change", function (e) {
                            $('#startDate').data("DateTimePicker").maxDate(e.date);
                        });
                    },
                    setEmployeesUrl: function (status) {

                        if (status === 1) {
                            this.employeesUrl = '/employees-history?status=1';
                            this.createReportUrl = ROOT_URL + '/employees-history/create-report?status=1';
                        } else if (status === 2) {
                            this.employeesUrl = '/employees-history?status=2';
                            this.createReportUrl = ROOT_URL + '/employees-history/create-report?status=2';
                        } else if (status === 0) {
                            this.employeesUrl = '/employees-history?status=0 ';
                            this.createReportUrl = ROOT_URL + '/employees-history/create-report?status=0';
                        }

                        if (this.nameSearch != '') {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'fullname=' +
                                    this.nameSearch;
                            this.createReportUrl += 'fullname=' +
                                    this.nameSearch;
                        }

                        if (this.rfcSearch != '') {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'rfc=' +
                                    this.rfcSearch;
                            this.createReportUrl += 'rfc=' +
                                    this.rfcSearch;
                        }

                        return true;
                    },
                    setEmployeesUrlCharacters: function (url) {
                        if (url.indexOf('?') > -1) {
                            url += '&';
                            return url;
                        } else {
                            url += '?';
                            return url;
                        }
                    },
                    getDwEmployees: function () {
                        var self = this;
                        this.$http.get(
                                ROOT_URL + this.employeesUrl
                        ).success(function (data) {
                            this.dwEmployees = data;
                            if (this.dwEmployees.length > 0) {
                                this.registerNumber = this.dwEmployees.length;
                                this.isThereItems = true;
                            } else {
                                showAlert("No hay datos para esa busqueda, intente con otra combinaciòn", {type: 3});
                                setInterval(function () {
                                    location.reload();
                                }, 3000);
                            }
                        }).error(function (data) {
                            showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
                        });
                    },
                    getEmployees: function () {
                        this.dwEmployees = [];
                        this.employeesHistories = [];
                        this.searching = true;
                        if (this.setEmployeesUrl(0)) {
                            this.getEmployeesHistory();
                        }
                    },
                    getEmployeesHistory: function () {
                        var self = this;
                        this.$http.get(
                                ROOT_URL + this.employeesUrl
                        ).success(function (data) {
                            this.dwEmployees = data;
                            if (this.dwEmployees.length > 0) {
                                this.isThereItems = true;
                                this.registerNumber = this.dwEmployees.length;
                            } else {
                                showAlert("No hay datos para esa busqueda, intente con otra combinaciòn", {type: 3});
                                setInterval(function () {
                                    location.reload();
                                }, 3000);
                            }
                        }).error(function (data) {
                            showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
                        });
                    },
                    startDateChanged: function () {
                        this.isThereItems = false;
                    },
                    endDateChanged: function () {
                        this.isThereItems = false;
                    },
                    onDiscountButton: function (dwEmployee) {
                        this.currentDwEmployee = dwEmployee;
                        console.log(this.currentDwEmployee);
                        $("#discountModal").modal("show");
                        this.getPDbyDistributor(dwEmployee);
                    },
                    getPDbyDistributor: function (dwEmployee) {
                        this.$http.get(ROOT_URL + '/distributor-pd/distributor/' + dwEmployee.idDistributor).success(function (data) {
                            this.dPD = data;
                        });
                    },
                    activarTimePickerApplicationDate: function () {
                        var fecha = new Date();
                        var fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();

                        this.timePickerApplicationDate = $('#dateApplication').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: fecha_actual
                        }).data();
                    },
                    onPDtoEmployee: function () {
                        if(this.perceptionDeduction.idCPd == 1){
                            this.perceptionDeduction.amount = this.division;
                        }else if(this.perceptionDeduction.idCPd == 7){
                            this.perceptionDeduction.amount = this.perceptionDeduction.rode;
                        }
                        this.perceptionDeduction.employee = (JSON.parse(JSON.stringify(this.currentDwEmployee)));
                        this.perceptionDeduction.applicationDate = this.timePickerApplicationDate.DateTimePicker.date().toISOString().slice(0, -1);
                        this.$http.post(ROOT_URL + "/perceptions-deductions-employee/save", JSON.stringify(this.perceptionDeduction)).success(function (data) {
                            showAlert("P/D realizada correctamente");
                            this.pd = data;
                            this.deleteFields();
                        }).error(function () {
                            showAlert("Error en la solicitid", {type: 3});
                        });
                    },
                    deleteFields: function () {
                        this.perceptionDeduction.employee = {};
                        this.perceptionDeduction.pdReason = "";
                        this.perceptionDeduction.days = "";
                        this.perceptionDeduction.amount = "";
                        this.perceptionDeduction.idCPd = "";
                        this.perceptionDeduction.applicationDate = "";
                        this.dPD = [];
                        this.application = "";
                        this.division = 0.0;
                        this.resultCommission = "";
                        this.perceptionDeduction.rode = "";
                        $("#discountModal").modal("hide");
                    },
                    calculate: function () {
                        if(this.perceptionDeduction.idCPd == 1){
                            var division = 30;
                        var result1 = this.currentDwEmployee.salary/division;
                        this.division = result1*this.perceptionDeduction.days;
                        }else if(this.perceptionDeduction.idCPd == 7){
                            this.resultCommission = (this.perceptionDeduction.rode/this.commissions.commissionsCash1)*this.commissions.commissionsCash2;
                        }
                    },
                    commissionEffective: function(){
                        this.$http.get(ROOT_URL + "/commissions-cash/" + this.idCommissionCash).success(function (data){
                            this.commissions = data;
                        });
                    },
                    cleanFields: function () {
                        this.perceptionDeduction.employee = {};
                        this.perceptionDeduction.pdReason = "";
                        this.perceptionDeduction.days = "";
                        this.perceptionDeduction.amount = "";
                        this.perceptionDeduction.applicationDate = "";
                        this.application = "";
                        this.division = 0.0;
                        this.resultCommission = "";
                        this.perceptionDeduction.rode = "";
                    }
                },
                filters: {
                    currencyDisplay : {
                        read: function(val) {
                            return val.formatMoney(2, '');
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

    <jsp:attribute name="styles">
        <style>
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
            }

            .table-body .table-row:nth-child(2n+1) {
                background: white;
            }

            .table-row {
                padding: 1rem;
            }

            .flex-content {
                overflow-x: hidden;
            }

            textarea {
                resize: vertical;
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-8">
                        <h2>Busqueda de empleado para asignaciòn de P/D</h2>
                    </div>
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-2" v-if="dwEmployees.length > 0" style="margin-top: 35px">
                        <label><p style="color: darkblue">Nùmero de registros: {{registerNumber}}</p></label>
                    </div>
                </div>
                    <%-- <div v-if="!hierarchy.length > 0"
                         style="height: 6rem; padding: 2rem 0;">
                        <div class="loader">Cargando...</div>
                    </div> --%>
                <div>
                        <%--<div class="row">--%>
                        <%--<div class="col-md-2">--%>
                        <%--<label>Status</label>--%>
                        <%--<select v-model="status" class="form-control" @change="statusChanged">--%>
                        <%--<option selected :value="allStatus">{{allStatus.name}}</option>--%>
                        <%--<option selected :value="activeStatus">{{activeStatus.name}}</option>--%>
                        <%--<option :value="inactiveStatus">{{inactiveStatus.name}}</option>--%>
                        <%--</select>--%>
                        <%--</div>--%>
                        <%--<div class="col-md-2">--%>
                        <%--<label>Distribuidor</label>--%>
                        <%--<select v-model="searchSelectedOptions.distributor" class="form-control"--%>
                        <%--@change="distributorChanged">--%>
                        <%--<option selected :value="defaultDistributor">{{defaultDistributor.name}}</option>--%>
                        <%--<option v-for="distributor in distributors"--%>
                        <%--:value="distributor">--%>
                        <%--{{ distributor.distributorName }}--%>
                        <%--</option>--%>
                        <%--</select>--%>
                        <%--</div>--%>
                        <%--<div class="col-md-2">--%>
                        <%--<label>Región</label>--%>
                        <%--<select v-model="searchSelectedOptions.region" class="form-control"--%>
                        <%--@change="regionChanged"--%>
                        <%--:disabled="searchSelectedOptions.distributor.idDistributor == 0">--%>
                        <%--<option selected :value="defaultRegion">{{defaultRegion.name}}</option>--%>
                        <%--<option v-for="region in regions"--%>
                        <%--:value="region">--%>
                        <%--{{ region.regionName }}--%>
                        <%--</option>--%>
                        <%--</select>--%>
                        <%--</div>--%>
                        <%--<div class="col-md-2">--%>
                        <%--<label>Zona</label>--%>
                        <%--<select v-model="searchSelectedOptions.zona" class="form-control"--%>
                        <%--@change="zonaChanged"--%>
                        <%--:disabled="searchSelectedOptions.region.idRegion == 0">--%>
                        <%--<option selected :value="defaultZona">{{defaultZona.name}}</option>--%>
                        <%--<option v-for="zona in zonas"--%>
                        <%--:value="zona">--%>
                        <%--{{ zona.name }}--%>
                        <%--</option>--%>
                        <%--</select>--%>
                        <%--</div>--%>
                        <%--<div class="col-md-2">--%>
                        <%--<label>Sucursal</label>--%>
                        <%--<select v-model="searchSelectedOptions.branch" class="form-control"--%>
                        <%--@change="branchChanged"--%>
                        <%--:disabled="searchSelectedOptions.zona.idZonas == 0">--%>
                        <%--<option selected :value="defaultBranch">{{defaultBranch.name}}</option>--%>
                        <%--<option v-for="branch in branchch"--%>
                        <%--:value="branch">--%>
                        <%--{{ branch.branchShort }}--%>
                        <%--</option>--%>
                        <%--</select>--%>
                        <%--</div>--%>
                        <%--<div class="col-md-2">--%>
                        <%--<label>Área</label>--%>
                        <%--<select v-model="searchSelectedOptions.area"--%>
                        <%--class="form-control" @change="areaChanged"--%>
                        <%--:disabled="searchSelectedOptions.distributor.idDistributor == 0">--%>
                        <%--<option selected :value="defaultArea">{{defaultArea.name}}</option>--%>
                        <%--<option v-for="area in areas"--%>
                        <%--:value="area">--%>
                        <%--{{ area.areaName }}--%>
                        <%--</option>--%>
                        <%--</select>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                    <div class="row">
                            <%--<div class="col-md-2">--%>
                            <%--<label>Puesto</label>--%>
                            <%--<select v-model="searchSelectedOptions.role" class="form-control"--%>
                            <%--:disabled="searchSelectedOptions.area.idArea == 0">--%>
                            <%--<option selected :value="defaultRole">{{defaultRole.roleName}}</option>--%>
                            <%--<option v-for="role in selectedArea.roles"--%>
                            <%--:value="role">--%>
                            <%--{{ role.roleName }}--%>
                            <%--</option>--%>
                            <%--</select>--%>
                            <%--</div>--%>
                        <div class="col-md-2">
                            <label>Nombre</label>
                            <input v-model="nameSearch" type="text" class="form-control"
                                   placeholder="Nombre del empleado">
                        </div>
                        <div class="col-md-2">
                            <label>RFC</label>
                            <input v-model="rfcSearch" type="text" class="form-control" placeholder="RFC">
                        </div>
                            <%--<div class="col-md-2">--%>
                            <%--<label>--%>
                            <%--Fecha inicial--%>
                            <%--</label>--%>
                            <%--<div class="form-group">--%>
                            <%--<div class="input-group date" id="startDate">--%>
                            <%--<input type="text" class="form-control" v-model="searchSelectedOptions.startDate">--%>
                            <%--<span class="input-group-addon" @click="destroyDateTimePickerStart">--%>
                            <%--<span class="glyphicon glyphicon-calendar"></span>--%>
                            <%--</span>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="col-md-2">--%>
                            <%--<label>--%>
                            <%--Fecha final--%>
                            <%--</label>--%>
                            <%--<div class="form-group">--%>
                            <%--<div class="input-group date" id="endDate">--%>
                            <%--<input type="text" class="form-control" v-model="searchSelectedOptions.endDate">--%>
                            <%--<span class="input-group-addon" @click="activateDateTimePickerEnd(searchSelectedOptions.startDate)">--%>
                            <%--<span class="glyphicon glyphicon-calendar"></span>--%>
                            <%--</span>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                        <div class="col-md-2">
                            <button style="margin-top: 25px" class="btn btn-info" @click="getEmployees">
                                Buscar
                            </button>
                                <%--<a style="margin-top: 25px" class="btn btn-success" @click="onExportButton"--%>
                                <%--v-if="isThereItems">--%>
                                <%--Exportar--%>
                                <%--</a>--%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <br>
                <div v-if="!isThereItems && searching == true"
                     style="height: 6rem; padding: 2rem 0;">
                    <div class="loader">Cargando...</div>
                </div>
                <div style="background: #ddd" class="panel panel-default" v-if="isThereItems">
                    <!-- Default panel contents -->
                    <!-- Table -->
                    <div class="flex-box container-fluid" v-if="dwEmployees.length > 0">
                        <div class="row table-header active">
                            <div class="col-md-2"><b>Nombre de empleado</b></div>
                            <div class="col-md-2"><b>RFC</b></div>
                            <div class="col-md-3"><b>Puesto</b></div>
                            <div class="col-md-2"><b>Sucursal</b></div>
                            <div class="col-md-2"><b>Fecha de ingreso</b></div>
                            <div class="col-md-1"><b>Incidencia</b></div>
                        </div>
                        <br>
                        <div class="table-body flex-row flex-content">
                            <div class="row table-row" v-for="dwEmployee in dwEmployees">
                                <div class="col-md-2">{{dwEmployee.fullName}}</div>
                                <div class="col-md-2">{{dwEmployee.rfc}}</div>
                                <div class="col-md-3">{{dwEmployee.rolesR.roleName}}</div>
                                <div class="col-md-2">{{dwEmployee.dwEnterprisesR.branch.branchShort}}</div>
                                <div class="col-md-2">
                                    <center>{{dwEmployee.joinDateFormats.simpleDate}}</center>
                                </div>
                                <div class="col-md-1">
                                    <button class="btn btn-default btn-sm" @click="onDiscountButton(dwEmployee)"
                                            data-toggle="tooltip" data-placement="top" title="Incidencia">
                                        <span class="glyphicon glyphicon-flag"></span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal Descuentos -->
            <div class="modal fade" id="discountModal" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <form v-on:submit.prevent="onPDtoEmployee">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title">Percepciones/Deducciones</h4>
                            </div>
                            <div class="modal-body">
                                <br>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="col-xs-8" style="margin-top: 21px">
                                            <label>Empleado: </label>
                                            {{currentDwEmployee.fullName}}
                                        </div>
                                        <div class="col-xs-4">
                                            <label>Fecha de aplicación:</label>
                                            <div class='input-group date' id='dateApplication'>
                                                <input type='text' class="form-control" v-model="application" required>
                                                <span class="input-group-addon"
                                                      @click="activarTimePickerApplicationDate()">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="col-xs-6">
                                            <label>Tipo de Percepción/Deducción:</label>
                                            <select class="form-control" v-model="perceptionDeduction.idCPd" @change="cleanFields()" required>
                                                <option v-for="pd in dPD" value="{{pd.cPerceptionsDeductions.idCPd}}">
                                                    {{pd.cPerceptionsDeductions.namePD}}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-xs-2">
                                        </div>
                                        <div class="col-xs-4" v-if="perceptionDeduction.idCPd > 0 && (perceptionDeduction.idCPd != 1 && perceptionDeduction.idCPd != 7)">
                                            <label>Monto:</label>
                                            <input class="form-control" v-model="perceptionDeduction.amount | currency"
                                                   onkeypress="return validateFloatKeyPress(this,event)" required>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                
                                <div class="row" v-if="perceptionDeduction.idCPd == 7">
                                    <div class="col-xs-12">
                                        <div class="col-xs-4">
                                            <label>Monto:</label>
                                            <input class="form-control" v-model="perceptionDeduction.rode"
                                                   onkeypress="return validateFloatKeyPress(this,event)" maxlength="10" @input="calculate()" required>
                                        </div>
                                        <div class="col-xs-4">
                                        </div>
                                        <div class="col-xs-4">
                                            <label>Monto a descontar</label>
                                            <div v-if="resultCommission > 0">
                                                <label>{{resultCommission | currency}}</label>
                                            </div>
                                        </div>
                                    </div>    
                                </div>
                                <br>
                               
                                <div class="row" v-if="perceptionDeduction.idCPd == 1">
                                    <div class="col-xs-12">
                                        <div class="col-xs-4">
                                            <label>Dias a descontar:</label>
                                            <input class="form-control" v-model="perceptionDeduction.days"
                                                   onkeypress="return isNumberKey(event)" maxlength="3" @input="calculate()" required>
                                        </div>
                                        <div class="col-xs-4">
                                        </div>
                                        <div class="col-xs-4">
                                            <label>Monto a descontar</label>
                                            <div v-if="division > 0">
                                                <label>{{division | currency}}</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="row"  v-if="perceptionDeduction.idCPd > 0 && (perceptionDeduction.idCPd != 1 && perceptionDeduction.idCPd != 7)">
                                    <div class="col-xs-12">
                                        <div class="col-xs-12">
                                            <label>Motivo:</label>
                                            <textarea v-model="perceptionDeduction.pdReason" cols="500" rows="1"
                                                      class="form-control" required></textarea>

                                        </div>
                                    </div>
                                </div>
                                <br>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" @click="deleteFields">Salir</button>
                                <button class="btn btn-success">Guardar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>

