package cn.unis.gmweb.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.unis.gmweb.mapper.BkcMapper;


@Service
public class BkcServiceImpl implements BkcService {

	@Resource
	private BkcMapper bkcMapper;

	@Override
	public List<String> findCidList(String sbid) {
		return bkcMapper.findCidList(sbid);
	}

	

}
