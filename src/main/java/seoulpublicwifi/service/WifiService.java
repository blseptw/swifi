package seoulpublicwifi.service;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import seoulpublicwifi.dao.WifiDao;
import seoulpublicwifi.dto.WifiDto;

public class WifiService {
    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088";
    private static final String API_KEY = "567754684a67676b3132336d654d4366";
    private static final String DATA_TYPE = "json";
    private static final String SERVICE_NAME = "TbPublicWifiInfo";
    
    // WIFI 총 개수
    public int total() throws IOException {
        String urlBuilder = BASE_URL + "/" +
                URLEncoder.encode(API_KEY, "UTF-8") + "/" +
                URLEncoder.encode(DATA_TYPE, "UTF-8") + "/" +
                URLEncoder.encode(SERVICE_NAME, "UTF-8") + "/1/1";

        URL url = new URL(urlBuilder);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader br;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
        	br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
        	br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        
        br.close();
        conn.disconnect();

        Gson gson = new Gson(); // JSON 데이터를 자바 객체로 변환하기 위한 라이브러리
        JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
        
        int listTotalCount = jsonObject.getAsJsonObject(SERVICE_NAME).get("list_total_count").getAsInt();
        
        return listTotalCount;
    }

    // WIFI 정보 저장
    public void save() throws IOException {
        int data = 1;
        for (int i = 0; i < total() / 1000 + 1; i++) {
            String urlBuilder = BASE_URL + "/" +
                    URLEncoder.encode(API_KEY, "UTF-8") + "/" +
                    URLEncoder.encode(DATA_TYPE, "UTF-8") + "/" +
                    URLEncoder.encode(SERVICE_NAME, "UTF-8") + "/" +
                    URLEncoder.encode(String.valueOf(data), "UTF-8") + "/" +
                    URLEncoder.encode(String.valueOf(data + 999), "UTF-8");

            URL url = new URL(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
            JsonArray rowArray = jsonObject.getAsJsonObject(SERVICE_NAME).getAsJsonArray("row");

            for (int j = 0; j < rowArray.size(); j++) {
                JsonObject rowObject = rowArray.get(j).getAsJsonObject();

                WifiDto wifiDto = new WifiDto();
                wifiDto.setMgrNo(rowObject.get("X_SWIFI_MGR_NO").getAsString());
                wifiDto.setWrdofc(rowObject.get("X_SWIFI_WRDOFC").getAsString());
                wifiDto.setMainNm(rowObject.get("X_SWIFI_MAIN_NM").getAsString());
                wifiDto.setAdres1(rowObject.get("X_SWIFI_ADRES1").getAsString());
                wifiDto.setAdres2(rowObject.get("X_SWIFI_ADRES2").getAsString());
                wifiDto.setInstlFloor(rowObject.get("X_SWIFI_INSTL_FLOOR").getAsString());
                wifiDto.setInstlTy(rowObject.get("X_SWIFI_INSTL_TY").getAsString());
                wifiDto.setInstlMby(rowObject.get("X_SWIFI_INSTL_MBY").getAsString());
                wifiDto.setSvcSe(rowObject.get("X_SWIFI_SVC_SE").getAsString());
                wifiDto.setCmcwr(rowObject.get("X_SWIFI_CMCWR").getAsString());
                wifiDto.setCnstcYear(rowObject.get("X_SWIFI_CNSTC_YEAR").getAsInt());
                wifiDto.setInoutDoor(rowObject.get("X_SWIFI_INOUT_DOOR").getAsString());
                wifiDto.setRemars3(rowObject.get("X_SWIFI_REMARS3").getAsString());
                wifiDto.setLat(rowObject.get("LAT").getAsDouble());
                wifiDto.setLnt(rowObject.get("LNT").getAsDouble());
                String workDttmStr = rowObject.get("WORK_DTTM").getAsString();
                Timestamp workDttmTs = Timestamp.valueOf(workDttmStr);
                wifiDto.setWorkDttm(workDttmTs);

                WifiDao wifiDao = new WifiDao();
                wifiDao.insert(wifiDto);
            }
            data += 1000;
        }
    }

}
