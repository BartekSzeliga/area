package com.dsv.example.demo.area.core.ports.incoming;

import com.dsv.example.demo.area.core.model.UpdateAreaLinkCommand;

public interface UpdateAreaLink {
    void handle(UpdateAreaLinkCommand updateAreaLinkCommand);
}
