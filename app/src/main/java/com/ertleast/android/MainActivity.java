package com.ertleast.android;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static java.lang.System.exit;


public class MainActivity extends AppCompatActivity
        implements
        // Implement the Navigation Drawer selection listener
        NavigationView.OnNavigationItemSelectedListener,
        // Implement the connection change call back
        NetworkChangeReceiver.ConnectionChangeCallback {
    private DrawerLayout drawer;
    private BottomNavigationView bottomNav;
    private NavigationView navigationView;
    private NetworkChangeReceiver networkChangeReceiver = null;
    //Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //Reference to the Navigation Drawer object
                    NavigationView navigationView = findViewById(R.id.nav_view);
                    //The Bottom Navigation Menu items are shown deselected at first
                    bottomNav.getMenu().setGroupCheckable(0, true, true);
                    //Choose action to be taken as per Bottom navigation menu item selected
                    switch (item.getItemId()) {
                        //Case for Message in Bottom Navigation Menu
                        case R.id.nav_home:
                            //The Bottom Navigation Menu item for Message is shown selected
                            navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                            //Launch the HomeFragment
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new HomeFragment()).commit();
                            //Leave the switch case
                            break;
                        //Case for Chat in Bottom Navigation Menu
                        case R.id.nav_drug:
                            //The Bottom Navigation Menu item for Message is shown selected
                            navigationView.getMenu().findItem(R.id.nav_drug).setChecked(true);

                            //Launch the DrugFragment
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new DrugFragment()).commit();
                            //Leave the switch case
                            break;
                        //Case for Profile in Bottom Navigation Menu
                        case R.id.nav_disease:
                            //The Bottom Navigation Menu item for Message is shown selected
                            navigationView.getMenu().findItem(R.id.nav_disease).setChecked(true);
                            //Launch the SpecialityFragment
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new SpecialityWebFragment()).commit();
                            //Leave the switch case
                            break;
                    }

                    return true;
                }
            };


    @Override
    protected void attachBaseContext(Context base) {
        //Fetches the latest language configuration from the SharePreferences
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Request code 1 corresponds to the results from Settings Activity
        if (requestCode == 1) {
            // RESULT_OK corresponds to the results from Language change in Settings Activity
            if (resultCode == RESULT_OK) {
                // Following code restarts the whole App
                PackageManager packageManager = getPackageManager();
                Intent intent_restart = packageManager.getLaunchIntentForPackage(getPackageName());
                ComponentName componentName = intent_restart.getComponent();
                Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                startActivity(mainIntent);
                exit(0);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        bottomNav = findViewById(R.id.bottom_nav_view);
        bottomNav.setVisibility(View.GONE);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        ////////////
        // Create an IntentFilter instance.
        IntentFilter intentFilter = new IntentFilter();
        // Add network connectivity change action.
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        // Set broadcast receiver priority.
        intentFilter.setPriority(100);
        // Create a network change broadcast receiver.
        networkChangeReceiver = new NetworkChangeReceiver();

        // Register the broadcast receiver with the intent filter object.
        registerReceiver(networkChangeReceiver, intentFilter);
        // Enable the connection change receiver to call back
        networkChangeReceiver.setConnectionChangeCallback(this);


        /*requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar*/

        //////
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setTitle(getResources().getString(R.string.app_name));
        setTitle("Thai Pharma Index");
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.abs_layout);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final View headerView = navigationView.getHeaderView(0);



        SharedPreferences prefs = getSharedPreferences("com.biogenics.data", Context.MODE_PRIVATE);
        String mem_name = prefs.getString("mem_name", "0000000000");
        String email = prefs.getString("email", "0000000000");


        headerView.findViewById(R.id.text_username).setVisibility(View.VISIBLE);
        headerView.findViewById(R.id.text_useremail).setVisibility(View.VISIBLE);
        headerView.findViewById(R.id.text_welcome).setVisibility(View.VISIBLE);


        TextView navUsername = headerView.findViewById(R.id.text_username);
        navUsername.setText(mem_name);
        TextView navUseremail = headerView.findViewById(R.id.text_useremail);
        navUseremail.setText(email);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    //Event Handler for Navigation Drawer Item being selected
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //To keep consistency the Bottom Navigation Menu items are shown deselected at first
        bottomNav.getMenu().setGroupCheckable(0, true, true);
        //Choose action to be taken as per Navigation Drawer item selected
        switch (item.getItemId()) {
            //Case for Message in Navigation Drawer
            case R.id.nav_home:

                //To keep consistency the Bottom Navigation Menu item for Message is shown selected
                bottomNav.getMenu().findItem(R.id.nav_home).setChecked(true);
                //Launch the HomeFragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                //Leave the switch case
                break;
            //Case for Chat in Navigation Drawer
            case R.id.nav_drug:
                //To keep consistency the Bottom Navigation Menu item for Chat is shown selected
                bottomNav.getMenu().findItem(R.id.nav_drug).setChecked(true);

                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                //Launch the DrugFragment
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DrugFragment()).commit();*/
                //Leave the switch case
                break;
            //Case for Profile in Navigation Drawer
            case R.id.nav_disease:
                //To keep consistency the Bottom Navigation Menu item for Profile is shown selected
                bottomNav.getMenu().findItem(R.id.nav_disease).setChecked(true);
                //Launch the SpecialityFragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SpecialityWebFragment()).commit();
                //Leave the switch case
                break;
            //Case for Scanner in Navigation Drawer
            case R.id.nav_speciality:
                //To keep consistency the Bottom Navigation Menu item for Scanner is shown selected
                //bottomNav.getMenu().setGroupCheckable(0, false, true);
                //Launch the ScannerActivity
                //startActivity(new Intent(getApplicationContext(), SpecialityActivity.class));
                //Leave the switch case
                break;
            //Case for Scanner in Navigation Drawer
            case R.id.nav_scanner:
                //To keep consistency the Bottom Navigation Menu item for Scanner is shown selected
                //bottomNav.getMenu().setGroupCheckable(0, false, true);
                //Launch the ScannerActivity
                startActivity(new Intent(getApplicationContext(), ScanActivity.class));
                //Leave the switch case
                break;
            //Case for Calculators in Navigation Drawer
            case R.id.nav_calculators:
                //bottomNav.getMenu().setGroupCheckable(0, false, true);
                //Launch the CalculatorsHeaderActivity
                startActivity(new Intent(getApplicationContext(), CalculatorsHeaderActivity.class));
                //Leave the switch case
                break;
            //Case for Settings in Navigation Drawer
            case R.id.nav_settings:
                //Intent referring to the SettingsActivity
                Intent intent_wait = new Intent(getApplicationContext(), SettingsActivity.class);
                //Wait for the results of Settings change
                startActivityForResult(intent_wait, 1);
                //startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                //Leave the switch case
                break;
            //Case for Share in Navigation Drawer
            case R.id.nav_share:
                //Deliver some data to someone else
                Intent intent_share = new Intent(Intent.ACTION_SEND);
                //the MIME type text/plain with the default is ASCII (US-ASCII) charset is assumed
                intent_share.setType("text/plain");
                //URL from where App can be downloaded
                String link_playstore = "http://thaipharmaindex.com/bio_mobile/ThaiPharma.apk";
                //Pre-defined descriptive text along with the link to download the App
                String share_body = "Try our app: " + link_playstore;
                //Pre-defined subject to be shared when in email
                String share_subject = "Thai Pharma App";
                //Body descriptive text to be displayed in the shared text
                intent_share.putExtra(Intent.EXTRA_TEXT, share_body);
                //Subject line of the link to be shared when in email
                intent_share.putExtra(Intent.EXTRA_SUBJECT, share_subject);
                //The chooser heading to be displayed
                startActivity(Intent.createChooser(intent_share, "Share using:"));
                //Leave the switch case
                break;
            //Case for Helpline 24 x 7 in Navigation Drawer
            case R.id.nav_send:
                //Intent to call the phone dialler
                Intent intent = new Intent(Intent.ACTION_DIAL);
                //Phone number pre-defined
                intent.setData(Uri.parse("tel:6625137856"));
                //Invokes the dialer with the pre-defined phone number
                startActivity(intent);
                //Leave the switch case
                break;
            case R.id.nav_logout:
                HandleSharedPreferences.persist(this, "logged_in", "false");
                finish();
                startActivity(new Intent(MainActivity.this, LanguageActivity.class));
                //System.exit(0);
                break;
        }
        //Close the drawer immediately after some option has been pressed. Push object to x-axis
        // position at the start of its container, in this case the left side of the MainActivity
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onConnectionChange(boolean isConnected) {
        String networkMessage = "";
        if (isConnected ) {
            if (bottomNav.getMenu().findItem(R.id.nav_home).isChecked()) {
                //Reload the fragment container
                    /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();*/
            }
            if (bottomNav.getMenu().findItem(R.id.nav_drug).isChecked()) {
                //Reload the fragment container
               /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DrugFragment()).commit();*/
            }
            if (bottomNav.getMenu().findItem(R.id.nav_disease).isChecked()) {
                //Reload the fragment container
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SpecialityFragment()).commit();*/
            }

            // will be called when internet is back
            networkMessage = getResources().getString(R.string.net_available);
        } else {
            // will be called when internet is gone.
            networkMessage = getResources().getString(R.string.net_not_available);
        }

        // Use a toast to show network status info.
       // Toast.makeText(this, networkMessage, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // If the broadcast receiver is not null then unregister it.
        // This action is better placed in activity onDestroy() method.
        if (this.networkChangeReceiver != null) {
            unregisterReceiver(this.networkChangeReceiver);
        }
    }


}