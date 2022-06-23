package hm.net.src.map.test;

import hm.net.java.util.HHashMap;
import org.junit.Test;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class HHashMapTest {

    @Test
    public void testPutVal() {
        HHashMap<String, String> map = new HHashMap<>();
        map.put("username", "Yan Jiahong");
        assert map.size() == 1;
    }
}
