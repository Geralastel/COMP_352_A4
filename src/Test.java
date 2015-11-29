import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Test {

	public static void main(String[] args) {

		//----------------------------------------------------------------------------
		Scanner in = null;
		try{
			in = new Scanner(new FileInputStream("hash_test_file1.txt"));
		}

		catch(FileNotFoundException e){
			System.out.println("Error");
			System.exit(0);
		}
		//----------------------------------------------------------------------------


		HashTable h1 = new HashTable();
		
		int count = 0;
		
		while(in.hasNext()){
			String word = in.next();
			//System.out.println(word);
			count++;
			//System.out.println(count);
			h1.put(word, word);
		
	
//			int code = h1.hash(word);
//			int hashcode = h1.hashfun(word);
//		
	}
		h1.printHashtableStatistics();
		
		

	}

}