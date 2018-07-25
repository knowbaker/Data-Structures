package ordered;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class PriorityQueue<E extends Comparable<E>> {
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
	private E[] pq;
	private int n; //number of elements in the priority queue
	private Comparator<E> comparator;
	
	/*
	 * The unchecked suppression in both constructors is safe since the addition of elements 
	 * can only happen thru push(E e)
	 */
	@SuppressWarnings("unchecked")
	public PriorityQueue(int initialCapacity) {
		applyGreaterThanZeroCheck(initialCapacity);
		this.pq = (E[])new Comparable[initialCapacity];
	}
	
	@SuppressWarnings("unchecked")
	public PriorityQueue(int initialCapacity, Comparator<E> comparator) {
		applyGreaterThanZeroCheck(initialCapacity);
		this.comparator = comparator;
		this.pq = (E[])new Comparable[initialCapacity];
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public int size() {
		return n;
	}
	
	/**
	 * Swap root element with last element, reduce heap size, and let the new root sink down
	 * Return the original root element
	 */
	public E poll() {
		if(n == 0)
			throw new NoSuchElementException();
		
		E e = pq[0];
		swap(0, n-- - 1);
		sink(0);
		return e;
	}
	
	/**
	 * 
	 * @param e: Element to be added to the heap
	 * Add element to the end and let it sink up
	 */
	public void add(E e) {
		if(n == MAX_ARRAY_SIZE)
			throw new ArrayIndexOutOfBoundsException("Priority queue size has reached maximum allowable array size");
		
		pq[n++] = e;
		swim(n - 1);
	}
	
	private void sink(int i) {
		int x = getExtremeChildIndexOf(i);
		if(x < 0 || compare(i, x) <= 0)
			return;
		
		swap(i, x);
		sink(x);
	}
	
	private void swim(int i) {
		int x = getParentIndexOf(i);
		if(x < 0 || compare(x, i) <= 0)
			return;
		
		swap(i, x);
		swim(x);
	}
	
	private int getParentIndexOf(int i) {
		return (i - 1) >> 1;
	}
	
	//Smallest according to comparator
	private int getExtremeChildIndexOf(int i) {
		int l = getLeftChildIndexOf(i);
		int r = getRightChildIndexOf(i);
		if(l < 0 && r < 0)
			return -1;
		if(l < 0)
			return r;
		if(r < 0)
			return l;
		return compare(l, r) < 0 ? l : r;
	}
	
	private int getLeftChildIndexOf(int i) {
		int l = (i << 1) + 1;
		return l >= n || l < 0 ? -1 : l;
	}
	
	private int getRightChildIndexOf(int i) {
		int r = (i + 1) << 1;
		return r >= n || r < 0 ? -1 : r;
	}
	
	//We define the generic as E extends Comparable<E> in the class declaration
	//This avoids a potentially costly cast if(comparator == null)
	private int compare(int i, int j) {
		if(comparator == null)
			return pq[i].compareTo(pq[j]);
		else
			return comparator.compare(pq[i], pq[j]);
	}
	
	private void swap(int i, int j) {
		E e = pq[i];
		pq[i] = pq[j];
		pq[j] = e;
	}
	
	private void applyGreaterThanZeroCheck(int n) {
		if(n <= 0)
			throw new IllegalArgumentException("Provided value must be a positive integer");
	}
}
