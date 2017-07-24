package com.m520it.testim.util;

import com.m520it.testim.common.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class RYUtils {
//    App-Key 或 RC-App-Key	String	开发者平台分配的 App Key。
//    Nonce或 RC-Nonce	String	随机数，无长度限制。
//    Timestamp 或 RC-Timestamp	String	时间戳，从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的秒数。
//    Signature 或 RC-Signature	String	数据签名。
    private static String createNonce(){
        Random rand = new Random();
        int i = rand.nextInt(10000);
        return i + "";
    }

    private static String createTimestamp(){
        return System.currentTimeMillis()+"";
    }

    private static String sha1(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for(int i=0;i<bits.length;i++){
            int a = bits[i];
            if(a<0) a+=256;
            if(a<16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }

    private static String createSignature(String nonce,String timeStamp){
        String s = Constants.RY_APP_SECRET + nonce + timeStamp;
        try {
            s = sha1(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static List<OkHttpUtils.Param> createHeader(){

        String nonce = createNonce();
        String timeStamp = createTimestamp();
        String signature = createSignature(nonce,timeStamp);

        List<OkHttpUtils.Param> headers = new ArrayList<>();
        headers.add(new OkHttpUtils.Param("App-Key",Constants.RY_APP_KEY));
        headers.add(new OkHttpUtils.Param("Nonce",nonce));
        headers.add(new OkHttpUtils.Param("Timestamp", timeStamp));
        headers.add(new OkHttpUtils.Param("Signature",signature));
        return headers;
    }

}
