package com.hypermart.login.com.hypermart.login.helani;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.helani.model.Customer;

public class EditAccountFragment extends Fragment implements View.OnClickListener{

    View view;
    Button btn;
    String strText;
    DatabaseHelper db;
    EditText name,address,contact,userName,email,password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.helani_activity_edit_account, container, false);
        btn = (Button) view.findViewById(R.id.saveChanges);
        btn.setOnClickListener(this);
        db =  new DatabaseHelper(getActivity());

        return view;
    }

    public void onClick(View view) {

        if(!validateUserName() | !validateEmail() | !validatePassword())
        {
            return;
        }

        else {


            Customer customer = new Customer();

            String cName = name.getText().toString();
            String cAddress = address.getText().toString();
            String cContact = contact.getText().toString();
            String user = userName.getText().toString();
            String cEmail = email.getText().toString();
            String cPass = password.getText().toString();

            customer.setName(cName);
            customer.setAddress(cAddress);
            customer.setContactNo(cContact);
            customer.setUserName(user);
            customer.setEmail(cEmail);
            customer.setPassword(cPass);


            boolean isUpdated = db.update(customer);

            if (isUpdated == true) {

                Toast.makeText(getActivity(), "Data updated", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Data not updated", Toast.LENGTH_LONG).show();

            }


            MyAccountFragment myAccountFragment = new MyAccountFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myAccountFragment, "findThisFragment").addToBackStack(null).commit();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        name= (EditText) getActivity().findViewById(R.id.editText_name);
        address =getActivity().findViewById(R.id.editText_address);
        contact = getActivity().findViewById(R.id.editText_number);
        userName = getActivity().findViewById(R.id.editText_userName);
        email = getActivity().findViewById(R.id.editText_email);
        password = getActivity().findViewById(R.id.editText_Password);


        SharedPreferences sp1 = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        strText = sp1.getString("CurrentUser", null);

        Cursor rs = db.getDataById(strText);

        if(rs.getCount()==0){
            showMessage("Error","Nothing Found");
            return;

        }

        else {

            while(rs.moveToNext()){

                String newUserName=rs.getString(0);
                String newEmail= rs.getString(1);
                String newPassword = rs.getString(2);
                String newName = rs.getString(3);
                String newAddress = rs.getString(4);
                String newContactNo = rs.getString(5);


                userName.setEnabled(false);
                name.setText(newName,TextView.BufferType.EDITABLE);
                address.setText(newAddress,TextView.BufferType.EDITABLE);
                contact.setText(newContactNo,TextView.BufferType.EDITABLE);
                userName.setText(newUserName,TextView.BufferType.EDITABLE);
                email.setText(newEmail,TextView.BufferType.EDITABLE);
                password.setText(newPassword,TextView.BufferType.EDITABLE);

            }

        }



    }



    public void showMessage(String title,String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }

    private boolean validateUserName() {

        String userNameInput = userName.getEditableText().toString().trim();

        if (userNameInput.isEmpty()) {

            userName.setError("User Name field can't be empty");
            return false;

        }

        else {

            userName.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {

        String emailInput = email.getEditableText().toString().trim();

        if (emailInput.isEmpty()) {

            email.setError("Email field can't be empty");
            return false;

        } else {

            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {

        String passwordInput = password.getEditableText().toString().trim();

        if (passwordInput.isEmpty()) {

            password.setError("Password field can't be empty");
            return false;

        } else {

            password.setError(null);
            return true;
        }
    }






}
