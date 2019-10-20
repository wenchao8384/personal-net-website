package com.gofun.model;

import lombok.Data;

import java.util.List;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/10/18
 */
@Data
public class HeheJSONResult {
    private String rotated_image_height;
    private String image_angle;
    private String rotated_image_width;
    private List<OcrDataJSON> ocr_data_list;
    private String type;
}
