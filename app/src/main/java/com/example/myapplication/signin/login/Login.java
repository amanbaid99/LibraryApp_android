package com.example.myapplication.signin.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.User.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText emailid,password;
    Button loginbtn;
    TextView signupbtn,adminlgnbtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    public  String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailid=(EditText)findViewById(R.id.EmailID );
        password=(EditText)findViewById(R.id.Password );
        fAuth= FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        loginbtn=(Button)findViewById(R.id.LoginBtn);
        signupbtn=(TextView)findViewById(R.id.textlgnNavBtn) ;
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSignupPage();
            }
        });
        emailid.setText("amanbaid99@gmail.com");
        password.setText("amanbaid99");
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String EmailID = emailid.getText().toString().toLowerCase().trim();
                String Password = password.getText().toString().trim();

                if (TextUtils.isEmpty(EmailID)) {
                    emailid.setError("Userid is required");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    password.setError("password is required");
                    return;
                }
                if (password.length() < 8) {
                    password.setError("password must be 8 charcters long");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                    fAuth.signInWithEmailAndPassword(EmailID, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                             uid = fAuth.getUid();
                             FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                         if (task.isSuccessful()) {
                             Intent j = new Intent(getApplicationContext(), MainActivity.class);
                             j.putExtra("ID",uid);
                             startActivity(j);
                             Toast.makeText(Login.this, ""+user, Toast.LENGTH_SHORT).show();

//                             Toast.makeText(Login.this, "Login Successfull"+uid, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, "Error Logging In" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                        }
                    });
            }
        });
    }
    public void OpenSignupPage(){
        Intent snppg=new Intent(getApplicationContext(),Signup.class);
        startActivity(snppg);
    }

}
