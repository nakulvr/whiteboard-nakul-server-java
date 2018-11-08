package com.example.whiteboardfall2018serverjava.repositories;

import com.example.whiteboardfall2018serverjava.models.Topic;
import com.example.whiteboardfall2018serverjava.models.Widget;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository
        extends CrudRepository<Topic, Integer> {
}
