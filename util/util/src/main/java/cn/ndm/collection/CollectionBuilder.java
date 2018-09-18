package cn.ndm.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CollectionBuilder {
    private static final Map emptyMap;
    private static final Set emptySet;
    private static final Collection emptyCollection;

    static {
        emptyMap = Maps.newHashMap();
        emptySet = Sets.newHashSet();
        emptyCollection = Lists.newArrayList();
    }

    public static String getString(Object obj){
        return obj == null ? "" : obj.toString();
    }
}
