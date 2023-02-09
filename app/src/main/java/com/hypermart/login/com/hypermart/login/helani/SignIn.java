package com.hypermart.login.com.hypermart.login.helani;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.gihan.Main;
import com.hypermart.login.com.hypermart.login.helani.model.Customer;

public class SignIn extends AppCompatActivity {

    Button signIn;
    TextView txtView;
    EditText userName,password;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helani_activity_sign_in);
        myDb = new DatabaseHelper(this);

        txtView = this.findViewById(R.id.signuptxt);
        userName = (EditText)this.findViewById(R.id.userName);
        password = (EditText) this.findViewById(R.id.password);

        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignIn.this, "Loading Sign Up Page", Toast.LENGTH_SHORT).show();
                SignIn.this.startActivity(new Intent(SignIn.this, SignUp.class));

            }
        });


        signIn = findViewById(R.id.signin);



        signIn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {


                if(!validateUserName() | !validatePassword())
                {
                    return;
                }

                else{

                    Customer customer = new Customer();

                    String user = userName.getText().toString();
                    String pass = password.getText().toString();

                    customer.setUserName(user);
                    customer.setPassword(pass);

                Boolean checkCredentials = myDb.getSignIn(customer);
                if(checkCredentials == false){

                    showMessage("Sign In Failed!","Invalid Credentials");
                    return;
                }
                else {
                    Toast.makeText(SignIn.this, "Sign In successful! ", Toast.LENGTH_SHORT).show();

                    SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("CurrentUser",userName.getText().toString());
                    Cursor rs = myDb.getDataById(user);

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


                            ed.putString("Email",newEmail);

                        }

                    }
                    ed.commit();

                    SignIn.this.startActivity(new Intent(SignIn.this, Main.class));
                }
            }}
        });


    }


    public void showMessage(String title,String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        else if(userNameInput.length()>10) {

            userName.setError("User Name is too long");
            return false;

        }

        else {

            userName.setError(null);
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