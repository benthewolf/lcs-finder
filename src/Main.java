import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
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
    private int length = 0;
    private int[] row1;
    private int[] row2;
    public LCS(File a, File b) throws IOException{

        StringBuilder container1 = new StringBuilder();
        Scanner reader = new Scanner(new FileInputStream(a));
        while(reader.hasNextLine()){
            String line = reader.nextLine();
            for(int num = 0; num < line.length(); ++num){
                    container1.append(line.charAt(num));
            }
        }

        StringBuilder container2 = new StringBuilder();
        reader = new Scanner(new FileInputStream(b));

        while(reader.hasNextLine()){
            String line = reader.nextLine();
            for(int num = 0; num < line.length(); ++num){
                    container2.append(line.charAt(num));
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
        this.length = this.row1[this.row1.length - 1];
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
        double ratio = (200 * (double)this.getLength()) /(this.compare.length() + this.template.length());
        System.out.printf("The plagiarism ratio is: %1.2f\n", ratio);
    }


    public void search(){
        for (int a = 0; a < this.compare.length(); ++a){
            for (int b = 0; b < this.template.length(); ++b){
                if (this.template.charAt(b) != this.compare.charAt(a)){
                      this.row2[b + 1] = Math.max(
                              this.row2[b],
                              this.row1[b + 1]
                      );
                }
                else{
                    this.row2[b + 1] = this.row1[b] + 1;
                }
            }
            int[] temp = this.row1;
            Arrays.fill(temp, 0);
            this.row1 = this.row2;
            this.row2 = temp;
        }

    }


    private void initBoard(){
        this.row1 = new int[this.template.length() + 1];
        this.row2 = new int[this.template.length() + 1];

    }


}

