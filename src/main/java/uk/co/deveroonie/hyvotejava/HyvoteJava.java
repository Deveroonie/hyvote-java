package uk.co.deveroonie.hyvotejava;

import tools.jackson.databind.ObjectMapper;
import uk.co.deveroonie.hyvotejava.models.Vote;
import uk.co.deveroonie.hyvotejava.util.BuildMessage;
import uk.co.deveroonie.hyvotejava.util.Encryption;
import uk.co.deveroonie.hyvotejava.util.Keys;

import javax.crypto.SecretKey;
import java.net.Socket;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

public class HyvoteJava {
    private String siteName;
    public HyvoteJava(String sitename) {
        this.siteName = sitename;
    }
    void processVote(String host, int port, String publicKey, String username) {
        ObjectMapper mapper = new ObjectMapper();
        Vote vote = new Vote();
        vote.playerName = username;
        vote.voteSite = this.siteName;
        vote.timestamp = currentTimeMillis();
        vote.nonce = String.valueOf(UUID.randomUUID());

        try {
            String voteJSON = mapper.writeValueAsString(vote);
            SecretKey aesKey = Keys.generateKey();
            byte[] encryptedKey = Encryption.encryptAESKey(
                    aesKey.getEncoded(),
                    Keys.pemToKey(Keys.stripPublicKeyHeaders(publicKey))
            );
            byte[] encryptedPayload = Encryption.encryptPayload(voteJSON, aesKey);

            byte[] message = BuildMessage.build(encryptedKey, encryptedPayload);

            try (Socket socket = new Socket(host, port)) {
                socket.getOutputStream().write(message);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
