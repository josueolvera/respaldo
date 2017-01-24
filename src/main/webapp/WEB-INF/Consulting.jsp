<%--
  Created by IntelliJ IDEA.
  User: PC_YAIR
  Date: 23/01/2017
  Time: 08:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

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
            if(number.length>1 && charCode == 46){
                return false;
            }
            //get the carat position
            var caratPos = getSelectionStart(el);
            var dotPos = el.value.indexOf(".");
            if( caratPos > dotPos && dotPos>-1 && (number[1].length > 1)){
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
                dwEmployees: [],
                employeesHistories: [],
                isThereItems: false,
                searching: false,
                costo:0,
                totales:0,
                diferencia:0,
                user: {},
                nameSearch: '',
                createReportUrl: '',
                startDate: '',
                endDate: '',

                distributors: {},
                select : {
                    distributor:null,
                    startdate:'',
                    endDate:'',
                    numEmployeeSearch:''
                },
                registerNumber: 0,
                var1: 0,
                var2: 0,
                var3: 0,
                reportFileName:'',
                aux1: false,
                aux2: false,
                aux3: false,
                aux4: false
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
                            ROOT_URL + '/policy-truckdriver/get-by-date?startDate='+this.selected.startDate
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

                searchPolize: function (policy,startDate) {
                    this.getPolicysByDate();

                },
                sumTotals: function () {
                    var self = this;
                    this.Polycys.forEach(function (policy) {
                        self.precioVenta += policy.priceSale;
                        self.costo+=policy.cost;
                        self.totales+=policy.total;
                    });
                },

                onExportButton: function () {
                    $("#exportModal").modal("show");
                },

                createReport: function () {
                    if (this.reportFileName != '') {
                        $("#exportModal").modal("hide");
                        var createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                        createReportUrl += 'reportFileName=' + this.reportFileName;
                        this.reportFileName = '';
                        location.href = createReportUrl;
                    } else {
                        showAlert("Debe asignar un nombre de archivo", {type: 3});
                        return;
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
                activateDateTimePickerEnd: function (startDate) {

                    var minDate = moment(startDate, 'DD-MM-YYYY')
                            .format('YYYY-MM-DD');

                    var date = new Date();
                    var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();

                    this.dateTimePickerEnd = $('#endDate').datetimepicker({
                        locale: 'es',
                        format: 'DD-MM-YYYY',
                        useCurrent: false,
                        minDate: minDate,
                        maxDate: currentDate
                    }).data();
                },
                destroyDateTimePickerStart: function () {
                    this.activateDateTimePickerStart();
                    $("#startDate").on("dp.change", function (e) {
                        $('#endDate').data("DateTimePicker").minDate(e.date);
                    });
                    $("#endDate").on("dp.change", function (e) {
                        $('#startDate').data("DateTimePicker").maxDate(e.date);
                    });
                },
                validateFields: function () {
                    if (this.select.numEmployeeSearch.length > 0) {
                        this.select.distributor = {};
                        this.aux1 = false;
                        this.aux2 = true;

                    }else if (this.select.distributor.idDistributor > 0){
                        this.select.numEmployeeSearch = '';
                        this.aux2 = false;
                        this.aux1 = true;
                    }else {
                        this.aux1 = false;
                        this.aux2 = false;
                        this.aux3 = false;
                        this.aux4 = false;
                    }
                },
                findPerseptionsDeductions: function () {
                    if (this.select.numEmployeeSearch.length == 0 && this.select.distributor.distributorName.length == 0){
                        showAlert("Es necesario ingresar un id empleado o un distribuidor", {type:3});
                    }else {
                        if(this.select.startdate.length > 0){
                            if (this.select.endDate.length == 0){
                                showAlert("Es necesario ingresar una fecha final", {type: 3});
                            }else {
                                showAlert("Aqui llamada AJAX");
                            }
                        }else {
                            showAlert("Aqui llamada AJAX");
                        }
                    }
                }
            }
        });
    </script>

    </jsp:attribute>

<jsp:body>
    <div id="content" class="flex-box container-fluid">
    <div>
        <div class="col-md-8">
            <h2>Reporte de compensaciones</h2>
        </div>
        <div class="col-md-4 text-right" style="margin-top: 10px">
            <label>Solicitante</label>
            <p>
                <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
            </p>
        </div>
    </div>
        <div class="row">
            <div class="panel panel-default">
            <div class="panel-heading">Datos de la busqueda</div>
            <div class="panel-body">
                <div class="col-md-2">
                    <label>No. Empleado</label>
                    <input :disabled = "aux1"  v-model="select.numEmployeeSearch" type="text" class="form-control" placeholder="No. Empleado"  @input="validateFields">
                </div>
                <div class="col-md-2">
                    <label>Distribuidor</label>
                    <select :disabled ="aux2" v-model="select.distributor" class="form-control " @change="validateFields">
                        <option></option>
                        <option v-for="distributor in distributors" :value="distributor">
                            {{ distributor.distributorName}}
                        </option>
                    </select>
                </div>
                <div class="col-md-2">
                    <label>
                        Fecha inicial
                    </label>
                    <div class="form-group">
                        <div class="input-group date" id="startDate">
                            <input type="text" class="form-control" v-model="select.startdate" :disabled ="aux3">
                            <span class="input-group-addon" @click="destroyDateTimePickerStart">
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
                        <div class="input-group date" id="endDate">
                            <input  type="text" class="form-control" v-model="select.endDate" :disabled ="aux4">
                            <span class="input-group-addon"
                                  @click="activateDateTimePickerEnd(select.startdate)">
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
                <div class="col-md-12">
                    <br>
                    <div v-if="!isThereItems && searching == true"
                         style="height: 6rem; padding: 2rem 0;">
                        <div class="loader">Cargando...</div>
                    </div>
                    <div style="background: #ddd" class="panel panel-default"  v-if="isThereItems" >
                        <!-- Default panel contents -->
                        <!-- Table de contenidos -->
                        <div class="flex-box container-fluid"  v-if="dwEmployees.length > 0">
                            <div class="row table-header active">
                                <div class="col-md-2"><b>No de empleado</b></div>
                                <div class="col-md-2"><b>Nombre</b></div>
                                <div class="col-md-2"><b>RFC</b></div>
                                <div class="col-md-2"><b>Total de percepción</b></div>
                                <div class="col-md-2"><b>Total de deducción</b></div>
                                <div class="col-md-1"><b>Total</b></div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row " v-for="dwEmployee in dwEmployees" onmouseover='this.style.background="#2ba6cb"' onmouseout='this.style.background="#DDDDDD"'>
                                    <div class="col-md-1">{{dwEmployee.idEmployee}}</div>
                                    <div class="col-md-2">{{dwEmployee.fullName}}</div>
                                    <div class="col-md-2">{{dwEmployee.rfc}}</div>
                                    <div class="col-md-2">{{policy.dStartValidity.dayOfMonth}}-{{policy.dStartValidity.monthValue}}-{{policy.dStartValidity.year}}</div>
                                    <div class="col-md-2">{{policy.dEndValidity.dayOfMonth}}-{{policy.dEndValidity.monthValue}}-{{policy.dEndValidity.year}}</div>
                                    <div class="col-md-1">{{policy.insuranceAmount}}</div>
                                    <div class="col-md-1">{{policy.cTypeSecure.name}}</div>
                                    <div class="col-md-1">{{policy.priceSale}}</div>
                                    <div class="col-md-1">{{policy.cost}}</div>
                                    <div class="col-md-1">{{policy.total}}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-body flex-row flex-content"  v-if="Polycys.length > 0" style="width:100%">
                        <div class="row table-row" style="font-weight: bold;">
                            <div class="col-md-1">Totales:</div>
                            <div class="col-md-8"></div>
                            <div class="col-md-1">{{var2}}</div>
                            <div class="col-md-1">{{var3}}</div>
                            <div class="col-md-1">{{var1}}</div>
                        </div>
                    </div>
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
                            <div clas ="col-md-2">
                            <div class="checkbox">
                                <label>
                                    <input type="radio"  id="input-1" value="radio1" v-model="picked">
                                    Con desglose
                                </label>
                            </div>
                                <br>
                                <br>

                            </div>
                            <div class="checkbox">
                                <label>
                                    <input  type="radio"  id="input-2" value="radio2" v-model="picked" >
                                    Sin desglose
                                </label>
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