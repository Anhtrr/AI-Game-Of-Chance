import java.util.*;

/**
 *  
 *  Artificial Inteligence - Programming Assignment #3 - Game of Chance
 *  Professor Ernest Davis
 *  @author Anh Tran - 4/7/2023
 *  Due: 4/10/2023 - 11AM (EST)
 *  
 */
public class diceGame {
    // MAIN
    public static void main(String args[]){
        int N_Sides = -1, L_Target = -1, U_Target = -1, N_Dice = -1, N_Games = -1;
        double M = -1;
        // READ INPUT
        try {
            String[] readInput = args;
            N_Dice = Integer.valueOf(readInput[0]);
            N_Sides = Integer.valueOf(readInput[1]);
            L_Target = Integer.valueOf(readInput[2]);
            U_Target = Integer.valueOf(readInput[3]);
            M = Double.valueOf(readInput[4]);
            N_Games = Integer.valueOf(readInput[5]);
        } catch (Exception e) {
            System.err.println("\nPlease make sure the program is run in the right command format: " +
            "\n\n   'java diceGame.java [NDice] [NSides] [LTarget] [UTarget] [M] [NGames]'\n" + 
            "\n      - Ignore (and not include) '[]' in your command." + 
            "\n      - Make sure all values are filled with a space between each." + 
            "\n      - Make sure all values are integers, except 'M'." + 
            "\n      - 'M' is a floating point number or integer." + 
            "\n      - Make sure all values are larger than 0.\n");
            System.exit(0);
        }
        if (N_Sides <= 0 || L_Target <= 0 || U_Target <= 0 || N_Dice <= 0 || N_Games <= 0 || M <= 0){
            System.err.println("\nPlease make sure the program is run in the right command format: " +
            "\n\n   'java diceGame.java [NDice] [NSides] [LTarget] [UTarget] [M] [NGames]'\n" + 
            "\n      - Ignore (and not include) '[]' in your command." + 
            "\n      - Make sure all values are filled with a space between each." + 
            "\n      - Make sure all values are integers, except 'M'." + 
            "\n      - 'M' can be a floating point number or integer." + 
            "\n      - Make sure all values are larger than 0.\n");
            System.exit(0);
        }
        // RUN GAME
        runGame(N_Dice, N_Sides, L_Target, U_Target, N_Games, M);
    }
    
    // RUN GAME 
    public static void runGame(int N_Dice, int N_Sides, int L_Target, int U_Target, int N_Games, double M){
        // WinCount- ; LoseCount-
        //  [X - current point count for the player about to play]
        //  [Y - point count for opponent]
        //  [J - number of dice the current player rolls]
        // = Times Won with current state(X,Y,J) ; Times Lost with current state(X,Y,J)
        int[][][] WinCount = new int[L_Target][L_Target][N_Dice+1]; 
        int[][][] LoseCount= new int[L_Target][L_Target][N_Dice+1];
        // Initialize data structure - WinCount && LoseCount
        for (int x = 0; x < L_Target; x++){
            for (int y = 0; y < L_Target; y++){
                for (int j = 0; j <= N_Dice; j++){
                    WinCount[x][y][j] = 0;
                    LoseCount[x][y][j] = 0;
                }
            }
        }
        String reinforcementLearningLine = "\nReinforcement learning experiment with M = " + M + ", NGames = " + String.format("%,d", N_Games) + "\n";
        String header = "\n";
        String footer = "";
        for(int i = 0; i < reinforcementLearningLine.length(); i++){
            header+="=";
            footer+="=";
        }
        footer+="\n";
        System.out.println(header);
        System.out.println("\nGame: NDice=" + N_Dice + ", NSides=" + N_Sides + ", LTarget=" + L_Target + ", UTarget=" + U_Target);
        System.out.println(reinforcementLearningLine);
        // Play Game for N_Games number of times
        for(int i = 0; i < N_Games; i++){
            playGame(N_Dice, N_Sides, L_Target, U_Target, LoseCount, WinCount, M);
        }
        // Extract Answer from final state of WinCount and LoseCount
        extractAnswer(WinCount, LoseCount);
        System.out.println(footer);
    }

    // PLAY GAME A SINGULAR TIME
    public static void playGame(int N_Dice, int N_Sides, int L_Target, int U_Target, int[][][] LoseCount,
    int[][][] WinCount, double M){        
        int[] currentState = new int[2];
        // player 1
        currentState[0] = 0; 
        // player 2
        currentState[1] = 0;        
        // Keep track of game trace
        List<int[]> scoreSequence = new ArrayList<>();
        List<Integer> diceRolledSequence = new ArrayList<>();
        List<int[]> outcomeSequence = new ArrayList<>();
        // Winner or Loser condition
        while(currentState[1] < L_Target){
            int[] stateCopy = new int[2];
            stateCopy[0] = currentState[0]; stateCopy[1] = currentState[1];
            scoreSequence.add(stateCopy);
            int numOfDiceChosen = chooseDice(stateCopy, LoseCount, WinCount, N_Dice, M);
            diceRolledSequence.add(numOfDiceChosen);
            int[] diceOutcome = rollDice(numOfDiceChosen, N_Sides);
            outcomeSequence.add(diceOutcome);
            // Update score
            for (int diceValue : diceOutcome){
                currentState[0] += diceValue;
            }
            // Switch Turns
            int player1Score = currentState[0];
            int player2Score = currentState[1];
            currentState[0] = player2Score;
            currentState[1] = player1Score;
        }
        // Determine Winner or Loser
        int win = -1;
        int mod = -1; 
        if (currentState[1] > U_Target){
            win = 0;
            mod = (scoreSequence.size()-1) % 2;
        }
        else{
            win = 1;
            mod = (scoreSequence.size()-1) % 2;
        }
        // Update WinCount and LoseCount
        // for each turn
        for(int i = 0; i < scoreSequence.size(); i++){
            int x = scoreSequence.get(i)[0];
            int y = scoreSequence.get(i)[1];
            int j = diceRolledSequence.get(i);
            if (i % 2 == mod){
                if(win == 1){
                    WinCount[x][y][j]+=1;
                }
                else if (win == 0){
                    LoseCount[x][y][j]+=1;
                }
            }
            else{
                if(win == 1){
                    LoseCount[x][y][j]+=1;
                }
                else if (win == 0){
                    WinCount[x][y][j]+=1;
                }
            }
        }
    }

    // CHOOSE DICE
    public static int chooseDice(int[] Score, int[][][] LoseCount, int[][][] WinCount, int N_Dice, double M){
        int score_X = Score[0];
        int score_Y = Score[1];
        int T = 0;
        double[] f_Values = new double[N_Dice+1];
        f_Values[0] = 0;
        // Calculate fj(x,y,j) for 1 ... N_Dice 
        // Calculate T
        for (int i = 1; i <= N_Dice; i++){
            T+= WinCount[score_X][score_Y][i] + LoseCount[score_X][score_Y][i];
            int numerator = WinCount[score_X][score_Y][i];
            int denominator = WinCount[score_X][score_Y][i] + LoseCount[score_X][score_Y][i];
            double f_Value = 0;
            if(denominator == 0){
               f_Value = 0.5;
            }
            else{
                f_Value = (double) numerator / (double) denominator;
            }
            f_Values[i] = f_Value;
        }
        // Select B
        int B = 0;
        for (int i = 1; i <= N_Dice; i++){
            if (f_Values[i] > f_Values[B]){
                B = i;
            }
        }
        // Calc g
        double g = 0;
        for (int i = 1; i <= N_Dice; i++){
            if(i != B){
                g += f_Values[i];
            }
        }
        // Calculate pj(x,y,j) for 1 ... N_Dice 
        double[] p_Values = new double[N_Dice+1];
        p_Values[0] = 0;
        double oneMinusPB = 0;
        // Set PB
        for (int i = 1; i <= N_Dice; i++){
            if(i == B){
                double p_Value = (double) ((T * f_Values[i]) + M)/ (double) ((T * f_Values[i]) + (N_Dice * M));
                p_Values[i] = p_Value;
                oneMinusPB = 1 - p_Value;
            }
            else{
                p_Values[i] = 0;
            }
        }
        // Set Pj
        for (int i = 1; i <= N_Dice; i++){
            if(i == B){
                continue;
            }
            else{
                double p_Value = (double) ((oneMinusPB * T * f_Values[i]) + M) / (double) ((g * T) + ((N_Dice-1)*M));
                p_Values[i] = p_Value;
            }
        }
        int chosenFromDist = chooseFromDist(p_Values);
        return chosenFromDist;
    }
    
    // HELPER 1 - CHOOSE FROM DISTRIBUTION
    public static int chooseFromDist(double[] p_Values){
        // Sampling from distribution
        double[] u_Values = new double[p_Values.length];
        u_Values[0] = 0;
        for(int i = 1; i < p_Values.length; i++){
            u_Values[i] = u_Values[i-1] + p_Values[i];
        }
        double x = Math.random();
        for (int i = 0; i < p_Values.length-1; i++){
            if(x < u_Values[i]){
                return i;
            }
        }
        return p_Values.length-1;
    }

    // HELPER 2 - SIMULATE ROLLING DICE
    public static int[] rollDice(int N_Dice, int N_Sides){
        int[] rolledValues = new int[N_Dice];
        // for each dice
        for (int i = 0 ; i < N_Dice; i++){
            // roll a value between 1 and number of sides on dice
            int rolledValue = (int)(Math.random() * N_Sides) + 1;
            rolledValues[i] = rolledValue;
        }
        return rolledValues;
    }

    // HELPER 3 - EXTRACT ANSWER
    public static void extractAnswer(int[][][] WinCount, int[][][] LoseCount){        
        String playString = "PLAY =\n";
        String probabilityString = "PROB =\n";
        for (int x = 0; x < WinCount.length; x++){
            // for each state <x, y>
            for(int y = 0; y < WinCount[x].length; y++){
                // choose number of dice - j && find max probability
                int chosen_J = 0;
                double probability_J = 0;
                // for each j - number of dice to roll at current state <x,y>
                for(int j = 0; j < WinCount[x][y].length; j++){
                    int numerator = WinCount[x][y][j];
                    int denominator = WinCount[x][y][j] + LoseCount[x][y][j];
                    if (denominator == 0){
                        continue;
                    }
                    else{
                        double tmp_probability_j = (double) numerator/ (double) denominator;
                        // find max probability and chosen J for each state
                        if (tmp_probability_j > probability_J){
                            chosen_J = j;
                            probability_J = tmp_probability_j;
                        }
                        else{
                            continue;
                        }
                    }
                }
                playString += "     " + chosen_J; 
                if (probability_J != 0){
                    probabilityString += "    " + String.format("%.4f", probability_J);
                }
                else{
                    probabilityString += "         0";
                }
            }
            playString += "\n"; probabilityString += "\n";
        }
        // Output
        System.out.println(playString);
        System.out.println(probabilityString);
    }
}
