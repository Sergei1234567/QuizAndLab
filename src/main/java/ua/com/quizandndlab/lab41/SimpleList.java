package ua.com.quizandndlab.lab41;

import java.util.Iterator;

public interface SimpleList<T> {
    boolean add(T newElement);

    T get(int index);

    Iterator<T> iterator();

    int size();

    boolean isEmpty();

    T remove(int index);
}
