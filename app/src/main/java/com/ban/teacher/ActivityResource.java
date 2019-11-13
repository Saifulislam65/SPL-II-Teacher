package com.ban.teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ActivityResource extends AppCompatActivity {
    EditText resourceTitle, resourceLink;
    Button resourceShare;
    DatabaseReference resource;
    String resourcePath;
    ArrayList<ListResource> resourceList;
    RecyclerView recyclerView;
    AdapterSearchAssignment adapterSearchAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
    }
}
