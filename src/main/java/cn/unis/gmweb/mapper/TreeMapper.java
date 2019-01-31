package cn.unis.gmweb.mapper;


import java.util.List;

import cn.unis.gmweb.pojo.HtRealTimeTree;
import cn.unis.gmweb.pojo.QdlRealTimeTree;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.unis.gmweb.pojo.Tree;

public interface TreeMapper {

    List<Tree> findHtTree(@Param("lineName") String lineName);

    @Select("SELECT q_id ,q_name FROM xj_qy_big WHERE z_id=(SELECT z_id FROM xj_zl_big WHERE z_name=#{lineName}) AND q_name LIKE '%全'")
    List<Tree> findqdlTree(String lineName);

    List<HtRealTimeTree> findHtRealTimeTree(String lineName);

    List<QdlRealTimeTree> findQdlRealTimeTree(String lineName);
    @Select("select s_id from bkc_sb_big")
    List<String> findAllPump();
    @Select("select z_name from xj_zl_big where z_name like 'A%'")
    List<String> findAllLine();
    @Select("select z_name from xj_zl_big where z_name like 'A36%'")
    List<String> findAllQdlLine();
}
