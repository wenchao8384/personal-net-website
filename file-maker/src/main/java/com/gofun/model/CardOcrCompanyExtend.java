package com.gofun.model;

import lombok.Data;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/10/18
 */
@Data
public class CardOcrCompanyExtend {
    private Long id;
    private String userOcrInfoId;
    private String cardImgId ;
    private String cardImgSex ;
    private String cardImgName ;
    private String cardImgBirthday ;
    private String cardImgNation ;
    private String cardAddr ;
    private String cardValidateDate;
    private String cardIssueAuthority ;
    private String companyType ;
}
