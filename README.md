# A1 - Piraten Karpen

  * Author: Davis Lenover
  * Email: lenoverd@mcmaster.ca

## Build and Execution

  * To clean your working directory:
    * `mvn clean`
  * To compile the project:
    * `mvn compile`
  * To run the project in development mode:
    * `mvn -q exec:java` (here, `-q` tells maven to be _quiet_)
  * To package the project as a turn-key artefact:
    * `mvn package`
  * To run the packaged delivery:
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar`

Remark: **We are assuming here you are using a _real_ shell (e.g., anything but PowerShell on Windows)**

## Game Setup
* To start a game, please type the types of players you would like followed by the amount of them.
* * Ex: `java -jar target/piraten-karpen-jar-with-dependencies.jar --random 2 --combo 2 --seabattle 2` specifies six total players, two of each type
* Users can also specify the number of games via command line by adding the `-g` or `--game` argument followed by the number of desired games
* * Ex: `java -jar target/piraten-karpen-jar-with-dependencies.jar --random 2 --combo 2 --seabattle 2 -g 50` specifies six total players and to play 50 games


## Trace Mode
 * Trace mode allows users to view what is happening with regards to both player logic and general logic as a game progresses
 * Trace mode is available by running the compiled jar with argument `-ta` or `--traceActive`
* * Ex: `java -jar target/piraten-karpen-jar-with-dependencies.jar --random 2 --combo 2 --seabattle 2 -g 50 -ta`
 **NOTE: This WILL produce a very large output log to console if the amount of rounds played is high!**

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * < Feature operates as expected (or rather outlined if in text) by client and is functionally sound (i.e. bug free and programmer is confident in it to operate correctly) >

### Business Logic Backlog
| MVP? | Id  | Feature  | Status  |  Started  | Delivered |
| :-:  |:-:  |---       | :-:     | :-:       | :-:       |
| x   | F01 | Roll a dice |  D | 09/01/23 | 10/01/23 |
| x   | F02 | Roll eight dices |  D | 10/01/23  | 10/01/23 |
| x   | F03 | End of game with three cranes | D | 11/01/23 | 12/01/23 |
| x   | F04 | Calculate Percentage Wins after end of 42 games | D | 12/01/23 | 12/01/23 |
| x   | F05 | Player keeping random dice at their turn | D | 11/01/23 | 12/01/23 |
| x   | F06 | Score points (Counting Gold/Diamonds) | D | 11/01/23 | 12/01/23 |
| x   | F07 | Simulate games | D | 12/01/23 | 12/01/23 |

### Enhanced Business Logic Backlog
| MVP? | Id  | Feature  | Status  |  Started  | Delivered |
| :-:  |:-:  |---       | :-:     | :-:       | :-:       |
|    | F08 | Multiple dice pairs/combination rewards |  D | 18/01/23 | 19/01/23 |
|    | F09 | Player maximize combo strategy |  D | 18/01/23 | 21/01/23 |
|    | F10 | Allow user to choose player strategies via command line |  D | 20/01/23 | 21/01/23 |
|    | F11 | Add card deck |  D | 23/01/23 | 23/01/23 |
|    | F12 | Allow user to draw a card |  D | 23/01/23 | 23/01/23 |
|    | F13 | Add Sea-battle card logic |  D | 23/01/23 | 23/01/23 |
|    | F14 | Add new sea battle player strategy |  D | 23/01/23 | 23/01/23 |
|    | F15 | Add Monkey Business card logic |  D | 24/01/23 | 24/01/23 |
|    | F16 | Add island of skulls mechanic |  D | 29/01/23 | 29/01/23 |
|    | F17 | Allow user to change number of games to play | D | 29/01/23 | 29/01/23 |
