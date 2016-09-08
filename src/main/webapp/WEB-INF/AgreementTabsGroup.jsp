<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>


<t:template pageTitle="BID Group: Tabuladores por grupo de convenios">

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
            function isNumberKeyAndLetterKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode === 13 ||
                        (charCode > 64 && charCode < 91) ||
                        (charCode > 47 && charCode < 58) ||
                        (charCode > 96 && charCode < 123) ||
                        charCode === 8
                ) {
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
                    this.getAgreementGroups();
                },
                data: {
                    agreementGroups: {},
                    idAgreementGroup: 0,
                    tabsOfGroup: {}
                },
                methods: {
                    getAgreementGroups: function(){
                        this.$http.get(ROOT_URL+"/c-agreements-groups/actives")
                          .success(function (data)
                          {
                             this.agreementGroups= data;
                          });
                    },
                    getTabsOfGroup: function(){
                        this.$http.get(ROOT_URL+"/agreement-condition/"+this.idAgreementGroup)
                          .success(function (data)
                          {
                             this.tabsOfGroup= data;
                          });
                    },
                    updateTab: function(tab){
                        console.log(tab);
                    }
                },
                filters: {

                }
            });


        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos">

            <div class="row">
              <div class="col-xs-12 text-center">
                <h1>Gestión de Tabuladores de Grupos</h1>
              </div>
            </div>

            <br>
            <div class="row">
              <div class="col-xs-3 text-left">
                <label>
                    Grupo de Convenio
                </label>
                <select class="form-control" v-model="idAgreementGroup" @change="getTabsOfGroup">
                    <option value="0"></option>
                    <option v-for="agreementGroup in agreementGroups" value="{{agreementGroup.idAg}}">
                        {{agreementGroup.agreementGroupName}}
                    </option>
                </select>
              </div>
            </div>
            <br>

            <div class="row" v-if="idAgreementGroup > 0">
              <div class="col-xs-3">
                <label>
                Monto minimo
                </label>
                <div class="input-group">
                  <span class="input-group-addon">$</span>
                  <input type="text" class="form-control" placeholder="">
                </div>
              </div>

              <div class="col-xs-3">
                <label>
                Monto máximo
                </label>
                <div class="input-group">
                  <span class="input-group-addon">$</span>
                  <input type="text" class="form-control" placeholder="">
                </div>
              </div>

              <div class="col-xs-3">
                <label>
                Tabulador
                </label>
                <div class="input-group">
                  <span class="input-group-addon">%</span>
                  <input type="text" class="form-control" placeholder="">
                </div>
              </div>

              <div class="col-xs-3 text-left" style="margin-top: 25px">
                  <button class="btn btn-default">
                      <span class="glyphicon glyphicon-plus"></span>
                  </button>
              </div>
            </div>

            <div class="row">
              <div class="col-xs-12">
                  <h3>Tabuladores Existentes</h3>
              </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                  <table class="table table-striped">
                      <thead>
                          <th>
                              #
                          </th>
                          <th>
                              Monto minimo
                          </th>
                          <th>
                              Monto maximo
                          </th>
                          <th>
                              Tabulador
                          </th>
                          <th>
                              Estatus
                          </th>
                      </thead>
                      <tbody>
                          <tr v-for="tab in tabsOfGroup">
                              <td>
                                  {{$index + 1}}
                              </td>
                              <td>
                                  {{tab.amountMin}}
                              </td>
                              <td>
                                  {{tab.amountMax}}
                              </td>
                              <td>
                                  {{tab.tabulator}}
                              </td>
                              <td>
                                  <input type="checkbox" :value="tab" v-model="tab.statusBoolean" @change="updateTab(tab)">
                              </td>
                          </tr>
                      </tbody>
                  </table>
                </div>

            </div>



        </div>
        <!-- #contenidos -->

        <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
