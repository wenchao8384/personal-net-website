package com.gofun.controller;

import com.alibaba.fastjson.JSON;
import com.gofun.mapper.gofun10.CarinfoMapper;
import com.gofun.mapper.gofun10.ParkingSpaceMapper;
import com.gofun.model.ParkingSpacePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/9/24
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private CarinfoMapper carinfoMapper;

    @Autowired
    private ParkingSpaceMapper parkingSpaceMapper;

    @RequestMapping("/test1")
    public String test1() {
        List<ParkingSpacePo> parkingSpacePos = parkingSpaceMapper.selectAllEmptyUse();
        System.out.println("EmptyUse数1：" + parkingSpacePos.size());
        StringBuffer stringBuffer = new StringBuffer();
        FileWriter fileWriter = null;
        BufferedWriter bw = null ;
        try {
            fileWriter = new FileWriter("test.txt");
            bw = new BufferedWriter(fileWriter);
            int i = 0;
            for (ParkingSpacePo parkingSpacePo : parkingSpacePos) {
                ParkingSpacePo parkingSpacePo1 = parkingSpaceMapper.selectAllEmptyUseByCarId(parkingSpacePo.getCarid());
                stringBuffer.append("curl 'http://10.0.15.2:9102/parkingEye/fixSpaceForCarOutbound?parkingId=")
                        .append(parkingSpacePo1.getParkingid())
                        .append("&carId=")
                        .append(parkingSpacePo1.getCarid())
                        .append("&spaceId=")
                        .append(parkingSpacePo1.getSpaceid())
                        .append("' -X GET");

                bw.write(stringBuffer.toString());
                bw.newLine();
                stringBuffer.delete(0,stringBuffer.length());
                System.out.println(++i);
                bw.flush();
            }
            bw.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    @RequestMapping("/test2")
    public String test2() {
        List<ParkingSpacePo> parkingSpacePos = parkingSpaceMapper.selectAllEmptyUse();

        return JSON.toJSONString(parkingSpacePos.size());
    }
}
