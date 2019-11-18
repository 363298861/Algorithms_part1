import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int SIZE = 3;
    private static final int GROWTH_FACTOR = 2;

    private Item[] list = (Item[]) new Object[SIZE];
    private int num;

    public RandomizedQueue(){
    }

    public boolean isEmpty(){
        return num == 0;
    }

    public int size(){
        return num;
    }

    public void enqueue(Item item){
        if (item == null)
            throw new IllegalArgumentException();
        if(num == list.length - 1){
            Item[] tem = (Item[]) new Object[num * GROWTH_FACTOR];
            for(int i = 0; i < num; i++)
                tem[i] = list[i];
            list = tem;
        }
        list[num++] = item;
    }

    public Item dequeue(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        int a = StdRandom.uniform(num);
        Item item = list[a];
        for(int i = a; i < num; i++)
            list[i] = list[i + 1];
        num--;
        return item;
    }

    public Item sample(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        int a = StdRandom.uniform(num);
        return list[a];
    }

    /*@Override
    public String toString(){
        String rtn = "";
        for(int i = 0; i < num; i++) {
            if(rtn != "")
                rtn += ", ";
            rtn += list[i];
        }
        return rtn;
    }*/

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Item[] RandomIterator = (Item[]) new Object[num];
        int index;

        ListIterator() {
            copy();
            StdRandom.shuffle(RandomIterator);
        }


        private Item[] copy() {
            if (num != 0) {
                for(int i = 0; i < num; i++)
                    RandomIterator[i] = list[i];
            }
            return RandomIterator;
        }

        @Override
        public boolean hasNext() {
            return index < num;
        }

        @Override
        public Item next() {
            return hasNext() ? RandomIterator[index++] : null;
        }
    }
}
