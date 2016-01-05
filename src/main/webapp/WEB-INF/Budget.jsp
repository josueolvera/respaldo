<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../assets/css/barralateral.css">
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
            this.$http.get("http://localhost:8080/BIDGroup/areas")
                    .success(function (data)
                    {
                       this.catalogoAreas= data;
                       this.bandera1ernivel= true;
                    });

            this.$http.get("http://localhost:8080/BIDGroup/budget-categories")
                    .success(function (data)
                    {
                       this.catalogoRubros= data;
                       this.bandera2donivel= true;
                    });

            this.$http.get("http://localhost:8080/BIDGroup/budget-subcategories")
                    .success(function (data)
                    {
                       this.catalogoSubRubros= data;
                       this.bandera3ernivel= true;
                    });

            this.$http.get("http://localhost:8080/BIDGroup/distributors")
                    .success(function (data)
                    {
                       this.catalogoDistribuidor= data;
                       //this.bandera3ernivel= true;
                    });

            this.$http.get("http://localhost:8080/BIDGroup/branchs")
                    .success(function (data)
                    {
                       this.catalogoSucursales= data;
                       //this.bandera3ernivel= true;
                    });
            this.$http.get("http://localhost:8080/BIDGroup/groups")
                    .success(function (data)
                    {
                       this.catalogoGrupo= data;
                       //this.bandera3ernivel= true;
                    });
          },
          ready: function ()
          {
              this.$http.get("http://localhost:8080/BIDGroup/groups/1")
                      .success(function (data) {
                        // this.sinagrupar= data;
                         var self= this;
                          var agrupados = this.groupBy(data.budgetsList, function (item)
                          {
                              return item.idArea;
                          });

                          this.arbolNiveles = agrupados.map(function(ele)
                          {
                            return self.groupBy(ele, function(item)
                            {
                            return item.idBudgetCategory;
                            });
                          });
                      });
          },
          data: {
            meses: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
            'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            catalogoGrupo: {},
            datosPresupuesto: {},
            catalogoDistribuidor: {},
            catalogoRegion: {},
            catalogoSucursales: {},
            catalogoAreas: {},
            catalogoRubros: {},
            catalogoSubRubros: {},
            arbolNiveles: {},
            contenido: {},
            sucursales: {},
            flag: true,
            bandera1ernivel: false,
            bandera2donivel: false,
            bandera3ernivel: false,
            banderacontenido: false,
            group: 0,
            lastkeysearch: '',
            group: '',
            area: '',
            totalArea: ''
          },
          methods:
          {
            groupBy: function (array, filter)
            {
                var self = this;
                var groups = {};
                array.forEach(function (element)
                {
                    var group = JSON.stringify(filter(element));
                    groups[group] = groups[group] || [];
                    groups[group].push(element);
                });
                return Object.keys(groups).map(function (group) {
                    return groups[group];
                });
            }
            ,
            prepareList: function(event)
            {
              $(event.target).toggleClass('expanded');
              $(event.target).children('ul').toggle('medium');
              event.stopPropagation();
              this.navigation(event.target.id);
            },
            prepareList1: function(event)
            {
              $(event.target).toggleClass('expanded');
              $(event.target).children('ul').toggle('medium');
              var ids= event.target.id.substr(2, event.target.id.length);
              var p = $("#"+ids);
              var position = p.position();
              var posicion= position.top;
              $("#page-content-wrapper").scrollTop(posicion);
              event.stopPropagation();
            }
            ,
            navigation: function(key)
            {
              var ids= key.substr(2, key.length);
              var res = ids.split("-");

              if (this.lastkeysearch !== key)
              {
                this.$http.get("http://localhost:8080/BIDGroup/dw-enterprises/"+res[0]+"/"+res[1])
                        .success(function (data)
                        {
                           var self= this;
                           var agrupados = this.groupBy(data, function (item)
                           {
                              return item.iddistributor;
                           });

                           this.sucursales = agrupados.map(function(ele)
                           {
                             return self.groupBy(ele, function(item)
                             {
                             return item.idregion;
                             });
                           });
                        });

                        this.$http.get("http://localhost:8080/BIDGroup/budgets/"+res[0]+"/"+res[1])
                                .success(function (data)
                                {
                                  this.contenido = data;
                                  this.searchConcepts(res[0], res[1]);
                                });
                      this.group = res[0];
                      this.area = res[1];
                      this.lastkeysearch = key;
              }


            },
            createConcept: function ()
            {
                  var objeto=
                          {
                          idConcept: 0,
                          idBudget: 0,
                          dwEnterprise: 0,
                          year: 2015,
                          conceptName: '',
                          conceptMonth: [
                            {name: 'Enero', month: 1, amountConcept: ''},
                            {name: 'Febrero', month: 2 , amountConcept: ''},
                            {name: 'Marzo', month: 3, amountConcept: ''},
                            {name: 'Abril', month: 4, amountConcept: ''},
                            {name: 'Mayo', month: 5, amountConcept: ''},
                            {name: 'Junio', month: 6, amountConcept: ''},
                            {name: 'Julio', month: 7, amountConcept: ''},
                            {name: 'Agosto', month: 8 , amountConcept: ''},
                            {name: 'Septiembre', month: 9, amountConcept: ''},
                            {name: 'Octubre', month: 10, amountConcept: ''},
                            {name: 'Noviembre', month: 11, amountConcept: ''},
                            {name: 'Diciembre', month: 12, amountConcept: ''}
                          ],
                          total: 0,
                          equals: false
                        };
                        return objeto;
            },
            createTotalMonths: function()
            {
              var objeto=
                       [
                        {month: 1, montoConcept: ''},
                        {month: 2 ,montoConcept: ''},
                        {month: 3, montoConcept: ''},
                        {month: 4, montoConcept: ''},
                        {month: 5, montoConcept: ''},
                        {month: 6, montoConcept: ''},
                        {month: 7, montoConcept: ''},
                        {month: 8 ,montoConcept: ''},
                        {month: 9, montoConcept: ''},
                        {month: 10,montoConcept: ''},
                        {month: 11,montoConcept: ''},
                        {month: 12,montoConcept: ''}
                      ];
                    return objeto;
            },
              searchConcepts: function(group, area)
              {
                var self= this;
                this.$http.get("http://localhost:8080/BIDGroup/budget-concepts/group-area/"+group+"/"+area)
                        .success(function (data)
                        {
                          this.datosPresupuesto = data;

                          $.each(this.datosPresupuesto, function(index, el)
                          {
                            $.each(el.conceptos, function(indexs, ele)
                            {
                                $.each(ele.conceptMonth, function(indexss, elem)
                                {
                                  self.moneyFormat(elem, ele, el);
                                });
                            });
                          });
                          this.mixedArrays();
                        });
              }
            ,
              seteoInfo: function(idDwEnterprise, budget, event)
            {
              event.preventDefault();
              var concepto= this.createConcept();
              var totalMeses= this.createTotalMonths();
              concepto.dwEnterprise = idDwEnterprise;
              concepto.idBudget= budget.idBudget;
            //  this.arrayConcepts.push(concepto);
             if (! budget.conceptos)
             {
               Vue.set(budget,"conceptos", []);
               Vue.set(budget,"granTotal", '');
               Vue.set(budget,"totalMonth", totalMeses);
               //budget.totalMonth.push(totalMeses);
             }
             budget.conceptos.push(concepto);
          },
            mixedArrays: function()
            {
                var self= this;
                $.each(this.contenido, function(index, el)
                {
                  var BudgetTem = el;
                  $.each(self.datosPresupuesto, function(indexs, ele)
                  {
                    if (BudgetTem.idBudget == ele.idBudget)
                    {
                      self.contenido.$remove(BudgetTem);
                      self.contenido.push(ele);
                    }
                  });
                });
                self.groupArray();
            }
           ,
           groupArray: function()
           {
             this.contenido = this.groupBy(this.contenido, function (item)
             {
                return item.idBudgetCategory;
             });
             this.banderacontenido = true;
             this.obtainGranTotal();
           }
           ,
          deleteObject: function(budget, concepto)
          {
            if (concepto.idConcept> 0)
            {
              this.$http.delete("http://localhost:8080/BIDGroup/budget-concepts/"+concepto.idConcept)
                      .success(function (data)
                      {
                        showAlert(data);
                        this.$http.get("http://localhost:8080/BIDGroup/budgets/"+this.group+"/"+this.area)
                                .success(function (data)
                                {
                                  this.contenido = data;
                                  this.searchConcepts(this.group, this.area);
                                });
                      });
            }
            else{
              budget.conceptos.$remove(concepto);
              this.obtainTotalConcept(concepto, budget);
            }
          },
          moneyFormat: function(mes, concepto, budget)
          {
            var total= accounting.formatNumber(mes.amountConcept);
            mes.amountConcept= '$'+total;
            this.obtainTotalConcept(concepto, budget);
          },
          equalsImport: function(concepto, budget)
          {
            if (concepto.equals)
            {
              if (concepto.conceptMonth[0].amountConcept)
              {

                $.each(concepto.conceptMonth, function(index, el)
                {
                  el.amountConcept= concepto.conceptMonth[0].amountConcept;
                });

              }
              else{
                alert("Debes ingresar un monto en el primer mes para esta acción");
                concepto.equals= false;
              }
            }
            else
            {
              $.each(concepto.conceptMonth, function(index, el)
              {
                if (el.month> 1)
                {
                    el.amountConcept= '';
                }
                else{
                  concepto.total= accounting.unformat(el.amountConcept);
                }
              });
            }
            this.obtainTotalConcept(concepto, budget);
          },
          obtainTotalConcept: function(concepto, budget)
          {
            concepto.total= 0;
            budget.granTotal= 0;

            $.each(budget.totalMonth, function(index, elemento)
            {
              elemento.montoConcept='';
              var totalMes=0;
              var key= index;
              $.each(budget.conceptos, function(index, element)
              {
                 totalMes+=accounting.unformat(element.conceptMonth[key].amountConcept);
              });
              elemento.montoConcept= "$"+accounting.formatNumber(totalMes);
            });


            $.each(concepto.conceptMonth, function(index, el)
            {
              var totals= accounting.unformat(el.amountConcept);
              concepto.total+= parseInt(totals);
            });

            concepto.total= accounting.formatNumber(concepto.total);

            $.each(budget.conceptos, function(index, el)
            {
              var total= accounting.unformat(el.total);
              budget.granTotal += total;
            });

          budget.granTotal= accounting.formatNumber(budget.granTotal);
          //this.totalArea += budget.granTotal;

        },
        obtainGranTotal: function()
        {
          this.totalArea= 0;
          var self= this;
          vm.contenido.forEach(function(budgetagrupado){
            budgetagrupado.forEach(function(budget){
              if ( typeof budget.granTotal != "undefined")
              {
                var granTotal= accounting.unformat(budget.granTotal);
                alert(granTotal);
                self.totalArea += granTotal;
              }
            });
          });
          this.totalArea = accounting.formatNumber(this.totalArea);
        }
        ,
        saveBudget: function(eventoconcepto)
        {
          var self= this;
          $.ajax({
          url: "http://localhost:8080/BIDGroup/budget-month-concepts",
          type: "POST",
          data: JSON.stringify(eventoconcepto),
          contentType: "application/json",
          success: function()
          {
            showAlert("Concepto almacenado con exito");

            self.$http.get("http://localhost:8080/BIDGroup/budgets/"+self.group+"/"+self.area)
                    .success(function (data)
                    {
                      self.contenido = data;
                      self.searchConcepts(self.group, self.area);
                    });
          },
          error: function()
          {
            alert("Ha habido un error con tu solicitud, intenta nuevamente");
          }
        });
        }
        },
        filters: {
          areaName: function (argument)
          {
            var name;
            this.catalogoAreas.forEach(function(elemento)
            {
                if (argument == elemento.idArea)
                {
                  name = elemento.areaName;
                }
            });
            return name;
          },
          budgetCategory: function(argument)
          {
            var name;
            this.catalogoRubros.forEach(function(elemento)
            {
                if (argument == elemento.idBudgetCategory)
                {
                  name = elemento.budgetCategory;
                }
            });
            return name;
          },
          BudgetSubcategory: function(argument)
          {
            var name;
            this.catalogoSubRubros.forEach(function(elemento)
            {
                if (argument == elemento.idBudgetSubcategory)
                {
                  name = elemento.budgetSubcategory;
                }
            });
            return name;
          },
          DistributorFilter: function (argument)
          {
            var name;
            this.catalogoDistribuidor.forEach(function(elemento)
            {
                if (argument == elemento.idDistributor)
                {
                  name = elemento.distributorName;
                }
            });
            return name;
          },
          SucursalFilter: function (argument)
          {
            var name;
            this.catalogoSucursales.forEach(function(elemento)
            {
                if (argument == elemento.idBranch)
                {
                  name = elemento.branchShort;
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
        <div id="wrapper">
            <!-- Sidebar -->
            <div id="sidebar-wrapper">
                <ul class="sidebar-nav" >
                  <li id="o-1" @click="prepareList($event)">BIDGroup
                  <ul v-if="bandera1ernivel">
                    <li class="firstlevel collapsed" v-for="item in arbolNiveles" @click="prepareList($event)" id="o-1-{{item[0][0].idArea}}">
                      {{item[0][0].idArea | areaName}}
                      <ul v-if="bandera2donivel" id="u{{item[0][0].idArea}}" style="display: none">
                        <li class="secondlevel" v-for="items in item" @click="prepareList1($event)"
                          id="o-1-{{item[0][0].idArea}}-{{items[0].idBudgetCategory}}">
                          {{items[0].idBudgetCategory | budgetCategory}}
                        </li>
                      </ul>
                    </li>

                  </ul>
                  </li>
                </ul> <!-- /#sidebar-nav -->
            </div>
            <!-- /#sidebar-wrapper -->

            <!-- Page Content -->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                  <div class="row" v-for="suc in sucursales" v-if="banderacontenido">
                    <div class="col-xs-12">
                    <div class="row" v-for="sucs in suc">
                      <div class="row" v-for="sucss in sucs" style="margin-left: 0px">

                        <div class="row" style="margin-left: 0px; margin-right: 0px">
                          <div class="col-xs-6 text-left">
                            <h2 style="font-weight: bold">{{sucss.iddistributor | DistributorFilter}}<small>&nbsp;{{sucss.idbranch | SucursalFilter}}</small></h2>
                          </div>
                          <div class="col-xs-6 text-right">
                            <h3>{{sucss.idarea | areaName}}</h3>

                            <div class="col-xs-6 col-xs-offset-6">
                              <div class="input-group">
                                <span class="input-group-addon">$</span>
                                <input type="text" class="form-control" disabled="true" v-model="totalArea">
                              </div>
                            </div>
                          </div>
                        </div>

                        <hr>
                      <div class="row" v-for="cont in contenido" style="margin-left: 0px; margin-right: 0px" id="1-{{sucss.idarea}}-{{cont[0].idBudgetCategory}}">
                        <div class="col-xs-12">
                          <div class="bs-callout bs-callout-default">
                          <h4>{{cont[0].idBudgetCategory | budgetCategory }}</h4>
                          <div class="row" v-for="conte in cont" id="1-{{sucss.idarea}}-{{cont[0].idBudgetCategory}}-{{conte.idBudgetSubcategory}}"
                             style="margin-left: 0px; margin-right: 0px">
                            <div class="row" style="margin-left: 0px; margin-right: 0px">
                              <div class="col-xs-4">
                                <h5>{{conte.idBudgetSubcategory | BudgetSubcategory }}</h5>
                                <div class="input-group">
                                  <span class="input-group-addon">$</span>
                                  <input type="text" class="form-control" placeholder="" disabled="true" v-model=conte.granTotal>
                                </div>
                              </div>
                              <div class="col-xs-1 text-left">
                                <button type="button" class="btn btn-default" id="g-{{cont[0].idBudgetCategory}}-{{conte.idBudgetSubcategory}}"
                                   @click="seteoInfo(sucss.idDwEnterprise, conte, $event)"
                                   style="margin-top: 40px">
                                  <span class="glyphicon glyphicon-plus"></span>
                                </button>
                              </div>
                              <div class="col-xs-6 text left" v-if="conte.conceptos.length > 0">
                                <button type="button" class="btn btn-default" id="s-{{cont[0].idBudgetCategory}}-{{conte.idBudgetSubcategory}}"
                                   @click="saveBudget(conte.conceptos)" style="margin-top: 40px">
                                  <span class="glyphicon glyphicon-floppy-disk"></span>
                                </button>
                              </div>
                            </div>
                            <br>
                            <div class="row" style="margin-left: 0px" v-if="conte.conceptos.length > 0">
                              <div class="col-xs-2" style="padding-left: 0px; padding-right: 1px">
                                <label>
                                  Concepto
                                </label>
                              </div>
                              <div class="col-xs-9">
                                <div class="col-xs-1" v-for="mes in meses"
                                  style="padding-left: 0px; padding-right: 1px">
                                  <label>
                                    {{mes}}
                                  </label>
                                </div>
                              </div>
                              <div class="col-xs-1" style="padding-left: 0px; padding-right: 0px">
                                <div class="col-xs-4" style="padding-left: 0px; padding-right: 0px">
                                  <label>
                                    Total
                                  </label>
                                </div>
                                <div class="col-xs-4" style="padding-left: 0px; padding-right: 0px">

                                </div>
                              </div>
                            </div>

                            <div class="row" style="margin-left: 0px" v-for="concepto in conte.conceptos">
                              <div class="col-xs-2" style="padding-left: 0px; padding-right: 1px">
                                <input type="text" name="name" class="form-control input-sm" style="font-size: 10px" v-model="concepto.conceptName">
                              </div>
                              <div class="col-xs-9">
                                <div class="col-xs-1" v-for="mess in concepto.conceptMonth"
                                  style="padding-left: 0px; padding-right: 1px">
                                    <input type="text" class="form-control input-sm" placeholder=""
                                      id="{{mess.month}}" v-model="mess.amountConcept" @change="moneyFormat(mess, concepto, conte)"
                                      style="font-size: 10px" onkeypress="return isNumberKey(event)">
                                </div>
                              </div>
                              <div class="col-xs-1" style="padding-left: 0px; padding-right: 0px">
                                <div class="col-xs-4" style="padding-left: 0px; padding-right: 0px">
                                  <label style="font-size: 9px; margin-top: 6px">
                                    {{concepto.total}}
                                  </label>
                                </div>
                                <div class="col-xs-4" style="padding-left: 0px; padding-right: 0px">
                                  <button type="button" class="btn btn-link"
                                     @click="deleteObject(conte, concepto)">
                                    <span class="glyphicon glyphicon-minus"></span>
                                  </button>
                                </div>
                                <div class="col-xs-4 text-right">
                                  <div class="checkbox">
                                    <input type="checkbox" value="" style="margin-top: 1px" v-model="concepto.equals" @change="equalsImport(concepto, conte)">
                                  </div>
                                </div>
                              </div>
                            </div>
                            <br>

                              <div class="row" style="margin-left: 0px" v-if="conte.conceptos.length > 0">
                                <div class="col-xs-2 text-right">
                                  <label>
                                    Total:
                                  </label>
                                </div>

                                <div class="col-xs-9">
                                  <div class="col-xs-1 text-center" v-for="totalmes in conte.totalMonth"
                                    style="padding-left: 0px; padding-right: 1px">
                                    <label>
                                      {{totalmes.montoConcept}}
                                    </label>
                                  </div>
                                </div>

                                <div class="col-xs-1 text-left" style="padding-left: 0px; padding-right: 1px">
                                  <label>
                                    $ {{conte.granTotal}}
                                  </label>
                                </div>
                              </div>

                          </div>
                          </div>
                        </div>
                      </div>
                      </div>
                    </div>
                  </div>
                </div>
                <pre>
                  {{$data.contenido | json}}
                </pre>
                </div> <!-- /#container-fluid -->
            </div> <!-- /#Page Content -->
        </div> <!-- /#wrapper -->
      </div> <!-- #contenidos -->
    </jsp:body>
</t:template>