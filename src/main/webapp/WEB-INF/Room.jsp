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
              this.getRoom();
          },
          data: {
              user:{},
              now: "${now}",
              idRoom: ${room},
              room:{},
              calendar:{},
              selectedDay:'',
              startHourTimePicker:'',
              startHour:'',
              endHourTimePicker:'',
              endHour:'',
              title:''
              
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
              getRoom: function () {
                  this.$http.get(ROOT_URL + '/rooms/' + this.idRoom)
                          .success(function (data) {
                              this.room = data;
                  })
                  .error(function (data) {
                      
                  });
              },
              addReservation: function () {
                  var event= {};
                    event.title=  this.title,
                    event.start = this.selectedDay+''+startHour,
                    event.end = this.selectedDay+''+endHour,
                    event.user = this.User,
                    event.room = this.Room,
                    this.$http.post(ROOT_URL + "/events", JSON.stringify(this.employee)).success(function (event) {
                          
                            showAlert("Registro de empleado exitoso");
                            
                            });
                  
                  $('#reservationModal').modal('hide');
                  
              },
              createCalendar: function () {
                  
                  var self = this;
                  
                  this.calendar = $('#calendar').fullCalendar({
                     
                        now: this.now,
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay,listMonth'
                                
			},
                        businessHours: true, // display business hours
			
                        nowIndicator:true,
			navLinks: true, // can click day/week names to navigate views
			eventLimit: true, // allow "more" link when too many events
                        dayClick: function(date, jsEvent, view) {
                            
                            self.selectedDay = date.format();

                            //$(this).css('background-color', 'red');
                            
                            $('#reservationModal').modal('show');
                            self.startHourTimePicker = $('#startHour').datetimepicker({
                                    format: 'LT'
                                });
                            self.endHourTimePicker = $('#endHour').datetimepicker({
                                    format: 'LT'
                                });

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
            
          <div class="modal fade" id="reservationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog modal-lg" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title" id="myModalLabel">Reservación para el día {{selectedDay}}</h4>
                </div>
                <div class="modal-body">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                          <h3 class="panel-title">Sala de juntas {{room.name}}</h3>
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                      <tr>
                                        <th>Email</th>
                                        <th>Título</th>
                                        <th>Hora de entrada</th>
                                        <th>Hora de salida</th>
                                      </tr>
                                    </thead>
                                    <tbody>
                                      <tr v-fo=''>
                                        <td>{{user.mail}}</td>
                                        <td>{{title}}</td>
                                        <td>{{startHour}} </td>
                                        <td>{{endHour}}</td>
                                      </tr>
                                    </tbody>
                                  </table>
                            </div>
                        </div>
                      </div>
                    <label>Email</label>
                    <br>
                    <span class="label label-default">{{user.mail}}</span>
                    <br>
                    <div class="row">
                        <div class='col-md-6'>
                            <div class="form-group">
                                <br>
                                <label>Título</label>
                                <input type='text' class="form-control" v-model={{title}} />
                            </div>
                        </div>
                        <div class='col-md-3'>
                            <div class="form-group">
                                <br>
                                <label>Hora de entrada</label>
                                <div class='input-group date' id='startHour'>
                                    <input type='text' class="form-control" v-model={{startHour}} />
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-time"></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class='col-md-3'>
                            <div class="form-group">
                                <br>
                                <label>Hora de salida</label>
                                <div class='input-group date' id='endHour'>
                                    <input type='text' class="form-control" v-model= {{endHour}}/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-time"></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                        
                        
                    </div>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                  <button type="button" class="btn btn-primary" @click="addReservation">Añadir Reservacion</button>
                </div>
              </div>
            </div>
          </div>
      </div>
        
        
        <!-- #contenidos -->
    </jsp:body>
</t:template>