package com.zhifei.cloud.tools;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 封装响应工具类
 *
 * @author He Zhifei
 * @date 2020/7/18 0:27
 */
public class ResponseTools {

    /**
     * 把JSON数据写回response
     * @param response
     * @param object
     */
    public static void writeJson(HttpServletResponse response, Object object) {
        response.setContentType("application/json;charset=UTF-8");
        try (
                PrintWriter writer = response.getWriter();
                ) {
            writer.write(object instanceof String ? (String) object : JacksonTools.toJson(object));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
