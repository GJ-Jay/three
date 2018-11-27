package gj.bwie.gejuan.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import gj.bwie.gejuan.MainActivity;
import gj.bwie.gejuan.R;
import gj.bwie.gejuan.frag.manager.Mfragment;

public class Frag03 extends Fragment {

    private DrawerLayout drawerLayout;
    private FrameLayout fl;
    private ListView lv;

    ArrayList<String> list = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag03,container,false);
        lv = view.findViewById(R.id.lv);
        fl = view.findViewById(R.id.fl);
        drawerLayout = view.findViewById(R.id.drawerlayout);

        //设置适配器的类
        initData();
        //点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击传值
                //开启事务 getActivity().getSupportFragmentManager()
               getActivity().getSupportFragmentManager().beginTransaction().replace(
                      R.id.fl,Mfragment.getInstance(list.get(position))).commit();
               //点击后消失
                drawerLayout.closeDrawers();
            }
        });
        //设置监听
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                //关闭的时候
                Toast.makeText(getActivity(), "啊! 我关闭啦! ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        return view;
    }

    private void initData() {
        for (int i = 0;i<10;i++){
            list.add("drawerlayout"+i);
        }
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_1,list
        );
        lv.setAdapter(myAdapter);
    }
}
