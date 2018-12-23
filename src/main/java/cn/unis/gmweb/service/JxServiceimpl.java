package cn.unis.gmweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.unis.gmweb.mapper.JxMapper;

@Service
public class JxServiceimpl implements JxService {
	@Autowired
	private JxMapper jxMapper;
	@Override
	public String getInterfaceId(String sbid, String interface_type) {
		return jxMapper.getInterfaceId(sbid,interface_type);
	}

}
