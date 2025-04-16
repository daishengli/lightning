package top.daishengli.lightning.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.persistence.*;

/**
 * 用户
 *
 * @author daishengli
 */
@Data
@Entity
@Table(name = "users")
@Tag(name = "用户", description = "用户实体类")
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "主键")
    private Long id;

    @Column
    @Schema(description = "昵称")
    private String nickname;

    @Column
    @Schema(description = "邮箱")
    private String email;

    @Column
    @Schema(description = "openid")
    private String openid;

    @Column
    @Schema(description = "unionid")
    private String unionid;

    @Column
    @Schema(description = "头像")
    private String avatar;

    @Column
    @Schema(description = "是否是管理员")
    private Boolean adminStatus;
}
