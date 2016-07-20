<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 05/07/16
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Boletos de avión">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm= new Vue({
                el: '#content',
                ready: function () {
                    this.getPlaneTicketsTypes();
                    this.getTravelCities();
                    this.getPlaneSeatsTypes();
                },
                created: function () {
                    this.getUserInSession();
                    this.setDefaultValues();
                },
                data: {
                    requestBody:{
                        planeTicketType:null,
                        flights:[],
                        passengers:[]
                    },
                    userInSession:null,
                    planeTicketsTypes : [],
                    planeSeatsTypes : [],
                    travelCities : [],
                    flights:[],
                    passengers:[],
                    dateTimePickerReturnDate:'',
                    dateTimePickerDepartureDate:'',
                    dateTimePickerBirthdate:''
                },
                computed : {

                },
                methods: {
                    getUserInSession: function()
                    {
                        this.$http.get(ROOT_URL + "/user").
                        success(function (data)
                        {
                            this.userInSession = data;
                        }).error(function(data)
                        {
                            showAlert("Ha habido un error al obtener al usuario en sesion");
                        });

                    },
                    getPlaneTicketsTypes : function () {
                        this.$http.get(ROOT_URL + '/plane-tickets-types')
                                .success(function (data) {
                                    this.planeTicketsTypes = data;
                                })
                                .error(function (data) {

                                });
                    },
                    getPlaneSeatsTypes : function () {
                        this.$http.get(ROOT_URL + '/plane-seats-types')
                                .success(function (data) {
                                    this.planeSeatsTypes = data;
                                })
                                .error(function (data) {

                                });
                    },
                    getTravelCities : function () {
                        this.$http.get(ROOT_URL + '/travel-cities')
                                .success(function (data) {
                                    this.travelCities = data;
                                })
                                .error(function (data) {

                                });
                    },
                    setDefaultValues: function () {
                        var passenger = {};
                        var flight = {};
                        this.requestBody.passengers = [];
                        this.requestBody.flights = [];
                        this.requestBody.passengers.push(passenger);
                        this.requestBody.flights.push(flight);

                    },
                    activateDateTimePickerDepartureDate: function (index) {

                        var currentDate = new Date();

                        this.dateTimePickerDepartureDate = $('#departureDate' +  index).datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: currentDate
                        }).data();
                    },
                    activateDateTimePickerBirthdate: function (index) {
                        var currentDate = new Date();

                        this.dateTimePickerBirthdate = $('#birthdate' +  index).datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: currentDate
                        }).data();
                    },
                    activateDateTimePickerReturnDate: function () {

                        var currentDate = new Date();

                        this.dateTimePickerReturnDate = $('#returnDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: currentDate
                        }).data();
                    },
                    addPassenger : function () {
                        if (this.requestBody.passengers.length < 100) {
                            var passenger = {};
                            this.requestBody.passengers.push(passenger);
                        }
                    },
                    removePassenger : function (passenger) {
                        if (this.requestBody.passengers.length > 1) {
                            this.requestBody.passengers.$remove(passenger);
                        }
                    },
                    addFlight : function () {
                        if (this.requestBody.flights.length < 10) {
                            var flight = {};
                            this.requestBody.flights.push(flight);
                        }
                    },
                    removeFlight : function (flight) {
                        if (this.requestBody.flights.length > 1) {
                            this.requestBody.flights.$remove(flight);
                        }
                    },
                    planeTicketTypeChanged: function () {
                        var firstFlight = this.requestBody.flights[0];

                        if (this.requestBody.flights.length > 1) {
                            this.requestBody.flights = [];
                            this.requestBody.flights.push(firstFlight);
                        } else if ("returnDate" in firstFlight) {
                            delete firstFlight.returnDate;
                            this.requestBody.flights = [];
                            this.requestBody.flights.push(firstFlight);
                        }
                    },
                    setFile: function (event,passenger) {
                        var reader = new FileReader();
                        var file = event.target.files[0];

                        reader.onload = (function(theFile) {
                            return function(e) {

                                var dataUrl = e.target.result;

                                Vue.set(passenger, "file", {});
                                Vue.set(passenger.file, "name", theFile.name);
                                Vue.set(passenger.file, "size", theFile.size);
                                Vue.set(passenger.file, "type", theFile.type);
                                Vue.set(passenger.file, "dataUrl", e.target.result);
                            };
                        })(file);
                        reader.readAsDataURL(file);
                    },
                    savePlaneTicket : function () {

                        var body = {
                            planeTicketType : this.requestBody.planeTicketType,
                            startDate: this.requestBody.flights[0].departureDate
                        };

                        this.$http.post(ROOT_URL + '/plane-tickets', body)
                                .success(function (data) {
                                    var self = this;

                                    this.requestBody.flights.forEach(function (flight) {
                                        self.saveFlight(flight, data);
                                    });

                                    this.requestBody.passengers.forEach(function (passenger) {
                                        self.savePassenger(passenger, data);
                                    });

                                    this.setDefaultValues();
                                    showAlert("Se ha guardado la solicitud");
                                })
                                .error(function (data) {
                                    this.setDefaultValues();
                                });
                    },
                    saveFlight:function (flight, idPlaneTicket) {
                        this.$http.post(ROOT_URL + '/flights?idPlaneTicket=' + idPlaneTicket, flight)
                                .success(function (data) {

                                })
                                .error(function (data) {});
                    },
                    savePassenger:function (passenger, idPlaneTicket) {
                        var file = passenger.file;
                        delete passenger.file;

                        this.$http.post(ROOT_URL + '/passengers?idPlaneTicket=' + idPlaneTicket, passenger)
                                .success(function (data) {
                                    this.savePassengerDocument(file, data);
                                })
                                .error(function (data) {});
                    },
                    savePassengerDocument:function (file, idPassenger) {
                        this.$http.post(ROOT_URL + '/passenger-documents?idPassenger=' + idPassenger,file)
                                .success(function (data) {})
                                .error(function (data) {});
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
            }
            .table-body .table-row:nth-child(2n+1) {
                background: #ddd;
            }
            .table-row {
                padding: 1rem;
            }
            .flex-content {
                overflow-x: hidden;
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div class="col-md-12" id="content">
            <h2>Boletos de avión</h2>
            <div class="row">
                <div class="col-md-3">
                    <h4>Nueva solicitud</h4>
                    <select class="form-control" v-model="requestBody.planeTicketType" @change="planeTicketTypeChanged">
                        <option :value="planeTicketType" v-for="planeTicketType in planeTicketsTypes">
                            {{planeTicketType.planeTicketName}}
                        </option>
                    </select>
                </div>
                <div class="col-md-5">

                </div>
                <div class="col-md-4 text-right">
                    <h4>Solicitante</h4>
                    <p>
                        <span class="label label-default">{{userInSession.dwEmployee.employee.fullName}}</span>
                    </p>
                    <br>
                </div>
            </div>

            <form v-on:submit.prevent="savePlaneTicket">
                <div class="row" v-if="requestBody.planeTicketType !== null">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-md-6">
                                    <h3 class="panel-title"><strong>Informacón de solicitud</strong></h3>
                                </div>
                                <div v-if="requestBody.planeTicketType.planeTicketName === 'VARIAS CIUDADES'">
                                    <div class="col-md-6 text-right">
                                        <button type="button" class="btn btn-default btn-sm" @click="addFlight">Agregar vuelo</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="row" v-for="flight in requestBody.flights">
                                <div class="col-md-3">
                                    <label>Origen</label>
                                    <div class="form-group">
                                        <select class="form-control" v-model="flight.cityOrigin" required>
                                            <option :value="travelCity" v-for="travelCity in travelCities">{{travelCity.cityName}}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <label>Destino</label>
                                    <div class="form-group">
                                        <select class="form-control" v-model="flight.cityDestination" required>
                                            <option :value="travelCity" v-for="travelCity in travelCities">{{travelCity.cityName}}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <label>Fecha de salida</label>
                                    <div class="form-group">
                                        <div class="input-group date" id="departureDate{{$index}}" @click="activateDateTimePickerDepartureDate($index)">
                                            <input type="text" class="form-control" v-model="flight.departureDate" required>
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                        </div>
                                    </div>
                                </div>
                                <div v-if="requestBody.planeTicketType.planeTicketName === 'IDA Y VUELTA'">
                                    <div class="col-md-3">
                                        <label>Fecha de regreso</label>
                                        <div class="form-group">
                                            <div class="input-group date" id="returnDate" @click="activateDateTimePickerReturnDate">
                                                <input type="text" class="form-control" v-model="flight.returnDate" required>
                                   <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div v-if="requestBody.planeTicketType.planeTicketName === 'VARIAS CIUDADES' && requestBody.flights.length > 1">
                                    <div class="col-md-3">
                                        <label style="visibility: hidden">delete</label>
                                        <div class="form-group">
                                            <button class="btn btn-default" @click="removeFlight(flight)"
                                                    data-toggle="modal" data-placement="top" title="Eliminar">
                                                <span class="glyphicon glyphicon-trash"></span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-10">
                        </div>
                        <div class="col-md-2">
                            <label>Pasajeros</label>
                            <div class="row">
                                <div class="col-md-6">
                                    <input v-model="requestBody.passengers.length" type="text" class="form-control" disabled>
                                </div>
                                <div class="col-md-6">
                                    <button type="button" class="btn btn-default" @click="addPassenger">Agregar</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <br>

                    <div v-for="passenger in requestBody.passengers">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-md-4">
                                        <h3 class="panel-title"><strong>Información de pasejero</strong></h3>
                                    </div>
                                    <div class="col-md-4 text-center">
                                        Pasajero número {{$index + 1}}
                                    </div>
                                    <div class="col-md-4" v-if="requestBody.passengers.length > 1">
                                        <button class="close" @click="removePassenger(passenger)"><span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="col-md-offset-1">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <label>Nombre completo</label>
                                            <input v-model="passenger.fullName" type="text" class="form-control" required>
                                        </div>
                                        <div class="col-md-2">
                                            <label>Puesto</label>
                                            <input v-model="passenger.job" type="text" class="form-control" required>
                                        </div>
                                        <div class="col-md-2">
                                            <label>Empresa</label>
                                            <input v-model="passenger.company" type="text" class="form-control" required>
                                        </div>
                                        <div class="col-md-2">
                                            <label>Numero telefonico</label>
                                            <input v-model="passenger.phoneNumber" type="text" class="form-control" required>
                                        </div>
                                        <div class="col-md-2">
                                            <label>Fecha de nacimiento</label>
                                            <div class="form-group">
                                                <div class="input-group date" id="birthdate{{$index}}" @click="activateDateTimePickerBirthdate($index)">
                                                    <input type="text" class="form-control" v-model="passenger.birthdate" required>
                                               <span class="input-group-addon">
                                                   <span class="glyphicon glyphicon-calendar"></span>
                                               </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-2">
                                            <label>Asiento</label>
                                            <br>
                                            <label class="radio-inline" v-for="planeSeatType in planeSeatsTypes">
                                                <input type="radio" name="travel{{$parent.$index}}" v-model="passenger.planeSeatType"
                                                       :value="planeSeatType" required>{{planeSeatType.planeSeatType}}
                                            </label>
                                            <h6><strong>* Sujeto a disponibilidad</strong></h6>
                                        </div>
                                        <div class="col-md-4">
                                            <label>Pasaporte</label>
                                            <input @change="setFile($event,passenger)" type="file" class="form-control" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label>Socio frecuente</label>
                                            <input v-model="passenger.frequentPartner" type="text" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-offset-10 col-md-2 text-right">
                            <button class="btn btn-success form-control" type="submit">Solicitar</button>
                        </div>
                    </div>
                    <br>
                </div>
            </form>
        </div>
    </jsp:body>
</t:template>
