package org.pro.demang;

import javax.servlet.http.HttpSession;

/* 이게 쓸모가 있을지 잘 모르겠음... */
public class Util {
	public static String loginId( HttpSession session ) {
		return session.getAttribute("login")+"";
	}
}
