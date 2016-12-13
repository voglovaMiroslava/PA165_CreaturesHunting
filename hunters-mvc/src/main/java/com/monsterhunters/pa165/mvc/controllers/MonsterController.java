package com.monsterhunters.pa165.mvc.controllers;

import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.dto.MonsterCreateDTO;
import com.monsterhunters.pa165.dto.MonsterDTO;
import com.monsterhunters.pa165.dto.UserDTO;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.facade.LocationFacade;
import com.monsterhunters.pa165.facade.MonsterFacade;
import static com.monsterhunters.pa165.mvc.controllers.UserController.AUTHENTICATED_USER;
import com.monsterhunters.pa165.mvc.editors.LocationEditor;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Miroslava Voglova
 */
@Controller
@RequestMapping("/monster")
public class MonsterController {

    private final static Logger LOG = LoggerFactory.getLogger(MonsterController.class);

    @Autowired
    private MonsterFacade monsterFacade;

    @Autowired
    private LocationFacade locationFacade;

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(LocationDTO.class, new LocationEditor(locationFacade));
    }

    @ModelAttribute("authenticatedUser")
    public UserDTO getUser(HttpServletRequest request) {
        return (UserDTO) request.getSession().getAttribute(AUTHENTICATED_USER);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        LOG.debug("[mvc] getting list of all monsters");
        model.addAttribute("monsters", monsterFacade.findAll());
        return "monster/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        LOG.debug("[mvc] getting view of monster with id " + id);
        model.addAttribute("monster", monsterFacade.findById(id));
        return "monster/view";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newMonster(Model model, HttpServletRequest request) {
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
        LOG.debug("[mvc] getting form for new monster");
        model.addAttribute("monsterToCreate", new MonsterCreateDTO());
        prepateModelForMonsterForm(model);
        return "monster/new";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
        LOG.debug("[mvc] deleting monster with id " + id);
        MonsterDTO monster = monsterFacade.findById(id);
        monsterFacade.deleteMonster(id);
        redirectAttributes.addFlashAttribute("alert_success", "Monster \"" + monster.getName() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/monster/list").toUriString();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("monsterToCreate") MonsterCreateDTO formMonster, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
        LOG.debug("[mvc] creating new monster", formMonster);
        if (bindingResult.hasErrors()) {
            prepateModelForMonsterForm(model);
            return "monster/new";
        }

        Long id = monsterFacade.createMonster(formMonster);
        redirectAttributes.addFlashAttribute("alert_success", "Monster " + formMonster.getName() + " was created");
        return "redirect:" + uriBuilder.path("/monster/view/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editMonster(@PathVariable Long id, Model model, HttpServletRequest request) {
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
        LOG.debug("[mvc] getting form for editing monster");
        MonsterDTO monster = monsterFacade.findById(id);
        model.addAttribute("monsterToUpdate", monster);
        prepateModelForMonsterForm(model);
        return "monster/edit";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String create(@PathVariable Long id, @Valid @ModelAttribute("monsterToUpdate") MonsterDTO formMonster, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        if (getUser(request) == null || getUser(request).isAdmin() == false) {
            return "/403";
        }
        LOG.debug("[mvc] updating monster with id " + id);
        if (bindingResult.hasErrors()) {
            prepateModelForMonsterForm(model);
            return "monster/edit";
        }

        monsterFacade.updateMonster(formMonster);

        redirectAttributes.addFlashAttribute("alert_success", "Monster " + formMonster.getName() + " was updated");
        return "redirect:" + uriBuilder.path(
                "/monster/view/{id}").buildAndExpand(id).encode().toUriString();
    }

    private void prepateModelForMonsterForm(Model model) {
        model.addAttribute("locationList", locationFacade.getAllLocations());
        model.addAttribute("monsterTypes", MonsterType.values());
    }
}
