package de.codecentric.slider.util.set;

import java.util.HashSet;
import java.util.Set;

public class SynchronizedHashSet implements SimpleSet {
    private Set<Long> set;

    public SynchronizedHashSet() {
	set = new HashSet<>(1_000_000);
    }

    @Override
    public String toString() {
	return "Synchronized HashSet";
    }

    @Override
    public boolean add(Long entry) {
	synchronized (set) {
	    return set.add(entry);
	}
    }
}
