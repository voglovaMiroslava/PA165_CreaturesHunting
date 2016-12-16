# PA165_CreaturesHunting

[![Build Status](https://travis-ci.org/voglovaMiroslava/PA165_CreaturesHunting.svg?branch=master)](https://travis-ci.org/voglovaMiroslava/PA165_CreaturesHunting)


### Official assignment
After a nuclear disaster there are a lot of mutans, zombies and other creatures wandering through some areas of our world. A handful of survivals must defend themselves agains these creatures. There are available various kinds of weapons, each weapon has different useability against some monster. This system should enable storing information about monsters (name, height, weight, agility, ...), areas where the monsters live (name, description, area) and available weapons (name, gun-reach, ammunition used). Aministrator of the system should be able to perform CRUD operations on all entities. Ordinary user will use this system to record his experience which weapon could be efficiently used against the monster as well as to assign areas where the creature has been spotted.

### Web

####Start Web:

```
  $ cd  hunters-mvc && mvn tomcat7:run
```

### Rest

####Start REST:
```
  $ cd  hunters-rest && mvn tomcat7:run
```
####CURL commands:

List of monsters:
```
  $ curl -i -X GET http://localhost:8080/pa165/rest/monsters
```
Single monster:
```
  $ curl -i -X GET http://localhost:8080/pa165/rest/monsters/{id}
```
Delete monster:
```
  $ curl -i -X DELETE http://localhost:8080/pa165/rest/monsters/{id}
```
Create monster:
```
  $ curl -X POST http://localhost:8080/pa165/rest/monsters/ -i -H "Content-Type: application/json" --data '{"name":"monter-name", "height":20.0, "weight":10.0,"power":200,"locationId": 2, "types":["DRAGON"]}'
```
List of weapons:
```
  $ curl -i -X GET http://localhost:8080/pa165/rest/weapons
```
Single weapon:
```
  $ curl -i -X GET http://localhost:8080/pa165/rest/weapons/{id}
```
Create weapon:
```
  $  curl -X POST http://localhost:8080/pa165/rest/weapons/ -i -H "Content-Type: application/json" --data '{"name":"test","ammo":"20","damage":"10","gunReach":"200", "effectiveAgainst":["DRAGON"]}'
```
Delete weapon:
```
  $  curl -i -X DELETE http://localhost:8080/pa165/rest/weapons/{id}
```
Add monster type to weapon:
```
  $   curl -X PUT -G 'http://localhost:8080/pa165/rest/weapons/{id}/types' -d 'monsterType=GROUND'
```
Remove monster type from weapon:
```
  $   curl -X DELETE -G 'http://localhost:8080/pa165/rest/weapons/{id}/types' -d 'monsterType=FLYING'
```
Update weapon:
```
$ curl -X PUT -i -H "Content-Type: application/json" --data '{"name":"TEST UPDATE","ammo":"50","damage":"20","gunReach":"200", "effectiveAgainst":["DRAGON"]}' http://localhost:8080/pa165/rest/weapons/{id}
 ```
 Add comment to weapon:
 ```
$  curl -X PUT http://localhost:8080/pa165/rest/weapons/2/comments/ -i -H "Content-Type: application/json" --data '{"id":3,"user":{"id":2,"nickname":"JohnWick", "email":"johnwick@getkill.com","admin":false},"content":"Test unassigned comment."}'
 ```
 Remove comment from weapon:
  ```
$  curl -i -X DELETE http://localhost:8080/pa165/rest/weapons/{weaponID}/comments/{commentID}
 ```

List of locations:
```
  $ curl -i -X GET http://localhost:8080/pa165/rest/locations
```
Single location:
```
  $ curl -i -X GET http://localhost:8080/pa165/rest/locations/{id}
```
Best weapon in location
```
curl -i -X GET http://localhost:8080/pa165/rest/locations/{id}/bestweapon
```
Monsters in location
```
curl -i -X GET http://localhost:8080/pa165/rest/locations/{id}}/monsters
```
Create location:
```
curl -X POST http://localhost:8080/pa165/rest/locations/ -i -H "Content-Type: application/json" --data '{"name":"test","description":"testing description"}'
```
Delete location:
```
  $  curl -i -X DELETE http://localhost:8080/pa165/rest/locations/{id}
```
 Create comment to location:
```
curl -X POST http://localhost:8080/pa165/rest/locations/{locationId}/comments/{userNickName} -i -H "Content-Type: application/json" --data '{"content":"test content to comment"}'
```
Delete comment in location
```
curl -i -X DELETE http://localhost:8080/pa165/rest/locations/{locationId}/comments/{commentId}
```