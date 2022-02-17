package com.week7;

import java.util.Iterator;
import java.util.Random;

public class IteratorsExample{
    public static void main(String[] args) {
        Iterator<Integer> gen = new IGen();
        Iterator<Integer> f1 = new FilterOdd(gen);
        Iterator<Integer> f2 = new FilterDix7(f1);

        while(f2.hasNext())
            System.out.println(f2.next());
    }
}

 class IGen implements Iterator {
    private Random rand = new Random();
    @Override
    public boolean hasNext() {
        return true;
    }
    @Override
    public Object next() {
        return rand.nextInt(10000);
    }
}

class FilterOdd implements Iterator{
    private Integer last = 0;
    private Iterator<Integer> prior;
    public FilterOdd(Iterator<Integer> p){
        prior = p;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Object next() {
        int n = 0;
        do{
            n = prior.next();
        }while(n % 2 == 0);
        return n;
    }
}


class FilterDix7 implements Iterator{
    private Integer last = 0;
    private Iterator<Integer> prior;
    public FilterDix7(Iterator<Integer> p){
        prior = p;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Object next() {
        int n = 0;
        do{
            n = prior.next();
        }while(n % 7 != 0 || Math.abs(n-last) < 1000);
        last = n;
        return n;
    }
}

















