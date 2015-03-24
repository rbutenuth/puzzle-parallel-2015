package de.codecentric.slider.util.set;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteHashSet implements SimpleSet {
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Set<Long> set;

    public ReadWriteHashSet() {
	set = new HashSet<>(1_000_000);
    }
    
    @Override
    public String toString() {
	return "ReadWrite HashSet";
    }

    @Override
    public boolean add(Long entry) {
	boolean added;
	rwl.readLock().lock();
	if (set.contains(entry)) {
	    added = false;
	    rwl.readLock().unlock();
	} else {
	    rwl.readLock().unlock();
	    rwl.writeLock().lock();
	    added = set.add(entry);	
	    rwl.writeLock().unlock();
	}
	return added;
    }
}
