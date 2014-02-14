package coms311;

import java.util.ArrayList;

public class InsertionSort<E extends Comparable<? super E>> implements SortAnalysis<E>
{
	
	InsertionSort()
	{
	}
	
	@Override
	public int analyzeSort(ArrayList<E> list)
	{
		long beginMilli= System.currentTimeMillis();
		int size = list.size();
		if (size <= 1)
			return (int) (System.currentTimeMillis()-beginMilli);
		
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
		return (int) (System.currentTimeMillis()-beginMilli);
	}
	
	// Returns
	
	/**
	 * @param size
	 *            - size of the array to be created
	 * @return an ArrayList<Integer> of size "size" of the best case scenario
	 *         for InsertionSort
	 */
	public static ArrayList<Integer> bestCase(int size)
	{
		size = Math.max(0, size);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < size; i++)
			list.add(i);
		
		return list;
	}
	
	/**
	 * @param size
	 *            size of the array to be created
	 * @return an ArrayList<Integer> of size "size" of the worst case scenario
	 *         for InsertionSort
	 */
	public static ArrayList<Integer> worstCase(int size)
	{
		size = Math.max(0, size);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < size; i++)
			list.add(size - i);
		
		return list;
	}
}
