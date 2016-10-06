<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Agenda">
    <jsp:attribute name="scripts">

        <script type="text/javascript">
          var vm= new Vue({
          el: '#contenidos',
          created: function(){
              
          },
          ready: function ()
          {
              this.getUser();
          },
          data: {
              user:{},
              rooms:[],
              roomUrl: ROOT_URL + '/agenda/rooms?room='
          },
          methods:
          {
              getRooms: function (idRole) {
                  this.$http.get(ROOT_URL + '/rooms?role=' + idRole)
                          .success(function (data) {
                              this.rooms = data
                  })
                  .error(function (data) {
                      
                  });
              },
              getUser: function () {
                  this.$http.get(ROOT_URL + '/user')
                          .success(function (data) {
                              this.user = data;
                      this.getRooms(this.user.dwEmployee.idRole);
                  })
                  .error(function (data) {
                      
                  });
              }
          }
        });

        </script>
    </jsp:attribute>
        
    <jsp:attribute name="styles">
        <style>
            /* centered columns styles */
            .row-centered {
                text-align:center;
            }
            .col-centered {
                display:inline-block;
                float:none;
                /* reset the text-align */
                text-align:left;
                /* inline-block space fix */
                margin-right:-4px;
            }
        </style>
    </jsp:attribute>

    <jsp:body>
      <div id="contenidos">
          <div class="container-fluid">
            <br>
            <div class="row">
                  <div class="col-md-12">
                      <h2>Administración de salas de juntas</h2>
                      <br>
                      <div class="row row-centered" >
                        <div class="col-md-3 col-centered" v-for="room in rooms">
                            <div class="thumbnail" style="background-color: #f5f5f5">
                            <img class="img-thumbnail" :src= "room.imageurl">
                            <div class="caption text-center">
                              <h3>{{room.name}}</h3>
                             <p><a :href="roomUrl + room.idRoom" class="btn btn-primary">Reservar</a> </p>
                            </div>
                          </div>
                        </div>
                       </div>
                  </div>
                </div>
          </div>
      </div> <!-- #contenidos -->
    </jsp:body>
</t:template>
