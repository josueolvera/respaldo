<%--
  Created by IntelliJ IDEA.
  User: Kevin Salvador
  Date: 09/03/2017
  Time: 10:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../assets/css/barralateral.css">
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>

<t:template pageTitle="BID Group: Presupuestos">

    <jsp:attribute name="scripts">
        <script type="text/javascript">
            function validateFloatKeyPress(el, evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                var number = el.value.split('.');
                if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                if (number.length > 1 && charCode == 46) {
                    return false;
                }
                var caratPos = getSelectionStart(el);
                var dotPos = el.value.indexOf(".");
                if (caratPos > dotPos && dotPos > -1 && (number[1].length > 1)) {
                    return false;
                }
                return true;
            }
            function getSelectionStart(o) {
                if (o.createTextRange) {
                    var r = document.selection.createRange().duplicate();
                    r.moveEnd('character', o.value.length);
                    if (r.text == '') return o.value.length;
                    return o.value.lastIndexOf(r.text)
                } else return o.selectionStart
            }
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>
        <script>
            Number.prototype.formatMoney = function (places, symbol, thousand, decimal) {
                places = !isNaN(places = Math.abs(places)) ? places : 2;
                symbol = symbol !== undefined ? symbol : "$";
                thousand = thousand || ",";
                decimal = decimal || ".";
                var number = this,
                    negative = number < 0 ? "-" : "",
                    i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
                    j = (j = i.length) > 3 ? j % 3 : 0;
                return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
            };
        </script>
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                created: function () {
                    this.setYears();
                },
                ready: function () {
                    this.getBussinessLines();
                    this.getUserInSession();
                    this.getMonths();
                },
                data: {
                    now: "${now}",
                    minYear: null,
                    maxYear: null,
                    totalCostCenter: null,
                    months: [],
                    years: [],
                    yearbefore: null,
                    budgets: [],
                    concepts: [],
                    user: {},
                    selected: {
                        bussiness: null,
                        distributor: null,
                        costCenter: null,
                        year: null,
                        budgetCategory: null,
                        budget: null,
                        subbudget: null
                    },
                    bussinessLine: {},
                    distributor: {},
                    searching: false,
                    rolesCostCenter: [],
                    costCenterList: [],
                    budgetNatureList: [],
                    budgetCategories: [],
                    role: null,
                    errorMessage: '',
                    authorizationBudget: {},
                    valida: false,
                    authoriza: false,
                    modifica: false,
                    secondLevel: 0,
                    bussinessLines: [],
                    selectedOptions: {
                        bussinessLinesSelected: [],
                        distributorsSelected: [],
                        costCenterSelected: [],
                        year: '',
                        reportName: ''
                    },
                    distributors: [],
                    costCenters: [],
                    bussinesSelect: [],
                    bussinessReports: [],
                    bussinessReport: {},
                    validationBussiness: {
                        bussinessReportsObject: {},
                        show: false
                    },
                    costCenterStatus: [],
                    authorizeOrDenieCostCenters: [],
                    costCenterByAuthorizeOrReject: {
                        costCenter: {},
                        reason: '',
                        status: '',
                        year: ''
                    },
                    costCenterNotAutorizhed: [],
                    costCenterToModify: {},
                    name: ''
                },
                methods: {
                    getAllCostCentersByStatus: function () {
                        this.$http.get(ROOT_URL + "/authorizathion-costcenter").success(function (data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (costCenter) {
                                if (isNaN(costCenter.costCenter)) {
                                    jsonObjectIndex[costCenter.costCenter._id] = costCenter.costCenter;
                                } else {
                                    costCenter.costCenter = jsonObjectIndex[costCenter.costCenter];
                                }
                            });

                            data.forEach(function (cCostCenterStatus) {
                                if (isNaN(cCostCenterStatus.cCostCenterStatus)) {
                                    jsonObjectIndex[cCostCenterStatus.cCostCenterStatus._id] = cCostCenterStatus.cCostCenterStatus;
                                } else {
                                    cCostCenterStatus.cCostCenterStatus = jsonObjectIndex[cCostCenterStatus.cCostCenterStatus];
                                }
                            });

                            data.forEach(function (user) {
                                if (isNaN(user.users)) {
                                    jsonObjectIndex[user.users._id] = user.users;
                                } else {
                                    user.users = jsonObjectIndex[user.users];
                                }
                            });

                            this.costCenterStatus = data;
                        }).error(function () {
                            showAlert("Error al realizar la solicitud", {type: 3});
                        });
                    },
                    getBussinessLines: function () {
                        this.$http.get(ROOT_URL + "/bussiness-line").success(function (data) {
                            this.bussinessLines = data;
                        }).error(function () {
                            showAlert("Error al generar la solicitud");
                        });
                    },
                    getUserInSession: function () {
                        this.$http.get(ROOT_URL + "/user")
                            .success(function (data) {
                                this.user = data;
                                this.role = this.user.dwEmployee.idRole;
                                this.getRolesCostCenter(this.role);
                            })
                            .error(function (data) {
                                showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                            });
                    },
                    getMonths: function () {
                        this.$http.get(ROOT_URL + '/months')
                            .success(function (data) {
                                this.months = data;
                            })
                            .error(function (data) {

                            });
                    },
                    getRolesCostCenter: function (idRole) {
                        this.$http.get(ROOT_URL + '/roles-cost-center/role/' + idRole)
                            .success(function (data) {
                                var self = this;
                                var index;
                                this.rolesCostCenter = data;
                                if (data.length < 2) {
                                    this.selected.budgetNature = data[0].budgetNature;
                                }
                                data.forEach(function (item) {
                                    index = self.arrayObjectIndexOf(self.costCenterList, item.costCenter.idCostCenter, 'idCostCenter');
                                    if (index == -1) self.costCenterList.push(item.costCenter);
                                });
                                data.forEach(function (item) {
                                    index = self.arrayObjectIndexOf(self.budgetNatureList, item.budgetNature.idBudgetNature, 'idBudgetNature');
                                    if (index == -1) self.budgetNatureList.push(item.budgetNature);
                                });
                            })
                            .error(function (data) {
                            });
                    },
                    setYears: function () {
                        var now = new Date(this.now);
                        this.selected.year = now.getFullYear();
                        this.minYear = this.selected.year - 3;
                        this.maxYear = this.selected.year + 5;
                        for (var i = this.minYear; i <= this.maxYear; i++) {
                            this.years.push(i)
                        }
                    },
                    searchBudget: function () {
                        this.searching = true;
                        this.getBudgets();
                    },
                    getBudgets: function () {
                        var anioanterior = this.selected.year;
                        this.yearbefore = anioanterior - 1;
                        var url = ROOT_URL +
                            '/budgets/get-budget-levels?bussinessline=' + this.selected.bussiness.idBusinessLine + '&distributor='
                            + this.selected.distributor.idDistributor + '&cost_center=' + this.selected.costCenter.idCostCenter
                            + '&year=' + this.selected.year;
                        this.$http.get(url).success(function (data) {
                            var self = this;
                            this.budgets = data;
                            this.searching = false;
                            this.getAuthorizationBudget();
                            if (data.length <= 0) {
                                showAlert('No existen presupuestos por autorizar');
                            }
                            //this.getTotalCostCenter();
                        }).error(function (data) {
                            showAlert("Hola");
                        });
                    },
                    getBussinessLineByValidation: function () {
                        if (this.selectedOptions.year > 0) {
                            this.$http.post(ROOT_URL + "/distributor-cost-center/prueba", JSON.stringify(this.selectedOptions)).success(function (data) {
                                this.budgets = data;
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            });
                        } else {
                            showAlert("Debes seleccionar por lo menos un año", {type: 3});
                        }
                    },
                    getAuthorizationBudget: function () {
                        this.$http.get(ROOT_URL + '/authorizathion-costcenter/cost?cost_center=' + this.selected.costCenter.idCostCenter + '&year=' + this.selected.year).success(function (data) {
                            this.authorizationBudget = data;
                            if (this.authorizationBudget.validation) {
                                this.valida = true;
                            }
                            if (this.authorizationBudget.authorization) {
                                this.authoriza = true;
                            }
                            if (this.authorizationBudget.modify) {
                                this.modifica = true;
                            }
                        }).error(function (data) {
                        });
                    },
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    getTotalCostCenter: function () {
                        var self = this;
                        this.totalCostCenter = 0;
                        this.budgets.totalCategoryAmount = 0;
                        this.budgets.forEach(function (budget) {
                            budget.totalCategoryAmount = 0;
                        });
                        this.budgets.forEach(function (budget) {
                            budget.realBudgetSpendings.forEach(function (subbudget) {
                                budget.totalCategoryAmount += subbudget.totalBudgetAmount;
                            });
                        });
                        this.budgets.forEach(function (budget) {
                            self.totalCostCenter += budget.totalCategoryAmount;
                        });
                    },
                    onChangeFilter: function () {
                        this.budgets = [];
                    },
                    showSendAuthorizationOrDenies: function () {
                        this.authorizeOrDenieCostCenters = [];
                        this.getOnlyCostCentersNotAutorizedorReject();
                        $('#sendAuthorizationOrDenies').modal('show');
                    },
                    closeSendAuthorizationOrDenies: function () {
                        $('#sendAuthorizationOrDenies').modal('hide');
                    },
                    showAuthorizationBudget: function () {
                        if (this.authorizeOrDenieCostCenters.length > 0) {
                            var costCenterReasonEmpty = [];
                            this.authorizeOrDenieCostCenters.forEach(function (costCenterSended) {
                                if (costCenterSended.reason.length == 0) {
                                    costCenterReasonEmpty.push(costCenterSended);
                                }
                            });

                            if (costCenterReasonEmpty.length == 0) {
                                this.closeSendAuthorizationOrDenies();
                                $('#authorizationBudget').modal('show');
                            } else {
                                showAlert("Es necesario que se llenen todos los comentarios de los centros costos", {type: 3});
                            }
                        } else {
                            showAlert("Se debe seleccionar por lo menos un centro de costos", {type: 3});
                        }
                    },
                    closeAuthorizationBudget: function () {
                        $('#authorizationBudget').modal('hide');
                        $('#sendAuthorizationOrDenies').modal('show');
                    },
                    showDeniesBudget: function () {
                        this.closeSendAuthorizationOrDenies();
                        $('#deniesBudget').modal('show');
                    },
                    closeDeniesBudget: function () {
                        $('#deniesBudget').modal('hide');
                    },
                    confirmAuthorization: function () {
                        var object = {
                            authorizeOrDenieCostCenters: []
                        };
                        var self = this;
                        this.authorizeOrDenieCostCenters.forEach(function (data) {
                            object.authorizeOrDenieCostCenters.push(data);
                        });

                        this.$http.post(ROOT_URL + '/budgets/validated', JSON.stringify(object)).success(function (data) {
                            showAlert('Las respuestas seleccionadas seran enviadas a las personas correspondientes');
                            $('#authorizationBudget').modal('hide');
                        }).error(function (data) {
                            showAlert('Error en la solicitud, intentelo nuevamente', {type: 3});
                        });
                    },
                    selectBussinessLine: function (bussineLine) {
                        var object = {};
                        var self = this;

                        object = JSON.parse(JSON.stringify(bussineLine));
                        var aux = this.selectedOptions.bussinessLinesSelected.filter(function (element) {
                            if (element.idBusinessLine == bussineLine.idBusinessLine) {
                                return element;
                            }
                        });
                        if (aux == 0) {
                            this.$http.get(ROOT_URL + "/distributor-cost-center/bussiness-line/" + bussineLine.idBusinessLine).success(function (element) {
                                element.forEach(function (distributor) {
                                    var aux1 = self.selectedOptions.distributorsSelected.filter(function (enterprise) {
                                        if (enterprise.idDistributor == distributor.idDistributor) {
                                            return enterprise
                                        }
                                    });
                                    if (aux1 == 0) {
                                        var aux2 = self.distributors.filter(function (enterprise) {
                                            if (enterprise.idDistributor == distributor.idDistributor) {
                                                return enterprise;
                                            }
                                        });
                                        if (aux2 == 0) {
                                            self.distributors.push(distributor);
                                        }
                                    }
                                });
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            });
                            this.selectedOptions.bussinessLinesSelected.push(object);
                            this.bussinessLines.$remove(bussineLine);
                        }
                    },
                    selectDistributor: function (distributor) {
                        var object = {};
                        var self = this;

                        object = JSON.parse(JSON.stringify(distributor));
                        var aux = this.selectedOptions.distributorsSelected.filter(function (element) {
                            if (element.idDistributor == distributor.idDistributor) {
                                return element;
                            }
                        });
                        if (aux == 0) {
                            this.$http.post(ROOT_URL + "/distributor-cost-center/b-distributor/" + distributor.idDistributor, JSON.stringify(this.selectedOptions)).success(function (element) {
                                element.forEach(function (cc) {
                                    var aux1 = self.selectedOptions.costCenterSelected.filter(function (costCenter) {
                                        if (costCenter.idCostCenter == cc.idCostCenter) {
                                            return costCenter
                                        }
                                    });
                                    if (aux1 == 0) {
                                        var aux2 = self.costCenters.filter(function (costCenter) {
                                            if (costCenter.idCostCenter == cc.idCostCenter) {
                                                return costCenter;
                                            }
                                        });
                                        if (aux2 == 0) {
                                            self.costCenters.push(cc);
                                        }
                                    }
                                });
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            });
                            this.selectedOptions.distributorsSelected.push(object);
                            this.distributors.$remove(distributor);
                        }
                    },
                    selectCostCenter: function (costCenter) {
                        var object = {};
                        var self = this;

                        object = JSON.parse(JSON.stringify(costCenter));
                        var aux = this.selectedOptions.costCenterSelected.filter(function (element) {
                            if (element.idCostCenter == costCenter.idCostCenter) {
                                return element;
                            }
                        });
                        if (aux == 0) {
                            this.selectedOptions.costCenterSelected.push(object);
                            this.costCenters.$remove(costCenter);
                        }
                    },
                    cleanFields: function () {
                        location.reload();
                    },
                    getAllBudgetsStatus: function () {
                        this.getAllCostCentersByStatus();
                        $("#budgetsStatus").modal("show");
                    },
                    openModalModify: function (costCenter) {
                        this.costCenterToModify = JSON.parse(JSON.stringify(costCenter));
                        $("#budgetsStatus").modal("hide");
                        $("#authorizationModify").modal("show");
                    },
                    cancelModalModify: function () {
                        $("#authorizationModify").modal("hide");
                    },
                    cancelBudgetStatus: function () {
                        $("#budgetsStatus").modal("hide");
                    },
                    addCostCenterByAuthorizeOrReject: function () {
                        var self = this;
                        if (this.costCenterByAuthorizeOrReject.status.length > 0 && this.costCenterByAuthorizeOrReject.costCenter.idCostCenter > 0) {
                            var costCenterAR = {
                                costCenter: this.costCenterByAuthorizeOrReject.costCenter,
                                reason: this.costCenterByAuthorizeOrReject.reason,
                                status: this.costCenterByAuthorizeOrReject.status,
                                year: this.selectedOptions.year
                            };
                            var aux = this.authorizeOrDenieCostCenters.filter(function (costCenterToSended) {
                                if (costCenterAR.costCenter.idCostCenter == costCenterToSended.costCenter.idCostCenter) {
                                    return costCenterToSended;
                                }
                            });
                            if (aux == 0) {
                                this.authorizeOrDenieCostCenters.push(costCenterAR);
                                this.costCenterByAuthorizeOrReject.costCenter = {};
                                this.costCenterByAuthorizeOrReject.reason = '';
                                this.costCenterByAuthorizeOrReject.status = '';
                            } else {
                                this.costCenterByAuthorizeOrReject.costCenter = {};
                                this.costCenterByAuthorizeOrReject.reason = '';
                                this.costCenterByAuthorizeOrReject.status = '';
                                showAlert("No se puede agregar un centro de costos que ya esta seleccionado", {type: 3});
                            }
                        } else {
                            showAlert("Es necesario seleccionar un Centro de Costos y un Estatus", {type: 3});
                        }
                    },
                    getOnlyCostCentersNotAutorizedorReject: function () {
                        this.$http.post(ROOT_URL + "/authorizathion-costcenter/get-costcenter-rn", JSON.stringify(this.selectedOptions)).success(function (data) {
                            this.costCenterNotAutorizhed = data;
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type: 3});
                        });
                    },
                    confirmModification: function (value) {
                        this.$http.post(ROOT_URL + "/authorizathion-costcenter/modify-request/" + value, JSON.stringify(this.costCenterToModify)).success(function (data) {
                            this.cancelModalModify();
                            showAlert('La respuesta seleccionada sera enviada a las persona correspondiente');

                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type: 3});
                        });
                    },
                    openExportReportModal: function () {
                        this.selectedOptions.reportName = '';
                        $("#exportReport").modal("show");
                    },
                    cancelModalExport: function () {
                        $("#exportReport").modal("hide");
                    },
                    exportBudgetReport: function () {
                        if (this.selectedOptions.reportName.length > 0) {
                            this.$http.post(ROOT_URL + "/distributor-cost-center/report", JSON.stringify(this.selectedOptions)).success(function (data) {
                                this.cancelModalExport();
                                this.name = data;
                               window.location = ROOT_URL + "/distributor-cost-center/download?name="+data;
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            });
                            this.$http.get(ROOT_URL + "/distributor-cost-center/delete-report?name="+this.name).success(function () {
                            });
                        } else {
                            showAlert("Es necesario dar un nombre al reporte", {type: 3});
                        }
                    }
                },
                filters: {
                    currencyDisplay: {
                        read: function (val) {
                            return val.formatMoney(2, '');
                        },
                        write: function (val, oldVal) {
                            var number = +val.replace(/[^\d.]/g, '');
                            return isNaN(number) ? 0 : parseFloat(number.toFixed(2));
                        }
                    }
                }
            });
        </script>
    </jsp:attribute>
    <jsp:attribute name="styles">
        <style>
            @media (min-width: 992px) {
                .container-scroll {
                    overflow-x: auto;
                }

                .container-scroll > .row {
                    width: 2025px;
                }
            }
        </style>
    </jsp:attribute>
    <jsp:body>
        <div id="content">
            <div class="row">
                <div class="col-md-9" style="text-align: left">
                    <h2>VALIDACIÓN DE PRESUPUESTO</h2>
                </div>
                <div class="col-md-3 pull-right">
                    <button style="margin-top: 25px; margin-left: 150px" class="btn btn-warning"
                            @click="getAllBudgetsStatus()">
                        Estatus Presupuesto
                    </button>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-2">
                    <label>Linea de negocio</label>
                    <select class="form-control" v-model="bussinessLine"
                            @change="selectBussinessLine(bussinessLine)" required>
                        <option v-for="bussinessLine in bussinessLines" :value="bussinessLine">
                            {{bussinessLine.acronym}}
                        </option>
                    </select>
                    <br>
                    <div v-for="(index, linea) in selectedOptions.bussinessLinesSelected">
                        <div id="{{index}}" class="col-md-5">
                            <label style="background: #003333; border-radius: 4px; color: white; font-family: 'Helvetica Neue'">
                                &nbsp;{{linea.acronym}}&nbsp;</label>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <label>Distribuidor</label>
                    <select v-model="distributor" class="form-control" @change="selectDistributor(distributor)"
                            :disabled="distributors.length == 0" required>
                        <option v-for="distributor in distributors" :value="distributor">{{distributor.acronyms}}
                        </option>
                    </select>
                    <br>
                    <div v-for="distributor in selectedOptions.distributorsSelected">
                        <div class="col-md-5">
                            <label style="background: #003333; border-radius: 5px; color: white; font-family: 'Helvetica Neue'">
                                &nbsp;{{distributor.acronyms}}&nbsp;</label>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <label>Centro de costos</label>
                    <select v-model="selected.costCenter" class="form-control"
                            @change="selectCostCenter(selected.costCenter)" :disabled="costCenters.length == 0"
                            required>
                        <option v-for="costCenter in costCenters" :value="costCenter">{{costCenter.name}}</option>
                    </select>
                    <br>
                    <div v-for="cc in selectedOptions.costCenterSelected">
                        <div class="col-md-6">
                            <label style="background: #003333; border-radius: 5px; color: white; font-family: 'Helvetica Neue'">
                                &nbsp;{{cc.name}}&nbsp;</label>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <label>Año</label>
                    <select v-model="selectedOptions.year" class="form-control" required>
                        <option></option>
                        <option v-for="year in years" :value="year">
                            {{year}}
                        </option>
                    </select>
                </div>
                <div class="col-md-1">
                    <button style="margin-top: 25px" @click="getBussinessLineByValidation()" class="btn btn-info">
                        Buscar
                    </button>
                </div>
                <div class="col-md-1">
                    <button style="margin-top: 25px" @click="cleanFields()" class="btn btn-danger">Limpiar campos
                    </button>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-7" v-if="budgets.length > 0">
                </div>
                <div class="col-md-2" v-if="budgets.length > 0">
                    <button style="margin-top: 25px" class="btn btn-default" @click="showSendAuthorizationOrDenies">
                        Autorizar o Rechazar
                    </button>
                </div>
                <div class="col-md-2" v-if="budgets.length > 0">
                    <button style="margin-top: 25px" class="btn btn-success" @click="openExportReportModal()">
                        Exportar
                    </button>
                </div>
            </div>
            <br>
            <!--<div class="row">
                <div class="col-md-8 text-right" v-if="budgets.length > 0">
                    <h3>Total centro de costo: <b class="text-primary">{{totalCostCenter | currency}}</b></h3>
                </div>
            </div>-->
            <!--<div v-if="searching" class="col-md-12" style="height: 6rem; padding: 2rem 0;">
                <div class="loader">Cargando...</div>
            </div>-->

            <div class="container-fluid container-scroll" v-if="budgets.length > 0">
                <div class="row" style="background-color: #878787">
                    <div class="col-md-12" style="color: black">
                        <div class="row" style="height: 50px">
                            <div class="col-md-1 text-center">
                                <h5></h5>
                            </div>
                            <div class="col-md-1 text-center">
                                <h5><b>Total de: {{selectedOptions.year-1}}</b></h5>
                            </div>
                            <div class="col-md-8 text-center">
                                <div class="col-md-1" v-for="month in months">
                                    <h5><b>{{month.month | uppercase}}</b></h5>
                                </div>
                            </div>
                            <div class="col-md-1">
                                <h5><b>Total de: {{selectedOptions.year}}</b></h5>
                            </div>
                            <div class="col-md-1"></div>
                        </div>
                    </div>

                    <div class="col-md-12" style="height:70%;overflow-y: auto;">
                        <div class="row" v-for="(index, bussinessLine) in budgets" style="background-color: #dfdfdf">
                            <div class="col-md-12">
                                <div class="col-md-1">
                                    <h5><b>{{bussinessLine.name}}</b></h5>
                                </div>
                                <div class="col-md-1 text-center">
                                    <b class="text-primary">{{bussinessLine.totalBudgetAmountLastYear | currency}}</b>
                                </div>
                                <div class="col-md-8 text-center">
                                    <div class="col-md-1">
                                        <b>{{bussinessLine.januaryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{bussinessLine.februaryAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{bussinessLine.marchAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{bussinessLine.aprilAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{bussinessLine.mayAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{bussinessLine.juneAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{bussinessLine.julyAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{bussinessLine.augustAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{bussinessLine.septemberAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{bussinessLine.octoberAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{bussinessLine.novemberAmount | currency}}</b>
                                    </div>
                                    <div class="col-md-1">
                                        <b>{{bussinessLine.decemberAmount | currency}}</b>
                                    </div>
                                </div>
                                <div class="col-md-1 text-center">
                                    <b class="text-primary">{{bussinessLine.totalAmount | currency}}</b>
                                </div>
                                <div class="col-md-1">
                                    <button class="btn btn-default" @click="bussinessLine.show = !bussinessLine.show">
                                        <span style="margin-top: 0.5px" class="glyphicon glyphicon-plus"
                                              v-if="bussinessLine.show == false"></span>
                                        <span style="margin-top: 0.5px" class="glyphicon glyphicon-minus"
                                              v-if="bussinessLine.show == true"></span>
                                    </button>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div id="{{index}}+{{bussinessLine.name}}" class="row"
                                     v-for="distributor in bussinessLine.distributorList"
                                     style="background: #333333; color: white" v-if="bussinessLine.show">
                                    <div class="col-md-12">
                                        <div class="col-md-1">
                                            <h5><b>&nbsp;&nbsp;&nbsp;{{distributor.name}}</b></h5>
                                        </div>
                                        <div class="col-md-1 text-center">
                                            <b class="text-primary">{{distributor.totalBudgetAmountLastYear |
                                                currency}}</b>
                                        </div>
                                        <div class="col-md-8 text-center">
                                            <div class="col-md-1">
                                                <b>{{distributor.januaryAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{distributor.februaryAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{distributor.marchAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{distributor.aprilAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{distributor.mayAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{distributor.juneAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{distributor.julyAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{distributor.augustAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{distributor.septemberAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{distributor.octoberAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{distributor.novemberAmount | currency}}</b>
                                            </div>
                                            <div class="col-md-1">
                                                <b>{{distributor.decemberAmount | currency}}</b>
                                            </div>
                                        </div>
                                        <div class="col-md-1 text-center">
                                            <b class="text-primary">{{distributor.totalAmount | currency}}</b>
                                        </div>
                                        <div class="col-md-1">
                                            <button class="btn btn-default"
                                                    @click="distributor.show = !distributor.show">
                                                <span style="margin-top: 0.5px" class="glyphicon glyphicon-plus"
                                                      v-if="distributor.show == false"></span>
                                                <span style="margin-top: 0.5px" class="glyphicon glyphicon-minus"
                                                      v-if="distributor.show == true"></span>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="row" v-for="costCenter in distributor.costCenterList"
                                             style="background: white; color: black" v-if="distributor.show">
                                            <div class="col-md-12">
                                                <div class="col-md-1">
                                                    <h5><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{costCenter.name}}</b>
                                                    </h5>
                                                </div>
                                                <div class="col-md-1 text-center">
                                                    <b class="text-primary">{{costCenter.totalBudgetAmountLastYear |
                                                        currency}}</b>
                                                </div>
                                                <div class="col-md-8 text-center">
                                                    <div class="col-md-1">
                                                        <b>{{costCenter.januaryAmount | currency}}</b>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <b>{{costCenter.februaryAmount | currency}}</b>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <b>{{costCenter.marchAmount | currency}}</b>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <b>{{costCenter.aprilAmount | currency}}</b>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <b>{{costCenter.mayAmount | currency}}</b>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <b>{{costCenter.juneAmount | currency}}</b>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <b>{{costCenter.julyAmount | currency}}</b>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <b>{{costCenter.augustAmount | currency}}</b>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <b>{{costCenter.septemberAmount | currency}}</b>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <b>{{costCenter.octoberAmount | currency}}</b>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <b>{{costCenter.novemberAmount | currency}}</b>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <b>{{costCenter.decemberAmount | currency}}</b>
                                                    </div>
                                                </div>
                                                <div class="col-md-1 text-center">
                                                    <b class="text-primary">{{costCenter.totalAmount | currency}}</b>
                                                </div>
                                                <div class="col-md-1">
                                                    <button class="btn btn-default"
                                                            @click="costCenter.show = !costCenter.show">
                                                        <span style="margin-top: 0.5px" class="glyphicon glyphicon-plus"
                                                              v-if="costCenter.show == false"></span>
                                                        <span style="margin-top: 0.5px"
                                                              class="glyphicon glyphicon-minus"
                                                              v-if="costCenter.show == true"></span>
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="row" v-for="budgetCategory in costCenter.budgetCategories"
                                                     style="background: #333333; color: white" v-if="costCenter.show">
                                                    <div class="col-md-12">
                                                        <div class="col-md-1">
                                                            <h5><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{budgetCategory.name}}</b>
                                                            </h5>
                                                        </div>
                                                        <div class="col-md-1 text-center">
                                                            <b class="text-primary">{{budgetCategory.totalBudgetAmountLastYear
                                                                | currency}}</b>
                                                        </div>
                                                        <div class="col-md-8 text-center">
                                                            <div class="col-md-1">
                                                                <b>{{budgetCategory.januaryCategoryAmount |
                                                                    currency}}</b>
                                                            </div>
                                                            <div class="col-md-1">
                                                                <b>{{budgetCategory.februaryCategoryAmount |
                                                                    currency}}</b>
                                                            </div>
                                                            <div class="col-md-1">
                                                                <b>{{budgetCategory.marchCategoryAmount | currency}}</b>
                                                            </div>
                                                            <div class="col-md-1">
                                                                <b>{{budgetCategory.aprilCategoryAmount | currency}}</b>
                                                            </div>
                                                            <div class="col-md-1">
                                                                <b>{{budgetCategory.mayCategoryAmount | currency}}</b>
                                                            </div>
                                                            <div class="col-md-1">
                                                                <b>{{budgetCategory.juneCategoryAmount | currency}}</b>
                                                            </div>
                                                            <div class="col-md-1">
                                                                <b>{{budgetCategory.julyCategoryAmount | currency}}</b>
                                                            </div>
                                                            <div class="col-md-1">
                                                                <b>{{budgetCategory.augustCategoryAmount |
                                                                    currency}}</b>
                                                            </div>
                                                            <div class="col-md-1">
                                                                <b>{{budgetCategory.septemberCategoryAmount |
                                                                    currency}}</b>
                                                            </div>
                                                            <div class="col-md-1">
                                                                <b>{{budgetCategory.octoberCategoryAmount |
                                                                    currency}}</b>
                                                            </div>
                                                            <div class="col-md-1">
                                                                <b>{{budgetCategory.novemberCategoryAmount |
                                                                    currency}}</b>
                                                            </div>
                                                            <div class="col-md-1">
                                                                <b>{{budgetCategory.decemberCategoryAmount |
                                                                    currency}}</b>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-1 text-center">
                                                            <b class="text-primary">{{budgetCategory.totalCategoryAmount
                                                                | currency}}</b>
                                                        </div>
                                                        <div class="col-md-1">
                                                            <button class="btn btn-default"
                                                                    @click="budgetCategory.show = !budgetCategory.show">
                                                                <span style="margin-top: 0.5px"
                                                                      class="glyphicon glyphicon-plus"
                                                                      v-if="budgetCategory.show == false"></span>
                                                                <span style="margin-top: 0.5px"
                                                                      class="glyphicon glyphicon-minus"
                                                                      v-if="budgetCategory.show == true"></span>
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-12">
                                                        <div class="row"
                                                             v-for="conceptFirstLevel in budgetCategory.realBudgetSpendings"
                                                             style="background: #666666; color: white"
                                                             v-if="budgetCategory.show">
                                                            <div class="col-md-12">
                                                                <div class="col-md-1">
                                                                    <h5><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{conceptFirstLevel.budget.conceptBudget.nameConcept}}</b>
                                                                    </h5>
                                                                </div>
                                                                <div class="col-md-1 text-center">
                                                                    <b class="text-primary">{{conceptFirstLevel.totalLastYearAmount
                                                                        | currency}}</b>
                                                                </div>
                                                                <div class="col-md-8 text-center">
                                                                    <div class="col-md-1">
                                                                        <b>{{conceptFirstLevel.januaryBudgetAmount |
                                                                            currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{conceptFirstLevel.februaryBudgetAmount |
                                                                            currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{conceptFirstLevel.marchBudgetAmount |
                                                                            currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{conceptFirstLevel.aprilBudgetAmount |
                                                                            currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{conceptFirstLevel.mayBudgetAmount |
                                                                            currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{conceptFirstLevel.juneBudgetAmount |
                                                                            currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{conceptFirstLevel.julyBudgetAmount |
                                                                            currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{conceptFirstLevel.augustBudgetAmount |
                                                                            currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{conceptFirstLevel.septemberBudgetAmount |
                                                                            currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{conceptFirstLevel.octoberBudgetAmount |
                                                                            currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{conceptFirstLevel.novemberBudgetAmount |
                                                                            currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{conceptFirstLevel.decemberBudgetAmount |
                                                                            currency}}</b>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-1 text-center">
                                                                    <b class="text-primary">{{conceptFirstLevel.totalBudgetAmount
                                                                        | currency}}</b>
                                                                </div>
                                                                <div class="col-md-1">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row"
                                                             v-for="budgetSubcategory in budgetCategory.budgetSubcategories"
                                                             style="background: white; color: black"
                                                             v-if="budgetCategory.show">
                                                            <div class="col-md-12">
                                                                <div class="col-md-1">
                                                                    <h5><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{budgetSubcategory.name}}</b>
                                                                    </h5>
                                                                </div>
                                                                <div class="col-md-1 text-center">
                                                                    <b class="text-primary">{{budgetSubcategory.totalBudgetAmountLastYear
                                                                        | currency}}</b>
                                                                </div>
                                                                <div class="col-md-8 text-center">
                                                                    <div class="col-md-1">
                                                                        <b>{{budgetSubcategory.januarySubcategoryAmount
                                                                            | currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{budgetSubcategory.februarySubcategoryAmount
                                                                            | currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{budgetSubcategory.marchSubcategoryAmount
                                                                            | currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{budgetSubcategory.aprilSubcategoryAmount
                                                                            | currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{budgetSubcategory.maySubcategoryAmount
                                                                            | currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{budgetSubcategory.juneSubcategoryAmount
                                                                            | currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{budgetSubcategory.julySubcategoryAmount
                                                                            | currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{budgetSubcategory.augustSubcategoryAmount
                                                                            | currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{budgetSubcategory.septemberSubcategoryAmount
                                                                            | currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{budgetSubcategory.octoberSubcategoryAmount
                                                                            | currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{budgetSubcategory.novemberSubcategoryAmount
                                                                            | currency}}</b>
                                                                    </div>
                                                                    <div class="col-md-1">
                                                                        <b>{{budgetSubcategory.decemberSubcategoryAmount
                                                                            | currency}}</b>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-1 text-center">
                                                                    <b class="text-primary">{{budgetSubcategory.totalSubcategoryAmount
                                                                        | currency}}</b>
                                                                </div>
                                                                <div class="col-md-1">
                                                                    <button class="btn btn-default"
                                                                            @click="budgetSubcategory.show = !budgetSubcategory.show">
                                                                        <span style="margin-top: 0.5px"
                                                                              class="glyphicon glyphicon-plus"
                                                                              v-if="budgetSubcategory.show == false"></span>
                                                                        <span style="margin-top: 0.5px"
                                                                              class="glyphicon glyphicon-minus"
                                                                              v-if="budgetSubcategory.show == true"></span>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="row"
                                                                     v-for="conceptSecondLevel in budgetSubcategory.realBudgetSpendings"
                                                                     style="background: #333333; color: white"
                                                                     v-if="budgetSubcategory.show">
                                                                    <div class="col-md-12">
                                                                        <div class="col-md-1">
                                                                            <h5><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{conceptSecondLevel.budget.conceptBudget.nameConcept}}</b>
                                                                            </h5>
                                                                        </div>
                                                                        <div class="col-md-1 text-center">
                                                                            <b class="text-primary">{{conceptSecondLevel.totalLastYearAmount
                                                                                | currency}}</b>
                                                                        </div>
                                                                        <div class="col-md-8 text-center">
                                                                            <div class="col-md-1">
                                                                                <b>{{conceptSecondLevel.januaryBudgetAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{conceptSecondLevel.februaryBudgetAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{conceptSecondLevel.marchBudgetAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{conceptSecondLevel.aprilBudgetAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{conceptSecondLevel.mayBudgetAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{conceptSecondLevel.juneBudgetAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{conceptSecondLevel.julyBudgetAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{conceptSecondLevel.augustBudgetAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{conceptSecondLevel.septemberBudgetAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{conceptSecondLevel.octoberBudgetAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{conceptSecondLevel.novemberBudgetAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{conceptSecondLevel.decemberBudgetAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-md-1 text-center">
                                                                            <b class="text-primary">{{conceptSecondLevel.totalBudgetAmount
                                                                                | currency}}</b>
                                                                        </div>
                                                                        <div class="col-md-1"></div>
                                                                    </div>
                                                                </div>
                                                                <div class="row"
                                                                     v-for="budgetSubSubCategory in budgetSubcategory.budgetSubSubCategories"
                                                                     style="background: #666666; color: white"
                                                                     v-if="budgetSubcategory.show">
                                                                    <div class="col-md-12">
                                                                        <div class="col-md-1">
                                                                            <h5><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{budgetSubSubCategory.name}}</b>
                                                                            </h5>
                                                                        </div>
                                                                        <div class="col-md-1 text-center">
                                                                            <b class="text-primary">{{budgetSubSubCategory.totalBudgetAmountLastYear
                                                                                | currency}}</b>
                                                                        </div>
                                                                        <div class="col-md-8 text-center">
                                                                            <div class="col-md-1">
                                                                                <b>{{budgetSubSubCategory.januarySubSubcategoryAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{budgetSubSubCategory.februarySubSubcategoryAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{budgetSubSubCategory.marchSubSubcategoryAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{budgetSubSubCategory.aprilSubSubcategoryAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{budgetSubSubCategory.maySubSubcategoryAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{budgetSubSubCategory.juneSubSubcategoryAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{budgetSubSubCategory.julySubSubcategoryAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{budgetSubSubCategory.augustSubSubcategoryAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{budgetSubSubCategory.septemberSubSubcategoryAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{budgetSubSubCategory.octoberSubSubcategoryAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{budgetSubSubCategory.novemberSubSubcategoryAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                            <div class="col-md-1">
                                                                                <b>{{budgetSubSubCategory.decemberSubSubcategoryAmount
                                                                                    | currency}}</b>
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-md-1 text-center">
                                                                            <b class="text-primary">{{budgetSubSubCategory.totalSubSubcategoryAmount
                                                                                | currency}}</b>
                                                                        </div>
                                                                        <div class="col-md-1">
                                                                            <button class="btn btn-default"
                                                                                    @click="budgetSubSubCategory.show = !budgetSubSubCategory.show">
                                                                                <span style="margin-top: 0.5px"
                                                                                      class="glyphicon glyphicon-plus"
                                                                                      v-if="budgetSubSubCategory.show == false"></span>
                                                                                <span style="margin-top: 0.5px"
                                                                                      class="glyphicon glyphicon-minus"
                                                                                      v-if="budgetSubSubCategory.show == true"></span>
                                                                            </button>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-12">
                                                                        <div class="row"
                                                                             v-for="conceptThirdLevel in budgetSubSubCategory.realBudgetSpendingList"
                                                                             style="background: #999999; color: black"
                                                                             v-if="budgetSubSubCategory.show">
                                                                            <div class="col-md-12">
                                                                                <div class="col-md-1">
                                                                                    <h5><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{conceptThirdLevel.budget.conceptBudget.nameConcept}}</b>
                                                                                    </h5>
                                                                                </div>
                                                                                <div class="col-md-1 text-center">
                                                                                    <b class="text-primary">{{conceptThirdLevel.totalLastYearAmount
                                                                                        | currency}}</b>
                                                                                </div>
                                                                                <div class="col-md-8 text-center">
                                                                                    <div class="col-md-1">
                                                                                        <b>{{conceptThirdLevel.januaryBudgetAmount
                                                                                            | currency}}</b>
                                                                                    </div>
                                                                                    <div class="col-md-1">
                                                                                        <b>{{conceptThirdLevel.februaryBudgetAmount
                                                                                            | currency}}</b>
                                                                                    </div>
                                                                                    <div class="col-md-1">
                                                                                        <b>{{conceptThirdLevel.marchBudgetAmount
                                                                                            | currency}}</b>
                                                                                    </div>
                                                                                    <div class="col-md-1">
                                                                                        <b>{{conceptThirdLevel.aprilBudgetAmount
                                                                                            | currency}}</b>
                                                                                    </div>
                                                                                    <div class="col-md-1">
                                                                                        <b>{{conceptThirdLevel.mayBudgetAmount
                                                                                            | currency}}</b>
                                                                                    </div>
                                                                                    <div class="col-md-1">
                                                                                        <b>{{conceptThirdLevel.juneBudgetAmount
                                                                                            | currency}}</b>
                                                                                    </div>
                                                                                    <div class="col-md-1">
                                                                                        <b>{{conceptThirdLevel.julyBudgetAmount
                                                                                            | currency}}</b>
                                                                                    </div>
                                                                                    <div class="col-md-1">
                                                                                        <b>{{conceptThirdLevel.augustBudgetAmount
                                                                                            | currency}}</b>
                                                                                    </div>
                                                                                    <div class="col-md-1">
                                                                                        <b>{{conceptThirdLevel.septemberBudgetAmount
                                                                                            | currency}}</b>
                                                                                    </div>
                                                                                    <div class="col-md-1">
                                                                                        <b>{{conceptThirdLevel.octoberBudgetAmount
                                                                                            | currency}}</b>
                                                                                    </div>
                                                                                    <div class="col-md-1">
                                                                                        <b>{{conceptThirdLevel.novemberBudgetAmount
                                                                                            | currency}}</b>
                                                                                    </div>
                                                                                    <div class="col-md-1">
                                                                                        <b>{{conceptThirdLevel.decemberBudgetAmount
                                                                                            | currency}}</b>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="col-md-1 text-center">
                                                                                    <b class="text-primary">{{conceptThirdLevel.totalBudgetAmount
                                                                                        | currency}}</b>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <!--dialogs-->
            <div class="modal fade" id="sendAuthorizationOrDenies" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" aria-label="Close"
                                    @click="closeSendAuthorizationOrDenies">
                                <span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="sendValidationLabel"><b>Autorizar o Rechazar Presupuesto</b>
                            </h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-4">
                                        <label>Centro de Costos</label>
                                        <select class="form-control" v-model="costCenterByAuthorizeOrReject.costCenter">
                                            <option v-for="costCenter in costCenterNotAutorizhed" :value="costCenter">
                                                {{costCenter.name}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-md-1">
                                        <button class="btn btn-default" style="margin-top: 25px"
                                                @click="addCostCenterByAuthorizeOrReject()"><span
                                                class="glyphicon glyphicon-plus"></span></button>
                                    </div>
                                    <div class="col-md-1"></div>
                                    <div class="col-md-4">
                                        <input type="radio" id="one" value="1"
                                               v-model="costCenterByAuthorizeOrReject.status"><label for="one">&nbsp;
                                        Autorizar
                                        <span class="glyphicon glyphicon-triangle-bottom"
                                              style="color: #457a1a; font-size: 100%"></span></label>
                                        <br>
                                        <input type="radio" id="two" value="2"
                                               v-model="costCenterByAuthorizeOrReject.status"><label for="two">&nbsp;
                                        Rechazar
                                        <span class="glyphicon glyphicon-triangle-bottom"
                                              style="color: #EE0909; font-size: 100%"></span>
                                    </label>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <br>
                            <br>
                            <div class="row">
                                <div class="col-md-12" v-if="authorizeOrDenieCostCenters.length > 0">
                                    <div class="col-md-2"></div>
                                    <div class="col-md-1"></div>
                                    <div class="col-md-2"></div>
                                    <div class="col-md-1"></div>
                                    <div class="col-md-6"><label>Comentarios</label></div>
                                </div>
                                <div class="col-md-12" v-for="costCenterReasons in authorizeOrDenieCostCenters">
                                    <div class="col-md-2">
                                        <label>{{costCenterReasons.costCenter.name}}</label>
                                    </div>
                                    <div class="col-md-1"></div>
                                    <div class="col-md-2">
                                        <span class="glyphicon glyphicon-triangle-bottom"
                                              style="color: #457a1a; font-size: 200%"
                                              v-if="costCenterReasons.status == 1"></span>
                                        <span class="glyphicon glyphicon-triangle-bottom"
                                              style="color: #EE0909; font-size: 200%" v-else></span>
                                    </div>
                                    <div class="col-md-1"></div>
                                    <div class="col-md-6"><textarea class="form-control" rows="3"
                                                                    v-model="costCenterReasons.reason"></textarea></div>
                                </div>
                                <br>
                                <br>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-success" @click="showAuthorizationBudget">Enviar</button>
                            <button class="btn btn-default" @click="closeSendAuthorizationOrDenies">Cancelar
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="authorizationBudget" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" aria-label="Close"
                                    @click="closeAuthorizationBudget">
                                <span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="authorizationBudgetLabel"><b>Autorizar o Rechazar
                                Presupuesto</b></h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-12">
                                    Estas apunto de <label>Autorizar o Rechazar</label> los cetros de costos
                                    seleccionados.
                                    <label>¿Estas seguro(a)?</label>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-success" @click="confirmAuthorization">Aceptar</button>
                            <button type="button" class="btn btn-default" @click="closeAuthorizationBudget">Cancelar
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="authorizationModify" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" aria-label="Close"
                                    @click="cancelModalModify">
                                <span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="authorizationModifyLabel"><b>Realizar modificación</b></h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <p>Deseas autorizar o rechazar el presupuesto del centro de costos
                                        {{costCenterToModify.costCenter.name}},
                                        tu respuesta será notificada a {{costCenterToModify.users.mail}}</p>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="confirmModification(1)">Autorizar
                            </button>
                            <button type="button" class="btn btn-danger" @click="confirmModification(2)">Rechazar
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade bd-example-modal-lg" id="budgetsStatus" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" aria-label="Close"
                                    @click="cancelBudgetStatus()">
                                <span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="budgetsStatusLabel"><b>Estatus Presupuesto</b></h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <table class="table table-striped">
                                        <thead>
                                        </thead>
                                        <tbody>
                                        <tr v-for="costCenter in costCenterStatus"
                                            v-if="costCenter.idCCostCenterStatus != 1">
                                            <td class="col-xs-2"><label style="margin-top: 10px">{{costCenter.costCenter.name}}</label>
                                            </td>
                                            <td class="col-xs-1"><label style="margin-top: 10px">{{costCenter.year}}</label></td>
                                            <td class="col-xs-1"></td>
                                            <td class="col-xs-1" style="margin-top: 12px">
                                                <span class="glyphicon glyphicon-triangle-bottom"
                                                      style="color: #7A7A7A; font-size: 200%"
                                                      v-if="costCenter.idCCostCenterStatus == 1"></span>
                                                <span class="glyphicon glyphicon-triangle-bottom"
                                                      style="color: #DF9A1B; font-size: 200%"
                                                      v-if="costCenter.idCCostCenterStatus == 2"></span>
                                                <span class="glyphicon glyphicon-triangle-bottom"
                                                      style="color: #EE0909; font-size: 200%"
                                                      v-if="costCenter.idCCostCenterStatus == 3"></span>
                                                <span class="glyphicon glyphicon-triangle-bottom"
                                                      style="color: #457a1a; font-size: 200%"
                                                      v-if="costCenter.idCCostCenterStatus == 4"></span>
                                                <span class="glyphicon glyphicon-triangle-bottom"
                                                      style="color: #457a1a;  font-size: 200%"
                                                      v-if="costCenter.idCCostCenterStatus == 5"></span>
                                            </td>
                                            <td class="col-xs-1"></td>
                                            <td class="col-xs-1" style="margin-top: 12px">
                                                <div class="col-xs-1" style="margin-right: 50px; margin-left: 50px">
                                                    <span class="label label-default"
                                                          v-if="costCenter.idCCostCenterStatus == 1">Sin enviar</span>
                                                </div>
                                                <div class="col-xs-1" style="margin-right: 50px; margin-left: 50px">
                                                    <span class="label label-warning"
                                                          v-if="costCenter.idCCostCenterStatus == 2">Sin autorizar</span>
                                                </div>
                                                <div class="col-xs-1" style="margin-right: 50px; margin-left: 50px">
                                                    <span class="label label-danger"
                                                          v-if="costCenter.idCCostCenterStatus == 3">Rechazado</span>
                                                </div>
                                                <div class="col-xs-1" style=" margin-right: 50px; margin-left: 50px">
                                                    <span class="label label-success"
                                                          v-if="costCenter.idCCostCenterStatus == 4 || costCenter.idCCostCenterStatus == 5">Autorizado</span>
                                                </div>
                                            </td>

                                            <td class="col-xs-3" style="margin-top: 10px">
                                                <div v-if="costCenter.idCCostCenterStatus == 5">
                                                    <button class="btn btn-info" @click="openModalModify(costCenter)">
                                                        Autorizar/Rechazar Modificación
                                                    </button>
                                                </div>
                                                <div v-else>&nbsp;&nbsp;&nbsp;</div>
                                            </td>
                                            <td class="col-xs-2"></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" @click="cancelBudgetStatus()">Cancelar
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="exportReport" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" aria-label="Close"
                                    @click="cancelModalExport()">
                                <span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="exportReportLabel"><b>Exportar Reporte</b></h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-5">
                                        <label>Nombre del archivo:</label>
                                        <input type="text" class="form-control" v-model="selectedOptions.reportName">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="exportBudgetReport()">Exportar
                            </button>
                            <button type="button" class="btn btn-default" @click="cancelModalExport()">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>


        </div>
    </jsp:body>
</t:template>