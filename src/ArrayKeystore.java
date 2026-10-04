import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayKeystore implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    public static ArrayList<Account> startup() {
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKey key = LoadFromKeyStore("keystore.keystore","1213131564872");
            if (key == null){
                key = generateKey();
                ArrayKeystore.StoreToKeyStore(key,"1213131564872","keystore.keystore");
            }

            byte[] fileBytes = Files.readAllBytes(Paths.get("encrypted.dat"));
            byte[] ivBytes = new byte[16];
            System.arraycopy(fileBytes,0,ivBytes,0,16);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            byte[] encryptedData = Arrays.copyOfRange(fileBytes,16,fileBytes.length);

            return decryptObject(encryptedData, key, iv, cipher);

        }
        catch (Exception e) {
            System.out.println("No previous data found: " + e);
            return new ArrayList<>();
        }
    }

    public static void encryptObject(ArrayList<Account> arrayList){
        try{
            SecretKey myKey = generateKey();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = generateIV();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(arrayList);
            byte[] objectBytes = bos.toByteArray();
            cipher.init(Cipher.ENCRYPT_MODE, myKey, iv);
            byte[] encrypted = cipher.doFinal(objectBytes);

            ArrayKeystore.StoreToKeyStore(myKey,"1213131564872","keystore.keystore");
            ArrayKeystore.StoreArrayList(encrypted, iv);

        }
        catch (Exception e){
            System.out.println("Error Encrypting: " + e);
        }
    }
    @SuppressWarnings("unchecked")
    public static ArrayList<Account> decryptObject(byte[] encryptedData, SecretKey myKey, IvParameterSpec iv, Cipher cipher){
        try {
            cipher.init(Cipher.DECRYPT_MODE, myKey, iv);
            byte[] plainTextBytes = cipher.doFinal(encryptedData);
            ByteArrayInputStream bis = new ByteArrayInputStream(plainTextBytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (ArrayList<Account>) ois.readObject();
        }
        catch (Exception e){
            System.out.println("Error loading data: " + e);
            return null;
        }
    }
    public static SecretKey generateKey(){
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        }
        catch (Exception e){
            return null;
        }
    }
    public static IvParameterSpec generateIV(){
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static void StoreToKeyStore(SecretKey keyToStore, String password, String filepath) throws Exception {
        KeyStore javaKeyStore = KeyStore.getInstance("JCEKS");
        javaKeyStore.load(null,null);

        javaKeyStore.setKeyEntry("keyAlias", keyToStore, password.toCharArray(),null);
        OutputStream writeStream = new FileOutputStream(filepath);
        javaKeyStore.store(writeStream, password.toCharArray());
    }

    public static SecretKey LoadFromKeyStore(String filepath, String password){
        try {
            KeyStore keyStore = KeyStore.getInstance("JCEKS");
            InputStream readStream = new FileInputStream(filepath);
            keyStore.load(readStream, password.toCharArray());
            return (SecretKey) keyStore.getKey("keyAlias", password.toCharArray());
        }
        catch (Exception e){
            System.out.println("Error loading key: " + e);
            return null;
        }

    }

    public static void StoreArrayList(byte[] encrypted, IvParameterSpec iv){
        try(FileOutputStream fos = new FileOutputStream("encrypted.dat")){
            fos.write(iv.getIV());
            fos.write(encrypted);
        }
        catch (Exception e){
            System.out.println("Error saving account: " + e);
        }
    }

}
