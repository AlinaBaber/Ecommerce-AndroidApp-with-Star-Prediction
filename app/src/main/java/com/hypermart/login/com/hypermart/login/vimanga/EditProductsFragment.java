package com.hypermart.login.com.hypermart.login.vimanga;

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


public class EditProductsFragment extends Fragment implements View.OnClickListener {

    View view;
    Button btn,deleteBtn;
    String strText;
    DatabaseHelper db;
    EditText name,category,price,quantity,description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.vimanga_activity_edit_products, container, false);
        btn = (Button) view.findViewById(R.id.editsubmit);
        db = new DatabaseHelper(getActivity());

        btn.setOnClickListener(this);

        return view;
    }

    public void onClick(View view) {

        if(!validateProductName() | !validateProductCategory() | !validateProductPrice() | !validateProductQuantity() | !validateProductDescription())
        {
            return;
        }

        else {
            boolean isUpdated = db.updateProduct(name.getText().toString(), category.getText().toString(), price.getText().toString(), quantity.getText().toString(), description.getText().toString());

            if (isUpdated == true) {

                Toast.makeText(getActivity(), "Data updated", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Data not updated", Toast.LENGTH_LONG).show();

            }


            ProductFragment productFragment = new ProductFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, productFragment, "findThisFragment").addToBackStack(null).commit();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        name = (EditText) getActivity().findViewById(R.id.editText1);
        category = getActivity().findViewById(R.id.editText2);
        price = getActivity().findViewById(R.id.editText5);
        quantity = getActivity().findViewById(R.id.editText3);
        description = getActivity().findViewById(R.id.editText4);
        deleteBtn = getActivity().findViewById(R.id.deleteProduct);
        db = new DatabaseHelper(getActivity());


        SharedPreferences sp1 = this.getActivity().getSharedPreferences("product", Context.MODE_PRIVATE);

        strText = sp1.getString("ProductName", null);

        Cursor rs = db.getProductById(strText);

        if (rs.getCount() == 0) {
            showMessage("Error", "Nothing Found");
            return;

        } else {

            while (rs.moveToNext()) {

                //String newUserName = rs.getString(0);
                String newProduct = rs.getString(1);
                String newCategory = rs.getString(2);
                String newPrice = rs.getString(3);
                String newQuantity = rs.getString(4);
                String newDescription = rs.getString(5);


                name.setText(newProduct, TextView.BufferType.EDITABLE);
                category.setText(newCategory, TextView.BufferType.EDITABLE);
                price.setText(newPrice, TextView.BufferType.EDITABLE);
                quantity.setText(newQuantity, TextView.BufferType.EDITABLE);
                description.setText(newDescription, TextView.BufferType.EDITABLE);

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

    private boolean validateProductName() {

        String userNameInput = name.getEditableText().toString().trim();

        if (userNameInput.isEmpty()) {

            name.setError("Product name field can't be empty");
            return false;

        }


        else {

            name.setError(null);
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

            price.setError("Price field can't be empty");
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

            description.setError("Description field can't be empty");
            return false;

        }

        else {

            description.setError(null);
            return true;
        }
    }

}
