package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.Course;
import com.example.whiteboardfall2018serverjava.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")

public class CourseService {
    @Autowired
    UserService userService;

    List<Course> courses = null;
    @GetMapping("/api/user/{userId}/course")
    public List<Course> findallCourses(@PathVariable("userId") int userId) {
        User user = userService.findUserById(userId);
        return user.getCourses();
    }

    @PostMapping("/api/user/{userId}/course")
    public List<Course> createCourse(
            @PathVariable("userId") int userId,
            @RequestBody Course course) {
     User user = userService.findUserById(userId);
     user.getCourses().add(course);
     return user.getCourses();
    }

    @GetMapping("/api/user/{userId}/course/{courseId}")
    public Course findCourseById(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId) {

        User user = userService.findUserById(userId);
        if(user != null) {
            for(Course course: user.getCourses()) {
                if(course.getId() == courseId)
                    return course;
            }
        }
        return null;
    }
}
