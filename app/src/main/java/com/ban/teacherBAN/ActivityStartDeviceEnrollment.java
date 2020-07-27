package com.ban.teacherBAN;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityStartDeviceEnrollment extends AppCompatActivity {
    Button device, QR;
    TextView deviceSecret;
    int colorCounter = 0;
    DatabaseReference deviceReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_device_enrollment);

        device = findViewById(R.id.device_attendance_button);
        deviceSecret = findViewById(R.id.device_secret);
        QR = findViewById(R.id.qr);

        QR = findViewById(R.id.qr);
        QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes
                    startActivityForResult(intent, 0);

                } catch (Exception e) {

                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);

                }
            }
        });

        deviceReference = FirebaseDatabase.getInstance().getReference("Device/");

        device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(colorCounter%2 == 1) {
                    device.setBackgroundResource(R.drawable.round_background_ash);
                    device.setText("Start");
                    colorCounter++;
                }else {
                    device.setBackgroundResource(R.drawable.round_background);
                    device.setText("Stop");
                    colorCounter++;
                    deviceOperation();
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String code = data.getStringExtra("SCAN_RESULT");
                deviceSecret.setText(code);
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }
    private void deviceOperation() {
        // ListDevice listDevice = new ListDevice(ActivityInsideCourse.courseCodeForQrGenerator);
        String secret = deviceSecret.getText().toString();
        if(secret.length()!= 0){
            deviceReference.child(secret).child("courseKey").setValue(ActivityInsideCourse.courseCodeForQrGenerator);
            deviceReference.child(secret).child("mode").setValue("1");
        }else {
            Toast.makeText(getApplicationContext(), "Secret key is null!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String secret = deviceSecret.getText().toString();
       if(secret != null){
           deviceReference.child(secret).child("courseKey").setValue("NoCourseFound");
           deviceReference.child(secret).child("mode").setValue("2");
       }
    }
}
