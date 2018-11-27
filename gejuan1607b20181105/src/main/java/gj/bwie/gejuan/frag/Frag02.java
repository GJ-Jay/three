package gj.bwie.gejuan.frag;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.xlistviewlibrary.utils.NetWordUtils;
import com.bwie.xlistviewlibrary.view.XListView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import gj.bwie.gejuan.R;
import gj.bwie.gejuan.bean.Bean;

/**
 * 多条目并且上拉加载下拉刷新
 */
public class Frag02 extends Fragment {

    private XListView xListView;
    String baseUrl = "http://api.expoon.com/AppNews/getNewsList/type/1/p/";
    //大集合
    ArrayList<Bean.DataBean> list = new ArrayList<Bean.DataBean>();
    private ImageLoader instance;
    int page;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag02,container,false);
        //初始化组件
        xListView = view.findViewById(R.id.xlv);
        //获取实例
        instance = ImageLoader.getInstance();
        //创建适配器
        myAdapter = new MyAdapter();
        //设置适配器
        xListView.setAdapter(myAdapter);
        //调用网络路径的类
        initData(page);
        //允许加载更多
        xListView.setPullLoadEnable(true);
        //设置上拉下拉的监听
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                //刷新是清空集合 赋值到第一条数据 及最新数据
                list.clear();
                initData(0);
            }

            @Override
            public void onLoadMore() {
                //加载更多page+1 添加到集合
                page++;
                initData(page);
            }
        });

        return view;
    }

    private void initData(int page) {
        //第一次进入时为0
        String s = baseUrl + page;
        new MAsyncTask().execute(s);
    }

    private class MAsyncTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            //调用的是xllistviewlibrary中的方法
            String netjson = NetWordUtils.getNetjson(strings[0]);
            return netjson;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //解析json
            Gson gson = new Gson();
            Bean bean = gson.fromJson(s, Bean.class);
            List<Bean.DataBean> data = bean.getData();
            //添加到大集合中
            list.addAll(data);
            //刷新适配器
            myAdapter.notifyDataSetChanged();
            //设置刷新完成隐藏列表头和底部加载
            uiComplan();
        }
    }

    private void uiComplan() {
        xListView.setRefreshTime("刚刚");//设置刷新头
        xListView.stopRefresh();//让刷新头消失
        xListView.stopLoadMore();//让上拉加载的ui消失
    }

    private class MyAdapter extends BaseAdapter {
        //条目类型  从0 开始
        @Override
        public int getItemViewType(int position) {
            return position%2;
        }

        //条目个数
        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //判断种类
            int itemViewType = getItemViewType(position);
            switch (itemViewType){
                case 0://有图片
                    //优化
                    ViewHolder0 vh0 = null;
                    if(convertView==null){
                        convertView = View.inflate(getActivity(),R.layout.iv_item0,null);
                        vh0 = new ViewHolder0();
                        vh0.textView1 = convertView.findViewById(R.id.textView1);
                        vh0.textView2 = convertView.findViewById(R.id.textView2);
                        vh0.iv = convertView.findViewById(R.id.iv);
                        convertView.setTag(vh0);
                    }else{
                        vh0 = (ViewHolder0) convertView.getTag();
                    }
                    vh0.textView1.setText(list.get(position).getNews_summary());
                    vh0.textView2.setText(list.get(position).getNews_title());
                    instance.displayImage(list.get(position).getPic_url(),vh0.iv);
                    break;
                case 1://无图片
                    //优化
                    ViewHolder1 vh1 = null;
                    if(convertView==null){
                        convertView = View.inflate(getActivity(),R.layout.iv_item1,null);
                        vh1 = new ViewHolder1();
                        vh1.tv1 = convertView.findViewById(R.id.tv1);
                        vh1.tv2 = convertView.findViewById(R.id.tv2);
                        convertView.setTag(vh1);
                    }else{
                        vh1 = (ViewHolder1) convertView.getTag();
                    }
                    vh1.tv1.setText(list.get(position).getNews_summary());
                    vh1.tv2.setText(list.get(position).getNews_title());
                    break;
            }
            return convertView;
        }
    }
    public class ViewHolder0{
        TextView textView1;
        TextView textView2;
        ImageView iv;
    }

    public class ViewHolder1{
        TextView tv1;
        TextView tv2;
    }
}
