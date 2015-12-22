<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../assets/css/barralateral.css">
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Presupuestos">

    <jsp:attribute name="scripts">
        <script type="text/javascript">
        new Vue({
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

                    this.obtainYearBudget();
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
            totalfilas: 1,
            catalogoGrupo: {},
            concept: '',
            concepts: [],
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
            conceptos:
            {
            group: 0,
            area: 0,
            category: 0,
            subcategory: 0,
            dwEnterprise: 0,
            year: 0,
            conceptName: '',
            conceptMonth: [
              {name: 'Enero', month: 1, amountConcept: 0},
              {name: 'Febrero', month: 2 , amountConcept: 0},
              {name: 'Marzo', month: 3, amountConcept: 0},
              {name: 'Abril', month: 4, amountConcept: 0},
              {name: 'Mayo', month: 5, amountConcept: 0},
              {name: 'Junio', month: 6, amountConcept: 0},
              {name: 'Julio', month: 7, amountConcept: 0},
              {name: 'Agosto', month: 8 , amountConcept: 0},
              {name: 'Septiembre', month: 9, amountConcept: 0},
              {name: 'Octubre', month: 10, amountConcept: 0},
              {name: 'Noviembre', month: 11, amountConcept: 0},
              {name: 'Diciembre', month: 12, amountConcept: 0}
            ],
            total: 0
            }
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
            },
            prepareList: function(event)
            {
              $(event.target).toggleClass('expanded');
              $(event.target).children('ul').toggle('medium');
              event.stopPropagation();
              this.navigation(event.target.id);
            },
            navigation: function(key)
            {
              var res = key.split("-");
              this.conceptos.group= res[0];
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
                          this.contenido = this.groupBy(data, function (item)
                          {
                              return item.idBudgetCategory;
                          });
                          this.banderacontenido= true;
                      });
            },
            guardarConcepto: function()
            {
              this.concepts.push({
                concepto: this.concept,
                monto: 1800
              });
            },
            seteoInfo: function(idDwEnterprise, idarea, idBudgetCategory, idBudgetSubcategory)
            {
              this.conceptos.dwEnterprise = idDwEnterprise;
              this.conceptos.area= idarea;
              this.conceptos.category= idBudgetCategory;
              this.conceptos.subcategory= idBudgetSubcategory;
            //  alert(idDwEnterprise + " " + idarea+ " " + idBudgetCategory+ " " + idBudgetSubcategory);
          },
          obtainYearBudget: function()
          {
              var date= new Date();
              this.conceptos.year= parseInt(date.getFullYear());
          },
          aumentofilas: function()
          {
            var totalfilass= this.totalfilas;
            this.totalfilas= totalfilass + 1;
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
                <ul class="sidebar-nav">
                  <li id="1" @click="prepareList($event)">BIDGroup
                  <ul v-if="bandera1ernivel">
                    <li class="firstlevel collapsed" v-for="item in arbolNiveles" @click="prepareList($event)" id="1-{{item[0][0].idArea}}">
                      {{item[0][0].idArea | areaName}}
                      <ul v-if="bandera2donivel" id="u{{item[0][0].idArea}}">
                        <li class="secondlevel" v-for="items in item" @click="prepareList($event)" id="1-{{item[0][0].idArea}}-{{items[0].idBudgetCategory}}">
                          {{items[0].idBudgetCategory | budgetCategory}}
                          <ul v-if="bandera3ernivel" id="u{{item[0][0].idArea}}-{{items[0].idBudgetCategory}}">
                            <li class="thirdlevel collapsed" v-for="itemss in items" id="1-{{item[0][0].idArea}}-{{items[0].idBudgetCategory}}-{{itemss.idBudgetSubcategory}}">
                              {{itemss.idBudgetSubcategory | BudgetSubcategory}}
                            </li>
                          </ul>
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

                  <div class="row">
                    <div class="col-xs-6">
                      <h2 style="font-weight: bold">BID GROUP<small>&nbsp;CORPORATIVO</small></h2>
                    </div>
                    <div class="col-xs-6 text-right">
                      <h3>ADMINISTRACION</h3>
                    </div>
                  </div>
                  <hr>

                      <div class="row">
                        <div class="col-xs-12">
                          <div class="bs-callout bs-callout-default">
                          <h4>Nomina</h4>
                          <div class="row">
                            <div class="col-xs-4">
                              <h5>Nomina Administrativa</h5>
                              <div class="input-group">
                                <span class="input-group-addon">$</span>
                                <input type="text" class="form-control" placeholder="" disabled="true">
                              </div>
                            </div>
                            <div class="col-xs-8 text-right">
                                <button type="button" class="btn btn-default" @click="aumentofilas">
                                  <span class="glyphicon glyphicon-plus"></span>
                                </button>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-xs-12">
                              <table class="table table-bordered" style="font-size: 12px">
                                <thead>
                                  <th>
                                    Concepto
                                  </th>
                                  <th v-for="mes in conceptos.conceptMonth">
                                    {{mes.name}}
                                  </th>
                                  <th>
                                    Total
                                  </th>
                                </thead>
                                <tbody>
                                  <tr v-for="vari in totalfilas">
                                    <td v-for="var in 14">
                                        <input type="text" class="form-control input-sm" placeholder="">
                                    </td>
                                  </tr>
                                </tbody>
                              </table>
                            </div>
                          </div>
                        </div>
                      </div>

                  </div>
                  </div>
                  <pre>
                    {{$data.totalfilas}}
                  </pre>
                </div> <!-- /#container-fluid -->
            </div> <!-- /#Page Content -->
        </div> <!-- /#wrapper -->
      </div> <!-- #contenidos -->

    </jsp:body>
</t:template>
