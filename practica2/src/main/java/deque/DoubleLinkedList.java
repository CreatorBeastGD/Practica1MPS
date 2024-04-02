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
    public T get(int index) throws IndexOutOfBoundsException {
        
        if(index < 0 || index >= size())
        {
            throw new IndexOutOfBoundsException("ERROR: Índice introducido incorrecto");
        }
        
        LinkedNode<T> node = first;
        for(int i = index ; i > 0 ; i--) 
        {
            node = node.getNext();
        }

        return node.getItem();
    }

    @Override
    public boolean contains(T value) {
        LinkedNode<T> node = first;
        boolean result = false;
        
        while(node != null && !result) {
            result = node.getItem().equals(value);
            node = node.getNext();
        }

        return result;
    }

    @Override
    public void remove(T value) {
        
        if (contains(value)) 
        {
            LinkedNode<T> node = first;
            boolean found = false;
        
            while(!found) 
            {
                found = node.getItem().equals(value);
                
                if (!found) 
                {
                    node = node.getNext();
                }
            }

            if(size() == 1)
            {
                first = null;
                last = null;
            }
            else 
            {
                if (!node.isFirstNode()) {
                    node.getPrevious().setNext(node.getNext());
                } 
                else {
                    node.getNext().setPrevious(null);
                    first = node.getNext();
                }
    
                if(!node.isLastNode()) {
                    node.getNext().setPrevious(node.getPrevious());
                }
                else {
                    node.getPrevious().setNext(null);
                    last = node.getPrevious();
                }
            }
            node.setNext(null);
            node.setPrevious(null);
            node = null;
        }
    }

    @Override
    public void sort(Comparator<? super T> comparator) {
        
        boolean swap = true;

        while(swap)
        {
            swap = false;
            LinkedNode<T> actual = first;
            LinkedNode<T> next = actual.getNext();
            int comparation;           

            while(next != null)
            {
                comparation = comparator.compare(actual.getItem(), next.getItem());

                if(comparation > 0) // actual > next
                {
                    // Realiza el intercambio de nodos
                    swap = true;
                    if (actual.getPrevious() != null) {
                        actual.getPrevious().setNext(next);
                    } else {
                        first = next;
                    }

                    if (next.getNext() != null) {
                        next.getNext().setPrevious(actual);
                    } else {
                        last = actual;
                    }

                    actual.setNext(next.getNext());
                    next.setPrevious(actual.getPrevious());
                    actual.setPrevious(next);
                    next.setNext(actual);

                    // Actualiza las referencias para la próxima iteración
                    LinkedNode<T> temp = next;
                    next = actual;
                    actual = temp;

                }
                actual = next;
                next = next.getNext();
            }
        }
    }

    // CUANDO SE HAGAN LOS TEST, ESTO HAY QUE ELIMINARLO O COMENTARLO
    @Override
    public String toString() {
        
        String toString = "DoubleLinkedList: [";
        for (int i = 0 ; i < this.size() ; i++) {
            Object x = this.get(i);
            toString += x.toString();
            if (i != this.size()-1) {
                toString += ", ";
            }
        }
        toString += "]";
        toString += "\n First:      " + this.first.toString();
        toString += "\n Last:       " + this.last.toString();
        toString += "\n Elements:   " + this.size();


        return toString;
    }

}
