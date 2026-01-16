package uk.co.deveroonie.hyvotejava.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;

public class BuildMessage {
    public static byte[] build(byte[] aes, byte[] payload) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.write("HV01".getBytes(StandardCharsets.US_ASCII)); // use Proto V1 (the only one available)

        dos.writeInt(aes.length);
        dos.write(aes);

        dos.writeInt(payload.length);
        dos.write(payload);

        dos.flush();
        return baos.toByteArray();
    }
}
