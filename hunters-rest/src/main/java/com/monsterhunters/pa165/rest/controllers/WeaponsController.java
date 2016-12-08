package com.monsterhunters.pa165.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monsterhunters.pa165.dto.CommentDTO;
import com.monsterhunters.pa165.dto.MonsterDTO;
import com.monsterhunters.pa165.dto.WeaponCreateDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.facade.WeaponFacade;
import com.monsterhunters.pa165.rest.exceptions.ResourceAlreadyExistingException;
import com.monsterhunters.pa165.rest.exceptions.ResourceNotFoundException;
import com.monsterhunters.pa165.rest.tools.ApiUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.List;

/**
 * Created by babcang
 *
 * @author Babcan G
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_WEAPONS)
public class WeaponsController {
    final static Logger logger = LoggerFactory.getLogger(WeaponsController.class);

    @Autowired
    private WeaponFacade weaponFacade;

    /**
     * Get list of all Weapons GET
     * http://localhost:8080/rest/weapons
     *
     * @return list of WeaponDTO
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<WeaponDTO> getWeapons() throws JsonProcessingException {
        logger.debug("rest getWeapons()");
        return weaponFacade.getAllWeapons();
    }

    /**
     * Get weapon by id GET
     * http://localhost:8080/rest/weapons/1
     *
     * @param id is ID of weapon
     * @return WeaponDTO
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO getWeapon(@PathVariable("id") long id) throws Exception {
        try {
            return weaponFacade.getWeaponById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Delete weapon by id DELETE
     * http://localhost:8080/rest/weapons/1
     *
     * @param id of weapon
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteWeapon(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteWeapon({})", id);
        try {
            weaponFacade.deleteWeapon(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Create new weapon by POST
     * curl -X POST http://localhost:8080/rest/weapons/create
     * -i -H "Content-Type: application/json" --data
     * '{"name":"test","ammo":"20","damage":"10","gunReach":"200",
     * "effectiveAgainst":["DRAGON"]}'
     *
     * @param weapon
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO createWeapon(@RequestBody WeaponCreateDTO weapon) throws Exception {

        logger.debug("rest createWeapon()");

        try {
            Long id = weaponFacade.createWeapon(weapon);
            return weaponFacade.getWeaponById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    /**
     *
     * @param id
     * @param comment
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO addComment(@PathVariable("id") long id, @RequestBody CommentDTO comment) throws Exception {
        logger.debug("rest addComment({)", id);
        try {
            weaponFacade.addComment(id, comment.getId());
            return weaponFacade.getWeaponById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    /**
     *
     * @param id
     * @param commentId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/comments/{cId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO removeComment(@PathVariable(value="id") long id, @PathVariable(value="cId") long commentId) throws Exception {
        logger.debug("rest removeComment({)", id);
        try {
            weaponFacade.removeComment(id, commentId);
            return weaponFacade.getWeaponById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/killable", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<MonsterDTO> getKillableMonsters(@PathVariable("id") long id) throws Exception {
        try {
            return weaponFacade.getKillableMonsters(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }
}
