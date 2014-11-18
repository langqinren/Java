package CollectionOfObjects;

import java.util.WeakHashMap;

/**
 * Created by Shawn on 11/16/2014.
 */
class Key {
    private String ident;

    public Key(String id) {
        ident = id;
    }

    public String toString() {
        return ident;
    }

    public int hashCode() {
        return ident.hashCode();
    }

    public boolean equals(Object o) {
        return  (o instanceof Key) && ident.equals(((Key)o).ident);
    }

    public void finalize() {
        System.out.println("Finalizing key " + ident);
    }
}

class Value {
    private String ident;

    public Value(String id) {
        ident = id;
    }

    public String toString() {
        return ident;
    }

    public void finalize() {
        System.out.println("Finalizing value " + ident);
    }
}

public class CanonicalMapping {
    public static void main(String[] args) {
        int size = 1000;

        if (args.length > 0) size = Integer.parseInt(args[0]);

        Key[] keys = new Key[size];

        WeakHashMap<Key, Value> map = new WeakHashMap<Key, Value>();

        for (int i=0; i<size; i++) {
            Key   k = new Key(Integer.toString(i));
            Value v = new Value(Integer.toString(i));

            //if (i % 3 == 0) keys[i] = k;

            map.put(k, v);
        }

        System.gc();

        System.out.println("size=" + map.size());

        System.out.println("end");
    }
}
