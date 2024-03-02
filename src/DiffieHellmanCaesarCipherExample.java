public class DiffieHellmanCaesarCipherExample {

    public static void main(String[] args) {
        // Заданные параметры
        int p = 19; // Простое число p
        int G = 29; // Примитивный корень по модулю p
        int bobPrivateKey = 6; // Приватный ключ Боба
        int alicePrivateKey = 3; // Приватный ключ Алисы

        // Вычисление публичных ключей
        int bobPublicKey = generatePublicKey(G, bobPrivateKey, p); // Публичный ключ Боба
        int alicePublicKey = generatePublicKey(G, alicePrivateKey, p); // Публичный ключ Алисы

        // Вычисление сессионного ключа
        int bobSessionKey = generateSessionKey(alicePublicKey, bobPrivateKey, p); // Сессионный ключ Боба
        int aliceSessionKey = generateSessionKey(bobPublicKey, alicePrivateKey, p); // Сессионный ключ Алисы

        // Вывод результатов
        System.out.println("Публичный ключ Боба: " + bobPublicKey);
        System.out.println("Публичный ключ Алисы: " + alicePublicKey);
        System.out.println("Сессионный ключ Боба: " + bobSessionKey);
        System.out.println("Сессионный ключ Алисы: " + aliceSessionKey);

        // Шифрование и расшифровка сообщения
        String message = "Hello, Alice!";
        System.out.println("Отправленное сообщение: " + message);
        String encryptedMessage = encrypt(message, bobSessionKey); // Зашифрованное сообщение
        System.out.println("Зашифрованное сообщение: " + encryptedMessage);
        String decryptedMessage = decrypt(encryptedMessage, aliceSessionKey); // Расшифрованное сообщение
        System.out.println("Расшифрованное сообщение: " + decryptedMessage);
    }

    // Вычисление публичного ключа по формуле (G^приватный_ключ) mod P
    private static int generatePublicKey(int G, int privateKey, int p) {
        return modPow(G, privateKey, p); // Используется метод modPow для вычисления степени по модулю
    }

    // Вычисление сессионного ключа по формуле (публичный_ключ_собеседника^ свой_приватный_ключ) mod P
    private static int generateSessionKey(int publicKey, int privateKey, int p) {
        return modPow(publicKey, privateKey, p); // Используется метод modPow для вычисления степени по модулю
    }

    // Вспомогательный метод для вычисления (a^b) mod p
    private static int modPow(int a, int b, int p) {
        long result = 1;
        for (int i = 0; i < b; i++) {
            result = (result * a) % p; // Вычисление степени a^b по модулю p
        }
        return (int) result; // Возвращение результата в виде целого числа
    }

    // Шифрование сообщения с помощью алгоритма Цезаря
    private static String encrypt(String message, int key) {
        StringBuilder encryptedMessage = new StringBuilder();
        for (char c : message.toUpperCase().toCharArray()) { // Приводим все символы к верхнему регистру
            if (Character.isLetter(c)) {
                char shiftedChar = (char) (((c - 'A') + key) % 26 + 'A'); // Шифруем символ с учетом ключа
                encryptedMessage.append(shiftedChar); // Добавляем зашифрованный символ к зашифрованному сообщению
            } else {
                encryptedMessage.append(c); // Оставляем символ без изменений, если это не буква
            }
        }
        return encryptedMessage.toString(); // Возвращаем зашифрованное сообщение в виде строки
    }

    // Расшифровка сообщения с помощью алгоритма Цезаря
    private static String decrypt(String encryptedMessage, int key) {
        StringBuilder decryptedMessage = new StringBuilder();
        for (char c : encryptedMessage.toUpperCase().toCharArray()) { // Приводим все символы к верхнему регистру
            if (Character.isLetter(c)) {
                char shiftedChar = (char) (((c - 'A') - key + 26) % 26 + 'A'); // Расшифровываем символ с учетом ключа
                decryptedMessage.append(shiftedChar); // Добавляем расшифрованный символ к расшифрованному сообщению
            } else {
                decryptedMessage.append(c); // Оставляем символ без изменений, если это не буква
            }
        }
        return decryptedMessage.toString(); // Возвращаем расшифрованное сообщение в виде строки
    }
}