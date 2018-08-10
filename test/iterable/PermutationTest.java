package iterable;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PermutationTest {

	@Test
	/**
	 * If Permutation is Iterable, then it should be usable with the enhanced for loop
	 */
	public void testIterableFor() {
		testFor(Arrays.asList("x"), Permutation.of("x"));
		testFor(Arrays.asList("abc", "acb", "bac", "bca", "cba", "cab"), Permutation.of("abc"));
	}
	
	@Test
	/**
	 * If Permutation is Iterable, then it should be usable with the for-each loop
	 */
	public void testIterableForEach() {
		testForEach(Arrays.asList("x"), Permutation.of("x"));
		testForEach(Arrays.asList("abc", "acb", "bac", "bca", "cba", "cab"), Permutation.of("abc"));
	}
	
	@Test
	/**
	 * If Permutation is an Iterator, then it should be usable with the while(hasNext()) loop
	 */
	public void testIterator() {
		testWhile(Arrays.asList("x"), Permutation.of("x"));
		testWhile(Arrays.asList("abc", "acb", "bac", "bca", "cba", "cab"), Permutation.of("abc"));
	}
	
	private void testFor(List<String> expectedPerm, Permutation actualPerm) {
		int i = 0;
		for(String actual : actualPerm)
			assertEquals(expectedPerm.get(i++), actual);
	}
	
	private void testForEach(List<String> expectedPerm, Permutation actualPerm) {
		Iterator<String> iter = expectedPerm.iterator();
		actualPerm.forEach(actual -> assertEquals(iter.next(), actual));
	}

	private void testWhile(List<String> expectedPerm, Permutation actualPerm) {
		int i = 0;
		while(actualPerm.hasNext())
			assertEquals(expectedPerm.get(i++), actualPerm.next());
	}
}
