<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<jsp:include page="../layout/header.jsp" />
<div class="container-md">
<h1>Member Modify Page</h1>
<sec:authorize access="isAuthenticated()">
<sec:authentication property="principal.uvo.email" var="authEmail" />   
<sec:authentication property="principal.uvo.nickName" var="authNick" />   
<sec:authentication property="principal.uvo.regDate" var="authRegDate" />   
<sec:authentication property="principal.uvo.lastLogin" var="authLastLogin" />   
<sec:authentication property="principal.uvo.authList" var="auths" /> 
</sec:authorize>
<form action="/user/modify" method="post">
<input type="hidden" name="email" value="${authEmail }">
	<div class="col">
			<div class="card" style="width: 18rem;">
			  <img src="/re/image/pic01.jpg" class="card-img-top" alt="...">
			  <div class="card-body">
			    <h5 class="card-title"><input type="text" name="nickName" value="${authNick }"></h5>
			    <p class="card-text">${authEmail }</p>
			    <p class="card-text"><input type="text" name="pwd" placeholder="Password..."></p>
			    <p class="card-text"> 
			    ( <c:forEach items="${auths }" var="authList">
			    	${authList.auth } 
			    </c:forEach> )
			    </p>
			    <p class="card-text">${authLastLogin }</p>
			    <p class="card-text">(${authRegDate })</p>
			    
			    <a href="#" class="btn btn-primary">Go somewhere</a>
			  </div>
			</div>
		</div>	
	
	<button type="submit" class="btn btn-primary">Modify</button>
	<button type="button" class="btn btn-danger" id="delBtn">delete</button>
</form>

</div>

<script type="text/javascript">
document.getElementById('delBtn').addEventListener('click',()=>{
    let check = confirm("정말 탈퇴하시겠습니까?");
    //confirm => treu / false
    console.log(check);
    if(check){
        location.href="/user/remove";
    }
})
</script>
<jsp:include page="../layout/footer.jsp" />