package seoulpublicwifi.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkGroupDto {
	private int id;             // 번호
	private String groupNm;     // 그룹명
	private int seq;            // 순서
	private Timestamp regDttm;  // 등록일자
	private Timestamp uptDttm;  // 수정일자
	
}
