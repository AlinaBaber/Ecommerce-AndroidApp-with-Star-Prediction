package com.hypermart.login.com.hypermart.login;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.gihan.model.Review;
import com.hypermart.login.com.hypermart.login.vimanga.model.Product;
import com.hypermart.login.com.hypermart.login.gihan.Tables1;
import com.hypermart.login.com.hypermart.login.gihan.model.Bank;
import com.hypermart.login.com.hypermart.login.gihan.model.Delivery;
import com.hypermart.login.com.hypermart.login.gihan.model.Payment;
import com.hypermart.login.com.hypermart.login.helani.model.Customer;
import com.hypermart.login.com.hypermart.login.isuri.model.Cart;


public class DatabaseHelper extends SQLiteOpenHelper {



    public static final String DATABASE_NAME = "hypermart.db";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    int[] IMAGES;
    String[] NAMES;
    String[] DESCRIPTIONS;
    int[] QUANTITY;
    int[] PRICE;
    int[] RATING;
    int j;
    String[] CATEGORY;
    String productname,description,category;
    int price;
    int quantity,image;
    float rating;
    String strText;
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String[] Category= new String[]{"LEDs","Transistors","Drivers","Sensors"};
        //------------------------------Helani------------------------------
        sqLiteDatabase.execSQL("CREATE TABLE "+ Tables1.User.TABLE_NAME_CUSTOMER + "(" + Tables1.User.COL_1 + " TEXT PRIMARY KEY , " + Tables1.User.COL_2 + " TEXT NOT NULL , " + Tables1.User.COL_3 + " TEXT NOT NULL," + Tables1.User.COL_4 + " TEXT ," + Tables1.User.COL_5 + " TEXT ," + Tables1.User.COL_6 + " TEXT )");

        //------------------------------Gihan-------------------------------
        sqLiteDatabase.execSQL("CREATE TABLE " + Tables1.Delivery.TABLE_NAME_DELIVERY + "(" + Tables1.Delivery.COL_1_D + " INTEGER  PRIMARY KEY AUTOINCREMENT , " + Tables1.Delivery.COL_2_D +" TEXT NOT NULL," + Tables1.Delivery.COL_3_D + " TEXT NOT NULL , " + Tables1.Delivery.COL_4_D + " INTEGER NOT NULL , " + Tables1.Delivery.COL_5_D + " INTEGER NOT NULL ," + Tables1.Delivery.COL_6_D + " TEXT NOT NULL UNIQUE , " + Tables1.Delivery.COL_7_D + " TEXT NOT NULL )");
        sqLiteDatabase.execSQL("CREATE TABLE " + Tables1.Bank.TABLE_NAME_BANK + "(" + Tables1.Bank.COL_1_B + " INTEGER PRIMARY KEY , " + Tables1.Bank.COL_2_B + " TEXT NOT NULL , " + Tables1.Bank.COL_3_B + " TEXT NOT NULL , " + Tables1.Bank.COL_4_B + " TEXT NOT NULL , " + Tables1.Bank.COL_5_B + " INTEGER NOT NULL )");
       // sqLiteDatabase.execSQL("INSERT INTO " + Tables1.Bank.TABLE_NAME_BANK + " VALUES(1234123412341234,'alinababer','Visa Debit Card','03/20',233)");
       // sqLiteDatabase.execSQL("INSERT INTO " + Tables1.Bank.TABLE_NAME_BANK + " VALUES(1234123412345678,'esha','Master Card','11/22',243)");
        sqLiteDatabase.execSQL("CREATE TABLE " + Tables1.Payment.TABLE_NAME_PAYMENT + "(" + Tables1.Payment.COL_1_P + " INTEGER PRIMARY KEY AUTOINCREMENT ," + Tables1.Payment.COL_2_P + " TEXT NOT NULL UNIQUE, " + Tables1.Payment.COL_3_P + " REAL NOT NULL )");


        //------------------------------Isuri-------------------------------

        sqLiteDatabase.execSQL("CREATE TABLE "+ Tables1.Cart.TABLE_NAME_CART + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , " + Tables1.Cart.COL_1_C + " TEXT  , " + Tables1.Cart.COL_2_C + " INTEGER , " + Tables1.Cart.COL_3_C + " INTEGER , " + Tables1.Cart.COL_4_C + " TEXT , "+Tables1.Cart.COL_5_C+" TEXT )");

        sqLiteDatabase.execSQL("CREATE TABLE "+Tables1.Product.TABLE_NAME_PRODUCT+" ( "+Tables1.Product.COL_1_P+ " INTEGER PRIMARY KEY AUTOINCREMENT , " + Tables1.Product.COL_2_P +" TEXT UNIQUE , "+ Tables1.Product.COL_3_P+" TEXT , "+ Tables1.Product.COL_4_P+" TEXT , "+Tables1.Product.COL_5_P+" TEXT , "+ Tables1.Product.COL_6_P+" TEXT , "+Tables1.Product.COL_7_P+" TEXT )");
       // boolean flag=AddDefaultProducts(Category);
       // if (flag== true)
       // {System.out.println(" Successfully added all Products");}
        //else {System.out.println(" Failed to add all Products");}
        sqLiteDatabase.execSQL("CREATE TABLE "+Tables1.Review.TABLE_NAME_REVIEW+" ( "+Tables1.Review.COL_1_R+" INTEGER PRIMARY KEY AUTOINCREMENT , "+Tables1.Review.COL_2_R+" TEXT , "+ Tables1.Review.COL_3_R+" TEXT , " +Tables1.Review.COL_4_R+" TEXT , "+Tables1.Review.COL_5_R+" TEXT ) ");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //------------------------------Helani------------------------------
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Tables1.User.TABLE_NAME_CUSTOMER);

        //------------------------------Gihan-------------------------------
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Tables1.Delivery.TABLE_NAME_DELIVERY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Tables1.Payment.TABLE_NAME_PAYMENT);


        //------------------------------Isuri-------------------------------
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Tables1.Cart.TABLE_NAME_CART);

        //------------------------------Vimanga-------------------------------
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Tables1.Product.TABLE_NAME_PRODUCT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Tables1.Review.TABLE_NAME_REVIEW);
        onCreate(sqLiteDatabase);
    }


        //------------------------------Helani------------------------------

    public boolean signup(Customer customer){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables1.User.COL_1,customer.getUserName());
        contentValues.put(Tables1.User.COL_2,customer.getEmail());
        contentValues.put(Tables1.User.COL_3,customer.getPassword());

        long result = sqLiteDatabase.insert( Tables1.User.TABLE_NAME_CUSTOMER , null , contentValues);

        if(result == -1){

            return  false;
        }
        else{

            return true;
        }

    }

    public Boolean getSignIn(Customer customer){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String sql="select * from " + Tables1.User.TABLE_NAME_CUSTOMER +" where userName = '"+ customer.getUserName() +"' and password = '"+customer.getPassword()+"'";
        Cursor res = sqLiteDatabase.rawQuery (sql,null);
        Log.d("Test",sql);

        if(res.getCount()>0){
            return true;

        }
        else {
            return false;
        }


    }

    public Cursor getDataById(String user){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql="select * from " + Tables1.User.TABLE_NAME_CUSTOMER +" where userName = '"+ user +"'";
        Cursor res = sqLiteDatabase.rawQuery (sql,null);

        return res;

    }

    public boolean update(Customer customer){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables1.User.COL_2,customer.getEmail());
        contentValues.put(Tables1.User.COL_3,customer.getPassword());
        contentValues.put(Tables1.User.COL_4,customer.getName());
        contentValues.put(Tables1.User.COL_5,customer.getAddress());
        contentValues.put(Tables1.User.COL_6,customer.getContactNo());
        db.update(Tables1.User.TABLE_NAME_CUSTOMER,contentValues,"userName = ?",new String[] {customer.getUserName()});

        return true;

    }

    public Integer deleteData(String userName){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Tables1.User.TABLE_NAME_CUSTOMER,"userName = ?",new String[]{userName})+db.delete(Tables1.Delivery.TABLE_NAME_DELIVERY,"userName = ?",new String[]{userName})+db.delete(Tables1.Payment.TABLE_NAME_PAYMENT,"userName = ?",new String[]{userName})+db.delete(Tables1.Cart.TABLE_NAME_CART,"userName = ?",new String[]{userName});

    }

        //------------------------------Gihan-------------------------------

    public boolean enterDeliveryDetails(Delivery delivery){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables1.Delivery.COL_2_D,delivery.getName());
        contentValues.put(Tables1.Delivery.COL_3_D,delivery.getDeliveryAddress());
        contentValues.put(Tables1.Delivery.COL_4_D,delivery.getPostalCode());
        contentValues.put(Tables1.Delivery.COL_5_D,delivery.getContactNo());
        contentValues.put(Tables1.Delivery.COL_6_D,delivery.getUserName());
        contentValues.put(Tables1.Delivery.COL_7_D,delivery.getDateTime());

        long result =sqLiteDatabase.insert(Tables1.Delivery.TABLE_NAME_DELIVERY,null,contentValues);

        if(result == -1){

            return  false;
        }
        else{

            return true;
        }

    }

    public Boolean cardValidation(Bank bank){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String sql="select * from " + Tables1.Bank.TABLE_NAME_BANK  + " where cardNumber  = '" + bank.getCardNumber() + "' and holderName = '" + bank.getHolderName() + "' and  cardType = '" + bank.getCardType() + "' and expiryDate = '" + bank.getExpiryDate() + "' and cvv = '" + bank.getCvv() +"'";
        Cursor res = sqLiteDatabase.rawQuery (sql,null);
        Log.d("Test",sql);

        if(res.getCount()>0){
            return true;

        }
        else {
            return false;
        }



    }

    public boolean insertPayment(Payment payment){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables1.Payment.COL_2_P,payment.getUserName());
        contentValues.put(Tables1.Payment.COL_3_P,payment.getAmount());

        long result =sqLiteDatabase.insert(Tables1.Payment.TABLE_NAME_PAYMENT,null,contentValues);

        if(result == -1){

            return  false;
        }
        else{

            return true;
        }

    }

    public Cursor getDataByIdOrder(String user){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql="select * from " + Tables1.Delivery.TABLE_NAME_DELIVERY +" where userName = '"+ user +"'";
        Cursor res = sqLiteDatabase.rawQuery (sql,null);

        return res;

    }

    public Cursor getamountByIdOrder(String user){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql="select * from " + Tables1.Payment.TABLE_NAME_PAYMENT +" where userName = '"+ user +"'";
        Cursor res = sqLiteDatabase.rawQuery (sql,null);

        return res;

    }


    public boolean updateOrder(Delivery delivery){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables1.Delivery.COL_2_D,delivery.getName());
        contentValues.put(Tables1.Delivery.COL_3_D,delivery.getDeliveryAddress());
        contentValues.put(Tables1.Delivery.COL_4_D,delivery.getPostalCode());
        contentValues.put(Tables1.Delivery.COL_5_D,delivery.getContactNo());
        db.update(Tables1.Delivery.TABLE_NAME_DELIVERY,contentValues,"userName = ?",new String[] {delivery.getUserName()});

        return true;

    }


    public Integer cancelOrder(String userName){

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(Tables1.Delivery.TABLE_NAME_DELIVERY,"userName = ?",new String[]{userName})+db.delete(Tables1.Payment.TABLE_NAME_PAYMENT,"userName = ?",new String[]{userName});

    }

    public Cursor getTotalById(String user){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql="select total  from " + Tables1.Cart.TABLE_NAME_CART +" where userName = '"+ user +"'";
        Cursor res = sqLiteDatabase.rawQuery (sql,null);

        return res;

    }


        //------------------------------Isuri-------------------------------

    public boolean insertCart(Cart cart){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables1.Cart.COL_1_C , cart.getProductName());
        contentValues.put(Tables1.Cart.COL_2_C , cart.getQuantity());
        contentValues.put(Tables1.Cart.COL_3_C , cart.getTotal());
        contentValues.put(Tables1.Cart.COL_4_C, cart.getUserName());
        contentValues.put(Tables1.Cart.COL_5_C, cart.getImage());

        long result = sqLiteDatabase.insert(Tables1.Cart.TABLE_NAME_CART , null, contentValues);

        if(result == -1){

            return  false;
        }
        else{

            return true;
        }

    }

    public Cursor getDataCartall(){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql="select * from " + Tables1.Cart.TABLE_NAME_CART ;
        Cursor res = sqLiteDatabase.rawQuery (sql,null);

        return res;

    }
    public Cursor getDataCart(String user){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql="select * from " + Tables1.Cart.TABLE_NAME_CART +" where userName = '"+ user +" ' ";
        Cursor res = sqLiteDatabase.rawQuery (sql,null);

        return res;

    }


    public boolean updateCart(Cart cart){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables1.Cart.COL_2_C,cart.getQuantity());
        contentValues.put(Tables1.Cart.COL_3_C,cart.getTotal());

        db.update(Tables1.Cart.TABLE_NAME_CART,contentValues,"userName = ?",new String[] {cart.getUserName()});

        return true;

    }


    public Integer deleteCart(Cart cart){

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(Tables1.Cart.TABLE_NAME_CART,"userName = ?",new String[]{cart.getUserName()});

    }

    public Integer deleteCartitem(Cart cart){

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(Tables1.Cart.TABLE_NAME_CART,"productName = ?",new String[]{cart.getProductName()});

    }
    //------------------------------Vimanga-------------------------------
    public boolean addProduct(Product p){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables1.Product.COL_2_P,p.getProductName());
        contentValues.put(Tables1.Product.COL_3_P,p.getCategory());
        contentValues.put(Tables1.Product.COL_4_P,p.getPrice());
        contentValues.put(Tables1.Product.COL_5_P,p.getQuantity());
        contentValues.put(Tables1.Product.COL_6_P,p.getDescription());
        contentValues.put(Tables1.Product.COL_7_P,p.getImage());
        long result =sqLiteDatabase.insert(Tables1.Product.TABLE_NAME_PRODUCT,null,contentValues);

        if(result == -1){

            return  false;
        }
        else{

            return true;
        }

    }
    public boolean addReview(Review r){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables1.Review.COL_2_R,r.getProductName());
        contentValues.put(Tables1.Review.COL_3_R,r.getStar());
        contentValues.put(Tables1.Review.COL_4_R,r.getUserName());
        contentValues.put(Tables1.Review.COL_5_R,r.getReview());

        long result =sqLiteDatabase.insert(Tables1.Review.TABLE_NAME_REVIEW,null,contentValues);

        if(result == -1){

            return  false;
        }
        else{

            return true;
        }

    }
    public Cursor getReviewbyID(String name){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql="select * from " + Tables1.Review.TABLE_NAME_REVIEW+" where productname = '"+ name +"'";
        Cursor res = sqLiteDatabase.rawQuery (sql,null);

        return res;
    }
    public Cursor getProductById(String name){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql="select * from " + Tables1.Product.TABLE_NAME_PRODUCT +" where productname = '"+ name +"'";
        Cursor res = sqLiteDatabase.rawQuery (sql,null);

        return res;

    }
    public Cursor getProductByCategory(String category){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql="select * from " + Tables1.Product.TABLE_NAME_PRODUCT +" where "+Tables1.Product.COL_3_P+" = '"+ category +"'";
        Cursor res = sqLiteDatabase.rawQuery (sql,null);

        return res;

    }
    public boolean updateProduct(String name,String category,String price,String quantity,String description){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Tables1.Product.COL_2_P,name);
        contentValues.put(Tables1.Product.COL_3_P,category);
        contentValues.put(Tables1.Product.COL_4_P,price);
        contentValues.put(Tables1.Product.COL_5_P,quantity);
        contentValues.put(Tables1.Product.COL_6_P,description);
        db.update(Tables1.Product.TABLE_NAME_PRODUCT,contentValues,"productname = ?",new String[] {name});

        return true;

    }

    public Integer deleteProduct(String Name){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Tables1.Product.TABLE_NAME_PRODUCT,"productname = ?",new String[]{Name});

    }

    public Cursor getAllProducts(){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql="select * from " + Tables1.User.TABLE_NAME_CUSTOMER +"";
        Cursor res = db.rawQuery (sql,null);
        return res;
    }









}
