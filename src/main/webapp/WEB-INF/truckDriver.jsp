<%--
  Created by IntelliJ IDEA.
  User: Kevin Salvador
  Date: 10/01/2017
  Time: 05:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>
<t:template pageTitle="BID Group: Consulta de polizas">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.getPolicys();
                },
                data: {
                    isThereItems: false,
                    Polycys: [],
                    searching: false,

                    selected:{

                        startDate:'',
                        policy:null
                    },



                    registerNumber: 0
                },
                methods : {
                    activateDateTimePickerStart: function () {
                        var date = new Date();
                        var dd = new Date().getDate()-1;
                        var currentDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + dd;
                        this.dateTimePickerStart = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'YYYY-MM-DD',
                            useCurrent: false,
                            maxDate: currentDate
                        }).data();

                    },
                    destroyDateTimePickerStart: function () {
                        this.activateDateTimePickerStart();
                        $("#startDate").on("dp.change", function (e) {
                        });
                    },


                    getPolicysByDate: function () {
                        var self = this;
                        this.$http.get(
                                ROOT_URL + '/policy-truckdriver/get-by-date?startDate='+this.selected.startDate
                        ).success(function (data) {
                            this.Polycys = data;
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

                    }
                }
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div id="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-8">
                        <h2>Conciliación camionero</h2>
                    </div>
                    <div class="col-md-2" v-if="Polycys.length > 0"  style="margin-top: 35px">
                        <label><p style="color: darkblue">Nùmero de registros: {{registerNumber}}</p></label>
                    </div>
                </div>
                <div class="row">
                    <form v-on:submit.prevent="searchPolize(selected.policy, selected.startDate)">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-2">
                                    <label>Fecha de aplicacion</label>
                                    <div class="form-group">
                                        <div class="input-group date" id="startDate">
                                            <input type="text" class="form-control" v-model="selected.startDate">
                                            <span class="input-group-addon" @click="activateDateTimePickerStart">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <button style="margin-top: 25px" class="btn btn-info" @Click="getPolicysByDate">Consultar</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-12">
                    <br>
                    <div v-if="!isThereItems && searching == true"
                         style="height: 6rem; padding: 2rem 0;">
                        <div class="loader">Cargando...</div>
                    </div>
                    <div style="background: #ddd" class="panel panel-default" v-if="isThereItems">
                        <!-- Default panel contents -->
                        <!-- Table de contenidos -->
                        <div class="flex-box container-fluid" v-if="dwEmployees.length > 0">
                            <div class="row table-header active">
                                <div class="col-md-1"><b>Numero de polizas</b></div>
                                <div class="col-md-2"><b>Precio de Venta</b></div>
                                <div class="col-md-2"><b>Comisión</b></div>
                                <div class="col-md-1"><b>Diferencia</b></div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row" v-for="dwEmployee in dwEmployees">
                                    <div class="col-md-1">{{Polycys.}}</div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
