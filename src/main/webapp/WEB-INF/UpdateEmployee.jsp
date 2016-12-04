<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 27/06/16
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>

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
            var vm = new Vue({
                el: '#content',
                created: function () {

                },
                ready: function () {
                    this.getDwEmployee();
                    this.obtainStates();
                    this.getSizes();
                    this.obtainEducation();
                    this.obtainStatusMarital();
                    this.obtainBanks();
                    this.obtainEmployeeTypes();
                    this.obtainContractTypes();
                    this.obtainGenders();
                    this.getDistributors();
                },
                data: {
                    dwEmployee: {},
                    dwEnterprise: {},
                    role: {},
                    idDwEmployee: ${idDwEmployee},
                    estadosMunicipios: [],
                    asentamiento: [],
                    educationList: [],
                    statusMaritalList: [],
                    estados: [],
                    sizes: [],
                    banks: [],
                    employeeAccount: {},
                    contractTypes: [],
                    employeeTypes: [],
                    genders: [],
                    branchs: [],
                    documentTypes: [],
                    employeeDocuments: [],
                    newEmployeeDocuments: [],
                    selectOptions: {
                        areas: [],
                        roles: [],
                        dwEnterprises: [],
                        documentTypes: [],
                        hierarchy: []
                    },
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
                    defaultDistributor: {
                        idDistributor: 0,
                        name: ''
                    },
                    timePickerJoinDate: '',
                    timePickerBirthday: '',
                    regresarBusqueda: ROOT_URL + '/saem/search-employees',
                    downloadUrl: ROOT_URL + '/employee-documents/download/',
                    distributors: [],
                    areas: [],
                    regions: [],
                    zonas: [],
                    branchch: [],
                    selectedArea: {},
                    haveImss: false
                },
                methods: {
                    getDwEmployee: function () {
                        this.$http.get(ROOT_URL + '/dw-employees/' + this.idDwEmployee)
                                .success(function (data) {
                                    this.dwEmployee = data;
                                    this.setDates();
                                    this.dwEnterprise = this.dwEmployee.dwEnterprise;
                                    this.role = this.dwEmployee.role;
                                    this.obtainAsentamientos();
                                    this.getEmployeeAccounts();
                                    this.getEmployeeDocuments();
                                    this.newEmployeeDocuments = [];
                                })
                                .error(function (data) {

                                });
                    },
                    getEmployeeDocuments: function () {
                        this.$http.get(ROOT_URL + '/employee-documents/' + this.dwEmployee.idEmployee)
                                .success(function (data) {
                                    this.employeeDocuments = data;
                                    this.getDocumentTypes();
                                });
                    },
                    getDocumentTypes: function () {
                        this.$http.get(ROOT_URL + "/employee-document-types/employee/" + this.dwEmployee.idDwEmployee)
                                .success(function (data) {
                                    this.documentTypes = data;
                                });
                    },
                    setDates: function () {
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
                    getEmployeeAccounts: function () {
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
                    getSizes: function () {
                        this.$http.get(ROOT_URL + "/sizes").success(function (data) {
                            this.sizes = data;
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
                    obtainAsentamientos: function () {
                        var postcode = this.dwEmployee.employee.postcode;
                        if (this.dwEmployee.employee.postcode.length >= 5) {
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
                    validateFile: function (event) {
                        if (!event.target.files[0].name.match(/(\.jpg|\.jpeg|\.pdf|\.png)$/i)) {
                            event.target.value = null;
                            showAlert("Tipo de archivo no admitido", {type: 3});
                        }
                    },
                    validateEmail: function (email) {
                        var re = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
                        if (!re.test(email)) {
                            showAlert("Ingresa un email correcto", {type: 3});
                            this.dwEmployee.employee.mail = '';
                        }
                    },
                    validateRfc: function (rfc) {
                        if (rfc.length < 13) {
                            showAlert("El rfc debe contener 13 caracteres", {type: 3});
                        }
                    },
                    validateCurp: function (curp) {
                        if (curp.length < 18) {
                            showAlert("El curp debe contener 18 caracteres", {type: 3});
                        }
                    },
                    updateEmployee: function () {
                        var self = this;

                        this.dwEmployee.employee.state = this.estadosMunicipios.estado.nombreEstado;
                        this.dwEmployee.employee.city = this.estadosMunicipios.nombreMunicipios;

                        var requestBody = {
                            dwEmployee: this.dwEmployee,
                            employeeAccount: this.employeeAccount
                        };

                        if(this.selectedOptions.distributor.idDistributor > 0){
                            this.dwEmployee.dwEnterprise = this.selectedOptions;
                            this.dwEmployee.role = this.selectedOptions.role;
                        }

                        this.$http.post(ROOT_URL + "/dw-employees/update", requestBody).success(function (data) {
                            showAlert("Actualización de empleado exitoso");
                            if (this.newEmployeeDocuments.length > 0) {
                                this.newEmployeeDocuments.forEach(function (document) {
                                    self.updateEmployeeDocument(document);
                                });
                            }
                        }).error(function (data) {
                            showAlert("Ha habido un error con la solicitud, intente nuevamente", {type: 3});
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
                    updateEmployeeDocument: function (document) {
                        this.$http.post(ROOT_URL + '/employee-documents/update/' + this.dwEmployee.idEmployee, document)
                                .success(function (data) {
                                    this.getDwEmployee();
                                })
                                .error(function (data) {

                                });
                    },
                    validateAccountEmployee: function (account, clabe) {
                        if (account.length > 0 && clabe.length > 0) {
                            if (clabe.indexOf(account) == -1) {
                                if (!showAlert("La clabe es incorrecta", {type: 3})) {
                                    this.employeeAccount.account.accountNumber = '';
                                    this.employeeAccount.account.accountClabe = '';
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
                    setFile: function (event, object) {
                        var self = this;
                        var document = {};
                        var index;

                        if ("idDocumentType" in object) {
                            index = object.idDocumentType - 1;
                        } else {
                            index = object.cDocumentType.idDocumentType - 1;
                        }

                        if ("idDocument" in object) {
                            document.idDocument = object.idDocument;
                        } else {
                            document.documentType = object;
                        }


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
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    validateRoleBranch: function () {
                        var self = this;
                        this.$http.get(ROOT_URL + "/dw-enterprises?idDistributor="+this.selectedOptions.distributor.idDistributor
                                +"&idRegion="+this.selectedOptions.region.idRegion+"&idZona="+this.selectedOptions.zona.idZonas
                                +"&idBranch="+this.selectedOptions.branch.idBranch+"&idArea="+this.selectedOptions.area.idArea).success(function (data) {
                            self.$http.get(ROOT_URL + "/dw-employees/validate-role/"+data[0].idDwEnterprise+"/"+self.selectedOptions.role.idRole).success(function () {

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
                        this.$http.get(ROOT_URL + "/dw-enterprises?idDistributor="+this.selectedOptions.distributor.idDistributor
                                +"&idRegion="+this.selectedOptions.region.idRegion+"&idZona="+this.selectedOptions.zona.idZonas
                                +"&idBranch=0&idArea="+this.selectedOptions.area.idArea).success(function (data) {
                            self.$http.get(ROOT_URL + "/dw-employees/validate-role/"+data[0].idDwEnterprise+"/"+self.selectedOptions.role.idRole).success(function () {

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
                        this.$http.get(ROOT_URL + "/dw-enterprises?idDistributor="+this.selectedOptions.distributor.idDistributor
                                +"&idRegion="+this.selectedOptions.region.idRegion+"&idZona=0&idBranch=0&idArea="+this.selectedOptions.area.idArea).success(function (data) {
                            self.$http.get(ROOT_URL + "/dw-employees/validate-role/"+data[0].idDwEnterprise+"/"+self.selectedOptions.role.idRole).success(function () {

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
                    validateImss: function () {
                        if(this.dwEmployee.employee.imss.length > 0){
                            this.haveImss = true;
                        }else{
                            this.haveImss = false;
                        }
                    },
                    exit: function () {
                        window.location = this.regresarBusqueda;
                    }
                }

            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="row">
                <div class="col-xs-8 text-header">
                    <h2>Modificación de empleados</h2>
                </div>
                <div class="col-xs-2">
                </div>
                <div class="col-xs-2 text-right" style="margin-top: 25px">
                    <button class="btn btn-default" @click="exit">Regresar
                    </button>
                </div>
            </div>
            <br>
            <form v-on:submit.prevent="updateEmployee">
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">Datos de la empresa</div>
                    <div class="panel-body">
                        <div class="col-xs-12">
                            <div class="row">
                                <label>Asignación actual</label>
                                <table class="table table-striped" v-if="role.idRole == 80 || role.idRole == 81 || role.idRole == 64 || role.idRole == 63">
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

                                <table class="table table-striped" v-show="dwEnterprise.distributor.saemFlag == false">
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
                            <br>
                            <div class="row">
                                <label>Reasignar</label>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>Selecciona Distribuidor/Empresa</label>
                                    <select v-model="selectedOptions.distributor" class="form-control"
                                            @change="distributorChanged">
                                        <option :value="defaultDistributor">
                                            {{ defaultDistributor.name }}
                                        </option>
                                        <option v-for="distributor in distributors"
                                                :value="distributor">
                                            {{ distributor.distributorName }}
                                        </option>
                                    </select>
                                </div>
                                <div>
                                    <div class="col-xs-4">
                                        <label>Área</label>
                                        <select v-model="selectedOptions.area" class="form-control"
                                                :required="selectedOptions.distributor.idDistributor > 0" @change="areaChanged"
                                                :disabled="selectedOptions.distributor.idDistributor == 0">
                                            <option v-for="area in areas"
                                                    :value="area">
                                                {{ area.areaName }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-4">
                                        <label>Puesto</label>
                                        <select v-model="selectedOptions.role" class="form-control"
                                                :required="selectedOptions.distributor.idDistributor > 0" @change="roleChanged()"
                                                :disabled="selectedOptions.area.idArea == 0">
                                            <option v-for="role in selectedArea"
                                                    :value="role.role">
                                                {{ role.role.roleName }}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="row"
                                 v-if="selectedOptions.role.idRole == 80 || selectedOptions.role.idRole == 81 || selectedOptions.role.idRole == 64 || selectedOptions.role.idRole == 82">
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
                            </div>
                            <br>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">Información personal</div>
                    <div class="panel-body">
                        <div class="col-xs-12">
                            <div class="row">
                                <div class="col-xs-3">
                                    <label>Nombre</label>
                                    <input class="form-control" v-model="dwEmployee.employee.firstName"
                                           onkeypress="return isLetterKey(event)" required>
                                </div>
                                <div class="col-xs-3">
                                    <label>Segundo nombre</label>
                                    <input class="form-control" v-model="dwEmployee.employee.middleName"
                                           onkeypress="return isLetterKey(event)">
                                </div>
                                <div class="col-xs-3">
                                    <label>Apellido paterno</label>
                                    <input class="form-control" v-model="dwEmployee.employee.parentalLast"
                                           onkeypress="return isLetterKey(event)" required>
                                </div>
                                <div class="col-xs-3">
                                    <label>Apellido materno</label>
                                    <input class="form-control" v-model="dwEmployee.employee.motherLast"
                                           onkeypress="return isLetterKey(event)">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-3">
                                    <label>Genero</label>
                                    <select class="form-control" v-model="dwEmployee.employee.gender" required>
                                        <option v-for="gender in genders" :value="gender">
                                            {{gender.genderName}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-3">
                                    <label>Fecha de Nacimiento</label>
                                    <div class="form-group">
                                        <div class="input-group date" id="birthday"
                                             @click="activeDateTimePickerBirthday">
                                            <input type="text" class="form-control"
                                                   v-model="dwEmployee.employee.birthday" required>
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-3">
                                    <label>Lugar de Nacimiento</label>
                                    <select class="form-control" v-model="dwEmployee.employee.birthPlace" required>
                                        <option v-for="estado in estados"
                                                value="{{estado.nombreEstado}}">
                                            {{estado.nombreEstado}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-3">
                                    <label>CURP</label>
                                    <input class="form-control" v-model="dwEmployee.employee.curp" maxlength="18"
                                           @change="validateCurp(employee.curp)" required>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-3">
                                    <label>Código postal</label>
                                    <input class="form-control" maxlength="5" v-model="dwEmployee.employee.postcode"
                                           @input="obtainAsentamientos"
                                           onkeypress="return isNumberKey(event)" required>
                                </div>
                                <div class="col-xs-3">
                                    <label>Estado</label>
                                    <input class="form-control" v-model="estadosMunicipios.estado.nombreEstado" disabled
                                           required>
                                </div>
                                <div class="col-xs-3">
                                    <label>Municipio/Delegación</label>
                                    <input class="form-control" v-model="estadosMunicipios.nombreMunicipios" disabled
                                           required>
                                </div>
                                <div class="col-xs-3">
                                    <label>Colonia</label>
                                    <select class="form-control" v-model="dwEmployee.employee.colonia" required>
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
                                    <input class="form-control" v-model="dwEmployee.employee.street" required>
                                </div>
                                <div class="col-xs-2">
                                    <label>Número Exterior</label>
                                    <input class="form-control" maxlength="5"
                                           v-model="dwEmployee.employee.exteriorNumber" required>
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
                                    <input class="form-control" v-model="dwEmployee.employee.fatherName"
                                           onkeypress="return isLetterKey(event)" :required="haveImss == true">
                                </div>
                                <div class="col-xs-3">
                                    <label>Nombre de la madre</label>
                                    <input class="form-control" v-model="dwEmployee.employee.motherName"
                                           onkeypress="return isLetterKey(event)" :required="haveImss == true">
                                </div>
                                <div class="col-xs-3">
                                    <label>Estado civil</label>
                                    <select class="form-control" v-model="dwEmployee.employee.statusMarital" required>
                                        <option v-for="statusMarital in statusMaritalList" value="{{statusMarital}}">
                                            {{statusMarital.maritalName}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-3">
                                    <label>Escolaridad</label>
                                    <select class="form-control" v-model="dwEmployee.employee.education" required>
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
                                    <select class="form-control" v-model="dwEmployee.employee.size">
                                        <option v-for="size in sizes" :value="size">
                                            {{size.sizeName}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-3">
                                    <label>Número de talla</label>
                                    <input class="form-control" name="name" v-model="dwEmployee.employee.sizeNumber"
                                           maxlength="2" onkeypress="return isNumberKey(event)">
                                </div>
                                <div class="col-xs-3">
                                    <label>Télefono de casa</label>
                                    <input class="form-control" v-model="dwEmployee.employee.homePhone" maxlength="10"
                                           onkeypress="return isNumberKey(event)" required>
                                </div>
                                <div class="col-xs-3">
                                    <label>Móvil</label>
                                    <input class="form-control" v-model="dwEmployee.employee.cellPhone" maxlength="10"
                                           onkeypress="return isNumberKey(event)" required>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-3">
                                    <label>Email</label>
                                    <input class="form-control" v-model="dwEmployee.employee.mail"
                                           @change="validateEmail(employee.mail)" required>
                                </div>
                                <div class="col-xs-3" v-if="dwEnterprise.distributor.saemFlag == true || selectedOptions.distributor.saemFlag == true ">
                                    <label>Clave SAP</label>
                                    <input class="form-control" v-model="dwEmployee.employee.claveSap">
                                </div>
                                <div class="col-xs-3">
                                    <label>RFC</label>
                                    <input class="form-control" v-model="dwEmployee.employee.rfc" maxlength="13"
                                           @change="validateRfc(employee.rfc)" required>
                                </div>
                                <div class="col-xs-3">
                                    <label>Fecha de ingreso</label>
                                    <div class="form-group">
                                        <div class="input-group date" id="joinDate"
                                             @click="activeDateTimePickerJoinDate">
                                            <input type="text" class="form-control"
                                                   v-model="dwEmployee.employee.joinDate" required>
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
                    <div class="panel-heading">Datos del contrato</div>
                    <div class="panel-body">
                        <div class="col-xs-12">
                            <div class="row">
                                <div class="col-xs-2">
                                    <label>Tipo de empleado</label>
                                    <select class="form-control" v-model="dwEmployee.employee.employeeType" required>
                                        <option v-for="employeeType in employeeTypes" :value="employeeType">
                                            {{employeeType.employeeTypeName}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-2">
                                    <label>Tipo de contrato</label>
                                    <select class="form-control" v-model="dwEmployee.employee.contractType" required>
                                        <option v-for="contractType in contractTypes" :value="contractType">
                                            {{contractType.contractTypeName}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-3" v-if="dwEmployee.employee.employeeType.idEmployeeType == 1">
                                    <label>IMSS</label>
                                    <input class="form-control" maxlength="18" v-model="dwEmployee.employee.imss"
                                           onkeypress="return isNumberKey(event)" required @change="validateImss">
                                </div>
                                <div class="col-xs-3" v-if="dwEmployee.employee.employeeType.idEmployeeType == 1">
                                    <label>Infonavit</label>
                                    <input class="form-control" maxlength="15"
                                           v-model="dwEmployee.employee.infonavitNumber">
                                </div>
                                <div class="col-xs-3"
                                     v-show="(dwEnterprise.distributor.idDistributor == 2 && role.idRole == 63) || (selectedOptions.distributor.idDistributor == 2 && selectedOptions.role.idRole == 63)">
                                    <label>SISTARH</label>
                                    <input class="form-control" maxlength="3" onkeypress="return isNumberKey(event)"
                                           v-model="dwEmployee.employee.sistarh">
                                </div>
                            </div>
                            <br>
                        </div>
                    </div>
                </div>
                <br>
                <br>
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">Datos bancarios</div>
                    <div class="panel-body">
                        <div class="col-xs-12">
                            <div class="row">
                                <div class="col-xs-3">
                                    <label>Banco</label>
                                    <select class="form-control" v-model="employeeAccount.account.bank" required>
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
                                               onkeypress="return isNumberKey(event)"
                                               @change="validateAccountEmployee(employeeAccount.account.accountNumber,employeeAccount.account.accountClabe)"
                                               required>
                                    </div>
                                </div>

                                <div class="col-xs-3">
                                    <label>CLABE</label>
                                    <div class="input-group">
                                        <span class="input-group-addon">#</span>
                                        <input type="text" class="form-control" maxlength="18"
                                               v-model="employeeAccount.account.accountClabe"
                                               onkeypress="return isNumberKey(event)"
                                               @change="validateAccountEmployee(employeeAccount.account.accountNumber,employeeAccount.account.accountClabe)"
                                               required>
                                    </div>
                                </div>
                                <div class="col-xs-3">
                                    <label>Salario</label>
                                    <input type="text" class="form-control" maxlength="18"
                                           v-model="dwEmployee.employee.salary"
                                           onkeypress="return isNumberKey(event)" required>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">Documentos de empleado</div>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Tipo de documento</th>
                                <th>Nombre</th>
                                <th>Fecha de actualización</th>
                                <th>Nuevo Documento</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="employeeDocument in employeeDocuments">
                                <td>{{$index + 1}}</td>
                                <td>
                                    {{employeeDocument.cDocumentType.documentName}}
                                </td>
                                <td>
                                    <a :href="downloadUrl + employeeDocument.idDocument">{{employeeDocument.documentName}}</a>
                                </td>
                                <td>
                                    {{employeeDocument.uploadDateFormats.simpleDate}}
                                </td>
                                <td>
                                    <input type="file" class="form-control" @change="setFile($event, employeeDocument)">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <br>
                <div class="panel panel-default">
                    <div class="panel-heading">Cargar documentos de empleado</div>
                    <div class="panel-body">
                        <div class="col-xs-12">
                            <div class="row">
                                <table class="table table-striped">
                                    <tr v-for="docType in documentTypes"
                                        v-if="docType.field == 0 && docType.required == 1">
                                        <td v-if="docType.field == 0 && docType.required == 1">
                                            {{ docType.documentName }}
                                        </td>
                                        <td>
                                            <input v-if="docType.field == 0 && docType.required == 1"
                                                   @change="setFile($event, docType)" type="file"
                                                   class="form-control"
                                                   :disabled="isSaving"
                                                   :name="'file-type-' + docType.idDocumentType"
                                                   accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                        </td>
                                    </tr>
                                    <tr v-for="docType in documentTypes"
                                        v-if="docType.field == 0 && docType.required == 0 && docType.idDocumentType <= 9 && dwEmployee.dwEnterprise.distributor.idDistributor !== 2">
                                        <td v-if="docType.field == 0 && docType.required == 0 && docType.idDocumentType <= 9 && dwEmployee.dwEnterprise.distributor.idDistributor !== 2">
                                            {{ docType.documentName }}
                                        </td>
                                        <td>
                                            <input v-if="docType.field == 0 && docType.required == 0 && docType.idDocumentType <= 9 && dwEmployee.dwEnterprise.distributor.idDistributor !== 2"
                                                   @change="setFile($event, docType)" type="file"
                                                   class="form-control"
                                                   :disabled="isSaving"
                                                   :name="'file-type-' + docType.idDocumentType"
                                                   accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                        </td>
                                    </tr>
                                    <tr v-for="docType in documentTypes"
                                        v-if="docType.field == 0 && docType.required == 0 && docType.idDocumentType <= 9 && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole !== 63">
                                        <td v-if="docType.field == 0 && docType.required == 0  && docType.idDocumentType <= 9 && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole !== 63">
                                            {{ docType.documentName }}
                                        </td>
                                        <td>
                                            <input v-if="docType.field == 0 && docType.required == 0 && docType.idDocumentType <= 9 && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole !== 63"
                                                   @change="setFile($event, docType)" type="file"
                                                   class="form-control"
                                                   :disabled="isSaving"
                                                   :name="'file-type-' + docType.idDocumentType"
                                                   accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                        </td>
                                    </tr>
                                    <tr v-for="docType in documentTypes"
                                        v-if="docType.field == 0 && docType.required == 0 && docType.idDocumentType == 8  && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole ==  63">
                                        <td v-if="docType.field == 0 && docType.required == 0 && docType.idDocumentType == 8  && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole ==  63">
                                            {{ docType.documentName }}
                                        </td>
                                        <td>
                                            <input v-if="docType.field == 0 && docType.required == 0 && docType.idDocumentType == 8  && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole ==  63"
                                                   @change="setFile($event, docType)" type="file"
                                                   class="form-control"
                                                   :disabled="isSaving"
                                                   :name="'file-type-' + docType.idDocumentType"
                                                   accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                        </td>
                                    </tr>
                                    <tr v-for="docType in documentTypes"
                                        v-if="docType.field == 0 && docType.required == 0 && docType.idDocumentType == 9  && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole ==  63">
                                        <td v-if="docType.field == 0 && docType.required == 0 && docType.idDocumentType == 9  && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole ==  63">
                                            {{ docType.documentName }}
                                        </td>
                                        <td>
                                            <input v-if="docType.field == 0 && docType.required == 0 && docType.idDocumentType == 9 && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole ==  63"
                                                   @change="setFile($event, docType)" type="file"
                                                   class="form-control"
                                                   :disabled="isSaving"
                                                   :name="'file-type-' + docType.idDocumentType"
                                                   accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                        </td>
                                    </tr>
                                    <tr v-for="docType in documentTypes"
                                        v-if="docType.field == 1 && dwEmployee.employee.infonavitNumber.length !== 0 && docType.idDocumentType == 10">
                                        <td v-if="docType.field == 1 && dwEmployee.employee.infonavitNumber.length !== 0 && docType.idDocumentType == 10">
                                            {{ docType.documentName }}
                                        </td>
                                        <td>
                                            <input v-if="docType.field == 1 && dwEmployee.employee.infonavitNumber.length !== 0 && docType.idDocumentType == 10"
                                                   @change="setFile($event, docType)" type="file"
                                                   class="form-control"
                                                   :disabled="isSaving"
                                                   :name="'file-type-' + docType.idDocumentType"
                                                   accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                        </td>
                                    </tr>
                                    <tr v-for="docType in documentTypes"
                                        v-if="docType.field == 1 && dwEmployee.employee.imss.length !== 0  && docType.idDocumentType == 11">
                                        <td v-if="docType.field == 1 && dwEmployee.employee.imss.length !== 0  && docType.idDocumentType == 11">
                                            {{ docType.documentName }}
                                        </td>
                                        <td>
                                            <input v-if="docType.field == 1 && dwEmployee.employee.imss.length !== 0  && docType.idDocumentType == 11"
                                                   @change="setFile($event, docType)" type="file"
                                                   class="form-control"
                                                   :disabled="isSaving"
                                                   :name="'file-type-' + docType.idDocumentType"
                                                   accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                        </td>
                                    </tr>
                                    <tr v-for="docType in documentTypes"
                                        v-show="docType.field == 1 && dwEmployee.employee.sistarh !== '' && docType.idDocumentType == 12 && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole ==  63">
                                        <td v-show="docType.field == 1 && dwEmployee.employee.sistarh !== '' && docType.idDocumentType == 12 && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole == 63">
                                            {{ docType.documentName }}
                                        </td>
                                        <td>
                                            <input v-show="docType.field == 1 && dwEmployee.employee.sistarh !== '' && docType.idDocumentType == 12 && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole ==  63"
                                                   @change="setFile($event, docType)" type="file"
                                                   class="form-control"
                                                   :disabled="isSaving"
                                                   :name="'file-type-' + docType.idDocumentType"
                                                   accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                        </td>
                                    </tr>
                                    <tr v-for="docType in documentTypes"
                                        v-show="docType.field == 0 && docType.idDocumentType >= 13 && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole ==  63">
                                        <td v-show="docType.field == 0 && docType.idDocumentType >= 13 && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole ==  63">
                                            {{ docType.documentName }}
                                        </td>
                                        <td>
                                            <input v-show="docType.field == 0 && docType.idDocumentType >= 13 && dwEmployee.dwEnterprise.distributor.idDistributor == 2 && dwEmployee.role.idRole ==  63"
                                                   @change="setFile($event, docType)" type="file"
                                                   class="form-control"
                                                   :disabled="isSaving"
                                                   :name="'file-type-' + docType.idDocumentType"
                                                   accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-12">
                        <div class="col-xs-10">
                        </div>
                        <div class="col-xs-1">
                            <button class="btn btn-default" style="margin-left: 35px" @click="exit">Salir
                            </button>
                        </div>
                        <div class="col-xs-1">
                            <button type="submit" class="btn btn-success" style="margin-left: 5px">
                                Guardar
                            </button>
                        </div>
                    </div>
                </div>
            </form>
            <br>
            <br>
        </div>
    </jsp:body>
</t:template>
