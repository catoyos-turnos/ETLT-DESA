<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">ETLT</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <c:choose>
          	<c:when test="${usuario != null}">
          	</c:when>
          	<c:otherwise>
          	</c:otherwise>
          </c:choose>
        </div><!--/.navbar-collapse -->
      </div>
    </nav>
