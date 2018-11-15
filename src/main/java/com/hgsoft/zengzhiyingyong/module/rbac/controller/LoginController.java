package com.hgsoft.zengzhiyingyong.module.rbac.controller;

import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.User;
import com.hgsoft.zengzhiyingyong.module.rbac.service.ModuleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by hegc on 2016/4/8.
 * 登录Controller
 */
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ModuleService moduleService;

    /**
     * 登录-请求转发到login.jsp
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 登录校验
     * @param username
     * @param password
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(required = true) String username,
                        @RequestParam(required = true)String password,
                        Model model) {

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        LoginToken token = new LoginToken(username, password, null);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return "redirect:/index";
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
            model.addAttribute("username", username);
            model.addAttribute("error", e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage());
            model.addAttribute("username", username);
            model.addAttribute("error", "系统发生错误！");
        }
        return "login";
    }

    /**
     * 登录后跳转的首页
     * @param model
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityContextHolder.USER_CONTEXT);
        System.out.println("用户Id ： " + user.getId());
        model.addAttribute("user", user);
        model.addAttribute("modules", moduleService.getMenus(user.getId()));

        return "home/index";
    }


    /**
     * 登出
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
//        SecurityContextHolder.destroy();
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    /**
     * 禁止访问
     * @return
     */
    @RequestMapping("/unauthorized")
    public String unauthorized() {
        return "error/403";
    }
}