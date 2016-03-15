<%--
  User: rafael
  Date: 8/02/16
  Time: 01:13 PM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Bandeja de Entrada">
    <jsp:attribute name="styles">
        <style>
            #content {
                margin-top: 1.5rem;
            }
            .notification.expanded {
                margin-top: 1.5rem;
                margin-bottom: 1.5rem;
            }
            .notifications-list {
                margin: 2rem 0;
            }
            .notifications-list h4 {
                margin-left: 2rem;
            }
            .notifications-group {
                margin-bottom: 2rem;
            }
            .notifications-group :last-child .card {
                border-bottom: 1px solid #c8c8c8;
            }
            .notification-details {
                padding: 2rem;
                border: 1px solid #c8c8c8;
                background: #FFFFFF;
                box-shadow: 0 -1px 0 #e5e5e5, 0 0 2px rgba(0,0,0,.12), 0 2px 4px rgba(0,0,0,.24);
            }
            .card-body {
                cursor: pointer;
            }
        </style>
    </jsp:attribute>

    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.fetchInbox();
                    this.delayModal.datePicker = $('#delayDateTimePicker').datetimepicker({
                        format: 'dd MM yyyy hh:mm A',
                        minDate: moment().add(1, 'minutes'),
                        inline: true,
                        sideBySide: true,
                        daysOfWeekDisabled: [0,6],
                        locale: 'es'
                    }).data();
                },
                data: {
                    idUser: ${user.idUser},
                    showHistory: false,
                    notifications: [],
                    notificationExpanded: null,
                    delayModal: {
                        notification: null,
                        datePicker: null
                    }
                },
                filters: {
                    resourceViewURI: function (val, id) {
                        var uri = val.replace(/#/, id);
                        return ROOT_URL + "/" + uri;
                    }
                },
                methods: {
                    groupBy: function (array, filter) {
                        var groups = {};
                        array.forEach(function (element) {
                            var group = JSON.stringify(filter(element));
                            groups[group] = groups[group] || [];
                            groups[group].push(element);
                        });
                        return Object.keys(groups).map(function (group) {
                            return groups[group];
                        });
                    },
                    fetchInbox: function () {
                        this.$http.get(ROOT_URL + "/user/inbox").success(function (data) {
                            var jsonIndex = {};
                            data.forEach(function (item) {
                                if (isNaN(item.resourcesTasks.tasks)) {
                                    jsonIndex[item.resourcesTasks.tasks._id] = item.resourcesTasks.tasks;
                                } else {
                                    item.resourcesTasks.tasks = jsonIndex[item.resourcesTasks.tasks];
                                }
                                if (isNaN(item.resourcesTasks.view)) {
                                    jsonIndex[item.resourcesTasks.view._id] = item.resourcesTasks.view;
                                } else {
                                    item.resourcesTasks.view = jsonIndex[item.resourcesTasks.view];
                                }
                            });

                            this.notifications = this.groupBy(data, function (item) {
                                if (item.dueDate.dateDiff.negative) {
                                    return "atrasadas";
                                }
                                return item.dueDate.dateElements.interval;
                            });

                            Vue.nextTick(function () {
                                var el = document.getElementById("content");
                                el.classList.remove("hidden");
                            });
                        });
                    },
                    toggleExpandedNotification: function (notification) {
                        if (notification.expanded) {
                            notification.expanded = false;
                            this.notificationExpanded.expanded = false;
                        } else {
                            if (this.notificationExpanded != null) {
                                this.notificationExpanded.expanded = false;
                            }
                            Vue.set(notification, "expanded", true);
                            this.notificationExpanded = notification;

                            var uri = notification.resourcesTasks.tasks.taskName.replace(/#/, notification.idResource);
                            this.$http.get(ROOT_URL + "/" + uri).success(function (data) {
                                Vue.set(notification, "details", data);
                            });
                        }
                    },
                    showDelayModal: function (notification) {
                        $('#delayModal').modal("show");
                        console.log(notification);
                    },
                    saveNotificationDelay: function (notification) {
                        var date = this.delayModal.datePicker.DateTimePicker.date();
                        console.log(date.toISOString());
                    },
                    markAsRead: function (notification) {
                        this.$http.put(
                                ROOT_URL + "/notifications/archive/" + notification.idNotification
                        ).success(function (data) {
                            showAlert("Notificacion archivada");
                        });
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content" class="hidden">
            <div class="col-xs-12">
                <div class="col-md-6"><h4>Notificaciones</h4></div>
                <div class="text-right col-md-6">
                    <label>Ver historial</label>
                    <input type="checkbox" data-toggle="toggle">
                </div>
            </div>
            <div class="notifications-list col-md-12">
                <div v-for="notificationGroup in notifications" class="notifications-group">
                    <h4 v-if="! notificationGroup[0].dueDate.dateDiff.negative" class="text-left">
                        {{ notificationGroup[0].dueDate.dateElements.interval.name }}
                        <span class="small">{{ notificationGroup[0].dueDate.dateTextLong }}</span>
                    </h4>
                    <h4 v-if="notificationGroup[0].dueDate.dateDiff.negative" class="text-left">
                        Atrasadas
                    </h4>
                    <div v-for="notification in notificationGroup" class="notification"
                         :class="{ 'row': notification.expanded, 'expanded': notification.expanded }">
                        <div class="card card-inline clearfix">
                            <div @click="toggleExpandedNotification(notification)" class="card-body clearfix">
                                <div class="card-image">
                                    <span class="badge"
                                          :style="{
                                            background: '#' + notification.notificationTypes.background,
                                            color: '#' + notification.notificationTypes.foreground
                                          }">
                                        {{ notification.notificationTypes.notificationSymbol }}
                                    </span>
                                </div>
                                <div class="card-title"><p>{{ notification.title }}</p></div>
                                <div class="card-subtitle"><p>{{ notification.subtitle }}</p></div>
                                <div class="card-text"><p>- {{ notification.text }}</p></div>
                            </div>
                            <div class="card-actions">
                                <span v-if="notification.dueDate.dateDiff.negative" class="label label-danger">
                                    {{ notification.dueDate.dateElements.dayNameLong | capitalize }}
                                    {{ notification.dueDate.dateElements.day }}
                                    <span v-if="! notification.dueDate.dateElements.interval.sameMonth">
                                        , {{ notification.dueDate.dateElements.monthNameLong | capitalize }}
                                    </span>
                                    <span v-if="! notification.dueDate.dateElements.interval.sameYear">
                                        {{ notification.dueDate.dateElements.year }}
                                    </span>
                                </span>
                                <span v-if="! notification.dueDate.dateDiff.negative">
                                    <span v-if="! notification.dueDate.dateDiff.zero">
                                        <span :class="{
                                                'label-warning': notification.dueDate.dateDiff.period.days <= 7,
                                                'label-primary': notification.dueDate.dateDiff.period.days > 7
                                            }" class="label">
                                            {{ notification.dueDate.dateElements.dayNameLong | capitalize }}
                                            {{ notification.dueDate.dateElements.day }}
                                            <span v-if="! notification.dueDate.dateElements.interval.sameMonth">
                                                , {{ notification.dueDate.dateElements.monthNameLong | capitalize }}
                                            </span>
                                            <span v-if="! notification.dueDate.dateElements.interval.sameYear">
                                                {{ notification.dueDate.dateElements.year }}
                                            </span>
                                        </span>
                                    </span>
                                    <span v-if="notification.dueDate.dateDiff.zero" class="label label-danger">
                                        {{ notification.dueDate.dateElements.interval.name }}
                                    </span>
                                </span>
                                <a v-if="notification.idUser == idUser" @click.prevent="markAsRead(notification)" href="#">
                                    <span class="glyphicon glyphicon-ok"></span>
                                </a>
                                <a v-if="notification.idUser == idUser" @click.prevent="showDelayModal(notification)" href="#">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </a>
                                <a href="{{ notification.resourcesTasks.view.cTasks.taskName | resourceViewURI notification.idResource }}">
                                    <span class="glyphicon glyphicon-new-window"></span>
                                </a>
                            </div>
                        </div>
                        <div :class="{ 'in': notification.expanded }"
                             class="notification-details collapse clearfix">
                            <p class="col-xs-4"><strong>Recivida el: {{ notification.creationDate.dateNumber }}</strong></p>
                            <p class="col-xs-4"><strong>Vencimiento: {{ notification.dueDate.dateNumber }}</strong></p>
                            <p v-for="(key, value) in notification.details" class="col-xs-4">
                                <span>{{ key }}</span>
                                <span>{{ value }}</span>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <%-- Modal: Posponer notificacion --%>
            <div id="delayModal" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Posponer notificación</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div id="delayDateTimePicker"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button @click="saveNotificationDelay(delayModal.notification)"
                                    class="btn btn-success">
                                    Guardar
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>

</t:template>
