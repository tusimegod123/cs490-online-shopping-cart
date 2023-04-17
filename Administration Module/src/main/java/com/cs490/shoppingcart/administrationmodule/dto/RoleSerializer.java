package com.cs490.shoppingcart.administrationmodule.dto;

import com.cs490.shoppingcart.administrationmodule.model.Role;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Set;

public class RoleSerializer {
        public void serialize(Set<Role> roles, JsonGenerator gen, SerializerProvider serializers) throws IOException, java.io.IOException {
            if (roles != null && !roles.isEmpty()) {
                gen.writeObject(roles.iterator().next());
            }
        }
}

