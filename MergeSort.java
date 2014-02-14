package coms311;

import java.util.ArrayList;
import java.util.Stack;

public class MergeSort<E extends Comparable<? super E>> implements SortAnalysis<E>
{
	
	/**
	 * @author Jim Tappe This private class is used as an element in the stack
	 *         for mergesort. left and right are the endpoints of the subarray
	 *         to be sorted if isSorted is true the subArray from left to
	 *         (left+right)/2 and the subArray from (left+right)/2 +1 to right
	 *         is are sorted
	 */
	private static class MergeCommand
	{
		protected int left;
		protected int right;
		protected boolean isSorted;
		
		public MergeCommand(int left, int right, boolean isSorted)
		{
			this.left = left;
			this.right = right;
			this.isSorted = isSorted;
		}
	}
	
	private static void recWorstCase(ArrayList<Integer> list, boolean processLeft, Stack<Integer> worstCaseList)
	{
		// base case
		if (list.size() <= 2)
		{
			if (processLeft)
				for (int i = 0; i < list.size(); i++)
					worstCaseList.push(list.get(i));
			
			// Since recWorstCase is called once for processLeft and once for
			// 'processRight'(=!processLeft) we only need to push elements to
			// the stack for one side
			return;
		}
		
		if (processLeft)
		{
			ArrayList<Integer> newLeft = new ArrayList<Integer>();
			for (int i = 0; i < list.size(); i += 2)
				newLeft.add(list.get(i));
			
			recWorstCase(newLeft, true, worstCaseList);
			recWorstCase(newLeft, false, worstCaseList);
		}
		else
		{
			ArrayList<Integer> newRight = new ArrayList<Integer>();
			for (int i = 1; i < list.size(); i += 2)
				newRight.add(list.get(i));
			
			recWorstCase(newRight, true, worstCaseList);
			recWorstCase(newRight, false, worstCaseList);
		}
		
	}
	
	public static ArrayList<Integer> bestCase(int size)
	{
		size = Math.max(0, size);
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (int i = 0; i < size; i++)
			ret.add(i);

		return ret;
	}
	
	public static ArrayList<Integer> worstCase(int size)
	{
		
		size = Math.max(0, size);
		
		ArrayList<Integer> sortedList = new ArrayList<Integer>();
		for (int i = 0; i < size; i++)
			sortedList.add(i);
		
		if (size <= 2)
			return sortedList;
		
		Stack<Integer> worstCaseList = new Stack<Integer>();
		recWorstCase(sortedList, true, worstCaseList);
		recWorstCase(sortedList, false, worstCaseList);
		
		Stack<Integer> reverseWorstCase = new Stack<Integer>();
		
		while (!worstCaseList.isEmpty())
			reverseWorstCase.push(worstCaseList.pop());
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
		while (!reverseWorstCase.isEmpty())
			ret.add(reverseWorstCase.pop());
		
		return ret;
	}
	
	@Override
	public int analyzeSort(ArrayList<E> list)
	{
		long beginMilli= System.currentTimeMillis();
		int size = list.size();
		if (size < 2)
			return (int) (System.currentTimeMillis()- beginMilli);
		
		// Merge Sort with stack
		Stack<MergeCommand> cmdStack = new Stack<MergeCommand>();
		cmdStack.push(new MergeCommand(0, size - 1, false));
		
		while (!cmdStack.isEmpty())
		{
			MergeCommand curCmd = cmdStack.peek();
			int left = curCmd.left;
			int right = curCmd.right;
			int midL = (left + right) / 2;
			int midR = midL + 1;
			
			if (curCmd.isSorted)
			{
				// then we merge
				// create a sorted list from the two sub arrays
				ArrayList<E> sortedList = new ArrayList<E>();

				
				int li = left;
				int ri = midR;
				// This is the only loop that preforms a compare, which takes
				// constant time. In the worst case, control will remain in this
				// loop and compare every element alternating which half of the
				// list it grabs the next element from.
				
				while (li <= midL && ri <= right)
				{// merge
					E leftElem = list.get(li);
					E rightElem = list.get(ri);
					
					// We are actually looking for the largest elements here
					// As we pop them on the stack sortedList
					
					if (leftElem.compareTo(rightElem) < 0)
					{
						sortedList.add(leftElem);
						li++;
					}
					else
					{
						sortedList.add(rightElem);
						ri++;
					}
				}
				
				// these loop will iterate through the list at most
				// half of the elements of the list. This would be the best
				// scenario
				while (ri <= right)
					sortedList.add(list.get(ri++));
				while (li <= midL)
					sortedList.add(list.get(li++));
				
				// copy the sorted elements to the list
				while (left <= right)
					list.set(left++, sortedList.remove(0));
				
				cmdStack.pop();
			}
			else
			{
				if (left < midL)
					cmdStack.push(new MergeCommand(left, midL, false));
				if (midR < right)
					cmdStack.push(new MergeCommand(midR, right, false));
				
				// Since we pushed the left and right sub Array onto the stack
				// when the while loop gets back to this specific curCmd we know
				// it's subArrays will have been sorted thus we can set this
				// binary flag to true to indicate a merge is needed
				curCmd.isSorted = true;
			}
		}
		
		return (int) (System.currentTimeMillis() - beginMilli);
		
	}
}
