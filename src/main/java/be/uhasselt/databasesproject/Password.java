package be.uhasselt.databasesproject;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
    public static String hashString(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] message = messageDigest.digest(string.getBytes());

            BigInteger bigInteger = new BigInteger(1, message);
            String hashText = bigInteger.toString(16);

            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }

            return hashText;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isSamePassword(String input, int id) {
        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        String hashedRunnerPassword = runnerJdbi.getHashedPassword(id);
        String hashedInput = hashString(input);

        return hashedInput.equals(hashedRunnerPassword);
    }
}
