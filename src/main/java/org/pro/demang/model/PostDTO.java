package org.pro.demang.model;

import java.io.File;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class PostDTO {

	private int p_id;
	private int p_origin;
	private String p_type;
	private int p_writer;
	private String p_content;
	private File p_image;
	private Timestamp p_regDate;
	
}
