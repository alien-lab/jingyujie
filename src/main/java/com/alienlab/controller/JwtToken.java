package com.alienlab.controller;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by juhuiguang on 2017/4/10.
 */
public class JwtToken {
    @Value("${jwt.TTLMillis}")
    private Long TTLMillis;
    @Value("${jwt.audience}")
    private String audience;
    @Value("${jwt.base64Security}")
    private String base64Security;

    @RequestMapping(value="/getToken",method = RequestMethod.GET)
    public ResponseEntity getToken(){
        String token = JwtUtils.createJWT("zhuliang","123456","",audience,"test",TTLMillis,base64Security);
        JSONObject jo = new JSONObject();
        jo.put("token",token);
        return  ResponseEntity.ok().body(jo);
    }
}
