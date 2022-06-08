package org.pro.demang.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentDTO {
	private int c_id;
	private int c_writer;
	private String c_content;
	private int c_postNo;
	private Timestamp c_regDate;
	private MemberDTO memberDTO;

	public void setC_writer(int c_writer) {
		this.c_writer = c_writer;
	}
	public void setC_writer(String c_writer) {
		this.c_writer = Integer.parseInt(c_writer);
	}
	
	
	public String getRegDate() {
		if( getC_regDate() == null ) return "";
		
		Timestamp now = new Timestamp( System.currentTimeMillis() );// 현재시각
		long gap = ( now.getTime() - getC_regDate().getTime() )/1000;// 시간차이 (초)
		
		
		if( gap < 60 ) // 1분 미만이면 초
			return gap+"초 전";
		if( gap < 3600 ) // 1시간 미만이면 분
			return gap/60+"분 전";
		
		//// 그 이상 시간차이는 그냥 시각 반환
		String str;
		if( now.getYear()+1900 != getYear() )// 년도 다름: 년부터
			return 
				getYear()+"년 "
				+getMonth()+"월 " 
				+ getC_regDate().getDate()+"일 "
				+getC_regDate().getHours()+":"
				+getC_regDate().getMinutes();
		if( now.getMonth()+1 != getMonth() )// 년도 같음, 월 다름: 월부터
			return 
				getMonth()+"월 " 
				+ getDate()+"일 "
				+getHours()+":"
				+getMinutes();
		if( now.getDate() != getDate() )// 년,월도 같음, 일 다름: 일부터
			return 
				getDate()+"일 "
				+getHours()+":"
				+getMinutes();
		else
			return 
				"오늘 "
				+getHours()+":"
				+getMinutes();
	}
	
	public int getYear() {
		return getC_regDate().getYear()+1900;
	}
	public int getMonth() {
		return getC_regDate().getMonth()+1;
	}
	public int getDate() {
		return getC_regDate().getDate();
	}
	public int getHours() {
		return getC_regDate().getHours();
	}
	public int getMinutes() {
		return getC_regDate().getMinutes();
	}
}
