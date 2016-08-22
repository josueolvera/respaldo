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

<t:template pageTitle="BID Group: Busqueda de empleados">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.activateDateTimePickerStart();
                },
                data: {
                    updateEmployeeUrl: ROOT_URL + '/saem/update-employee?idDwEmployee=',
                    status: null,
                    dwEmployees: [],
                    employeesHistories: [],
                    hierarchy: [],
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
                    registerNumber: 0
                },
                methods: {
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    createReport: function () {
                        if (this.reportFileName != '') {
                            $("#exportModal").modal("hide");
                            var createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            createReportUrl += 'reportFileName=' + this.reportFileName;
                            this.createReportUrl.slice(0, -1);
                            this.reportFileName = '';
                            location.href = createReportUrl;
                        } else {
                            showAlert("Debe asignar un nombre de archivo", {type: 3});
                            return;
                        }
                    },
                    onExportButton: function () {
                        $("#exportModal").modal("show");
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

//                        if (this.searchSelectedOptions.distributor.idDistributor != 0) {
//                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
//                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
//                            this.employeesUrl += 'idDistributor=' +
//                                    this.searchSelectedOptions.distributor.idDistributor;
//                            this.createReportUrl += 'idDistributor=' +
//                                    this.searchSelectedOptions.distributor.idDistributor;
//                        }
//
//                        if (this.searchSelectedOptions.region.idRegion != 0) {
//                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
//                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
//                            this.employeesUrl += 'idRegion=' +
//                                    this.searchSelectedOptions.region.idRegion;
//                            this.createReportUrl += 'idRegion=' +
//                                    this.searchSelectedOptions.region.idRegion;
//                        }
//
//                        if (this.searchSelectedOptions.zona.idZonas != 0) {
//                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
//                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
//                            this.employeesUrl += 'idZona=' +
//                                    this.searchSelectedOptions.zona.idZonas;
//                            this.createReportUrl += 'idZona=' +
//                                    this.searchSelectedOptions.zona.idZonas;
//                        }
//
//                        if (this.searchSelectedOptions.branch.idBranch != 0) {
//                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
//                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
//                            this.employeesUrl += 'idBranch=' +
//                                    this.searchSelectedOptions.branch.idBranch;
//                            this.createReportUrl += 'idBranch=' +
//                                    this.searchSelectedOptions.branch.idBranch;
//                        }
//
//                        if (this.searchSelectedOptions.area.idArea != 0) {
//                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
//                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
//                            this.employeesUrl += 'idArea=' +
//                                    this.searchSelectedOptions.area.idArea;
//                            this.createReportUrl += 'idArea=' +
//                                    this.searchSelectedOptions.area.idArea;
//                        }
//
//                        if (this.searchSelectedOptions.role.idRole != 0) {
//                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
//                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
//                            this.employeesUrl += 'idRole=' +
//                                    this.searchSelectedOptions.role.idRole;
//                            this.createReportUrl += 'idRole=' +
//                                    this.searchSelectedOptions.role.idRole;
//                        }

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

//                        if (this.searchSelectedOptions.startDate != '') {
//                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
//                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
//                            this.employeesUrl += 'startDate=' +
//                                    this.searchSelectedOptions.startDate;
//                            this.createReportUrl += 'startDate=' +
//                                    this.searchSelectedOptions.startDate;
//
//                            if (this.searchSelectedOptions.endDate != '') {
//                                this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
//                                this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
//                                this.employeesUrl += 'endDate=' +
//                                        this.searchSelectedOptions.endDate;
//                                this.createReportUrl += 'endDate=' +
//                                        this.searchSelectedOptions.endDate;
//                            } else {
//                                showAlert("Selecciona una fecha final",{type:3});
//                                return false;
//                            }
//                        }

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
//                        if (this.status.value === 1) {
//                            if (this.setEmployeesUrl(this.status.value)) {
//                                this.getDwEmployees();
//                            }
//                        } else if (this.status.value === 2) {
//                            if (this.setEmployeesUrl(this.status.value)) {
//                                    this.getEmployeesHistory();
//                            }
//                        } else if (this.status.value === 0) {
//                            if (this.setEmployeesUrl(this.status.value)) {
//                                this.getDwEmployees();
//                            }
//                        }
                        if (this.setEmployeesUrl(0)) {
                            this.getDwEmployees();
                        }
                    },
                    getEmployeesHistory: function () {
                        var self = this;
                        this.$http.get(
                                ROOT_URL + this.employeesUrl
                        ).success(function (data) {
                            this.dwEmployees = data;
                            this.dwEmployees.forEach(function (element) {
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
//                    distributorChanged: function () {
//                        this.searchSelectedOptions.region = this.defaultRegion;
//                        this.searchSelectedOptions.zona = this.defaultZona;
//                        this.searchSelectedOptions.branch = this.defaultBranch;
//                        this.searchSelectedOptions.area = this.defaultArea;
//                        this.searchSelectedOptions.role = this.defaultRole;
//                        this.getAreaByDistributor(this.searchSelectedOptions.distributor.idDistributor);
//                        this.getRegionByDistributor(this.searchSelectedOptions.distributor.idDistributor);
//                    },
//                    getRegionByDistributor: function (idDistributor) {
//                        //this.isThereItems = false;
//                        var self = this;
//                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor-region/"+idDistributor).success(function (data) {
//                            this.regions = [];
//                            var index;
//                            data.forEach(function (region) {
//                                index =  self.arrayObjectIndexOf(self.regions,region.idRegion,'idRegion');
//                                if(index == -1) self.regions.push(region);
//                            });
//
//                        }).error(function () {
//                            showAlert("No existen regiones para esa compañia", {type : 3});
//                        });
//                    },
//                    getAreaByDistributor: function (idDistributor) {
//                        var self = this;
//                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor-area/"+idDistributor).success(function (data) {
//                            this.areas = [];
//                            var index;
//                            data.forEach(function (area) {
//                                index =  self.arrayObjectIndexOf(self.areas,area.idArea,'idArea');
//                                if(index == -1) self.areas.push(area);
//                            });
//                        }).error(function () {
//                            showAlert("No existen areas para esa compañia", {type : 3});
//                        });
//                    },
//                    regionChanged: function () {
//                        this.searchSelectedOptions.zona = this.defaultZona;
//                        this.searchSelectedOptions.branch = this.defaultBranch;
//                        this.searchSelectedOptions.area = this.defaultArea;
//                        this.searchSelectedOptions.role = this.defaultRole;
//                        //this.isThereItems = false;
//                        this.getZonaByDistributorAndRegion(this.searchSelectedOptions.distributor.idDistributor, this.searchSelectedOptions.region.idRegion);
//                    },
//                    getZonaByDistributorAndRegion: function (idDistributor, idRegion) {
//                        var self = this;
//                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor-region-zona/"+idDistributor+"/"+idRegion).success(function (data) {
//                            this.zonas = [];
//                            var index;
//                            data.forEach(function (zona) {
//                                index =  self.arrayObjectIndexOf(self.zonas,zona.idZonas,'idZonas');
//                                if(index == -1) self.zonas.push(zona);
//                            });
//                        }).error(function () {
//                            showAlert("No existen zonas para esa compañìa y regiòn ", {type : 3});
//                        });
//                    },
//                    zonaChanged: function () {
//                        this.searchSelectedOptions.branch = this.defaultBranch;
//                        this.searchSelectedOptions.area = this.defaultArea;
//                        this.searchSelectedOptions.role = this.defaultRole;
//                        this.getBranchByDistributorAndRegionAndZona(this.searchSelectedOptions.distributor.idDistributor,  this.searchSelectedOptions.region.idRegion, this.searchSelectedOptions.zona.idZonas);
//                    },
//                    getBranchByDistributorAndRegionAndZona: function (idDistributor, idRegion, idZona) {
//                        var self = this;
//                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor-region-zona-branch/"+idDistributor+"/"+idRegion+"/"+idZona).success(function (data) {
//                            this.branchch = [];
//                            var index;
//                            data.forEach(function (branch) {
//                                index =  self.arrayObjectIndexOf(self.branchch,branch.idBranch,'idBranch');
//                                if(index == -1) self.branchch.push(branch);
//                            });
//                        }).error(function () {
//                            showAlert("No existen sucursales para esa compañìa, regiòn y zona", {type : 3});
//                        });
//                    },
//                    branchChanged: function () {
//                        this.searchSelectedOptions.area = this.defaultArea;
//                        this.searchSelectedOptions.role = this.defaultRole;
//                        this.getAreaByBranch(this.searchSelectedOptions.branch.idBranch);
//                    },
//                    getAreaByBranch: function (idBranch) {
//                        this.areas = [];
//                        var self = this;
//                        this.$http.get(ROOT_URL + "/dw-enterprises/branch-area/"+idBranch).success(function (data) {
//                            this.areas = [];
//                            var index;
//                            data.forEach(function (area) {
//                                index =  self.arrayObjectIndexOf(self.areas,area.idArea,'idArea');
//                                if(index == -1) self.areas.push(area);
//                            });
//                        }).error(function () {
//                            showAlert("No existen areas para esa compañìa, regiòn, zona y sucursal", {type : 3});
//                        });
//                    },
//                    areaChanged: function () {
//                        this.searchSelectedOptions.role = this.defaultRole;
//                        this.$http.get(ROOT_URL + "/areas/area-role/" + this.searchSelectedOptions.area.idArea).success(function (data) {
//                            this.selectedArea = data;
//                        });
//                        //this.isThereItems = false;
//                    },
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
                        this.$http.post(ROOT_URL + '/dw-employees/change-employee-status', this.currentDwEmployee.idDwEmployee)
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
                        this.getDistributors();
                        this.$http.get(ROOT_URL + '/employees-history/' + idEH).success(function (data) {
                            this.employeeH = data;
                            $("#reactivacionModal").modal("show");
                        });
                    },
//                    obtainBranchs: function () {
//                        this.$http.get(ROOT_URL + "/branchs").success(function (data) {
//                            this.branchRespaldo = data;
//                            this.branchs = data;
//                        })
//                    },
//                    selectedOptionsBranchChanged: function () {
//                        this.selectedOptions.area = this.defaultAreas;
//                        this.selectedOptions.role = this.defaultRoles;
//                        this.areaRespaldo = [];
//                        this.rolesRespaldos = [];
//                        this.$http.get(ROOT_URL + "/dw-enterprises/branch/" + this.selectedOptions.branch.idBranch).success(function (data) {
//                            this.areaRespaldo = data;
//                            this.getLastAssign();
//                        });
//                    },
//                    selectedOptionsDwEnterpriseChanged: function () {
//                        this.rolesRespaldos = [];
//                        this.selectedOptions.role = this.defaultRoles;
//                        this.$http.get(ROOT_URL + "/areas/area-role/" + this.selectedOptions.area.idArea).success(function (data) {
//                            this.rolesRespaldos = data.roles;
//                            this.getLastAssign();
//                        });
//                    },
//                    reactivationEmployee: function () {
//                        this.newAssignamentDwEnterprises.branch = this.selectedOptions.branch;
//                        this.newAssignamentDwEnterprises.area = this.selectedOptions.area;
//                        this.newAssignamentDwEnterprises.role = this.selectedOptions.role;
//                        this.newAssignamentDwEnterprises.idEH = this.employeeH.idEmployeeHistory;
//
//                        if(this.selectedOptions.branch.idBranch > 0 && this.selectedOptions.area.idArea> 0 && this.selectedOptions.role.idRole > 0){
//                            this.$http.post(ROOT_URL + "/employees-history/reactivation/" + this.employeeH.idEmployeeHistory, JSON.stringify(this.newAssignamentDwEnterprises))
//                                    .success(function (data) {
//                                        showAlert("Reactivaciòn de empleado exitosa");
//                                        this.getDwEmployees();
//                                        this.selectedOptions.area = {};
//                                        this.selectedOptions.distributor = {};
//                                        this.selectedOptions.region = {};
//                                        this.selectedOptions.branch = {};
//                                        this.selectedOptions.role = {};
//                                        $("#reactivacionModal").modal("hide");
//                                    }).error(function (data) {
//                                showAlert("Error en la reactivaciòn",{type: 3});
//                            });
//                        }else {
//                            showAlert("Es necesario reasignar al empleado a una sucursal",{type: 3});
//                        }
//                    },
//                    getLastAssign: function () {
//                        if(this.checkLastAssign){
//                            this.branchs = [];
//                            this.branchs.push(this.dwEnterprise.branch);
//                            this.selectOptions.roles = [];
//                            this.selectOptions.roles.push(this.role);
//                            this.selectOptions.dwEnterprises = [];
//                            this.selectOptions.dwEnterprises.push(this.dwEnterprise);
//                        }else{
//                            this.branchs = [];
//                            this.branchs = this.branchRespaldo;
//                            this.selectOptions.roles = [];
//                            this.selectOptions.roles = this.rolesRespaldos;
//                            this.selectOptions.dwEnterprises = [];
//                            this.selectOptions.dwEnterprises = this.areaRespaldo;
//                        }
//                    }

                    getDistributors: function () {
                        this.$http.get(ROOT_URL + "/distributors?forStock=true").success(function (data) {
                            this.distributors = data;
                        });
                    },
                    distributorChanged: function () {
                        this.regions = [];
                        this.zonas = [];
                        this.branchch = [];
                        this.areas = [];
                        this.selectedArea = [];
                        this.selectedOptions.region = this.defaultRegion;
                        this.selectedOptions.zona = this.defaultZona;
                        this.selectedOptions.branch = this.defaultBranch;
                        this.selectedOptions.area = this.defaultArea;
                        this.selectedOptions.role = this.defaultRole;
                        this.getAreaByDistributor(this.selectedOptions.distributor.idDistributor);
                        this.getRegionByDistributor(this.selectedOptions.distributor.idDistributor);
                    },
                    getRegionByDistributor: function (idDistributor) {
                        //this.isThereItems = false;
                        var self = this;
                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor-region/" + idDistributor).success(function (data) {
                            this.regions = [];
                            var index;
                            data.forEach(function (region) {
                                index = self.arrayObjectIndexOf(self.regions, region.idRegion, 'idRegion');
                                if (index == -1) self.regions.push(region);
                            });

                        }).error(function () {
                            showAlert("No existen regiones para esa compañia", {type: 3});
                        });
                    },
                    getAreaByDistributor: function (idDistributor) {
                        var self = this;
                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor-area/" + idDistributor).success(function (data) {
                            this.areas = [];
                            var index;
                            data.forEach(function (area) {
                                index = self.arrayObjectIndexOf(self.areas, area.idArea, 'idArea');
                                if (index == -1) self.areas.push(area);
                            });
                        }).error(function () {
                            showAlert("No existen areas para esa compañia", {type: 3});
                        });
                    },
                    regionChanged: function () {
                        this.selectedOptions.zona = this.defaultZona;
                        this.selectedOptions.branch = this.defaultBranch;
                        //this.isThereItems = false;
                        this.getZonaByDistributorAndRegion(this.selectedOptions.distributor.idDistributor, this.selectedOptions.region.idRegion);
                    },
                    getZonaByDistributorAndRegion: function (idDistributor, idRegion) {
                        var self = this;
                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor-region-zona/" + idDistributor + "/" + idRegion).success(function (data) {
                            this.zonas = [];
                            var index;
                            data.forEach(function (zona) {
                                index = self.arrayObjectIndexOf(self.zonas, zona.idZonas, 'idZonas');
                                if (index == -1) self.zonas.push(zona);
                            });
                        }).error(function () {
                            showAlert("No existen zonas para esa compañìa y regiòn ", {type: 3});
                        });
                    },
                    zonaChanged: function () {
                        this.selectedOptions.branch = this.defaultBranch;
                        this.getBranchByDistributorAndRegionAndZona(this.selectedOptions.distributor.idDistributor, this.selectedOptions.region.idRegion, this.selectedOptions.zona.idZonas);
                    },
                    getBranchByDistributorAndRegionAndZona: function (idDistributor, idRegion, idZona) {
                        var self = this;
                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor-region-zona-branch/" + idDistributor + "/" + idRegion + "/" + idZona).success(function (data) {
                            this.branchch = [];
                            var index;
                            data.forEach(function (branch) {
                                index = self.arrayObjectIndexOf(self.branchch, branch.idBranch, 'idBranch');
                                if (index == -1) self.branchch.push(branch);
                            });
                        }).error(function () {
                            showAlert("No existen sucursales para esa compañìa, regiòn y zona", {type: 3});
                        });
                    },
//                    branchChanged: function () {
//                        this.getAreaByBranch(this.selectedOptions.branch.idBranch);
//                    },
//                    getAreaByBranch: function (idBranch) {
//                        this.areas = [];
//                        var self = this;
//                        this.$http.get(ROOT_URL + "/dw-enterprises/branch-area/" + idBranch).success(function (data) {
//                            this.areas = [];
//                            var index;
//                            data.forEach(function (area) {
//                                index = self.arrayObjectIndexOf(self.areas, area.idArea, 'idArea');
//                                if (index == -1) self.areas.push(area);
//                            });
//                        }).error(function () {
//                            showAlert("No existen areas para esa compañìa, regiòn, zona y sucursal", {type: 3});
//                        });
//                    },
                    areaChanged: function () {
                        this.selectedOptions.role = this.defaultRole;
                        this.$http.get(ROOT_URL + "/distributor-area-rol/" + this.selectedOptions.distributor.idDistributor + "/" + this.selectedOptions.area.idArea).success(function (data) {
                            this.selectedArea = data;
                            this.getRegionByDistributorArea();
                            this.getBranchByDistributorArea();
                        });
                        //this.isThereItems = false;
                    },
                    getRegionByDistributorArea: function () {
                        var self = this;
                        this.$http.get(
                                ROOT_URL + "/dw-enterprises/distributor-area/region/" + this.selectedOptions.distributor.idDistributor + "/" + this.selectedOptions.area.idArea)
                                .success(function (data) {
                                    this.regions = [];
                                    var index;
                                    data.forEach(function (region) {
                                        index = self.arrayObjectIndexOf(self.regions, region.idRegion, 'idRegion');
                                        if (index == -1) self.regions.push(region);
                                    });

                                }).error(function () {
                            showAlert("No existen regiones para esa compañia", {type: 3});
                        });
                    },
                    getBranchByDistributorArea: function () {
                        var self = this;
                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor-area/branch/" + this.selectedOptions.distributor.idDistributor + "/" + this.selectedOptions.area.idArea).success(function (data) {
                            this.branchch = [];
                            var index;
                            data.forEach(function (branch) {
                                index = self.arrayObjectIndexOf(self.branchch, branch.idBranch, 'idBranch');
                                if (index == -1) self.branchch.push(branch);
                            });
                        }).error(function () {
                            showAlert("No existen sucursales para esa compañìa, regiòn y zona", {type: 3});
                        });
                    },
                    validateRoleBranch: function () {
                        var self = this;
                        this.$http.get(ROOT_URL + "/dw-enterprises?idDistributor=" + this.selectedOptions.distributor.idDistributor
                                + "&idRegion=" + this.selectedOptions.region.idRegion + "&idZona=" + this.selectedOptions.zona.idZonas
                                + "&idBranch=" + this.selectedOptions.branch.idBranch + "&idArea=" + this.selectedOptions.area.idArea).success(function (data) {
                            self.$http.get(ROOT_URL + "/dw-employees/validate-role/" + data[0].idDwEnterprise + "/" + self.selectedOptions.role.idRole).success(function () {

                            }).error(function () {
                                showAlert("El puesto seleccionado ya esta ocupado por otra persona", {type: 3});
                                self.branchch = [];
                                self.regions = [];
                                self.zonas = [];
                                self.areas = [];
                                self.selectedArea = [];
                                self.selectedOptions = {
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
                                }
                            });
                        }).error(function () {
                            showAlert("Error al obtener informaciòn para esa sucursal", {type: 3});
                            this.branchch = [];
                            this.regions = [];
                            this.zonas = [];
                            this.areas = [];
                            this.selectedArea = [];
                            this.selectedOptions = {
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
                            }
                        });
                    },
                    validateRoleZona: function () {
                        var self = this;
                        this.$http.get(ROOT_URL + "/dw-enterprises?idDistributor=" + this.selectedOptions.distributor.idDistributor
                                + "&idRegion=" + this.selectedOptions.region.idRegion + "&idZona=" + this.selectedOptions.zona.idZonas
                                + "&idBranch=0&idArea=" + this.selectedOptions.area.idArea).success(function (data) {
                            self.$http.get(ROOT_URL + "/dw-employees/validate-role/" + data[0].idDwEnterprise + "/" + self.selectedOptions.role.idRole).success(function () {

                            }).error(function () {
                                showAlert("El puesto seleccionado ya esta ocupado por otra persona", {type: 3});
                                self.branchch = [];
                                self.regions = [];
                                self.zonas = [];
                                self.areas = [];
                                self.selectedArea = [];
                                self.selectedOptions = {
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
                                }
                            });
                        }).error(function () {
                            showAlert("Error al obtener informaciòn para esa zona", {type: 3});
                            this.branchch = [];
                            this.regions = [];
                            this.zonas = [];
                            this.areas = [];
                            this.selectedArea = [];
                            this.selectedOptions = {
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
                            }
                        });
                    },
                    validateRoleRegion: function () {
                        var self = this;
                        this.$http.get(ROOT_URL + "/dw-enterprises?idDistributor=" + this.selectedOptions.distributor.idDistributor
                                + "&idRegion=" + this.selectedOptions.region.idRegion + "&idZona=0&idBranch=0&idArea=" + this.selectedOptions.area.idArea).success(function (data) {
                            self.$http.get(ROOT_URL + "/dw-employees/validate-role/" + data[0].idDwEnterprise + "/" + self.selectedOptions.role.idRole).success(function () {

                            }).error(function () {
                                showAlert("El puesto seleccionado ya esta ocupado por otra persona", {type: 3});
                                self.branchch = [];
                                self.regions = [];
                                self.zonas = [];
                                self.areas = [];
                                self.selectedArea = [];
                                self.selectedOptions = {
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
                                }
                            });
                        }).error(function () {
                            showAlert("Error al obtener informaciòn para esa regiòn", {type: 3});
                            this.branchch = [];
                            this.regions = [];
                            this.zonas = [];
                            this.areas = [];
                            this.selectedArea = [];
                            this.selectedOptions = {
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
                            }
                        });
                    },
                    roleChanged: function () {
                        this.selectedOptions.zona = this.defaultZona;
                        this.selectedOptions.branch = this.defaultBranch;
                    },
                    cleanAndBuild: function () {
                        if (this.checkLastAssign == true) {
                            this.regions = [];
                            this.zonas = [];
                            this.branchch = [];
                            this.areas = [];
                            this.selectedArea = [];
                            this.selectedOptions.distributor = this.defaultDistributor;
                            this.selectedOptions.region = this.defaultRegion;
                            this.selectedOptions.zona = this.defaultZona;
                            this.selectedOptions.branch = this.defaultBranch;
                            this.selectedOptions.area = this.defaultArea;
                            this.selectedOptions.role = this.defaultRole;
                        }
                    },
                    reactivationEmployee: function () {

                        if (this.checkLastAssign == false) {
                            this.newAssignamentDwEnterprises.idEH = this.employeeH.idEmployeeHistory;
                            this.newAssignamentDwEnterprises.dwEnterprise = this.selectedOptions;
                            this.newAssignamentDwEnterprises.role = this.selectedOptions.role;
                        } else {
                            this.newAssignamentDwEnterprises.idEH = this.employeeH.idEmployeeHistory;
                            this.newAssignamentDwEnterprises.dwEnterprise = this.dwEnterprise;
                            this.newAssignamentDwEnterprises.role = this.role;
                        }
                        this.$http.post(ROOT_URL + "/employees-history/reactivation/" + this.employeeH.idEmployeeHistory, JSON.stringify(this.newAssignamentDwEnterprises))
                                .success(function (data) {
                                    showAlert("Reactivaciòn de empleado exitosa");
                                    this.getDwEmployees();
                                    this.branchch = [];
                                    this.regions = [];
                                    this.zonas = [];
                                    this.areas = [];
                                    this.selectedArea = [];
                                    this.selectedOptions = {
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
                                    };
                                    $("#reactivacionModal").modal("hide");
                                }).error(function (data) {
                            showAlert("Error en la reactivaciòn", {type: 3});
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
                        <h2>Busqueda de empleado</h2>
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
                            <div class="col-md-2"><b>Puesto</b></div>
                            <div class="col-md-1"><b>Sucursal</b></div>
                            <div class="col-md-2"><b>Fecha de ingreso</b></div>
                            <div class="col-md-1"><b>Editar</b></div>
                            <div class="col-md-2"><b>Baja/Reactivación</b></div>
                        </div>
                        <br>
                        <div class="table-body flex-row flex-content">
                            <div class="row table-row" v-for="dwEmployee in dwEmployees">
                                <div class="col-md-2">{{dwEmployee.fullName}}</div>
                                <div class="col-md-2">{{dwEmployee.rfc}}</div>
                                <div class="col-md-2">{{dwEmployee.rolesR.roleName}}</div>
                                <div class="col-md-1">{{dwEmployee.dwEnterprisesR.branch.branchShort}}</div>
                                <div class="col-md-2">
                                    <center>{{dwEmployee.joinDateFormats.simpleDate}}</center>
                                </div>
                                <div class="col-md-1">
                                    <center><a class="btn btn-default btn-sm"
                                               :href="updateEmployeeUrl + dwEmployee.idDwEmployee"
                                               data-toggle="tooltip" data-placement="top" title="Modificar"
                                               v-if="dwEmployee.idActionType != 2">
                                        <span class="glyphicon glyphicon-pencil"></span>
                                    </a></center>
                                </div>
                                <div class="col-md-2 text-center">
                                    <button class="btn btn-danger btn-sm" @click="onDeleteButton(dwEmployee)"
                                            data-toggle="tooltip" data-placement="top" title="Eliminar"
                                            v-if="dwEmployee.idActionType != 2">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </button>
                                    <button class="btn btn-default btn-sm"
                                            data-toggle="tooltip" data-placement="top" title="Reactivar"
                                            v-if="dwEmployee.idActionType == 2"
                                            @click="getReactivationData(dwEmployee.idEmployeeHistory)">
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
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
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
                            <button type="button" class="btn btn-default" data-dismiss="modal">Salir</button>
                            <button type="button" class="btn btn-success" @click="createReport">Aceptar</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Baja -->
            <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Confirmación</h4>
                        </div>
                        <div class="modal-body">
                            <br>
                            <div class="form-inline">
                                <div class="form-group">
                                    El empleado con el nombre <label>{{currentDwEmployee.fullName}}</label> y el rfc
                                    <label>{{currentDwEmployee.rfc}}</label> será dado de baja.
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Salir</button>
                            <button type="button" class="btn btn-danger" @click="changeEmployeeStatus">Aceptar</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="reactivacionModal" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Reactivaciòn</h4>
                        </div>
                        <div class="modal-body">
                            <br>
                            <div class="row">
                                <div class="col-xs-12">
                                    <label>Ùltima asignaciòn</label>
                                </div>
                                <div class="col-xs-12">
                                    <table class="table table-striped"
                                           v-if="role.idRole == 80 || role.idRole == 81 || role.idRole == 64 || role.idRole == 63">
                                        <thead>
                                        <th class="col-xs-2">Distribuidor</th>
                                        <th class="col-xs-2">Region</th>
                                        <th class="col-xs-2">Zona</th>
                                        <th class="col-xs-2">Sucursal</th>
                                        <th class="col-xs-2">Área</th>
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
                                                {{dwEnterprise.zona.name}}
                                            </td>
                                            <td class="col-xs-2">
                                                {{dwEnterprise.branch.branchShort}}
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

                                    <table class="table table-striped" v-if="role.idRole == 62">
                                        <thead>
                                        <th class="col-xs-2">Distribuidor</th>
                                        <th class="col-xs-2">Region</th>
                                        <th class="col-xs-2">Zona</th>
                                        <th class="col-xs-2">Área</th>
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
                                                {{dwEnterprise.zona.name}}
                                            </td>
                                            <td class="col-xs-2">
                                                {{dwEnterprise.branch.branchShort}}
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

                                    <table class="table table-striped" v-if="role.idRole == 61">
                                        <thead>
                                        <th class="col-xs-2">Distribuidor</th>
                                        <th class="col-xs-2">Region</th>
                                        <th class="col-xs-2">Área</th>
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
                                                {{dwEnterprise.area.areaName}}
                                            </td>
                                            <td class="col-xs-2">
                                                {{role.roleName}}
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>

                                    <table class="table table-striped"
                                           v-show="dwEnterprise.distributor.saemFlag == false">
                                        <thead>
                                        <th class="col-xs-2">Empresa</th>
                                        <th class="col-xs-2">Sucursal</th>
                                        <th class="col-xs-2">Área</th>
                                        <th class="col-xs-2">Puesto</th>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td class="col-xs-2">
                                                {{dwEnterprise.distributor.distributorName}}
                                            </td>
                                            <td class="col-xs-2">
                                                {{dwEnterprise.branch.branchShort}}
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
                                    <label>Distribuidor/Empresa</label>
                                    <select v-model="selectedOptions.distributor" class="form-control"
                                            @change="distributorChanged" :disabled="checkLastAssign == true">
                                        <option></option>
                                        <option v-for="distributor in distributors"
                                                :value="distributor">
                                            {{ distributor.distributorName }}
                                        </option>
                                    </select>
                                </div>
                                <div>
                                    <div class="col-xs-3">
                                        <label>Área</label>
                                        <select v-model="selectedOptions.area" class="form-control"
                                                :required="selectedOptions.distributor.idDistributor > 0"
                                                @change="areaChanged"
                                                :disabled="selectedOptions.distributor.idDistributor <= 0">
                                            <option v-for="area in areas"
                                                    :value="area">
                                                {{ area.areaName }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Puesto</label>
                                        <select v-model="selectedOptions.role" class="form-control"
                                                :required="selectedOptions.distributor.idDistributor > 0"
                                                @change="roleChanged()"
                                                :disabled="selectedOptions.area.idArea == 0">
                                            <option v-for="role in selectedArea"
                                                    :value="role.role">
                                                {{ role.role.roleName }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-3 checkbox">
                                        <label>
                                            <input type="checkbox" v-model="checkLastAssign" @change="cleanAndBuild()">
                                            Asignaciòn anterior
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="row"
                                 v-if="selectedOptions.role.idRole == 80 || selectedOptions.role.idRole == 81 || selectedOptions.role.idRole == 64">
                                <div class="col-xs-4">
                                    <label>Región</label>
                                    <select v-model="selectedOptions.region" class="form-control"
                                            required @change="regionChanged"
                                            :disabled="selectedOptions.distributor.idDistributor == 0">
                                        <option v-for="region in regions"
                                                :value="region">
                                            {{ region.regionName }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-4">
                                    <label>Zona</label>
                                    <select v-model="selectedOptions.zona" class="form-control"
                                            required @change="zonaChanged"
                                            :disabled="selectedOptions.region.idRegion == 0">
                                        <option v-for="zona in zonas"
                                                :value="zona">
                                            {{ zona.name }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-4">
                                    <label>Sucursal</label>
                                    <select v-model="selectedOptions.branch" class="form-control"
                                            required
                                            :disabled="selectedOptions.zona.idZonas == 0">
                                        <option v-for="branch in branchch"
                                                :value="branch">
                                            {{ branch.branchShort }}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="row"
                                 v-if="selectedOptions.role.idRole == 63">
                                <div class="col-xs-4">
                                    <label>Región</label>
                                    <select v-model="selectedOptions.region" class="form-control"
                                            required @change="regionChanged"
                                            :disabled="selectedOptions.distributor.idDistributor == 0">
                                        <option v-for="region in regions"
                                                :value="region">
                                            {{ region.regionName }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-4">
                                    <label>Zona</label>
                                    <select v-model="selectedOptions.zona" class="form-control"
                                            required @change="zonaChanged"
                                            :disabled="selectedOptions.region.idRegion == 0">
                                        <option v-for="zona in zonas"
                                                :value="zona">
                                            {{ zona.name }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-4">
                                    <label>Sucursal</label>
                                    <select v-model="selectedOptions.branch" class="form-control"
                                            required @change="validateRoleBranch()"
                                            :disabled="selectedOptions.zona.idZonas == 0">
                                        <option v-for="branch in branchch"
                                                :value="branch">
                                            {{ branch.branchShort }}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="row" v-if="selectedOptions.role.idRole == 62">
                                <div class="col-xs-4">
                                    <label>Región</label>
                                    <select v-model="selectedOptions.region" class="form-control"
                                            required @change="regionChanged"
                                            :disabled="selectedOptions.distributor.idDistributor == 0">
                                        <option v-for="region in regions"
                                                :value="region">
                                            {{ region.regionName }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-4">
                                    <label>Zona</label>
                                    <select v-model="selectedOptions.zona" class="form-control"
                                            required @change="validateRoleZona()"
                                            :disabled="selectedOptions.region.idRegion == 0">
                                        <option v-for="zona in zonas"
                                                :value="zona">
                                            {{ zona.name }}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="row" v-if="selectedOptions.role.idRole == 61">
                                <div class="col-xs-4">
                                    <label>Región</label>
                                    <select v-model="selectedOptions.region" class="form-control"
                                            required @change="validateRoleRegion()"
                                            :disabled="selectedOptions.distributor.idDistributor == 0">
                                        <option v-for="region in regions"
                                                :value="region">
                                            {{ region.regionName }}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="row" v-if="selectedOptions.distributor.saemFlag == false">
                                <div class="col-xs-4">
                                    <label>Sucursal</label>
                                    <select v-model="selectedOptions.branch" class="form-control"
                                            :required="selectedOptions.distributor.idDistributor > 0"
                                            :disabled="selectedOptions.area.idArea == 0">
                                        <option v-for="branch in branchch"
                                                :value="branch">
                                            {{ branch.branchShort }}
                                        </option>
                                    </select>
                                </div>
                                <br>
                            </div>
                            <br>
                            <div class="modal-footer" v-if="checkLastAssign == true || selectedOptions.role.idRole > 0">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Salir</button>
                                <button type="button" class="btn btn-success" @click="reactivationEmployee()">
                                    Reactivar
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
