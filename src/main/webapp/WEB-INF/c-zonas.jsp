<%--
  User: Leonardo
  Date: 28/04/17
  Time: 09:32 AM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Zonas">
    <jsp:attribute name="styles">
        <style>
            .table-striped th {
                background: black;
                color: white;
            }
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
            }
            .table-body.table-row:nth-child(2n+1){
                background: #fdedf4;
            }
            .table-row {
                padding: 1rem;
            }
            .flex-content {
                overflow-x: hidden;
            }
        </style>
    </jsp:attribute>
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
            function isNumberKeyAndLetterKey(evt){
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 13 ||
                    (charCode > 64 && charCode < 91) ||
                    (charCode > 47 && charCode < 58) ||
                    (charCode > 96 && charCode < 123) ||
                    charCode === 8
                )  {
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
                    this.getZonasez();
                },
                data: {
                    zonasez: [],
                    zonase: {
                        name: ''
                    }
                },
                methods: {
                    getZonasez: function () {
                        this.$http.get(ROOT_URL + "/zonas").success(function (data) {
                            this.zonasez = data;
                        })
                    },
                    showModalAlta: function () {
                        this.zonase.name = '';
                        $("#modalAlta").modal("show");
                    },
                    saveZonase: function () {
                        if(this.zonase.name.length > 0){
                            this.$http.post(ROOT_URL + "/zonas/save-zonas", JSON.stringify(this.zonase)).success(function (data) {
                                this.zonasez = [];
                                this.zonasez= data;
                                $("#modalAlta").modal("hide");
                                showAlert("Registro guardado con exito");
                            }).error(function () {
                                showAlert("Error en la solicitud", {type: 3});
                            })
                        }else{
                            showAlert("Es necesario que se llenen los campos: Nombre", {type: 3});
                        }
                    }
                },
                filters: {}
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div id="contenidos">
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-10">
                        <h2>Catalogo de zonas</h2>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-success btn-lg" style="margin-top: 16px;" @click="showModalAlta()">Agregar zonas</button>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-striped">
                        <thead>
                        <th class="col-md-6">Id zonas</th>
                        <th class="col-md-6">Nombre de zonas</th>
                        </thead>
                        <tbody>
                        <tr v-for="zonase in zonasez" v-if="zonase.idZonas !=1000">
                            <td class="col-md-6">{{zonase.idZonas}}</td>
                            <td class="col-md-6">{{zonase.name}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal fade" id="modalAlta" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <div class="alert alert-success">
                            <h4 class="modal-title" id="" style="text-align: center"><label>Catalogo de zonas</label></h4>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-12">
                                        <label>Nombre de zonas</label>
                                        <input class="form-control" type="text" name="Nombre" onpaste="alert('Acceso Denegado');return false" v-model="zonase.name" onkeypress="return isLetterKey(event)">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-info" @click="saveZonase()">Guardar</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
