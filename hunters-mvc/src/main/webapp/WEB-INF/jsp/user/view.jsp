<%--
  Created by IntelliJ IDEA.
  User: Snurka
  Date: 12/12/2016
  Time: 2:15 PM
  To change this template use File | Settings | File Templates.
--%>

<my:pagetemplate title="User">
<jsp:attribute name="body">

    <table class="table">
        <caption>Users</caption>
        <thead>
        <tr>
            <th>nickname</th>
            <th>email</th>
            <th>isAdmin</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td><c:out value="${user.nickname}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.isAdmin}"/></td>
            </tr>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>
C
