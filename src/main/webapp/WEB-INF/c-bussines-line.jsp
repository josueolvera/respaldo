<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Catalogo linea de negocio">

    <jsp:attribute name="styles">
        <style>
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

        <script type="text/javascript">
            var vm = new Vue({
                el: '#contenidos',
                created: function () {
                },
                ready: function () {
                    this.getBussinessLines();
                },
                data: {
                    bussinessLines: [],
                    bussinessLine: {
                        name: '',
                        acronym: ''
                    }
                },
                methods: {
                    getBussinessLines: function () {
                        this.$http.get(ROOT_URL + "/bussiness-line").success(function (data) {
                            this.bussinessLines = data;
                        })
                    },
                    showModalAlta: function () {
                        this.bussinessLine.name = '';
                        this.bussinessLine.acronym = '';
                        $("#modalAlta").modal("show");
                    },
                    saveBussinessLine: function () {
                        if(this.bussinessLine.name.length > 0 && this.bussinessLine.acronym.length > 0){
                            this.$http.post(ROOT_URL + "/bussiness-line/save", JSON.stringify(this.bussinessLine)).success(function (data) {
                                this.bussinessLines = [];
                                this.bussinessLines = data;
                                $("#modalAlta").modal("hide");
                                showAlert("Registro guardado con exito");
                            }).error(function () {
                                showAlert("Error en la solicitud", {type: 3});
                            })
                        }else{
                            showAlert("Es necesario que se llenen los campos: Nombre, Acronimo", {type: 3});
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
                        <h2>Catalogo de lineas de negocio</h2>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-info" style="margin-top: 15px" @click="showModalAlta()">Agregar linea de
                            negocio
                        </button>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-striped">
                        <thead>
                        <th class="col-md-4">id Linea de negocio</th>
                        <th class="col-md-4">Nombre</th>
                        <th class="col-md-4">Acronimo</th>
                        </thead>
                        <tbody>
                        <tr v-for="bussinessLine in bussinessLines">
                            <td class="col-md-4">{{bussinessLine.idBusinessLine}}</td>
                            <td class="col-md-4">{{bussinessLine.name}}</td>
                            <td class="col-md-4">{{bussinessLine.acronym}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal fade" id="modalAlta" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content modal-lg">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id=""><label>Catalogo de lineas de negocio</label></h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-6">
                                        <label>Nombre</label>
                                        <input class="form-control" type="text" v-model="bussinessLine.name" onkeypress="return isLetterKey(event)">
                                    </div>
                                    <div class="col-md-6">
                                        <label>Acronimo</label>
                                        <input class="form-control" type="text" v-model="bussinessLine.acronym" onkeypress="return isLetterKey(event)">
                                    </div>
                                </div>
                            </div>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="saveBussinessLine()">Guardar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
