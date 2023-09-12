package com.wip.dao;

import com.wip.dto.cond.ContentCond;
import com.wip.model.MetaDomain;
import com.wip.model.RelationShipDomain;
import com.wip.model.TutorialDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TutorialDao {
    /**
     * 添加教程
     * @param tutorialDomain
     */
    void addTutorial(TutorialDomain tutorialDomain);

    /**
     * 根据编号获取教程
     * @param tid
     * @return
     */
    TutorialDomain getTutorialById(Integer tid);

    /**
     * 更新教程
     * @param tutorialDomain
     */
    void updateTutorialById(TutorialDomain tutorialDomain);

    /**
     * 根据条件获取教程列表
     * @param contentCond
     * @return
     */
    List<TutorialDomain> getTutorialByCond(ContentCond contentCond);

    /**
     * 删除教程
     * @param tid
     */
    void deleteTutorialById(Integer tid);

    /**
     * 获取教程总数
     * @return
     */
    Long getTutorialCount();

    /**
     * 通过分类获取教程
     * @param category
     * @return
     */
    List<TutorialDomain> getTutorialByCategory(@Param("category") String category);

    /**
     * 通过标签获取教程
     * @param tid
     * @return
     */
    List<TutorialDomain> getTutorialByTags(List<RelationShipDomain> tid);
}
