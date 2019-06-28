package ua.com.quizandndlab.lab40;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleArrayList<E> implements SimpleList<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private E[] data;
    private int size = 0;

    public SimpleArrayList() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public SimpleArrayList(int initialCapacity) {
        this.data = (E[]) new Object[initialCapacity];
    }

    @Override
    public boolean add(E newElement) {
        ensureCapacity(size + 1);
        data[size] = newElement;
        size++;
        return true;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return data[index];
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        E oldValue = data[index];
        int numMoved = size - index - 1;
        System.arraycopy(data, index + 1, data, index, numMoved);
        data[--size] = null;
        return oldValue;
    }

    private void rangeCheck(int index) {
        if (index < 0 || size < index) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(minCapacity, data.length + (data.length >> 1));
            E[] newData = (E[]) new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, data.length);
            this.data = newData;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            /**
             * Index of element to be returned by subsequent call to next.
             */
            int cursor = 0;

            /**
             * Index of element returned by most recent call to next or
             * previous.  Reset to -1 if this element is deleted by a call
             * to remove.
             */
            int lastRet = -1;

            public boolean hasNext() {
                return cursor != size();
            }

            public E next() {
                try {
                    int i = cursor;
                    E next = get(i);
                    lastRet = i;
                    cursor = i + 1;
                    return next;
                } catch (IndexOutOfBoundsException e) {
                    throw new NoSuchElementException();
                }
            }

            public void remove() {
                if (lastRet < 0)
                    throw new IllegalStateException();

                try {
                    SimpleArrayList.this.remove(lastRet);
                    if (lastRet < cursor)
                        cursor--;
                    lastRet = -1;
                } catch (IndexOutOfBoundsException e) {
                    throw new ConcurrentModificationException();
                }
            }

        };
    }

    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleList)) return false;

        SimpleArrayList<?> that = (SimpleArrayList<?>) o;

        Iterator<E> itThis = this.iterator();
        Iterator<?> itThat = that.iterator();
        while (itThis.hasNext() && itThat.hasNext()) {
            E o1 = itThis.next();
            Object o2 = itThat.next();
            if (!(o1 == null ? o2 == null : o1.equals(o2)))
                return false;
        }
        return !(itThis.hasNext() || itThat.hasNext());

    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E e = it.next();
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
    }
    /*BODY*/
}
