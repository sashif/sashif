import java.io.IOException;
import java.util.Scanner;

public class ConnectFour {
    public static int emptySpaces;
    public static int[][] board;
    
    public ConnectFour(){
        board=new int[7][7];  //square array
        //6 columns and one additional column to print out "|" = int[7][7]
        emptySpaces=42;
    }
                
    public static void board(){
        for(int i=1;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
              char letter = '0';
              if(board[i][j]==1){
                letter = 'R';
              }
              else if(board[i][j]==2){
                  letter = 'Y';
              }
                if(board[i][j] == 0)
                    System.out.print("|   ");
                else
                    System.out.print("| " + letter + " ");
            }
          
            System.out.println();
        }
        
        for(int i=0;i<29;i++)
            System.out.print(".");
        System.out.println();
        
    }
    
    public boolean playMove(int column, int playerNum){
        int i=0;
        for(i=0;i<7;i++){
            if(board[i][column] == 1 || board[i][column] == 2){
                board[i-1][column]=playerNum;
                break;
            }
        }
        if(i == 7)
            board[i-1][column]=playerNum;
        emptySpaces--;
        return four(i-1,column);
    }
    
        public boolean filled(){
        return emptySpaces == 0; //6 by 7 board
    } 
    public boolean canDrop(int column){
        return board[0][column] == 0 && column!=7; 
    }
        
    public boolean notFirstRow(int column){
          boolean firstRow = false;
          for(int i=0; i<board.length; i++){
            if (i==0){
              firstRow = true;
            }
    }
                  return firstRow; 
        }
    
    public boolean four(int x, int y){
        int num=board[x][y];
        int count=0;
        int i=y;
        
        //HORIZONTAL.
        while(i<7 && board[x][i] == num){
            count++;
            i++;
        }
        i=y-1;
        while(i>=0 && board[x][i] == num){
            count++;
            i--;
        }
        if(count == 4)
            return true;
        
        //VERTICAL.
        count=0;
        int j=x;
        while(j<7 && board[j][y] == num){
            count++;
            j++;
        }
        if(count == 4)
            return true;
        
        //SECONDARY DIAGONAL.
        count=0;
        i=x;
        j=y;
        while(i<7 && j<7 && board[i][j] == num){
            count++;
            i++;
            j++;
        }
        i=x-1;
        j=y-1;
        while(i>=0 && j>=0 && board[i][j] == num){
            count++;
            i--;
            j--;
        }
        if(count == 4)
            return true;
        
        //LEADING DIAGONAL.
        count=0;
        i=x;
        j=y;
        while(i<7 && j>=0 && board[i][j] == num){
            count++;
            i++;
            j--;
        }
        i=x-1;
        j=y+1;
        while(i>=0 && j<7 && board[i][j] == num){
            count++;
            i--;
            j++;
        }
        if(count == 4)
            return true;
        
        return false;
    }
     
    public static void main(String args[])throws IOException{
        
        ConnectFour newGame=new ConnectFour(); //create object of new game
        newGame.board(); //display board to player
        Scanner scan=new Scanner(System.in); //create scanner object
        outer:
        while(true){ //run loop infinitely , until runs into condition in line 33,50,64 to break
            int column;
            //PLAYER 1.
            while(true){ //run loop infinitely , until runs into condition in line 35 to break
            System.out.println("Drop a red disk at column(0-6):");
            column=scan.nextInt();
                if(newGame.canDrop(column) && newGame.notFirstRow(column)){ //board[0] is not displayed
                    if(newGame.playMove(column, 1)){
                        newGame.board();
                        System.out.println("The red player won.");
                        break outer;
                    }
                    break;
                }
                else
                    System.out.println("You cannot drop a disk into this column.");
            }
            newGame.board();
            
            //PLAYER 2.    
            while(true){//will run infinitely , until runs into condition in line 55 to break
            System.out.println("Drop a yellow disk at column(0-6):");
            column=scan.nextInt();
                if(newGame.canDrop(column) && newGame.notFirstRow(column)){
                    if(newGame.playMove(column, 2)){
                        newGame.board();
                        System.out.println("The yellow player won.");
                        break outer;
                    }
                    break;
                }
                else
                    System.out.println("You cannot drop a disk into this column.");
            }
            newGame.board();
            
            if(newGame.filled()){
                System.out.print("The board is full. Start a new game.");
                break;
            }
        }
    }
}