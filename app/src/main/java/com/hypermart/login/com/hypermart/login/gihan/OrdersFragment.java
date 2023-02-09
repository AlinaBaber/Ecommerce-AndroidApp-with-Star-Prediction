package com.hypermart.login.com.hypermart.login.gihan;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.isuri.ShoppingCartFragment;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.*;


public class OrdersFragment extends Fragment {

    View view;
    Button editOrder, cancelOrder;
    DatabaseHelper db;
    String strText;
    List<String> ORDERID= new ArrayList<>();
    List<String> ORDERNAME= new ArrayList<>();
    List<String> ORDERDELIVERYADDRESS= new ArrayList<>();
    List<String> ORDERPOSTAL= new ArrayList<>();
    List<String> ORDERCONTACT= new ArrayList<>();
    List<String> ORDERDATE= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.gihan_activity_orders, container, false);

        db = new DatabaseHelper(getActivity());

        SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        strText = sp1.getString("CurrentUser", null);

        Cursor rs = db.getDataByIdOrder(strText);


        if (rs.getCount() == 0) {


            Toast.makeText(getActivity(), "Place an Order first", Toast.LENGTH_LONG).show();
            getActivity().startActivity(new Intent(getActivity(), Main.class));
            showMessage("Error", "Place an Order first");


        } else {

            while (rs.moveToNext()) {

                String newOrderId = String.valueOf(rs.getInt(0));
                String newOrderName = rs.getString(1);
                String newDeliveryAddress = rs.getString(2);
                String newPostal = rs.getString(3);
                String newContact = rs.getString(4);
                String newDate = rs.getString(6);
                ORDERID.add(newOrderId);
                ORDERNAME.add(newOrderName);
                ORDERDELIVERYADDRESS.add(newDeliveryAddress);
                ORDERPOSTAL.add(newPostal);
                ORDERCONTACT.add(newContact);
                ORDERDATE.add(newDate);


            }

        }
        ListView listView = (ListView) view.findViewById(R.id.listvieworder);
        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);
        return view;

    }

    public void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }
    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            int length = 0;
            db = new DatabaseHelper(getActivity());
            Cursor rs = db.getDataByIdOrder(strText);


            if (rs.getCount() == 0) {


                Toast.makeText(getActivity(), "Add Comment first.", Toast.LENGTH_LONG).show();
                // getActivity().startActivity(new Intent(getActivity(), Main.class));
                // showMessage("Error","Add comment first.");


            } else {

                while (rs.moveToNext()) {

                    length = length + 1;
                    //SERNAMES[]=newusername;
                    //EVIEWS[]=newReview;
                    //ARS.add(newstar);
                }
            }
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

            TextView orderId, orderDate, orderName, orderAddress, orderPostal, orderPhone, orderAmount;
            view = getLayoutInflater().inflate(R.layout.gihan_customercustomorder, null);

            orderId = (TextView) view.findViewById(R.id.orderId);
            orderDate = (TextView) view.findViewById(R.id.orderDate);
            orderName = (TextView) view.findViewById(R.id.orderName);
            orderAddress = (TextView) view.findViewById(R.id.orderAddress);
            orderPostal = (TextView) view.findViewById(R.id.orderPostal);
            orderPhone = (TextView) view.findViewById(R.id.orderPhone);
            orderAmount = (TextView) view.findViewById(R.id.orderAmount);
            Button editorder = (Button) view.findViewById(R.id.editOrder);
            cancelOrder = (Button) view.findViewById(R.id.cancelOrder);

            orderId.setText(ORDERID.get(i));
            orderName.setText(ORDERNAME.get(i));
            orderAddress.setText(ORDERDELIVERYADDRESS.get(i));
            orderPostal.setText(ORDERPOSTAL.get(i));
            orderPhone.setText(ORDERCONTACT.get(i));
            orderDate.setText(ORDERDATE.get(i));


            Cursor payAmount = db.getamountByIdOrder(strText);

            while (payAmount.moveToNext()) {

                String amount = payAmount.getString(2);

                orderAmount.setText(amount);

            }
            editOrder= (Button) view.findViewById(R.id.editOrder);
            editOrder.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    EditOrderFragment editOrderFragment = new EditOrderFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editOrderFragment, "findThisFragment").addToBackStack(null).commit();

                }
            });

            cancelOrder.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Confirmation")
                            .setMessage("Do you really want to cancel this order?").setIcon(R.drawable.ic_add_alert_black_24dp)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Integer cancelled = db.cancelOrder(strText);

                                    if (cancelled > 0) {
                                        Toast.makeText(getActivity(), "Order Cancelled", Toast.LENGTH_SHORT).show();
                                        getActivity().startActivity(new Intent(getActivity(), Main.class));
                                    } else {

                                        showMessage("Error", "Order Cancellation ended with an error");

                                    }
                                }
                            }).setNegativeButton("NO", null).show();


                }

            });
            return view;
        }


        public void showMessage(String title, String message) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.show();


        }

    }
}
