package com.hypermart.login.com.hypermart.login.gihan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.gihan.model.Review;
import com.hypermart.login.com.hypermart.login.helani.EditAccountFragment;
import com.hypermart.login.com.hypermart.login.vimanga.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static android.content.ContentValues.TAG;


public class AddProductReviewFragment extends Fragment implements View.OnClickListener{

    View view;
    DatabaseHelper db;
    Button submit, reset;
    EditText review;
    String star;
    String base64Header;
    String[] result;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy gfgPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);
        }
        view = Objects.requireNonNull(inflater).inflate(R.layout.gihan_activity_add_review, container, false);
        submit = (Button) view.findViewById(R.id.reviewsubmit);
        submit.setOnClickListener(this);
        db = new DatabaseHelper(getActivity());

        return view;
    }

    public void onClick(View view) {

        CustomerProductReviewFragment customerproductreviewFragment = new CustomerProductReviewFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, customerproductreviewFragment, "findThisFragment").addToBackStack(null).commit();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TextView productname,category,quantity,description;


        review =  getActivity().findViewById(R.id.review);




        register();



    }


    public void register() {
        submit.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                SharedPreferences sp2 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                String username = sp2.getString("CurrentUser", null);
                SharedPreferences sp = getActivity().getSharedPreferences("product", Context.MODE_PRIVATE);
                SharedPreferences sp1 = getActivity().getSharedPreferences("Category", Context.MODE_PRIVATE);

                String newProduct = sp.getString("ProductName", null);
                int rating = sp.getInt("rating",1);


                    Review  r = new Review();

                    r.setProductName(newProduct.toString().trim());
                    r.setReview(review.getText().toString().trim());
                System.out.println("Review:"+review.getText().toString().trim());
                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .build();

                AndroidNetworking.initialize(getActivity().getApplicationContext(),okHttpClient);
                ANRequest request = AndroidNetworking.post("http://urooba.pythonanywhere.com/predictstar")
                        .setPriority(Priority.HIGH)
                        .addBodyParameter("text",review.getText().toString().trim())
                        .addHeaders("Project", base64Header)
                        .addHeaders("Content-Type", "application/json")
                        .build()
                        ;


                ANResponse response = request.executeForJSONObject();

                if (response.isSuccess()) {
                    String results = response.getResult().toString();
                    JSONObject json = null;
                    try {
                        json = new JSONObject(results);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(json.toString());
                    String star = null;
                    try {
                        star = json.getString("star");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Rate:"+star);
                    r.setStar(star);
                    Log.d(TAG, "response : " + star);
                } else {
                    ANError error = response.getError();
                    Log.d(TAG, "response : " + error);
                }

                    //r.setStar(star);
                    r.setUserName(username.toString().trim());


                    boolean isInserted = db.addReview(r);

                   if (isInserted == true) {


                        Toast.makeText(getActivity(), "Successful!", Toast.LENGTH_SHORT).show();
                       CustomerProductReviewFragment ReviewsProductsFragment = new CustomerProductReviewFragment();
                       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ReviewsProductsFragment, "findThisFragment").addToBackStack(null).commit();

                   } else {
                        showMessage("Failed!", "Please Fill out the fields correctly");
                    }



            }
        });

    }


    public void showMessage(String title,String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }


    /////////////////////////////////


}

