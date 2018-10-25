package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.Course;
import com.example.whiteboardfall2018serverjava.models.Lesson;
import com.example.whiteboardfall2018serverjava.models.Module;
import com.example.whiteboardfall2018serverjava.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class LessonService {
    @Autowired
    UserService userService;
    @GetMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson")
    public List<Lesson> findLessonsForCourseId(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId) {
        User user = userService.findUserById(userId);
        for(Course course : user.getCourses()) {
            if(course.getId() == courseId) {
                for(Module module: course.getModules()) {
                    if(module.getId() == moduleId) {
                        return module.getLessons();
                    }
                }
            }
        }
        return null;
    }
}
