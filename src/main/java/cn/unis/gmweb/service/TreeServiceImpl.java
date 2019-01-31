package cn.unis.gmweb.service;

import java.util.List;

import javax.annotation.Resource;

import cn.unis.gmweb.pojo.HtRealTimeTree;
import cn.unis.gmweb.pojo.QdlRealTimeTree;
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

    @Override
    public List<HtRealTimeTree> findHtRealTimeTree(String lineName) {
        return treeMapper.findHtRealTimeTree(lineName);
    }

	@Override
	public List<QdlRealTimeTree> findQdlRealTimeTree(String lineName) {
		return treeMapper.findQdlRealTimeTree(lineName);
	}

	@Override
	public List<String> findAllPump() {
		return treeMapper.findAllPump();
	}

	@Override
	public List<String> findAllLine() {
		return treeMapper.findAllLine();
	}

	@Override
	public List<String> findAllQdlLine() {
		return treeMapper.findAllQdlLine();
	}

}
