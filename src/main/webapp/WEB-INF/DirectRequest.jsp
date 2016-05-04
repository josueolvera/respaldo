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
        created: function()
        {

        },
        ready: function ()
        {
          this.timePicker = $('#datetimepicker1').datetimepicker({
            locale: 'es',
            format: 'YYYY/MM/DD'
            }).data();

          this.obtainRequestInformation.idRequestCategory= this.RequestCategory;
          this.$http.get(ROOT_URL+"/request-types/request-category/"+ this.obtainRequestInformation.idRequestCategory)
                  .success(function (data)
                  {
                     this.RequestTypes= data;
                  });
          this.obtainUserInSession();
          this.obtainAllUsers();
          this.obtainSuppliers();
          this.obtainCurrencies();
          this.verifyUpdateOrSave();
        },
        data:
        {
          RequestCategory: ${cat},
          idRequest: ${idRequest},
          obtainRequestInformation:
           {
             idRequestCategory: '',
             idRequestType: '',
             idProductType: '',
             idUserResponsable: '',
             applyingDate: ''
           },
           RequestTypes: {},
           desactivarCombos: false,
           isUpdate: false,
           ProductTypes: {},
           Productos: {},
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
               idRequestStatus: '',
               isSaved: false
             },
            products: []
          },
          desactivarGuardar: true,
          timePicker: '',
          Users: {},
          ResponseRequestInformation: {},
          estimation: {
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
              indexOfForm: '',
              userAuthorization: {
                idUser: '',
                username: '',
                mail: ''
              },
              userEstimation: {
                idUser: '',
                username: '',
                mail: ''
              },
              isSaved: true
            },
            suppliers: {},
            currencies: {},
            userInSession: '',
            isSavingNow: false,
            AccountsPayables: [],
            infoAutorization: '',
            userRequest: ''

        },
        methods:
        {
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
          obtainAllUsers: function()
          {
           this.$http.get(ROOT_URL + "/users").success(function (data)
            {
               this.Users= data;
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
                    }).error(function(data)
                    {
                      showAlert("No existe presupuesto para este tipo de solicitud");
                      this.obtainRequestInformation.idRequestType= '';
                      this.obtainRequestInformation.idProductType= '';
                      this.obtainRequestInformation.idUserResponsable= '';
                      this.obtainRequestInformation.applyingDate= '';
                      this.objectRequest.products= [];
                      this.idProducto= '';
                      this.desactivarCombos= false;
                    });
          },
          matchInformation: function(requestInformation)
          {
            this.objectRequest.request.idRequestTypesProduct= requestInformation.requestTypesProduct.idRequestTypeProduct;
            this.objectRequest.request.applyingDate= this.obtainRequestInformation.applyingDate;
            this.objectRequest.request.idUserResponsable= this.obtainRequestInformation.idUserResponsable;
            this.objectRequest.request.idBudgetMonthBranch = requestInformation.budgetMonthBranch.idBudgetMonthBranch;
          },
          obtainSuppliers: function()
          {
            this.$http.get(ROOT_URL + "/providers").success(function (data)
             {
                this.suppliers= data;
             });
          },
          obtainAccounts: function(estimation)
          {
            this.$http.get(ROOT_URL + "/providers-accounts/provider/"+estimation.idSupplier).success(function (data)
             {
                  estimation.accountSupplier= data;
             });
          },
          obtainCurrencies: function()
          {
            this.$http.get(ROOT_URL + "/currencies").success(function (data)
             {
                  this.currencies= data;
             });
          },
          saveAllInformationRequest: function(event)
          {
            this.saveRequest();
          },
          saveRequest: function()
          {
            this.isSavingNow= true;
            this.$http.post(ROOT_URL+"/requests", JSON.stringify(this.objectRequest)).
            success(function(data)
            {
              this.fillRequestInformation(data);
            }).error(function(data)
            {
              showAlert("Ha habido un error intente nuevamente");
            });
          },
          fillRequestInformation: function(datos)
          {
            this.objectRequest.request.idRequest= datos.idRequest;
            this.estimation.idRequest= this.objectRequest.request.idRequest;
            this.objectRequest.request.folio= datos.folio;
            this.objectRequest.request.creationDate= datos.creationDateFormats.iso;
            this.objectRequest.request.idUserRequest= datos.userRequest.idUser;
            this.objectRequest.request.idRequestStatus= datos.requestStatus.idRequestStatus;
            Vue.set(this.objectRequest.request, "userRequest", datos.userRequest );
            Vue.set(this.objectRequest.request, "userResponsable", datos.userResponsible );
            this.objectRequest.request.isSaved = true;
            this.desactivarGuardar= true;
            this.saveEstimations();
          },
          saveEstimations: function()
          {
            var responseOfEstimation;
            this.$http.post(ROOT_URL+"/estimations", JSON.stringify(this.estimation)).
            success(function(data)
            {
              responseOfEstimation= data;
              showAlert("Registro de solicitud exitoso");
              this.matchEstimationInfo(responseOfEstimation, this.estimation);
            }).error(function(data)
            {
              showAlert("Ha fallado el registro de su cotizacion, intente nuevamente");
            });
          },
          matchEstimationInfo: function(responseOfEstimation, cotizacion)
          {
            cotizacion.idEstimation= responseOfEstimation.idEstimation;
            cotizacion.outOfBudget= responseOfEstimation.outOfBudget;
            cotizacion.idEstimationStatus= responseOfEstimation.estimationStatus.idEstimationStatus;
            cotizacion.idUserEstimation= responseOfEstimation.userEstimation.idUser;
            cotizacion.creationDate= responseOfEstimation.creationDateFormats.iso;
            cotizacion.userEstimation.idUser= responseOfEstimation.userEstimation.idUser;
            cotizacion.userEstimation.username= responseOfEstimation.userEstimation.username;
            cotizacion.userEstimation.mail= responseOfEstimation.userEstimation.mail;
            cotizacion.isSaved= false;
            this.isSavingNow= false;
            this.createAccountPayable();
          },
          createAccountPayable: function()
          {
            var self= this;
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
              rate: '',
              idDatePicker: ''
            }
              accountPayable.folio= this.objectRequest.request.folio;
              accountPayable.amount= this.estimation.amount;
              accountPayable.payNum= 1;
              accountPayable.totalPayments= 1;
              accountPayable.idCurrency= this.estimation.idCurrency;
              accountPayable.rate= this.estimation.rate;
              this.AccountsPayables.push(accountPayable);

              this.$http.post(ROOT_URL+"/accounts-payable/folio?folio="+this.objectRequest.request.folio, JSON.stringify(this.AccountsPayables)).
              success(function(data)
              {
                var idRequest= this.objectRequest.request.idRequest;
                this.isSavingNow= false;
                this.AccountsPayables.forEach(function(element)
                {
                    data.forEach(function(el)
                    {
                      if (el.payNum == element.payNum)
                      {
                          element.idAccountPayable = el.idAccountPayable;
                          element.paidAmount = el.paidAmount;
                          element.creationDate = self.convertDates(el.creationDateFormats.dateNumber);
                          if (element.dueDate !== "")
                          {
                             element.dueDate = self.convertDates(el.dueDateFormats.dateNumber);
                          }
                          element.idAccountPayableStatus = el.accountPayableStatus.idAccountPayableStatus;
                          element.idOperationType = el.operationType.idOperationType;
                      }
                    });
                });
                setInterval(function()
                {
                  window.location.href= ROOT_URL+"/siad/directa/"+idRequest
                },2000);
              });

        },
          verifyUpdateOrSave: function()
          {
            if (this.idRequest> 0)
            {
              this.$http.get(ROOT_URL+"/requests/"+this.idRequest).
              success(function(data)
              {
                this.matchInformationUpdate(data);
              }).error(function(data){
                showAlert("Ha habido un error al obtener la informacion");
              });
            }
          },
          matchInformationUpdate: function(data)
          {
            var self= this;
            this.isUpdate= true;
            this.obtainRequestInformation.idRequestType= data.requestTypeProduct.idRequestType;
            this.obtainRequestInformation.applyingDate= data.applyingDateFormats.dateNumber;
            this.obtainRequestInformation.idUserResponsable= data.idUserResponsible;
            this.objectRequest.request.idRequest= data.idRequest;
            this.objectRequest.request.description= data.description;
            this.objectRequest.request.purpose= data.purpose;
            this.objectRequest.request.folio= data.folio;
            this.objectRequest.request.creationDate= this.convertDates(data.creationDateFormats.dateNumber);
            this.objectRequest.request.applyingDate= this.convertDates(data.applyingDateFormats.dateNumber);
            this.objectRequest.request.idUserRequest= data.userRequest.idUser;
            this.objectRequest.request.idUserResponsable= data.idUserResponsible;
            this.objectRequest.request.idBudgetMonthBranch= data.idBudgetMonthBranch;
            this.objectRequest.request.idRequestTypeProduct= data.idRequestTypeProduct;
            this.objectRequest.request.idRequestStatus= data.idRequestStatus;
            this.obtainInformationAutorization();
            this.userRequest = data.userRequest.mail;
            data.requestProductsList.forEach(function(element)
            {
            var producto= self.createProduct();
            producto.idProduct= element.product.idProduct;
            producto.descripcion= element.product.product;
            self.objectRequest.products.push(producto);
            });

            this.$http.get(ROOT_URL+"/estimations/request/"+this.idRequest).
            success(function(data)
            {
              this.matchInformationEstimationsUpdate(data);
            }).error(function(data){
              showAlert("Ha habido un error al obtener la informacion de la cotizacion");
            });

          },
          matchInformationEstimationsUpdate: function(data)
          {
            var self = this;
              data.forEach(function(element)
              {
                self.estimation.idEstimation= element.idEstimation;
                self.estimation.amount = element.amount;
                self.estimation.rate= element.rate;
                self.estimation.sku= element.sku;
                self.estimation.outOfBudget = element.outOfBudget;
                self.estimation.idRequest = element.idRequest;
                self.estimation.idEstimationStatus = element.idEstimationStatus;
                self.estimation.idAccount = element.idAccount;
                self.estimation.idCurrency = element.idCurrency;
                self.estimation.idUserEstimation = element.idUserEstimation;
                self.estimation.indexOfForm = self.estimation.length;
                self.estimation.creationDate = element.creationDateFormats.iso;
                self.fillSuppliers(self.estimation);
              });
          },
          fillSuppliers: function(cotizacion)
          {
            var self= this;
            this.$http.get(ROOT_URL+"/providers-accounts/account/"+cotizacion.idAccount).
            success(function(data)
            {
              data.forEach(function(element)
              {
                self.estimation.idSupplier= element.idProvider;

                self.$http.get(ROOT_URL + "/providers-accounts/provider/"+cotizacion.idSupplier).
                success(function (data)
                 {
                      self.estimation.accountSupplier= data;
                 });
              });

            }).error(function(data){
              showAlert("Ha habido un error al obtener la informacion de las cotizaciones");
            });
          },
          downloadFile: function(idEstimation)
          {
            this.$http.get(ROOT_URL + "/estimations/attachment/download/"+idEstimation).
            success(function (data)
             {

             }).error(function(data)
             {
              showAlert("Ha habido un error al obtener el archivo");
             });
          },
          modifyCotizacion: function(cotizacion)
          {
            this.$http.post(ROOT_URL+"/estimations/cotizacion.idEstimation", JSON.stringify(cotizacion)).
            success(function(data)
            {
              showAlert("Modificacion Exitosa");

            }).error(function(data)
            {
              showAlert("Ha fallado el registro de su cotizacion, intente nuevamente");
            });

          },
          autorizeRequest: function()
          {
            //Aqui tenemos que poner la funcion para autorizar y despues crear la cuenta por pagar
          },
          declineRequest: function()
          {
            alert("Se rechaza");
          },
          obtainUserInSession: function()
          {
            this.$http.get(ROOT_URL + "/user").
            success(function (data)
             {
               this.userInSession = data;
               this.userRequest = data.mail;

             }).error(function(data)
             {
              showAlert("Ha habido un error al obtener al usuario en sesion");
             });

          },
          convertDates: function(date)
          {
            var dateinformatguion= date.split("-");
            return dateinformatguion[2]+"/"+dateinformatguion[1]+"/"+dateinformatguion[0];
          },
          autorizarSolicitudIndividual: function(info)
          {
            var detalle= {
              details: ''
            }
            detalle.details = info.details;

            this.$http.post(ROOT_URL+"/folios/authorizations/"+ info.idAuthorization +"/authorize",JSON.stringify(detalle)).
            success(function(data)
            {
              showAlert(data);
              setInterval(function()
              {
                window.location.reload()
              },2500);
            }).error(function() {
              showAlert("Ha habido un error al autorizar la solicitud, intente nuevamente");
            });
          },
          rechazarSolicitudIndividual: function(info)
          {
            var detalle= {
              details: ''
            }
            detalle.details = info.details;

            this.$http.post(ROOT_URL+"/folios/authorizations/"+ info.idAuthorization +"/reject", JSON.stringify(detalle)).
            success(function(data)
            {
              showAlert(data);
              setInterval(function()
              {
                window.location.reload()
              },2500);
            }).error(function() {
              showAlert("Ha habido un error al cancelar la solicitud, intente nuevamente");
            });
          },
          obtainInformationAutorization: function()
          {
            this.infoAutorization= '';
            this.$http.get(ROOT_URL+"/folios?folio="+ this.objectRequest.request.folio).
            success(function(data)
            {
              this.infoAutorization= data;

            }).error(function(data)
            {
              showAlert("No se ha podido obtener la informacion de la autorizacion");
            });
          }
        },
      filters:
      {
         obtainMailUser: function(param)
        {
          var self= this;
          var newParam;
          self.Users.forEach(function(element)
          {
              if (element.idUser == param)
              {
              newParam =element.mail;
              }
          });
          return newParam;
        },
        filterNull: function(param)
        {
          if (param == "null")
          {
              return ''
          }
          else
          {
            return param;
          }

        }

      }
      });

        </script>
    </jsp:attribute>

    <jsp:body>
      <div id="contenidos">

          <div class="container-fluid" style="margin-left: 100px">
            <form v-on:submit.prevent="saveAllInformationRequest">
            <div class="row">
              <div class="col-xs-4">
              <h1>Solicitud Directa</h1>
              </div>
              <div class="col-xs-4 col-xs-offset-4">
                <label>
                  Solicitante:
                </label>
                <input class="form-control" type="text" name="name" value="" disabled="true" v-model="userRequest">
              </div>
            </div>
            <br>
            <div class="row">
              <div class="col-xs-2">
               <label>
                 Tipo de Solicitud:
               </label>
               <select class="form-control" v-model="obtainRequestInformation.idRequestType" :disabled="desactivarCombos || isUpdate" @change="obtainProductType" required>
                 <option v-for="RequestType in RequestTypes"
                   value="{{RequestType.idRequestType}}">{{RequestType.requestType}}
                 </option>
               </select>
              </div>

              <div class="col-xs-2">
                <label>
                  Tipo de producto
                </label>
                <select class="form-control" v-model="obtainRequestInformation.idProductType" :disabled="desactivarCombos || isUpdate"
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
                <select class="form-control" v-model="idProducto" id="selectProducto" :disabled="isUpdate" required>
                  <option v-for="Product in Productos" value="{{Product.idProduct}}">
                    {{Product.product}}
                  </option>
                </select>
              </div>

              <div class="col-xs-1">
                <div class="col-xs-6">
                  <button type="button" class="btn btn-default" style="margin-top: 25px; margin-left: -33px"
                  :disabled="isUpdate"  v-on:click="saveProduct">
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
                    <input type='text' class="form-control" v-model="obtainRequestInformation.applyingDate"
                      :disabled="isUpdate" @change="obtainRequestInfo">
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
                <select class="form-control" required="true" :disabled="isUpdate" v-model="obtainRequestInformation.idUserResponsable"
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
                    <button class="btn btn-default" @click="deleteProduct(produc)" :disabled="isUpdate">
                      <span class="glyphicon glyphicon-remove"></span>
                    </button>
                  </div>
                </div>

              </div>
              <br>
            <div class="row">
              <div class="col-xs-12">
                <label>
                  Descripcion de la Solicitud:
                </label>
                <textarea class="form-control" rows="3" cols="50" v-model="objectRequest.request.description"
                  :disabled="isUpdate" required></textarea>
              </div>
            </div>
            <br>
            <div class="row">
              <div class="col-xs-12">
                <label>
                  Motivo de la Solicitud:
                </label>
                <textarea class="form-control" rows="3" cols="50" v-model="objectRequest.request.purpose"
                  :disabled="isUpdate" required></textarea>
              </div>
            </div>

            <br>

            <div class="row">
              <div class="col-xs-12">
                <div class="panel panel-default">
                  <div class="panel-heading">
                    <div class="row">
                      <div class="col-xs-4 text-left">
                        <h3 class="panel-title">Informacion del Pago</h3>
                      </div>
                      <div class="col-xs-4" >
                        <span class="label label-danger" v-if="estimation.outOfBudget == 1">Cotizacion Fuera de Presupuesto</span>
                      </div>
                      <div class="col-xs-4">
                        <div class="col-xs-8">

                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="panel-body">
                    <div class="row">
                      <div class="col-xs-2">
                        <label>
                          Proveedor
                        </label>
                        <select class="form-control" v-model="estimation.idSupplier"
                          @change="obtainAccounts(estimation)" required="true">
                          <option></option>
                          <option v-for="supplier in suppliers" value="{{supplier.idProvider}}">
                            {{supplier.providerName}}
                          </option>
                        </select>
                      </div>
                      <div class="col-xs-2">
                        <label>
                          Cuenta Bancaria
                        </label>
                        <select class="form-control" v-model="estimation.idAccount" required="true">
                          <option></option>
                          <option v-for="accounts in estimation.accountSupplier" value="{{accounts.idAccount}}">
                            {{accounts.account.accountNumber}}
                          </option>
                        </select>
                      </div>
                      <div class="col-xs-2">
                        <label>
                          Tipo de Moneda
                        </label>
                        <select class="form-control" v-model="estimation.idCurrency" required="true">
                          <option></option>
                          <option v-for="curr in currencies" value="{{curr.idCurrency}}">
                            {{curr.currency}}
                          </option>
                        </select>
                      </div>
                      <div class="col-xs-2">
                        <label>
                          Monto
                        </label>
                        <div class="input-group">
                          <span class="input-group-addon">$</span>
                          <input number class="form-control" placeholder="" v-model="estimation.amount" required="true">
                        </div>
                      </div>
                      <div class="col-xs-2">
                        <label>
                          SKU(Opcional)
                        </label>
                        <input class="form-control" v-model="estimation.sku">
                      </div>
                      <div class="col-xs-2">
                        <label>
                          Tipo de Cambio
                        </label>
                        <div class="input-group">
                          <span class="input-group-addon">%</span>
                          <input number class="form-control" placeholder="" v-model="estimation.rate" required="true">
                        </div>
                      </div>
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-xs-12 text-right">
                      <button class="btn btn-success" :disabled="desactivarGuardar || isSavingNow">Guardar Solicitud</button>
                    </div>
                  </div>
                </div>

              </form>
              </div>

              <div class="row">
                <div class="col-xs-12">
                  <label>
                    Autorizaciones de la Solicitud.
                  </label>
                  <table class="table table-striped">
                    <thead>
                      <th>
                        Usuario
                      </th>
                      <th>
                        Estatus
                      </th>
                      <th>
                        Autorizar
                      </th>
                      <th>
                        Detalles
                      </th>
                    </thead>
                    <tbody>
                      <tr v-for="info in infoAutorization.authorizations">
                        <td>
                          {{info.idUser | obtainMailUser}}
                        </td>
                        <td>
                          <span class="label label-success" v-if="info.idAuthorizationStatus == 2">Autorizado</span>
                          <span class="label label-info" v-if="info.idAuthorizationStatus == 1">Pendiente</span>
                          <span class="label label-danger" v-if="info.idAuthorizationStatus == 3">Rechazado</span>
                        </td>
                        <td>
                          <button type="button" class="btn btn-success btn-sm" name="button" @click="autorizarSolicitudIndividual(info)"
                            v-if="info.idAuthorizationStatus == 1 & info.idUser == userInSession.idUser">Autorizar</button>
                          <button type="button" class="btn btn-danger btn-sm" name="button" @click="rechazarSolicitudIndividual(info)"
                            v-if="info.idAuthorizationStatus == 1 & info.idUser == userInSession.idUser">Rechazar</button>

                        </td>
                        <td>
                          <textarea name="name" rows="3" cols="40" v-model="info.details" v-if="info.idAuthorizationStatus == 1">

                          </textarea>
                          <textarea name="name" rows="3" cols="40" v-model="info.details | filterNull"
                            v-if="info.idAuthorizationStatus == 3 || info.idAuthorizationStatus == 2" disabled="true" >

                          </textarea>
                        </td>
                      </tr>
                    </tbody>

                  </table>

                </div>
              </div>
          </div>
          </div> <!-- container-fluid -->

      </div> <!-- #contenidos -->
      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
