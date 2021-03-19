# InSession startcode

## Team
**Peter R Andersen**
* [Github](https://github.com/Peter-Rambeck)

**Jens Gelbek**
* [Github](https://github.com/jensgelbek)

**Rasmus Ditlev Hansen**
* [Github](https://github.com/RasmusDH)

**Tobias Zimmermann**
* [Github](https://github.com/tobias-z)

## Getting started
1. Change the `remote.server` to your domain in the pom.xml
2. Change env variable `connection_str` in the EMF_Creator
3. Insert that new env variable on your droplets docker-compose.yml file together with a new database name
4. Run these commands:
    - docker-compose down
    - docker-compose build
    - docker-compose up -d
    
5. Create the new database on your droplet

### Travis
In the .travis.yml there is a section `only` under branches, this is where you can decide which branches should be build on push.
