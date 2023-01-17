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

## Trace Mode
 * Trace mode is available by running the main method in PiratenKarpen with argument **'traceActive'**
 **NOTE: This WILL produce a very large output log to console if the amount of rounds played is high!**

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * < Feature operates as expected (or rather outlined if in text) by client and is functionally sound (i.e. bug free and programmer is confident in it to operate correctly) >

### Backlog 

| MVP? | Id  | Feature  | Status  |  Started  | Delivered |
| :-:  |:-:  |---       | :-:     | :-:       | :-:       |
| x   | F01 | Roll a dice |  S | 01/01/23 |  |
| x   | F02 | Roll eight dices  |  B (F01) |   |
| x   | F03 | Select how many games as command-line arg.  |  P  |   |
| x   | F04 | end of game with three cranes | P | |
| x   | F05 | Player keeping random dice at their turn | B (F02) | | 
| x   | F06 | Score points: 3-of-a-kind | B (F04) | | 
| ... | ... | ... |

### Updated Business Logic Backlog
| MVP? | Id  | Feature  | Status  |  Started  | Delivered |
| :-:  |:-:  |---       | :-:     | :-:       | :-:       |
| x   | F01 | Roll a dice |  D | 09/01/23 | 10/01/23 |
| x   | F02 | Roll eight dices |  D | 10/01/23  | 10/01/23 |
| x   | F03 | End of game with three cranes | D | 11/01/23 | 12/01/23 |
| x   | F04 | Calculate Percentage Wins after end of 42 games | D | 12/01/23 | 12/01/23 |
| x   | F05 | Player keeping random dice at their turn | D | 11/01/23 | 12/01/23 |
| x   | F06 | Score points (Counting Gold/Diamonds) | D | 11/01/23 | 12/01/23 |
| x   | F07 | Simulate games | D | 12/01/23 | 12/01/23 |

