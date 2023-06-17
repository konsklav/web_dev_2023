package helperclasses;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashHelper
{
    // Generates random salt based on the length provided and returns it to the method call
    public static String generateSalt(){
        String salt = null;
        SecureRandom r = new SecureRandom();
        // Generate random number between 0 and 45 and add 5 (final number: [5, 50])
        int length = r.nextInt(46) + 5;

        // Generates random String
        StringBuilder sb = new StringBuilder(length);
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$&()";
        SecureRandom r2 = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = r2.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        salt = sb.toString();
        return salt;
    }

    //Hashes the given password using the specified salt
    public static String hashPassword(String password, String salt){
        String hashedPassword = null;

        final String toHash = salt + password + salt;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            return "00000000000000000000000000000000";
        }
        messageDigest.update(toHash.getBytes(), 0, toHash.length());

        hashedPassword = new BigInteger(1, messageDigest.digest()).toString(16);

        if (hashedPassword.length() < 32) {
            hashedPassword = "0" + hashedPassword;
        }

        return hashedPassword;
    }
}
