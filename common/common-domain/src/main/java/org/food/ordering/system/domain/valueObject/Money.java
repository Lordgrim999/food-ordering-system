package org.food.ordering.system.domain.valueObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    private final BigDecimal amount;
    public final static Money ZERO=new Money(BigDecimal.ZERO);
    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isMoneyGreaterThanZero()
    {
        return this.amount!=null && this.amount.compareTo(BigDecimal.ZERO)>0;
    }

    public boolean isMoneyGreaterThan(Money money)
    {
        return this.amount!=null && this.amount.compareTo(money.getAmount())>0;
    }

    public Money addMoney(Money money)
    {
        return new Money(setScale(this.amount.add(money.getAmount())));
    }

    public Money subtractMoney(Money money)
    {
        return new Money(setScale(this.amount.subtract(money.getAmount())));
    }
    public Money multiplyMoney(int value)
    {
        return new Money(setScale(this.amount.multiply(new BigDecimal(value))));
    }

    public BigDecimal setScale(BigDecimal value)
    {
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }


}
