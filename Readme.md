# DeeplayTest
This app calculates the shortest way from the top-left corner of the game field to te down-right one.  

![Picture3](/lib/Picture3.png)
### Usage information
Game field has 4X4 size and contains upper case letters - first letters of game tiles:  
`S` - *Swamp*  
`W` - *Water*  
`T` - *Thicket*  
`P` - *Plain*  

There are 3 types of creatures in this game:  
`Human`  
`Woodman`  
`Swamper`  

Each tile type has its own moving cost for every creature.
### Input/output data
Class _Solution_ has _main_ method that accepts two arguments:  
- Game field formatted like a 16-letters string. For example - _STWSWTPPTPTTPWPP_.  
- Creature type. For example - _Human_.  

Also, you need a data.config file, that contains each tile's cost.  
Each creature must be uppercased, located on its own line, that specifies costs. Separate it with a spacebar.  
Creatures order doesn't matter, but tiles order does. Correct order is  

`Swamp - Water - Thicket - Plain`  

Example for the data.config file:  

```
HUMAN 5 2 3 1
SWAMPER 2 2 5 2
WOODMAN 3 3 2 2
```

Method prints to the console the cost of the shortest path.  
For the example above it's 10.
### Docker
This app made for run with docker only.  
Repository's path on DockerHub: ***pashabezborod/deeplaytest***  
Mount data.config file to **/app/data/data.config** and add two arguments for **main** method.

To run it with docker you can use following command:  
```
docker run -v path_to_data.config:/app/data/data.config pashabezborod/deeplaytest gameField CreatureName
```

or just  
```
docker run pashabezborod/deeplaytest gameField CreatureName  
```
to run it with default configs you can find above.

Example for docker command:  
```
docker run -v /home/user/data.config:/app/data/data.config pashabezborod/deeplaytest STWSWTPPTPTTPWPP Human
```
### Task in pictures