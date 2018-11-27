package gj.bwie.gejuan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import java.util.ArrayList;

import gj.bwie.gejuan.frag.Frag01;
import gj.bwie.gejuan.frag.Frag02;
import gj.bwie.gejuan.frag.Frag03;

public class MainActivity extends FragmentActivity {

    private ViewPager pager;
    private RadioGroup radioGroup;
    private ArrayList<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化组件
        pager = findViewById(R.id.pager);
        radioGroup = findViewById(R.id.radio);

        //创建list集合
        list = new ArrayList<>();
        //创建对象
        Frag01 frag01 = new Frag01();
        Frag02 frag02 = new Frag02();
        Frag03 frag03 = new Frag03();
        //添加
        list.add(frag01);
        list.add(frag02);
        list.add(frag03);
        //设置适配器
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        //默认第一个页面选中
        radioGroup.check(radioGroup.getChildAt(0).getId());

        //设置点击按钮切换页面
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio1:
                        pager.setCurrentItem(0);
                        break;

                    case R.id.radio2:
                        pager.setCurrentItem(1);
                        break;

                    case R.id.radio3:
                        pager.setCurrentItem(2);
                        break;
                }
            }
        });

        //设置页面切换按钮改变
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                radioGroup.check(radioGroup.getChildAt(i).getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
