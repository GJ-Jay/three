package gj.bwie.gejuan.frag;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import gj.bwie.gejuan.R;
import gj.bwie.gejuan.bean.Bean;
import gj.bwie.gejuan.dao.Dao;
import gj.bwie.gejuan.util.NetUtil;

public class Frag01 extends Fragment {

    //网址
   String urlString = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
    //创建集合
    List<Bean.DataBean> list = new ArrayList<Bean.DataBean>();
    private ListView lv;
    private MyAdapter myAdapter;
    private Dao dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag01,container,false);
        //初始化组件
        lv = view.findViewById(R.id.lv);
        //设置适配器
        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);
        //创建对象
        dao = new Dao(getContext());


        //长按 删除条目
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //删除条目
                list.remove(position);
                //删除数据库
                long delete = dao.delete("new_table", "title=?", new String[]{list.get(position).getNews_title()});
                Toast.makeText(getContext(),"删除成功"+"删除条目个数"+delete,Toast.LENGTH_SHORT).show();
                //刷新适配器
                myAdapter.notifyDataSetChanged();
                return true;
            }
        });

        new MAsyncTask().execute(urlString);

        return view;
    }

    //适配器
    private class MyAdapter extends BaseAdapter {

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
            //listview优化
            ViewHolder vh = null;
            if(convertView==null){
                //视图
                convertView = View.inflate(getContext(),R.layout.item,null);
                //创建对象
                vh = new ViewHolder();
                vh.tv1 = convertView.findViewById(R.id.tv1);
                vh.tv2 = convertView.findViewById(R.id.tv2);
                convertView.setTag(vh);
            }else{
                vh = (ViewHolder) convertView.getTag();
            }
            //赋值
            vh.tv1.setText(list.get(position).getNews_title());
            vh.tv2.setText(list.get(position).getNews_summary());
            return convertView;
        }
    }
    class ViewHolder{
        TextView tv1;
        TextView tv2;
    }

    //AsyncTask请求数据
    public class MAsyncTask extends AsyncTask<String,Void,String>{

        //子线程
        @Override
        protected String doInBackground(String... strings) {
            String jsonString = NetUtil.getJson(strings[0]);
            return jsonString;
        }

        //主线程
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //解析Json
            Gson gson = new Gson();
            Bean bean = gson.fromJson(s, Bean.class);
            /*Bean bean = gson.fromJson(s, Bean.class);*/
            List<Bean.DataBean> data = bean.getData();
            //添加到list中
            list.addAll(data);
            //添加集合到数据库中
            for (int i=0; i<list.size();i++){
                ContentValues values = new ContentValues();
                Bean.DataBean dataBean = list.get(i);
                values.put("title",dataBean.getNews_title());
                values.put("summary",dataBean.getNews_summary());
                dao.insert("new_table",null,values);
            }
            //适配器刷新
            myAdapter.notifyDataSetChanged();
        }
    }



}
