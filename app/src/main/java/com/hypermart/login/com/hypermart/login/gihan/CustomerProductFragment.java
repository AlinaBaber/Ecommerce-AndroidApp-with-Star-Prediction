package com.hypermart.login.com.hypermart.login.gihan;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.isuri.ShoppingCartFragment;
import com.hypermart.login.com.hypermart.login.isuri.model.Cart;
import com.hypermart.login.com.hypermart.login.vimanga.model.Product;

import java.util.ArrayList;
import java.util.List;


public class CustomerProductFragment extends Fragment   {


    View view;
    Button btn1;
    DatabaseHelper db;
    List<String> USERNAMES = new ArrayList<>();
    List<String> IMAGES= new ArrayList<>();
    List<String> NAMES= new ArrayList<>();
    List<String> DESCRIPTIONS= new ArrayList<>();
    List<String> QUANTITY= new ArrayList<>();
    List<String> PRICE= new ArrayList<>();
    List<Integer> RATING= new ArrayList<>();
    int j;
    List<String> CATEGORY;
    String productname,description,category;
    int price;
    int quantity;
    int image;
    float rating;
    String currentCategory;
    int[] IMAGES1;
    String[] NAMES1;
    String[] DESCRIPTIONS1;
    int[] QUANTITY1;
    int[] PRICE1;
    int[] RATING1;

    String strText;
    String[] CATEGORY1;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.gihan_activity_customer_products, container, false);
        SharedPreferences sp1 = this.getActivity().getSharedPreferences("Category", Context.MODE_PRIVATE);

        String strText = sp1.getString("CurrentCategory", null);
        if (strText.equals("LEDs")){
            IMAGES1= new int[]{R.drawable.productl, R.drawable.producti};
            NAMES1 = new String[]{"P10 LED Display", "LED Light Strips 3V DC",};
            DESCRIPTIONS1 = new String[]{"Panel LED Module, Product SKU: CKT- 1379", "Product SKU: CKT-1163"};
            PRICE1 = new int[]{938, 300};
            QUANTITY1 = new int[]{1000, 1000};
            RATING1= new int[]{0,0};
            CATEGORY1= new String[]{"LEDs","LEDs"};

        }
        else         if (strText.equals("Transistors")){
            IMAGES1 = new int[]{R.drawable.productd, R.drawable.producto};
            NAMES1 = new String[]{"PNP TRANSISTOR TIP147T", "6N136 8 Pin Transistor"};
            DESCRIPTIONS1 = new String[]{"Complementary power Darlington transistor TIP147", "Optocoupler ,Product SKU: CKT-2214"};
            PRICE1 = new int[]{120, 100};
            QUANTITY1 = new int[]{1000, 1000};
            RATING1= new int[]{0,0};
            CATEGORY1= new String[]{"Transistors","Transistors"};
        }
        else         if (strText.equals("Drivers")){
            IMAGES1 = new int[]{R.drawable.producth,R.drawable.productg};
            NAMES1 = new String[]{"TB6600 Stepper driver", "TB6560 Stepper motor driver"};
            DESCRIPTIONS1 = new String[]{"9V 40VDC CNC router machine part tools , Product SKU: EZ-073-CKT-073", "3A DC 10V-35V"};
            PRICE1 = new int[]{2200, 1199};
            QUANTITY1 = new int[]{1000, 1000};
            RATING1= new int[]{0,0};
            CATEGORY1= new String[]{"Drivers","Drivers"};
        }
        else {
            IMAGES1 = new int[]{R.drawable.productn, R.drawable.productk};
            NAMES1 = new String[]{"HC-SR04 Sensor", "MICROPHONE SENSOR"};
            DESCRIPTIONS1 = new String[]{"Ultrasonic Brand: Circuit PK Product SKU: EZ-071-CKT-071", "HIGH SENSITIVITY SOUND DETECTION MODULE FOR ARDUINO AVR PIC"};
            PRICE1 = new int[]{400, 530};
            RATING1= new int[]{0,0};
            QUANTITY1 = new int[]{1000, 1000};
            CATEGORY1= new String[]{"Sensors","Sensors"};
        }

        listView = (ListView) view.findViewById(R.id.listview);

        final CustomAdapter customAdapter = new CustomAdapter();

        btn1 = (Button) view.findViewById(R.id.addToCart);

        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                customAdapter.getItem(pos);
                j=pos;
                // showMessage("error", String.valueOf(j));
                SharedPreferences sp2 = getActivity().getSharedPreferences("product", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp2.edit();
                SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                String strText = sp1.getString("CurrentUser", null);
                ed.putString("ProductName", NAMES1[j]);
                ed.putInt("Price", PRICE1[j]);
                ed.putInt("Quantity", QUANTITY1[j]);
                ed.putString("Description", DESCRIPTIONS1[j]);
                ed.putInt("rating", getTotalStars(NAMES1[j]));
                ed.putString("category", CATEGORY1[j]);
                ed.putInt("productimage",IMAGES1[j]);
                ed.putInt("i", j);
                ed.commit();
                productname = null;
                quantity = 0;
                price = 0;
                description = null;
                rating = 0;
                category = null;
                image = 0;


                CustomerViewProductFragment viewProductFragment = new CustomerViewProductFragment();


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, viewProductFragment, "findThisFragment").addToBackStack(null).commit();

            }
        });


        db = new DatabaseHelper(getActivity());

        return view;

    }

    public int addProductstoList(){
        Cursor rs = db.getProductByCategory(currentCategory);

        if(rs.getCount()==0){


            Toast.makeText(getActivity(),"Add Comment first.",Toast.LENGTH_LONG).show();
            // getActivity().startActivity(new Intent(getActivity(), Main.class));
            // showMessage("Error","Add comment first.");





        }

        else {

            while(rs.moveToNext()){

                String newProductid = String.valueOf(rs.getInt(0));
                String newproductname=rs.getString(1);
                String newcategory= rs.getString(2);
                String newprice= rs.getString(3);
                String newquantity= rs.getString(4);
                String newdescription=rs.getString(5);
                String newimage= rs.getString(6);
                NAMES.add(newproductname);
                CATEGORY.add(newcategory);
                PRICE.add(newprice);
                QUANTITY.add(newquantity);
                DESCRIPTIONS.add(newdescription);
                IMAGES.add(newimage);
                RATING.add(getTotalStars(newproductname));
            }}
        System.out.println(NAMES);
        return NAMES.size();
    }
    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return IMAGES1.length;
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

            view = getLayoutInflater().inflate(R.layout.gihan_customercustomlayout, null);

            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textView_name = (TextView) view.findViewById(R.id.textView_name);
            TextView textView_description = (TextView) view.findViewById(R.id.textView_description);
            Button cart = (Button) view.findViewById(R.id.addToCart);
            Button buy = (Button) view.findViewById(R.id.buy);
            RatingBar starstatus = (RatingBar) view.findViewById(R.id.ratingBarstar);
            imageView.setImageResource(IMAGES1[i]);
            textView_name.setText(NAMES1[i]);
            textView_description.setText("Rs." + String.valueOf(PRICE1[i]));
            Float rate;
            rate = Float.valueOf(getTotalStars(NAMES1[i]));
            starstatus.setNumStars(5);
            starstatus.setRating(rate);

            Product p = new Product();

            // p.setProductName(productname.toString().trim());
            // p.setCategory(category.toString().trim());
            //p.setPrice(String.valueOf(price).toString().trim());
            //p.setQuantity(String.valueOf(quantity));
            //p.setDescription(description.toString().trim());
            //p.setImage(String.valueOf(image).toString().trim());
            //boolean isInserted = db.addProduct(p);

            // if (isInserted == true) {


            //     Toast.makeText(getActivity(), "Successful!", Toast.LENGTH_SHORT).show();

            // } else {
            //     showMessage("Failed!", "Please Fill out the fields correctly");
            // }

            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productname = NAMES1[j];
                    quantity = QUANTITY1[j];
                    price = PRICE1[j];
                    description = DESCRIPTIONS1[j];

                    rating = (float) getTotalStars(productname);
                    category = CATEGORY1[j];
                    image = IMAGES1[i];
                    SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

                    String strText = sp1.getString("CurrentUser", null);
                    Cart c= new Cart();
                    c.setImage(String.valueOf(image));
                    c.setProductName(productname);
                    c.setQuantity(1);
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

                    Toast.makeText(getActivity(), "Loading Order Process", Toast.LENGTH_SHORT).show();
                    DeliverInfoFragment deliverInfoFragment = new DeliverInfoFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, deliverInfoFragment, "findThisFragment").addToBackStack(null).commit();

                }
            });


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



