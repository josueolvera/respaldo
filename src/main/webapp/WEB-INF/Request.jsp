<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Solicitudes">
    <jsp:attribute name="scripts">

        <script type="text/javascript">
          var vm= new Vue({
          el: '#contenidos',
          created: function(){

          },
          ready: function ()
          {
            this.obtainRequestInfo();

          },
          data:
          {
            requestCategories: {}
          },
          methods:
          {
            obtainRequestInfo: function()
            {
              this.$http.get(ROOT_URL+"/request-categories")
              .success(function (data)
              {
                this.requestCategories= data;
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
            <br>
            <div class="row">
              <div class="col-xs-4 col-xs-offset-3">

                <div class="list-group" v-for="requestCategorie in requestCategories">
                    <a href="../{{requestCategorie.idView.cTasks.taskName}}?cat={{requestCategorie.idRequestCategorie}}" class="list-group-item">
                    <h4 class="list-group-item-heading">{{requestCategorie.category}}</h4>
                    <p class="list-group-item-text">
                      {{{requestCategorie.information}}}
                    </p>
                  </a>
                </div>

              </div>
            </div>
            <br>
          </div>
      </div> <!-- #contenidos -->
    </jsp:body>
</t:template>
