<%@ page import="seoulpublicwifi.dao.HistoryDao" %>
<%@ page import="seoulpublicwifi.dto.HistoryDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<link rel="stylesheet" type="text/css" href="css/index.css">
</head>

<body>
<h1>위치 히스토리 목록</h1>

<div id="link-list">
    <a href="index.jsp">홈</a>
    &#124;
    <a href="history.jsp">위치 히스토리 목록</a>
    &#124;
    <a href="load_wifi.jsp">Open API 와이파이 정보 가져오기</a>
    &#124;
    <a href="bookmark.jsp">북마크 보기</a>
    &#124;
    <a href="bookmarkGroup.jsp">북마크 그룹 관리</a>
</div>

<table id="table-list">
    <thead>
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    </thead>
    <tbody>
    <%
        HistoryDao historyDao = new HistoryDao();
        if (historyDao.count() == 0) {
    %>
    <tr>
        <td colspan="5">
            데이터가 존재하지 않습니다.
        </td>
    </tr>
    <%
    } else {
        List<HistoryDto> historyDtoList = historyDao.selectList();
        for (HistoryDto item : historyDtoList) {
    %>
    <tr>
        <td>
            <%= item.getId() %>
        </td>
        <td>
            <%= item.getLat() %>
        </td>
        <td>
            <%= item.getLnt() %>
        </td>
        <td>
    		<%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getSrchDttm()) %>
        </td>
        <td>
            <button onclick="deleteId(<%= item.getId() %>);">삭제</button>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>

<script>
    function deleteId(id) {
        location.href = "history_delete.jsp?id=" + id;
    }
</script>

</body>
</html>