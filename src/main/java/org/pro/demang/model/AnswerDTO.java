package org.pro.demang.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AnswerDTO{
    private int a_no;
    private int c_id;
    private String a_content;
    private Timestamp a_regDate;

}
