package plotly.doc;

import org.mozilla.javascript.NativeArray;

// This class is to override NativeArray#getDefaultValue. Ideally we would just do this in an anonymous class in Scala
// code, but https://github.com/scala/bug/issues/11575 makes this impossible.
class NativeArrayWithDefault extends NativeArray {
    private final Object defaultValue;

    public NativeArrayWithDefault(Object[] array, Object defaultValue) {
        super(array);
        this.defaultValue = defaultValue;
    }

    @Override
    public Object getDefaultValue(Class<?> hint) {
        return defaultValue;
    }
}