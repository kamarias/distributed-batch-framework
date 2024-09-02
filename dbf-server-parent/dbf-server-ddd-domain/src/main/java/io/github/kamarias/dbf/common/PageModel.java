package io.github.kamarias.dbf.common;

public class PageModel {


    private Integer pageNum;


    /**
     * 每页展示行数
     */
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 计算偏移量（适用于queryDsl）
     *
     * @return 获取偏移量
     */
    public long getOffset() {
        return (pageNum - 1) * pageSize;
    }

}
