package cn.unis.gmweb.controller.xjcontroller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.unis.gmweb.pojo.Tree;
import cn.unis.gmweb.service.TreeService;

/**
 * 获取查询下拉树
 * @author lgf
 *
 */
@Controller
@RequestMapping("/xj/tree")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TreeController {

	@Resource
	private TreeService treeService;
	/**
	 * 火探漏电流温度查询树
	 * @param lineName 线路名称 A36
	 * @return
	 */
	@RequestMapping("httree/{lineName}")
	@ResponseBody
	public List<Tree> findHtTree(@PathVariable String lineName) {
		List<Tree> treeList =treeService.findHtTree(lineName);
		return treeList;
	}
	
	/**
	 * 全电量设备查询树
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("qdltree/{lineName}")
	@ResponseBody
	public List<Tree> findqdlTree(@PathVariable String lineName){
		List<Tree> qdlList =treeService.findqdlTree(lineName);
		System.out.println(qdlList.size()+":"+qdlList.toString());
		return qdlList;
	}
	
}
