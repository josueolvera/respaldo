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

<t:template pageTitle="BID Group: Reportes SAEM">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.activateDateTimePickerStart();
                    this.getDistributors();
                    this.getRegions();
                    this.getZonas();
                    this.getBranchs();
                    this.getAreas();
                },
                data: {
                    updateEmployeeUrl: ROOT_URL + '/saem/update-employee?idDwEmployee=',
                    status: null,
                    dwEmployees: [],
                    employeesHistories: [],
                    hierarchy: [],
                    searching: false,
                    searchSelectedOptions: {
                        area: {
                            idArea: 0,
                            name: 'TODOS'
                        },
                        distributor: {
                            idDistributor: 0,
                            name: 'TODOS'
                        },
                        region: {
                            idRegion: 0,
                            name: 'TODOS'
                        },
                        zona:  {
                            idZonas: 0,
                            name: 'TODOS'
                        },
                        branch:  {
                            idBranch: 0,
                            name: 'TODOS'
                        },
                        role:  {
                            idRole: 0,
                            name: 'TODOS'
                        },
                        report: null,
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
                    reportDistributors: {
                        idReporte: 1,
                        name: 'Reporte operativo'
                    },
                    reportCompanys: {
                        idReporte: 2,
                        name: 'Reporte corporativo'
                    },
                    reportBPO: {
                        idReporte: 3,
                        name: 'Reporte BPO'
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
                    dwEnterprises: []
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
                            this.reportFileName = '';
                            location.href = createReportUrl;
                        } else {
                            showAlert("Debe asignar un nombre de archivo", {type: 3});
                            return;
                        }
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

                        if (this.searchSelectedOptions.distributor.idDistributor != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idDistributor=' +
                                    this.searchSelectedOptions.distributor.idDistributor;
                            this.createReportUrl += 'idDistributor=' +
                                    this.searchSelectedOptions.distributor.idDistributor;
                        }

                        if (this.searchSelectedOptions.region.idRegion != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idRegion=' +
                                    this.searchSelectedOptions.region.idRegion;
                            this.createReportUrl += 'idRegion=' +
                                    this.searchSelectedOptions.region.idRegion;
                        }

                        if (this.searchSelectedOptions.zona.idZonas != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idZona=' +
                                    this.searchSelectedOptions.zona.idZonas;
                            this.createReportUrl += 'idZona=' +
                                    this.searchSelectedOptions.zona.idZonas;
                        }

                        if (this.searchSelectedOptions.branch.idBranch != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idBranch=' +
                                    this.searchSelectedOptions.branch.idBranch;
                            this.createReportUrl += 'idBranch=' +
                                    this.searchSelectedOptions.branch.idBranch;
                        }

                        if (this.searchSelectedOptions.area.idArea != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idArea=' +
                                    this.searchSelectedOptions.area.idArea;
                            this.createReportUrl += 'idArea=' +
                                    this.searchSelectedOptions.area.idArea;
                        }

                        if (this.searchSelectedOptions.report.idReporte != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idReport=' +
                                    this.searchSelectedOptions.report.idReporte;
                            this.createReportUrl += 'idReport=' +
                                    this.searchSelectedOptions.report.idReporte;
                        }

                        if (this.searchSelectedOptions.role.idRole != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idRole=' +
                                    this.searchSelectedOptions.role.idRole;
                            this.createReportUrl += 'idRole=' +
                                    this.searchSelectedOptions.role.idRole;
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
                                showAlert("Selecciona una fecha final", {type: 3});
                                return false;
                            }
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
                        if (this.status.value === 1) {
                            if (this.setEmployeesUrl(this.status.value)) {
                                this.getEmployeesHistory();
                            }
                        } else if (this.status.value === 2) {
                            if (this.setEmployeesUrl(this.status.value)) {
                                this.getEmployeesHistory();
                            }
                        } else if (this.status.value === 0) {
                            if (this.setEmployeesUrl(this.status.value)) {
                                this.getEmployeesHistory();
                            }
                        }
                    },
                    getEmployeesHistory: function () {
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
                                this.emptyFields();
                            }
                        }).error(function (data) {
                            showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
                        });
                    },
                    distributorChanged: function () {
                        var self = this;
                        this.searchSelectedOptions.region = this.defaultRegion;
                        this.searchSelectedOptions.zona = this.defaultZona;
                        this.searchSelectedOptions.branch = this.defaultBranch;
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                        if(this.searchSelectedOptions.distributor.idDistributor > 0){
                            this.$http.get(ROOT_URL + "/dw-enterprises/distributor-saem/"+this.searchSelectedOptions.distributor.idDistributor+"/0").success(function (data) {
                               this.regions = [];
                                this.zonas = [];
                                this.branchch = [];
                                this.areas = [];
                                var index;
                                data.forEach(function (element) {
                                    index = self.arrayObjectIndexOf(self.regions, element.region.idRegion, 'idRegion');
                                    if (index == -1) self.regions.push(element.region);
                                    index = self.arrayObjectIndexOf(self.zonas, element.zona.idZonas, 'idZonas');
                                    if (index == -1) self.zonas.push(element.zona);
                                    index = self.arrayObjectIndexOf(self.branchch, element.branch.idBranch, 'idBranch');
                                    if (index == -1) self.branchch.push(element.branch);
                                    index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                    if (index == -1) self.areas.push(element.area);
                                });
                            }).error(function () {
                                showAlert("No existe informacion para ese distribuidor", {type: 3});
                            });
                        }else{
                            this.$http.get(ROOT_URL + "/dw-enterprises/distributor-saem/"+this.searchSelectedOptions.distributor.idDistributor+"/1").success(function (data) {
                                this.regions = [];
                                this.zonas = [];
                                this.branchch = [];
                                this.areas = [];
                                var index;
                                data.forEach(function (element) {
                                    index = self.arrayObjectIndexOf(self.regions, element.region.idRegion, 'idRegion');
                                    if (index == -1) self.regions.push(element.region);
                                    index = self.arrayObjectIndexOf(self.zonas, element.zona.idZonas, 'idZonas');
                                    if (index == -1) self.zonas.push(element.zona);
                                    index = self.arrayObjectIndexOf(self.branchch, element.branch.idBranch, 'idBranch');
                                    if (index == -1) self.branchch.push(element.branch);
                                    index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                    if (index == -1) self.areas.push(element.area);
                                });
                            }).error(function () {
                                showAlert("No existe informacion para ese distribuidor", {type: 3});
                            });
                        }
                    },
                    regionChanged: function () {
                        var self = this;
                        this.searchSelectedOptions.zona = this.defaultZona;
                        this.searchSelectedOptions.branch = this.defaultBranch;
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                        if(this.searchSelectedOptions.distributor.idDistributor > 0 && this.searchSelectedOptions.region.idRegion > 0){
                            this.$http.get(ROOT_URL + "/dw-enterprises?idDistributor="
                                    +this.searchSelectedOptions.distributor.idDistributor+"&idRegion="+this.searchSelectedOptions.region.idRegion)
                                    .success(function (data) {
                                        this.zonas = [];
                                        this.branchch = [];
                                        this.areas = [];
                                        var index;
                                        data.forEach(function (element) {
                                            index = self.arrayObjectIndexOf(self.zonas, element.zona.idZonas, 'idZonas');
                                            if (index == -1) self.zonas.push(element.zona);
                                            index = self.arrayObjectIndexOf(self.branchch, element.branch.idBranch, 'idBranch');
                                            if (index == -1) self.branchch.push(element.branch);
                                            index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                            if (index == -1) self.areas.push(element.area);
                                        });
                                    }).error(function () {
                                showAlert("No existe informacion para esa regiòn", {type: 3});
                            });
                        }else {
                            if(this.searchSelectedOptions.region.idRegion > 0){
                                this.$http.get(ROOT_URL + "/dw-enterprises/region-saem/"+this.searchSelectedOptions.region.idRegion+"/0").success(function (data) {
                                    this.zonas = [];
                                    this.branchch = [];
                                    this.areas = [];
                                    var index;
                                    data.forEach(function (element) {
                                        index = self.arrayObjectIndexOf(self.zonas, element.zona.idZonas, 'idZonas');
                                        if (index == -1) self.zonas.push(element.zona);
                                        index = self.arrayObjectIndexOf(self.branchch, element.branch.idBranch, 'idBranch');
                                        if (index == -1) self.branchch.push(element.branch);
                                        index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                        if (index == -1) self.areas.push(element.area);
                                    });
                                }).error(function () {
                                    showAlert("No existe informacion para esa regiòn", {type: 3});
                                });
                            }else{
                                this.$http.get(ROOT_URL + "/dw-enterprises/region-saem/"+this.searchSelectedOptions.region.idRegion+"/1").success(function (data) {
                                    this.zonas = [];
                                    this.branchch = [];
                                    this.areas = [];
                                    var index;
                                    data.forEach(function (element) {
                                        index = self.arrayObjectIndexOf(self.zonas, element.zona.idZonas, 'idZonas');
                                        if (index == -1) self.zonas.push(element.zona);
                                        index = self.arrayObjectIndexOf(self.branchch, element.branch.idBranch, 'idBranch');
                                        if (index == -1) self.branchch.push(element.branch);
                                        index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                        if (index == -1) self.areas.push(element.area);
                                    });
                                }).error(function () {
                                    showAlert("No existe informacion para esa regiòn", {type: 3});
                                });
                            }
                        }
                    },
                    zonaChanged: function () {
                        var self = this;
                        this.searchSelectedOptions.branch = this.defaultBranch;
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                        if(this.searchSelectedOptions.distributor.idDistributor > 0 && this.searchSelectedOptions.region.idRegion > 0
                                && this.searchSelectedOptions.zona.idZonas >0){
                            this.$http.get(ROOT_URL + "/dw-enterprises?idDistributor="
                                    +this.searchSelectedOptions.distributor.idDistributor+"&idRegion="+this.searchSelectedOptions.region.idRegion
                                    +"&idZona="+this.searchSelectedOptions.zona.idZonas)
                                    .success(function (data) {
                                        this.branchch = [];
                                        this.areas = [];
                                        var index;
                                        data.forEach(function (element) {
                                            index = self.arrayObjectIndexOf(self.branchch, element.branch.idBranch, 'idBranch');
                                            if (index == -1) self.branchch.push(element.branch);
                                            index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                            if (index == -1) self.areas.push(element.area);
                                        });
                                    }).error(function () {
                                showAlert("No existe informacion para esa zona", {type: 3});
                            });
                        }else {
                            if(this.searchSelectedOptions.zona.idZonas > 0){
                                this.$http.get(ROOT_URL + "/dw-enterprises/zona-saem/"+this.searchSelectedOptions.zona.idZonas+"/0").success(function (data) {
                                    this.branchch = [];
                                    this.areas = [];
                                    var index;
                                    data.forEach(function (element) {
                                        index = self.arrayObjectIndexOf(self.branchch, element.branch.idBranch, 'idBranch');
                                        if (index == -1) self.branchch.push(element.branch);
                                        index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                        if (index == -1) self.areas.push(element.area);
                                    });
                                }).error(function () {
                                    showAlert("No existe informacion para esa zona", {type: 3});
                                });
                            }else{
                                this.$http.get(ROOT_URL + "/dw-enterprises/zona-saem/"+this.searchSelectedOptions.zona.idZonas+"/1").success(function (data) {
                                    this.branchch = [];
                                    this.areas = [];
                                    var index;
                                    data.forEach(function (element) {
                                        index = self.arrayObjectIndexOf(self.branchch, element.branch.idBranch, 'idBranch');
                                        if (index == -1) self.branchch.push(element.branch);
                                        index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                        if (index == -1) self.areas.push(element.area);
                                    });
                                }).error(function () {
                                    showAlert("No existe informacion para esa zona", {type: 3});
                                });
                            }
                        }
                    },
                    branchChanged: function () {
                        var self = this;
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                        if(this.searchSelectedOptions.distributor.idDistributor > 0 && this.searchSelectedOptions.region.idRegion > 0
                                && this.searchSelectedOptions.zona.idZonas > 0 && this.searchSelectedOptions.branch.idBranch > 0){
                            this.$http.get(ROOT_URL + "/dw-enterprises?idDistributor="
                                    +this.searchSelectedOptions.distributor.idDistributor+"&idRegion="+this.searchSelectedOptions.region.idRegion
                                    +"&idZona="+this.searchSelectedOptions.zona.idZonas+"&idBranch="+this.searchSelectedOptions.branch.idBranch)
                                    .success(function (data) {
                                        this.areas = [];
                                        var index;
                                        data.forEach(function (element) {
                                            index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                            if (index == -1) self.areas.push(element.area);
                                        });
                                    }).error(function () {
                                showAlert("No existe informacion para esta sucursal", {type: 3});
                            });
                        }else {
                            if(this.searchSelectedOptions.branch.idBranch > 0){
                                this.$http.get(ROOT_URL + "/dw-enterprises/branch-saem/"+this.searchSelectedOptions.branch.idBranch+"/0").success(function (data) {
                                    this.areas = [];
                                    var index;
                                    data.forEach(function (element) {
                                        index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                        if (index == -1) self.areas.push(element.area);
                                    });
                                }).error(function () {
                                    showAlert("No existe informacion para esta sucursal", {type: 3});
                                });
                            }else{
                                this.$http.get(ROOT_URL + "/dw-enterprises/branch-saem/"+this.searchSelectedOptions.branch.idBranch+"/1").success(function (data) {
                                    this.areas = [];
                                    var index;
                                    data.forEach(function (element) {
                                        index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                        if (index == -1) self.areas.push(element.area);
                                    });
                                }).error(function () {
                                    showAlert("No existe informacion para esta sucursal", {type: 3});
                                });
                            }
                        }
                    },
                    areaChanged: function () {
                        var self = this;
                        this.searchSelectedOptions.role = this.defaultRole;
                        if (this.searchSelectedOptions.distributor.idDistributor > 0){
                            this.$http.get(ROOT_URL + "/distributor-area-rol/" + this.searchSelectedOptions.distributor.idDistributor + "/" + this.searchSelectedOptions.area.idArea).success(function (data) {
                                this.selectedArea = data;
                            });
                        }else {
                            this.$http.get(ROOT_URL + "/distributor-area-rol/" + this.searchSelectedOptions.area.idArea).success(function (data) {
                                this.selectedArea = [];
                                var index;
                                data.forEach(function (element) {
                                    index = self.arrayObjectIndexOf(self.selectedArea, element.role.idRole, 'idRole');
                                    if (index == -1) self.selectedArea.push(element);
                                });
                            });
                        }
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
                    getDistributors: function () {
                        this.$http.get(ROOT_URL + "/distributors?forStock=true").success(function (data) {
                            this.distributors = data;
                        });
                    },
                    getRegions: function () {
                        this.$http.get(ROOT_URL + "/regions").success(function (data) {
                            this.regions = data;
                        });
                    },
                    getZonas: function () {
                        this.$http.get(ROOT_URL + "/zonas").success(function (data) {
                            this.zonas = data;
                        });
                    },
                    getBranchs: function () {
                        this.$http.get(ROOT_URL + "/branchs").success(function (data) {
                            this.branchch = data;
                        });
                    },
                    getAreas: function () {
                        this.$http.get(ROOT_URL + "/areas").success(function (data) {
                            this.areas = data;
                        });
                    },
                    onExportButton: function () {
                        $("#exportModal").modal("show");
                    },
                    distributorChangedCorp: function () {
                        var self = this;
                        this.searchSelectedOptions.region = this.defaultRegion;
                        this.searchSelectedOptions.zona = this.defaultZona;
                        this.searchSelectedOptions.branch = this.defaultBranch;
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                        if(this.searchSelectedOptions.distributor.idDistributor > 0){
                            this.$http.get(ROOT_URL + "/dw-enterprises/distributor-saem/"+this.searchSelectedOptions.distributor.idDistributor+"/0").success(function (data) {
                                this.branchch = [];
                                this.areas = [];
                                var index;
                                data.forEach(function (element) {
                                    index = self.arrayObjectIndexOf(self.branchch, element.branch.idBranch, 'idBranch');
                                    if (index == -1) self.branchch.push(element.branch);
                                    index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                    if (index == -1) self.areas.push(element.area);
                                });
                            }).error(function () {
                                showAlert("No existe informacion para ese distribuidor", {type: 3});
                            });
                        }else{
                            this.$http.get(ROOT_URL + "/dw-enterprises/distributor-saem/"+this.searchSelectedOptions.distributor.idDistributor+"/2").success(function (data) {
                                this.branchch = [];
                                this.areas = [];
                                var index;
                                data.forEach(function (element) {
                                    index = self.arrayObjectIndexOf(self.branchch, element.branch.idBranch, 'idBranch');
                                    if (index == -1) self.branchch.push(element.branch);
                                    index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                    if (index == -1) self.areas.push(element.area);
                                });
                            }).error(function () {
                                showAlert("No existe informacion para ese distribuidor", {type: 3});
                            });
                        }
                    },
                    branchChangedCorp: function () {
                        var self = this;
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                        if(this.searchSelectedOptions.distributor.idDistributor > 0 && this.searchSelectedOptions.branch.idBranch > 0){
                            this.$http.get(ROOT_URL + "/dw-enterprises?idDistributor=" +this.searchSelectedOptions.distributor.idDistributor+"&idBranch="+this.searchSelectedOptions.branch.idBranch)
                                    .success(function (data) {
                                        this.areas = [];
                                        var index;
                                        data.forEach(function (element) {
                                            index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                            if (index == -1) self.areas.push(element.area);
                                        });
                                    }).error(function () {
                                showAlert("No existe informacion para esta sucursal", {type: 3});
                            });
                        }else {
                            if(this.searchSelectedOptions.branch.idBranch > 0){
                                this.$http.get(ROOT_URL + "/dw-enterprises/branch-saem/"+this.searchSelectedOptions.branch.idBranch+"/0").success(function (data) {
                                    this.areas = [];
                                    var index;
                                    data.forEach(function (element) {
                                        index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                        if (index == -1) self.areas.push(element.area);
                                    });
                                }).error(function () {
                                    showAlert("No existe informacion para esta sucursal", {type: 3});
                                });
                            }else{
                                this.$http.get(ROOT_URL + "/dw-enterprises/branch-saem/"+this.searchSelectedOptions.branch.idBranch+"/2").success(function (data) {
                                    this.areas = [];
                                    var index;
                                    data.forEach(function (element) {
                                        index = self.arrayObjectIndexOf(self.areas, element.area.idArea, 'idArea');
                                        if (index == -1) self.areas.push(element.area);
                                    });
                                }).error(function () {
                                    showAlert("No existe informacion para esta sucursal", {type: 3});
                                });
                            }
                        }
                    },
                    emptyFields: function () {
                        this.regions = [];
                        this.zonas = [];
                        this.branchch = [];
                        this.areas = [];
                        this.selectedArea = [];
                        this.dwEmployees = [];
                        this.getDistributors();
                        this.getRegions();
                        this.getZonas();
                        this.getBranchs();
                        this.getAreas();
                        this.searchSelectedOptions.distributor.idDistributor = 0;
                        this.searchSelectedOptions.distributor.name = "TODOS";
                        this.searchSelectedOptions.region.idRegion = 0;
                        this.searchSelectedOptions.region.name = "TODOS";
                        this.searchSelectedOptions.zona.idZonas = 0;
                        this.searchSelectedOptions.zona.name = "TODOS";
                        this.searchSelectedOptions.branch.idBranch = 0;
                        this.searchSelectedOptions.branch.name = "TODOS";
                        this.searchSelectedOptions.area.idArea = 0;
                        this.searchSelectedOptions.area.name = "TODOS";
                        this.searchSelectedOptions.role.idRole = 0;
                        this.searchSelectedOptions.role.name = "TODOS";
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
                        <h2>Generador de reportes</h2>
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
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-5">
                                <label>Selecciona un Reporte</label>
                                <select v-model="searchSelectedOptions.report" class="form-control" @change="emptyFields()">
                                    <option selected :value="reportDistributors">{{reportDistributors.name}}</option>
                                    <option selected :value="reportCompanys">{{reportCompanys.name}}</option>
                                    <option selected :value="reportBPO">{{reportBPO.name}}</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div v-if="searchSelectedOptions.report.idReporte == 1">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-2">
                                    <label>Distribuidor</label>
                                    <select v-model="searchSelectedOptions.distributor" class="form-control"
                                            @change="distributorChanged">
                                        <option selected :value="defaultDistributor">{{defaultDistributor.name}}</option>
                                        <option v-for="distributor in distributors" v-if="distributor.saemFlag == true"
                                                :value="distributor">
                                            {{ distributor.distributorName }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label>Región</label>
                                    <select v-model="searchSelectedOptions.region" class="form-control"
                                            @change="regionChanged"
                                            :disabled="searchSelectedOptions.distributor.idDistributor < 0">
                                        <option selected :value="defaultRegion">{{defaultRegion.name}}</option>
                                        <option v-for="region in regions"
                                                :value="region" v-if="region.saemFlag == 1">
                                            {{ region.regionName }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label>Zona</label>
                                    <select v-model="searchSelectedOptions.zona" class="form-control"
                                            @change="zonaChanged"
                                            :disabled="searchSelectedOptions.distributor.idDistributor < 0">
                                        <option selected :value="defaultZona">{{defaultZona.name}}</option>
                                        <option v-for="zona in zonas"
                                                :value="zona" v-if="zona.saemFlag == 1">
                                            {{ zona.name }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label>Sucursal</label>
                                    <select v-model="searchSelectedOptions.branch" class="form-control"
                                            @change="branchChanged"
                                            :disabled="searchSelectedOptions.distributor.idDistributor < 0">
                                        <option selected :value="defaultBranch">{{defaultBranch.name}}</option>
                                        <option v-for="branch in branchch"
                                                :value="branch" v-if="branch.saemFlag == 1">
                                            {{ branch.branchShort }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label>Área</label>
                                    <select v-model="searchSelectedOptions.area"
                                            class="form-control" @change="areaChanged"
                                            :disabled="searchSelectedOptions.distributor.idDistributor < 0">
                                        <option selected :value="defaultArea">{{defaultArea.name}}</option>
                                        <option v-for="area in areas"
                                                :value="area" v-if="area.saemFlag == 1">
                                            {{ area.areaName }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label>Puesto</label>
                                    <select v-model="searchSelectedOptions.role" class="form-control"
                                            :disabled="searchSelectedOptions.area.idArea == 0">
                                        <option selected :value="defaultRole">{{defaultRole.roleName}}</option>
                                        <option v-for="role in selectedArea"
                                                :value="role.role">
                                            {{ role.role.roleName }}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-2">
                                    <label>Status</label>
                                    <select v-model="status" class="form-control" @change="statusChanged">
                                        <option selected :value="allStatus">{{allStatus.name}}</option>
                                        <option selected :value="activeStatus">{{activeStatus.name}}</option>
                                        <option :value="inactiveStatus">{{inactiveStatus.name}}</option>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label>Nombre</label>
                                    <input v-model="nameSearch" type="text" class="form-control"
                                           placeholder="Nombre del Empleado">
                                </div>
                                <div class="col-md-2">
                                    <label>RFC</label>
                                    <input v-model="rfcSearch" type="text" class="form-control" placeholder="RFC">
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
                            <span class="input-group-addon"
                                  @click="activateDateTimePickerEnd(searchSelectedOptions.startDate)">
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
                    <div v-if="searchSelectedOptions.report.idReporte == 2">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-2">
                                    <label>Distribuidor</label>
                                    <select v-model="searchSelectedOptions.distributor" class="form-control"
                                            @change="distributorChangedCorp">
                                        <option selected :value="defaultDistributor">{{defaultDistributor.name}}</option>
                                        <option v-for="distributor in distributors" v-if="distributor.saemFlag == false"
                                                :value="distributor">
                                            {{ distributor.distributorName }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label>Sucursal</label>
                                    <select v-model="searchSelectedOptions.branch" class="form-control"
                                            @change="branchChangedCorp"
                                            :disabled="searchSelectedOptions.distributor.idDistributor < 0">
                                        <option selected :value="defaultBranch">{{defaultBranch.name}}</option>
                                        <option v-for="branch in branchch"
                                                :value="branch" v-if="branch.saemFlag == 0">
                                            {{ branch.branchShort }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label>Área</label>
                                    <select v-model="searchSelectedOptions.area"
                                            class="form-control" @change="areaChanged"
                                            :disabled="searchSelectedOptions.distributor.idDistributor < 0">
                                        <option selected :value="defaultArea">{{defaultArea.name}}</option>
                                        <option v-for="area in areas"
                                                :value="area" v-if="area.saemFlag == 0">
                                            {{ area.areaName }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label>Puesto</label>
                                    <select v-model="searchSelectedOptions.role" class="form-control"
                                            :disabled="searchSelectedOptions.area.idArea == 0">
                                        <option selected :value="defaultRole">{{defaultRole.roleName}}</option>
                                        <option v-for="role in selectedArea"
                                                :value="role.role">
                                            {{ role.role.roleName }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label>Status</label>
                                    <select v-model="status" class="form-control" @change="statusChanged">
                                        <option selected :value="allStatus">{{allStatus.name}}</option>
                                        <option selected :value="activeStatus">{{activeStatus.name}}</option>
                                        <option :value="inactiveStatus">{{inactiveStatus.name}}</option>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label>Nombre</label>
                                    <input v-model="nameSearch" type="text" class="form-control"
                                           placeholder="Nombre del Empleado">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-2">
                                    <label>RFC</label>
                                    <input v-model="rfcSearch" type="text" class="form-control" placeholder="RFC">
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
                            <span class="input-group-addon"
                                  @click="activateDateTimePickerEnd(searchSelectedOptions.startDate)">
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
                    <div v-if="searchSelectedOptions.report.idReporte == 3">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-2">
                                    <label>Status</label>
                                    <select v-model="status" class="form-control" @change="statusChanged">
                                        <option selected :value="allStatus">{{allStatus.name}}</option>
                                        <option :value="activeStatus">{{activeStatus.name}}</option>
                                        <option :value="inactiveStatus">{{inactiveStatus.name}}</option>
                                    </select>
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
                            <span class="input-group-addon"
                                  @click="activateDateTimePickerEnd(searchSelectedOptions.startDate)">
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
                            <div class="col-md-2"><b>Distribuidor</b></div>
                            <div class="col-md-2"><b>Sucursal</b></div>
                            <div class="col-md-1"><b>Puesto</b></div>
                            <div class="col-md-1"><b>Fecha de ingreso</b></div>
                            <div class="col-md-1"><b>Fecha de ultima modificaciòn</b></div>
                            <div class="col-md-1"><b>Estatus del empleado</b></div>
                        </div>
                        <br>
                        <div class="table-body flex-row flex-content">
                            <div class="row table-row" v-for="dwEmployee in dwEmployees">
                                <div class="col-md-2">{{dwEmployee.fullName}}</div>
                                <div class="col-md-2">{{dwEmployee.rfc}}</div>
                                <div class="col-md-2">{{dwEmployee.dwEnterprisesR.distributor.distributorName}}</div>
                                <div class="col-md-2">{{dwEmployee.dwEnterprisesR.branch.branchShort}}</div>
                                <div class="col-md-1">{{dwEmployee.rolesR.roleName}}</div>
                                <div class="col-md-1">
                                    <center>{{dwEmployee.joinDateFormats.simpleDate}}</center>
                                </div>
                                <div class="col-md-1">
                                    <center>{{dwEmployee.crationDateDateFormats.simpleDate}}</center>
                                </div>
                                <div class="col-md-1"><center>{{dwEmployee.actionTypesR.actionType}}</center></div>
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
        </div>
    </jsp:body>
</t:template>
