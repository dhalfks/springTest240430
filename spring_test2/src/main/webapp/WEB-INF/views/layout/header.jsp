<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>header.jsp</title>
<link href="/re/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="/re/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Spring</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/">Home</a>
        </li>
        
        <li class="nav-item">
          <a class="nav-link" href="/board/list">게시판 보기</a>
        </li>
        <sec:authorize access="isAnonymous()">
        
	        <li class="nav-item">
	          <a class="nav-link" href="/user/register">회원가입</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="/user/login">로그인</a>
	        </li>
        
        </sec:authorize>
        
        <!-- access의 권한이 있는지 확인 -->
        <!-- 현재 사용자의 정보 : principal -->
        <sec:authorize access="isAuthenticated()">
	    <sec:authentication property="principal.uvo.email" var="authEmail" />   
	    <sec:authentication property="principal.uvo.nickName" var="authNick" />   
	    <sec:authentication property="principal.uvo.authList" var="auths" />   
	    <li class="nav-item">
          <a class="nav-link" href="/board/register">게시판 글쓰기</a>
        </li>
	     <c:choose>
	     	<c:when test="${auths.stream().anyMatch(authVO -> authVO.auth.equals('ROLE_ADMIN')).get() }">
		        <li class="nav-item">
		          <a class="nav-link" href="/user/list">회원리스트 ${authNick }(${authEmail } / ADMIN)</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link" href="/user/modify">회원정보수정 ${authNick }(${authEmail })</a>
		        </li>
	     	</c:when>
	     	<c:otherwise>
		         <li class="nav-item">
		          <a class="nav-link" href="/user/modify">회원정보수정 ${authNick }(${authEmail })</a>
		        </li>
	     	</c:otherwise>
	     </c:choose>
	     
	         <li class="nav-item">
	          <a class="nav-link" href="" id="logoutLink">로그아웃</a>
	        </li>
	        <form action="/user/logout" method="post" id="logoutForm">
	        	<!-- 인증된 계정의 이메일 -->
	        	<input type="hidden" name="email" value="${authEmail }">
	        </form>
        
        </sec:authorize>
        
        
       </ul> 
    </div>
  </div>
</nav>

<script type="text/javascript">
document.getElementById('logoutLink').addEventListener('click',(e)=>{
	e.preventDefault();
    document.getElementById('logoutForm').submit(); 
});
</script>