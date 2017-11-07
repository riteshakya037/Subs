package io.subs.domain.models;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;
import org.parceler.Parcel;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("WeakerAccess") @Parcel public class UserProfile {
    @SerializedName("fullName") protected String userFullName;
    @SerializedName("email") protected String userEmail;
    @SerializedName("subAvailable") protected int subAvailable = 5;

    public UserProfile(String userFullName, String userEmail) {
        this.userFullName = userFullName;
        this.userEmail = userEmail;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getSubAvailable() {
        return subAvailable;
    }

    @SuppressWarnings("StringBufferReplaceableByString") @Override public String toString() {
        final StringBuilder sb = new StringBuilder("UserProfile{");
        sb.append("userFullName='").append(userFullName).append('\'');
        sb.append(", userEmail='").append(userEmail).append('\'');
        sb.append(", subAvailable=").append(subAvailable);
        sb.append('}');
        return sb.toString();
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fullName", userFullName);
        result.put("email", userEmail);
        result.put("subAvailable", subAvailable);
        return result;
    }
}
