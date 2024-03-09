<%@ page import="seoulpublicwifi.dto.BookmarkDto" %>
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

    String groupId = request.getParameter("groupId");
    String mgrNo = request.getParameter("mgrNo");

    if (groupId.equals("none")) { // 이전 페이지로 이동
        response.sendRedirect(request.getHeader("Referer"));
        return;
    }

    BookmarkDto bookmarkDto = new BookmarkDto();
    bookmarkDto.setGroupId(Integer.parseInt(groupId));
    bookmarkDto.setMgrNo(mgrNo);

    BookmarkDao bookmarkDao = new BookmarkDao();
    int affected = bookmarkDao.insert(bookmarkDto);
%>

<script>
    <%
        String str = affected > 0 ? "북마크를 추가하였습니다." : "북마크 추가에 실패하였습니다.";
    %>
    alert("<%= str %>");
    location.href = "bookmark.jsp";
</script>

</body>
</html>