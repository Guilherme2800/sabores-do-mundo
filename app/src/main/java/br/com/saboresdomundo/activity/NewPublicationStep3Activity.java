package br.com.saboresdomundo.activity;

import androidx.annotation.Nullable;
import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.model.Publication;

public class NewPublicationStep3Activity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1;
    private Button btnSelectImage;
    private ImageView imageView;

    private Publication publication;

    private Uri image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_publication_step3);

        publication = (Publication) getIntent().getSerializableExtra("publication");

        btnSelectImage = findViewById(R.id.btnSelectImage);
        imageView = findViewById(R.id.imageSelected);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Verifique se a permissão foi concedida
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // A permissão ainda não foi concedida, solicite-a ao usuário
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
        }

        buildNextStep();
    }

     private void buildNextStep(){

        Button button = findViewById(R.id.receitaProximaEtapa3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imageIsValid()){

                    Toast.makeText(getApplicationContext(), "Processando imagem, aguarde...", Toast.LENGTH_SHORT).show();

                    setImage();

                    Intent intent = new Intent(NewPublicationStep3Activity.this, NewPublicationStep4Activity.class);
                    intent.putExtra("publication", publication);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Insira a imagem para continuar...", Toast.LENGTH_SHORT).show();
                }

            }
        });

     }

     private boolean imageIsValid(){
        return this.image != null;
     }

     private void setImage(){
        this.publication.setImgPath(saveImageToDrawable(image));
     }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            this.image = imageUri;
        }
    }

    private String saveImageToDrawable(Uri imageUri) {
        try {

            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            File diretorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File pasta = new File(diretorio, "MinhasImagens");
            pasta.mkdirs();

            String nomeArquivo = publication.getTitle() + "imagem.png";
            File arquivo = new File(pasta, nomeArquivo);

            FileOutputStream stream = new FileOutputStream(arquivo);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.flush();
            stream.close();

            Toast.makeText(getApplicationContext(), "Imagem salva com sucesso!", Toast.LENGTH_SHORT).show();

            return arquivo.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}