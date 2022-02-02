package upei.cs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Load the player data from file
 * Serve that data up in Blocks of Players
 */
public class DataLoader {

    private List<Player> players;
    private int count;
    public static final int BLOCK_SIZE = 1000;

    /**
     * Public constructor
     * @param path gives the path to a data file of players
     */
    public DataLoader(Path path) {
        count = 0;
        try {
            loadAll(path);
        }catch(IOException e) {
            e.printStackTrace();
            System.err.println("Error opening File");
        }
    }

    /**
     * load all the players from a given file
     * @param filePath the path to the file
     * @throws IOException if the file doesn't exist
     */
    private void loadAll(Path filePath) throws IOException {
        players = Files.lines(filePath)
                .map(DataLoader::playerFromString)
                .toList();
    }

    /**
     * Convert a line from the data file into a Player object
     * @param line a line given by rank name(with possible spaces)
     * @return the equivalent Player object
     */
    private static Player playerFromString(String line) {
        Scanner scan = new Scanner(line);
        int rank = scan.nextInt();
        String name = scan.nextLine();
        return new Player(name, rank);
    }

    /**
     * Return a list of the next players
     * simulates them joining the lobby
     * @return the list of players
     */
    public List<Player> nextBlockOfPlayers() {
        int previous = count;
        count += BLOCK_SIZE;
        if (count >= players.size()) {
            count = players.size();
        }
        return players.subList(previous, count);
    }

    public List<Player> getAllPlayers() {
        return players;
    }

    public int getCount() {
        return count;
    }

    public int totalSize() {
        return players.size();
    }
}
