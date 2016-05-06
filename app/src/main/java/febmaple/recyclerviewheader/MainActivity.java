package febmaple.recyclerviewheader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter = new MyAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置分割线
        FmItemDecoration itemDecoration = new FmItemDecoration(this, LinearLayoutManager.HORIZONTAL, R.drawable.shape_rvdivider);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(mAdapter);
        //获取数据，给adapter灌数据并且notifyDataSetChanged();
        mAdapter.addDatas(generateData());
        setHeader(mRecyclerView);
        //item点击事件
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String data) {
                //Toast.makeText(MainActivity.this,""+ position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //产生假数据
    private ArrayList<String> generateData() {
        ArrayList<String> data = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            data.add("测试数据" + i);
        }
        return data;

    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.header, view, false);
        mAdapter.setHeaderView(header);
    }
}

