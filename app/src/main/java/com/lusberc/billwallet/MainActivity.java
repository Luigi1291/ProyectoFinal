package com.lusberc.billwallet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.lusberc.billwallet.LogIn.FragmentLogin;
import com.lusberc.billwallet.LogIn.FragmentSignUp;
import com.lusberc.billwallet.LogIn.LoginActivity;

import java.io.File;
import java.io.IOException;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Uri imageUri;
    private static final int TAKE_PICTURE = 1;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private static final String TAG = "MAIN ACTIVITY::";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .build();
        db.setFirestoreSettings(settings);
        FirebaseApp.initializeApp(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageUri;
                    int writePermissionCode = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int readPermissionCode = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (readPermissionCode == PackageManager.PERMISSION_DENIED || writePermissionCode == PackageManager.PERMISSION_DENIED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    } else {

                    try {
                        FirebaseVisionImage image;
                        image = FirebaseVisionImage.fromFilePath(getApplicationContext(), selectedImage);
                        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                        .getOnDeviceTextRecognizer();
                        textRecognizer.processImage(image)
                            .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                @Override
                                public void onSuccess(FirebaseVisionText result) {

                                    String resultText = result.getText();
                                    showTextOnFragment(resultText);
                                    addImageTextToFirebase(resultText);
                                    /*
                                    If the text recognition operation succeeds, a FirebaseVisionText object will be passed to the success listener.
                                    A FirebaseVisionText object contains the full text recognized in the image and zero or more TextBlock objects.
                                    Each TextBlock represents a rectangular block of text, which contains zero or more Line objects.
                                    Each Line object contains zero or more Element objects, which represent words and word-like entities (dates, numbers, and so on).
                                    For each TextBlock , Line , and Element object, you can get the text recognized in the region and the bounding coordinates of the region.

                                     */


                                    /*Yo can also iterate through the blocks and extract the text from there
                                    * This is more used on cases that you require an specific value*/
                                    /*for (FirebaseVisionText.TextBlock block : result.getTextBlocks()) {
                                        String blockText = block.getText();
                                        Float blockConfidence = block.getConfidence();
                                        List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
                                        Point[] blockCornerPoints = block.getCornerPoints();
                                        Rect blockFrame = block.getBoundingBox();
                                        for (FirebaseVisionText.Line line : block.getLines()) {
                                            String lineText = line.getText();
                                            Float lineConfidence = line.getConfidence();
                                            List<RecognizedLanguage> lineLanguages = line.getRecognizedLanguages();
                                            Point[] lineCornerPoints = line.getCornerPoints();
                                            Rect lineFrame = line.getBoundingBox();
                                            for (FirebaseVisionText.Element element : line.getElements()) {
                                                String elementText = element.getText();
                                                Float elementConfidence = element.getConfidence();
                                                List<RecognizedLanguage> elementLanguages = element.getRecognizedLanguages();
                                                Point[] elementCornerPoints = element.getCornerPoints();
                                                Rect elementFrame = element.getBoundingBox();
                                            }
                                        }
                                    }*/
                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            showToastMsj("Failed to process text from image");
                                        }
                                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    showToastMsj("Failed to load");
                    Log.e("Camera", e.toString());
                    Log.e("Camera", e.getStackTrace().toString());
                }
                    }
            }
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/ceberm"));
            startActivity(browserIntent);
            return true;
        }
        else
            if(id == R.id.action_SignOut){
                try {
                    //Cerrar Sesión
                    mAuth.signOut();
                    //Abrir login activity
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    this.startActivity(intent);
                    //Cerrar aplicación
                    this.finish();
                    Toast.makeText(getApplicationContext(), "Sesión Cerrada", LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al cerrar su sesión.", LENGTH_LONG).show();
                    Log.d(TAG, e.getMessage());
                }
            }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        0);
            }
            else{
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePhoto(this.getCurrentFocus());
                //startActivity(intent);
            }
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        startActivityForResult(intent, TAKE_PICTURE);
    }

    public void showToastMsj(String message){
        Toast.makeText(getApplicationContext(), message,
                LENGTH_LONG).show();
    }

    private void showTextOnFragment(String text){
        TextView txtRes = this.findViewById(R.id.txtResult);
        txtRes.setText(text);
    }

    private void addImageTextToFirebase(String imageText){

        db.collection("textBills")
                .document(currentUser.getUid())
                .set(imageText)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference) {
                        Log.d(TAG, "Image Text added for user "+ currentUser.getDisplayName());
                        Snackbar mySnackbar = Snackbar.make(getCurrentFocus(),
                                "Image text added with ID:" ,
                                Snackbar.LENGTH_SHORT);
                        //mySnackbar.setAction(R.string.undo_string, new MyUndoListener());
                        mySnackbar.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding bill", e);
                        Snackbar mySnackbar = Snackbar.make(getCurrentFocus(),
                                "Error adding bill", Snackbar.LENGTH_SHORT);
                        //mySnackbar.setAction(R.string.undo_string, new MyUndoListener());
                        mySnackbar.show();
                    }
                });
    }


}
