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
import android.widget.TextView;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.gihan.model.Delivery;
import com.hypermart.login.com.hypermart.login.isuri.model.Cart;


import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;


public class DeliverInfoFragment extends Fragment implements View.OnClickListener {

    DatabaseHelper db;
    View view;
    Button btn;
    EditText name, address, contact, postalCode, date;

    /*---------------------------Validations---------------------------------*/
    public static final Pattern PHONE_NO = Pattern.compile("^[0-9]{10}$");
    public static final Pattern POSTAL_CODE = Pattern.compile("^[0-9]{5}$");
    public static final Pattern NAME = Pattern.compile("^[a-zA-z ]*$");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gihan_activity_delivery_info, container, false);
        btn = (Button) view.findViewById(R.id.proceed);
        btn.setOnClickListener(this);
        db = new DatabaseHelper(getActivity());

        return view;

    }

    public void onClick(View view) {

        name = getActivity().findViewById(R.id.delivery_name);
        address = getActivity().findViewById(R.id.delivery_address);
        contact = getActivity().findViewById(R.id.delivery_contact);
        date = getActivity().findViewById(R.id.delivery_time);
        postalCode = getActivity().findViewById(R.id.delivery_postal);


        if(!validateDate()|!validateName()|!validateDeliveryAddress()|!validateNumber()|!validatePostalCode()){

            return;
        }

        else {

            SharedPreferences sp1 = this.getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);

            String strText = sp1.getString("CurrentUser", null);

            Delivery delivery = new Delivery();
            String deliveryName = name.getText().toString().trim();
            String deliveryAddress = address.getText().toString().trim();
            String postal = postalCode.getText().toString().trim();
            String contactNo = contact.getText().toString().trim();
            String userName = strText;
            String deliveryDate = date.getText().toString().trim();

            delivery.setName(deliveryName);
            delivery.setDeliveryAddress(deliveryAddress);
            delivery.setPostalCode(postal);
            delivery.setContactNo(contactNo);
            delivery.setUserName(userName);
            delivery.setDateTime(deliveryDate);





            boolean isInserted = db.enterDeliveryDetails(delivery);

                if (isInserted) {

                    Toast.makeText(getActivity(), "Delivery Data Insertion  Successful!", Toast.LENGTH_SHORT).show();
                    PaymentInfoFragment paymentInfoFragment = new PaymentInfoFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, paymentInfoFragment, "findThisFragment").addToBackStack(null).commit();
                    DeleteCart();
                }
                else {

                    showMessage("Delivery Data Insertion Failed!", "Order already placed");

                }
        }
    }
    public void DeleteCart(){
        SharedPreferences sp1 = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        String strText = sp1.getString("CurrentUser", null);

        Cart cart = new Cart();

        cart.setUserName(strText);
        DatabaseHelper db = new DatabaseHelper(getActivity());
        Integer deleted =db.deleteCart(cart);

        if(deleted > 0){

            Toast.makeText(getActivity(),"Cart Data deleted",Toast.LENGTH_LONG).show();
            getActivity().startActivity(new Intent(getActivity(), Main.class));

        }

        else

            Toast.makeText(getActivity(),"Error!",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        name = getActivity().findViewById(R.id.delivery_name);
        address = getActivity().findViewById(R.id.delivery_address);
        contact = getActivity().findViewById(R.id.delivery_contact);
        date = getActivity().findViewById(R.id.delivery_time);
        postalCode = getActivity().findViewById(R.id.delivery_postal);

        Date current = Calendar.getInstance().getTime();


        SharedPreferences sp1 = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String strText = sp1.getString("CurrentUser", null);

        Cursor rs = db.getDataById(strText);

        if (rs.getCount() == 0) {
            showMessage("Error", "Nothing Found");
            return;

        }
        else {

            while (rs.moveToNext()) {

                String newName = rs.getString(3);
                String newAddress = rs.getString(4);
                String newContactNo = rs.getString(5);

                date.setEnabled(false);
                name.setText(newName, TextView.BufferType.EDITABLE);
                address.setText(newAddress, TextView.BufferType.EDITABLE);
                contact.setText(newContactNo, TextView.BufferType.EDITABLE);
                date.setText(current.toString(),TextView.BufferType.EDITABLE);


            }


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


    private boolean validateDate()
    {

        String dateInput = date.getEditableText().toString().trim();

        if (dateInput.isEmpty()) {

            date.setError("Date field can't be empty");
            return false;

        } else {

            date.setError(null);
            return true;
        }
    }

    private boolean validateName() {

        String nameInput = name.getEditableText().toString().trim();

        if (nameInput.isEmpty()) {

            name.setError("Name field can't be empty");
            return false;

        }

        else if(nameInput.length()>20) {

            name.setError("Name is too long");
            return false;

        }

        else if(!NAME.matcher(nameInput).matches()) {

            name.setError("Name can only be consists with  alphabets and spaces");
            return false;

        }

        else {

            name.setError(null);
            return true;
        }


    }

    private boolean validateDeliveryAddress() {

        String addressInput = address.getEditableText().toString().trim();

        if (addressInput.isEmpty()) {

            address.setError("Delivery Address field can't be empty");
            return false;

        }
        else if(addressInput.length()>30) {

            address.setError("Delivery Address is too long");
            return false;

        }

        else {

            address.setError(null);
            return true;
        }


    }

    private boolean validatePostalCode() {


        String codeInput = postalCode.getEditableText().toString().trim();

        if (codeInput.isEmpty()) {

            postalCode.setError("Postal Code field can't be empty");
            return false;

        }
        else if(!POSTAL_CODE.matcher(codeInput).matches()) {

            postalCode.setError("Postal Code must be 5 digits in length");
            return false;

        }

        else {

            postalCode.setError(null);
            return true;
        }


    }

    private boolean validateNumber() {

        String numberInput = contact.getEditableText().toString().trim();

        if (numberInput.isEmpty()) {

            contact.setError("Contact Number field can't be empty");
            return false;

        }

        else if(!PHONE_NO.matcher(numberInput).matches()) {

            contact.setError("Contact Number must be 10 digits");
            return false;

        }

        else {

            contact.setError(null);
            return true;
        }


    }

}