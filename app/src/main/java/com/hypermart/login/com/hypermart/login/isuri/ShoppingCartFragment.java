package com.hypermart.login.com.hypermart.login.isuri;

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
import com.hypermart.login.com.hypermart.login.gihan.DeliverInfoFragment;
import com.hypermart.login.com.hypermart.login.gihan.Main;
import com.hypermart.login.com.hypermart.login.isuri.model.Cart;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCartFragment extends Fragment {


    View view;
    Button btn,deletecart;
    DatabaseHelper db;
    List<String> CARTPRODUCTNAME= new ArrayList<>();
    List<String> CARTPRODUCTQUANTITY= new ArrayList<>();
    List<String> CARTPRODUCTPRICE = new ArrayList<String>();
    List<Integer> CARTPRODUCTIMAGE = new ArrayList<>();
    List<String> CARTUSERNAME= new ArrayList<String>();
    String strText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.isuri_activity_shopping_cart, container, false);
        btn = (Button) view.findViewById(R.id.checkout);
        deletecart = (Button) view.findViewById(R.id.Cartdelete);


        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                       DeliverInfoFragment deliverInfoFragment = new DeliverInfoFragment();
                                       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, deliverInfoFragment, "findThisFragment").addToBackStack(null).commit();

                                   }
                               });
        deletecart.setOnClickListener(new View.OnClickListener() {
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
                db = new DatabaseHelper(getActivity());

        SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        strText = sp1.getString("CurrentUser", null);


        Cursor rs = db.getDataCartall();
        if(rs.getCount()==0){


            Toast.makeText(getActivity(),"Add a product to cart first",Toast.LENGTH_LONG).show();
            getActivity().startActivity(new Intent(getActivity(), Main.class));
            showMessage("Error","Add a product to cart first");



        }

        else {

            while(rs.moveToNext()){

                String newcartproductname=String.valueOf(rs.getString(1));
                String newcartproductquantity=String.valueOf(rs.getString(2));
                String newcartproductprice= String.valueOf(rs.getString(3));
                String newusername= String.valueOf(rs.getString(4));
                String newcartproductimage= String.valueOf(rs.getString(5));
                System.out.println(newcartproductname+newcartproductquantity+newcartproductprice+newcartproductimage);
                if( rs.getString(4).equals(strText)) {


                    CARTPRODUCTNAME.add(newcartproductname);
                    CARTPRODUCTIMAGE.add(Integer.valueOf(newcartproductimage));
                    CARTPRODUCTPRICE.add(newcartproductprice);
                    CARTPRODUCTQUANTITY.add(newcartproductquantity);
                    CARTUSERNAME.add(newusername);
                    // quantity.setText(newAmount);
                    // total.setText(newTotal);
                }

            }

        }

        ListView listView = (ListView) view.findViewById(R.id.listviewcart);
        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);


        return view;

    }


    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            int length=0;
            db = new DatabaseHelper(getActivity());
            Cursor rs = db.getDataCartall();


            if(rs.getCount()==0){


                Toast.makeText(getActivity(),"Add Comment first.",Toast.LENGTH_LONG).show();
                // getActivity().startActivity(new Intent(getActivity(), Main.class));
                // showMessage("Error","Add comment first.");





            }

            else {

                while(rs.moveToNext()){
                    if( rs.getString(4).equals(strText)) {
                        length = length + 1;
                    }

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
        public View getView(final int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.gihan_customercustomcart, null);

            Button quantityIncrement,quantityDecrement;

            ImageView cancelcart;



            TextView cartproductname = (TextView) view.findViewById(R.id.cartproductname);
            ImageView cartproductimage = (ImageView) view.findViewById(R.id.cartproductimage) ;
            quantityDecrement = (Button) view.findViewById(R.id.quantityDecrement);
            quantityIncrement = (Button) view.findViewById(R.id.quantityIncrement);
            TextView quantity =(TextView) view.findViewById(R.id.cartQuantity);
            TextView total = getActivity().findViewById(R.id.cartTotal);
            cancelcart= (ImageView) view.findViewById(R.id.cancelCart);
            String newcartproduct= CARTPRODUCTNAME.get(i);
            Integer newcartproductimage= CARTPRODUCTIMAGE.get(i);
            String newAmount = String.valueOf(CARTPRODUCTQUANTITY.get(i));
            String newTotal = CARTPRODUCTPRICE.get(i);
            cartproductimage.setImageResource(Integer.valueOf(newcartproductimage));
            cartproductname.setText(newcartproduct);
            quantity.setText(newAmount);
            total.setText(newTotal);

            View finalView = view;
            quantityIncrement.setOnClickListener(new View.OnClickListener() {

                SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

                String strText = sp1.getString("CurrentUser", null);

                Cursor rs1 = db.getDataCartall();
                TextView quantity = (TextView) finalView.findViewById(R.id.cartQuantity);
                TextView total = getActivity().findViewById(R.id.cartTotal);



                @Override
                public void onClick(View view) {

                    while(rs1.moveToNext()){
                        if( rs1.getString(4).equals(strText)) {

                            String newQty = String.valueOf(rs1.getInt(1));
                            String newAmount = String.valueOf(rs1.getFloat(2));

                            quantity.setText(newQty);
                            total.setText(newAmount);

                        }


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
                    cart.setTotal( t);
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

            View finalView1 = view;
            quantityDecrement.setOnClickListener(new View.OnClickListener() {


                SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

                String strText = sp1.getString("CurrentUser", null);

                Cursor rs1 = db.getDataCartall();
                TextView quantity = (TextView) finalView1.findViewById(R.id.cartQuantity);
                TextView total = getActivity().findViewById(R.id.cartTotal);




                @Override
                public void onClick(View view) {


                    while(rs1.moveToNext()){
                        if( rs1.getString(4).equals(strText)) {

                            String newQty = String.valueOf(rs1.getInt(1));
                            String newAmount = String.valueOf(rs1.getFloat(2));

                            quantity.setText(newQty);
                            total.setText(newAmount);
                        }
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
                        cart.setTotal( t);
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
                    String newcartproduct= CARTPRODUCTNAME.get(i);
                    String itemid = (String) cartproductname.getText();
                    cart.setProductName(itemid);

                    Integer deleted =db.deleteCartitem(cart);

                    if(deleted > 0){

                        Toast.makeText(getActivity(),"Cart Data deleted",Toast.LENGTH_LONG).show();
                        getActivity().startActivity(new Intent(getActivity(), Main.class));

                    }

                    else

                        Toast.makeText(getActivity(),"Error!",Toast.LENGTH_LONG).show();

                }
            });





            return view;
        }

    }

    public void showMessage(String title,String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }


}
