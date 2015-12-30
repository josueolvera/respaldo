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
                    this.fetchAreas();
                    this.fetchDocumentTypes();
                },
                data: {
                    stockGroups: {},
                    selectOptions: {
                        distributors: [],
                        areas: [],
                        documentTypes: []
                    },
                    selectedOptions: {
                        area: null,
                        distributor: null
                    },
                    attachmentsModal: {
                        article: null,
                        uploadUrl: ROOT_URL + "/stock/attachments/",
                        fileInput: "file-type-"
                    },
                    attachmentsDownloadUrl: ROOT_URL + "/stock/attachments/download/"
                },
                methods: {
                    groupBy: function (array, filter) {
                        var groups = {};
                        array.forEach(function (element) {
                            var group = JSON.stringify(filter(element));
                            groups[group] = groups[group] || [];
                            groups[group].push(element);
                        });
                        return Object.keys(groups).map(function (group) {
                            return groups[group];
                        });
                    },
                    fetchStock: function (distributor) {
                        this.$http.get(ROOT_URL + "/stock/" + distributor.idDistributor).success(function (data) {
                            this.stockGroups = this.groupBy(data, function (item) {
                                return item.idDwEnterprises;
                            });
                        });
                    },
                    fetchDistributors: function () {
                        this.$http.get(ROOT_URL + "/distributors?forStock=true").success(function (data) {
                            this.selectOptions.distributors = data;
                        });
                    },
                    fetchAreas: function () {
                        this.$http.get(ROOT_URL + "/areas").success(function (data) {
                            this.selectOptions.areas = data;
                        });
                    },
                    fetchDocumentTypes: function () {
                        this.$http.get(ROOT_URL + "/document-types").success(function (data) {
                            this.selectOptions.documentTypes = data;
                        });
                    },
                    areaFilter: function (item) {
                        if (this.selectedOptions.area == null || this.selectedOptions.area == 0) {
                            return item;
                        }
                        if (item[0].dwEnterprises.idarea == this.selectedOptions.area.idArea) {
                            return item;
                        }
                    },
                    showAttachmentsModal: function (article) {
                        this.attachmentsModal.article = article;
                        console.log(article);
                        $("#attachmentsModal").modal("show");
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            .stock-groups {
                margin-top: 2rem;
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="col-lg-12"><h2 class="text-center">Inventario</h2></div>
            <div class="col-lg-12">
                <div class="col-lg-3">
                    <label>Distribuidor</label>
                    <select v-model="selectedOptions.distributor" class="form-control"
                            @change="fetchStock(selectedOptions.distributor)">
                        <option v-for="distributor in selectOptions.distributors"
                                :value="distributor">
                                {{ distributor.distributorName }}
                        </option>
                    </select>
                </div>
                <div class="col-lg-3">
                    <label>Area</label>
                    <select v-model="selectedOptions.area" class="form-control"
                            :disabled="selectOptions.areas.length < 2">
                        <option value="0" selected>Todas</option>
                        <option v-for="area in selectOptions.areas"
                                :value="area">
                                {{ area.areaName }}
                        </option>
                    </select>
                </div>
                <div class="col-lg-3">
                    <label>Region</label>
                    <select class="form-control"></select>
                </div>
                <div class="col-lg-3">
                    <label>Sucursal</label>
                    <select class="form-control"></select>
                </div>
            </div>
            <div class="stock-groups col-xs-12">
                <div v-for="stock in stockGroups | filterBy areaFilter" class="">
                    <div class="text-center col-xs-12"><h4>{{ stock[0].dwEnterprises.idArea.areaName }}</h4></div>
                    <div class="col-xs-12 panel-group">
                        <div v-for="article in stock" class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="text-center col-xs-10">
                                        <div class="col-xs-3"><strong>Artículo</strong></div>
                                        <div class="col-xs-3"><strong>No. de serie</strong></div>
                                        <div class="col-xs-3"><strong>Fecha de ingreso</strong></div>
                                        <div class="col-xs-3"><strong>Asignado a</strong></div>
                                    </div>
                                    <div class="col-xs-2"><strong></strong></div>
                                </div>
                                <div class="row">
                                    <div class="text-center col-xs-10">
                                        <div class="col-xs-3">{{ article.article.articleName }}</div>
                                        <div class="col-xs-3">{{ article.serialNumber }}</div>
                                        <div class="col-xs-3">
                                            {{ article.creationDateFormats.dateNumber }}
                                        </div>
                                        <div class="col-xs-3">{{ article.idEmployee }}</div>
                                    </div>
                                    <div class="text-right col-xs-2">
                                        <button @click="showAttachmentsModal(article)" class="btn btn-default">
                                            <span class="glyphicon glyphicon-paperclip"></span>
                                        </button>
                                        <button @click="" class="btn btn-default">
                                            <span class="glyphicon glyphicon-pencil"></span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <%-- Panel Interno --%>
                            <div class="panel-collapse">
                                <div class="panel-body">
                                    <%-- Atributos --%>
                                    <div class="col-xs-6">
                                        <table class="table table-striped">
                                            <tr v-for="property in article.propertiesList">
                                                <td>{{ property.attributesArticles.attributes.attributeName }}</td>
                                                <td>{{ property.value }}</td>
                                            </tr>
                                        </table>
                                    </div>
                                    <%-- Documentos Adjuntos --%>
                                    <div class="col-xs-6">
                                        <table class="table table-striped">
                                            <tr v-for="document in article.stockDocumentsList">
                                                <td>{{ document.documentType.documentName }}</td>
                                                <td>
                                                    <a :href="attachmentsDownloadUrl + document.idStockDocument">
                                                        {{ document.documentName }}
                                                    </a>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%-- Modal para carga de archivos adjuntos --%>
            <div id="attachmentsModal" class="modal fade">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Documentos adjuntos</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>Artículo: </label>
                                    {{ attachmentsModal.article.article.articleName }}
                                </div>
                                <div class="col-xs-4">
                                    <label>No. de Serie: </label>
                                    {{ attachmentsModal.article.serialNumber }}
                                </div>
                                <div class="col-xs-4">
                                    <label>Asignado a: </label>
                                    {{ attachmentsModal.article.idEmployee }}
                                </div>
                            </div>
                            <form :action="attachmentsModal.uploadUrl + attachmentsModal.article.idStock"
                                  method="post" enctype="multipart/form-data">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Documento</th>
                                            <th>Documento actual</th>
                                            <th>Nuevo documento</th>
                                        </tr>
                                    </thead>
                                    <tr v-for="docType in selectOptions.documentTypes">
                                        <td>{{ docType.documentName }}</td>
                                        <td>
                                            <a v-if="attachmentsModal.article.stockDocumentsList[$index]"
                                               :href="attachmentsDownloadUrl + attachmentsModal.article.stockDocumentsList[$index].idStockDocument">
                                                {{ attachmentsModal.article.stockDocumentsList[$index].documentName }}
                                            </a>
                                        </td>
                                        <td>
                                            <input type="file" class="form-control"
                                                   :name="attachmentsModal.fileInput + docType.idDocumentType">
                                        </td>
                                    </tr>
                                </table>
                                <hr />
                                <div class="text-right">
                                    <button class="btn btn-default" data-dismiss="modal">Cancelar</button>
                                    <input type="submit" value="Guardar" class="btn btn-success">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
