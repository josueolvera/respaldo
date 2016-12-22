<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Reports Corporate Roster">

    <jsp:attribute name="scripts">
        <script type="text/javascript">
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
        </script>

        <script>
            function isLetterKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 32 ||
                        charCode === 13 ||
                        (charCode > 64 && charCode < 91) ||
                        (charCode > 96 && charCode < 123) ||
                        charCode === 8
                ) {
                    return true;
                }
                else {
                    return false;
                }
            }
            function isNumberKeyAndLetterKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 13 ||
                        (charCode > 64 && charCode < 91) ||
                        (charCode > 47 && charCode < 58) ||
                        (charCode > 96 && charCode < 123) ||
                        charCode === 8
                ) {
                    return true;
                }
                else {
                    return false;
                }

            }
        </script>

        <script type="text/javascript">
            var vm = new Vue({
                el: '#contenidos',
                created: function () {

                },
                ready: function () {
                    this.getAllReportsAuthorizedCoporate();
                },
                data: {
                    downloadUrl: ROOT_URL + '/calculation-reports/download/',
                    reportAuthorized:[]
                },
                methods: {
                    getAllReportsAuthorizedCoporate: function () {
                        this.$http.get(ROOT_URL + "/calculation-reports/authorized-corporate").success(function (data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (rA) {
                                if (isNaN(rA.sqlQueries)) {
                                    jsonObjectIndex[rA.sqlQueries._id] = rA.sqlQueries;
                                } else {
                                    rA.sqlQueries = jsonObjectIndex[rA.sqlQueries];
                                }
                            });
                            this.reportAuthorized = data;
                        }).error(function () {

                        })
                    }
                },
                filters: {}
            });


        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            textarea{
                resize: none;
            }

        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos">

            <div class="row">
                <div class="col-xs-8">
                    <h2>Repositorio corporativo</h2>
                </div>
                <div class="col-xs-4">
                </div>
            </div>
            <br>
            <div class="row" v-if="reportAuthorized.length>0">
                <div class="col-xs-12">
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading"><b>Reportes autorizados</b></div>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Nombre del archivo</th>
                                    <th>Descargar</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="report in reportAuthorized">
                                    <td>
                                        {{report.fileName}}.xlsx
                                    </td>
                                    <td>
                                        <a style="margin-left: 10px"  class="btn btn-default" :href="downloadUrl + report.idCalculationReport"
                                           data-toggle="tooltip" data-placement="top" title="Descargar">
                                            <span class="glyphicon glyphicon-cloud-download"></span>
                                        </a>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
