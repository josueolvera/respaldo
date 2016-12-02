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
          created: function() {
              
          },
          ready: function () {
              this.getUser();
              this.getRoom();
              this.getEvents();
          },
          data: {
              user:{},
              now: "${now}",
              idRoom: ${room},
              room:{},
              calendar:null,
              eventsByDay:[],
              selectedDay:'',
              startHourTimePicker:'',
              startHour:'',
              endHourTimePicker:'',
              endHour:'',
              title:'',
              id:''
              
          },
          methods: {
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
              refresh: function(){
                location = self.location;  
              },
              
              getEvents: function () {
                  this.$http.get(ROOT_URL + '/events?room=' + this.idRoom)
                          .success(function (data) {
                              
                              if (this.calendar == null) {
                                  this.createCalendar(data);
                              } else {
                                 // this.calendar.fullCalendar('removeEventSource');
                              }
                              
                              
                  })
                  .error(function (data) {
                      
                  });
              },
              getEventsByDay: function () {
                  this.$http.get(ROOT_URL + '/events?room=' + this.idRoom + '&day=' + this.selectedDay )
                          .success(function (data) {

                              var jsonObjectIndex = {};
                      
                                data.forEach(function (event) {
                                    if (isNaN(event.user)) {
                                        jsonObjectIndex[event.user._id] = event.user;
                                    } else {
                                        event.user = jsonObjectIndex[event.user];
                                    }
                                });
                                
                              this.eventsByDay = data;
                              
                              $('#reservationModal').modal('show');
                     
                  })
                  .error(function (data) {
                      
                  });
              },
              addReservation: function () {
                  if (this.startHour.length < 5) {
                      this.startHour = '0' + this.startHour;
                  }
                  
                  if (this.endHour.length < 5) {
                      this.endHour = '0' + this.endHour;
                  }
                  
                  var event= {};
                    event.title=  this.title,
                    event.start = this.selectedDay+'T'+this.startHour,
                    event.end = this.selectedDay+'T'+this.endHour,
                    event.idUser = this.user.idUser,
                    event.room = this.room,
                    this.$http.post(ROOT_URL + "/events?room=" + this.idRoom, JSON.stringify(event))
                    .success(function (event) {
                        
                        this.clearEvenData();
                        
                        this.getEventsByDay();
                            showAlert("Reservación Agendada");
                            }).error(function (data) {
                                showAlert(data.error.message, {type: 3});
                            });
                  
              },
              clearEvenData: function () {
                  this.title = '';
                  this.startHour = '';
                  this.endHour = '';
              },
              deleteReservation: function (id) {
                  this.id= id;
                        this.$http.post(ROOT_URL + "/events/" + this.id)
                                .success(function (data) {
                                    
                                    showAlert("Reservación cancelada");
                            this.getEventsByDay();
                            
                                });
                    },
              createCalendar: function (events) {
                  
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
                        eventSources: {
                            events: events,
                            textColor: 'white', // an option!
                            backgroundColor: '#337ab7',
                            borderColor: '#2e6da4'
                        },
                        dayClick: function(date,allDay, jsEvent, view) {
                     
                            var myDate = new Date();

                            //How many days to add from today?
                            var daysToAdd = -2;

                            myDate.setDate(myDate.getDate() + daysToAdd);

                            if (date < myDate) {
                                //TRUE Clicked date smaller than today + daysToadd
                                showAlert("Fecha Invalida",{type: 3});
                            } else {
                                //FLASE Clicked date larger than today + daysToadd
                               self.selectedDay = date.format();
                               self.getEventsByDay();
                            }
                            
                        }
                    });
              },
              activarDateTimePickerStarHour: function (){
                   var fecha = new Date();

                    $('#startHour').datetimepicker({
                           format: 'LT',
                            //minDate: moment({h:fecha.getHours(), m:fecha.getMinutes()})
                        minDate:moment({h:08, m:00})
                       });
              },
              activarDateTimePickerEndHour: function (startHour){
                  $('#endHour').datetimepicker({
                        format: 'LT',
                       minDate: moment(startHour, 'HH:mm').add(1,'minute')

                    });
              }
          },
          filters: {
              hour: function (val) {
                  var date = moment(val);
                  return date.format('hh:mm a');
              },
              date: function (val) {
                  var date = moment(val);
                  return date.format('LL');
              }
          }
        });

        </script>
    </jsp:attribute>
        
    <jsp:attribute name="styles">
        <style>
            #calendar {
                
                background-color: white;
                padding: 10px;
                
                
                border-top-color: #080808
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="contenidos" class="col-md-12 ">
          <div class="container-fluid">
              <h2>Sala de Juntas {{room.name}}</h2>
              <div id="calendar" style=""></div>
              <br>
          </div>
            
          <div class="modal fade" id="reservationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog modal-lg" role="document">
              <div class="modal-content">
                  <form id="attachments-form" method="post" enctype="multipart/form-data"
                      v-on:submit.prevent="addReservation">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title" id="myModalLabel">Reservación para el día <b class="text-primary">{{selectedDay | date}}</b></h4>
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
                                        <th>Motivo</th>
                                        <th>Hora de entrada</th>
                                        <th>Hora de salida</th>
                                        <th></th>
                                      </tr>
                                    </thead>
                                    <tbody>
                                      <tr v-for="event in eventsByDay">
                                        <td>{{event.user.mail}}</td>
                                        <td>{{event.title}}</td>
                                        <td>{{event.start | hour}} </td>
                                        <td>{{event.end | hour}}</td>
                                        <td><button class="btn btn-default" type="button" @click="deleteReservation(event.id)">
                                                    <span class="glyphicon glyphicon-trash"></span></button></td>
                                      </tr>
                                    </tbody>
                                  </table>
                            </div>
                        </div>
                      </div>
                  <!--  <label>Email</label>
                    <br>
                   
                     
                        <span class="label label-default">{{user.mail}}</span>
                    -->
                    <br>
                    <div class="row">
                            <div class='col-md-6'>
                            <div class="form-group">
                                <br>
                                <label>Motivo</label>
                                <input type='text' class="form-control" v-model="title" required/>
                            </div>
                        </div>
                        <div class='col-md-3'>
                            <div class="form-group">
                                <br>
                                <label>Hora de entrada</label>
                                <div class='input-group date' id='startHour'>
                                    <input type='text' class="form-control" v-model="startHour" required/>
                                    <span class="input-group-addon" @click="activarDateTimePickerStarHour()">
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
                                    <input type='text' class="form-control" v-model="endHour" required/>
                                    <span class="input-group-addon" @click="activarDateTimePickerEndHour(startHour)">
                                        <span class="glyphicon glyphicon-time"></span>
                                    </span>
                                </div>
                            </div>
                          </div>
                        
                        
                          </div>
                       </div>
                        
                <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal"@click="refresh">Salir</button>
                  <button type="submit" class="btn btn-primary">Añadir Reservacion</button>
                </div>
                  </form>
              </div>
            </div>
          </div>
      </div>
        
        
        <!-- #contenidos -->
    </jsp:body>
</t:template>