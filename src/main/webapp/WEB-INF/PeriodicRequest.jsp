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

            this.timePickerPagoInicial = $('#datePagoInicial').datetimepicker({
              locale: 'es',
              format: 'YYYY/MM/DD'
              }).data();

              this.timePickerFechaVencimiento = $('#dateFechaVencimiento').datetimepicker({
                locale: 'es',
                format: 'YYYY/MM/DD'
                }).data();

            this.obtainAllUsers();
            this.obtainSuppliers();
            this.obtainCurrencies();
            this.obtainAllPeriods();
            this.obtainRequestInformation.idRequestCategory= this.RequestCategory;
            this.$http.get(ROOT_URL+"/request-types/request-category/"+ this.obtainRequestInformation.idRequestCategory)
                    .success(function (data)
                    {
                       this.RequestTypes= data;
                    });
            this.verifyUpdateOrSave();
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
                idRequestStatus: '',
                isSaved: false
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
            RequestCategory: ${cat},
            idRequest: ${idRequest},
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
            isUpdate: false,
            desactivarGuardar: true,
            numberOfRequest: 0,
            estimations: [],
            suppliers: {},
            idSupplier: '',
            accounts: {},
            idAccount: '',
            timePicker: '',
            currencies: {},
            periodicPayment:
            {
            idPeriodicPayment: '',
            folio: '',
            amount: '',
            initialDate: '',
            nextPayment: '',
            dueDate: '',
            paymentNum: '',
            idPeriod: '',
            idPeriodicPaymentStatus: '',
            idCurrency: '',
            rate: ''
          },
          timePickerPagoInicial: '',
          timePickerFechaVencimiento: '',
          Periods: ''
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
                showAlert("Registro de solicitud exitoso, ahora puedes agregar las cotizaciones")
                this.fillRequestInformation(data);
              }).error(function(data)
              {

              });
            },
            fillRequestInformation: function(datos)
            {
              this.objectRequest.request.idRequest= datos.idRequest;
              this.objectRequest.request.folio= datos.folio;
              this.periodicPayment.folio= datos.folio;
              this.objectRequest.request.creationDate= datos.creationDateFormats.iso;
              this.objectRequest.request.idUserRequest= datos.userRequest.idUser;
              this.objectRequest.request.idRequestStatus= datos.requestStatus.idRequestStatus;
              Vue.set(this.objectRequest.request, "userRequest", datos.userRequest );
              Vue.set(this.objectRequest.request, "userResponsable", datos.userResponsible );
              this.objectRequest.request.isSaved = true;
              this.desactivarGuardar= true;
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
              isSaved: true,
              requiredFile: true
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
              if (cotizacion.idEstimation > 0)
              {
                //this.modifyCotizacion(cotizacion);
                if (cotizacion.fileName !== "")
                {
                  var form = document.getElementById("form-"+cotizacion.indexOfForm);
                  var formData = new FormData(form);

                  this.$http.post(ROOT_URL+"/estimations/"+cotizacion.idEstimation, JSON.stringify(cotizacion)).
                  success(function(data)
                  {
                    this.$http.post(ROOT_URL+"/estimations/"+cotizacion.idEstimation+"/attachment", formData).
                    success(function(data)
                    {
                      showAlert("Modificacion Realizada con Exito");
                    }).error(function(data){
                      showAlert("La modificacion se ha realizado, pero hubo un error al guardar el archivo");
                    });

                  }).error(function(data)
                  {
                    showAlert("Ha fallado el registro de su cotizacion, intente nuevamente");
                  });

                }
                else{
                  this.$http.post(ROOT_URL+"/estimations/"+cotizacion.idEstimation, JSON.stringify(cotizacion)).
                  success(function(data)
                  {
                    showAlert("Modificacion Exitosa");

                  }).error(function(data)
                  {
                    showAlert("Ha fallado el registro de su cotizacion, intente nuevamente");
                  });
                }
              }
              else
              {
              var form = document.getElementById("form-"+cotizacion.indexOfForm);
              var formData = new FormData(form);
              var responseOfEstimation;
              var responseOfFileUpload;
              this.$http.post(ROOT_URL+"/estimations", JSON.stringify(cotizacion)).
              success(function(data)
              {
                responseOfEstimation= data;
                this.$http.post(ROOT_URL+"/estimations/"+data.idEstimation+"/attachment", formData).
                success(function(data)
                {
                  showAlert("Registro de cotizacion Exitoso");
                  responseOfFileUpload= data;
                  this.matchEstimationInfo(responseOfEstimation, responseOfFileUpload, cotizacion);
                }).error(function(data){
                  showAlert("La cotizacion se ha guardado, pero hubo un error al guardar el archivo");
                });

              }).error(function(data)
              {
                showAlert("Ha fallado el registro de su cotizacion, intente nuevamente");
              });
            }
            },
            matchEstimationInfo: function(responseOfEstimation, responseOfFileUpload, cotizacion)
            {
              cotizacion.idEstimation= responseOfEstimation.idEstimation;
              cotizacion.fileName= responseOfFileUpload.fileName;
              cotizacion.outOfBudget= responseOfEstimation.outOfBudget;
              cotizacion.idEstimationStatus= responseOfEstimation.estimationStatus.idEstimationStatus;
              cotizacion.idUserEstimation= responseOfEstimation.userEstimation.idUser;
              cotizacion.creationDate= responseOfEstimation.creationDateFormats.iso;
              cotizacion.userEstimation.idUser= responseOfEstimation.userEstimation.idUser;
              cotizacion.userEstimation.username= responseOfEstimation.userEstimation.username;
              cotizacion.userEstimation.mail= responseOfEstimation.userEstimation.mail;
              cotizacion.isSaved= false;
              cotizacion.requiredFile= false;
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
              this.periodicPayment.folio= data.folio;
              this.objectRequest.request.description= data.description;
              this.objectRequest.request.purpose= data.purpose;
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
                showAlert("Ha habido un error al obtener la informacion de las cotizacion");
              });

            },
            matchInformationEstimationsUpdate: function(data)
            {
              var self = this;
                data.forEach(function(element)
                {
                  var cotizacion= self.createCotizacion();
                  cotizacion.idEstimation= element.idEstimation;
                  cotizacion.amount = element.amount;
                  cotizacion.rate= element.rate;
                  cotizacion.sku= element.sku;
                  cotizacion.outOfBudget = element.outOfBudget;
                  cotizacion.idRequest = element.idRequest;
                  cotizacion.idEstimationStatus = element.idEstimationStatus;
                  cotizacion.idAccount = element.idAccount;
                  cotizacion.idCurrency = element.idCurrency;
                  cotizacion.idUserEstimation = element.idUserEstimation;
                  cotizacion.creationDate = element.creationDateFormats.iso;
                  cotizacion.requiredFile = false;
                  self.fillSuppliers(cotizacion);
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
                  cotizacion.idSupplier= element.idProvider;

                  self.$http.get(ROOT_URL + "/providers-accounts/provider/"+cotizacion.idSupplier).
                  success(function (data)
                   {
                        cotizacion.accountSupplier= data;
                   });
                  cotizacion.indexOfForm = self.estimations.length;
                  self.estimations.push(cotizacion);
                });

              }).error(function(data){
                showAlert("Ha habido un error al obtener la informacion de las cotizacion");
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
            obtainAllPeriods: function()
            {
              this.$http.get(ROOT_URL + "/periods").
              success(function (data)
               {
                 this.Periods = data;
               }).error(function(data)
               {
                showAlert("Ha habido un error al obtener los periodos");
               });

            },
            savePeriodicPayment: function()
            {
              var dateInitialWithout= this.periodicPayment.initialDate;
              var datedueDateWithout= this.periodicPayment.dueDate;
              if (this.periodicPayment.dueDate !== "")
              {
                var dateDueDate= this.timePickerFechaVencimiento.DateTimePicker.date();
                var dateisoDue= dateDueDate.toISOString();
                this.periodicPayment.dueDate = dateisoDue.slice(0, -1);
              }
              var dateInitial = this.timePickerPagoInicial.DateTimePicker.date();
              var dateisoInitial= dateInitial.toISOString();
              this.periodicPayment.initialDate= dateisoInitial.slice(0, -1);


              this.$http.post(ROOT_URL+"/requests/period-payment", JSON.stringify(this.periodicPayment)).
              success(function(data)
              {
                showAlert("Registro de informacion de pago exitoso");
                this.periodicPayment.idPeriodicPayment= data.idPeriodicPayment;
                this.periodicPayment.initialDate= dateInitialWithout;
                this.periodicPayment.dueDate= datedueDateWithout;
                this.periodicPayment.idPeriodicPaymentStatus= data.periodicPaymentStatus.idPeriodicPaymentStatus;
                this.periodicPayment.paymentNum = data.paymentNum;
              }).error(function(data)
              {
                showAlert("Ha fallado el registro de su informacion, intente nuevamente");
              });

            },
            prepareModalPeriodicPayment: function(cotizacion)
            {
              $("#periodicPayment").modal("show");
              this.periodicPayment.amount= cotizacion.amount;
              this.periodicPayment.idCurrency= cotizacion.idCurrency;
              this.periodicPayment.rate= cotizacion.rate;
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
                    v-on:click="saveProduct" :disabled="isUpdate">
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
                      @change="obtainRequestInfo" :disabled="isUpdate">
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
                @change="obtainRequestInfo" :disabled="isUpdate">
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
                    <button class="btn btn-link" @click="deleteProduct(produc)" :disabled="isUpdate">
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
                <textarea class="form-control" rows="3" cols="50" v-model="objectRequest.request.description"
                  :disabled="isUpdate" required></textarea>
              </div>
            </div>
            <br>
            <div class="row">
              <div class="col-xs-12">
                <label>
                  Motivo de Contratacion:
                </label>
                <textarea class="form-control" rows="3" cols="50" v-model="objectRequest.request.purpose"
                  :disabled="isUpdate" required></textarea>
              </div>
            </div>

            <br>
            <div class="row">
              <div class="col-xs-6 text-left">
                <button class="btn btn-success" :disabled="desactivarGuardar">Guardar Solicitud</button>
              </div>
              <div class="col-xs-6 text-right">
                <button type="button" class="btn btn-default" @click="newCotizacion" v-if="objectRequest.request.isSaved || isUpdate">Agregar Cotizacion
                </button>
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
                    <div class="col-xs-4 text-left">
                      <h3 class="panel-title">Cotizacion</h3>
                    </div>
                    <div class="col-xs-4" >
                      <span class="label label-danger" v-if="cotizacion.outOfBudget == 1">Cotizacion Fuera de Presupuesto</span>
                    </div>
                    <div class="col-xs-4">
                      <div class="col-xs-9">

                      </div>
                      <div class="col-xs-1 text-right" v-if="cotizacion.idEstimation == 0">
                        <button class="btn btn-sm btn-default">
                          <span class="glyphicon glyphicon-floppy-disk"></span>
                        </button>
                      </div>
                      <div class="col-xs-1 text-right" v-if="cotizacion.idEstimation == 0">
                        <button type="button" class="btn btn-sm btn-default" @click="deleteCotizacion(cotizacion)" >
                          <span class="glyphicon glyphicon-remove"></span>
                        </button>
                      </div>

                      <div class="col-xs-1 text-right" v-if="cotizacion.idEstimation > 0">
                        <button class="btn btn-sm btn-default">
                          <span class="glyphicon glyphicon-pencil"></span>
                        </button>
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
                      <select class="form-control" v-model="cotizacion.idSupplier"
                        @change="obtainAccounts(cotizacion)" required="true">
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
                      <select class="form-control" v-model="cotizacion.idAccount" required="true">
                        <option></option>
                        <option v-for="accounts in cotizacion.accountSupplier" value="{{accounts.idAccount}}">
                          {{accounts.account.accountNumber}}
                        </option>
                      </select>
                    </div>
                    <div class="col-xs-2">
                      <label>
                        Tipo de Moneda
                      </label>
                      <select class="form-control" v-model="cotizacion.idCurrency" required="true">
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
                        <input number class="form-control" placeholder="" v-model="cotizacion.amount" required="true">
                      </div>
                    </div>
                    <div class="col-xs-2">
                      <label>
                        Tipo de Cambio
                      </label>
                      <div class="input-group">
                        <span class="input-group-addon">%</span>
                        <input number class="form-control" placeholder="" v-model="cotizacion.rate" required="true">
                      </div>
                    </div>
                    <div class="col-xs-2">
                      <label>
                        SKU
                      </label>
                      <input class="form-control" v-model="cotizacion.sku">
                    </div>
                  </div>
                  <br>
                  <div class="row">
                    <div class="col-xs-3">
                      <label>
                        Archivo de la Cotizacion
                      </label>
                      <input type="file" name="file" class="form-control"
                       v-model="cotizacion.fileName" required="{{cotizacion.requiredFile}}">
                    </div>
                    <div class="col-xs-2" v-if="cotizacion.idEstimation > 0">
                      <button type="button" class="btn btn-link"
                        @click="downloadFile(cotizacion.idEstimation)" style="margin-top: 25px">Ver archivo
                      </button>
                    </div>
                    <div class="col-xs-2" v-if="cotizacion.idAccount > 0">
                      <button type="button" class="btn btn-link" @click="prepareModalPeriodicPayment(cotizacion)"
                       style="margin-top: 25px">Agregar Informacion de Pago
                      </button>
                    </div>

                    </div>
                  </div>
                </div>
              </div>
            </form>
            </div>
          </div>
          <div class="modal fade" id="periodicPayment" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                  <h4 class="modal-title">Introduzca la informacion del pago periodico</h4>
                </div>
                <div class="modal-body">
                  <div class="row">
                    <div class="col-xs-6">
                      <label>
                        Monto:
                      </label>
                      <div class="input-group">
                        <span class="input-group-addon">$</span>
                        <input type="text" class="form-control" placeholder="" v-model="periodicPayment.amount"
                          disabled="true">
                      </div>
                    </div>

                    <div class="col-xs-6">
                      <label>
                        Periodicidad
                      </label>
                      <select class="form-control" name="" v-model="periodicPayment.idPeriod">
                        <option></option>
                        <option v-for="periodo in Periods" value="{{periodo.idPeriod}}">
                          {{ periodo.period}}
                        </option>
                      </select>
                    </div>

                  </div>
                  <br>
                  <div class="row">

                    <div class="col-xs-6">
                      <label>
                        Fecha Aplicacion
                      </label>
                      <div class="form-group">
                      <div class='input-group date' id='datePagoInicial'>
                          <input type='text' class="form-control" v-model="periodicPayment.initialDate">
                          <span class="input-group-addon">
                              <span class="glyphicon glyphicon-calendar"></span>
                          </span>
                      </div>
                      </div>
                    </div>

                    <div class="col-xs-6">
                      <label>
                        Fecha Vencimiento (Opcional)
                      </label>
                      <div class="form-group">
                      <div class='input-group date' id='dateFechaVencimiento'>
                          <input type='text' class="form-control" v-model="periodicPayment.dueDate">
                          <span class="input-group-addon">
                              <span class="glyphicon glyphicon-calendar"></span>
                          </span>
                      </div>
                      </div>
                    </div>
                  </div>
                  <br>
                    <div class="row">
                      <div class="col-xs-6">
                        <label>
                          Tipo de Moneda
                        </label>
                        <select class="form-control" v-model="periodicPayment.idCurrency" disabled="true">
                          <option></option>
                          <option v-for="curr in currencies" value="{{curr.idCurrency}}">
                            {{curr.currency}}
                          </option>
                        </select>
                      </div>

                      <div class="col-xs-6">
                        <label>
                          Tipo de Cambio
                        </label>
                        <div class="input-group">
                          <span class="input-group-addon">%</span>
                          <input number class="form-control" placeholder="" v-model="periodicPayment.rate"
                            disabled="true">
                        </div>
                      </div>
                    </div>
                    <br>
                      <div class="row">
                        <div class="col-xs-12 text-right">
                          <button type="button" class="btn btn-success" @click="savePeriodicPayment">
                            Guardar
                          </button>
                        </div>
                      </div>
                </div>
              </div>
            </div>
          </div>

          <pre>
            {{$data.estimations | json}}

          </pre>
          </div> <!-- container-fluid -->

      </div> <!-- #contenidos -->
      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
