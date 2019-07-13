# Android-RSA-Encryption

This project is an example of how one can encrypt messages on Android side using RSA.
Below is an instruction how to generate Public and Private keys and code sample of description process

## Keys generation

1. Generating key pairs
 ```shell 
 openssl genrsa -out "...\rsa\my_key" 1024
```

2. Converting to PKCS#8 format
```shell 
openssl pkcs8 -topk8 -inform PEM -outform DER -in "...\rsa\my_key" -out "...\rsa\key.der" -nocrypt
```

3. Output public key portion in DER format
```shell 
openssl rsa -in "...rsa\key.der" -inform DER -pubout -outform DER -out "...\rsa\key_pub.der"
```


See, for more information
https://codeartisan.blogspot.com/2009/05/public-key-cryptography-in-java.html


## Decryption (e.g. on backend side)

```java
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public static PrivateKey getPrivateKey(String filename)
            throws Exception {

        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int)f.length()];
        dis.readFully(keyBytes);
        dis.close();

        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
    
    
    public void decryptMessage(String decryptedMessage) throws NoSuchPaddingException, 
    NoSuchAlgorithmException, InvalidKeyException, 
    BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey); // the key loaded from the previous method
        String decryptedMessage = new String(cipher.doFinal(Base64.getDecoder().decode(decryptedMessage)));
    }
