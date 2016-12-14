package com.monsterhunters.pa165.mvc.controllers;

import com.monsterhunters.pa165.dto.*;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.exceptions.HuntersServiceException;
import com.monsterhunters.pa165.facade.CommentFacade;
import com.monsterhunters.pa165.facade.WeaponFacade;
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

import static com.monsterhunters.pa165.mvc.controllers.UserController.AUTHENTICATED_USER;

/**
 * Created by babcang
 *
 * @author Babcan G
 */
@Controller
@RequestMapping("/weapon")
public class WeaponController {

    final static Logger log = LoggerFactory.getLogger(WeaponController.class);

    @Autowired
    private WeaponFacade weaponFacade;

    @Autowired
    private CommentFacade commentFacade;

    /**
     * Load page with list of all weapons.
     * Also display  buttons to add, delete or view specific weapon
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("list of weapons");
        try {
            model.addAttribute("weapons", weaponFacade.getAllWeapons());
        } catch (HuntersServiceException ex) {
            return "/404";
        }
        return "weapon/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        log.debug("view({})", id);
        try {
            model.addAttribute("weapon", weaponFacade.getWeaponById(id));
            model.addAttribute("killable", weaponFacade.getKillableMonsters(id));
        } catch (HuntersServiceException ex) {
            return "/404";
        }
        return "weapon/view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id,
                         UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest request) {
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
        try {
            WeaponDTO weapon = weaponFacade.getWeaponById(id);
            weaponFacade.deleteWeapon(id);
            log.debug("delete({})", id);
            redirectAttributes.addFlashAttribute("alert_success", "Weapon \"" + weapon.getName() + "\" was deleted.");
            return "redirect:" + uriBuilder.path("/weapon/list").toUriString();
        } catch (HuntersServiceException ex) {
            return "/404";
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editWeapon(@PathVariable long id, Model model,
                             HttpServletRequest request) {
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
        try {
            log.debug("editWeapon()");
            WeaponDTO weapon = weaponFacade.getWeaponById(id);
            log.debug("weaponDTO with id after weaponFacede.getWeaponById({})", weapon.getId());
            log.debug(weapon.getId().toString());
            model.addAttribute("weaponUpdate", weapon);
            return "weapon/edit";
        } catch (HuntersServiceException ex) {
            return "/404";
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("weaponUpdate") WeaponDTO formBean,
                         @PathVariable long id, BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder,
                         HttpServletRequest request) {
        log.debug("update(weaponUpdate={})", formBean);
        log.debug("update(weaponUpdateID={})", id);
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            return "weapon/edit";
        }
        try {
            formBean.setComments(weaponFacade.getWeaponById(id).getComments());
            id = weaponFacade.updateWeapon(formBean);
        } catch (HuntersServiceException ex) {
            bindingResult.rejectValue("name", "error.nameAlreadyExist", "Please type different name.");
            bindingResult.rejectValue("ammo", "error.wrongNumber", "Please type correct number.");
            bindingResult.rejectValue("damage", "error.wrongNumber", "Please type correct number.");
            return "weapon/edit";
        }
        log.debug("id of updated weapon", id);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Weapon " + formBean.getName() + " was updated");
        return "redirect:" + uriBuilder.path("/weapon/view/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newWeapon(Model model, HttpServletRequest request) {
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
        log.debug("newWeapon()");
        model.addAttribute("weaponCreate", new WeaponCreateDTO());
//        model.addAttribute("monsterTypes", MonsterType.values());
        return "weapon/new";
    }

    @ModelAttribute("monsterTypes")
    public MonsterType[] monsterTypes() {
        log.debug("monsterTypes()");
        return MonsterType.values();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("weaponCreate") WeaponCreateDTO formBean,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder,
                         HttpServletRequest request) {
        log.debug("create(weaponCreate={})", formBean);
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            return "weapon/new";
        }
        try {
            Long id = weaponFacade.createWeapon(formBean);
            //report success
            redirectAttributes.addFlashAttribute("alert_success", "Weapon " + formBean.getName() + " was created");
            return "redirect:" + uriBuilder.path("/weapon/view/{id}").buildAndExpand(id).encode().toUriString();
        } catch (HuntersServiceException ex) {
            bindingResult.rejectValue("name", "error.nameAlreadyExist", "Please type different name.");
            bindingResult.rejectValue("ammo", "error.wrongNumber", "Please type correct number.");
            bindingResult.rejectValue("damage", "error.wrongNumber", "Please type correct number.");
            return "weapon/edit";
        }
    }

    @RequestMapping(value = "/{id}/comment/new", method = RequestMethod.GET)
    public String newComment(@PathVariable long id, Model model, HttpServletRequest request) {
        if (getUser(request) == null) {
            return "/403";
        }
        log.debug("newComment()");
        model.addAttribute("commentCreate", new CommentCreateDTO());
        model.addAttribute("weaponId", id);
        return "/weapon/comment/new";
    }

    @RequestMapping(value = "/{id}/comment/create", method = RequestMethod.POST)
    public String createComment(@PathVariable long id, @Valid @ModelAttribute("commentCreate") CommentCreateDTO formBean, BindingResult bindingResult,
                                Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(commentCreate={})", formBean);

        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            model.addAttribute("weaponId", id);
            return "/weapon/comment/new";
        }
        try {
            log.debug("create(commentCreate with user={})", formBean);
            Long commentId = commentFacade.createComment(formBean);
            weaponFacade.addComment(id, commentId);
            //report success
            redirectAttributes.addFlashAttribute("alert_success", "Comment was created and assigned to weapon");
            return "redirect:" + uriBuilder.path("/weapon/view/{id}").buildAndExpand(id).encode().toUriString();
        } catch (HuntersServiceException ex) {
            bindingResult.rejectValue("name", "error.nameAlreadyExist", "Please type different name.");
            return "/weapon/comment/new";
        }

    }

    @RequestMapping(value = "/{id}/comment/delete/{cId}", method = RequestMethod.POST)
    public String deleteComment(@PathVariable long id,
                                @PathVariable long cId, Model model,
                                RedirectAttributes redirectAttributes,
                                UriComponentsBuilder uriBuilder,
                                HttpServletRequest request) {
        if (getUser(request).getId() != id && getUser(request).isAdmin() == false) {
            return "/403";
        }
        if (getUser(request) == null) {
            return "/403";
        }
        CommentDTO comment = commentFacade.getCommentById(cId);
        weaponFacade.removeComment(id, cId);
        log.debug("deleteComment({})", cId);
        redirectAttributes.addFlashAttribute("alert_success", "Comment \"" + comment.getId() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/weapon/view/{id}").buildAndExpand(id).encode().toUriString();
    }

    @ModelAttribute("authenticatedUser")
    public UserDTO getUser(HttpServletRequest request) {
        return (UserDTO) request.getSession().getAttribute(AUTHENTICATED_USER);
    }

}
