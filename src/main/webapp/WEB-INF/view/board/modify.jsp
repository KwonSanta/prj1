<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:import url="/WEB-INF/fragment/navbar.jsp"/>
<h3>${board.id}번 게시물 수정</h3>
<form action="/modify" method="post">
    <input type="hidden" name="id" value="${board.id}">
    <div>
        제목
        <input type="text" name="title" value="${board.title}" required>
    </div>
    <div>
        본문
        <textarea name="content" cols="30" rows="10" required>${board.content}</textarea>
    </div>
    <div>
        작성자
        <input type="text" name="writer" value="${board.writer}" required>
    </div>
    <div>
        <button>수정</button>
    </div>
</form>

</body>
</html>
