package com.mavericks.server;

import java.util.*;

public class SetCustom<V> implements Set<V> {
    private List<V> values;

    public SetCustom(){
        values=new ArrayList<>();
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    @Override
    public Iterator iterator() {
        return values.iterator();
    }

    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @Override
    public boolean add(V o) {
        if(values.contains(o)){
            return false;
        }
        else{
            values.add(o);
            return true;
        }
    }

    public V get(int index){
       return values.get(index);
    }

    @Override
    public boolean remove(Object o) {
        return values.remove(o);
    }

    @Override
    public boolean addAll(Collection collection) {
        Iterator<V> iter = collection.iterator();
        while(iter.hasNext()){
            if(add(iter.next())==false){
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public boolean removeAll(Collection collection) {
        return values.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection collection) {
        return values.retainAll(collection);
    }

    @Override
    public boolean containsAll(Collection collection) {
        return values.containsAll(collection);
    }

    @Override
    public Object[] toArray(Object[] objects) {
        return null;
    }
}
