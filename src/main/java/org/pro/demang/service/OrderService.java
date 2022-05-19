package org.pro.demang.service;

import javax.servlet.http.HttpSession;

import org.pro.demang.model.OrderDTO;

public interface OrderService {

	void newOrder(OrderDTO dto, String loginId );
}
