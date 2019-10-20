package com.gofun.model;

import lombok.Data;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/10/18
 */
@Data
public class UserOcrInfo {
    private Long id;
    private String cardImgF = "";
    private String cardImgB = "";
    private String license0 = "";
    private String licenseC = "";
    private String cardImgId = "";
    private String cardImgSex = "";
    private String cardImgName = "";
    private String cardImgBirthday = "";
    private String cardImgNation = "";
    private String cardAddr = "";
    private String licenseId = "";
    private String licenseName = "";
    private String licenseSex = "";
    private String licenseNation = "";
    private String licenseAddr = "";
}
