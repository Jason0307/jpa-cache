package org.zhubao.util;

import java.security.NoSuchAlgorithmException;

public class Digest {

    // public static void main(String[] args) {
    //
    // String str = "123";
    // String result = "";
    // java.security.MessageDigest alga = null;
    // try {
    // alga = java.security.MessageDigest.getInstance("MD5");
    // } catch (NoSuchAlgorithmException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // alga.update(str.getBytes());
    // byte[] digesta = alga.digest();
    // result =byte2hex(digesta);
    // System.out.println(result);
    // }

    /**
     * @Title: byte2hex
     * @Description: binary is converted to Hexadecimal string
     * @param b
     * @return String
     */
    private String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }

    /**
     * @Title: hex2byte
     * @Description: string Hexadecimal is converted to binary
     * @param str
     * @return byte[]
     */
    @SuppressWarnings("unused")
    private byte[] hex2byte(String str) {
        if (str == null)
            return null;
        str = str.trim();
        int len = str.length();
        if (len == 0 || len % 2 == 1)
            return null;
        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[i / 2] = (byte) Integer
                        .decode("0x" + str.substring(i, i + 2)).intValue();
            }
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @Title: returnMd5
     * @Description: get string Hexadecimal
     * @param str
     * @return String
     */
    public String returnMd5(String str) {
        java.security.MessageDigest alga = null;
        try {
            alga = java.security.MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        alga.update(str.getBytes());
        byte[] digesta = alga.digest();
        str = byte2hex(digesta);
        return str;
    }

}