package org.pro.demang.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ContactUsDTO {
    private int c_id;
    private String m_email;
    private String c_contentTitle;
    private String c_contactUsValues;
    private String c_content;
    private Timestamp c_regDate;
    private MemberDTO memberDTO;
    private ContactUsImgDTO contactUsImgDTO;

}
