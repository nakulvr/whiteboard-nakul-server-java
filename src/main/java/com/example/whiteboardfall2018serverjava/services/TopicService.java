package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.*;
import com.example.whiteboardfall2018serverjava.repositories.HeadingWidgetRepository;
import com.example.whiteboardfall2018serverjava.repositories.TopicRepository;
import com.example.whiteboardfall2018serverjava.repositories.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*", allowCredentials = "true" , allowedHeaders = "*")
public class TopicService {
    @Autowired
    UserService userService;
    @Autowired
    LessonService lessonService;
    @Autowired
    CourseService courseService;
    @Autowired
    HeadingWidgetRepository hr;
    @Autowired
    TopicRepository tr;

    @Autowired
    WidgetRepository wr;

    @GetMapping("/api/topic")
    public List<Topic> findallTopics(){
        return (List<Topic>) tr.findAll();
    }

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
    public List<Course> createTopic(@PathVariable("userId") int userId,
                                   @PathVariable("courseId") int courseId,
                                   @PathVariable("moduleId") int moduleId,
                                   @PathVariable("lessonId") int lessonId,
                                   @RequestBody Topic topic) {
        Lesson lesson = lessonService.findLessonById(userId, courseId, moduleId, lessonId);
        lesson.getTopics().add(topic);
        return courseService.findAllCourses(userId);
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
    public List<Course> deleteTopic(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @PathVariable("lessonId") int lessonId,
            @PathVariable("topicId") int topicId) {
        Lesson lesson = lessonService.findLessonById(userId, courseId, moduleId, lessonId);
        lesson.getTopics().removeIf((Topic topic) -> topic.getId() == topicId);
        return courseService.findAllCourses(userId);
    }

    @PutMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic/{topicId}")
    public List<Course> updateTopic(
            @PathVariable("userId") int userId,
            @PathVariable("courseId") int courseId,
            @PathVariable("moduleId") int moduleId,
            @PathVariable("lessonId") int lessonId,
            @PathVariable("topicId") int topicId,
            @RequestBody Topic topic) {
//        deleteTopic(userId, courseId, moduleId, lessonId, topicId);
//        createTopic(userId, courseId, moduleId, lessonId, topic);
        Topic topicToEdit = findTopicById(userId, courseId, moduleId, lessonId, topicId);
        topicToEdit.setWidgets(topic.getWidgets());
        topicToEdit.setTitle(topic.getTitle());
        return courseService.findAllCourses(userId);
    }

    @GetMapping("/api/topic/{topicId}/widget")
    public List<Widget> findWidgetForTopic(
            @PathVariable("topicId") int topicId) {
        return tr.findById(topicId).get().getWidgets();
    }

    @PostMapping("/api/topic/{topicId}/widget")
    public List<Widget> createWidget(
            @PathVariable("topicId") int topicId,
            @RequestBody Widget widget) {
        Topic topic = tr.findById(topicId).get();
        widget.setTopic(topic);
        widget.setType("HEADING");
        HeadingWidget headingWidget = new HeadingWidget();
        headingWidget.setTitle(widget.getTitle());
        headingWidget.setSize("1");
        headingWidget.setText("Heading");
        headingWidget.setTopic(widget.getTopic());
        headingWidget.setType(widget.getType());
        hr.save(headingWidget);
//        wr.save(widget);
        return tr.findById(topicId).get().getWidgets();
    }
}
