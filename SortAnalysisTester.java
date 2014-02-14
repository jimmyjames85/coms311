package coms311;

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
	 * @param size
	 * @param mag
	 * @return an 'size'-element ArrayList<Integer> with random integers from 0
	 *         to mag
	 */
	public static ArrayList<Integer> randCaseMag(int size, int mag)
	{
		Random rand = new Random(System.nanoTime());
		
		size = Math.max(0, size);
		mag = Math.abs(mag);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < size; i++)
			list.add(rand.nextInt(mag));
		
		return list;
	}
	
	public static ArrayList<Integer> randCase(int size)
	{
		size = Math.max(0, size);
		
		ArrayList<Integer> sortedList = new ArrayList<Integer>();
		for (int i = 0; i < size; i++)
			sortedList.add(i);
		
		ArrayList<Integer> randList = new ArrayList<Integer>();
		Random rand = new Random(System.nanoTime());
		while (!sortedList.isEmpty())
		{
			int r = rand.nextInt(sortedList.size());
			randList.add(sortedList.get(r));
			sortedList.remove(r);
		}
		
		return randList;
	}
	
	public static void testInsertionsSort(PrintStream out, int maxArraySize, int incrementBy)
	{
		
		maxArraySize = Math.max(0, maxArraySize);
		incrementBy = Math.max(1, incrementBy);
		
		InsertionSort<Integer> is = new InsertionSort<Integer>();
		
		out.println("n,Best Case,Random Case,Worst Case");
		
		for (int n = 0; n <= maxArraySize; n += incrementBy)
		{
			int randCase = is.analyzeSort(randCase(n));
			int worstCase = is.analyzeSort(InsertionSort.worstCase(n));
			// int bestCase = is.analyzeSort(InsertionSort.bestCase(n));
			// out.println(n + "," + bestCase + "," + randCase + "," +
			// worstCase);
			out.println(n + "," + randCase + "," + worstCase);
		}
	}
	
	public static void testMergeSort(PrintStream out, int maxArraySize, int incrementBy)
	{
		maxArraySize = Math.max(0, maxArraySize);
		incrementBy = Math.max(1, incrementBy);
		MergeSort<Integer> ms = new MergeSort<Integer>();
		out.println("n,Best Case,Random Case,Worst Case");
		for (int n = 0; n < maxArraySize; n += incrementBy)
		{
			int worstCase = ms.analyzeSort(MergeSort.worstCase(n));
			int randCase = ms.analyzeSort(randCase(n));
			// int bestCase = ms.analyzeSort(MergeSort.bestCase(n));
			// out.println(n + "," + bestCase + "," + randCase + "," +
			// worstCase);
			out.println(n + "," + randCase + "," + worstCase);
			
		}
	}
	
	public static void testQuickSort(PrintStream out, int maxArraySize, int incrementBy)
	{
		maxArraySize = Math.max(0, maxArraySize);
		incrementBy = Math.max(1, incrementBy);
		QuickSort<Integer> qs = new QuickSort<Integer>();
		out.println("n,Best Case,Random Case,Worst Case");
		for (int n = 0; n < maxArraySize; n += incrementBy)
		{
			int randCase = qs.analyzeSort(randCase(n));
			int worstCase = qs.analyzeSort(QuickSort.worstCase(n));
			// int bestCase = qs.analyzeSort(QuickSort.bestCase(n));
			// out.println(n + "," + bestCase + "," + randCase + "," +
			// worstCase);
			out.println(n + "," + randCase + "," + worstCase);
		}
	}
	
	public static void main(String[] args)
	{
		//testInsertionsSort(System.out, 10000, 100);
		//testMergeSort(System.out, 90000, 1000);
		testQuickSort(System.out, 20000, 1000);
	}
	
}
