package upei.cs;

import upei.cs.algs4.*;

/**
 * Class to track the two middle players:
 * 1. the lowest ranked player (best) amoung the half of players that are highest ranked (worst)
 *  (best of the worst)
 *
 * 2. the highest ranked player (worst) amoung the half of players that are lowest ranked (best)
 *  (worst of the best)
 *
 *  Remember if there is an odd number of players then the median player is considered to
 *  be the worst of the best.
 *
 *  Remember: low rankings are good, high rankings are bad.
 *
 *  DO NOT alter the method headers for the public constructor,
 *  nor put or bestOfTheWorst or worstOfTheBest methods
 *
 *  Unit-testing relies on their exact method header matching their original state
 *
 */
public class BubbleTracker {

    MinPQ<Player> worstPlayers = new MinPQ<>();
    MaxPQ<Player> bestPlayers = new MaxPQ<>();

    /**
     * Public Constructor Don't change this
     */
    public BubbleTracker() {

    }


    /**
     * Put a player with name and rank into the data structure
     * requires the player.name and player.rank are distinct
     * @param player the player being inserted into the data structure
     */
    public void put(Player player){
        //if it is the first player to be added
        if(bestPlayers.isEmpty())
            bestPlayers.insert(player);
        else if(worstPlayers.isEmpty())//if it's the second player
        {
            //check if the new player has a lower rank than the previously added one, then add it to the appropriate PQ
            if(player.rank() < (bestPlayers.max()).rank())
            {
                worstPlayers.insert(bestPlayers.max());
                bestPlayers.delMax();
                bestPlayers.insert(player);
            }
            else
                worstPlayers.insert(player);
        }
        else
        {
            //if the player to be added is going to make the number of players in the game odd
            if((bestPlayers.size()+ worstPlayers.size()) % 2 == 0)
            {
                //check to which PQ the new player should be added and add it.
                if(player.rank() < (bestPlayers.max()).rank())
                {
                    bestPlayers.insert(player);
                }
                else if(player.rank() > (worstPlayers.min()).rank())
                {
                    worstPlayers.insert(player);
                    Player temp = worstPlayers.min();
                    bestPlayers.insert(temp);
                    worstPlayers.delMin();
                }
                else
                {
                    bestPlayers.insert(player);
                }
            }
            else
            {
                //check where the new player should be added when the number of players is currently odd, and take a player out of a PQ and add it to the other one if necessary.
                if(player.rank() > (worstPlayers.min()).rank())
                {
                    worstPlayers.insert(player);
                }
                else if(player.rank() < (bestPlayers.max()).rank())
                {
                    bestPlayers.insert(player);
                    Player temp = bestPlayers.max();
                    bestPlayers.delMax();
                    worstPlayers.insert(temp);
                }
                else
                {
                    worstPlayers.insert(player);
                }
            }
        }
    }

    /**
     * Return the name of the driver who has the best ranking (lowest numeric value)
     * in the bottom half of all drivers.
     *
     * In the case of an odd number of drivers (>=3) this will return the ranking immediately worse (higher value)
     * than the median value. In the case of 1 driver only, that driver will be returned.
     *
     * Recall that the best possible ranking is 1 and higher numbers translate into worse rankings
     *
     * @return the best ranking player (lowest numeric score) in the bottom 50\% of player or empty string in the case their
     * is no such player
     */
    public String bestOfTheWorst()
    {
        //it has access of O(1) to get the first element in the PQ
        if(worstPlayers.size() < 1)
            return "";
        else
            return (worstPlayers.min()).name();

    }

    /**
     * Return the name of the player who has the poorest ranking (largest numerical ranking number)
     * in the top half of all players.
     *
     * In the case there are an odd number of players
     * this will return the exact median unless there is only one driver
     * in which case this returns empty string.
     *
     * Recall the top ranked player possible has ranking 1
     * @return the worst ranking player in the top 50\% of players or empty string if
     * no such driver exists
     */
    public String worstOfTheBest() {
        //it has access of O(1) to get the first element in the PQ
        if(bestPlayers.size() < 1)
            return "";
        else
            return (bestPlayers.max()).name();
    }
}
