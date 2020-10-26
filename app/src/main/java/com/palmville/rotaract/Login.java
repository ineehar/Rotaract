package com.palmville.rotaract;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class Login extends AppCompatActivity {

    ImageView Backbutton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    Button btn;
    EditText username;
    EditText password;
    TextView registration;
    TextView forgotusername;
    TextView Forgotpasswd;
    String TAG;
    String un;
    String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TAG = "LOGIN";
        FirebaseAuth.getInstance().signOut();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){

//            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            Intent intent;
//            if (pref.getString("name","")!="") {
//                intent = new Intent(getApplicationContext(), MainDashboard.class);
//            }
//            else {
//                intent = new Intent(getApplicationContext(), Name.class);
//            }
            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        btn = findViewById(R.id.button);
        username = findViewById(R.id.username);
        password = findViewById(R.id.passwd);
        registration = findViewById(R.id.register);
        Forgotpasswd = findViewById(R.id.forgotpasswd);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Registration.class);
                startActivity(intent);
                finish();

            }
        });

        // need to change
        Forgotpasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                un=username.getText().toString().trim();
                mPassword =password.getText().toString().trim();
                userLogin();
            }
        });


    }


    private void userLogin() {
        initFirestore();
        if (TextUtils.isEmpty(un)) {
            Toast.makeText(this, "Please enter email id or username", Toast.LENGTH_SHORT).show();
            username.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(un).matches()){
            db.collection("users")
                    .whereEqualTo("Username", un)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String em= (String) document.getData().get("Email");
                                    Log.d(TAG, document.getId() + " => " + document.getData().get("Email"));
                                    signIn(em, mPassword);
                                }
                            } else {
                                Log.d("LOGIN UNSUCCESSFUL", "Incorrect username");
                                Toast.makeText(Login.this, "Incorrect login credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            return;
        }
        if (TextUtils.isEmpty(mPassword)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        }
        else {
            signIn(un, mPassword);
        }
    }

    private void initFirestore() {
        db = FirebaseFirestore.getInstance();
    }

    private void signIn(String un, String mPassword) {
        mAuth.signInWithEmailAndPassword(un, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG,"SignInWithEmail:Success",task.getException());
                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                            //String uid = mAuth.getCurrentUser().getUid();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
