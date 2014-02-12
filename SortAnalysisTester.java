package coms311;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class SortAnalysisTester
{
	
	public static <E> void printList(ArrayList<E> list)
	{
		for (int i = 0; i < 10; i++)
			System.out.print(list.get(i) + " ");
		
		System.out.println("\n");
	}
	
	/**
	 * 
	 * @param size
	 * @param mag
	 * @return an 'size'-element ArrayList<Integer> with random integers from 0
	 *         to mag
	 */
	public static ArrayList<Integer> randCase(int size, int mag)
	{
		Random rand = new Random(System.currentTimeMillis());
		
		size = Math.max(0, size);
		mag = Math.abs(mag);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < size; i++)
			list.add(rand.nextInt(mag));
		
		return list;
	}
	
	public static void testInsertionsSort(String fileLoc)
	{
		
		File outFile = new File(fileLoc);
		
		PrintStream out;
		try
		{
			out = new PrintStream(outFile);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File not found");
			return;
		}
		
		InsertionSort<Integer> is = new InsertionSort<Integer>();
		
		int size = 99999;
		
		out.println("\"n\",\"Worst Case\",\"Random Case\",\"Best Case\"");
		
		for (int n = 0; n <= size; n += 1000)
		{
			int worstCase = is.analyzeSort(InsertionSort.worstCase(n));
			int randCase = is.analyzeSort(randCase(n, n << 1));
			int bestCase = is.analyzeSort(InsertionSort.bestCase(n));
			out.println("\"" + n + "\",\"" + worstCase + "\",\"" + randCase + "\",\"" + bestCase + "\"");
		}
		
		out.close();
		
	}
	
	public static void testMergeSort(String fileLoc)
	{
		MergeSort<Integer> ms = new MergeSort<Integer>();
		int n = 999;
		for (int i = 0; i < 994; i++)
		{
			ArrayList<Integer> list =randCase(i, 9); 
			System.out.println(list.toString());
			System.out.println(ms.analyzeSort(list));

		}
		
		
		{
			ArrayList<Integer> list = randCase(n, 99);
			
			
			int msTime = ms.analyzeSort(list);
			System.out.println("n: " + n + "\t\tmergeSort: " + msTime);
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println("Starting Tests...");
		

				
		MergeSort<Integer> ms = new MergeSort<Integer>();
		
		int size = 9999;
		
		System.out.println("\"n\",\"Worst Case\",\"Random Case\",\"Best Case\"");
		
		for (int n = 0; n <= size; n += 100)
		{
			int worstCase = ms.analyzeSort(InsertionSort.worstCase(n));
			int randCase = ms.analyzeSort(randCase(n, n << 1));
			int bestCase = ms.analyzeSort(InsertionSort.bestCase(n));
			System.out.println("\"" + n + "\",\"" + worstCase + "\",\"" + randCase + "\",\"" + bestCase + "\"");
		}
		
		System.out.close();
		
		//testMergeSort("sdf");
		
	}
	
}
