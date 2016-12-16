# PA165_CreaturesHunting

[![Build Status](https://travis-ci.org/voglovaMiroslava/PA165_CreaturesHunting.svg?branch=master)](https://travis-ci.org/voglovaMiroslava/PA165_CreaturesHunting)


### Official assignment
After a nuclear disaster there are a lot of mutans, zombies and other creatures wandering through some areas of our world. A handful of survivals must defend themselves agains these creatures. There are available various kinds of weapons, each weapon has different useability against some monster. This system should enable storing information about monsters (name, height, weight, agility, ...), areas where the monsters live (name, description, area) and available weapons (name, gun-reach, ammunition used). Aministrator of the system should be able to perform CRUD operations on all entities. Ordinary user will use this system to record his experience which weapon could be efficiently used against the monster as well as to assign areas where the creature has been spotted.

### Rest

####Start REST:
```
  $ cd  hunters-rest
  $ mvn tomcat7:run
```
####CURL commands:

List of locations:
```
  $ curl -i -X GET http://localhost:8080/pa165/rest/locations
```
Single location:
```
  $ curl -i -X GET http://localhost:8080/pa165/rest/locations/{id}
```
