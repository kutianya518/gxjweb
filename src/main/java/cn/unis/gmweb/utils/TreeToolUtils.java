package cn.unis.gmweb.utils;

import cn.unis.gmweb.pojo.BaseTree;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class TreeToolUtils<T extends BaseTree> {

    private List<T> rootList; //根节点对象存放到这里

    private List<T> bodyList; //其他节点存放到这里，可以包含根节点

    public TreeToolUtils(List<T> rootList, List<T> bodyList) {
        this.rootList = rootList;
        this.bodyList = bodyList;
    }

    public List<T> getTree(){   //调用的方法入口
        if(bodyList != null && !bodyList.isEmpty()){
            //声明一个map，用来过滤已操作过的数据
            Map<String,String> map = Maps.newHashMapWithExpectedSize(bodyList.size());
            rootList.forEach(beanTree -> getChild(beanTree,map));
            return rootList;
        }
        return null;
    }

    public void getChild(T beanTree,Map<String,String> map){
        List<BaseTree> childList = Lists.newArrayList();
        bodyList.stream()
                .filter(c -> !map.containsKey(c.getQyid()))
                .filter(c ->c.getParentId().equals(beanTree.getQyid()))
                .forEach(c ->{
                    map.put(c.getQyid(),c.getParentId());
                    getChild(c,map);
                    childList.add(c);
                });
        beanTree.setChildren(childList);
    }


}