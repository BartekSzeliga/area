package com.dsv.example.demo.area.core


import com.dsv.example.demo.area.core.model.CreateAreaCommand
import com.dsv.example.demo.area.core.model.LeafletArea
import com.dsv.example.demo.area.core.model.LeafletId
import com.dsv.example.demo.area.core.ports.outgoing.LeafletAreaDatabase
import spock.lang.Specification

class AreaFacadeTest extends Specification {
    LeafletAreaDatabase database
    AreaFacade areaFacade

    def setup() {
        database = Mock()
        areaFacade = new AreaFacade(database)
    }

    def "should throw exception when leaflet does not exist"() {
        given: "prepare command"
        def leafletId = new LeafletId(5)
        def command = new CreateAreaCommand(1, 1, 1, 1, "link", leafletId, 4)

        and: "There is no leaflet"
        database.getLeaflet(leafletId.getId()) >> Optional.empty()

        when:
        areaFacade.handle(command)

        then:
        thrown IllegalArgumentException
    }

    def "should add area to database"() {
        given: "prepare command"
        def leafletId = new LeafletId(5)
        def command = new CreateAreaCommand(1, 1, 1, 1, "link", leafletId, 4)

        and: "There is leaflet"
        database.getLeaflet(leafletId.getId()) >> Optional.of(new LeafletArea(leafletId, 5))

        when: "Invoke command"
        areaFacade.handle(command)

        then: "Database save should be invoked 1 time"
        1 * database.save(command)
    }

    def "should throw exception when pageNumber is exceeded"() {
        given: "prepare command"
        def leafletId = new LeafletId(5)
        def command = new CreateAreaCommand(1, 1, 1, 1, "link", leafletId, 6)

        and: "There is leaflet"
        database.getLeaflet(leafletId.getId()) >> Optional.of(new LeafletArea(leafletId, 5))

        when: "Invoke command"
        areaFacade.handle(command)

        then: "Database save should be invoked 1 time"
        thrown IllegalArgumentException
    }

}
