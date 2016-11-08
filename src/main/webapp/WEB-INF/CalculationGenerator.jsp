<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../assets/css/barralateral.css">
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Generador de calculo">

    <jsp:attribute name="scripts">
        <script type="text/javascript">

            var vm = new Vue({
                el: '#content',
                ready : function () {
                    this.obtainDateType();
                    this.findAll();
                },
                data: {
                    search : {
                        startDate: '',
                        endDate: ''
                    },
                    dateTimePickerStartDate : null,
                    dateTimePickerEndDate : null,
                    fromDate: '',
                    ofDate: '',
                    commission: [],
                    btn : false,
                    btnExportReport: false,
                    dateTypes: [],
                    typeCalculation: {},
                    adviserCredit: {
                        fromDate: '',
                        toDate: ''
                    },
                    commsionableAmounts: [],
                    agreementsTabs: [],
                    name: ''
                },
                methods: {
                    activateDateTimePickerStartDate: function () {

                        var currentDate = new Date();

                        this.dateTimePickerStartDate = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false
                        }).data();
                    },
                    activateDateTimePickerStartDateMonth: function () {

                        this.dateTimePickerStartDate = $('#startDateMonth').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false
                        }).data();
                    },
                    activateDateTimePickerEndDate: function (fechaFinal) {

                        var currentDate = new Date();

                        var fecha = moment(fechaFinal, 'DD-MM-YYYY').format('YYYY-MM-DD');
                        var maxFecha = moment(fecha).add(4, 'days');

                        this.dateTimePickerEndDate = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: fecha
                      //      maxDate: maxFecha
                        }).data();
                    },
                    activateDateTimePickerEndDateMonth: function (fechaFinal) {
                        var fecha = '';
                        var maxFecha = '';
                        fecha = moment(fechaFinal, 'DD-MM-YYYY').format('YYYY-MM-DD');
                        maxFecha = moment(fecha).add(1, 'months').subtract(1, 'days');

                        this.dateTimePickerEndDate = $('#endDateMonth').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: fecha
                      //      maxDate: maxFecha
                        }).data();
                    },
                    destruirTimePickerEndDateMonth: function(){
                        this.search.endDate= "";
                        $("#startDate").on("dp.change", function (e) {
                         //   var fecha = moment(e.date, 'DD-MM-YYYY').format('YYYY-MM-DD');
                         //   var maxFecha = moment(fecha).add(1, 'months').subtract(1, 'days');
                            $('#endDateMonth').data("DateTimePicker").minDate(e.date);
                        });
                    },
                    destruirTimePickerEndDate: function () {
                        this.search.endDate= "";
                        $("#startDate").on("dp.change", function (e) {
                         //   var fecha = moment(e.date, 'DD-MM-YYYY').format('YYYY-MM-DD');
                         //   var maxFecha = moment(fecha).add(4, 'days');
                            $('#endDate').data("DateTimePicker").minDate(e.date);
                        });
                    },
                    saveCalculation : function () {
                        this.$http.get(ROOT_URL + "/backup-commission/save").success(function (data) {
                            this.commission = data;
                            showAlert("Calculo guardado con exito");
                            this.btn = true;
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type:3});
                            this.btn = false;
                        });
                    },
                    generateCalculation : function () {
                        this.commsionableAmounts = [];

                        var fecha = new Date();
                        var fecha_actual;
                        if(fecha.getDate() < 10){
                             fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-0" + fecha.getDate();
                        }else{
                             fecha_actual = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
                        }
                        var fechaMinima = moment(fecha_actual).subtract('months', 2).format('YYYY-MM-DD');

                        this.fromDate = this.dateTimePickerStartDate.DateTimePicker.date().toISOString().slice(0, -1);
                        this.ofDate = this.dateTimePickerEndDate.DateTimePicker.date().toISOString().slice(0, -1);

                        this.adviserCredit.fromDate = fechaMinima+"T00:00:00.000";
                        this.adviserCredit.toDate = fecha_actual+"T00:00:00.000";

                        this.$http.get(ROOT_URL + "/sap-sale/prueba/"+this.typeCalculation.idDateCalculation
                                +"?fromDate="+this.fromDate+"&toDate="+this.ofDate
                                +"&fromJoinDate="+this.adviserCredit.fromDate+"&toJoinDate="+this.adviserCredit.toDate).success(function (data) {
                            this.btnExportReport = true;
                            this.commsionableAmounts =  data;
                        }).error(function () {
                            showAlert("Hubo un error al generar el calculo intente de nuevo", {type:3});
                        });
//                        window.location = ROOT_URL + "/sap-sale/prueba/"+this.typeCalculation.idDateCalculation
//                                +"?fromDate="+this.fromDate+"&toDate="+this.ofDate
//                                +"&fromJoinDate="+this.adviserCredit.fromDate+"&toJoinDate="+this.adviserCredit.toDate;
//                        this.btn = true;
//                        showAlert('No es posible generar debido a que no existen empleados asignados', {type:2});
                    },
                    obtainDateType: function () {
                        this.$http.get(ROOT_URL + "/date-calculation").success(function (data) {
                            this.dateTypes = data;
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type: 3});
                        });
                    },
                    clear: function () {
                        this.search.endDate = "";
                        this.search.startDate = "";
                        this.activateDateTimePickerStartDateMonth();
                        this.activateDateTimePickerStartDate();
                        this.changeStatus();
                    },
                    exportReport: function () {
                        if(this.name.length > 0) {
                            window.location = ROOT_URL + "/commission-amount-group/report-advisers?file_name=" + this.name;
                            this.btn = true;
                            $('#modalNombre').modal('hide');
                            this.name = '';
                        } else {
                            showAlert("Ingresa un nombre al reporte", {type: 3})
                        }
                    },
                    exportReportMonthly: function () {
                        if(this.name.length > 0) {
                            window.location = ROOT_URL + "/commission-amount-group/report-all-commissions?fromDate="+this.fromDate+"&toDate="+this.ofDate+"&file_name="+this.name;
                            this.btn = true;
                            $('#modalNombre').modal('hide');
                            this.name = '';
                        } else {
                            showAlert("Ingresa un nombre al reporte", {type: 3})
                        }
                    },
                    findAll: function () {
                        this.$http.get(ROOT_URL + "/commission-amount-group").success(function (data) {
                            this.commsionableAmounts =  data;
                        });
                    },
                    changeStatus: function () {
                        this.$http.post(ROOT_URL + "/date-calculation/change-status/"+this.typeCalculation.idDateCalculation).success(function (data) {
                            this.agreementsTabs = data;
                        }).error(function () {
                        });
                    },
                    openModalReportName: function () {
                        this.name = "";
                        $('#modalNombre').modal('show');
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="container-fluid">
                <h2>Generador de cálculo de comisiones</h2>
                <br>
                <div class="row">
                    <div class="col-md-3">
                        <label>
                            Tipo de calculo
                        </label>
                        <select class="form-control" v-model="typeCalculation" @change="clear()">
                            <option v-for="type in dateTypes" :value="type">
                                {{type.nameDate}}
                            </option>
                        </select>
                    </div>
                </div>
                <br>
                <div class="row">
                    <form v-on:submit.prevent="searchCalculation">
                        <div class="col-md-3" v-show="typeCalculation.idDateCalculation  > 0 ">
                            <label>Fecha de inicio</label>
                            <div class="form-group">
                                <div class="input-group date" id="startDate">
                                    <input type="text" class="form-control" v-model="search.startDate" required>
                                    <span class="input-group-addon" @click="destruirTimePickerEndDate()">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-9" v-show="typeCalculation.idDateCalculation == 1">
                            <div class="col-md-4">
                                <label>Fecha final</label>
                                <div class="form-group">
                                    <div class="input-group date" id="endDate">
                                        <input type="text" class="form-control" v-model="search.endDate" required>
                                        <span class="input-group-addon" @click="activateDateTimePickerEndDate(search.startDate)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn form-control"
                                        @click="generateCalculation" style="margin-top: 27px">
                                    Generar cálculo
                                </button>
                            </div>
                            <div class="col-md-2">
                                <button type="button" @click="openModalReportName" class="btn btn-success form-control" style="margin-top: 27px">
                                    Exportar reporte
                                </button>
                            </div>
                            <div class="col-md-2" v-if="btn">
                                <button type="button" @click="saveCalculation" class="btn btn-info form-control" style="margin-top: 27px">
                                    Guardar calculo
                                </button>
                            </div>
                        </div>
                        <div class="col-md-9" v-show="typeCalculation.idDateCalculation == 2">
                            <div class="col-md-4">
                                <label>Fecha final</label>
                                <div class="form-group">
                                    <div class="input-group date" id="endDateMonth">
                                        <input type="text" class="form-control" v-model="search.endDate" required>
                                        <span class="input-group-addon" @click="activateDateTimePickerEndDateMonth(search.startDate)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn form-control"
                                        @click="generateCalculation" style="margin-top: 27px">
                                    Generar cálculo
                                </button>
                            </div>
                            <div class="col-md-2">
                                <button type="button" @click="openModalReportName" class="btn btn-success form-control" style="margin-top: 27px">
                                    Exportar reporte
                                </button>
                            </div>
                            <div class="col-md-2" v-if="btn">
                                <button type="button" @click="saveCalculation" class="btn btn-info form-control" style="margin-top: 27px">
                                    Guardar calculo
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <div v-if="!commsionableAmounts.length > 0"
                     style="height: 6rem; padding: 2rem 0;">
                    <div class="loader">Cargando...</div>
                </div>
            </div>

            <div class="modal fade" id="modalNombre" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
                <div class="modal-dialog ">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">
                                Nombre del archivo
                            </h4>
                        </div>
                        <div class="modal-body">
                            <div class="col-xs-6">
                                <input class="form-control" name="name" v-model="name">
                            </div>
                        </div>
                        <br>
                        <br>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button v-if="typeCalculation.idDateCalculation == 1" type="button" class="btn btn-success" @click="exportReport">
                                Reporte semanal
                            </button>
                            <button v-if="typeCalculation.idDateCalculation == 2" type="button" class="btn btn-success" @click="exportReportMonthly">
                                Reporte mensual
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
