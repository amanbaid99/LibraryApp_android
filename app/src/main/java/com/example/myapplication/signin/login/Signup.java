package com.example.myapplication.signin.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.User.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class Signup extends AppCompatActivity {
    EditText  Fullname, phone, emailid, password;
    Button  Signupbtn,chooseimg;
    TextView loginbtn;
    EditText Imgname;
    ImageView imgdisplay;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    UserDB userdb;
    DatabaseReference databaseReference;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
         Fullname=(EditText)findViewById(R.id.FullName );
         phone=(EditText)findViewById(R.id.Phone );
        emailid=(EditText)findViewById(R.id.EmailID );
         password=(EditText)findViewById(R.id.Password );
        Signupbtn=(Button)findViewById(R.id.Signupbtn);
         loginbtn=(TextView)findViewById(R.id.TextNavLgn) ;
         fAuth=FirebaseAuth.getInstance();
         progressBar=findViewById(R.id.progressBar);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("UserDB");
        chooseimg = (Button) findViewById(R.id.imgchooser);
        imgdisplay = (ImageView) findViewById(R.id.imgselectdisplay);
        userdb=new UserDB();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        chooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                   finish();
        }
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLoginPage();
            }
        });
         Signupbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onsignupbtn();

             }



         });
    }
    public void OpenLoginPage(){
        Intent intent1 =new Intent(getApplicationContext(),Login.class);
        startActivity(intent1);
    }



    public void onsignupbtn() {



        final String EmailID=emailid.getText().toString().trim();
        String Password=password.getText().toString().trim();
        String fname=Fullname.getText().toString().trim();
        String number=phone.getText().toString().trim();




        if(TextUtils.isEmpty(EmailID)){
            emailid.setError("Userid is required");
            return;
        }
        if(TextUtils.isEmpty(Password)){
            password.setError("password is required");
            return;
        }

        if(TextUtils.isEmpty(fname)){
            Fullname.setError("Fullanme  is required");
            return;
        }
        if(password.length()<8){
            password.setError("password must be 8 charcters long");
            return;

        }


        fAuth.createUserWithEmailAndPassword(EmailID,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {



                if(task.isSuccessful()){
                    userdb.setFullName(Fullname.getText().toString().trim());
                    userdb.setNumber(phone.getText().toString().trim());
                    userdb.setEmail(emailid.getText().toString().trim());
                    userdb.setUid(fAuth.getUid());
                    databaseReference.push().setValue(userdb);

                    Toast.makeText(Signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                }
                else{
                    Toast.makeText(Signup.this, "Error Signing Up" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                uploadImage();
            }
        });


    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgdisplay.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                            userdb.setProfilrimg(downloadUri.toString());

                            Toast.makeText(Signup.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Signup.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }




    }


    }




