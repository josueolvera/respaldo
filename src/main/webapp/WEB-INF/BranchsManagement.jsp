<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 16/06/16
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Administración de tickets">

    <jsp:attribute name="scripts">
        <script>
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.getBranchs();
                    this.getDistributors();
                },
                data: {
                    branchs:'',
                    newBranchForm:{
                        isActive:false,
                        isValid:false,
                        data:{
                            branch:{
                                branchName:'',
                                branchShort:''
                            }
                        }
                    },
                    selected:{
                        distributor:null,
                        region:null,
                        zona: null
                    },
                    branchFilter:'',
                    selectedBranch:{},
                    selectOptions:{
                        distributors:[],
                        regions:[],
                        zonas:[]
                    }
                },
                methods: {
                    arrayObjectIndexOf : function(myArray, searchTerm, property) {
                        for(var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },
                    getDistributors: function () {
                        this.$http.get(ROOT_URL + "/distributors?forStock=true").success(function (data) {
                            this.selectOptions.distributors = data;
                        });
                    },
                    getBranchs : function () {
                        this.$http.get(ROOT_URL + "/branchs")
                                .success(function (data) {
                                    this.branchs = data;
                                })
                                .error(function (data) {

                        });
                    },
                    changeFormIsActive : function () {
                        this.selected.distributor = null;
                        this.selected.region = null;
                        this.selected.zona = null;
                        this.newBranchForm.isActive = (this.newBranchForm.isActive !== true);
                    },
                    newBranchFormValidation: function () {
                        if (
                                this.newBranchForm.data.branch.branchName != ''
                                &&
                                this.newBranchForm.data.branch.branchShort != ''
                                &&
                                this.selected.distributor != null
                                &&
                                this.selected.region != null
                                &&
                                this.selected.zona != null
                        ) {
                            this.newBranchForm.isValid = true;
                        }
                    },
                    saveNewBranch : function () {

                        this.newBranchFormValidation();

                        if (this.newBranchForm.isValid) {
                            var idDistributor = this.selected.distributor.idDistributor;
                            var idRegion = this.selected.region.idRegion;
                            var idZona = this.selected.zona.idZonas;

                            this.$http.post(
                                    ROOT_URL + "/branchs/distributor/" + idDistributor + "/region/" + idRegion + "/zona/" + idZona,
                                    this.newBranchForm.data.branch
                            ).success(function (data) {

                                this.newBranchForm.isActive = false;

                                this.getBranchs();

                                showAlert("Sucursal guardada")

                                this.newBranchForm.data = {
                                    branch: {
                                        branchName: '',
                                        branchShort: ''
                                    }
                                };

                                this.selected = {
                                    distributor:null,
                                    region:null
                                };

                                this.newBranchForm.isValid = false;
                            }).error(function (data) {

                                showAlert("Error. No se pudo guardar la sucursal",{type:3})

                                this.newBranchForm.data = {
                                    branch:{
                                        branchName:'',
                                        branchShort:''
                                    }
                                };

                                this.selected = {
                                    distributor:null,
                                    region:null
                                };

                                this.newBranchForm.isValid = false;
                            });
                        } else {
                            showAlert("Nombre, Nombre corto distribuidor y region son campos requeridos",{type:3});
                        }
                    },
                    onChangeDistributor : function () {
                        this.selected.region = null;
                        this.selected.zona = null;
                        this.getRegionByDistributor(this.selected.distributor.idDistributor);
                    },
                    onChangeRegion : function () {
                        this.selected.zona = null;
                        this.getZonaByDistributorAndRegion(this.selected.distributor.idDistributor, this.selected.region.idRegion);
                    },
                    getRegionByDistributor: function (idDistributor) {
                        var self = this;
                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor-region/"+idDistributor)
                                .success(function (data) {
                                    this.selectOptions.regions = [];
                                    var index;
                                    data.forEach(function (region) {
                                        index =  self.arrayObjectIndexOf(self.selectOptions.regions,region.idRegion,'idRegion');
                                        if(index == -1) self.selectOptions.regions.push(region);
                                    });
                                }).error(function (data) {
                            showAlert("No existen regiones para esa compañía", {type : 3});
                        });
                    },
                    getZonaByDistributorAndRegion: function (idDistributor, idRegion) {
                        var self = this;
                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor-region-zona/"+idDistributor+"/"+idRegion)
                                .success(function (data) {
                                    this.selectOptions.zonas = [];
                                    var index;
                                    data.forEach(function (zona) {
                                        index =  self.arrayObjectIndexOf(self.selectOptions.zonas,zona.idZonas,'idZonas');
                                        if(index == -1) self.selectOptions.zonas.push(zona);
                                    });
                                }).error(function (data) {
                            showAlert("No existen zonas para esa compañía y región ", {type : 3});
                        });
                    },
                    changeBranchStatus : function () {
                        this.$http.post(
                                ROOT_URL + "/branchs/change-branch-status"
                                ,this.selectedBranch.idBranch
                        ).success(function (data) {
                            this.getBranchs();
                            this.clearSelectedBranch();
                            this.hideModal("#deleteModal");
                        }).error(function (data) {
                        });
                    },
                    editBranch : function () {

                        var requestBody = {
                            idDistributor : this.selected.distributor.idDistributor,
                            idRegion : this.selected.region.idRegion,
                            idZona : this.selected.zona.idZonas,
                            idBranch : this.selectedBranch.idBranch,
                            idDwEnterprise : this.selectedBranch.dwEnterprises[0].idDwEnterprise
                        };

                        this.$http.post(
                                ROOT_URL + "/branchs/update"
                                ,requestBody
                        ).success(function (data) {
                            this.selected = {
                                distributor:null,
                                region:null,
                                zona:null
                            };

                            this.getBranchs();
                            this.clearSelectedBranch();
                            this.hideModal("#editModal");
                        }).error(function (data) {
                        });
                    },
                    onDeleteBranch : function (branch) {
                        this.setSelectedBranch(branch);
                        this.showModal("#deleteModal");
                    },
                    hideEditModal : function () {
                        this.hideModal("#editModal");
                    },
                    onEditBranch : function (branch) {
                        this.selected.distributor = null;
                        this.selected.region = null;
                        this.selected.zona = null;
                        this.newBranchForm.isActive = false;
                        this.setSelectedBranch(branch);
                        this.showModal("#editModal");
                    },
                    setSelectedBranch : function (branch) {
                        this.selectedBranch = branch;
                    },
                    clearSelectedBranch : function () {
                        this.selectedBranch = {};
                    },
                    showModal : function (idModal) {
                        $(idModal).modal("show");
                    },
                    hideModal : function (idModal) {
                        $(idModal).modal("hide");
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style></style>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <br>
            <div class="col-md-8">
                <h2>Gestión de sucursales</h2>
            </div>
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-md-10">
                                <h4><strong>Sucursales</strong></h4>
                            </div>
                            <div class="col-md-2">
                                <label class="sr-only">Nombre</label>
                                <input v-model="branchFilter" :disabled="!branchs.length > 0"
                                       type="text" class="form-control" placeholder="Buscar">
                            </div>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-condensed table-bordered table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>
                                        NOMBRE
                                    </th>
                                    <th>
                                        NOMBRE CORTO
                                    </th>
                                    <th>
                                        DISTRIBUIDOR
                                    </th>
                                    <th>
                                        REGION
                                    </th>
                                    <th>
                                        ZONA
                                    </th>
                                    <th class="text-center">
                                        <div>
                                            <button class="btn btn-default btn-sm" @click="changeFormIsActive" v-if="!newBranchForm.isActive"
                                                    data-toggle="tooltip" data-placement="top" title="Agregar sucursal">
                                                <span class="glyphicon glyphicon-plus"></span>
                                            </button>
                                            <button class="btn btn-default btn-sm" @click="changeFormIsActive" v-if="newBranchForm.isActive"
                                                    data-toggle="tooltip" data-placement="top" title="Cancelar">
                                                <span class="glyphicon glyphicon-minus"></span>
                                            </button>
                                        </div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-if="newBranchForm.isActive" class="success">
                                    <td>
                                        <div class="form-group">
                                            <label class="sr-only">Nombre</label>
                                            <input v-model="newBranchForm.data.branch.branchName"
                                                   type="text" class="form-control"  placeholder="Nombre">
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <label class="sr-only" >Nombre corto</label>
                                            <input v-model="newBranchForm.data.branch.branchShort"
                                                   type="text" class="form-control"  placeholder="Nombre corto">
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <label class="sr-only">Distribuidor</label>
                                            <select v-model="selected.distributor" class="form-control"
                                                    @change="onChangeDistributor" required>
                                                <option v-for="distributor in selectOptions.distributors"
                                                        :value="distributor">
                                                    {{ distributor.distributorName }}
                                                </option>
                                            </select>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <label class="sr-only">Region</label>
                                            <select v-model="selected.region" class="form-control" required
                                                    :disabled="selected.distributor == null" @change="onChangeRegion">
                                                <option v-for="region in selectOptions.regions"
                                                        :value="region">
                                                    {{ region.regionName }}
                                                </option>
                                            </select>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <label class="sr-only">Zona</label>
                                            <select v-model="selected.zona" class="form-control" required
                                                    :disabled="selected.region == null">
                                                <option v-for="zona in selectOptions.zonas"
                                                        :value="zona">
                                                    {{ zona.name }}
                                                </option>
                                            </select>
                                        </div>
                                    </td>
                                    <td class="text-center">
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-success btn-sm" @click="saveNewBranch"
                                                    data-toggle="tooltip" data-placement="top" title="Guardar">
                                                <span class="glyphicon glyphicon-floppy-disk"></span>
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                                <tr v-for="branch in branchs | filterBy branchFilter | orderBy 'branchName'" v-if="branch.status">
                                    <td>
                                        {{branch.branchName}}
                                    </td>
                                    <td>
                                        {{branch.branchShort}}
                                    </td>
                                    <td>
                                        {{branch.dwEnterprises[0].distributor.distributorName}}
                                    </td>
                                    <td>
                                        {{branch.dwEnterprises[0].region.regionName}}
                                    </td>
                                    <td>
                                        {{branch.dwEnterprises[0].zona.name}}
                                    </td>
                                    <td class="text-center">
                                        <div>
                                            <button class="btn btn-default btn-sm" @click="onEditBranch(branch)"
                                                    data-toggle="tooltip" data-placement="top" title="Modificar">
                                                <span class="glyphicon glyphicon-edit"></span>
                                            </button>
                                            <button class="btn btn-danger btn-sm" @click="onDeleteBranch(branch)"
                                                    data-toggle="modal" data-placement="top" title="Eliminar">
                                                <span class="glyphicon glyphicon-trash"></span>
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!-- deleteModal -->
            <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="deleteModalLabel">Confirmación de baja de sucursal</b></h4>
                        </div>
                        <div class="modal-body">
                            La sucursal <b>{{selectedBranch.branchName}}</b> sera dada de baja.
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-danger" @click="changeBranchStatus">Aceptar</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- editModal -->
            <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" @click="hideEditModal"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="editModalLabel">Editar</h4>
                        </div>
                        <form v-on:submit.prevent="editBranch">
                            <div class="modal-body">

                                <div class="row">
                                    <div class="col-md-6">
                                        <label>Nombre</label>
                                        <p>
                                            {{selectedBranch.branchName}}
                                        </p>
                                    </div>
                                    <div class="col-md-4">
                                        <label>Nombre corto</label>
                                        <p>
                                            {{selectedBranch.branchShort}}
                                        </p>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-4">
                                        <label>Distribuidor</label>
                                        <select v-model="selected.distributor" class="form-control"
                                                @change="onChangeDistributor" required>
                                            <option v-for="distributor in selectOptions.distributors"
                                                    :value="distributor">
                                                {{ distributor.distributorName }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <label>Region</label>
                                        <select v-model="selected.region" class="form-control" required
                                                :disabled="selected.distributor == null" @change="onChangeRegion">
                                            <option v-for="region in selectOptions.regions"
                                                    :value="region">
                                                {{ region.regionName }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <label>Zona</label>
                                        <select v-model="selected.zona" class="form-control" required
                                                :disabled="selected.region == null">
                                            <option v-for="zona in selectOptions.zonas"
                                                    :value="zona">
                                                {{ zona.name }}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" @click="hideEditModal">Cancelar</button>
                                <button type="submit" class="btn btn-success">Guardar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>

