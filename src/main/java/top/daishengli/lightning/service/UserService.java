package top.daishengli.lightning.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import top.daishengli.lightning.common.CommonPageRequest;
import top.daishengli.lightning.common.CommonPageResponse;
import top.daishengli.lightning.entity.User;
import top.daishengli.lightning.repository.UserRepository;
import top.daishengli.lightning.util.CommonUtil;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 用户业务
 *
 * @author daishengli
 */
@Service
public class UserService {

    @Resource
    UserRepository userRepository;

    @Resource
    CommonUtil commonUtil;

    @Resource
    TimedCache<String, Object> timedCache;

    /**
     * 验证码登录
     *
     * @param email 邮箱
     * @param code  验证码
     * @return token信息
     */
    public SaTokenInfo codeLogin(String email, String code) {
        Object cacheCode = timedCache.get(email);
        if (ObjUtil.isEmpty(cacheCode)) {
            return null;
        }
        if (!code.equals(cacheCode.toString())) {
            return null;
        }
        timedCache.remove(email);
        User user = userRepository.findUserByEmail(email);
        StpUtil.login(user.getOpenid());
        return StpUtil.getTokenInfo();
    }

    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @return 发送成功与否
     */
    public Boolean sendCode(String email) {
        User user = this.findUserByEmail(email);
        if (ObjUtil.isEmpty(user)) {
            return false;
        }
        return commonUtil.sendCode(email, 6);
    }

    /**
     * 通过openid查询用户信息
     *
     * @param openid openid
     * @return 用户信息
     */
    public User findUserByOpenid(String openid) {
        return userRepository.findUserByOpenid(openid);
    }

    /**
     * 通过邮箱查询用户信息
     *
     * @param email 邮箱
     * @return 用户信息
     */
    private User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    /**
     * 保存或更新用户信息
     *
     * @param user 用户信息
     * @return 用户信息
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * 获取用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 分页查询用户列表
     *
     * @param queryEntity 查询条件
     * @return 分页数据
     */
    public CommonPageResponse<User> pageList(CommonPageRequest<User> queryEntity) {
        User query = Optional.ofNullable(queryEntity.getQuery()).orElse(new User());
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (StrUtil.isNotBlank(query.getNickname())) {
                    predicateList.add(criteriaBuilder.like(root.get("nickname"), "%" + query.getNickname() + "%"));
                }
                if (StrUtil.isNotBlank(query.getEmail())) {
                    predicateList.add(criteriaBuilder.like(root.get("email"), "%" + query.getEmail() + "%"));
                }
                if (Optional.ofNullable(query.getAdminStatus()).isPresent()) {
                    predicateList.add(criteriaBuilder.equal(root.get("adminStatus").as(Boolean.class), query.getAdminStatus()));
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(predicates));
            }
        };
        return new CommonPageResponse<User>().of(userRepository.findAll(specification, queryEntity.getPageRequest()), queryEntity);
    }

    /**
     * 新增数据
     *
     * @param entity 实体
     * @return 新增数据
     */
    public User add(User entity) {
        return userRepository.save(entity);
    }

    /**
     * 删除数据
     *
     * @param entity 实体
     */
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    /**
     * 修改数据
     *
     * @param entity 实体
     * @return 修改数据
     */
    public User edit(User entity) {
        if (entity.getId() != null) {
            return userRepository.save(entity);
        }
        throw new RuntimeException("无数据ID");
    }

    /**
     * 查询数据
     *
     * @param id id
     * @return 数据
     */
    public User getOne(Long id) {
        return userRepository.findById(id).orElse(new User());
    }
}
