package com.ban.teacher;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityDeviceAttendance extends AppCompatActivity {

    Button device, QR;
    EditText deviceSecret;
    int colorCounter = 0;
    DatabaseReference deviceReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_attendance);

        device = findViewById(R.id.device_attendance_button);
        deviceSecret = findViewById(R.id.device_secret);
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
        if(secret != null){
            deviceReference.child(secret).child("courseKey").setValue(ActivityInsideCourse.courseCodeForQrGenerator);
            deviceReference.child(secret).child("mode").setValue("0");
        }else {
            Toast.makeText(getApplicationContext(), "Secret key is null!",Toast.LENGTH_LONG ).show();
        }
    }
}
