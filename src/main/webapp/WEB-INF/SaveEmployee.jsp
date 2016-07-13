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
                        format: 'DD-MM-YYYY',
                    }).data();
                },
                ready: function () {
                    this.obtainStates();
                    this.obtainEducation();
                    this.obtainStatusMarital();
                    this.obtainBanks();
                    this.obtainCurrencies();
                    this.fetchHierarchy();
                    this.fetchDocumentTypes();
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
                    timePickerIngreso: '',
                    timePickerBirthday: '',
                    joinDate: '',
                    birthday: '',
                    estadosMunicipios: {},
                    input: '',
                    fields: {
                        IMSS: '',
                        SISTARH: '',
                        Infonavit: '',
                    },
                    working: {},
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
                        this.employee.city = this.estadosMunicipios.nombreMunicipios;
                        this.employee.state = this.estadosMunicipios.estado.nombreEstado;
                        this.employee.dwEmployees.area = this.selectedOptions.area;
                        this.employee.dwEmployees.role = this.selectedOptions.role;
                        this.employee.joinDate = this.timePickerIngreso.DateTimePicker.date().toISOString().slice(0, -1);
                        this.employee.birthday = this.timePickerBirthday.DateTimePicker.date().toISOString().slice(0, -1);
                        var form = document.getElementById('attachments-form');
                        var formData = new FormData(form);

                        this.$http.post(ROOT_URL + "/employees/save", JSON.stringify(this.employee)).success(function (data) {
                            this.working = data;
                            showAlert("Registro de empleado exitoso");
                            this.uploadFilesEmployee(this.working, formData);
                        }).error(function () {
                            this.employee.employeeAccountList = [];
                            showAlert("Ha habido un error con la solicitud, intente nuevamente", {type: 3});
                        });
                    },
                    fetchDocumentTypes: function () {
                        this.$http.get(ROOT_URL + "/employee-document-types").success(function (data) {
                            this.selectOptions.documentTypes = data;
                        });
                    },
                    validateFile: function (event) {
                        if (! event.target.files[0].name.match(/(\.jpg|\.jpeg|\.pdf|\.png)$/i)) {
                            event.target.value = null;
                            showAlert("Tipo de archivo no admitido", {type:3});
                        }
                    },
                    showDocumentsByDistributors: function (distributors) {
                        var array;
                        var self = this;
                        array = distributors.split(":");
                        var arrayFilltered = array.filter(function (item) {
                            return item == self.selectedOptions.distributor.id;
                        });
                        return arrayFilltered.length > 0;
                    },
                    uploadFilesEmployee: function (employee,formData) {
                        this.isSaving = true;
                        this.$http.post(
                                ROOT_URL + "/employees/" + employee.idEmployee + "/attachments",
                                formData
                        ).success(function (data)
                        {
                            form.reset();
                        }).error(function (data) {
                            this.isSaving = false;
                            form.reset();
                            showAlert(data.error.message, {type:3})
                        });

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
                                <select class="form-control" name="" v-model="cuenta.idBank">
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
            <div class="panel panel-default">
                <div class="panel-heading">Documentación</div>
                <div class="panel-body">
                    <div class="col-xs-12">
                        <form id="attachments-form" method="post" enctype="multipart/form-data"
                              v-on:submit.prevent="saveEmployee">
                        <div class="row">
                            <table class="table table-striped">
                                <tr v-for="docType in selectOptions.documentTypes" v-if="showDocumentsByDistributors(docType.idDistributors)">

                                    <td>{{ docType.documentName }}</td>
                                    <td v-if="docType.field == 1 ">
                                        <input v-model="fields[docType.documentName]" name="{{docType.documentName}}" required
                                               type="text" class="form-control"></td>
                                    <td>
                                        <input v-if="docType.field == 0 || fields[docType.documentName].length > 0" @change="validateFile($event)" type="file" class="form-control"
                                               :disabled="isSaving"
                                               :name="'file-type-' + docType.idDocumentType"
                                               accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                    </td>
                                </tr>
                            </table>
                        </div>
                            <button type="submit" :disabled="isSaving"
                                    class="btn btn-success">
                                Guardar
                            </button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
