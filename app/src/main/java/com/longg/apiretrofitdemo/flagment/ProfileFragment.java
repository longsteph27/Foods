package com.longg.apiretrofitdemo.flagment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.longg.apiretrofitdemo.Activity.MainActivity;
import com.longg.apiretrofitdemo.R;

public class ProfileFragment extends Fragment {
    ImageView imggallerry_avt;
    TextView tvname, tvemail;
    EditText edtfirstname, edtlastname, edtdob, edtweight, edtheight;
    private Uri mUri;
    private View mView;
    MainActivity mMainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        mMainActivity = (MainActivity) getActivity();
        setfields(mView);
        imggallerry_avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(mMainActivity)
                        .crop()
                        /*.compress(1024)
                        .maxResultSize(1080, 1080)*/
                        .start();
            }
        });
        return mView;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        mUri = uri;
        imggallerry_avt.setImageURI(uri);
    }

    private void setfields(View mView) {
        edtfirstname = mView.findViewById(R.id.tv_first_name);
        edtlastname = mView.findViewById(R.id.tv_last_name);
        tvname = mView.findViewById(R.id.tv_name);
        tvemail = mView.findViewById(R.id.tv_email);
        edtdob = mView.findViewById(R.id.tv_dob);
        edtweight = mView.findViewById(R.id.tv_weight);
        edtheight = mView.findViewById(R.id.tv_height);
        imggallerry_avt = mView.findViewById(R.id.image_edit_avt);

    }
}