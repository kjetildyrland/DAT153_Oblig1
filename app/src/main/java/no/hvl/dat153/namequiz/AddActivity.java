package no.hvl.dat153.namequiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;

import no.hvl.dat153.namequiz.DatabaseList.Person;

/**
 *
 */
public class AddActivity extends AppCompatActivity {

    private static int REQUEST_IMAGE_CAPTURE = 0;
    private static int RESULT_LOAD_IMAGE = 1;
    Button addButton, leggTilButton, cameraButton;

    /**
     * creates a view for the add activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addButton = (Button) findViewById(R.id.velgBilde);
        leggTilButton = (Button) findViewById(R.id.leggTilPerson);
        cameraButton = (Button) findViewById(R.id.camera_button);

        addButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                             Intent i = new Intent(
                                                     Intent.ACTION_PICK,
                                                     android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                             startActivityForResult(i, RESULT_LOAD_IMAGE);

                                         }
                                     }
        );
        leggTilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPersonActivity();
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


    }

    /**
     * goes to main activity
     */
    private void moveToMainActivity() {
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
    }


    /**
     * adds a new person to the database
     */
    private void addPersonActivity() {

        ImageView imageView = (ImageView) findViewById(R.id.imageViewQuiz);
        EditText editText = findViewById(R.id.content);
        String result = editText.getText().toString();
        if (imageView.getDrawable() == null || result.matches("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("ERROR!!!!");
            builder.setMessage("Legg til et bilde og navn");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setCancelable(false);
            builder.show();
            return;
        }

        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        Person p = new Person(String.valueOf(DatabaseList.ITEMS.size() + 1), result, bitmap);
        DatabaseList.addItem(p);
        moveToMainActivity();

    }

    /**
     * opens photo gallery on phone
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageToUpload = findViewById(R.id.imageViewQuiz);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            ImageView imageView = (ImageView) findViewById(R.id.imageViewQuiz);
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imageToUpload.setImageBitmap(bitmap);

        }
    }
    }
