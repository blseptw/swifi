<%@ page import="seoulpublicwifi.dao.BookmarkDao" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");

    String id = request.getParameter("id");

    BookmarkDao bookmarkDao = new BookmarkDao();
    int affected = bookmarkDao.delete(Integer.parseInt(id));
%>

<script>
    <%
        String str = affected > 0 ? "북마크를 해제하였습니다." : "북마크 해제에 실패하였습니다.";
    %>
    alert("<%= str %>");
    location.href = "bookmark.jsp";
</script>
</body>
</html>