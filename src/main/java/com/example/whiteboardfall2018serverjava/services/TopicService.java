package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class TopicService {
    @Autowired
    UserService userService;
    @Autowired
    LessonService lessonService;
    @GetMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic")
    public List<Topic> findAllTopics(
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

    @PostMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic")
    public List<Topic> createTopic(@PathVariable("userId") int userId,
                                   @PathVariable("courseId") int courseId,
                                   @PathVariable("moduleId") int moduleId,
                                   @PathVariable("lessonId") int lessonId,
                                   @RequestBody Topic topic) {
        Lesson lesson = lessonService.findLessonById(userId, courseId, moduleId, lessonId);
        lesson.getTopics().add(topic);
        return lesson.getTopics();
    }

    @GetMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic/{topicId}")
    public Topic findTopicById(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @PathVariable("lessonId") int lessonId,
            @PathVariable("topicId") int topicId) {
        Lesson lesson = lessonService.findLessonById(userId, courseId, moduleId, lessonId);
        for(Topic topic : lesson.getTopics()) {
            if(topic.getId() == topicId)
                return topic;
        }
        return null;
    }

    @DeleteMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic/{topicId}")
    public List<Topic> deleteTopic(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @PathVariable("lessonId") int lessonId,
            @PathVariable("topicId") int topicId) {
        Lesson lesson = lessonService.findLessonById(userId, courseId, moduleId, lessonId);
        lesson.getTopics().removeIf((Topic topic) -> topic.getId() == topicId);
        return lesson.getTopics();
    }

    @PutMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic/{topicId}")
    public List<Topic> updateTopic(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @PathVariable("lessonId") int lessonId,
            @PathVariable("topicId") int topicId,
            @RequestBody Topic topic) {
        deleteTopic(userId, courseId, moduleId, lessonId, topicId);
        createTopic(userId, courseId, moduleId, lessonId, topic);
        return findAllTopics(userId, courseId, moduleId, lessonId);
    }
}