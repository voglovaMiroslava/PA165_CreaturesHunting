<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Edit location">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/location/update/${locationUpdate.id}"
                   modelAttribute="locationUpdate" cssClass="form-horizontal">

            <div>
                <form:label path="name" cssClass="control-label">Name</form:label>
                <div>
                    <form:input path="name" cssClass="form-control" value="${locationUpdate.name}"/>
                    <form:errors path="name" cssClass="help-block"/>
                </div>
            </div>
            <div>
                <form:label path="description" cssClass="control-label">Description</form:label>
                <div>
                    <form:input path="description" cssClass="form-control" value="${locationUpdate.description}"/>
                    <form:errors path="description" cssClass="help-block"/>
                </div>
            </div>
                
            <button type="submit">Update Location</button>
        </form:form>
            
                    <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-md-8">
              <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title"><fmt:message key="location.edit"/></h3>
                </div>
                <!-- /.box-header -->
                <!-- form start -->
                <form:form method="post" action="${pageContext.request.contextPath}/location/update" modelAttribute="location">
                  <div class="box-body">
                    <div class="form-group">
                      <form:hidden path="id" cssClass="form-control" />
                    </div>
                    <div class="form-group">
                      <form:label path="name"><fmt:message key="example.name"/></form:label>
                      <fmt:message key="location.enterName" var="enterName"/>
                      <form:input path="name" cssClass="form-control" placeholder="${enterName}" />
                      <form:errors path="name" cssClass="help-block"/>
                    </div>
                    <div class="form-group">
                      <form:label path="description"><fmt:message key="example.description"/></form:label>
                      <form:select path="description" cssClass="form-control" items="${description}" />
                    </div>
                  </div>
                  <!-- /.box-body -->

                  <div class="box-footer">
                    <button type="submit" class="btn btn-primary" ><fmt:message key="rental.submit"/></button>
                  </div>
                </form:form>
              </div>
             
            </div>
          </div>
        </section><!-- /.content -->

    </jsp:attribute>
</my:pagetemplate>