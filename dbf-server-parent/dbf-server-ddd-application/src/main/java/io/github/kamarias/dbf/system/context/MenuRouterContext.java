package io.github.kamarias.dbf.system.context;

import io.github.kamarias.dbf.system.model.MenuRouterMetaModel;
import io.github.kamarias.dbf.system.model.MenuRouterModel;

import java.util.List;

public class MenuRouterContext {



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
     * 路由类型
     */
    private Integer type;

    /**
     * 路由元数据
     */
    private MenuRouterMetaModel meta;

    /**
     * 子路由
     */
    private List<MenuRouterModel> children;


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuRouterMetaModel getMeta() {
        return meta;
    }

    public void setMeta(MenuRouterMetaModel meta) {
        this.meta = meta;
    }

    public List<MenuRouterModel> getChildren() {
        return children;
    }

    public void setChildren(List<MenuRouterModel> children) {
        this.children = children;
    }


}
