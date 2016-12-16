package com.monsterhunters.pa165.mvc.controllers;

import com.monsterhunters.pa165.dto.CommentCreateDTO;
import com.monsterhunters.pa165.dto.LocationCreateDTO;
import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.dto.UserDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;
import com.monsterhunters.pa165.facade.CommentFacade;
import com.monsterhunters.pa165.facade.LocationFacade;
import static com.monsterhunters.pa165.mvc.controllers.UserController.AUTHENTICATED_USER;
import java.util.Objects;
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
import org.springframework.orm.jpa.JpaSystemException;

/**
 * Created by Tomas Durcak
 *
 * @author Tomas Durcak
 */
@Controller
@RequestMapping("/location")
public class LocationController {

    final static Logger log = LoggerFactory.getLogger(LocationController.class);
//    public static final String AUTHENTICATED_USER = "authenticatedUser";

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
        try {
        model.addAttribute("locations", locationFacade.getAllLocations());
        } catch (HuntersServiceException ex) {
            return "/404";
        }
        return "location/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        log.debug("view({})", id);
        try {
        model.addAttribute("bestWeapon", locationFacade.getBestWeapon(id));
        model.addAttribute("location", locationFacade.getLocationById(id));
        model.addAttribute("monsters", locationFacade.getMonsters(id));
        model.addAttribute("comments", locationFacade.getComments(id));
        } catch (HuntersServiceException ex) {
            return "/404";
        }
        return "location/view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model,
            UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }

        LocationDTO location = locationFacade.getLocationById(id);
        locationFacade.deleteLocation(id);
        log.debug("delete({})", id);
        redirectAttributes.addFlashAttribute("alert_success", "Location \"" + location.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/location/list").toUriString();
    }

    @RequestMapping(value = "/{locationId}/comment/delete/{commentId}", method = RequestMethod.POST)
    public String deleteComment(@PathVariable long locationId, @PathVariable long commentId, Model model,
            UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Long uId = commentFacade.getCommentById(commentId).getUser().getId();
        if (getUser(request) == null || (getUser(request).isAdmin() == false && !Objects.equals(getUser(request).getId(), uId))) {
            return "/403";
        }
        
        locationFacade.removeComment(locationId, commentId);
        redirectAttributes.addFlashAttribute("alert_success", "Comment was deleted.");
        return "redirect:" + uriBuilder.path("/location/view/" + locationId).toUriString();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editLocation(@PathVariable long id, Model model, HttpServletRequest request) {
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
        
        log.debug("editLocation()");
        LocationDTO location = locationFacade.getLocationById(id);
        if(location == null)
            return "/404";
        log.debug("locationDTO with id after locationFacede.getLocationById(id)", location.getId());
        log.debug(location.getId().toString());
        model.addAttribute("locationUpdate", location);
        return "location/edit";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String create(@PathVariable long id, @Valid @ModelAttribute("locationUpdate") LocationDTO formBean, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        log.debug("update(locationUpdate={})", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            return "location/edit";
        }
        //create location
        try {
            id = locationFacade.updateLocation(formBean);
            log.debug("id of updated location", id);
            //report success
            redirectAttributes.addFlashAttribute("alert_success", "Location " + id + " was updated.");
            return "redirect:" + uriBuilder.path("/location/view/{id}").buildAndExpand(id).encode().toUriString();
        } catch (HuntersServiceException ex) {
            bindingResult.rejectValue("name", "error.updateError", "Please type different name.");
            return "location/edit";
        }
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newLocation(Model model, HttpServletRequest request) {
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
        log.debug("new()");
        model.addAttribute("locationCreate", new LocationCreateDTO());
        return "location/new";
    }

    @RequestMapping(value = "/{id}/comment/new", method = RequestMethod.GET)
    public String newComment(@PathVariable long id, Model model, HttpServletRequest request) {
        if (getUser(request) == null) {
            return "/403";
        }
        LocationDTO location = locationFacade.getLocationById(id);
        if(location == null)
            return "/404";
        
        log.debug("newComment()");
        model.addAttribute("commentCreate", new CommentCreateDTO());
        model.addAttribute("locationId", id);
        return "/location/comment/new";
    }

    @RequestMapping(value = "/{id}/comment/create", method = RequestMethod.POST)
    public String createComment(@PathVariable long id, @Valid @ModelAttribute("commentCreate") CommentCreateDTO formBean, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        log.debug("create(commentCreate={})", formBean);
        if (getUser(request) == null) {
            return "/403";
        }
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            model.addAttribute("locationId", id);
            return "/location/comment/new";
        }
        LocationDTO location = locationFacade.getLocationById(id);
        if(location == null)
            return "/403";
        try {
            //create comment
            Long cId = commentFacade.createComment(formBean);
            locationFacade.addComment(id, cId);
            //report success
            redirectAttributes.addFlashAttribute("alert_success", "Comment to location " + locationFacade.getLocationById(id).getName() + " was created.");
            return "redirect:" + uriBuilder.path("/location/view/" + id).buildAndExpand(cId).encode().toUriString();
        } catch (HuntersServiceException ex) {
            bindingResult.rejectValue("content", "error.commentAlreadyExist", "Same comment already exists for this location.");
            model.addAttribute("locationId", id);
            return "/location/comment/new";
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("locationCreate") LocationCreateDTO formBean, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        log.debug("create(locationCreate={})", formBean);
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
//        in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            return "/location/new";
        }
        //create location
        try {
            Long id = locationFacade.createLocation(formBean);
            //report success
            redirectAttributes.addFlashAttribute("alert_success", "Location " + formBean.getName() + " was created");
            return "redirect:" + uriBuilder.path("/location/view/{id}").buildAndExpand(id).encode().toUriString();
        } catch (HuntersServiceException ex) {
            bindingResult.rejectValue("name", "error.nameAlreadyExist", "Please type different name.");
            return "location/new";
        }

    }

    @ModelAttribute("authenticatedUser")
    public UserDTO getUser(HttpServletRequest request) {
        return (UserDTO) request.getSession().getAttribute(AUTHENTICATED_USER);
    }

}
