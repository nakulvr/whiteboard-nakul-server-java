package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.Course;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")

public class CourseService {
    List<Course> courses = new ArrayList<Course>();
    @GetMapping("/api/course")
    public List<Course> findallCourses() {
        Course c1 = new Course(123, "cs5610");
        Course c2 = new Course(234, "cs5200");
        courses.add(c1);
        courses.add(c2);
        return courses;
    }
}
