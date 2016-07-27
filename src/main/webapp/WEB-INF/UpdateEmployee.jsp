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
                    var fecha = new Date();
                    var fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
                    var fechaMinima = moment(fecha_actual).subtract('years', 18);

                    this.timePickerBirthday = $('#dateBirthDay').datetimepicker({
                        locale: 'es',
                        format: 'DD-MM-YYYY',
                        defaultDate: fechaMinima,
                        useCurrent: false,
                        maxDate: fechaMinima
                    }).data();

                    this.timePickerIngreso = $('#dateJoin').datetimepicker({
                        locale: 'es',
                        format: 'DD-MM-YYYY'
                    }).data();
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
                    account: {},
                    contractTypes: [],
                    employeeTypes: [],
                    genders: [],
                    selectOptions: {
                        distributors: [],
                        areas: [],
                        roles: [],
                        dwEnterprises: [],
                        documentTypes: []
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
                    branchs:[],
                    employee: {
                        idDwEnterprise: '',
                        idRole: '',
                        idEmployee: '',
                        idDwEmployee: '',
                        firstName: '',
                        middleName: '',
                        parentalLast: '',
                        motherLast: '',
                        rfc: '',
                        claveSap: '',
                        curp: '',
                        imss: '',
                        infonavitNumber: '',
                        mail: '',
                        employeeType: '',
                        contractType: '',
                        salary: '',
                        joinDate: '',
                        status: '',
                        street: '',
                        exteriorNumber: '',
                        interiorNumber: '',
                        colonia: '',
                        city: '',
                        state: '',
                        postcode: '',
                        cellPhone: '',
                        fatherName: '',
                        motherName: '',
                        idEducation: '',
                        homePhone: '',
                        size: '',
                        sizeNumber: '',
                        gender: '',
                        dwEmployees: {
                            area: {
                                id: 0,
                                dwEnterprise:{}
                            },
                            role: {
                                id: 0
                            }
                        },
                        employeeAccountList: [],
                        birthPlace: '',
                        idStatusMarital: '',
                        birthday: '',
                        idAccount: ''
                    },
                    joinDate: '',
                    birthday: '',
                    timePickerIngreso: '',
                    timePickerBirthday: '',
                    regresarBusqueda: ROOT_URL + '/saem/search-employees'
                },
                methods: {
                    getDwEmployee: function () {
                        this.$http.get(ROOT_URL + '/dw-employees/' + this.idDwEmployee)
                                .success(function (data) {
                                    this.dwEmployee = data;
                                    this.employee.idEmployee = this.dwEmployee.employee.idEmployee;
                                    this.employee.firstName = this.dwEmployee.employee.firstName;
                                    this.employee.middleName = this.dwEmployee.employee.middleName;
                                    this.employee.parentalLast = this.dwEmployee.employee.parentalLast;
                                    this.employee.motherLast = this.dwEmployee.employee.motherLast;
                                    this.employee.rfc = this.dwEmployee.employee.rfc;
                                    this.employee.claveSap = this.dwEmployee.employee.claveSap;
                                    this.employee.curp = this.dwEmployee.employee.curp;
                                    this.employee.imss = this.dwEmployee.employee.imss;
                                    this.employee.infonavitNumber = this.dwEmployee.employee.infonavitNumber;
                                    this.employee.mail = this.dwEmployee.employee.mail;
                                    this.employee.employeeType = this.dwEmployee.employee.employeeTypes.employeeTypeName;
                                    this.employee.contractType = this.dwEmployee.employee.contractTypes.contractTypeName;
                                    this.employee.salary = this.dwEmployee.employee.salary;
                                    this.employee.status = this.dwEmployee.employee.status;
                                    this.employee.birthPlace = this.dwEmployee.employee.birthplace;
                                    this.employee.state = this.dwEmployee.employee.state;
                                    this.employee.street = this.dwEmployee.employee.street;
                                    this.employee.exteriorNumber = this.dwEmployee.employee.exteriorNumber;
                                    this.employee.interiorNumber = this.dwEmployee.employee.interiorNumber;
                                    this.employee.colonia = this.dwEmployee.employee.colonia;
                                    this.employee.city = this.dwEmployee.employee.city;
                                    this.employee.postcode = this.dwEmployee.employee.postcode;
                                    this.employee.cellPhone = this.dwEmployee.employee.cellPhone;
                                    this.employee.fatherName = this.dwEmployee.employee.fatherName;
                                    this.employee.motherName = this.dwEmployee.employee.motherName;
                                    this.employee.idEducation = this.dwEmployee.employee.idEducation;
                                    this.employee.idStatusMarital = this.dwEmployee.employee.idStatusMarital;
                                    this.employee.homePhone = this.dwEmployee.employee.homePhone;
                                    this.employee.size = this.dwEmployee.employee.size;
                                    this.employee.sizeNumber = this.dwEmployee.employee.sizeNumber;
                                    this.employee.gender = this.dwEmployee.employee.genders.genderName;
                                    this.joinDate = this.dwEmployee.employee.joinDateFormats.dateNumber;
                                    this.birthday = this.dwEmployee.employee.birthDayFormats.dateNumber;
                                    this.employee.idDwEnterprise = this.dwEmployee.idDwEnterprise;
                                    this.employee.idRole = this.dwEmployee.idRole;
                                    this.employee.dwEmployees.area.dwEnterprise = this.dwEmployee.dwEnterprise;
                                    this.employee.dwEmployees.role = this.dwEmployee.role;
                                    this.employee.idDwEmployee = this.dwEmployee.idDwEmployee;
                                    this.obtainAsentamientos();
                                    this.obtainGenders();
                                    this.$http.get(ROOT_URL + "/employees-accounts/actives/" + this.employee.idEmployee).success(function (data) {
                                        this.account = data;
                                    });
                                })
                                .error(function (data) {

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
                            console.log(data);
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
                            this.education = data;
                        });
                    },
                    obtainStatusMarital: function () {
                        this.$http.get(ROOT_URL + "/status-marital").success(function (data) {
                            this.statusMarital = data;
                        });
                    },
                    getBranchs : function () {
                        this.$http.get(ROOT_URL + "/branchs").success(function (data) {
                            this.branchs = data;
                        });
                    },
                    obtainAsentamientos: function () {
                        var postcode = this.employee.postcode;
                        if (this.employee.postcode >= 4) {
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
                    selectedOptionsBranchChanged: function () {
                        this.selectedOptions.area = this.defaultArea;
                        this.selectedOptions.role = this.defaultRole;
                        this.selectOptions.dwEnterprises = [];
                        this.selectOptions.roles = [];
                        this.$http.get(ROOT_URL + "/dw-enterprises/branch/" + this.selectedOptions.branch.idBranch).success(function (data) {
                            this.selectOptions.dwEnterprises = data;
                        });
                    },
                    selectedOptionsDwEnterpriseChanged: function () {
                        this.selectOptions.roles = [];
                        this.selectedOptions.role = this.defaultRole;
                        this.$http.get(ROOT_URL + "/areas/area-role/" + this.selectedOptions.area.idArea).success(function (data) {
                            this.selectOptions.roles = data.roles;
                        });
                    },
                    updateEmployee: function () {
                        var cuenta = {};
                        cuenta.accountClabe = this.account.account.accountClabe;
                        cuenta.accountNumber = this.account.account.accountNumber;
                        cuenta.idBank = this.account.account.idBank;
                        cuenta.idCurrency = this.account.account.idCurrency;
                        this.employee.employeeAccountList.push(cuenta);
                        this.employee.idAccount = this.account.account.idAccount;
                        this.employee.city = this.estadosMunicipios.nombreMunicipios;
                        this.employee.state = this.estadosMunicipios.estado.nombreEstado;
                        if(this.selectedOptions.area.id.length > 0){
                            this.employee.dwEmployees.area = this.selectedOptions.area;
                        }
                        if(this.selectedOptions.role.id > 0){
                            this.employee.dwEmployees.role = this.selectedOptions.role;
                        }
                        this.employee.joinDate = moment(this.joinDate, 'DD/MM/YYYY').toISOString().slice(0,-1);
                        this.employee.birthday = moment(this.birthday, 'DD/MM/YYYY').toISOString().slice(0, -1);

                        this.$http.post(ROOT_URL + "/employees/update", JSON.stringify(this.employee)).success(function (data) {
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
                                <input class="form-control" name="name" v-model="employee.firstName" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Segundo nombre</label>
                                <input class="form-control" name="name" v-model="employee.middleName" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Apellido paterno</label>
                                <input class="form-control" name="name" v-model="employee.parentalLast" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Apellido materno</label>
                                <input class="form-control" name="name" v-model="employee.motherLast" onkeypress="return isLetterKey(event)">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Genero</label>
                                <select class="form-control" name="" v-model="employee.gender">
                                    <option></option>
                                    <option v-for="gender in genders" :value="gender.genderName">
                                        {{gender.genderName}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Fecha de Nacimiento</label>
                                <div class='input-group date' id='dateBirthDay'>
                                    <input type='text' class="form-control" v-model="birthday">
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                            <div class="col-xs-3">
                                <label>Lugar de Nacimiento</label>
                                <select class="form-control" name="" v-model="employee.birthPlace">
                                    <option></option>
                                    <option v-for="estado in estados"
                                            value="{{estado.nombreEstado}}">
                                        {{estado.nombreEstado}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>CURP</label>
                                <input class="form-control" name="name" v-model="employee.curp" maxlength="18" @change="validateCurp(employee.curp)">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Código postal</label>
                                <input class="form-control" name="name" maxlength="5" v-model="employee.postcode"
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
                                <select class="form-control" name="" v-model="employee.colonia">
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
                                <input class="form-control" name="name" v-model="employee.street">
                            </div>
                            <div class="col-xs-2">
                                <label>Número Exterior</label>
                                <input class="form-control" name="name" maxlength="5"
                                       v-model="employee.exteriorNumber">
                            </div>
                            <div class="col-xs-2">
                                <label>Número Interior</label>
                                <input class="form-control" name="name" maxlength="5"
                                       v-model="employee.interiorNumber">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Nombre del padre</label>
                                <input class="form-control" name="name" v-model="employee.fatherName" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Nombre de la madre</label>
                                <input class="form-control" name="name" v-model="employee.motherName" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Estado civil</label>
                                <select class="form-control" name="" v-model="employee.idStatusMarital">
                                    <option></option>
                                    <option v-for="status in statusMarital" value="{{status.idStatusMarital}}">
                                        {{status.maritalName}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Escolaridad</label>
                                <select class="form-control" name="" v-model="employee.idEducation">
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
                                <input class="form-control" name="name" v-model="employee.size" maxlength="3" onkeypress="return isLetterKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Número de talla</label>
                                <input class="form-control" name="name" v-model="employee.sizeNumber" maxlength="2" onkeypress="return isNumberKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Télefono de casa</label>
                                <input class="form-control" name="name" v-model="employee.homePhone" maxlength="10" onkeypress="return isNumberKey(event)">
                            </div>
                            <div class="col-xs-3">
                                <label>Móvil</label>
                                <input class="form-control" name="name" v-model="employee.cellPhone" maxlength="10" onkeypress="return isNumberKey(event)">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Email</label>
                                <input class="form-control" name="name" v-model="employee.mail" @change="validateEmail(employee.mail)">
                            </div>
                            <div class="col-xs-3">
                                <label>Clave SAP</label>
                                <input class="form-control" name="name" v-model="employee.claveSap">
                            </div>
                            <div class="col-xs-3">
                                <label>RFC</label>
                                <input class="form-control" name="name" v-model="employee.rfc" maxlength="13" @change="validateRfc(employee.rfc)">
                            </div>
                            <div class="col-xs-3">
                                <label>Fecha de ingreso</label>
                                <div class='input-group date' id='dateJoin'>
                                    <input type='text' class="form-control" v-model="joinDate">
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
                                <select v-model="selectedOptions.area" class="form-control"
                                        @change="selectedOptionsDwEnterpriseChanged"
                                        :disabled="selectOptions.dwEnterprises.length <= 0">
                                    <option selected :value="defaultArea">{{defaultArea.name}}</option>
                                    <option v-for="dwEnterprise in selectOptions.dwEnterprises"
                                            :value="dwEnterprise.area">
                                        {{ dwEnterprise.area.areaName }}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <label>Puesto</label>
                                <select v-model="selectedOptions.role" class="form-control"
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
                                <select class="form-control" v-model="employee.employeeType">
                                    <option></option>
                                    <option v-for="type in employeeTypes" :value="type.employeeTypeName">
                                        {{type.employeeTypeName}}
                                    </option>
                                </select>
                            </div>
                            <div class="col-xs-2">
                                <label>Tipo de contrato</label>
                                <select class="form-control" v-model="employee.contractType">
                                    <option></option>
                                    <option v-for="contract in contractTypes" :value="contract.contractTypeName">
                                        {{contract.contractTypeName}}
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
                                <select class="form-control" name="" v-model="account.account.idBank">
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
                                           v-model="account.account.accountNumber"
                                           onkeypress="return isNumberKey(event)">
                                </div>
                            </div>

                            <div class="col-xs-3">
                                <label>CLABE</label>
                                <div class="input-group">
                                    <span class="input-group-addon">#</span>
                                    <input type="text" id="sclabe" class="form-control" maxlength="18"
                                           v-model="account.account.accountClabe"
                                           onkeypress="return isNumberKey(event)">
                                </div>
                            </div>
                            <div class="col-xs-3">
                                <label>Salario</label>
                                <input type="text" class="form-control" maxlength="18"
                                       v-model="employee.salary"
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
                    <button type="button" class="btn btn-default" @click="updateEmployee()">
                        Aceptar
                    </button>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
