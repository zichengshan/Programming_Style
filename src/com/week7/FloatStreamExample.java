package com.week7;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class Floaties implements Spliterator<Float> {
    private float last = 1.0f;
    private float rand = (float)Math.random();

    public Stream<Float> stream() {
        return StreamSupport.stream(this, false);
    }

    @Override
    public int characteristics() {
        return Spliterator.IMMUTABLE | Spliterator.NONNULL;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Float> action) {
//        if (rand >= 0.1) {
//            while (Math.abs(last - rand) < 0.4) {
//                rand = (float)Math.random();
//            }
//            last = rand;
//            action.accept(rand);
//            return true;
//        }

        if(rand >= 0.1){
            rand = (float)Math.random();
            action.accept(rand);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<Float> trySplit() {
        return null;
    }

}

public class FloatStreamExample {
    public static void main(String[] args) {
        Floaties floaties = new Floaties();
        floaties.stream().forEach(System.out::println);
    }

}
