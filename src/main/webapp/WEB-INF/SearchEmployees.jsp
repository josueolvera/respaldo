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
                data: {
                    updateEmployeeUrl: ROOT_URL + '/saem/update-employee?idDwEmployee=',
                    status : null,
                    dwEmployees: [],
                    employeesHistories:[],
                    hierarchy: [],
                    searchSelectedOptions: {
                        area: null,
                        distributor: null,
                        region: null,
                        branch: null,
                        role: null,
                        name: '',
                        rfc:''
                    },
                    selectedArea:{},
                    defaultArea: {
                        id:0,
                        name:''
                    },
                    defaultDistributor: {
                        id:0,
                        name:''
                    },
                    defaultRegion: {
                        id:0,
                        name:''
                    },
                    defaultBranch: {
                        id:0,
                        name:''
                    },
                    defaultRole: {
                        idRole:0,
                        roleName:''
                    },
                    employeesUrl : null
                },
                methods: {
                    setEmployeesUrl : function (status) {

                        if (status === 1) {
                            this.employeesUrl = '/dw-employees';
                        } else if (status === 2) {
                            this.employeesUrl = '/employees-history';
                        }

                        if (this.searchSelectedOptions.distributor.id != 0) {
                            this.setEmployeesUrlCharacters();
                            this.employeesUrl += 'idDistributor=' +
                                    this.searchSelectedOptions.distributor.id;
                        }
                        if (this.searchSelectedOptions.region.id != 0) {
                            this.setEmployeesUrlCharacters();
                            this.employeesUrl += 'idRegion=' +
                                    this.searchSelectedOptions.region.id;
                        }
                        if (this.searchSelectedOptions.branch.id != 0) {
                            this.setEmployeesUrlCharacters();
                            this.employeesUrl += 'idBranch=' +
                                    this.searchSelectedOptions.branch.id;
                        }
                        if (this.searchSelectedOptions.area.id != 0) {
                            this.setEmployeesUrlCharacters();
                            this.employeesUrl += 'idArea=' +
                                    this.searchSelectedOptions.area.id;
                        }
                        if (this.searchSelectedOptions.role.idRole != 0) {
                            this.setEmployeesUrlCharacters();
                            this.employeesUrl += 'idRole=' +
                                    this.searchSelectedOptions.role.idRole;
                        }
                        console.log(this.employeesUrl);
                    },
                    setEmployeesUrlCharacters : function () {
                        if(this.employeesUrl.indexOf('?') > -1)
                        {
                            this.employeesUrl += '&';
                        } else {
                            this.employeesUrl += '?';
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

                          this.employeesHistories = [];
                          this.dwEmployees = data;
                      }).error(function (data) {

                      });
                    },
                    getEmployees : function () {
                        if (this.status === 1) {
                            this.setEmployeesUrl(this.status);
                            this.getDwEmployees();
                        } else if (this.status === 2) {
                            this.setEmployeesUrl(this.status);
                            this.getEmployeesHistory();
                        }
                    },
                    getEmployeesHistory : function () {
                        this.$http.get(
                                ROOT_URL + this.employeesUrl
                        ).success(function (data) {
                            this.dwEmployees = [];
                            this.employeesHistories = data;
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
                    },
                    regionChanged: function () {
                        this.searchSelectedOptions.branch = this.defaultBranch;
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                    },
                    branchChanged: function () {
                        this.searchSelectedOptions.area = this.defaultArea;
                        this.searchSelectedOptions.role = this.defaultRole;
                    },
                    areaChanged: function () {
                        this.searchSelectedOptions.role = this.defaultRole;
                        this.$http.get(ROOT_URL + "/areas/area-role/" + this.searchSelectedOptions.area.id).success(function (data) {
                            this.selectedArea = data;
                        });
                    },
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="col-md-12">
                <h2>Busqueda de Empleado</h2>
                <br>
                <br>
                <div class="row">
                    <div class="col-md-2">
                        <label>Status</label>
                        <select v-model="status" class="form-control">
                            <option selected :value="1">Activo</option>
                            <option :value="2">Inactivo</option>
                        </select>
                    </div>
                    <div class="col-xs-2">
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
                    <div class="col-xs-2">
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
                    <div class="col-xs-2">
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
                    <div class="col-xs-2">
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
                    <div class="col-md-3">
                        <label>Nombre</label>
                        <input v-model="searchSelectedOptions.name" type="text" class="form-control">
                    </div>
                    <div class="col-md-3">
                        <label>RFC</label>
                        <input v-model="searchSelectedOptions.rfc" type="text" class="form-control">
                    </div>
                    <div class="col-md-3">
                        <button style="margin-top: 25px" class="btn btn-info" @click="getEmployees">
                            Buscar
                        </button>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <br>
                <div class="panel panel-default" v-if="dwEmployees.length > 0 || employeesHistories.length > 0">
                    <!-- Default panel contents -->
                    <!-- Table -->
                    <div class="table-responsive" v-if="dwEmployees.length > 0">
                        <table class="table table-condensed table-hover table-striped">
                            <thead>
                            <tr>
                                <th>
                                    Nombre de empleado
                                </th>
                                <th>
                                    RFC
                                </th>
                                <th>
                                    Mail
                                </th>
                                <th>
                                    Puesto
                                </th>
                                <th>
                                    Sucursal
                                </th>
                                <th>
                                    Region
                                </th>
                                <th>
                                    Empresa
                                </th>
                                <th>
                                    Fecha de ingreso
                                </th>
                                <th>
                                    Edición
                                </th>
                                <th>
                                    Baja
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="dwEmployee in dwEmployees |
                            filterBy searchSelectedOptions.name in 'employee.fullName'|
                            filterBy searchSelectedOptions.rfc in 'employee.rfc'">
                                <td>
                                    {{dwEmployee.employee.fullName}}
                                </td>
                                <td>
                                    {{dwEmployee.employee.rfc}}
                                </td>
                                <td>
                                    {{dwEmployee.employee.mail}}
                                </td>
                                <td>
                                    {{dwEmployee.role.roleName}}
                                </td>
                                <td>
                                    {{dwEmployee.dwEnterprise.region.regionName}}
                                </td>
                                <td>
                                    {{dwEmployee.dwEnterprise.branch.branchName}}
                                </td>
                                <td>
                                    {{dwEmployee.dwEnterprise.distributor.distributorName}}
                                </td>
                                <td>
                                    {{dwEmployee.employee.joinDateFormats.dateNumber}}
                                </td>
                                <td>
                                    <a class="btn btn-default btn-sm" :href="updateEmployeeUrl + dwEmployee.idDwEmployee"
                                            data-toggle="tooltip" data-placement="top" title="Modificar">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </a>
                                </td>
                                <td>
                                    <button class="btn btn-default btn-sm"
                                            data-toggle="tooltip" data-placement="top" title="Eliminar">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="table-responsive" v-if="employeesHistories.length > 0">
                        <table class="table table-condensed table-hover table-striped">
                            <thead>
                            <tr>
                                <th>
                                    Nombre de empleado
                                </th>
                                <th>
                                    RFC
                                </th>
                                <th>
                                    Mail
                                </th>
                                <th>
                                    Puesto
                                </th>
                                <th>
                                    Sucursal
                                </th>
                                <th>
                                    Region
                                </th>
                                <th>
                                    Empresa
                                </th>
                                <th>
                                    Fecha de ingreso
                                </th>
                                <th>
                                    Reactivacion
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="employeesHistory in employeesHistories |
                            filterBy searchSelectedOptions.name in 'fullName'|
                            filterBy searchSelectedOptions.rfc in 'rfc'">
                                <td>
                                    {{employeesHistory.fullName}}
                                </td>
                                <td>
                                    {{employeesHistory.rfc}}
                                </td>
                                <td>
                                    {{employeesHistory.mail}}
                                </td>
                                <td>
                                    {{dwEmployee.role.roleName}}
                                </td>
                                <td>
                                    {{dwEmployee.dwEnterprise.region.regionName}}
                                </td>
                                <td>
                                    {{dwEmployee.dwEnterprise.branch.branchName}}
                                </td>
                                <td>
                                    {{dwEmployee.dwEnterprise.distributor.distributorName}}
                                </td>
                                <td>
                                    {{dwEmployee.employee.joinDateFormats.dateNumber}}
                                </td>
                                <td>
                                    <button class="btn btn-default btn-sm"
                                            data-toggle="tooltip" data-placement="top" title="Reactivar">
                                        <span class="glyphicon glyphicon-refresh"></span>
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
