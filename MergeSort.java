package coms311;

import java.util.ArrayList;
import java.util.Stack;

public class MergeSort<E extends Comparable<? super E>> implements
		SortAnalysis<E>
{
	
	/**
	 * 
	 * @author Jim Tappe
	 * 
	 *         This private class is used as an element in the stack for
	 *         mergesort.
	 * 
	 *         left and right are the endpoints of the subarray to be sorted if
	 *         isSorted is true the subArray from left to (left+right)/2 and the
	 *         subArray from (left+right)/2 +1 to right is are sorted
	 */
	private class MergeCommand
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
	
	@Override
	public int analyzeSort(ArrayList<E> list)
	{
		long beginMilli = System.currentTimeMillis();
		int size = list.size();
		
		if (size < 2)
			return (int) (System.currentTimeMillis() - beginMilli);
		
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
				// create a sorted list from the two sub arrays
				Stack<E> sortedList = new Stack<E>();
				
				int li = midL;
				int ri = right;
				
				while (li >= left && ri >= midR)
				{// merge
					if (list.get(li).compareTo(list.get(ri)) > 0)
						sortedList.push(list.get(li--));
					else
						sortedList.push(list.get(ri--));
				}
	
				// add remaining elements
				while (ri >= midR)
					sortedList.push(list.get(ri--));
				while (li >= left)
					sortedList.push(list.get(li--));
				
				// no pop back the sorted elements to the actual list
				while (!sortedList.isEmpty())
					list.set(left++, sortedList.pop());
				
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
		
		if(list.size()==9800)
			System.out.println(list.toString());
		
		int time = (int) (System.currentTimeMillis() - beginMilli);
		System.out.println("time="+time);
		return time;
	}
}
