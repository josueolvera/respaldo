<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Alta Empleado">

    <jsp:attribute name="styles">
        <style>
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
            }

            .table-body .table-row:nth-child(2n+1) {
                background: #ddd;
            }

            .table-row {
                padding: 1rem;
            }

            .flex-content {
                overflow-x: hidden;
            }
        </style>
    </jsp:attribute>

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
            var vm = new Vue({
                el: '#contenidos',
                created: function () {
                    var fecha = new Date();
                    var fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth()+1) + "-" + fecha.getDate();
                    var fechaMinima = moment(fecha_actual).subtract('years',18);

                    this.timePickerBirthday = $('#dateBirthDay').datetimepicker({
                        locale: 'es',
                        format: 'DD-MM-YYYY',
                        defaultDate: fechaMinima,
                        useCurrent: false,
                        maxDate: fechaMinima
                    }).data();

                    this.timePickerIngreso = $('#dateJoin').datetimepicker({
                        locale: 'es',
                        format: 'DD-MM-YYYY',
                    }).data;
                },
                ready: function () {
                    this.obtainStates();
                    this.obtainEducation();
                    this.obtainStatusMarital();
                    this.obtainBanks();
                    this.obtainCurrencies();
                    this.fetchHierarchy();
                },
                data: {
                    employee: {
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
                        postCode: '',
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
                            },
                            role: {
                                id: 0,
                            }
                        },
                        employeeAccountList: [],
                        birthPlace: '',
                        idStatusMarital: '',
                        birthday: '',
                    },
                    estados: [],
                    education: [],
                    statusMarital: [],
                    estadosMunicipios: {},
                    asentamiento: [],
                    cuenta: {
                        accountNumber: '',
                        accountClabe: '',
                        idBank: '',
                        idCurrency: '',
                    },
                    banks: [],
                    currencies: [],
                    selectOptions: {
                        distributors: [],
                        areas: [],
                        hierarchy: [],
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
                    timePickerIngreso: '',
                    timePickerBirthday: '',
                },
                methods: {
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
                    obtainCurrencies: function () {
                        this.$http.get(ROOT_URL + "/currencies").success(function (data) {
                            this.currencies = data;
                        });

                    },
                    fetchHierarchy: function () {
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
                    saveEmployee: function () {
                      this.employee.employeeAccountList.push(this.cuenta);
                        this.employee.dwEmployees.area = this.selectedOptions.area;
                        this.employee.dwEmployees.role = this.selectedOptions.role;
                        this.$http.post(ROOT_URL + "/employees/save", JSON.stringify(this.employee)).success(function (data) {
                            showAlert("Registro de empleado exitoso");
                        }).error(function () {
                            showAlert("Ha habido un error con la solicitud, intente nuevamente", {type: 3});
                        });
                    },
                },
                filters: {}
            });


        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos" class="flex-box container-fluid">
            <div>
                <div class="row">
                    <div class="col-xs-8 text-header">
                        <h2>Registro de empleados</h2>
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
                                <input class="form-control" name="name" v-model="employee.firstName">
                            </div>
                            <div class="col-xs-3">
                                <label>Segundo nombre</label>
                                <input class="form-control" name="name" v-model="employee.middleName">
                            </div>
                            <div class="col-xs-3">
                                <label>Aoellido paterno</label>
                                <input class="form-control" name="name" v-model="employee.parentalLast">
                            </div>
                            <div class="col-xs-3">
                                <label>Apellido materno</label>
                                <input class="form-control" name="name" v-model="employee.motherLast">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Genero</label>
                                <select class="form-control" name="" v-model="employee.gender">
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
                                    <input type='text' class="form-control" v-model="employee.birthday">
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
                                <input class="form-control" name="name" v-model="employee.curp">
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
                                <input class="form-control" name="name" v-model="employee.fatherName">
                            </div>
                            <div class="col-xs-3">
                                <label>Nombre de la madre</label>
                                <input class="form-control" name="name" v-model="employee.motherName">
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
                                <input class="form-control" name="name" v-model="employee.size">
                            </div>
                            <div class="col-xs-3">
                                <label>Número de talla</label>
                                <input class="form-control" name="name" v-model="employee.sizeNumber">
                            </div>
                            <div class="col-xs-3">
                                <label>Télefono de casa</label>
                                <input class="form-control" name="name" v-model="employee.homePhone">
                            </div>
                            <div class="col-xs-3">
                                <label>Móvil</label>
                                <input class="form-control" name="name" v-model="employee.cellPhone">
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-3">
                                <label>Email</label>
                                <input class="form-control" name="name" v-model="employee.mail">
                            </div>
                            <div class="col-xs-3">
                                <label>Clave SAP</label>
                                <input class="form-control" name="name" v-model="employee.claveSap">
                            </div>
                            <div class="col-xs-3">
                                <label>RFC</label>
                                <input class="form-control" name="name" v-model="employee.rfc">
                            </div>
                            <div class="col-xs-3">
                                <label>Fecha de ingreso</label>
                                <div class='input-group date' id='dateJoin'>
                                    <input type='text' class="form-control" v-model="employee.joinDate">
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
                                <select class="form-control" name="" v-model="cuenta.idBanks">
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
                                           v-model="cuenta.accountNumber"
                                           onkeypress="return isNumberKey(event)">
                                </div>
                            </div>

                            <div class="col-xs-3">
                                <label>CLABE</label>
                                <div class="input-group">
                                    <span class="input-group-addon">#</span>
                                    <input type="text" id="sclabe" class="form-control" maxlength="18"
                                           v-model="cuenta.accountClabe"
                                           onkeypress="return isNumberKey(event)">
                                </div>
                            </div>
                            <div class="col-xs-3">
                                <label>Moneda</label>
                                <select class="form-control" name="" v-model="cuenta.idCurrency">
                                    <option></option>
                                    <option v-for="curre in currencies" value="{{curre.idCurrency}}">
                                        {{curre.currency}}
                                    </option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="panel panel-default">
                <div class="panel-heading">Datos laborales</div>
                <div class="panel-body">
                    <div class="col-xs-12">
                        <div class="row">
                            <div class="col-xs-3">
                                <label>IMSS</label>
                                <input class="form-control" name="name" v-model="employee.imss">
                            </div>
                            <div class="col-xs-3">
                                <label>Credito infonavit</label>
                                <input class="form-control" name="name" v-model="employee.infonavitNumber">
                            </div>
                            <div class="col-xs-2">
                                <button type="button" class="btn btn-sm btn-default" data-toggle="tooltip"
                                        data-placement="bottom" title="Agregar"
                                        @click="saveEmployee()">
                                    <span class="glyphicon glyphicon-plus"></span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
