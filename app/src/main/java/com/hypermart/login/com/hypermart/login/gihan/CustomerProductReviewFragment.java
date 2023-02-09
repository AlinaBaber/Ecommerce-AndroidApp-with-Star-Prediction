package com.hypermart.login.com.hypermart.login.gihan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.isuri.ShoppingCartFragment;
import com.hypermart.login.com.hypermart.login.vimanga.ProductFragment;

import java.util.ArrayList;
import java.util.List;


public class CustomerProductReviewFragment extends Fragment   {


    View view;
    Button btn1,addreviews;
    DatabaseHelper db;
    List<String> USERNAMES = new ArrayList<>();
    List<String> REVIEWS = new ArrayList<>();
    List<String> STARS = new ArrayList<>();
    String name,review;
    String star;
    String strText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.gihan_activity_customer_productsreview, container, false);

        final SharedPreferences sp2 = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        final SharedPreferences sp1 = this.getActivity().getSharedPreferences("product", Context.MODE_PRIVATE);
        strText = sp1.getString("ProductName", null);
        db = new DatabaseHelper(getActivity());
        Cursor rs = db.getReviewbyID(strText);


        if(rs.getCount()==0){


            Toast.makeText(getActivity(),"Add Comment first.",Toast.LENGTH_LONG).show();
            // getActivity().startActivity(new Intent(getActivity(), Main.class));
            // showMessage("Error","Add comment first.");





        }

        else {

            while(rs.moveToNext()){

                String newReviewid = String.valueOf(rs.getInt(0));
                String newproductname=rs.getString(1);
                String newstar= rs.getString(2);
                //String newusername= rs.getString(3);
                String newReview= rs.getString(4);
                String newusername= rs.getString(3);
                USERNAMES.add(newusername);
                REVIEWS.add(newReview);
                STARS.add(newstar);
            }}

        ListView listView = (ListView) view.findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();

        btn1 = (Button) view.findViewById(R.id.addToCart);

        listView.setAdapter(customAdapter);

        db = new DatabaseHelper(getActivity());
        addreviews= (Button) view.findViewById(R.id.addreview);
        addreviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strText = sp2.getString("CurrentUser", null);
                if(strText.equals("Admin")){

                    AddProductReviewFragment addproductreviewsFragment = new AddProductReviewFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addproductreviewsFragment, "findThisFragment").addToBackStack(null).commit();

                }

                else{
                    AddProductReviewFragment addproductreviewsFragment = new AddProductReviewFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addproductreviewsFragment, "findThisFragment").addToBackStack(null).commit();

                }


            }
        });
        return view;

    }


    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            int length=0;
            db = new DatabaseHelper(getActivity());
            Cursor rs = db.getReviewbyID(strText);


            if(rs.getCount()==0){


                Toast.makeText(getActivity(),"Add Comment first.",Toast.LENGTH_LONG).show();
                // getActivity().startActivity(new Intent(getActivity(), Main.class));
                // showMessage("Error","Add comment first.");





            }

            else {

                while(rs.moveToNext()){
                    length=length+1;


                    //SERNAMES[]=newusername;
                    //EVIEWS[]=newReview;
                    //ARS.add(newstar);
                }}
            return length;
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

            view = getLayoutInflater().inflate(R.layout.gihan_customercustomreview, null);
            SharedPreferences sp = getActivity().getSharedPreferences("product", Context.MODE_PRIVATE);
            String newProduct = sp.getString("ProductName", null);
            TextView username = (TextView) view.findViewById(R.id.username);
            TextView reviewcomment = (TextView) view.findViewById(R.id.reviewcomment);
            TextView starrate = (TextView) view.findViewById(R.id.starrate);


                Toast.makeText(getActivity(),"Add Comment first.",Toast.LENGTH_LONG).show();

                    username.setText(USERNAMES.get(i));
                    reviewcomment.setText(REVIEWS.get(i));
                    starrate.setText(STARS.get(i));


            return view;
        }

    }


    public void showMessage(String title,String message)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }



}

