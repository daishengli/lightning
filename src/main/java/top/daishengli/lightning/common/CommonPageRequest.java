package top.daishengli.lightning.common;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.PageRequest;

/**
 * 分页请求
 *
 * @author daishengli
 * @date 2024-09-19 00:20:31
 **/
public class CommonPageRequest<T> {
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
     * 查询条件
     */
    @Schema(description = "查询条件")
    private T query;

    /**
     * 获取 页码
     *
     * @return page 页码
     */
    public Integer getPage() {
        return page != null ? page : 1;
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
        return pageSize != null ? pageSize : 10;
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
     * 获取 查询条件
     *
     * @return query 查询条件
     */
    public T getQuery() {
        return query;
    }

    /**
     * 设置 查询条件
     *
     * @param query 查询条件
     */
    public void setQuery(T query) {
        this.query = query;
    }

    /**
     * 获取JPA分页对象
     *
     * @return JPA分页对象
     */
    public PageRequest getPageRequest() {
        return PageRequest.of(this.getPage() - 1, this.getPageSize());
    }
}
