package net.hrsoft.voter.home.model;

import net.hrsoft.voter.common.BaseModel;
import net.hrsoft.voter.home.adapter.StatisticsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author YangCihang.
 * @Email yangcihang@hrsoft.net
 * @since 2017/4/23 0023.
 */
public class Problem extends BaseModel{
    private String title;
    private String description;
    private int type;
    private ArrayList<QuestionValue> options;

    public ArrayList<QuestionValue> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<QuestionValue> options) {
        this.options = options;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
