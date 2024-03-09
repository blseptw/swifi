<%@ page import="seoulpublicwifi.dto.BookmarkDto" %>
<%@ page import="seoulpublicwifi.dao.BookmarkDao" %>
<%@ page import="seoulpublicwifi.dao.BookmarkGroupDao" %>
<%@ page import="seoulpublicwifi.dto.BookmarkGroupDto" %>
<%@ page import="seoulpublicwifi.dao.WifiDao" %>
<%@ page import="seoulpublicwifi.dto.WifiDto" %>
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
<h1>북마크 목록</h1>

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
        <th>북마크 이름</th>
        <th>와이파이명</th>
        <th>등록일자</th>
        <th>비고</th>
    </tr>
    </thead>
    <tbody>
    <%
        BookmarkDao bookmarkDao = new BookmarkDao();
        if (bookmarkDao.count() == 0) {
    %>
    <tr>
        <td colspan="5">
            데이터가 존재하지 않습니다.
        </td>
    </tr>
    <%
    } else {
        List<BookmarkDto> bookmarkDtoList = bookmarkDao.selectList();
        for (BookmarkDto item : bookmarkDtoList) {
            BookmarkGroupDao bookmarkGroupDao = new BookmarkGroupDao();
            BookmarkGroupDto bookmarkGroupDto = bookmarkGroupDao.select(item.getGroupId());

            WifiDao wifiDao = new WifiDao();
            WifiDto wifiDto = wifiDao.select(item.getMgrNo());
    %>
    <tr>
        <td>
            <%= item.getId() %>
        </td>
        <td>
            <%= bookmarkGroupDto.getGroupNm() %>
        </td>
        <td>
            <a href="detail.jsp?mgrNo=<%= wifiDto.getMgrNo() %>">
                <%= wifiDto.getMainNm() %>
            </a>
        </td>
        <td>
            <%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getRegDttm()) %>
        </td>
        <td>
            <a href="bookmark_delete.jsp?id=<%= item.getId() %>">
                삭제
            </a>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
</html>