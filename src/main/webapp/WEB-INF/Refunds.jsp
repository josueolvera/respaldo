<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 06/07/16
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Rembolsos">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm= new Vue({
                el: '#content',
                ready: function () {

                },
                created: function () {
                    this.getUserInSession();
                },
                data: {
                    userInSession:null
                },
                methods: {
                    getUserInSession: function()
                    {
                        this.$http.get(ROOT_URL + "/user").
                        success(function (data)
                        {
                            this.userInSession = data;
                        }).error(function(data)
                        {
                            showAlert("Ha habido un error al obtener al usuario en sesion");
                        });

                    },
                    validateFile: function (event) {
                        if (! event.target.files[0].name.match(/(\.jpg|\.jpeg|\.pdf|\.png)$/i)) {
                            event.target.value = null;
                            showAlert("Tipo de archivo no admitido", {type:3});
                        }
                    },
                }
            });
        </script>
    </jsp:attribute>

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

    <jsp:body>
        <div id="content">
            <h2>Rembolsos</h2>
            <div class="row">
                <div class="col-md-2">
                    <label>Por concepto de</label>
                    <select class="form-control">
                        <option></option>
                        <option v-for="" value="">
                            HOSPEDAJE
                        </option>
                    </select>
                </div>
                <div class="col-md-6">
                </div>
                <div class="col-md-4 text-right">
                    <label>Solicitante</label>
                    <p>
                        <span class="label label-default">Nombre</span>
                    </p>
                </div>
            </div>
            <br>
            <div class="panel panel-default">
                <!-- Default panel contents -->
                <div class="panel-heading">Conceptos</div>
                <div class="panel-body">
                    <div class="col-xs-12">
                        <div class="row">
                            <div class="col-xs-2">
                                <label>Concepto</label>
                            </div>
                            <div class="col-xs-2">
                                <label>Nùmero de factura</label>
                                <input class="form-control" v-model="">
                            </div>
                            <div class="col-xs-2">
                                <label>Concepto de factura</label>
                                <input  class="form-control" v-model="">
                            </div>
                            <div class="col-xs-3">
                                <div class="col-xs-12">
                                    <label>Monto</label>
                                    <input  class="form-control" v-model="">
                                </div>
                                <dl class="dl-horizontal">
                                    <dt>Total</dt>
                                    <dd>
                                        <p class="underline">

                                        </p>
                                    </dd>
                                </dl>
                            </div>
                            <div class="col-xs-3">
                                <label>Documentos</label>
                                <input @change="validateFile($event)" type="file" class="form-control"
                                       :name=""
                                       accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="panel panel-default">
                <!-- Default panel contents -->
                <div class="panel-heading">Justificaciòn del rembolso</div>
                <div class="panel-body">
                    <div class="col-xs-12">
                        <textarea v-model="" cols="500" rows="1" class="form-control"></textarea>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-11"></div>
                <div class="col-md-1 text-right">
                    <button type="button" class="btn btn-success" @click="">
                        Guardar
                    </button>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
