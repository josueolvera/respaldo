<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Proveedores">

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
                    this.obtainBanks();
                    this.getProviders();
                    this.obtainCurrencies();
                    this.obtainRequestTypes();
                    this.productTypes();
                    this.obtainDistributors();
                },
                data: {
                    supplier: {
                        providerName: '',
                        businessName: '',
                        rfc: '',
                        idAccountingAccount: '',
                        providersAccountsList: [],
                        providersContactList: [],
                        providerAddressList: [],
                        providersProductsTypes: [],
                        creditDays: '',
                        cuttingDate: '',
                        firstLevel: '',
                        secondLevel: '',
                        thirdLevel: '',
                    },
                    provider: {
                        providerName: '',
                        businessName: '',
                        rfc: '',
                        idAccountingAccount: '',
                        providersAccountsList: [],
                        providersContactList: [],
                        providerAddressList: [],
                        providersProductsTypes: [],
                        creditDays: '',
                        cuttingDate: '',
                        firstLevel: '',
                        secondLevel: '',
                        thirdLevel: '',
                    },
                    idBanks: '',
                    banks: [],
                    providers: {},
                    search: '',
                    providerNames: '',
                    providerLastName: '',
                    providerSecondName: '',
                    supplierNames: '',
                    supplierLastName: '',
                    supplierSecondName: '',
                    currencies: {},
                    states: {},
                    idCurrency: '',
                    idState: '',
                    settlements: {},
                    idSettlement: '',
                    municipalities: {},
                    idMunicipality: '',
                    telephone: {
                        phoneNumber: '',
                        email: "",
                        post: "",
                        name: "",
                    },
                    cuenta: {
                        accountNumber: '',
                        accountClabe: '',
                        idBank: '',
                        idCurrency: '',
                    },
                    direccion: {
                        cp: '',
                        numExt: '',
                        numInt: '',
                        street: '',
                        idAsentamiento: '',
                    },
                    modalPregunta: {
                        provider: {
                            providerName: ""
                        }
                    },
                    modalCuenta: {
                        account: {}
                    },
                    phoneNumber: '',
                    email: '',
                    name: '',
                    post: '',
                    accountNumbers: '',
                    clabes: '',
                    requestInformation: {
                        idRequestType:'',
                        idProductType:'',
                    },
                    ProductTypes: {},
                    RequestTypes: {},
                    asentamiento:[],
                    distributors:[],
                    estadosMunicipios:{
                    },

                },
                methods: {
                    providerRfc: function () {
                        var providerNames = this.providerNames;
                        var providerLastName = this.providerLastName;
                        var providerSecondName = this.providerSecondName;

                        this.supplier.providerName = (providerNames + ":" + providerLastName + ":" + providerSecondName);
                    },
                    splitProviderName: function (provider) {
                        var array;
                        array = provider.providerName.split(":");
                        this.supplierNames = array[0];
                        this.supplierLastName = array[1];
                        this.supplierSecondName = array[2];
                    },
                    modifyName: function () {
                        var providerNames = this.supplierNames;
                        var providerLastName = this.supplierLastName;
                        var providerSecondName = this.supplierSecondName;

                        this.provider.providerName = (providerNames + ":" + providerLastName + ":" + providerSecondName);
                    },
                    saveAccount: function (bank, account, clabe, currency) {
                        var cuenta =
                        {
                            accountNumber: '',
                            accountClabe: '',
                            idBank: '',
                            idCurrency: ''
                        }

                        cuenta.accountNumber = account;
                        cuenta.accountClabe = clabe;
                        cuenta.idBank = bank;
                        cuenta.idCurrency = currency;

                        this.supplier.providersAccountsList.push(cuenta);

                        this.idBanks = ''
                        this.accountNumbers = ''
                        this.clabes = ''
                        this.idCurrency = '';

                    },
                    validateName: function(){
                        if(this.supplier.rfc.length == 13) {
                            if (this.providerNames.length != 0 || this.supplierLastName.length != 0 || this.supplierSecondName.length != 0) {
                                return true;
                            } else {
                                showAlert("Ingresa los campos Requeridos: Nombre, Apellido paterno, Apellido materno", {type: 3});
                                return false;
                            }
                        }
                        if(this.supplier.rfc.length == 12){
                                if (this.supplier.providerName.length != 0) {
                                    return true;
                                } else {
                                    showAlert("Ingresa los campos Requeridos: Razón social", {type: 3});
                                    return false;
                                }
                        }
                    },
                    validationAccount: function () {
                        if (this.supplier.providersAccountsList.length != 0) {
                            if (this.idBanks.length != 0 && this.accountNumbers.length != 0 && this.clabes.length != 0 && this.idCurrency.length != 0) {
                                this.saveAccount(this.idBanks, this.accountNumbers, this.clabes, this.idCurrency);
                                return true;
                            } else {
                                return true;
                            }
                        } else {
                            if (this.idBanks.length != 0 && this.accountNumbers.length != 0 && this.clabes.length != 0 && this.idCurrency.length != 0) {
                                this.saveAccount(this.idBanks, this.accountNumbers, this.clabes, this.idCurrency);
                                return true;
                            } else {
                                showAlert("Ingresa los campos Requeridos: Banco, Número de Cuenta, CLABE, Moneda", {type: 3});
                                return false;
                            }
                        }
                    },
                    validationProduct: function () {
                        if (this.supplier.providersProductsTypes.length != 0) {
                            if (this.requestInformation.idRequestType.length != 0 && this.requestInformation.idProductType.length != 0) {
                                this.addProviderProduct(this.requestInformation.idRequestType, this.requestInformation.idProductType);
                                return true;
                            } else {
                                return true;
                            }
                        } else {
                            if (this.requestInformation.idRequestType.length != 0 && this.requestInformation.idProductType.length != 0) {
                                this.addProviderProduct(this.requestInformation.idRequestType, this.requestInformation.idProductType);
                                return true;
                            } else {
                                showAlert("Ingresa los campos Requeridos: Rubro,Producto", {type: 3});
                                return false;
                            }
                        }
                    },
                    obtainBanks: function () {
                        this.$http.get(ROOT_URL + "/banks")
                                .success(function (data) {
                                    this.banks = data;

                                });
                    },
                    eliminarCuenta: function (cuenta) {
                        this.supplier.providersAccountsList.$remove(cuenta);
                    },
                    validationAddress: function () {
                        if (this.supplier.providerAddressList.length < 1) {
                            if (this.direccion.numExt.length != 0 && this.direccion.numInt.length != 0 && this.direccion.street != 0) {
                                this.saveAddress(this.direccion.street, this.direccion.numExt, this.direccion.numInt, this.direccion.idAsentamiento);
                                return true;
                            } else {
                                showAlert("Ingresa los campos requeridos: Calle, Núm. Exterior, Núm. Interior, Colonia", {type: 3});
                                return false;
                            }
                        }else{
                            return true;
                        }
                    },
                    saveAddress: function (street,numExt,numInt,idAsentamiento) {
                        direccion = {
                            numExt: '',
                            numInt: '',
                            street: '',
                            idAsentamiento: ''
                        };
                            direccion.numExt = numExt;
                            direccion.numInt = numInt;
                            direccion.street = street;
                            direccion.idAsentamiento = idAsentamiento;

                            this.supplier.providerAddressList.push(direccion);
                    },
                    validationSave: function () {
                        if (this.supplier.rfc.length != 0) {
                            var validation5 = this.validationProduct();
                            var validation4 = this.validateName();
                            var validation2 = this.validationContact();
                            var validation = this.validationAccount();
                            var validation3 = this.validationAddress();
                            if (validation == true && validation5 == true && validation3 == true && validation4 == true && validation2 == true) {
                                this.obtainAccountinAccount();
                            }
                        } else {
                            showAlert("Ingresa el RFC", {type: 3});
                        }
                    },

                    saveProvider: function () {
                        if (this.supplier.rfc.length == 13) {
                            this.providerRfc();
                        }
                        this.$http.post(ROOT_URL + "/providers", JSON.stringify(this.supplier)).success(function (data) {
                            showAlert("Registro de proveedor exitoso");
                            $('#modalAlta').modal('hide');
                            this.getProviders();

                        }).error(function () {
                            showAlert("Ha habido un error con la solicitud, intente nuevamente", {type: 3});
                        });

                        this.cancelar();
                    },
                    getProviders: function () {
                        this.$http.get(ROOT_URL + "/providers")
                                .success(function (data) {
                                    var jsonObjectIndex = {};
                                    data.forEach(function (provider) {
                                        if (isNaN(provider.accountingAccounts)) {
                                            jsonObjectIndex[provider.accountingAccounts._id] = provider.accountingAccounts;
                                        } else {
                                            provider.accountingAccounts = jsonObjectIndex[provider.accountingAccounts];
                                        }
                                    });
                                    this.providers = data;
                                });
                    },
                    getProviderAccount: function (provider) {
                        this.$http.get(ROOT_URL + "/accounts/provider/" + provider.idProvider)
                                .success(function (data) {
                                    Vue.set(provider, 'providersAccountsList', data);
                                });
                    },
                    getProviderAddress: function (provider) {
                        this.$http.get(ROOT_URL + "/provider-address/provider/" + provider.idProvider)
                                .success(function (data) {
                                    Vue.set(this.provider, 'providerAddressList', data);
                                });
                    },
                    getProviderProduct: function (provider) {
                        this.$http.get(ROOT_URL + "/providers-products-types/provider/"+provider.idProvider)
                                .success(function (data) {
                                    Vue.set(this.provider, 'providersProductsTypes', data);
                        });
                    },
                    modifyProvider: function (provider) {
                        $('#modalModi').modal('show');
                        this.$http.get(ROOT_URL + "/provider-contact/provider/" + provider.idProvider)
                                .success(function (data) {

                                    this.provider = (JSON.parse(JSON.stringify(provider)));

                                    if (this.provider.rfc.length == 13) {
                                        this.splitProviderName(this.provider);
                                    }
                                    this.getProviderAddress(this.provider);
                                    this.getProviderAccount(this.provider);
                                    this.getProviderProduct(this.provider);
                                    Vue.set(this.provider, 'providersContactList', data);
                                });
                    },
                    deleteAccount: function (account) {
                        this.$http.post(ROOT_URL + "/accounts/low/" + account.idAccount)
                                .success(function (data) {
                                    this.provider.providersAccountsList.$remove(account);
                                    this.getProviderAccount(this.provider);
                                    showAlert("Cuenta Eliminada");
                                    $('#modalCuenta').modal('hide');
                                });

                    },
                    removePhone: function (phone) {
                        this.$http.delete(ROOT_URL + "/provider-contact/" + phone.idProviderContact)
                                .success(function (data) {
                                    showAlert("Contacto eliminado");
                                    this.provider.providersContactList.$remove(phone);
                                });
                    },
                    addProviderAccount: function (supplier, cuenta) {
                        this.$http.post(ROOT_URL + "/accounts/provider/" + supplier.idProvider, cuenta)
                                .success(function (data) {
                                    showAlert("Cuenta guardada con éxito");
                                    this.$http.get(ROOT_URL + "/accounts/provider/" + this.provider.idProvider)
                                            .success(function (data) {
                                                Vue.set(this.provider, 'providersAccountsList', data);
                                            });
                                    cuenta.accountNumber = "";
                                    cuenta.accountClabe = "";
                                    cuenta.idBank = "";
                                    cuenta.idCurrency = "";
                                });

                    },
                    updateProvider: function (provider) {
                        if (provider.rfc.length == 13) {
                            this.modifyName();
                        }
                        this.$http.post(ROOT_URL + "/providers/" + provider.idProvider, provider)
                                .success(function (data) {
                                    showAlert("Proveedor actualizado");
                                    $('#modalModi').modal('hide');
                                    this.getProviders();
                                }).error(function () {
                            showAlert("Ha habido un error con la solicitud, intente nuevamente", {type: 3});
                        });
                        this.cancelar();
                    },
                    deleteProvider: function () {
                        this.$http.post(ROOT_URL + "/providers/low/" + this.modalPregunta.provider.idProvider)
                                .success(function (data) {
                                    this.getProviders();
                                    $('#modalPregunta').modal('hide');
                                    showAlert("Provedor eliminado");
                                });
                    },
                    filterNumber: function (val) {
                        return isNaN(val) ? '' : val;
                    },
                    obtainCurrencies: function () {
                        this.$http.get(ROOT_URL + "/currencies").success(function (data) {
                            this.currencies = data;
                        });

                    },
                    obtainStates: function () {
                        this.$http.get(ROOT_URL + "/states").success(function (data) {
                            this.states = data;
                        });
                    },
                    obtainDistributors: function() {
                      this.$http.get(ROOT_URL + "/distributors").success(function (data) {
                          this.distributors = data;
                      });
                    },
                    obtainSettlements: function () {
                        this.$http.get(ROOT_URL + "/settlements").success(function (data) {
                            this.settlements = data;
                        });
                    },
                    obtainMunicipalities: function () {
                        this.$http.get(ROOT_URL + "/municipalities").success(function (data) {
                            this.municipalities = data;
                        });
                    },
                    obtainRequestTypes: function () {
                        this.$http.get(ROOT_URL + "/request-types").success(function (data) {
                            this.RequestTypes = data;
                        });
                    },
                    obtainRequestTypeProduct: function () {
                        this.$http.get(ROOT_URL + "/request-type-product/" + this.requestInformation.idRequestType).success(function (data) {
                            this.ProductTypes = data;
                        });
                    },
                    addProviderProduct: function (idRequestType,idProductType) {
                        var requestInformation = {
                            idRequestType:'',
                            idProductType:'',
                        }
                      requestInformation.idRequestType = idRequestType;
                      requestInformation.idProductType = idProductType;
                        if (this.validationProviderProduct().length == 0) {
                            this.supplier.providersProductsTypes.push(requestInformation);
                        }
                    },
                    validationProviderProduct: function () {
                        var self = this;
                        return this.supplier.providersProductsTypes.filter(function (element){
                            if(self.requestInformation.idProductType == element.idProductType) {
                                return element;
                            }
                      });
                    },
                    savePhone: function (names, phones, emails, posts) {
                        var contact = {
                            phoneNumber: "",
                            email: "",
                            post: "",
                            name: "",

                        }

                        contact.phoneNumber = phones;
                        contact.email = emails;
                        contact.post = posts;
                        contact.name = names;

                        this.supplier.providersContactList.push(contact);

                        this.phoneNumber = "";
                        this.email = "";
                        this.post = "";
                        this.name = "";

                    },
                    validateEmail: function (email) {
                      var re = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
                        if(! re.test(email)) {
                            showAlert("Ingresa un email correcto",{type: 3});
                            return false;
                        }else{
                            this.savePhone(this.name, this.phoneNumber, this.email, this.post);
                            return true;
                        }
                    },
                    validateContact: function () {
                        this.validationContact()
                    },
                    validationContact: function () {
                        if (this.supplier.providersContactList.length != 0) {
                            if (this.name.length != 0 && this.phoneNumber.length != 0 && this.email.length != 0) {
                                this.validateEmail(this.email);
                                return true;
                            } else {
                                return true;
                            }
                        } else {
                            if (this.name.length != 0 && this.phoneNumber.length != 0 && this.email.length != 0) {
                                this.validateEmail(this.email);
                                return true;
                            } else {
                                showAlert("Ingresa los campos Requeridos: Nombre, Teléfono, Email", {type: 3});
                                return false;
                            }
                        }
                    },
                    deletePhone: function (phone) {
                        this.supplier.providersContactList.$remove(phone);
                        this.phoneNumbers = '';
                    },
                    deleteProduct: function (product) {
                        this.supplier.providersProductsTypes.$remove(product);
                    },
                    addProviderPhone: function (supplier, phone) {
                        if(this.validateEmail(phone.email)) {
                            this.$http.post(ROOT_URL + "/provider-contact/provider/" + supplier.idProvider, phone)
                                    .success(function (data) {
                                        this.provider.providersContactList.push(data);
                                        this.phoneNumbers = '';
                                        showAlert("Contacto guardado con éxito");
                                        this.telephone.phoneNumbers = '';
                                    });
                            this.telephone.name = "";
                            this.telephone.email = "";
                            this.telephone.phoneNumber = "";
                            this.telephone.post = "";
                        }
                    },
                    addAddress: function (provider, address) {
                        this.$http.post(ROOT_URL + "/provider-address/provider/" + provider.idProvider, address)
                                .success(function (data) {
                                    this.provider.providerAddressList.push(data);
                                    showAlert("Dirección guardada con éxito");
                                    this.getProviderAddress(provider);
                                });
                        this.direccion.cp = "";
                        this.direccion.street = "";
                        this.direccion.numExt = "";
                        this.direccion.numInt = "";
                        this.direccion.idAsentamiento = "";
                        this.estadosMunicipios.nombreMunicipios = "";
                        this.estadosMunicipios.estado.nombreEstado = "";
                    },
                    saveProviderProduct: function (provider, product) {
                        if(this.validationSupplierProduct(product) == 0 ) {
                            this.$http.post(ROOT_URL + "/providers-products-types/provider/" + provider.idProvider, product)
                                    .success(function (data) {
                                        this.provider.providersProductsTypes.push(data);
                                        showAlert("Producto guardado con exito");
                                        this.getProviderProduct(provider);
                                    });
                        }
                    },
                    validationSupplierProduct: function (product) {
                        return this.provider.providersProductsTypes.filter(function (element){
                            if(product.idProductType == element.idProductType) {
                                return element;
                            }
                        });
                    },
                    removeProviderProduct: function (product) {
                        this.$http.delete(ROOT_URL + "/providers-products-types/" + product.idProvidersProductsTypes)
                                .success(function (data) {
                                    this.provider.providersProductsTypes.$remove(product);
                                    showAlert("Producto eliminado");
                                });
                    },
                    removeAddress: function (address) {
                        this.$http.delete(ROOT_URL + "/provider-address/" + address.idProviderAddress)
                                .success(function (data) {
                                    this.provider.providerAddressList.$remove(address);
                                    showAlert("Dirección eliminada");
                                });
                    },
                    obtainAccountinAccount: function () {
                        this.$http.get(ROOT_URL + "/accounting-accounts/" + this.distributor.idDistributor + "/" + this.supplier.firstLevel + "/" + this.supplier.secondLevel + "/" + this.supplier.thirdLevel)
                                .success(function (data) {
                                    console.log(data);
                                this.supplier.idAccountingAccount = data.idAccountingAccount;
                                    this.saveProvider();
                        }).error(function () {
                           showAlert("No existe la cuenta contable");
                        });
                    },
                    cancelar: function () {

                        this.cuenta.accountNumber = '';
                        this.cuenta.accountClabe = '';
                        this.cuenta.idBank = '';
                        this.cuenta.idCurrency = '';
                        this.clabes = "";
                        this.accountNumbers = "";
                        this.direccion.cp = '';
                        this.direccion.numExt = '';
                        this.direccion.numInt = '';
                        this.direccion.street = '';
                        this.direccion.idSettlement = '';
                        this.direccion.idState = '';
                        this.direccion.idMunicipality = '';
                        this.idBanks = '';
                        this.providerNames = '';
                        this.providerLastName = '';
                        this.providerSecondName = '';
                        this.idCurrency = '';
                        this.idState = '';
                        this.idSettlement = '';
                        this.idMunicipality = '';
                        this.telephone.phoneNumbers = '';
                        this.telephone.phoneNumber = '';
                        this.telephone.email = '';
                        this.telephone.name = '';
                        this.telephone.post = '';
                        this.supplier.providerName = '';
                        this.supplier.businessName = '';
                        this.supplier.creditDays = '';
                        this.supplier.cuttingDate = '';
                        this.supplier.providersAccountsList = [];
                        this.supplier.providersContactList = [];
                        this.supplier.rfc = '';
                        this.supplier.accountingAccount = '';
                        this.supplier.addressProvider = [];
                        this.supplier.providersProductsTypes = [];
                        this.supplier.firstLevel =  '',
                        this.supplier.secondLevel = '',
                        this.supplier.thirdLevel = '',
                        this.name = "";
                        this.email = "";
                        this.phoneNumber = "";
                        this.post = "";
                        this.requestInformation.idRequestType = '',
                        this.requestInformation.idProductType = '',
                        this.asentamiento = [];
                        this.estadosMunicipios = {};
                        this.distributors = [],

                        $('#modalAlta').modal('hide');
                        $('#modalModi').modal('hide');
                    },
                    question: function (provider) {
                        this.modalPregunta.provider = provider;
                        $('#modalPregunta').modal('show');
                    },
                    questionAccount: function (account) {
                        this.modalCuenta.account = (JSON.parse(JSON.stringify(account)));
                        $('#modalCuenta').modal('show');
                    },
                    productTypes: function () {
                      this.$http.get(ROOT_URL + "/product-types").success(function (data) {
                          this.productos = data;
                      });
                    },
                    validateClabe: function(accountNumbers,clabes) {
                        var account = accountNumbers;
                        var clabe = clabes;
                            if (clabe.indexOf(account) == -1) {
                                if (!alert("La clabe es incorrecta")) {
                                    this.clabes = "";
                                    this.accountNumbers = "";
                                    this.cuenta.accountNumber = "";
                                    this.cuenta.accountClabe = "";
                                }
                            }
                    },
                    validateCuenta: function(accountNumbers,clabes) {
                        var account = accountNumbers;
                        var clabe = clabes;
                            if (!(clabes == null || clabes === "")) {
                                if (clabe.indexOf(account) == -1) {
                                    if (!alert("La clabe es incorrecta")) {
                                        this.accountNumbers = "";
                                        this.clabes = "";
                                        this.cuenta.accountNumber = "";
                                        this.cuenta.accountClabe = "";
                                    }
                                }
                            }
                    },
                    obtainAsentamientos: function(){
                        var postcode = this.direccion.cp;
                        if(this.direccion.cp >= 4){
                            this.$http.get(ROOT_URL + "/settlements/post-code?cp=" + postcode).success(function (data) {
                                this.asentamiento =  data;
                                if(data.length > 0){
                                    this.$http.get(ROOT_URL + "/municipalities/" + data[0].idEstado + "/" + data[0].idMunicipio).success(function (element) {
                                        this.estadosMunicipios = element;
                                    });
                                }
                            });
                        }else{
                            this.asentamiento = [];
                            this.estadosMunicipios={};
                        }
                    },
                    numberAndLetter: function(rfc){
                        var re = /^[a-z0-9]+$/i;
                        if(! re.test(rfc)) {
                            showAlert("Ingresa un RFC correcto",{type: 3});
                            this.supplier.rfc = "";
                        }
                    }
                },
                filters: {
                    numbersPadding: function (value) {
                        var result = "";
                        var padding = 3;
                        if (typeof value != 'undefined') {
                            result = "0000" + value;
                            result = result.substr(result.length - padding);
                        }
                        return result;
                    },
                    changeidBank: function (value) {
                        var name;
                        this.banks.forEach(function (element) {
                            if (value == element.idBank) {
                                name = element.acronyms;
                            }
                        });
                        return name;
                    },
                    changeidCurrency: function (value) {
                        var name;
                        this.currencies.forEach(function (element) {
                            if (value == element.idCurrency) {
                                name = element.currency;
                            }
                        });
                        return name;
                    },
                    changeidProduct: function(value) {
                      var name;
                        this.productos.forEach(function (element) {
                            if(value == element.idProductType){
                                name = element.productType;
                            }
                        });
                        return name;
                    },
                    separateProviderName: function (value) {
                        var name;
                        this.providers.forEach(function (element) {
                            if (value == element.providerName) {
                                name = element.providerName.replace(/:/g, " ");
                            }
                        });
                        return name;
                    },
                    separate: function (value) {
                        return value.replace(/:/g, " ");
                    }
                }
            });


        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos" class="flex-box container-fluid">
            <br>
            <div>
                <div class="row">
                    <div class="col-xs-8 text-header">
                        <h2>Proveedores</h2>
                    </div>

                    <div class="col-xs-4 text-right" style="padding: 0px">
                        <div class="col-xs-7 text-left" style="padding: 0px">
                            <label>Buscar por RFC</label>
                            <input class="form-control" maxlength="13" v-model="search">
                        </div>


                        <button type="button" class="btn btn-default" name="button"
                                style="margin-top: 25px" data-toggle="modal" data-target="#modalAlta">
                            Nuevo proveedor
                        </button>
                    </div>
                </div>


                <div>
                    <div class="row table-header">
                        <div class="col-xs-3"><b>Nombre/Razón social</b></div>
                        <div class="col-xs-3"><b>RFC</b></div>
                        <div class="col-xs-2"><b>Dias de crédito</b></div>
                        <div class="col-xs-2"><b>Día de corte</b></div>
                        <div class="col-xs-1"><b>Modificar</b></div>
                        <div class="col-xs-1"><b>Eliminar</b></div>
                    </div>
                </div>
            </div>
            <br>
            <div class="table-body flex-row flex-content">
                <div class="row table-row" v-for="provider in providers | filterBy search in 'rfc'" v-if="provider.supplierLow == null">
                    <div class="col-xs-3">{{provider.providerName | separateProviderName}}</div>
                    <div class="col-xs-3">{{provider.rfc}}</div>
                    <div class="col-xs-2">{{provider.creditDays}} dias</div>
                    <div class="col-xs-2">{{provider.cuttingDate}} del mes</div>
                    <div class="col-xs-1">
                        <button type="button" class="btn btn-default" name="button" data-toggle="tooltip"
                                data-placement="bottom" title="Modificar"
                                @click="modifyProvider(provider)"><span class="glyphicon glyphicon-pencil"></span>
                        </button>
                    </div>
                    <div class="col-xs-1">
                        <button type="button" class="btn btn-danger" name="button" data-toggle="tooltip"
                                data-placement="bottom" title="Eliminar"
                                @click="question(provider)"><span class="glyphicon glyphicon-trash"></span></button>
                    </div>
                </div>
            </div>
            <!-- container-fluid -->
            <div class="modal fade" id="modalAlta" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Registro de proveedor</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>RFC</label>
                                    <input maxlength="13" class="form-control" name="name" v-model="supplier.rfc"
                                           @change="numberAndLetter(supplier.rfc)" onkeypress="return isNumberKeyAndLetterKey(event)">
                                </div>
                            </div>
                            <br>
                            <div class="row" v-show="supplier.rfc.length==12">
                                <div class="col-xs-6">
                                    <label>Razón social</label>
                                    <input class="form-control" name="name" v-model="supplier.providerName">
                                </div>
                            </div>
                            <div class="row" v-show="supplier.rfc.length==13">
                                <div class="col-xs-3">
                                    <label>Nombre(s)</label>
                                    <input class="form-control" name="name" v-model="providerNames">
                                </div>
                                <div class="col-xs-3">
                                    <label>Apellido paterno</label>
                                    <input class="form-control" name="name" v-model="providerLastName">
                                </div>
                                <div class="col-xs-3">
                                    <label>Apellido materno</label>
                                    <input class="form-control" name="name" v-model="providerSecondName">
                                </div>
                            </div>
                            <br>
                            <div v-show="supplier.rfc.length >= 12">
                                <div class="row">
                                    <div class="col-xs-4">
                                        <label>Cuenta contable</label>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-4">
                                        <label>Distribuidor</label>
                                        <select class="form-control" name="" v-model="distributor.idDistributor">
                                            <option></option>
                                            <option v-for="distributor in distributors"
                                                    value="{{distributor.idDistributor}}">
                                                {{distributor.distributorName}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-2">
                                        <label>Primer nivel</label>
                                        <input maxlength="4" class="form-control text-center" name="name"
                                               v-model="supplier.firstLevel">
                                    </div>
                                    <div class="col-xs-2">
                                        <label>Segundo nivel</label>
                                        <input maxlength="3" class="form-control text-center" name="name"
                                               v-model="supplier.secondLevel">
                                    </div>
                                    <div class="col-xs-2">
                                        <label>Tercer nivel</label>
                                        <input maxlength="3" class="form-control text-center" name="name"
                                               v-model="supplier.thirdLevel">
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-4">
                                        <label>Proveedor de</label>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-3">
                                        <label>Rubro</label>
                                        <select class="form-control" name="" v-model="requestInformation.idRequestType"
                                                @change="obtainRequestTypeProduct">
                                            <option></option>
                                            <option v-for="RequestType in RequestTypes"
                                                    value="{{RequestType.idRequestType}}">
                                                {{RequestType.requestType}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Producto</label>
                                        <select class="form-control" name="" v-model="requestInformation.idProductType">
                                            <option></option>
                                            <option v-for="ProductType in ProductTypes"
                                                    value="{{ProductType.productType.idProductType}}">
                                                {{ProductType.productType.productType}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-1">
                                        <button type="button" class="btn btn-sm btn-default" data-toggle="tooltip"
                                                data-placement="bottom" title="Agregar" style="margin-top: 25px"
                                                @click="validationProduct()">
                                            <span class="glyphicon glyphicon-plus"></span>
                                        </button>
                                    </div>
                                </div>
                                <br>
                                <table class="table table-striped" v-show="supplier.providersProductsTypes.length> 0">
                                    <thead>
                                    <th class="col-xs-10">Producto</th>
                                    <th class="col-xs-1">Eliminar</th>
                                    </thead>
                                    <tbody>
                                    <tr v-for="product in supplier.providersProductsTypes">
                                        <td class="col-xs-10">{{product.idProductType | changeidProduct}}</td>
                                        <td class="col-xs-1">
                                            <button class="btn btn-danger" @click="deleteProduct(product)"
                                                    :disabled="isUpdate"
                                                    data-toggle="tooltip" data-placement="top" title="Quitar Producto">
                                                <span class="glyphicon glyphicon-trash"></span>
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <br>
                                <div class="row">
                                    <div class="col-xs-4">
                                        <label>Dirección</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <hr>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-3">
                                        <label>Calle</label>
                                        <input class="form-control" name="name" v-model="direccion.street">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Número Exterior</label>
                                        <input class="form-control" name="name" maxlength="5"
                                               v-model="direccion.numExt">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Número Interior</label>
                                        <input class="form-control" name="name" maxlength="5"
                                               v-model="direccion.numInt">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Código postal</label>
                                        <input class="form-control" name="name" maxlength="5" v-model="direccion.cp"
                                               @input="obtainAsentamientos"
                                               onkeypress="return isNumberKey(event)">
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-3">
                                        <label>Estado</label>
                                        <input class="form-control" name="name"
                                               v-model="estadosMunicipios.estado.nombreEstado" value="" disabled="true">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Municipio/Delegación</label>
                                        <input class="form-control" name="name"
                                               v-model="estadosMunicipios.nombreMunicipios" value="" disabled="true">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Colonia</label>
                                        <select class="form-control" name="" v-model="direccion.idAsentamiento">
                                            <option></option>
                                            <option v-for="set in asentamiento" value="{{set.idAsentamiento}}">
                                                {{set.nombreAsentamiento}}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <br>
                                <label>Información de Contacto</label>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <hr>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-3">
                                        <label>Nombre</label>
                                        <input class="form-control" name="name" v-model="name"
                                               onkeypress="return isLetterKey(event)">
                                    </div>
                                    <div class="col-xs-2">
                                        <label>Puesto</label>
                                        <input class="form-control" name="name" v-model="post"
                                               onkeypress="return isLetterKey(event)">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Teléfono (10 dígitos)</label>
                                        <input maxlength="10" class="form-control" name="name" v-model="phoneNumber"
                                               onkeypress="return isNumberKey(event)">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Correo</label>
                                        <input class="form-control" name="name" v-model="email"
                                               @change="validateEmail(email)">
                                    </div>
                                    <div class="col-xs-1">
                                        <button type="button" class="btn btn-sm btn-default" data-toggle="tooltip"
                                                data-placement="bottom" title="Agregar" style="margin-top: 25px"
                                                @click="validateContact()">
                                            <span class="glyphicon glyphicon-plus"></span>
                                        </button>
                                    </div>
                                </div>
                                <br>
                                <table class="table table-striped" v-show="supplier.providersContactList.length> 0">
                                    <thead>
                                    <th class="col-xs-2">Nombre</th>
                                    <th class="col-xs-2">Puesto</th>
                                    <th class="col-xs-3">Teléfono (10 dígitos)</th>
                                    <th class="col-xs-4">Correo</th>
                                    <th class="col-xs-1">Eliminar</th>
                                    </thead>
                                    <tbody>
                                    <tr v-for="phone in supplier.providersContactList">
                                        <td class="col-xs-3">{{phone.name}}</td>
                                        <td class="col-xs-2">{{phone.post}}</td>
                                        <td class="col-xs-3">{{phone.phoneNumber}}</td>
                                        <td class="col-xs-3">{{phone.email}}</td>
                                        <td class="col-xs-1">
                                            <button class="btn btn-danger" @click="deletePhone(phone)"
                                                    :disabled="isUpdate"
                                                    data-toggle="tooltip" data-placement="top" title="Quitar Contacto">
                                                <span class="glyphicon glyphicon-trash"></span>
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <br>
                                <label>Cuentas Bancarias</label>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <hr>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-3">
                                        <label>Banco</label>
                                        <select class="form-control" name="" v-model="idBanks">
                                            <option></option>
                                            <option v-for="bank in banks" value="{{bank.idBank}}">{{bank.acronyms}}
                                            </option>
                                        </select>
                                    </div>

                                    <div class="col-xs-3">
                                        <label>Número de Cuenta</label>
                                        <div class="input-group">
                                            <span class="input-group-addon">#</span>
                                            <input id="saccount" class="form-control" maxlength="12"
                                                   v-model="accountNumbers"
                                                   @change="validateCuenta(accountNumbers,clabes)"
                                                   onkeypress="return isNumberKey(event)">
                                        </div>
                                    </div>

                                    <div class="col-xs-3">
                                        <label>CLABE</label>
                                        <div class="input-group">
                                            <span class="input-group-addon">#</span>
                                            <input type="text" id="sclabe" class="form-control" maxlength="18"
                                                   v-model="clabes" @change="validateClabe(accountNumbers,clabes)"
                                                   onkeypress="return isNumberKey(event)">
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <label>Moneda</label>
                                        <select class="form-control" name="" v-model="idCurrency">
                                            <option></option>
                                            <option v-for="curre in currencies" value="{{curre.idCurrency}}">
                                                {{curre.currency}}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-xs-1">
                                        <button type="button" class="btn btn-sm btn-default" data-toggle="tooltip"
                                                data-placement="bottom" title="Agregar" style="margin-top: 25px"
                                                @click="validationAccount">
                                            <span class="glyphicon glyphicon-plus"></span>
                                        </button>
                                    </div>
                                </div>

                                <table class="table table-striped" v-show="supplier.providersAccountsList.length> 0">
                                    <thead>
                                    <th class="col-xs-2">Banco</th>
                                    <th class="col-xs-3">Número de cuenta</th>
                                    <th class="col-xs-4">CLABE</th>
                                    <th class="col-xs-2">Moneda</th>
                                    <th class="col-xs-1">Eliminar</th>
                                    </thead>
                                    <tbody>
                                    <tr v-for="supplier in supplier.providersAccountsList">
                                        <td class="col-xs-2">{{supplier.idBank | changeidBank }}</td>
                                        <td class="col-xs-3">{{supplier.accountNumber }}</td>
                                        <td class="col-xs-4">{{supplier.accountClabe}}</td>
                                        <td class="col-xs-2">{{supplier.idCurrency | changeidCurrency}}</td>
                                        <td class="col-xs-1">
                                            <button type="button" class="btn btn-danger" data-toggle="tooltip"
                                                    data-placement="bottom" title="Eliminar"
                                                    @click="eliminarCuenta(supplier)">
                                                <span class="glyphicon glyphicon-trash"></span>
                                            </button>
                                    </tr>
                                    </tbody>
                                </table>
                                <br>
                                <div class="row">
                                    <div class="col-xs-4">
                                        <label>Crédito</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <hr>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-3">
                                        <label>Días de crédito</label>
                                        <input class="form-control" name="name" v-model="supplier.creditDays"
                                               onkeypress="return isNumberKey(event)">
                                    </div>
                                    <div class="col-xs-3">
                                        <label>Fecha de corte</label>
                                        <select class="form-control" name="" v-model="supplier.cuttingDate">
                                            <option></option>
                                            <option v-for="i in 31" value="{{i+1}}">{{i+1}}</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">

                            <button type="button" class="btn btn-success" @click="validationSave">
                                Guardar
                            </button>

                            <button type="button" class="btn btn-default" @click="cancelar">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>


            <div class="modal fade" id="modalModi" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Modificar proveedor</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>RFC</label>
                                    <input maxlength="13" class="form-control" name="name" v-model="provider.rfc"
                                           disabled="true">
                                </div>
                            </div>
                            <div class="row" v-show="provider.rfc.length==12">
                                <div class="col-xs-4">
                                    <label>Razón social</label>
                                    <input class="form-control" name="name" v-model="provider.providerName"
                                           disabled="true">
                                </div>
                            </div>
                            <div class="row" v-show="provider.rfc.length==13">
                                <div class="col-xs-3">
                                    <label>Nombre(s)</label>
                                    <input class="form-control" name="name" v-model="supplierNames"
                                           onkeypress="return isLetterKey(event)" disabled="true">
                                </div>
                                <div class="col-xs-3">
                                    <label>Apellido paterno</label>
                                    <input class="form-control" name="name" v-model="supplierLastName"
                                           onkeypress="return isLetterKey(event)" disabled="true">
                                </div>
                                <div class="col-xs-3">
                                    <label>Apellido materno</label>
                                    <input class="form-control" name="name" v-model="supplierSecondName"
                                           onkeypress="return isLetterKey(event)" disabled="true">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>Cuenta contable</label>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-3">
                                    <label>Distribuidor</label>
                                    <p>{{ provider.accountingAccounts.distributor.distributorName }}</p>
                                </div>
                                <div class="col-xs-3">
                                    <label>Cuenta contable</label>
                                    <p>
                                        {{ provider.accountingAccounts.firstLevel }} -
                                        {{ provider.accountingAccounts.secondLevel | numbersPadding }} -
                                        {{ provider.accountingAccounts.thirdLevel | numbersPadding }}
                                    </p>
                                </div>
                                <div class="col-xs-6">
                                    <label>Descripción</label>
                                    <p>{{ provider.accountingAccounts.description }}</p>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>Proveedor de</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12">
                                    <hr>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-3">
                                    <label>Rubro</label>
                                    <select class="form-control" name="" v-model="requestInformation.idRequestType" @change="obtainRequestTypeProduct">
                                        <option></option>
                                        <option v-for="RequestType in RequestTypes" value="{{RequestType.idRequestType}}">
                                            {{RequestType.requestType}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-3">
                                    <label>Producto</label>
                                    <select class="form-control" name="" v-model="requestInformation.idProductType">
                                        <option></option>
                                        <option v-for="ProductType in ProductTypes" value="{{ProductType.productType.idProductType}}">
                                            {{ProductType.productType.productType}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-1">
                                    <button type="button" class="btn btn-sm btn-default" data-toggle="tooltip"
                                            data-placement="bottom" title="Agregar" style="margin-top: 25px"
                                            @click="saveProviderProduct(provider,requestInformation)">
                                        <span class="glyphicon glyphicon-plus"></span>
                                    </button>
                                </div>
                            </div>
                            <br>
                            <table class="table table-striped" v-show="provider.providersProductsTypes.length> 0">
                                <thead>
                                    <th class="col-xs-10">Producto</th>
                                    <th class="col-xs-1">Eliminar</th>
                                </thead>
                                <tbody>
                                    <tr v-for="product in provider.providersProductsTypes">
                                        <td class="col-xs-10">
                                            {{product.idProductType | changeidProduct}}
                                        </td>
                                        <td class="col-xs-1">
                                            <button class="btn btn-danger" @click="removeProviderProduct(product)" :disabled="isUpdate"
                                                    data-toggle="tooltip" data-placement="top" title="Quitar Producto">
                                                <span class="glyphicon glyphicon-trash"></span>
                                            </button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <br>
                            <div class="row">
                                <div class="col-xs-4">
                                    <label>Dirección</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3">
                                    <label>Calle</label>
                                    <input class="form-control" name="name" v-model="direccion.street">
                                </div>
                                <div class="col-xs-3">
                                    <label>Número  Exterior</label>
                                    <input class="form-control" name="name" maxlength="5" v-model="direccion.numExt">
                                </div>
                                <div class="col-xs-3">
                                    <label>Número Interior</label>
                                    <input class="form-control" name="name" maxlength="5" v-model="direccion.numInt">
                                </div>
                                <div class="col-xs-3">
                                    <label>Código postal</label>
                                    <input class="form-control" name="name" maxlength="5" v-model="direccion.cp" @input="obtainAsentamientos"
                                           onkeypress="return isNumberKey(event)">
                                </div>
                                <div class="col-xs-3">
                                    <label>Estado</label>
                                    <input class="form-control" name="name" v-model="estadosMunicipios.estado.nombreEstado" value="" disabled="true">
                                </div>
                                <div class="col-xs-3">
                                    <label>Municipio/Delegación</label>
                                    <input class="form-control" name="name" v-model="estadosMunicipios.nombreMunicipios" value="" disabled="true">
                                </div>
                                <div class="col-xs-3">
                                    <label>Colonia</label>
                                    <select class="form-control" name="" v-model="direccion.idAsentamiento">
                                        <option></option>
                                        <option v-for="set in asentamiento" value="{{set.idAsentamiento}}">
                                            {{set.nombreAsentamiento}}
                                        </option>
                                    </select>
                                </div>
                                <div class="col-xs-2 text-left">
                                    <button class="btn btn-default" @click="addAddress(provider,direccion)"
                                            :disabled="isUpdate" style="margin-top: 25px" data-toggle="tooltip"
                                            data-placement="top" title="Agregar Dirección">
                                        <span class="glyphicon glyphicon-plus"></span>
                                    </button>
                                </div>
                            </div>
                            <br>
                            <table class="table table-striped" v-show="provider.providerAddressList.length> 0">
                                <thead>
                                    <th class="col-xs-2">Calle</th>
                                    <th class="col-xs-1">Número Exterior</th>
                                    <th class="col-xs-1">Número Interior</th>
                                    <th class="col-xs-1">Código postal</th>
                                    <th class="col-xs-2">Estado</th>
                                    <th class="col-xs-2">Municipio/Delegación</th>
                                    <th class="col-xs-2">Colonia</th>
                                    <th class="col-xs-1">Eliminar</th>
                                </thead>
                                <tbody>
                                <tr v-for="address in provider.providerAddressList">
                                    <td class="col-xs-2">{{address.street}}</td>
                                    <td class="col-xs-2">{{address.numExt}}</td>
                                    <td class="col-xs-1">{{address.numInt}}</td>
                                    <td class="col-xs-1">{{address.asentamiento.codigoPostal}}</td>
                                    <td class="col-xs-1">{{address.asentamiento.municipios.estados.nombreEstado}}</td>
                                    <td class="col-xs-2">{{address.asentamiento.municipios.nombreMunicipios}}</td>
                                    <td class="col-xs-2">{{address.asentamiento.nombreAsentamiento}}</td>
                                    <td class="col-xs-1">
                                        <button v-if="provider.providerAddressList.length > 1"
                                                class="btn btn-danger" @click="removeAddress(address)"
                                                :disabled="isUpdate" data-toggle="tooltip" data-placement="top"
                                                title="Quitar Dirección">
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <br>
                            <label>Información de Contacto</label>
                            <div class="row">
                                <div class="col-xs-12">
                                    <hr>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3">
                                    <label>Nombre</label>
                                    <input class="form-control" name="name" v-model="telephone.name"
                                           onkeypress="return isLetterKey(event)">
                                </div>
                                <div class="col-xs-2">
                                    <label>Puesto</label>
                                    <input class="form-control" name="name" v-model="telephone.post"
                                           onkeypress="return isLetterKey(event)">
                                </div>
                                <div class="col-xs-3">
                                    <label>Teléfono (10 dígitos)</label>
                                    <input maxlength="10" class="form-control" name="name"
                                           v-model="telephone.phoneNumber" onkeypress="return isNumberKey(event)">
                                </div>
                                <div class="col-xs-3">
                                    <label>Correo</label>
                                    <input class="form-control" name="name" v-model="telephone.email" @change="validateEmail(telephone.email)">
                                </div>
                                <div class="col-xs-1">
                                    <button type="button" class="btn btn-sm btn-default" data-toggle="tooltip"
                                            data-placement="bottom" title="Agregar" style="margin-top: 25px"
                                            @click="addProviderPhone(provider, telephone)">
                                        <span class="glyphicon glyphicon-plus"></span>
                                    </button>
                                </div>
                            </div>
                            <br>
                            <table class="table table-striped" v-show="provider.providersContactList.length> 0">
                                <thead>
                                    <th class="col-xs-2">Nombre</th>
                                    <th class="col-xs-2">Puesto</th>
                                    <th class="col-xs-3">Teléfono (10 dígitos)</th>
                                    <th class="col-xs-4">Correo</th>
                                    <th class="col-xs-1">Eliminar</th>
                                </thead>
                                <tbody>
                                <tr v-for="phone in provider.providersContactList">
                                    <td class="col-xs-2">{{phone.name}}</td>
                                    <td class="col-xs-2">{{phone.post}}</td>
                                    <td class="col-xs-3">{{phone.phoneNumber}}</td>
                                    <td class="col-xs-4">{{phone.email}}</td>
                                    <td class="col-xs-1">
                                        <button v-if="provider.providersContactList.length > 1"
                                                class="btn btn-danger" @click="removePhone(phone)" :disabled="isUpdate"
                                                data-toggle="tooltip" data-placement="top" title="Quitar Numero">
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <br>
                            <label>Cuentas Bancarias</label>
                            <hr>
                            <div class="row">
                                <div class="col-xs-3">
                                    <label>Banco</label>
                                    <select class="form-control" name="" v-model="cuenta.idBank">
                                        <option></option>
                                        <option v-for="bank in banks" value="{{bank.idBank}}">{{bank.acronyms}}</option>
                                    </select>
                                </div>

                                <div class="col-xs-3">
                                    <label>Número de cuenta</label>
                                    <div class="input-group">
                                        <span class="input-group-addon">#</span>
                                        <input class="form-control" maxlength="12" v-model="cuenta.accountNumber"
                                               @change="validateCuenta(cuenta.accountNumber,cuenta.accountClabe)"
                                               onkeypress="return isNumberKey(event)">
                                    </div>
                                </div>

                                <div class="col-xs-3">
                                    <label>CLABE</label>
                                    <div class="input-group">
                                        <span class="input-group-addon">#</span>
                                        <input type="text" class="form-control" maxlength="18"
                                               @change="validateClabe(cuenta.accountNumber,cuenta.accountClabe)"
                                               v-model="cuenta.accountClabe" onkeypress="return isNumberKey(event)">
                                    </div>
                                </div>

                                <div class="col-xs-2">
                                    <label>Moneda</label>
                                    <select class="form-control" name="" v-model="cuenta.idCurrency">
                                        <option></option>
                                        <option v-for="curre in currencies" value="{{curre.idCurrency}}">
                                            {{curre.currency}}
                                        </option>
                                    </select>
                                </div>

                                <div class="col-xs-1">
                                    <button type="button" class="btn btn-sm btn-default" data-toggle="tooltip"
                                            data-placement="bottom" title="Agregar" style="margin-top: 25px"
                                            @click="addProviderAccount(provider,cuenta)">
                                        <span class="glyphicon glyphicon-plus"></span>
                                    </button>
                                </div>
                            </div>

                            <table class="table table-striped" v-show="provider.providersAccountsList.length> 0">
                                <thead>
                                    <th>Banco</th>
                                    <th>Número de cuenta</th>
                                    <th>CLABE</th>
                                    <th>Moneda</th>
                                    <th>Eliminar</th>
                                </thead>
                                <tbody>
                                <tr v-for="account in provider.providersAccountsList" v-if="account.deleteDay == null">
                                    <td class="col-xs-2">{{account.idBank | changeidBank }}</td>
                                    <td class="col-xs-3">{{account.accountNumber }}</td>
                                    <td class="col-xs-4">{{account.accountClabe}}</td>
                                    <td class="col-xs-2">{{account.idCurrency | changeidCurrency}}</td>
                                    <td class="col-xs-1">
                                        <button v-if="provider.providersAccountsList.length > 1"
                                                type="button" class="btn btn-danger" data-toggle="tooltip"
                                                data-placement="bottom" title="Eliminar"
                                                @click="questionAccount(account)">
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <br>
                            <div class="row" v-show="provider.rfc.length==12||provider.rfc.length==13">
                                <div class="col-xs-4">
                                    <label>Crédito</label>
                                </div>
                            </div>
                            <hr>
                            <div class="row" v-show="provider.rfc.length==12||provider.rfc.length==13">
                                <div class="col-xs-3">
                                    <label>Días de crédito</label>
                                    <input class="form-control" name="name" v-model="provider.creditDays"
                                           onkeypress="return isNumberKey(event)">
                                </div>
                                <div class="col-xs-3">
                                    <label>Fecha de corte</label>
                                    <select class="form-control" name="" v-model="provider.cuttingDate">
                                        <option></option>
                                        <option v-for="i in 31" value="{{i+1}}">{{i+1}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">

                            <button type="button" class="btn btn-success" @click="updateProvider(provider)">
                                Guardar
                            </button>

                            <button type="button" class="btn btn-default" @click="cancelar">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="modalPregunta" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
                <div class="modal-dialog ">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">
                                Eliminar proveedor
                            </h4>
                        </div>
                        <div class="modal-body">
                            <p>El proveedor {{modalPregunta.provider.providerName|separate}} será
                                eliminado</p>
                        </div>
                        <div class="modal-footer">
                            <button id="btnFlag" type="button" class="btn btn-default" @click="deleteProvider">
                                Aceptar
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="modalCuenta" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
                <div class="modal-dialog ">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">
                                Eliminar cuenta
                            </h4>
                        </div>
                        <div class="modal-body">
                            <p>La cuenta {{modalCuenta.account.accountNumber}} sera eliminada</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" @click="deleteAccount(modalCuenta.account)">
                                Aceptar
                            </button>
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
