package se.jensen.niclas.springbootrestapi.KeyGenerator;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

/**
 *
 * Class that generates key pairs for JWT configuration
 *
 */

public class MyKeyGenerator {

    /**
     *
     * Generates both private and public key.
     * Will be visable in the console
     *
     * @param args the program argument
     * @throws Exception is thrown in case the keys does not work
     */

    public static void main(String[] args) throws Exception {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();

        String privateKey = Base64.getEncoder()
                .encodeToString(keyPair.getPrivate().getEncoded());

        String publicKey = Base64.getEncoder()
                .encodeToString(keyPair.getPublic().getEncoded());

        System.out.println("PRIVATE_KEY:");
        System.out.println(privateKey);

        System.out.println();
        System.out.println("PUBLIC_KEY:");
        System.out.println(publicKey);
    }
}
