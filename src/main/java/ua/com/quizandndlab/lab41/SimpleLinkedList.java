package ua.com.quizandndlab.lab41;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleLinkedList<E> implements SimpleList<E> {
    private Node<E> first = null; // head
    private Node<E> last = null; // tail
    private int size = 0;

    private static class Node<T> {
        private Node <T> prev;
        private T item;
        private Node <T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || size <= index) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    private Node<E> node(int index) {
        if (index < size / 2) {
            Node<E> tmp = first;
            for (int i = 0; i < index; i++) {
                tmp = tmp.next;
            }
            return tmp;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }
    private E unlink(Node<E> x) { //todo:
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }

    @Override
    public boolean add(E newElement) {
        final Node<E> tmp = last;
        final Node<E> newNode = new Node<>(tmp, newElement, null);
        last = newNode;
        if (tmp == null) {
            first = newNode;
        } else {
            tmp.next = newNode;
        }
        size++;
        return true;
    }
    @Override
    public E get(int index) {
        checkIndex(index);
        return node(index).item;
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
        checkIndex(index);
        return unlink(node(index));
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
                    SimpleLinkedList.this.remove(lastRet);
                    if (lastRet < cursor)
                        cursor--;
                    lastRet = -1;
                } catch (IndexOutOfBoundsException e) {
                    throw new NoSuchElementException();
                }
            }

        };
    }

    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (! it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleList)) return false;

        SimpleLinkedList<?> that = (SimpleLinkedList<?>) o;

        Iterator<E> itThis = this.iterator();
        Iterator<?> itThat = that.iterator();
        while (itThis.hasNext() && itThat.hasNext()) {
            E o1 = itThis.next();
            Object o2 = itThat.next();
            if (!(o1==null ? o2==null : o1.equals(o2)))
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
}
