package Others;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

public class SoftwireTest
{
    private static int duplicateCharacters(String input) {
        HashSet<Character> set = new HashSet<>();
        ArrayList<Character> list = new ArrayList<>();
        int result = 0;
        for(char character: input.toCharArray()){
            if(!set.add(character) && !list.contains(character)){
                list.add(character);
                result++;
            }

        }
        return result;
    }

    private static int secondHighestDigit(String input) {

        ArrayList<Integer> list = new ArrayList<>();
        int counter = 0;

        for(char character: input.toCharArray()){
            if(Character.isDigit(character)){
                counter ++;
                int digit = Integer.parseInt(String.valueOf(character));
                list.add(digit);
            }
        }
        if(counter < 2){
            return -1;
        }

        Collections.sort(list);
        return list.get(list.size() - 2);
    }

    private static int flr(String directions) {

        int xCoordinates = 0;
        int yCoordinates = 0;
        int movementCounter = 0;

        ArrayList<Character> validDirections = new ArrayList<>();
        validDirections.add('F');
        validDirections.add('L');
        validDirections.add('R');

        ArrayList<String> validOrientations = new ArrayList<>();
        validOrientations.add("Y+");
        validOrientations.add("X+");
        validOrientations.add("Y-");
        validOrientations.add("X-");

        int currentOrientationIndex = 0;

        String currentOrientation = "Y+";

        int size = validOrientations.size();

        ArrayList<Character> trueDirections = new ArrayList<>();

        // retrieve the valid directions from the input
        for(char direction: directions.toCharArray()){
            if(validDirections.contains(direction)){
                trueDirections.add(direction);
            }
        }

        // Execute the valid directions
        for(char trueDirection: trueDirections){
            switch(trueDirection){
                case 'R':
                    currentOrientationIndex = (currentOrientationIndex + 1) %size;
                    currentOrientation = validOrientations.get(currentOrientationIndex);
                    break;
                case 'L':
                    currentOrientationIndex = Math.floorMod(currentOrientationIndex - 1, size);
                    currentOrientation = validOrientations.get(currentOrientationIndex);
                    break;
                case 'F':
                    switch(currentOrientation){
                        case "Y+":
                            yCoordinates++;
                            break;
                        case "X+":
                            xCoordinates ++;
                            break;
                        case "Y-":
                            yCoordinates --;
                            break;
                        case "X-":
                            xCoordinates --;
                            break;
                    }
                    break;
            }
        }

        // Go back to starting point
            if(yCoordinates < 0){
                switch(currentOrientation){
                    case "Y+":
                        movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                        currentOrientation = "Y+";
                        movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
                        break;
                    case "X+":
                        if(xCoordinates > 0){
                            movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                            currentOrientation = "Y+";
                            movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
                        }
                        else if(xCoordinates < 0){
                            movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
                            currentOrientation = "X+";
                            movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                        }
                        else{
                            movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                        }
                        break;
                    case "Y-":
                        movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
                        if(xCoordinates != 0){
                            currentOrientation = "X+";
                        }
                        movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                        break;
                    case "X-":
                        if(xCoordinates < 0){
                            movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                            currentOrientation = "Y+";
                            movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
                        }
                        else if (xCoordinates > 0){
                            movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
                            currentOrientation = "X-";
                            movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                        }
                        else{
                            movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                        }
                        break;
                }

            }
            else if(yCoordinates > 0){
                switch(currentOrientation){
                    case "Y+":
                        movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
                        if(xCoordinates != 0){
                            currentOrientation = "X-";
                        }
                        movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                        break;
                    case "X+":
                        if(xCoordinates > 0){
                            movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                            currentOrientation = "Y-";
                            movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
                        }
                        else if(xCoordinates < 0){
                            movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
                            currentOrientation = "X+";
                            movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                        }
                        else{
                            movementCounter += fixYCoordinates(yCoordinates, currentOrientation);

                        }
                        break;
                    case "Y-":
                        movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                        currentOrientation = "Y-";
                        movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
                        break;
                    case "X-":
                        if(xCoordinates < 0){
                            movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                            currentOrientation = "Y-";
                            movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
                        }
                        else if (xCoordinates > 0){
                            movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
                            currentOrientation = "X-";
                            movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                        }
                        else{
                            movementCounter += fixYCoordinates(yCoordinates, currentOrientation);
                        }
                        break;
                }

            }
            else{
                movementCounter+= fixXCoordinates(xCoordinates,currentOrientation);
            }
        return movementCounter;
    }

    private static int fixXCoordinates(int xCoordinates, String currentOrientation){
        String orientation = currentOrientation;
        int x = xCoordinates;
        int numberOfMovements = 0;

        if(x < 0){
            switch(orientation){
                case "Y+":
                case "Y-":
                    numberOfMovements++;
                    break;
                case "X-":
                    numberOfMovements += 2;
                    break;
            }

        }
        else if(x > 0){
            switch(orientation){
                case "Y+":
                case "Y-":
                    numberOfMovements++;
                    break;
                case "X+":
                    numberOfMovements += 2;
                    break;
            }

        }
        numberOfMovements += Math.abs(x);
        return numberOfMovements;
    }

    private static int fixYCoordinates(int yCoordinates, String currentOrientation){
        String orientation = currentOrientation;
        int y = yCoordinates;
        int numberOfMovements = 0;

        if(y < 0){
            switch(orientation){
                case "Y-":
                    numberOfMovements+= 2;
                    break;
                case "X+":
                    numberOfMovements++;
                    break;
                case "X-":
                    numberOfMovements ++;
                    break;
            }

        }
        else if(y > 0) {
            switch(orientation){
                case "Y+":
                    numberOfMovements+= 2;
                    break;
                case "X-":
                    numberOfMovements++;
                    break;
                case "X+":
                    numberOfMovements ++;
                    break;
            }

        }
        numberOfMovements += Math.abs(y);
        return numberOfMovements;

    }
    public static void main(String[] args) {
        System.out.println(flr("RRRRF"));

    }
}
