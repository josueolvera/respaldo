<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 27/06/16
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Actualizar empleado">
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
        </script>
        <script type="text/javascript">
            var vm= new Vue({
                el: '#content',
                ready: function () {
                    this.getDwEmployee();
                    this.obtainStates();
                    this.obtainEducation();
                    this.obtainStatusMarital();
                    this.obtainBanks();

                },
                data: {
                    dwEmployee:{},
                    idDwEmployee: ${idDwEmployee},
                    estadosMunicipios: [],
                    asentamiento: [],
                    education: [],
                    statusMarital: [],
                    estados: [],
                    banks: [],
                    account: [],
                    selectOptions: {
                        distributors: [],
                        areas: [],
                        hierarchy: [],
                        documentTypes: [],
                    },
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
                    defaultArea: {
                        id: 0,
                        name: ''
                    },
                    defaultDistributor: {
                        id: 0,
                        name: ''
                    },
                    defaultRegion: {
                        id: 0,
                        name: ''
                    },
                    defaultBranch: {
                        id: 0,
                        name: ''
                    },
                    defaultRole: {
                        id: 0,
                        name: ''
                    },
                },
                methods: {
                    getDwEmployee: function () {
                        this.$http.get(ROOT_URL + '/dw-employees/' + this.idDwEmployee)
                                .success(function (data) {
                                    this.dwEmployee = data;
                                    this.obtainAsentamientos();
                                    this.fetchHierarchy();
                                    this.$http.get(ROOT_URL + "/employees-accounts/employee/" + this.dwEmployee.employee.idEmployee).success(function (data) {
                                        this.account = data;
                                    });
                                })
                                .error(function (data) {

                                });
                    },
                    obtainStates: function () {
                        this.$http.get(ROOT_URL + "/states").success(function (data) {
                            this.estados = data;
                        });
                    },
                    obtainEducation: function () {
                        this.$http.get(ROOT_URL + "/education").success(function (data) {
                            this.education = data;
                        });
                    },
                    obtainStatusMarital: function () {
                        this.$http.get(ROOT_URL + "/status-marital").success(function (data) {
                            this.statusMarital = data;
                        });
                    },
                    obtainAsentamientos: function () {
                        var postcode = this.dwEmployee.employee.postcode;
                        if (this.dwEmployee.employee.postcode >= 4) {
                            this.$http.get(ROOT_URL + "/settlements/post-code?cp=" + postcode).success(function (data) {
                                this.asentamiento = data;
                                if (data.length > 0) {
                                    this.$http.get(ROOT_URL + "/municipalities/" + data[0].idEstado + "/" + data[0].idMunicipio).success(function (element) {
                                        this.estadosMunicipios = element;
                                    });
                                }
                            });
                        } else {
                            this.asentamiento = [];
                            this.estadosMunicipios = {};
                        }
                    },
                    obtainBanks: function () {
                        this.$http.get(ROOT_URL + "/banks")
                                .success(function (data) {
                                    this.banks = data;

                                });
                    },
                    validateFile: function (event) {
                        if (! event.target.files[0].name.match(/(\.jpg|\.jpeg|\.pdf|\.png)$/i)) {
                            event.target.value = null;
                            showAlert("Tipo de archivo no admitido", {type:3});
                        }
                    },
                    validateEmail: function (email) {
                        var re = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
                        if(! re.test(email)) {
                            showAlert("Ingresa un email correcto",{type: 3});
                            this.employee.mail = '';
                        }
                    },
                    validateRfc: function (rfc) {
                        if(rfc.length < 13){
                            showAlert("El rfc debe contener 13 caracteres",{type: 3});
                        }
                    },
                    validateCurp: function (curp) {
                        if(curp.length < 18){
                            showAlert("El curp debe contener 18 caracteres",{type: 3});
                        }
                    },
                    fetchHierarchy: function () {
                        var self = this;
                        this.$http.get(ROOT_URL + "/dw-enterprises/hierarchy").success(function (data) {
                            this.selectOptions.hierarchy = data;
                        });
                    },
                    selectedOptionsDistributorChanged: function () {

                        this.selectedOptions.region = this.defaultRegion;
                        this.selectedOptions.branch = this.defaultBranch;
                        this.selectedOptions.area = this.defaultArea;
                    },
                    selectedOptionsRegionChanged: function () {
                        this.selectedOptions.branch = this.defaultBranch;
                        this.selectedOptions.area = this.defaultArea;
                    },
                    selectedOptionsBranchChanged: function () {
                        this.selectedOptions.area = this.defaultArea;
                        this.selectedOptions.role = this.defaultRole;
                    },
                    selectedOptionsAreaChanged: function () {
                        this.selectedOptions.role = this.defaultRole;
                        this.$http.get(ROOT_URL + "/areas/area-role/" + this.selectedOptions.area.id).success(function (data) {
                            this.selectOptions.areas = data;
                        });
                    },
                }

            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div>
                <div class="row">
                    <div class="col-xs-8 text-header">
                        <h2>Modificación de empleados</h2>
                    </div>
                </div>
            </div>
            <br>
            <div class="panel panel-default">
                <div class="panel-heading">Información personal</div>
                <div class="panel-body">
                    <div class="col-xs-12">
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Nombre</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.firstName" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Segundo nombre</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.middleName" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Apellido paterno</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.parentalLast" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Apellido materno</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.motherLast" onkeypress="return isLetterKey(event)">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Genero</label>
                                <select id="selectGenero" class="form-control" name="" v-model="dwEmployee.employee.gender">
                                    <option></option>
                                    <option value="0">
                                        Mujer
                                    </option>
                                    <option value="1">
                                        Hombre
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Fecha de Nacimiento</label>
                                <div class='input-group date' id='dateBirthDay'>
                                    <input type='text' class="form-control" v-model="dwEmployee.employee.birthDayFormats.dateNumber">
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                            <div class="col-xs-3">
                                <label>Lugar de Nacimiento</label>
                                <select class="form-control" name="" v-model="dwEmployee.employee.birthPlace">
                                    <option></option>
                                    <option v-for="estado in estados"
                                            value="{{estado.nombreEstado}}">
                                        {{estado.nombreEstado}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>CURP</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.curp" maxlength="18" @change="validateCurp(employee.curp)">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Código postal</label>
                                <input class="form-control" name="name" maxlength="5" v-model="dwEmployee.employee.postcode"
                                       @input="obtainAsentamientos"
                                       onkeypress="return isNumberKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Estado</label>
                                <input class="form-control" name="name"
                                       v-model="estadosMunicipios.estado.nombreEstado" value="" disabled="true">
                            </div>
                            <div class="col-xs-3">
                                <label>Municipio/Delegación</label>
                                <input class="form-control" name="name"
                                       v-model="estadosMunicipios.nombreMunicipios" value="" disabled="true">
                            </div>
                            <div class="col-xs-3">
                                <label>Colonia</label>
                                <select class="form-control" name="" v-model="dwEmployee.employee.colonia">
                                    <option></option>
                                    <option v-for="set in asentamiento" value="{{set.nombreAsentamiento}}">
                                        {{set.nombreAsentamiento}}
                                    </option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Calle</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.street">
                            </div>
                            <div class="col-xs-2">
                                <label>Número Exterior</label>
                                <input class="form-control" name="name" maxlength="5"
                                       v-model="dwEmployee.employee.exteriorNumber">
                            </div>
                            <div class="col-xs-2">
                                <label>Número Interior</label>
                                <input class="form-control" name="name" maxlength="5"
                                       v-model="dwEmployee.employee.interiorNumber">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Nombre del padre</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.fatherName" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Nombre de la madre</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.motherName" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Estado civil</label>
                                <select class="form-control" name="" v-model="dwEmployee.employee.idStatusMarital">
                                    <option></option>
                                    <option v-for="status in statusMarital" value="{{status.idStatusMarital}}">
                                        {{status.maritalName}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Escolaridad</label>
                                <select class="form-control" name="" v-model="dwEmployee.employee.idEducation">
                                    <option></option>
                                    <option v-for="edu in education" value="{{edu.idEducation}}">
                                        {{edu.educationName}}
                                    </option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Talla</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.size" maxlength="3" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Número de talla</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.sizeNumber" maxlength="2" onkeypress="return isNumberKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Télefono de casa</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.homePhone" maxlength="10" onkeypress="return isNumberKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Móvil</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.cellPhone" maxlength="10" onkeypress="return isNumberKey(event)">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Email</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.mail" @change="validateEmail(employee.mail)">
                            </div>
                            <div class="col-xs-3">
                                <label>Clave SAP</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.claveSap">
                            </div>
                            <div class="col-xs-3">
                                <label>RFC</label>
                                <input class="form-control" name="name" v-model="dwEmployee.employee.rfc" maxlength="13" @change="validateRfc(employee.rfc)">
                            </div>
                            <div class="col-xs-3">
                                <label>Fecha de ingreso</label>
                                <div class='input-group date' id='dateJoin'>
                                    <input type='text' class="form-control" v-model="dwEmployee.employee.joinDateFormats.dateNumber">
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="panel panel-default">
                <!-- Default panel contents -->
                <div class="panel-heading">Datos de la empresa</div>
                <div class="panel-body">
                    <div class="col-xs-12">
                        <div class="row">
                            <label>Asignaciòn actual</label>
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
                                        {{dwEmployee.dwEnterprise.distributor.distributorName}}
                                    </td>
                                    <td class="col-xs-2">
                                        {{dwEmployee.dwEnterprise.region.regionName}}
                                    </td>
                                    <td class="col-xs-2">
                                        {{dwEmployee.dwEnterprise.branch.branchName}}
                                    </td>
                                    <td class="col-xs-2">
                                        {{dwEmployee.dwEnterprise.area.areaName}}
                                    </td>
                                    <td class="col-xs-2">
                                        {{dwEmployee.role.roleName}}
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <br>
                        <div class="row">
                            <label>Reasignar</label>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Distribuidor</label>
                                <select v-model="selectedOptions.distributor" class="form-control"
                                        @change="selectedOptionsDistributorChanged">
                                    <option selected :value="defaultDistributor">{{defaultDistributor.name}}</option>
                                    <option v-for="distributor in selectOptions.hierarchy[0].subLevels"
                                            :value="distributor">
                                        {{ distributor.name }}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Región</label>
                                <select v-model="selectedOptions.region" class="form-control"
                                        @change="selectedOptionsRegionChanged"
                                        :disabled="selectedOptions.distributor.id == 0">
                                    <option selected :value="defaultRegion">{{defaultRegion.name}}</option>
                                    <option v-for="region in selectedOptions.distributor.subLevels"
                                            :value="region">
                                        {{ region.name }}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Sucursal</label>
                                <select v-model="selectedOptions.branch" class="form-control"
                                        @change="selectedOptionsBranchChanged"
                                        :disabled="selectedOptions.region.id == 0">
                                    <option selected :value="defaultBranch">{{defaultBranch.name}}</option>
                                    <option v-for="branch in selectedOptions.region.subLevels"
                                            :value="branch">
                                        {{ branch.name }}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Área</label>
                                <select v-model="selectedOptions.area" class="form-control"
                                        @change="selectedOptionsAreaChanged"
                                        :disabled="selectedOptions.branch.id == 0">
                                    <option selected :value="defaultArea">{{defaultArea.name}}</option>
                                    <option v-for="area in selectedOptions.branch.subLevels"
                                            :value="area">
                                        {{ area.name }}
                                    </option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Puesto</label>
                                <select v-model="selectedOptions.role" class="form-control"
                                        :disabled="selectedOptions.area.id == 0">
                                    <option selected :value="defaultRole">{{defaultRole.name}}</option>
                                    <option v-for="role in selectOptions.areas.roles"
                                            :value="role.idRole">
                                        {{ role.roleName }}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Tipo de empleado</label>
                                <select class="form-control" name="" v-model="employee.employeeType">
                                    <option></option>
                                    <option value="1">
                                        Base
                                    </option>
                                    <option value="2">
                                        Satelite
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Tipo de contrato</label>
                                <select class="form-control" name="" v-model="employee.contractType">
                                    <option></option>
                                    <option value="1">
                                        Determinado
                                    </option>
                                    <option value="2">
                                        Indeterminado
                                    </option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="panel panel-default">
                <!-- Default panel contents -->
                <div class="panel-heading">Datos bancarios</div>
                <div class="panel-body">
                    <div class="col-xs-12">
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Banco</label>
                                <select class="form-control" name="" v-model="account[0].account.idBank">
                                    <option></option>
                                    <option v-for="bank in banks" value="{{bank.idBank}}">{{bank.acronyms}}
                                    </option>
                                </select>
                            </div>

                            <div class="col-xs-3">
                                <label>Número de Cuenta</label>
                                <div class="input-group">
                                    <span class="input-group-addon">#</span>
                                    <input id="saccount" class="form-control" maxlength="11"
                                           v-model="account[0].account.accountNumber"
                                           onkeypress="return isNumberKey(event)">
                                </div>
                            </div>

                            <div class="col-xs-3">
                                <label>CLABE</label>
                                <div class="input-group">
                                    <span class="input-group-addon">#</span>
                                    <input type="text" id="sclabe" class="form-control" maxlength="18"
                                           v-model="account[0].account.accountClabe"
                                           onkeypress="return isNumberKey(event)">
                                </div>
                            </div>
                            <div class="col-xs-3">
                                <label>Salario</label>
                                <input type="text" class="form-control" maxlength="18"
                                       v-model="dwEmployee.employee.salary"
                                       onkeypress="return isNumberKey(event)">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
