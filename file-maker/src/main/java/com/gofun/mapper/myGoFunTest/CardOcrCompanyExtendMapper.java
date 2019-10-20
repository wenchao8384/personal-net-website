package com.gofun.mapper.myGoFunTest;

import com.gofun.model.CardOcrCompanyExtend;

import java.util.List;

public interface CardOcrCompanyExtendMapper {
    int insert(CardOcrCompanyExtend record);

    List<CardOcrCompanyExtend> selectAll();

    List<CardOcrCompanyExtend> selectById(Integer id);

    List<CardOcrCompanyExtend> selectByMainId(Integer id);
}
