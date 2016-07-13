<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 05/07/16
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Boletos de avión">
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
        <div class="col-md-12" id="content">
            <h2>Viaticos</h2>
            <div class="row">
                <div class="col-md-9">
                    <h3>Solicitud de Viáticos</h3>
                </div>
                <div class="col-md-3">
                    <label>Solicitante</label>
                    <p class="underline">{{userInSession.dwEmployee.employee.fullName}}</p>
                </div>
            </div>
            <div class="row">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Informacón de solicitud</h3>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-3">
                                <label>Viaje</label>
                                <br>
                                <label class="radio-inline">
                                    <input type="radio" name="travel" value="option1">Nacional
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="travel" value="option2">Internacional
                                </label>
                            </div>
                            <div class="col-md-3">
                                <label>
                                    Fecha inicial
                                </label>
                                <div class="form-group">
                                    <div class="input-group date" id="startDate">
                                        <input type="text" class="form-control" v-model="travelExpence.startDate">
                                   <span class="input-group-addon" @click="destroyDateTimePickerStart">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <label>
                                    Fecha final
                                </label>
                                <div class="form-group">
                                    <div class="input-group date" id="endDate">
                                        <input type="text" class="form-control" v-model="travelExpence.endDate">
                                   <span class="input-group-addon" @click="activateDateTimePickerEnd(travelExpence.startDate)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <label>Destino</label>
                                <input v-model="" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">
                                <label>km estimados</label>
                                <input v-model="" type="text" class="form-control">
                            </div>
                            <div class="col-md-2">
                                <label>Casetas</label>
                                <div class="input-group">
                                    <span class="input-group-addon">$</span>
                                    <input v-model="" type="text" class="form-control">
                                </div>

                            </div>
                            <div class="col-md-8">
                                <label>Motivo de viaje</label>
                                <textarea v-model="" cols="500" rows="1" class="form-control"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Comceptos autorizados</h3>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-2">
                                <label>Nombre completo</label>
                                <input v-model="" type="text" class="form-control">
                            </div>
                            <div class="col-md-2">
                                <label>Puesto</label>
                                <input v-model="" type="text" class="form-control">
                            </div>
                            <div class="col-md-2">
                                <label>Empresa</label>
                                <input v-model="" type="text" class="form-control">
                            </div>
                            <div class="col-md-2">
                                <label>Numero telefonico</label>
                                <input v-model="" type="text" class="form-control">
                            </div>
                            <div class="col-md-2">
                                <label>Fecha de nacimiento</label>
                                <input v-model="" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">
                                <label>Asiento</label>
                                <br>
                                <label class="radio-inline">
                                    <input type="radio" name="travel" value="option1">Ventana
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="travel" value="option2">Pasillo
                                </label>
                                <h6><strong>* Sujeto a disponibilidad</strong></h6>
                            </div>
                            <div class="col-md-4">
                                <label>Pasaporte</label>
                                <input v-model="" type="file" class="form-control">
                            </div>
                            <div class="col-md-4">
                                <label>Socio frecuente</label>
                                <input v-model="" type="text" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-offset-10 col-md-2 text-right">
                        <button class="btn btn-success form-control" @click="">Solicitar</button>
                    </div>
                </div>
                <br>
            </div>
        </div>
    </jsp:body>
</t:template>
