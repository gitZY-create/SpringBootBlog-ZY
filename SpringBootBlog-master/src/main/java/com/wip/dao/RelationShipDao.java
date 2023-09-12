/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * DateTime: 2018/7/24 23:07
 **/
package com.wip.dao;

import com.wip.model.RelationShipDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章和项目关联表
 */
@Mapper
public interface RelationShipDao {

    /**
     * 根据meta编号获取关联
     * @param mid
     * @return
     */
    List<RelationShipDomain> getRelationShipByMid(Integer mid);

    /**
     * 根据meta编号获取关联
     * @param mid
     * @return
     */
    List<RelationShipDomain> getRelationShipByMidTuto(Integer mid);


    /**
     * 根据meta编号删除关联
     * @param mid
     */
    void deleteRelationShipByMid(Integer mid);

    /**
     * 根据meta编号删除关联
     * @param mid
     */
    void deleteRelationShipByMidTuto(Integer mid);

    /**
     * 获取数量
     * @param cid
     * @param mid
     * @return
     */
    Long getCountById(@Param("cid") Integer cid, @Param("mid") Integer mid);

    /**
     * 获取教程数量
     * @param tid
     * @param mid
     * @return
     */
    Long getCountByIdTuto(@Param("tid") Integer tid, @Param("mid") Integer mid);

    /**
     * 添加
     * @param relationShip
     * @return
     */
    int addRelationShip(RelationShipDomain relationShip);

    /**
     * 添加tid
     * @param relationShip
     * @return
     */
    int addRelationShipTuto(RelationShipDomain relationShip);

    /**
     * 根据文章编号删除关联
     * @param cid
     */
    void deleteRelationShipByCid(int cid);

    /**
     * 根据教程编号删除关联
     * @param tid
     */
    void deleteRelationShipByTid(int tid);

    /**
     * 根据文章ID获取关联
     * @param cid
     */
    List<RelationShipDomain> getRelationShipByCid(Integer cid);

    /**
     * 根据文章id获取关联
     * @param tid
     * @return
     */
    List<RelationShipDomain> getRelationShipByTid(Integer tid);
}
