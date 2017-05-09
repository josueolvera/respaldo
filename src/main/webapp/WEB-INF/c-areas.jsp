<%--
  User: lEONARDO
  Date: 28/04/17
  Time: 09:10 AM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Catalogo de Areas">

    <jsp:attribute name="styles">
        <style>
            .table-striped th {
                background: firebrick;
                color: black;
            }
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
            }
            .table-body .table-row:nth-child(2n+1) {
                background: #ddd;
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
        <script>
            function validar(e){
                return!(e.keycode==86&&e.ctrlKey)
            }
        </script>

        <script type="text/javascript">
            var vm = new Vue({
                el: '#contenidos',
                created: function () {
                },
                ready: function () {
                    this.getareasas();
                },
                data: {
                    areasas: [],
                    areasa: {
                        name: ''
                    }
                },
                methods: {
                    getareasas: function () {
                        this.$http.get(ROOT_URL + "/areas").success(function (data) {
                            this.areasas = data;
                        })
                    },
                    showModalAlta: function () {
                        this.areasa.name = '';
                        $("#modalAlta").modal("show");
                    },
                    saveareasa: function () {
                        if(this.areasa.name.length > 0){
                            this.$http.post(ROOT_URL + "/areas/save-areas", JSON.stringify(this.areasa)).success(function (data) {
                                this.areasas = [];
                                this.areasas = data;
                                $("#modalAlta").modal("hide");
                                showAlert("Registro guardado con exito");
                            }).error(function () {
                                showAlert("Error en la solicitud", {type: 3});
                            })
                        }else{
                            showAlert("Es necesario que se llenen el campos: Nombre,", {type: 3});
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
                        <h2>Catalogo de Areas</h2>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-success" style="margin-top: 15px" @click="showModalAlta()">Agregar Area
                        </button>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-striped">
                        <thead>
                        <th class="col-md-6">Id de areas</th>
                        <th class="col-md-6">Nombre de areas</th>
                        </thead>
                        <tbody>
                        <tr v-for="areasa in areasas">
                            <td class="col-md-6">{{areasa.idArea}}</td>
                            <td class="col-md-6">{{areasa.areaName}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal fade" id="modalAlta" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content modal-sm">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <div class="alert alert-info">
                            <h4 class="modal-title" style="text-align: center" id=""><label>Catalogo de Areas</label></h4>
                        </div>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-12">
                                        <label>Nombre de Areas</label>
                                        <input class="form-control" type="text" name="Nombre de area" onpaste="alert('Acceso denegado');return false" v-model="areasa.name" onkeypress="return isLetterKey(event)">
                                    </div>
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-info" @click="saveareasa()">Guardar</button>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>