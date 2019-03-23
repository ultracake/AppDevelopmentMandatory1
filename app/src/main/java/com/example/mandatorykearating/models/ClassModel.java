package com.example.mandatorykearating.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassModel implements Parcelable
{
    private String name;
    private String teacher;

    private double subjectRelevans;
    private double teacherOverallPerformance;
    private double teacherPreparation;
    private double amountOfFeedback;
    private double howGoodTheirEx;
    private double jobOpportunities;

    public ClassModel(String name, String teacher, double subjectRelevans, double teacherOverallPerformance, double teacherPreparation, double amountOfFeedback, double howGoodTheirEx, double jobOpportunities) {
        this.name = name;
        this.teacher = teacher;
        this.subjectRelevans = subjectRelevans;
        this.teacherOverallPerformance = teacherOverallPerformance;
        this.teacherPreparation = teacherPreparation;
        this.amountOfFeedback = amountOfFeedback;
        this.howGoodTheirEx = howGoodTheirEx;
        this.jobOpportunities = jobOpportunities;
    }

    //for testing
    public ClassModel(String name, String teacher)
    {
        this.name = name;
        this.teacher = teacher;
        this.subjectRelevans = 5;
        teacherOverallPerformance = 3;
        teacherPreparation = 3;
        amountOfFeedback = 2;
        howGoodTheirEx = 1;
        jobOpportunities = 3;
    }

    protected ClassModel(Parcel in)
    {
        name = in.readString();
        teacher = in.readString();
        subjectRelevans = in.readDouble();
        teacherOverallPerformance = in.readDouble();
        teacherPreparation = in.readDouble();
        amountOfFeedback = in.readDouble();
        howGoodTheirEx = in.readDouble();
        jobOpportunities = in.readDouble();
    }

    public static final Creator<ClassModel> CREATOR = new Creator<ClassModel>()
    {
        @Override
        public ClassModel createFromParcel(Parcel in) {
            return new ClassModel(in);
        }

        @Override
        public ClassModel[] newArray(int size) {
            return new ClassModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public double getSubjectRelevans() {
        return subjectRelevans;
    }

    public void setSubjectRelevans(double subjectRelevans) {
        this.subjectRelevans = subjectRelevans;
    }

    public double getTeacherOverallPerformance() {
        return teacherOverallPerformance;
    }

    public void setTeacherOverallPerformance(double teacherOverallPerformance) {
        this.teacherOverallPerformance = teacherOverallPerformance;
    }

    public double getTeacherPreparation() {
        return teacherPreparation;
    }

    public void setTeacherPreparation(double teacherPreparation) {
        this.teacherPreparation = teacherPreparation;
    }

    public double getAmountOfFeedback() {
        return amountOfFeedback;
    }

    public void setAmountOfFeedback(double amountOfFeedback) {
        this.amountOfFeedback = amountOfFeedback;
    }

    public double getHowGoodTheirEx() {
        return howGoodTheirEx;
    }

    public void setHowGoodTheirEx(double howGoodTheirEx) {
        this.howGoodTheirEx = howGoodTheirEx;
    }

    public double getJobOpportunities() {
        return jobOpportunities;
    }

    public void setJobOpportunities(double jobOpportunities) {
        this.jobOpportunities = jobOpportunities;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeString(teacher);
        dest.writeDouble(subjectRelevans);
        dest.writeDouble(teacherOverallPerformance);
        dest.writeDouble(teacherPreparation);
        dest.writeDouble(amountOfFeedback);
        dest.writeDouble(howGoodTheirEx);
        dest.writeDouble(jobOpportunities);
    }
}
