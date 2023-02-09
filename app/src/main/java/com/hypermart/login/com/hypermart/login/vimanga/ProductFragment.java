package com.hypermart.login.com.hypermart.login.vimanga;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hypermart.login.R;


public class ProductFragment extends Fragment implements View.OnClickListener {

    View view, bn;
    Button btn, etbtn;
    int[] IMAGES = {R.drawable.producta, R.drawable.productb, R.drawable.productc,R.drawable.productg};
    String[] NAMES = {"Kylie Lipgloss", "Madora lipstick", "Dior Eyeliner", "Dior Mascara"};
    String[] DESCRIPTIONS = {"Product Details", "Product Details", "Product Details", "Product Details"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.vimanga_activity_products, container, false);
        bn = inflater.inflate(R.layout.vimanga_customlayout, container, false);


        ListView listView = (ListView) view.findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);


        btn = (Button) view.findViewById(R.id.addproducts);
        btn.setOnClickListener(this);
        etbtn = (Button) bn.findViewById(R.id.editItem);
        etbtn.setOnClickListener(this);


        return view;

    }

    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.vimanga_customlayout, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textView_name = (TextView) view.findViewById(R.id.textView_name);
            TextView textView_description = (TextView) view.findViewById(R.id.textView_description);
            Button edit =(Button)view.findViewById(R.id.editItem);

            imageView.setImageResource(IMAGES[i]);
            textView_name.setText(NAMES[i]);
            textView_description.setText(DESCRIPTIONS[i]);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ViewProductFragment viewProductFragment = new ViewProductFragment();

                    SharedPreferences sp2 = getActivity().getSharedPreferences("product", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp2.edit();
                    ed.putString("ProductName","LEDs");
                    ed.commit();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewProductFragment, "findThisFragment").addToBackStack(null).commit();

                }
            });

            return view;
        }

    }

    public void onClick(View view) {


       AddProductFragment addProductFragment = new AddProductFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,addProductFragment,"findThisFragment").addToBackStack(null).commit();





        }


    }



