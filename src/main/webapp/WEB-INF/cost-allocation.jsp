<%--
  Created by IntelliJ IDEA.
  User: Kevin Salvador
  Date: 14/12/2016
  Time: 10:25 AM
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
                    setPercentageUrl: ROOT_URL+ '/siad/cost-client?idDwEmployee=',
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
                    employeesUrl: null,
                    dateTimePickerStart: '',
                    dateTimePickerEnd: '',
                    isThereItems: false,
                    currentDwEmployee: {},
                    nameSearch: '',
                    rfcSearch: '',
                    numEmployeeSearch: '',
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
                    multilevelEmployee: null
                },
                methods: {
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
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
                        if (this.numEmployeeSearch != '') {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idEmployee=' +
                                    this.numEmployeeSearch;
                            this.createReportUrl += 'idEmployee=' +
                                    this.numEmployeeSearch;
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
                            }else {
                                showAlert("No hay datos para esa busqueda, intente con otra combinaciòn", {type: 3});
                                setInterval(function () {
                                    location.reload();
                                }, 3000);
                            }
                        }).error(function (data) {
                            showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
                        });
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
                <div>
                    <div class="row">
                        <div class="col-md-2">
                            <label>Nombre</label>
                            <input v-model="nameSearch" type="text" class="form-control"
                                   placeholder="Nombre del empleado">
                        </div>
                        <div class="col-md-2">
                            <label>RFC</label>
                            <input v-model="rfcSearch" type="text" class="form-control" placeholder="RFC">
                        </div>
                        <div class="col-md-2">
                            <label>No. Empleado</label>
                            <input v-model="numEmployeeSearch" type="text" class="form-control" placeholder="No. Empleado">
                        </div>
                        <div class="col-md-2">
                            <button style="margin-top: 25px" class="btn btn-info" @click="getEmployees">
                                Buscar
                            </button>
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
                    <!-- Table -->
                    <div class="flex-box container-fluid" v-if="dwEmployees.length > 0">
                        <div class="row table-header active">
                            <div class="col-md-2"><b>Nombre de empleado</b></div>
                            <div class="col-md-2"><b>RFC</b></div>
                            <div class="col-md-2"><b>Puesto</b></div>
                            <div class="col-md-2"><b>Sucursal</b></div>
                            <div class="col-md-2"><b>Fecha de ingreso</b></div>
                            <div class="col-md-2"><b>Asignar porcentaje</b></div>
                        </div>
                        <br>
                        <div class="table-body flex-row flex-content">
                            <div class="row table-row" v-for="dwEmployee in dwEmployees">
                                <div class="col-md-2">{{dwEmployee.fullName}}</div>
                                <div class="col-md-2">{{dwEmployee.rfc}}</div>
                                <div class="col-md-2">{{dwEmployee.rolesR.roleName}}</div>
                                <div class="col-md-2">{{dwEmployee.dwEnterprisesR.branch.branchShort}}</div>
                                <div class="col-md-2">
                                    <center>{{dwEmployee.joinDateFormats.simpleDate}}</center>
                                </div>
                                <div class="col-md-1">
                                    <center><a class="btn btn-default btn-sm"
                                               :href="setPercentageUrl + dwEmployee.idDwEmployee"
                                               data-toggle="tooltip" data-placement="top" title="Asignar">
                                                <%--v-if="dwEmployee.idActionType != 2"--%>
                                        <span class="glyphicon glyphicon-pencil"></span>
                                    </a></center>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>