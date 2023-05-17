package br.com.saboresdomundo;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import br.com.saboresdomundo.activity.FilterByCategory;
import br.com.saboresdomundo.databinding.ActivitySelectCountryBinding;
import br.com.saboresdomundo.model.Category;

public class SelectCountryActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivitySelectCountryBinding binding;

    private boolean fine_location;

    private int FINE_LOCATION_REQUEST = 0;

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySelectCountryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestPermission();

        buildButtonBack();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void buildButtonBack(){
        TextView view = findViewById(R.id.select_country_back);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void requestPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        this.fine_location = (permissionCheck == PackageManager.PERMISSION_GRANTED);
        if (this.fine_location) return;
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                FINE_LOCATION_REQUEST);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean granted = (grantResults.length > 0) &&
                (grantResults[0] == PackageManager.PERMISSION_GRANTED);
        this.fine_location = (requestCode == FINE_LOCATION_REQUEST) && granted;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        latitude = 0d;
        longitude = 0d;

        // Add markers for each country
        LatLng latlng = new LatLng(-15.77972, -47.92972);

        try{
            FusedLocationProviderClient fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(this);
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(location -> {
                if (location!=null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            });

            latlng = new LatLng(latitude, longitude);

        }catch(Exception e){
            System.out.println("Pegando localização padrão");
        }finally {
            if(latitude == 0d || longitude == 0d){
                latlng = new LatLng(-15.77972, -47.92972);
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addresses;
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    String countryName = addresses.get(0).getCountryName();

                    Category category = new Category(R.mipmap.world, countryName, R.mipmap.country_background_world_foreground);

                    Intent intent = new Intent(SelectCountryActivity.this, FilterByCategory.class);
                    intent.putExtra("category", category);

                    Toast.makeText(SelectCountryActivity.this, category.getName(), Toast.LENGTH_LONG);

                    SelectCountryActivity.this.startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void test(Marker marker){
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
    }
}