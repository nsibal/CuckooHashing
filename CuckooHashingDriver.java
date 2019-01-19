/**
 * @author Leejia James
 * @author Nirbhay Sibal
 *
 * Comparison of hashing implementations - Driver program
 *
 * Ver 1.0: 2018/10/20
 */
package lxj171130;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class CuckooHashingDriver {
	public static void main(String[] args) throws FileNotFoundException {
		// Performance of Cuckoo Hashing
		CuckooHashing<Long> cuckooHashing = new CuckooHashing<>();
        Scanner sc;
        if (args.length > 0)
        {
            File file = new File(args[0]);
            sc = new Scanner(file);
        }
        else
        {
            sc = new Scanner(System.in);
        }

        String operation = "";
        long operand = 0;
        long result = 0;
        int modValue = 999983;

        System.out.println("Cuckoo Hashing Performance:");
        // Initialize the timer
        Timer timer = new Timer();

        while (!((operation = sc.next()).equals("End")))
        {
            switch (operation)
            {
            case "Add":
                operand = sc.nextLong();
                if (cuckooHashing.add(operand))
                {
                    result = (result + 1) % modValue;
                }
                break;
            case "Remove":
                operand = sc.nextLong();
                if (cuckooHashing.remove(operand) != null)
                {
                    result = (result + 1) % modValue;
                }
                break;
            case "Contains":
                operand = sc.nextLong();
                if (cuckooHashing.contains(operand))
                {
                    result = (result + 1) % modValue;
                }
                break;
            }
        }

        // End Time
        timer.end();

        System.out.println(result);
        System.out.println(timer);
        System.out.println();
        
        // Performance of Java's Hashset
        if (args.length > 0)
        {
            File file = new File(args[0]);
            sc = new Scanner(file);
        }
        else
        {
            sc = new Scanner(System.in);
        }

        operation = "";
        operand = 0;
        result = 0;
        modValue = 999983;
        	
        System.out.println("Java Hashset Performance:");
        // Initialize the timer
        timer = new Timer();
        HashSet<Long> hs = new HashSet<>();
        while (!((operation = sc.next()).equals("End")))
        {
            switch (operation)
            {
            case "Add":
                operand = sc.nextLong();
                if (hs.add(operand))
                {
                    result = (result + 1) % modValue;
                }
                break;
            case "Remove":
                operand = sc.nextLong();
                if (hs.remove(operand))
                {
                    result = (result + 1) % modValue;
                }
                break;
            case "Contains":
                operand = sc.nextLong();
                if (hs.contains(operand))
                {
                    result = (result + 1) % modValue;
                }
                break;
            }
        }

        // End Time
        timer.end();
        
        System.out.println(result);
        System.out.println(timer);
        System.out.println();
        
        
        //Generating an array of random integers and
        //calculating how many distinct numbers it has
        
        Random rand = new Random();
        int test = 10000;
        int lowerBound = 10;
        int upperBound = 100000;
        Object[] arr1 = new Object[test];

        for (int i1=0;i1<arr1.length;i1++) {
          arr1[i1] = Math.abs(rand.nextInt(upperBound)-lowerBound);
        }
        System.out.println("Total numbers generated: "+ arr1.length);
        System.out.println();
        
        System.out.println("Cuckoo Hashing Performance - Distinct numbers: ");
        timer = new Timer();
        int distinctCount = distinctElements(arr1);
        timer.end();
        System.out.println(timer);
        System.out.println("Distinct: " + distinctCount);
        System.out.println();

        System.out.println("Java Hashset Performance - Distinct numbers: ");
        HashSet<Object> hs1 = new HashSet<>();
        timer = new Timer();
        for (Object element : arr1) {
          hs1.add(element);
        }
        timer.end();
        System.out.println(timer);
        System.out.println("Distinct: "+ hs1.size());
        
    }
	
	/**
	 * Calculate the number of distinct elements in an array using the 
	 * hashing implementation (Cuckoo Hashing)
	 * @param arr
	 * @return number of distinct elements
	 */
	static<T> int distinctElements(T[] arr) {
		int count = 0;
		CuckooHashing<T> cuckooHashing1 = new CuckooHashing<>();
		for(T x: arr) {
			if(cuckooHashing1.add(x)) {
				count = count + 1;
			}
		}
		return count;
	}

}
