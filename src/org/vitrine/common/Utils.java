package org.vitrine.common;

import org.apache.commons.codec.binary.Base32;
import org.vitrine.core.Console;
import java.time.Instant;

//Corrections
import java.nio.ByteBuffer;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.vitrine.core.Sketch;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static final int TOTP_TIME_STEP_SECONDS = 30;
    private static final int TOTP_NUMBER_OF_DIGITS = 6;

    public static boolean validateTotp(String secretKey, String code) {
        // Convert secretKey to Bytes
        Base32 base32 = new Base32();
        byte[] secretKeyBytes = base32.decode(secretKey);

        // Get the current time in TOTP time steps
        long timeSteps = Instant.now().getEpochSecond() / TOTP_TIME_STEP_SECONDS;

        // Calculate the TOTP code for the current time step
        String totp = generateTotp(secretKeyBytes, timeSteps);

        // Check if the provided code matches the calculated TOTP code
        return totp.equals(code);
    }

    public static String generateTotp(byte[] secretKey, long timeSteps) {
        try {
            // Convert the time steps to a byte array
            byte[] timeBytes = ByteBuffer.allocate(8).putLong(timeSteps).array();

            // Calculate the HMAC-SHA1 hash of the secret key and the time bytes
            //byte[] hmacSha1 = DigestUtils.sha1Hmac(secretKey, timeBytes);//Deprecated.

            // Create a new Mac object with the "HmacSHA1" algorithm and the secret key
            SecretKeySpec keySpec = new SecretKeySpec(secretKey, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(keySpec);

            // Calculate the HMAC-SHA1 hash of the time bytes
            byte[] hmacSha1 = mac.doFinal(timeBytes);

            // Truncate the hash to the specified number of digits
            int offset = hmacSha1[hmacSha1.length - 1] & 0xf;
            int binary =
                    ((hmacSha1[offset] & 0x7f) << 24) |
                            ((hmacSha1[offset + 1] & 0xff) << 16) |
                            ((hmacSha1[offset + 2] & 0xff) << 8) |
                            (hmacSha1[offset + 3] & 0xff);
            int otp = binary % (int) Math.pow(10, TOTP_NUMBER_OF_DIGITS);

            // Convert the TOTP code to a string
            return String.format("%06d", otp);
        } catch (Exception e) {
            // Handle the exception
            e.printStackTrace();
            return "ERROR";
        }
    }


    public static void debugGenerateTotp() {
        //Set secretKey
        String secretKeyString = "vanilla";

        // Convert secretKey to Bytes
        Base32 base32 = new Base32();
        byte[] secretKeyBytes = base32.decode(secretKeyString);
        // Get the current time in TOTP time steps
        long timeSteps = Instant.now().getEpochSecond() / TOTP_TIME_STEP_SECONDS;

        // Print the TOTP code for the current time step
        Console.println("OTP :", Console.Color.WHITE);
        Console.println(generateTotp(secretKeyBytes, timeSteps), Console.Color.WHITE);
    }

    /**
     * Get fractal sketch list (include sub package in name, ex: examples.Kock.X)
     * To get only the name, use getFractalsList()
     * @return A list of string
     */
    public static List<String> getFractalsSketchList() {
        List<String> fractals = new ArrayList<String>();

        try (ScanResult scanResult = new ClassGraph().acceptPackages("org.vitrine")
                .enableClassInfo().scan()) {

            for (ClassInfo _class : scanResult.getAllClasses()) {
                if (_class.extendsSuperclass(Sketch.class)) {
                    fractals.add(_class.getName().replace("org.vitrine.sketchs.", ""));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

        return fractals;
    }

    /**
     * Get fractal list
     * @return Name of fractals
     */
    public static List<String> getFractalsList() {
        List<String> fractals = new ArrayList<String>();

        for (String sketch : getFractalsSketchList()) {
            String[] withoutDots = sketch.split("\\.");
            fractals.add(withoutDots[withoutDots.length-1]);
        }

        return fractals;
    }
}
