<%--
  Created by IntelliJ IDEA.
  User: g_on_
  Date: 07/06/2017
  Time: 12:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="v-bind" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>

<t:template pageTitle="BID Group: Tesorería">

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

        <style>
            #combo{

                background-color: transparent;
                border-width:0;
            }
        </style>
    </jsp:attribute>

    <jsp:attribute name="scripts">
    <script type="text/javascript">
        var vm = new Vue({
            el: '#content',
            created: function () {

            },
            ready: function () {
                this.getUserInSession();
                //this.activateDateTimePickerStart();
                this.getDistributors();
                this.obtainDetailBanks();
                this.obtainDistributorsWithRequests();
            },
            data: {
                startDate: '',
                endDate: '',
                total: 0.00,
                totalParcial: 0,
                userInSession: {},
                userActive: '',
                opcionReporte: '',
                distributors: [],
                distributorsWithRequests: [],
                bussinessLine: {
                    acronyms: ''
                },
                requestsPD: [],
                distributorSelected: {},
                detailBanks: [],
                requestsDates: [],
                banks:[],
                pD2:{
                    requestsSelected: []
                },
                folio: '',
                arregloPd: [],
                application: '',
                timePickerApplicationDate: '',
                dateTimePickerStart: null,
                dateTimePickerEnd: null,
                request: {},
                reschedule: {
                    applicationDate: ''
                },
                selectedOptions:{
                    bank: {
                        idBank: 0
                    }
                },
                objectBanks: [],
                distributor: ''
            },
            methods: {
                exportReportPayRequests: function () {
                    if(this.startDate == '' || this.endDate == '' || this.opcionReporte == '') {
                        showAlert("Selecciona todos los parametros", {type: 3});
                    }else{
                        if(this.opcionReporte == 2) {
                            this.startDate = this.dateTimePickerStart.DateTimePicker.date().toISOString().slice(0, -1);
                            this.endDate = this.dateTimePickerEnd.DateTimePicker.date().toISOString().slice(0, -1);
                            window.location = ROOT_URL + "/pay-requests-history/report-pay-requests?startDate=" + this.startDate + "&endDate=" + this.endDate;
                            this.clearReportData();
                        }else if(this.opcionReporte == 1){
                            this.startDate = this.dateTimePickerStart.DateTimePicker.date().toISOString().slice(0, -1);
                            this.endDate = this.dateTimePickerEnd.DateTimePicker.date().toISOString().slice(0, -1);
                            window.location = ROOT_URL + "/purchase-invoice/report-payables?startDate=" + this.startDate + "&endDate=" + this.endDate + "&status=" + 8;
                            this.clearReportData();
                        }
                    }
                },
                clearReportData: function () {
                    this.startDate = '';
                    this.endDate = '';
                    this.opcionReporte = '';
                },
                getDistributors: function () {
                    this.$http.get(ROOT_URL + "/distributors").success(function (data) {
                        this.distributors = data;
                    })
                },
                findByFolio: function () {
                    this.requestsPD = [];
                    this.$http.get(ROOT_URL + "/requests/folios?folio=" + this.folio).success(function (data) {
                        this.requestsPD = data;
                    })
                },
                obtainCurrentRequests: function () {
                    this.requestsPD = [];
                    //this.distributor = idDistributor;
                    this.$http.get(ROOT_URL + "/requests/distributor/" + this.distributorSelected.idDistributor).success(function (data) {
                        var jsonObjectIndex = {};
                        var self = this;
                        data.forEach(function (pD2) {
                            if(isNaN(pD2.distributorCostCenter)){
                                jsonObjectIndex[pD2.distributorCostCenter._id] = pD2.distributorCostCenter;
                            }else{
                                pD2.distributorCostCenter = jsonObjectIndex[pD2.distributorCostCenter];
                            }
                        });
                        data.forEach(function (pD2) {
                            if (isNaN(pD2.requestCategory)){
                                jsonObjectIndex[pD2.requestCategory._id] = pD2.requestCategory;
                            }else{
                                pD2.requestCategory = jsonObjectIndex[pD2.requestCategory];
                            }
                        });
                        this.requestsPD = data;
                    }).error(function () {
                        showAlert("Error al obtener información de Requests", {type: 3});
                    });
                },
                obtainDistributorsWithRequests: function () {
                    this.distributorsWithRequests = [];
                    this.$http.get(ROOT_URL + "/requests/distributors-with-requests").success(function (data) {
                        this.distributorsWithRequests = data;
                    }).error(function () {
                       showAlert("Error al obtener la información.", {type: 3})
                    });
                },
                payRequestsSelected: function () {
                    this.requestsPD = [];
                    this.$http.post(ROOT_URL + "/requests/pay-selected", JSON.stringify(this.pD2)).success(function (data) {
                        this.arregloPd = data;
                        var self = this;
                        showAlert("Se pagaron con exito las solicitudes!");
                        $("#modalPagar").modal("hide");
                        this.obtainCurrentRequests(this.distributorSelected.idDistributor);
                        this.total = 0;
                        this.obtainDetailBanks();
                        this.totalParcial = 0;
                        this.pD2.requestsSelected = [];
                        this.obtainDistributorsWithRequests();
                    }).error(function () {
                        showAlert("Error en la solicitud, vuelva a intentarlo", {type: 3});
                    });
                },
                obtainDetailBanks: function () {
                    var self = this;
                    this.$http.get(ROOT_URL + "/distributors-detail-banks").success(function (data) {
                        this.detailBanks = data;
                        var self = this;
                        this.detailBanks.forEach(function (element) {
                            self.total += element.amount;
                        });
                    });
                },
                obtainTotalParcial: function () {
                    var self = this;
                    self.totalParcial = 0;
                    this.pD2.requestsSelected.forEach(function (element) {
                        self.totalParcial += element.purchaseInvoices.amountWithIva
                    });
                },
                confirmSelection: function () {
                    var self = this;

                    if(this.pD2.requestsSelected == ''){
                        showAlert("Debes seleccionar algo para pagar", {type: 3});
                    }
                    else{
                        this.objectBanks = [];
                        this.pD2.requestsSelected.forEach(function (element) {
                            if(element.bank != null && element.bank != ''){
                                self.objectBanks.push(element);
                            }
                            //this.distributor =element.bank.idDistributor;
                        });

                        if(this.pD2.requestsSelected.length == this.objectBanks.length){
                            this.pD2.requestsSelected.forEach(function (element) {
                                if(element.bank.amount >= element.purchaseInvoices.amountWithIva){
                                    $("#modalPagar").modal("show");
                                }else{
                                    showAlert("No hay suficiente dinero para pagar", {type: 3});
                                }
                            });
                        }else {
                            showAlert("Debes seleccionar un banco por cada solicitud", {type: 3});
                        }
                    }
                },
                getUserInSession: function () {
                    this.$http.get(ROOT_URL + "/user")
                        .success(function (data) {
                            this.userInSession = data;
                            this.userActive = data.dwEmployee.employee.fullName;
                        })
                        .error(function (data) {
                            showAlert("Ha habido un error al obtener al usuario en sesion...");
                        });
                },
                onDateChanged: function () {
                    var startDate;
                    var endDate;

                    var startDateISOString = this.dateTimePickerStart.DateTimePicker.date().toISOString();
                    var endDateISOString = this.dateTimePickerEnd.DateTimePicker.date().toISOString();

                    startDate = new Date(startDateISOString);
                    endDate = new Date(endDateISOString);

                    if (startDate instanceof Date && endDate instanceof Date) {
                        var timeDiff = Math.abs(startDate.getTime() - endDate.getTime());
                        this.dateDifference = Math.ceil(timeDiff / (1000 * 3600 * 24)) + 1;
                    }
                },
                activateDateTimePickerStart: function () {
                    //var currentDate = new Date();

                    this.dateTimePickerStart = $('#startDate').datetimepicker({
                        locale: 'es',
                        format: 'DD-MM-YYYY',
                        useCurrent: false,
                        //maxDate: currentDate
                    }).data();
                    //var self = this;
                    //$('#startDate').on('dp.change', function (e) {
                        //self.onDateChanged();
                    //});

                },
                activateDateTimePickerEnd: function (startDate) {
                    if(startDate != '') {
                        var minDate = moment(startDate, 'DD-MM-YYYY')
                            .format('YYYY-MM-DD');

                        this.dateTimePickerEnd = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: minDate
                        }).data();
                        //var self = this;
                        //$('#endDate').on('dp.change', function (e) {
                            //self.onDateChanged();
                        //});
                    }else {
                        showAlert("Primero selecciona la fecha de inicio", {type: 3})
                    }
                },
                destroyDateTimePickerStart: function () {
                    $('#startDate').on('dp.change', function (e) {
                        $('#endDate').data('DateTimePicker').minDate(e.date);
                    });

                    $('#endDate').on('dp.change', function (e) {
                        $('#startDate').data('DateTimePicker').maxDate(e.date);
                    });
                },
                activarTimePickerApplicationDate: function () {
                    var fecha = new Date();
                    var fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();

                    this.timePickerApplicationDate = $('#dateApplication').datetimepicker({
                        locale: 'es',
                        format: 'DD-MM-YYYY',
                        useCurrent: false,
                        minDate: fecha_actual
                    }).data();
                },
                getRescheduleData: function (pd) {
                    this.request = (JSON.parse(JSON.stringify(pd)));
                    $("#modalReprogramar").modal("show");
                },
                reschedulePD: function () {
                    this.requestsPD = [];
                    if (this.application != '') {
                        this.reschedule.applicationDate = this.timePickerApplicationDate.DateTimePicker.date().toISOString().slice(0, -1);
                        this.$http.post(ROOT_URL + "/accounts-payables-dates/reschedule/" + this.request.idRequest, JSON.stringify(this.reschedule)).success(function (data) {
                            this.arregloPd = data;
                            showAlert("Reprogramacion exitosa");
                            $("#modalReprogramar").modal("hide");
                            this.obtainCurrentRequests(this.distributor);
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type: 3});
                        });
                    } else {
                        showAlert("Debes ingresar la fecha de aplicaciòn", {type: 3});
                    }
                }
            },

            filters: {
                separate: function (value) {
                    return value.replace(/:/g, ' ');
                },
                currencyDisplay: {
                    read: function (val) {
                        if (typeof val == 'number') {
                            return val.formatMoney(2, '');
                        }
                    },
                    write: function (val, oldVal) {
                        var number = +val.replace(/[^\d.]/g, '');
                        return isNaN(number) ? 0 : parseFloat(number.toFixed(2));
                    }
                }
            }
        });
    </script>

    <script type="text/javascript">
        function mueveReloj(){
            momentoActual = new Date()
            dia = momentoActual.getDate()
            if(dia < 10)
                dia = "0" + dia;
            mes = momentoActual.getMonth()+1
            if(mes < 10)
                mes = "0" + mes;
            anio = momentoActual.getFullYear()

            hora = momentoActual.getHours()
            if(hora < 10)
                hora = "0" + hora
            minuto = momentoActual.getMinutes()
            if(minuto < 10)
                minuto = "0" + minuto;
            segundo = momentoActual.getSeconds()
            if(segundo < 10)
                segundo = "0" + segundo;

            horaImprimible = dia + " / " + mes + " / " + anio + "           " + hora + ":" + minuto + ":" + segundo

            document.form_reloj.reloj.value = horaImprimible

            setTimeout("mueveReloj()",1000)
        }
    </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-4">
                        <h1>Tesorería</h1>
                    </div>

                    <div class="col-md-3" >
                        <form>
                            <div class="col-md-12" style="margin-top: 10px">
                                <label>Búsqueda por folio</label>
                                <input class="form-control" v-model="folio">
                            </div>

                        </form>
                    </div>
                    <div class="col-md-2">
                        <button style="margin-top: 20%" class="btn btn-info" @click="findByFolio()">Buscar</button>
                    </div>
                    <div class="col-md-3 text-right" style="margin-top: 10px">
                        <label>Solicitante</label>
                        <p>
                            <span class="label label-default">{{userActive}}</span>
                        </p>
                    </div>
                </div>
            </div>
            <br>
            <div class="panel panel-default" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #aaaaaa">
                            <h5 style="text-align: center"><b style="color: black">TOTAL DE FLUJO DE EFECTIVO</b></h5>
                        </div>
                        <table class="table table-striped">
                            <thead>
                            <th class="col-md-12 text-center">
                                <body onload="mueveReloj()">
                                <form name="form_reloj">
                                    <input type="text" name="reloj"
                                           style="background-color:transparent; border-width:0; text-align: center" size="30">
                                </form>
                                </body>
                            </th>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="col-md-12" style="text-align: center"><b>TOTAL: </b>{{total | currency}}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!--CUENTAS-->
            <div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="col-md-12" style="background-color: #aaaaaa">
                                <h5><b style="color: black">CUENTAS</b></h5>
                            </div>
                            <div class="panel-body">
                                <div class="col-md-12">
                                    <div class="col-md-3">
                                        <label>Selecciona tipo de Cuentas</label>
                                        <select v-model="opcionReporte" class="form-control">
                                            <option></option>
                                            <option :value="1">Cuentas por pagar</option>
                                            <option :value="2">Cuentas pagadas</option>
                                        </select>
                                    </div>

                                    <div class="col-md-3 text-left" style="padding-left: 0">
                                        <label>De:</label>
                                        <div class="form-group">
                                            <div class="input-group date" id="startDate">
                                                <input type="text" class="form-control"
                                                       v-model="startDate" required>
                                                <span class="input-group-addon" @click="activateDateTimePickerStart">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-3 text-left">
                                        <label>A:</label>
                                        <div class="form-group">
                                            <div class="input-group date" id="endDate">
                                                <input type="text" class="form-control"
                                                       v-model="endDate" required>
                                                <span class="input-group-addon" @click="activateDateTimePickerEnd(startDate)">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-3 text-left">
                                        <div>
                                            <button style="margin-top: 10%" type="button" class="btn btn-success" @click="exportReportPayRequests">Generar</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-12">
                <div class="col-md-4">
                    <label>Seleccione un distribuidor</label>
                    <select class="form-control" v-model="distributorSelected" @change="obtainCurrentRequests()">
                        <option></option>
                        <option v-for="distributor in distributors" :value="distributor">
                            {{distributor.idDistributor}} - {{distributor.distributorName}}
                        </option>
                    </select>
                </div>
                <br>
                <div class="col-md-8">
                    <div class="panel panel-default">
                        <div class="card">
                            <div class="card-header" role="tab" id="headingOne">
                                <div class="panel-heading" style="background-color: #83d386">
                                    <a class="collapsed" data-toggle="collapse" data-parent="#collapseOne" href="#collapseOne"
                                       aria-expanded="false" aria-controls="collapseOne">
                                        <div class="col-md-11 text-center">
                                            <b style="color: #ffffff">BUZÓN DE SOLICITUDES</b>
                                        </div>
                                    </a>
                                    <div class="col-md-1 text-right">
                                        <label class="circleyel"></label>
                                    </div>
                                    <br>
                                </div>
                            </div>
                            <div id="collapseOne" class="collapse" role="tabpanel" aria-labelledby="headingOne">
                                <div class="card-block">
                                    <div class="panel-body">
                                        <div class="col-md-12">
                                            <div class="row">
                                                <table class="table table-striped">
                                                    <tr>
                                                        <td class="col-md-3 text-center"><b>Distribuidor</b></td>
                                                        <td class="col-md-3 text-center"><b>Saldo en cuenta</b></td>
                                                        <td class="col-md-3 text-center"><b>Total a pagar</b></td>
                                                        <td class="col-md-3 text-center"><b>Numero de solicitudes</b></td>
                                                    </tr>
                                                    <tr v-for="DWR in distributorsWithRequests">
                                                        <td class="col-md-3 text-center">{{DWR.idDistributor}} - {{DWR.distributorName}}</td>
                                                        <td class="col-md-3 text-center">{{DWR.accountBalance | currency}}</td>
                                                        <td class="col-md-3 text-center">{{DWR.amountExpended | currency}}</td>
                                                        <td class="col-md-3 text-center">{{DWR.requestNumber}}</td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Colapso de la empresa seleccionada-->
            <div class="col-md-12" v-if="requestsPD.length > 0">
                <div class="panel panel-default">
                    <div class="card">
                        <div class="card-header" role="tab" id="headingThree">
                            <div class="panel-heading" style="background-color: #95f09c">
                                <a class="collapsed" data-toggle="collapse" data-parent="#collapseThree" href="#collapseThree"
                                   aria-expanded="false" aria-controls="collapseThree">
                                    <div class="col-md-11 text-center">
                                        <b style="color: #000000">Pago de solicitudes de la empresa: {{distributorSelected.distributorName}}</b>
                                    </div>
                                </a>
                                <div class="col-md-1 text-right">
                                    <label class="circleyel"></label>
                                </div>
                                <br>
                            </div>
                        </div>
                        <div id="collapseThree" class="collapse" role="tabpanel" aria-labelledby="headingThree">
                            <div class="card-block">
                                <div class="panel-body">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div>
                                                <div class="row table-header">
                                                    <div class="col-xs-1 text-center"><b>Folio</b></div>
                                                    <div class="col-xs-2 text-center"><b>Centro de Costos</b></div>
                                                    <div class="col-xs-1 text-center"><b>Tipo de Solicitud</b></div>
                                                    <div class="col-xs-1 text-center"><b>Beneficiario</b></div>
                                                    <div class="col-xs-1 text-center"><b>Núm. de Factura</b></div>
                                                    <div class="col-xs-1 text-center"><b>Monto con IVA</b></div>
                                                    <div class="col-xs-1 text-center"><b>Fecha de pago</b></div>
                                                    <div class="col-xs-2 text-center"><b>Banco</b></div>
                                                    <div class="col-xs-1 text-center"><b>Aplicar pago</b></div>
                                                    <div class="col-xs-1 text-center"><b>Re-programar</b></div>
                                                </div>
                                            </div>
                                        </div>
                                        <br>
                                        <div class="table-body flex-row flex-content-{{index}}">
                                            <div class="row table-row" v-for="(index2, pd) in requestsPD | orderBy 'requestsDates.scheduledDateFormats.dateNumber'">

                                                <div class="col-xs-1 text-center">{{pd.folio}}</div>
                                                <div class="col-xs-2 text-center">{{pd.distributorCostCenter.costCenter.name}}</div>
                                                <div class="col-xs-1 text-center">{{pd.requestCategory.requestCategoryName}}</div>
                                                <div class="col-xs-1 text-center">{{pd.purchaseInvoices.provider.providerName}}</div>
                                                <div class="col-xs-1 text-center">{{pd.purchaseInvoices.idPurchaseInvoices}}</div>
                                                <div class="col-xs-1 text-center">{{pd.purchaseInvoices.amountWithIva | currency}}</div>
                                                <div class="col-xs-1 text-center">{{pd.requestsDates.scheduledDateFormats.dateNumber}}
                                                </div>
                                                <div class="col-xs-2 text-center">
                                                    <select v-model="pd.bank" class="form-control" id="{{index2}}" required>
                                                        <option></option>
                                                        <option v-for="bank in detailBanks" v-if="pd.distributorCostCenter.idDistributor == bank.idDistributor" v-bind:value="bank" >
                                                            {{bank.banks.acronyms}} : {{bank.amount | currency}}
                                                        </option>
                                                    </select>
                                                </div>
                                                <div class="col-xs-1 text-center"><input type="checkbox" :value="pd" v-model="pD2.requestsSelected" @change="obtainTotalParcial()"></div>
                                                <div class="col-xs-1 text-center">
                                                    <button class="btn btn-default btn-sm"
                                                            data-toggle="tooltip" data-placement="top" title="Reprogramar"
                                                            @click="getRescheduleData(pd)">
                                                        <span class="glyphicon glyphicon-time"></span>
                                                    </button>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <div class="text-right">
                                        <button type="button" class="btn btn-success text-right" name="button" @click="confirmSelection()"
                                                style="margin-top: 25px" data-toggle="modal">Pagar
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--Cierra colapso de la empresa seleccionada-->

                <%-- Modal para re-programar --%>
            <div class="modal fade" id="modalReprogramar" tabindex="-1" role="dialog" aria-labelledby=""
                 aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Reprogramar</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-6">
                                    <label>Fecha para reprogramar</label>
                                    <div class='input-group date' id='dateApplication'>
                                        <input type='text' class="form-control" v-model="application">
                                        <span class="input-group-addon" @click="activarTimePickerApplicationDate()">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                    </div>
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-success" @click="reschedulePD">Reprogramar</button>
                        </div>
                    </div>
                </div>
            </div>

                <%-- Modal para pagar --%>
            <div class="modal fade" id="modalPagar" tabindex="-1" role="dialog" aria-labelledby=""
                 aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Comprobante de pago de solicitudes</h4>
                        </div>
                        <div class="modal-body" style="overflow:scroll;">
                            <div class="row">
                                <div class="col-md-12">
                                    <table class="table table-striped">
                                        <thead>
                                        <th class="col-xs-1 text-center"><b>Folio</b></th>
                                        <th class="col-xs-1 text-center"><b>Centro de Costos</b></th>
                                        <th class="col-xs-1 text-center"><b>Tipo de Solicitud</b></th>
                                        <th class="col-xs-1 text-center"><b>Beneficiario</b></th>
                                        <th class="col-xs-1 text-center"><b>Banco</b></th>
                                        <th class="col-xs-1 text-center"><b>Cuenta</b></th>
                                        <th class="col-xs-1 text-center"><b>CLABE</b></th>
                                        <th class="col-xs-1 text-center"><b>Núm. Factura</b></th>
                                        <th class="col-xs-1 text-center"><b>Monto con IVA</b></th>
                                        <th class="col-xs-2 text-center"><b>Fecha de pago</b></th>
                                        <th class="col-xs-1 text-center"><b>Banco</b></th>
                                        </thead>
                                        <tbody>
                                        <tr v-for="pd in pD2.requestsSelected">
                                            <td class="col-xs-1 text-center">{{pd.folio}}</td>
                                            <td class="col-xs-1 text-center">{{pd.distributorCostCenter.distributors.acronyms}}</td>
                                            <td class="col-xs-1 text-center">{{pd.requestCategory.requestCategoryName}}</td>
                                            <td class="col-xs-1 text-center">{{pd.purchaseInvoices.provider.providerName}}</td>
                                            <td class="col-xs-1 text-center">{{pd.purchaseInvoices.account.bank.acronyms}}</td>
                                            <td class="col-xs-1 text-center">{{pd.purchaseInvoices.account.accountNumber}}</td>
                                            <td class="col-xs-1 text-center">{{pd.purchaseInvoices.account.accountClabe}}</td>
                                            <td class="col-xs-1 text-center">{{pd.purchaseInvoices.idPurchaseInvoices}}</td>
                                            <td class="col-xs-1 text-center">{{pd.purchaseInvoices.amountWithIva | currency}}</td>
                                            <td class="col-xs-2 text-center">{{pd.requestsDates.scheduledDateFormats.dateNumber}}</td>
                                            <td class="col-xs-1 text-center">{{pd.bank.banks.acronyms}}</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button type="button" id="boton-pagar" class="btn btn-success" @click="payRequestsSelected()">Pagar</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <%--termina archivos de cotizacion--%>
    </jsp:body>
</t:template>