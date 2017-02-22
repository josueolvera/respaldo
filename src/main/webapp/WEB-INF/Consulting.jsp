<%--
  Created by IntelliJ IDEA.
  User: PC_YAIR
  Date: 23/01/2017
  Time: 08:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>

<t:template pageTitle="BID Group: Consulta">

    <jsp:attribute name="scripts">

    <script type="text/javascript">
        function validateFloatKeyPress(el, evt) {
            var charCode = (evt.which) ? evt.which : event.keyCode;
            var number = el.value.split('.');
            if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
                return false;
            }
            //just one dot
            if (number.length > 1 && charCode == 46) {
                return false;
            }
            //get the carat position
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
    </script>

    <script type="text/javascript">
        var vm = new Vue({
            el: '#content',
            created: function () {

            },
            ready: function () {
                this.getUserInSession();
                this.getDistributors();
            },

            data: {
                URLSearch: '',
                dwEmployees: [],
                employeesHistories: [],
                isThereItems: false,
                searching: true,
                costo: 0,
                totales: 0,
                diferencia: 0,
                user: {},
                nameSearch: '',
                createReportUrl: '',
                startDate: '',
                endDate: '',
                reports: [],
                distributors: {},
                select: {
                    distributor: null,
                    startdate: '',
                    endDate: '',
                    numEmployeeSearch: '',
                    report: null
                },
                registerNumber: 0,
                var1: 0,
                var2: 0,
                var3: 0,
                reportFileName: '',
                aux1: false,
                aux2: false,
                aux3: false,
                aux4: false,
                dateTimePickerStart: '',
                dateTimePickerEndDate: '',
                picked: '',
                idDistributor: '',
                totalPercepcion: 0,
                totalDeduccion: 0,
                totalTotal: 0,
                var1: 0,
                var2: 0,
                var3: 0
            },


            methods: {
                getUserInSession: function () {
                    this.$http.get(ROOT_URL + "/user")
                            .success(function (data) {
                                this.user = data;
                                this.user.dwEmployee.idRole
                            })
                            .error(function (data) {
                                showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                            });
                },
                getDistributors: function () {
                    this.$http.get(ROOT_URL + "/distributors").success(function (data) {
                        this.distributors = data;
                    });
                },
                getPolicysByDate: function () {
                    var self = this;
                    this.$http.get(
                            ROOT_URL + '/policy-truckdriver/get-by-date?startDate=' + this.selected.startDate
                    ).success(function (data) {
                        var jsonObjectIndex = {};
                        data.forEach(function (policy) {
                            if (isNaN(policy.cTypeSecure)) {
                                jsonObjectIndex[policy.cTypeSecure._id] = policy.cTypeSecure;
                            } else {
                                policy.cTypeSecure = jsonObjectIndex[policy.cTypeSecure];
                            }
                        });
                        this.Polycys = data;
                        this.sumTotals();
                        this.var1 = this.totales.toFixed(2);
                        this.var2 = this.precioVenta.toFixed(2);
                        this.var3 = this.costo.toFixed(2);
                        if (this.Polycys.length > 0) {
                            this.registerNumber = this.Polycys.length;
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
                startDateChanged: function () {
                    this.isThereItems = false;
                },
                endDateChanged: function () {
                    this.isThereItems = false;
                },

                searchPolize: function (policy, startDate) {
                    this.getPolicysByDate();

                },
                sumTotals: function () {
                    var self = this;
                    this.Polycys.forEach(function (policy) {
                        self.precioVenta += policy.priceSale;
                        self.costo += policy.cost;
                        self.totales += policy.total;
                    });
                },

                onExportButton: function () {
                    $("#exportModal").modal("show");
                },

                createReport: function () {
                    if (this.reportFileName != '') {
                        if (this.picked != '') {
                            if (this.picked == "false") {
                                this.reportSqlSinDes();
                            } else {
                                this.reportSql();
                            }
                        } else {
                            showAlert("Debe escoger un tipo de reporte", {type: 3});
                        }
                    } else {
                        showAlert("Debe asignar un nombre de archivo", {type: 3});
                    }
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
                activateDateTimePickerEndApplicationDate: function (fechaInicial) {

                    var fecha = moment(fechaInicial, 'DD-MM-YYYY').format('YYYY-MM-DD');

                    this.dateTimePickerEndDate = $('#applicationDateEnd').datetimepicker({
                        locale: 'es',
                        format: 'DD-MM-YYYY',
                        useCurrent: false,
                        minDate: fecha
                    }).data();
                },
                validateFields: function () {
                    if (this.select.numEmployeeSearch.length > 0) {
                        this.select.distributor = {};
                        this.aux1 = false;
                        this.aux2 = true;

                    } else if (this.select.distributor.idDistributor > 0) {
                        this.select.numEmployeeSearch = '';
                        this.aux2 = false;
                        this.aux1 = true;
                    } else {
                        this.aux1 = false;
                        this.aux2 = false;
                        this.aux3 = false;
                        this.aux4 = false;
                    }
                },
                findPerseptionsDeductions: function () {
                    this.searching = false;
                    var self = this;
                    var fechaInicial = "0000-00-00";
                    var fechaFinal = "0000-00-00";
                    var idEmployee = this.select.numEmployeeSearch;
                    var distribuidor = '';
                    if (this.select.startdate.length != 0 && this.select.endDate.length != 0) {
                        fechaInicial = moment(this.select.startdate, 'DD-MM-YYYY').format('YYYY-MM-DD');
                        fechaFinal = moment(this.select.endDate, 'DD-MM-YYYY').format('YYYY-MM-DD');
                    }
                    if (this.select.distributor.idDistributor == null) {
                        distribuidor = 0;
                    } else {
                        distribuidor = this.select.distributor.idDistributor;
                    }
                    if (idEmployee == "") {
                        idEmployee = 0;
                    }
                    this.$http.get(ROOT_URL + "/employees-history/get-perception?idEmployee=" + idEmployee + "&idDistributor=" + distribuidor + "&startDate="
                            + fechaInicial + "&endDate=" + fechaFinal)
                            .success(function (data) {
                                this.reports = data;
                                //this.searching = true;
                                if (data.length > 0) {
                                    this.sumTotals();
                                    this.searching = true;
                                }
                            })
                            .error(function (data) {
                                showAlert("No se encontraron datos, intente otra convinacion", {type: 3});
                            });
                    this.picked = "";
                },
                reportSqlSinDes:function () {
                    $("#exportModal").modal("hide");
                    var fechaInicial = "0000-00-00";
                    var fechaFinal = "0000-00-00";
                    if (this.select.startdate.length != 0 && this.select.endDate.length != 0) {
                        fechaInicial = moment(this.select.startdate, 'DD-MM-YYYY').format('YYYY-MM-DD');
                        fechaFinal = moment(this.select.endDate, 'DD-MM-YYYY').format('YYYY-MM-DD');
                    }
                    var distribuidor = '';
                    if (this.idDistributor <= 0) {
                        distribuidor = 0;
                    } else {
                        distribuidor = this.select.distributor.idDistributor;
                    }
                    var idEmployee = this.select.numEmployeeSearch;
                    if (idEmployee == "") {
                        idEmployee = '';
                    }
                    location.href = ROOT_URL + "/employees-history/report-by-employee?idEmployee=" + idEmployee + "&idDistributor=" + distribuidor + "&startDate="
                            + fechaInicial + "&endDate=" + fechaFinal + "&fileName=" + this.reportFileName;
                    this.picked = "";
                    this.reportFileName = "";
                },
                reportSql: function () {
                    $("#exportModal").modal("hide");
                    var fechaInicial = "0000-00-00";
                    var fechaFinal = "0000-00-00";


                    if (this.select.startdate.length != 0 && this.select.endDate.length != 0) {
                        fechaInicial = moment(this.select.startdate, 'DD-MM-YYYY').format('YYYY-MM-DD');
                        fechaFinal = moment(this.select.endDate, 'DD-MM-YYYY').format('YYYY-MM-DD');
                    }

                    var distribuidor = '';

                    if (this.idDistributor <= 0) {
                        distribuidor = 0;
                    } else {
                        distribuidor = this.select.distributor.idDistributor;
                    }

                    var idEmployee = this.select.numEmployeeSearch;

                    if (idEmployee == "") {
                        idEmployee = '';
                    }

                    location.href = ROOT_URL + "/employees-history/execute-report?idEmployee=" + idEmployee + "&idDistributor=" + distribuidor + "&startDate="
                            + fechaInicial + "&endDate=" + fechaFinal + "&fileName=" + this.reportFileName;
                    this.picked = "";
                    this.reportFileName = "";
                },
                sumTotals: function () {
                    var self = this;
                    this.reports.forEach(function (report) {
                        self.totalPercepcion += report[4];
                        self.totalDeduccion += report[3];
                        self.totalTotal += report[5];
                    });

                    this.var1 = this.totalPercepcion.toFixed(2);
                    this.var2 = this.totalDeduccion.toFixed(2);
                    this.var3 = this.totalTotal.toFixed(2);
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
                background: black;
                color: white;

            }

            .table-body .table-row:nth-child(2n+1) {
                background: white;
                overflow: auto;
                border: solid 1px;
            }

            .table-row {
                padding: 1rem;
            }

            .flex-content {
                overflow-x: hidden;
            }

            .table-hover {
                background-color: #2ba6cb;
            }
        </style>
    </jsp:attribute>
    <jsp:body>
        <div id="content" class="flex-box container-fluid">
            <div class="col-md-12">
                <div class="col-md-8" style="margin-right: 80px">
                    <h2>Reporte de compensaciones</h2>
                </div>
                <div class="col-md-4 text-right" style="margin-top: 10px">
                </div>
            </div>
            <br>
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div style="background: #286090; color: white" class="panel-heading">Datos de la busqueda</div>
                    <div class="panel-body">
                        <div class="col-md-2">
                            <label>No. Empleado</label>
                            <input :disabled="aux1" v-model="select.numEmployeeSearch" type="text" class="form-control"
                                   placeholder="No. Empleado" @input="validateFields">
                        </div>
                        <div class="col-md-2">
                            <label>Distribuidor</label>
                            <select :disabled="aux2" v-model="select.distributor" class="form-control "
                                    @change="validateFields">
                                <option></option>
                                <option v-for="distributor in distributors" :value="distributor">
                                    {{distributor.distributorName}}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>
                                Fecha inicial
                            </label>
                            <div class="form-group">
                                <div class="input-group date" id="startDate">
                                    <input type="text" class="form-control" v-model="select.startdate" :disabled="aux3">
                                    <span class="input-group-addon"
                                          @click="activateDateTimePickerStart()">
                            <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <label>
                                Fecha final
                            </label>
                            <div class="form-group">
                                <div class="input-group date" id="applicationDateEnd">
                                    <input type="text" class="form-control" v-model="select.endDate" :disabled="aux4">
                                    <span class="input-group-addon"
                                          @click="activateDateTimePickerEndApplicationDate(select.startdate)">
                            <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-2">
                            <button style="margin-top: 25px" class="btn btn-info" @click="findPerseptionsDeductions()">
                                Buscar
                            </button>
                            <a style="margin-top: 25px" class="btn btn-success" @click="onExportButton"
                            >
                                Exportar
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <br>
            <div class="col-md-12">
                <br>
                <div v-if="searching == false"
                     style="height: 6rem; padding: 2rem 0;">
                    <div class="loader">Cargando...</div>
                </div>
                <div style="background: #ddd" class="panel panel-default">
                    <!-- Default panel contents -->
                    <!-- Table de contenidos -->
                    <div class="flex-box container-fluid" v-if="reports.length > 0 && searching == true">
                        <div class="row table-header active">
                            <div class="col-md-2"><b>No de empleado</b></div>
                            <div class="col-md-2"><b>Nombre</b></div>
                            <div class="col-md-2"><b>RFC</b></div>
                            <div class="col-md-2"><b>Total de percepción</b></div>
                            <div class="col-md-2"><b>Total de deducción</b></div>
                            <div class="col-md-2"><b>Total</b></div>
                        </div>
                        <br>
                        <div class="table-body flex-row flex-content">
                            <div class="row table-row " v-for="report in reports"
                                 onmouseover='this.style.background="#2ba6cb"'
                                 onmouseout='this.style.background="#DDDDDD"'>
                                <div class="col-md-2">{{report[0]}}</div>
                                <div class="col-md-2">{{report[1]}}</div>
                                <div class="col-md-2">{{report[2]}}</div>
                                <div class="col-md-2">{{report[4]}}</div>
                                <div class="col-md-2">{{report[3]}}</div>
                                <div class="col-md-2">{{report[5]}}</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="table-body flex-row flex-content" v-if="reports.length > 0" style="width:100%">
                    <div class="row table-row" style="font-weight: bold;">
                        <div class="col-md-2">Totales:</div>
                        <div class="col-md-4"></div>
                        <div class="col-md-2">{{var1}}</div>
                        <div class="col-md-2">{{var2}}</div>
                        <div class="col-md-2">{{var3}}</div>
                    </div>
                </div>
            </div>
            <!-- Modal Exportar -->
            <div class="modal fade" id="exportModal" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-sm" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Exportar Reporte</h4>
                        </div>
                        <div class="modal-body">
                            <br>
                            <div class="form-inline">
                                <div class="form-group">
                                    <label for="reportFileName">Asigne un nombre al archivo</label>
                                    <input type="text" id="reportFileName" class="form-control"
                                           placeholder="Nombre del Archivo" v-model="reportFileName">
                                </div>
                                <br>
                                <br>
                                <div clas="col-md-2">
                                    <div class="checkbox">
                                        <label>
                                            <input type="radio" id="input-1" value="true" v-model="picked">
                                            Con desglose
                                        </label>
                                        <br>
                                        <br>
                                        <label>
                                            <input type="radio" id="input-2" value="false" v-model="picked">
                                            Sin desglose
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-success" @click="createReport">Aceptar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>