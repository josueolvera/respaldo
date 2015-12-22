<%--
  User: rafael
  Date: 10/12/15
  Time: 03:59 PM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Inventario">

    <jsp:attribute name="scripts">
        <script>
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.fetchDistributors();
                },
                data: {
                    stock: {},
                    selectOptions: {
                        distributors: []
                    },
                    dist: {}
                },
                methods: {
                    fetchStock: function (distributor) {
                        this.$http.get(ROOT_URL + "/stock/" + distributor.idDistributor).success(function (data) {
                            this.stock = data;
                        });
                    },
                    fetchDistributors: function () {
                        this.$http.get(ROOT_URL + "/distributors").success(function (data) {
                            this.selectOptions.distributors = data;
                        });
                    }
                }
            });
        </script>
    </jsp:attribute>


    <jsp:body>
        <div id="content">
            <div class="col-lg-12"><h2 class="text-center">Inventario</h2></div>
            <div class="col-lg-12">
                <div class="form-group">
                    <label>Distribuidor</label>
                    <select v-model="dist" @change="fetchStock(dist)" class="form-control">
                        <option v-for="distributor in selectOptions.distributors"
                                v-bind:value="distributor">
                                {{ distributor.distributorName }}
                        </option>
                    </select>
                </div>
            </div>
            <div class="panel-group col-xs-12">
                <div v-for="article in stock" class="panel panel-default">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-4"><strong>Articulo</strong></div>
                            <div class="col-xs-4"><strong>No. de serie</strong></div>
                            <div class="col-xs-4"><strong>Fecha de ingreso</strong></div>
                        </div>
                        <div class="row">
                            <div class="col-xs-4">{{ article.article.articleName }}</div>
                            <div class="col-xs-4">{{ article.serialNumber }}</div>
                            <div class="col-xs-4">{{ article.creationDate.dayOfMonth }}/
                                {{ article.creationDate.monthValue }}/
                                {{ article.creationDate.year }}</div>
                        </div>
                    </div>
                    <div class="panel-collapse">
                        <div class="panel-body">
                            <div class="col-xs-8 col-lg-push-2">
                                <table class="table table-striped">
                                    <tr v-for="property in article.propertiesList">
                                        <td>{{ property.attributesArticles.attributes.attributeName }}</td>
                                        <td>{{ property.value }}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
