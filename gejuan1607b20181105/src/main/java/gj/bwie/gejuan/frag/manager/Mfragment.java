package gj.bwie.gejuan.frag.manager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Mfragment extends Fragment {
    public static Fragment getInstance(String s) {
        Mfragment mFragment = new Mfragment();
        Bundle bundle = new Bundle();
        bundle.putString("name",s);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        Bundle bundle = getArguments();
        String name = (String) bundle.get("name");
        textView.setText(name);
        return textView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
