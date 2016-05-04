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
          },
        data:
        {
          supplier:
          {
            providerName: '',
            businessName: '',
            rfc: '',
            providersAccountsList: []
          },
          provider: {
            providerName: '',
            businessName: '',
            rfc: '',
            providersAccountsList: []
          },
          idBanks: '',
          banks: [],
          providers: '',
          search: '',
          cuenta:
          {
            accountNumber: '',
            accountClabe: '',
            idBank: '',
          },

          },
        methods:
        {
          saveAccount: function()
          {
            var cuenta=
            {
              accountNumber: '',
              accountClabe: '',
              idBank: ''
            }

            cuenta.accountNumber= this.accountNumbers;
            cuenta.accountClabe= this.clabes;
            cuenta.idBank= this.idBanks;

            this.supplier.providersAccountsList.push(cuenta);

            this.idBanks= ''
            this.accountNumbers= ''
            this.clabes=''

          },
          obtainBanks: function()
          {
            this.$http.get(ROOT_URL + "/banks")
                    .success(function (data)
                    {
                       this.banks= data;

                    });
          },
          eliminarCuenta: function(cuenta)
          {
            this.supplier.providersAccountsList.$remove(cuenta);
          },
          saveProvider: function()
          {
            var self= this;

            this.$http.post(ROOT_URL + "/providers", JSON.stringify(this.supplier)).
            success(function(data)
            {
              showAlert("Registro de Proveedor Exitoso");
              this.getProviders();

            }).error(function(){
              showAlert("Ha habido un error con la solicitud, intente nuevamente");
            });

          },
          getProviders: function()
          {
            this.$http.get(ROOT_URL + "/providers")
                    .success(function (data)
                    {
                       this.providers= data;

                    });
          },
          modifyProvider: function(provider) {
            this.provider = (JSON.parse(JSON.stringify(provider)));
            $('#modalModi').modal('show');
            this.$http.get(ROOT_URL+"/accounts/provider/"+this.provider.idProvider)
                    .success(function (data) {
                      Vue.set(this.provider, 'providersAccountsList', data);
                    });
          },
          deleteAccount: function (account)
          {
            this.$http.delete(ROOT_URL+"/accounts/"+account.idAccount)
                    .success(function (data)
                    {
                      showAlert("Cuenta Eliminada");
                      this.provider.providersAccountsList.$remove(account);
                    });


          },
          addProviderAccount: function (supplier,cuenta)
          {
            this.$http.post(ROOT_URL+"/accounts/provider/"+supplier.idProvider,cuenta)
                    .success(function (data)
                    {
                      showAlert("Cuenta Guardada con Exito");
                      this.$http.get(ROOT_URL+"/accounts/provider/"+this.provider.idProvider)
                              .success(function (data) {
                                Vue.set(this.provider, 'providersAccountsList', data);
                              });
                      cuenta.accountNumber="";
                      cuenta.accountClabe="";
                      cuenta.idBank="";
                     });

          },
          updateProvider: function (provider) {
           this.$http.post(ROOT_URL+"/providers/"+provider.idProvider,provider)
                    .success(function (data) {
                          showAlert("Proveedor Actualizado");
                          $('#modalModi').modal('hide');
                          this.getProviders();
                    }).error(function(){
                          showAlert("Ha habido un error con la solicitud, intente nuevamente");
            });

          },
          deleteProvider: function (provider) {
              this.$http.delete(ROOT_URL+"/providers/"+provider.idProvider)
                      .success(function (data) {
                        showAlert("Provedor Eliminado");
                        this.getProviders();
                      });
          },
          filterNumber: function(val)
          {
            return isNaN(val) ? '' : val;
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
                Nombre del Proveedor
              </th>
              <th>
                Razón Social
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
              <tr v-for="provider in providers | filterBy search">
                <td>
                  {{provider.providerName}}
                </td>
                <td>
                  {{provider.businessName}}
                </td>
                <td>
                  {{provider.rfc}}
                </td>
                <td>
                  <button type="button" class="btn btn-success" name="button"
                   @click="modifyProvider(provider)">Modificar</button>
                </td>
                <td>
                  <button type="button" class="btn btn-danger" name="button"
                  @click="deleteProvider(provider)">Eliminar</button>
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
                Alta de Proveedores
              </h4>
            </div>
            <div class="modal-body">

              <div class="row">
                <div class="col-xs-4">
                  <label>
                    Nombre del Proveedor
                  </label>
                  <input class="form-control" name="name" v-model="supplier.providerName">
                </div>
                <div class="col-xs-4">
                  <label>
                    Razón Social
                  </label>
                  <input class="form-control" name="name" v-model="supplier.businessName">
                </div>
                <div class="col-xs-4">
                  <label>
                    RFC
                  </label>
                  <input class="form-control" name="name" v-model="supplier.rfc">
                </div>
              </div>
              <br>
                <div class="row">
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

                  <button type="button" class="btn btn-default" @click="saveAccount" style="margin-top: 25px">
                  Agregar Cuenta
                  </button>
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
                      Clabe
                    </th>
                    <th style="color: red">
                      Eliminar la Cuenta
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
                        <button type="button" class="btn btn-danger" @click="eliminarCuenta(supplier)">
                          Eliminar Cuenta
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>

                <div class="row"  v-if="supplier.providersAccountsList.length> 0">
                  <div class="col-xs-12 text-left">
                    <button type="button" class="btn btn-success" @click="saveProvider">
                      Guardar Proveedor
                    </button>
                  </div>
                </div>

            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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
                  Modificación de Proveedores
                </h4>
              </div>
              <div class="modal-body">

                <div class="row">
                  <div class="col-xs-4">
                    <label>
                      Nombre del Proveedor
                    </label>
                    <input class="form-control" name="name" v-model="provider.providerName" >
                  </div>
                  <div class="col-xs-4">
                    <label>
                      Razón Social
                    </label>
                    <input class="form-control" name="name" v-model="provider.businessName">
                  </div>
                  <div class="col-xs-4">
                    <label>
                      RFC
                    </label>
                    <input class="form-control" name="name" v-model="provider.rfc">
                  </div>
                </div>
                <br>
                <div class="row">
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

                  <button type="button" class="btn btn-default" @click="addProviderAccount(provider,cuenta)" style="margin-top: 25px">
                    Agregar Cuenta
                  </button>
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
                    Clabe
                  </th>
                  <th style="color: red">
                    Eliminar la Cuenta
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
                      <button type="button" class="btn btn-danger" @click="deleteAccount(account)">
                        Eliminar Cuenta
                      </button>
                    </td>
                  </tr>
                  </tbody>
                </table>


                <div class="row"  v-if="provider.providersAccountsList.length> 0">
                  <div class="col-xs-12 text-left">
                    <button type="button" class="btn btn-success" @click="updateProvider(provider)">
                      Guardar Proveedor
                    </button>
                  </div>
                </div>

              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>
      </div> <!-- #contenidos -->

      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
