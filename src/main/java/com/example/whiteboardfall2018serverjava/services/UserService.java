package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.*;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*" , allowCredentials = "true" , allowedHeaders = "*")
public class UserService {
    static List<User> users = new ArrayList<User>();
    static String[] usernames    = {"alice"};
    static String[] courseTitles = {"cs5200", "cs5610", "cs5500"};
    static String[] moduleTitles = {"Module 1", "Module 2"};
    static String[] lessonTitles = {"lesson 1", "lesson 2"};
    static String[] topicTitles = {"topic 1", "topic 2"};
    static String[] widgetTitles = {"widget 1", "widget 2"};

    {
        List<Widget> widgets = new ArrayList<Widget>();
        for (String widgetTitle : widgetTitles) {
            widgets.add(new Widget(widgetTitle));
        }

        List<Topic> topics = new ArrayList<Topic>();
        for(String topicTitle: topicTitles) {
            Topic topic = new Topic(topicTitle);
            if(topicTitle.equals("topic 1")) {
                topic.setWidgets(widgets);
            }
            topics.add(topic);
        }

        List<Lesson> lessons = new ArrayList<Lesson>();
        for(String lessonTitle : lessonTitles) {
            Lesson lesson = new Lesson(lessonTitle);
            if(lessonTitle.equals("lesson 1")) {
                lesson.setTopics(topics);
            }
            lessons.add(lesson);
        }

        List<Module> modules = new ArrayList<Module>();
        for(String moduleTitle : moduleTitles) {
            Module module = new Module(moduleTitle);
            if(moduleTitle.equals("Module 1")) {
                module.setLessons(lessons);
            }
            else if(moduleTitle.equals("Module 2")) {
                module.setLessons(lessons);
            }
            modules.add(module);
        }

        List<Course> courses = new ArrayList<Course>();
        for(String courseTitle : courseTitles) {
            Course course = new Course(courseTitle);
//            if(courseTitle.equals("cs5200")) {
                course.setModules(modules);
//            }
            courses.add(course);
        }
        for(String username: usernames) {
            User user = new User(username);
            if(username.equals("alice")) {
                user.setPassword("alice");
                user.setFirstName("Alice");
                user.setLastName("WonderLand");
                user.setCourses(courses);
            }
            users.add(user);
        }
    }

    @GetMapping("/api/user")
    public List<User> findAllUsers() {
        return users;
    }

//    @PostMapping("/api/user")
//    public List<User> createUser(@RequestBody User user) {
//        users.add(user);
//        return users;
//    }

    @GetMapping("/api/user/{userId}")
    public User findUserById(@PathVariable("userId") int userId) {
        for(User user : users) {
            if(user.getId() == userId)
                return user;
        }
        return null;
    }

    @PostMapping("/api/register")
    public User register(@RequestBody User user,
                         HttpSession session) {
        session.setAttribute("currentUser", user);
        users.add(user);
        return user;
    }

    @GetMapping("/api/profile")
    public User profile(HttpSession session) {
        User currentUser = (User)
                session.getAttribute("currentUser");
        return currentUser;
    }

    @PutMapping("/api/profile")
    public User profileUpdate(@RequestBody User loggedInUser,
                              HttpSession session) {
        for(User user : users) {
            if(user.getUsername().equals(loggedInUser.getUsername())) {
                user.setPassword(loggedInUser.getPassword());
                user.setFirstName(loggedInUser.getFirstName());
                user.setLastName(loggedInUser.getLastName());
                return user;
            }
        }
        return null;
    }

    @PostMapping("/api/logout")
    public void logout
            (HttpSession session) {
        session.invalidate();
    }
    @PostMapping("/api/login")
    public User login(@RequestBody User credentials,
                          HttpSession session) {
        for (User user : users) {
            if( user.getUsername().equals(credentials.getUsername())
                    && user.getPassword().equals(credentials.getPassword())) {
                session.setAttribute("currentUser", user);
                return user;
            }
        }
        return null;
    }
}
