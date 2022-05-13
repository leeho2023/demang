package org.pro.demang.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
	private int h_id;
	private int h_speaker;
	private int h_listener;
	private String h_content;
	private Timestamp h_datetime;
	
	
	public ChatDTO( String m1, String m2, String h_content ) {
		this.h_speaker = Integer.parseInt(m1);
		this.h_listener = Integer.parseInt(m2);
		this.h_content = h_content;
	}
}
