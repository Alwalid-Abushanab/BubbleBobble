package upei.cs;

import org.junit.jupiter.api.*;
import upei.cs.algs4.Quick;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BubbleTrackerTest {

    @Nested
    @DisplayName("Basic Tests")
    class BasicTests {
        Player p1 = new Player("P1", 1);
        Player p2 = new Player("P2", 2);
        Player p3 = new Player("P3", 3);
        Player p4 = new Player("P4", 4);
        Player p5 = new Player("P5", 5);
        BubbleTracker bubbleTracker = null;

        @BeforeEach
        public void init() {
            //create a new bubble tracker before each test
            bubbleTracker = new BubbleTracker();
        }

        /**
         * empty bubble tracker, returns empty strings
         */
        @Test
        public void emptyBubbleTrackerReturnsEmpty() {
            assertEquals("", bubbleTracker.bestOfTheWorst(), "best of the worst is not empty string");
            assertEquals("", bubbleTracker.worstOfTheBest(), "worst of the best is not empty string");
        }

        /**
         * Add 1 person -> should be the worstOfBest
         */
        @Test
        public void onePersonIsWorstOfBest() {
            bubbleTracker.put(p1);
            System.out.println("worst of best:" + bubbleTracker.worstOfTheBest());
            assertEquals(p1.name(), bubbleTracker.worstOfTheBest(), "worst of best is wrong");
        }

        /**
         * Add 1 person -> best of worst should return "" (empty string)
         */
        @Test
        public void onePersonBestOfWorstIsEmpty() {
            bubbleTracker.put(p1);
            System.out.println("best of worst:" + bubbleTracker.bestOfTheWorst());
            assertEquals("", bubbleTracker.bestOfTheWorst(), "best of the worst is not empty string");
        }

        /**
         * Add 2 people: -> highest rank is best of worst
         * -> lowest rank is worst of the best
         */
        @Test
        public void twoPersonBubbleTracker() {
            bubbleTracker.put(p1);
            bubbleTracker.put(p2);
            assertEquals(p2.name(), bubbleTracker.bestOfTheWorst(), "best of the worst should be P2");
            assertEquals(p1.name(), bubbleTracker.worstOfTheBest(), "worst of the best should be P1");

        }

        /**
         * Add 3 people to a BubbleTracker and check the results
         * P1, 1
         * P2, 2 <- worst of the best
         * P3, 3 <- best of the worst
         * <p>
         * Expected: bestOfWorst: C
         * Expected: worstOfBest: B
         */
        @Test
        public void threePersonLobbyTest() {
            bubbleTracker.put(p1);
            bubbleTracker.put(p2);
            bubbleTracker.put(p3);

            System.out.println("best of worst:" + bubbleTracker.bestOfTheWorst());
            System.out.println("worst of best:" + bubbleTracker.worstOfTheBest());

            assertEquals(p3.name(), bubbleTracker.bestOfTheWorst(), "best of worst is wrong");
            assertEquals(p2.name(), bubbleTracker.worstOfTheBest(), "worst of best is wrong");
        }

        /**
         * Add 3 people to a BubbleTracker
         * in descending order of rank (worst to best)
         * P3, 3
         * P2, 2
         * P1, 1
         * <p>
         * Expected: bestOfWorst: C
         * Expected: worstOfBest: B
         */
        @Test
        public void threePersonLobbyTestDescending() {
            bubbleTracker.put(p3);
            bubbleTracker.put(p2);
            bubbleTracker.put(p1);

            System.out.println("best of worst:" + bubbleTracker.bestOfTheWorst());
            System.out.println("worst of best:" + bubbleTracker.worstOfTheBest());

            assertEquals(p3.name(), bubbleTracker.bestOfTheWorst(), "best of worst is wrong");
            assertEquals(p2.name(), bubbleTracker.worstOfTheBest(), "worst of best is wrong");
        }

        /**
         * Add 4 people to a BubbleTracker and check the results
         * P1, 1
         * P2, 2 <- worst of the best
         * P3, 3 <- best of the worst
         * P4, 4
         * <p>
         * Expected: bestOfWorst: C
         * Expected: worstOfBest: B
         */
        @Test
        public void fourPersonLobbyTest() {
            bubbleTracker.put(p4);
            bubbleTracker.put(p1);
            bubbleTracker.put(p2);
            bubbleTracker.put(p3);

            System.out.println("best of worst:" + bubbleTracker.bestOfTheWorst());
            System.out.println("worst of best:" + bubbleTracker.worstOfTheBest());

            assertEquals(p3.name(), bubbleTracker.bestOfTheWorst(), "best of worst is wrong");
            assertEquals(p2.name(), bubbleTracker.worstOfTheBest(), "worst of best is wrong");
        }

        /**
         * Add 5 people to a BubbleTracker and check the results
         * P1, 1
         * P2, 2
         * P3, 3 <- worst of the best
         * P4, 4 <- best of the worst
         * P5, 5
         * <p>
         * Expected: bestOfWorst: C
         * Expected: worstOfBest: B
         */
        @Test
        public void fivePersonLobbyTest() {
            bubbleTracker.put(p4);
            bubbleTracker.put(p1);
            bubbleTracker.put(p5);
            bubbleTracker.put(p2);
            bubbleTracker.put(p3);

            System.out.println("best of worst:" + bubbleTracker.bestOfTheWorst());
            System.out.println("worst of best:" + bubbleTracker.worstOfTheBest());

            assertEquals(p4.name(), bubbleTracker.bestOfTheWorst(), "best of worst is wrong");
            assertEquals(p3.name(), bubbleTracker.worstOfTheBest(), "worst of best is wrong");
        }
    }
    @Nested
    @DisplayName("Small and Medium Dataset Tests")
    class SmMed {
        private BubbleTracker bubbleTracker = null;
        @BeforeEach
        public void init() {
            //create a new bubble tracker before each test
            bubbleTracker = new BubbleTracker();
        }

        /**
         * Load the data from tinyLobby.txt and check results
         */
        @Test
        public void tinyLobbyTest() {
            Path resourceDir = Paths.get("src", "test", "resources");
            final String DATA_FILE = resourceDir.toAbsolutePath() + "/upei/cs/data/tinyLobby.txt";
            DataLoader loader = null;
            Path p = Path.of(DATA_FILE);
            loader = new DataLoader(p);
            ArrayList<Player> players = new ArrayList<>(loader.getAllPlayers());
            final long startTime = System.currentTimeMillis();
            for (Player player : players) {
                bubbleTracker.put(player);
                bubbleTracker.worstOfTheBest();
                bubbleTracker.bestOfTheWorst();
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("Total execution time (ms): " + (endTime - startTime));
            System.out.println("best of worst:" + bubbleTracker.bestOfTheWorst());
            System.out.println("worst of best:" + bubbleTracker.worstOfTheBest());

            players.sort(Player::compareTo);
            String worstOfBest = players.get((players.size() - 1) / 2).name();
            String bestOfWorst = players.get((players.size() - 1) / 2 + 1).name();

            assertEquals(bestOfWorst, bubbleTracker.bestOfTheWorst(), "best of worst is wrong");
            assertEquals(worstOfBest, bubbleTracker.worstOfTheBest(), "worst of best is wrong");
        }

        /**
         * Load the data from mediumLobby.txt and check results
         */
        @Test
        public void mediumLobbyTest() {
            Path resourceDir = Paths.get("src", "test", "resources");
            final String DATA_FILE = resourceDir.toAbsolutePath() + "/upei/cs/data/mediumLobby.txt";
            DataLoader loader = null;
            Path p = Path.of(DATA_FILE);
            loader = new DataLoader(p);
            ArrayList<Player> players = new ArrayList<>(loader.getAllPlayers());
            loader = null;

            final long startTime = System.currentTimeMillis();
            for (Player player : players) {
                bubbleTracker.put(player);
                //just for timing purposes / simulation
                bubbleTracker.bestOfTheWorst();
                bubbleTracker.worstOfTheBest();
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("Total execution time (ms): " + (endTime - startTime));

            System.out.println("best of worst:" + bubbleTracker.bestOfTheWorst());
            System.out.println("worst of best:" + bubbleTracker.worstOfTheBest());

            players.sort(Player::compareTo);
            String worstOfBest = players.get((players.size() - 1) / 2).name();
            String bestOfWorst = players.get((players.size() - 1) / 2 + 1).name();

            assertEquals(bestOfWorst, bubbleTracker.bestOfTheWorst(), "best of worst is wrong");
            assertEquals(worstOfBest, bubbleTracker.worstOfTheBest(), "worst of best is wrong");
        }
    }



    @Nested
    @DisplayName("big tests")
    class BigTests{
        private BubbleTracker bubbleTracker = null;

        Player[] pArr = null;
        @BeforeEach
        void loadData() {
            //create a new bubble tracker before each test
            bubbleTracker = new BubbleTracker();

            Path resourceDir = Paths.get("src", "test", "resources");
            final String DATA_FILE = resourceDir.toAbsolutePath() + "/upei/cs/data/bigLobby.txt";
            DataLoader loader = null;
            Path p = Path.of(DATA_FILE);
            loader = new DataLoader(p);
            pArr = loader.getAllPlayers().toArray(new Player[0]);
        }

        /**
         * Load the data from bigLobby.txt and check results
         */
        @Test
        public void bigLobbyTestAccuracy() {
            final long startTime = System.currentTimeMillis();
            for (Player player : pArr) {
                bubbleTracker.put(player);
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("Total execution time: " + (endTime - startTime));

            System.out.println("best of worst:" + bubbleTracker.bestOfTheWorst());
            System.out.println("worst of best:" + bubbleTracker.worstOfTheBest());

            var worstOfBest = (Player) Quick.select(pArr, (pArr.length-1)/2);
            var bestOfWorst =(Player) Quick.select(pArr, (pArr.length - 1) / 2 + 1);

            assertEquals(bestOfWorst.name(),bubbleTracker.bestOfTheWorst(), "best of worst is wrong");
            assertEquals(worstOfBest.name(), bubbleTracker.worstOfTheBest(), "worst of best is wrong");
        }
        /**
         * Load the data from bigLobby.txt and check results
         */
        @Test
        @Timeout(value=1, unit=SECONDS)
        public void bigTestLobbyFastEnough() {
            final long startTime = System.currentTimeMillis();
            for (Player player : pArr) {
                bubbleTracker.put(player);
                bubbleTracker.worstOfTheBest();
                bubbleTracker.bestOfTheWorst();
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("Less accurate than JUnit estimated total execution time: " + (endTime - startTime));
        }

    }

    @Nested
    @DisplayName("big tests with 25K players")
    class Big25KTests{
        private BubbleTracker bubbleTracker = null;

        Player[] pArr = null;
        @BeforeEach
        void loadData() {
            //create a new bubble tracker before each test
            bubbleTracker = new BubbleTracker();

            Path resourceDir = Paths.get("src", "test", "resources");
            final String DATA_FILE = resourceDir.toAbsolutePath() + "/upei/cs/data/big25KLobby.txt";
            DataLoader loader = null;
            Path p = Path.of(DATA_FILE);
            loader = new DataLoader(p);
            pArr = loader.getAllPlayers().toArray(new Player[0]);
        }

        /**
         * Load the data from bigLobby.txt and check results
         */
        @Test
        public void big25KLobbyTestAccuracy() {
            final long startTime = System.currentTimeMillis();
            for (Player player : pArr) {
                bubbleTracker.put(player);
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("Total execution time: " + (endTime - startTime));

            System.out.println("best of worst:" + bubbleTracker.bestOfTheWorst());
            System.out.println("worst of best:" + bubbleTracker.worstOfTheBest());

            var worstOfBest = (Player) Quick.select(pArr, (pArr.length-1)/2);
            var bestOfWorst =(Player) Quick.select(pArr, (pArr.length - 1) / 2 + 1);

            assertEquals(bestOfWorst.name(),bubbleTracker.bestOfTheWorst(), "best of worst is wrong");
            assertEquals(worstOfBest.name(), bubbleTracker.worstOfTheBest(), "worst of best is wrong");
        }
        /**
         * Load the data from big25KLobby.txt and check results
         */
        @Test
        @Timeout(value=4, unit=SECONDS)
        public void big25KTestLobbyFastEnough() {
            final long startTime = System.currentTimeMillis();
            for (Player player : pArr) {
                bubbleTracker.put(player);
                bubbleTracker.worstOfTheBest();
                bubbleTracker.bestOfTheWorst();
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("Less accurate than JUnit estimated total execution time: " + (endTime - startTime));
        }

    }

    @Nested
    @DisplayName("big tests with 100K players")
    class Big100KTests{
        private BubbleTracker bubbleTracker = null;

        Player[] pArr = null;
        @BeforeEach
        void loadData() {
            //create a new bubble tracker before each test
            bubbleTracker = new BubbleTracker();

            Path resourceDir = Paths.get("src", "test", "resources");
            final String DATA_FILE = resourceDir.toAbsolutePath() + "/upei/cs/data/Large100KLobby.txt";
            DataLoader loader = null;
            Path p = Path.of(DATA_FILE);
            loader = new DataLoader(p);
            pArr = loader.getAllPlayers().toArray(new Player[0]);
        }

        /**
         * Load the data from bigLobby.txt and check results
         */
        @Test
        public void big100KLobbyTestAccuracy() {
            final long startTime = System.currentTimeMillis();
            for (Player player : pArr) {
                bubbleTracker.put(player);
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("Total execution time: " + (endTime - startTime));

            System.out.println("best of worst:" + bubbleTracker.bestOfTheWorst());
            System.out.println("worst of best:" + bubbleTracker.worstOfTheBest());

            var worstOfBest = (Player) Quick.select(pArr, (pArr.length-1)/2);
            var bestOfWorst =(Player) Quick.select(pArr, (pArr.length - 1) / 2 + 1);

            assertEquals(bestOfWorst.name(),bubbleTracker.bestOfTheWorst(), "best of worst is wrong");
            assertEquals(worstOfBest.name(), bubbleTracker.worstOfTheBest(), "worst of best is wrong");
        }
        /**
         * Load the data from big100KLobby.txt and check results
         */
        @Test
        public void big100KTestLobbyFastEnough() {
            final long startTime = System.currentTimeMillis();
            for (Player player : pArr) {
                bubbleTracker.put(player);
                bubbleTracker.worstOfTheBest();
                bubbleTracker.bestOfTheWorst();
            }
            final long endTime = System.currentTimeMillis();
            System.out.println("Less accurate than JUnit estimated total execution time: " + (endTime - startTime));
        }

    }

}