##Parcist
This is a simple Android library that piggy-backs off of `Parcel` to convert objects to and from byte format.  Currently this is in alpha, so expect it to change quite a bit.  This will eventually be a library project that you can add via Gradle.

Parcist easily works with any existing Parcelable object you have.  It actually works with any plain Object, but has better defined use if you stick with Parcelable.  Check it out, it's very easy!

Marshalling:
    byte[] bytes = Parcist.writeTypedList(testObjects);
    
Unmarshalling:
    try {
        List<User> users2 = new ArrayList<User>();
        Parcist.readTypedList(bytes, users2, User.CREATOR);
    } catch (ParcistInvalidatedException e) {
        e.printStackTrace();
    }

##But... isn't that bad?
There's an inherent risk to using this library, but it is very small.  Parcist depends upon Parcel's native methods, and if those change between the time your object is marshalled and unmarshalled, then it could fail.  Luckily, Android updates don't happen often and at this point they probably won't touch Parcel's implementation.  Still, please be aware of this.

Don't fret though.  This problem can be solved by copying Parcel's native implementation into my library so that it's guaranteed not to change.  Once Android Studio has better native support I will start this. :)

##Why should I use it then?
Speed!

This may not be the right solution to persist an object for a very long time, but not everything needs that kind of a shelf life.  Think of a cache.  If -- in the very small chance it happens -- the data can't be unmarshalled then just invalidate the cache.

##You mentioned speed...
Yes.  Early benchmarks have shown a speed improvement of 8-9x over Gson.  Here is a sample benchmark running on my Motorola Droid MAXX phone:

10 Samples, List with 10,000 objects
    D/benchmark﹕ Speed ratio: GsonBenchmark : ParcistBenchmark 5.52
    D/benchmark﹕ Size ratio: GsonBenchmark : ParcistBenchmark 0.89
    
10 Samples, List with 50,000 objects
    D/benchmark﹕ Speed ratio: GsonBenchmark : ParcistBenchmark 7.98
    D/benchmark﹕ Size ratio: GsonBenchmark : ParcistBenchmark 0.89

##What's left
- Investigate the usefulness of a chaining API to make Parcist'ing multiple objects easier.
- Copy Parcel's native implementation to remove framework dependency.