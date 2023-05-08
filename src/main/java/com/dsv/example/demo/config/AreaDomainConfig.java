package com.dsv.example.demo.config;

import com.dsv.example.demo.area.core.AreaFacade;
import com.dsv.example.demo.area.core.ports.incoming.CreateArea;
import com.dsv.example.demo.area.core.ports.incoming.DeleteArea;
import com.dsv.example.demo.area.core.ports.incoming.QueryArea;
import com.dsv.example.demo.area.core.ports.incoming.UpdateAreaLink;
import com.dsv.example.demo.area.core.ports.outgoing.LeafletAreaDatabase;
import com.dsv.example.demo.area.infrastructure.LeafletAreaDatabaseAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

public class AreaDomainConfig {

    @Bean
    public LeafletAreaDatabase leafletAreaDatabase(JdbcTemplate jdbcTemplate) {
        return new LeafletAreaDatabaseAdapter(jdbcTemplate);
    }

    @Bean
    @Qualifier("CreateArea")
    public CreateArea createArea(LeafletAreaDatabase database) {
        return new AreaFacade(database);
    }

    @Bean
    @Qualifier("QueryArea")
    public QueryArea queryArea(LeafletAreaDatabase database) {
        return new AreaFacade(database);
    }

    @Bean
    @Qualifier("UpdateAreaLink")
    public UpdateAreaLink updateAreaLink(LeafletAreaDatabase database) {
        return new AreaFacade(database);
    }

    @Bean
    @Qualifier("DeleteArea")
    public DeleteArea deleteArea(LeafletAreaDatabase database) {
        return new AreaFacade(database);
    }
}
