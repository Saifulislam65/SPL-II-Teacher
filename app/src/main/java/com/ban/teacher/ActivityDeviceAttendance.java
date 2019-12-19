package com.ban.teacher;

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

    Button device;
    EditText deviceSecret;
    int colorCounter = 0;
    DatabaseReference deviceReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_attendance);

        device = findViewById(R.id.device_attendance_button);
        deviceSecret = findViewById(R.id.device_secret);

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
