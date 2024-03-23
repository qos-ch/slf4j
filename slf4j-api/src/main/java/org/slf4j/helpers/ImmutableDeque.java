package org.slf4j.helpers;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class ImmutableDeque<T> implements Deque<T> {
    Deque<T> deque;

    public ImmutableDeque(Deque<T> deque) {
        if (deque == null) {
            throw new IllegalArgumentException();
        }

        this.deque = deque;
    }
    public static <T> ImmutableDeque<T> of(Deque<T> deque) {
        return new ImmutableDeque<>(deque);
    }
    @Override
    public void addFirst(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addLast(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offerFirst(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offerLast(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T removeFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T removeLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T getFirst() {
        return deque.getFirst();
    }

    @Override
    public T getLast() {
        return deque.getLast();
    }

    @Override
    public T peekFirst() {
        return deque.peekFirst();
    }

    @Override
    public T peekLast() {
        return deque.peekLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T poll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T element() {
        return deque.element();
    }

    @Override
    public T peek() {
        return deque.peek();
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void push(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T pop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return deque.containsAll(collection);
    }

    @Override
    public boolean contains(Object o) {
        return deque.contains(o);
    }

    @Override
    public int size() {
        return deque.size();
    }

    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return deque.iterator();
    }

    @Override
    public Object[] toArray() {
        return deque.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] array) {
        return deque.toArray(array);
    }

    @Override
    public Iterator<T> descendingIterator() {
        return deque.descendingIterator();
    }
}
