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
		testFor(Permutation.of("x"), Arrays.asList("x"));
		testFor(Permutation.of("abc"), Arrays.asList("abc", "acb", "bac", "bca", "cba", "cab"));
	}
	
	@Test
	/**
	 * If Permutation is Iterable, then it should be usable with the for-each loop
	 */
	public void testIterableForEach() {
		testForEach(Permutation.of("x"), Arrays.asList("x"));
		testForEach(Permutation.of("abc"), Arrays.asList("abc", "acb", "bac", "bca", "cba", "cab"));
	}
	
	@Test
	/**
	 * If Permutation is an Iterator, then it should be usable with the while(hasNext()) loop
	 */
	public void testIterator() {
		testWhile(Permutation.of("x"), Arrays.asList("x"));
		testWhile(Permutation.of("abc"), Arrays.asList("abc", "acb", "bac", "bca", "cba", "cab"));
	}
	
	private void testFor(Permutation actualPerm, List<String> expectedPerm) {
		int i = 0;
		for(String actual : actualPerm)
			assertEquals(expectedPerm.get(i++), actual);
	}
	
	private void testForEach(Permutation actualPerm, List<String> expectedPerm) {
		Iterator<String> iter = expectedPerm.iterator();
		actualPerm.forEach(actual -> assertEquals(actual, iter.next()));
	}

	private void testWhile(Permutation actualPerm, List<String> expectedPerm) {
		int i = 0;
		while(actualPerm.hasNext())
			assertEquals(expectedPerm.get(i++), actualPerm.next());
	}
}
