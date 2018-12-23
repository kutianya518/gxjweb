package cn.unis.gmweb.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.unis.gmweb.mapper.TreeMapper;
import cn.unis.gmweb.pojo.Tree;
@Service
public class TreeServiceImpl implements TreeService {

	@Resource
	private TreeMapper treeMapper;
	@Override
	public List<Tree> findHtTree(String lineName) {
		return treeMapper.findHtTree(lineName);
	}
	@Override
	public List<Tree> findqdlTree(String lineName) {
		return treeMapper.findqdlTree(lineName);
	}

}
