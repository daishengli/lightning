package top.daishengli.lightning.common;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页结果
 *
 * @author daishengli
 * @date 2024-09-19 00:36:50
 **/
public class CommonPageResponse<T> {
    /**
     * 页码
     */
    @Schema(description = "页码")
    private Integer page;

    /**
     * 每一页的数据量
     */
    @Schema(description = "每一页的数据量")
    private Integer pageSize;

    /**
     * 总数据量
     */
    @Schema(description = "总数据量")
    private Long total;

    /**
     * 数据
     */
    @Schema(description = "数据")
    private List<T> rows;

    /**
     * 获取 页码
     *
     * @return page 页码
     */
    public Integer getPage() {
        return page;
    }

    /**
     * 设置 页码
     *
     * @param page 页码
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 获取 每一页的数据量
     *
     * @return pageSize 每一页的数据量
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置 每一页的数据量
     *
     * @param pageSize 每一页的数据量
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取 总数据量
     *
     * @return total 总数据量
     */
    public Long getTotal() {
        return total;
    }

    /**
     * 设置 总数据量
     *
     * @param total 总数据量
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * 获取 数据
     *
     * @return rows 数据
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置 数据
     *
     * @param rows 数据
     */
    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    /**
     * 返回分页数据
     *
     * @param all 查询出的分页数据
     * @param queryEntity 查询条件
     * @return 分页数据
     */
    public CommonPageResponse<T> of(Page<T> all, CommonPageRequest<T> queryEntity) {
        CommonPageResponse<T> result = new CommonPageResponse<T>();
        result.setPage(queryEntity.getPage());
        result.setPageSize(queryEntity.getPageSize());
        result.setTotal(all.getTotalElements());
        result.setRows(all.getContent());
        return result;
    }
}
