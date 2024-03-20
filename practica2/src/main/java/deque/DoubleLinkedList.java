package deque;

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
}
