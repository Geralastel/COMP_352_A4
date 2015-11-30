package hash;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Hash {

    public static void main(String[] args) {

        //----------------------------------------------------------------------
        Scanner in = null;
        Scanner in2 = null;
        try {
            in = new Scanner(new FileInputStream("hash_test_file1.txt"));
            in2 = new Scanner(new FileInputStream("hash_test_file1.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Error");
            System.exit(0);
        }
	//----------------------------------------------------------------------

        HashTable h1 = new HashTable();
        int count = 0;
        String word;
        while (in.hasNext()) {
            word = in.next();
            //System.out.println(word);
            count++;
            //System.out.println(count);
            h1.put(word, word);	
        }
        h1.printHashtableStatistics();
        for(int i = 0; i < 10000; i++) {
            word = in2.next();
            h1.remove(word);
        }
        h1.printHashtableStatistics();
    }
}
