package ordered;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

public class PriorityQueueTest {

	@Test
	public void testMinHeapProperty() {
		test(null);
	}
	
	@Test
	public void testMinHeapWithExplicitComparator() {
		test(Comparator.comparing(Integer::intValue));
	}
	
	@Test
	public void testMaxHeapProperty() {
		test(Comparator.comparingInt(Integer::intValue).reversed());
	}
	
	private void test(Comparator<Integer> c) {
		int n = 1_000_000;
		PriorityQueue<Integer> pq = c == null ? new PriorityQueue<>(n) : new PriorityQueue<>(n, c);
		Integer[] a = new Integer[n];
		for(int i = 0; i < n; i++) {
			int x = (int) (Math.floor(Math.random() * Math.floor(5)) + 1);
			pq.add(x);
			a[i] = x;
		}
		Arrays.sort(a, c); //if c == null, it defaults to Arrays.sort(a)
		for(int x : a) {
			int actual = pq.poll().intValue();
			assertEquals(x, actual);
		}
	}	
}
