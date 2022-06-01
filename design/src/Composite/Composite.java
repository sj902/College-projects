package Composite;
import java.util.*;

public class Composite {}

interface ValueContainer extends Iterable<Integer> {}

class SingleValue implements ValueContainer {
    public int value;

    // please leave this constructor as-is
    public SingleValue(int value)
    {
        this.value = value;
    }

    @Override
    public Iterator<Integer> iterator() {
        return Collections.singleton(this.value).iterator();
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return Collections.singleton(this.value).spliterator();
    }
}

class ManyValues extends ArrayList<Integer> implements ValueContainer {}


class MyList extends ArrayList<ValueContainer> {
    // please leave this constructor as-is
    public MyList(Collection<? extends ValueContainer> c)
    {
        super(c);
    }

    public int sum()
    {
        int s = 0;
        for(ValueContainer vc: this){
           for(int n: vc){
               s += n;
           }
        }
        return s;
    }
}