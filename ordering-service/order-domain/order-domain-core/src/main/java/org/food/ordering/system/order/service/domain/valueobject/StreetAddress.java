package org.food.ordering.system.order.service.domain.valueobject;
import java.util.Objects;
import java.util.UUID;
public class StreetAddress {
    private final UUID id;
    private final String postalCode;
    private final String street;
    private final String city;

    public StreetAddress(UUID id, String postalCode, String street, String city) {
        this.id = id;
        this.postalCode = postalCode;
        this.street = street;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetAddress that = (StreetAddress) o;
        return Objects.equals(postalCode, that.postalCode) && Objects.equals(street, that.street) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postalCode, street, city);
    }
}
