package org.pro.demang.model;

//// 회원 정보 + 팔로잉 수 + 팔로워 수

public class MemberDTO_ext {
	private MemberDTO member;
	private int followingCount;
	private int followerCount;
	
	
	
	public MemberDTO getMember() {
		return member;
	}



	public void setMember(MemberDTO member) {
		this.member = member;
	}



	public int getFollowingCount() {
		return followingCount;
	}



	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}



	public int getFollowerCount() {
		return followerCount;
	}



	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}



	public MemberDTO_ext(MemberDTO member, int followingCount, int followerCount) {
		super();
		this.member = member;
		this.followingCount = followingCount;
		this.followerCount = followerCount;
	}
}
