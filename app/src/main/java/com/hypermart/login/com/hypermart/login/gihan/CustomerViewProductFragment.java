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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.isuri.ShoppingCartFragment;
import com.hypermart.login.com.hypermart.login.isuri.model.Cart;
import com.hypermart.login.com.hypermart.login.vimanga.EditProductsFragment;

import java.util.ArrayList;
import java.util.List;

import static com.hypermart.login.R.id.price;
import static com.hypermart.login.R.id.userName;

public class CustomerViewProductFragment extends Fragment implements View.OnClickListener {

    View view;
    Button btn,cart,buy;
    DatabaseHelper db;
    String strText;
    RatingBar star;
    TextView name,category,price,quantity,description;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gihan_activity_view_product, container, false);

        btn = (Button) view.findViewById(R.id.viewreview);
        db = new DatabaseHelper(getActivity());
        btn.setOnClickListener(this);
        return view;
    }


    public void onClick(View view) {

        CustomerProductReviewFragment ReviewsProductsFragment = new CustomerProductReviewFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ReviewsProductsFragment, "findThisFragment").addToBackStack(null).commit();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView name,category,price,quantity,description;
        ImageView img=  (ImageView) getActivity().findViewById(R.id.productview);
        name= (TextView) getActivity().findViewById(R.id.name);
        category =getActivity().findViewById(R.id.category);
        price = getActivity().findViewById(R.id.price);
        quantity = getActivity().findViewById(R.id.quantity);
        description = getActivity().findViewById(R.id.description);
        cart = getActivity().findViewById(R.id.addcart);
        buy = getActivity().findViewById(R.id.buybtn);
        star = getActivity().findViewById(R.id.ratingBarstar);

        SharedPreferences sp = getActivity().getSharedPreferences("product", Context.MODE_PRIVATE);
        SharedPreferences sp1 = getActivity().getSharedPreferences("Category", Context.MODE_PRIVATE);

        String newProduct = sp.getString("ProductName", null);
        String newCategory =  sp.getString("category", null);
        int rating = sp.getInt("rating",0);
        int newQuantity = sp.getInt("Quantity", 0);
        int newprice = sp.getInt("Price", 0);
        int image = sp.getInt("productimage", 0);
        String newDescription= sp.getString("Description",null);
        img.setImageResource(image);
        name.setText(newProduct);
        category.setText(newCategory);
        price.setText("Rs."+String.valueOf(newprice));
        quantity.setText(String.valueOf(newQuantity));
        description.setText(newDescription);
        RatingBar starstatus = (RatingBar)view.findViewById(R.id.ratingBar);
        Float rate;
        rate = Float.valueOf(getTotalStars(newProduct));
        starstatus.setNumStars(5);
        starstatus.setRating(rate);
       // Cursor rs = db.getProductById(strText);


       // if(rs.getCount()==0){
       //     showMessage("Error","Nothing Found");
       //     return;

      //  }

      //  else {

      //      while(rs.moveToNext()){

       //         newProduct= rs.getString(1);
      //          newCategory = rs.getString(2);
      //          String newprices = rs.getString(3);
        //        String newQuantitys = rs.getString(4);
        //        newDescription = rs.getString(5);
        //        String newimage= rs.getString(6);
        //        img.setImageResource(Integer.valueOf(image));
        //        name.setText(newProduct);
        //        category.setText(newCategory);
         //       price.setText(newprices);
         //       quantity.setText(newQuantitys);
         //       description.setText(newDescription);
         //       starstatus.setNumStars(5);
          //      starstatus.setRating((float) rate);

        //  }

      //  }
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

                String strText = sp1.getString("CurrentUser", null);

                SharedPreferences sp = getActivity().getSharedPreferences("product", Context.MODE_PRIVATE);

                String productname = sp.getString("productname", null);
                int quantity = sp.getInt("Quantity", 0);
                int price = sp.getInt("Price", 0);
                int image = sp.getInt("productimage",0);
                Cart c= new Cart();
                c.setImage(String.valueOf(image));
                c.setProductName(productname);
                c.setQuantity(quantity);
                c.setTotal(price);
                c.setUserName(strText);
                boolean isInserted = db.insertCart(c);

                if (isInserted == true) {


                    Toast.makeText(getActivity(), "Data Added to database", Toast.LENGTH_SHORT).show();
                    System.out.println("Data Added to database");
                    ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, shoppingCartFragment, "findThisFragment").addToBackStack(null).commit();

                } else {
                    showMessage("Error", "Cannot Add Data");
                    System.out.println("Failed to add in database");
                }


            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(),"Loading Order Process",Toast.LENGTH_SHORT).show();
                DeliverInfoFragment deliverInfoFragment = new DeliverInfoFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, deliverInfoFragment, "findThisFragment").addToBackStack(null).commit();

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
    public int getTotalStars(String productname){
        int totalstars =0;
        Cursor rs = db.getReviewbyID(productname);
        int[] reviewstars;

        List<String> STARS = new ArrayList<>();
        if(rs.getCount()==0){


            Toast.makeText(getActivity(),"Add Comment first.",Toast.LENGTH_LONG).show();
            //getActivity().startActivity(new Intent(getActivity(), Main.class));
            // showMessage("Error","Add comment first.");
            totalstars= 0;



        }

        else {

            while(rs.moveToNext()){

                String c1 = String.valueOf(rs.getString(0));
                String c2 = String.valueOf(rs.getString(1));
                String c3 = String.valueOf(rs.getString(2));
                String c4=  String.valueOf(rs.getString(3));
                String c5=  String.valueOf(rs.getString(4));
                Integer rate=Integer.valueOf(rs.getString(2));
                System.out.println(c2+":"+c4+": Rate:"+c3);
                Toast.makeText(getActivity(),"Rate:"+c1+c2+c3+c4+c5,Toast.LENGTH_LONG).show();
                System.out.println(c1+c2+c3+c4+c5);
                STARS.add(c3);
                totalstars=totalstars+rate;

            }
            totalstars= totalstars/STARS.size();

        }
        System.out.println("Rate list:"+STARS);
        System.out.println("Total Rate:"+totalstars);
        Toast.makeText(getActivity(),"Total Rate:"+totalstars,Toast.LENGTH_LONG).show();
        return totalstars;
    }
}
