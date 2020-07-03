package com.wrh.wrhutil.action.page;

import com.alibaba.fastjson.JSONObject;
import com.wrh.wrhutil.config.aspect.ResponseData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/7/3 14:29
 * @describe
 */

@RestController
public class LoginAction {


    @PostMapping(value = "/login")
    public ResponseData login(String userName, String password){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accessToken","wrhaccessToken");
        return ResponseData.success();
    }

    @PostMapping(value = "/user/info")
    public ResponseData userInfo(String accessToken){
        return ResponseData.success();
    }




}
