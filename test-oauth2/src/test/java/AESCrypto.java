import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 
 * <b>功能描述: aes工具</b><br> TODO
 * @author 	lbo@cndatacom.com
 * @version 1.0.0
 * @since 	JDK 1.8
 * 
 * @Note
 * <b>创建时间:</b> 2019-11-21 16:35:28
 */
@SuppressWarnings("restriction")
public class AESCrypto {
    private static Charset CHARSET = Charset.forName("utf-8");
    private String appId;
    private byte[] aesKey;

    // 生成4个字节的网络字节序
    private static byte[] getNetworkBytesOrder(int sourceNumber) {
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte)(sourceNumber & 0xFF);
        orderBytes[2] = (byte)(sourceNumber >> 8 & 0xFF);
        orderBytes[1] = (byte)(sourceNumber >> 16 & 0xFF);
        orderBytes[0] = (byte)(sourceNumber >> 24 & 0xFF);
        return orderBytes;
    }

    // 还原4个字节的网络字节序
    private static int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        for (int i = 0; i < 4; i++) {
            sourceNumber <<= 8;
            sourceNumber |= orderBytes[i] & 0xff;
        }
        return sourceNumber;
    }

    // 随机生成16位字符串
    private static String getRandomStr() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(base.length());
            buffer.append(base.charAt(number));
        }
        return buffer.toString();
    }

    /**
     * 对明文进行加密.
     *
     * @param text
     *            需要加密的明文
     * @return 加密后base64编码的字符串
     * @throws AESCryptoException
     *             aes加密失败
     */
    private static String encryptData(String randomStr, byte[] text, byte[] appId, byte[] aesKey)
        throws AESCryptoException {
        ByteGroup byteCollector = new ByteGroup();
        byte[] randomStrBytes = randomStr.getBytes(CHARSET);
        byte[] networkBytesOrder = getNetworkBytesOrder(text.length);

        // randomStr + networkBytesOrder + text + appId
        byteCollector.addBytes(randomStrBytes);
        byteCollector.addBytes(networkBytesOrder);
        byteCollector.addBytes(text);
        byteCollector.addBytes(appId);

        // ... + pad: 使用自定义的填充方式对明文进行补位填充
        byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
        byteCollector.addBytes(padBytes);

        // 获得最终的字节流, 未加密
        byte[] unencrypted = byteCollector.toBytes();

        try {
            // 设置加密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            // 加密
            byte[] encrypted = cipher.doFinal(unencrypted);

            // 使用BASE64对加密后的字符串进行编码
            return Base64.encode(encrypted);

        } catch (GeneralSecurityException e) {
            throw new AESCryptoException(e.getMessage(), e);
        }
    }

    /**
     * 对密文进行解密.
     *
     * @param text
     *            需要解密的密文
     * @return 解密得到的明文
     * @throws AESCryptoException
     *             aes解密失败
     */
    private static byte[] decryptData(String text, String appId, byte[] aesKey) throws AESCryptoException {
        byte[] original;
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);

            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decode(text);

            // 解密
            original = cipher.doFinal(encrypted);
        } catch (GeneralSecurityException e) {
            throw new AESCryptoException(e.getMessage(), e);
        }

        // 去除补位字符
        byte[] bytes = PKCS7Encoder.decode(original);

        // 分离16位随机字符串,网络字节序和appId
        byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

        int length = recoverNetworkBytesOrder(networkOrder);

        byte[] backAppId = Arrays.copyOfRange(bytes, 20 + length, bytes.length);
        if (!appId.equals(new String(backAppId))) {
            throw new AESCryptoException("AppId不匹配", null);
        }
        return Arrays.copyOfRange(bytes, 20, 20 + length);
    }

    /**
     * 构造函数
     * 
     * @param appId
     *            拿到应用对应的appId 也就是 thirdpartyCode
     * @param encodingAesKey
     *            取得系统生成的 aesKey
     */
    public AESCrypto(String appId, String encodingAesKey) {
        this.appId = appId;
        this.aesKey = encodingAesKey.getBytes();
    }

    /**
     * 加密
     * 
     * @param content
     * @return
     * @throws AESCryptoException
     */
    public String encrypt(byte[] content) throws AESCryptoException {
        return encryptData(getRandomStr(), content, this.appId.getBytes(), this.aesKey);
    }

    /**
     * 解密
     * 
     * @param postData
     * @return
     * @throws AESCryptoException
     */
    public byte[] decrypt(String postData) throws AESCryptoException {
        return decryptData(postData, this.appId, this.aesKey);
    }

    // 生成 authenticator
    public static void main(String[] args) {
        String key = "86688b1be70059cc";   // 分配给子系统的安全码
        String code = "gxdj";  //子系统编码
        
        AESCrypto aesCrypto = new AESCrypto(code, key);
        try {
            // 这个key.getBytes() 是文档中的 msg 要求 用key 作为 msg
            String a = aesCrypto.encrypt(key.getBytes());
            System.out.println(a);
            a = new String(aesCrypto.decrypt(a));
            System.out.println(a);
        } catch (AESCryptoException e) {
            e.printStackTrace();
        }
    }
}
