package com.example.epicgames.configurations.security;

import org.hibernate.tool.schema.spi.SchemaFilter;
import org.hibernate.tool.schema.spi.SchemaFilterProvider;

public class CustomSchemaFilterProvider implements SchemaFilterProvider {

    @Override
    public SchemaFilter getCreateFilter() {
        return CustomSchemaFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getDropFilter() {
        return CustomSchemaFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getMigrateFilter() {
        return CustomSchemaFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getValidateFilter() {
        return CustomSchemaFilter.INSTANCE;
    }
}
