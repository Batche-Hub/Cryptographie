/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.doranco.cryptage.pbe;

import fr.doranco.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Boule
 */
public abstract class CryptagePBE {

    private static final int KEY_SIZE = 128;
    private static final String KEY_ALGO = "AES";
    private static final String ALGOTRANSFORM = "AES/CBC/PKCS5Padding";
    private static final String KDF = "PBKDF2WithHmacSHA1";
    private static final int ITERATION = 1000;
    private static final Random RANDOM = new SecureRandom();

    private CryptagePBE() {
    }

    /**
     * On crypte à partir de la clé PBE générée grâce à la méthode plus bas.
     * C'est très similaire aux autres méthodes de cryptage, hormis le fait que nous avons des paramètres d'initialisation, le IvParameterSpec.
     * C'est un vecteur qui est sur 16 bytes (128bits) et qui permet de randomiser l'encryptage des données. Ainsi, celui-ci doit toujours être unique
     * afin de se protéger des attaques sémantiques c'est à dire empêcher une tierce partie de découvrir un pattern dans l'arrangement des blocks. Comment cela est possible ?
     * Cipher en fait, découpe les blocs. Si ces blocs ne sont pas "mélangés", on peut retrouver facilement le message grâce à une simple opération mathématique,
     * ce qui rend de fait notre cryptage nul. Expliquons un peu : si nous avons deux messages qu'on découpe par bloc de 4 caractères et qu'on chiffre ces caractère, on peut voir facilement
     * la relation qu'entretiennent les blocs d'origine avec les blocs "chiffrés".
     * 
     * 
     * On utilise donc des vecteurs d'initialisation qui permettent de "mélanger" les différent blocs, et donc de rendre caduc la possibilité
     * de découvrir les messages en clair par la simple opération mathématique (qui met en relation deux messages chiffrés avec la même clé sans vecteur) décrite plus haut.
     * Enfin impossible... Il n'y a jamais d'impossible en terme de sécurité, mais dans l'état actuel des puissances de calcul disponible, ce serait très compliqué.
     * L'idée, comme toujours, est de dissuader !
     * 
     * Notons, avant de terminer, qu'il est très important d'utiliser un vecteur d'initialisation unique à CHAQUE FOIS. Car sinon, si le vecteur est toujours le même, on se retrouve
     * avec des motifs apparents, ce qui rend de nouveau le chiffrage nul... 
     * généré
     * @param objetACrypter
     * @param clePBE
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     * @throws InvalidAlgorithmParameterException 
     */
    public static final byte[] encrypter(Object objetACrypter, SecretKey clePBE) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidAlgorithmParameterException {

        Cipher cipher = Cipher.getInstance(ALGOTRANSFORM);

        cipher.init(Cipher.ENCRYPT_MODE, clePBE,new IvParameterSpec(new byte[16]));
        byte[] objetBytes = Utils.convertToBytes(objetACrypter);
        byte[] cipherBytes = cipher.doFinal(objetBytes);
        return cipherBytes;
    }
    /**
     * On décrypte le message crypté. Ici j'ai fait un retour en string qui nous donne un tableau de bytes, qui permet aisément de voir si le message original est bien retrouvé.
     * Si on fait un retour de type "new String (cipher.doFinal(stringCrypte));", on aura quelque chose qui nous semblera différent alors que ce n'est pas le cas.
     * La bonne manière de présenter en String un tableau est bien : Arrays.toString(monTableau).
     * @param stringCrypte
     * @param cle
     * @param encodage
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException 
     */
    public static final String decrypter(byte[] stringCrypte, SecretKey cle, String encodage) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(ALGOTRANSFORM);
        
        cipher.init(Cipher.DECRYPT_MODE, cle, new IvParameterSpec(new byte[16]));
        //Ne pas oublier de caster le retour afin de récupérer l'objet désiré !
        return Arrays.toString(cipher.doFinal(stringCrypte));
    }

    /**
     * Ici on crée une clé à partir du mot de passe. L'idée est de saler ce mot de passe afin de le rendre plus difficile à retrouver
     * et viter des pattern visible au niveau des clés. Ainsi le mot de passe rend la clé plus difficile encoree à cracker.
     * Pour saler, on fait appel à une méthode présente plus bas, qui crée un tableau de caractère de 16 bytes, càd 128bits.
     * On pourrait complexifier la chose en choisissant 32 bytes (comme c'est parfois conseillé), ce qui donnerait un salage de 256bits.
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public static final SecretKey generatePBEKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKey clePBE = null;
        char[] passwordTochar = password.toCharArray();
        PBEKeySpec pbeKeySpec = new PBEKeySpec(passwordTochar, CryptagePBE.getNextSalt(), ITERATION, KEY_SIZE);
        //On vide le tableau de charactère du mot de passe.
        password = "";
        for (int j = 0; j < passwordTochar.length; j++) {
            passwordTochar[j] = 0;
        }
        
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KDF);
        SecretKey keyPBE = keyFactory.generateSecret(pbeKeySpec);
        clePBE = new SecretKeySpec(keyPBE.getEncoded(), KEY_ALGO);
        return clePBE;
    }
    /**
     * La méthode @getNextSalt() permet de un "salage" généré à partir d'un nombre aléatoire
     * obtenu grâce à un secure random présent plus haut (c'est très important d'utiliser un secure random)
     * En effet, la randomisation en informatique n'est pas chose si aisée...
     * On invoquera cette méthode pour générer la clé PBE.
     * @return 
     */
    public static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Cette méthode nous sert à convertir notre clé en bytes afin de pouvoir la stocker
     * et la retrouver par la suite.
     * En effet, si on peut utiliser la méthode cle.getEncoded(), cette méthode ne possède pas
     * de réciproque, ce qui rend tout simplement impossible de retrouver notre clé ! Au revoir les données !
     * Cette méthode est la même que celle présente dans le package utils, mais j'ai préféré
     * en mettre une ici afin que la clé puisse bénéficier de sa propre méthode qui n'accepte
     * que une SecretKey en argument.
     * @param key
     * @return
     * @throws IOException 
     */
    public static final byte[] convertKeyToBytes(SecretKey key) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(key);
            return bos.toByteArray();
        }
    }

    /**
     * Cette méthode est à invoquer lorsqu'on veut retrouver le clé en format SecretKey (ou Key si vous l'utilisez)
     * afin de pouvoir l'utiliser afin de déchiffrer nos données. Cette méthode retourne une SecretKey, ainsi, pas besoin de caster.
     * @param keyData
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static final SecretKey convertKeyFromBytes(byte[] keyData) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(keyData);
                ObjectInput in = new ObjectInputStream(bis)) {
            return (SecretKey) in.readObject();
        }
    }
}
