package upei.cs;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import eu.hansolo.tilesfx.tools.Helper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class LobbyController {
    public static final String DATA_FILE = "upei/cs/data/big25kLobby.txt";

    public FlowGridPane flowpane;
    private Tile gaugeSparklineTile;
    private Tile circularProgressTile;
    private Tile switchTile;
    private ScheduledExecutorService executor;
    private ScheduledFuture<?> playerLoaderHandle;

    //a few things we'll update when the switch is on
    public Label bestOfTheWorst;
    public Label worstOfTheBest;
    private long beforeTime;
    private long afterTime;

    //the DataStructure that tracks who is on the bubble
    private BubbleTracker bubbleStructure;
    private DataLoader loader;

    protected void onSwitchOn() {
        final Runnable  playerLoader = () -> {

            List<Player> players = loader.nextBlockOfPlayers();

            if (players.isEmpty()) {
                return;
            }
            beforeTime = System.nanoTime();
            String bestOfWorstPlayers ="";
            String worstOfBestPlayers ="";
            for(var player: players) {
                bubbleStructure.put(player);
                //even though we don't use this we want it in the timing
                bestOfWorstPlayers = bubbleStructure.bestOfTheWorst();
                worstOfBestPlayers = bubbleStructure.worstOfTheBest();
            }
            afterTime = System.nanoTime();
            double duration = (afterTime - beforeTime)/100000.0;

            System.out.println(duration);

            final String best = bestOfWorstPlayers;
            final String worst = worstOfBestPlayers;

            System.out.println(duration);
            System.out.println(best);
            System.out.println(worst);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    bestOfTheWorst.setText("best of worst: " + best);
                    worstOfTheBest.setText("worst of best: " + worst);

                    gaugeSparklineTile.setValue(duration);
                    circularProgressTile.setValue( 100*((double)loader.getCount())/loader.totalSize());
                }
            });

        };
        executor = Executors.newScheduledThreadPool(1);
        playerLoaderHandle = executor.scheduleAtFixedRate(playerLoader, 0, 1, TimeUnit.SECONDS);
    }

    protected void onSwitchOff() {
        playerLoaderHandle.cancel(false);
        executor.shutdown();
    }


    @FXML
    private void initialize() {
        URL resource = LobbyController.class.getClassLoader().getResource(DATA_FILE);
        try {
            Path p = Path.of(resource.toURI());
            loader = new DataLoader(p);
        }catch(URISyntaxException e) {
            e.printStackTrace();
        }
        bubbleStructure = new BubbleTracker();
        createGaugeSparklineTile();
        createProgressTile();
        createSwitchTile();
        flowpane.add(gaugeSparklineTile, 1, 1);
        flowpane.add(circularProgressTile, 3, 1);
        flowpane.add(switchTile, 2, 1);
    }


    /**
     * The following three methods are taken from the TilesFX demo project:
     * https://github.com/HanSolo/tilesfx/blob/master/src/main/java/eu/hansolo/tilesfx/Demo.java
     */
    private void createSwitchTile() {
        switchTile = TileBuilder.create()
                .skinType(Tile.SkinType.SWITCH)
                .prefSize(100, 300)
                .title("Open the Lobby")
                .build();

        switchTile.setOnSwitchReleased(e-> {
            if (switchTile.isActive()) {
                onSwitchOn();
            } else {
                onSwitchOff();
            }
        });
    }

    private void createGaugeSparklineTile() {
        gaugeSparklineTile = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE_SPARK_LINE)
                .prefSize(300, 300)
                .title("Performance: time in ms to load a block of players")

                .animated(true)
                .textVisible(false)
                .averagingPeriod(20)
                .autoReferenceValue(true)


                .barColor(Tile.YELLOW_ORANGE)
                .barBackgroundColor(Color.rgb(255, 255, 255, 0.1))
                .sections(
                        new eu.hansolo.tilesfx.Section(0, 33, Tile.GREEN),
                        new eu.hansolo.tilesfx.Section(33, 67, Tile.YELLOW),
                        new eu.hansolo.tilesfx.Section(67, 100, Tile.LIGHT_RED))
                .sectionsVisible(true)
                .highlightSections(true)
                .strokeWithGradient(true)
                .fixedYScale(false)
                .gradientStops(new Stop(0.0, Tile.LIGHT_GREEN),
                        new Stop(0.33, Tile.LIGHT_GREEN),
                        new Stop(0.33,Tile.YELLOW),
                        new Stop(0.67, Tile.YELLOW),
                        new Stop(0.67, Tile.LIGHT_RED),
                        new Stop(1.0, Tile.LIGHT_RED))

                .valueColor(Color.RED)
                .unit("ms")
                .smoothing(true)
                .build();
    }

    private void createProgressTile() {

        circularProgressTile = TileBuilder.create()
                .skinType(Tile.SkinType.CIRCULAR_PROGRESS)
                .prefSize(300, 300)
                .title("Percentage of total lobby loaded")
                //.text("Some text")
                .unit(Helper.PERCENTAGE)
                .build();

    }
}