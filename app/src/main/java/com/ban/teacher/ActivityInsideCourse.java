package com.ban.teacher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


public class ActivityInsideCourse extends AppCompatActivity {
    ViewPager viewPager;
    private TextView mTextMessage;
    String parent, path;
    public static String coursePath, courseCodeForQrGenerator;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_features:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_attendance:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_student_profile:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_course);


        mTextMessage = (TextView) findViewById(R.id.message);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        coursePath = "Course/"+getPath();
        courseCodeForQrGenerator = getPath();


        viewPager = (ViewPager) findViewById(R.id.inside_course_viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentDashboard());
        adapter.AddFragment(new FragmentAttendance());
        adapter.AddFragment(new FragmentStudentsProfile());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(0).setChecked(false);

                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public String getPath(){
        Bundle extras = getIntent().getExtras();
        parent = extras.getString("STRING_I_NEED");
        path = String.valueOf(FragmentCourses.parentList.get(Integer.parseInt(parent)));
        return path;
    }

}
