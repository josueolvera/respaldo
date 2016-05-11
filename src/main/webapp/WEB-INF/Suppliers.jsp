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
            accountingaccount: '',
            providersAccountsList: [],
            phoneNumbersList: [],
            addressProvider: []
          },
          provider: {
            providerName: '',
            businessName: '',
            rfc: '',
            accountingaccount: '',
            providersAccountsList: [],
            phoneNumbersList:[],
            addressProvider: []
          },
          idBanks: '',
          banks: [],
          providers: '',
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
            phoneNumbers:'',
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
            ext:'',
            int:'',
            street:'',
            idSettlement: '',
            idState:'',
            idMunicipality:'',
          },

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
          saveAccount: function () {
            var cuenta =
            {
              accountNumber: '',
              accountClabe: '',
              idBank: '',
              idCurrency: ''
            }

            cuenta.accountNumber = this.accountNumbers;
            cuenta.accountClabe = this.clabes;
            cuenta.idBank = this.idBanks;
            cuenta.idCurrency = this.idCurrency;

            this.supplier.providersAccountsList.push(cuenta);

            this.idBanks = ''
            this.accountNumbers = ''
            this.clabes = ''
            this.idCurrency = '';

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
          saveProvider: function () {
            var direccion = {
              cp: '',
              ext: '',
              int: '',
              street: '',
              idSettlement: '',
              idState: '',
              idMunicipality: '',
            };
            direccion.cp = this.direccion.cp;
            direccion.ext = this.direccion.ext;
            direccion.int = this.direccion.int;
            direccion.street = this.direccion.street;
            direccion.idState = this.direccion.idState;
            direccion.idSettlement = this.direccion.idSettlement;
            direccion.idMunicipality = this.direccion.idMunicipality;

            this.supplier.addressProvider.push(direccion);

            if(this.supplier.rfc.length==13){
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
            this.$http.get(ROOT_URL + "/phone-numbers/provider/"+this.provider.idProvider)
                    .success(function (data) {
                      Vue.set(this.provider,'phoneNumbersList', data);
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
            this.$http.delete(ROOT_URL + "/phone-numbers/" + phone.idPhoneNumber)
                    .success(function (data) {
                      showAlert("Telefono Eliminado");
                      this.provider.phoneNumbersList.$remove(phone);
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
          deleteProvider: function (provider) {
            this.$http.post(ROOT_URL + "/providers/low/" + provider.idProvider)
                    .success(function (data) {
                      this.getProviders();
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
          savePhone: function () {
            var self = this;

            var telephone = {
              phoneNumber: '',
            };

            telephone.phoneNumber = this.phoneNumbers;

            this.supplier.phoneNumbersList.push(telephone);
            this.phoneNumbers = ''
          },
          deletePhone: function (phone) {
            this.supplier.phoneNumbersList.$remove(phone);
            this.phoneNumbers = '';
          },
          addProviderPhone: function (supplier, phone) {
            this.$http.post(ROOT_URL + "/phone-numbers/provider/" + supplier.idProvider, {phoneNumbers : phone})
                    .success(function (data) {
                      this.provider.phoneNumbersList.push(data);
                      this.phoneNumbers = '';
                      showAlert("Teléfono Guardado con Éxito");
                      this.telephone.phoneNumbers = '';
                    });
          },
         cancelar: function () {

            this.cuenta.accountNumber= '';
            this.cuenta.accountClabe= '';
            this.cuenta.idBank='';
            this.cuenta.idCurrency= '';
            this.direccion.cp= '';
            this.direccion.ext='';
            this.direccion.int='';
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
            this.supplier.providerName= '';
            this.supplier.businessName= '';
            this.supplier.providersAccountsList=[];
            this.supplier.phoneNumbersList=[];
            this.supplier.rfc= '';
            this.supplier.accountingaccount= '';

            $('#modalAlta').modal('hide');
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
          }

        }
      });



        </script>
    </jsp:attribute>

    <jsp:body>
      <div id="contenidos">

      <div class="container-fluid" style="margin-left: 100px">
        <div class="row">
          <div class="col-xs-6 text-left">
          <h1>Búsqueda de Proveedores</h1>
          </div>

          <div class="col-xs-3">
            <label>
              Buscar por RFC
            </label>
            <input class="form-control" maxlength="13" v-model="search">
          </div>

          <div class="col-xs-3">
            <button type="button" class="btn btn-default" name="button"
              style="margin-top: 25px" data-toggle="modal" data-target="#modalAlta">
              Agregar Nuevo Proveedor
            </button>
          </div>

          <table class="table table-striped">
            <thead>
              <th>
                Nombre del Proveedor/Razón Social
              </th>
              <th>
                Cuneta Contable
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
                  {{provider.accountingaccount}}
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
                          @click="deleteProvider(provider)"><span class="glyphicon glyphicon-trash"></span></button>
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
              <h4 class="modal-title" id="">
                Registro de Proveedor
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
              <div class="row" v-if="(supplier.rfc).length==12">
                <div class="col-xs-4">
                  <label>
                    Razón Social
                  </label>
                  <input class="form-control" name="name" v-model="supplier.providerName">
                </div>
                <div class="col-xs-4">
                  <label>
                    Cuenta Contable
                  </label>
                  <input class="form-control" name="name" v-model="supplier.accountingaccount">
                </div>
              </div>
              <div class="row" v-if="(supplier.rfc).length==13">
                <div class="col-xs-3">
                  <label>
                    Nombre
                  </label>
                  <input class="form-control" name="name" v-model="providerNames">
                </div>
                <div class="col-xs-3">
                  <label>
                    Apellido Paterno
                  </label>
                  <input class="form-control" name="name" v-model="providerLastName">
                </div>
                <div class="col-xs-3">
                  <label>
                    Apellido Materno
                  </label>
                  <input class="form-control" name="name" v-model="providerSecondName">
                </div>
                <div class="col-xs-3">
                  <label>
                    Cuenta Contable
                  </label>
                  <input class="form-control" name="name" v-model="supplier.accountingaccount">
                </div>
              </div>
              <br>
              <div class="row" v-if="(supplier.rfc).length==12||(supplier.rfc).length==13">
                <div class="col-xs-4">
                  <label>
                    Dirección
                  </label>
                </div>
              </div>
              <div class="row" v-if="(supplier.rfc).length==12||(supplier.rfc).length==13">
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
                  <input class="form-control" name="name" maxlength="5" v-model="direccion.ext" >
                </div>
                <div class="col-xs-3">
                  <label>
                    No. Interior
                  </label>
                  <input class="form-control" name="name" maxlength="5" v-model="direccion.int" >
                </div>
                <div class="col-xs-3">
                  <label>
                    C.P.
                  </label>
                  <input class="form-control" name="name" maxlength="5" v-model="direccion.cp" onkeypress="return isNumberKey(event)">
                </div>
              </div>
              <br>
              <div class="row" v-if="(supplier.rfc).length==12||(supplier.rfc).length==13">
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
              <div class="row" v-if="(supplier.rfc).length==12||(supplier.rfc).length==13">
                <div class="col-xs-4">
                  <label>
                    Teléfono (10 dígitos)
                  </label>
                  <input maxlength="10" class="form-control" name="name" v-model="phoneNumbers" onkeypress="return isNumberKey(event)">
                </div>
                <div class="col-xs-1">
                  <button type="button" class="btn btn-sm btn-default"  data-toggle="tooltip" data-placement="bottom" title="Agregar" style="margin-top: 25px" @click="savePhone()">
                    <span class="glyphicon glyphicon-plus"></span>
                  </button>
                </div>
              </div>
              <br>
              <div class="row" v-if="(supplier.rfc).length==12||(supplier.rfc).length==13" v-for=" phone in supplier.phoneNumbersList">
                <div class="col-xs-4">
                  <div class="col-xs-4">
                    {{phone.phoneNumber}}
                  </div>
                  <div class="col-xs-2 text-left">
                    <button class="btn btn-default" @click="deletePhone(phone)" :disabled="isUpdate" data-toggle="tooltip" data-placement="top" title="Quitar Numero">
                      <span class="glyphicon glyphicon-remove"></span>
                    </button>
                  </div>
                </div>

              </div>
              <br>
                <div class="row" v-if="(supplier.rfc).length==12||(supplier.rfc).length==13">
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
                  <button type="button" class="btn btn-sm btn-default"  data-toggle="tooltip" data-placement="bottom" title="Agregar" style="margin-top: 25px" @click="saveAccount">
                    <span class="glyphicon glyphicon-plus"></span>
                  </button>
                  </div>
                </div>

                <table class="table table-striped" v-if="supplier.providersAccountsList.length> 0">
                  <thead>
                    <th>
                      Banco
                    </th>
                    <th>
                      Número de Cuenta
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
              <div class="col-xs-10 text-right" v-if="supplier.providersAccountsList.length> 0">
                <button type="button" class="btn btn-default" @click="saveProvider">
                  Guardar
                </button>
              </div>
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
                <h4 class="modal-title" id="">
                  Modificar Proveedor
                </h4>
              </div>
              <div class="modal-body">

                <div class="row">
                  <div class="col-xs-4">
                    <label>
                      RFC
                    </label>
                    <input  maxlength="13" class="form-control" name="name" v-model="provider.rfc">
                  </div>
                </div>
                <div class="row" v-if="(provider.rfc).length==12">
                  <div class="col-xs-4">
                    <label>
                      Razón Social
                    </label>
                    <input class="form-control" name="name" v-model="provider.providerName">
                  </div>
                  <div class="col-xs-4">
                    <label>
                      Cuenta Contable
                    </label>
                    <input class="form-control" name="name" v-model="provider.accountingaccount">
                  </div>
                </div>
                <div class="row" v-if="(provider.rfc).length==13">
                  <div class="col-xs-3">
                    <label>
                      Nombre
                    </label>
                    <input class="form-control" name="name" v-model="supplierNames">
                  </div>
                  <div class="col-xs-3">
                    <label>
                      Apellido Paterno
                    </label>
                    <input class="form-control" name="name" v-model="supplierLastName">
                  </div>
                  <div class="col-xs-3">
                    <label>
                      Apellido Materno
                    </label>
                    <input class="form-control" name="name" v-model="supplierSecondName">
                  </div>
                  <div class="col-xs-3">
                    <label>
                      Cuenta Contable
                    </label>
                    <input class="form-control" name="name" v-model="provider.accountingaccount">
                  </div>
                </div>
                <br>
                <div class="row" v-if="(provider.rfc).length==12||(provider.rfc).length==13">
                  <div class="col-xs-4">
                    <label>
                      Dirección
                    </label>
                  </div>
                </div>
                <div class="row" v-if="(provider.rfc).length==12||(provider.rfc).length==13" v-for="address in provider.addressProvider">
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
                <div class="row" v-if="(provider.rfc).length==12||(provider.rfc).length==13">
                  <div class="col-xs-4">
                    <label>
                      Teléfono (10 dígitos)
                    </label>
                    <input maxlength="10" class="form-control" name="name" v-model="phoneNumbers" onkeypress="return isNumberKey(event)">
                  </div>
                  <div class="col-xs-1">
                    <button type="button" class="btn btn-sm btn-default"  data-toggle="tooltip" data-placement="bottom" title="Agregar" style="margin-top: 25px" @click="addProviderPhone(provider,phoneNumbers)">
                      <span class="glyphicon glyphicon-plus"></span>
                    </button>
                  </div>
                </div>
                <br>
                <div class="row" v-if="(provider.rfc).length==12||(provider.rfc).length==13" v-for=" phone in provider.phoneNumbersList">
                  <div class="col-xs-4">
                    <div class="col-xs-4">
                      {{phone.phoneNumber}}
                    </div>
                    <div class="col-xs-2 text-left">
                      <button class="btn btn-default" @click="removePhone(phone)" :disabled="isUpdate" data-toggle="tooltip" data-placement="top" title="Quitar Numero">
                        <span class="glyphicon glyphicon-remove"></span>
                      </button>
                    </div>
                  </div>

                </div>
                <br>
                <div class="row" v-if="(provider.rfc).length==12||(provider.rfc).length==13">
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
                      Número de Cuenta
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

                <table class="table table-striped" v-if="provider.providersAccountsList.length> 0">
                  <thead>
                  <th>
                    Banco
                  </th>
                  <th>
                    Número de Cuenta
                  </th>
                  <th>
                    CLABE
                  </th>
                  <th>
                    Moneda
                  </th>
                  <th >
                    Eliminar Cuenta
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
                <div class="col-xs-10 text-right" >
                  <button type="button" class="btn btn-default" @click="updateProvider(provider)">
                    Guardar
                  </button>
                </div>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
              </div>
            </div>
          </div>
        </div>
      </div> <!-- #contenidos -->

      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
