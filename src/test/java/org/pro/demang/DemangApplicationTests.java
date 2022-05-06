package org.pro.demang;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.service.MemberService;
import org.pro.demang.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemangApplicationTests {

    @Autowired
    private MemberService memberService;
    @Autowired
	PostService postService;

	@Test
	void contextLoads() {
		List<MemberDTO> list = memberService.fList(1);
		System.out.println("TESTTTTTTTTTTTTTTTTTTTT"+list);
		
	}

}
