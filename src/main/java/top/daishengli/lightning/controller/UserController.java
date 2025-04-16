package top.daishengli.lightning.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import top.daishengli.lightning.common.BaseController;
import top.daishengli.lightning.common.CommonPageRequest;
import top.daishengli.lightning.common.CommonPageResponse;
import top.daishengli.lightning.common.CommonResponse;
import top.daishengli.lightning.entity.User;
import top.daishengli.lightning.service.UserService;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 用户相关API
 *
 * @author daishengli
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户相关API")
public class UserController extends BaseController {

    @Resource
    UserService userService;

    @Operation(summary = "获取当前用户")
    @GetMapping("/getCurrentUser")
    @SaCheckLogin
    public CommonResponse<User> getCurrentUser() {
        String openid = StpUtil.getLoginIdAsString();
        User user = userService.findUserByOpenid(openid);
        return this.success(user);
    }

    @Operation(summary = "更新用户信息")
    @PostMapping("/updateUserInfo")
    @SaCheckLogin
    public CommonResponse<User> updateUserInfo(@RequestBody User user) {
        if (StrUtil.isEmpty(user.getOpenid())) {
            return this.failed("未更新用户信息！");
        }
        Optional<User> dbUser = userService.findById(user.getId());
        if (dbUser.isEmpty()) {
            return this.failed("未更新用户信息！");
        }
        if (!dbUser.get().getOpenid().equals(user.getOpenid())) {
            return this.failed("未更新用户信息！");
        }
        user.setAdminStatus(false);
        User save = userService.save(user);
        return this.success(save);
    }

    @Operation(summary = "获取验证码", parameters = {
            @Parameter(name = "email", description = "邮箱", required = true)
    })
    @GetMapping("/getCode")
    public CommonResponse<Boolean> getCode(String email) {
        if (!Validator.isEmail(email)) {
            return this.failed("邮箱格式不正确！");
        }
        Boolean code = userService.sendCode(email);
        return this.success(code);
    }

    @Operation(summary = "验证码登录", parameters = {
            @Parameter(name = "email", description = "邮箱", required = true),
            @Parameter(name = "code", description = "验证码", required = true)
    })
    @PostMapping("/codeLogin")
    public CommonResponse<SaTokenInfo> codeLogin(String email, String code) {
        if (!Validator.isEmail(email)) {
            return this.failed("邮箱格式不正确！");
        }
        if (!Validator.isNumber(code)) {
            return this.failed("验证码格式不正确！");
        }
        SaTokenInfo tokenInfo = userService.codeLogin(email, code);
        if (ObjUtil.isEmpty(tokenInfo)) {
            return this.failed("验证码错误");
        }
        return this.success(tokenInfo);
    }

    @PostMapping("/logout")
    @Operation(summary = "退出登录")
    public CommonResponse<Boolean> logout() {
        StpUtil.logout();
        return this.success(Boolean.TRUE);
    }

    @Operation(summary = "分页获取所有用户")
    @PostMapping("/pageList")
    @SaCheckLogin
    @SaCheckRole("admin")
    public CommonResponse<CommonPageResponse<User>> pageList(@RequestBody CommonPageRequest<User> queryEntity) {
        CommonPageResponse<User> result = userService.pageList(queryEntity);
        return this.success(result);
    }

    @Operation(summary = "新增用户")
    @PostMapping("/add")
    @SaCheckLogin
    @SaCheckRole("admin")
    public CommonResponse<User> add(@RequestBody User entity) {
        User result = userService.add(entity);
        return this.success(result);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/delete")
    @SaCheckLogin
    @SaCheckRole("admin")
    public CommonResponse<Boolean> delete(@RequestBody User entity) {
        userService.delete(entity);
        return this.success(Boolean.TRUE);
    }

    @Operation(summary = "修改用户")
    @PutMapping("/edit")
    @SaCheckLogin
    @SaCheckRole("admin")
    public CommonResponse<User> edit(@RequestBody User entity) {
        User result = userService.edit(entity);
        return this.success(result);
    }

    @Operation(summary = "获取用户信息", parameters = {
            @Parameter(name = "id", description = "用户ID", required = true)
    })
    @GetMapping("/{id}")
    @SaCheckLogin
    @SaCheckRole("admin")
    public CommonResponse<User> getUser(@PathVariable(value = "id") Long id) {
        User user = userService.findById(id).orElse(null);
        return this.success(user);
    }

}
