##Parcist
This is a simple Android library that piggy-backs off of `Parcel` to convert objects to and from byte format.  Currently this is in alpha, so expect it to change quite a bit.  This will eventually be a library project that you can add via Gradle.

##But... isn't that bad?
There's an inherent risk to using this library.  Since it depends on Parcel, which is a framework class, updates to Android could potentially break your object's byte format.  For example, if you com.trevore.parcist.Parcist an object and write it to disk, receive an OTA Android update on your device, and then try to restore the object, there's a small chance it won't work.

##Why should I use it then?
Speed!

This may not be the right solution to store an object for a long time, but not everything needs that long of a shelf life.  Think of a cache.  If -- in the very small chance it happens -- the data can't be unmarshalled then just invalidate the cache.

##You mentioned speed...
Yes.  Early benchmarks have shown a speed improvement of 8-9x over Gson.  Here is a sample benchmark running on my Motorola Droid MAXX phone:

    08-02 01:15:08.460    7482-7482/com.trevore.parcist D/benchmark﹕ Starting benchmark.
    08-02 01:15:22.022    7482-7482/com.trevore.parcist D/benchmark﹕ Average Gson time (ms): 1159.6
    08-02 01:15:22.022    7482-7482/com.trevore.parcist D/benchmark﹕ Average Parcist time (ms): 140.5
    08-02 01:15:22.022    7482-7482/com.trevore.parcist D/benchmark﹕ Gson space (b): 27000.1
    08-02 01:15:22.022    7482-7482/com.trevore.parcist D/benchmark﹕ Parcist space (b): 28000.4
    08-02 01:15:22.023    7482-7482/com.trevore.parcist D/benchmark﹕ Parcist is 8.25 times faster than Gson.
    08-02 01:15:22.024    7482-7482/com.trevore.parcist D/benchmark﹕ Parcist takes 1.04 times more space than Gson.
    08-02 01:15:22.024    7482-7482/com.trevore.parcist D/benchmark﹕ Benchmark complete.