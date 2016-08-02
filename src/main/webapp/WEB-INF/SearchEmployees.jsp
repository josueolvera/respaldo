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

<t:template pageTitle="BID Group: Busqueda de empleados">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm= new Vue({
                el: '#content',
                ready: function () {
                    this.activateDateTimePickerStart();
                    this.fetchHierarchy();
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
                        zona: null,
                        branch: null,
                        role: null,
                        startDate: '',
                        endDate:''
                    },
                    selectedArea:{},
                    allStatus:{
                      name: 'TODOS',
                        value: 0
                    },
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
                    defaultZona: {
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
                    currentDwEmployee:{},
                    nameSearch: '',
                    rfcSearch: '',
                    dwEnterprise: {},
                    role: {},
                    selectedOptions: {
                        area: {
                            id: 0
                        },
                        distributor: {
                            id: 0
                        },
                        region: {
                            id: 0
                        },
                        branch: {
                            id: 0
                        },
                        role: {
                            id: 0
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
                        branch: {
                            id: 0
                        },
                        area: {
                            id: 0
                        },
                        role: {
                            id: 0
                        }
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
                    activateDateTimePickerStart: function () {

                        var date = new Date();
                        var currentDate = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();

                        this.dateTimePickerStart = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            maxDate: currentDate
                        }).data();

                    },
                    activateDateTimePickerEnd: function(startDate){

                        var minDate= moment(startDate, 'DD-MM-YYYY')
                                .format('YYYY-MM-DD');

                        var date = new Date();
                        var currentDate = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();

                        this.dateTimePickerEnd = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: minDate,
                            maxDate: currentDate
                        }).data();
                    },
                    destroyDateTimePickerStart: function(){
                        this.activateDateTimePickerStart();
                        $("#startDate").on("dp.change", function (e) {
                            $('#endDate').data("DateTimePicker").minDate(e.date);
                        });
                        $("#endDate").on("dp.change", function (e) {
                            $('#startDate').data("DateTimePicker").maxDate(e.date);
                        });
                    },
                    setEmployeesUrl : function (status) {

                        if (status === 1) {
                            this.employeesUrl = '/employees-history?status=1';
                            this.createReportUrl = ROOT_URL + '/dw-employees/create-report?status=1';
                        } else if (status === 2) {
                            this.employeesUrl = '/employees-history?status=2';
                            this.createReportUrl = ROOT_URL + '/dw-employees/create-report?status=0';
                        } else if (status === 0) {
                            this.employeesUrl = '/employees-history?status=0 ';
                            this.createReportUrl = ROOT_URL + '/dw-employees/create-report';
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

                        if (this.searchSelectedOptions.zona.id != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idZona=' +
                                    this.searchSelectedOptions.zona.id;
                            this.createReportUrl += 'idZona=' +
                                    this.searchSelectedOptions.zona.id;
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
                        var self= this;
                        this.$http.get(
                                ROOT_URL + this.employeesUrl
                        ).success(function (data) {
                            this.dwEmployees = data;
                            this.dwEmployees.forEach(function(element){
                                self.$http.get(
                                        ROOT_URL + "/dw-enterprises/" + element.idDwEnterprise
                                ).success(function (data) {
                                    Vue.set(element, "dwEnterprisesR", data)

                                    this.$http.get(
                                            ROOT_URL + "/roles/" + element.idRole
                                    ).success(function (data) {
                                        Vue.set(element, "rolesR", data)
                                    }).error(function (data) {
                                        showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
                                    });


                                }).error(function (data) {
                                    showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
                                });
                            });
                            if (this.dwEmployees.length > 0) {
                                this.isThereItems = true;
                            }
                        }).error(function (data) {
                            showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
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
                        } else if (this.status.value === 0) {
                            if (this.setEmployeesUrl(this.status.value)) {
                                this.getDwEmployees();
                            }
                        }
                    },
                    getEmployeesHistory : function () {
                        var self= this;
                        this.$http.get(
                                ROOT_URL + this.employeesUrl
                        ).success(function (data) {
                            this.dwEmployees = data;
                            this.dwEmployees.forEach(function(element){
                                self.$http.get(
                                        ROOT_URL + "/dw-enterprises/" + element.idDwEnterprise
                                ).success(function (data) {
                                    Vue.set(element, "dwEnterprisesR", data)

                                    this.$http.get(
                                            ROOT_URL + "/roles/" + element.idRole
                                    ).success(function (data) {
                                        Vue.set(element, "rolesR", data)
                                    }).error(function (data) {
                                        showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
                                    });


                                }).error(function (data) {
                                    showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
                                });
                            });
                            if (this.dwEmployees.length > 0) {
                                this.isThereItems = true;
                            }
                        }).error(function (data) {
                            showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
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
                        //this.isThereItems = false;
                    },
                    regionChanged: function () {
                        this.searchSelectedOptions.branch = this.defaultBranch;
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                        //this.isThereItems = false;
                    },
                    branchChanged: function () {
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                        //this.isThereItems = false;
                    },
                    areaChanged: function () {
                        this.searchSelectedOptions.role = this.defaultRole;
                        this.$http.get(ROOT_URL + "/areas/area-role/" + this.searchSelectedOptions.area.id).success(function (data) {
                            this.selectedArea = data;
                        });
                        //this.isThereItems = false;
                    },
                    statusChanged: function () {
                        //this.isThereItems = false;
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
                                    showAlert("Empleado dado de baja exitosamente");
                                    this.getDwEmployees();
                                    $("#deleteModal").modal("hide");
                                })
                                .error(function (date) {
                                    showAlert("Ha habido un error con su solicitud intente nuevamente");
                                });
                    },
                    getRoleEmployeeHistory: function (idEH) {
                        this.$http.get(ROOT_URL + '/roles/employee-history/' + idEH).success(function (data) {
                            this.role = data;
                        });
                    },
                    getDwEnterpriseEH: function (idEH) {
                        this.$http.get(ROOT_URL + '/dw-enterprises/employee-history/' + idEH).success(function (data) {
                            this.dwEnterprise = data;
                        });
                    },
                    getReactivationData: function (idEH) {
                        this.getRoleEmployeeHistory(idEH);
                        this.getDwEnterpriseEH(idEH);
                        this.obtainBranchs();
                        this.$http.get(ROOT_URL + '/employees-history/' + idEH).success(function (data) {
                            this.employeeH = data;
                            $("#reactivacionModal").modal("show");
                        });
                    },
                    obtainBranchs: function () {
                        this.$http.get(ROOT_URL + "/branchs").success(function (data) {
                            this.branchs = data;
                        })
                    },
                    selectedOptionsBranchChanged: function () {
                        this.selectedOptions.area = this.defaultAreas;
                        this.selectedOptions.role = this.defaultRoles;
                        this.selectOptions.dwEnterprises = [];
                        this.selectOptions.roles = [];
                        this.$http.get(ROOT_URL + "/dw-enterprises/branch/" + this.selectedOptions.branch.idBranch).success(function (data) {
                            this.selectOptions.dwEnterprises = data;
                        });
                    },
                    selectedOptionsDwEnterpriseChanged: function () {
                        this.selectOptions.roles = [];
                        this.selectedOptions.role = this.defaultRoles;
                        this.$http.get(ROOT_URL + "/areas/area-role/" + this.selectedOptions.area.idArea).success(function (data) {
                            this.selectOptions.roles = data.roles;
                        });
                    },
                    reactivationEmployee: function () {
                        this.newAssignamentDwEnterprises.branch = this.selectedOptions.branch;
                        this.newAssignamentDwEnterprises.area = this.selectedOptions.area;
                        this.newAssignamentDwEnterprises.role = this.selectedOptions.role;
                        this.newAssignamentDwEnterprises.idEH = this.employeeH.idEmployeeHistory;

                        if(this.selectedOptions.branch.idBranch > 0 && this.selectedOptions.area.idArea> 0 && this.selectedOptions.role.idRole > 0){
                            this.$http.post(ROOT_URL + "/employees-history/reactivation/" + this.employeeH.idEmployeeHistory, JSON.stringify(this.newAssignamentDwEnterprises))
                                    .success(function (data) {
                                        showAlert("Reactivaciòn de empleado exitosa");
                                        this.getDwEmployees();
                                        $("#reactivacionModal").modal("hide");
                                    }).error(function (data) {
                                showAlert("Error en la reactivaciòn",{type: 3});
                            });
                        }else {
                            showAlert("Es necesario otorgar una nueva asignaciòn al empleado",{type: 3});
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
                <%-- <div v-if="!hierarchy.length > 0"
                     style="height: 6rem; padding: 2rem 0;">
                    <div class="loader">Cargando...</div>
                </div> --%>
                <div v-if="hierarchy.length > 0">
                    <div class="row">
                        <div class="col-md-2">
                            <label>Status</label>
                            <select v-model="status" class="form-control" @change="statusChanged">
                                <option selected :value="allStatus">{{allStatus.name}}</option>
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
                            <label>Zona</label>
                            <select v-model="searchSelectedOptions.zona" class="form-control"
                                    @change="zonaChanged"
                                    :disabled="searchSelectedOptions.region.id == 0">
                                <option selected :value="defaultZona">{{defaultZona.name}}</option>
                                <option v-for="zona in searchSelectedOptions.region.subLevels"
                                        :value="zona">
                                    {{ zona.name }}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Sucursal</label>
                            <select v-model="searchSelectedOptions.branch" class="form-control"
                                    @change="branchChanged"
                                    :disabled="searchSelectedOptions.zona.id == 0">
                                <option selected :value="defaultBranch">{{defaultBranch.name}}</option>
                                <option v-for="branch in searchSelectedOptions.zona.subLevels"
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

                    </div>
                    <div class="row">
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
                        <div class="col-md-2">
                            <label>Nombre/RFC</label>
                            <input v-model="nameSearch" type="text" class="form-control" placeholder="Nombre del Empleado/RFC">
                        </div>
                        <div class="col-md-2">
                            <label>
                                Fecha inicial
                            </label>
                            <div class="form-group">
                                <div class="input-group date" id="startDate">
                                    <input type="text" class="form-control" v-model="searchSelectedOptions.startDate">
                                   <span class="input-group-addon" @click="destroyDateTimePickerStart">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <label>
                                Fecha final
                            </label>
                            <div class="form-group">
                                <div class="input-group date" id="endDate">
                                    <input type="text" class="form-control" v-model="searchSelectedOptions.endDate">
                                   <span class="input-group-addon" @click="activateDateTimePickerEnd(searchSelectedOptions.startDate)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
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
                            <div class="col-md-1"><b>Sucursal</b></div>
                            <div class="col-md-2"><b>Fecha de ingreso</b></div>
                            <div class="col-md-1"><b>Edición</b></div>
                            <div class="col-md-2"><b>Baja/Reactivación</b></div>
                        </div>
                        <br>
                        <div class="table-body flex-row flex-content">
                            <div class="row table-row" v-for="dwEmployee in dwEmployees | filterBy nameSearch">
                                <div class="col-md-2">{{dwEmployee.fullName}}</div>
                                <div class="col-md-2">{{dwEmployee.rfc}}</div>
                                <div class="col-md-2">{{dwEmployee.rolesR.roleName}}</div>
                                <div class="col-md-1">{{dwEmployee.dwEnterprisesR.branch.branchShort}}</div>
                                <div class="col-md-2">{{dwEmployee.joinDateFormats.simpleDate}}</div>
                                <div class="col-md-1">
                                    <a class="btn btn-default btn-sm" :href="updateEmployeeUrl + dwEmployee.idDwEmployee"
                                       data-toggle="tooltip" data-placement="top" title="Modificar" v-if="dwEmployee.idActionType != 2">
                                        <span class="glyphicon glyphicon-pencil"></span>
                                    </a>
                                </div>
                                <div class="col-md-2 text-center">
                                    <button class="btn btn-danger btn-sm" @click="onDeleteButton(dwEmployee)"
                                            data-toggle="tooltip" data-placement="top" title="Eliminar" v-if="dwEmployee.idActionType != 2">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </button>
                                    <button class="btn btn-default btn-sm"
                                            data-toggle="tooltip" data-placement="top" title="Reactivar"
                                            v-if="dwEmployee.idActionType == 2" @click="getReactivationData(dwEmployee.idEmployeeHistory)">
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
                                    <input type="text" class="form-control" v-model="currentDwEmployee.fullName" disabled>
                                </div>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <div class="form-group">
                                    <label>RFC: </label>&nbsp;&nbsp;
                                    <input type="text" class="form-control" v-model="currentDwEmployee.rfc" disabled>
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
            <div class="modal fade" id="reactivacionModal" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Reactivaciòn</h4>
                        </div>
                        <div class="modal-body">
                            <br>
                            <div class="row">
                                <div class="col-xs-12">
                                    <label>Ùltima asignaciòn</label>
                                </div>
                                <div class="col-xs-12">
                                    <table class="table table-striped">
                                        <thead>
                                        <th class="col-xs-2">Distribuidor</th>
                                        <th class="col-xs-2">Regiòn</th>
                                        <th class="col-xs-3">Sucursal</th>
                                        <th class="col-xs-3">Àrea</th>
                                        <th class="col-xs-2">Puesto</th>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td class="col-xs-2">
                                                {{dwEnterprise.distributor.distributorName}}
                                            </td>
                                            <td class="col-xs-2">
                                                {{dwEnterprise.region.regionName}}
                                            </td>
                                            <td class="col-xs-2">
                                                {{dwEnterprise.branch.branchName}}
                                            </td>
                                            <td class="col-xs-2">
                                                {{dwEnterprise.area.areaName}}
                                            </td>
                                            <td class="col-xs-2">
                                                {{role.roleName}}
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3">
                                    <label>Surcursal</label>
                                    <select v-model="selectedOptions.branch" class="form-control"
                                            required @change="selectedOptionsBranchChanged">
                                        <option v-for="branch in branchs"
                                                :value="branch">
                                            {{ branch.branchShort }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-3">
                                    <label>Àrea</label>
                                    <select v-model="selectedOptions.area" class="form-control"
                                            required @change="selectedOptionsDwEnterpriseChanged"
                                            :disabled="selectOptions.dwEnterprises.length <= 0">
                                        <option v-for="dwEnterprise in selectOptions.dwEnterprises"
                                                :value="dwEnterprise.area">
                                            {{ dwEnterprise.area.areaName }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-3">
                                    <label>Puesto</label>
                                    <select v-model="selectedOptions.role" class="form-control"
                                            required :disabled="selectedOptions.area.id <= 0">
                                        <option v-for="role in selectOptions.roles"
                                                :value="role">
                                            {{ role.roleName }}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" @click="reactivationEmployee()">Reactivar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Salir</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
