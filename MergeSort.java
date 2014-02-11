package coms311;

import java.util.ArrayList;
import java.util.Stack;

public class MergeSort<E extends Comparable<? super E>> implements SortAnalysis<E>
{

	
	
	@Override
	public int analyzeSort(ArrayList<E> list)
	{
		long beginMilli = System.currentTimeMillis();
		int size = list.size();
		if(size <2)
			return (int)(System.currentTimeMillis() - beginMilli);
		
		
		//Merge Sort
		
		Stack<Integer> nextIndex = new Stack<Integer>();
		
		nextIndex.push(3);
		nextIndex.push(35);
		nextIndex.push(23);
		
		while(!nextIndex.isEmpty())
		{
			
			System.out.print(nextIndex.pop() + " ");
			
		}
		
		
		
		return (int)(System.currentTimeMillis() - beginMilli);
	}
	
	
	public static void main(String[] args)
	{
		ArrayList<Integer> list = InsertionSort.randCase(34, 132);
		MergeSort<Integer> ms = new MergeSort<Integer>();
		ms.analyzeSort(list);
		

	}


}
