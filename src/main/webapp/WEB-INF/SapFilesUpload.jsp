<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Carga de archivos">
    <jsp:attribute name="scripts">
        <script type="text/javascript">

        </script>

        <script type="text/javascript">
            var vm= new Vue({
                el: '#filesUpload',
                data: {
                },
                methods: {

                    saveSapSales: function () {
                        var formElement = document.getElementById("formSap");
                        var formData = new FormData(formElement);
                        this.$http.post(ROOT_URL + '/sap-sale/excel', formData)
                                .success(function (data) {
                                    console.log(data);
                                    $('#checkExistigSaleModal').modal('hide');
                                })
                                .error(function (data) {
                                    console.log(data);
                                });

                    },
                    updateSapSales: function () {
                        var formElement = document.getElementById("formSap");
                        var formData = new FormData(formElement);
                        this.$http.put(ROOT_URL + '/sap-sale/excel', formData)
                                .success(function (data) {
                                    console.log(data);
                                    $('#checkExistigSaleModal').modal('hide');
                                })
                                .error(function (data) {
                                    console.log(data);
                                });

                    },
                    checkExistingSale: function () {
                        var formElement = document.getElementById("formSap");
                        var formData = new FormData(formElement);
                        this.$http.post(ROOT_URL + '/sap-sale/check-existing-sale', formData)
                                .success(function (data) {
                                    console.log(data);
                                    if (data == true) {
                                        $('#checkExistigSaleModal').modal('show');
                                    } else {
                                        this.saveSapSales();
                                    }
                                })
                                .error(function (data) {
                                    console.log(data);
                                });
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="filesUpload">
            <h1>Carga de archivos SAP</h1>
            <form id="formSap" enctype="multipart/form-data" v-on:submit.prevent="checkExistingSale">
                <div class="form-group">
                    <label for="sapInputFile">SAP ventas</label>
                    <input type="file" name="file" id="sapInputFile"
                           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,
                           application/vnd.ms-excel">
                    <br>
                    <button class="btn btn-success" type="submit">
                        Cargar archivo SAP
                    </button>
                </div>
            </form>

            <div class="modal fade" id="checkExistigSaleModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <%--<div class="modal-header">--%>
                            <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>--%>
                            <%--<h4 class="modal-title" id="myModalLabel">Aviso</h4>--%>
                        <%--</div>--%>
                        <div class="modal-body">
                            Algunas de estas ventas ya existen, ¿desea sobreescribir los registros o guardarlos como nuevos?.
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="saveSapSales">Guardar</button>
                            <button type="button" class="btn btn-primary" @click="updateSapSales">Sobreescribir</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>



        </div>
    </jsp:body>
</t:template>
