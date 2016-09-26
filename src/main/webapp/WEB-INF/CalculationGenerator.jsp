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
                    this.activateDateTimePickerStartDate();
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
                    btn : false
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
                    activateDateTimePickerEndDate: function (fechaFinal) {

                        var currentDate = new Date();

                        var fecha= moment(fechaFinal, 'DD-MM-YYYY').format('YYYY-MM-DD');

                        this.dateTimePickerEndDate = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: fecha
                        }).data();
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
                        this.fromDate = this.dateTimePickerStartDate.DateTimePicker.date().toISOString().slice(0, -1);
                        this.ofDate = this.dateTimePickerEndDate.DateTimePicker.date().toISOString().slice(0, -1);
                        window.location = ROOT_URL + "/sap-sale/prueba?fromDate="+this.fromDate+"&toDate="+this.ofDate;
                        this.btn = true;
//                        showAlert('No es posible generar debido a que no existen empleados asignados', {type:2});
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
                    <form v-on:submit.prevent="searchCalculation">
                        <div class="col-md-3">
                            <label>Fecha de inicio</label>
                            <div class="form-group">
                                <div class="input-group date" id="startDate">
                                    <input type="text" class="form-control" v-model="search.startDate" required>
                                    <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
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
                            <button type="button" class="btn btn-success form-control"
                                    @click="generateCalculation" style="margin-top: 27px">
                                Generar cálculo
                            </button>
                        </div>
                        <div class="col-md-2" v-if="btn">
                            <button type="button" @click="saveCalculation" class="btn btn-info form-control" style="margin-top: 27px">
                                Guardar calculo
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
