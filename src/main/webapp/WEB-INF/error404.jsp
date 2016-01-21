<%--
  User: rafael
  Date: 21/01/16
  Time: 04:24 PM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Error">
    <p class="text-center" style="font-size: 3rem;">No existe</p>
</t:template>
