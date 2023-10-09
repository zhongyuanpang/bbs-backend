package com.pzy.common.httpresult;


/**
 * 公共返回码
 * */
public enum ResultCode {
    SUCCESS(true,200,"操作成功！"),
    // 资源未找到
    NOT_FOUND(false, 404, "not found"),
    //参数错误
    PARAM_ERROR(false, 400, "参数错误！"),

    // 1000～1999 区间表示CRUD错误
    GET_NULL_ERROR(false, 1000, "get null error"),
    DATA_IS_NULL(false, 1001, "data is null"),
    ADD_ERROR(false, 1002, "add error"),
    MODIFY_ERROR(false, 1003, "modify error"),
    DEL_ERROR(false, 1004, "del error"),
    IS_EXIST(false, 1005,"该字段值已存在！"),
    EXPORT_ERROR(false, 1006, "导出失败"),
    EXPORT_REPEAT(false, 1007, "不能重复导出"),
    IMPORT_ERROR(false, 1008, "导入失败"),
    SAVE_ERROR(false, 1009, "保存失败"),

    FAIL(false,10001,"操作失败"),
    UNAUTHENTICATED(false,10002,"您还未登录"),
    UNAUTHORISE(false,10003,"权限不足"),
    LOGIN_FAIL(false, 10004, "登陆失败"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),
    SIGN_TOKEN_ISNOT_EXIST_FAIL(false, 10005, "token过期或不存在"),


    /**
     * CRUD成功请求返回信息 默认200
     */
    ADD_SUCCESS("添加成功"),
    DEL_SUCCESS("删除成功"),
    MODIFY_SUCCESS("修改成功"),
    QUERY_SUCCESS("查询成功"),
    IMPORT_SUCCESS("导入成功"),
    EXPORT_SUCCESS("导出成功"),
    UPLOAD_SUCCESS("上传成功"),
    LOGIN_SUCCESS("登录成功"),
    ;


    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    ResultCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    ResultCode(String msg) {
        this.message = msg;
        this.success = true;
        this.code = 200;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
