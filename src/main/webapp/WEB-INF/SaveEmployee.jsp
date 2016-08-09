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
                        format: 'DD-MM-YYYY'
                    }).data();
                },
                ready: function () {
                    this.obtainStates();
                    this.obtainEducation();
                    this.obtainStatusMarital();
                    this.obtainBanks();
                    this.obtainCurrencies();
                    this.obtainGenders();
                    this.obtainEmployeeTypes();
                    this.obtainContractTypes();
                    this.getSizes();
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
                        sistarh: '',
                        imss: '',
                        infonavitNumber: '',
                        mail: '',
                        employeeType: '',
                        contractType: '',
                        salary: 0,
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
                        idSize: '',
                        sizeNumber: '',
                        gender: '',
                        dwEmployees: {
                            distributor: {
                                id: 0
                            },
                            region: {
                                id: 0
                            },
                            zona: {
                                id: 0
                            },
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
                        employeeAccountList: [],
                        birthPlace: '',
                        idStatusMarital: '',
                        birthday: ''
                    },
                    estados: [],
                    education: [],
                    statusMarital: [],
                    newEmployeeDocuments: [],
                    estadosMunicipios: {},
                    asentamiento: [],
                    cuenta: {
                        accountNumber: '',
                        accountClabe: '',
                        idBank: '',
                        idCurrency: ''
                    },
                    banks: [],
                    currencies: [],
                    contractTypes: [],
                    employeeTypes: [],
                    genders: [],
                    timePickerIngreso: '',
                    timePickerBirthday: '',
                    joinDate: '',
                    birthday: '',
                    estadosMunicipios: {},
                    input: '',
                    fields: {
                        IMSS: '',
                        SISTARH: '',
                        Infonavit: ''
                    },
                    working: {},
                    selectOptions: {
                        distributors: [],
                        areas: [],
                        roles: [],
                        dwEnterprises: [],
                        hierarchy: []
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
                        zona: {
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
                    defaultRegion: {
                        idRegion: 0,
                        name: ''
                    },
                    defaultZona: {
                        idZonas: 0,
                        name: ''
                    },
                    defaultRole: {
                        idRole: 0,
                        name: ''
                    },
                    branchs: [],
                    documentTypes: [],
                    dwEnter: {
                        idDistributor: 0
                    },
                    sizes: []
                },
                methods: {
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
                        if (this.employee.postcode.length >= 5) {
                            this.$http.get(ROOT_URL + "/settlements/post-code?cp=" + postcode).success(function (data) {
                                this.asentamiento = data;
                                if (data.length > 0) {
                                    this.$http.get(ROOT_URL + "/municipalities/" + data[0].idEstado + "/" + data[0].idMunicipio).success(function (element) {
                                        this.estadosMunicipios = element;
                                    });
                                } else {
                                    showAlert("El codigo postal no existe", {type: 3});
                                    this.estadosMunicipios = {};
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
                    saveEmployee: function () {
                        var self = this;
                        this.employee.employeeAccountList.push(this.cuenta);
                        this.employee.city = this.estadosMunicipios.nombreMunicipios;
                        this.employee.state = this.estadosMunicipios.estado.nombreEstado;
                        this.employee.dwEmployees.distributor = this.selectedOptions.distributor;
                        this.employee.dwEmployees.region = this.selectedOptions.region;
                        this.employee.dwEmployees.zona = this.selectedOptions.zona;
                        this.employee.dwEmployees.branch = this.selectedOptions.branch;
                        this.employee.dwEmployees.area = this.selectedOptions.area;
                        this.employee.dwEmployees.role = this.selectedOptions.role;
                        this.employee.joinDate = this.timePickerIngreso.DateTimePicker.date().toISOString().slice(0, -1);
                        this.employee.birthday = this.timePickerBirthday.DateTimePicker.date().toISOString().slice(0, -1);
                        if (this.employee.idSize.length == 0) {
                            this.employee.idSize = 7;
                        }
                        this.$http.post(ROOT_URL + "/employees/save", JSON.stringify(this.employee)).success(function (data) {
                            this.working = data;
                            showAlert("Registro de empleado exitoso");
                            this.newEmployeeDocuments.forEach(function (document) {
                                self.uploadFilesEmployee(document, data.idEmployee)
                            });
                        }).error(function () {
                            this.employee.employeeAccountList = [];
                            showAlert("Ha habido un error con la solicitud, intente nuevamente", {type: 3});
                        });
                    },
                    fetchDocumentTypes: function () {
                        this.$http.get(ROOT_URL + "/employee-document-types/" + this.selectedOptions.branch.id + "/" + this.selectedOptions.area.id)
                                .success(function (data) {
                                    this.documentTypes = data;
                                    this.getDwEnterpriseSelected();
                                });
                    },
                    validateFile: function (file) {
                        if (!file.name.match(/(\.jpg|\.jpeg|\.pdf|\.png)$/i)) {
                            event.target.value = null;
                            showAlert("Tipo de archivo no admitido", {type: 3});
                            return false;
                        }

                        return true;
                    },
                    uploadFilesEmployee: function (document, idEmployee) {
                        this.isSaving = true;
                        this.$http.post(
                                ROOT_URL + "/employees/" + idEmployee + "/attachments",
                                document
                        ).success(function (data) {
                            location.reload();
                        }).error(function (data) {
                            this.isSaving = false;
                            showAlert(data.error.message, {type: 3})
                        });

                    },
                    validateEmail: function (email) {
                        var re = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
                        if (!re.test(email)) {
                            showAlert("Ingresa un email correcto", {type: 3});
                            this.employee.mail = '';
                        }
                    },
                    validateRfc: function (rfc) {
                        if (rfc.length < 13) {
                            showAlert("El rfc debe contener 13 caracteres", {type: 3});
                        } else {
                            this.$http.get(ROOT_URL + "/employees/validate/rfc?rfc=" + rfc).success(function (data) {
                                return true;
                            }).error(function (element) {
                                showAlert("El rfc ya existe", {type: 3});
                                this.emptyFields();
                            })
                        }
                    },
                    validateCurp: function (curp) {
                        if (curp.length < 18) {
                            showAlert("El curp debe contener 18 caracteres", {type: 3});
                        } else {
                            this.$http.get(ROOT_URL + "/employees/validate/curp?curp=" + curp).success(function (data) {
                                return true;
                            }).error(function (element) {
                                showAlert("El curp ya existe", {type: 3});
                                this.emptyFields();
                            })
                        }
                    },
                    getDwEnterpriseSelected: function () {
                        this.$http.get(ROOT_URL + "/dw-enterprises/branch-area/" + this.selectedOptions.branch.id + "/" + this.selectedOptions.area.id).success(function (data) {
                            this.dwEnter = data;
                        });
                    },
                    validateAccountEmployee: function (account, clabe) {
                        if (account.length > 0 && clabe.length > 0) {
                            if (clabe.indexOf(account) == -1) {
                                if (!showAlert("La clabe es incorrecta", {type: 3})) {
                                    this.cuenta.accountNumber = "";
                                    this.cuenta.accountClabe = "";
                                }
                            }
                        } else {
                            if (account.length > 0 && (account.length > 11 || account.length < 5)) {
                                showAlert("Debes ingresar entre 5 y 11 caracteres en el numero de cuenta", {type: 3});
                            }
                            if (clabe.length > 0 && clabe.length < 18) {
                                showAlert("Debes ingresar 18 caracteres en la CLABE", {type: 3});
                            }
                            if (account.length < 5 && clabe.length < 18) {
                                showAlert("Debes ingresar un Numero de Cuenta o CLABE", {type: 3});
                            }
                        }
                    },
                    setFile: function (event, docType) {
                        var self = this;
                        var document = {};
                        var index = docType.idDocumentType - 1;

                        document.documentType = docType.documentType;

                        var reader = new FileReader();
                        var file = event.target.files[0];

                        if (file) {
                            if (this.validateFile(file)) {
                                reader.onload = (function (theFile) {
                                    return function (e) {

                                        document.file = {
                                            name: theFile.name,
                                            size: theFile.size,
                                            type: theFile.type,
                                            dataUrl: e.target.result
                                        };

                                        if (index > -1) {
                                            self.newEmployeeDocuments[index] = document;
                                        }
                                    };
                                })(file);
                                reader.readAsDataURL(file);
                            } else {
                                if (index > -1) {
                                    self.newEmployeeDocuments.splice(index, 1);
                                }
                            }
                        } else {
                            if (index > -1) {
                                self.newEmployeeDocuments.splice(index, 1);
                            }
                        }
                    },
                    emptyFields: function () {
                        this.employee.firstName = '';
                        this.employee.middleName = '';
                        this.employee.parentalLast = '';
                        this.employee.motherLast = '';
                        this.employee.rfc = '';
                        this.employee.claveSap = '';
                        this.employee.curp = '';
                        this.employee.sistarh = '';
                        this.employee.imss = '';
                        this.employee.infonavitNumber = '';
                        this.employee.mail = '';
                        this.employee.employeeType = '';
                        this.employee.contractType = '';
                        this.employee.salary = 0;
                        this.employee.joinDate = '';
                        this.employee.status = '';
                        this.employee.street = '';
                        this.employee.exteriorNumber = '';
                        this.employee.interiorNumber = '';
                        this.employee.colonia = '';
                        this.employee.city = '';
                        this.employee.state = '';
                        this.employee.postcode = '';
                        this.employee.cellPhone = '';
                        this.employee.fatherName = '';
                        this.employee.motherName = '';
                        this.employee.idEducation = '';
                        this.employee.homePhone = '';
                        this.employee.idSize = '';
                        this.employee.sizeNumber = '';
                        this.employee.gender = '';
                        this.employee.birthPlace = '';
                        this.employee.idStatusMarital = '';
                        this.employee.birthday = '';
                        this.joinDate = '';
                        this.birthday = '';
                        this.estadosMunicipios.nombreMunicipios = '';
                        this.estadosMunicipios.estado.nombreEstado = '';
                    },
                    getSizes: function () {
                        this.$http.get(ROOT_URL + "/sizes").success(function (data) {
                            this.sizes = data;
                        });
                    },
                    fetchHierarchy: function () {
                        this.$http.get(ROOT_URL + "/dw-enterprises/hierarchy").success(function (data) {
                            this.selectOptions.hierarchy = data;
                        });
                    },
                    selectedOptionsDistributorChanged: function () {
                        this.selectedOptions.region = this.defaultRegion;
                        this.selectedOptions.zona = this.defaultZona;
                        this.selectedOptions.branch = this.defaultBranch;
                        this.selectedOptions.area = this.defaultArea;
                        this.selectedOptions.role = this.defaultRole;
                    },
                    selectedOptionsRegionChanged: function () {
                        this.selectedOptions.zona = this.defaultZona;
                        this.selectedOptions.branch = this.defaultBranch;
                        this.selectedOptions.area = this.defaultArea;
                        this.selectedOptions.role = this.defaultRole;
                    },
                    selectedOptionsZonaChanged: function () {
                        this.selectedOptions.branch = this.defaultBranch;
                        this.selectedOptions.area = this.defaultArea;
                        this.selectedOptions.role = this.defaultRole;
                    },
                    selectedOptionsBranchChanged: function () {
                        this.selectedOptions.area = this.defaultArea;
                        this.selectedOptions.role = this.defaultRole;
                    },
                    selectedOptionsAreaChanged: function () {
                        this.selectOptions.roles = [];
                        this.selectedOptions.role = this.defaultRole;
                        this.$http.get(ROOT_URL + "/areas/area-role/" + this.selectedOptions.area.id).success(function (data) {
                            this.selectOptions.roles = data.roles;
                        });
                    }
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
                        <h2>Registro de empleado</h2>
                    </div>
                </div>
            </div>
            <div class="row">
                <form id="attachments-form" method="post" enctype="multipart/form-data"
                      v-on:submit.prevent="saveEmployee">
                    <br>
                    <div class="panel panel-default">
                        <div class="panel-heading">Información personal</div>
                        <div class="panel-body">
                            <div class="col-xs-12">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <label>Nombre</label>
                                        <input class="form-control" name="name" v-model="employee.firstName"
                                               onkeypress="return isLetterKey(event)" required>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Segundo nombre</label>
                                        <input class="form-control" name="name" v-model="employee.middleName"
                                               onkeypress="return isLetterKey(event)">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Apellido paterno</label>
                                        <input class="form-control" name="name" v-model="employee.parentalLast"
                                               onkeypress="return isLetterKey(event)" required>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Apellido materno</label>
                                        <input class="form-control" name="name" v-model="employee.motherLast"
                                               onkeypress="return isLetterKey(event)" required>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-3">
                                        <label>Genero</label>
                                        <select class="form-control" name="" v-model="employee.gender" required>
                                            <option></option>
                                            <option v-for="gender in genders" :value="gender.idGender">
                                                {{gender.genderName}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Fecha de Nacimiento</label>
                                        <div class='input-group date' id='dateBirthDay'>
                                            <input type='text' class="form-control" v-model="birthday" required>
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                        </div>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Lugar de Nacimiento</label>
                                        <select class="form-control" name="" v-model="employee.birthPlace" required>
                                            <option></option>
                                            <option v-for="estado in estados"
                                                    value="{{estado.nombreEstado}}">
                                                {{estado.nombreEstado}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>CURP</label>
                                        <input class="form-control" name="name" v-model="employee.curp" maxlength="18"
                                               @change="validateCurp(employee.curp)" required>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-3">
                                        <label>Código postal</label>
                                        <input class="form-control" name="name" maxlength="5"
                                               v-model="employee.postcode"
                                               @input="obtainAsentamientos"
                                               onkeypress="return isNumberKey(event)" required>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Estado</label>
                                        <input class="form-control" name="name"
                                               v-model="estadosMunicipios.estado.nombreEstado" disabled>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Municipio/Delegación</label>
                                        <input class="form-control" name="name"
                                               v-model="estadosMunicipios.nombreMunicipios" disabled>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Colonia</label>
                                        <select class="form-control" name="" v-model="employee.colonia" required>
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
                                        <input class="form-control" name="name" v-model="employee.street" required>
                                    </div>
                                    <div class="col-xs-2">
                                        <label>Número Exterior</label>
                                        <input class="form-control" name="name" maxlength="5"
                                               v-model="employee.exteriorNumber" required>
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
                                        <input class="form-control" name="name" v-model="employee.fatherName"
                                               onkeypress="return isLetterKey(event)">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Nombre de la madre</label>
                                        <input class="form-control" name="name" v-model="employee.motherName"
                                               onkeypress="return isLetterKey(event)">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Estado civil</label>
                                        <select class="form-control" name="" v-model="employee.idStatusMarital"
                                                required>
                                            <option></option>
                                            <option v-for="status in statusMarital" value="{{status.idStatusMarital}}">
                                                {{status.maritalName}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Escolaridad</label>
                                        <select class="form-control" name="" v-model="employee.idEducation" required>
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
                                        <select class="form-control" v-model="employee.idSize">
                                            <option v-for="size in sizes" :value="size.idSize">
                                                {{size.sizeName}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Número de talla</label>
                                        <input class="form-control" name="name" v-model="employee.sizeNumber"
                                               maxlength="2" onkeypress="return isNumberKey(event)">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Télefono de casa</label>
                                        <input class="form-control" name="name" v-model="employee.homePhone"
                                               maxlength="10" onkeypress="return isNumberKey(event)" required>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Móvil</label>
                                        <input class="form-control" name="name" v-model="employee.cellPhone"
                                               maxlength="10" onkeypress="return isNumberKey(event)" required>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-3">
                                        <label>Email</label>
                                        <input class="form-control" name="name" v-model="employee.mail"
                                               @change="validateEmail(employee.mail)" required>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Clave SAP</label>
                                        <input class="form-control" name="name" v-model="employee.claveSap"
                                               maxlength="15">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>RFC</label>
                                        <input class="form-control" name="name" v-model="employee.rfc" maxlength="13"
                                               @change="validateRfc(employee.rfc)" required>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Fecha de ingreso</label>
                                        <div class='input-group date' id='dateJoin'>
                                            <input type='text' class="form-control" v-model="joinDate" required>
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
                                                required @change="selectedOptionsDistributorChanged">
                                            <option v-for="distributor in selectOptions.hierarchy[0].subLevels"
                                                    :value="distributor">
                                                {{ distributor.name }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Región</label>
                                        <select v-model="selectedOptions.region" class="form-control"
                                                required @change="selectedOptionsRegionChanged"
                                                :disabled="selectedOptions.distributor.id == 0">
                                            <option v-for="region in selectedOptions.distributor.subLevels"
                                                    :value="region">
                                                {{ region.name }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Zona</label>
                                        <select v-model="selectedOptions.zona" class="form-control"
                                                required @change="selectedOptionsZonaChanged()"
                                                :disabled="selectedOptions.region.id == 0">
                                            <option v-for="area in selectedOptions.region.subLevels"
                                                    :value="area">
                                                {{ area.name }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Sucursal</label>
                                        <select v-model="selectedOptions.branch" class="form-control"
                                                required @change="selectedOptionsBranchChanged"
                                                :disabled="selectedOptions.zona.id == 0">
                                            <option v-for="branch in selectedOptions.zona.subLevels"
                                                    :value="branch">
                                                {{ branch.name }}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-3">
                                        <label>Área</label>
                                        <select v-model="selectedOptions.area" class="form-control"
                                                required @change="selectedOptionsAreaChanged()"
                                                :disabled="selectedOptions.branch.id == 0">
                                            <option v-for="area in selectedOptions.branch.subLevels"
                                                    :value="area">
                                                {{ area.name }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Puesto</label>
                                        <select v-model="selectedOptions.role" class="form-control"
                                                required @change="fetchDocumentTypes()"
                                                :disabled="selectedOptions.area.id == 0">
                                            <option v-for="role in selectOptions.roles"
                                                    :value="role">
                                                {{ role.roleName }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-2">
                                        <label>Tipo de empleado</label>
                                        <select class="form-control" name="" v-model="employee.employeeType" required>
                                            <option></option>
                                            <option v-for="type in employeeTypes" :value="type.idEmployeeType">
                                                {{type.employeeTypeName}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-2">
                                        <label>Tipo de contrato</label>
                                        <select class="form-control" name="" v-model="employee.contractType" required>
                                            <option></option>
                                            <option v-for="contract in contractTypes" :value="contract.idContractType">
                                                {{contract.contractTypeName}}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-3"
                                         v-if="dwEnter.idDistributor == 2 && selectedOptions.role.idRole == 4">
                                        <label>SISTARH</label>
                                        <input class="form-control" maxlength="2" name="name" v-model="employee.sistarh"
                                               onkeypress="return isNumberKey(event)">
                                    </div>
                                    <div class="col-xs-3" v-if="employee.employeeType == 1">
                                        <label>IMSS</label>
                                        <input class="form-control" name="name" v-model="employee.imss" maxlength="18"
                                               onkeypress="return isNumberKey(event)">
                                    </div>
                                    <div class="col-xs-3" v-if="employee.employeeType == 1">
                                        <label>Infonavit</label>
                                        <input class="form-control" name="name" v-model="employee.infonavitNumber"
                                               maxlength="15">
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
                                        <select class="form-control" name="" v-model="cuenta.idBank" required>
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
                                                   onkeypress="return isNumberKey(event)"
                                                   @change="validateAccountEmployee(cuenta.accountNumber,cuenta.accountClabe)"
                                                   required>
                                        </div>
                                    </div>

                                    <div class="col-xs-3">
                                        <label>CLABE</label>
                                        <div class="input-group">
                                            <span class="input-group-addon">#</span>
                                            <input type="text" id="sclabe" class="form-control" maxlength="18"
                                                   v-model="cuenta.accountClabe"
                                                   onkeypress="return isNumberKey(event)"
                                                   @change="validateAccountEmployee(cuenta.accountNumber,cuenta.accountClabe)"
                                                   required>
                                        </div>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Salario</label>
                                        <input type="text" class="form-control" maxlength="18"
                                               v-model="employee.salary"
                                               onkeypress="return isNumberKey(event)" required>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="panel panel-default" v-show="selectOptions.roles.length > 0">
                        <div class="panel-heading">Documentación</div>
                        <div class="panel-body">
                            <div class="col-xs-12">
                                <div class="row">
                                    <table class="table table-striped">
                                        <tr v-for="docType in documentTypes"
                                            v-if="docType.documentType.field == 0 && docType.documentType.required == 1">
                                            <td v-if="docType.documentType.field == 0 && docType.documentType.required == 1">
                                                {{ docType.documentType.documentName }}
                                            </td>
                                            <td>
                                                <input v-if="docType.documentType.field == 0 && docType.documentType.required == 1"
                                                       @change="setFile($event, docType)" type="file"
                                                       class="form-control"
                                                       :disabled="isSaving"
                                                       :name="'file-type-' + docType.documentType.idDocumentType"
                                                       accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg," required>
                                            </td>
                                        </tr>
                                        <tr v-for="docType in documentTypes"
                                            v-if="docType.documentType.field == 0 && docType.documentType.required == 0 && docType.documentType.idDocumentType < 13">
                                            <td v-if="docType.documentType.field == 0 && docType.documentType.required == 0 && docType.documentType.idDocumentType < 13">
                                                {{ docType.documentType.documentName }}
                                            </td>
                                            <td>
                                                <input v-if="docType.documentType.field == 0 && docType.documentType.required == 0 && docType.documentType.idDocumentType < 13"
                                                       @change="setFile($event, docType)" type="file"
                                                       class="form-control"
                                                       :disabled="isSaving"
                                                       :name="'file-type-' + docType.documentType.idDocumentType"
                                                       accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                            </td>
                                        </tr>
                                        <tr v-for="docType in documentTypes"
                                            v-if="docType.documentType.field == 1 && employee.infonavitNumber !== '' && docType.documentType.idDocumentType == 10">
                                            <td v-if="docType.documentType.field == 1 && employee.infonavitNumber !== '' && docType.documentType.idDocumentType == 10">
                                                {{ docType.documentType.documentName }}
                                            </td>
                                            <td>
                                                <input v-if="docType.documentType.field == 1 && employee.infonavitNumber !== '' && docType.documentType.idDocumentType == 10"
                                                       @change="setFile($event, docType)" type="file"
                                                       class="form-control"
                                                       :disabled="isSaving"
                                                       :name="'file-type-' + docType.documentType.idDocumentType"
                                                       accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg," required>
                                            </td>
                                        </tr>
                                        <tr v-for="docType in documentTypes"
                                            v-if="docType.documentType.field == 1 && employee.imss !== '' && docType.documentType.idDocumentType == 11">
                                            <td v-if="docType.documentType.field == 1 && employee.imss !== '' && docType.documentType.idDocumentType == 11">
                                                {{ docType.documentType.documentName }}
                                            </td>
                                            <td>
                                                <input v-if="docType.documentType.field == 1 && employee.imss !== '' && docType.documentType.idDocumentType == 11"
                                                       @change="setFile($event, docType)" type="file"
                                                       class="form-control"
                                                       :disabled="isSaving"
                                                       :name="'file-type-' + docType.documentType.idDocumentType"
                                                       accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg," required>
                                            </td>
                                        </tr>
                                        <tr v-for="docType in documentTypes"
                                            v-show="docType.documentType.field == 1 && employee.sistarh !== '' && docType.documentType.idDocumentType == 12 && docType.idDistributor == 2 && selectedOptions.role.idRole == 4">
                                            <td v-show="docType.documentType.field == 1 && employee.sistarh !== '' && docType.documentType.idDocumentType == 12 && docType.idDistributor == 2 && selectedOptions.role.idRole == 4">
                                                {{ docType.documentType.documentName }}
                                            </td>
                                            <td>
                                                <input v-show="docType.documentType.field == 1 && employee.sistarh !== '' && docType.documentType.idDocumentType == 12 && docType.idDistributor == 2 && selectedOptions.role.idRole == 4"
                                                       @change="setFile($event, docType)" type="file"
                                                       class="form-control"
                                                       :disabled="isSaving"
                                                       :name="'file-type-' + docType.documentType.idDocumentType"
                                                       accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                            </td>
                                        </tr>
                                        <tr v-for="docType in documentTypes"
                                            v-show="docType.documentType.field == 0 && docType.documentType.idDocumentType >= 13 && docType.idDistributor == 2 && selectedOptions.role.idRole == 4">
                                            <td v-show="docType.documentType.field == 0 && docType.documentType.idDocumentType >= 13 && docType.idDistributor == 2 && selectedOptions.role.idRole == 4">
                                                {{ docType.documentType.documentName }}
                                            </td>
                                            <td>
                                                <input v-show="docType.documentType.field == 0 && docType.documentType.idDocumentType >= 13 && docType.idDistributor == 2 && selectedOptions.role.idRole == 4"
                                                       @change="setFile($event, docType)" type="file"
                                                       class="form-control"
                                                       :disabled="isSaving"
                                                       :name="'file-type-' + docType.documentType.idDocumentType"
                                                       accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-11">
                        </div>
                        <div class="col-xs-1">
                            <button type="submit" :disabled="isSaving"
                                    class="btn btn-success">
                                Guardar
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
