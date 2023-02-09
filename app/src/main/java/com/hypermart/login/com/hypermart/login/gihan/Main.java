package com.hypermart.login.com.hypermart.login.gihan;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.hypermart.login.com.hypermart.login.helani.MyAccountFragment;
import com.hypermart.login.R;
import com.hypermart.login.com.hypermart.login.helani.SignIn;
import com.hypermart.login.com.hypermart.login.helani.SignUp;
import com.hypermart.login.com.hypermart.login.isuri.ShoppingCartFragment;


public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout dl;
    private ActionBarDrawerToggle adt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gihan_activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);

        dl = this.findViewById(R.id.drawerLayout);
        adt = new ActionBarDrawerToggle(this, dl, toolbar, R.string.open, R.string.close);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        dl.addDrawerListener(adt);
        adt.syncState();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

            navigationView.setCheckedItem(R.id.home);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {

            case R.id.account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyAccountFragment()).commit();
                break;

            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;


            case R.id.cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShoppingCartFragment()).commit();
                break;


            case R.id.orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OrdersFragment()).commit();
                break;

            case R.id.logout:

                new AlertDialog.Builder(this)
                        .setTitle("Logout Confirmation")
                        .setMessage("Are you sure you want to logout?").setIcon(R.drawable.ic_add_alert_black_24dp)
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Main.this.startActivity(new Intent(Main.this, SignIn.class));

                            }
                        }).setNegativeButton("NO",null).show();


                break;

        }

        dl.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
