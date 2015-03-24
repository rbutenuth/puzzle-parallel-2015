package de.codecentric.slider.util.set;

import java.util.HashSet;
import java.util.Set;

public class SimpleHashSet implements SimpleSet {
    private Set<Long> set;

    public SimpleHashSet() {
	set = new HashSet<>(1_000_000);
    }
    
    @Override
    public String toString() {
	return "HashSet";
    }

    @Override
    public boolean add(Long entry) {
	return set.add(entry);
    }
}
