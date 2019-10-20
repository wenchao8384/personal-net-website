package com.gofun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gofun.mapper.myGoFunTest.CardOcrCompanyExtendMapper;
import com.gofun.mapper.myGoFunTest.LicenseOcrCompanyExtendMapper;
import com.gofun.mapper.myGoFunTest.UserOcrInfoMapper;
import com.gofun.model.*;
import com.gofun.util.HttpUtil;
import com.gofun.util.MyImageUtil;
import com.gofun.util.TxtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/10/15
 */
@RestController
@RequestMapping("/ocrTest")
public class OcrTestController {

    @Autowired
    private UserOcrInfoMapper userOcrInfoMapper;
    @Autowired
    private CardOcrCompanyExtendMapper cardOcrCompanyExtendMapper;
    @Autowired
    private LicenseOcrCompanyExtendMapper licenseOcrCompanyExtendMapper;

    @RequestMapping("/xinhuoOcrTest")
    public String xinhuoOcrTest(){
        System.out.println("xinhuoOcrTest方法被调用");
        try {
            HttpUtil.post(new URL("url"), MyImageUtil.imgFileToBase64("file/1.jpg"));
            return "success" ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "fail" ;
        }
    }


    @RequestMapping("/httpTest")
    public String httpTest(){
        System.out.println("httpTest方法被调用");
        try {
            HttpUtil.post(new URL("http://10.19.11.8:9101/activityForOrder/getEstimateReduceByActivity"), "{\"activityVersionId\": \"48b06255791e48babe6f6a5d9a46343c\",\"baseAmount\": 13.5,\"carTypeId\": \"c7b51d01-07b6-486a-aff0-ef28bc3f46e5\",\"cityCode\": \"010\",\"energy\": 2,\"orderEndTime\": 1570786492,\"orderMileage\": 2,\"orderMinutes\": 7,\"orderStartTime\": 1570786072,\"orderType\": 0,\"returnParkingId\": \"X000001115\",\"returnParkingKind\": 1,\"takeParkingId\": \"2280654bc076470187485c3b30b59eff\",\"takeParkingKind\": 1,\"userId\": \"b9f256f642804f789a71292cedeba73c\"}");
            return "success" ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "fail" ;
        }
    }


    @RequestMapping("/sqlTest")
    public String sqlTest(){
        return  (JSON.toJSONString(cardOcrCompanyExtendMapper.selectAll()));
    }

    @RequestMapping("/sqlTest1")
    public String sqlTest1(){
        CardOcrCompanyExtend cardOcrCompanyExtend = new CardOcrCompanyExtend();
        cardOcrCompanyExtend.setCardAddr("12331");
        cardOcrCompanyExtend.setCardImgBirthday("123");
        cardOcrCompanyExtend.setCardImgId("1231");
        cardOcrCompanyExtend.setCardImgName("4414");
        cardOcrCompanyExtend.setCardImgNation("4123123");
        cardOcrCompanyExtend.setCardImgSex("41231");
        cardOcrCompanyExtend.setUserOcrInfoId("1");
        cardOcrCompanyExtend.setCompanyType("xxxxxxxxxxxx");
        return  (JSON.toJSONString(cardOcrCompanyExtendMapper.insert(cardOcrCompanyExtend)));
    }

    @RequestMapping("/sqlTest2")
    public String sqlTest2(){
        return  (JSON.toJSONString(licenseOcrCompanyExtendMapper.selectAll()));
    }

    @RequestMapping("/sqlTest3")
    public String sqlTest3(){
        String result = "{\"cost_time\":1549,\"result\":{\"rotated_image_height\":494,\"image_angle\":0,\"rotated_image_width\":720,\"ocr_data_list\":[{\"value\":\"肖滢滢\",\"key\":\"name\",\"description\":\"姓名\"},{\"value\":\"中华人民共和国机动车驾驶证\",\"key\":\"type\",\"description\":\"类型\"},{\"value\":\"350502199903231020\",\"key\":\"driving_license_main_number\",\"description\":\"驾驶证证号\"},{\"value\":\"女\",\"key\":\"sex\",\"description\":\"性别\"},{\"value\":\"中国\\/CHN\",\"key\":\"driving_license_main_nationality\",\"description\":\"国籍\"},{\"value\":\"福建省泉州市丰泽区宝洲路208号宝州花园A10幢201室\",\"key\":\"address\",\"description\":\"住址\"},{\"value\":\"1999-03-23\",\"key\":\"birthday\",\"description\":\"出生日期\"},{\"value\":\"2019-10-14\",\"key\":\"issue_date\",\"description\":\"初次领证日期\"},{\"value\":\"C1\",\"key\":\"drive_type\",\"description\":\"准驾车型\"},{\"value\":\"2019-10-14至2025-10-14\",\"key\":\"valid_period_from\",\"description\":\"有限期始(至)\"}],\"type\":\"drive_license\"},\"code\":200,\"message\":\"success\"}";
        //字符串转json
        JSONObject jsonObject = JSONObject.parseObject(result);
        HeheJSON heheJSON = JSONObject.parseObject(result, HeheJSON.class);
        List<OcrDataJSON> ocr_data_list = heheJSON.getResult().getOcr_data_list();

        LicenseOcrCompanyExtend licenseOcrCompanyExtend = new LicenseOcrCompanyExtend();
        licenseOcrCompanyExtend.setUserOcrInfoId("123");

        ocr_data_list.forEach((s)->{
            switch (s.getKey()){
                case  "type" :
                    licenseOcrCompanyExtend.setLicenseType(s.getValue());
                    break ;
                case  "driving_license_main_number" :
                    licenseOcrCompanyExtend.setLicenseId(s.getValue());
                    break ;
                case  "name" :
                    licenseOcrCompanyExtend.setLicenseName(s.getValue());
                    break ;
                case  "sex" :
                    licenseOcrCompanyExtend.setLicenseSex(s.getValue());
                    break ;
                case  "driving_license_main_nationality" :
                    licenseOcrCompanyExtend.setLicenseNation(s.getValue());
                    break ;
                case  "address" :
                    licenseOcrCompanyExtend.setLicenseAddr(s.getValue());
                    break ;
                case  "birthday" :
                    licenseOcrCompanyExtend.setLicenseBirthday(s.getValue());
                    break ;
                case  "issue_date" :
                    licenseOcrCompanyExtend.setLicenseIssueDate(s.getValue());
                    break ;
                case  "drive_type" :
                    licenseOcrCompanyExtend.setLicenseDriveType(s.getValue());
                    break ;
                case  "valid_period" :
                    licenseOcrCompanyExtend.setLicenseValidPeriod(s.getValue());
                    break ;
                case  "valid_period_from" :
                    licenseOcrCompanyExtend.setLicenseValidPeriodFrom(s.getValue());
                    break ;
                case  "file_number" :
                    licenseOcrCompanyExtend.setLicenseFileNumber(s.getValue());
                    break ;

            }
            licenseOcrCompanyExtend.setCompanyType("hehe");
        });
        return  (JSON.toJSONString(licenseOcrCompanyExtendMapper.insert(licenseOcrCompanyExtend)));
    }

    @RequestMapping("/toDb")
    public String toDb(){
        String[] strings = TxtUtil.toArrayByFileReader1("file/OCR.txt");
        for (int i = 0; i < strings.length; i++) {
            String[] s = strings[i].replace("\"", "").split("\t");
            if(s.length > 13){
                System.out.println(s[4]);
            UserOcrInfo userOcrInfo = new UserOcrInfo();
            userOcrInfo.setCardImgF(s[0]);
            userOcrInfo.setCardImgB(s[1]);
            userOcrInfo.setLicense0(s[2]);
            userOcrInfo.setLicenseC(s[3]);
            userOcrInfo.setCardImgId(s[4]);
            userOcrInfo.setCardImgSex(s[5]);
            userOcrInfo.setCardImgName(s[6]);
            userOcrInfo.setCardImgBirthday(s[7]);
            userOcrInfo.setCardImgNation(s[8]);
            userOcrInfo.setLicenseId(s[9]);
            userOcrInfo.setLicenseName(s[10]);
            userOcrInfo.setLicenseSex(s[11]);
            userOcrInfo.setLicenseNation(s[12]);
            userOcrInfoMapper.insert(userOcrInfo);
            }
        }
        return String.valueOf(strings.length);
    }

    @RequestMapping("/heheOcrTest")
    public String heheOcrTest(){
        System.out.println("heheOcrTest方法被调用");
        String url = "https://ocr-api.ccint.com/ocr_service?app_key=%s";
        String appKey = "bf4f901f6706619cca688fdd1de41a11"; // your app_key
        String appSecret = "585b778509c759eb8d6d8ab6d083c490"; // your app_secret
        url = String.format(url, appKey);
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            String imgData = MyImageUtil.netImageToBase64("http://imgpub1.shouqiev.com/gofunapi/images/20191017/QnpBZgiUEK.jpg");
            String param="{\"app_secret\":\"%s\",\"image_data\":\"%s\"}";
            param=String.format(param,appSecret,imgData);
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // 设置请求方式
            conn.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的
            conn.connect();
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.append(param);
            out.flush();
            out.close();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(result);

        //字符串转json
        JSON.toJSON(result);
        return result;
    }

    public static String imageToBase64(String path)
    {
        String imgFile = path;
        InputStream in = null;
        byte[] data = null;
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    @RequestMapping("/saveCard")
    public static void saveCard(){
        String result = "{\"cost_time\":1549,\"result\":{\"rotated_image_height\":494,\"image_angle\":0,\"rotated_image_width\":720,\"ocr_data_list\":[{\"value\":\"肖滢滢\",\"key\":\"name\",\"description\":\"姓名\"},{\"value\":\"中华人民共和国机动车驾驶证\",\"key\":\"type\",\"description\":\"类型\"},{\"value\":\"350502199903231020\",\"key\":\"driving_license_main_number\",\"description\":\"驾驶证证号\"},{\"value\":\"女\",\"key\":\"sex\",\"description\":\"性别\"},{\"value\":\"中国\\/CHN\",\"key\":\"driving_license_main_nationality\",\"description\":\"国籍\"},{\"value\":\"福建省泉州市丰泽区宝洲路208号宝州花园A10幢201室\",\"key\":\"address\",\"description\":\"住址\"},{\"value\":\"1999-03-23\",\"key\":\"birthday\",\"description\":\"出生日期\"},{\"value\":\"2019-10-14\",\"key\":\"issue_date\",\"description\":\"初次领证日期\"},{\"value\":\"C1\",\"key\":\"drive_type\",\"description\":\"准驾车型\"},{\"value\":\"2019-10-14至2025-10-14\",\"key\":\"valid_period_from\",\"description\":\"有限期始(至)\"}],\"type\":\"drive_license\"},\"code\":200,\"message\":\"success\"}";
        //字符串转json
        JSONObject jsonObject = JSONObject.parseObject(result);
        HeheJSON heheJSON = JSONObject.parseObject(result, HeheJSON.class);
        System.out.println(heheJSON);
    }


    public void analysisHeHeJSON(){
        String result = "{\"cost_time\":1549,\"result\":{\"rotated_image_height\":494,\"image_angle\":0,\"rotated_image_width\":720,\"ocr_data_list\":[{\"value\":\"肖滢滢\",\"key\":\"name\",\"description\":\"姓名\"},{\"value\":\"中华人民共和国机动车驾驶证\",\"key\":\"type\",\"description\":\"类型\"},{\"value\":\"350502199903231020\",\"key\":\"driving_license_main_number\",\"description\":\"驾驶证证号\"},{\"value\":\"女\",\"key\":\"sex\",\"description\":\"性别\"},{\"value\":\"中国\\/CHN\",\"key\":\"driving_license_main_nationality\",\"description\":\"国籍\"},{\"value\":\"福建省泉州市丰泽区宝洲路208号宝州花园A10幢201室\",\"key\":\"address\",\"description\":\"住址\"},{\"value\":\"1999-03-23\",\"key\":\"birthday\",\"description\":\"出生日期\"},{\"value\":\"2019-10-14\",\"key\":\"issue_date\",\"description\":\"初次领证日期\"},{\"value\":\"C1\",\"key\":\"drive_type\",\"description\":\"准驾车型\"},{\"value\":\"2019-10-14至2025-10-14\",\"key\":\"valid_period_from\",\"description\":\"有限期始(至)\"}],\"type\":\"drive_license\"},\"code\":200,\"message\":\"success\"}";
        //字符串转json
        JSONObject jsonObject = JSONObject.parseObject(result);
        HeheJSON heheJSON = JSONObject.parseObject(result, HeheJSON.class);
        List<OcrDataJSON> ocr_data_list = heheJSON.getResult().getOcr_data_list();

        LicenseOcrCompanyExtend licenseOcrCompanyExtend = new LicenseOcrCompanyExtend();

        ocr_data_list.forEach((s)->{
            switch (s.getKey()){
                case  "type" :
                    licenseOcrCompanyExtend.setLicenseType(s.getValue());
                    break ;
                case  "driving_license_main_number" :
                    licenseOcrCompanyExtend.setLicenseId(s.getValue());
                    break ;
                case  "name" :
                    licenseOcrCompanyExtend.setLicenseName(s.getValue());
                    break ;
                case  "sex" :
                    licenseOcrCompanyExtend.setLicenseSex(s.getValue());
                    break ;
                case  "driving_license_main_nationality" :
                    licenseOcrCompanyExtend.setLicenseNation(s.getValue());
                    break ;
                case  "address" :
                    licenseOcrCompanyExtend.setLicenseAddr(s.getValue());
                    break ;
                case  "birthday" :
                    licenseOcrCompanyExtend.setLicenseBirthday(s.getValue());
                    break ;
                case  "issue_date" :
                    licenseOcrCompanyExtend.setLicenseIssueDate(s.getValue());
                    break ;
                case  "drive_type" :
                    licenseOcrCompanyExtend.setLicenseDriveType(s.getValue());
                    break ;
                case  "valid_period" :
                    licenseOcrCompanyExtend.setLicenseValidPeriod(s.getValue());
                    break ;
                case  "valid_period_from" :
                    licenseOcrCompanyExtend.setLicenseValidPeriodFrom(s.getValue());
                    break ;
                case  "file_number" :
                    licenseOcrCompanyExtend.setLicenseFileNumber(s.getValue());
                    break ;

            }
        });

        CardOcrCompanyExtend cardOcrCompanyExtend = new CardOcrCompanyExtend();
        ocr_data_list.forEach((s)->{
            switch (s.getKey()){
                case  "name" :
                    cardOcrCompanyExtend.setCardImgName(s.getValue());
                    break ;
                case  "sex" :
                    cardOcrCompanyExtend.setCardImgSex(s.getValue());
                    break ;
                case  "nationality" :
                    cardOcrCompanyExtend.setCardImgNation(s.getValue());
                    break ;
                case  "birth" :
                    cardOcrCompanyExtend.setCardImgBirthday(s.getValue());
                    break ;
                case  "address" :
                    cardOcrCompanyExtend.setCardAddr(s.getValue());
                    break ;
                case  "id_number" :
                    cardOcrCompanyExtend.setCardImgId(s.getValue());
                    break ;
                case  "validate_date" :
                    cardOcrCompanyExtend.setCardValidateDate(s.getValue());
                    break ;
                case  "issue_authority" :
                    cardOcrCompanyExtend.setCardIssueAuthority(s.getValue());
                    break ;
            }
        });
    }

    public static void main(String[] args) {
        saveCard();
    }
}
