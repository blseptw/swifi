<%@ page import="seoulpublicwifi.dto.BookmarkDto" %>
<%@ page import="seoulpublicwifi.dao.BookmarkDao" %>
<%@ page import="seoulpublicwifi.dao.BookmarkGroupDao" %>
<%@ page import="seoulpublicwifi.dto.BookmarkGroupDto" %>
<%@ page import="seoulpublicwifi.dao.WifiDao" %>
<%@ page import="seoulpublicwifi.dto.WifiDto" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<link rel="stylesheet" type="text/css" href="css/bookmarkGroup.css">
</head>

<body>
<h1>북마크 삭제</h1>

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
<p>북마크를 삭제하시겠습니까?</p>

<%
    String id = request.getParameter("id");

    BookmarkDao bookmarkDao = new BookmarkDao();
    BookmarkDto bookmarkDto = bookmarkDao.select(Integer.parseInt(id));

    BookmarkGroupDao bookmarkGroupDao = new BookmarkGroupDao();
    BookmarkGroupDto bookmarkGroupDto = bookmarkGroupDao.select(bookmarkDto.getGroupId());

    WifiDao wifiDao = new WifiDao();
    WifiDto wifiDto = wifiDao.select(bookmarkDto.getMgrNo());
%>

<form method="post" action="bookmark_deleteOk.jsp">
    <table id="table-list">
        <tr>
            <th>북마크 이름</th>
            <td>
                <%= bookmarkGroupDto.getGroupNm() %>
            </td>
        </tr>
        <tr>
            <th>와이파이명</th>
            <td>
                <%= wifiDto.getMainNm() %>
            </td>
        </tr>
        <tr>
            <th>등록일자</th>
            <td>
                <%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bookmarkDto.getRegDttm()) %>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <a href="bookmark.jsp">돌아가기</a>
                &#124;
                <input type="submit" name="delete" value="삭제">
                <input type="hidden" name="id" value="<%= bookmarkDto.getId() %>">
            </td>
        </tr>
    </table>
</form>
</body>
</html>