package cn.unis.gmweb.service;

import java.util.List;

import cn.unis.gmweb.pojo.HtRealTimeTree;
import cn.unis.gmweb.pojo.QdlRealTimeTree;
import cn.unis.gmweb.pojo.Tree;

public interface TreeService {
    List<Tree> findHtTree(String lineName);

    List<Tree> findqdlTree(String lineName);

    List<HtRealTimeTree> findHtRealTimeTree(String lineName);

    List<QdlRealTimeTree> findQdlRealTimeTree(String lineName);

    List<String> findAllPump();

    List<String> findAllLine();

    List<String> findAllQdlLine();
}
