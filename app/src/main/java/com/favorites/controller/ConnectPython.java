package com.favorites.controller;

import com.favorites.comm.aop.LoggerManage;
import com.favorites.service.ConnectPythonService;
import com.favorites.service.impl.ConnectPythonServiceImpl;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/python")
public class ConnectPython {

    @Autowired
    ConnectPythonService pythonService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "/get")
    @LoggerManage(description = "文章收集")
    @ApiOperation(value = "分类器", tags = "文章分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "str", paramType = "body", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数错误"),
            @ApiResponse(code = 404, message = "接口路径不正确")
    })
    public List<String> getCollectClass(@RequestBody String str) {
        return pythonService.getResultList(str);
    }
//    @PostMapping(value = "/get")
//    @LoggerManage(description = "文章收集")
//    @ApiOperation(value = "分类器", tags = "文章分类")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "str", paramType = "body", required = true)
//    })
//    @ApiResponses({
//            @ApiResponse(code = 400, message = "请求参数错误"),
//            @ApiResponse(code = 404, message = "接口路径不正确")
//    })
//    public String getCollectClass(@RequestBody String str) {
//        return pythonService.getResult(str);
//    }

}
