package com.example.whiteboardfall2018serverjava.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.whiteboardfall2018serverjava.models.ListWidget;
import com.example.whiteboardfall2018serverjava.models.Topic;
import com.example.whiteboardfall2018serverjava.models.Widget;
import com.example.whiteboardfall2018serverjava.repositories.ListWidgetRepository;
import com.example.whiteboardfall2018serverjava.repositories.TopicRepository;

@RestController
@CrossOrigin(origins="*")
public class ListWidgetService {
    @Autowired
    TopicRepository tr;
    @Autowired
    ListWidgetRepository listWidgetRepository;
    @PostMapping("/api/topic/{topicId}/widget/list")
    public List<Widget> createListWidget(
            @PathVariable("topicId") int topicId,
            @RequestBody ListWidget listWidget) {
        listWidget.setWidgetType("LIST");
        Topic topic = tr.findById(topicId).get();
        listWidget.setTopic(topic);
        listWidget = listWidgetRepository.save(listWidget);
        return tr.findById(topicId).get().getWidgets();
    }
}