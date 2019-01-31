package cn.unis.gmweb.pojo;

import java.util.List;

public class BaseTree {
    private String qyid;//区域id
    private String parentId;//上级区域id
    private String qyName;//区域名称
    private String time;
    private List<BaseTree> children;

    public BaseTree() {
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getQyName() {
        return qyName;
    }

    public void setQyName(String qyName) {
        this.qyName = qyName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<BaseTree> getChildren() {
        return children;
    }

    public void setChildren(List<BaseTree> children) {
        this.children = children;
    }
}
