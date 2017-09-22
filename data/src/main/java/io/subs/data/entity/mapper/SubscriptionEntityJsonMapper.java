package io.subs.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import io.subs.data.entity.SubscriptionEntity;
import java.lang.reflect.Type;
import java.util.List;
import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class SubscriptionEntityJsonMapper {

    private final Gson gson;

    @Inject public SubscriptionEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link SubscriptionEntity}.
     *
     * @param userJsonResponse A json representing a user profile.
     * @return {@link SubscriptionEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json
     * structure.
     */
    public SubscriptionEntity transformSubscriptionEntity(String userJsonResponse)
            throws JsonSyntaxException {
        final Type userEntityType = new TypeToken<SubscriptionEntity>() {
        }.getType();
        return this.gson.fromJson(userJsonResponse, userEntityType);
    }

    /**
     * Transform from valid json string to List of {@link SubscriptionEntity}.
     *
     * @param userListJsonResponse A json representing a collection of users.
     * @return List of {@link SubscriptionEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json
     * structure.
     */
    public List<SubscriptionEntity> transformSubscriptionEntityCollection(
            String userListJsonResponse) throws JsonSyntaxException {
        final Type listOfSubscriptionEntityType = new TypeToken<List<SubscriptionEntity>>() {
        }.getType();
        return this.gson.fromJson(userListJsonResponse, listOfSubscriptionEntityType);
    }
}
