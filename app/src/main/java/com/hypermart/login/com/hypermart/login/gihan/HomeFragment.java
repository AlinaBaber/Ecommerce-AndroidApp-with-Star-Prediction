package com.hypermart.login.com.hypermart.login.gihan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hypermart.login.com.hypermart.login.vimanga.ProductFragment;
import com.hypermart.login.R;


public class HomeFragment extends Fragment {

    View view;
    ImageView img1,img2,img3,img4;
    String strText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String[] Category= new String[]{"LEDs","Transistors","Drivers","Sensors"};
        view = inflater.inflate(R.layout.gihan_activity_home, container, false);
        final SharedPreferences sp1 = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        final SharedPreferences sp = this.getActivity().getSharedPreferences("Category", Context.MODE_PRIVATE);
        img1= (ImageView) view.findViewById(R.id.lipgloss);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    strText = sp1.getString("CurrentUser", null);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("CurrentCategory",Category[0]);
                    ed.commit();
                if(strText.equals("Admin")){

                    ProductFragment productFragment = new ProductFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, productFragment, "findThisFragment").addToBackStack(null).commit();

                }

                else{
                    CustomerProductFragment customerProductFragment = new CustomerProductFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, customerProductFragment, "findThisFragment").addToBackStack(null).commit();
                }


            }
        });
        img2= (ImageView) view.findViewById(R.id.lipsticks);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strText = sp1.getString("CurrentUser", null);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("CurrentCategory",Category[1]);
                    ed.commit();
                if(strText.equals("Admin")){

                    ProductFragment productFragment = new ProductFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, productFragment, "findThisFragment").addToBackStack(null).commit();

                }

                else{
                    CustomerProductFragment customerProductFragment = new CustomerProductFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, customerProductFragment, "findThisFragment").addToBackStack(null).commit();
                }


            }
        });
        img3= (ImageView) view.findViewById(R.id.eyeproducts);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strText = sp1.getString("CurrentUser", null);

                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("CurrentCategory",Category[2]);
                    ed.commit();
                if(strText.equals("Admin")){

                    ProductFragment productFragment = new ProductFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, productFragment, "findThisFragment").addToBackStack(null).commit();

                }

                else{
                    CustomerProductFragment customerProductFragment = new CustomerProductFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, customerProductFragment, "findThisFragment").addToBackStack(null).commit();
                }

            }
        });
        img4= (ImageView) view.findViewById(R.id.faceproducts);
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strText = sp1.getString("CurrentUser", null);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("CurrentCategory",Category[3]);
                    ed.commit();
                if(strText.equals("Admin")){

                    ProductFragment productFragment = new ProductFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, productFragment, "findThisFragment").addToBackStack(null).commit();

                }

                else{
                    CustomerProductFragment customerProductFragment = new CustomerProductFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, customerProductFragment, "findThisFragment").addToBackStack(null).commit();
                }
            }
        });



        return view;
    }


}
