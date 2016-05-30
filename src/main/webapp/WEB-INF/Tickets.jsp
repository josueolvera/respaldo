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

<t:template pageTitle="BID Group: Tickets">

    <jsp:attribute name="scripts">
        <script>
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.getIncidences();
                    this.getPriorities();
                    this.getUserInSession();
                },
                data: {
                    ticket:{
                        incidence:'',
                        priority:'',
                        descripcionProblema:'',
                        correo:'',
                        ticketStatus:{
                            idTicketStatus: 1,
                            ticketStatusName: "Abierto"
                        },

                    },
                    ticketFound:'',
                    incidences:[],
                    priorities:[],
                    userInSession:'',
                    validForm:false,
                    folioToSearch:''
                },
                methods: {
                    getTicketByFolio:function () {
                        this.$http.get(ROOT_URL + '/ticket/folio?folio=' + this.folioToSearch).success(function (data) {
                            this.ticketFound = data;
                        }).error(function (data) {

                        });
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
                    getUserInSession: function()
                    {
                        this.$http.get(ROOT_URL + "/user").
                        success(function (data)
                        {
                            this.userInSession = data;
                            this.ticket.correo = this.userInSession.mail;

                        }).error(function(data)
                        {
                            showAlert("Ha habido un error al obtener al usuario en sesion");
                        });

                    },
                    formValidation:function () {
                        if (
                                this.ticket.incidence != '' &&
                                this.ticket.priority != '' &&
                                this.ticket.descripcionProblema != '' &&
                                this.ticket.correo != ''
                        ) {
                            return true;
                        } else return false;
                    },
                    saveTicket:function () {
                        if (this.formValidation()) {
                            this.$http.post(ROOT_URL + '/ticket',this.ticket).success(function (data) {

                                this.ticket.incidence = '';
                                this.ticket.priority = '';
                                this.ticket.descripcionProblema = '';

                                showAlert("Registro guardado con exito");
                            }).error(function (data) {

                            });
                        } else {
                            showAlert("Tipo de insidencia, prioridad, descripción requeridos",{type:3});
                        }
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
            <h1 class="text-center">Tickets</h1>
            <br>
            <div class="col-xs-offset-1 col-xs-10">
                <form v-on:submit.prevent="saveTicket">
                    <div class="row">
                        <div class="col-xs-4">
                            <label>Solicitante</label>
                            <input type="text" class="form-control" v-model="userInSession.dwEmployee.employee.fullName" disabled>
                        </div>
                        <div class="col-xs-4">
                            <label>Tipo de solicitud</label>
                            <select class="form-control" v-model="ticket.incidence">
                                <option></option>
                                <option v-for="incidence in incidences" value="{{incidence}}">
                                    {{incidence.incidenceName}}
                                </option>
                            </select>
                        </div>
                        <div class="col-xs-4">
                            <label>Prioridad</label>
                            <select class="form-control" v-model="ticket.priority">
                                <option></option>
                                <option v-for="priority in priorities" value="{{priority}}">
                                    {{priority.priorityName}}
                                </option>
                            </select>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-xs-12">
                            <label>Descripción</label>
                            <textarea class="form-control" maxlength="200"
                                      rows="3" v-model="ticket.descripcionProblema">
                            </textarea>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="text-center">
                            <button type="submit" class="btn btn-success">Solicitar</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-xs-offset-1 col-xs-10">
                <br>
                <br>
                <h3 class="text-center">Consultar estado de ticket</h3>
                <br>
                <form class="form-inline text-center" v-on:submit.prevent="getTicketByFolio">
                    <div class="form-group">
                        <label>Folio</label>
                        <input type="search" v-model="folioToSearch" class="form-control"/>
                        <button type="submit" class="btn btn-primary">Buscar</button>
                    </div>
                </form>
                <br>
                <div class="panel-group ticket-list" v-if="ticketFound">
                    <div class="ticket panel panel-default">
                        <div class="panel-heading">
                            <div class="row table-header">
                                <div class="col-xs-2"><strong>Folio</strong></div>
                                <div class="col-xs-3"><strong>Solicitante</strong></div>
                                <div class="col-xs-5"><strong>Descripción</strong></div>
                                <div class="col-xs-2"><strong>Status</strong></div>
                            </div>
                            <div class="row table-row">
                                <div class="col-xs-2"><p>{{ ticketFound.folio }}</p></div>
                                <div class="col-xs-3"><p>{{ ticketFound.user.dwEmployee.employee.fullName }}</p></div>
                                <div class="col-xs-5"><p>{{ ticketFound.descripcionProblema }}</p></div>
                                <div class="col-xs-2"><p>{{ ticketFound.ticketStatus.ticketStatusName }}</p></div>
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
                                        <div class="col-xs-4"><p>{{ ticketFound.incidence.incidenceName }}</p></div>
                                        <div class="col-xs-2"><p>{{ ticketFound.priority.priorityName }}</p></div>
                                        <div class="col-xs-3">
                                            <p>
                                                {{ ticketFound.fechaInicioFormats.dateTextLong }} - {{ ticketFound.fechaInicioFormats.time12 }}
                                            </p>
                                        </div>
                                        <div class="col-xs-3">
                                            <p>
                                                {{ ticketFound.fechaFinalFormats.dateTextLong }} - {{ ticketFound.fechaFinalFormats.time12 }}
                                            </p>
                                        </div>
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
