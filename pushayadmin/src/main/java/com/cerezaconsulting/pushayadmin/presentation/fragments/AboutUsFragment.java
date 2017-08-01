package com.cerezaconsulting.pushayadmin.presentation.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cerezaconsulting.pushayadmin.R;
import com.cerezaconsulting.pushayadmin.core.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by katherine on 31/07/17.
 */

public class AboutUsFragment extends BaseFragment {


    @BindView(R.id.photo_profile)
    ImageView photoProfile;
    @BindView(R.id.ly_image_profile)
    LinearLayout lyImageProfile;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tv_personal)
    TextView tvPersonal;
    @BindView(R.id.ly_personal_edit)
    LinearLayout lyPersonalEdit;
    @BindView(R.id.im_cel)
    ImageView imCel;
    @BindView(R.id.tv_cel_detail)
    TextView tvCelDetail;
    @BindView(R.id.ly_cel)
    LinearLayout lyCel;
    @BindView(R.id.im_email)
    ImageView imEmail;
    @BindView(R.id.tv_email_detail)
    TextView tvEmailDetail;
    @BindView(R.id.ly_email)
    LinearLayout lyEmail;
    @BindView(R.id.im_wsp)
    ImageView imWsp;
    @BindView(R.id.tv_name_detail)
    TextView tvNameDetail;
    @BindView(R.id.ly_personal)
    LinearLayout lyPersonal;
    Unbinder unbinder;

    public static AboutUsFragment newInstance() {
        return new AboutUsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_us,  container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.im_cel, R.id.im_email, R.id.im_wsp, R.id.tv_cel_detail, R.id.tv_email_detail, R.id.tv_name_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_cel:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+51992406360"));
                startActivity(intent);
                break;

            case R.id.tv_cel_detail:
                Intent intentCel = new Intent(Intent.ACTION_DIAL);
                intentCel.setData(Uri.parse("tel:+51992406360"));
                startActivity(intentCel);
                break;

            case R.id.im_email:
                Intent itSend = new Intent(Intent.ACTION_SEND);
                //vamos a enviar texto plano
                itSend.setType("plain/text");
                //colocamos los datos para el envío
                itSend.putExtra(Intent.EXTRA_EMAIL, new String[]{"katherine.caillahua@gmail.com"});
                startActivity(itSend);
                break;

            case R.id.tv_email_detail:
                Intent intentSend = new Intent(Intent.ACTION_SEND);
                //vamos a enviar texto plano
                intentSend.setType("plain/text");
                //colocamos los datos para el envío
                intentSend.putExtra(Intent.EXTRA_EMAIL, new String[]{"katherine.caillahua@gmail.com"});
                startActivity(intentSend);
                break;

            case R.id.im_wsp:
                Uri uri = Uri.parse("smsto:+51992406360");
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(i, ""));
                break;

            case R.id.tv_name_detail:
                Uri uriWsp = Uri.parse("smsto:+51992406360");
                Intent iWsp = new Intent(Intent.ACTION_SENDTO, uriWsp);
                iWsp.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(iWsp, ""));
                break;
        }
    }
}
