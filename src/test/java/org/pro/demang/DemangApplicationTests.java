package org.pro.demang;

import org.junit.jupiter.api.Test;
import org.pro.demang.mapper.MainMapper;
import org.pro.demang.service.MemberService;
import org.pro.demang.service.OrderService;
import org.pro.demang.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class DemangApplicationTests {

    @Autowired
    private MemberService memberService;
    @Autowired
	PostService postService;
    @Autowired
    OrderService orderService;
	@Autowired
	private MainMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void followingNum() {
		System.out.println( mapper.followingCount(1) );
	}

}
