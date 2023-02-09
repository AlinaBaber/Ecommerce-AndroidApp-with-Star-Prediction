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
import android.widget.EditText;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.gihan.model.Delivery;

import java.util.regex.Pattern;

public class EditOrderFragment extends Fragment implements  View.OnClickListener{

    View view;
    Button btn;
    DatabaseHelper db;
    EditText orderId,orderDate,orderName,orderAddress,orderPostal,orderPhone;


    /*---------------------------Validations---------------------------------*/
    public static final Pattern PHONE_NO = Pattern.compile("^[0-9]{10}$");
    public static final Pattern POSTAL_CODE = Pattern.compile("^[0-9]{5}$");
    public static final Pattern NAME = Pattern.compile("^[a-zA-z ]*$");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.gihan_activity_edit_order, container, false);
        btn = (Button) view.findViewById(R.id.updateOrder);
        btn.setOnClickListener(this);
        db = new DatabaseHelper(getActivity());

        return view;
    }

    @Override
    public void onClick(View view) {


        if(!validateDate()|!validateName()|!validateDeliveryAddress()|!validateNumber()|!validatePostalCode()){

            return;
        }

        else {

            SharedPreferences sp1 = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

            String strText = sp1.getString("CurrentUser", null);

            Delivery delivery = new Delivery();

            String oName = orderName.getText().toString();
            String oAddress = orderAddress.getText().toString();
            String oPostal = orderPostal.getText().toString();
            String oPhone = orderPhone.getText().toString();
            String user = strText;

            delivery.setName(oName);
            delivery.setDeliveryAddress(oAddress);
            delivery.setPostalCode(oPostal);
            delivery.setContactNo(oPhone);
            delivery.setUserName(user);


            boolean isUpdated = db.updateOrder(delivery);

            if (isUpdated == true)
            {

                Toast.makeText(getActivity(), "Order Data updated", Toast.LENGTH_LONG).show();
                OrdersFragment ordersFragment = new OrdersFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ordersFragment, "findThisFragment").addToBackStack(null).commit();

            }
            else
            {
                Toast.makeText(getActivity(), "Error occurred while updating data", Toast.LENGTH_LONG).show();

            }



        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        orderId=  getActivity().findViewById(R.id.editOrderID);
        orderDate =getActivity().findViewById(R.id.editOrderDate);
        orderName = getActivity().findViewById(R.id.editOrderName);
        orderAddress = getActivity().findViewById(R.id.editOrderAddress);
        orderPostal = getActivity().findViewById(R.id.editOrderPostal);
        orderPhone = getActivity().findViewById(R.id.editOrderContactNo);


        SharedPreferences sp1 = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String strText = sp1.getString("CurrentUser", null);


        Cursor rs = db.getDataByIdOrder(strText);


        if(rs.getCount()==0){


            showMessage("Error","Place an Order first");

            getActivity().startActivity(new Intent(getActivity(), Main.class));

            return;

        }

        else {

            while(rs.moveToNext()){

                String newOrderId = String.valueOf(rs.getInt(0));
                String newOrderName=rs.getString(1);
                String newDeliveryAddress= rs.getString(2);
                String newPostal = rs.getString(3);
                String newContact = rs.getString(4);
                String newDate = rs.getString(6);


                orderId.setEnabled(false);
                orderDate.setEnabled(false);

                orderId.setText(newOrderId);
                orderName.setText(newOrderName);
                orderAddress.setText(newDeliveryAddress);
                orderPostal.setText(newPostal);
                orderPhone.setText(newContact);
                orderDate.setText(newDate);


            }

        }}

    public void showMessage(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }


    private boolean validateDate() {

        String dateInput = orderDate.getEditableText().toString().trim();

        if (dateInput.isEmpty()) {

            orderDate.setError("Date field can't be empty");
            return false;

        } else {

            orderDate.setError(null);
            return true;
        }
    }

    private boolean validateName() {

        String nameInput = orderName.getEditableText().toString().trim();

        if (nameInput.isEmpty()) {

            orderName.setError("Name field can't be empty");
            return false;

        }

        else if(!NAME.matcher(nameInput).matches()) {

            orderName.setError("Name can only be consists with  alphabets and spaces");
            return false;

        }

        else if(nameInput.length()>20) {

            orderName.setError("Name cannot exceed 20 characters");
            return false;

        }

        else {

            orderName.setError(null);
            return true;
        }


    }

    private boolean validateDeliveryAddress() {

        String addressInput = orderAddress.getEditableText().toString().trim();

        if (addressInput.isEmpty()) {

            orderAddress.setError("Delivery Address field can't be empty");
            return false;

        }
        else if(addressInput.length()>30) {

            orderAddress.setError("Delivery Address cannot exceed 30 characters");
            return false;

        }

        else {

            orderAddress.setError(null);
            return true;
        }


    }
    private boolean validatePostalCode() {

        String codeInput = orderPostal.getEditableText().toString().trim();

        if (codeInput.isEmpty()) {

            orderPostal.setError("Postal Code field can't be empty");
            return false;

        }
        else if(!POSTAL_CODE.matcher(codeInput).matches()) {

            orderPostal.setError("Postal Code must be 5 digits in length");
            return false;

        }

        else {

            orderPostal.setError(null);
            return true;
        }


    }
    private boolean validateNumber() {

        String numberInput = orderPhone.getEditableText().toString().trim();

        if (numberInput.isEmpty()) {

            orderPhone.setError("Contact Number field can't be empty");
            return false;

        }
        else if(!PHONE_NO.matcher(numberInput).matches()) {

            orderPhone.setError("Contact Number must be 10 digits");
            return false;

        }
        else {

            orderPhone.setError(null);
            return true;
        }


    }




}
