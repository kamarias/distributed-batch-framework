package io.github.kamarias.dbf.system.dto;

public class RouterDto {

    /**
     * 主键
     */
    private String id;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 组件名称
     */
    private String name;

    /**
     * 路由类型（0、目录，1、菜单）
     */
    private Integer type;

    /**
     * 是否路由启用状态（0、停用，1、启用）
     */
    private Integer state;

    /**
     * 父级路由Id
     */
    private String parentRouterId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getParentRouterId() {
        return parentRouterId;
    }

    public void setParentRouterId(String parentRouterId) {
        this.parentRouterId = parentRouterId;
    }
}
