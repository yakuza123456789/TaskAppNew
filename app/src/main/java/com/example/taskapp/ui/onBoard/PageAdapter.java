package com.example.taskapp.ui.onBoard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;

import com.example.taskapp.MainActivity;
import com.example.taskapp.Prefs;
import com.example.taskapp.R;
import com.example.taskapp.ui.interfaces.OnItemClickListener;
import com.example.taskapp.ui.interfaces.OnViewListener;

public class PageAdapter extends PagerAdapter {



    private  String[] titles = new String[]{"Привет","Как ты","Пока"};

    private int[] img = {R.drawable.cool,R.drawable.ifg,R.drawable.utl};

    public OnViewListener onViewListener;
    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.page_board,container,false);
        container.addView(view);
        TextView textView = view.findViewById(R.id.textTitle);
        Button btnStart = view.findViewById(R.id.btnStart);
        ImageView imageView = view.findViewById(R.id.imageView);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Prefs(container.getContext()).setShown();
                onViewListener.onViewPagerClick();
            }
        });
        textView.setText(titles[position]);
        if (position == 0 || position == 1){
            btnStart.setVisibility(View.GONE);
        }
        imageView.setImageResource(img[position]);
        return view;
    }

    public void setOnViewClickListener(OnViewListener onViewListener) {
        this.onViewListener = onViewListener;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
