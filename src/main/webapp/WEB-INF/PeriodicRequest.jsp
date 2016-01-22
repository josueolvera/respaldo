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
            this.obtainAllUsers();
            this.idRequestCategory= this.getGet();
            this.request.idRequestCategory = this.idRequestCategory.cat;

            this.$http.get(ROOT_URL+"/request-types/request-category/"+this.idRequestCategory.cat)
                    .success(function (data)
                    {
                       this.RequestTypes= data;
                    });
          },
          data:
          {
            request:
            {
              idRequestCategory: '',
              idRequestType: '',
              idProductType: '',
              description: '',
              purpose: '',
              products: [],
              idUser: ''
            },
            idRequestCategory: '',
            optionSelect: [],
            RequestTypes: {},
            ProductTypes: {},
            Productos: {},
            Users: {},
            idProducto: '',
            desactivarCombos: false,
            desactivarGuardar: true,
            numberOfRequest: 0
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
              this.$http.get(ROOT_URL+"/product-types/request-category-type/"+this.idRequestCategory.cat+"/"+this.request.idRequestType)
                      .success(function (data)
                      {
                         this.ProductTypes= data;
                      });

            },
            obtainProducts: function()
            {
              this.Productos= {};
              this.$http.get(ROOT_URL+"/products/product-type/"+this.request.idProductType)
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
                  self.request.products.push(producto);
                }
              });
              if (this.request.products.length> 0)
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
                this.request.products.$remove(product);
                if (this.request.products.length == 0)
                {
                  this.desactivarCombos= false;
                  this.desactivarGuardar = true;
                }
            },
            saveRequest: function(event)
            {
              this.request.idUser= this.optionSelect[0].selectize.getValue();
              this.$http.post(ROOT_URL+"/requests", JSON.stringify(this.request)).
              success(function(data)
              {
                showAlert(data);
              }).error(function(data)
              {
                showAlert("No se pudo");
              });
            },
            obtainAllUsers: function()
            {
             this.$http.get(ROOT_URL + "/users").success(function (data)
              {
                this.optionSelect = $('#select_responsable').selectize({
                   create: false,
                   sortField: 'text',
                   searchField: 'mail',
                   valueField: 'idUser',
						       labelField: 'username',
                   options: data
                 });
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

          <div class="container-fluid">

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
               <select class="form-control" v-model="request.idRequestType" :disabled="desactivarCombos" @change="obtainProductType" required>
                 <option v-for="RequestType in RequestTypes"
                   value="{{RequestType.idRequestType}}">{{RequestType.requestType}}
                 </option>
               </select>
              </div>

              <div class="col-xs-2">
                <label>
                  Tipo de producto
                </label>
                <select class="form-control" v-model="request.idProductType" :disabled="desactivarCombos"
                  @change="obtainProducts" required>
                  <option v-for="ProductType in ProductTypes" value="{{ProductType.idProductType}}">
                    {{ProductType.productType}}
                  </option>
                </select>
              </div>

              <div class="col-xs-2">
                <label>
                  Producto
                </label>
                <select class="form-control" v-model="idProducto" id="selectProducto" required>
                  <option v-for="Product in Productos" value="{{Product.idProduct}}">
                    {{Product.product}}
                  </option>
                </select>
              </div>

              <div class="col-xs-1">
                <div class="col-xs-6">
                  <button type="button" class="btn btn-default" style="margin-top: 25px; margin-left: -33px" v-on:click="saveProduct">
                    <span class="glyphicon glyphicon-plus"></span>
                  </button>
                </div>
              </div>

              <!--
             <div class="col-xs-2">
               <label>
                 Duración:
               </label>
               <select class="form-control" name="" id="tipoArticulo">
                 <option value="1">1 Mes</option>
                 <option value="0">Otro..</option>
               </select>
             </div>

             <div class="col-xs-2">
               <label>
                 Tipo de Pago:
               </label>
               <select class="form-control" name="" id="tipoArticulo">
                 <option value="1">Semanal</option>
                 <option value="2">Quincenal</option>
                 <option value="3">Mensual</option>
                 <option value="4">Bimestral</option>
               </select>
             </div>

           -->
             <div class="col-xs-2">
               <label>
                 Responsable:
               </label>
               <select class="form-control" id="select_responsable">

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

              <div class="row" v-for="produc in request.products">
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
                <textarea class="form-control" rows="3" cols="50" v-model="request.description" required></textarea>
              </div>
            </div>
            <br>
            <div class="row">
              <div class="col-xs-12">
                <label>
                  Motivo de Contratacion:
                </label>
                <textarea class="form-control" rows="3" cols="50" v-model="request.purpose" required></textarea>
              </div>
            </div>

            <br>
            <div class="row">
              <div class="col-xs-12 text-right">
                <button class="btn btn-success" :disabled="desactivarGuardar">Guardar Solicitud</button>
              </div>
            </div>

          </form>
          <br>
          <div class="row">
            <div class="col-xs-12">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h3 class="panel-title">Cotizacion</h3>
                </div>
                <div class="panel-body">

                </div>
              </div>
            </div>
          </div>

          </div> <!-- container-fluid -->

      </div> <!-- #contenidos -->
      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
