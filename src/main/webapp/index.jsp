<%@ page import="seoulpublicwifi.dto.WifiDto" %>
<%@ page import="seoulpublicwifi.dao.WifiDao" %>
<%@ page import="seoulpublicwifi.dao.HistoryDao" %>
<%@ page import="seoulpublicwifi.dto.HistoryDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<link rel="stylesheet" type="text/css" href="css/index.css">
</head>

<body>
	<h1>와이파이 정보 구하기</h1>
	
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

	<form method="get" action="index.jsp" id="form-list">
	    <label>
	        LAT: <input type="text" id="lat" name="lat" value="0.0">
	    </label>
	    <label>
	        LNT: <input type="text" id="lnt" name="lnt" value="0.0">
	    </label>
	    <input type="button" value="내 위치 가져오기" onclick="getLocation();">
	    <input type="submit" value="근처 WIFI 정보 보기">
	</form>

	<table id="table-list">
	    <thead>
	    <tr>
	        <th>거리(Km)</th>
	        <th>관리번호</th>
	        <th>자치구</th>
	        <th>와이파이명</th>
	        <th>도로명주소</th>
	        <th>상세주소</th>
	        <th>설치위치(층)</th>
	        <th>설치유형</th>
	        <th>설치기관</th>
	        <th>서비스구분</th>
	        <th>망종류</th>
	        <th>설치연도</th>
	        <th>실내외구분</th>
	        <th>WIFI접속환경</th>
	        <th>X좌표</th>
	        <th>Y좌표</th>
	        <th>작업일자</th>
	    </tr>
	    </thead>
    <tbody>
	 <%
        String lat = request.getParameter("lat");
        String lnt = request.getParameter("lnt");

        double latValue = 0.0;
        double lntValue = 0.0;

        if (lat != null && !lat.isEmpty()) {
            try {
                latValue = Double.parseDouble(lat);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (lnt != null && !lnt.isEmpty()) {
            try {
                lntValue = Double.parseDouble(lnt);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (latValue == 0.0 && lntValue == 0.0) {
    %>
    <tr>
        <td colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
    </tr>
    <%
    } else {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setLat(latValue);
        historyDto.setLnt(lntValue);

        HistoryDao historyDao = new HistoryDao();
        historyDao.insert(historyDto);

        WifiDao wifiDao = new WifiDao();
        List<WifiDto> wifiDaoList = wifiDao.selectList(lntValue, latValue);

        for (WifiDto item : wifiDaoList) {
    %>
    <tr>
        <td>
            <%= item.getDist() %>
        </td>
        <td>
            <%= item.getMgrNo() %>
        </td>
        <td>
            <%= item.getWrdofc() %>
        </td>
        <td>
            <a href="detail.jsp?mgrNo=<%= item.getMgrNo() %>&dist=<%= item.getDist() %>">
                <%= item.getMainNm() %>
            </a>
        </td>
        <td>
            <%= item.getAdres1() %>
        </td>
        <td>
            <%= item.getAdres2() %>
        </td>
        <td>
            <%= item.getInstlFloor() %>
        </td>
        <td>
            <%= item.getInstlTy() %>
        </td>
        <td>
            <%= item.getInstlMby() %>
        </td>
        <td>
            <%= item.getSvcSe() %>
        </td>
        <td>
            <%= item.getCmcwr() %>
        </td>
        <td>
            <%= item.getCnstcYear() %>
        </td>
        <td>
            <%= item.getInoutDoor() %>
        </td>
        <td>
            <%= item.getRemars3() %>
        </td>
        <td>
            <%= item.getLnt() %>
        </td>
        <td>
            <%= item.getLat() %>
        </td>
        <td>
    		<%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getWorkDttm()) %>
		</td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>

<script>
    const params = new URLSearchParams(window.location.search)
    const lnt = params.get("lnt")
    const lat = params.get("lat")

    if (lnt) {
        const lntElem = document.getElementById("lnt")
        lntElement.setAttribute("value", lnt)
    }

    if (lat) {
        const latElem = document.getElementById("lat")
        latElement.setAttribute("value", lat)
    }

    function getLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        } else {
            alert("이 브라우저는 위치 정보를 지원하지 않습니다.");
        }
    }

    function showPosition(position) {
        const lat = position.coords.latitude;
        const lnt = position.coords.longitude;

        document.getElementById("lat").value = lat;
        document.getElementById("lnt").value = lnt;
    }
</script>
</body>
</html>