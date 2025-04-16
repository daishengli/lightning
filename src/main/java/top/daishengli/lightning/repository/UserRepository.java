package top.daishengli.lightning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import top.daishengli.lightning.entity.User;

/**
 * 用户数据库操作接口
 *
 * @author daishengli
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     * 通过用户openid获取用户信息
     *
     * @param openid 用户openid
     * @return 用户信息
     */
    User findUserByOpenid(String openid);

    /**
     * 通过用户email获取用户信息
     *
     * @param email 用户email
     * @return 用户信息
     */
    User findUserByEmail(String email);
}
