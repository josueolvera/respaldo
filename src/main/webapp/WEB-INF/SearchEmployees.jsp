<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 23/06/16
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Carga de archivos">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm= new Vue({
                el: '#content',
                ready: function () {
                    this.fetchHierarchy();
                },
                created: function () {
                    this.activateDateTimePickers();
                },
                data: {
                    updateEmployeeUrl: ROOT_URL + '/saem/update-employee?idDwEmployee=',
                    status : null,
                    dwEmployees: [],
                    employeesHistories:[],
                    hierarchy: [],
                    searching:false,
                    searchSelectedOptions: {
                        area: null,
                        distributor: null,
                        region: null,
                        branch: null,
                        role: null,
                        startDate: '',
                        endDate:''
                    },
                    selectedArea:{},
                    activeStatus:{
                        name:'ACTIVO',
                        value:1
                    },
                    inactiveStatus: {
                        name:'INACTIVO',
                        value:2
                    },
                    defaultArea: {
                        id:0,
                        name:'TODOS'
                    },
                    defaultDistributor: {
                        id:0,
                        name:'TODOS'
                    },
                    defaultRegion: {
                        id:0,
                        name:'TODOS'
                    },
                    defaultBranch: {
                        id:0,
                        name:'TODOS'
                    },
                    defaultRole: {
                        idRole:0,
                        roleName:'TODOS'
                    },
                    createReportUrl: '',
                    employeesUrl : null,
                    dateTimePickerStart:'',
                    dateTimePickerEnd:'',
                    reportFileName:'',
                    isThereItems:false,
                    currentDwEmployee:{}
                },
                methods: {
                    createReport: function () {
                        if (this.reportFileName != '') {
                            $("#exportModal").modal("hide");
                            var createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            createReportUrl += 'reportFileName=' + this.reportFileName;
                            this.createReportUrl.slice(0,-1);
                            this.reportFileName = '';
                            location.href = createReportUrl;
                        } else {
                            showAlert("Debe asignar un nombre de archivo",{type:3});
                            return;
                        }
                    },
                    onExportButton: function () {
                        $("#exportModal").modal("show");
                    },
                    activateDateTimePickers: function () {

                        var currentDate = new Date();

                        this.dateTimePickerStart = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            maxDate: currentDate
                        }).data;

                        this.dateTimePickerEnd = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            maxDate: currentDate
                        }).data;
                    },
                    activateDateTimePickerEnd: function(startDate){

                        var minDate= moment(startDate, 'DD-MM-YYYY')
                                .format('YYYY-MM-DD');
                        var currentDate = new Date();

                        this.dateTimePickerEnd = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: minDate,
                            maxDate: currentDate
                        }).data();
                    },
                    destroyDateTimePickerStart: function(){
                        $("#startDate").on("dp.change", function (e) {
                            $('#endDate').data("DateTimePicker").minDate(e.date);
                        });
                        $("#endDate").on("dp.change", function (e) {
                            $('#startDate').data("DateTimePicker").maxDate(e.date);
                        });
                    },
                    setEmployeesUrl : function (status) {

                        if (status === 1) {
                            this.employeesUrl = '/dw-employees';
                            this.createReportUrl = ROOT_URL + '/dw-employees/create-report';
                        } else if (status === 2) {
                            this.employeesUrl = '/employees-history';
                            this.createReportUrl = ROOT_URL + '/employees-history/create-report';
                        }

                        if (this.searchSelectedOptions.distributor.id != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idDistributor=' +
                                    this.searchSelectedOptions.distributor.id;
                            this.createReportUrl += 'idDistributor=' +
                                    this.searchSelectedOptions.distributor.id;
                        }

                        if (this.searchSelectedOptions.region.id != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idRegion=' +
                                    this.searchSelectedOptions.region.id;
                            this.createReportUrl += 'idRegion=' +
                                    this.searchSelectedOptions.region.id;
                        }

                        if (this.searchSelectedOptions.branch.id != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idBranch=' +
                                    this.searchSelectedOptions.branch.id;
                            this.createReportUrl += 'idBranch=' +
                                    this.searchSelectedOptions.branch.id;
                        }

                        if (this.searchSelectedOptions.area.id != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idArea=' +
                                    this.searchSelectedOptions.area.id;
                            this.createReportUrl += 'idArea=' +
                                    this.searchSelectedOptions.area.id;
                        }

                        if (this.searchSelectedOptions.role.idRole != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idRole=' +
                                    this.searchSelectedOptions.role.idRole;
                            this.createReportUrl += 'idRole=' +
                                    this.searchSelectedOptions.role.idRole;
                        }

                        if (this.searchSelectedOptions.startDate != '') {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'startDate=' +
                                    this.searchSelectedOptions.startDate;
                            this.createReportUrl += 'startDate=' +
                                    this.searchSelectedOptions.startDate;

                            if (this.searchSelectedOptions.endDate != '') {
                                this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                                this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                                this.employeesUrl += 'endDate=' +
                                        this.searchSelectedOptions.endDate;
                                this.createReportUrl += 'endDate=' +
                                        this.searchSelectedOptions.endDate;
                            } else {
                                showAlert("Selecciona una fecha final",{type:3});
                                return false;
                            }
                        }

                        return true;
                    },
                    setEmployeesUrlCharacters : function (url) {
                        if(url.indexOf('?') > -1)
                        {
                            url += '&';
                            return url;
                        } else {
                            url += '?';
                            return url;
                        }
                    },
                    getDwEmployees: function () {
                        this.$http.get(
                                ROOT_URL + this.employeesUrl
                        ).success(function (data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (dwEmployee) {
                                if (isNaN(dwEmployee.dwEnterprise)) {
                                    jsonObjectIndex[dwEmployee.dwEnterprise._id] = dwEmployee.dwEnterprise;
                                } else {
                                    dwEmployee.dwEnterprise = jsonObjectIndex[dwEmployee.dwEnterprise];
                                }
                            });
                            this.dwEmployees = data;
                            if (data.length > 0) {
                                this.isThereItems = true;
                            }
                            this.searching = false;
                        }).error(function (data) {

                        });
                    },
                    getEmployees : function () {
                        this.dwEmployees = [];
                        this.employeesHistories = [];
                        this.searching = true;
                        if (this.status.value === 1) {
                            if (this.setEmployeesUrl(this.status.value)) {
                                this.getDwEmployees();
                            }
                        } else if (this.status.value === 2) {
                            if (this.setEmployeesUrl(this.status.value)) {
                                this.getEmployeesHistory();
                            }
                        }
                    },
                    getEmployeesHistory : function () {
                        this.$http.get(
                                ROOT_URL + this.employeesUrl
                        ).success(function (data) {
                            this.employeesHistories = data;

                            if (data.length > 0) {
                                this.isThereItems = true;
                            }
                            this.searching = false;
                        }).error(function (data) {

                        });
                    },
                    fetchHierarchy: function () {
                        this.$http.get(ROOT_URL + "/dw-enterprises/hierarchy").success(function (data) {
                            this.hierarchy = data;
                        });
                    },
                    distributorChanged: function () {
                        this.searchSelectedOptions.region = this.defaultRegion;
                        this.searchSelectedOptions.branch = this.defaultBranch;
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                        this.isThereItems = false;
                    },
                    regionChanged: function () {
                        this.searchSelectedOptions.branch = this.defaultBranch;
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                        this.isThereItems = false;
                    },
                    branchChanged: function () {
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                        this.isThereItems = false;
                    },
                    areaChanged: function () {
                        this.searchSelectedOptions.role = this.defaultRole;
                        this.$http.get(ROOT_URL + "/areas/area-role/" + this.searchSelectedOptions.area.id).success(function (data) {
                            this.selectedArea = data;
                        });
                        this.isThereItems = false;
                    },
                    statusChanged: function () {
                        this.isThereItems = false;
                    },
                    startDateChanged: function () {
                        this.isThereItems = false;
                    },
                    endDateChanged: function () {
                        this.isThereItems = false;
                    },
                    onDeleteButton: function (dwEmployee) {
                        this.currentDwEmployee = dwEmployee;
                        $("#deleteModal").modal("show");
                    },
                    changeEmployeeStatus: function () {
                        this.$http.post(ROOT_URL + '/dw-employees/change-employee-status',this.currentDwEmployee.idDwEmployee)
                                .success(function (data) {
                                    this.getEmployees();
                                    $("#deleteModal").modal("hide");
                                })
                                .error(function (date) {

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
                background: white;
            }
            .table-row {
                padding: 1rem;
            }
            .flex-content {
                overflow-x: hidden;
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-8">
                        <h2>Busqueda de Empleado</h2>
                    </div>
                </div>
                <div v-if="!hierarchy.length > 0"
                     style="height: 6rem; padding: 2rem 0;">
                    <div class="loader">Cargando...</div>
                </div>
                <div v-if="hierarchy.length > 0">
                    <div class="row">
                        <div class="col-md-2">
                            <label>Status</label>
                            <select v-model="status" class="form-control" @change="statusChanged">
                                <option selected :value="activeStatus">{{activeStatus.name}}</option>
                                <option :value="inactiveStatus">{{inactiveStatus.name}}</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Distribuidor</label>
                            <select v-model="searchSelectedOptions.distributor" class="form-control"
                                    @change="distributorChanged">
                                <option selected :value="defaultDistributor">{{defaultDistributor.name}}</option>
                                <option v-for="distributor in hierarchy[0].subLevels"
                                        :value="distributor">
                                    {{ distributor.name }}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Región</label>
                            <select v-model="searchSelectedOptions.region" class="form-control"
                                    @change="regionChanged"
                                    :disabled="searchSelectedOptions.distributor.id == 0">
                                <option selected :value="defaultRegion">{{defaultRegion.name}}</option>
                                <option v-for="region in searchSelectedOptions.distributor.subLevels"
                                        :value="region">
                                    {{ region.name }}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Sucursal</label>
                            <select v-model="searchSelectedOptions.branch" class="form-control"
                                    @change="branchChanged"
                                    :disabled="searchSelectedOptions.region.id == 0">
                                <option selected :value="defaultBranch">{{defaultBranch.name}}</option>
                                <option v-for="branch in searchSelectedOptions.region.subLevels"
                                        :value="branch">
                                    {{ branch.name }}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Área</label>
                            <select v-model="searchSelectedOptions.area"
                                    class="form-control" @change="areaChanged"
                                    :disabled="searchSelectedOptions.branch.id == 0">
                                <option selected :value="defaultArea">{{defaultArea.name}}</option>
                                <option v-for="area in searchSelectedOptions.branch.subLevels"
                                        :value="area">
                                    {{ area.name }}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Puesto</label>
                            <select v-model="searchSelectedOptions.role" class="form-control"
                                    :disabled="searchSelectedOptions.area.id == 0">
                                <option selected :value="defaultRole">{{defaultRole.roleName}}</option>
                                <option v-for="role in selectedArea.roles"
                                        :value="role">
                                    {{ role.roleName }}
                                </option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <label>Nombre</label>
                            <input v-model="searchSelectedOptions.name" type="text" class="form-control" placeholder="Nombre del Empleado">
                        </div>
                        <div class="col-md-2">
                            <label>RFC</label>
                            <input v-model="searchSelectedOptions.rfc" type="text" class="form-control" placeholder="RFC">
                        </div>
                        <div class="col-md-4" id="picker">
                            <label for="datepicker">Intervalo de Fechas</label>
                            <div class="input-daterange input-group" id="datepicker">
                                <span class="input-group-addon">De</span>
                                <input type="text" class="form-control" @change="startDateChanged"
                                       v-model="searchSelectedOptions.startDate" name="start"
                                       id="startDate" @click="destroyDateTimePickerStart">
                                <span class="input-group-addon">a</span>
                                <input type="text" class="form-control" @change="endDateChanged"
                                       v-model="searchSelectedOptions.endDate" name="end"
                                       id="endDate" @click="activateDateTimePickerEnd(searchSelectedOptions.endDate)">
                            </div>
                        </div>
                        <div class="col-md-2">
                            <button style="margin-top: 25px" class="btn btn-info" @click="getEmployees">
                                Buscar
                            </button>
                            <a style="margin-top: 25px" class="btn btn-success" @click="onExportButton"
                               v-if="isThereItems">
                                Exportar
                            </a>
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
                            <div class="col-md-2"><b>Puesto</b></div>
                            <div class="col-md-2"><b>Sucursal</b></div>
                            <div class="col-md-2"><b>Fecha de ingreso</b></div>
                            <div class="col-md-1"><b>Edición</b></div>
                            <div class="col-md-1"><b>Baja</b></div>
                        </div>
                        <br>
                        <div class="table-body flex-row flex-content">
                            <div class="row table-row" v-for="dwEmployee in dwEmployees |
                            filterBy searchSelectedOptions.name in 'employee.fullName'|
                            filterBy searchSelectedOptions.rfc in 'employee.rfc'">
                                <div class="col-md-2">{{dwEmployee.employee.fullName}}</div>
                                <div class="col-md-2">{{dwEmployee.employee.rfc}}</div>
                                <div class="col-md-2">{{dwEmployee.role.roleName}}</div>
                                <div class="col-md-2">{{dwEmployee.dwEnterprise.branch.branchShort}}</div>
                                <div class="col-md-2">{{dwEmployee.employee.joinDateFormats.simpleDate}}</div>
                                <div class="col-md-1">
                                    <a class="btn btn-default btn-sm" :href="updateEmployeeUrl + dwEmployee.idDwEmployee"
                                       data-toggle="tooltip" data-placement="top" title="Modificar">
                                        <span class="glyphicon glyphicon-pencil"></span>
                                    </a>
                                </div>
                                <div class="col-md-1">
                                    <button class="btn btn-danger btn-sm" @click="onDeleteButton(dwEmployee)"
                                            data-toggle="tooltip" data-placement="top" title="Eliminar">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="flex-box container-fluid"  v-if="employeesHistories.length > 0">
                        <div class="row table-header active">
                            <div class="col-md-2"><b>Nombre de empleado</b></div>
                            <div class="col-md-2"><b>RFC</b></div>
                            <div class="col-md-2"><b>Puesto</b></div>
                            <div class="col-md-2"><b>Sucursal</b></div>
                            <div class="col-md-2"><b>Fecha de ingreso</b></div>
                            <div class="col-md-2"><b>Reactivación</b></div>
                        </div>
                        <br>
                        <div class="table-body flex-row flex-content">
                            <div class="row table-row" v-for="employeesHistory in employeesHistories |
                            filterBy searchSelectedOptions.name in 'fullName'|
                            filterBy searchSelectedOptions.rfc in 'rfc'">
                                <div class="col-md-2">{{employeesHistory.fullName}}</div>
                                <div class="col-md-2">{{employeesHistory.rfc}}</div>
                                <div class="col-md-2">{{employeesHistory.roleName}}</div>
                                <div class="col-md-2">{{employeesHistory.branchShort}}</div>
                                <div class="col-md-2">{{employeesHistory.joinDateFormats.simpleDate}}</div>
                                <div class="col-md-2 text-center">
                                    <button class="btn btn-default btn-sm"
                                            data-toggle="tooltip" data-placement="top" title="Reactivar">
                                        <span class="glyphicon glyphicon-refresh"></span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Exportar -->
            <div class="modal fade" id="exportModal" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-sm" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Exportar Reporte</h4>
                        </div>
                        <div class="modal-body">
                            <br>
                            <div class="form-inline">
                                <div class="form-group">
                                    <label for="reportFileName">Asigne un nombre al archivo</label>
                                    <input type="text" id="reportFileName" class="form-control"
                                           placeholder="Nombre del Archivo" v-model="reportFileName">
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="createReport">Aceptar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Salir</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Baja -->
            <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Confirmacion de Baja de Empleado</h4>
                        </div>
                        <div class="modal-body">
                            <br>
                            <div class="form-inline">
                                <div class="form-group">
                                    <label>Nombre: </label> &nbsp;&nbsp;
                                    <input type="text" class="form-control" v-model="currentDwEmployee.employee.fullName" disabled>
                                </div>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <div class="form-group">
                                    <label>RFC: </label>&nbsp;&nbsp;
                                    <input type="text" class="form-control" v-model="currentDwEmployee.employee.rfc" disabled>
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" @click="changeEmployeeStatus">Baja</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Salir</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
