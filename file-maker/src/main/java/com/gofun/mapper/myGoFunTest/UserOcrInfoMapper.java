package com.gofun.mapper.myGoFunTest;

import com.gofun.model.UserOcrInfo;

import java.util.List;

public interface UserOcrInfoMapper {
    int insert(UserOcrInfo record);

    List<UserOcrInfo> selectAll();

    List<UserOcrInfo> selectById(Integer id);
}
