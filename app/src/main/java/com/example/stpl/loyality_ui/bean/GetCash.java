package com.example.stpl.loyality_ui.bean;

/**
 * Created by stpl on 23/3/18.
 */

public class GetCash {

    String description,message,respMsg;
    int code;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /*
    {
        "errorCode": 0, "result": {
        "code": 200,
                "description": "transaction successful",
                "message": "transaction successful"
    },

        "respMsg": "transaction successful"
    }
*/

}
