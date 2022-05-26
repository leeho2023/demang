package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.MerchandiseDTO;
import org.pro.demang.model.OrderDTO;

public interface OrderService {
	
	public MerchandiseDTO getMerchandise( int mer_id );
	public List<MerchandiseDTO> getMerchandiseList( int mer_id );
	
	void newOrder(OrderDTO dto, int loginId );

	void paymentValidate(String ord_id);

	boolean paymentVerify(String imp_uid, String ord_id);
}
