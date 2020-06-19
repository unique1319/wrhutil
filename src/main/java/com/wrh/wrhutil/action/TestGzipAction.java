package com.wrh.wrhutil.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrh.wrhutil.GZIPUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import static com.wrh.wrhutil.GZIPUtil.compressByByte;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/5/28 13:48
 * @describe
 */

@RestController
@RequestMapping("/gzip")
public class TestGzipAction {


    @ApiOperation(value = "测试Gzip")
    @RequestMapping(value = "/grid", method = RequestMethod.GET)
    public String testGrid() throws JsonProcessingException {
        int len1 = 800;
        int len2 = 800;
        float[][] grid = new float[len1][len2];
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                grid[i][j] = 999999.1f;
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] data = objectMapper.writeValueAsBytes(grid);

//        return new String(data);
        return GZIPUtil.compressByStr(new String(data));
    }

    @ApiOperation(value = "测试1")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数有误"),
            @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"),
            @ApiResponse(code = 404, message = "请求路径不存在"),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @GetMapping("/test")
    public String testDoc() {
        return "姓名";
    }


    @ApiOperation(value = "测试2")
    @GetMapping("/test2")
    public String testDoc2(String name) {
        return "姓名：" + name;
    }

}
