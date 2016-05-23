<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />


<t:template pageTitle="BID Group: Proveedores">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
        function isNumberKey(evt)
        {
        var charCode = (evt.which) ? evt.which : event.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
        return true;
        }
        </script>

        <script>
            function isLetterKey(evt)
            {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 32 ||
                        charCode === 13 ||
                        (charCode > 64 && charCode < 91) ||
                        (charCode > 96 && charCode < 123) ||
                        charCode === 8
                )
                {
                    return true;
                }
                else {
                    return false;
                }
            }
        </script>

        <script type="text/javascript">
        var vm= new Vue({
        el: '#contenidos',
        created: function()
        {

        },
        ready: function ()
        {
          this.obtainBanks();
          this.getProviders();
          this.obtainCurrencies();
          this.obtainSettlements();
          this.obtainStates();
          this.obtainMunicipalities();
          },
        data:
        {
          supplier:
          {
            providerName: '',
            businessName: '',
            rfc: '',
            accountingAccount: '',
            providersAccountsList: [],
            providersContactList: [],
            addressProvider: []
          },
          provider: {
            providerName: '',
            businessName: '',
            rfc: '',
              accountingAccount: '',
            providersAccountsList: [],
            providersContactList:[],
            addressProvider: []
          },
          idBanks: '',
          banks: [],
          providers: {},
          search: '',
          providerNames:'',
          providerLastName:'',
          providerSecondName:'',
          supplierNames:'',
          supplierLastName:'',
          supplierSecondName:'',
          currencies: {},
          states:{},
          idCurrency:'',
          idState:'',
          settlements:{},
          idSettlement:'',
          municipalities:{},
          idMunicipality:'',
          telephone:
          {
              phoneNumber:'',
            email: "",
            post: "",
            name: "",
          },
          cuenta:
          {
            accountNumber: '',
            accountClabe: '',
            idBank: '',
            idCurrency: '',
          },
          direccion:{
            cp: '',
              numExt:'',
            numInt:'',
            street:'',
            idSettlement: '',
            idState:'',
            idMunicipality:'',
          },
            modalPregunta:{
                provider:{
                    providerName: ""
                }
            },
            phoneNumber:'',
            email:'',
            name:'',

          },
        methods: {
          providerRfc: function () {
            var providerNames = this.providerNames;
            var providerLastName = this.providerLastName;
            var providerSecondName = this.providerSecondName;

            this.supplier.providerName=(providerNames+":"+providerLastName+":"+providerSecondName);

          },
          showName: function (provider) {
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

            this.provider.providerName=(providerNames+":"+providerLastName+":"+providerSecondName);
          },
          saveAccount: function (bank,account,clabe,currency) {
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
          validationAccount: function () {
                if(this.supplier.providersAccountsList.length!=0) {
                    if(this.idBanks.length!=0&&this.accountNumbers.length!=0&&this.clabes.length!=0&&this.idCurrency.length!=0){
                        this.saveAccount(this.idBanks,this.accountNumbers,this.clabes,this.idCurrency);
                        return true;
                    }
                } else {
                    if(this.idBanks.length!=0&&this.accountNumbers.length!=0&&this.clabes.length!=0&&this.idCurrency.length!=0){
                        this.saveAccount(this.idBanks,this.accountNumbers,this.clabes,this.idCurrency);
                        return true;
                    }else {
                        showAlert("Ingresa los campos Requeridos: Banco, Número de Cuenta, CLABE, Moneda");
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

          saveAddress: function () {
              var direccion = {
                  cp: '',
                  numExt: '',
                  numInt: '',
                  street: '',
                  idSettlement: '',
                  idState: '',
                  idMunicipality: '',
              };
              direccion.cp = this.direccion.cp;
              direccion.numExt = this.direccion.numExt;
              direccion.numInt = this.direccion.numInt;
              direccion.street = this.direccion.street;
              direccion.idState = this.direccion.idState;
              direccion.idSettlement = this.direccion.idSettlement;
              direccion.idMunicipality = this.direccion.idMunicipality;

              this.supplier.addressProvider.push(direccion);
          },
          saveProvider: function () {
             this.saveAddress();
              var validation2 = this.validationContact();
              var validation = this.validationAccount();
              if (validation == true && validation2 == true) {
                  if (this.supplier.rfc.length == 13) {
                      this.providerRfc();
                  }

                  this.$http.post(ROOT_URL + "/providers", JSON.stringify(this.supplier)).success(function (data) {
                      showAlert("Registro de Proveedor Exitoso");
                      $('#modalAlta').modal('hide');
                      this.getProviders();

                  }).error(function () {
                      showAlert("Ha habido un error con la solicitud, intente nuevamente");
                  });

                  this.cancelar();
              }

          },
          getProviders: function () {
            this.$http.get(ROOT_URL + "/providers")
                    .success(function (data) {
                      this.providers = data;
                    });
          },
          modifyProvider: function (provider) {
            this.provider = (JSON.parse(JSON.stringify(provider)));

            if(this.provider.rfc.length==13){
              this.showName(this.provider);
            }
            $('#modalModi').modal('show');

            this.$http.get(ROOT_URL + "/provider-address/provider/"+this.provider.idProvider)
                    .success(function (data) {
                      this.provider.addressProvider = data;
                    });
            this.$http.get(ROOT_URL + "/accounts/provider/" + this.provider.idProvider)
                    .success(function (data) {
                      Vue.set(this.provider, 'providersAccountsList', data);
                    });
            this.$http.get(ROOT_URL + "/provider-contact/provider/"+this.provider.idProvider)
                    .success(function (data) {
                      Vue.set(this.provider,'providersContactList', data);
                    });
          },
          deleteAccount: function (account) {
           this.$http.delete(ROOT_URL + "/accounts/" + account.idAccount)
                    .success(function (data) {
                      this.provider.providersAccountsList.$remove(account);
                      showAlert("Cuenta Eliminada");
                    });

          },
          removePhone: function (phone) {
            this.$http.delete(ROOT_URL + "/provider-contact/" + phone.idProviderContact)
                    .success(function (data) {
                      showAlert("Telefono Eliminado");
                      this.provider.providersContactList.$remove(phone);
                    });
          },
          addProviderAccount: function (supplier, cuenta) {
            this.$http.post(ROOT_URL + "/accounts/provider/" + supplier.idProvider, cuenta)
                    .success(function (data) {
                      showAlert("Cuenta Guardada con Exito");
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
            if(provider.rfc.length==13){
              this.modifyName();
            }
            this.$http.post(ROOT_URL + "/providers/" + provider.idProvider, provider)
                    .success(function (data) {
                      showAlert("Proveedor Actualizado");
                      $('#modalModi').modal('hide');
                      this.getProviders();
                    }).error(function () {
              showAlert("Ha habido un error con la solicitud, intente nuevamente");
            });
          },
          deleteProvider: function () {
              this.$http.post(ROOT_URL + "/providers/low/" + this.modalPregunta.provider.idProvider)
                      .success(function (data) {
                          this.getProviders();
                          $('#modalPregunta').modal('hide');
                          showAlert("Provedor Eliminado");
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
          savePhone: function (names,phones,emails,posts) {
              var contact={
                  phoneNumber:"",
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
            validationContact: function () {
                if(this.supplier.providersContactList.length!=0) {
                    if(this.name.length!=0&&this.phoneNumber.length!=0&&this.email.length!=0){
                        this.savePhone(this.name,this.phoneNumber,this.email,this.post);
                        return true;
                    }
                } else {
                if(this.name.length!=0&&this.phoneNumber.length!=0&&this.email.length!=0){
                    this.savePhone(this.name,this.phoneNumber,this.email,this.post);
                    return true;
                }else {
                    showAlert("Ingresa los campos Requeridos: Nombre, Teléfono, Email");
                    return false;
                }
                }
            },
          deletePhone: function (phone) {
            this.supplier.providersContactList.$remove(phone);
            this.phoneNumbers = '';
          },
          addProviderPhone: function (supplier, phone) {
            this.$http.post(ROOT_URL + "/provider-contact/provider/" + supplier.idProvider, phone)
                    .success(function (data) {
                      this.provider.providersContactList.push(data);
                      this.phoneNumbers = '';
                      showAlert("Contacto Guardado con Éxito");
                      this.telephone.phoneNumbers = '';
                    });
              this.telephone.name="";
              this.telephone.email="";
              this.telephone.phoneNumber="";
              this.telephone.post="";
          },
         cancelar: function () {

            this.cuenta.accountNumber= '';
            this.cuenta.accountClabe= '';
            this.cuenta.idBank='';
            this.cuenta.idCurrency= '';
            this.clabes="";
            this.accountNumbers="";
            this.direccion.cp= '';
            this.direccion.numExt='';
            this.direccion.numInt='';
            this.direccion.street='';
            this.direccion.idSettlement= '';
            this.direccion.idState='';
            this.direccion.idMunicipality='';
            this.idBanks= '';
            this.providerNames='';
            this.providerLastName='';
            this.providerSecondName='';
            this.idCurrency='';
            this.idState='';
            this.idSettlement='';
            this.idMunicipality='';
            this.telephone.phoneNumbers='';
            this.telephone.email='';
            this.telephone.name='';
            this.telephone.post='';
            this.supplier.providerName= '';
            this.supplier.businessName= '';
            this.supplier.providersAccountsList=[];
            this.supplier.providersContactList=[];
            this.supplier.rfc= '';
            this.supplier.accountingAccount= '';
            this.name="";
            this.email="";
            this.phoneNumber="";
            this.post="";

            $('#modalAlta').modal('hide');
          },
         question: function (provider) {
             this.modalPregunta.provider = provider;
             $('#modalPregunta').modal('show');
         }
        },
        filters:
        {
          changeidBank: function(value)
          {
            var name;
          this.banks.forEach(function (element)
          {
            if (value == element.idBank)
            {
              name= element.acronyms;
            }
          });
            return name;
          },
          changeidCurrency: function(value)
          {
            var name;
            this.currencies.forEach(function (element)
            {
              if (value == element.idCurrency)
              {
                name= element.currency;
              }
            });
            return name;
          },
          separateProviderName: function (value) {
            var name;
            this.providers.forEach(function (element) {
              if(value == element.providerName){
                name = element.providerName.replace(/:/g," ");
              }
            });
            return name;
          },
            separate: function (value) {
                return value.replace(/:/g," ");
            }
        }
      });



        </script>
    </jsp:attribute>

    <jsp:body>
      <div id="contenidos">
          <br>
      <div class="container-fluid" style="margin-left: 100px">
        <div class="row">
          <div class="col-xs-8 text-left" style="padding: 0px">
          <h1>Proveedores</h1>
          </div>

            <div class="col-xs-4 text-right" style="padding: 0px">
                <div class="col-xs-7 text-left" style="padding: 0px">
            <label>
              Buscar por RFC
            </label>
            <input class="form-control" maxlength="13" v-model="search">
            </div>


            <button type="button" class="btn btn-default" name="button"
              style="margin-top: 25px" data-toggle="modal" data-target="#modalAlta">
             Nuevo Proveedor
            </button>
          </div>

          <table class="table table-striped">
            <thead>
              <th>
                Nombre/Razón social
              </th>
              <th>
                Cuenta contable
              </th>
              <th>
                RFC
              </th>
              <th>
                Modificar
              </th>
              <th>
                Eliminar
              </th>
            </thead>

            <tbody>
              <tr v-for="provider in providers | filterBy search" v-if="provider.supplierLow == null">
                <td>
                  {{provider.providerName | separateProviderName}}
                </td>
                <td>
                  {{provider.accountingAccount}}
                </td>
                <td>
                  {{provider.rfc}}
                </td>
                <td>
                  <button type="button" class="btn btn-default" name="button" data-toggle="tooltip" data-placement="bottom" title="Modificar Proveedor"
                          @click="modifyProvider(provider)"><span class="glyphicon glyphicon-pencil"></span></button>
                </td>
                <td>
                  <button type="button" class="btn btn-default" name="button" data-toggle="tooltip" data-placement="bottom" title="Eliminar Proveedor"
                          @click="question(provider)"><span class="glyphicon glyphicon-trash"></span></button>
                </td>
              </tr>
            </tbody>

          </table>

        </div>
      </div> <!-- container-fluid -->

      <div class="modal fade" id="modalAlta" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
              <h4 class="modal-title">
                Registro de proveedor
              </h4>
            </div>
            <div class="modal-body">
           <div class="row">
              <div class="col-xs-4">
                <label>
                  RFC
                </label>
                <input maxlength="13" class="form-control" name="name" v-model="supplier.rfc">
              </div>
              </div>
                <br>
              <div class="row" v-show="(supplier.rfc).length==12">
                <div class="col-xs-4">
                  <label>
                    Razón social
                  </label>
                  <input class="form-control" name="name" v-model="supplier.providerName">
                </div>
                <div class="col-xs-4">
                  <label>
                    Cuenta contable
                  </label>
                  <input class="form-control" name="name" v-model="supplier.accountingAccount">
                </div>
              </div>
              <div class="row" v-show="(supplier.rfc).length==13">
                <div class="col-xs-3">
                  <label>
                    Nombre
                  </label>
                  <input class="form-control" name="name" v-model="providerNames" onkeypress="return isLetterKey(event)">
                </div>
                <div class="col-xs-3">
                  <label>
                    Apellido paterno
                  </label>
                  <input class="form-control" name="name" v-model="providerLastName" onkeypress="return isLetterKey(event)">
                </div>
                <div class="col-xs-3">
                  <label>
                    Apellido materno
                  </label>
                  <input class="form-control" name="name" v-model="providerSecondName" onkeypress="return isLetterKey(event)">
                </div>
                <div class="col-xs-3">
                  <label>
                    Cuenta contable
                  </label>
                  <input class="form-control" name="name" v-model="supplier.accountingAccount">
                </div>
              </div>
              <br>
              <div class="row" v-show="(supplier.rfc).length==12||(supplier.rfc).length==13">
                <div class="col-xs-4">
                  <label>
                    Dirección
                  </label>
                </div>
              </div>
              <div class="row" v-show="(supplier.rfc).length==12||(supplier.rfc).length==13">
                <div class="col-xs-3">
                  <label>
                    Calle
                  </label>
                  <input class="form-control" name="name" v-model="direccion.street">
                </div>
                <div class="col-xs-3">
                  <label>
                    No. Exterior
                  </label>
                  <input class="form-control" name="name" maxlength="5" v-model="direccion.numExt" >
                </div>
                <div class="col-xs-3">
                  <label>
                    No. Interior
                  </label>
                  <input class="form-control" name="name" maxlength="5" v-model="direccion.numInt" >
                </div>
                <div class="col-xs-3">
                  <label>
                    C.P.
                  </label>
                  <input class="form-control" name="name" maxlength="5" v-model="direccion.cp" onkeypress="return isNumberKey(event)">
                </div>
              </div>
              <br>
              <div class="row" v-show="(supplier.rfc).length==12||(supplier.rfc).length==13">
                <div class="col-xs-3">
                  <label>
                    Colonia
                  </label>
                  <select class="form-control" name="" v-model="direccion.idSettlement">
                    <option></option>
                    <option v-for="set in settlements" value="{{set.idSettlement}}">{{set.settlementName}}</option>
                  </select>
                </div>
                <div class="col-xs-3">
                <label>
                  Municipio/Delegación
                </label>
                <select class="form-control" name="" v-model="direccion.idMunicipality">
                  <option></option>
                  <option v-for="mun in municipalities" value="{{mun.idMunicipality}}">{{mun.municipalityName}}</option>
                </select>
                </div>
                <div class="col-xs-3">
              <label>
                Estado
              </label>
              <select class="form-control" name="" v-model="direccion.idState">
                <option></option>
                <option v-for="state in states" value="{{state.idState}}">{{state.stateName}}</option>
              </select>
              </div>
         <!--       <div class="col-xs-2 text-left">
                  <button class="btn btn-default" @click="saveAdrress()" :disabled="isUpdate" data-toggle="tooltip" data-placement="top" title="Agregar">
                    <span class="glyphicon glyphicon-plus"></span>
                  </button>
                </div> -->
            </div>
              <br>
                <label v-show="(supplier.rfc).length==12||(supplier.rfc).length==13">Información de Contacto</label>
                <div class="row" v-if="(supplier.rfc).length==12||(supplier.rfc).length==13">
                    <div class="col-xs-12">
                        <hr>
                    </div>
                </div>
              <div class="row" v-show="(supplier.rfc).length==12||(supplier.rfc).length==13">
                <div class="col-xs-3">
                  <label>
                        Nombre
                  </label>
                  <input class="form-control" name="name" v-model="name" onkeypress="return isLetterKey(event)">
                </div>
                  <div class="col-xs-2">
                      <label>
                          Puesto
                      </label>
                      <input class="form-control" name="name" v-model="post" onkeypress="return isLetterKey(event)">
                  </div>
                  <div class="col-xs-3">
                      <label>
                          Teléfono (10 dígitos)
                      </label>
                      <input maxlength="10" class="form-control" name="name" v-model="phoneNumber" onkeypress="return isNumberKey(event)">
                  </div>
                  <div class="col-xs-3">
                      <label>
                          Correo
                      </label>
                      <input class="form-control" name="name" v-model="email" >
                  </div>
                <div class="col-xs-1">
                  <button type="button" class="btn btn-sm btn-default"  data-toggle="tooltip" data-placement="bottom" title="Agregar" style="margin-top: 25px" @click="validationContact()">
                    <span class="glyphicon glyphicon-plus"></span>
                  </button>
                </div>
              </div>
              <br>
                <table class="table table-striped" v-show="supplier.providersContactList.length> 0">
                    <thead>
                    <th class="col-xs-3">
                        Nombre
                    </th>
                    <th class="col-xs-2">
                        Puesto
                    </th>
                    <th class="col-xs-3">
                        Teléfono (10 dígitos)
                    </th>
                    <th class="col-xs-3">
                        Correo
                    </th>
                    <th class="col-xs-1">
                        Opción
                    </th>
                    </thead>
                    <tbody>
                    <tr v-for="phone in supplier.providersContactList">
                        <td class="col-xs-3">
                            {{phone.name}}
                        </td>
                        <td class="col-xs-2">
                            {{phone.post}}
                        </td>
                        <td class="col-xs-3">
                            {{phone.phoneNumber}}
                        </td>
                        <td class="col-xs-3">
                            {{phone.email}}
                        </td>
                        <td class="col-xs-1">
                            <button class="btn btn-default" @click="deletePhone(phone)" :disabled="isUpdate" data-toggle="tooltip" data-placement="top" title="Quitar Numero">
                                <span class="glyphicon glyphicon-remove"></span>
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
              <br>
                <label v-show="(supplier.rfc).length==12||(supplier.rfc).length==13">Cuentas Bancarias</label>
                <div class="row" v-show="(supplier.rfc).length==12||(supplier.rfc).length==13">
                    <div class="col-xs-12">
                        <hr>
                    </div>
                </div>
                <div class="row" v-show="(supplier.rfc).length==12||(supplier.rfc).length==13">
                  <div class="col-xs-3">
                    <label>
                      Banco
                    </label>
                    <select class="form-control" name="" v-model="idBanks">
                      <option></option>
                      <option v-for="bank in banks" value="{{bank.idBank}}">{{bank.acronyms}}</option>
                    </select>
                  </div>

                  <div class="col-xs-3">
                    <label>
                      Número de Cuenta
                    </label>
                    <div class="input-group">
                      <span class="input-group-addon">#</span>
                      <input class="form-control" maxlength="12" v-model="accountNumbers" onkeypress="return isNumberKey(event)">
                    </div>
                  </div>

                  <div class="col-xs-3">
                    <label>
                      CLABE
                    </label>
                    <div class="input-group">
                      <span class="input-group-addon">#</span>
                      <input type="text" class="form-control" maxlength="18" v-model="clabes" onkeypress="return isNumberKey(event)">
                    </div>
                  </div>
                  <div class="col-xs-2">
                    <label>
                      Moneda
                    </label>
                    <select class="form-control" name="" v-model="idCurrency">
                      <option></option>
                      <option v-for="curre in currencies" value="{{curre.idCurrency}}">{{curre.currency}}</option>
                    </select>
                  </div>
                  <div class="col-xs-1">
                  <button type="button" class="btn btn-sm btn-default"  data-toggle="tooltip" data-placement="bottom" title="Agregar" style="margin-top: 25px" @click="validationAccount">
                    <span class="glyphicon glyphicon-plus"></span>
                  </button>
                  </div>
                </div>

                <table class="table table-striped" v-show="supplier.providersAccountsList.length> 0">
                  <thead>
                    <th>
                      Banco
                    </th>
                    <th>
                      Número de cuenta
                    </th>
                    <th>
                      CLABE
                    </th>
                    <th>
                      Moneda
                    </th>
                    <th>
                      Opción
                    </th>
                  </thead>
                  <tbody>
                    <tr v-for="supplier in supplier.providersAccountsList">
                      <td>
                        {{supplier.idBank | changeidBank }}
                      </td>
                      <td>
                        {{supplier.accountNumber }}
                      </td>
                      <td>
                        {{supplier.accountClabe}}
                      </td>
                      <td>
                        {{supplier.idCurrency | changeidCurrency}}
                      </td>
                      <td>
                        <button type="button" class="btn btn-sm btn-default"  data-toggle="tooltip" data-placement="bottom" title="Eliminar" style="margin-top: 15px" @click="eliminarCuenta(supplier)">
                          <span class="glyphicon glyphicon-trash"></span>
                        </button>
                    </tr>
                  </tbody>
                </table>

            </div>
            <div class="modal-footer">

                <button type="button" class="btn btn-default" @click="saveProvider">
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
                <h4 class="modal-title">
                  Modificar proveedor
                </h4>
              </div>
              <div class="modal-body">

                <div class="row">
                  <div class="col-xs-4">
                    <label>
                      RFC
                    </label>
                    <input  maxlength="13" class="form-control" name="name" v-model="provider.rfc" disabled="true">
                  </div>
                </div>
                <div class="row" v-show="(provider.rfc).length==12">
                  <div class="col-xs-4">
                    <label>
                      Razón social
                    </label>
                    <input class="form-control" name="name" v-model="provider.providerName">
                  </div>
                  <div class="col-xs-4">
                    <label>
                      Cuenta contable
                    </label>
                    <input class="form-control" name="name" v-model="provider.accountingAccount">
                  </div>
                </div>
                <div class="row" v-show="(provider.rfc).length==13">
                  <div class="col-xs-3">
                    <label>
                      Nombre
                    </label>
                    <input class="form-control" name="name" v-model="supplierNames" onkeypress="return isLetterKey(event)">
                  </div>
                  <div class="col-xs-3">
                    <label>
                      Apellido paterno
                    </label>
                    <input class="form-control" name="name" v-model="supplierLastName" onkeypress="return isLetterKey(event)">
                  </div>
                  <div class="col-xs-3">
                    <label>
                      Apellido materno
                    </label>
                    <input class="form-control" name="name" v-model="supplierSecondName" onkeypress="return isLetterKey(event)">
                  </div>
                  <div class="col-xs-3">
                    <label>
                      Cuenta contable
                    </label>
                    <input class="form-control" name="name" v-model="provider.accountingAccount">
                  </div>
                </div>
                <br>
                <div class="row" v-show="(provider.rfc).length==12||(provider.rfc).length==13">
                  <div class="col-xs-4">
                    <label>
                      Dirección
                    </label>
                  </div>
                </div>
                <div class="row" v-show="(provider.rfc).length==12||(provider.rfc).length==13" v-for="address in provider.addressProvider">
                  <div class="col-xs-3">
                    <label>
                      Calle
                    </label>
                    <input class="form-control" name="name" v-model="address.street">
                  </div>
                  <div class="col-xs-3">
                    <label>
                      No. Exterior
                    </label>
                    <input class="form-control" name="name" maxlength="5" v-model="address.numExt">
                  </div>
                  <div class="col-xs-3">
                    <label>
                      No. Interior
                    </label>
                    <input class="form-control" name="name" maxlength="5" v-model="address.numInt">
                  </div>
                  <div class="col-xs-3">
                    <label>
                      C.P.
                    </label>
                    <input class="form-control" name="name" maxlength="5" v-model="address.cp" onkeypress="return isNumberKey(event)">
                  </div>
                  <div class="col-xs-3">
                    <label>
                      Colonia
                    </label>
                    <select class="form-control" name="" v-model="address.idSettlement">
                      <option></option>
                      <option v-for="set in settlements" value="{{set.idSettlement}}">{{set.settlementName}}</option>
                    </select>
                  </div>
                  <div class="col-xs-3">
                    <label>
                      Municipio/Delegación
                    </label>
                    <select class="form-control" name="" v-model="address.idMunicipality">
                      <option></option>
                      <option v-for="mun in municipalities" value="{{mun.idMunicipality}}">{{mun.municipalityName}}</option>
                    </select>
                  </div>
                  <div class="col-xs-3">
                    <label>
                      Estado
                    </label>
                    <select class="form-control" name="" v-model="address.idState">
                      <option></option>
                      <option v-for="state in states" value="{{state.idState}}">{{state.stateName}}</option>
                    </select>
                  </div>
                </div>
                  <br>
                  <label v-show="(provider.rfc).length==12||(provider.rfc).length==13">Información de Contacto</label>
                  <div class="row" v-show="(provider.rfc).length==12||(provider.rfc).length==13">
                      <div class="col-xs-12">
                          <hr>
                      </div>
                  </div>
                  <div class="row" v-show="(provider.rfc).length==12||(provider.rfc).length==13">
                      <div class="col-xs-3">
                          <label>
                              Nombre
                          </label>
                          <input class="form-control" name="name" v-model="telephone.name" onkeypress="return isLetterKey(event)">
                      </div>
                      <div class="col-xs-2">
                          <label>
                              Puesto
                          </label>
                          <input class="form-control" name="name" v-model="telephone.post" onkeypress="return isLetterKey(event)">
                      </div>
                      <div class="col-xs-3">
                          <label>
                              Teléfono (10 dígitos)
                          </label>
                          <input maxlength="10" class="form-control" name="name" v-model="telephone.phoneNumber" onkeypress="return isNumberKey(event)">
                      </div>
                      <div class="col-xs-3">
                          <label>
                              Correo
                          </label>
                          <input class="form-control" name="name" v-model="telephone.email" >
                      </div>
                      <div class="col-xs-1">
                          <button type="button" class="btn btn-sm btn-default"  data-toggle="tooltip" data-placement="bottom" title="Agregar" style="margin-top: 25px" @click="addProviderPhone(provider, telephone)">
                              <span class="glyphicon glyphicon-plus"></span>
                          </button>
                      </div>
                  </div>
                  <br>
                  <table class="table table-striped" v-show="provider.providersContactList.length> 0">
                      <thead>
                      <th class="col-xs-3">
                          Nombre
                      </th>
                      <th class="col-xs-2">
                          Puesto
                      </th>
                      <th class="col-xs-3">
                          Teléfono (10 dígitos)
                      </th>
                      <th class="col-xs-3">
                          Correo
                      </th>
                      <th class="col-xs-1">
                          Opción
                      </th>
                      </thead>
                      <tbody>
                      <tr v-for="phone in provider.providersContactList">
                          <td class="col-xs-3">
                              {{phone.name}}
                          </td>
                          <td class="col-xs-2">
                              {{phone.post}}
                          </td>
                          <td class="col-xs-3">
                              {{phone.phoneNumber}}
                          </td>
                          <td class="col-xs-3">
                              {{phone.email}}
                          </td>
                          <td class="col-xs-1">
                              <button class="btn btn-default" @click="removePhone(phone)" :disabled="isUpdate" data-toggle="tooltip" data-placement="top" title="Quitar Numero">
                                  <span class="glyphicon glyphicon-remove"></span>
                              </button>
                          </td>
                      </tr>
                      </tbody>
                  </table>
                  <br>
                  <label v-show="(provider.rfc).length==12||(provider.rfc).length==13">Cuentas Bancarias</label>
                  <div class="row" v-show="(provider.rfc).length==12||(provider.rfc).length==13">
                      <div class="col-xs-12">
                          <hr>
                      </div>
                  </div>
                <div class="row" v-show="(provider.rfc).length==12||(provider.rfc).length==13">
                  <div class="col-xs-3">
                    <label>
                      Banco
                    </label>
                    <select class="form-control" name="" v-model="cuenta.idBank">
                      <option></option>
                      <option v-for="bank in banks" value="{{bank.idBank}}">{{bank.acronyms}}</option>
                    </select>
                  </div>

                  <div class="col-xs-3">
                    <label>
                      Número de cuenta
                    </label>
                    <div class="input-group">
                      <span class="input-group-addon">#</span>
                      <input class="form-control" maxlength="12" v-model="cuenta.accountNumber" onkeypress="return isNumberKey(event)">
                    </div>
                  </div>

                  <div class="col-xs-3">
                    <label>
                      CLABE
                    </label>
                    <div class="input-group">
                      <span class="input-group-addon">#</span>
                      <input type="text" class="form-control" maxlength="18" v-model="cuenta.accountClabe" onkeypress="return isNumberKey(event)">
                    </div>
                  </div>

                  <div class="col-xs-2">
                    <label>
                      Moneda
                    </label>
                    <select class="form-control" name="" v-model="cuenta.idCurrency">
                      <option></option>
                      <option v-for="curre in currencies" value="{{curre.idCurrency}}">{{curre.currency}}</option>
                    </select>
                  </div>

                  <div class="col-xs-1">
                    <button type="button" class="btn btn-sm btn-default"  data-toggle="tooltip" data-placement="bottom" title="Agregar" style="margin-top: 25px" @click="addProviderAccount(provider,cuenta)">
                      <span class="glyphicon glyphicon-plus"></span>
                    </button>
                  </div>
                </div>

                <table class="table table-striped" v-show="provider.providersAccountsList.length> 0">
                  <thead>
                  <th>
                    Banco
                  </th>
                  <th>
                    Número de cuenta
                  </th>
                  <th>
                    CLABE
                  </th>
                  <th>
                    Moneda
                  </th>
                  <th >
                    Eliminar cuenta
                  </th>
                  </thead>
                  <tbody>
                  <tr v-for="account in provider.providersAccountsList">
                    <td>
                      {{account.idBank | changeidBank }}
                    </td>
                    <td>
                      {{account.accountNumber }}
                    </td>
                    <td>
                      {{account.accountClabe}}
                    </td>
                    <td>
                      {{account.idCurrency | changeidCurrency}}
                    </td>
                    <td>
                      <button type="button" class="btn btn-sm btn-default"  data-toggle="tooltip" data-placement="bottom" title="Eliminar"  @click="deleteAccount(account)">
                        <span class="glyphicon glyphicon-trash"></span>
                      </button>
                    </td>
                  </tr>
                  </tbody>
                </table>

              </div>
              <div class="modal-footer">

                  <button type="button" class="btn btn-default" @click="updateProvider(provider)">
                    Guardar
                  </button>

                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
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
                          <p>El provedor con nombre {{modalPregunta.provider.providerName|separate}} sera eliminado</p>
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
      </div> <!-- #contenidos -->

      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
