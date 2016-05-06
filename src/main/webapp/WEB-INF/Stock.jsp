<%--
  Created by IntelliJ IDEA.
  User: gerardo8
  Date: 06/05/16
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Inventario">

    <jsp:attribute name="scripts">
        <script>
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.getDistributors();
                },
                data: {
                    isSaving: false,
                    stock: {},
                    distributors: [],
                    areas: [],
                    dwEnterprises:[],
                    attributes: [],
                    values: [],
                    articleStatus: [],
                    employees: [],
                    hierarchy: [],
                    distributor: {},
                    area: {},
                    documentTypes: [],
                    dwEnterprices: null,
                    attachmentsModal: {
                        article: null,
                        fileInput: "file-type-"
                    },
                    editModal: {
                        selectAttr: null,
                        selectValue: null,
                        serialNumber: 0,
                        stockFolio: 0,
                        articleStatus: null,
                        purchasePrice: 0,
                        employees: null,
                        selectedEmployee: null,
                        article: null
                    },
                    assignmentsModal: {
                        selected: {
                            distributor: null,
                            region: null,
                            branch: null,
                            area: null
                        },
                        article: null
                    },
                    attachmentsDownloadUrl: ROOT_URL + "/stock/attachments/download/"
                },
                methods: {
                    getDistributors: function () {
                        this.$http.get(ROOT_URL + "/distributors?forStock=true").success(function (data) {
                            this.distributors = data;
                        });

                    },
                    getDwEnterprises() {
                        this.$http.get(ROOT_URL + "/dw-enterprises/distributor/" + idDistributor)
                                .success(function (data) {
                                    this.dwEnterprises = data;
                                    this.dwEnterprises.forEach(function(element)
                                    {
                                        this.getArea(element.idArea);
                                    });
                                    console.log(data);
                                });
                    },
                    getArea: function (idArea) {
                        this.$http.get(ROOT_URL + "/areas/" + idArea).success(function (data) {
                            this.areas.push(data);
                        });
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">

    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="col-md-12"><h2 class="text-center">Inventario</h2></div>
            <div class="panel panel-default">
                    <div class="panel-heading">
                        <form class="form-inline">
                            <div class="form-group">
                                <label>Distribuidor</label>
                                <select class="form-control" v-model="distributor"
                                        @change="getAreas(distributor.idDistributor)">
                                    <option v-for="distributor in distributors"
                                            :value="distributor">
                                        {{ distributor.distributorName }}
                                    </option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Área</label>
                                <select class="form-control">
                                    <option v-for="area in areas"
                                            :value="area">
                                        {{ area.areaName }}
                                    </option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-success">Buscar</button>
                        </form>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>

                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            <div>

            </div>
        </div>

    </jsp:body>
</t:template>
