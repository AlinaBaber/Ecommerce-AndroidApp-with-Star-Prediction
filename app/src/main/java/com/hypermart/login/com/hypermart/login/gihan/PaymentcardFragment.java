package com.hypermart.login.com.hypermart.login.gihan;


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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.DatabaseHelper;
import com.hypermart.login.com.hypermart.login.gihan.model.Bank;
import com.hypermart.login.com.hypermart.login.gihan.model.Payment;

import java.util.regex.Pattern;


public class PaymentcardFragment extends Fragment implements View.OnClickListener {

    DatabaseHelper myDb;
    View view;
    Button completeOrder;
    EditText holderName,cardNumber,expiryDate,cvv,amount;
    Spinner cardType;
    Spinner dropdown;


    /*---------------------------Validations---------------------------------*/
    public static final Pattern CARD_NO = Pattern.compile("^[0-9]{16}$");
    public static final Pattern EXPIRE_DATE = Pattern.compile("^[\\d][\\d][/][\\d][\\d]$");
    public static final Pattern NAME = Pattern.compile("^[a-zA-z ]*$");
    public static final Pattern CVV = Pattern.compile("^[0-9]{3}$");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.gihan_activity_payment_for_card, container, false);
        completeOrder =(Button)view.findViewById(R.id.completeOrder);
        completeOrder.setOnClickListener(this);
        myDb = new DatabaseHelper(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        dropdown = (Spinner) view.findViewById(R.id.spinner);
        String[] items = new String[]{"Visa Debit Card", "Master Card", "Visa Credit Card"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        holderName = getActivity().findViewById(R.id.holderName);
        cardNumber = getActivity().findViewById(R.id.cardNo);
        expiryDate = getActivity().findViewById(R.id.expiryDate);
        cvv = getActivity().findViewById(R.id.cvv);
        amount = getActivity().findViewById(R.id.payAmount);
        cardType = getActivity().findViewById(R.id.spinner);


        SharedPreferences sp1 = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        String strText = sp1.getString("CurrentUser", null);

         Cursor rs = myDb.getTotalById(strText);


        while(rs.moveToNext()){

            String newTotal = String.valueOf(rs.getFloat(0));

            amount.setEnabled(false);
            amount.setText(newTotal);

        }

    }

    @Override
    public void onClick(View view) {

        if(!validateHolderName() | !validateCardNo() | !validateExpiryDate() | !validateCVV() | !validateAmount())
        {
            return;
        }

        else {

            Bank bank = new Bank();

            String cName = holderName.getText().toString().trim();
            String cNo =  cardNumber.getText().toString().trim();
            String expire = expiryDate.getText().toString().trim();
            String cvvNo = cvv.getText().toString().trim();
            String type =  cardType.getSelectedItem().toString();

            bank.setHolderName(cName);
            bank.setCardNumber(cNo);
            bank.setExpiryDate(expire);
            bank.setCvv(cvvNo);
            bank.setCardType(type);




            Boolean checkCardValidity = myDb.cardValidation(bank);

            if(checkCardValidity == false){

                showMessage("Error","Invalid Card Data ");
                return;
            }
            else {

                SharedPreferences sp1 = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

                String strText = sp1.getString("CurrentUser", null);

                Payment payment = new Payment();

                String user = strText;
                float payAmount =  Float.parseFloat(amount.getText().toString().trim());

                payment.setUserName(user);
                payment.setAmount(payAmount);


                boolean isInserted = myDb.insertPayment(payment);

                if(isInserted == true){

                    Toast.makeText(getActivity(), "Payment verified! Order has been placed!  ", Toast.LENGTH_SHORT).show();
                    OrdersFragment ordersFragment = new OrdersFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ordersFragment, "findThisFragment").addToBackStack(null).commit();
                }
                else{

                    showMessage("Payment Data Insertion Failed!", "Enter valid Details");
                }

            }

        }
    }

    private boolean validateHolderName() {

        String holderNameInput = holderName.getEditableText().toString().trim();

        if (holderNameInput.isEmpty()) {

            holderName.setError("Holder Name field can't be empty");
            return false;

        }

        else if(!NAME.matcher(holderNameInput).matches()) {

            holderName.setError("Name can only be consists with  alphabets and spaces");
            return false;

        }

        else if(holderNameInput.length()>20){

            holderName.setError("Holder name cannot exceed 20 characters in length");
            return  false;
        }

        else {

            holderName.setError(null);
            return true;
        }
    }

    private boolean validateCardNo() {

        String cardNoInput = cardNumber.getEditableText().toString().trim();

        if (cardNoInput.isEmpty()) {

            cardNumber.setError("Card Number field can't be empty");
            return false;

        }
        else if(!CARD_NO.matcher(cardNoInput).matches()) {

            cardNumber.setError("Card Number must be 16 digits in length");
            return false;

        }
        else {

            cardNumber.setError(null);
            return true;
        }
    }

    private boolean validateExpiryDate() {

        String expiryDateInput = expiryDate.getEditableText().toString().trim();

        if (expiryDateInput.isEmpty()) {

            expiryDate.setError("Expiry Date field can't be empty");
            return false;

        }
        else if(!EXPIRE_DATE.matcher(expiryDateInput).matches()) {

            expiryDate.setError("Expiry Date must follow the above pattern");
            return false;

        }
        else {

            expiryDate.setError(null);
            return true;
        }
    }

    private boolean validateCVV() {

        String cvvInput = cvv.getEditableText().toString().trim();

        if (cvvInput.isEmpty()) {

            cvv.setError("CVV field can't be empty");
            return false;

        }
        else if(!CVV.matcher(cvvInput).matches()) {

            cvv.setError("CVV can only be consists with 3 digits");
            return false;

        }

        else {

            cvv.setError(null);
            return true;
        }
    }

    private boolean validateAmount() {

        String amountInput = amount.getEditableText().toString().trim();

        if (amountInput.isEmpty()) {

            amount.setError("Amount field can't be empty");
            return false;

        } else {

            amount.setError(null);
            return true;
        }
    }


    public void showMessage(String title,String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }


}
