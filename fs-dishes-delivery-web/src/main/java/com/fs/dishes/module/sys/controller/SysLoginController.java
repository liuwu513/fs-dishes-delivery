package com.fs.dishes.module.sys.controller;

import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.utils.ShiroUtils;
import com.fs.dishes.module.sys.entity.SysUser;
import com.fs.dishes.module.sys.service.SysUserService;
import com.fs.dishes.module.sys.service.SysUserTokenService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 *
 * Created by liuwu on 2018/2/28 0028.
 */
@Api(description = "系统登录相关接口")
@RestController
public class SysLoginController {
    @Autowired
    private Producer producer;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    @ApiOperation(value = "验证码图片", notes = "验证码图片", httpMethod = "GET")
    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @ApiOperation(value = "系统登录", notes = "系统登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "验证码", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public ResResult login(String username, String password, String captcha) throws IOException {
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if (!captcha.equalsIgnoreCase(kaptcha)) {
            return ResResult.error(111, "验证码不正确");
        }
        //用户信息
        SysUser user = sysUserService.queryByUserName(username);

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
            return ResResult.error(111, "账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return ResResult.error(111, "账号已被锁定,请联系管理员");
        }
        //生成token，并保存到数据库
        ResResult r = sysUserTokenService.createToken(user.getUserId());
        return r;
    }

}
