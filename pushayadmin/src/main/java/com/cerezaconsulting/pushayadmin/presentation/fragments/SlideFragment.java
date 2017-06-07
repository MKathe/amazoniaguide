package com.cerezaconsulting.pushayadmin.presentation.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseFragment;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TOTTUS on 21/06/2016.
 */
public class SlideFragment extends BaseFragment {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.titles)
    CirclePageIndicator titles;
    private InitAdapter initAdapter;

    public static SlideFragment newInstance() {
        return new SlideFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slide, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter = new InitAdapter(getActivity());
        pager.setAdapter(initAdapter);
        titles.notifyDataSetChanged();
        titles.setViewPager(pager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public static class InitAdapter extends PagerAdapter {

        Context context;
        ArrayList<Drawable> list;
        ArrayList<String> title;
        TextView textTitleMenuStart;
        @BindView(R.id.im_slide)
        ImageView imSlide;
        @BindView(R.id.text_title)
        TextView textTitle;
        private LayoutInflater layoutInflater;
        Drawable item;
        String tit;


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public InitAdapter(Context context) {
            this.context = context;
            this.list = new ArrayList<>();
            this.title = new ArrayList<>();
            this.layoutInflater = LayoutInflater.from(context);
            list.add(context.getDrawable(R.drawable.photo_guide));
            list.add(context.getDrawable(R.drawable.photo_guide));
            list.add(context.getDrawable(R.drawable.photo_guide));
            title.add("imagen 1");
            title.add("imagen 2");
            title.add("imagen 3");

        }

        public Object instantiateItem(final ViewGroup collection, final int position) {
            item = list.get(position);
            tit = title.get(position);
            final View view = layoutInflater.inflate(R.layout.item_slide, collection, false);
            ButterKnife.bind(this, view);
            imSlide.setImageDrawable(item);
            textTitle.setText(tit);

            collection.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void destroyItem(ViewGroup collection, int position,
                                Object view) {
            collection.removeView((View) view);
        }
    }
}

