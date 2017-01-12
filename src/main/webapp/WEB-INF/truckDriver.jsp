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
                    policys:{},
                    selected:{
                        startDate:'',
                        policy:null
                    }
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
                    searchPolize: function (policy,startDate) {
                        console.log(this.selected)
                        if(policy.idTypePolicy==1){

                        }else if(policy.idTypePolicy==2){

                        }else{

                        }
                    },
                    getPolicys:function () {
                        this.$http.get(ROOT_URL+'/c-type-policy').success(function (data) {
                            this.policys=data;
                        })
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
                    <div class="col-md-2" style="margin-top: 35px">
                        <label><p style="color: darkblue">Nùmero de registros: {{registerNumber}}</p></label>
                    </div>
                </div>
                <div class="row">
                    <form v-on:submit.prevent="searchPolize(selected.policy, selected.startDate)">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-4">
                                    <label>Selecciona el tipo de poliza</label>
                                    <select class="form-control" v-model="selected.policy">
                                        <option></option>
                                        <option v-for="policy in policys" value="{{policy}}" >
                                            {{policy.name}}
                                        </option>
                                    </select>
                                </div>
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
                                    <button style="margin-top: 25px" class="btn btn-info">Consultar</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-12">
                    <br>
                    <div style="background: #ddd" class="panel panel-default">
                        <div class="flex-box container-fluid">
                            <div class="row table-header active">
                                <div class="col-md-2"><b>Id</b></div>
                                <div class="col-md-2"><b>No. de placa</b></div>
                                <div class="col-md-2"><b>No. de folio </b></div>
                                <div class="col-md-2"><b>Fecha inicial</b></div>
                                <div class="col-md-2"><b>Fecha final</b></div>
                                <div class="col-md-2"><b>Suma del seguro</b></div>
                            </div>
                            <br>
                            <div class="table-body flex-row flex-content">
                                <div class="row table-row" v-for="dwEmployee in dwEmployees">
                                    {{poliza.idSinister}}
                                    {{}}
                                    {{}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
