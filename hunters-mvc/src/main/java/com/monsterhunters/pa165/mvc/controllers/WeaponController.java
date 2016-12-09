package com.monsterhunters.pa165.mvc.controllers;

import com.monsterhunters.pa165.dto.WeaponCreateDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.enums.MonsterType;
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

import javax.validation.Valid;

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

    /** Load page with list of all weapons.
     *  Also display  buttons to add, delete or view specific weapon
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("list of weapons");
        model.addAttribute("weapons", weaponFacade.getAllWeapons());
        return "weapon/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        log.debug("view({})", id);
        model.addAttribute("weapon", weaponFacade.getWeaponById(id));
//        model.addAllAttributes("comments", weaponFacade.getWeaponById(id).getComments());
        return "weapon/view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        WeaponDTO weapon = weaponFacade.getWeaponById(id);
        weaponFacade.deleteWeapon(id);
        log.debug("delete({})", id);
        redirectAttributes.addFlashAttribute("alert_success", "Weapon \"" + weapon.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/weapon/list").toUriString();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editWeapon(@PathVariable long id, Model model) {
        log.debug("editWeapon()");
        WeaponDTO weapon = weaponFacade.getWeaponById(id);
        log.debug("weaponDTO with id after weaponFacede.getWeaponById(id)", weapon.getId());
        log.debug(weapon.getId().toString());
        model.addAttribute("weaponUpdate", weapon);
        model.addAttribute("monsterTypes", MonsterType.values());
        return "weapon/edit";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("weaponUpdate") WeaponDTO formBean,@PathVariable long id, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("update(weaponUpdate={})", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "weapon/edit";
        }
        //create product
        id = weaponFacade.updateWeapon(formBean);
        log.debug("id of updated weapon", id);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Weapon " + id + " was updated");
        return "redirect:" + uriBuilder.path("/weapon/view/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newWeapon(Model model) {
        log.debug("new()");
        model.addAttribute("weaponCreate", new WeaponCreateDTO());
        model.addAttribute("monsterTypes", MonsterType.values());
        return "weapon/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("weaponCreate") WeaponCreateDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(weaponCreate={})", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            model.addAttribute("monsterTypes", MonsterType.values());
            return "weapon/new";
        }
        //create product
        Long id = weaponFacade.createWeapon(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Weapon " + id + " was created");
        return "redirect:" + uriBuilder.path("/weapon/view/{id}").buildAndExpand(id).encode().toUriString();
    }

}