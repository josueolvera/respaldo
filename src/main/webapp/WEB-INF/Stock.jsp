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
                    this.fetchHierarchy();
                    this.fetchDocumentTypes();
                    this.fetchArticleStatus();
                    this.getArticlesCategories();
                },
                data: {
                    assign:false,
                    isSaving: false,
                    stockGroups: [],
                    searching:false,
                    selectOptions: {
                        distributors: [],
                        areas: [],
                        articleStatus:[],
                        employees: [],
                        hierarchy: [],
                        documentTypes: [],
                        documentTypesRequired: [],
                        documentTypesNoRequired: []
                    },
                    stockFilter:'',
                    selectedOptions: {
                        area: {
                            id:0
                        },
                        distributor: {
                            id:0
                        },
                        region: {
                            id:0
                        },
                        branch: {
                            id:0
                        }
                    },
                    defaultArea: {
                        id:0,
                        name:''
                    },
                    defaultDistributor: {
                        id:0,
                        name:''
                    },
                    defaultRegion: {
                        id:0,
                        name:''
                    },
                    defaultBranch: {
                        id:0,
                        name:''
                    },
                    historicalModal: {
                        article: null,
                        fileInput: "file-type-"
                    },
                    editModal: {
                        serialNumber: 0,
                        stockFolio: 0,
                        articleStatus: null,
                        employees: null,
                        selectAttr: '',
                        selectValue:'',
                        attributes: [],
                        values: [],
                        selectedEmployee: null,
                        article: null
                    },
                    newArticleModal: {
                        serialNumber: null,
                        stockFolio: null,
                        articleStatus: null,
                        idArticleStatus:null,
                        purchaseDate:null,
                        attributes: [],
                        values: [],
                        invoiceNumber:null,
                        employees: null,
                        selectedEmployee: null,
                        articleCategory:'',
                        idArticle:'',
                        properties : [],
                        articles: [],
                        selectAttr: '',
                        selectValue:'',
                        articlesCategories:[],
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
                    getArticles : function (articleCategory) {
                        this.$http.get(ROOT_URL + "/articles/article-category/" + articleCategory.idArticlesCategory).success(function (data) {
                            this.newArticleModal.articles = data;
                            this.newArticleModal.idArticle = '';
                        });
                    },
                    getArticlesCategories : function () {
                        this.$http.get(ROOT_URL + "/articles-categories").success(function (data) {
                            this.newArticleModal.articlesCategories = data;
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
                    fetchAttributesEditModal: function (article) {
                        var self = this;
                        this.$http.get(ROOT_URL + "/attributes/" + article.article.idArticle).success(function (data) {
                            var attributes = [];
                            this.editModal.attributes = data;
                            this.editModal.attributes.forEach(function (attribute) {
                                article.propertiesList.forEach(function (property) {
                                    if (attribute.idAttribute === property.attributesArticles.attributes.idAttribute) {
                                        attributes.push(attribute)
                                    }
                                });
                            });
                            attributes.forEach(function (attribute) {
                                self.editModal.attributes.$remove(attribute);
                            });

                            if (this.editModal.selectAttr != '') {
                                this.editModal.selectAttr[0].selectize.destroy();
                            }

                            this.editModal.selectAttr = $('#select-attribute-edit').selectize({
                                maxItems: 1,
                                valueField: 'idAttribute',
                                labelField: 'attributeName',
                                searchField: 'attributeName',
                                options: self.editModal.attributes,
                                create: false
                            }).on('change',this.fetchEditModalValues);
                        });
                    },
                    fetchAttributesNewArticleModal: function (idArticle) {
                        this.$http.get(ROOT_URL + "/attributes/" + idArticle).success(function (data) {
                            this.newArticleModal.attributes = data;
                            if (this.newArticleModal.selectAttr != '') {
                                this.newArticleModal.selectAttr[0].selectize.destroy();
                            }
                            this.newArticleModal.selectAttr = $('#select-attribute-new').selectize({
                                maxItems: 1,
                                valueField: 'idAttribute',
                                labelField: 'attributeName',
                                searchField: 'attributeName',
                                options: data,
                                create: false
                            }).on('change',this.fetchNewArticleModalValues);
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
                    fetchNewArticleModalValues: function () {
                        var self = this;
                        this.$http.get(
                                ROOT_URL +
                                '/values?idAttribute=' +
                                self.newArticleModal.selectAttr[0].selectize.getValue() +
                                '&idArticlesCategory=' +
                                self.newArticleModal.articleCategory.idArticlesCategory
                        ).success(function (data) {
                            this.newArticleModal.values = data;
                            if (self.newArticleModal.selectValue != '') {
                                self.newArticleModal.selectValue[0].selectize.destroy();
                            }
                            this.newArticleModal.selectValue = $('#select-value-new').selectize({
                                maxItems: 1,
                                valueField: 'idValue',
                                labelField: 'value',
                                searchField: 'value',
                                options: data,
                                create: function (input, callback) {
                                    self.$http.post(ROOT_URL + "/values", {
                                        idAttribute: self.newArticleModal.selectAttr[0].selectize.getValue(),
                                        idArticlesCategory: self.newArticleModal.articleCategory.idArticlesCategory,
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
                    fetchEditModalValues: function () {
                        var self = this;
                        console.log(this.editModal.article.article.articlesCategories.idArticlesCategory);
                        this.$http.get(
                                ROOT_URL +
                                "/values?idAttribute=" +
                                self.editModal.selectAttr[0].selectize.getValue() +
                                '&idArticlesCategory=' +
                                self.editModal.article.article.articlesCategories.idArticlesCategory
                        ).success(function (data) {
                            this.editModal.values = data;

                            if (self.editModal.selectValue != '') {
                                self.editModal.selectValue[0].selectize.destroy();
                            }
                            this.editModal.selectValue = $('#select-value-edit').selectize({
                                maxItems: 1,
                                valueField: 'idValue',
                                labelField: 'value',
                                searchField: 'value',
                                options: data,
                                create: function (input, callback) {
                                    self.$http.post(ROOT_URL + "/values", {
                                        idAttribute: self.editModal.selectAttr[0].selectize.getValue(),
                                        idArticlesCategory: self.editModal.article.article.articlesCategories.idArticlesCategory,
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
                    cleanArticle: function (article) {
                        article.propertiesList = null;
                        article.stockDocumentsList = null;
                        article.stockEmployeeAssignmentsList = null;
                    },
                    removeProperty: function (article, property) {
                        this.isSaving = true;
                        this.$http.delete(ROOT_URL + "/stock/properties/" + property.idProperty).success(function () {
                            this.fetchStockProperties(article);
                            this.editModal.attributes.push(property.attributesArticles.attributes);
                            this.fetchAttributesEditModal(article);
                            this.fetchEditModalValues();
                            this.isSaving = false;
                        }).error(function () {
                            this.isSaving = false;
                            showAlert("No se pudo elimnar la propiedad, intente nuevamente", {type:3});
                        });
                    },
                    removePropertyNewArticle: function (property) {
                        this.newArticleModal.properties.$remove(property);
                        this.newArticleModal.attributes.push(property.attributesArticles.attributes);
                        this.newArticleModal.selectAttr[0].selectize.destroy();
                        this.newArticleModal.selectAttr = $('#select-attribute-new').selectize({
                            maxItems: 1,
                            valueField: 'idAttribute',
                            labelField: 'attributeName',
                            searchField: 'attributeName',
                            options: this.newArticleModal.attributes,
                            create: false
                        }).on('change',this.fetchNewArticleModalValues);

                        this.fetchNewArticleModalValues();
                    },
                    addProperties: function (idStock,properties) {
                        this.$http.post(ROOT_URL + "/stock/" + idStock + "/properties", JSON.stringify(properties)).success(function (data) {
                            showAlert("Articulo guardado")
                            this.getStocks();
                            this.closeNewArticleModal();
                            this.isSaving = false;
                        }).error(function (data) {
                            this.isSaving = false;
                            showAlert("No se pudo guardar el articulo", {type:3})
                        });
                    },
                    addProperty: function (article) {
                        this.isSaving = true;
                        var self = this;
                        var idAttr = this.editModal.selectAttr[0].selectize.getValue();
                        var idVal = this.editModal.selectValue[0].selectize.getValue();
                        var property = {
                            value: {
                                idValue: idVal,
                                value: this.editModal.selectValue[0].selectize.getOption(idVal).text()
                            },
                            attributesArticles: {
                                idArticle: article.idArticle,
                                idAttribute: this.editModal.selectValue[0].selectize.getOption(idVal).text(),
                                attributes: {
                                    idAttribute: idAttr,
                                    attributeName: this.editModal.selectAttr[0].selectize.getOption(idAttr).text()
                                }
                            }
                        };

                        this.$http.post
                        (
                                ROOT_URL + "/stock/" + article.idStock + "/properties",
                                property
                        ).success(function (data) {
                            this.fetchStockProperties(article);
                            this.editModal.attributes.forEach(function (attribute) {
                                if (attribute.idAttribute === idAttr) {
                                    self.editModal.attributes.$remove(attribute);
                                }
                            });

                            this.fetchAttributesEditModal(article);
                            this.fetchEditModalValues();

                            this.isSaving = false;
                        }).error(function (data) {
                            this.editModal.attribute = '';
                            this.editModal.value = '';
                            this.isSaving = false;
                            showAlert("No se pudo agregar la propiedad, intente nuevamente", {type:3})
                        });
                    },
                    addPropertyNewArticle: function () {
                        var self = this;
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

                        this.newArticleModal.attributes.forEach(function (attribute) {
                            if (attribute.idAttribute === Number(idAttr)) {
                                self.newArticleModal.attributes.$remove(attribute);
                            }
                        });

                        this.newArticleModal.selectAttr[0].selectize.destroy();
                        this.newArticleModal.selectAttr = $('#select-attribute-new').selectize({
                            maxItems: 1,
                            valueField: 'idAttribute',
                            labelField: 'attributeName',
                            searchField: 'attributeName',
                            options: self.newArticleModal.attributes,
                            create: false
                        }).on('change',this.fetchNewArticleModalValues);

                        this.fetchNewArticleModalValues();

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
                        this.fetchAttributesEditModal(article);
                        this.fetchEmployees(article.idDwEnterprises);
                        $("#editModal").modal("show");
                    },
                    showAssignmentsModal: function (article) {
                        this.assignmentsModal.article = article;
                        $("#assignmentsModal").modal("show");
                    },
                    closeEditModal: function () {
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
                        }).success(function () {
                            article.serialNumber = this.editModal.serialNumber;
                            article.stockFolio = this.editModal.stockFolio;
                            article.articleStatus = this.editModal.articleStatus;
                            this.closeEditModal();
                            this.isSaving = false;
                            showAlert("Articulo modificado con exito", {type: 1});
                        }).error(function (data) {
                            this.isSaving = false;
                            showAlert(data.error.message, {type: 3});
                        });
                    },
                    saveNewStockArticle: function (properties) {
                        this.isSaving = true;
                        console.log(properties);
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
                            this.getStocks();
                            this.assignmentsModal.selected.distributor = null;
                            this.assignmentsModal.selected.region = null;
                            this.assignmentsModal.selected.branch = null;
                            this.assignmentsModal.selected.area = null;
                            this.assignmentsModal.selected.dwEmployees = null;
                            showAlert("Asignación exitosa");
                            this.closeAssignmentsModal();
                        }).error(function (data) {
                            this.isSaving = false;
                            this.assignmentsModal.selected.distributor = null;
                            this.assignmentsModal.selected.region = null;
                            this.assignmentsModal.selected.branch = null;
                            this.assignmentsModal.selected.area = null;
                            this.assignmentsModal.selected.dwEmployees = null;
                            showAlert(data.error.message, {type:3});
                        })
                    },
                    showNewArticleModal: function () {
                        $("#newArticleModal").modal("show");
                    },
                    closeNewArticleModal: function () {
                        document.getElementById("newArticleFrom").reset();
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
                            this.closeAttachmentsModal();
                            this.fetchStockDocuments(article);
                            this.fetchStockDocumentsRecord(article);
                        }).error(function (data) {
                            this.isSaving = false;
                            form.reset();
                            showAlert(data.error.message, {type:3})
                        });
                    },
                    getStocks: function () {

                        if (this.selectedOptions.distributor.id == 0) {
                            showAlert('Selecciona un distribuidor',{type:3})
                            return;
                        }

                        this.stockGroups = [];
                        this.searching = true;

                        this.$http.get(
                                ROOT_URL + "/stock?idDistributor=" +
                                this.selectedOptions.distributor.id + "&idRegion=" +
                                this.selectedOptions.region.id  + "&idBranch=" +
                                this.selectedOptions.branch.id + "&idArea=" +
                                this.selectedOptions.area.id
                        ).success(function (data) {
                            this.stockGroups = this.groupBy(data, function (item) {
                                return item.idDwEnterprises;
                            });
                            this.attachOnScreen();
                            this.searching = false;
                        });
                    },
                    selectedOptionsDistributorChanged: function () {
                        this.selectedOptions.region = this.defaultRegion;
                        this.selectedOptions.branch = this.defaultBranch;
                        this.selectedOptions.area = this.defaultArea;
                    },
                    selectedOptionsRegionChanged: function () {
                        this.selectedOptions.branch = this.defaultBranch;
                        this.selectedOptions.area = this.defaultArea;
                    },
                    selectedOptionsBranchChanged: function () {
                        this.selectedOptions.area = this.defaultArea;
                    },
                    setIsCollapsed: function (article) {
                        if (article.isCollapsed === true) {
                            article.isCollapsed = false;
                            this.cleanArticle(article);

                        } else {
                            this.buildArticle(article);
                            Vue.set(article, "isCollapsed", true);

                        }
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
            <br>
            <div>
                <div class="col-xs-8">
                    <h2>Inventario</h2>
                </div>
                <div class="col-xs-3">
                    <div class="col-xs-9">
                        <label>Buscar</label>
                        <input type="text" class="form-control"
                               v-model="stockFilter" :disabled="!stockGroups.length > 0">
                    </div>
                    <div class="col-xs-3">
                        <label style="visibility: hidden">add</label>
                        <button class="btn btn-default" @click="showNewArticleModal"
                                data-toggle="tooltip" data-placement="top" title="Agregar artículo">
                            Agregar artículo
                        </button>
                    </div>
                </div>
            </div>
            <div class="col-xs-12" v-if="selectOptions.hierarchy.length > 0">
                <hr size="30" style="border-top: 1px solid #ccc;">
                <div class="col-xs-2">
                    <label>Distribuidor</label>
                    <select v-model="selectedOptions.distributor" class="form-control"
                            @change="selectedOptionsDistributorChanged">
                        <option selected :value="defaultDistributor">{{defaultDistributor.name}}</option>
                        <option v-for="distributor in selectOptions.hierarchy[0].subLevels"
                                :value="distributor">
                            {{ distributor.name }}
                        </option>
                    </select>
                </div>
                <div class="col-xs-2">
                    <label>Región</label>
                    <select v-model="selectedOptions.region" class="form-control"
                            @change="selectedOptionsRegionChanged"
                            :disabled="selectedOptions.distributor.id == 0">
                        <option selected :value="defaultRegion">{{defaultRegion.name}}</option>
                        <option v-for="region in selectedOptions.distributor.subLevels"
                                :value="region">
                            {{ region.name }}
                        </option>
                    </select>
                </div>
                <div class="col-xs-2">
                    <label>Sucursal</label>
                    <select v-model="selectedOptions.branch" class="form-control"
                            @change="selectedOptionsBranchChanged"
                            :disabled="selectedOptions.region.id == 0">
                        <option selected :value="defaultBranch">{{defaultBranch.name}}</option>
                        <option v-for="branch in selectedOptions.region.subLevels"
                                :value="branch">
                            {{ branch.name }}
                        </option>
                    </select>
                </div>
                <div class="col-xs-2">
                    <label>Área</label>
                    <select v-model="selectedOptions.area" class="form-control"
                            :disabled="selectedOptions.branch.id == 0">
                        <option selected :value="defaultArea">{{defaultArea.name}}</option>
                        <option v-for="area in selectedOptions.branch.subLevels"
                                :value="area">
                            {{ area.name }}
                        </option>
                    </select>
                </div>
                <div class="col-xs-1">
                    <label style="visibility: hidden">search</label>
                    <button class="btn btn-default" @click="getStocks"
                            data-toggle="tooltip" data-placement="top" title="Buscar artículo">
                        <span class="glyphicon glyphicon-search"></span>
                    </button>
                </div>
            </div>
            <br>
            <div v-if="!stockGroups.length > 0 && searching == true" class="col-xs-12"
                 style="height: 6rem; padding: 2rem 0;">
                <div class="loader">Cargando...</div>
            </div>
            <div class="stock-groups col-xs-12"  v-if="stockGroups.length > 0" && searching == false>
                <div v-for="stock in stockGroups | filterBy stockFilter">
                    <div class="text-center col-xs-12">
                        <h4>
                            {{ stock[0].dwEnterprises.distributor.distributorName }} - {{ stock[0].dwEnterprises.region.regionName }} - {{ stock[0].dwEnterprises.branch.branchShort }} - {{ stock[0].dwEnterprises.area.areaName }}
                        </h4>
                    </div>
                    <div class="col-xs-12 panel-group">
                        <div v-for="article in stock | filterBy stockFilter in 'article.articleName'"
                             class="lazy panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-8">
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
                                    <div class="text-right col-xs-4">
                                        <div class="col-xs-10">
                                            <button v-if="article.isCollapsed == true" @click="showEditArticleModal(article)" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="Editar artículo">
                                                <span class="glyphicon glyphicon-pencil"></span>
                                            </button>
                                            <button v-if="article.isCollapsed == true" @click="showAttachmentsModal(article)" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="Adjuntar archivos">
                                                <span class="glyphicon glyphicon-paperclip"></span>
                                            </button>
                                            <button v-if="article.isCollapsed == true" @click="showAssignmentsModal(article)" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="Reasignar artículo">
                                                <span class="glyphicon glyphicon-user"></span>
                                            </button>
                                            <button v-if="article.isCollapsed == true" @click="showHistoricalModal(article)" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="Historial de asignaciones archivos">
                                                <span class="glyphicon glyphicon-book"></span>
                                            </button>
                                        </div>
                                        <div class="col-xs-2">
                                            <button @click="setIsCollapsed(article)" data-toggle="collapse" href="#collapse{{article.idStock}}"
                                                    class="btn btn-default" data-toggle="tooltip" data-placement="top" title="Mostrar detalles">
                                                    <span :class="{ 'glyphicon-menu-down': ! article.isCollapsed, 'glyphicon-menu-up': article.isCollapsed }"
                                                          class="glyphicon" ></span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                                <%-- Panel Interno --%>
                            <div id="collapse{{article.idStock}}" class="panel-collapse collapse">
                                <div class="panel-body">
                                        <%-- Atributos --%>
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
                            <button class="close" @click="closeEditModal"><span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title"><strong>Modificación de Artículo</strong></h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-4">
                                    <label>Artículo </label>
                                    {{ editModal.article.article.articleName }}
                                </div>
                                <div class="col-md-4">
                                    <label>No. de Serie </label>
                                    {{ editModal.article.serialNumber }}
                                </div>
                            </div>
                            <hr />
                            <div class="row line">
                                <div class="col-md-4">
                                    <label>No. de Serie</label>
                                    <input v-model="editModal.serialNumber"
                                           :disabled="isSaving" type="text" class="form-control">
                                </div>
                                <div class="col-md-4">
                                    <label>Folio de Inventario</label>
                                    <input v-model="editModal.stockFolio"
                                           :disabled="isSaving" type="text" class="form-control">
                                </div>
                                <div class="col-md-4">
                                    <label>Estado de Artículo</label>
                                    <select v-model="editModal.articleStatus"
                                            :disabled="isSaving" class="form-control">
                                        <option v-for="status in selectOptions.articleStatus"
                                                :value="status">{{ status.articleStatus }}</option>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <h4>Propiedades</h4>
                            <hr>
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>Atributo</label>
                                    <select required id="select-attribute-edit" class="form-control"></select>
                                </div>
                                <div class="col-xs-4">
                                    <label>Valor</label>
                                    <select required id="select-value-edit" class="form-control"></select>
                                </div>
                                <div class="col-xs-1">
                                    <button @click.prevent="addProperty(editModal.article)"
                                            :disabled="isSaving"
                                            class="btn btn-default" style="margin-top: 2.5rem"
                                            data-toggle="tooltip" data-placement="top" title="Agregar Propiedad">
                                        <span class="glyphicon glyphicon-plus"></span>
                                    </button>
                                </div>
                            </div>
                            <br>
                            <div class="row line">
                                <div class="col-xs-12">
                                    <table class="table table-striped line">
                                        <tr v-for="property in editModal.article.propertiesList">
                                            <td class="col-xs-5">
                                                {{ property.attributesArticles.attributes.attributeName }}
                                            </td>
                                            <td class="col-xs-5">{{ property.value.value }}</td>
                                            <td class="col-xs-2">
                                                <button @click.prevent="removeProperty(editModal.article, property)"
                                                        :disabled="isSaving"
                                                        class="btn btn-danger"
                                                        data-toggle="tooltip" data-placement="top" title="Eliminar Propiedad">
                                                    <span class="glyphicon glyphicon-trash"></span>
                                                </button>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="text-right modal-footer">
                            <button :disabled="isSaving" class="btn btn-default" @click="closeEditModal">Salir</button>
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
                                        Salir
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
                                    <button type="button" @click="closeAttachmentsModal" :disabled="isSaving" class="btn btn-default">Salir</button>
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
                            <h4 class="modal-title"><strong>Nuevo de Artículo</strong></h4>
                        </div>
                        <form id="newArticleFrom" v-on:submit.prevent="saveNewStockArticle(newArticleModal.properties)"
                              method="post" enctype="multipart/form-data">
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-4">
                                        <label>Tipo de producto</label>
                                        <select v-model="newArticleModal.articleCategory"
                                                @change="getArticles(newArticleModal.articleCategory)"
                                                :disabled="isSaving" class="form-control" required>
                                            <option v-for="articleCategory in newArticleModal.articlesCategories"
                                                    :value="articleCategory">
                                                {{ articleCategory.articlesCategoryName }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <label>Producto</label>
                                        <select v-model="newArticleModal.idArticle" name="article"
                                                @change="fetchAttributesNewArticleModal(newArticleModal.idArticle)" required
                                                :disabled="isSaving || newArticleModal.articleCategory == ''" class="form-control">
                                            <option v-for="article in newArticleModal.articles"
                                                    :value="article.idArticle">
                                                {{ article.articleName }}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div v-if="newArticleModal.idArticle != ''">
                                    <br>
                                    <div class="row">
                                        <div class="col-md-4"
                                             v-if="newArticleModal.articleCategory.requireSerialNumber == 1">
                                            <label>No. de Serie</label>
                                            <input v-model="newArticleModal.serialNumber" name="serialNumber" required
                                                   :disabled="isSaving" type="text" class="form-control">
                                        </div>
                                        <div class="col-md-4">
                                            <label>Número de factura</label>
                                            <input v-model="newArticleModal.invoiceNumber" name="invoiceNumber" required
                                                   :disabled="isSaving" type="text" class="form-control">
                                        </div>
                                        <div class="col-md-4">
                                            <label>Fecha de compra</label>
                                            <div class="input-group date" id="datetimepickerPurchaseDate"
                                                 @click="setUpTimePickerPurchaseDate">
                                                <input type='text' name="purchaseDate" name="purchaseDate" required
                                                       class="form-control" v-model="newArticleModal.purchaseDate" required>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                    <h4>Propiedades</h4>
                                    <hr>
                                    <div class="row">
                                        <div class="col-xs-4">
                                            <label>Atributo</label>
                                            <select :disabled="isSaving" id="select-attribute-new" class="form-control"></select>
                                        </div>
                                        <div class="col-xs-4">
                                            <label>Valor</label>
                                            <select :disabled="isSaving" id="select-value-new" class="form-control"></select>
                                        </div>
                                        <div class="col-xs-1">
                                            <button type="button" @click="addPropertyNewArticle"
                                                    :disabled="isSaving"
                                                    class="btn btn-default" style="margin-top: 2.5rem"
                                                    data-toggle="tooltip" data-placement="top" title="Agregar Propiedad">
                                                <span class="glyphicon glyphicon-plus"></span>
                                            </button>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <table class="table table-striped line">
                                                <tr v-for="property in newArticleModal.properties">
                                                    <td class="col-xs-5">
                                                        {{ property.attributesArticles.attributes.attributeName }}
                                                    </td>
                                                    <td class="col-xs-5">{{ property.value.value }}</td>
                                                    <td class="col-xs-2">
                                                        <button @click="removePropertyNewArticle(property)"
                                                                :disabled="isSaving"
                                                                class="btn btn-danger"
                                                                data-toggle="tooltip" data-placement="top" title="Eliminar Propiedad">
                                                            <span class="glyphicon glyphicon-trash"></span>
                                                        </button>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th>Documento</th>
                                            <th>Nuevo Documento</th>
                                        </tr>
                                        </thead>
                                        <tr v-for="docType in selectOptions.documentTypes" v-if="docType.required == 1">
                                            <td>{{ docType.documentName }}</td>
                                            <td>
                                                <input @change="validateFile($event)" type="file" class="form-control"
                                                       :disabled="isSaving"
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
                                                <select v-model="newArticleModal.selected.distributor" class="form-control" required
                                                        @change="distributorChanged">
                                                    <option v-for="distributor in selectOptions.hierarchy[0].subLevels"
                                                            :value="distributor">
                                                        {{ distributor.name }}
                                                    </option>
                                                </select>
                                            </div>
                                            <div class="col-md-3 col-xs-6">
                                                <label>Región</label>
                                                <select v-model="newArticleModal.selected.region" class="form-control" required
                                                        @change="regionChanged" :disabled="newArticleModal.selected.distributor == null">
                                                    <option v-for="region in newArticleModal.selected.distributor.subLevels"
                                                            :value="region">
                                                        {{ region.name }}
                                                    </option>
                                                </select>
                                            </div>
                                            <div class="col-md-3 col-xs-6">
                                                <label>Sucursal</label>
                                                <select v-model="newArticleModal.selected.branch" class="form-control" required
                                                        @change="branchChanged" :disabled="newArticleModal.selected.region == null">
                                                    <option v-for="branch in newArticleModal.selected.region.subLevels"
                                                            :value="branch">
                                                        {{ branch.name }}
                                                    </option>
                                                </select>
                                            </div>
                                            <div class="col-md-3 col-xs-6">
                                                <label>Área</label>
                                                <select v-model="newArticleModal.selected.area" class="form-control" required
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
                                                <select v-model="newArticleModal.selected.dwEmployees" class="form-control" required
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
                                                           :disabled="isSaving" required
                                                           :name="newArticleModal.fileInput + docType.idDocumentType"
                                                           accept="application/pdf,
                                                         image/png,image/jpg,image/jpeg,">
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                                <br>
                                <div class="text-right modal-footer">
                                    <button type="button" :disabled="isSaving" class="btn btn-default"
                                            @click="closeNewArticleModal">
                                        Salir
                                    </button>
                                    <button type="submit"
                                            :disabled="isSaving"
                                            class="btn btn-success">
                                        Guardar
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
