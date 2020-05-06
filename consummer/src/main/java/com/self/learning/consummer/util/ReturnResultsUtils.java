package com.self.learning.consummer.util;

import org.springframework.stereotype.Component;

@Component
public class ReturnResultsUtils {
    /**
     * 不带数据的返回成功
     * @return
     */
    public static ReturnResults returnSuccess(){
        ReturnResults returnResults = new ReturnResults();
        returnResults.setCode(1);
        returnResults.setMessage("success");
        return returnResults;
    }

    /**
     * 带数据的返回成功
     * @return
     */
    public static ReturnResults returnSuccesss(Object data){
        ReturnResults returnResults = new ReturnResults();
        returnResults.setCode(1);
        returnResults.setMessage("success");
        returnResults.setData(data);
        return returnResults;
    }

    /**
     * 返回失败
     * @return
     */
    public static ReturnResults returnFail(int code, String msg){
        ReturnResults returnResults = new ReturnResults();
        returnResults.setCode(code);
        returnResults.setMessage(msg);
        return returnResults;
    }

    /**
     * 自定义返回
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static ReturnResults returnDefined(int code,String msg,Object data){
        ReturnResults returnResults = new ReturnResults();
        returnResults.setCode(code);
        returnResults.setMessage(msg);
        returnResults.setData(data);
        return returnResults;
    }
}
