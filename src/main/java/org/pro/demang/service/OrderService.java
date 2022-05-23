package org.pro.demang.service;

import org.pro.demang.model.OrderDTO;

public interface OrderService {

	void newOrder(OrderDTO dto, String loginId );

	void paymentValidate(String ord_id);

	boolean paymentVerify(String imp_uid, String ord_id);
}
