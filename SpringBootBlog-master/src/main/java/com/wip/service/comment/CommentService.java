/**
 * Created by IntelliJ IDEA.
 * User: Kyrie
 * DateTime: 2018/7/31 15:40
 **/
package com.wip.service.comment;

import com.github.pagehelper.PageInfo;
import com.wip.dto.cond.CommentCond;
import com.wip.model.CommentDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论相关Service接口
 */
public interface CommentService {

    /**
     * 添加评论
     * @param comments
     */
    void addComment(CommentDomain comments);

    /**
     * 添加教程评论
     * @param comments
     */
    void addCommentTuto(CommentDomain comments);

    /**
     * 通过文章ID获取评论
     * @param cid
     * @return
     */
    List<CommentDomain> getCommentsByCId(Integer cid);

    /**
     * 通过教程ID获取评论
     * @param tid
     * @return
     */
    List<CommentDomain> getCommentsTutoByTId(Integer tid);

    /**
     * 根据条件获取评论列表
     * @param commentCond   查询条件
     * @param pageNum       分页参数 第几页
     * @param pageSize      分页参数 每页条数
     * @return
     */
    PageInfo<CommentDomain> getCommentsByCond(CommentCond commentCond, int pageNum, int pageSize);

    /**
     * 根据条件获取教程评论列表
     * @param commentCond
     * @return
     */
    PageInfo<CommentDomain> getCommentsTutoByCond(CommentCond commentCond,int pageNum, int pageSize);

    /**
     * 通过ID获取评论
     * @param coid
     * @return
     */
    CommentDomain getCommentById(Integer coid);

    /**
     * 通过ID获取教程评论
     * @param coid
     * @return
     */
    CommentDomain getCommentTutoById(@Param("coid") Integer coid);

    /**
     * 更新评论状态
     * @param coid
     * @param status
     */
    void updateCommentStatus(Integer coid, String status);

    /**
     * 更新教程评论状态
     * @param coid
     * @param status
     */
    void updateCommentStatusTuto(Integer coid, String status);

    /**
     * 删除评论
     * @param id
     */
    void deleteComment(Integer id);

    /**
     * 删除教程评论
     * @param id
     */
    void deleteCommentTuto(Integer id);

}
