package org.pro.demang.service;

import org.pro.demang.model.OrderDTO;

public interface OrderService {

	void newOrder(OrderDTO dto, String loginId );
}
