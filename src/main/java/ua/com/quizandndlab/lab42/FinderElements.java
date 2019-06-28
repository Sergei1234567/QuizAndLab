package ua.com.quizandndlab.lab42;

import java.util.Iterator;

public class FinderElements {
    public static Integer findElement(SinglyLinkedList<Integer> singlyLinkedList, int k) {
        Iterator<Integer> it = singlyLinkedList.iterator();
        int size = 0;
        while (it.hasNext()) {
            it.next();
            size++;
        }
        int index = size - 1 - k;
        if (index < 0) throw new IndexOutOfBoundsException();
        it = singlyLinkedList.iterator();
        int count = 0;
        while (it.hasNext()) {
            int result = it.next();
            if(index == count) return result;
            count++;
        }
        throw new IndexOutOfBoundsException();
    }
}
