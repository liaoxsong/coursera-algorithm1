import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Created by song on 3/9/17.
 * A queue can be enqueued and dequeued at both ends.. should not be too hard..
 */
public class Deque<Item> implements Iterable<Item> {

    //have to use a linked list
    private class Node{
        Item value;
        Node next;
    }

    private int N = 0;

    private Node first, last;

    public Deque() {
        first = null; last = null;
        N = 0;
    }

    public boolean isEmpty() { //incrementSize() and decrementSize() must be called after this
        return N == 0;
    }
    public int size() {
        return N;
    }

    private void incrementSize() {
        N++;
    }
    private void decrementSize() {
        N--;
    }

    /**
     * add an item at a beginning of first
     * after first operation, last is not set?? problematic...
     * */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("try to add a null item");

        //connect first to last

        Node newFirst = new Node();
        newFirst.value = item;

        if (isEmpty()) {
            first = newFirst;
            last = first;//set both at same time
        } else {
            newFirst.next = first;
            first = newFirst;
        }


        incrementSize();
    }


    /**
     * typical enqueue operation, append to tail of linked list
     * NOTE: first time this is called, first and last is set!
     * @param item item to be added
     * */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("try to add a null item");


        Node oldLast = last;

        last = new Node();
        last.value = item;
        last.next = null;

        if (isEmpty()) {
            first = last ;
        }
        else {
            oldLast.next = last;
        }

        incrementSize();
    }

    /**
     * typical deQueue operation
     * */
    public Item removeFirst() {
        if (isEmpty())  throw new NoSuchElementException("try to remove from an empty queue");
        decrementSize();

        Object firstValue = first.value;
        first = first.next;

        return (Item) firstValue;
    }

    /**
     * typical pop() operation, takes O(N)...?
     *
     * */
    public Item removeLast() {//typical stack pop() operation
        if (isEmpty()) throw new NoSuchElementException("try to remove from an empty queue");
        decrementSize();

//        //if just one node
        if (first.next == null) {
            Object returnValue = first.value;
            first = null;
            return (Item) returnValue;
        }

        Node current = first;
        Object returnValue = -1;

        while (current != null) {
            if (current.next.next == null) { //if 1->2->3->4, we want to stop at 3, i.e. next's next is null,
                //and here we get the next value and set to next to null, bingo..
                returnValue = current.next.value;
                current.next = null;
            }
            current = current.next;
        }

        return (Item) returnValue;
    }

    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {return current!=null;}

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("no more element");
            Object item = current.value;
            current = current.next;
            return (Item) item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("does not support remove");
        }
    }


    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);

        System.out.println("last removed:" + deque.removeLast());
        System.out.println("first removed:" + deque.removeFirst());
        System.out.println("last removed:" + deque.removeLast());

        Iterator iterator = deque.iterator();
        while(iterator.hasNext()) {
         //   System.out.println(iterator.next());
        }
    }
}
