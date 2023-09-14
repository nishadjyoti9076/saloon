package com.shashank.platform.saloon.ui;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import static com.shashank.platform.saloon.application.MySaloonApplication.sharedPreferenceClass;
import static com.shashank.platform.saloon.constant.ApiConst.ONE_TIME_LOGIN;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.afollestad.materialdialogs.DialogBehavior;
import com.afollestad.materialdialogs.LayoutMode;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.bottomsheets.BottomSheet;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.shashank.platform.saloon.BottomNavigationBehavior;
import com.shashank.platform.saloon.R;
import com.shashank.platform.saloon.constant.SharedPreferenceClass;
import com.shashank.platform.saloon.retrofit.ApiRepository;
import com.squareup.picasso.Picasso;

import java.util.Collections;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView bottomNavigationView;

    private MutableLiveData<String> allApplicationObserver = new MutableLiveData<>();
    private MutableLiveData<String> errorObserver = new MutableLiveData<>();
    private PlacesClient placesClient;
    private FusedLocationProviderClient fusedLocationClient;
    private String address;
    private SweetAlertDialog mDialog;//declare this dialog globally
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationHome:
                    return true;
                case R.id.navigationInstruction:
                    return true;
                case R.id.navigationNotification:
/*                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);*/
                    return true;
                case R.id.navigationprofile:
                    showCustomViewDialog(new BottomSheet(LayoutMode.WRAP_CONTENT));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Places.initialize(this, "AIzaSyCOejrdA6Od52MC7gbIKCGTeFJeYc6fOY4");
        placesClient = Places.createClient(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (checkLocationPermission()) {
            getLastKnownLocation();
        } else {
            requestLocationPermission();
        }

        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

        LinearLayout ll_view_leads = (LinearLayout) findViewById(R.id.ll_view_leads);
        LinearLayout ll_leave_manegement = (LinearLayout) findViewById(R.id.ll_leave_manegement);
        LinearLayout ll_view_product = (LinearLayout) findViewById(R.id.ll_view_product);
        LinearLayout ll_update_prfile = (LinearLayout) findViewById(R.id.ll_update_prfile);
        LinearLayout ll_productAllocation = (LinearLayout) findViewById(R.id.ll_productallocation);
        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.mainuser);

        String imageUrl =
                "https://mysalonss.dreamitsolution.org/AdminContent/ProfilePic/6682fc7d-7c19-4b65-b147-6b93697d529f_user-png.png";
        Picasso.get()
                .load(imageUrl)
                .into(circleImageView);

        ll_view_leads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AssignedLeadsActivity.class);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });

        ll_leave_manegement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowLeaveActivity.class);
                startActivity(intent);
            }
        });

        ll_view_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewListOfProductActivity.class);
                startActivity(intent);
            }
        });

        ll_update_prfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewProfileActivity.class);
                startActivity(intent);
            }
        });


        ll_productAllocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductAllocationActivity.class);
                startActivity(intent);
            }
        });

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_dark_mode) {
            //code for setting dark mode
            //true for dark mode, false for day mode, currently toggling on each click

        }

      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }

    private void showCustomViewDialog(DialogBehavior dialogBehavior) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_custom_view);

        LinearLayout ll_helpline = bottomSheetDialog.findViewById(R.id.ll_helpline);
        LinearLayout ll_logout = bottomSheetDialog.findViewById(R.id.ll_logout);

        LinearLayout ll_changepassword = bottomSheetDialog.findViewById(R.id.ll_changepassword);


        ll_helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HelpLineActivity.class);
                startActivity(intent);
            }
        });


        ll_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });


        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SweetAlertDialog pDialog = new SweetAlertDialog(
                        MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                pDialog.setContentText("Are you sure you want to Logout?");
                pDialog.setCancelButton("NO", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        bottomSheetDialog.dismiss();
                        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

                    }
                });
                pDialog.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        //sweetAlertDialog.dismissWithAnimation();
                        //sweetAlertDialog.dismissWithAnimation();
                        sharedPreferenceClass.clearData();

                        doLogout();
                        //  ApiRepository.logoutAPI(allApplicationObserver, errorObserver);
                    }
                }).show();

                pDialog.show();
                pDialog.setCancelable(false);
            }
        });
        bottomSheetDialog.show();
    }

    public void doLogout() {
        SweetAlertDialog mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText("Loading ...");
        mDialog.setCancelable(false);
        mDialog.show();
        /* ApiRepository.INSTANCE.logoutAPI(allApplicationObserver, errorObserver, mDialog);*/
        Toast.makeText(MainActivity.this, "Logout Sucessfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, NewLoginActivity.class);
        startActivity(intent);
        mDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            finishAffinity();
        } else {

            Toast
                    .makeText(this, "Click once again to exit", Toast.LENGTH_LONG)
                    .show();
            doubleBackToExitPressedOnce = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 3 * 1000);
        }
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },
                123
        );
    }

    private boolean checkLocationPermission() {
        int fineLocationPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        );
        int coarseLocationPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        );
        return fineLocationPermission == PackageManager.PERMISSION_GRANTED ||
                coarseLocationPermission == PackageManager.PERMISSION_GRANTED;
    }


    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                            mDialog.setTitleText("Getting Location ...");
                            mDialog.setCancelable(false);
                            mDialog.show();
                            reverseGeocodeLocation(location.getLatitude(), location.getLongitude());
                        } else {
                            // Handle case where last known location is not available
                        }
                    }
                });
    }

    private void reverseGeocodeLocation(double latitude, double longitude) {
        Place.Field placeFields = Place.Field.ADDRESS;
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(Collections.singletonList(placeFields)).build();

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        placesClient.findCurrentPlace(request).addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
            @Override
            public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                if (task.isSuccessful()) {
                    FindCurrentPlaceResponse response = task.getResult();
                    if (response != null && response.getPlaceLikelihoods() != null && !response.getPlaceLikelihoods().isEmpty()) {
                        PlaceLikelihood likelihood = response.getPlaceLikelihoods().get(0);
                        Place place = likelihood.getPlace();
                        address = place.getAddress();
                        mDialog.dismissWithAnimation();
                        Log.d("reverseGeocodeLocation", "reverseGeocodeLocation: " + address);
                        // Process the address data
                    }
                } else {
                    Exception exception = task.getException();
                    if (exception != null) {
                        exception.printStackTrace();
                        // Handle error
                    }
                }
            }
        });
    }
}

/*
http://mysalonssapi.dreamitsolution.org/http://mysalonssapi.dreamitsolution.org/api/UserLeaves/UpdateLeave
        {
        "id": 13,
        "userId": 4,
        "todate": "2023-09-11T00:00:00",
        "fromDate": "2023-09-10T00:00:00",
        "reason": "fever",
        "subject": "test"
        }*/
