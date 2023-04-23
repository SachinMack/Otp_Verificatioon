package com.example.otpverificatioon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.core.app.NotificationCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et_mobileNo;
    Dialog dialog;
    EditText et_otp;
    String last_two_digits="",first_two_digits="",message="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(0);
        et_mobileNo = findViewById(R.id.et_mobileNo);
        dialog= new Dialog(MainActivity.this);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getOtp(View view) {
        // Toast.makeText(this, "hiii", Toast.LENGTH_SHORT).show();
        if (et_mobileNo.getText().toString().isEmpty()) {
            et_mobileNo.setError("Can not be empty");
        } else {
            sendOtp();
        }
    }

    private void sendOtp() {
        String num = et_mobileNo.getText().toString();
        first_two_digits = num.substring(0, 2);
        last_two_digits = num.substring(8, 10);
         message = first_two_digits + last_two_digits;
        String title = "Your OTP";

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.baseline_arrow_back)
                        .setContentTitle(title)
                        .setContentText(message);
        mBuilder.setTicker(title + " " + message);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManager mNotificationManager = null;
        if (mNotificationManager != null) {
        } else {
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "default";
            NotificationChannel channel = new NotificationChannel(channelId, title, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(message);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setSound(defaultSoundUri, null);
            channel.enableVibration(true);
            channel.setShowBadge(true);

            mNotificationManager.createNotificationChannel(channel);
            Notification mNotification = new Notification.Builder(this, channelId)
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.baseline_arrow_back)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .build();
            mNotificationManager.notify(110101, mNotification);
        } else {
            Notification notification = mBuilder.build();
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            notification.defaults |= Notification.DEFAULT_SOUND;
            mNotificationManager.notify(110101, notification);
        }

        showOtpDialog();
    }

    private void showOtpDialog() {
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        et_otp = dialog.findViewById(R.id.et_otp);

        et_otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==4){
                   // Toast.makeText(MainActivity.this, ""+et_otp.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,VerifyOtp.class);
                    intent.putExtra("number",et_mobileNo.getText().toString());
                    intent.putExtra("otp",message);
                    startActivity(intent);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        dialog.show();

    }
}