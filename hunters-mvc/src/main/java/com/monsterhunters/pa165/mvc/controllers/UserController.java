package com.monsterhunters.pa165.mvc.controllers;

import com.monsterhunters.pa165.dto.UserAuthenticateDTO;
import com.monsterhunters.pa165.dto.UserChangePassDTO;
import com.monsterhunters.pa165.dto.UserCreateDTO;
import com.monsterhunters.pa165.dto.UserDTO;
import com.monsterhunters.pa165.exceptions.user.EmailAlreadyExistsException;
import com.monsterhunters.pa165.exceptions.user.NicknameAlreadyExistsException;
import com.monsterhunters.pa165.exceptions.user.UserDoesNotExistsException;
import com.monsterhunters.pa165.facade.CommentFacade;
import com.monsterhunters.pa165.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Snurka on 12/12/2016.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    public static final String AUTHENTICATED_USER = "authenticatedUser";

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private CommentFacade commentFacade;

    /** Load page with list of all users.
     *  Also display  buttons to add, delete or view specific user
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        LOGGER.debug("list of users");
        model.addAttribute("users", userFacade.getAllUsers());
        return "user/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model, HttpServletRequest request) {
        if(request.getSession().getAttribute(AUTHENTICATED_USER) != null) {
            UserDTO authenticatedUser = (UserDTO) request.getSession().getAttribute(AUTHENTICATED_USER);
            if(authenticatedUser.getId().equals(id)) {
                LOGGER.debug("view({})", id);
                model.addAttribute("user", userFacade.getUserById(id));
//            model.addAttribute("userComments", commentFacade.getCommentsByUserNickname(authenticatedUser.getNickname()));
                return "user/view";
            } else {
                return "403";
            }
        } else {
            return "404";
        }

    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(Model model) {
        model.addAttribute("userCreate", new UserCreateDTO());
        return "user/signin";
    }

    @RequestMapping(value = "/trySignin", method = RequestMethod.POST)
    public String trySignin(Model model,
                           @Valid @ModelAttribute("userCreate") UserCreateDTO formBean,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           UriComponentsBuilder uriBuilder,
                           HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return bindingErrors(model, bindingResult, "user/signin");
        }
        try {
            userFacade.registerUser(formBean);
            redirectAttributes.addFlashAttribute("alert_success", String.format("User %s signed in", formBean.getNickname()));
            LOGGER.debug("User signed in: " + formBean.getNickname());
            return "redirect:/";
        } catch (EmailAlreadyExistsException e) {
            LOGGER.trace("Sign in failed. Caused by: ", e);
            bindingResult.rejectValue("email", "error.emailAlreadyExists", String.format("Email %s is already registered.", e.getEmail()));
            return "user/signin";
        } catch (NicknameAlreadyExistsException e) {
            LOGGER.trace("Sign in failed. Caused by: ", e);
            bindingResult.rejectValue("email", "error.nicknameAlreadyExists", String.format("Nickname %s is already registered.", e.getNickname()));
            return "user/signin";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("userAuthenticate", new UserAuthenticateDTO());
        return "user/login";
    }

    @RequestMapping(value = "/tryLogin", method = RequestMethod.POST)
    public String tryLogin(Model model,
                           @Valid @ModelAttribute("userAuthenticate") UserAuthenticateDTO formBean,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           UriComponentsBuilder uriBuilder,
                           HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return bindingErrors(model, bindingResult, "user/login");
        }
        try {
            boolean result = userFacade.authenticateUser(formBean);
            if(!result) {
                bindingResult.rejectValue("password", "error.wrongNicknameOrPassword", "Authentication failed. Please type correct nickname/password");
                return "user/login";
            }
            redirectAttributes.addFlashAttribute("alert_success", "You have been logged in as " + formBean.getNickname() + ".");
            request.getSession().setAttribute(AUTHENTICATED_USER, userFacade.getUserByNickname(formBean.getNickname()));
            LOGGER.debug("User logged in: " + formBean.getNickname());
            return "redirect:/";
        } catch (UserDoesNotExistsException e) {
            LOGGER.trace("Login failed. Caused: ", e);
            bindingResult.rejectValue("nickname", "error.nicknameDoesNotExists", "Please type correct nickname.");
            return "user/login";
        }
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword(Model model) {
        model.addAttribute("changePass", new UserChangePassDTO());
        return "user/changePassword";
    }

    @RequestMapping(value = "/tryChangePassword", method = RequestMethod.POST)
    public String tryChangePassword(Model model,
                                    @Valid @ModelAttribute("changePass") UserChangePassDTO formBean,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    UriComponentsBuilder uriBuilder,
                                    HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return bindingErrors(model, bindingResult, "user/changePassword");
        }
        try {
            boolean result = userFacade.changePassword(formBean);
            if(!result) {
                bindingResult.rejectValue("oldPassword", "error.wrongNicknameOrPassword", "Authentication failed. Please type correct nickname/password");
                return "user/changePassword";
            }
            redirectAttributes.addFlashAttribute("alert_success", "You have been logged in as " + formBean.getNickname() + ".");
            LOGGER.debug("User logged in: " + formBean.getNickname());
            return "redirect:/";
        } catch (UserDoesNotExistsException e) {
            LOGGER.trace("Login failed. Caused: ", e);
            bindingResult.rejectValue("nickname", "error.nicknameDoesNotExists", "Please type correct nickname.");
            return "user/changePassword";
        }
    }

    private String bindingErrors(Model model, BindingResult bindingResult, String redirectString) {
        for (ObjectError ge : bindingResult.getGlobalErrors()) {
            LOGGER.trace("ObjectError: {}", ge);
        }
        for (FieldError fe : bindingResult.getFieldErrors()) {
            model.addAttribute(fe.getField() + "_error", true);
            LOGGER.trace("FieldError: {}", fe);
        }
        return redirectString;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request) {
        LOGGER.debug("logout");
        request.getSession().removeAttribute(AUTHENTICATED_USER);
        model.addAttribute("alert_success", "You have been logged out.");
        return "home";
    }



    /*@RequestMapping(value = "/changePassword/{id}", method = RequestMethod.GET)
    public String changePassword(@PathVariable long id, Model model) {
        LOGGER.debug("changePassowrd({})", id);
        return "user/changePassword";
    }

    @RequestMapping(value = "/updatePass/{id}", method = RequestMethod.POST)
    public String updatePassword(@Valid @ModelAttribute("user") UserDTO formBean,
                                 @PathVariable long id,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 UriComponentsBuilder uriBuilder)
    {
        LOGGER.debug("update(userChangePassword={})", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                LOGGER.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                LOGGER.trace("FieldError: {}", fe);
            }
            return "user/changePassword";
        }
        //create product
        id = userFacade.changePassword(formBean);
        LOGGER.debug("id of updated user", id);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "User " + id + " was updated");
        return "redirect:" + uriBuilder.path("/user/view/{id}").buildAndExpand(id).encode().toUriString();
    }*/


//    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
//    public String editUser(@PathVariable long id, Model model) {
//        LOGGER.debug("editUser()");
//        UserDTO user = userFacade.getUserById(id);
//        LOGGER.debug("userDTO with id after userFacede.getUserById(id)", user.getId());
//        LOGGER.debug(user.getId().toString());
//        model.addAttribute("userUpdate", user);
//        return "user/edit";
//    }

//    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
//    public String create(@Valid @ModelAttribute("userUpdate") UserDTO formBean, @PathVariable long id, BindingResult bindingResult,
//                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
//        LOGGER.debug("update(userUpdate={})", formBean);
//        //in case of validation error forward back to the the form
//        if (bindingResult.hasErrors()) {
//            for (ObjectError ge : bindingResult.getGlobalErrors()) {
//                LOGGER.trace("ObjectError: {}", ge);
//            }
//            for (FieldError fe : bindingResult.getFieldErrors()) {
//                model.addAttribute(fe.getField() + "_error", true);
//                LOGGER.trace("FieldError: {}", fe);
//            }
//            return "user/edit";
//        }
//        //create product
//        id = userFacade.updateUser(formBean);
//        LOGGER.debug("id of updated user", id);
//        //report success
//        redirectAttributes.addFlashAttribute("alert_success", "User " + id + " was updated");
//        return "redirect:" + uriBuilder.path("/user/view/{id}").buildAndExpand(id).encode().toUriString();
//    }

//    @RequestMapping(value = "/new", method = RequestMethod.GET)
//    public String newUser(Model model) {
//        LOGGER.debug("new()");
//        model.addAttribute("userCreate", new UserCreateDTO());
//        model.addAttribute("monsterTypes", MonsterType.values());
//        return "user/new";
//    }

//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    public String create(@Valid @ModelAttribute("userCreate") UserCreateDTO formBean, BindingResult bindingResult,
//                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
//        LOGGER.debug("create(userCreate={})", formBean);
//        //in case of validation error forward back to the the form
//        if (bindingResult.hasErrors()) {
//            for (ObjectError ge : bindingResult.getGlobalErrors()) {
//                LOGGER.trace("ObjectError: {}", ge);
//            }
//            for (FieldError fe : bindingResult.getFieldErrors()) {
//                model.addAttribute(fe.getField() + "_error", true);
//                LOGGER.trace("FieldError: {}", fe);
//            }
//            model.addAttribute("monsterTypes", MonsterType.values());
//            return "user/new";
//        }
//        //create product
//        Long id = userFacade.createUser(formBean);
//        //report success
//        redirectAttributes.addFlashAttribute("alert_success", "User " + id + " was created");
//        return "redirect:" + uriBuilder.path("/user/view/{id}").buildAndExpand(id).encode().toUriString();
//    }
}
