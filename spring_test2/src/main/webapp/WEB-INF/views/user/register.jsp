<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../layout/header.jsp" />
<div class="container-md">
<h1>User Join Page</h1>
<form action="/user/register" method="post">
	
	<div class="mb-3">
	  <label for="e" class="form-label">E-mail</label>
	  <input type="email" class="form-control" name="email" id="e" placeholder="example@test.com...">
	</div>
	<div class="mb-3">
	  <label for="p" class="form-label">PassWord</label>
	  <input type="password" class="form-control" name="pwd" id="p" placeholder="Password...">
	</div>
	<div class="mb-3">
	  <label for="n" class="form-label">Nick_Name</label>
	  <input type="text" class="form-control" name="nickName" id="n" placeholder="nickName...">
	</div>
	
	<button type="submit" class="btn btn-primary">JOIN</button>
</form>

</div>
<jsp:include page="../layout/footer.jsp" />