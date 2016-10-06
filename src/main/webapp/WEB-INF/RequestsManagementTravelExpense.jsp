<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 26/09/16
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Gastos de viaje">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.getTravelExpense();
                },
                created: function () {

                },
                data: {
                    idTravelExpense: ${travelExpense},
                    travelExpense: {},
                    justification: null
                },
                methods: {
                    getTravelExpense: function () {
                        this.$http.get(ROOT_URL + '/travel-expenses/' + this.idTravelExpense).success(function (data) {
                            this.travelExpense = data;
                        }).error(function (data) {

                        });
                    },
                    changeRequestStatus: function (idRequesStatus) {

                        var requestBody = {};

                        requestBody.status = idRequesStatus;

                        if (this.justification != null) {
                            requestBody.justification = this.justification;
                        }

                        this.$http.post(
                                ROOT_URL + '/travel-expenses/change-status?travel_expense=' + this.idTravelExpense,
                                requestBody
                        ).success(function (data) {
                            showAlert('Solicitud ' + data.request.requestStatus.requestStatus);
                            this.goBack();
                        }).error(function (data) {

                        });
                    },
                    goBack: function () {
                        window.location = ROOT_URL + "/siad/requests-management";
                    },
                    showJustificationModal: function () {
                        $('#justificationModal').modal('show')
                    },
                    hideJustificationModal: function () {
                        this.justification = null;
                        $('#justificationModal').modal('hide')
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            .underline {
                border-bottom: 1px solid grey;
            }

            textarea {
                resize: none;
            }

        </style>
    </jsp:attribute>

    <jsp:body>
        <div class="col-md-12" id="content">
            <div class="row">
                <div class="col-md-8">
                    <h2>Soicitud de viáticos</h2>
                </div>
                <div class="col-md-4 text-right" style="margin-top: 20px">
                    <h4>Folio: <b class="text-primary">{{travelExpense.request.folio}}</b></h4>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-8">
                            <b>Información de solicitud</b>
                        </div>
                        <div class="col-md-4 text-right">
                            <b :class="{ 'text-primary': travelExpense.request.requestStatus.idRequestStatus == 1, 'text-success': travelExpense.request.requestStatus.idRequestStatus == 4, 'text-danger': travelExpense.request.requestStatus.idRequestStatus == 3 }">{{travelExpense.request.requestStatus.requestStatus}}</b>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">
                            <label>Viaje</label>
                            <p class="underline">
                                {{travelExpense.travelType.typeName}}
                            </p>
                        </div>
                        <div class="col-md-3">
                            <label>Fecha inicial</label>
                            <p class="underline">
                                {{travelExpense.startDateFormats.simpleDate}}
                            </p>
                        </div>
                        <div class="col-md-3">
                            <label>Fecha final</label>
                            <p class="underline">
                                {{travelExpense.endDateFormats.simpleDate}}
                            </p>
                        </div>
                        <div class="col-md-3">
                            <label>Destino</label>
                            <p class="underline">
                                {{travelExpense.destination}}
                            </p>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-8">
                            <label>Motivo de viaje</label>
                            <blockquote>
                                <p>{{travelExpense.request.purpose}}</p>
                            </blockquote>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-7">
                    <div class="col-md-12">
                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <b>Información de solicitante</b>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <label>Nombre</label>
                                            <p class="underline">
                                                {{travelExpense.request.userRequest.dwEmployee.employee.fullName}}
                                            </p>
                                        </div>
                                        <div class="col-md-4">
                                            <label>Puesto</label>
                                            <p class="underline">
                                                {{travelExpense.request.userRequest.dwEmployee.role.roleName}}
                                            </p>
                                        </div>
                                        <div class="col-md-4">
                                            <label>Empresa</label>
                                            <p class="underline">
                                                {{travelExpense.request.userRequest.dwEmployee.dwEnterprise.distributor.distributorName}}
                                            </p>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-md-4" v-if="travelExpense.request.userRequest.dwEmployee.dwEnterprise.region.regionName != ''">
                                            <label>Región</label>
                                            <p class="underline">
                                                {{travelExpense.request.userRequest.dwEmployee.dwEnterprise.region.regionName}}
                                            </p>
                                        </div>
                                        <div class="col-md-4">
                                            <label>Sucursal</label>
                                            <p class="underline">
                                                {{travelExpense.request.userRequest.dwEmployee.dwEnterprise.branch.branchName}}
                                            </p>
                                        </div>
                                        <div class="col-md-4">
                                            <label>Area</label>
                                            <p class="underline">
                                                {{travelExpense.request.userRequest.dwEmployee.dwEnterprise.area.areaName}}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <b>Deposito</b>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <label>Banco</label>
                                            <p class="underline">
                                                {{travelExpense.request.userRequest.dwEmployee.employee.employeesAccountsList[0].account.bank.acronyms}}
                                            </p>
                                        </div>
                                        <div class="col-md-4">
                                            <label>Número de cuenta</label>
                                            <p class="underline">
                                                {{travelExpense.request.userRequest.dwEmployee.employee.employeesAccountsList[0].account.accountNumber}}
                                            </p>
                                        </div>
                                        <div class="col-md-4">
                                            <label>CALBE</label>
                                            <p class="underline">
                                                {{travelExpense.request.userRequest.dwEmployee.employee.employeesAccountsList[0].account.accountClabe}}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <b>Conceptos autorizados</b>
                        </div>
                        <div class="panel-body">
                            <from class="form-horizontal">
                                <div class="form-group" v-for="concept in travelExpense.travelExpenseConceptList">
                                    <label class="col-md-4 control-label">{{concept.budgetConcept.budgetConcept}}</label>
                                    <div class="col-md-8 text-center">
                                        <p class="underline">
                                            {{concept.amount | currency}}
                                        </p>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">TOTAL</label>
                                    <div class="col-md-8 text-center">
                                        <p class="underline">
                                            {{travelExpense.totalAmount | currency}}
                                        </p>
                                    </div>
                                </div>
                            </from>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8"></div>
                <div class="col-md-4" v-if="travelExpense.request.requestStatus.idRequestStatus == 1">
                    <div class="col-md-6">
                        <button class="btn btn-success form-control" @click="changeRequestStatus(4)">Autorizar</button>
                    </div>
                    <div class="col-md-6">
                        <button class="btn btn-default form-control" @click="showJustificationModal">Rechazar</button>
                    </div>
                </div>
                <div class="col-md-4" v-else>
                    <div class="col-md-6">
                    </div>
                    <div class="col-md-6">
                        <button class="btn btn-default form-control" @click="goBack()">Regresar</button>
                    </div>
                </div>
            </div>
            <br>
            <div class="modal fade" id="justificationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <form v-on:submit.prevent="changeRequestStatus(3)">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Justificación de rechazo</h4>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>Justifique</label>
                                    <textarea rows="5" v-model="justification" class="form-control" required></textarea>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-success">Aceptar</button>
                                <button type="button" class="btn btn-default" @click="hideJustificationModal">Cancelar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>

