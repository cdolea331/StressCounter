package com.carlosolea;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import java.io.*;
import java.util.Random;

public class Controller {
    private enum Age {FEUDAL_AGE, CASTLE_AGE, IMPERIAL_AGE }
    @FXML
    private Spinner p1StressMeter, p2StressMeter, p3StressMeter, p4StressMeter, incrementMeter;
    private Spinner[] spinners = new Spinner[4];
    @FXML
    private Label player1Status, player2Status, player3Status, player4Status, player1Name, player2Name,
            player3Name, player4Name;
    private Label[] statusLabels = new Label[4];
    private Label[] nameLabels = new Label[4];

    @FXML
    private Button p1Reset, p2Reset, p3Reset, p4Reset, resetAll;
    private Button[] buttons = new Button[5];

    @FXML
    private ChoiceBox ageChoiceBox;
    @FXML
    private ChoiceBox playerCountBox;
    @FXML
    private CheckBox player1Active,player2Active,player3Active,player4Active,toggleAllActive;
    private CheckBox[] activeBoxes = new CheckBox[5];
    private boolean[] playerVirtue = {false, false, false, false};
    private boolean[] playerAfflicted = {false, false, false, false};




    @FXML
    public void initialize(){
        spinners[0] = p1StressMeter;
        spinners[1] = p2StressMeter;
        spinners[2] = p3StressMeter;
        spinners[3] = p4StressMeter;
        statusLabels[0] = player1Status;
        statusLabels[1] = player2Status;
        statusLabels[2] = player3Status;
        statusLabels[3] = player4Status;
        nameLabels[0] = player1Name;
        nameLabels[1] = player2Name;
        nameLabels[2] = player3Name;
        nameLabels[3] = player4Name;
        buttons[0] = p1Reset;
        buttons[1] = p2Reset;
        buttons[2] = p3Reset;
        buttons[3] = p4Reset;
        buttons[4] = resetAll;
        activeBoxes[0] = player1Active;
        activeBoxes[1] = player2Active;
        activeBoxes[2] = player3Active;
        activeBoxes[3] = player4Active;
        activeBoxes[4] = toggleAllActive;



        loadState();

        ageChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Age[] ages = Age.values();
                ageChange(ages[newValue.intValue()]);
            }
        });

        for(Button button: buttons){
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int player = button.getId().charAt(1)-49;
                    if(player < 4 && player > -1) {
                        playerReset(player);
                    }else {
                        for (int i = 0; i < 4; i++)
                            playerReset(i);
                    }
                }
            });
        }

        for(Spinner spinner : spinners){
            spinner.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    int playerNum = spinner.getId().charAt(1)-49;
                    int currentMaxValue = ((SpinnerValueFactory.IntegerSpinnerValueFactory)spinner.getValueFactory()).getMax();
                    if((int)newValue > currentMaxValue){
                        spinner.increment((currentMaxValue));
                    }
                    if((int)newValue >= (currentMaxValue/2)){
                        resolveTest(playerNum, currentMaxValue);
                    }
                    if((int)newValue == currentMaxValue){
                        heartAttack(playerNum);
                    }

                }
            });
        }

        activeBoxes[4].selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                for (int i = 0; i < 4; i++) {
                    activeBoxes[i].selectedProperty().set(newValue);
                }
            }
        });

    }//End init

    @FXML
    public void ageChange(Age age){
        switch (age){
            case FEUDAL_AGE:
                for(Spinner spinner : spinners){
                    if((int)spinner.getValue() > 20){
                        spinner.decrement(((int)spinner.getValue() - 20));
                    }
                    spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20,
                            (int)spinner.getValue()));
                }
                break;
            case CASTLE_AGE:
                for(Spinner spinner : spinners){
                    if((int)spinner.getValue() > 40){
                        spinner.decrement(((int)spinner.getValue() - 40));
                    }
                    spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 40,
                            (int)spinner.getValue()));
                }
                break;
            case IMPERIAL_AGE:
                for(Spinner spinner : spinners){
                    if((int)spinner.getValue() > 60){
                        spinner.decrement(((int)spinner.getValue() - 60));
                    }
                    spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60,
                            (int)spinner.getValue()));
                }
                break;
            default:

        }//End switch
    }//End ageChange

    public void resolveTest(int playerNum, int currentMax){
        Random rng = new Random();
        int roll = rng.nextInt(100);
        String affliction;
        if(!playerAfflicted[playerNum] && !playerVirtue[playerNum]){
           if(rng.nextInt(100) < 24/(Integer.parseInt(playerCountBox.getValue().toString()))){
                if (roll < 20) {
                    affliction = "COURAGEOUS";
                } else if (roll < 40 && roll >= 20) {
                    affliction = "FOCUSED";
                }else if (roll < 60 && roll >= 40) {
                    affliction = "BLESSED";
                }else if (roll < 80 && roll >= 60) {
                    affliction = "QUICK THINKER";
                }
                else {
                    affliction = "INSPIRATIONAL";
                }
                setVirtue(playerNum, affliction);
            }else {
               if (roll < 20) {
                   affliction = "ABUSIVE";
               } else if (roll < 40 && roll >= 20) {
                   affliction = "DELUSIONAL";
               }else if (roll < 60 && roll >= 40) {
                   affliction = "HOPELESS";
               }else if (roll < 80 && roll >= 60) {
                   affliction = "TRAITOROUS";
               }
               else {
                   affliction = "PARANOID";
               }
               setAffiliction(playerNum, affliction);
           }
        }//End if

    }//End resolveTest

    public void heartAttack(int playerNum){
        if(!playerVirtue[playerNum]) {
            statusLabels[playerNum].setText("HEART ATTACK!!!");
            statusLabels[playerNum].setScaleY(1.6);
            statusLabels[playerNum].setTextFill(Color.DARKRED);
        }
    }
    @FXML
    public void playerReset(int player){
        statusLabels[player].setText("Doing fine");
        statusLabels[player].setScaleY(1.0);
        statusLabels[player].setTextFill(Color.BLACK);
        spinners[player].decrement((int)spinners[player].getValue());
        playerAfflicted[player] = false;
        playerVirtue[player] = false;
    }
    @FXML
    public void saveState(){

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("Stress_save.txt"))){
            bw.write(ageChoiceBox.getValue() + "," + playerCountBox.getValue() + "\n");
            for(int i = 0; i < 4; i++){
                bw.write(nameLabels[i].getText() + "," + Integer.parseInt(spinners[i].getValue().toString()) + "," +
                playerAfflicted[i] + "," + playerVirtue[i] + "," + statusLabels[i].getText() + "\n");
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void loadState(){
        try(BufferedReader br = new BufferedReader(new FileReader("Stress_save.txt"))){
            String[] input;
            input = br.readLine().split(",");
            Age age;
            switch (input[0].toUpperCase()){
                case "FEUDAL AGE":
                    age = Age.FEUDAL_AGE;
                    ageChoiceBox.getSelectionModel().select(0);
                    break;
                case "CASTLE AGE":
                    age = Age.CASTLE_AGE;
                    ageChoiceBox.getSelectionModel().select(1);
                    break;
                case "IMPERIAL AGE":
                    age = Age.IMPERIAL_AGE;
                    ageChoiceBox.getSelectionModel().select(2);
                    break;
                default:
                    age = Age.FEUDAL_AGE;

            }

            playerCountBox.getSelectionModel().select(Integer.parseInt(input[1])-1);
            for(int i = 0; i < 4; i++) {
                input = br.readLine().split(",");
                nameLabels[i].setText(input[0]);
                spinners[i].increment((int)Integer.parseInt(input[1]));
                if(Boolean.parseBoolean(input[2])){
                    setAffiliction(i, input[4]);
                    if(input[4].toUpperCase().equals("HEART ATTACK!!!") ){
                        statusLabels[i].setTextFill(Color.DARKRED);
                    }
                }else if(Boolean.parseBoolean(input[3])){
                    setVirtue(i, input[4]);
                }else {
                    statusLabels[i].setText(input[4]);
                }
            }
            ageChange(age);


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void setVirtue(int player, String virtue){
        statusLabels[player].setTextFill(Color.GOLD);
        statusLabels[player].setScaleY(1.3);
        statusLabels[player].setText(virtue);
        spinners[player].decrement(((SpinnerValueFactory.IntegerSpinnerValueFactory)spinners[player].getValueFactory()).getMax());
        playerVirtue[player] = true;

    }
    private void setAffiliction(int player, String affliction){

            statusLabels[player].setTextFill(Color.RED);
            statusLabels[player].setScaleY(1.3);
            statusLabels[player].setText(affliction);
            playerAfflicted[player] = true;


    }

    @FXML
    private void teamIncrement(){
        for(int i = 0; i < 4; i++){
            if(activeBoxes[i].isSelected()){
                spinners[i].increment((int)incrementMeter.getValue());
            }
        }
    }
    @FXML
    private void teamDecrement(){
        for(int i = 0; i < 4; i++){
            if(activeBoxes[i].isSelected()){
                spinners[i].decrement(Integer.parseInt(incrementMeter.getValue().toString()));
            }
        }
    }


}
