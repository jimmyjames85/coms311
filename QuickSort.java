package coms311;

import java.util.ArrayList;
import java.util.Stack;

public class QuickSort<E extends Comparable<? super E>> implements SortAnalysis<E>
{
	
	private static class ProcessCmd
	{
		protected int left;
		protected int right;
		
		public ProcessCmd(int left, int right)
		{
			this.left = left;
			this.right = right;
		}
	}
	
	public QuickSort()
	{
	}
	
	public static ArrayList<Integer> worstCase(int size)
	{
		size = Math.max(0, size);
		ArrayList<Integer> worst = new ArrayList<Integer>();
		for (int i = 0; i < size; i++)
			worst.add(size - i);
		return worst;
	}
	
	public static ArrayList<Integer> bestCase(int size)
	{
		size = Math.max(0, size);
		ArrayList<Integer> best = new ArrayList<Integer>();
		for (int i = 0; i < size; i++)
			best.add(i);
		
		return best;
	}
	
	@Override
	public int analyzeSort(ArrayList<E> list)
	{
		
		

		long beginMilli = System.currentTimeMillis();
		analyzeSortSTACK(list);
		long endMilli = System.currentTimeMillis();

		long diffMilli = endMilli-beginMilli;

		return (int) diffMilli;
		
	}
	
	private int findHighestElemToTheRight(ArrayList<E> list, int curPivot, int maxRight)
	{
		if (curPivot == maxRight)
			return curPivot;
		else if (curPivot == maxRight - 1)
			return maxRight;
		
		// Now we are gauranteed at least 2 elements
		int secondHighestElem = curPivot + 1;
		int highestElem = secondHighestElem + 1;
		
		while (highestElem < maxRight && list.get(highestElem).compareTo(list.get(secondHighestElem)) <= 0)
			highestElem++;
		
		return highestElem;
	}
	
	public int analyzeSortNonStackNonRec(ArrayList<E> list)
	{
		
		long beginMilli = System.currentTimeMillis();
		int size = list.size();
		
		if (size < 2)
		{
			long endMilli = System.currentTimeMillis();
			return (int) (endMilli - beginMilli);
		}
		
		int left = 0;
		int right = list.size() - 1;
		
		int lastPivot = partition(list, left, right);
		
		// Case where list is 1 or 2 elements long and partition() automatically
		// sorts it
		if (lastPivot == right + 1)
		{
			long endMilli = System.currentTimeMillis();
			return (int) (endMilli - beginMilli);
		}
		
		while (left != right)
		{
			
			if (left < lastPivot - 1)
			{
				// go left
				int curPivot = partition(list, left, lastPivot - 1);
				
				if (curPivot == lastPivot)
				{
					// then partition sorted the small subArray for us and we
					// need to correctly find the last pivot
					left = lastPivot + 1;
					int highestElem = findHighestElemToTheRight(list, curPivot, right);
					
					// Go Right
					if (highestElem <= right)
						lastPivot = highestElem + 1;
				}
				else
					lastPivot = curPivot;
			}
			else
			{
				System.out.println(list);
				System.out.println("L" + left);
				// At this point we've gone as far left as we can.
				// To find the last known pivot we just scan increasing values
				// until we get to a decreasing one
				
				while (left < right && list.get(left).compareTo(list.get(left + 1)) <= 0)
					left++;
				
				System.out.println("L" + left);
				System.out.print("");
				int highestElem = findHighestElemToTheRight(list, left - 1, right);
				System.out.println("..HE:" + highestElem);
				lastPivot = highestElem + 1;
				
				/*
								// then partition sorted the small subArray for us and we
								// need to correctly find the last pivot
								left = lastPivot + 1;


								
								System.out.println("HE:"+highestElem);
								if (highestElem <= right)
									lastPivot = highestElem + 1;


								
								/*
								 * // go right
								 * 
								 * int secondHighestElem = lastPivot + 1; left =
								 * secondHighestElem;
								 * 
								 * int highestElem = lastPivot + 1; // look for the highest
								 * which will become our new right while (highestElem <= right
								 * &&
								 * list.get(highestElem).compareTo(list.get(secondHighestElem))
								 * <= 0) highestElem++;
								 * 
								 * // now i should point to the right part of the range if
								 * (highestElem <= right) lastPivot = partition(list,
								 * secondHighestElem, highestElem);
								 */
				
			}
			
		}
		long endMilli = System.currentTimeMillis();
		return (int) (endMilli - beginMilli);
	}
	
	private void swap(int i, int j, ArrayList<E> list)
	{
		E tmp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, tmp);
	}
	
	// returns right+1 if every element from left to right has been sorted
	
	private int partition(ArrayList<E> list, int left, int right)
	{
		System.out.println(list + "(" + left + "," + right + ")" + list.get(left) + ":" + list.get(right));
		
		// Case where 1 or 2 element sub List is easy to sort with a swap
		if (right - left < 2)
		{
			if (right != left && list.get(left).compareTo(list.get(right)) > 0)
			{
				E tmp = list.get(left);
				list.set(left, list.get(right));
				list.set(right, tmp);
			}
			return right + 1;
		}
		
		int highestElem = right;
		
		// initially this is the same as the pivot which has to be less than or
		// equal to the highestElem
		int secondHighestElem = left;
		
		int pivotPos = left;
		
		int i = pivotPos + 1;
		int j = right;
		
		E pivot = list.get(pivotPos);
		while (i <= j)
		{
			while (i <= right && list.get(i).compareTo(pivot) <= 0)
				i++;
			while (j > pivotPos && list.get(j).compareTo(pivot) > 0)
			{
				if (list.get(j).compareTo(list.get(secondHighestElem)) >= 0 && list.get(j).compareTo(list.get(highestElem)) < 0)
					secondHighestElem = j;
				
				if (list.get(j).compareTo(list.get(highestElem)) >= 0)
				{
					if (j != right)
						secondHighestElem = highestElem;
					highestElem = j;
				}
				
				j--;
			}
			
			if (i <= j)
				swap(i, j, list);
		}
		
		swap(j, pivotPos, list);
		
		// if the secondHighestElement points to the pivot then the partition to
		// the right only holds the highest element. In this case we want the
		// secondHighestElem to point to the highestElem as well
		if (secondHighestElem == pivotPos)
			secondHighestElem = highestElem;
		
		// System.out.println(list + "h=" + highestElem + "SH=" +
		// secondHighestElem + "p=" + j +
		// "("+list.get(highestElem)+","+list.get(secondHighestElem)+","+list.get(j)+")");
		
		// Now we set j+1 to the secondHighestElem to mark the beginning of our
		// range and we set left to the highestElem to the end of our range
		if (j < right)
		{
			swap(j + 1, secondHighestElem, list);
			if (j + 1 == highestElem)
				highestElem = secondHighestElem;
			
			secondHighestElem = j + 1;
		}
		
		swap(highestElem, right, list);
		highestElem = right;
		
		System.out.println(list + "h=" + highestElem + "SH=" + secondHighestElem + "p=" + j + "(" + list.get(highestElem) + "," + list.get(secondHighestElem) + "," + list.get(j) + ")");
		
		/*
		for (int p = left; p <= right; p++)
		{
			
			if (p == secondHighestElem)
				System.out.print("SH-");
			if (p == highestElem)
				System.out.print("H-");
			if (p == j)
				System.out.print("P-");
			
			System.out.print(list.get(p) + " ");
		}*/
		
		// TODO explain why
		if (j == right)
			j--;
		
		// j is now the new pivot point
		// if j has any elements to the left of it then highest element is at
		// j+1 and the second highest element is at index right
		// else h is the new pivot point the highest element and the second
		// highest element
		return j;
	}
	
	public int analyzeSortSTACK(ArrayList<E> list)
	{
		long beginMilli = System.currentTimeMillis();
		int size = list.size();
		
		if (size < 2)
		{
			long endMilli = System.currentTimeMillis();
			return (int) (endMilli - beginMilli);
		}
		
		Stack<ProcessCmd> cmdStack = new Stack<ProcessCmd>();
		
		cmdStack.add(new ProcessCmd(0, size - 1));
		
		while (!cmdStack.isEmpty())
		{
			System.out.println(System.currentTimeMillis());
			ProcessCmd curCmd = cmdStack.pop();
			
			int pivot = curCmd.left;
			int last = curCmd.right;
			
			int i = pivot + 1;
			int j = last;
			
			E pivotElem = list.get(pivot);
			
			while (i <= j)
			{
				E iElem = list.get(i);
				E jElem = list.get(j);
				
				while (i <= curCmd.right && iElem.compareTo(pivotElem) < 0)
					i++;
				while (j > curCmd.left && jElem.compareTo(pivotElem) >= 0)
					j--;
				
				// swap i with j
				if (i < j)
				{
					list.set(i, jElem);
					list.set(j, iElem);
				}
			}
			
			// swap pivot with j
			if (j != pivot)
			{
				list.set(pivot, list.get(j));
				list.set(j, pivotElem);
				
				// j is now the new pivot point
				pivot = j;
			}
			
			// push the two sub Arrays
			if (curCmd.left < pivot - 1)
				cmdStack.push(new ProcessCmd(curCmd.left, pivot - 1));
			if (j + 1 < curCmd.right)
				cmdStack.push(new ProcessCmd(pivot + 1, curCmd.right));
		}
		
		long endMilli = System.currentTimeMillis();
		return (int) (endMilli - beginMilli);
	}
}
