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

          },
          data:
          {

          },
          methods:
          {



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

                <div class="list-group">
                    <a href="#" class="list-group-item">
                    <h4 class="list-group-item-heading">List group item heading</h4>
                    <p class="list-group-item-text">...</p>
                  </a>
                </div>

              </div>
            </div>

          </div>
      </div> <!-- #contenidos -->
    </jsp:body>
</t:template>
