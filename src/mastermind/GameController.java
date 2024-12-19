package mastermind;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameController {
    private static final int number_of_selected_colors=4;
    private static final int max_of_attempts=10;
    private List<Color> colorList= Arrays.asList(
            Color.PURPLE,Color.GREEN,Color.BLUE,Color.RED,Color.YELLOW,Color.ORANGE
    );
    private List<Color> real_colors;
    private int attempts;
    private Color[]guessed_colors=new Color[number_of_selected_colors];
    private VBox mainlayout;
    private GridPane guessgrid;
    private Label feedbacklabel;
    private int seconds=0;
    private int minutes=0;
    private Timeline timer;
    private Label timerlabel;
    private Label resultlabel;
    public GameController() {
        attempts=0;
        Random_mixed_colors();

    }
    private void Random_mixed_colors() {
        real_colors =new ArrayList<>(colorList);
        Collections.shuffle(real_colors);
        real_colors=real_colors.subList(0,number_of_selected_colors);
    }
    VBox CreateGameLayout() {
        mainlayout = new VBox(20);
        mainlayout.setAlignment(Pos.CENTER);
        timerlabel = new Label("00:00");
        timerlabel.setId("timerlabel");
        resultlabel = new Label();
        resultlabel.setFont(new javafx.scene.text.Font("Arial",16));
        resultlabel.setTextFill(Color.BLACK);
        Label Hellotitlelabel=new Label("Welcome to MasterMind ♡");
        Hellotitlelabel.setFont(new javafx.scene.text.Font("Arial",24));
        Hellotitlelabel.setTextFill(Color.WHITE);
        Label instructionlabel=new Label("You have 10 attempts to guess real colors");
        instructionlabel.setFont(new javafx.scene.text.Font("Arial",12));
        instructionlabel.setTextFill(Color.WHITE);
        guessgrid = new GridPane();
        guessgrid.setHgap(16);
        guessgrid.setVgap(16);
        guessgrid.setAlignment(Pos.CENTER);
        for(int i=0;i<number_of_selected_colors;i++) {
            Button colorbutton = new Button("Choose color");
            colorbutton.setPrefSize(120,50);
            colorbutton.setId("colorbutton");
            final int index=i;
            colorbutton.setOnAction(e -> PickColor(colorbutton,index));
            guessgrid.add(colorbutton,i,0);


        }
        Button submitbutton = new Button("Submit");
        submitbutton.setId("submitbutton");
        submitbutton.setOnAction(e ->SubmitGuess());
        feedbacklabel = new Label();
        feedbacklabel.setFont(new javafx.scene.text.Font("Arial",14));
        feedbacklabel.setTextFill(Color.WHITE);
        HBox timerbox = new HBox(20);
        timerbox.setAlignment(Pos.TOP_RIGHT);
        timerbox.getChildren().add(timerlabel);
        mainlayout.getChildren().addAll(Hellotitlelabel,instructionlabel, timerbox,guessgrid,submitbutton,feedbacklabel,resultlabel);
        return mainlayout;
    }

    private void SubmitGuess() {
        if(attempts<max_of_attempts){
            boolean guessed=true;
            for(Color guessedcolor:guessed_colors){
                if (guessedcolor==null) {
                    guessed=false;
                    break;
                }
            }
            if(!guessed){
                feedbacklabel.setText("You haven't chosen four colors yet.");
                feedbacklabel.setTextFill(Color.RED);
                return;
            }
            String feedback=GiveFeedback(guessed_colors);
            feedbacklabel.setText(feedback);
            attempts++;
            if(IsCorrectGuess(guessed_colors)){
                feedbacklabel.setText("🎉Congratulations! You guessed the correct colors!🎉");
                feedbacklabel.setTextFill(Color.GREEN);
                EndGame(true);
            }
            else if (attempts==max_of_attempts){
                feedbacklabel.setText("You guessed wrong 😔.Please try again.");
                feedbacklabel.setTextFill(Color.WHITE);
                EndGame(false);
            }
        }
    }

    private void EndGame(boolean win) {
        timer.stop();
        if(win){
            resultlabel.setText("You won in : "+String.format("%02d:%02d",minutes,seconds));
            resultlabel.setTextFill(Color.GREEN);
        }else {
            resultlabel.setText("You lost after : "+String.format("%02d:%02d",minutes,seconds));
            resultlabel.setTextFill(Color.RED);
        }
    }

    private boolean IsCorrectGuess(Color[] guessedColors) {
        return Arrays.equals(guessedColors,real_colors.toArray());
    }

    private String GiveFeedback(Color[] guessedColors) {
        int correct_position=0;
        int correct_color=0;
        for (int i=0;i<number_of_selected_colors;i++) {
            if (guessedColors[i] != null && guessedColors[i].equals(real_colors.get(i))) {
                correct_position++;
            }
            else if (guessedColors[i] != null && real_colors.contains(guessedColors[i])) {
                correct_color++;
            }
        }
        return "Correct Position : " +correct_position+ "\n Correct Color but wrong position : " +correct_color;
    }

    public void PickColor(Button colorbutton, int index) {
        Color chosen_color=ChoseRandomColors();
        colorbutton.setStyle("-fx-background-color:"+toHexString(chosen_color)+";");
        guessed_colors[index]=chosen_color;
    }

    private String toHexString(Color c) {
        int r=(int) (c.getRed() * 255);
        int g=(int) (c.getGreen() * 255);
        int b=(int) (c.getBlue() * 255);
        return String.format("#%02x%02x%02x",r,g,b );
    }

    private Color ChoseRandomColors() {
        Random r=new Random();
        return colorList.get(r.nextInt(colorList.size()));
    }

    public void StartTime(){
        timer = new Timeline(new KeyFrame(Duration.seconds(1),e->{
            seconds++;
            if(seconds==60){
                seconds=0;
                minutes++;
            }
            UpdateTimerlable();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
    private void UpdateTimerlable() {
        String time=String.format("%02d:%02d",minutes,seconds);
        timerlabel.setText(time);
    }
}
