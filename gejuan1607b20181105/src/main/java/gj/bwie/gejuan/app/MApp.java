package gj.bwie.gejuan.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(this).build();
        //获取实例
        ImageLoader instance = ImageLoader.getInstance();
        instance.init(build);
    }
}
