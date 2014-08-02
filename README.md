##Parcist
This is a simple Android library that piggy-backs off of `Parcel` to convert objects to and from byte format.  Currently this is in alpha, so expect it to change quite a bit.  This will eventually be a library project that you can add via Gradle.

##But... isn't that bad?
There's an inherent risk to using this library.  Since it depends on Parcel, which is a framework class, updates to Android could potentially break your object's byte format.  For example, if you com.trevore.parcist.Parcist an object and write it to disk, receive an OTA Android update on your device, and then try to restore the object, there's a small chance it won't work.

##Why should I use it then?
Speed!

This may not be the right solution to store an object for a long time, but not everything needs that long of a shelf life.  Think of a cache.  If -- in the very small chance it happens -- the data can't be unmarshalled then just invalidate the cache.

##You mentioned speed...
Yes.  Early benchmarks have shown a speed improvement of over 10x for marshalling and unmarshalling data compared to GSON.  The trade-off is that the com.trevore.parcist.Parcist objects take 3x more space.