package api.handle.util;

import org.apache.commons.lang.RandomStringUtils;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-9-11 上午9:45
 */

public class NonceRandomUtil {
    private final SimpleDateFormat INTERNATE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private final String[] SPACES = { "0", "00", "0000", "00000000" };
    private Date lastTime;
    private int counter = 0;

    public String randomString(int length)
    {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public int randomInt()
    {
        return new SecureRandom().nextInt();
    }

    public String randomHexInt()
    {
        return Integer.toHexString(randomInt());
    }

    public long randomLong()
    {
        return new SecureRandom().nextLong();
    }

    public String randomHexLong()
    {
        return Long.toHexString(randomLong());
    }

    public String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    public String currentTimestamp()
    {
        Date now = new Date();
        return INTERNATE_DATE_FORMAT.format(now);
    }

    public long currentMills()
    {
        return System.currentTimeMillis();
    }

    public String currentHexMills()
    {
        return Long.toHexString(currentMills());
    }

    public synchronized String getCounter()
    {
        Date currentTime = new Date();

        if (currentTime.equals(lastTime)) {
            counter += 1;
        } else {
            lastTime = currentTime;
            counter = 0;
        }
        return Integer.toHexString(counter);
    }

    public String format(String source, int length)
    {
        int spaceLength = length - source.length();
        StringBuilder buf = new StringBuilder();

        while (spaceLength >= 8) {
            buf.append(SPACES[3]);
            spaceLength -= 8;
        }

        for (int i = 2; i >= 0; i--) {
            if ((spaceLength & 1 << i) != 0) {
                buf.append(SPACES[i]);
            }
        }

        buf.append(source);
        return buf.toString();
    }
    public static void main(String[] args) throws IOException {
        NonceRandomUtil nonceRandomUtil = new NonceRandomUtil();
        System.out.println(nonceRandomUtil.randomString(16));
        System.out.println(nonceRandomUtil.randomInt());
        System.out.println(nonceRandomUtil.randomHexInt());
        System.out.println(nonceRandomUtil.randomLong());
        System.out.println(nonceRandomUtil.randomHexLong());
        System.out.println(nonceRandomUtil.randomUUID());
        System.out.println(nonceRandomUtil.currentTimestamp());
        System.out.println(nonceRandomUtil.currentMills());
        System.out.println(nonceRandomUtil.currentHexMills());
        System.out.println(nonceRandomUtil.getCounter());
    }
}
