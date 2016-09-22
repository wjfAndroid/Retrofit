package com.wjf.test.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public class Student {
    public String name;
    public List<Course> mCourses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return mCourses;
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", mCourses=" + mCourses +
                '}';
    }

    public class Course {
        public String courseID;
        public String courseName;

        public String getCourseID() {
            return courseID;
        }

        public void setCourseID(String courseID) {
            this.courseID = courseID;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        @Override
        public String toString() {
            return "Course{" +
                    "courseID='" + courseID + '\'' +
                    ", courseName='" + courseName + '\'' +
                    '}';
        }
    }


}
