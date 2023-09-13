package com.wip.service.tutorial.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wip.constant.ErrorConstant;
import com.wip.constant.Types;
import com.wip.constant.WebConst;
import com.wip.dao.CommentDao;
import com.wip.dao.RelationShipDao;
import com.wip.dao.TutorialDao;
import com.wip.dto.cond.ContentCond;
import com.wip.exception.BusinessException;
import com.wip.model.*;
import com.wip.service.meta.MetaService;
import com.wip.service.tutorial.TutorialService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class TutorialServiceImpl implements TutorialService {
    @Autowired
    private TutorialDao tutorialDao;

    @Autowired
    private MetaService metaService;

    @Autowired
    private RelationShipDao relationShipDao;

    @Autowired
    private CommentDao commentDao;

    @Transactional
    @Override
    @CacheEvict(value = {"tutorialCache", "tutorialCaches"}, allEntries = true, beforeInvocation = true)
    public void addTutorial(TutorialDomain tutorialDomain) {
        if (null == tutorialDomain)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);

        if (StringUtils.isBlank(tutorialDomain.getTitle()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_CAN_NOT_EMPTY);

        if (tutorialDomain.getTitle().length() > WebConst.MAX_TITLE_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_IS_TOO_LONG);

        if (StringUtils.isBlank(tutorialDomain.getContent()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_CAN_NOT_EMPTY);

        if (tutorialDomain.getContent().length() > WebConst.MAX_CONTENT_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_IS_TOO_LONG);

        // 取到标签和分类
        String tags = tutorialDomain.getTags();
        String categories = tutorialDomain.getCategories();

        // 添加文章
        tutorialDao.addTutorial(tutorialDomain);

        // 添加分类和标签
        int tid = tutorialDomain.getTid();
        metaService.addMetasTutorial(tid, tags, Types.TAG.getType());
        metaService.addMetasTutorial(tid, categories, Types.CATEGORY.getType());

    }

    @Override
    @Cacheable(value = "tutorialCache", key = "'tutorialById_' + #p0")
    public TutorialDomain getTutorialById(Integer tid) {
        if (null == tid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return tutorialDao.getTutorialById(tid);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"tutorialCache", "tutorialCaches"}, allEntries = true, beforeInvocation = true)
    public void updateTutorialById(TutorialDomain tutorialDomain) {

        // 标签和分类
        String tags = tutorialDomain.getTags();
        String categories = tutorialDomain.getCategories();

        // 更新文章
        tutorialDao.updateTutorialById(tutorialDomain);
        int tid = tutorialDomain.getTid();
        relationShipDao.deleteRelationShipByTid(tid);
        metaService.addMetasTutorial(tid,tags,Types.TAG.getType());
        metaService.addMetasTutorial(tid,categories,Types.CATEGORY.getType());
    }

    @Override
    @Cacheable(value = "tutorialCaches", key = "'tutorialsByCond_' + #p1 + 'type_' + #p0.type")
    public PageInfo<TutorialDomain> getTutorialByCond(ContentCond contentCond, int pageNum, int pageSize) {
        if (null == contentCond)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        PageHelper.startPage(pageNum,pageSize);
        List<TutorialDomain> tutorials = tutorialDao.getTutorialByCond(contentCond);
        PageInfo<TutorialDomain> pageInfo = new PageInfo<>(tutorials);
        return pageInfo;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"tutorialCache","tutorialCaches"},allEntries = true, beforeInvocation = true)
    public void deleteTutorialById(Integer tid) {
        if (null == tid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        // 删除文章
        tutorialDao.deleteTutorialById(tid);

        // 同时要删除该 文章下的所有评论
        List<CommentDomain> comments = commentDao.getCommentByCId(tid);
        if (null != comments && comments.size() > 0) {
            comments.forEach(comment -> {
                commentDao.deleteComment(comment.getCoid());
            });
        }

        // 删除标签和分类关联
        List<RelationShipDomain> relationShips = relationShipDao.getRelationShipByCid(tid);
        if (null != relationShips && relationShips.size() > 0) {
            relationShipDao.deleteRelationShipByCid(tid);
        }
    }

    @Override
    @CacheEvict(value = {"tutorialCache","tutorialCaches"}, allEntries = true, beforeInvocation = true)
    public void updateTutorialByTid(TutorialDomain tutorial) {
        if (null != tutorial && null != tutorial.getTid()) {
            tutorialDao.updateTutorialById(tutorial);
        }
    }

    @Override
    @Cacheable(value = "tutorialCache", key = "'tutorialByCategory_' + #p0")
    public List<TutorialDomain> getTutorialByCategory(String category) {
        if (null == category)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return tutorialDao.getTutorialByCategory(category);
    }

    @Override
    @Cacheable(value = "tutorialCache", key = "'tutorialByTags_'+ #p0")
    public List<TutorialDomain> getTutorialByTags(MetaDomain tags) {
        if (null == tags)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        List<RelationShipDomain> relationShip = relationShipDao.getRelationShipByMid(tags.getMid());
        if (null != relationShip && relationShip.size() > 0) {
            return tutorialDao.getTutorialByTags(relationShip);
        }
        return null;
    }
}
