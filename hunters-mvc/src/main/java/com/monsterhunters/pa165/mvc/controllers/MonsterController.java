package com.monsterhunters.pa165.mvc.controllers;

import com.monsterhunters.pa165.dto.LocationDTO;
import com.monsterhunters.pa165.dto.MonsterCreateDTO;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.facade.LocationFacade;
import com.monsterhunters.pa165.facade.MonsterFacade;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String newMonster(Model model) {
        LOG.debug("[mvc] getting form for new monster");

        Map<Long, String> locations = new LinkedHashMap<>();
        for (LocationDTO loc : locationFacade.getAllLocations()) {
            locations.put(loc.getId(), loc.getName());
        }
        model.addAttribute("locationList", locations);
        model.addAttribute("monsterCreate", new MonsterCreateDTO());
        model.addAttribute("monsterTypes", MonsterType.values());

        return "monster/new";
    }
}
