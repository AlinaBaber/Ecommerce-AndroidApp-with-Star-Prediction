package com.hypermart.login.com.hypermart.login.vimanga;

import android.content.Intent;
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

import com.hypermart.login.com.hypermart.login.vimanga.model.Product;
import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.gihan.Main;
import com.hypermart.login.com.hypermart.login.helani.EditAccountFragment;


public class AddProductFragment extends Fragment implements View.OnClickListener{

    View view;
    DatabaseHelper db;
    Button submit, reset;
    EditText productname, category, quantity, description, price;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.vimanga_activity_add_products, container, false);
        submit = (Button) view.findViewById(R.id.submitButton);
        submit.setOnClickListener(this);
        db = new DatabaseHelper(getActivity());

        return view;
    }

    public void onClick(View view) {

        EditAccountFragment editAccountFragment = new EditAccountFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editAccountFragment, "findThisFragment").addToBackStack(null).commit();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TextView productname,category,quantity,description;


        productname =  getActivity().findViewById(R.id.productName);
        category = getActivity().findViewById(R.id.category);
        price = getActivity().findViewById(R.id.price);
        quantity = getActivity().findViewById(R.id.quantity);
        description = getActivity().findViewById(R.id.description);
        reset = getActivity().findViewById(R.id.resetButton);



        register();



    }


    public void register() {
        submit.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                if(!validateProductName() | !validateProductCategory() | !validateProductQuantity() | !validateProductPrice() | !validateProductDescription())
                {
                    return;
                }
                else {
                    Product p = new Product();

                    p.setProductName(productname.getText().toString().trim());
                    p.setCategory(category.getText().toString().trim());
                    p.setPrice(price.getText().toString().trim());
                    p.setQuantity(quantity.getText().toString().trim());
                    p.setDescription(description.getText().toString().trim());

                    boolean isInserted = db.addProduct(p);

                    if (isInserted == true) {


                        Toast.makeText(getActivity(), "Successful!", Toast.LENGTH_SHORT).show();
                        AddProductFragment.this.startActivity(new Intent(getActivity(), Main.class));
                    } else {
                        showMessage("Failed!", "Please Fill out the fields correctly");
                    }

                }

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

    private boolean validateProductName() {

        String userNameInput = productname.getEditableText().toString().trim();

        if (userNameInput.isEmpty()) {

            productname.setError("User Name field can't be empty");
            return false;

        }
        else if(userNameInput.length()>10) {

            productname.setError("User Name is too long");
            return false;

        }

        else {

            productname.setError(null);
            return true;
        }
    }


    private boolean validateProductCategory() {

        String userNameInput = category.getEditableText().toString().trim();

        if (userNameInput.isEmpty()) {

            category.setError("Category field can't be empty");
            return false;

        }

        else {

            category.setError(null);
            return true;
        }
    }

    private boolean validateProductQuantity() {

        String userNameInput = quantity.getEditableText().toString().trim();

        if (userNameInput.isEmpty()) {

            quantity.setError("Quantity field can't be empty");
            return false;

        }

        else {

            quantity.setError(null);
            return true;
        }
    }

    private boolean validateProductPrice() {

        String userNameInput = price.getEditableText().toString().trim();

        if (userNameInput.isEmpty()) {

            price.setError("Quantity field can't be empty");
            return false;

        }

        else {

            price.setError(null);
            return true;
        }
    }

    private boolean validateProductDescription() {

        String userNameInput = description.getEditableText().toString().trim();

        if (userNameInput.isEmpty()) {

            description.setError("Quantity field can't be empty");
            return false;

        }

        else {

            description.setError(null);
            return true;
        }
    }

    /////////////////////////////////


}

