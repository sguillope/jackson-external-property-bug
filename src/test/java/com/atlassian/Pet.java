package com.atlassian;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

public class Pet {
    String type;

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type"
    )
    @JsonTypeIdResolver(AnimalTypeIdResolver.class)
    Animal animal;

    @JsonCreator
    public Pet(@JsonProperty("type") String type, @JsonProperty("animal") Animal animal) {
        this.type = type;
        this.animal = animal;
    }

    private static class AnimalTypeIdResolver extends TypeIdResolverBase {
        @Override
        public String idFromValue(Object value) {
            return idFromValueAndType(value, value.getClass());
        }

        @Override
        public String idFromValueAndType(Object value, Class<?> suggestedType) {
            if (suggestedType.isAssignableFrom(Cat.class)) {
                return "cat";
            } else if (suggestedType.isAssignableFrom(Dog.class)) {
                return "dog";
            }
            return null;
        }

        @Override
        public JavaType typeFromId(DatabindContext context, String id) {
            if ("cat".equals(id)) {
                return context.constructType(Cat.class);
            } else if ("dog".equals(id)) {
                return context.constructType(Dog.class);
            }
            return null;
        }

        @Override
        public Id getMechanism() {
            return Id.NAME;
        }
    }

    static interface Animal {
    }

    public static class Cat implements Animal {
    }

    public static class Dog implements Animal {
    }
}
