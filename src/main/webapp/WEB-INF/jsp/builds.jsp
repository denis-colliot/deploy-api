<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="build" items="${builds.builds}" varStatus="status">
    <c:set var="buildId" value="build-${status.index}" scope="page" />

    <tr class="build-row">
        <td class="build-data-wrapper build-wrapper">
            <input type="checkbox" name="build" id="${buildId}" class="build-cb" value="${build.uri}" />
            <label for="${buildId}" class="build-label">${build.uri}</label>
        </td>
        <td class="build-data-wrapper number-wrapper">
            <select name="buildNumber" id="${buildId}-numbers" disabled="true"></select>
        </td>
        <td class="build-data-wrapper env-wrapper">
            <select name="env" id="${buildId}-environments" disabled="true"></select>
        </td>
    </tr>
</c:forEach>