<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />


<t:template pageTitle="BID Group: Presupuestos">
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
          created: function(){

          },
          ready: function ()
          {
            this.timePicker = $('#datetimepicker1').datetimepicker({
              locale: 'es',
              format: 'YYYY/MM/DD'
              }).data();

            this.obtainAllUsers();
            this.obtainSuppliers();
            this.obtainCurrencies();
            this.RequestCategory= this.getGet();
            this.obtainRequestInformation.idRequestCategory= this.RequestCategory.cat;
            this.$http.get(ROOT_URL+"/request-types/request-category/"+ this.obtainRequestInformation.idRequestCategory)
                    .success(function (data)
                    {
                       this.RequestTypes= data;
                    });
          },
          data:
          {
            months:[
              {idMonth: 1, name: 'Enero'},
              {idMonth: 2, name: 'Febrero'},
              {idMonth: 3, name: 'Marzo'},
              {idMonth: 4, name: 'Abril'},
              {idMonth: 5, name: 'Mayo'},
              {idMonth: 6, name: 'Junio'},
              {idMonth: 7, name: 'Julio'},
              {idMonth: 8, name: 'Agosto'},
              {idMonth: 9, name: 'Septiembre'},
              {idMonth: 10, name: 'Octubre'},
              {idMonth: 11, name: 'Noviembre'},
              {idMonth: 12, name: 'Diciembre'}
            ],
            objectRequest:
            {
            request:
              {
                idRequest: '',
                folio: '',
                description: '',
                purpose: '',
                creationDate: '',
                applyingDate: '',
                idUserRequest: '',
                idUserResponsable: '',
                idBudgetMonthBranch: '',
                idRequestTypesProduct: '',
                idRequestStatus: ''
              },
             products: []
           },
            responseSaveRequest: ''
           ,
            obtainRequestInformation:
            {
              idRequestCategory: '',
              idRequestType: '',
              idProductType: '',
              idUserResponsable: '',
              applyingDate: ''
            },
            RequestCategory: '',
            ResponseRequestInformation: '',
            idRequestType: '',
            idProductType: '',
            optionSelect: [],
            RequestTypes: {},
            ProductTypes: {},
            Productos: {},
            Users: {},
            idProducto: '',
            desactivarCombos: false,
            desactivarGuardar: true,
            numberOfRequest: 0,
            estimations: [],
            suppliers: {},
            idSupplier: '',
            accounts: {},
            idAccount: '',
            timePicker: '',
            currencies: {}
          },
          methods:
          {
            getGet: function()
            {
              var loc = document.location.href;
              var getString = loc.split('?')[1];
              var GET = getString.split('&');
              var get = {};//this object will be filled with the key-value pairs and returned.

              for(var i = 0, l = GET.length; i < l; i++){
                 var tmp = GET[i].split('=');
                 get[tmp[0]] = unescape(decodeURI(tmp[1]));
              }
              return get;
            },
            obtainProductType: function()
            {
              this.ProductTypes= {};
              this.$http.get(ROOT_URL+"/product-types/request-category-type/"+this.obtainRequestInformation.idRequestCategory+"/"+this.obtainRequestInformation.idRequestType)
                      .success(function (data)
                      {
                         this.ProductTypes= data;
                      });

            },
            obtainProducts: function()
            {
              this.Productos= {};
              this.$http.get(ROOT_URL+"/products/product-type/"+this.obtainRequestInformation.idProductType)
                      .success(function (data)
                      {
                         this.Productos= data;
                      });
            },
            obtainRequestInfo: function()
            {
              var date = this.timePicker.DateTimePicker.date();
              var dateiso= date.toISOString();
              this.obtainRequestInformation.applyingDate= dateiso.slice(0, -1);

              this.$http.post(ROOT_URL+"/requests/month-branch-product-type", JSON.stringify(this.obtainRequestInformation))
                      .success(function (data)
                      {
                         this.ResponseRequestInformation= data;
                         this.matchInformation(this.ResponseRequestInformation);
                      });

            }
            ,
            matchInformation: function(requestInformation)
            {
              this.objectRequest.request.idRequestTypesProduct= requestInformation.requestTypesProduct.idRequestTypeProduct;
              this.objectRequest.request.applyingDate= this.obtainRequestInformation.applyingDate;
              this.objectRequest.request.idUserResponsable= this.obtainRequestInformation.idUserResponsable;
              this.objectRequest.request.idBudgetMonthBranch = requestInformation.budgetMonthBranch.idBudgetMonthBranch;

            },
            saveProduct: function()
            {
              var producto= this.createProduct();
              var self= this;
              this.Productos.forEach(function(element)
              {
                if (self.idProducto == element.idProduct)
                {
                  producto.idProduct = element.idProduct;
                  producto.descripcion = element.product;
                  self.objectRequest.products.push(producto);
                }
              });
              if (this.objectRequest.products.length> 0)
              {
                this.desactivarCombos= true;
                this.desactivarGuardar= false;
              }
            },
            createProduct: function()
            {
              var producto= {
                idProduct: '',
                descripcion: ''
              };
              return producto;
            },
            deleteProduct: function(product)
            {
                this.objectRequest.products.$remove(product);
                if (this.objectRequest.products.length == 0)
                {
                  this.desactivarCombos= false;
                  this.desactivarGuardar = true;
                }
            },
            saveRequest: function(event)
            {
              this.$http.post(ROOT_URL+"/requests", JSON.stringify(this.objectRequest)).
              success(function(data)
              {
                this.fillRequestInformation(data);

              }).error(function(data)
              {

              });
            },
            fillRequestInformation: function(datos)
            {
              this.objectRequest.request.idRequest= datos.idRequest;
              this.objectRequest.request.folio= datos.folio;
              this.objectRequest.request.creationDate= datos.creationDateFormats.iso;
              this.objectRequest.request.idUserRequest= datos.userRequest.idUser;
              this.objectRequest.request.idRequestStatus= datos.requestStatus.idRequestStatus;
              Vue.set(this.objectRequest.request, "userRequest", datos.userRequest );
              Vue.set(this.objectRequest.request, "userResponsable", datos.userResponsible );
            }
            ,
            obtainAllUsers: function()
            {
             this.$http.get(ROOT_URL + "/users").success(function (data)
              {
                 this.Users= data;
              });
            },
            createCotizacion: function()
            {
              var cotizacion=
              {
              idEstimation: '',
              amount: '',
              rate: '',
              fileName: '',
              sku: '',
              outOfBudget: '',
              idRequest: '',
              idEstimationStatus: '',
              idAccount: '',
              idCurrency: '',
              idUserAuthorization: '',
              idUserEstimation: '',
              creationDate: '',
              idSupplier: '',
              accountSupplier: {},
              indexOfForm: ''
              }
              return cotizacion;
            },
            newCotizacion: function()
            {
              var cotizacion= this.createCotizacion();
              cotizacion.idRequest= this.objectRequest.request.idRequest;
              cotizacion.indexOfForm= this.estimations.length;
              this.estimations.push(cotizacion);
            },
            deleteCotizacion: function(cotizacion)
            {
              this.estimations.$remove(cotizacion);
            },
            createAccountPayable: function()
            {
              var accountPayable=
              {
                idAccountPayable: '',
                folio: '',
                amount: '',
                paidAmount: '',
                payNum: '',
                totalPayments: '',
                creationDate: '',
                dueDate: '',
                idAccountPayableStatus: '',
                idOperationType: '',
                idCurrency: '',
                rate: ''
              }
              return accountPayable;
            },
            obtainSuppliers: function()
            {
              this.$http.get(ROOT_URL + "/providers").success(function (data)
               {
                  this.suppliers= data;
               });
            },
            obtainAccounts: function(cotizacion)
            {
              this.$http.get(ROOT_URL + "/providers-accounts/provider/"+cotizacion.idSupplier).success(function (data)
               {
                    cotizacion.accountSupplier= data;
               });

            },
            obtainCurrencies: function()
            {
              this.$http.get(ROOT_URL + "/currencies").success(function (data)
               {
                    this.currencies= data;
               });

            },
            saveEstimations: function(cotizacion)
            {
              var form = document.getElementById("form-"+cotizacion.indexOfForm);
              var formData = new FormData(form);
              this.$http.post(ROOT_URL+"/estimations", JSON.stringify(cotizacion)).
              success(function(data)
              {
                console.log("Bien");

                this.$http.post(ROOT_URL+"/estimations/"+data.idEstimation+"/attachment", formData).
                success(function(data)
                {
                  console.log("Vientos");
                }).error(function(data){
                  console.log("Triste");
                });

              }).error(function(data)
              {
                console.log("Mal");
              });
            }
          },
        filters:
        {

        }
        });

        </script>
    </jsp:attribute>

    <jsp:body>
      <div id="contenidos">

          <div class="container-fluid" style="margin-left: 100px">

            <form v-on:submit.prevent="saveRequest">
            <div class="row">
              <div class="col-xs-4">
              <h1>Solicitud Periodica</h1>
              </div>
              <div class="col-xs-4 col-xs-offset-4">
                <label>
                  Solicitante:
                </label>
                <input class="form-control" type="text" name="name" value="" disabled="true">
              </div>
            </div>
            <br>
            <div class="row">
              <div class="col-xs-2">
               <label>
                 Tipo de Solicitud:
               </label>
               <select class="form-control" v-model="obtainRequestInformation.idRequestType" :disabled="desactivarCombos" @change="obtainProductType" required>
                 <option v-for="RequestType in RequestTypes"
                   value="{{RequestType.idRequestType}}">{{RequestType.requestType}}
                 </option>
               </select>
              </div>

              <div class="col-xs-2">
                <label>
                  Tipo de producto
                </label>
                <select class="form-control" v-model="obtainRequestInformation.idProductType" :disabled="desactivarCombos"
                  @change="obtainProducts" required>
                  <option v-for="ProductType in ProductTypes" value="{{ProductType.idProductType}}">
                    {{ProductType.productType}}
                  </option>
                </select>
              </div>

              <div class="col-xs-2">
                <label>
                  Productos
                </label>
                <select class="form-control" v-model="idProducto" id="selectProducto" required>
                  <option v-for="Product in Productos" value="{{Product.idProduct}}">
                    {{Product.product}}
                  </option>
                </select>
              </div>

              <div class="col-xs-1">
                <div class="col-xs-6">
                  <button type="button" class="btn btn-default" style="margin-top: 25px; margin-left: -33px"
                    v-on:click="saveProduct">
                    <span class="glyphicon glyphicon-plus"></span>
                  </button>
                </div>
              </div>

              <div class="col-xs-2">
                <label>
                  Fecha Aplicacion
                </label>
                <div class="form-group">
                <div class='input-group date' id='datetimepicker1'>
                    <input type='text' class="form-control" v-model="obtainRequestInformation.applyingDate" @change="obtainRequestInfo">
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
                </div>

              </div>

              <div class="col-xs-2">
                <label>
                  Responsable:
                </label>
                <select class="form-control" required="true" v-model="obtainRequestInformation.idUserResponsable"
                @change="obtainRequestInfo">
                  <option></option>
                  <option v-for="user in Users" value="{{user.idUser}}"> {{user.username}} </option>
                </select>
              </div>
            </div>
            <br>
              <div class="row">
                <div class="col-xs-12">
                  <label>
                    Lista de Productos
                  </label>
                </div>
              </div>

              <div class="row" v-for="produc in objectRequest.products">
                <div class="col-xs-4">
                  <div class="col-xs-4">
                    {{produc.descripcion}}
                  </div>
                  <div class="col-xs-2 text-left">
                    <button class="btn btn-link" @click="deleteProduct(produc)">
                      <span class="glyphicon glyphicon-remove"></span>
                    </button>
                  </div>
                </div>

              </div>
              <br>
            <div class="row">
              <div class="col-xs-12">
                <label>
                  Descripcion del Servicio:
                </label>
                <textarea class="form-control" rows="3" cols="50" v-model="objectRequest.request.description" required></textarea>
              </div>
            </div>
            <br>
            <div class="row">
              <div class="col-xs-12">
                <label>
                  Motivo de Contratacion:
                </label>
                <textarea class="form-control" rows="3" cols="50" v-model="objectRequest.request.purpose" required></textarea>
              </div>
            </div>

            <br>
            <div class="row">
              <div class="col-xs-6 text-left">
                <button type="button" class="btn btn-default" @click="newCotizacion">Agregar Cotizacion</button>
              </div>
              <div class="col-xs-6 text-right">
                <button class="btn btn-success" :disabled="desactivarGuardar">Guardar Solicitud</button>
              </div>
            </div>

          </form>
          <br>
          <div class="row" v-for="cotizacion in estimations">
            <form v-on:submit.prevent="saveEstimations(cotizacion)" id="form-{{cotizacion.indexOfForm}}">
            <div class="col-xs-12">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <div class="row">
                    <div class="col-xs-8 text-left">
                      <h3 class="panel-title">Cotizacion</h3>
                    </div>
                    <div class="col-xs-4">
                      <div class="col-xs-6 text-right">
                        <button class="btn btn-sm btn-default">
                          <span class="glyphicon glyphicon-floppy-disk"></span>
                        </button>
                      </div>
                      <div class="col-xs-6">
                        <button type="button" class="btn btn-sm btn-default" @click="deleteCotizacion(cotizacion)" >
                          <span class="glyphicon glyphicon-remove"></span>
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="panel-body">
                  <div class="row">
                    <div class="col-xs-3">
                      <label>
                        Proveedor
                      </label>
                      <select class="form-control" v-model="cotizacion.idSupplier" @change="obtainAccounts(cotizacion)">
                        <option></option>
                        <option v-for="supplier in suppliers" value="{{supplier.idProvider}}">
                          {{supplier.providerName}}
                        </option>
                      </select>
                    </div>
                    <div class="col-xs-3">
                      <label>
                        Cuenta Bancaria
                      </label>
                      <select class="form-control" v-model="cotizacion.idAccount">
                        <option></option>
                        <option v-for="accounts in cotizacion.accountSupplier" value="{{accounts.idAccount}}">
                          {{accounts.account.accountNumber}}
                        </option>
                      </select>
                    </div>
                    <div class="col-xs-3">
                      <label>
                        Tipo de Moneda
                      </label>
                      <select class="form-control" v-model="cotizacion.idCurrency">
                        <option></option>
                        <option v-for="curr in currencies" value="{{curr.idCurrency}}">
                          {{curr.currency}}
                        </option>
                      </select>
                    </div>
                    <div class="col-xs-3">
                      <label>
                        Monto
                      </label>
                      <div class="input-group">
                        <span class="input-group-addon">$</span>
                        <input number class="form-control" placeholder="" v-model="cotizacion.amount">
                      </div>
                    </div>
                  </div>
                  <br>
                  <div class="row">
                    <div class="col-xs-3">
                      <label>
                        SKU
                      </label>
                      <input class="form-control" v-model="cotizacion.sku">
                    </div>
                    <div class="col-xs-4">
                      <label>
                        Archivo de la Cotizacion
                      </label>
                      <input type="file" name="file" class="form-control" v-model="cotizacion.fileName">
                    </div>
                    <div class="col-xs-3">
                      <label>
                        Tipo de Cambio
                      </label>
                      <div class="input-group">
                        <span class="input-group-addon">$</span>
                        <input number class="form-control" placeholder="" v-model="cotizacion.rate">
                      </div>
                    </div>
                    </div>
                  </div>
                </div>
              </div>
            </form>
            </div>
            <pre>
              {{ $data.estimations | json}}
            </pre>
          </div>

          </div> <!-- container-fluid -->

      </div> <!-- #contenidos -->
      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
