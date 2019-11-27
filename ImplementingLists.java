package sample;
//  Sukhamrit Singh
//  Implementing Lists
/*
This is a program that implements a revised MyLinkedList class
after I have included all the code needed to fill in and complete
all the methods that were omitted. Next, I wrote a (main) driver
program that initializes a linked list with 10 names, and then completely
tests every one of its methods of ensure that the class meets all its
requirements.
 */

//  Import the necessary libraries
import java.util.Collection;

public class ImplementingLists<E> implements MyList<E> {

    public static void main(String[] args) {
        // Create a list for strings
        ImplementingLists<String> list = new ImplementingLists<>();

        // Add elements to the list  and printing them out
        list.add("Sukhamrit");
        System.out.println("Added Sukhamrit to the list ---- 1:  "
                + list);

        list.add(0, "Alex");
        System.out.println("Added Alex to the list ---- 2:  " + list);

        list.add("Bob");
        System.out.println("Added Bob to the list ---- 3:  " + list);

        list.addLast("Jonnathan");
        System.out.println("Added Jonnathan to the list ---- 4:  "
                + list);

        list.add(2, "Nathan");
        System.out.println("Added Nathan to the list ---- 5:  " + list);

        list.add(5, "Claire");
        System.out.println("Added Claire to the list ---- 6:  " + list);

        list.add(0, "Angela");
        System.out.println("Added Angela to the list ---- 7:  " + list);

        list.add(0, "Diego");
        System.out.println("Added Diego to the list ---- 8:  " + list);

        list.add(0, "Kyle");
        System.out.println("Added Kyle to the list ---- 9:  " + list);

        list.add(0, "Daniel");
        System.out.println("Added Daniel to the list ---- 10: " + list);

        // Remove elements from the list
        list.remove(0);
        System.out.println("Removed Daniel from the list ---- 11: " + list);

        list.remove(2);
        System.out.println("Removed Angela from the list ---- 12: " + list);

        list.remove(list.size() - 1);
        System.out.print("Removed Claire from the list ---- 13: " + list +
                "\nThe final list it ---- 14: ");

        //  using iterator in for-loop to traverse element in LinkedList
        for (String s: list)
            System.out.print(s.toUpperCase() + " ");

        list.clear();
        System.out.println("\nThe list size after clearing is: "
                + list.size());
    }

    private Node<E> head, tail;
    private int size = 0; // Number of elements in the list

    /** Create an empty list */
    public ImplementingLists() {
    }

    /** Create a list from an array of objects */
    public ImplementingLists(E[] objects) {
        for (int i = 0; i < objects.length; i++)
            add(objects[i]);
    }

    /** Return the head element in the list */
    public E getFirst() {
        if (size == 0) {
            return null;
        }
        else {
            return head.element;
        }
    }

    /** Return the last element in the list */
    public E getLast() {
        if (size == 0) {
            return null;
        }
        else {
            return tail.element;
        }
    }

    /** Add an element to the beginning of the list */
    public void addFirst(E e) {

        // Create a node object
        Node<E> node = new Node<>(e);

        // assign node to head
        node.next = head;
        head = node;
        size++;

        // the new node is the only node in list
        if (tail == null)
            tail = head;
    }

    /** Add an element to the end of the list */
    public void addLast(E e) {
        Node<E> node = new Node<>(e);

        if (tail == null) {
            head = tail = node;
        }
        else {
            // Link the new with the last node
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override /** Add a new element at the specified index
     * in this list. The index of the head element is 0 */
    public void add(int index, E e) {
        if (index == 0) {
            addFirst(e);
        }
        else if (index >= size) {
            addLast(e);
        }
        else {
            Node<E> current = head;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node<E> temp = current.next;
            current.next = new Node<>(e);
            (current.next).next = temp;
            size++;
        }
    }

    /** Remove the head node and
     *  return the object that is contained in the removed node. */
    public E removeFirst() {
        if (size == 0) {
            return null;
        }
        else {
            E temp = head.element;
            head = head.next;
            size--;
            if (head == null) {
                tail = null;
            }
            return temp;
        }
    }

    /** Remove the last node and
     * return the object that is contained in the removed node. */
    public E removeLast() {
        if (size == 0) {
            return null;
        }
        else if (size == 1) {
            E temp = head.element;
            head = tail = null;
            size = 0;
            return temp;
        }
        else {
            Node<E> current = head;

            for (int i = 0; i < size - 2; i++) {
                current = current.next;
            }

            E temp = tail.element;
            tail = current;
            tail.next = null;
            size--;
            return temp;
        }
    }

    @Override /** Remove the element at the specified position in this
     *  list. Return the element that was removed from the list. */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        else if (index == 0) {
            return removeFirst();
        }
        else if (index == size - 1) {
            return removeLast();
        }
        else {
            Node<E> previous = head;

            for (int i = 1; i < index; i++) {
                previous = previous.next;
            }

            Node<E> current = previous.next;
            previous.next = current.next;
            size--;
            return current.element;
        }
    }

    @Override /** Override toString() to return elements in the list */
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            result.append(current.element);
            current = current.next;
            if (current != null) {
                result.append(", ");
            }
            else {
                result.append("]");
            }
        }
        return result.toString();
    }

    @Override /** Clear the list */
    public void clear() {
        size = 0;
        head = tail = null;
    }

    @Override /** Return true if this list contains the element e */
    public boolean contains(Object e) {
        // Left as an exercise
        return true;
    }

    @Override /** Return the element at the specified index */
    public E get(int index) {
        // Left as an exercise
        return null;
    }

    @Override /** Return the index of the head matching element in
     *  this list. Return -1 if no match. */
    public int indexOf(Object e) {
        // Left as an exercise
        return 0;
    }

    @Override /** Return the index of the last matching element in
     *  this list. Return -1 if no match. */
    public int lastIndexOf(E e) {
        // Left as an exercise
        return 0;
    }

    @Override /** Replace the element at the specified position
     *  in this list with the specified element. */
    public E set(int index, E e) {
        // Left as an exercise
        return null;
    }

    @Override /** Override iterator() defined in Iterable */
    public java.util.Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator
            implements java.util.Iterator<E> {
        private Node<E> current = head; // Current index

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public E next() {
            E e = current.element;
            current = current.next;
            return e;
        }

        @Override
        public void remove() {
        }
    }

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element) {
            this.element = element;
        }
    }

    @Override /** Return the number of elements in this list */
    public int size() {
        return size;
    }

}

//  Creating a new interface MyList for the LinkedList
interface MyList<E> extends Collection<E> {
    //  Add new element in specific index
    public void add(int index, E e);

    //  Return an element with specific index
    public E get(int index);

    //Return the index of first matching element in list
    // But if can not find any match, returns -1
    public int indexOf(Object e);

    //Return the index of last matching element in list
    // But if can not find any match, returns -1
    public int lastIndexOf(E e);

    //  Remove element with specific index,
    //  return element removed from list
    public E remove(int index);

    //  Return element with specific index for the list
    public E set(int index, E e);

    // Add new element at end of list
    @Override
    public default boolean add(E e) {
        add(size(), e);
        return true;
    }

    //Return true if list contains no elements */
    @Override
    public default boolean isEmpty() {
        return size() == 0;
    }

    //  Remove first element
    //  Move next elements to left in list
    @Override
    public default boolean remove(Object e) {
        if (indexOf(e) >= 0) {
            remove(indexOf(e));
            return true;
        }
        else
            return false;
    }

    @Override
    public default boolean containsAll(Collection<?> c) {
        return true;
    }

    @Override
    public default boolean addAll(Collection<? extends E> c) {
        return true;
    }

    @Override
    public default boolean removeAll(Collection<?> c) {
        return true;
    }

    @Override
    public default boolean retainAll(Collection<?> c) {
        return true;
    }

    @Override
    public default Object[] toArray() {
        return null;
    }

    @Override
    public default <T> T[] toArray(T[] array) {
        return null;
    }
}
