<%-- 
    Document   : Room
    Created on : 5/10/2016, 12:37:17 PM
    Author     : rubens
--%>

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
              this.createCalendar();
          },
          data: {
              user:{},
              now: "${now}",
              room: ${room},
              calendar:{}
          },
          methods:
          {
              getUser: function () {
                  this.$http.get(ROOT_URL + '/user')
                          .success(function (data) {
                              this.user = data;
                  })
                  .error(function (data) {
                      
                  });
              },
              createCalendar: function () {
                  this.calendar = $('#calendar').fullCalendar({
                     
                        defaultDate: this.now,
                        
                        
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay,listMonth'
                                
			},
                        businessHours: true, // display business hours
			
                        nowIndicator:true,
			navLinks: true, // can click day/week names to navigate views
			eventLimit: true, // allow "more" link when too many events
                        dayClick: function(date, jsEvent, view, resourceObj) {
                             console.log(date);
                             console.log(jsEvent);
                             console.log(view);
                             console.log(resourceObj);
                        }
                    });
              }
          }
        });

        </script>
    </jsp:attribute>
        
    <jsp:attribute name="styles">
        <style>
            #calendar {
                
                background-color: white;
                padding: 80px;
                border-style: solid;
                
                border-top-color: #080808
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos" class="col-md-10 col-md-offset-2">
          <div class="container-fluid">
              <h2>Sala de Juntas</h2>
              <div id="calendar" style=""></div>
              <br>
          </div>
      </div>
        
        
        <!-- #contenidos -->
    </jsp:body>
</t:template>