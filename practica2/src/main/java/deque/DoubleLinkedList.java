package deque;

public class DoubleLinkedList<T> implements DoubleLinkedQueue<T> {

    private LinkedNode<T> first;
    private LinkedNode<T> last;
    private int size;

    public DoubleLinkedList() {
        // TODO
        first = null;
        last = null;
        size = 0;

    }

    @Override
    public void prepend(T value) {
        // TODO
        LinkedNode<T> newNode = new LinkedNode<>(value, null, null);
        if (size()==0) {
            first = newNode;
            last = newNode;
        } else {
            newNode.setNext(first);
            first.setPrevious(newNode);
            last=newNode;
        }
        size++;
    }

    @Override
    public void append(T value) {
        // TODO
        LinkedNode<T> newNode = new LinkedNode<>(value, null, null);
        if (size()==0) {
            first = newNode;
            last = newNode;
        } else {
            last.setNext(newNode);
            newNode.setPrevious(last);
            last = newNode;
        }
        size++;
    }

    @Override
    public void deleteFirst() {
        // TODO
        if (size()!=0) {
            if (size == 1) {
                first = null;
                last = null;
            } else {
                first = first.getNext();
                first.setPrevious(null);
            }
            size--;
        }
    }

    @Override
    public void deleteLast() {
        // TODO
        if (size()!=0) {
            if (size == 1) {
                first = null;
                last = null;
            } else {
                last = last.getPrevious();
                last.setNext(null);
            }
            size--;
        }
    }

    @Override
    public T first() {
        // TODO
        if (size() != 0) {
            return null;
        }
        return first.getItem();
    }

    @Override
    public T last() {
        // TODO
        if (size() != 0) {
            return null;
        }
        return last.getItem();
    }

    @Override
    public int size() {
        // TODO
        return this.size;
    }
}
