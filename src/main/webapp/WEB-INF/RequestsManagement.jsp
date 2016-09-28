<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 26/09/16
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Administración de solicitudes de viaticos">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {

                },
                created: function () {

                },
                data: {
                    user: {},
                    travelExpenses: [],
                    checks: [],
                    planeTickets: [],
                    refunds: []
                },
                methods: {
                    getUserInSession: function () {
                        this.$http.get(ROOT_URL + "/user")
                                .success(function (data) {
                                    this.user = data;
                                })
                                .error(function (data) {
                                    showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                                });
                    },
                    getTravelExpenses: function () {
                        this.$http.get(ROOT_URL + "/travel-expenses")
                                .success(function (data) {
                                    this.travelExpenses = data;
                                });
                    },
                    getChecks: function () {
                        this.$http.get(ROOT_URL + "/checks")
                                .success(function (data) {
                                    this.checks = data;
                                });
                    },
                    getPlaneTickets: function () {
                        this.$http.get(ROOT_URL + "/plane-tickets")
                                .success(function (data) {
                                    this.planeTickets = data;
                                });
                    },
                    getRefunds: function () {
                        this.$http.get(ROOT_URL + "/refunds")
                                .success(function (data) {
                                    this.refunds = data;
                                });
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            #content {
                margin-top: 1.5rem;
            }
            .loader {
                margin-top: 2rem;
            }
            .notification.expanded {
                margin-top: 1.5rem;
                margin-bottom: 1.5rem;
            }
            .notifications-list {
                margin: 2rem 0;
            }
            .notifications-list h4 {
                margin-left: 2rem;
            }
            .notifications-group {
                margin-bottom: 2rem;
            }
            .notifications-group :last-child .card {
                border-bottom: 1px solid #c8c8c8;
            }
            .notification-details {
                padding: 2rem;
                border: 1px solid #c8c8c8;
                background: #FFFFFF;
                box-shadow: 0 -1px 0 #e5e5e5, 0 0 2px rgba(0,0,0,.12), 0 2px 4px rgba(0,0,0,.24);
            }
            .card-body {
                cursor: auto;
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div class="col-md-12" id="content">
            <div class="row">
                <div class="col-md-6">
                    <h2>Gastos de viaje</h2>
                </div>
                <div class="col-md-6">
                    <input type="text" class="form-control" v-model="">
                </div>
            </div>
            <div class="col-md-12">
                <div class="row notification" v-for="travelExpense in travelExpenses">
                    <div class="col-xs-12">
                        <div class="card card-inline clearfix">
                            <div class="card-body clearfix">
                                <div class="card-image">
                                    <span class="badge">V</span>
                                </div>
                                <div class="card-title">
                                    <p>
                                        Viáticos
                                    </p>
                                </div>
                                <div class="card-subtitle">
                                    <p>
                                        Folio - {{travelExpense.request.folio}}
                                    </p>
                                </div>
                                <div class="card-text">
                                    <p>
                                        Aplica -$ {{ accountPayable.amount}}
                                    </p>
                                </div>
                            </div>
                            <div class="card-actions">
                                <a href="{{}}"
                                   target="_blank" data-toggle="tooltip" data-placement="top" title="Ver solicitud">
                                    <span class="glyphicon glyphicon-new-window"></span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
