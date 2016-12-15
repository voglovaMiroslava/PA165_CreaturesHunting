package com.monsterhunters.pa165.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monsterhunters.pa165.dto.CommentDTO;
import com.monsterhunters.pa165.dto.UserDTO;
import com.monsterhunters.pa165.dto.WeaponCreateDTO;
import com.monsterhunters.pa165.dto.WeaponDTO;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.facade.WeaponFacade;
import com.monsterhunters.pa165.rest.controllers.GlobalExceptionController;
import com.monsterhunters.pa165.rest.controllers.WeaponsController;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by babcang
 *
 * @author Babcan G
 */
@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class WeaponsControllerTest {

    @Mock
    private WeaponFacade weaponFacade;


    @InjectMocks
    WeaponsController weaponsController = new WeaponsController();

    private MockMvc mockMvc;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(weaponsController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(GlobalExceptionController.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new GlobalExceptionController(), method);
            }
        };
        exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

    @Test
    public void debugTest() throws Exception {
        doReturn(Collections.unmodifiableList(this.createWeapons())).when(
                weaponFacade).getAllWeapons();
        mockMvc.perform(get("/weapons")).andDo(print());
    }

    @Test
    public void shouldGetAllWeapons() throws Exception {
        doReturn(Collections.unmodifiableList(this.createWeapons())).when(
                weaponFacade).getAllWeapons();

        mockMvc.perform(get("/weapons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==10)].name").value("Pistol"))
                .andExpect(jsonPath("$.[?(@.id==11)].name").value("Bazooka"));
    }

    @Test
    public void shouldGetWeapon() throws Exception {
        List<WeaponDTO> weapons = this.createWeapons();

        doReturn(weapons.get(0)).when(weaponFacade).getWeaponById(10L);

        mockMvc.perform(get("/weapons/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("Pistol"));
    }

    @Test
    public void shouldDeleteWeapon() throws Exception {

        List<WeaponDTO> weapons = this.createWeapons();

        mockMvc.perform(delete("/weapons/10"))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldCreateWeapon() throws Exception {
        WeaponCreateDTO weaponCreateDTO = new WeaponCreateDTO();
        weaponCreateDTO.setName("Bizon");
        weaponCreateDTO.setDamage(10);
        weaponCreateDTO.setAmmo(64);

        doReturn(1L).when(weaponFacade).createWeapon(
                any(WeaponCreateDTO.class));
        String json = this.convertObjectToJsonBytes(weaponCreateDTO);

        System.out.println(json);

        mockMvc.perform(
                post("/weapons/").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void shouldUpdateWeapon() throws Exception {
        List<WeaponDTO> weapons = this.createWeapons();

        doReturn(weapons.get(0)).when(weaponFacade).getWeaponById(10L);
        doReturn(10L).when(weaponFacade).updateWeapon(weapons.get(0));
        weapons.get(0).setName("RenamedPistol");

        String json = this.convertObjectToJsonBytes(weapons.get(0));
        mockMvc.perform(put("/weapons/10").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAddType() throws Exception {
        List<WeaponDTO> weapons = this.createWeapons();

        doReturn(weapons.get(0)).when(weaponFacade).getWeaponById(10L);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                weapons.get(0).addEffectiveAgainst(MonsterType.FIRE);
                return null;
            }
        }).when(weaponFacade).addEffectiveAgainst(10L, MonsterType.FIRE);

        mockMvc.perform(
                put("/weapons/10/types?monsterType=FIRE")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.effectiveAgainst").value("FIRE"));

    }

    @Test
    public void shouldRemoveType() throws Exception {
        List<WeaponDTO> weapons = this.createWeapons();

        doReturn(weapons.get(1)).when(weaponFacade).getWeaponById(11L);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                weapons.get(1).removeEffectiveAgainst(MonsterType.FIRE);
                return null;
            }
        }).when(weaponFacade).removeEffectiveAgainst(11L, MonsterType.FIRE);

        mockMvc.perform(
                delete("/weapons/11/types?monsterType=FIRE")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.effectiveAgainst").value("GROUND"));
    }

    @Test
    public void shouldAddComment() throws Exception {
        List<WeaponDTO> weapons = this.createWeapons();

        doReturn(weapons.get(0)).when(weaponFacade).getWeaponById(10L);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setUser(user());
        commentDTO.setContent("Test COMMENT");
        commentDTO.setId(1L);

        String json = this.convertObjectToJsonBytes(commentDTO);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                weapons.get(0).addComment(commentDTO);
                return null;
            }
        }).when(weaponFacade).addComment(weapons.get(0).getId(), commentDTO.getId());

        mockMvc.perform(put("/weapons/10/comments").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==10)].name").value("Pistol"))
                .andExpect(jsonPath("$.[?(@.id==10)].comments.[?(@.id==1)].content").value("Test COMMENT"));
    }

    @Test
    public void shouldDeleteComment() throws Exception {
        List<WeaponDTO> weapons = this.createWeapons();

        doReturn(weapons.get(1)).when(weaponFacade).getWeaponById(11L);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                weapons.get(1).removeComment(comment());
                return null;
            }
        }).when(weaponFacade).removeComment(weapons.get(1).getId(), comment().getId());

        mockMvc.perform(
                delete("/weapons/11/comments/100")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[?(@.id==11)].name").value("Bazooka"));
    }

    private List<WeaponDTO> createWeapons() {
        WeaponDTO weaponOne = new WeaponDTO();
        weaponOne.setId(10L);
        weaponOne.setName("Pistol");
        weaponOne.setAmmo(10);
        weaponOne.setDamage(60);

        WeaponDTO weaponTwo = new WeaponDTO();
        weaponTwo.setId(11L);
        weaponTwo.setName("Bazooka");
        weaponTwo.setAmmo(3);
        weaponTwo.setDamage(160);
        weaponTwo.addEffectiveAgainst(MonsterType.FIRE);
        weaponTwo.addEffectiveAgainst(MonsterType.GROUND);

        UserDTO user = user();
        CommentDTO comment = comment();
        weaponTwo.addComment(comment);

        return Arrays.asList(weaponOne, weaponTwo);
    }

    private UserDTO user() {
        UserDTO user = new UserDTO();
        user.setId(1000L);
        user.setAdmin(true);
        user.setNickname("Luke");
        user.setEmail("luke@power.gal");
        return user;
    }

    private CommentDTO comment(){
        CommentDTO comment = new CommentDTO();
        comment.setId(100L);
        comment.setContent("This is comment for weapon 2");
        comment.setUser(user());
        return comment;
    }

    private static String convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }


}
