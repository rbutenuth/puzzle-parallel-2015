package de.codecentric.slider.util.set;

import java.util.HashSet;
import java.util.Set;

public class ArrayOfSynchronizedHashSet implements SimpleSet {
    static final int ARRAY_SIZE = 1 << 10;
    private Set<Long>[] sets;

    @SuppressWarnings("unchecked")
    public ArrayOfSynchronizedHashSet() {
	sets = new HashSet[ARRAY_SIZE];
	for (int i = 0; i < ARRAY_SIZE; i++) {
	    sets[i] = new HashSet<Long>(1_000);
	}
    }
    
    @Override
    public String toString() {
	return "Array of HashSet";
    }

    @Override
    public boolean add(Long entry) {
	Set<Long> set = sets[entry.hashCode() & (ARRAY_SIZE - 1)];
	synchronized (set) {
	    return set.add(entry);
	}
    }
}
