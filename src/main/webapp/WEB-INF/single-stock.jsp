<%--
  User: rafael
  Date: 10/02/16
  Time: 11:11 AM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Articulo de Inventario">

    <jsp:attribute name="scripts">
        <script type="application/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.fetchStock();
                    this.fetchDocumentTypes();
                    this.fetchValues();
                    this.fetchArticleStatus();
                    this.fetchHierarchy();
                },
                data: {
                    idStock: ${idStock},
                    article: null,
                    isSaving: false,
                    selectOptions: {
                        attributes: [],
                        values: [],
                        articleStatus: [],
                        hierarchy: [],
                        documentTypes: []
                    },
                    attachmentsModal: {
                        article: null,
                        fileInput: "file-type-"
                    },
                    editModal: {
                        selectAttr: null,
                        selectValue: null,
                        serialNumber: 0,
                        stockFolio: 0,
                        articleStatus: null,
                        purchasePrice: 0,
                        employees: null,
                        selectedEmployee: null,
                        article: null
                    },
                    assignmentsModal: {
                        selected: {
                            distributor: null,
                            region: null,
                            branch: null,
                            area: null
                        },
                        article: null
                    },
                    attachmentsDownloadUrl: ROOT_URL + "/stock/attachments/download/"
                },
                methods: {
                    fetchStock: function () {
                        this.$http.get(ROOT_URL + "/stock/" + this.idStock).success(function (data) {
                            this.article = data;
                            this.buildArticle(this.article);
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
                    fetchStockAssignmentsRecord: function (article) {
                        this.$http.get(
                                ROOT_URL + "/stock/" + article.idStock + "/assignments/record"
                        ).success(function(data) {
                            var jsonObjectIndex = {};
                            data.forEach(function (el) {
                                if (isNaN(el.dwEnterprises)) {
                                    jsonObjectIndex[el.dwEnterprises._id] = el.dwEnterprises;
                                } else {
                                    el.dwEnterprises = jsonObjectIndex[el.dwEnterprises];
                                }
                                if (isNaN(el.employee)) {
                                    jsonObjectIndex[el.employee._id] = el.employee;
                                } else {
                                    el.employee = jsonObjectIndex[el.employee];
                                }
                            });

                            Vue.set(article, "assignmentsRecord", data);
                            $("#assignmentsModal").modal("show");
                        }).error(function () {
                            showAlert("Permiso denegado", {type: 3});
                        });
                    },
                    fetchHierarchy: function () {
                        this.$http.get(ROOT_URL + "/dw-enterprises/hierarchy").success(function (data) {
                            this.selectOptions.hierarchy = data;
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
                    fetchArticleStatus: function () {
                        this.$http.get(ROOT_URL + "/stock-status").success(function (data) {
                            this.selectOptions.articleStatus = data;
                        });
                    },
                    fetchEmployees: function(idDw) {
                        this.$http.get(ROOT_URL + "/employees?idDwEnterprise=" + idDw).success(function (data) {
                            this.editModal.employees = data;
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
                        }).error(function (data) {
                            this.isSaving = false;
                            form.reset();
                            showAlert(data.error.message, {type:3})
                        });
                    },
                    distributorChanged: function () {
                        this.assignmentsModal.selected.region = null;
                        this.assignmentsModal.selected.branch = null;
                        this.assignmentsModal.selected.area = null;
                    },
                    regionChanged: function () {
                        this.assignmentsModal.selected.branch = null;
                        this.assignmentsModal.selected.area = null;
                    },
                    branchChanged: function () {
                        this.assignmentsModal.selected.area = null;
                    },
                    validateFile: function (event) {
                        if (! event.target.files[0].name.match(/(\.jpg|\.jpeg|\.pdf|\.png)$/i)) {
                            event.target.value = null;
                            showAlert("Tipo de archivo no admitido", {type:3});
                        }
                    },
                    showAttachmentsModal: function (article) {
                        this.attachmentsModal.article = article;
                        this.fetchStockDocumentsRecord(article);
                    },
                    showEditArticleModal: function (article) {
                        this.editModal.article = article;
                        this.editModal.serialNumber = article.serialNumber;
                        this.editModal.stockFolio = article.stockFolio;
                        this.editModal.articleStatus = article.articleStatus;
                        this.editModal.purchasePrice = article.purchasePrice;
                        this.editModal.selectedEmployee = article.stockEmployeeAssignmentsList[0].employee;
                        this.fetchAttributes(article.article.idArticle);
                        this.fetchEmployees(article.idDwEnterprises);
                        $("#editModal").modal("show");
                    },
                    showAssignmentsModal: function (article) {
                        this.assignmentsModal.article = article;
                        this.fetchStockAssignmentsRecord(article);
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
                        this.$http.post(ROOT_URL + "/stock/" + article.idStock, {
                            serialNumber: this.editModal.serialNumber,
                            stockFolio: this.editModal.stockFolio,
                            articleStatus: this.editModal.articleStatus,
                            purchasePrice: this.editModal.purchasePrice,
                            employee: {
                                idEmployee: this.editModal.selectedEmployee.idEmployee
                            }
                        }).success(function () {
                            article.serialNumber = this.editModal.serialNumber;
                            article.stockFolio = this.editModal.stockFolio;
                            article.articleStatus = this.editModal.articleStatus;
                            article.purchasePrice = this.editModal.purchasePrice;
                            article.stockEmployeeAssignmentsList[0].employee = this.editModal.selectedEmployee;
                            this.isSaving = false;
                            showAlert("Articulo modificado con exito", {type: 1});
                        }).error(function (data) {
                            this.isSaving = false;
                            showAlert(data.error.message, {type: 3});
                        });
                    },
                    saveStockAssignment: function (article) {
                        if (this.assignmentsModal.selected.area == null) {
                            showAlert("Distribudor, region, sucursal y area son requeridos", {type:3});
                            return;
                        }
                        this.isSaving = true;
                        this.$http.post(ROOT_URL + "/stock/" + article.idStock + "/assignments", {
                            idDwEnterprise: this.assignmentsModal.selected.area.dwEnterprise.idDwEnterprise
                        }).success(function () {
                            this.isSaving = false;
                            showAlert("Asignación exitosa");
                            this.fetchStock();
                            this.closeAssignmentsModal();
                        }).error(function (data) {
                            this.isSaving = false;
                            showAlert(data.error.message, {type:3});
                        })
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="col-lg-12"><h2 class="text-center">Inventario</h2></div>
            <div class="stock-groups col-xs-12">
                <div class="text-center col-xs-12"><h4>{{ article.dwEnterprises.area.areaName }}</h4></div>
                <div class="col-xs-12 panel-group">
                    <div @build="buildArticle(article)" class="lazy panel panel-default">
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
                                            <td>Estado</td>
                                            <td>{{ article.articleStatus.articleStatus }}</td>
                                        </tr>
                                        <tr>
                                            <td>Folio de inventario</td>
                                            <td>{{ article.stockFolio }}</td>
                                        </tr>
                                        <tr>
                                            <td>Solicitud</td>
                                            <td>{{ article.folio }}</td>
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
                                <div class="col-md-4 col-xs-6">
                                    <label>Artículo: </label>
                                    {{ editModal.article.article.articleName }}
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>No. de Serie: </label>
                                    {{ editModal.article.serialNumber }}
                                </div>
                                <div class="col-md-4 col-xs-12">
                                    <label>Asignado a: </label>
                                    {{ editModal.article.stockEmployeeAssignmentsList[0].employee.firstName }}
                                    {{ editModal.article.stockEmployeeAssignmentsList[0].employee.middleName }}
                                    {{ editModal.article.stockEmployeeAssignmentsList[0].employee.parentalLast }}
                                    {{ editModal.article.stockEmployeeAssignmentsList[0].employee.motherLast }}
                                </div>
                            </div>
                            <hr />
                            <div class="row line">
                                <div class="col-md-8 col-xs-12">
                                    <label>Asignar a:</label>
                                    <select v-model="editModal.selectedEmployee.idEmployee"
                                            :disabled="isSaving" class="form-control">
                                        <option v-for="employee in editModal.employees"
                                                :value="employee.idEmployee">
                                            {{ employee.firstName }}
                                            {{ employee.middleName }}
                                            {{ employee.parentalLast }}
                                            {{ employee.motherLast }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>No. de serie</label>
                                    <input v-model="editModal.serialNumber"
                                           :disabled="isSaving" type="text" class="form-control">
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>Folio de Inventario</label>
                                    <input v-model="editModal.stockFolio"
                                           :disabled="isSaving" type="text" class="form-control">
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>Estado de artículo</label>
                                    <select v-model="editModal.articleStatus"
                                            :disabled="isSaving" class="form-control">
                                        <option v-for="status in selectOptions.articleStatus"
                                                :value="status">{{ status.articleStatus }}</option>
                                    </select>
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>Precio de compra</label>
                                    <input v-model="editModal.purchasePrice"
                                           :disabled="isSaving" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="row line">
                                <div class="text-center line col-xs-12"><strong>Propiedades</strong></div>
                                <div class="col-xs-12 col-md-8 col-md-push-2">
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
                                <div class="col-xs-12 col-md-8 col-md-push-2">
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
                <div class="modal-dialog modal-lg" style="height: 90%;">
                    <div class="modal-content flex-box">
                        <div class="modal-header flex-row flex-header">
                            <button class="close" @click="closeAssignmentsModal"><span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Modificaciar asignacion</h4>
                        </div>
                        <div class="modal-body flex-box">
                            <div class="row flex-row flex-header">
                                <div class="col-md-4 col-xs-6">
                                    <label>Artículo: </label>
                                    {{ assignmentsModal.article.article.articleName }}
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>No. de Serie: </label>
                                    {{ assignmentsModal.article.serialNumber }}
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>Folio de inventario: </label>
                                    {{ assignmentsModal.article.stockFolio }}
                                </div>
                                <div class="col-xs-12"><label>Asignar a</label></div>
                                <div class="col-md-3 col-xs-6">
                                    <label>Distribuidor</label>
                                    <select v-model="assignmentsModal.selected.distributor" class="form-control"
                                            @change="distributorChanged">
                                        <option v-for="distributor in selectOptions.hierarchy[0].subLevels"
                                                :value="distributor">
                                            {{ distributor.name }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-3 col-xs-6">
                                    <label>Region</label>
                                    <select v-model="assignmentsModal.selected.region" class="form-control"
                                            @change="regionChanged" :disabled="assignmentsModal.selected.distributor == null">
                                        <option v-for="region in assignmentsModal.selected.distributor.subLevels"
                                                :value="region">
                                            {{ region.name }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-3 col-xs-6">
                                    <label>Sucursal</label>
                                    <select v-model="assignmentsModal.selected.branch" class="form-control"
                                            @change="branchChanged" :disabled="assignmentsModal.selected.region == null">
                                        <option v-for="branch in assignmentsModal.selected.region.subLevels"
                                                :value="branch">
                                            {{ branch.name }}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-md-3 col-xs-6">
                                    <label>Area</label>
                                    <select v-model="assignmentsModal.selected.area" class="form-control"
                                            @change="" :disabled="assignmentsModal.selected.branch == null">
                                        <option v-for="area in assignmentsModal.selected.branch.subLevels"
                                                :value="area">
                                            {{ area.name }}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="flex-row flex-content">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>Asignado a</th>
                                        <th>Distribuidor</th>
                                        <th>Region</th>
                                        <th>Sucursal</th>
                                        <th>Area</th>
                                        <th>Fecha de asignación</th>
                                    </tr>
                                    </thead>
                                    <tr class="success">
                                        <td>
                                            {{ assignmentsModal.article.stockEmployeeAssignmentsList[0].employee.firstName }}
                                            {{ assignmentsModal.article.stockEmployeeAssignmentsList[0].employee.middleName }}
                                            {{ assignmentsModal.article.stockEmployeeAssignmentsList[0].employee.parentalLast }}
                                            {{ assignmentsModal.article.stockEmployeeAssignmentsList[0].employee.motherLast }}
                                        </td>
                                        <td>{{ assignmentsModal.article.dwEnterprises.distributor.distributorName }}</td>
                                        <td>{{ assignmentsModal.article.dwEnterprises.region.regionName }}</td>
                                        <td>{{ assignmentsModal.article.dwEnterprises.branch.branchName }}</td>
                                        <td>{{ assignmentsModal.article.dwEnterprises.area.areaName }}</td>
                                        <td>
                                            {{ assignmentsModal.article.stockEmployeeAssignmentsList[0].assignmentDateFormats.dateNumber }}
                                            <span class="label label-success">Actual</span>
                                        </td>
                                    </tr>
                                    <tr class="text-center">
                                        <td colspan="6"><label>Historial de asignaciones</label></td>
                                    </tr>
                                    <tr v-for="assignment in assignmentsModal.article.assignmentsRecord">
                                        <td>
                                            {{ assignment.employee.firstName }}
                                            {{ assignment.employee.middleName }}
                                            {{ assignment.employee.parentalLast }}
                                            {{ assignment.employee.motherLast }}
                                        </td>
                                        <td>{{ assignment.dwEnterprises.distributor.distributorName }}</td>
                                        <td>{{ assignment.dwEnterprises.region.regionName }}</td>
                                        <td>{{ assignment.dwEnterprises.branch.branchName }}</td>
                                        <td>{{ assignment.dwEnterprises.area.areaName }}</td>
                                        <td>{{ assignment.assignmentDateFormats.dateNumber }}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="text-right modal-footer flex-row flex-footer">
                            <button :disabled="isSaving" class="btn btn-default" @click="closeAssignmentsModal">Cancelar</button>
                            <button @click="saveStockAssignment(assignmentsModal.article)"
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
