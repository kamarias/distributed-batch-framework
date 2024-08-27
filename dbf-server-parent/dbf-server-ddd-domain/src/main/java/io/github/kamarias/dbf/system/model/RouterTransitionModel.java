package io.github.kamarias.dbf.system.model;

/**
 * 路由动画信息
 */
public class RouterTransitionModel {

    /**
     * 动画效果
     *
     * @see {@link 'https://next.router.vuejs.org/guide/advanced/transitions.html#transitions'}
     * @see 'animate.css' {@link 'https://animate.style'}
     */
    private String name;

    /**
     * 进场动画
     */
    private String enterTransition;

    /**
     * 离场动画
     */
    private String leaveTransition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnterTransition() {
        return enterTransition;
    }

    public void setEnterTransition(String enterTransition) {
        this.enterTransition = enterTransition;
    }

    public String getLeaveTransition() {
        return leaveTransition;
    }

    public void setLeaveTransition(String leaveTransition) {
        this.leaveTransition = leaveTransition;
    }


}
