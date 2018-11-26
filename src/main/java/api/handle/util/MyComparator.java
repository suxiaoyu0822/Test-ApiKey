package api.handle.util;

import java.util.Comparator;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-11-5 下午4:33
 */

public class MyComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
}
