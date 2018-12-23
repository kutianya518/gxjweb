package cn.unis.gmweb.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface JxMapper {
	@Select("select interface_Id from jx_jk_big where S_id=#{sbid} and interface_type=#{interface_type}")
	public String getInterfaceId(@Param("sbid") String sbid, @Param("interface_type") String interface_type);

}
