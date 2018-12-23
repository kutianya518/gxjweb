package cn.unis.gmweb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface BkcMapper {
	@Select("select c_id from bkc_cd_big where s_id=#{sbid} and c_name like '%轴承%' order by c_id")
	public List<String> findCidList(String sbid);
	
	

}
