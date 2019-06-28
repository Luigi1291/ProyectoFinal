package com.lusberc.billwallet;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lusberc.billwallet.LogIn.LoginActivity;
import com.lusberc.billwallet.Models.Bill;
import com.lusberc.billwallet.Models.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static android.widget.Toast.LENGTH_LONG;
import static com.lusberc.billwallet.Utilities.GeneralValidations.extractBillDate;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Uri imageUri;
    //private ContentLoadingProgressBar progressBar;
    private static final int TAKE_PICTURE = 1;
    private static final int PICK_IMAGE = 2;
    private static final int INTERNET = 3;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private static final String TAG = "MAIN ACTIVITY::";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference(currentUser.getUid());
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .build();
        db.setFirestoreSettings(settings);
        FirebaseApp.initializeApp(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //progressBar = (ContentLoadingProgressBar) this.findViewById(R.id.progress_bar);
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
                    int writePermissionCode = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int readPermissionCode = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (readPermissionCode == PackageManager.PERMISSION_DENIED || writePermissionCode == PackageManager.PERMISSION_DENIED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        }
                    } else {

                    try {
                        FirebaseVisionImage image;
                        image = FirebaseVisionImage.fromFilePath(getApplicationContext(), imageUri);
                        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                        .getOnDeviceTextRecognizer();
                        textRecognizer.processImage(image)
                            .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                @Override
                                public void onSuccess(FirebaseVisionText result) {

                                    MyApplication._mBill.setImageText(result.getText());
                                    addBillToFirebase();
                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            showToastMsj("Fallo al procesar la imagen");
                                        }
                                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    showToastMsj("Fallo al cargar la imagen");
                    Log.e("Camera", e.toString());
                    Log.e("Camera", Arrays.toString(e.getStackTrace()));
                }
                    }
            }
            break;
            case PICK_IMAGE: {
                if( data != null) {
                    imageUri = data.getData();

                    try {
                        FirebaseVisionImage image;
                        image = FirebaseVisionImage.fromFilePath(getApplicationContext(), imageUri);
                        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                                .getOnDeviceTextRecognizer();
                        textRecognizer.processImage(image)
                                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                    @Override
                                    public void onSuccess(FirebaseVisionText result) {

                                        MyApplication._mBill.setImageText(result.getText());
                                        addBillToFirebase();
                                    }
                                })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                showToastMsj("Fallo al procesar la imagen");
                                            }
                                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        showToastMsj("Fallo al cargar la imagen");
                        Log.e("Camera", e.toString());
                        Log.e("Camera", Arrays.toString(e.getStackTrace()));
                    }
                }
                else{
                    showToastMsj("No seleccionaste ninguna imagen");
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
                        TAKE_PICTURE);
            }
            else{
                takePhoto(this.getCurrentFocus());
            }
        } else if (id == R.id.nav_gallery) {

            //TODO: Crear lista de imagenes con vista previa por usuario.

        } else if (id == R.id.nav_upload) {
            //Revisar permisos de escritura y lectura
            int writePermissionCode = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermissionCode = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (readPermissionCode == PackageManager.PERMISSION_DENIED || writePermissionCode == PackageManager.PERMISSION_DENIED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
            }
            else{
                uploadPhoto(this.getCurrentFocus());
            }

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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date resultdate = new Date(System.currentTimeMillis());
        MyApplication._mBill.setFechaVencimiento(formatter.format(resultdate));
        File photo = new File(Environment.getExternalStorageDirectory(),  "Bill.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        startActivityForResult(intent, TAKE_PICTURE);
    }

    public void uploadPhoto(View view) {
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, PICK_IMAGE);
    }

    public void showToastMsj(String message){
        Toast.makeText(getApplicationContext(), message,
                LENGTH_LONG).show();
    }

    private void showTextOnFragment(String text){
        TextView txtRes = this.findViewById(R.id.txtResult);
        txtRes.setCursorVisible(true);
        txtRes.setText(text);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case TAKE_PICTURE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    takePhoto(this.getCurrentFocus());

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case PICK_IMAGE:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0){
                 if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    uploadPhoto(this.getCurrentFocus());

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
                }
            }
            case INTERNET:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0){
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                        addImageToFirebaseStorage();

                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                    }
                    return;
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void addImageToFirebaseStorage(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {


            String imagePath = "images/users/" + MyApplication._mBill.getImageName() + ".jpg";
            final StorageReference riversRef = mStorageRef.child(imagePath);
            final ImageView mImageView = findViewById(R.id.imgResult);
            UploadTask uploadTask = riversRef.putFile(imageUri);
            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    showToastMsj("Falló al intentar cargar la imagen");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(
                            new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uriData) {
                                    /*
                                        Download the image from FireBase and set it as
                                        ImageView image programmatically.
                                    */
                                    new DownLoadImageTask(mImageView).execute(uriData.toString());
                                }
                            }
                    );
                    showToastMsj(riversRef.getDownloadUrl().toString());
                }
            });
        }else{
                Log.v(TAG,"Internet Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            }
        } else {
            Log.v(TAG,"Read from file Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    private void addBillToFirebase(){

        //progressBar.setVisibility(View.VISIBLE);
        String fechaVencimiento =  extractBillDate(MyApplication._mBill.getImageText());
        if(!fechaVencimiento.isEmpty())MyApplication._mBill.setFechaVencimiento(fechaVencimiento);
        MyApplication._mBill.setUserID(currentUser.getUid());
        String id = db.collection("textBills")
                .document(currentUser.getUid())
                .collection("Facturas")
                .document().getId();
        MyApplication._mBill.setImageName(id);



        addImageToFirebaseStorage();


        /*TODO:
            Extraer Monto total
            guardar imagen usando el storage de firebase
         */
/*

//Maps
            // Intent intent = new Intent(view.getContext(), MainActivity.class);
            Intent intent = new Intent(view.getContext(), MapsActivityCurrentPlace.class);
            //intent.putExtra("RESULT_VALUE", resultado.toString());
            getActivity().startActivity(intent);
            getActivity().finish();


        db.collection("textBills")
                .document(MyApplication._mBill.getUserID())
                .collection("Facturas")
                .document(MyApplication._mBill.getImageName())
                .set(MyApplication._mBill)
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
                });*/
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected void onProgressUpdate(Integer... values) {
            //progressBar.setProgress(values[0]);
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }
        protected void onPostExecute(Bitmap result){
            //progressBar.setVisibility(View.GONE);
            imageView.setImageBitmap(result);
        }
    }
}


