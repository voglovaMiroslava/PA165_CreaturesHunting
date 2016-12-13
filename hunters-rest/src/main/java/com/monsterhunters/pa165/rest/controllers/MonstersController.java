package com.monsterhunters.pa165.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monsterhunters.pa165.dto.MonsterCreateDTO;
import com.monsterhunters.pa165.dto.MonsterDTO;
import com.monsterhunters.pa165.facade.MonsterFacade;
import com.monsterhunters.pa165.rest.exceptions.ResourceNotFoundException;
import com.monsterhunters.pa165.rest.tools.ApiUris;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Miroslava Voglova
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_MONSTERS)
public class MonstersController {

    private final static Logger LOG = LoggerFactory.getLogger(MonstersController.class);

    @Autowired
    private MonsterFacade monsterFacade;

    /**
     *
     * @return list of MonsterDTO
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public final Collection<MonsterDTO> getMonsters() throws JsonProcessingException {
        LOG.debug("rest getting all monsters");
        
        return monsterFacade.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public final MonsterDTO getMonster(@PathVariable("id") long id) throws Exception {
        LOG.debug("rest getting monster");
        
        try {
            return monsterFacade.findById(id);
        } catch (Exception e) {
            LOG.error("Exception was thrown while getting monster with id " + id, e);
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public final void deleteMonster(@PathVariable("id") long id) throws Exception {
        LOG.debug("rest deleting monster");
        
        try {
            monsterFacade.deleteMonster(id);
        } catch (Exception e) {
            LOG.error("Exception was thrown while deleting monster with id " + id, e);
            throw new ResourceNotFoundException();
        }
    }
    
    /**
     * Create new monster by POST
     * curl -X POST http://localhost:8080/rest/monters/
     * -i -H "Content-Type: application/json" --data
     * '{"name":"monter-name","height":"20","weight":"10","power":"200","locationId": {},
     * "types":["DRAGON"]}'
     *
     * @param monster
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final MonsterDTO creareMonster(@RequestBody MonsterCreateDTO monster) throws Exception {
        LOG.debug("rest creating monster");

        try {
            Long id = monsterFacade.createMonster(monster);
            return monsterFacade.findById(id);
        } catch (Exception e) {
            LOG.error("Exception was thrown while creating monster with id " + monster, e);
            throw new ResourceNotFoundException();
        }
    }
}
