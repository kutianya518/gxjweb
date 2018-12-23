package cn.unis.gmweb.service;

import java.util.List;

import cn.unis.gmweb.pojo.Tree;

public interface TreeService {
	public List<Tree> findHtTree(String lineName);
	public List<Tree> findqdlTree(String lineName);

}
