<%@ page import="seoulpublicwifi.dao.BookmarkGroupDao" %>
<%@ page import="seoulpublicwifi.dto.BookmarkGroupDto" %>
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
    String groupNm = request.getParameter("groupNm");
    String seq = request.getParameter("seq");

    BookmarkGroupDto bookmarkGroupDto = new BookmarkGroupDto();
    bookmarkGroupDto.setId(Integer.parseInt(id));
    bookmarkGroupDto.setGroupNm(groupNm);
    bookmarkGroupDto.setSeq(Integer.parseInt(seq));

    BookmarkGroupDao bookmarkGroupDao = new BookmarkGroupDao();
    int affected = bookmarkGroupDao.update(bookmarkGroupDto);
%>

<script>
    <%
        String str = affected > 0 ? "북마크 그룹을 수정하였습니다." : "북마크 그룹 수정에 실패하였습니다.";
    %>
    alert("<%= str %>");
    location.href = "bookmarkGroup.jsp";
</script>
</body>
</html>