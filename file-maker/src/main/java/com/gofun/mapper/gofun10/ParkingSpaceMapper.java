package com.gofun.mapper.gofun10;

import com.gofun.model.ParkingSpacePo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParkingSpaceMapper {
    int insert(ParkingSpacePo record);

    List<ParkingSpacePo> selectAll();

    List<ParkingSpacePo> selectAllEmptyUse();

    List<ParkingSpacePo> selectAllEmptyUseByCarIdList(@Param("carIds") List<String> carIds );

    ParkingSpacePo selectAllEmptyUseByCarId(@Param("carId") String carId );
}