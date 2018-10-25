package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class TopicService {
    @Autowired
    UserService userService;
    @GetMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic")
    public List<Topic> findTopicsForLessonId(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @PathVariable("lessonId") int lessonId) {
        User user =  userService.findUserById(userId);
        for(Course course: user.getCourses()) {
            if(course.getId() == courseId) {
                for(Module module : course.getModules()) {
                    if(module.getId() == moduleId) {
                        for(Lesson lesson : module.getLessons()) {
                            if(lesson.getId() == lessonId) {
                                return lesson.getTopics();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
