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
import android.widget.TextView;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.gihan.DeliverInfoFragment;
import com.hypermart.login.com.hypermart.login.gihan.Main;
import com.hypermart.login.com.hypermart.login.isuri.model.Cart;


public class ShoppingCartFragment extends Fragment implements View.OnClickListener {


    View view;
    Button btn;
    DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.isuri_activity_shopping_cart, container, false);
        btn = (Button) view.findViewById(R.id.checkout);
        btn.setOnClickListener(this);
        db = new DatabaseHelper(getActivity());

        return view;

    }

    public void onClick(View view) {

        DeliverInfoFragment deliverInfoFragment = new DeliverInfoFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, deliverInfoFragment, "findThisFragment").addToBackStack(null).commit();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button quantityIncrement,quantityDecrement;

        ImageView cancelcart;




        quantityDecrement = getActivity().findViewById(R.id.quantityDecrement);
        quantityIncrement = getActivity().findViewById(R.id.quantityIncrement);
        TextView quantity = getActivity().findViewById(R.id.cartQuantity);
        TextView total = getActivity().findViewById(R.id.cartTotal);
        cancelcart = getActivity().findViewById(R.id.cancelCart);


        SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        String strText = sp1.getString("CurrentUser", null);

        Cursor rs = db.getDataCart(strText);


        if(rs.getCount()==0){


            Toast.makeText(getActivity(),"Add a product to cart first",Toast.LENGTH_LONG).show();
            getActivity().startActivity(new Intent(getActivity(), Main.class));
            showMessage("Error","Add a product to cart first");


            return;

        }

        else {

            while(rs.moveToNext()){

                String newAmount = String.valueOf(rs.getInt(1));
                String newTotal  = String.valueOf(rs.getFloat(2));

                quantity.setText(newAmount);
                total.setText(newTotal);


            }

        }





        quantityIncrement.setOnClickListener(new View.OnClickListener() {

            SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

            String strText = sp1.getString("CurrentUser", null);

            Cursor rs1 = db.getDataCart(strText);
            TextView quantity = getActivity().findViewById(R.id.cartQuantity);
            TextView total = getActivity().findViewById(R.id.cartTotal);



            @Override
            public void onClick(View view) {

                while(rs1.moveToNext()){

                    String newQty = String.valueOf(rs1.getInt(1));
                    String newAmount = String.valueOf(rs1.getFloat(2));

                    quantity.setText(newQty);
                    total.setText(newAmount);




                }


                CharSequence qt = quantity.getText();
                int n = Integer.parseInt(qt.toString());

                n = n + 1;

                int t = 100 * n;


                quantity.setText("" + n);
                total.setText("" + t);

                Cart cart = new Cart();

                SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

                String strText = sp1.getString("CurrentUser", null);

                cart.setQuantity(n);
                cart.setTotal(t);
                cart.setUserName(strText);

                Boolean isUpdated = db.updateCart(cart);

                if(isUpdated == true){

                    Toast.makeText(getActivity(),"Quantity Increment Updated",Toast.LENGTH_SHORT).show();
                }

                else{

                    Toast.makeText(getActivity(),"Quantity Update Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

        quantityDecrement.setOnClickListener(new View.OnClickListener() {


            SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

            String strText = sp1.getString("CurrentUser", null);

            Cursor rs1 = db.getDataCart(strText);
            TextView quantity = getActivity().findViewById(R.id.cartQuantity);
            TextView total = getActivity().findViewById(R.id.cartTotal);




            @Override
            public void onClick(View view) {


                while(rs1.moveToNext()){

                    String newQty = String.valueOf(rs1.getInt(1));
                    String newAmount = String.valueOf(rs1.getFloat(2));

                    quantity.setText(newQty);
                    total.setText(newAmount);

                }


                CharSequence qt = quantity.getText();
                int n = Integer.parseInt(qt.toString());
                if(n > 0) {
                    n = n - 1;

                    int t = 100 * n;
                    quantity.setText("" + n);

                    total.setText("" + t);


                    Cart cart = new Cart();

                    SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

                    String strText = sp1.getString("CurrentUser", null);

                    cart.setQuantity(n);
                    cart.setTotal(t);
                    cart.setUserName(strText);

                    Boolean isUpdated = db.updateCart(cart);

                    if (isUpdated == true) {

                        Toast.makeText(getActivity(), "Quantity Decrement Updated", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getActivity(), "Quantity Update Failed", Toast.LENGTH_SHORT).show();
                    }

                }

                else{

                    Toast.makeText(getActivity(),"Quantity must have a positive value ",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });


        cancelcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

                String strText = sp1.getString("CurrentUser", null);

                Cart cart = new Cart();

                cart.setUserName(strText);

                Integer deleted =db.deleteCart(cart);

                if(deleted > 0){

                    Toast.makeText(getActivity(),"Cart Data deleted",Toast.LENGTH_LONG).show();
                    getActivity().startActivity(new Intent(getActivity(), Main.class));

                }

                else

                    Toast.makeText(getActivity(),"Error!",Toast.LENGTH_LONG).show();

            }
        });


    }



    public void showMessage(String title,String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }


}
