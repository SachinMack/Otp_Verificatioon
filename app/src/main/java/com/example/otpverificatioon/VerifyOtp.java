package com.example.otpverificatioon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;

public class VerifyOtp extends AppCompatActivity {
    PinView pinView;
    TextView number;
    String num="",otp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        number =(TextView) findViewById(R.id.tv_num);
        pinView = findViewById(R.id.pinview);
        num = getIntent().getStringExtra("number");
        otp = getIntent().getStringExtra("otp");
        number.setText(num);


    }

    public void verifyOtp(View view) {
        if (otp != null){
            if (otp.equals(pinView.getText().toString())){
                Toast.makeText(this, "Successfully verified!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Wrong OTP!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}