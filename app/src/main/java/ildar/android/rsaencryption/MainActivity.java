package ildar.android.rsaencryption;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ildar.android.rsaencryption.util.TokenHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String messageToEncrypt = "We are anonymous";
        try {
            String encryptedMessage = TokenHandler.getEncryptedMessage(this, messageToEncrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
