package seoulpublicwifi.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkDto {
	private int id;              // 번호
    private int groupId;         // 북마크 그룹 번호
    private String mgrNo;        // 와이파이 관리 번호
    private Timestamp regDttm;   // 등록일자

}
