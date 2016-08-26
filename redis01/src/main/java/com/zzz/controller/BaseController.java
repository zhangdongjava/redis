package com.zzz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by dell_2 on 2016/8/26.
 */
public class BaseController {
    ObjectMapper objectMapper = new ObjectMapper();

    public String buildParentCallback(Object content, String funcName) throws JsonProcessingException {
        String contentStr = objectMapper.writeValueAsString(content);
        StringBuilder builder = new StringBuilder();
        builder.append("<script>");
        builder.append("parent.");
        builder.append(funcName);
        builder.append("(");
        builder.append(contentStr);
        builder.append(")");
        builder.append("</script>");
        return builder.toString();
    }
    public String buildParentCallback(String content, String funcName)  {
        StringBuilder builder = new StringBuilder();
        builder.append("<script>");
        builder.append("parent.");
        builder.append(funcName);
        builder.append("(");
        builder.append(content);
        builder.append(")");
        builder.append("</script>");
        return builder.toString();
    }

    public String ObjectToJson(Object o) throws JsonProcessingException {
        return  objectMapper.writeValueAsString(o);
    }

}
