package org.pro.demang.model;

import lombok.Data;

//// 회원 정보 + 팔로잉 수 + 팔로워 수
@Data
public class MemberDTO_ext {
	private MemberDTO member;
	private int followingCount;
	private int followerCount;
	
	public MemberDTO_ext(MemberDTO member, int followingCount, int followerCount) {
		super();
		this.member = member;
		this.followingCount = followingCount;
		this.followerCount = followerCount;
	}
}
