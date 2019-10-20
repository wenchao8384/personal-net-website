package com.gofun.mapper.myGoFunTest;

import com.gofun.model.LicenseOcrCompanyExtend;

import java.util.List;

public interface LicenseOcrCompanyExtendMapper {
    int insert(LicenseOcrCompanyExtend record);

    List<LicenseOcrCompanyExtend> selectAll();

    List<LicenseOcrCompanyExtend> selectById(Integer id);

    List<LicenseOcrCompanyExtend> selectByMainId(Integer id);
}
