package com.hypermart.login.com.hypermart.login.vimanga;

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
import android.widget.TextView;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.gihan.Main;

public class ViewProductFragment extends Fragment implements View.OnClickListener {

    View view;
    Button btn,deleteBtn;
    DatabaseHelper db;
    String strText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vimanga_activity_view_product, container, false);
        btn = (Button) view.findViewById(R.id.editProduct);
        db = new DatabaseHelper(getActivity());
        btn.setOnClickListener(this);
        return view;
    }


    public void onClick(View view) {

        EditProductsFragment editProductsFragment = new EditProductsFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editProductsFragment, "findThisFragment").addToBackStack(null).commit();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView name,category,price,quantity,description;

        name= (TextView) getActivity().findViewById(R.id.name);
        category =getActivity().findViewById(R.id.category);
        price = getActivity().findViewById(R.id.price);
        quantity = getActivity().findViewById(R.id.quantity);
        description = getActivity().findViewById(R.id.description);
        deleteBtn =  getActivity().findViewById(R.id.deleteProduct);


        SharedPreferences sp1 = this.getActivity().getSharedPreferences("product", Context.MODE_PRIVATE);

        strText = sp1.getString("ProductName", null);


        Cursor rs = db.getProductById(strText);


        if(rs.getCount()==0){
            showMessage("Error","Nothing Found");
            return;

        }

        else {

            while(rs.moveToNext()){

                String newProduct= rs.getString(1);
                String newCategory = rs.getString(2);
                String newprice = rs.getString(3);
                String newQuantity = rs.getString(4);
                String newDescription = rs.getString(5);


                name.setText(newProduct);
                category.setText(newCategory);
                price.setText(newprice);
                quantity.setText(newQuantity);
                description.setText(newDescription);

            }

        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer deleted = db.deleteProduct(strText);

                if (deleted > 0) {

                    Toast.makeText(getActivity(), "Product deleted", Toast.LENGTH_LONG).show();
                    getActivity().startActivity(new Intent(getActivity(), Main.class));

                } else

                    Toast.makeText(getActivity(), "Error!", Toast.LENGTH_LONG).show();

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
