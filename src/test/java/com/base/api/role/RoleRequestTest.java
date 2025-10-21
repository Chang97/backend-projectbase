package com.base.api.role;

import static org.assertj.core.api.Assertions.assertThat;

import com.base.api.role.dto.RoleRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoleRequestTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerSubtypes(RoleRequest.class);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    void deserializePlainStructure() throws JsonProcessingException {
        String json = "{\"roleName\":\"ADMIN\",\"useYn\":true}";

        RoleRequest request = objectMapper.readValue(json, RoleRequest.class);

        assertThat(request.roleName()).isEqualTo("ADMIN");
        assertThat(request.useYn()).isTrue();
    }

    @Test
    void deserializeNestedDataStructureBoolean() throws JsonProcessingException {
        String json = "{\"data\":{\"roleName\":\"MANAGER\",\"useYn\":true}}";

        RoleRequest request = objectMapper.readValue(json, RoleRequest.class);

        assertThat(request.roleName()).isEqualTo("MANAGER");
        assertThat(request.useYn()).isTrue();
    }

    @Test
    void deserializeNestedDataStructureString() throws JsonProcessingException {
        String json = "{\"data\":{\"roleName\":\"USER\",\"useYn\":\"Y\"}}";

        RoleRequest request = objectMapper.readValue(json, RoleRequest.class);

        assertThat(request.roleName()).isEqualTo("USER");
        assertThat(request.useYn()).isTrue();
    }
}

