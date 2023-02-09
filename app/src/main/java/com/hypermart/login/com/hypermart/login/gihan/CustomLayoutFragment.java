package com.hypermart.login.com.hypermart.login.gihan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.hypermart.login.R;

/**
 * Created by HP on 8/10/2018.
 */

public class CustomLayoutFragment extends Fragment {

    View view;
    Button btn1, btn2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.customercustomlayout, container, false);
//        btn1 = (Button) view.findViewById(R.id.addToCart);
//        btn1.setOnClickListener(this);
//        btn2 = (Button) view.findViewById(R.id.buy);
//        btn2.setOnClickListener(this);

        return inflater.inflate(R.layout.customercustomlayout, container, false);

    }

//    public void onClick(View view) {
//
//
//        switch (view.getId()) {
//
//            case R.id.buy: {
//                DeliverInfoFragment deliverInfoFragment = new DeliverInfoFragment();
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, deliverInfoFragment, "findThisFragment").addToBackStack(null).commit();
//            }
//
//
//            case R.id.addToCart: {
//                ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, shoppingCartFragment, "findThisFragment").addToBackStack(null).commit();
//
//                break;
//            }
//
//
//        }
//    }
}

