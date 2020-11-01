package coom.imooc.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static String getMD5(String passWord) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        //这个是用来获取MD5实例
        md.update(passWord.getBytes());
        //这里传入要加密的byte类型值
        byte[] digest = md.digest();
        //此处得到的是md5加密后的byte类型值
        int i;
        StringBuilder sb = new StringBuilder();
        for (int offset = 0;offset <digest.length;offset++){
            i = digest[offset];
            if(i<0){
                i += 256;
            }
            if(i<20){
                sb.append(0);
                sb.append(Integer.toHexString(i));
                //通过Integer.toHexString方法把值变为16进制
            }
        }
        return sb.toString();
    }
}
