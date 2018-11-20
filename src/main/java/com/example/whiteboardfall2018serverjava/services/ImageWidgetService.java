package com.example.whiteboardfall2018serverjava.services;

import com.example.whiteboardfall2018serverjava.models.ImageWidget;
import com.example.whiteboardfall2018serverjava.models.Topic;
import com.example.whiteboardfall2018serverjava.models.Widget;
import com.example.whiteboardfall2018serverjava.repositories.ImageWidgetRepository;
import com.example.whiteboardfall2018serverjava.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins="*")
public class ImageWidgetService {
    @Autowired
    TopicRepository tr;
    @Autowired
    ImageWidgetRepository ir;
    @Autowired
    WidgetService widgetService;
    @Autowired
    TopicService topicService;

    @PostMapping("/api/topic/{topicId}/image/widget")
    public List<Widget> createImageWidget(
            @PathVariable("topicId") int topicId,
            @RequestBody ImageWidget imageWidget) {
        imageWidget.setType("IMAGE");
        Topic topic = tr.findById(topicId).get();
        imageWidget.setTopic(topic);
        ir.save(imageWidget);
        return tr.findById(topicId).get().getWidgets();
    }

    @GetMapping("/api/topic/{topicId}/image/widget")
    public List<Widget> findAllImageWidget(
            @PathVariable("topicId") int topicId){
        return tr.findById(topicId).get().getWidgets().stream()
                .filter(w -> w.getType().equals("IMAGE")).collect(Collectors.toList());
    }

    @GetMapping("/api/topic/{topicId}/image/widget/{widgetId}")
    public Widget findImageWidgetById(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId) {
//        tr.findById(topicId).get().getWidgets().stream().filter(w -> w.getType().equals("LIST")).collect(Collectors.toList());
        return tr.findById(topicId).get().getWidgets()
                .stream().filter(w -> w.getId() == widgetId).findAny().orElse(null);
    }
    @PutMapping("/api/topic/{topicId}/image/widget/{widgetId}")
    public List<Widget> updateImageWidget(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId,
            @RequestBody ImageWidget imageWidget){

//        Widget widgetToUpdate = findImageWidgetById(topicId, widgetId);
//        widgetToUpdate.setTitle(widget.getTitle());
//        widgetToUpdate.setType(widget.getType());
//        widgetService.updateWidgetRepo(widgetToUpdate, widget);
        ImageWidget imageWidgetToUpdate = ir.findById(widgetId).get();
        imageWidgetToUpdate.setSrc(imageWidget.getSrc());
        imageWidgetToUpdate.setTitle(imageWidget.getTitle());
        return topicService.findWidgetForTopic(topicId);
    }


    @DeleteMapping("/api/topic/{topicId}/image/widget/{widgetId}")
    public List<Widget> deleteImageWidget(
            @PathVariable("topicId") int topicId,
            @PathVariable("widgetId") int widgetId) {
        ir.deleteById(widgetId);
        return topicService.findWidgetForTopic(topicId);
    }
}