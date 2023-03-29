package org.vitrine.object;

import lombok.Getter;
import org.apache.commons.codec.binary.Base32;
import org.vitrine.common.Utils;
import org.vitrine.core.Console;

import java.time.Instant;

public class Television {
    private String totp;
    private int sessionId = 0;
    private String sessionTotp;
    private long lastActivity;
    @Getter
    private boolean inUse = false;
    private final String secretKeyString = "vanilla";

    /**
     * Generate the active TOTP
     */
    public void generateTotp() {
        // Convert secretKey to Bytes
        Base32 base32 = new Base32();
        byte[] secretKeyBytes = base32.decode(secretKeyString);
        // Get the current time in TOTP time steps
        long timeSteps = Instant.now().getEpochSecond() / Utils.TOTP_TIME_STEP_SECONDS;

        totp = Utils.generateTotp(secretKeyBytes, timeSteps);
        Console.println("Code OTP : " + totp, Console.Color.WHITE);
    }

    /**
     * Periodic checks on tv session activity
     * Terminate the session after 30 seconds without feedback from client
     */
    public void checkInactivity() {
        if (inUse && System.currentTimeMillis() >= lastActivity + 30000) { // 30s
            inUse = false;
            sessionTotp = "";
        }
    }

    /**
     * Update TV session activity
     */
    public void updateActivity() {
        lastActivity = System.currentTimeMillis();
    }

    /**
     * Connection attempt to TV
     * @param code TOTP code from client
     * @return If possible to connect, return the new session ID. Otherwise return false (0)
     */
    public int connect(String code) {
        if (inUse) return 0;

        if (code.equals(totp)) {
            inUse = true;
            sessionTotp = code;
            updateActivity();

            return ++sessionId;
        }

        return 0;
    }

    /**
     * Validate if the provided session is the active TV session
     * @param sessionId Session id to validate
     * @param sessionTotp Session TOTP to validate
     * @return Session is valid or not
     */
    public boolean isTvSessionValid(String sessionId, String sessionTotp) {
        try {
            int id = Integer.parseInt(sessionId);
           if (this.sessionId == id && sessionTotp.equals(this.sessionTotp)) { // The request session is the active one
               updateActivity();
               return true;
           } else { // Old session or invalid one
               return false;
           }
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
