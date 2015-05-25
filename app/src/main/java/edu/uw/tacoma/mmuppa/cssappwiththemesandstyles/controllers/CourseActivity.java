package edu.uw.tacoma.mmuppa.cssappwiththemesandstyles.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uw.tacoma.mmuppa.cssappwiththemesandstyles.R;
import edu.uw.tacoma.mmuppa.cssappwiththemesandstyles.data.CourseDB;
import edu.uw.tacoma.mmuppa.cssappwiththemesandstyles.model.Course;


public class CourseActivity extends ActionBarActivity {

    private Course mCourse;

    private EditText mCourseIdEditText;
    private EditText mCourseShortDescriptionEditText;
    private EditText mCourseLongDescriptionEditText;
    private EditText mCoursePrereqsEditText;
    private Button mAddButton;

    private SharedPreferences mSharedPreferences;

    // Keys for the values in Shared Preferences
    private final static String COURSE_ID = "courseId";
    private final static String SHORT_DESC = "shortDesc";
    private final static String LONG_DESC = "longDesc";
    private final static String PRE_REQS = "prereqs";
    private final static String UNKNOWN = "";

    private CourseDB mCourseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // Display the Back Navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCourseDB = new CourseDB(this);

        mCourseIdEditText = (EditText) findViewById(R.id.course_id_edit_text);
        mCourseShortDescriptionEditText = (EditText) findViewById(R.id.course_short_desc_edit_text);
        mCourseLongDescriptionEditText = (EditText) findViewById(R.id.course_long_desc_edit_text);
        mCoursePrereqsEditText = (EditText) findViewById(R.id.course_prereqs_edit_text);
        mAddButton = (Button) findViewById(R.id.course_add_button);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseId = mCourseIdEditText.getText().toString();
                String shortDesc = mCourseShortDescriptionEditText.getText().toString();
                String longDesc = mCourseLongDescriptionEditText.getText().toString();
                String prereqs = mCoursePrereqsEditText.getText().toString();

                if (courseId.length() == 0 || shortDesc.length() == 0
                        || longDesc.length() == 0) {
                    Toast.makeText(v.getContext(), "Please enter all fields except for prereqs"
                            , Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (prereqs.length() == 0) prereqs = null;
                mCourse = new Course(courseId, shortDesc, longDesc, prereqs);

                try {
                    mCourseDB.insert(courseId, shortDesc, longDesc, prereqs);
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG)
                            .show();

                    return;
                }
                Toast.makeText(v.getContext(), "Course added successfully!", Toast.LENGTH_SHORT)
                        .show();

            }
        });
    }

    /**
     * Save the state when the back button or home button is pressed.
     */
    @Override
    protected void onPause() {
        super.onPause();

        String courseId = mCourseIdEditText.getText().toString();
        String shortDesc = mCourseShortDescriptionEditText.getText().toString();
        String longDesc = mCourseLongDescriptionEditText.getText().toString();
        String prereqs = mCoursePrereqsEditText.getText().toString();

        // Save the state
        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        Editor editor = mSharedPreferences.edit();
        editor.putString(COURSE_ID, courseId);
        editor.putString(SHORT_DESC, shortDesc);
        editor.putString(LONG_DESC, longDesc);
        editor.putString(PRE_REQS, prereqs);
        editor.commit();

    }

    /**
     * Restore the state when the app is in the foreground
     */
    @Override
    protected void onStart() {
        super.onStart();


        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);

        if (mSharedPreferences != null) {
            mCourseIdEditText.setText(mSharedPreferences.getString(COURSE_ID, UNKNOWN));
            mCourseShortDescriptionEditText.setText(mSharedPreferences.getString(SHORT_DESC, UNKNOWN));
            mCourseLongDescriptionEditText.setText(mSharedPreferences.getString(LONG_DESC, UNKNOWN));
            mCoursePrereqsEditText.setText(mSharedPreferences.getString(PRE_REQS, UNKNOWN));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCourseDB != null)
            mCourseDB.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Take back to Course List Activity
        if (id == android.R.id.home)  {
            if (NavUtils.getParentActivityName(this) != null) {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        else if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
