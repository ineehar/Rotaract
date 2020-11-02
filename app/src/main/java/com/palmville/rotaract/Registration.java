package com.palmville.rotaract;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Registration extends AppCompatActivity {

    EditText donorName, donorContactNumber, donorEmailId, donorAge, donorBloodGroup, donorClub, donorPassword, donorConfirmPassword;
    Button btn_register;
    RadioButton recentDonorYes, recentDonorNo;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String recentCheck = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Casting Views
        donorName = (EditText) findViewById(R.id.name_of_donor);
        donorContactNumber = (EditText) findViewById(R.id.contact_of_donor);
        donorEmailId = (EditText) findViewById(R.id.email_of_donor);
        donorAge = (EditText) findViewById(R.id.age_of_donor);
        donorBloodGroup = (EditText) findViewById(R.id.bloodgroup_of_donor);
        donorClub = (EditText) findViewById(R.id.club_of_donor);
        donorPassword = (EditText) findViewById(R.id.passwd);
        donorConfirmPassword = (EditText) findViewById(R.id.confirm_pwd_registration);
        recentDonorYes = (RadioButton) findViewById(R.id.yes_radio_button);
        recentDonorNo = (RadioButton) findViewById(R.id.no_radio_button);
        btn_register = (Button) findViewById(R.id.btn_register);

        databaseReference = FirebaseDatabase.getInstance().getReference("Donor");
        firebaseAuth = FirebaseAuth.getInstance();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String dName = donorName.getText().toString();
                final String dEmail = donorEmailId.getText().toString();
                final int dAge = donorAge.getText();
                final int dContact= donorContactNumber.getText();
                final String dBloodGroup = donorBloodGroup.getText().toString();
                final String dClub = donorClub.getText().toString();
                String dPassword = donorPassword.getText().toString();
                String dConfirmPassword = donorConfirmPassword.getText().toString();

                if (recentDonorYes.isChecked()){
                    recentCheck = "Yes";

                }
                if (recentDonorNo.isChecked()){
                    recentCheck = "No";
                }

                if (TextUtils.isEmpty(dEmail)){
                    Toast.makeText(Registration.this, "Please Enter Email", Toast.LENGTH_SHORT);
                }
                if (TextUtils.isEmpty(dName)){
                    Toast.makeText(Registration.this, "Please Enter Full Name", Toast.LENGTH_SHORT);
                }

                if (TextUtils.isEmpty(dPassword)){
                    Toast.makeText(Registration.this, "Please Enter Password", Toast.LENGTH_SHORT);
                }

                if (TextUtils.isEmpty(dBloodGroup)){
                    Toast.makeText(Registration.this, "Please Enter Blood Group", Toast.LENGTH_SHORT);
                }

                if (TextUtils.isEmpty(dClub)){
                    Toast.makeText(Registration.this, "Please Enter Rotaract Club Name", Toast.LENGTH_SHORT);
                }
                if (TextUtils.isEmpty(dConfirmPassword)){
                    Toast.makeText(Registration.this, "Please Confirm password", Toast.LENGTH_SHORT);
                }
                if(dContact == null){
                    Toast.makeText(Registration.this, "Please Enter Contact Number", Toast.LENGTH_SHORT);
                }


                firebaseAuth.createUserWithEmailAndPassword(dEmail, dPassword)
                        .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Donor information = new Donor(
                                        dName,
                                        dEmail,
                                        dAge,
                                        dBloodGroup,
                                        dClub,
                                        recentCheck,
                                        dContact
                                    );

                                    FirebaseDatabase.getInstance().getReference("Donor")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Registration.this, "Registration Complete", Toast.LENGTH_SHORT);
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        }
                                    });

                                } else {
                                    // If sign in fails, display a message to the user.

                                }

                                // ...
                            }
                        });


            }
        });
    }
}
