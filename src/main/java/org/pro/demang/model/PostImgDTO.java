package org.pro.demang.model;

import java.util.Base64;

import lombok.Data;

@Data
public class PostImgDTO {
	private int i_no;
	private int p_id;
	private byte[] i_image;
	

	//// 뷰에서 이미지 불러오기용: 바이트 배열의 이미지를 Base64를 통해 인코딩한 뒤 문자열로 반환. 뷰에서 타임리프로 <img th:src="'data:image/png;base64,'+${dto.m_profilePicString}">등으로 쓰면 된다.
	public String getImage() {
		return new String(Base64.getEncoder().encodeToString(i_image));
	}
}
