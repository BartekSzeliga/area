package com.dsv.example.demo.area.core.ports.incoming;

import com.dsv.example.demo.area.core.model.DeleteAreaCommand;
import com.dsv.example.demo.area.core.model.DeletePageAreasCommand;

public interface DeleteArea {
    void handle(DeleteAreaCommand deleteAreaCommand);

    void handle(DeletePageAreasCommand deletePageAreasCommand);
}
