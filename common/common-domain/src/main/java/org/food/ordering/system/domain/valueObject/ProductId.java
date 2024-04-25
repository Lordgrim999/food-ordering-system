package org.food.ordering.system.domain.valueObject;

import java.util.UUID;

public class ProductId extends BaseId<UUID> {
   public ProductId(UUID value)
   {
       super(value);
   }
}
