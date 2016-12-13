package com.monsterhunters.pa165.sampledata;

import com.monsterhunters.pa165.entity.Comment;
import com.monsterhunters.pa165.entity.Location;
import com.monsterhunters.pa165.entity.Monster;
import com.monsterhunters.pa165.entity.User;
import com.monsterhunters.pa165.entity.Weapon;
import com.monsterhunters.pa165.enums.MonsterType;
import com.monsterhunters.pa165.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by babcang
 *
 * @author Babcan G
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private WeaponService weaponService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private MonsterService monsterService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    private Set<MonsterType> setOfMonsterTypes = new HashSet<>();

    @Override
    public void loadData() throws IOException {
        //TODO create some data
        setOfMonsterTypes.add(MonsterType.DRAGON);
        setOfMonsterTypes.add(MonsterType.FIRE);
        setOfMonsterTypes.add(MonsterType.FLYING);
        setOfMonsterTypes.add(MonsterType.HYPNOTIC);
        setOfMonsterTypes.add(MonsterType.LIGHTNING);

        Weapon weaponAK47 = weapon("AK47", 30, 70, 300, MonsterType.DRAGON);
        Weapon weaponAWP = weapon("AWP", 10, 90, 800, MonsterType.FLYING);
        Weapon weaponP90 = weapon("P90", 50, 55, 200, setOfMonsterTypes);

        User userJW = user("JohnWick", "johnwick@getkill.com", "NBS1234", false);
        User userY = user("Yoda", "yoda@starwars.com", "green", true);

        Comment comment1 = comment("Light saber I would prefer, but good weapon is this also", userY);
        Comment comment2 = comment("Very good for long distance.", userJW);
        Comment commentUA = comment("Test unassigned comment.", userJW);

        weaponAK47.addComment(comment1);
        weaponAWP.addComment(comment2);

        Comment brnoComment = comment("Go, this ist place where you must!  Herh herh herh.", userY);
        Comment novotComment = comment("To the pub go, shall we, hmm, hmm? Yeesssssss.", userY);
        Comment dulovCommnet = comment("Of the united states the next sith lord, donald trump is. His master, george wallace was, until he struck him down.Yeesssssss. Yeesssssss.", userY);

        Location brno = location("Brno", "Nice place in the middle of Europe.");
        Location novot = location("Novot", "Village with lots of monsters.");
        Location mutne = location("Mutne", "Warning, never try to go there.");
        Location dulov = location("Dulov", "Place where insects rotate around a big hole.");
        brno.addComment(brnoComment);
        novot.addComment(novotComment);
        dulov.addComment(dulovCommnet);

        Monster drake = monster("Waterly Dragon", 290.0, 450.9, 120, mutne, new HashSet<MonsterType>() {
            {
                add(MonsterType.FAIRY);
                add(MonsterType.DRAGON);
                add(MonsterType.WATER);
            }
        });

        Monster mug = monster("Tiny mug", 0.15, 7.4, 26, dulov, new HashSet<MonsterType>() {
            {
                add(MonsterType.UNDEAD);
                add(MonsterType.GROUND);
                add(MonsterType.FLYING);
            }
        });

        Monster fly = monster("Serial killer", 4.35, 0.6, 240, novot, new HashSet<MonsterType>() {
            {
                add(MonsterType.HYPNOTIC);
                add(MonsterType.LIGHTNING);
                add(MonsterType.FIRE);
                add(MonsterType.FLYING);
            }
        });

        Monster zombie = monster("Standard zombie", 170.6, 75.2, 37, novot, new HashSet<MonsterType>() {
            {
                add(MonsterType.UNDEAD);
            }
        });
    }

    private static Date toDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private Weapon weapon(String name, int ammo, int damage, int gunReach, MonsterType effectiveAgainst) {
        Weapon weapon = new Weapon();
        weapon.setName(name);
        weapon.setAmmo(ammo);
        weapon.setDamage(damage);
        weapon.setGunReach(gunReach);
        weapon.addEffectiveAgainst(effectiveAgainst);
        weaponService.createWeapon(weapon);
        return weapon;
    }

    private Weapon weapon(String name, int ammo, int damage, int gunReach, Set<MonsterType> setEffectiveAggainst) {
        Weapon weapon = new Weapon();
        weapon.setName(name);
        weapon.setAmmo(ammo);
        weapon.setDamage(damage);
        weapon.setGunReach(gunReach);
        weapon.setEffectiveAgainst(setEffectiveAggainst);
        weaponService.createWeapon(weapon);
        return weapon;
    }

    private Comment comment(String content, User user) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        commentService.createComment(comment);
        return comment;
    }

    private User user(String nickname, String mail, String plainPassword, boolean isAdmin) {
        User user = new User();
        user.setNickname(nickname);
        user.setEmail(mail);
        user.setAdmin(isAdmin);
        userService.registerUser(user, plainPassword);
        return user;
    }

    private Location location(String name, String descrition) {
        Location location = new Location();
        location.setName(name);
        location.setDescription(descrition);
        locationService.createLocation(location);
        return location;
    }

    private Monster monster(String name, Double height, Double weight, int power, Location loc, Set<MonsterType> types) {
        Monster monster = new Monster();
        monster.setName(name);
        monster.setHeight(height);
        monster.setWeight(weight);
        monster.setPower(power);
        monster.setLocation(loc);
        monster.setTypes(types);
        monsterService.createMonster(monster);
        return monster;
    }

}
