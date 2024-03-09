<%@ page import="seoulpublicwifi.dao.HistoryDao" %>
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
    String id = request.getParameter("id");

    HistoryDao historyDao = new HistoryDao();
    int affected = historyDao.delete(Integer.valueOf(id));
%>

<script>
    <%
        String str = affected > 0 ? "위치 히스토리를 삭제하였습니다." : "위치 히스토리 삭제에 실패하였습니다.";
    %>
    alert("<%= str %>");
    location.href = "history.jsp";
</script>
</body>
</html>