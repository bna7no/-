package rsa;

import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static RSAKeyPair currentKeys = null;
    private static List<BigInteger> lastCipher = null;
    private static String lastPlainText = null;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            System.out.println("\n========= RSA ШИФРУВАННЯ =========");
            System.out.println("1. Згенерувати RSA-ключі");
            System.out.println("2. Зашифрувати текст");
            System.out.println("3. Розшифрувати текст");
            System.out.println("4. Показати ключі");
            System.out.println("5. Запустити тести");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    generateKeys();
                    break;

                case "2":
                    encryptText();
                    break;

                case "3":
                    decryptText();
                    break;

                case "4":
                    showKeys();
                    break;

                case "5":
                    RSATester.runAllTests();
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Невірний вибір.");
            }
        }

        System.out.println("Програма завершена.");
    }

    private static void generateKeys() {

        System.out.println("\nОберіть розмір ключа:");
        System.out.println("1 - 512 біт");
        System.out.println("2 - 1024 біт");
        System.out.println("3 - 2048 біт");

        System.out.print("Вибір: ");
        String option = scanner.nextLine();

        int bits;

        switch (option) {
            case "2":
                bits = 1024;
                break;

            case "3":
                bits = 2048;
                break;

            default:
                bits = 512;
        }

        long start = System.currentTimeMillis();

        currentKeys = RSAEngine.generateKeys(bits);

        long end = System.currentTimeMillis();

        System.out.println("\nКлючі успішно згенеровані.");
        System.out.println("Час генерації: " + (end - start) + " мс");
    }

    private static void encryptText() {

        if (currentKeys == null) {
            System.out.println("Спочатку згенеруйте ключі.");
            return;
        }

        System.out.print("\nВведіть текст: ");
        String text = scanner.nextLine();

        long start = System.currentTimeMillis();

        lastCipher = RSAEngine.encrypt(
                text,
                currentKeys.getPublicExponent(),
                currentKeys.getModulus()
        );

        lastPlainText = text;

        long end = System.currentTimeMillis();

        System.out.println("\nЗашифрований текст:");
        System.out.println(RSAEngine.cipherToString(lastCipher));

        System.out.println("\nЧас шифрування: " + (end - start) + " мс");
    }

    private static void decryptText() {

        if (currentKeys == null || lastCipher == null) {
            System.out.println("Немає даних для розшифрування.");
            return;
        }

        long start = System.currentTimeMillis();

        String decrypted = RSAEngine.decrypt(
                lastCipher,
                currentKeys.getPrivateExponent(),
                currentKeys.getModulus()
        );

        long end = System.currentTimeMillis();

        System.out.println("\nРозшифрований текст:");
        System.out.println(decrypted);

        System.out.println("\nЧас розшифрування: " + (end - start) + " мс");

        if (decrypted.equals(lastPlainText)) {
            System.out.println("Текст успішно відновлено.");
        } else {
            System.out.println("Помилка розшифрування.");
        }
    }

    private static void showKeys() {

        if (currentKeys == null) {
            System.out.println("Ключі ще не згенеровані.");
            return;
        }

        System.out.println("\n=== ВІДКРИТИЙ КЛЮЧ ===");
        System.out.println("e = " + currentKeys.getPublicExponent());
        System.out.println("n = " + currentKeys.getModulus());

        System.out.println("\n=== ЗАКРИТИЙ КЛЮЧ ===");
        System.out.println("d = " + currentKeys.getPrivateExponent());
        System.out.println("n = " + currentKeys.getModulus());
    }
}