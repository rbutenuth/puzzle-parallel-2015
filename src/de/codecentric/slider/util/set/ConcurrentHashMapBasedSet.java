package de.codecentric.slider.util.set;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapBasedSet implements SimpleSet {
    private ConcurrentHashMap<Long, Long> map;

    public ConcurrentHashMapBasedSet() {
	map = new ConcurrentHashMap<>(1_000_000);
    }

    @Override
    public String toString() {
	return "ConcurrentHashMap based Set";
    }

    @Override
    public boolean add(Long entry) {
	return map.put(entry, entry) == null;
    }
}
