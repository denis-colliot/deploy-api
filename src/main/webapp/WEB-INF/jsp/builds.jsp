<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="build" items="${builds.builds}" varStatus="status">
    <c:set var="buildId" value="build-${status.index}" scope="page" />

    <div class="build-row">
        <div class="build-wrapper">
            <input type="checkbox" id="${buildId}" class="build-cb" value="${build.uri}" />
            <label for="${buildId}" class="build-label">${build.uri}</label>
        </div>
        <div class="buildNumber-wrapper">
            <select name="buildNumber" id="${buildId}-numbers" disabled="true"></select>
        </div>
    </div>
</c:forEach>