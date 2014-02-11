package coms311;

import java.util.ArrayList;
import java.util.Random;

public class InsertionSort<E extends Comparable<? super E>> implements
		SortAnalysis<E>
{

	/**
	 * DEFAULT CONSTRUCTOR
	 */
	InsertionSort()
	{
	}

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
			int compareValue = list.get(n).compareTo(list.get(i));

			while (n > 0 && compareValue <= 0)
			{
				// swap(n,i);
				E tmp = list.get(n);
				list.set(n, list.get(i));
				list.set(i, tmp);
				n--;

				compareValue = list.get(n).compareTo(list.get(i));
			}

		}

		return 0;
	}

	public static <E> void printList(ArrayList<E> list)
	{
		for (int i = 0; i < 10; i++)
			System.out.print(list.get(i) + " ");
		
		System.out.println("\n");
	}

	public static void main(String[] args)
	{
		Random rand = new Random(System.currentTimeMillis());
		int mag = 999;

		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++)
			list.add(rand.nextInt(mag));

		printList(list);
		InsertionSort<Integer> is = new InsertionSort<Integer>();
		printList(list);
		
		System.out.println("analayzeSort() returned " + is.analyzeSort(list));

	}

}
