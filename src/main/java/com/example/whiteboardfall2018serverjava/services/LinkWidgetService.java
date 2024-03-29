package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.LinkWidget;
import com.example.whiteboardfall2018serverjava.models.Topic;
import com.example.whiteboardfall2018serverjava.models.Widget;
import com.example.whiteboardfall2018serverjava.repositories.LinkWidgetRepository;
import com.example.whiteboardfall2018serverjava.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins="*")
public class LinkWidgetService {
    @Autowired
    TopicRepository tr;
    @Autowired
    LinkWidgetRepository lr;
    @Autowired
    WidgetService widgetService;
    @Autowired
    TopicService topicService;

    @PostMapping("/api/topic/{topicId}/link/widget")
    public List<Widget> createLinkWidget(
            @PathVariable("topicId") int topicId,
            @RequestBody LinkWidget linkWidget) {
        linkWidget.setType("LINK");
        Topic topic = tr.findById(topicId).get();
        linkWidget.setTopic(topic);
        lr.save(linkWidget);
        return tr.findById(topicId).get().getWidgets();
    }

    @GetMapping("/api/topic/{topicId}/link/widget")
    public List<Widget> findAllLinkWidget(
            @PathVariable("topicId") int topicId){
        return tr.findById(topicId).get().getWidgets().stream()
                .filter(w -> w.getType().equals("LINK")).collect(Collectors.toList());
    }

    @GetMapping("/api/topic/{topicId}/link/widget/{widgetId}")
    public Widget findLinkWidgetById(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId) {
//        tr.findById(topicId).get().getWidgets().stream().filter(w -> w.getType().equals("LIST")).collect(Collectors.toList());
        return tr.findById(topicId).get().getWidgets()
                .stream().filter(w -> w.getId() == widgetId).findAny().orElse(null);
    }
    @PutMapping("/api/topic/{topicId}/link/widget/{widgetId}")
    public List<Widget> updateLinkWidget(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId,
            @RequestBody LinkWidget linkWidget){
//        Widget widgetToUpdate = findLinkWidgetById(topicId, widgetId);
//        widgetToUpdate.setTitle(widget.getTitle());
//        widgetToUpdate.setType(widget.getType());
//        widgetService.updateWidgetRepo(widgetToUpdate, widget);
        LinkWidget linkWidgetToUpdate = lr.findById(widgetId).get();
        linkWidgetToUpdate.setHref(linkWidget.getHref());
        linkWidgetToUpdate.setTitle(linkWidget.getTitle());
        lr.save(linkWidgetToUpdate);
        return topicService.findWidgetForTopic(topicId);
    }

    @DeleteMapping("/api/topic/{topicId}/link/widget/{widgetId}")
    public List<Widget> deleteLinkWidget(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId) {
        lr.deleteById(widgetId);
        return topicService.findWidgetForTopic(topicId);
    }
}