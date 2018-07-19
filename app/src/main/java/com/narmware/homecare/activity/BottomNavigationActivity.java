package com.narmware.homecare.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.narmware.homecare.R;
import com.narmware.homecare.fragment.AddressFragment;
import com.narmware.homecare.fragment.BookingFragment;
import com.narmware.homecare.fragment.DashboardFragment;
import com.narmware.homecare.fragment.DetailedFragment;
import com.narmware.homecare.fragment.ProfileFragment;
import com.narmware.homecare.fragment.SubSrvicesFragment;
import com.narmware.homecare.fragment.ViewMoreFragment;
import com.narmware.homecare.helper.SharedPreferencesHelper;

public class BottomNavigationActivity extends AppCompatActivity implements DashboardFragment.OnFragmentInteractionListener,BookingFragment.OnFragmentInteractionListener,ProfileFragment.OnFragmentInteractionListener,
        ViewMoreFragment.OnFragmentInteractionListener,DetailedFragment.OnFragmentInteractionListener,SubSrvicesFragment.OnFragmentInteractionListener,AddressFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        try {
            if (SharedPreferencesHelper.getIsMainAct(BottomNavigationActivity.this).equals("SubServices")) {
                SharedPreferencesHelper.setIsMainAct("Dashboard", BottomNavigationActivity.this);
            }
        }catch (Exception e)
        {}
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setSelectedItemId(R.id.nav_search);
        setFragment(new DashboardFragment(),"dashboard");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {

        /*if (getFragmentManager().getBackStackEntryCount() == 0) {
            Fragment f = getSupportFragmentManager().findFragmentByTag("dashboard");
            if (f instanceof DashboardFragment) {
                Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
            }
        }*/
        if(SharedPreferencesHelper.getIsMainAct(BottomNavigationActivity.this).equals("Dashboard"))
        {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit app", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
        else {
            super.onBackPressed();
        }

        if(SharedPreferencesHelper.getIsMainAct(BottomNavigationActivity.this).equals("SubServices"))
        {
            super.onBackPressed();
            SharedPreferencesHelper.setIsMainAct("Dashboard",BottomNavigationActivity.this);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_search:
                    setFragment(new DashboardFragment(),"dashboard");
                    return true;
                case R.id.nav_booking:
                    setFragment(new BookingFragment(),"booking");
                    return true;
                case R.id.nav_profile:
                    setFragment(new ProfileFragment(),"profile");
                    return true;
            }
            return false;
        }
    };

    public void setFragment(Fragment fragment,String tag)
    {
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment,tag);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
