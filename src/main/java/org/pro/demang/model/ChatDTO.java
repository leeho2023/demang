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
	

	public ChatDTO(int h_speaker, int h_listener, String h_content) {
		this.h_speaker = h_speaker;
		this.h_listener = h_listener;
		this.h_content = h_content;
	}
}
