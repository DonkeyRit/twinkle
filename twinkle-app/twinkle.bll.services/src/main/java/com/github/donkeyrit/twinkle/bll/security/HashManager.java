package com.github.donkeyrit.twinkle.bll.security;

import java.security.MessageDigest;

public class HashManager 
{
    public static String generateHash(String input)
    {
        /**
         * Method convert input string into
         * hash with method sha1-1
         */
        String res = "";
        try
        {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) 
            {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            res = sb.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
         
        return res;
    }
}
