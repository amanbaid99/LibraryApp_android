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
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static android.view.View.VISIBLE;

public class Signup extends AppCompatActivity {
    EditText  Fullname, phone, emailid, password;
    Button  Signupbtn;
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
        imgdisplay = (ImageView) findViewById(R.id.imgselectdisplay);
        userdb=new UserDB();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        {
            emailid.setText("email.com");

            Fullname.setText("email");

            password.setText("email.com");

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
        final String number=phone.getText().toString().trim();




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
        progressBar.setVisibility(VISIBLE);


        fAuth.createUserWithEmailAndPassword(EmailID,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {



                if(task.isSuccessful()){


                    userdb.setFullName(Fullname.getText().toString().trim());
                    userdb.setNumber(phone.getText().toString().trim());
                    userdb.setEmail(emailid.getText().toString().trim());
                    userdb.setUid(fAuth.getUid());

                    String uid=fAuth.getUid();
                    databaseReference.child(uid).child("userinfo").setValue(userdb);



                    Toast.makeText(Signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                }
                else{
                    Toast.makeText(Signup.this, "Error Signing Up" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });


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




    }




