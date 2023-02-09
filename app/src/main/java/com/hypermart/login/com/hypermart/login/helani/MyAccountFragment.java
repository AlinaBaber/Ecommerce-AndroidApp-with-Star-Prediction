package com.hypermart.login.com.hypermart.login.helani;


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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.gihan.Main;


public class MyAccountFragment extends Fragment implements View.OnClickListener {
    View view;
    Button btn,deleteBtn;
    DatabaseHelper db;
    String strText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.helani_activity_my_account, container, false);
        btn = (Button) view.findViewById(R.id.editAccount);
        btn.setOnClickListener(this);
        db =  new DatabaseHelper(getActivity());

        return view;

    }

    public void onClick(View view) {

        EditAccountFragment editAccountFragment = new EditAccountFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editAccountFragment, "findThisFragment").addToBackStack(null).commit();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView name,address,contact,userName,email,password;

        name= (TextView) getActivity().findViewById(R.id.name);
        address =getActivity().findViewById(R.id.address);
        contact = getActivity().findViewById(R.id.number);
        userName = getActivity().findViewById(R.id.userName);
        email = getActivity().findViewById(R.id.email);
        password = getActivity().findViewById(R.id.password);
        deleteBtn =  getActivity().findViewById(R.id.deleteAccount);


        SharedPreferences sp1 = this.getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);

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


                 name.setText(newName);
                 address.setText(newAddress);
                 contact.setText(newContactNo);
                 userName.setText(newUserName);
                 email.setText(newEmail);
                 password.setText(newPassword);

            }

        }


        deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Confirmation")
                        .setMessage("Do you really want to delete your Account?").setIcon(R.drawable.ic_add_alert_black_24dp)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Integer deleted =db.deleteData(strText);

                                if(deleted > 0){
                                    Toast.makeText(getActivity(),"Account deleted",Toast.LENGTH_LONG).show();
                                    getActivity().startActivity(new Intent(getActivity(), SignIn.class));}

                                else{

                                    showMessage("Error","Delete Account Process ended with an error");

                                }
                            }
                        }).setNegativeButton("NO",null).show();


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

}

