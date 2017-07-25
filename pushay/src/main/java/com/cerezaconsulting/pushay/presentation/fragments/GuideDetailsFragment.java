package com.cerezaconsulting.pushay.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cerezaconsulting.pushay.R;
import com.cerezaconsulting.pushay.core.BaseFragment;
import com.cerezaconsulting.pushay.data.entities.SchedulesEntity;
import com.cerezaconsulting.pushay.presentation.activities.CreateReservationActivity;
import com.cerezaconsulting.pushay.presentation.adapters.ListSchedulesAdapter;
import com.cerezaconsulting.pushay.utils.CircleTransform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 24/07/17.
 */

public class GuideDetailsFragment extends BaseFragment {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.title_destiny_name)
    TextView titleDestinyName;
    @BindView(R.id.tv_destiny_name)
    TextView tvDestinyName;
    @BindView(R.id.title_locality)
    TextView titleLocality;
    @BindView(R.id.tv_locality)
    TextView tvLocality;
    @BindView(R.id.title_hour)
    TextView titleHour;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.title_tv_price)
    TextView titleTvPrice;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.btn_next)
    Button btnNext;
    Unbinder unbinder;
    @BindView(R.id.tv_day_name)
    TextView tvDayName;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.rating)
    RatingBar rating;

    private SchedulesEntity schedulesEntity;
    private String date;

    public GuideDetailsFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        //mPresenter.start();
    }

    public static GuideDetailsFragment newInstance(Bundle bundle) {
        GuideDetailsFragment fragment = new GuideDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mSessionManager = new SessionManager(getContext());
        schedulesEntity = (SchedulesEntity) getArguments().getSerializable("schedulesEntity");
        date = getArguments().getString("date");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_guides_detail, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName.setText(schedulesEntity.getGuide().getFullName());
        tvDestinyName.setText(schedulesEntity.getDestiny().getName());
        tvDay.setText(getDay());
        tvDayName.setText(schedulesEntity.getDay().getName());
        tvLocality.setText(schedulesEntity.getLocality());
        tvHour.setText(schedulesEntity.getHour());
        tvPrice.setText(schedulesEntity.getPriceNormal());
        rating.setRating(Float.valueOf(schedulesEntity.getGuide().getClasification()));
        if (schedulesEntity.getGuide().getPicture() != null) {
            Glide.with(getContext())
                    .load(schedulesEntity.getGuide().getPicture())
                    .transform(new CircleTransform(getContext()))
                    .into(imageView);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public String getDay() {
        Date tempDate = null;
        SimpleDateFormat parseDateFromServer = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parseDateForShowDetail = new SimpleDateFormat("dd' de 'MMMM' del 'yyyy", new Locale("es", "ES"));
        try {
            tempDate = parseDateFromServer.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parseDateForShowDetail.format(tempDate);
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        bundle.putSerializable("schedulesEntity", schedulesEntity);
        next(getActivity(), bundle, CreateReservationActivity.class, false);
    }
}
