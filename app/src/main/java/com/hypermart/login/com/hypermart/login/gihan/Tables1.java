package com.hypermart.login.com.hypermart.login.gihan;


import android.provider.BaseColumns;

public final class Tables1 {


    private Tables1(){}
    public static  class User implements BaseColumns{


        public static final String TABLE_NAME_CUSTOMER = "customer";
        public static final String COL_1 = "userName";
        public static final String COL_2 = "email";
        public static final String COL_3 = "password";
        public static final String COL_4 = "name";
        public static final String COL_5 = "address";
        public static final String COL_6 = "contactNumber";






    }
    public static class Product implements BaseColumns{

        public static final String TABLE_NAME_PRODUCT = "products";
        public static final String COL_1_P = "ID";
        public static final String COL_2_P = "productName";
        public static final String COL_3_P = "category";
        public static final String COL_4_P = "price";
        public static final String COL_5_P = "quantity";
        public static final String COL_6_P = "description";
        public static final String COL_7_P = "image";

    }
    public static class Review implements BaseColumns{
        public static final String TABLE_NAME_REVIEW="reviews";
        public static final String COL_1_R="ID";
        public static final String COL_2_R= "productName";
        public static final String COL_3_R= "star";
        public static final String COL_4_R= "username";
        public static final String COL_5_R= "review";
    }
    public static  class Cart implements BaseColumns {


        public static final String TABLE_NAME_CART = "cart";
        public static final String COL_1_C = "productName";
        public static final String COL_2_C = "quantity";
        public static final String COL_3_C = "total";
        public static final String COL_4_C = "userName";
        public static final String COL_5_C ="image";





    }
    public static class Delivery implements BaseColumns{

        public static final String TABLE_NAME_DELIVERY = "delivery";
        public static final String COL_1_D = "orderId";
        public static final String COL_2_D = "name";
        public static final String COL_3_D = "deliveryAddress";
        public static final String COL_4_D = "postalCode";
        public static final String COL_5_D = "contactNo";
        public static final String COL_6_D = "userName";
        public static final String COL_7_D = "dateTime";



    }
    public static class Customer implements BaseColumns{

        public static final String TABLE_NAME_DELIVERY = "Customer";
        public static final String COL_1_D = "userId";
        public static final String COL_2_D = "name";
        public static final String COL_3_D = "password";
        public static final String COL_4_D = "emailID";
        public static final String COL_6_D = "userName";



    }

    public static  class Bank implements  BaseColumns{


        public static final String TABLE_NAME_BANK = "bank";
        public static final String COL_1_B = "cardNumber";
        public static final String COL_2_B = "holderName";
        public static final String COL_3_B = "cardType";
        public static final String COL_4_B = "expiryDate";
        public static final String COL_5_B = "cvv";


    }

    public static class Payment implements BaseColumns{


        public static final String TABLE_NAME_PAYMENT = "payment";
        public static final String COL_1_P = "paymentId";
        public static final String COL_2_P = "userName";
        public static final String COL_3_P = "amount";


    }


}
