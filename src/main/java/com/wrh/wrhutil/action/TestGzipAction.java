package com.wrh.wrhutil.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrh.wrhutil.GZIPUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/grid")
    public String testGrid() throws JsonProcessingException {
        int len1 = 500;
        int len2 = 300;
        float[][] grid = new float[len1][len2];
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                grid[i][j] = 999999.1f;
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] data = objectMapper.writeValueAsBytes(grid);

//        return data;
        return GZIPUtil.compressByStr(new String(data));
    }

}
