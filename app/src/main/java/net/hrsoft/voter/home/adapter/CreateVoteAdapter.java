package net.hrsoft.voter.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import net.hrsoft.voter.R;

import java.util.List;

/**
 * @author YangCihang.
 * @since 2017/4/18 0018.
 */

public class CreateVoteAdapter extends RecyclerView.Adapter<CreateVoteAdapter.RecHolder> {
    private List<String> mlist;
    private Context mcontext;
    private static final int FirstItem = 0;//第一个选项的位置
    private static final int SecondItem = 1;//第二个选项的位置


    public CreateVoteAdapter(List<String> list, Context context) {
        mlist = list;
        mcontext = context;
        addItem();
        addItem();
        notifyDataSetChanged();
    }

    /**
     * 添加选项
     */
    public void addItem() {
        mlist.add("");
    }

    @Override
    public RecHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_recycler_creat_vote, parent, false);
        return new RecHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecHolder holder, final int position) {
        if (position == FirstItem || position == SecondItem) {
            holder.deleteImg.setVisibility(View.GONE);
        }
        holder.inputEdit.addTextChangedListener(new TextSwitcher(holder));
        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "text  "+holder.inputEdit.getText().toString());
                mlist.remove(holder.getPosition());//列表remove LaoutPosition是当前所见的位置
                notifyItemRemoved(holder.getPosition());//移除Item
                //Log.d(TAG, "onClick: "+position+"  hodler "+holder.getLayoutPosition()+"  count"+getItemCount());
                //notifyItemRangeChanged(position,getItemCount()); 顺序Changed
            }
        });
        holder.inputEdit.setText(""); //初始的时候设为空，防止复用时候数据再次出现
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class RecHolder extends RecyclerView.ViewHolder {
        public EditText inputEdit;
        public ImageView deleteImg;

        public RecHolder(View itemView) {
            super(itemView);
            inputEdit = (EditText) itemView.findViewById(R.id.edit_add_item);
            deleteImg = (ImageView) itemView.findViewById(R.id.img_delete_item);
        }

    }

    /**
     * 监听EditText变化的接口
     */
    public interface SaveEditListener {
        void saveEdit(int position, String content);
    }

    /**
     * 重新实现Watcher接口
     */
    class TextSwitcher implements TextWatcher {
        private RecHolder reHolder;

        public TextSwitcher(RecHolder recHolder) {
            this.reHolder = recHolder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            SaveEditListener saveEditListener = (SaveEditListener) mcontext;
            if (s != null) {
                saveEditListener.saveEdit(reHolder.getPosition(), s.toString());
            }
        }
    }
}
