package br.com.saboresdomundo.activity;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import br.com.saboresdomundo.HomeActivity;
import br.com.saboresdomundo.R;
import br.com.saboresdomundo.SelectCountryActivity;
import br.com.saboresdomundo.config.FirebaseAuthListener;
import br.com.saboresdomundo.databinding.ActivitySelectCountryBinding;
import br.com.saboresdomundo.model.Category;
import br.com.saboresdomundo.model.Publication;
import br.com.saboresdomundo.model.Usuario;
import br.com.saboresdomundo.model.builder.CategoryBuilder;
import br.com.saboresdomundo.model.builder.PublicationViewBuilder;

public class NewPublicationStep4Activity extends FragmentActivity implements OnMapReadyCallback {

    private Publication publication;

    private List<String> categoriasList = new ArrayList<>();

    private GoogleMap mMap;
    private ActivitySelectCountryBinding binding;

    private boolean fine_location;

    private int FINE_LOCATION_REQUEST = 0;

    double latitude;
    double longitude;

    FirebaseAuth fbAuth;

    FirebaseAuthListener authListener;

    DatabaseReference drUser;

    Usuario user;

    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_publication_step4);

        publication = (Publication) getIntent().getSerializableExtra("publication");

        requestPermission();
        buildInserirCategorias();
        buildFinalizar();

        this.fbAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser fbUser = fbAuth.getCurrentUser();
        drUser = database.getReference("users/" + fbUser.getUid());

        drUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario tempUser = dataSnapshot.getValue(Usuario.class);
                if (tempUser != null) {
                    NewPublicationStep4Activity.this.user = tempUser;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_new_category);
        mapFragment.getMapAsync(this);
    }

    private void buildFinalizar(){

        Button button = findViewById(R.id.receitaProximaEtapa4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategories();
                savePublication();

                Toast.makeText(getApplicationContext(), "Receita salva com sucesso...", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(NewPublicationStep4Activity.this, MainActivity.class));
            }
        });
    }

    public void setCategories(){

        TextView countrySelected = findViewById(R.id.country_selected);

        String text = countrySelected.getText().toString().split(":")[1];

        Optional<Category> country = CategoryBuilder.buildCountryCategories().stream().filter(cat -> cat.getName().equalsIgnoreCase(text)).findFirst();

        if(country.isPresent()){
            this.publication.getCategory().add(country.get());
        }else{
            Category newCategory = new Category();
            newCategory.setImg(R.mipmap.image_default);
            newCategory.setImgFilter(R.mipmap.image_default);
            newCategory.setName(text);
            this.publication.getCategory().add(newCategory);
        }

        for(String category : categoriasList){
            Optional<Category> first = CategoryBuilder.buildDefultCategories().stream().filter(cat -> cat.getName().equalsIgnoreCase(category)).findFirst();
            if(first.isPresent()){
                this.publication.getCategory().add(first.get());
            }else{
                Category newCategory = new Category();
                newCategory.setImg(R.mipmap.image_default);
                newCategory.setImgFilter(R.mipmap.image_default);
                newCategory.setName(category);
                this.publication.getCategory().add(newCategory);
            }
        }

    }

    public void savePublication(){
        publication.setAutor(user.getName());
        PublicationViewBuilder.news.add(publication);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("publications");

        String publicationId = myRef.push().getKey();

        myRef.child(publicationId).setValue(publication);
    }

    private void buildInserirCategorias(){
        Button button = findViewById(R.id.inserirCategoria);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText categoriaInput = findViewById(R.id.categoriaInput);
                String categoria = categoriaInput.getText().toString();
                if(categoria.isEmpty()){
                    return;
                }
                categoriasList.add(categoria);

                categoriaInput.setText("");

                ListView categorias = findViewById(R.id.novaReceitaCategorias);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(NewPublicationStep4Activity.this, android.R.layout.simple_list_item_1, categoriasList);
                categorias.setAdapter(adapter);
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

        try{
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1);
            String countryName = addresses.get(0).getCountryName();

            TextView countrySelected = findViewById(R.id.country_selected);
            countrySelected.setText("Pais selecionado:"+ countryName);
        } catch (IOException e) {
            e.printStackTrace();
        }



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addresses;
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    String countryName = addresses.get(0).getCountryName();

                    TextView countrySelected = findViewById(R.id.country_selected);
                    countrySelected.setText("Pais selecionado:"+ countryName);

                    Toast.makeText(NewPublicationStep4Activity.this, countryName, Toast.LENGTH_LONG);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}