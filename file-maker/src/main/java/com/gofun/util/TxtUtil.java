package com.gofun.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/10/18
 */
public class TxtUtil {
    public static String[] toArrayByFileReader1(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(TxtUtil.class.getClassLoader().getResource(name).getPath());
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            String s = arrayList.get(i);
            //System.out.println(s.split(" "));
            array[i] = s;
        }
        // 返回数组
        return array;
    }

    public static void main(String[] args) {
        toArrayByFileReader1("file/OCR.txt");
    }
}
