package hm.net.exam.aqs;

import hm.net.java.util.HHashMap;

/**
 * @author Yan Jiahong
 * Created on 2022/6/20
 */
public class HmHashMapTest {
    public static void main(String[] args) {
        HHashMap<String, String> map = new HHashMap<>();
        map.put("yjh", "19863");
        map.put("yjh_01", "1121244");
        System.out.println(map);
        //Map<String, String> map = new HashMap<>();
        //System.out.println(map);
    }
}
