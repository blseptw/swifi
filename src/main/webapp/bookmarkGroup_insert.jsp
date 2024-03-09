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
<h1>북마크 그룹 추가</h1>

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

<form method="post" action="bookmarkGroup_insertOk.jsp">
    <table id="table-list">
        <tr>
            <th>북마크 이름</th>
            <td>
                <input type="text" name="groupNm">
            </td>
        </tr>
        <tr>
            <th>순서</th>
            <td>
                <input type="text" name="seq">
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <input type="submit" value="추가">
            </td>
        </tr>
    </table>
</form>
</body>
</html>