<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 23/05/16
  Time: 09:04
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Administración de tickets">

    <jsp:attribute name="scripts">
        <script>
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.getTicketByFolio();
                },
                data: {
                    ticket:{},
                    folio: "${folio}"
                },
                methods: {
                    getTicketByFolio:function () {
                        this.$http.post(ROOT_URL + '/ticket/folio', this.folio).success(function (data) {
                            this.ticket = data;
                        }).error(function (data) {

                        });
                    },
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
            .ticket-list {
                height: 500px;
                overflow-x: hidden;
            }
            .btn-toggle-ticket:hover {
                background-color: #AFAFAF;
            }
            .btn-toggle-ticket {
                border-color: #dddddd;
                background-color: #f5f5f5;
            }
            .ticket-list .ticket:nth-child(2n+1) .panel-heading,
            .ticket-list .ticket:nth-child(2n+1) .btn-toggle-ticket {
                background-color: #dddddd;
            }
            .ticket .table-header, .btn-toggle-ticket {
                cursor: pointer;
            }
            .ticket p {
                overflow-wrap: break-word;
            }
            .btn-cerrar-ticket {
                cursor: default;
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <br>
            <h1 class="text-center">Ticket</h1>
            <br>
            <div class="col-xs-offset-1 col-xs-10">
                <div class="panel-group ticket-list" v-if="ticket">
                    <div class="ticket panel panel-default">
                        <div class="panel-heading">
                            <div class="row table-header">
                                <div class="col-xs-2"><strong>Folio</strong></div>
                                <div class="col-xs-2"><strong>Correo</strong></div>
                                <div class="col-xs-6"><strong>Descripción</strong></div>
                                <div class="col-xs-2"><strong>Status</strong></div>
                            </div>
                            <div class="row table-row">
                                <div class="col-xs-2"><p>{{ ticket.folio }}</p></div>
                                <div class="col-xs-2"><p>{{ ticket.correo }}</p></div>
                                <div class="col-xs-6"><p>{{ ticket.descripcionProblema }}</p></div>
                                <div class="col-xs-2"><p>{{ ticket.ticketStatus.ticketStatusName }}</p></div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="panel">
                                <div class="panel-body">
                                    <div class="col-xs-12 details-header">
                                        <div class="col-xs-4"><strong>Tipo de solicitud</strong></div>
                                        <div class="col-xs-2"><strong>Prioridad</strong></div>
                                        <div class="col-xs-3"><strong>Fecha de solicitud</strong></div>
                                        <div class="col-xs-3"><strong>Fecha de fin</strong></div>
                                    </div>
                                    <div class="col-xs-12 details-row">
                                        <div class="col-xs-4"><p>{{ ticket.incidence.incidenceName }}</p></div>
                                        <div class="col-xs-2"><p>{{ ticket.priority.priorityName }}</p></div>
                                        <div class="col-xs-3"><p>{{ ticket.fechaInicioFormats.dateTextLong }}</p></div>
                                        <div class="col-xs-3"><p>{{ ticket.fechaFinalFormats.dateTextLong }}</p></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>