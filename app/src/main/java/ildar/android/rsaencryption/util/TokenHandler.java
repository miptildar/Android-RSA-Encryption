package ildar.android.rsaencryption.util;

import android.content.Context;
import android.util.Base64;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import ildar.android.rsaencryption.R;

public class TokenHandler {

    public static String getEncryptedMessage(Context context, String messageToEncrypt) throws Exception {

        /**
         * You can't use Cipher.getInstance("RSA") in Android
         * See https://stackoverflow.com/a/31401015/3405101
         */
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        PublicKey publicKey = loadPublicKey(context);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] bytes = cipher.doFinal(messageToEncrypt.getBytes());
        byte[] androidEncode = Base64.encode(bytes, Base64.NO_WRAP);
        return new String(androidEncode, StandardCharsets.UTF_8);
    }



    private static PublicKey loadPublicKey(Context context) throws Exception {
        // Loading Public key from resources Raw folder
        InputStream resourceAsStream = context.getResources().openRawResource(R.raw.key2_pub);
        byte[] keyBytes = FileUtil.toByteArray(resourceAsStream);

        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

}
