package org.pro.demang.model;

import lombok.Data;

@Data
public class TagDTO {

	private String t_tag;
	private int t_postNo;
	private PostDTO postDTO;
	private MemberDTO memberDTO;
	private PostImgDTO postImgDTO;
	
}
