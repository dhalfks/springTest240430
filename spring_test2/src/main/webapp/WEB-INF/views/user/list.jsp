<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../layout/header.jsp" />
<div class="container-md">
	<h1>Member List Page</h1>
	<div class="row">
	<c:forEach items="${userList }" var="uvo">
		<div class="col">
			<div class="card" style="width: 18rem;">
			  <img src="/re/image/pic01.jpg" class="card-img-top" alt="...">
			  <div class="card-body">
			    <h5 class="card-title">${uvo.nickName }</h5>
			    <p class="card-text">${uvo.email }</p>
			    <p class="card-text"> 
			    ( <c:forEach items="${uvo.authList }" var="authList">
			    	${authList.auth } 
			    </c:forEach> )
			    </p>
			    <p class="card-text">${uvo.lastLogin }</p>
			    <p class="card-text">(${uvo.regDate })</p>
			    
			    <a href="#" class="btn btn-primary">Go somewhere</a>
			  </div>
			</div>
		</div>	
	</c:forEach>
	</div>
</div>
<jsp:include page="../layout/footer.jsp" />