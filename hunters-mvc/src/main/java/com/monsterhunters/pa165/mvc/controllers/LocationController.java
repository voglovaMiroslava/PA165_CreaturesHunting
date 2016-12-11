package com.monsterhunters.pa165.mvc.controllers;

import com.monsterhunters.pa165.dto.CommentCreateDTO;
import com.monsterhunters.pa165.dto.LocationCreateDTO;
import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.dto.UserDTO;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.facade.CommentFacade;
import com.monsterhunters.pa165.facade.LocationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Tomas Durcak
 *
 * @author Tomas Durcak
 */
@Controller
@RequestMapping("/location")
public class LocationController {

    final static Logger log = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationFacade locationFacade;

    @Autowired
    private CommentFacade commentFacade;

    /**
     * Load page with list of all locations. Also display buttons to add, delete
     * or view specific location
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("list of locations");
        model.addAttribute("locations", locationFacade.getAllLocations());
        return "location/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        log.debug("view({})", id);
        model.addAttribute("location", locationFacade.getLocationById(id));
//        model.addAllAttributes("comments", locationFacade.getLocationById(id).getComments());
        return "location/view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes, HttpServletRequest request) {
//        UserDTO user = (UserDTO) request.getSession().getAttribute("authenticatedUser");
//        if (user.isAdmin() == false) {
//            return "home/404";
//        }

        LocationDTO location = locationFacade.getLocationById(id);
        locationFacade.deleteLocation(id);
        log.debug("delete({})", id);
        redirectAttributes.addFlashAttribute("alert_success", "Location \"" + location.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/location/list").toUriString();
    }

    @RequestMapping(value = "/{locationId}/comment/delete/{commentId}", method = RequestMethod.POST)
    public String deleteComment(@PathVariable long locationId, @PathVariable long commentId, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        locationFacade.removeComment(locationId, commentId);
        return "redirect:" + uriBuilder.path("/location/view/"+ locationId).toUriString();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editLocation(@PathVariable long id, Model model) {
        log.debug("editLocation()");
        LocationDTO location = locationFacade.getLocationById(id);
        log.debug("locationDTO with id after locationFacede.getLocationById(id)", location.getId());
        log.debug(location.getId().toString());
        model.addAttribute("locationUpdate", location);
        return "location/edit";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String create(@PathVariable long id, @Valid @ModelAttribute("locationUpdate") LocationDTO formBean, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("update(locationUpdate={})", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "location/edit";
        }
        //create product
        id = locationFacade.updateLocation(formBean);
        log.debug("id of updated location", id);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Location " + id + " was updated");
        return "redirect:" + uriBuilder.path("/location/view/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newLocation(Model model) {
        log.debug("new()");
        model.addAttribute("locationCreate", new LocationCreateDTO());
        return "location/new";
    }

    @RequestMapping(value = "/{id}/comment/new", method = RequestMethod.GET)
    public String newComment(@PathVariable long id, Model model){
        log.debug("newComment()");
        model.addAttribute("commentCreate", new CommentCreateDTO());
        model.addAttribute("locationId",id);
        //TODO change to get user id from loged user
        model.addAttribute("userId",1L);
        return "/location/comment/new";
    }
    
    @RequestMapping(value = "/{id}/comment/create", method = RequestMethod.POST)
    public String createComment(@PathVariable long id, @Valid @ModelAttribute("commentCreate") CommentCreateDTO formBean, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(commentCreate={})", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            model.addAttribute("locationId", id);
            //TODO also change automatic user id from authenticated user
            model.addAttribute("userId",1L);
            return "/location/comment/new";
        }
        //create product
        Long cId = commentFacade.createComment(formBean);
        locationFacade.addComment(id, cId);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Comment " + cId + " was created");
        return "redirect:" + uriBuilder.path("/location/view/"+ id).buildAndExpand(cId).encode().toUriString();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("locationCreate") LocationCreateDTO formBean, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(locationCreate={})", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
//            model.addAttribute("monsterTypes", MonsterType.values());
            return "location/new";
        }
        //create product
        Long id = locationFacade.createLocation(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Location " + id + " was created");
        return "redirect:" + uriBuilder.path("/location/view/{id}").buildAndExpand(id).encode().toUriString();
    }

}
