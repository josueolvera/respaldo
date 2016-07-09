<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Text Querys">

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
                    this.sqlQueriesAvailables();
                },
                data: {
                    sqlQueries: [],
                    sqlQuery:{
                    },
                    idQuery: '',
                },
                methods: {
                    sqlQueriesAvailables: function () {
                        this.$http.get(ROOT_URL + "/sql-queries/querys").success(function (data) {
                            this.sqlQueries = data;
                        });
                    },
                    buildReport: function () {
                        this.$http.get(ROOT_URL + "/sql-queries/" + this.idQuery).success(function (data) {
                            this.sqlQuery = data;
                            window.location= ROOT_URL+"/sql-queries/"+data.idQuery+"/build?file_name=reporte";
                        }).error(function () {
                            showAlert("Error al generar el archivo", {type: 3})
                        })

                    }
                },
                filters: {

                }
            });


        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos">
        <br>
        <div>
            <div class="row">
                <div class="col-xs-4"></div>
                <div class="col-xs-7">
                    <h2>Generador de reportes</h2>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-xs-4"></div>
                    <div class="col-xs-3">
                        <label>Calcular</label>
                        <select class="form-control" name="" v-model="idQuery">
                            <option></option>
                            <option v-for="query in sqlQueries"
                                    value="{{query.idQuery}}">
                                {{query.queryName}}
                            </option>
                        </select>
                    </div>
                    <div class="col-xs-2">
                        <button type="button" class="btn btn-success" data-placement="bottom" style="margin-top: 25px"
                                @click="buildReport">
                            Generar
                        </button>
                    </div>
                <div class="col-xs-3"></div>

            </div>
        <!-- container-fluid -->

        </div>
        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
