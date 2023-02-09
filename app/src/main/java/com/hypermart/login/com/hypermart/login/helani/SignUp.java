package com.hypermart.login.com.hypermart.login.helani;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.helani.model.Customer;

public class SignUp extends AppCompatActivity {

    DatabaseHelper db;
    Button signUp, reset;
    EditText userName, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helani_activity_sign_up);
        db = new DatabaseHelper(this);

        userName = (EditText) findViewById(R.id.userName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        signUp = (Button) findViewById(R.id.signup);
        reset = (Button) findViewById(R.id.reset);

        register();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName.setText("");
                email.setText("");
                password.setText("");
                confirmPassword.setText("");


            }
        });


    }


    public void register() {
        signUp.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                if(!validateUserName() | !validateEmail() | !validatePassword() | !confirmPassword())
                {
                    return;
                }
                else {
                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {


                        Customer customer = new Customer();

                        String user = userName.getText().toString().trim();
                        String cEmail =email.getText().toString().trim();
                        String pass = password.getText().toString().trim();

                        customer.setUserName(user);
                        customer.setEmail(cEmail);
                        customer.setPassword(pass);


                        boolean isInserted = db.signup(customer);

                        if (isInserted == true) {


                            Toast.makeText(SignUp.this, "Registration Successful! Sign In to Continue", Toast.LENGTH_SHORT).show();
                            SignUp.this.startActivity(new Intent(SignUp.this, SignIn.class));
                        } else {
                            showMessage("Registration Failed!", "User Name already exists");
                        }
                    } else {

                        showMessage("Registration Failed!", "Password fields are not matching");

                    }
                }

            }
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
    private boolean confirmPassword() {

        String confirmPasswordInput = confirmPassword.getEditableText().toString().trim();

        if (confirmPasswordInput.isEmpty()) {

            confirmPassword.setError("Password must be confirmed");
            return false;

        } else {

            confirmPassword.setError(null);
            return true;
        }
    }



    }





