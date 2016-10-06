<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 26/09/16
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Boletos de avión">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.getPlaneTicket();
                },
                created: function () {

                },
                data: {
                    idPlaneTicket: ${planeTicket},
                    planeTicket: {},
                    justification: null
                },
                methods: {
                    getPlaneTicket: function () {
                        this.$http.get(ROOT_URL + '/plane-tickets/' + this.idTravelExpense).success(function (data) {
                            this.planeTicket = data;
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
                                ROOT_URL + '/plane-tickets/change-status?plane_ticket=' + this.idTravelExpense,
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
        </style>
    </jsp:attribute>

    <jsp:body>
        <div class="col-md-12" id="content">
            <div class="row">
                <div class="col-md-8">
                    <h2>Soicitud de viáticos</h2>
                </div>
                <div class="col-md-4 text-right" style="margin-top: 20px">
                    <h4>Folio: <b class="text-primary">{{planeTicket.request.folio}}</b></h4>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>

