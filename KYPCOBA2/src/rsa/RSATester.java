package rsa;

import java.math.BigInteger;
import java.util.List;

public class RSATester {

    public static void runAllTests() {

        System.out.println("\n========= ТЕСТУВАННЯ =========");

        testEncryption();
        testUnicode();
        testKeySizes();

        System.out.println("Усі тести завершені.");
    }

    private static void testEncryption() {

        RSAKeyPair keys = RSAEngine.generateKeys(512);

        String text = "Hello RSA";

        List<BigInteger> cipher = RSAEngine.encrypt(
                text,
                keys.getPublicExponent(),
                keys.getModulus()
        );

        String decrypted = RSAEngine.decrypt(
                cipher,
                keys.getPrivateExponent(),
                keys.getModulus()
        );

        if (text.equals(decrypted)) {
            System.out.println("Тест шифрування: УСПІШНО");
        } else {
            System.out.println("Тест шифрування: ПОМИЛКА");
        }
    }

    private static void testUnicode() {

        RSAKeyPair keys = RSAEngine.generateKeys(512);

        String text = "Привіт RSA 😀";

        List<BigInteger> cipher = RSAEngine.encrypt(
                text,
                keys.getPublicExponent(),
                keys.getModulus()
        );

        String decrypted = RSAEngine.decrypt(
                cipher,
                keys.getPrivateExponent(),
                keys.getModulus()
        );

        if (text.equals(decrypted)) {
            System.out.println("Тест Unicode: УСПІШНО");
        } else {
            System.out.println("Тест Unicode: ПОМИЛКА");
        }
    }

    private static void testKeySizes() {

        int[] sizes = {512, 1024, 2048};

        for (int size : sizes) {

            RSAKeyPair keys = RSAEngine.generateKeys(size);

            if (keys.getKeySize() >= size - 1) {
                System.out.println("Тест ключа " + size + " біт: УСПІШНО");
            } else {
                System.out.println("Тест ключа " + size + " біт: ПОМИЛКА");
            }
        }
    }
}