<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Carga de archivos">
    <jsp:attribute name="scripts">
        <script type="text/javascript">

        </script>

        <script type="text/javascript">
            var vm = new Vue({
                el: '#filesUpload',
                ready: function () {
                    this.typeFile = '';
                    this.getTypesFile();
                },
                data: {
                    errorData: {},
                    typesFile: [],
                    typeFile: {
                        idSapFile: '',
                        sapFileName: '',
                        fileName: '',
                        lastUploadedDate: '',
                        lastUploadedDateFormats: ''
                    },
                    calculateDate: '',
                    datetimepickerCalculateDate: '',
                    layoutDownloadUrl: ROOT_URL + "/sap-file/layout/download/",
                    range: {
                        initial: '',
                        final: ''
                    }
                },
                methods: {
                    setUpTimePickerCalculateDate: function () {
                        this.datetimepickerCalculateDate = $('#datetimepickerCalculateDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            //minDate: moment().add(1, 'minutes')
                        }).data();
                    },
                    validateForm: function () {
                    },
                    clearFileForm: function () {
                        document.getElementById("inputFile").value = null;
                        this.errorData = '';
                        this.calculateDate = '';
                    },
                    saveFile: function () {
                        if (this.typeFile.idSapFile == 1) {

                        }
                        if (this.typeFile.idSapFile == 2) {

                        }
                        if (this.typeFile.idSapFile == 3) {

                        }
                        if (this.typeFile.idSapFile == 4) {
                            this.updateDwBranchs();
                        }
                        if (this.typeFile.idSapFile == 5) {
                            this.checkExistingMultilevel();
                            //this.saveMultilevel();
                        }
                        if (this.typeFile.idSapFile == 6) {

                        }
                        if (this.typeFile.idSapFile == 7) {

                        }
                        if (this.typeFile.idSapFile == 8) {
                            this.checkExistingSale();
                        }
                        if (this.typeFile.idSapFile == 9) {
                            this.checkExistingOutsourcing();
                        }
                        if (this.typeFile.idSapFile == 10) {
                            this.checkExistingCommission();
                        }
                        if (this.typeFile.idSapFile == 11) {
                            this.checkExistingCsbPayCommission();
                        }
                    },
                    getFileFormData: function () {
                        var formElement = document.getElementById("fileForm");
                        var formData = new FormData(formElement);
                        return formData;
                    },
                    updateTypeFile: function () {

                        this.$http.post(ROOT_URL + '/sap-file/' + this.typeFile.idSapFile, this.getFileFormData()
                                ).success(function (data) {
                            this.getTypesFile();
                            this.typeFile = '';
                            showAlert("Registros guardados con exito");
                        })
                                .error(function (data) {

                                });
                    },
                    getTypesFile: function () {
                        this.$http.get(ROOT_URL + '/sap-file')
                                .success(function (data) {
                                    this.typesFile = data;
                                })
                                .error(function (data) {

                                })
                    },
                    saveSapSales: function () {
                        $('#checkExistigSaleModal').modal('hide');
                        this.$http.post(ROOT_URL + '/sap-sale/excel', this.getFileFormData())
                                .success(function (data) {
                                    this.updateTypeFile();
                                })
                                .error(function (data) {

                                });
                    },
                    saveCsbPaymentCommission: function () {
                        $('#checkExistigCsbPayModal').modal('hide');
                        this.$http.post(ROOT_URL + '/csb-pay-commission/excel', this.getFileFormData())
                            .success(function (data) {
                                this.updateTypeFile();
                            })
                            .error(function (data) {

                            });
                    },
                    updateSapSales: function () {
                        $('#checkExistigSaleModal').modal('hide');
                        this.$http.post(ROOT_URL + '/sap-sale/update-excel', this.getFileFormData())
                                .success(function (data) {
                                    this.updateTypeFile();
                                })
                                .error(function (data) {
                                });
                    },
                    updateCsbPaymentCommission: function () {
                        $('#checkExistigCsbPayModal').modal('hide');
                        this.$http.post(ROOT_URL + '/csb-pay-commission/update-excel', this.getFileFormData())
                            .success(function (data) {
                                this.updateTypeFile();
                            })
                            .error(function (data) {
                            });
                    },
                    checkExistingSale: function () {
                        this.$http.post(ROOT_URL + '/sap-sale/check-existing-sale', this.getFileFormData())
                                .success(function (data) {
                                    if (data == true) {
                                        $('#checkExistigSaleModal').modal('show');
                                    } else {
                                        this.saveSapSales();
                                    }
                                })
                                .error(function (data) {
                                    this.errorData = data;
                                    $('#errorModal').modal('show');
                                });
                    },
                    checkExistingCsbPayCommission: function () {
                        this.$http.post(ROOT_URL + '/csb-pay-commission/check-existing-sale', this.getFileFormData())
                            .success(function (data) {
                                if (data == true) {
                                    $('#checkExistigCsbPayModal').modal('show');
                                } else {
                                    this.saveCsbPaymentCommission();
                                }
                            })
                            .error(function (data) {
                                this.errorData = data;
                                $('#errorModal').modal('show');
                            });
                    },
                    checkExistingOutsourcing: function () {
                        this.$http.post(ROOT_URL + '/outsourcing/check-existing-outsourcing', this.getFileFormData())
                                .success(function (data) {
                                    if (data == true) {
                                        $('#checkExistigOutsourcingModal').modal('show');
                                    } else {
                                        this.saveOutsourcing();
                                    }
                                })
                                .error(function (data) {
                                    this.errorData = data;
                                    $('#errorModal').modal('show');
                                });
                    },
                    saveOutsourcing: function () {
                        this.$http.post(ROOT_URL + '/outsourcing/excel', this.getFileFormData())
                                .success(function (data) {
                                    this.updateTypeFile();
                                    this.calculateDate = '';
                                    $('#checkExistigOutsourcingModal').modal('hide');
                                }).error(function (data) {
                            this.errorData = data;
                            $('#errorModal').modal('show');
                        });
                    },
                    updateOutsourcing: function () {
                        this.$http.post(ROOT_URL + '/outsourcing/update-excel', this.getFileFormData())
                                .success(function (data) {
                                    this.updateTypeFile();
                                    $('#checkExistigOutsourcingModal').modal('hide');
                                }).error(function (data) {
                            this.errorData = data;
                            $('#errorModal').modal('show');
                        });
                    },
                    updateDwBranchs: function () {
                        this.$http.post(ROOT_URL + '/dw-branchs/update-excel', this.getFileFormData())
                                .success(function (data) {
                                    this.updateTypeFile();
                                })
                                .error(function (data) {
                                    this.errorData = data;
                                    $('#errorModal').modal('show');
                                });
                    },
                    checkExistingGoal: function () {
                        this.$http.post(ROOT_URL + '/branchs-goals/update-excel', this.getFileFormData())
                                .success(function (data) {
                                    this.updateTypeFile();
                                })
                                .error(function (data) {
                                    this.errorData = data;
                                    $('#errorModal').modal('show');
                                });
                    },
                    checkExistingCommission: function () {
                        this.$http.post(ROOT_URL + '/comisiones/check-existing-commission', this.getFileFormData())
                                .success(function (data) {

                                    if (data == true) {
                                        $('#checkExistigCommissionModal').modal('show');
                                    } else {
                                        this.saveCommission();
                                    }
                                })
                                .error(function (data) {
                                    this.errorData = data;
                                    $('#errorModal').modal('show');
                                });
                    },
                    saveCommission: function () {
                        this.$http.post(ROOT_URL + '/comisiones/excel', this.getFileFormData())
                                .success(function (data) {
                                    this.updateTypeFile();
                                })
                                .error(function (data) {
                                    this.errorData = data;
                                    $('#errorModal').modal('show');
                                });
                    },
                    updateCommission: function () {
                        this.$http.post(ROOT_URL + '/comisiones/update-excel', this.getFileFormData())
                                .success(function (data) {
                                    this.updateTypeFile();
                                    $('#checkExistigCommissionModal').modal('hide');
                                })
                                .error(function (data) {
                                    this.errorData = data;
                                    $('#errorModal').modal('show');
                                });
                    },
                    checkExistingMultilevel: function (){
                        this.$http.post(ROOT_URL + '/multilevel/check-existing-multilevel', this.getFileFormData())
                                .success(function (data) {

                                    if (data == true) {
                                        $('#checkExistigMultilevelModal').modal('show');
                                    } else {
                                        this.saveMultilevel();
                                    }
                                })
                                .error(function (data) {
                                    this.errorData = data;
                                    $('#errorModal').modal('show');
                                });
                    },
                    saveMultilevel: function (){
                        this.$http.post(ROOT_URL + '/multilevel/excel', this.getFileFormData())
                                .success(function (data) {
                                    this.updateTypeFile();
                                })
                                .error(function (data) {
                                    this.errorData = data;
                                    $('#errorModal').modal('show');
                                });
                    },
                    updateMultilevel: function (){
                        this.$http.post(ROOT_URL + '/multilevel/update-multilevel', this.getFileFormData())
                                .success(function (data) {
                                    this.updateTypeFile();
                                    $('#checkExistigMultilevelModal').modal('hide');
                                })
                                .error(function (data) {
                                    this.errorData = data;
                                    $('#errorModal').modal('show');
                                });
                    },
                    sendToValidation: function () {
                        this.$http.post(ROOT_URL + "/sap-sale/send-to-validation", JSON.stringify(this.range)).success(function (data) {
                            $("#sendValidationModal").modal("hide");
                            showAlert("Se a enviado a validación las ventas, la persona encargada sera notificada");
                        }).error(function () {
                            showAlert("Error al enviar correo de validación de datos", {type:3});
                        });
                    },
                    openModalSendToValidation: function () {
                        this.range.initial = "";
                        this.range.final = "";
                        $("#sendValidationModal").modal("show");
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="filesUpload">
            <h1>Carga de archivos SAP</h1>
            <br>
            <div class="col-md-offset-1 col-md-10">

                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-4">Selecciona archivo a subir</label>
                        <div class="col-sm-6">
                            <select class="form-control" v-model="typeFile" @change="clearFileForm">
                                <option></option>
                                <option v-for="typeFile in typesFile" value="{{typeFile}}">
                                    {{typeFile.sapFileName}}
                                </option>
                            </select>
                        </div>
                    </div>
                </form>

                <div class="panel panel-default" v-if="typeFile != ''">
                    <!-- Default panel contents -->
                    <div class="panel-heading">{{typeFile.sapFileName}}</div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-5" style="margin-right: 100px">
                                    <form id="fileForm" enctype="multipart/form-data" v-on:submit.prevent="saveFile">
                                        <div class="form-group col-md-12">
                                            <div class="col-md-12">
                                                <div class="col-md-5">
                                                    <input id="inputFile" type="file" name="file" required
                                                           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,
                                               application/vnd.ms-excel">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group col-md-12" v-if="typeFile.idSapFile == 5 || typeFile.idSapFile == 9 || typeFile.idSapFile == 10" >
                                            <div class="col-md-12">
                                                <div class="col-md-12">
                                                    <label>
                                                        Fecha de calculo
                                                    </label>
                                                    <div class="input-group date" id="datetimepickerCalculateDate"
                                                         @click="setUpTimePickerCalculateDate()">
                                                        <input type='text' name="calculateDate" class="form-control" v-model="calculateDate" required>
                                                        <span class="input-group-addon">
                                                <span class="glyphicon glyphicon-calendar"></span>
                                            </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <br>
                                        <br>
                                        <div class="form-group col-md-12">
                                            <div class="col-md-12">
                                                <div class="col-md-2" style="margin-right: 92px">
                                                    <button class="btn btn-success" type="submit">
                                                        Subir archivo
                                                    </button>
                                                </div>
                                                <div class="col-md-2">
                                                    <button class="btn btn-info" v-if="typeFile.idSapFile == 8" type="button" @click="openModalSendToValidation()">
                                                        Enviar a validación
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">Archivos</div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>
                                            Archivo
                                        </th>
                                        <th>
                                            Nombre
                                        </th>
                                        <th>
                                            Fecha ultima vez subido
                                        </th>
                                        <th>
                                            Layout
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="typeFile in typesFile">
                                        <td>
                                            {{typeFile.sapFileName}}
                                        </td>
                                        <td>
                                            {{typeFile.fileName}}
                                        </td>
                                        <td>
                                            {{typeFile.lastUploadedDateFormats.dateTextLong}} - {{typeFile.lastUploadedDateFormats.time12}}
                                        </td>
                                        <td class="text-center">
                                            <a class="btn btn-default" :href="typeFile.layoutFileUrl"
                                               data-toggle="tooltip" data-placement="top" title="Descargar layout de archivo">
                                                <span class="glyphicon glyphicon-download-alt"></span>
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="checkExistigSaleModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            Algunas de estas ventas ya existen. ¿Desea sobreescribir los registros o guardarlos como nuevos?.
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="saveSapSales">Guardar</button>
                            <button type="button" class="btn btn-primary" @click="updateSapSales">Sobreescribir</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="checkExistigCsbPayModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            Algunas de estas ventas ya existen. ¿Desea sobreescribir los registros o guardarlos como nuevos?.
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-success" @click="saveCsbPaymentCommission">Guardar</button>
                            <button type="button" class="btn btn-primary" @click="updateCsbPaymentCommission">Sobreescribir</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="checkExistigOutsourcingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            Ya existen registros para esta fecha. ¿Desea sobreescribir los registros?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="updateOutsourcing">Aceptar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="checkExistigCommissionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            Ya existen comisiones para esta fecha. ¿Desea sobreescribir los registros?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="updateCommission">Aceptar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
            
            
            <div class="modal fade" id="checkExistigMultilevelModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            Ya existen comisiones multinivel para esta fecha. ¿Desea sobreescribir los registros?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="updateMultilevel">Aceptar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>


            <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Error</h4>
                        </div>
                        <div class="modal-body">
                            <div class="alert alert-danger" role="alert">
                                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                <span class="sr-only">Error:</span>
                                {{errorData.error.message}}
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="sendValidationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">
                                Envio de notificación de validación de ventas
                            </h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <p>Introduce el rango de las ventas cargadas</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-1">
                                    </div>
                                    <div class="col-md-4">
                                        <label>Fecha inicial</label>
                                        <input class="form-control" v-model="range.initial" placeholder="DD/MM/YYYY">
                                    </div>
                                    <div class="col-md-1">
                                        <hr style="border-width: 2px; margin-top: 40px; border-style: solid;">
                                    </div>
                                    <div class="col-md-4">
                                        <label>Fecha final</label>
                                        <input class="form-control" v-model="range.final" placeholder="DD/MM/YYYY">
                                    </div>
                                    <div class="col-md-1">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" @click="sendToValidation()">Aceptar</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
