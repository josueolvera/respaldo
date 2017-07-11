<%--
  Created by IntelliJ IDEA.
  User: Leonardo
  Date: 18/05/2017
  Time: 12:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>
<t:template pageTitle="BID Group: Bandeja de entrada solicitante">
     <jsp:attribute name="scripts">

        <script type="text/javascript">
            function validateFloatKeyPress(el, evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode;
                var number = el.value.split('.');
                if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
                //just one dot
                if (number.length > 1 && charCode == 46) {
                    return false;
                }
                //get the carat position
                var caratPos = getSelectionStart(el);
                var dotPos = el.value.indexOf(".");
                if (caratPos > dotPos && dotPos > -1 && (number[1].length > 1)) {
                    return false;
                }

                return true;
            }

            function getSelectionStart(o) {
                if (o.createTextRange) {
                    var r = document.selection.createRange().duplicate();
                    r.moveEnd('character', o.value.length);
                    if (r.text == '') return o.value.length;
                    return o.value.lastIndexOf(r.text)
                } else return o.selectionStart
            }
        </script>
         <script>
             Number.prototype.formatMoney = function (places, symbol, thousand, decimal) {
                 places = !isNaN(places = Math.abs(places)) ? places : 2;
                 symbol = symbol !== undefined ? symbol : "$";
                 thousand = thousand || ",";
                 decimal = decimal || ".";
                 var number = this,
                     negative = number < 0 ? "-" : "",
                     i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
                     j = (j = i.length) > 3 ? j % 3 : 0;
                 return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
             };
         </script>
         <script type="text/javascript">
             function isNumberKey(evt) {
                 var charCode = (evt.which) ? evt.which : event.keyCode;
                 if (charCode > 31 && (charCode < 48 || charCode > 57))
                     return false;
                 return true;
             }
         </script>
<script type="text/javascript">
    var vm = new Vue({
        el: '#content',
        created: function () {

        },
        ready: function () {
            this.getUserInSession();
            this.getRequestInformation();
            this.getRequestHistory();
        },
        data: {
            user: {},
            idRequest: ${idRequest},
            request: {},
            downloadUrl: ROOT_URL + '/estimations/attachment/download/',
            downloadUrlInvoices: ROOT_URL +'/purchase-invoices-files/attachment/download/',
            priceEstimations: {},
            selectedEstimation: {
                idEstimation: '',
                idRequest: '',
                justify: '',
                rejectJustify: ''
            },
            requestBoxUrl: ROOT_URL + "/siad/buy",
            providers: [],
            distributors: [],
            providerAddress: [],
            providerContact: [],
            distributor: {},
            provider: {
                providerName: ''
            },
            requestOrderProducts: {
                description: '',
                quantity: 1,
                price: 0
            },
            orderProducts: [],
            var1: 0,
            var2: 0,
            var3: 0,
            amount: 0,
            iva: 0,
            totalAmount: 0,
            startDate: '',
            contactCel: '',
            contactName: '',
            contactRole: '',
            sRequestOrderProduct: {
                provider: {},
                distributor: {},
                amount: 0,
                iva: 0,
                totalAmount: 0,
                startDate: '',
                contactCel: '',
                contactName: '',
                contactRole: '',
                orderProducts: [],
                idRequest: ''
            },
            requestOrder: null,
            fileVoucher: {
                voucherPdfFile: null,
                voucherXmlFile: null
            },
            isPdfFile: false,
            isXmlFile: false,
            billInformation: null,
            providerSelectize:{},
            providerAccountList:[],
            providerAccount:{},
            purchaseInvoices:{
                providerAccount: null,
                idProvider:null,
                idRequest:null,
                fileVoucher: {
                    voucherPdfFile: null,
                    voucherXmlFile: null
                },
                isXmlFile: false,
                billInformation: null
            },
            purchaseInvoice: null,
            amount1: '',
            amount2: '',
            amount3: '',
            button1: false,
            button2: false,
            button3: false,
            requestHistory: [],
            estimations: []
        },
        methods: {
            descarga: function (id) {
                window.open (ROOT_URL + "/order-documents-request/purchase-order-report?idRequestOrderDocument=" + id);
            },
            getRequestHistory: function () {
                this.$http.get(ROOT_URL + "/request-history/request/" + this.idRequest)
                    .success(function (data) {

                        var jsonObjectIndex = {};

                        data.forEach(function (requests) {
                            if (isNaN(requests.employee)) {
                                jsonObjectIndex[requests.employee._id] = requests.employee;
                            } else {
                                requests.employee = jsonObjectIndex[requests.employee];
                            }
                        });

                        var jsonObjectIndex = {};

                        data.forEach(function (requests) {
                            if (isNaN(requests.userRequest)) {
                                jsonObjectIndex[requests.userRequest._id] = requests.userRequest;
                            } else {
                                requests.userRequest = jsonObjectIndex[requests.userRequest];
                            }
                        });

                        var jsonObjectIndex = {};

                        data.forEach(function (requests) {
                            if (isNaN(requests.userResponsible)) {
                                jsonObjectIndex[requests.userResponsible._id] = requests.userResponsible;
                            } else {
                                requests.userResponsible = jsonObjectIndex[requests.userResponsible];
                            }
                        });

                        var jsonObjectIndex = {};

                        data.forEach(function (requests) {
                            if (isNaN(requests.creationDateFormats)) {
                                jsonObjectIndex[requests.creationDateFormats._id] = requests.creationDateFormats;
                            } else {
                                requests.creationDateFormats = jsonObjectIndex[requests.creationDateFormats];
                            }
                        });

                        var jsonObjectIndex = {};

                        data.forEach(function (requests) {
                            if (isNaN(requests.request)) {
                                jsonObjectIndex[requests.request._id] = requests.request;
                            } else {
                                requests.request = jsonObjectIndex[requests.request];
                            }
                        });

                        this.requestHistory = data;
                    })
                    .error(function (data) {

                    });
            },
            arrayObjectIndexOf: function (myArray, searchTerm, property) {
                for (var i = 0, len = myArray.length; i < len; i++) {
                    if (myArray[i][property] === searchTerm) return i;
                }
                return -1;
            },
            getUserInSession: function () {
                this.$http.get(ROOT_URL + "/user")
                    .success(function (data) {
                        this.user = data;
                    })
                    .error(function (data) {
                        showAlert("Ha habido un error al obtener al usuario en sesion", {type: 3});
                    });
            },
            getRequestInformation: function () {
                this.$http.get(ROOT_URL + "/requests/" + this.idRequest)
                    .success(function (data) {
                        this.request = data;

                        this.getRequestOrder(this.request.idRequest);
                        this.getRequestPurchaseInvoices(this.request.idRequest);
                        this.getRequestHistory();
                    })
                    .error(function (data) {

                    });
            },
            openModalSelectEstimation: function (estimation) {
                this.priceEstimations = JSON.parse(JSON.stringify(estimation));
                this.selectedEstimation.idEstimation = estimation.idPriceEstimation;
                this.selectedEstimation.idRequest = this.idRequest;
                this.selectedEstimation.justify = '';
                $("#modalSelectEstimation").modal("show");
            },
            selectedEstimations: function () {
                if (this.selectedEstimation.justify.length > 0) {
                    this.$http.post(ROOT_URL + "/estimations/validate", JSON.stringify(this.selectedEstimation)).success(function (data) {

                        console.log(data);

                        if(data == 1){
                            $("#modalSelectEstimation").modal("hide");
                            $("#modalSendValidationLevel").modal("show");
                        }else if(data == 2){
                            $("#modalSelectEstimation").modal("hide");
                            $("#modalSendValidationFinantialPlaning").modal("show");
                        }else if(data == 3){
                            this.autorizeEstimation(data);
                        }
                    }).error(function () {
                        showAlert("Error al generar la solicitud", {type: 3});
                    })
                } else {
                    showAlert("Es necesario llenar una justificacion", {type: 3});
                }
            },
            autorizeEstimation: function (option) {
                this.$http.post(ROOT_URL + "/estimations/authorize-estimation/"+option, JSON.stringify(this.selectedEstimation)).success(function (data) {
                                showAlert("Solicitud autorizada");
                                $("#modalSelectEstimation").modal("hide");
                                $("#modalSendValidationLevel").modal("hide");
                                $("#modalSendValidationFinantialPlaning").modal("hide");
                                this.getRequestInformation();
                            }).error(function () {
                                showAlert("Error al generar la solicitud", {type: 3});
                            });
            },
            rejectReuquestModal: function () {
                this.selectedEstimation.idRequest = this.idRequest;
                this.selectedEstimation.rejectJustify = '';
                $("#justificarrechazo").modal("show");
            },
            rejectRequest: function () {
                if (this.selectedEstimation.rejectJustify.length > 0) {
                    this.$http.post(ROOT_URL + "/requests/reject", JSON.stringify(this.selectedEstimation)).success(function (data) {
                        $("#justificarrechazo").modal("hide");
                        showAlert("Solicitud rechazada con exito");
                        this.getRequestInformation();
                    }).error(function () {
                        showAlert("Error al generar la solicitud", {type: 3});
                    })

                } else {
                    showAlert("Es necesario llenar la justificación del rechazo", {type: 3});
                }
            },
            getProviders: function () {
                this.$http.get(ROOT_URL + "/providers").success(function (data) {
                    this.providers = data;
                    this.createSelectForProviders(data);
                }).error(function () {
                    showAlert("Error al obtener la informacion de los proveedores", {type: 3});
                });
            },
            selectProvider: function () {
                this.getProviderAddress(this.provider.idProvider);
                this.getProviderContact(this.provider.idProvider);
            },
            getProviderAddress: function (idProvider) {
                this.$http.get(ROOT_URL + "/provider-address/provider/" + idProvider).success(function (data) {
                    this.providerAddress = data;
                    this.provider.providerAddress = this.providerAddress[0];
                }).error(function () {
                    showAlert("Error al obtener informacion de direccion del proveedor", {type: 3});
                })
            },
            getProviderContact: function (idProvider) {
                this.$http.get(ROOT_URL + "/provider-contact/provider/" + idProvider).success(function (data) {
                    this.providerContact = data;
                    this.provider.providerContact = this.providerContact[0];
                }).error(function () {
                    showAlert("Error al obtener informacion de contacto del proveedor", {type: 3});
                })
            },
            getDistributors: function () {
                this.$http.get(ROOT_URL + "/distributors").success(function (data) {
                    this.distributors = data;
                }).error(function () {
                    showAlert("Error al obtener la informacion de las empresas", {type: 3});
                })
            },
            openModalOrdenCompra: function () {
                this.provider = {};
                this.distributor = {};
                this.contactCel = "";
                this.contactName = "";
                this.contactRole = "";
                this.orderProducts = [];
                this.requestOrderProducts.description = '';
                this.requestOrderProducts.quantity = 1;
                this.requestOrderProducts.price = 0;
                this.amount = 0;
                this.iva = 0;
                this.totalAmount = 0;
                this.providerAddress = [];
                this.providerContact = [];
                this.startDate = '';
                this.getProviders();
                this.getDistributors();
                $("#ordendecompra").modal("show");
            },
            activateDateTimePickerApplicationDate: function () {
                this.dateTimePickerStartDate = $('#applicationDate').datetimepicker({
                    locale: 'es',
                    format: 'DD-MM-YYYY',
                    useCurrent: false
                }).data();
            },
            addProductOrdered: function () {
                var object = {
                    description: '',
                    quantity: 1,
                    price: 0
                };
                if (this.requestOrderProducts.description.length > 0 && this.requestOrderProducts.quantity > 0 && Number(this.requestOrderProducts.price.replace(",", "")) > 0) {
                    object = JSON.parse(JSON.stringify(this.requestOrderProducts));
                    this.orderProducts.push(object);
                    this.requestOrderProducts.description = '';
                    this.requestOrderProducts.quantity = 1;
                    this.requestOrderProducts.price = '0';

                    this.sumAmountPriceOrder();
                    this.amount = Number(this.var1.toFixed(2));
                    this.calculateIva(this.amount);
                    this.iva = Number(this.var2.toFixed(2));
                    this.calculateTotalAmount(this.amount, this.iva);
                    this.totalAmount = Number(this.var3.toFixed(2));
                } else {
                    showAlert("Es necesario llenar los campos con datos validos: Descripción, Cantidad, Precio", {type: 3});
                }
            },
            deleteProductOrdered: function (requestProduct) {
                this.orderProducts.$remove(requestProduct);

                this.sumAmountPriceOrder();
                this.amount = Number(this.var1.toFixed(2));
                this.calculateIva(this.amount);
                this.iva = Number(this.var2.toFixed(2));
                this.calculateTotalAmount(this.amount, this.iva);
                this.totalAmount = Number(this.var3.toFixed(2));
            },
            sumAmountPriceOrder: function () {
                var amount = 0;
                var self = this;

                this.var1 = 0;
                this.orderProducts.forEach(function (product) {
                    self.var1 += Number(product.price);
                });
            },
            calculateIva: function (amount) {
                var iva = 0;
                var percentage = 0.16;
                this.var2 = amount * percentage;
            },
            calculateTotalAmount: function (amount, iva) {
                var totalAmount = 0;
                this.var3 = amount + iva;
            },
            sendOrderRequestBuy: function () {

                var aux2 = false;
                if (this.provider.idProvider > 0) {
                    this.sRequestOrderProduct.provider = this.provider;
                    aux2 = true;
                } else {
                    showAlert("Es necesario seleccionar un proveedor", {type: 3});
                }

                var aux3 = false;
                if (this.distributor.idDistributor > 0) {
                    this.sRequestOrderProduct.distributor = this.distributor;
                    aux3 = true;
                } else {
                    showAlert("Es necesario seleccionar una empresa", {type: 3});
                }

                var aux4 = false;
                if (this.contactCel.length > 0 && this.contactName.length > 0 && this.contactRole.length > 0) {
                    this.sRequestOrderProduct.contactCel = this.contactCel;
                    this.sRequestOrderProduct.contactName = this.contactName;
                    this.sRequestOrderProduct.contactRole = this.contactRole;
                    aux4 = true;
                } else {
                    showAlert("Es necesario llenar los datos de contacto del emisor", {type: 3});
                }

                var aux5 = false;
                if (this.orderProducts.length > 0) {
                    this.sRequestOrderProduct.orderProducts = this.orderProducts;
                    this.sRequestOrderProduct.amount = this.amount;
                    this.sRequestOrderProduct.iva = this.iva;
                    this.sRequestOrderProduct.totalAmount = this.totalAmount;
                    aux5 = true;
                } else {
                    showAlert("Es necesario que por lo menos llenes una descripción, cantidad y precio, en la lista de detalle de la orden de compra"
                        , {type: 3});
                }

                if (aux2 == true && aux3 == true && aux4 == true && aux5 == true) {
                    this.sRequestOrderProduct.idRequest = this.idRequest;

                    this.$http.post(ROOT_URL + "/order-documents-request/save-order-documents", JSON.stringify(this.sRequestOrderProduct)).success(function (data) {
                        $("#ordendecompra").modal("hide");
                        this.getRequestInformation();
                        showAlert("Orden de compra generada con exito");
                    }).error(function () {
                        showAlert("Error al generar la orden de compra", {type: 3});
                    });
                } else {
                    showAlert("No entra falta algun campo", {type: 3});
                }
            },
            getRequestOrder: function (idRequest) {
                this.$http.get(ROOT_URL + "/order-documents-request/request/" + idRequest).success(function (data) {
                    this.requestOrder = data;
                }).error(function () {
                });
            },
            getRequestPurchaseInvoices: function (idRequest) {
                this.$http.get(ROOT_URL + "/purchase-invoice/request/" + idRequest).success(function (data) {
                    this.purchaseInvoice = data;
                }).error(function () {
                });
            },
            cancelRequestOrder: function () {
                this.$http.post(ROOT_URL + "/order-documents-request/" + this.requestOrder.idRequestOrderDocument).success(function (data) {
                    this.requestOrder = null;
                    showAlert("Orden de compra cancelada exitosamente");
                }).error(function () {
                    showAlert("Error al generar la cancelacion de la orden de compra", {type: 3});
                });
            },

            setFile: function (event, documentType) {
                var self = this;
                var file = event.target.files[0];

                if (this.validateFiles(file, documentType)) {

                    var reader = new FileReader();

                    reader.onload = (function (theFile) {
                        return function (e) {

                            switch (documentType) {
                                case 1:
                                    self.fileVoucher.voucherPdfFile = {
                                        name: theFile.name,
                                        size: theFile.size,
                                        type: theFile.type,
                                        dataUrl: e.target.result
                                    };
                                    self.isPdfFile = true;
                                    break;
                                case 2:
                                    self.fileVoucher.voucherXmlFile = {
                                        name: theFile.name,
                                        size: theFile.size,
                                        type: theFile.type,
                                        dataUrl: e.target.result
                                    };
                                    self.isXmlFile = true;
                                    break;
                                case 3:
                                    self.currentRefundConcept.otroFile = {
                                        name: theFile.name,
                                        size: theFile.size,
                                        type: theFile.type,
                                        dataUrl: e.target.result
                                    };
                            }
                        };
                    })(file);
                    reader.readAsDataURL(file);
                }
            },
            validateFiles: function (file, documentType) {

                var isValidated = false;

                switch (documentType) {
                    case 1:
                        isValidated = this.validateFilePDF(file);
                        break;
                    case 2:
                        isValidated = this.validateFileXML(file);
                        break;
                    case 3:
                        isValidated = this.validateFileOtro(file);
                }

                return isValidated;
            },
            validateFilePDF: function (file) {
                if (file.type !== 'application/pdf') {
                    event.target.value = null;
                    showAlert("Tipo de archivo no admitido", {type: 3});
                    return false;
                }

                return true;
            },
            validateFileXML: function (file) {
                if (file.type !== 'text/xml') {
                    event.target.value = null;
                    showAlert("Tipo de archivo no admitido", {type: 3});
                    return false;
                }

                return true;
            },
            saveFiles: function () {

                if (this.isPdfFile == true && this.isXmlFile == true) {
                    this.uploadVoucherXmlFile();
                } else {
                    if (this.isXmlFile == true) {
                        if (this.isPdfFile == false) {
                            showAlert("Es necesario que subas un archivo pdf", {type: 3});
                        } else {
                            this.uploadVoucherXmlFile();
                        }
                    } else {
                        if (this.isPdfFile == true) {
                            $("#activadatos").modal("show");
                            $("#pdfyxml").modal("hide");
                            this.getProviders();
                            this.isXmlFile = false;
                        } else {
                            showAlert("Debes subir un archivo pdf o xml", {type: 3});
                        }
                    }
                }

            },
            hideRefundConceptDocumentsModal: function () {
                $("#refundConceptDocumentsModal").modal("hide");
            },
            uploadVoucherXmlFile: function () {

                this.$http.post(ROOT_URL + '/refund-concepts/voucher-xml-data', this.fileVoucher.voucherXmlFile)
                    .success(function (data) {
                        this.billInformation = data;
                        $("#activadatos").modal("show");
                        $("#pdfyxml").modal("hide");
                        this.getProviders();

                    })
                    .error(function (data) {
                    });

            },
            opneModalUploadFile: function () {
                $("#activadatos").modal("hide");
                $("#pdfyxml").modal("show");
                this.getProviders();
            },
            createSelectForProviders : function (providers) {

                providers.forEach(function (provider) {
                    provider.providerName = provider.providerName.replace(/:/g, ' ');
                });

                var self = this;

                this.providerSelectize = $('#select-provider-1').selectize({
                    maxItems: 1,
                    valueField: 'idProvider',
                    labelField: 'providerName',
                    searchField: 'providerName',
                    options: providers,
                    onChange: function (value) {
                        self.providerAccounts(value);
                    }
                });

                return this.providerSelectize;
            },
            openModalActivados: function () {
                this.billInformation= null;
                this.fileVoucher = {
                    voucherPdfFile: null,
                    voucherXmlFile: null
                };
                this.providerAccount = {};
                this.isXmlFile = false;
                this.isPdfFile = false;
                $("#activadatos").modal("show");
                this.getProviders();
            },
            providerAccounts: function (idProvider) {
                this.purchaseInvoices.idProvider = idProvider;
                this.$http.get(ROOT_URL + "/providers-accounts/provider/" + idProvider).success(function (data) {
                    this.providerAccountList = data;
                }).error(function () {
                    showAlert("Error al obtener información  de la cuenta del proveedor", {type: 3});
                });
            },
            savePruchaseInvoices: function () {
                this.purchaseInvoices.providerAccount = this.providerAccount.account;
                this.purchaseInvoices.fileVoucher.voucherPdfFile = this.fileVoucher.voucherPdfFile;
                this.purchaseInvoices.fileVoucher.voucherXmlFile = this.fileVoucher.voucherXmlFile;
                this.purchaseInvoices.billInformation = this.billInformation;
                this.purchaseInvoices.idRequest = this.idRequest;
                this.purchaseInvoices.isXmlFile = this.isXmlFile;

                if (this.purchaseInvoices.providerAccount != null && this.purchaseInvoices.fileVoucher.voucherPdfFile != null && this.purchaseInvoices.idProvider > 0 && this.purchaseInvoices.billInformation != null){

                    this.$http.post(ROOT_URL + "/purchase-invoice/request/file", JSON.stringify(this.purchaseInvoices)).success(function (data) {
                        $("#activadatos").modal("hide");
                        this.getRequestInformation();
                        showAlert("Comprobante guardado con éxito");
                    }).error(function () {
                        showAlert("Error al generar guardar el comprobante", {type: 3});
                    })

                }else{

                    if(this.purchaseInvoices.fileVoucher.voucherPdfFile == null){
                        showAlert("Es necesario que carges el archivo PDF o XML", {type: 3});
                    }

                    if(this.purchaseInvoices.providerAccount == null){
                        showAlert("Es necesario que selecciones una cuenta de proveedor", {type: 3});
                    }


                    if(this.purchaseInvoices.idProvider == null){
                        showAlert("Es necesario que selecciones un proveedor", {type: 3});
                    }

                    if(this.purchaseInvoices.billInformation != null){
                        if (this.isXmlFile == false){
                            if(this.purchaseInvoices.billInformation.folio != null){
                                showAlert("Es necesario que ingreses un folio", {type: 3});
                            }
                            if(this.purchaseInvoices.billInformation.concept != null){
                                showAlert("Es necesario que ingreses un concepto", {type: 3});
                            }
                            if(this.purchaseInvoices.billInformation.moneda != null){
                                showAlert("Es necesario que ingreses un moneda", {type: 3});
                            }
                            if(this.purchaseInvoices.billInformation.tipoCambio != null){
                                showAlert("Es necesario que ingreses un tipo de cambio", {type: 3});
                            }
                            if(this.purchaseInvoices.billInformation.amountNotIva != null){
                                showAlert("Es necesario que ingreses un monto sin Iva", {type: 3});
                            }
                            if(this.purchaseInvoices.billInformation.amountWithIva != null){
                                showAlert("Es necesario que ingreses un monto con Iva", {type: 3});
                            }
                        }
                    }else{
                        showAlert("Es necesario que llenes los campos requeridos: Número de factura, Concepto factura, Monto con iva, Monto sin iva, Tipo de moneda, Tipo de cambio, Monto total", {type: 3});
                    }

                }
            },
            deletePurchaseInvoice: function () {
                this.$http.post(ROOT_URL + "/purchase-invoice/"+this.purchaseInvoice.idPurchaseInvoices).success(function (data) {
                    this.purchaseInvoice = null;
                    showAlert("Comprobante eliminado exitosamente");
                }).error(function () {
                   showAlert("Error al eliminar el comprobante", {type: 3});
                });
            },
            setFile1: function (event) {
                this.clearEstimation();
                var self = this;
                var file = event.target.files[0];
                var object = {
                    identificador: '',
                    file: "",
                    amount: ""
                };

                if (this.validateFilePDF(file)) {

                    var reader = new FileReader();

                    reader.onload = (function (theFile) {
                        return function (e) {
                            object.file = {
                                name: theFile.name,
                                size: theFile.size,
                                type: theFile.type,
                                dataUrl: e.target.result
                            };
                        };
                    })(file);
                    reader.readAsDataURL(file);
                    object.identificador = 1;
                    object.amount = this.amount1;

                    this.estimations.push(object);
                    this.button1 = true;
                }
            },
            setFile2: function (event) {
                this.clearEstimation();
                var self = this;
                var file = event.target.files[0];
                var object = {
                    identificador: '',
                    file: "",
                    amount: ""
                };

                if (this.validateFilePDF(file)) {

                    var reader = new FileReader();

                    reader.onload = (function (theFile) {
                        return function (e) {
                            object.file = {
                                name: theFile.name,
                                size: theFile.size,
                                type: theFile.type,
                                dataUrl: e.target.result
                            };
                        };
                    })(file);
                    reader.readAsDataURL(file);
                    object.identificador = 2;
                    object.amount = this.amount2;

                    this.estimations.push(object);
                    this.button2 = true;
                }
            },
            setFile3: function (event) {
                this.clearEstimation();
                var self = this;
                var file = event.target.files[0];
                var object = {
                    identificador: '',
                    file: "",
                    amount: ""
                };

                if (this.validateFilePDF(file)) {

                    var reader = new FileReader();

                    reader.onload = (function (theFile) {
                        return function (e) {
                            object.file = {
                                name: theFile.name,
                                size: theFile.size,
                                type: theFile.type,
                                dataUrl: e.target.result
                            };
                        };
                    })(file);
                    reader.readAsDataURL(file);
                    object.identificador = 3;
                    object.amount = this.amount3;

                    this.estimations.push(object);
                    this.button3 = true;
                }
            },
            removeDocument: function (id) {
                var self = this;
                this.estimations.forEach(function (fileEstimations) {
                    if(id == fileEstimations.identificador){
                        self.estimations.$remove(fileEstimations);
                    }
                });

                switch (id){
                    case 1:
                        this.button1 =  false;
                        break;
                    case 2:
                        this.button2 =  false;
                        break;
                    case 3:
                        this.button3 =  false;
                        break;
                }
            },
            sendToPaymanagment:function(){
                this.$http.get(ROOT_URL + "/requests/send-to-paymanagement?idRequest="+this.idRequest).success(function () {
                    showAlert("La solicitud sera enviada al administrador se pago");
                    $("#botonenviar").modal("hide");
                    this.getRequestInformation();
                }).error(function () {
                    showAlert("Error al enviar", {type: 3});
                });
            },
            clearEstimation: function () {
                this.estimation = {
                    amount: '',
                    provider: '',
                    account: '',
                    currency: '',
                    rate: '',
                    file: ''
                };
            },
            saveEstimations: function () {
                var self = this;
                this.estimations.forEach(function (estimation) {
                    self.saveEstimation(estimation);
                });
                showAlert("Cotizaciones cargadas exitosamente");
                setTimeout(function(){ location.reload(); }, 3000);
            },
            saveEstimation: function (estimation) {
                this.$http.post(ROOT_URL + '/estimations/request/' + this.idRequest, estimation).success(function (data) {
                }).error(function () {
                    showAlert("Error al agregar cotización", {type: 3});
                })
            }
        },
        filters: {
            separate: function (value) {
                return value.replace(/:/g, ' ');
            },
            currencyDisplay: {
                read: function (val) {
                    if (typeof val == 'number') {
                        return val.formatMoney(2, '');
                    }
                },
                write: function (val, oldVal) {
                    var number = +val.replace(/[^\d.]/g, '');
                    return isNaN(number) ? 0 : parseFloat(number.toFixed(2));
                }
            }
        }
    });

</script>

        <script type="text/javascript">
            function format(input) {
                var num = input.value.replace(/\,/g, '');
                if (!isNaN(num)) {
                    num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g, '$1,');
                    num = num.split('').reverse().join('').replace(/^[\,]/, '');
                    num = num.split('').join('').replace(/^[\,]/, '');
                    var inicio = num.substring(0, 1);
                    if (inicio == '0') {
                        showAlert("Corregir cantidad", {type: 3});
                        input.value = '';
                    } else {
                        input.value = num.split('').join('').replace(/^[\,]/, '');
                    }
                }

                else {
                    showAlert("Solo se permiten números", {type: 3});
                    input.value = '';
                }
            }

            function cleanField(obj) {
                var inicial = obj.value;
                if (obj.value == '0' || obj.value == '0.00') {
                    obj.value = '';
                } else {
                    return false;
                }
            }
            function cleanFieldProduct(obj) {
                var inicial = obj.value;
                if (obj.value == '1') {
                    obj.value = '';
                } else {
                    return false;
                }
            }
            function onlyNumbers(e) {
                var key = window.Event ? e.which : e.keyCode
                return (key >= 48 && key <= 57)
            }
            function backHistory() {
                history.back();
            }
            function ponerCeros(obj) {
                var contar = obj.value;
                var min = contar.length - 3;
                var max = contar.length;

                if (obj.value == "" || obj.value == null) {
                    obj.value = "";
                } else {
                    if (max >= 1 && max < 40) {
                        var extraer = contar.substring(min, max);
                        if (extraer == '.00') {
                            contar = contar.replace('.,', ',');
                            contar = contar.replace(',.', ',');
                            format(input);
                        } else {
                            contar = contar.replace('.,', ',');
                            contar = contar.replace(',.', ',');
                            format(input);
                        }
                    }
                }
            }

        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            textarea {
                resize: none;
            }
        </style>
        <style>
            textarea {
                resize: none;
            }

            label.circlered {
                width: 20px;
                height: 20px;
                -moz-border-radius: 50%;
                -webkit-border-radius: 50%;
                border-radius: 50%;
                background: #FF0000;
            }

            label.circleyel {
                width: 20px;
                height: 20px;
                -moz-border-radius: 50%;
                -webkit-border-radius: 50%;
                border-radius: 50%;
                background: #FFFF06;
            }

            label.circlegre {
                width: 20px;
                height: 20px;
                -moz-border-radius: 50%;
                -webkit-border-radius: 50%;
                border-radius: 50%;
                background: #00FF00;
            }

            #detalleGreen {
                color: green;
                font-size: 30px;
            }

            #detalleGreen1 {
                color: green;
                font-size: 30px;
            }

            #detalleGreen2 {
                color: green;
                font-size: 30px;
            }

            hr {
                height: 100px;
            }
        </style>
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
    <jsp:body>
        <!--INICIA PANTALLA DE SOLICITUD/VIGENTE PAGINA 25-->

        <div id="content">
            <div class="loading" v-if="request.priceEstimationsList.length==0">
            </div>
            <div class="row">
                <div class="col-md-8">
                    <h2 v-if="request.requestType.idRequestType == 1">Detalle de solicitud/Vigente
                        <label class="circleyel" ></label>
                    </h2>
                    <h2 v-if="request.requestType.idRequestType == 2">Detalle de solicitud/Finalizada
                        <label class="circlegre" ></label>
                    </h2>
                    <h2 v-if="request.requestType.idRequestType == 3">Detalle de solicitud/Rechazada
                        <label class="circlered" ></label>
                    </h2>
                </div>
                <div class="col-md-4 text-right" style="margin-top: 10px">
                    <label>Nombre de usuario</label>
                    <p>
                        <span class="label label-default">{{user.dwEmployee.employee.fullName}}</span>
                    </p>
                </div>
            </div>
            <br>
            <div class="panel panel-default" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos del solicitante</b></h5>
                            <table class="table table-striped">
                                <thead style="background-color: #BDBDBD">
                                <th class="col-md-4">Nombre</th>
                                <th class="col-md-4">Area</th>
                                <th class="col-md-4">Fecha de solicitud</th>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="col-md-3"><u>{{request.employees.fullName}}</u></td>
                                    <td class="col-md-3"><u>{{request.dwEmployees.dwEnterprise.area.areaName}}</u></td>
                                    <td class="col-md-3"><u>{{request.creationDateFormats.simpleDate}}</u></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel default" style="background-color: #F2F2F2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Datos de solicitud</b></h5>
                            <table class="table table-striped">
                                <thead style="background-color: #BDBDBD">
                                <th class="col-md-3">Linea de negocio</th>
                                <th class="col-md-3">Empresa</th>
                                <th class="col-md-3">Centro de costos</th>
                                <th class="col-md-3">Concepto</th>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="col-md-3"><u>{{request.distributorCostCenter.cBussinessLine.acronym}}</u></td>
                                    <td class="col-md-3"><u>{{request.distributorCostCenter.distributors.acronyms}}</u></td>
                                    <td class="col-md-3"><u>{{request.distributorCostCenter.costCenter.name}}</u></td>
                                    <td class="col-md-3"><u>{{request.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</u></td>
                                </tr>
                                </tbody>
                            </table>
                            <table class="table table-striped">
                                <thead style="background-color: #BDBDBD">
                                <th class="col-md-4">
                                    <div class="col-md-8 text-center">Lista de productos</div>
                                    <div class="col-md-4 text-center">Cantidad</div>
                                </th>
                                <th class="col-md-8">Justificacion</th>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="col-md-4">
                                        <div class="col-md-12" v-for="product in request.requestProductsList">
                                            <div class="col-md-8 text-center">
                                                {{product.roleProductRequest.cProductsRequest.productRequestName}}
                                            </div>
                                            <div class="col-md-4 text-center">{{product.quantity}}</div>
                                        </div>
                                    </td>
                                    <td class="col-md-8" rowspan="8">
                                        <textarea class="form-control" rows="4" v-model="request.reason"
                                                  readonly disabled>
                                            </textarea>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <br>

            <div class="panel panel-default" style="background-color: #F2F2F2" v-if="request.priceEstimationsList == null">
                <div class="panel-heading"><b style="color: black">Cotizaciones</b></div>
                <div class="panel-body">
                    <div class="col-md-12">
                        <div class="row">
                            <table class="table table-striped">
                                <tr class="col-md-12">
                                    <td class="col-md-2"><b>Cotización 1</b></td>
                                    <td class="col-md-5"></td>
                                    <td class="col-md-3">Monto cotización sin IVA</td>
                                    <td class="col-md-2">
                                        <input v-model="amount1 | currencyDisplay" class="form-control" type="text" placeholder="$"
                                               onclick="return cleanField(this)"
                                               onkeypress="return validateFloatKeyPress(this,event)"
                                               oninput="format(this)" onblur="ponerCeros(this)"
                                               maxlength="14" :disabled="button1 == true" />
                                    </td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2">Documento</td>
                                    <td class="col-md-6">
                                        <input @change="setFile1($event)" type="file"
                                               class="form-control" :disabled="amount1 == '' || button1 == true"
                                               required />
                                    </td>
                                    <td class="col-md-3"></td>
                                    <td class="col-md-1" v-if="button1 == true">
                                        <button type="button" class="btn btn-warning" @click="removeDocument(1)">Cambiar factura</button>
                                    </td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2"><b>Cotización 2</b></td>
                                    <td class="col-md-5"></td>
                                    <td class="col-md-3">Monto cotización sin IVA</td>
                                    <td class="col-md-2">
                                        <input v-model="amount2 | currencyDisplay" class="form-control" type="text" placeholder="$"
                                               onclick="return cleanField(this)"
                                               onkeypress="return validateFloatKeyPress(this,event)"
                                               oninput="format(this)" onblur="ponerCeros(this)"
                                               maxlength="14" :disabled="button2 == true"/></td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2">Documento</td>
                                    <td class="col-md-6">
                                        <input @change="setFile2($event)" type="file"
                                               class="form-control" :disabled="amount2 == '' || button2 == true"
                                               required />
                                    </td>
                                    <td class="col-md-3"></td>
                                    <td class="col-md-1" v-if="button2 == true">
                                        <button type="button" class="btn btn-warning" @click="removeDocument(2)">Cambiar factura</button>
                                    </td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2"><b>Cotización 3</b></td>
                                    <td class="col-md-5"></td>
                                    <td class="col-md-3">Monto cotización sin IVA</td>
                                    <td class="col-md-2">
                                        <input v-model="amount3 | currencyDisplay" class="form-control" type="text" placeholder="$"
                                               onclick="return cleanField(this)"
                                               onkeypress="return validateFloatKeyPress(this,event)"
                                               oninput="format(this)" onblur="ponerCeros(this)"
                                               maxlength="14" :disabled="button3 == true"/></td>
                                </tr>
                                <tr class="col-md-12">
                                    <td class="col-md-2">Documento</td>
                                    <td class="col-md-6">
                                        <input @change="setFile3($event)" type="file"
                                               class="form-control" :disabled="amount3 == '' || button3 == true"
                                               required />
                                    </td>
                                    <td class="col-md-3"></td>
                                    <td class="col-md-1" v-if="button3 == true">
                                        <button type="button" class="btn btn-warning" @click="removeDocument(3)">Cambiar factura</button>
                                    </td>
                                </tr>

                            </table>

                        </div>
                    </div>
                </div>
            </div>

            <div class="row" v-if="estimations.length == 3">
                <div class="col-md-12">
                    <div class="col-md-10">
                    </div>
                    <div class="col-md-2">
                        <button type="button" class="btn btn-success" @click="saveEstimations()">Agregar cotizaciones</button>
                    </div>
                </div>
            </div>

            <div class="panel panel default" style="background-color: #F2F2F2" v-if="request.idRequestStatus != 1 && request.idRequestStatus != 2">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Informacion</b></h5>
                            <table class="table table-striped">
                                <thead style="background-color: #BDBDBD">
                                <th class="col-md-4">Usuario</th>
                                <th class="col-md-3">Fecha</th>
                                <th class="col-md-1"></th>
                                <th class="col-md-4">Estado</th>
                                </thead>
                                <tbody>
                                <tr v-for="history in requestHistory">
                                    <td class="col-md-4">{{history.userResponsible.dwEmployee.employee.fullName}}</td>
                                    <td class="col-md-1">{{history.creationDateFormats.dateNumber}}</td>
                                    <td class="col-md-1">
                                        <span class="glyphicon glyphicon-triangle-bottom"
                                              style="color: #7A7A7A; font-size: 200%"
                                              v-if="history.cRequestStatus.idRequestStatus == 1"></span>
                                        <span class="glyphicon glyphicon-triangle-bottom"
                                              style="color: #DF9A1B; font-size: 200%"
                                              v-if="history.cRequestStatus.idRequestStatus == 2"></span>
                                        <span class="glyphicon glyphicon-triangle-bottom"
                                              style="color: #EE0909; font-size: 200%"
                                              v-if="history.cRequestStatus.idRequestStatus == 7"></span>
                                        <span class="glyphicon glyphicon-triangle-bottom"
                                              style="color: #457a1a; font-size: 200%"
                                              v-if="history.cRequestStatus.idRequestStatus == 4"></span>
                                        <span class="glyphicon glyphicon-triangle-bottom"
                                              style="color: #457a1a;  font-size: 200%"
                                              v-if="history.cRequestStatus.idRequestStatus == 5"></span>
                                        <span class="glyphicon glyphicon-triangle-bottom"
                                              style="color: #66afe9;  font-size: 200%"
                                              v-if="history.cRequestStatus.idRequestStatus == 3"></span>
                                        <span class="glyphicon glyphicon-triangle-bottom"
                                              style="color: #00FF00;  font-size: 200%"
                                              v-if="history.cRequestStatus.idRequestStatus == 6"></span>
                                    </td>
                                    <td class="col-md-6">{{history.cRequestStatus.requestStatus}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <br>
                    <br>
                </div>
            </div>

            <br>

            <div v-for="(index, estimation) in request.priceEstimationsList" v-if="estimation.idEstimationStatus != 3">
                <div class="panel panel-default" style="background-color: #F2F2F2">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-8"><br>
                                <h5><b style="color: black">Cotizacion {{index+1}}</b></h5>
                            </div>
                            <div class="col-md-2">
                                <label style="margin-top: 30px">Monto cotizado sin IVA </label>
                            </div>
                            <div class="col-md-2">
                                <input class="form-control" type="text" placeholder="$" style=" margin-top: 15px"
                                       v-model="estimation.amount | currency" maxlength="14" disabled/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-12" style="background-color: #BDBDBD">
                                <h6><b>Documento</b></h6>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-3"><br>
                                <h6>{{estimation.fileName}}</h6>
                            </div>
                            <div class="col-md-2"><br>
                                <a class="btn btn-primary btn-sm"
                                   :href="downloadUrl + estimation.idPriceEstimation"
                                   data-toggle="tooltip" data-placement="top" title="Descargar">
                                    Descargar
                                </a>
                            </div>
                            <div class="col-md-6">
                                <br>
                            </div>
                            <div class="col-md-1">
                                <br>
                                <button class="btn btn-success btn-sm"
                                        v-if="estimation.idEstimationStatus == 1 && request.idRequestStatus != 7"
                                        @click="openModalSelectEstimation(estimation)">
                                    Elegir
                                </button>
                                <span id="detalleGreen" v-if="estimation.idEstimationStatus == 2"
                                      class="glyphicon glyphicon-ok" style="margin-left: 65%"></span>
                            </div>
                            <br>
                        </div>
                    </div>
                    <br>
                </div>
            </div>
            <div class="panel panel-default" style="background-color: #F2F2F2"
                 v-if="request.reasonResponsible.length > 0">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12" style="background-color: #BDBDBD">
                            <h6><b>Justificacion</b></h6>
                        </div>
                        <br>
                        <div class="col-md-12">
                            <p>
                                {{request.reasonResponsible}}
                            </p>
                        </div>
                        <br>
                    </div>
                </div>
            </div>

            <div class="panel panel default" style="background-color: #f2f2f2" v-if="requestOrder != null">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Orden de compra</b></h5>
                            <table class="table table-striped">
                                <thead>
                                <th class="col-md-4" style="background-color: #aaaaaa">Numero de orden de compra
                                </th>
                                <th class="col-md-4" style="background-color: #aaaaaa">Monto total</th>
                                <th class="col-md-3" style="background-color: #aaaaaa">Fecha de generacion
                                </th>
                                <th class="col-md-1" style="background-color: #aaaaaa"></th>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="col-md-4">{{requestOrder.idRequestOrderDocument}}</td>
                                    <td class="col-md-4">{{requestOrder.totalAmount | currency}}</td>
                                    <td class="col-md-3">{{requestOrder.creationDateFormats.simpleDate}}</td>
                                    <td class="col-md-1">
                                        <button type="button" v-if="purchaseInvoice == null" class="btn btn-danger btn-sm"
                                                @click="cancelRequestOrder()">Cancelar
                                        </button>
                                        <button type="button" v-if="purchaseInvoice != null" class="btn btn-info btn-sm"
                                                @click="descarga(requestOrder.idRequestOrderDocument)">Descargar
                                        </button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div class="panel panel default" style="background-color: #f2f2f2" v-if="purchaseInvoice != null">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <h5><b style="color: black">Comprobante</b></h5>
                            <div class="col-xs-5">
                                <label>Proveedor</label><br><br>
                                <p>{{purchaseInvoice.provider.providerName | separate}}</p>
                            </div>
                            <div class="col-xs-2">
                                <label>Monto total</label><br><br>
                                <input class="form-control" placeholder="$"
                                       v-model="purchaseInvoice.totalAmount | currency" disabled>
                            </div>
                            <div class="col-xs-4"></div>
                            <div class="col-xs-1">
                                <label></label><br><br>
                                <button class="btn btn-danger btn-sm" style="margin-top: 10%" @click="deletePurchaseInvoice()" v-if="request.idRequestStatus == 2">
                                    Eliminar
                                </button>
                                <br><br>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-12">
                            <div class="col-md-7">
                                <table class="table">
                                    <thead>
                                        <th>Archivo</th>
                                        <th>Descargar</th>
                                    </thead>
                                    <tbody>
                                        <tr v-for="file in purchaseInvoice.purchaseInvoicesFiles">
                                            <td>{{file.fileName}}</td>
                                            <td><a class="btn btn-default btn-sm"
                                                   :href="downloadUrlInvoices + file.idPurchaseInvoicesFiles"
                                                   data-toggle="tooltip" data-placement="top" title="Descargar">
                                                <span class="glyphicon glyphicon-download"></span>
                                            </a></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-md-5"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row" v-if="request.priceEstimationsList != null">
                <div class="col-md-12">
                    <div class="col-md-7"></div>
                    <div class="col-md-2">
                        <button class="btn btn-info btn-sm" @click="openModalOrdenCompra()"
                                v-if="request.idRequestStatus == 2 && requestOrder == null">
                            Generar orden de compra
                        </button>
                    </div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-warning btn-sm"
                                v-if="request.idRequestStatus == 2 && requestOrder.idRequestOrderDocument > 0 && purchaseInvoice == null" @click="openModalActivados()">
                            Agregar Comprobante
                        </button>
                    </div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-danger btn lg-active" @click="rejectReuquestModal()"
                                v-if="request.idRequestStatus == 1">Rechazar
                        </button>
                        <button type="button" class="btn btn-success btn-sm"
                                v-if="request.idRequestStatus == 2 && purchaseInvoice != null"
                                data-toggle="modal" data-target="#botonenviar">Enviar
                        </button>
                    </div>
                    <div class="col-md-1">
                        <a class="btn btn-default btn-sm"
                           :href="requestBoxUrl"
                           data-toggle="tooltip" data-placement="top" title="Cancelar"
                           v-if="request.idRequestStatus == 1 || request.idRequestStatus == 2"
                        >
                            Cancelar
                        </a>
                        <a class="btn btn-default btn-sm"
                           :href="requestBoxUrl"
                           data-toggle="tooltip" data-placement="top" title="Salir"
                           v-if="request.idRequestStatus != 1 && request.idRequestStatus != 2"
                        >
                            Salir
                        </a>
                    </div>
                </div>
            </div>

            <br>
            <br>



            <!--TERMINA PANTALLA DE SOLICITUD/VIGENTE PAGINA 25-->
            <!--EMPIEZA MODAL 1 PARA LA OPCION ELEGIR DE LA PANTALLA DE SOLICITUD/VIGENTE PAGINA 26-->

            <div class="modal fade" id="modalSelectEstimation" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Justificar</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <textarea class="form-control" rows="3"
                                              v-model="selectedEstimation.justify"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" data-toggle="collapse"
                                    @click="selectedEstimations()">
                                Aceptar
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>



            <div class="modal fade" id="modalSendValidationFinantialPlaning" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Detalle de solicitud</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <label>Solicitud fuera de presupuesto</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <p>Se enviará a planeación financiera para su validación</p>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" data-toggle="collapse"
                                    @click="autorizeEstimation(2)">
                                Aceptar
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>



            <div class="modal fade" id="modalSendValidationLevel" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Detalle de solicitud</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <label>Solicitud fuera del monto autorizado</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <p>Se enviará a la persona correspondiente para su validación</p>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" data-toggle="collapse"
                                    @click="autorizeEstimation(1)">
                                Aceptar
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>


            <div class="modal fade" id="justificarrechazo" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Justificar rechazo</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                <textarea class="form-control" rows="4"
                                          v-model="selectedEstimation.rejectJustify"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" @click="rejectRequest()">Aceptar
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>



            <!--EMPIEZA MODAL DE LA PAGINA 27 A LA 28 ORDEN DE COMPRA-->

            <div class="modal fade" id="ordendecompra" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <div class="alert">
                                <img style="width: 30px; height: 30px">
                                <h3 class="modal-title" style="text-align: center"><label>ORDEN DE COMPRA</label>
                                </h3>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-12">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <h5 style="text-align: center"><B><I>EMPRESA QUE EMITE</I></B>
                                                        </h5>
                                                        <select v-model="distributor" class="form-control">
                                                            <option></option>
                                                            <option v-for="distributor in distributors"
                                                                    value="{{distributor}}">
                                                                {{distributor.acronyms}}
                                                            </option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <h5 style="text-align: center"><B><I>EMITIDO PARA</I></B>
                                                        </h5>
                                                        <select v-model="provider" @change="selectProvider()"
                                                                class="form-control">
                                                            <option></option>
                                                            <option v-for="provide in providers" value="{{provide}}">
                                                                {{provide.providerName | separate}}
                                                            </option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="panel-body">
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <label>Empresa:</label>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label>{{distributor.acronyms}}</label>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Empresa/Persona:</label>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label v-if="provider.providerName.length > 0">{{provider.providerName
                                                            | separate}}</label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <label>Dirección:</label>
                                                    </div>
                                                    <div class="col-md-4">
                                                        {{distributor.address}}
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Dirección:</label>
                                                    </div>
                                                    <div class="col-md-4">
                                                        {{providerAddress[0].street}}
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <label>Ciudad:</label>
                                                    </div>
                                                    <div class="col-md-4">
                                                        {{distributor.city}}
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Ciudad:</label>
                                                    </div>
                                                    <div class="col-md-4">
                                                        {{providerAddress[0].asentamiento.municipios.estados.nombreEstado}}
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <label>Código postal:</label>
                                                    </div>
                                                    <div class="col-md-4">
                                                        {{distributor.postcode}}
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>Código postal:</label>
                                                    </div>
                                                    <div class="col-md-4">
                                                        {{providerAddress[0].asentamiento.codigoPostal}}
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-2">
                                                        <label>RFC:</label>
                                                    </div>
                                                    <div class="col-md-4">
                                                        {{distributor.rfc}}
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label>RFC:</label>
                                                    </div>
                                                    <div class="col-md-4">
                                                        {{provider.rfc}}
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- segunda tabla contactos -->
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <h5 style="text-align: center"><b><i>CONTACTO DEL EMISOR</i></b>
                                                        </h5>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <h5 style="text-align: center"><b><i>CONTACTO DEL
                                                            RECEPTOR</i></b></h5>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="panel-body">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="col-md-4">
                                                            <label>Nombre:</label>
                                                        </div>
                                                        <div class="col-md-8">
                                                            <input class="form-control" v-model="contactName">
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="col-md-4">
                                                            <label>Nombre:</label>
                                                        </div>
                                                        <div class="col-md-8">
                                                            <input class="form-control"
                                                                   v-model="providerContact[0].name" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="col-md-4">
                                                            <label>Puesto:</label>
                                                        </div>
                                                        <div class="col-md-8">
                                                            <input class="form-control" v-model="contactRole">
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="col-md-4">
                                                            <label>Puesto:</label>
                                                        </div>
                                                        <div class="col-md-8">
                                                            <input class="form-control"
                                                                   v-model="providerContact[0].post" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="col-md-4">
                                                            <label>Télefono:</label>
                                                        </div>
                                                        <div class="col-md-8">
                                                            <input class="form-control" type="text" maxlength="14"
                                                                   v-model="contactCel"
                                                                   onkeypress="return isNumberKey(event)">
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="col-md-4">
                                                            <label>Télefono:</label>
                                                        </div>
                                                        <div class="col-md-8">
                                                            <input class="form-control" type="text" maxlength="14"
                                                                   v-model="providerContact[0].phoneNumber" disabled>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="#onemore">
                                        <div class="col-md-12">
                                            <div class="panel panel-default">
                                                <div class="panel-body">
                                                    <div class="row">
                                                        <table class="table">
                                                            <thead>
                                                            <tr>
                                                                <th class="col-md-6">Descripción</th>
                                                                <th class="col-md-2">Cantidad</th>
                                                                <th class="col-md-3">Precio</th>
                                                                <th class="col-md-1"></th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <tr>
                                                                <td class="col-md-6"><textarea class="form-control"
                                                                                               v-model="requestOrderProducts.description"
                                                                                               rows="1"></textarea></td>
                                                                <td class="col-md-2"><input class="form-control"
                                                                                            v-model="requestOrderProducts.quantity"
                                                                                            maxlength="3" id="cantidad"
                                                                                            onclick="return cleanFieldProduct(this)"
                                                                                            onkeypress="return validateFloatKeyPress(this,event)"
                                                                                            onInput="format(this)"
                                                                                            onblur="ponerCeros(this)"
                                                                />
                                                                </td>
                                                                <td class="col-md-3"><input class="form-control"
                                                                                            v-model="requestOrderProducts.price"
                                                                                            onclick="return cleanField(this)"
                                                                                            onkeypress="return validateFloatKeyPress(this,event)"
                                                                                            maxlength="14" id="precio"/>
                                                                </td>
                                                                <td class="col-md-1">
                                                                    <button class="btn btn-default btn-sm"
                                                                            @click="addProductOrdered()"><span
                                                                            class="glyphicon glyphicon-plus"></span>
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <div class="row" v-if="orderProducts.length > 0">
                                                        <table class="table">
                                                            <thead>
                                                            <tr>
                                                                <th class="col-md-6">Descripción</th>
                                                                <th class="col-md-2">Cantidad</th>
                                                                <th class="col-md-3">Precio</th>
                                                                <th class="col-md-1"></th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <tr v-for="orderProduct in orderProducts">
                                                                <td class="col-md-6">
                                                                    {{orderProduct.description}}
                                                                </td>
                                                                <td class="col-md-2">
                                                                    {{orderProduct.quantity}}
                                                                </td>
                                                                <td class="col-md-3">
                                                                    {{orderProduct.price | currency}}
                                                                </td>
                                                                <td class="col-md-1">
                                                                    <button class="btn btn-danger btn-sm"
                                                                            @click="deleteProductOrdered(orderProduct)">
                                                                        <span class="glyphicon glyphicon-trash"></span>
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <div class="row">
                                                    <div class="col-md-6"></div>
                                                    <div class="col-md-6" style="margin-left: 475px">
                                                        <form name="facturaSiad">
                                                            <dl class="dl-horizontal">
                                                                <dt style="margin: 5px">
                                                                <p>Subtotal</p></dt>
                                                                <input class="form-control" type="text"
                                                                       placeholder="$" style="width: 45%"
                                                                       name="subtotal" maxlength="14" readonly
                                                                       v-model="amount | currencyDisplay"
                                                                       disabled/>
                                                                <dt style="margin: 5px">
                                                                <p>IVA</p></dt>
                                                                <input class="form-control"
                                                                       type="text"
                                                                       placeholder="$" style="width: 45%"
                                                                       name="iva" readonly disabled
                                                                       v-model="iva | currencyDisplay"/>
                                                                <dt style="margin: 5px"><b
                                                                        style="color: black">Total</b>
                                                                </dt>
                                                                <input class="form-control"
                                                                       type="text"
                                                                       placeholder="$"
                                                                       v-model="totalAmount | currencyDisplay"
                                                                       style="width: 45%"
                                                                       name="total" maxlength="14" readonly
                                                                       disabled/>
                                                            </dl>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <hr class="firma" color="black" size="150" width="270" style="height: 2px;">
                                    <h6 style="text-align: center"><b>Nombre del solicitante de orden de compra</b></h6>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">

                            <button type="button" class="btn btn-success" @click="sendOrderRequestBuy()">Guardar
                            </button>
                            <button type="button" class="btn btn-default" class="close" data-dismiss="modal"
                                    aria-hidden="true">
                                Cancelar
                            </button>
                        </div>
                    </div>
                </div>
            </div>



            <!--TERMINA VISTA DETALLE DE SOLICITUD PAGINA 29-->
            <!--EMPIEZA MODAL DE ACTIVAR CAMPOS PAGINA 29-->

            <div class="modal fade" id="activadatos" role="dialog">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content" style="background-color: #f2f2f2">
                        <div class="modal-body">
                            <div class="panel panel-default" style="background-color: #f2f2f2">
                                <div class="row">
                                    <div class="col-md-12" v-if="isXmlFile == false"><br>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Concepto</b></h6></label><br><br>
                                            <p><b><h6><b>{{request.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</b>
                                            </h6></b></p>
                                        </div>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Número de factura</b></h6>
                                            </label><br><br>
                                            <input class="form-control" type="text"
                                                   v-model="billInformation.folio" maxlength="14"
                                                   :disabled="isXmlFile != false"/>
                                        </div>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Concepto factura</b></h6></label><br><br>
                                            <input type="text" v-model="billInformation.concept" :disabled="isXmlFile != false"
                                                   class="form-control">
                                        </div>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Monto con iva</b></h6></label><br><br>
                                            <input class="form-control" type="text"
                                                   maxlength="14" v-model="billInformation.amountWithIva | currency" onkeypress="return validateFloatKeyPress(this, event)"
                                                   :disabled="isXmlFile != false"/>
                                        </div>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Monto sin iva</b></h6></label><br><br>
                                            <input class="form-control" type="text" v-model="billInformation.amountNotIva | currency" onkeypress="return validateFloatKeyPress(this, event)"
                                                    maxlength="14"
                                                   :disabled="isXmlFile != false"/>
                                        </div>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Comprobante</b></h6></label><br><br>
                                            <button class="btn btn-primary btn-xs" @click="opneModalUploadFile()">Carga de documentos
                                            </button>
                                        </div>
                                    </div>


                                    <div class="col-md-12" v-if="isXmlFile == true">
                                        <br>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Concepto</b></h6></label><br><br>
                                            <p><b><h6><b>{{request.distributorCostCenter.accountingAccounts.budgetSubcategory.budgetSubcategory}}</b>
                                            </h6></b></p>
                                        </div>
                                        <div class="col-md-2">
                                            <label><h6><b style="color: black">Número de factura</b></h6>
                                            </label><br><br>
                                            <input class="form-control" type="text"
                                                   style="width: 90%"
                                                   v-model="billInformation.folio" maxlength="14"
                                                   disabled/>
                                        </div>
                                        <div class="col-md-5">
                                            <div class="row">
                                                <div class="col-md-7"><label><h6><b style="color: black">Concepto factura</b></h6></label></div>
                                                <div class="col-md-3"><label><h6><b style="color: black">Monto unitario</b></h6></label></div>
                                                <div class="col-md-2"><label><h6><b style="color: black">Cantidad</b></h6></label></div>
                                            </div>
                                            <div class="table-body flex-row flex-content" style="overflow-x:hidden; width:365px; max-height:180px">
                                                <div class="row table-row"
                                                     v-for="concepto in billInformation.conceptos.conceptos">
                                                    <div class="col-md-7">{{concepto.descripcion}}</div>
                                                    <div class="col-md-3">{{concepto.valorUnitario | currency}}</div>
                                                    <div class="col-md-2">{{concepto.cantidad}}</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <label><h6><b style="color: black; margin-left: 20px">Comprobante</b></h6></label><br>
                                            <button class="btn btn-primary btn-xs" @click="opneModalUploadFile()" style="margin-left: 20px">Carga
                                                de documentos
                                            </button>
                                        </div>
                                    </div>
                                    <br>
                                    <br>
                                    <br>
                                    <div class="col-md-12"><br>
                                        <div class="col-xs-3">
                                            <label><h6><b style="color: black">Proveedor</b></h6></label><br><br>
                                            <select class="form-control" id="select-provider-1">
                                            </select><br><br>
                                        </div>
                                        <div class="col-xs-3">
                                            <label><h6><b style="color: black">Cuenta bancaria</b></h6></label><br><br>
                                            <select class="form-control" v-model="providerAccount">
                                                <option v-for="pAccount in providerAccountList" value="{{pAccount}}">
                                                    {{pAccount.account.accountNumber}}
                                                </option>
                                            </select><br><br>
                                        </div>
                                        <div class="col-xs-2">
                                            <label><h6><b style="color: black">Tipo de moneda</b></h6></label><br><br>
                                            <input class="form-control" type="text"
                                                   style="width: 90%"
                                                   v-model="billInformation.moneda" maxlength="14"
                                                   :disabled="isXmlFile != false"/><br><br>
                                        </div>
                                        <div class="col-xs-2">
                                            <label><h6><b style="color: black">Tipo de cambio</b></h6></label><br><br>
                                            <input class="form-control" type="text"
                                                   style="width: 90%"
                                                   v-model="billInformation.tipoCambio | currency" maxlength="14" onkeypress="return validateFloatKeyPress(this, event)"
                                                   :disabled="isXmlFile != false"/><br><br>
                                        </div>
                                        <div class="col-xs-2">
                                            <label><h6><b style="color: black">Monto total</b></h6></label><br><br>
                                            <input class="form-control" type="text"
                                                   style="width: 90%"
                                                   v-model="billInformation.total | currency" maxlength="14" onkeypress="return validateFloatKeyPress(this, event)"
                                                   :disabled="isXmlFile != false"/><br><br>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="savePruchaseInvoices()">Guardar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>



            <!--TERMINA MODAL DE ACTIVAR CAMPOS PAGINA 29-->
            <!--EMPIEZA MODAL CARGAR DOCUMENTOS PDF, XML, PAGINA 30-->

            <div class="modal fade" id="pdfyxml" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <div>
                                <h5 class="modal-title">Carga de documentos</h5>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Factura PDF</label>
                                                <input @change="setFile($event, 1)" type="file"
                                                       class="form-control"
                                                       id="file-type-1"
                                                       name="file-type-1"
                                                       accept="application/pdf"/>
                                            </div>
                                        </div>
                                        <br><br>
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Factura XML</label>
                                                <input @change="setFile($event, 2)" type="file"
                                                       class="form-control"
                                                       id="file-type-2"
                                                       name="file-type-2"
                                                       accept=".xml"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" @click="saveFiles()" class="btn btn-success">Guardar</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">
                                    Cancelar
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>



            <div class="modal fade" id="botonenviar" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Enviar solicitud</h4>
                        </div>
                        <div class="modal-body">
                            <p>La solicitud sera enviada al adminsitrador de pagos,</p>
                            <p>para procesar el pago la factura</p>
                            <br>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="sendToPaymanagment()">Aceptar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>





        </div>
    </jsp:body>
</t:template>