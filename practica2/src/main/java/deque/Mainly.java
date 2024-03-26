package deque;

import java.util.Comparator;

public class Mainly 
{
    public static void main(String[] args) 
    {
        DoubleLinkedList<Integer> dll = new DoubleLinkedList<>();

        for(int i=0; i<15; i++)
        {   
            dll.append((int) (Math.random() * 100 + 1));
        }
        System.out.println(dll.toString());

        Comparator<Integer> comp = (x,y) -> x - y;

        dll.sort(comp);

        System.out.println(dll.toString());
    }



}