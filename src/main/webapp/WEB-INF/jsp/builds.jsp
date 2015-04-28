<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="build" items="${builds.builds}" varStatus="status">
    <div>
        <input type="checkbox" id="build-${status.index}" class="build-cb" />
        <label for="build-${status.index}" class="build-label">${build.uri}</label>
    </div>
</c:forEach>