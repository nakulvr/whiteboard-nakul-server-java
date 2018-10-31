package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.Course;
import com.example.whiteboardfall2018serverjava.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true" , allowedHeaders = "*")

public class CourseService {
    @Autowired
    UserService userService;

    List<Course> courses = null;
    @GetMapping("/api/user/{userId}/course")
    public List<Course> findAllCourses(@PathVariable("userId") int userId) {
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

    @DeleteMapping("/api/user/{userId}/course/{courseId}")
    public List<Course> deleteCourse(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId) {
        User user = userService.findUserById(userId);
        Iterator iter = user.getCourses().iterator();
        while (iter.hasNext()) {
            Course course = (Course)iter.next();
            if(course == findCourseById(userId, courseId)) {
                iter.remove();
            }
        }
        return findAllCourses(userId);
    }

    @PutMapping("/api/user/{userId}/course/{courseId}")
    public List<Course> updateCourse(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @RequestBody Course course) {
        deleteCourse(userId, courseId);
        createCourse(userId, course);
//        User user = userService.findUserById(userId);
//        int i = 0;
//        for(Iterator<Course> it = user.getCourses().iterator(); it.hasNext(); i++) {
//            Course courseOld = it.next();
//            if(courseOld == findCourseById(userId, courseId)) {
//                courseNew.setId(courseOld.getId());
//                user.getCourses().set(i, courseNew);
//            }
//        }
        return findAllCourses(userId);
    }
}
