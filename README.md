# Bubble Bobble

In this assignment you will implement a part of the *lobby* for a racing game. In this racing game there are up to 25,000 drivers who enter a race. Each driver has a unique ranking based on their performance in the game. For example a driver might be ranked 1 (meaning they are the best player in the whole game), or they could be ranked 10,000 meaning they are the 10,000th best player.

When a driver chooses to enter a race they will first be put into a lobby screen while they wait for the race to start. In this lobby area they will find out which type of car they will use for the race.

The racing game is designed to make the races more even. Thus poorly ranked drivers are given fast cars, while highly ranked drivers are given slow cars. In fact, the top half of the drivers in a race will be racing in a slow (but reliable): `Honda Odyssey '03`,  while the bottom half of the racers will be driving the much faster: `Renault Espace F1`.

*Who doesn't like a race involving only minivans?*.

In the event the race has an odd number of racers: the median ranked racer will race in the slow vehicle (the `Honda Odyssey '03`).

The game designers would like to add some flair to the lobby screen, while drivers are waiting for the race to start. Therefore, they would like you to report which drivers are currently the top ranked of the bottom half of the drivers and the lowest ranked of the top half of the drivers. These drivers are considered to be **on the bubble**.

Consider, for example, a race with the following drivers (and their ranking).

|Driver | Ranking | Potential Car |
|-------|---------| --- |
| GentleGiant | 4 | Odyssey
| SlicedBread | 8 | Odyssey |
|:arrow_heading_up: Top ranking drivers |---|Bottom ranking drivers:arrow_heading_down: |
| Billion\$Man | 22|  Renault |
| Mario       | 200| Renault

In this example the top ranked drivers are GentleGiant and SlicedBread so they expect to race in the slow vehicle. While the worst ranked drivers are Billion\$Man and Mario. They expect they will race in the fast vehicle. However SlicedBread and Billion\$Man are considered to be `on the bubble` as they are the drivers who could potentially change cars (move up into the top half, or move down into the bottom half) depending on who joins the lobby next.

Consider the situation that a new racer joins the lobby. The cars for those drivers who are on the bubble might change. At the moment Billion\$Man is the highest ranking of the low ranking racers. If a racer with a lower ranking than 22 (the ranking of Billion\$Man) joins the group, then Billion\$Man will join the top half (as per the rule the anyone ranked at exactly the median joins the top half). See below what happens when ClumsyPup with ranking 44 joins the lobby:

|Driver | Ranking | Potential Car |
|-------|---------| --- |
| GentleGiant | 4 | Odyssey
| SlicedBread | 8 | Odyssey |
| Billion$Man | 22|  Odyssey |
|:arrow_heading_up: Top ranking drivers |---|Bottom ranking drivers:arrow_heading_down: |
| ClumsyPup | 44 | Renault |
| Mario       | 200| Renault

Your task is to create a data structure that can efficiently report the two drivers who are on the bubble each time a new person joins the racing lobby.

Notes:
- Rankings are unique integer values (there are no two drivers with the same ranking)
- Driver Names are unique String values (there are no two drivers with the same name)
- Rankings fall between 1 and 5 million

- there can be up to 25,000 racers in a given race (in the lobby).

- no one can leave the lobby once they enter it

- your program will never be asked to report any drivers other than the two drivers who are on the bubble

You are provided a GUI to make the assignment a bit more interesting. It simulates adding players to the lobby and times your algorithm to see how fast it is. But the GUI is not particularly important just a fun way to see your code in action.

## Testing

In the test directory are a number of tests that the graders will run to ensure the accuracy and efficiency of your code. This is where you can debug and test your software more easily.

There are a lot of source files with this assignment but don't be put off by that, they are mostly from our algorithms4 text book. I added them as source so you could modify them if you wanted. They are all found in the algs4 directory under src. There are some other files that just pertain to the GUI code and some helper files, you do not need to adjust any of these.

There is no Github auto-grading on this assignment.

## Deliverable

The only file you **must** develop is the `BubbleTracker` file. You must complete this so that it allows access to the worstOfTheBest and the bestOfTheWorst, i.e., the two middle players in the game (they are on the bubble).

You can develop a novel data structure or modify some of the ones from the algs4 directory to get the exact data structure of your liking. But you are not required to do this. You can use the data structures from the algs4 directory, or the JCF as they are without modification and solve the assignment without issue.

## Bonus

However to encourage experimentation and as an added bonus the students having the most efficient BubbleTrackers will receive a bonus point on the assignment (and a bonus prize (which may or may not carry any worth)).

## Textbook hint:

Our textbook provides this hint for how to solve this problem:
- you could solve it using two priority queues

```ascii
  ---------------                    
   \  Pq of    /                     
    \ best    /                      
     \players/                       
      \     /                        
       \   /                         
        \ /                          
         -                           
        / \                          
       /   \                         
      /     \                        
     /       \                       
    /  PQ of  \                      
   /   worst   \                     
  /    players  \                    
 /               \                   
/------------------
```

Of course our text book has also given a number of efficiency improvements for the PQ but also some other Quick algorithms that might be of interest to try.

You are encouraged to experiment.

For reference the sample solution for this assignment is less than 40 lines of code.


## Grading

No Github autograding for this assignment (we have a fixed amount of compute time they'll give me for free and I don't want to use it all up on this assignment :(  )

Run the Tests locally on your machine to see how functional your code is.

|Gradeable | Points | Notes |
| --- | --- | --- |
| Readability | 1 | methods and classes are commented? variable names are consistent with java expectations |
| Design Choices | 2 | Is your choice of data structure clear? Have you divided tasks, used short clear methods, etc |
| Efficiency | 1 | Compared to other students how efficient is your solution |
| Functionality | 6 | How many test cases pass? |
| Bonus | 1 | Do you have one of top fastest programs |


## Youtube explanation:

https://youtu.be/FdqgzeRCp8Y
