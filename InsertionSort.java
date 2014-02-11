package coms311;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class InsertionSort<E extends Comparable<? super E>> implements
		SortAnalysis<E>
{

	/**
	 * DEFAULT CONSTRUCTOR
	 */
	InsertionSort()
	{}

	@Override
	public int analyzeSort(ArrayList<E> list)
	{
		long beginMilli = System.currentTimeMillis();
		int size = list.size();
		if (size <= 1)
			return (int) (System.currentTimeMillis() - beginMilli);

		
		/*
		 * Assume list[0] to [n-1] is sorted Start at index n and compare to the
		 * left from n-1 to 0. Swap if neccessary and stop comparing when item
		 * is no longer the biggest among 0 and n-1
		 */

		for (int i = 0; i < size - 1; i++)
		{
			int n = i + 1;
			int compareValue = list.get(n).compareTo(list.get(n - 1));

			while (n > 0 && compareValue <= 0)
			{
				// swap(n,i);
				E tmp = list.get(n);
				list.set(n, list.get(n - 1));
				list.set(n - 1, tmp);
				n--;
				if (n > 0)
					compareValue = list.get(n).compareTo(list.get(n - 1));
			}
		}

		return (int) (System.currentTimeMillis() - beginMilli);
	}

	// Returns an ArrayList<Integer> of size "size" of the best case scenario
	// for InsertionSort
	public static ArrayList<Integer> bestCase(int size)
	{
		size = Math.max(0, size);

		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < size; i++)
			list.add(i);

		return list;
	}

	// Returns an ArrayList<Integer> of size "size" of the worst case scenario
	// for InsertionSort
	public static ArrayList<Integer> worstCase(int size)
	{
		size = Math.max(0, size);
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < size; i++)
			list.add(size - i);

		return list;
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
		final Random rand = new Random(System.currentTimeMillis());

		size = Math.max(0, size);
		mag = Math.abs(mag);

		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < size; i++)
			list.add(rand.nextInt(mag));

		return list;
	}

	public static <E> void printList(ArrayList<E> list)
	{
		for (int i = 0; i < 10; i++)
			System.out.print(list.get(i) + " ");

		System.out.println("\n");
	}

	public static void main(String[] args)
	{
		File outFile = new File("InsertionSortOutput.csv");

		PrintStream out;
		try
		{
			out = new PrintStream(outFile);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("no file found");
			return;
		}

		InsertionSort<Integer> is = new InsertionSort<Integer>();

		int size = 99999;

		out.println("\"n\",\"Worst Case\",\"Random Case\",\"Best Case\"");

		for (int n = 0; n <= size; n += 1000)
		{
			int worstCase = is.analyzeSort(worstCase(n));
			int randCase = is.analyzeSort(randCase(n, n << 1));
			int bestCase = is.analyzeSort(bestCase(n));
			out.println("\"" + n + "\",\"" + worstCase + "\",\"" + randCase
					+ "\",\"" + bestCase + "\"");
		}

	}

}
