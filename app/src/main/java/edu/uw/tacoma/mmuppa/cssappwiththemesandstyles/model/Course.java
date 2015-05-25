package edu.uw.tacoma.mmuppa.cssappwiththemesandstyles.model;

import java.io.Serializable;

/**
 * Created by mmuppa on 4/13/15.
 */
public class Course implements Serializable {

    private String mCourseId;
    private String mShortDescription;
    private String mLongDescription;
    private String mPrereqs;

    public static final String ID = "id", SHORT_DESC = "shortDesc"
            , LONG_DESC = "longDesc", PRE_REQS = "prereqs";


    public Course(String courseId, String shortDescription
            , String longDescription, String prereqs) {
        setCourseId(courseId);
        setShortDescription(shortDescription);
        setLongDescription(longDescription);
        mPrereqs = prereqs;
    }

    public String getCourseId() {
        return mCourseId;
    }

    public void setCourseId(String courseId) {
        if (courseId == null)
            throw new IllegalArgumentException("Course Id must be supplied");
        if (courseId.length() < 5 )
            throw new IllegalArgumentException("Course Id must be at least 5 characters long");
        mCourseId = courseId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        if (shortDescription == null)
            throw new IllegalArgumentException(
                    "Course short description must be supplied");
        if (shortDescription.length() < 5)
            throw new IllegalArgumentException(
                    "Course short description must be at least 5 characters long");
        mShortDescription = shortDescription;
    }

    public String getLongDescription() {
        return mLongDescription;
    }

    public void setLongDescription(String longDescription) {
        if (longDescription == null)
            throw new IllegalArgumentException("Course long description must be supplied");
        if (longDescription.length() < 5)
            throw new IllegalArgumentException(
                    "Course long description must be at least 5 characters long");

        mLongDescription = longDescription;
    }

    public String getPrereqs() {
        return mPrereqs;
    }

    public void setPrereqs(String prereqs) {
        mPrereqs = prereqs;
    }

    @Override
    public String toString() {
        return "edu.uw.tacoma.mmuppa.cssappwithwebservices.model.Course{" +
                "mCourseId='" + mCourseId + '\'' +
                ", mShortDescription='" + mShortDescription + '\'' +
                ", mLongDescription='" + mLongDescription + '\'' +
                ", mPrereqs=" + mPrereqs +
                '}';
    }
}
