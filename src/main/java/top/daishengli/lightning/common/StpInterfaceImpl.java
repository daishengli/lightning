package top.daishengli.lightning.common;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.util.ObjUtil;
import org.springframework.stereotype.Component;
import top.daishengli.lightning.entity.User;
import top.daishengli.lightning.service.UserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 *
 * @author daishengli
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    UserService userService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        User user = userService.findUserByOpenid(String.valueOf(loginId));
        if (ObjUtil.isNotEmpty(user)) {
            List<String> list = new ArrayList<>();
            if (user.getAdminStatus()) {
                list.add("admin");
            }
            return list;
        }
        return null;
    }
}
