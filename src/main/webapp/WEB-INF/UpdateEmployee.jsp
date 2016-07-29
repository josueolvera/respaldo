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
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>

        <script>
            function isLetterKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
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
                var charCode = (evt.which) ? evt.which : event.keyCode;
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
                created: function () {

                },
                ready: function () {
                    this.getDwEmployee();
                    this.obtainStates();
                    this.obtainEducation();
                    this.obtainStatusMarital();
                    this.obtainBanks();
                    this.getBranchs();
                    this.obtainEmployeeTypes();
                    this.obtainContractTypes();
                    this.obtainGenders();
                },
                data: {
                    dwEmployee:{},
                    dwEnterprise:{},
                    role:{},
                    idDwEmployee: ${idDwEmployee},
                    estadosMunicipios: [],
                    asentamiento: [],
                    educationList: [],
                    statusMaritalList: [],
                    estados: [],
                    banks: [],
                    employeeAccount: {},
                    contractTypes: [],
                    employeeTypes: [],
                    genders: [],
                    branchs:[],
                    selectOptions: {
                        areas: [],
                        roles: [],
                        dwEnterprises: [],
                        documentTypes: []
                    },
                    selectedOptions: {
                        dwEnterprise: {
                            idDwEnterprise: 0
                        },
                        branch: {
                            idBranch: 0
                        },
                        role: {
                            idRole: 0
                        }
                    },
                    defaultArea: {
                        idArea: 0,
                        name: ''
                    },
                    defaultBranch: {
                        idBranch: 0,
                        name: ''
                    },
                    defaultRole: {
                        idRole: 0,
                        name: ''
                    },
                    timePickerJoinDate: '',
                    timePickerBirthday: '',
                    regresarBusqueda: ROOT_URL + '/saem/search-employees'
                },
                methods: {
                    getDwEmployee: function () {
                        this.$http.get(ROOT_URL + '/dw-employees/' + this.idDwEmployee)
                                .success(function (data) {
                                    this.dwEmployee = data;
                                    this.setDates();
                                    delete this.dwEmployee.idDwEmployee;
                                    delete this.dwEmployee.creationDate;
                                    delete this.dwEmployee.idEmployee;
                                    delete this.dwEmployee.idRole;
                                    this.dwEnterprise = this.dwEmployee.dwEnterprise;
                                    this.role = this.dwEmployee.role;
                                    this.obtainAsentamientos();
                                    this.getEmployeeAccounts();
                                })
                                .error(function (data) {

                                });
                    },
                    setDates:function () {
                        this.dwEmployee.employee.birthday = this.dwEmployee.employee.birthDayFormats.dateNumber;
                        this.dwEmployee.employee.joinDate = this.dwEmployee.employee.joinDateFormats.dateNumber;
                    },
                    activeDateTimePickerBirthday: function () {
                        var fecha = new Date();
                        var fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
                        var fechaMinima = moment(fecha_actual).subtract('years', 18);

                        this.timePickerBirthday = $('#birthday').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            defaultDate: this.dwEmployee.employee.birthDayFormats.iso,
                            useCurrent: false,
                            maxDate: fechaMinima
                        }).data();

                    },
                    activeDateTimePickerJoinDate: function () {
                        this.timePickerJoinDate = $('#joinDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            defaultDate: this.dwEmployee.employee.joinDateFormats.iso
                        }).data();
                    },
                    getEmployeeAccounts : function () {
                        this.$http.get(ROOT_URL + "/employees-accounts/actives/" + this.dwEmployee.employee.idEmployee).success(function (data) {
                            this.employeeAccount = data;
                        });
                    },
                    obtainGenders: function () {
                        this.$http.get(ROOT_URL + "/gender").success(function (data) {
                            this.genders = data;
                        });
                    },
                    obtainEmployeeTypes: function () {
                        this.$http.get(ROOT_URL + "/employee-type").success(function (data) {
                            this.employeeTypes = data;
                        });
                    },
                    obtainContractTypes: function () {
                        this.$http.get(ROOT_URL + "/contract-type").success(function (data) {
                            this.contractTypes = data;
                        });
                    },
                    obtainStates: function () {
                        this.$http.get(ROOT_URL + "/states").success(function (data) {
                            this.estados = data;
                        });
                    },
                    obtainEducation: function () {
                        this.$http.get(ROOT_URL + "/education").success(function (data) {
                            this.educationList = data;
                        });
                    },
                    obtainStatusMarital: function () {
                        this.$http.get(ROOT_URL + "/status-marital").success(function (data) {
                            this.statusMaritalList = data;
                        });
                    },
                    getBranchs : function () {
                        this.$http.get(ROOT_URL + "/branchs").success(function (data) {
                            this.branchs = data;
                        });
                    },
                    obtainAsentamientos: function () {
                        var postcode = this.dwEmployee.employee.postcode;
                        if (postcode >= 4) {
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
                            this.dwEmployee.employee.mail = '';
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
                    selectedOptionsBranchChanged: function () {
                        this.dwEmployee.dwEnterprise = this.dwEnterprise;
                        this.dwEmployee.role = this.role;
                        this.selectOptions.dwEnterprises = [];
                        this.selectOptions.roles = [];
                        this.$http.get(ROOT_URL + "/dw-enterprises/branch/" + this.selectedOptions.branch.idBranch).success(function (data) {
                            this.selectOptions.dwEnterprises = data;
                        });
                    },
                    selectedOptionsDwEnterpriseChanged: function () {
                        this.dwEmployee.role = this.role;
                        this.selectOptions.roles = [];
                        this.$http.get(ROOT_URL + "/areas/area-role/" + this.dwEmployee.dwEnterprise.area.idArea).success(function (data) {
                            this.selectOptions.roles = data.roles;
                        });
                    },
                    updateEmployee: function () {
                        this.dwEmployee.employee.employeeAccountList.push(this.employeeAccount.account);
                        this.$http.post(ROOT_URL + "/employees/update", JSON.stringify(this.dwEmployee)).success(function (data) {
                            showAlert("Actualizaciòn de empleado exitoso");
                        }).error(function () {
                            this.employee.employeeAccountList = [];
                            showAlert("Ha habido un error con la solicitud, intente nuevamente", {type: 3});
                        });
                    }
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
                                <input class="form-control" v-model="dwEmployee.employee.firstName" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Segundo nombre</label>
                                <input class="form-control" v-model="dwEmployee.employee.middleName" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Apellido paterno</label>
                                <input class="form-control" v-model="dwEmployee.employee.parentalLast" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Apellido materno</label>
                                <input class="form-control" v-model="dwEmployee.employee.motherLast" onkeypress="return isLetterKey(event)">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Genero</label>
                                <select class="form-control" v-model="dwEmployee.employee.gender">
                                    <option></option>
                                    <option v-for="gender in genders" :value="gender">
                                        {{gender.genderName}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Fecha de Nacimiento</label>
                                <div class="form-group">
                                    <div class="input-group date" id="birthday" @click="activeDateTimePickerBirthday">
                                        <input type="text" class="form-control" v-model="dwEmployee.employee.birthday">
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-3">
                                <label>Lugar de Nacimiento</label>
                                <select class="form-control" v-model="dwEmployee.employee.birthPlace">
                                    <option></option>
                                    <option v-for="estado in estados"
                                            value="{{estado.nombreEstado}}">
                                        {{estado.nombreEstado}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>CURP</label>
                                <input class="form-control" v-model="dwEmployee.employee.curp" maxlength="18" @change="validateCurp(employee.curp)">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Código postal</label>
                                <input class="form-control" maxlength="5" v-model="dwEmployee.employee.postcode"
                                       @input="obtainAsentamientos"
                                       onkeypress="return isNumberKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Estado</label>
                                <input class="form-control" v-model="dwEmployee.employee.state"
                                       v-model="estadosMunicipios.estado.nombreEstado" disabled>
                            </div>
                            <div class="col-xs-3">
                                <label>Municipio/Delegación</label>
                                <input class="form-control" v-model="dwEmployee.employee.city"
                                       v-model="estadosMunicipios.nombreMunicipios" disabled>
                            </div>
                            <div class="col-xs-3">
                                <label>Colonia</label>
                                <select class="form-control" v-model="dwEmployee.employee.colonia">
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
                                <input class="form-control" v-model="dwEmployee.employee.street">
                            </div>
                            <div class="col-xs-2">
                                <label>Número Exterior</label>
                                <input class="form-control" maxlength="5"
                                       v-model="dwEmployee.employee.exteriorNumber">
                            </div>
                            <div class="col-xs-2">
                                <label>Número Interior</label>
                                <input class="form-control" maxlength="5"
                                       v-model="dwEmployee.employee.interiorNumber">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Nombre del padre</label>
                                <input class="form-control" v-model="dwEmployee.employee.fatherName" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Nombre de la madre</label>
                                <input class="form-control" v-model="dwEmployee.employee.motherName" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Estado civil</label>
                                <select class="form-control" v-model="dwEmployee.employee.statusMarital">
                                    <option></option>
                                    <option v-for="statusMarital in statusMaritalList" value="{{statusMarital}}">
                                        {{statusMarital.maritalName}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Escolaridad</label>
                                <select class="form-control" v-model="dwEmployee.employee.education">
                                    <option></option>
                                    <option v-for="education in educationList" value="{{education}}">
                                        {{education.educationName}}
                                    </option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Talla</label>
                                <input class="form-control" v-model="dwEmployee.employee.size" maxlength="3" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Número de talla</label>
                                <input class="form-control" v-model="dwEmployee.employee.sizeNumber" maxlength="2" onkeypress="return isNumberKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Télefono de casa</label>
                                <input class="form-control" v-model="dwEmployee.employee.homePhone" maxlength="10" onkeypress="return isNumberKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Móvil</label>
                                <input class="form-control" v-model="dwEmployee.employee.cellPhone" maxlength="10" onkeypress="return isNumberKey(event)">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Email</label>
                                <input class="form-control" v-model="dwEmployee.employee.mail" @change="validateEmail(employee.mail)">
                            </div>
                            <div class="col-xs-3">
                                <label>Clave SAP</label>
                                <input class="form-control" v-model="dwEmployee.employee.claveSap">
                            </div>
                            <div class="col-xs-3">
                                <label>RFC</label>
                                <input class="form-control" v-model="dwEmployee.employee.rfc" maxlength="13" @change="validateRfc(employee.rfc)">
                            </div>
                            <div class="col-xs-3">
                                <label>Fecha de ingreso</label>
                                <div class="form-group">
                                    <div class="input-group date" id="joinDate" @click="activeDateTimePickerJoinDate">
                                        <input type="text" class="form-control" v-model="dwEmployee.employee.joinDate">
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                    </div>
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
                            <label>Asignación actual</label>
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
                        <br>
                        <div class="row">
                            <label>Reasignar</label>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-2">
                                <label>Sucursal</label>
                                <select v-model="selectedOptions.branch" class="form-control"
                                        @change="selectedOptionsBranchChanged">
                                    <option selected :value="defaultBranch">{{defaultBranch.name}}</option>
                                    <option v-for="branch in branchs"
                                            :value="branch">
                                        {{ branch.branchShort }}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Área</label>
                                <select v-model="dwEmployee.dwEnterprise" class="form-control"
                                        @change="selectedOptionsDwEnterpriseChanged"
                                        :disabled="selectOptions.dwEnterprises.length <= 0">
                                    <option selected :value="defaultArea">{{defaultArea.name}}</option>
                                    <option v-for="dwEnterprise in selectOptions.dwEnterprises"
                                            :value="dwEnterprise">
                                        {{ dwEnterprise.area.areaName }}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Puesto</label>
                                <select v-model="dwEmployee.role" class="form-control"
                                        :disabled="selectOptions.roles.length <= 0">
                                    <option selected :value="defaultRole">{{defaultRole.name}}</option>
                                    <option v-for="role in selectOptions.roles"
                                            :value="role">
                                        {{ role.roleName }}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-2">
                                <label>Tipo de empleado</label>
                                <select class="form-control" v-model="dwEmployee.employee.employeeType">
                                    <option></option>
                                    <option v-for="employeeType in employeeTypes" :value="employeeType">
                                        {{employeeType.employeeTypeName}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-2">
                                <label>Tipo de contrato</label>
                                <select class="form-control" v-model="dwEmployee.employee.contractType">
                                    <option></option>
                                    <option v-for="contractType in contractTypes" :value="contractType">
                                        {{contractType.contractTypeName}}
                                    </option>
                                </select>
                            </div>
                        </div>
                        <br>
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
                                <select class="form-control" v-model="employeeAccount.account.bank">
                                    <option></option>
                                    <option v-for="bank in banks" value="{{bank}}">
                                        {{bank.acronyms}}
                                    </option>
                                </select>
                            </div>

                            <div class="col-xs-3">
                                <label>Número de Cuenta</label>
                                <div class="input-group">
                                    <span class="input-group-addon">#</span>
                                    <input class="form-control" maxlength="11"
                                           v-model="employeeAccount.account.accountNumber"
                                           onkeypress="return isNumberKey(event)">
                                </div>
                            </div>

                            <div class="col-xs-3">
                                <label>CLABE</label>
                                <div class="input-group">
                                    <span class="input-group-addon">#</span>
                                    <input type="text" class="form-control" maxlength="18"
                                           v-model="employeeAccount.account.accountClabe"
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
            <br>
            <div class="row">
                <div class="col-xs-10">
                </div>
                <div class="col-xs-1">
                    <a class="btn btn-default" :href="regresarBusqueda">Regresar</a>
                </div>
                <div class="col-xs-1">
                    <button type="button" class="btn btn-primary" @click="updateEmployee()">
                        Aceptar
                    </button>
                </div>
            </div>
            <br>
            <br>
        </div>
    </jsp:body>
</t:template>
