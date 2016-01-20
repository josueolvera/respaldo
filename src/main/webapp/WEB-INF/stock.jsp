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
                    this.fetchValues();
                },
                data: {
                    isSaving: false,
                    stockGroups: {},
                    selectOptions: {
                        distributors: [],
                        areas: [],
                        attributes: [],
                        values: [],
                        documentTypes: []
                    },
                    selectedOptions: {
                        area: null,
                        distributor: null
                    },
                    attachmentsModal: {
                        article: null,
                        fileInput: "file-type-"
                    },
                    editModal: {
                        selectAttr: null,
                        selectValue: null,
                        article: null
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
                    attachOnScreen: function () {
                        $('.lazy').onScreen({
                            container: window,
                            direction: 'vertical',
                            doIn: function() {
                                this.dispatchEvent(new Event('build'));
                            },
                            tolerance: 0,
                            throttle: 50,
                            debug: true
                        });
                    },
                    fetchStock: function (distributor) {
                        this.$http.get(ROOT_URL + "/stock/" + distributor.idDistributor).success(function (data) {
                            this.stockGroups = this.groupBy(data, function (item) {
                                return item.idDwEnterprises;
                            });
                            this.$nextTick(function () {
                                this.attachOnScreen();
                            });
                        });
                    },
                    fetchStockProperties: function (article) {
                        this.$http.get(ROOT_URL + "/stock/" + article.idStock + "/properties").success(function (data) {
                            article.propertiesList = data;
                        });
                    },
                    fetchStockDocuments: function (article) {
                        this.$http.get(ROOT_URL + "/stock/" + article.idStock + "/attachments").success(function (data) {
                            article.stockDocumentsList = data;
                            this.isSaving = false;
                        });
                    },
                    fetchStockDocumentsRecord: function (article) {
                        this.$http.get(
                                ROOT_URL + "/stock/" + article.idStock + "/attachments/record"
                        ).success(function (data) {
                            Vue.set(article, "documentsRecord", data);
                            $("#attachmentsModal").modal("show");
                            this.isSaving = false;
                        }).error(function () {
                            showAlert("Permiso denegado", {type: 3});
                        });
                    },
                    fetchStockAssignments: function (article) {
                        this.$http.get(ROOT_URL + "/stock/" + article.idStock + "/assignments").success(function(data) {
                            article.stockEmployeeAssignmentsList = data;
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
                    fetchAttributes: function (idArticle) {
                        this.$http.get(ROOT_URL + "/attributes/" + idArticle).success(function (data) {
                            this.selectOptions.attributes = data;
                            this.editModal.selectAttr = $('#select-attribute').selectize({
                                maxItems: 1,
                                valueField: 'idAttribute',
                                labelField: 'attributeName',
                                searchField: 'attributeName',
                                options: data,
                                create: false
                            });
                        });
                    },
                    fetchValues: function () {
                        this.$http.get(ROOT_URL + "/values").success(function (data) {
                            var self = this;
                            this.selectOptions.values = data;
                            this.editModal.selectValue = $('#select-value').selectize({
                                maxItems: 1,
                                valueField: 'idValue',
                                labelField: 'value',
                                searchField: 'value',
                                options: data,
                                create: function (input, callback) {
                                    self.$http.post(ROOT_URL + "/values", {
                                        value: input
                                    }).success(function (data){
                                        callback(data);
                                    }).error(function (){
                                        callback();
                                    });
                                },
                                render: {
                                    option_create: function(data, escape) {
                                        return '<div data-selectable class="create">' +
                                                'Agregar <strong>' + escape(data.input) + '</strong>' +
                                                '</div>'
                                    }
                                }
                            });
                        });
                    },
                    buildArticle: function (article) {
                        if (article.propertiesList == null) {
                            this.fetchStockProperties(article);
                        }
                        if (article.stockDocumentsList == null) {
                            this.fetchStockDocuments(article);
                        }
                        if (article.stockEmployeeAssignmentsList == null) {
                            this.fetchStockAssignments(article);
                        }
                    },
                    removeProperty: function (article, property) {
                        this.isSaving = true;
                        this.$http.delete(ROOT_URL + "/stock/properties/" + property.idProperty).success(function () {
                            article.propertiesList.$remove(property);
                            this.isSaving = false;
                        }).error(function () {
                            this.isSaving = false;
                            showAlert("Ha habido un problema con su solicitud, intente nuevamente", {type:3});
                        });
                    },
                    addProperty: function (article) {
                        this.isSaving = true;

                        var idAttr = this.editModal.selectAttr[0].selectize.getValue();
                        var idVal = this.editModal.selectValue[0].selectize.getValue();
                        var property = {
                            value: {
                                idValue: idVal,
                                value: this.editModal.selectValue[0].selectize.getOption(idVal).text()
                            },
                            attributesArticles: {
                                idArticle: article.idArticle,
                                idAttribute: idAttr,
                                attributes: {
                                    idAttribute: idAttr,
                                    attributeName: this.editModal.selectAttr[0].selectize.getOption(idAttr).text()
                                }
                            }
                        };

                        this.$http.post(ROOT_URL + "/stock/" + article.idStock + "/properties", property).success(function () {
                            this.editModal.selectAttr[0].selectize.clear();
                            this.editModal.selectValue[0].selectize.clear();
                            this.fetchStockProperties(article);
                            this.isSaving = false;
                        }).error(function () {
                            this.isSaving = false;
                            showAlert("Ha habido un problema con su solicitud, intente nuevamente", {type:3})
                        });
                    },
                    uploadAttachments: function (article) {
                        this.isSaving = true;
                        var form = document.getElementById("attachments-form");
                        this.$http.post(ROOT_URL + "/stock/" + article.idStock + "/attachments", new FormData(form)).success(function () {
                            showAlert("Registro exitoso");
                            form.reset();
                            this.fetchStockDocuments(article);
                            this.fetchStockDocumentsRecord(article);
                        }).error(function () {
                            this.isSaving = false;
                            form.reset();
                            showAlert("Ha habido un problema con su solicitud, intente nuevamente", {type:3})
                        });
                    },
                    validateFile: function (event) {
                        if (! event.target.files[0].name.match(/(\.jpg|\.jpeg|\.pdf|\.png)$/i)) {
                            event.target.value = null;
                            showAlert("Tipo de archivo no admitido", {type:3});
                        }
                    },
                    areaFilter: function (item) {
                        if (this.selectedOptions.area == null || this.selectedOptions.area == 0) {
                            return item;
                        }
                        if (item[0].dwEnterprises.idArea == this.selectedOptions.area.idArea) {
                            return item;
                        }
                    },
                    showAttachmentsModal: function (article) {
                        this.attachmentsModal.article = article;
                        this.fetchStockDocumentsRecord(article);
                    },
                    showEditArticleModal: function (article) {
                        this.editModal.article = article;
                        this.fetchAttributes(article.article.idArticle);
                        $("#editModal").modal("show");
                    },
                    showAssignmentsModal: function (article) {
                        $("#assignmentsModal").modal("show");
                    },
                    closeEditModal: function () {
                        this.editModal.selectAttr[0].selectize.destroy();
                        $("#editModal").modal("hide");
                    },
                    closeAttachmentsModal: function () {
                        document.getElementById("attachments-form").reset();
                        $("#attachmentsModal").modal("hide");
                    },
                    closeAssignmentsModal: function () {
                        $("#assignmentsModal").modal("hide");
                    },
                    saveStockArticle: function (article) {
                        this.isSaving = true;
                        console.log(article);
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
            .line {
                margin-bottom: 2rem;
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="col-lg-12"><h2 class="text-center">Inventario</h2></div>
            <div class="col-lg-12">
                <div class="col-md-3 col-xs-6">
                    <label>Distribuidor</label>
                    <select v-model="selectedOptions.distributor" class="form-control"
                            @change="fetchStock(selectedOptions.distributor)">
                        <option v-for="distributor in selectOptions.distributors"
                                :value="distributor">
                                {{ distributor.distributorName }}
                        </option>
                    </select>
                </div>
                <div class="col-md-3 col-xs-6">
                    <label>Area</label>
                    <select v-model="selectedOptions.area" class="form-control"
                            @change="attachOnScreen"
                            :disabled="selectOptions.areas.length < 2">
                        <option value="0" selected>Todas</option>
                        <option v-for="area in selectOptions.areas"
                                :value="area">
                                {{ area.areaName }}
                        </option>
                    </select>
                </div>
                <div class="col-md-3 col-xs-6">
                    <label>Region</label>
                    <select class="form-control"></select>
                </div>
                <div class="col-md-3 col-xs-6">
                    <label>Sucursal</label>
                    <select class="form-control"></select>
                </div>
            </div>
            <div class="stock-groups col-xs-12">
                <div v-for="stock in stockGroups | filterBy areaFilter" class="">
                    <div class="text-center col-xs-12"><h4>{{ stock[0].dwEnterprises.area.areaName }}</h4></div>
                    <div class="col-xs-12 panel-group">
                        <div v-for="article in stock" @build="buildArticle(article)" data-loader="stockElementsLoader"
                             class="lazy panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="text-center col-xs-10">
                                        <div class="col-md-3 col-xs-6">
                                            <p><strong>Artículo</strong></p>
                                            <p>{{ article.article.articleName }}</p>
                                        </div>
                                        <div class="col-md-3 col-xs-6">
                                            <p><strong>No. de serie</strong></p>
                                            <p>{{ article.serialNumber }}</p>
                                        </div>
                                        <div class="col-md-3 col-xs-6">
                                            <p><strong>Fecha de ingreso</strong></p>
                                            <p>{{ article.creationDateFormats.dateNumber }}</p>
                                        </div>
                                        <div class="col-md-3 col-xs-6">
                                            <p><strong>Asignado a</strong></p>
                                            <p>
                                                {{ article.stockEmployeeAssignmentsList[0].employee.firstName }}
                                                {{ article.stockEmployeeAssignmentsList[0].employee.middleName }}
                                                {{ article.stockEmployeeAssignmentsList[0].employee.parentalLast }}
                                                {{ article.stockEmployeeAssignmentsList[0].employee.motherLast }}
                                            </p>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <button @click="showEditArticleModal(article)" class="btn btn-default">
                                            <span class="glyphicon glyphicon-pencil"></span>
                                        </button>
                                        <button @click="showAttachmentsModal(article)" class="btn btn-default">
                                            <span class="glyphicon glyphicon-paperclip"></span>
                                        </button>
                                        <button @click="showAssignmentsModal(article)" class="btn btn-default">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <%-- Panel Interno --%>
                            <div class="panel-collapse">
                                <div class="panel-body">
                                <%-- Atributos --%>
                                    <div v-if="article.propertiesList == null" class="col-xs-12"
                                         style="height: 6rem; padding: 2rem 0;">
                                        <div class="loader">Cargando...</div>
                                    </div>
                                    <div v-if="article.propertiesList != null" class="col-md-6 col-xs-12">
                                        <h5 class="text-center">Propiedades</h5>
                                        <table class="table table-striped">
                                            <tr>
                                                <td>Folio de inventario</td>
                                                <td>{{ article.stockFolio }}</td>
                                            </tr>
                                            <tr v-for="property in article.propertiesList">
                                                <td class="col-xs-6">
                                                    {{ property.attributesArticles.attributes.attributeName }}
                                                </td>
                                                <td class="col-xs-6">{{ property.value.value }}</td>
                                            </tr>
                                        </table>
                                    </div>
                                    <%-- Documentos Adjuntos --%>
                                    <div v-if="article.propertiesList != null" class="col-md-6 col-xs-12">
                                        <h5 class="text-center">Documentos</h5>
                                        <table class="table table-striped">
                                            <tr v-for="document in article.stockDocumentsList">
                                                <td class="col-xs-6">{{ document.documentType.documentName }}</td>
                                                <td class="col-xs-6">
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
            <div id="attachmentsModal" class="modal fade" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click.prevent="closeAttachmentsModal" class="close"><span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Documentos adjuntos</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-4 col-xs-6">
                                    <label>Artículo: </label>
                                    {{ attachmentsModal.article.article.articleName }}
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>No. de Serie: </label>
                                    {{ attachmentsModal.article.serialNumber }}
                                </div>
                                <div class="col-md-4 col-xs-12">
                                    <label>Asignado a: </label>
                                    {{ attachmentsModal.article.stockEmployeeAssignmentsList[0].employee.firstName }}
                                    {{ attachmentsModal.article.stockEmployeeAssignmentsList[0].employee.middleName }}
                                    {{ attachmentsModal.article.stockEmployeeAssignmentsList[0].employee.parentalLast }}
                                    {{ attachmentsModal.article.stockEmployeeAssignmentsList[0].employee.motherLast }}
                                </div>
                            </div>
                            <hr>
                            <form id="attachments-form"
                                  method="post" enctype="multipart/form-data">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Documento</th>
                                            <th>Documento actual</th>
                                            <th>Fecha de envío</th>
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
                                            {{ attachmentsModal.article.stockDocumentsList[$index].uploadingDateFormats.dateNumber }},
                                            {{ attachmentsModal.article.stockDocumentsList[$index].uploadingDateFormats.time12 }}
                                        </td>
                                        <td>
                                            <input @change="validateFile($event)" type="file" class="form-control"
                                                   :disabled="isSaving"
                                                   :name="attachmentsModal.fileInput + docType.idDocumentType">
                                        </td>
                                    </tr>
                                </table>
                                <div v-if="isSaving" class="progress">
                                    <div class="progress-bar progress-bar-striped active" style="width: 100%"></div>
                                </div>
                                <hr />
                                <div class="text-right">
                                    <button @click.prevent="closeAttachmentsModal" :disabled="isSaving" class="btn btn-default">Cancelar</button>
                                    <button @click.prevent="uploadAttachments(attachmentsModal.article)"
                                            :disabled="isSaving"
                                            class="btn btn-success">
                                        Guardar
                                    </button>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <h4 class="text-left">Historial de Documentos</h4>
                            <table class="table table-striped text-left">
                                <thead>
                                    <tr>
                                        <th>Documento</th>
                                        <th>Archivo</th>
                                        <th>Fecha de envío</th>
                                    </tr>
                                </thead>
                                <tr v-for="document in attachmentsModal.article.documentsRecord"
                                    v-if="document.currentDocument == 0">
                                    <td>{{ document.documentType.documentName }}</td>
                                    <td>
                                        <a :href="attachmentsDownloadUrl + document.idStockDocument">
                                            {{ document.documentName }}
                                        </a>
                                    </td>
                                    <td>
                                        {{ document.uploadingDateFormats.dateNumber }},
                                        {{ document.uploadingDateFormats.time12 }}
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <%-- Modal de modificacion de articulo de inventario --%>
            <div id="editModal" class="modal fade" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button class="close" @click="closeEditModal()"><span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Modificaciar articulo</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>Artículo: </label>
                                    {{ editModal.article.article.articleName }}
                                </div>
                                <div class="col-xs-4">
                                    <label>No. de Serie: </label>
                                    {{ editModal.article.serialNumber }}
                                </div>
                                <div class="col-xs-4">
                                    <label>Asignado a: </label>
                                    {{ editModal.article.idEmployee }}
                                </div>
                            </div>
                            <hr />
                            <div class="row line">
                                <div class="col-xs-4">
                                    <label>No. de serie</label>
                                    <input :disabled="isSaving" type="text" class="form-control">
                                </div>
                                <div class="col-xs-4">
                                    <label>Folio de Inventario</label>
                                    <input :disabled="isSaving" type="text" class="form-control">
                                </div>
                                <div class="col-xs-4">
                                    <label>Estado de artículo</label>
                                    <select :disabled="isSaving" class="form-control">
                                        <option value="1">Activo</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row line">
                                <div class="text-center line col-xs-12"><strong>Propiedades</strong></div>
                                <div class="col-xs-8 col-xs-push-2">
                                    <table class="table table-striped line">
                                        <tr v-for="property in editModal.article.propertiesList">
                                            <td class="col-xs-5">
                                                {{ property.attributesArticles.attributes.attributeName }}
                                            </td>
                                            <td class="col-xs-5">{{ property.value.value }}</td>
                                            <td class="col-xs-2">
                                                <button @click="removeProperty(editModal.article, property)"
                                                        :disabled="isSaving"
                                                        class="btn btn-default">
                                                    <span class="glyphicon glyphicon-remove"></span>
                                                </button>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-8 col-xs-push-2">
                                    <div class="col-xs-5">
                                        <label>Atributo</label>
                                        <select id="select-attribute" class="form-control"></select>
                                    </div>
                                    <div class="col-xs-5">
                                        <label>Valor</label>
                                        <select id="select-value" class="form-control"></select>
                                    </div>
                                    <div class="col-xs-2">
                                        <button @click="addProperty(editModal.article)"
                                                :disabled="isSaving"
                                                class="btn btn-default" style="margin-top: 2.5rem">
                                            <span class="glyphicon glyphicon-plus"></span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="text-right modal-footer">
                            <button :disabled="isSaving" class="btn btn-default" @click="closeEditModal">Cancelar</button>
                            <button @click="saveStockArticle(editModal.article)"
                                    :disabled="isSaving"
                                    class="btn btn-success">
                                Guardar
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <%-- Modal de asignaciones --%>
            <div id="assignmentsModal" class="modal fade" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button class="close" @click="closeAssignmentsModal"><span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Modificaciar asignacion</h4>
                        </div>
                        <div class="modal-body">
                        </div>
                        <div class="text-right modal-footer">
                            <button :disabled="isSaving" class="btn btn-default" @click="closeAssignmentsModal">Cancelar</button>
                            <button @click=""
                                    :disabled="isSaving"
                                    class="btn btn-success">
                                Guardar
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
