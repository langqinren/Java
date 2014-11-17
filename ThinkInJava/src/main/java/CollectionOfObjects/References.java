package CollectionOfObjects;

import java.lang.ref.*;

/**
 * Created by Shawn on 11/16/2014.
 */
class VeryBig {
    private static final int SZ = 10000;
    private double[] d = new double[SZ];
    private String ident;

    public VeryBig(String id) {
        ident = id;
    }

    public String toString() {
        return ident;
    }

    public void finalize() {
        System.out.println("Finalizing " + ident);
    }
}


public class References {
    private static ReferenceQueue rq = new ReferenceQueue();

    public static void checkQueue() {
        Object inq = rq.poll();

        if (inq != null) {
            System.out.println("In queue: " + (VeryBig)((Reference)inq).get() );
        }
    }

    public static void main(String[] args) {
        int size = 10;

        if (args.length > 0)
            size = Integer.parseInt(args[0]);

        SoftReference[] sa = new SoftReference[size];
        for (int i=0; i<sa.length; i++) {
            sa[i] = new SoftReference(new VeryBig("Soft " + i), rq);
            System.out.println("just created: " + (VeryBig)sa[i].get());
            checkQueue();
        }

        WeakReference[] wa = new WeakReference[size];
        for (int i=0; i<wa.length; i++) {
            wa[i] = new WeakReference(new VeryBig("Weak " + i), rq);
            System.out.println("just created: " + (VeryBig)wa[i].get());
            checkQueue();
        }

        SoftReference s = new SoftReference(new VeryBig("Soft Ref"));
        WeakReference w = new WeakReference(new VeryBig("Weak Ref"));

        Object o = new VeryBig("Strong Ref");
        new VeryBig("No Ref");

        System.gc();

        PhantomReference[] pa = new PhantomReference[size];
        for (int i=0; i<pa.length; i++) {
            pa[i] = new PhantomReference(new VeryBig("Phantom " +i), rq);
            System.out.println("just created: " + (VeryBig)pa[i].get());
            checkQueue();
        }
    }
}
