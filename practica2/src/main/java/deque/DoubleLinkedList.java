package deque;

import java.util.Comparator;

public class DoubleLinkedList<T> implements DoubleLinkedQueue<T> {

    private LinkedNode<T> first;
    private LinkedNode<T> last;
    private int size;

    public DoubleLinkedList() {

        first = null;
        last = null;
        size = 0;
    }

    @Override
    public void prepend(T value) {

        LinkedNode<T> newNode = new LinkedNode<>(value, null, null);
        if (size == 0) 
        {
            first = newNode;
            last = newNode;
        } 
        else {
            newNode.setNext(first);
            first.setPrevious(newNode);
            first = newNode;
        }
        size++;
    }

    @Override
    public void append(T value) {
        
        LinkedNode<T> newNode = new LinkedNode<>(value, null, null);
        if (size == 0) {
            first = newNode;
            last = newNode;
        } 
        else {
            last.setNext(newNode);
            newNode.setPrevious(last);
            last = newNode;
        }
        size++;
    }

    @Override
    public void deleteFirst() throws DoubleLinkedQueueException {
        
        if (size == 0) {
            throw new DoubleLinkedQueueException("[ERROR]: Empty deque");
        }

        if (size == 1) {
            first = null;
            last = null;
        } 
        else {
            first = first.getNext();
            first.setPrevious(null);
        }
        size--;
    }

    @Override
    public void deleteLast() throws DoubleLinkedQueueException {
        
        if (size == 0) {
            throw new DoubleLinkedQueueException("[ERROR]: Empty deque");
        }
        
        if (size == 1) {    
            first = null;
            last = null;
        } 
        else {
            last = last.getPrevious();
            last.setNext(null);
        }
        size--;
    }

    @Override
    public T first() throws DoubleLinkedQueueException {
        
        if (size == 0) {
            throw new DoubleLinkedQueueException("[ERROR]: Empty deque");
        }

        return first.getItem();
    }

    @Override
    public T last() throws DoubleLinkedQueueException {
        
        if (size == 0) {
            throw new DoubleLinkedQueueException("[ERROR]: Empty deque");
        }

        return last.getItem();
    }

    @Override
    public int size() {
    
        return this.size;
    }

    @Override
    public T get(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public boolean contains(T value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public void remove(T value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void sort(Comparator<? super T> comparator) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sort'");
    }
}
