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
                    this.fetchArticleStatus();
                    this.fetchHierarchy();
                    this.getArticles();
                },
                data: {
                    assign:false,
                    isSaving: false,
                    stockGroups: {},
                    selectOptions: {
                        distributors: [],
                        areas: [],
                        attributes: [],
                        values: [],
                        articleStatus:[],
                        employees: [],
                        hierarchy: [],
                        documentTypes: [],
                        documentTypesRequired: [],
                        documentTypesNoRequired: []
                    },
                    selectedOptions: {
                        area: null,
                        distributor: null
                    },
                    historicalModal: {
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
                    newArticleModal: {
                        selectAttr: null,
                        selectValue: null,
                        serialNumber: null,
                        stockFolio: null,
                        articleStatus: null,
                        idArticleStatus:null,
                        purchasePrice: null,
                        purchaseDate:null,
                        invoiceNumber:null,
                        employees: null,
                        selectedEmployee: null,
                        idArticle:'',
                        properties : [],
                        articles: [],
                        selected: {
                            distributor: null,
                            region: null,
                            branch: null,
                            area: null,
                            dwEmployees:null
                        },
                        fileInput: "file-type-"
                    },
                    attachmentsModal: {
                        article: null,
                        fileInput: "file-type-"
                    },
                    assignmentsModal: {
                        selected: {
                            distributor: null,
                            region: null,
                            branch: null,
                            area: null,
                            dwEmployees:null
                        },
                        fileInput: "file-type-",
                        article: null
                    },
                    attachmentsDownloadUrl: ROOT_URL + "/stock/attachments/download/",
                    selectedInvoiceNumber:false,
                    invoiceNumber:'',
                    datetimepickerPurchaseDate:''
                },
                methods: {
                    setUpTimePickerPurchaseDate:function () {
                        this.datetimepickerPurchaseDate = $('#datetimepickerPurchaseDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: moment().add(1, 'minutes')
                        }).data();
                    },
                    validateNewArticleForm: function () {

                    },
                    doNotSeeAssignView: function () {
                        this.assign = false;
                    },
                    seeAssignView: function () {
                        this.assign = true;
                    },
                    getArticles : function () {
                        this.$http.get(ROOT_URL + "/articles").success(function (data) {
                            this.newArticleModal.articles = data;
                        });
                    },
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
                        this.$nextTick(function () {
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
                        });
                    },
                    fetchStock: function (distributor) {
                        this.$http.get(ROOT_URL + "/stock?idDistributor=" + distributor.idDistributor).success(function (data) {
                            this.stockGroups = this.groupBy(data, function (item) {
                                return item.idDwEnterprises;
                            });
                            this.attachOnScreen();
                        });
                    },
                    fetchStockProperties: function (article) {
                        this.$http.get(ROOT_URL + "/stock/" + article.idStock + "/properties").success(function (data) {
                            article.propertiesList = data;
                        });
                    },
                    fetchStockPropertiesNewArticle: function (article,property) {
                        article.properties.push(property);
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
                        }).error(function () {
                            showAlert("Permiso denegado", {type: 3});
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
                    fetchHierarchy: function () {
                        this.$http.get(ROOT_URL + "/dw-enterprises/hierarchy").success(function (data) {
                            this.selectOptions.hierarchy = data;
                        });
                    },
                    fetchDocumentTypes: function () {
                        this.$http.get(ROOT_URL + "/stock-document-types").success(function (data) {
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
                    fetchAttributesNewArticle: function () {
                        this.$http.get(ROOT_URL + "/attributes/" + this.newArticleModal.idArticle).success(function (data) {
                            this.selectOptions.attributes = data;
                            this.newArticleModal.selectAttr = $('#select-attribute2').selectize({
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
                    fetchAllEmployees: function() {
                        this.$http.get(ROOT_URL + "/employees").success(function (data) {
                            this.selectOptions.employees = data;
                        });
                    },
                    fetchValues: function () {
                        this.$http.get(ROOT_URL + "/values").success(function (data) {
                            var self = this;
                            this.selectOptions.values = data;
                            this.newArticleModal.selectValue = $('#select-value2').selectize({
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
                            showAlert("No se pudo elimnar la propiedad, intente nuevamente", {type:3});
                        });
                    },
                    removePropertyNewArticle: function (property) {
                        this.newArticleModal.properties.$remove(property)
                    },
                    addProperties: function (idArticle,properties) {
                        this.$http.post
                        (
                                ROOT_URL + "/stock/" + idArticle + "/propertiesList",
                                properties
                        ).success(function (data) {
                            showAlert("Articulo guardado")
                            this.closeNewArticleModal();
                            this.isSaving = false;
                        }).error(function (data) {
                            this.isSaving = false;
                            showAlert("No se pudo guardar el articulo", {type:3})
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
                            showAlert("Propiedad agregada")
                            this.fetchStockProperties(article);
                            this.isSaving = false;
                        }).error(function () {
                            this.isSaving = false;
                            showAlert("No se pudo agregar la propiedad, intente nuevamente", {type:3})
                        });
                    },
                    addPropertyNewArticle: function () {
                        var idAttr = this.newArticleModal.selectAttr[0].selectize.getValue();
                        var idVal = this.newArticleModal.selectValue[0].selectize.getValue();
                        var property = {
                            value: {
                                idValue: idVal,
                                value: this.newArticleModal.selectValue[0].selectize.getOption(idVal).text()
                            },
                            attributesArticles: {
                                idArticle: this.newArticleModal.idArticle,
                                idAttribute: idAttr,
                                attributes: {
                                    idAttribute: idAttr,
                                    attributeName: this.newArticleModal.selectAttr[0].selectize.getOption(idAttr).text()
                                }
                            }
                        };
                        this.newArticleModal.properties.push(property);
                    },
                    uploadFilesAssignments: function (article) {
                        if (this.assignmentsModal.selected.dwEmployees == null) {
                            showAlert("Distribudor, region, sucursal y area son requeridos", {type:3});
                            return;
                        }
                        this.isSaving = true;
                        var form = document.getElementById('attachments-form');
                        var formData = new FormData(form);
                        this.$http.post(
                                ROOT_URL + "/stock/" + article.idStock + "/attachments",
                                formData
                        ).success(function (data)
                        {
                            this.saveStockAssignment(article);
                            form.reset();
                        }).error(function (data) {
                            this.isSaving = false;
                            form.reset();
                            showAlert(data.error.message, {type:3})
                        });

                    },
                    getFormData:function (formId) {
                        var form = document.getElementById(formId);
                        var formData = new FormData(form);
                        return formData;
                    },
                    distributorChanged: function () {
                        this.assignmentsModal.selected.region = null;
                        this.assignmentsModal.selected.branch = null;
                        this.assignmentsModal.selected.area = null;
                        this.assignmentsModal.selected.dwEmployees = null;

                        this.newArticleModal.selected.region = null;
                        this.newArticleModal.selected.branch = null;
                        this.newArticleModal.selected.area = null;
                        this.newArticleModal.selected.dwEmployees = null;
                    },
                    regionChanged: function () {
                        this.assignmentsModal.selected.branch = null;
                        this.assignmentsModal.selected.area = null;
                        this.assignmentsModal.selected.dwEmployees = null;

                        this.newArticleModal.selected.branch = null;
                        this.newArticleModal.selected.area = null;
                        this.newArticleModal.selected.dwEmployees = null;
                    },
                    branchChanged: function () {
                        this.assignmentsModal.selected.area = null;
                        this.assignmentsModal.selected.dwEmployees = null;

                        this.newArticleModal.selected.area = null;
                        this.newArticleModal.selected.dwEmployees = null;
                    },
                    areaChanged: function () {
                        this.assignmentsModal.selected.dwEmployees = null;

                        this.newArticleModal.selected.dwEmployees = null;

                    },
                    validateFile: function (event) {
                        if (! event.target.files[0].name.match(/(\.jpg|\.jpeg|\.pdf|\.png)$/i)) {
                            event.target.value = null;
                            showAlert("Tipo de archivo no admitido", {type:3});
                        }
                        if (event.target.name == 'file-type-4' && event.target.files[0] != null) {
                            this.selectedInvoiceNumber = true;
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
                    showHistoricalModal: function (article) {
                        this.historicalModal.article = article;
                        this.fetchStockAssignments(article);
                        this.fetchStockAssignmentsRecord(article);
                        this.fetchStockDocuments(article);
                        this.fetchStockDocumentsRecord(article);
                        $("#historicalModal").modal("show");
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
                        $("#assignmentsModal").modal("show");
                    },
                    closeEditModal: function () {
                        this.editModal.selectAttr[0].selectize.destroy();
                        $("#editModal").modal("hide");
                    },
                    closeHistoricalModal: function () {
                        $("#historicalModal").modal("hide");
                    },
                    closeAssignmentsModal: function () {
                        document.getElementById("attachments-form").reset();
                        this.selectedInvoiceNumber = false;
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
                    saveNewStockArticle: function (properties) {
                        this.isSaving = true;

                        var form = document.getElementById('newArticleFrom');
                        var formData = new FormData(form);
                        this.$http.post(ROOT_URL + "/stock", formData).success(function (data) {
                            this.addProperties(data.idStock,properties)
                        }).error(function (data) {
                            this.isSaving = false;
                        })
                    },
                    saveStockAssignment: function (article) {
                        this.isSaving = true;
                        this.$http.post(
                                ROOT_URL + "/stock/" + article.idStock + "/assignments/" + this.assignmentsModal.selected.dwEmployees.idEmployee,
                                {
                                    idDwEnterprise: this.assignmentsModal.selected.area.dwEnterprise.idDwEnterprise,
                                    invoiceNumber:this.invoiceNumber
                                }
                        ).success(function () {
                            this.isSaving = false;
                            this.fetchStock(this.selectedOptions.distributor);
                            this.assignmentsModal.selected.distributor = null;
                            this.assignmentsModal.selected.region = null;
                            this.assignmentsModal.selected.branch = null;
                            this.assignmentsModal.selected.area = null;
                            this.assignmentsModal.selected.dwEmployees = null;
                            showAlert("Asignación exitosa");
                            this.closeAssignmentsModal();
                        }).error(function (data) {
                            this.isSaving = false;
                            this.assignmentsModal.selected.dwEmployees = null;
                            showAlert(data.error.message, {type:3});
                        })
                    },
                    showNewArticleModal: function () {
                        $("#newArticleModal").modal("show");
                    },
                    closeNewArticleModal: function () {
                        document.getElementById("attachments-form2").reset();
                        $("#newArticleModal").modal("hide");
                    },
                    showAttachmentsModal: function (article) {
                        this.attachmentsModal.article = article;
                        this.fetchStockDocumentsRecord(article);
                        $("#attachmentsModal").modal("show");
                    },
                    closeAttachmentsModal: function () {
                        document.getElementById("attachments-form2").reset();
                        $("#attachmentsModal").modal("hide");
                    },
                    uploadAttachments: function (article) {
                        this.isSaving = true;
                        var form = document.getElementById("attachments-form2");
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
                <div class="col-md-2 col-xs-6">
                    <select v-model="selectedOptions.distributor" class="form-control"
                            @change="fetchStock(selectedOptions.distributor)"
                            data-toggle="tooltip" data-placement="top" title="Selecciona un distribuidor">
                        <option v-for="distributor in selectOptions.distributors"
                                :value="distributor">
                            {{ distributor.distributorName }}
                        </option>
                    </select>
                </div>
                <div class="col-md-2 col-xs-6">
                    <select v-model="selectedOptions.area" class="form-control"
                            @change="attachOnScreen"
                            :disabled="selectOptions.areas.length < 2"
                            data-toggle="tooltip" data-placement="top" title="Selecciona una área">
                        <option value="0" selected>Todas las Áreas</option>
                        <option v-for="area in selectOptions.areas"
                                :value="area">
                            {{ area.areaName }}
                        </option>
                    </select>
                </div>
                <div class="col-md-1 col-xs-6">
                    <button class="btn btn-default" @click="showNewArticleModal"
                            data-toggle="tooltip" data-placement="top" title="Añadir artículo">
                        <span class="glyphicon glyphicon-plus"></span>
                    </button>
                </div>
                <div style="visibility: hidden" class="col-md-2 col-xs-6">
                    <select class="form-control"
                            data-toggle="tooltip" data-placement="top" title="Selecciona una región">
                    </select>
                </div>
                <div style="visibility: hidden" class="col-md-2 col-xs-6">
                    <select class="form-control"
                            data-toggle="tooltip" data-placement="top" title="Selecciona una sucursal">

                    </select>
                </div>
                <div style="visibility: hidden" class="col-md-1 col-xs-6">
                    <button class="btn btn-dafault"
                            data-toggle="tooltip" data-placement="top" title="Buscar artículo">
                        <span class="glyphicon glyphicon-search"></span>
                    </button>
                </div>
            </div>
            <div class="stock-groups col-xs-12">
                <div v-for="stock in stockGroups | filterBy areaFilter" class="">
                    <div class="text-center col-xs-12">
                        <h4>{{ stock[0].dwEnterprises.branch.branchShort }} - {{ stock[0].dwEnterprises.area.areaName }}</h4>
                    </div>
                    <div class="col-xs-12 panel-group">
                        <div v-for="article in stock" @build="buildArticle(article)"
                             class="lazy panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="text-center col-xs-9">
                                        <div class="col-md-3 col-xs-6">
                                            <p><strong>Artículo</strong></p>
                                            <p>{{ article.article.articleName }}</p>
                                        </div>
                                        <div class="col-md-3 col-xs-6">
                                            <p><strong>No. de Serie</strong></p>
                                            <p>{{ article.serialNumber }}</p>
                                        </div>
                                        <div class="col-md-3 col-xs-6">
                                            <p><strong>Fecha de alta</strong></p>
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
                                    <div class="col-xs-3">
                                        <button @click="showEditArticleModal(article)" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="Editar artículo">
                                            <span class="glyphicon glyphicon-pencil"></span>
                                        </button>
                                        <button @click="showAttachmentsModal(article)" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="Adjuntar archivos">
                                            <span class="glyphicon glyphicon-paperclip"></span>
                                        </button>
                                        <button @click="showAssignmentsModal(article)" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="Reasignar artículo">
                                            <span class="glyphicon glyphicon-user"></span>
                                        </button>
                                        <button @click="showHistoricalModal(article)" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="Historial de asignaciones archivos">
                                            <span class="glyphicon glyphicon-book"></span>
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
                                                <td>Folio de Inventario</td>
                                                <td>{{ article.stockFolio }}</td>
                                            </tr>
                                            <tr>
                                                <td>Solicitud</td>
                                                <td>{{ article.folio }}</td>
                                            </tr>
                                            <tr>
                                                <td>Número de factura</td>
                                                <td>{{ article.invoiceNumber }}</td>
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
                <%-- Modal de historicos --%>
            <div id="historicalModal" class="modal fade" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button @click.prevent="closeHistoricalModal" class="close"><span aria-hidden="true">&times;</span>
                            </button>
                            <h3 class="modal-title">Historial</h3>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-4 col-xs-6">
                                    <label>Artículo </label>
                                    {{ historicalModal.article.article.articleName }}
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>No. de Serie </label>
                                    {{ historicalModal.article.serialNumber }}
                                </div>
                                <div class="col-md-4 col-xs-12">
                                    <label>Asignado a </label>
                                    {{ historicalModal.article.stockEmployeeAssignmentsList[0].employee.firstName }}
                                    {{ historicalModal.article.stockEmployeeAssignmentsList[0].employee.middleName }}
                                    {{ historicalModal.article.stockEmployeeAssignmentsList[0].employee.parentalLast }}
                                    {{ historicalModal.article.stockEmployeeAssignmentsList[0].employee.motherLast }}
                                </div>
                            </div>
                            <div class="flex-row flex-content">
                                <hr>
                                <h4 class="text-center"><strong>Historial de asignaciones</strong></h4>
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>Asignado a</th>
                                        <th>Distribuidor</th>
                                        <th>Región</th>
                                        <th>Sucursal</th>
                                        <th>Área</th>
                                        <th>Fecha de alta</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tr class="success" v-if="historicalModal.article.stockEmployeeAssignmentsList[0]">
                                        <td>
                                            {{ historicalModal.article.stockEmployeeAssignmentsList[0].employee.firstName }}
                                            {{ historicalModal.article.stockEmployeeAssignmentsList[0].employee.middleName }}
                                            {{ historicalModal.article.stockEmployeeAssignmentsList[0].employee.parentalLast }}
                                            {{ historicalModal.article.stockEmployeeAssignmentsList[0].employee.motherLast }}
                                        </td>
                                        <td>{{ historicalModal.article.dwEnterprises.distributor.distributorName }}</td>
                                        <td>{{ historicalModal.article.dwEnterprises.region.regionName }}</td>
                                        <td>{{ historicalModal.article.dwEnterprises.branch.branchName }}</td>
                                        <td>{{ historicalModal.article.dwEnterprises.area.areaName }}</td>
                                        <td>
                                            {{ historicalModal.article.stockEmployeeAssignmentsList[0].assignmentDateFormats.dateNumber }}
                                        </td>
                                        <td><span class="label label-success">Actual</span></td>
                                    </tr>
                                    <tr v-for="assignment in historicalModal.article.assignmentsRecord">
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
                                        <td></td>
                                    </tr>
                                </table>
                            </div>
                            <div class="flex-row flex-content">
                                <hr>
                                <h4 class="text-center"><strong>Historial de documentos</strong></h4>
                                <table class="table table-striped text-left">
                                    <thead>
                                    <tr>
                                        <th>Documento</th>
                                        <th>Archivo</th>
                                        <th>Fecha de Envío</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tr v-for="document in historicalModal.article.stockDocumentsList">
                                        <td>
                                            {{ document.documentType.documentName }}
                                        </td>
                                        <td>
                                            <a :href="attachmentsDownloadUrl + document.idStockDocument">
                                                {{ document.documentName }}
                                            </a>
                                        </td>
                                        <td>
                                            {{ document.uploadingDateFormats.dateNumber }},
                                            {{ document.uploadingDateFormats.time12 }}
                                        </td>
                                        <td><span class="label label-success">Actual</span></td>
                                    </tr>
                                    <tr v-for="document in historicalModal.article.documentsRecord"
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
                                        <td></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button @click.prevent="closeHistoricalModal" :disabled="isSaving" class="btn btn-default">
                                Salir
                            </button>
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
                            <h4 class="modal-title">Modificación de Artículo</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-4 col-xs-6">
                                    <label>Artículo </label>
                                    {{ editModal.article.article.articleName }}
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>No. de Serie </label>
                                    {{ editModal.article.serialNumber }}
                                </div>
                            </div>
                            <hr />
                            <div class="row line">
                                <div class="col-md-4 col-xs-6">
                                    <label>No. de Serie</label>
                                    <input v-model="editModal.serialNumber"
                                           :disabled="isSaving" type="text" class="form-control">
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>Folio de Inventario</label>
                                    <input v-model="editModal.stockFolio"
                                           :disabled="isSaving" type="text" class="form-control">
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>Estado de Artículo</label>
                                    <select v-model="editModal.articleStatus"
                                            :disabled="isSaving" class="form-control">
                                        <option v-for="status in selectOptions.articleStatus"
                                                :value="status">{{ status.articleStatus }}</option>
                                    </select>
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>Precio de Compra</label>
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
                                                        class="btn btn-default"
                                                        data-toggle="tooltip" data-placement="top" title="Eliminar Propiedad">
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
                                                class="btn btn-default" style="margin-top: 2.5rem"
                                                data-toggle="tooltip" data-placement="top" title="Agregar Propiedad">
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
                    <div class="modal-content">
                        <div class="modal-header">
                            <button class="close" @click="closeAssignmentsModal"><span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Modificar Asignación</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-4 col-xs-6">
                                    <label>Artículo </label>
                                    {{ assignmentsModal.article.article.articleName }}
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>No. de Serie </label>
                                    {{ assignmentsModal.article.serialNumber }}
                                </div>
                                <div class="col-md-4 col-xs-6">
                                    <label>Folio de Inventario </label>
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
                                    <label>Región</label>
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
                                    <label>Área</label>
                                    <select v-model="assignmentsModal.selected.area" class="form-control"
                                            @change="areaChanged" :disabled="assignmentsModal.selected.branch == null">
                                        <option v-for="area in assignmentsModal.selected.branch.subLevels"
                                                :value="area">
                                            {{ area.name }}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3 col-xs-6">
                                    <label>Empleado / Almacén</label>
                                    <select v-model="assignmentsModal.selected.dwEmployees" class="form-control"
                                            :disabled="assignmentsModal.selected.area == null">
                                        <option v-for="dwEmployees in assignmentsModal.selected.area.dwEnterprise.dwEmployeesList"
                                                :value="dwEmployees">
                                            {{ dwEmployees.employee.fullName }}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <hr>
                            <form id="attachments-form" method="post" enctype="multipart/form-data"
                                  v-on:submit.prevent="uploadFilesAssignments(assignmentsModal.article)">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>Documento</th>
                                        <th>Nuevo Documento</th>
                                    </tr>
                                    </thead>
                                    <tr v-for="docType in selectOptions.documentTypes" v-if="docType.required == 2">
                                        <td>{{ docType.documentName }}</td>
                                        <td>
                                            <input @change="validateFile($event)" type="file" class="form-control"
                                                   :disabled="isSaving"
                                                   :name="assignmentsModal.fileInput + docType.idDocumentType"
                                                   accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                        </td>
                                    </tr>
                                </table>
                                <div class="col-md-3 col-xs-6" v-if="selectedInvoiceNumber && !assignmentsModal.article.invoiceNumber">
                                    <label>Número de factura</label>
                                    <input v-model="invoiceNumber" type="text" class="form-control">
                                </div>
                                <div v-if="isSaving" class="progress">
                                    <div class="progress-bar progress-bar-striped active" style="width: 100%"></div>
                                </div>
                                <div class="text-right modal-footer flex-footer">
                                    <button type="button" :disabled="isSaving" class="btn btn-default" @click="closeAssignmentsModal">
                                        Cancelar
                                    </button>
                                    <button type="submit" :disabled="isSaving"
                                            class="btn btn-success">
                                        Guardar
                                    </button>
                                </div>
                            </form>
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
                                    {{ attachmentsModal.article.stockEmployeeAssignmentsList[0].employee.fullName }}
                                </div>
                            </div>
                            <hr>
                            <form id="attachments-form2"
                                  method="post" enctype="multipart/form-data">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>Documento</th>
                                        <th>Nuevo documento</th>
                                    </tr>
                                    </thead>
                                    <tr v-for="docType in selectOptions.documentTypes">
                                        <td>{{ docType.documentName }}</td>
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
                    </div>
                </div>
            </div>

                <%--modal de nuevo articulo--%>
            <div id="newArticleModal" class="modal fade" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button class="close" @click="closeNewArticleModal()"><span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Nuevo de Artículo </h4>
                            <label>Folio: {{newArticleModal.stockFolio}}</label>
                        </div>
                        <div class="modal-body">
                            <form id="newArticleFrom" v-on:submit.prevent="saveNewStockArticle(newArticleModal.properties)"
                                  method="post" enctype="multipart/form-data">
                                <div class="row line">
                                    <div class="col-md-4 col-xs-6">
                                        <label>No. de Serie</label>
                                        <input v-model="newArticleModal.serialNumber" name="serialNumber"
                                               :disabled="isSaving" type="text" class="form-control">
                                    </div>
                                    <div class="col-md-4 col-xs-6">
                                        <label>Número de factura</label>
                                        <input v-model="newArticleModal.invoiceNumber" name="invoiceNumber"
                                               :disabled="isSaving" type="text" class="form-control">
                                    </div>
                                    <div class="col-md-4 col-xs-6">
                                        <label>Fecha de compra</label>
                                        <div class="input-group date" id="datetimepickerPurchaseDate"
                                             @click="setUpTimePickerPurchaseDate">
                                            <input type='text' name="purchaseDate" name="purchaseDate"
                                                   class="form-control" v-model="newArticleModal.purchaseDate" required>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                        </div>
                                    </div>
                                    <div class="col-md-4 col-xs-6">
                                        <label>Tipo de producto</label>
                                        <select v-model="newArticleModal.idArticle" name="article"
                                                @change="fetchAttributesNewArticle"
                                                :disabled="isSaving" class="form-control">
                                            <option v-for="article in newArticleModal.articles"
                                                    :value="article.idArticle">
                                                {{ article.articleName }}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row line">
                                    <div class="text-center line col-xs-12"><strong>Propiedades</strong></div>
                                    <div class="col-xs-12 col-md-8 col-md-push-2">
                                        <table class="table table-striped line">
                                            <tr v-for="property in newArticleModal.properties">
                                                <td class="col-xs-5">
                                                    {{ property.attributesArticles.attributes.attributeName }}
                                                </td>
                                                <td class="col-xs-5">{{ property.value.value }}</td>
                                                <td class="col-xs-2">
                                                    <button @click="removePropertyNewArticle(property)"
                                                            :disabled="isSaving"
                                                            class="btn btn-default"
                                                            data-toggle="tooltip" data-placement="top" title="Eliminar Propiedad">
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
                                            <select id="select-attribute2" class="form-control"></select>
                                        </div>
                                        <div class="col-xs-5">
                                            <label>Valor</label>
                                            <select id="select-value2" class="form-control"></select>
                                        </div>
                                        <div class="col-xs-2">
                                            <button type="button" @click="addPropertyNewArticle"
                                                    :disabled="isSaving"
                                                    class="btn btn-default" style="margin-top: 2.5rem"
                                                    data-toggle="tooltip" data-placement="top" title="Agregar Propiedad">
                                                <span class="glyphicon glyphicon-plus"></span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>Documento</th>
                                        <th>Nuevo Documento</th>
                                    </tr>
                                    </thead>
                                    <tr v-for="docType in selectOptions.documentTypes">
                                        <td>{{ docType.documentName }}</td>
                                        <td>
                                            <input @change="validateFile($event)" type="file" class="form-control"
                                                   :disabled="isSaving" :required="docType.required == 2"
                                                   :name="newArticleModal.fileInput + docType.idDocumentType"
                                                   accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                        </td>
                                    </tr>
                                </table>
                                <br>
                                <div class="row">
                                    <div class="col-xs-12"><label>¿Deseas asignar este artículo?</label></div>
                                    <div class="col-md-3 col-xs-6">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" @change="seeAssignView" name="assign" id="optionsRadios1">
                                                Si
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label>
                                                <input type="radio" @change="doNotSeeAssignView" name="assign" id="optionsRadios2" checked>
                                                No
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div  v-if="assign">
                                    <div class="row">
                                        <div class="col-xs-12"><label>Asignar a</label></div>
                                        <div class="col-md-3 col-xs-6">
                                            <label>Distribuidor</label>
                                            <select v-model="newArticleModal.selected.distributor" class="form-control"
                                                    @change="distributorChanged">
                                                <option v-for="distributor in selectOptions.hierarchy[0].subLevels"
                                                        :value="distributor">
                                                    {{ distributor.name }}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-md-3 col-xs-6">
                                            <label>Región</label>
                                            <select v-model="newArticleModal.selected.region" class="form-control"
                                                    @change="regionChanged" :disabled="newArticleModal.selected.distributor == null">
                                                <option v-for="region in newArticleModal.selected.distributor.subLevels"
                                                        :value="region">
                                                    {{ region.name }}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-md-3 col-xs-6">
                                            <label>Sucursal</label>
                                            <select v-model="newArticleModal.selected.branch" class="form-control"
                                                    @change="branchChanged" :disabled="newArticleModal.selected.region == null">
                                                <option v-for="branch in newArticleModal.selected.region.subLevels"
                                                        :value="branch">
                                                    {{ branch.name }}
                                                </option>
                                            </select>
                                        </div>
                                        <div class="col-md-3 col-xs-6">
                                            <label>Área</label>
                                            <select v-model="newArticleModal.selected.area" class="form-control"
                                                    @change="areaChanged" :disabled="newArticleModal.selected.branch == null">
                                                <option v-for="area in newArticleModal.selected.branch.subLevels"
                                                        :value="area">
                                                    {{ area.name }}
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3 col-xs-6">
                                            <label>Empleado / Almacén</label>
                                            <select v-model="newArticleModal.selected.dwEmployees" class="form-control"
                                                    :disabled="newArticleModal.selected.area == null">
                                                <option v-for="dwEmployees in newArticleModal.selected.area.dwEnterprise.dwEmployeesList"
                                                        :value="dwEmployees">
                                                    {{ dwEmployees.employee.fullName }}
                                                </option>
                                            </select>
                                        </div>
                                        <div>
                                            <input type="text" :value="newArticleModal.selected.dwEmployees.idEmployee" name="employee" hidden>
                                            <input type="text" :value="newArticleModal.selected.area.dwEnterprise.idDwEnterprise" name="dwEnterprise" hidden>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="text-right modal-footer">
                                    <button type="button" :disabled="isSaving" class="btn btn-default"
                                            @click="closeNewArticleModal">
                                        Cancelar
                                    </button>
                                    <button type="submit"
                                            :disabled="isSaving"
                                            class="btn btn-success">
                                        Guardar
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
