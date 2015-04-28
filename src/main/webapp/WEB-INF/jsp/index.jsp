<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Deploy API</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="../../scripts/getScript.fix.js"></script>
    <script>
    $(document).ready(function() { $.getScript('../../scripts/scripts.js'); });

    function url(value) {
        return '<c:url value="' + value +'" />';
    }
    </script>

    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    <link rel=stylesheet type="text/css" href="../../styles/styles.css" />
</head>
<body>

    <div class="app-title"> Deploy API </div>

    <div class="content">

        <!-- Builds. -->
        <div id="builds-wrapper"></div>

        <!-- Environments. -->
        <div id="environments-wrapper"></div>

    </div>

</body>
</html>
