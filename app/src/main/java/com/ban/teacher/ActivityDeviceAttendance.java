package com.ban.teacher;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActivityDeviceAttendance extends AppCompatActivity {

    Button device, QR;
    TextView studentCount;
    EditText deviceSecret;
    String code;
    Thread thread;
    int colorCounter = 0;
    DatabaseReference deviceReference;
    DbHandlerAttendanceInitialization  initialization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_attendance);

        device = findViewById(R.id.device_attendance_button);
        deviceSecret = findViewById(R.id.device_secret);
        studentCount = findViewById(R.id.student_count);
        initialization = new DbHandlerAttendanceInitialization();
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
                code = data.getStringExtra("SCAN_RESULT");
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
            DbHandlerAttendanceInitialization initialization = new DbHandlerAttendanceInitialization();
            initialization.initialization();
            deviceReference.child(secret).child("courseKey").setValue(ActivityInsideCourse.courseCodeForQrGenerator);
            deviceReference.child(secret).child("mode").setValue("0");
            nonstopCount();
        }else {
            Toast.makeText(getApplicationContext(), "Invalid Secret Key!",Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String secret = deviceSecret.getText().toString();
        System.out.println("Secret: "+secret);
        if(secret!= null){
            deviceReference.child(secret).child("courseKey").setValue("NoCourseFound");
            deviceReference.child(secret).child("mode").setValue("2");
        }
        try{
            thread.interrupt();
        }catch (Exception e){
            //finish();
        }

    }

    public void nonstopCount(){
        thread = new Thread() {
            @Override
            public void run() {
                int i = 0;
                try {
                    while(true) {
                        sleep(2000);
                        String count  = Integer.toString(initialization.attendanceCount(returnDate()));
                        studentCount.setText(count);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
    public String returnDate(){
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");//formating according to my need
        String date = formatter.format(today);
        return date;
    }
}
