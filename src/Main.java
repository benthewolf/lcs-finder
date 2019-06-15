import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        LCS lcs = new LCS(new File("Player1.java"), new File("Player2.java"));
        long currtime = System.currentTimeMillis();
        lcs.search();
        System.out.printf("It took %1d ms to find\n", System.currentTimeMillis() - currtime);
        lcs.displayResults();
    }
}

class LCS{
    private String template;
    private String compare;
    private String[][] lcsTable;
    private StringBuilder subsequence = new StringBuilder();
    private int length = 0;
    public LCS(File a, File b) throws IOException{

        StringBuilder container1 = new StringBuilder();
        Scanner reader = new Scanner(new FileInputStream(a));
        while(reader.hasNextLine()){
            String line = reader.nextLine();
            for(int num = 0; num < line.length(); ++num){
                if (line.charAt(num) != ' '){
                    container1.append(line.charAt(num));
                }
            }
        }

        StringBuilder container2 = new StringBuilder();
        reader = new Scanner(new FileInputStream(b));

        while(reader.hasNextLine()){
            String line = reader.nextLine();
            for(int num = 0; num < line.length(); ++num){
                if (line.charAt(num) != ' '){
                    container2.append(line.charAt(num));
                }
            }
        }

        this.template = container1.toString();
        this.compare = container2.toString();
        initBoard();

    }

    public LCS(String a, String b){
          this.template = a;
          this.compare = b;
          initBoard();

    }

    private void setLength(){
        this.length = Integer.parseInt(this.lcsTable[this.lcsTable.length - 1][this.lcsTable[0].length - 1]);
    }

    public int getLength() {
        if (this.length == 0){
            this.setLength();
            return this.length;
        }
        return this.length;
    }

    public void displayResults(){
        System.out.println("The length is: " + this.getLength());
        double ratio = ((double)this.getLength()/this.compare.length()) * 100 ;
        System.out.printf("The plagiarism ratio is: %1.2f\n", ratio);
    }

    public void displayBoard(){
        for (String[] a: this.lcsTable){
            for (String b: a){
                System.out.print(b + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void getSubsequence(){
        if (this.subsequence.length() == 0) {
            int max = 0;
            for (int a = 2; a < this.lcsTable[0].length; ++a) {
                for (int b = 2; b < this.lcsTable.length; ++b) {
                    if (Integer.parseInt(this.lcsTable[b][a]) > max) {
                        this.subsequence.append(this.lcsTable[0][a]);
                        max = Integer.parseInt(this.lcsTable[b][a]);
                    }
                }
            }
            System.out.println("The longest subsequence is: " + subsequence.toString());
        }

        else {
            System.out.println("The longest subsequence is: " + subsequence.toString());
        }
    }

    public void search(){
        for (int a = 2; a < this.lcsTable.length; ++a){
            for (int b = 2; b < this.template.length() + 2 ; ++b){
                if (!this.lcsTable[a][0].equals(this.lcsTable[0][b])){
                      this.lcsTable[a][b] = Math.max(
                              Integer.parseInt(this.lcsTable[a][b - 1]),
                              Integer.parseInt(this.lcsTable[a - 1][b])
                      ) + "";
                }

                else{
                    this.lcsTable[a][b] = "" +(Integer.parseInt(this.lcsTable[a - 1][b - 1]) + 1);
                }
            }
        }
    }


    private void initBoard(){
        this.lcsTable = new String[this.compare.length() + 2][this.template.length() + 2];
        this.lcsTable[0][0] = this.lcsTable[0][1] = "0";
        for (int a = 0; a < this.lcsTable[0].length; ++a){
                if (a  < this.template.length()){
                    this.lcsTable[0][a + 2] = this.template.substring(a, a + 1);
                }
                this.lcsTable[1][a] = "0";
        }

        for (int a = 2; a < this.lcsTable.length; ++a){
            if (a - 2 < this.compare.length()){
                this.lcsTable[a][0] = this.compare.substring(a - 2, a - 1);
                this.lcsTable[a][1] = "0";
            }

        }
    }


}

