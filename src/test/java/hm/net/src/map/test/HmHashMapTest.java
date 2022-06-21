package hm.net.src.map.test;

import hm.net.src.map.HmHashMap;
import org.junit.Test;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class HmHashMapTest {

    @Test
    public void testPutVal() {
        HmHashMap<String, String> map = new HmHashMap<>();
        map.put("username", "Yan Jiahong");
        assert map.size() == 1;
    }
}
