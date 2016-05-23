<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 19/05/16
  Time: 13:48
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
                    this.getTickets();
                    this.getIncidences();
                    this.getPriorities();
                    this.getUserInSession();
                    this.getTicketStatus();
                },
                data: {
                    tickets:[],
                    incidences:[],
                    priorities:[],
                    ticketStatusList:[],
                    userInSession:'',
                    incidence:'',
                    priority:'',
                    ticketStatus:'',
                    correo:'',
                    question:'',
                    currentTicket:''
                },
                methods: {
                    getTickets:function () {
                        this.$http.get(ROOT_URL + '/ticket').success(function (data) {
                            this.tickets = data;
                        }).error(function (data) {

                        });
                    },
                    closeAskModal:function () {
                        this.question = '';
                        this.currentTicket = '';
                        $('#askModal').modal('hide');
                    },
                    acceptAction:function (data) {
                        this.question = '¿Estas suguro de que quieres cambiar el estado de este ticket?';
                        this.currentTicket = data;
                        $('#askModal').modal('show');

                    },
                    changeTicketStatus:function () {

                        this.$http.post(
                                ROOT_URL + '/ticket/change-ticket-status/' + this.currentTicket.idTicket,
                                this.currentTicket.ticketStatus
                        ).success(function (data) {
                            $('#askModal').modal('hide');
                            this.question = '';
                            this.currentTicket = '';
                            this.getTicketsByTicketStatusPriority();
                        }).error(function (data) {

                        });
                    },
                    getTicketsByTicketStatusPriority:function () {

                        if (this.priority != '' && this.ticketStatus != '') {
                            this.$http.get(ROOT_URL + '/ticket/' + this.ticketStatus.idTicketStatus + '/' + this.priority.idPriority).success(function (data) {
                                this.tickets = data;
                            }).error(function (data) {

                            });
                        } else {
                            if (this.priority != '') {
                                this.$http.get(ROOT_URL + '/ticket/priority/' + this.priority.idPriority).success(function (data) {
                                    this.tickets = data;
                                }).error(function (data) {

                                });
                            } else {
                                this.getTickets();
                            }
                            if (this.ticketStatus != '') {
                                this.$http.get(ROOT_URL + '/ticket/ticket-status/' + this.ticketStatus.idTicketStatus).success(function (data) {
                                    this.tickets = data;
                                }).error(function (data) {

                                });
                            } else {
                                this.getTickets();
                            }
                        }
                    },
                    getIncidences:function () {
                        this.$http.get(ROOT_URL + '/incidence').success(function (data) {
                            this.incidences = data;
                        }).error(function (data) {

                        });
                    },
                    getPriorities:function () {
                        this.$http.get(ROOT_URL + '/priority').success(function (data) {
                            this.priorities = data;
                        }).error(function (data) {

                        });
                    },
                    getTicketStatus:function () {
                        this.$http.get(ROOT_URL + '/ticket-status').success(function (data) {
                            this.ticketStatusList = data;
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

                    },
                    toggle: function (ticket) {
                        if (ticket.show) {
                            ticket.show = false;
                            $("#ticket-" + ticket.idTicket).collapse('hide');
                            return;
                        }
                        Vue.set(ticket, "show", true );
                        $("#ticket-" + ticket.idTicket).collapse('show');
                    },
                }
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            .ticket-list {
                height: 500px;
                overflow-y: scroll;
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
            <h1 class="text-center">Tickets</h1>
            <br>
            <div class="col-xs-offset-1 col-xs-10">
                <div class="form-group">
                    <div class="row">
                        <div class="col-xs-4">
                            <label>Correo</label>
                            <input type="text" v-model="correo" class="form-control"/>
                        </div>
                        <div class="col-xs-4">
                            <label>Prioridad</label>
                            <select v-model="priority" class="form-control" @change="getTicketsByTicketStatusPriority">
                                <option></option>
                                <option v-for="priority in priorities" value="{{priority}}">
                                    {{priority.priorityName}}
                                </option>
                            </select>
                        </div>
                        <div class="col-xs-4">
                            <label>Estado</label>
                            <select v-model="ticketStatus" class="form-control" @change="getTicketsByTicketStatusPriority">
                                <option></option>
                                <option v-for="ticketStatus in ticketStatusList" value="{{ticketStatus}}">
                                    {{ticketStatus.ticketStatusName}}
                                </option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="panel-group ticket-list">
                    <div class="ticket panel panel-default"
                         v-for="ticket in tickets | filterBy correo" v-if="ticket.ticketStatus.idTicketStatus != 4">
                        <div class="panel-heading">
                            <div class="row table-header">
                                <div class="col-xs-2"><strong>Folio</strong></div>
                                <div class="col-xs-2"><strong>Correo</strong></div>
                                <div class="col-xs-6"><strong>Descripción</strong></div>
                                <div class="col-xs-2"><strong>Estado</strong></div>
                            </div>
                            <div class="row table-row">
                                <div class="col-xs-2"><p>{{ ticket.folio }}</p></div>
                                <div class="col-xs-2"><p>{{ ticket.correo }}</p></div>
                                <div class="col-xs-6"><p>{{ ticket.descripcionProblema }}</p></div>
                                <div class="col-xs-2">
                                    <select v-model="ticket.ticketStatus" class="form-control" @change="acceptAction(ticket)">
                                        <option v-for="ticketStatus in ticketStatusList" value="{{ticketStatus}}">
                                            {{ticketStatus.ticketStatusName}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div id="ticket-{{ ticket.idTicket }}" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <div class="col-xs-12 details-header">
                                        <div class="col-xs-4"><strong>Tipo de Incidencia</strong></div>
                                        <div class="col-xs-2"><strong>Prioridad</strong></div>
                                        <div class="col-xs-3"><strong>Fecha de inicio</strong></div>
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
                        <div class="row">
                            <div @click="toggle(ticket)" class="col-xs-12 text-center btn-toggle-ticket">
                                <span v-bind:class="{ 'glyphicon-menu-down': ! ticket.show, 'glyphicon-menu-up': ticket.show }" class="glyphicon"></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="askModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-body">
                                {{question}}
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" @click="changeTicketStatus">Aceptar</button>
                                <button type="button" class="btn btn-default" @click="closeAskModal">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
