package febmaple.recyclerviewheader;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 作者： FebMaple on 2016/5/4.
 * 邮箱： febmaple219@gmail.com
 * 版权： FebMaple
 * ====================================================
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    //数据模型列表
    private ArrayList<String> mDatas = new ArrayList<>();
    //头
    private View mHeaderView;
    //头内控件
    private TextView tvHead;
    private Button btnChange;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
        ///////////////////////////////////////////////////////////头布局添加点击事件//////////////////////////////////////////////////////////////////////////
//        tvHead = (TextView) mHeaderView.findViewById(R.id.head);
//        btnChange = (Button) mHeaderView.findViewById(R.id.change);
//        btnChange.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvHead.setText("diandiandiandain");
//
//            }
//        });
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    //添加假数据
    public void addDatas(ArrayList<String> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new Holder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new Holder(layout);

    }

    @Override
    public void onBindViewHolder(final Holder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        //确切位置
        final int pos = getRealPosition(viewHolder);
        //绑定数据
        final String data = mDatas.get(pos);
        viewHolder.text.setText(data);
        viewHolder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.text.setText("变变变");
            }
        });
        //"整个"item的点击监听
        if (mListener == null) return;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(pos, data);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }
    
    //holder处理item，不处理头
    public class Holder extends RecyclerView.ViewHolder {
        TextView text;

        public Holder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position, String data);
    }

}
