package com.gofun.model;

import lombok.Data;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/10/18
 */
@Data
public class LicenseOcrCompanyExtend {
    private Long id;
    private String userOcrInfoId;
    private String licenseId ;
    private String licenseName ;
    private String licenseSex ;
    private String licenseNation ;
    private String licenseAddr ;
    private String licenseType ;
    private String licenseBirthday ;
    private String licenseIssueDate ;
    private String licenseDriveType ;
    private String licenseValidPeriod ;
    private String licenseValidPeriodFrom ;
    private String licenseFileNumber ;
    private String companyType ;
}
