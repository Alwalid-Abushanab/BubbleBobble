package upei.cs;

/**
 * A Player is comprised of a name and rank
 * use a record (instead of a class) to gain all the getters, equals and hashCode
 * requirements automatically
 */
public record Player(String name, int rank) implements Comparable<Player>{

    @Override
    public int compareTo(Player o) {
        return Integer.compare(this.rank, o.rank);
    }
}