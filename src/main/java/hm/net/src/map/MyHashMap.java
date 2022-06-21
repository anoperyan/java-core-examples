package hm.net.src.map;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Yan Jiahong
 * Created on 2022/6/20
 */
public class MyHashMap<K, V> extends AbstractMap<K, V>
        implements Map<K, V>, Cloneable, Serializable {

    public MyHashMap() {

    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new HashSet<>();
    }


    public static void main(String[] args) {
        MyHashMap<String, String> map = new MyHashMap<>();
        //map.put("xx", "xx");
        System.out.println(map);
    }
}
