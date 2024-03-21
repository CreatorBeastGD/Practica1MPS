package deque;

import java.util.Comparator;

public class Mainly 
{
    public static void main(String[] args) 
    {
        DoubleLinkedList<Integer> dll = new DoubleLinkedList<>();

        for(int i=0; i<12; i++)
        {   
            dll.append((int) (Math.random() * 12 + 1));
        }
        System.out.println(dll.toString());

        Comparator<Integer> comp = (x,y) -> x - y;

        Comparator<Integer> comparator = (x,y) -> {
            if(x < y) {return -1;}
            else if(x > y) {return 1;}
            else {return 0;}
        };

        String toString = "DoubleLinkedList: [";
        for (int i = 0 ; i < dll.size() ; i++) {
            Integer x = dll.get(i);
            toString += x;
            if (i != dll.size()-1) {
                toString += ", ";
            }
        }
        toString += "]";

        dll.sort(comparator);
        System.out.println(toString);
        
        System.out.println(dll.toString());
    }



}