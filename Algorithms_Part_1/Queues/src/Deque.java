import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private LL first, last;
    private int num;

    private class LL {
        Item item;
        LL next;

        LL(Item item, LL next){
            this.item = item;
            this.next = next;
        }

        /*@Override
        public String toString() {
            return next == null ? item.toString() : item + " " + next;
        }*/
    }

    public Deque() {
    }                          // construct an empty deque

    public boolean isEmpty() {
        return num == 0;
    }                // is the deque empty?

    public int size() {
        return num;
    }                       // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (first == null)
            first = last = new LL(item, null);
        else if (first.equals(last)){
            first = new LL(item, last);
        }else {
            LL oldFirst = first;
            first = new LL(item, oldFirst);
        }
        num++;
    }         // add the item to the front

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (first == null) {
            first = last = new LL(item, null);
        }else if (first.equals(last)){
            last = new LL(item, null);
            first.next = last;
        }else{
            LL oldLast = last;
            last = new LL(item, null);
            oldLast.next = last;
        }
        num++;
    }          // add the item to the end

    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        LL oldFirst = first;
        first = first.next;
        num--;
        return oldFirst.item;
    }  // remove and return the item from the front


    public Item removeLast() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        LL oldLast = last;
        if(first.equals(last))
            first = last = null;
        else if(first.next.equals(last)) {
            last = first;
            last.next = null;
        }
        else{
            ListIterator ite = new ListIterator();
            while (ite.current != null && ite.current.next != last) {
                ite.next();
            }
            if(ite.current != null) {
                ite.current.next = null;
                last = ite.current;
            }
        }
        num--;
        return oldLast.item;
    }                // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private LL current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    /*@Override
    public String toString(){
        return first == null ? null : first.toString();
    }*/
}