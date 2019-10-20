package com.gofun.mapper.gofun10;

import com.gofun.model.CarinfoPo;
import java.util.List;

public interface CarinfoMapper {
    int insert(CarinfoPo record);

    List<CarinfoPo> selectAll();
}