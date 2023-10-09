package com.pzy.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 */
@Component
@ConfigurationProperties(prefix = "pzy")
public class PzyConfig {

    /** 上传图片路径 */
    private static String uploadImgAbsolutePath;
    /** 上传视频路径 */
    private static String uploadVideoAbsolutePath;
    /** 保存到数据库图片路径 */
    private static String accessImgPath;
    /** 保存到数据库视频路径 */
    private static String accessVideoPath;
    /** 隐藏的前缀路径 */
    private static String prefixPath;
    /** 获取地址开关 */
    private static boolean addressEnabled;


    public static String getUploadImgAbsolutePath(){
        return uploadImgAbsolutePath;
    }

    public static String getUploadVideoAbsolutePath(){
        return uploadVideoAbsolutePath;
    }

    public static String getAccessImgPath(){
        return accessImgPath;
    }

    public static String getAccessVideoPath(){
        return accessVideoPath;
    }

    public static String getPrefixPath(){
        return prefixPath;
    }


    public void setUploadImgAbsolutePath(String uploadImgAbsolutePath){
        PzyConfig.uploadImgAbsolutePath = uploadImgAbsolutePath;
    }

    public void setUploadVideoAbsolutePath(String uploadVideoAbsolutePath){
        PzyConfig.uploadVideoAbsolutePath = uploadVideoAbsolutePath;
    }

    public void setAccessImgPath(String accessImgPath){
        PzyConfig.accessImgPath = accessImgPath;
    }

    public void setAccessVideoPath(String accessVideoPath){
        PzyConfig.accessVideoPath = accessVideoPath;
    }

    public void setPrefixPath(String prefixPath){
        PzyConfig.prefixPath = prefixPath;
    }


    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        PzyConfig.addressEnabled = addressEnabled;
    }

}
