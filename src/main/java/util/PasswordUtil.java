package util;

import data.Login;
import database.DatabaseQueries;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class PasswordUtil {
    private static final int HASH_SALT_BYTE_SIZE = 16;
    private static final String RNG_ALGORITHM = "SHA1PRNG";
    private static final String RNG_PROVIDER = "SUN";

    public static String hashPassword(String unhashedPassword, String salt) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(unhashedPassword.getBytes());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.out);
        }

        return generatedPassword;
    }

    public static String createSalt() throws NoSuchAlgorithmException, NoSuchProviderException {

        SecureRandom secureRandom = SecureRandom.getInstance(RNG_ALGORITHM, RNG_PROVIDER);
        byte[] salt = new byte[HASH_SALT_BYTE_SIZE];
        secureRandom.nextBytes(salt);
        return salt.toString();
    }

    public static boolean checkPassword(String email, String password) {
        int emailID = DatabaseQueries.doesEmailExist(email);
        Login login = DatabaseQueries.getLogin(emailID);

        String inputPassword = hashPassword(password, login.getSalt());
        return inputPassword.equals(login.getPassword());
    }
}
