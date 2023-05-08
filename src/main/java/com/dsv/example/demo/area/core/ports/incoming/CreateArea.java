package com.dsv.example.demo.area.core.ports.incoming;

import com.dsv.example.demo.area.core.model.CreateAreaCommand;

public interface CreateArea {
    void handle(CreateAreaCommand createAreaCommand);
}
