package com.java.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.Result;


/**
 * @ClassName BusCfgTask
 * @Description 同步商户配置
 * @Author buyixuan
 * @Date 2018-12-28 15:38
 */

@Component
@Path("/busCfg")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/busCfg", tags = "更新商户配置", produces = MediaType.APPLICATION_JSON)
public class BusCfgResource {


    @POST
    @Path("/updateBusCfg")
    @ApiOperation(value = "更新商户配置", notes = "更新商户配置")
    @ApiResponses({@ApiResponse(code = 400, message = "失败"), @ApiResponse(code = 500, message = "服务器内部错误")})
    public Result updateBusCfg() {

        return null;
    }
}
