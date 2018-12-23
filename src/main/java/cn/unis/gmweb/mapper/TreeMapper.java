package cn.unis.gmweb.mapper;




import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.unis.gmweb.pojo.Tree;

public interface TreeMapper {

	public List<Tree> findHtTree(@Param("lineName") String lineName);
	@Select("SELECT q_id ,q_name FROM xj_qy_big WHERE z_id=(SELECT z_id FROM xj_zl_big WHERE z_name=#{lineName}) AND q_name LIKE '%全'")
	public List<Tree> findqdlTree(String lineName);
}
