package com.wip.service.tutorial;

import com.github.pagehelper.PageInfo;
import com.wip.dto.cond.ContentCond;
import com.wip.model.MetaDomain;
import com.wip.model.TutorialDomain;

import java.util.List;

public interface TutorialService {

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
     * @param page
     * @param limit
     * @return
     */
    PageInfo<TutorialDomain> getTutorialByCond(ContentCond contentCond, int page, int limit);

    /**
     * 删除教程
     * @param tid
     */
    void deleteTutorialById(Integer tid);

    /**
     * 添加教程点击量
     * @param tutorial
     */
    void updateTutorialByTid(TutorialDomain tutorial);

    /**
     * 通过分类获取教程
     * @param category
     * @return
     */
    List<TutorialDomain> getTutorialByCategory(String category);

    /**
     * 通过标签获取教程
     * @param tags
     * @return
     */
    List<TutorialDomain> getTutorialByTags(MetaDomain tags);
}
