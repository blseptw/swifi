<%@ page import="seoulpublicwifi.dao.BookmarkGroupDao" %>
<%@ page import="seoulpublicwifi.dto.BookmarkGroupDto" %>
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
<h1>북마크 그룹</h1>

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

<button onclick="location.href='bookmarkGroup_insert.jsp';">
    북마크 그룹 이름 추가
</button>

<table id="table-list">
    <thead>
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>순서</th>
        <th>등록일자</th>
        <th>수정일자</th>
        <th>비고</th>
    </tr>
    </thead>
    <tbody>
    <%
        BookmarkGroupDao bookmarkGroupDao = new BookmarkGroupDao();
        if (bookmarkGroupDao.count() == 0) {
    %>
    <tr>
        <td colspan="6">
            데이터가 존재하지 않습니다.
        </td>
    </tr>
    <%
    } else {
        List<BookmarkGroupDto> bookmarkGroupDtoList = bookmarkGroupDao.selectList();
        for (BookmarkGroupDto item : bookmarkGroupDtoList) {
    %>
    <tr>
        <td>
            <%= item.getId() %>
        </td>
        <td>
            <%= item.getGroupNm() %>
        </td>
        <td>
            <%= item.getSeq() %>
        </td>
        <td>
            <%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getRegDttm()) %>
        </td>
        <td>
            <%= item.getUptDttm() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUptDttm()) : "" %>
        </td>
        <td>
            <a href="bookmarkGroup_update.jsp?id=<%= item.getId() %>">수정</a>
            &nbsp;
            <a href="bookmarkGroup_delete.jsp?id=<%= item.getId() %>">삭제</a>
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