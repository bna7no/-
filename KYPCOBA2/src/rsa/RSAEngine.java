package rsa;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class RSAEngine {

    private static final SecureRandom random = new SecureRandom();

    public static RSAKeyPair generateKeys(int bits) {

        BigInteger p = BigInteger.probablePrime(bits / 2, random);
        BigInteger q = BigInteger.probablePrime(bits / 2, random);

        while (p.equals(q)) {
            q = BigInteger.probablePrime(bits / 2, random);
        }

        BigInteger n = p.multiply(q);

        BigInteger phi = p.subtract(BigInteger.ONE)
                .multiply(q.subtract(BigInteger.ONE));

        BigInteger e = BigInteger.valueOf(65537);

        while (!phi.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.TWO);
        }

        BigInteger d = e.modInverse(phi);

        return new RSAKeyPair(e, d, n);
    }

    public static List<BigInteger> encrypt(
            String text,
            BigInteger e,
            BigInteger n
    ) {

        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);

        List<BigInteger> cipher = new ArrayList<>();

        for (byte b : bytes) {

            BigInteger message = BigInteger.valueOf(b & 0xFF);

            BigInteger encrypted = message.modPow(e, n);

            cipher.add(encrypted);
        }

        return cipher;
    }

    public static String decrypt(
            List<BigInteger> cipher,
            BigInteger d,
            BigInteger n
    ) {

        byte[] bytes = new byte[cipher.size()];

        for (int i = 0; i < cipher.size(); i++) {

            BigInteger decrypted = cipher.get(i).modPow(d, n);

            bytes[i] = decrypted.byteValue();
        }

        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String cipherToString(List<BigInteger> cipher) {

        StringBuilder sb = new StringBuilder();

        for (BigInteger block : cipher) {
            sb.append(block.toString(16)).append(" ");
        }

        return sb.toString();
    }
}