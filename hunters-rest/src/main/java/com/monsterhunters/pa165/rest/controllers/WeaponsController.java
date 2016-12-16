package com.monsterhunters.pa165.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monsterhunters.pa165.dto.*;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.facade.CommentFacade;
import com.monsterhunters.pa165.facade.WeaponFacade;
import com.monsterhunters.pa165.rest.exceptions.ResourceAlreadyExistingException;
import com.monsterhunters.pa165.rest.exceptions.ResourceNotFoundException;
import com.monsterhunters.pa165.rest.exceptions.ResourceNotModifiedException;
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

    @Autowired
    private CommentFacade commentFacade;

    /**
     * Get list of all Weapons by
     * curl -i -X GET http://localhost:8080/pa165/rest/weapons
     * @return list of WeaponDTO
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<WeaponDTO> getWeapons() throws JsonProcessingException {
        logger.debug("rest getWeapons()");
        return weaponFacade.getAllWeapons();
    }

    /**
     * Get weapon by id
     * curl -i -X GET http://localhost:8080/pa165/rest/weapons/1
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
     * curl -i -X DELETE http://localhost:8080/pa165/rest/weapons/1
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
     * curl -X POST http://localhost:8080/pa165/rest/weapons/
     * -i -H "Content-Type: application/json" --data
     * '{"name":"test","ammo":"20","damage":"10","gunReach":"200",
     * "effectiveAgainst":["DRAGON"]}'
     *
     * @param weapon
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
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

    /** Add comment to list of comments by PUT method
     * curl -X POST http://localhost:8080/pa165/rest/weapons/2/comments/
     * -i -H "Content-Type: application/json" --data
     * '{"userId":"1","content":"Test unassigned comment."}'
     * @param id is id of weapon
     * @param comment is CommentDTO object
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO addComment(@PathVariable("id") long id, @RequestBody CommentCreateDTO comment) throws Exception {
        logger.debug("rest addComment({)", id);
        try {
            Long commentId = commentFacade.createComment(comment);
            weaponFacade.addComment(id, commentId);
            return weaponFacade.getWeaponById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    /** Remove comment from list of comments by DELETE method
     * curl -i -X DELETE http://localhost:8080/pa165/rest/weapons/2/comments/2
     * @param id is id of weapon
     * @param commentId is id of comment to be removed
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/comments/{cId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO removeComment(@PathVariable(value="id") long id, @PathVariable(value="cId") long commentId) throws Exception {
        logger.debug("rest removeComment({)", id);
        try {
            weaponFacade.removeComment(id, commentId);
            return weaponFacade.getWeaponById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    /** Get list of monsters which can be killed by the weapon by GET method
     * curl -i -X GET http://localhost:8080/pa165/rest/weapons/3/killable
     * @param id of weapon
     * @return list of monsters that can be killed by weapon
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

    /**
     * Add effective monster type - type that can be hurt by weapon, by PUT method
     * curl -X PUT -G 'http://localhost:8080/pa165/rest/weapons/2/types' -d 'monsterType=GROUND'
     * @param id is id of weapon
     * @param monsterType is monsterType to be added to list
     * @return WeaponDTO object with modified effectiveAgainst list
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/types", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO addEffectiveType(@PathVariable("id") long id, @RequestParam MonsterType monsterType) throws Exception {
        logger.debug("rest addType({)", id);
        try {
            weaponFacade.addEffectiveAgainst(id, monsterType);
            return weaponFacade.getWeaponById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    /**Remove effective monster type from list in weapon by DELETE method
     * curl -X DELETE -G 'http://localhost:8080/pa165/rest/weapons/2/types' -d 'monsterType=FLYING'
     * @param id is id of weapon
     * @param monsterType is monsterType to be deleted from list
     * @return WeaponDTO object with modified effectiveAgainst list
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/types", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO removeEffectiveType(@PathVariable("id") long id, @RequestParam MonsterType monsterType) throws Exception {
        logger.debug("rest addType({)", id);
        try {
            weaponFacade.removeEffectiveAgainst(id, monsterType);
            return weaponFacade.getWeaponById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    /**Update parameters o weapon by PUT method
     * curl -X PUT -i -H "Content-Type: application/json"
     * --data '{"name":"TEST UPDATE","ammo":"50","damage":"20","gunReach":"200",
     * "effectiveAgainst":["DRAGON"]}' http://localhost:8080/pa165/rest/weapons/3
     * @param id is id of weapon to be updated
     * @param editedWeapon is WeaponDTO object with modified parameters
     * @return updated WeaponDTO object
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final WeaponDTO updateWeapon(@PathVariable("id") long id, @RequestBody WeaponDTO editedWeapon) throws Exception {

        logger.debug("rest update weapon with id {}", id);

        try {
            weaponFacade.getWeaponById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }

        try{
            editedWeapon.setId(id);
            weaponFacade.updateWeapon(editedWeapon);
        }catch(Exception ex){
            throw new ResourceNotModifiedException();
        }

        return editedWeapon;
    }
}
