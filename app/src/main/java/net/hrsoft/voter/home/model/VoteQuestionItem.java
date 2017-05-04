package net.hrsoft.voter.home.model;

/**
 * @author YangCihang.
 * @since 2017/4/19 0019.
 */

public class VoteQuestionItem  {
    private String title; //问题的标题（空）
    private String description; //问题的描述(空)
    private int type; //问题类型，1单选，2多选，3填空
    private String [] options; //选项组
}
