package hm.net.src.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yan Jiahong
 * Created on 2022/6/20
 */
public class Test {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("Xx", "xx");
        System.out.println(map);
        new HashMap<>();
        //int oldCap = 16;

        //System.out.printf("%s\r\n", Integer.toBinaryString(oldCap));
        //int newCap = 32;
        //
        //int hashCode = 26586;
        //for (int i = 1; i < 16; i++) {
        //    hashCode += i;
        //    //System.out.println(oldCap & hashCode);
        //    System.out.printf("oldCap: %010x, old idx: %s, new idx: %s, hashCode & oldCap) == 0: %s\r\n",
        //            oldCap,
        //            (hashCode & (oldCap - 1)),
        //            (hashCode & (newCap - 1)),
        //            ((hashCode & oldCap) == 0));
        //}
    }


}
