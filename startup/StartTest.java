package com.fr.performance.startup;

import com.fr.third.org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;

import static com.fr.performance.reader.EscapeStrReader.unescape;

/**
 * Created by yuwh on 2018/12/20
 * Description:none
 */
public class StartTest {
    public static void main(String args[]) throws UnsupportedEncodingException {
        /*char a = ' ';
        byte b = 0x2D;
        byte[] c = {0x2D,0x4E};
        */
        String d = "0x00D6, 0x00D0";
        /*System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        */
        System.out.println(d);
        byte [] bytes = d.getBytes("iso-8859-1");
        System.out.println(bytes);
        String e = new String( d.getBytes("iso-8859-1"), "GB2312");
        System.out.println(e);
        String f = StringEscapeUtils.unescapeJava("\u4E2D\u56FD");
        System.out.println(f);
        String g = unescape("/hhe/%u4E2D%u56FD%22.cpt");
        System.out.println(g);
    }
}
