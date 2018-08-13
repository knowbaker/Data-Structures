package iterable;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * An Iterable and Iterator for enumerating Permutations
 * Example:
 * <code>
 * 	Permutation perm = Permutation.of("atom")
 * 	for(String x : perm)
 * 		System.out.println(x);
 * </code>
 * 
 * Similarly, the iteration can be accomplished using the <code>while(perm.hasNext())</code> loop
 * TODO: Enhance buffer to queue more elements and find an optimized number
 * to be stored in the buffer
 * 
 * @author Parikshit Singh
 */
public class Permutation implements Iterator<String>, Iterable<String> {	
	private final BlockingQueue<char[]> dataQ;
	private final BlockingQueue<Boolean> cmdQ;
	private final Queue<String> buffer;
	
	private Permutation(String s, BlockingQueue<char[]> dataQ, BlockingQueue<Boolean> cmdQ) {
		this.dataQ = dataQ;
		this.cmdQ = cmdQ;
		this.buffer = new ArrayDeque<>();
		Runnable producer = new Producer(s.toCharArray(), dataQ, cmdQ);
		new Thread(producer).start();
		addToBuffer();
	}
	
	public static Permutation of(String s) {
		Objects.requireNonNull(s);
		if(s.isEmpty())
			throw new IllegalArgumentException("Empty String not allowed");
		
		BlockingQueue<char[]> dataQ = new ArrayBlockingQueue<>(1);
		BlockingQueue<Boolean> cmdQ = new ArrayBlockingQueue<>(1);
		Permutation perm = new Permutation(s, dataQ, cmdQ);
		return perm;
	}
	
	@Override
	public Iterator<String> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return !buffer.isEmpty();
	}

	@Override
	public String next() {
		addToBuffer();
		return buffer.poll();
	}
	
	private void addToBuffer() {
		try {
			cmdQ.put(true);//issue command to produce next permutation
			char[] a = dataQ.take();
			if(a.length > 0)//a.length == 0 -> No more permutations remaining
				buffer.add(String.valueOf(a));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}		
	}
	
	private class Producer implements Runnable {
		private char[] a;
		private final BlockingQueue<char[]> dataQ;
		private final BlockingQueue<Boolean> cmdQ;
		
		Producer(char[] a, BlockingQueue<char[]> dataQ, BlockingQueue<Boolean> cmdQ) {
			this.a = a;
			this.dataQ = dataQ;
			this.cmdQ = cmdQ;
		}

		@Override
		public void run() {
			while(true) {
				try {
					cmdQ.take();
					enumerate(0);
					dataQ.put(new char[] {});//No more permutations remaining
					break;
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		private void enumerate(int depth) throws InterruptedException {
			if(depth == a.length) {
				dataQ.put(a);
				cmdQ.take();//wait for command to produce next permutation
				return;
			}
			
			for(int i = depth; i < a.length; i++) {
				swap(i, depth);
				enumerate(depth + 1);
				swap(i, depth);
			}
		}

		private void swap(int x, int y) {
			char c = a[x];
			a[x] = a[y];
			a[y] = c;
		}
	}
}
