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

                    uploadSapSales: function () {
//                        var formElement = document.getElementById("formSap");
//                        var formData = new FormData(formElement);
//                        this.$http.post(ROOT_URL + '/sap-sale/check-existing-sale', formData)
//                                .success(function (data) {
//                                    console.log(data);
//                                    if (data = true) {
//                                        $('#checkExistigSaleModal').modal('show');
//                                    }
//                                })
//                                .error(function (data) {
//                                    console.log(data);
//                                });

                    },
                    checkExistingSale: function () {
                        var formElement = document.getElementById("formSap");
                        var formData = new FormData(formElement);
                        this.$http.get(ROOT_URL + '/sap-sale/check-existing-sale', formData)
                                .success(function (data) {
                                    console.log(data);
                                    if (data = true) {
                                        $('#checkExistigSaleModal').modal('show');
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
                    <input type="file" name="file" id="sapInputFile">
                    <br>
                    <button class="btn btn-success" type="submit">
                        Cargar archivo SAP
                    </button>
                </div>
            </form>

            <div class="modal fade" id="checkExistigSaleModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">Aviso</h4>
                        </div>
                        <div class="modal-body">
                            Estas ventas ya existen, ¿desea sobreescribir los registros o guardar como nuevos?.
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-success">Guardar</button>
                            <button type="button" class="btn btn-primary">Sobreescribir</button>
                        </div>
                    </div>
                </div>
            </div>



        </div>
    </jsp:body>
</t:template>
