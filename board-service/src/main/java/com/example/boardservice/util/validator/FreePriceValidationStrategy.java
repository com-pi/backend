package com.example.boardservice.util.validator;

import com.example.boardservice.adapter.in.web.command.PostBuyAndSellCommand;
import com.example.boardservice.adapter.in.web.command.UpdateBuyAndSellCommand;

public class FreePriceValidationStrategy implements ValidationStrategy {

    @Override
    public boolean validate(Object obj) {
        if (obj instanceof PostBuyAndSellCommand command) {
            return validateFields(command.getIsFree(), command.getPrice());
        }
        if (obj instanceof UpdateBuyAndSellCommand command) {
            return validateFields(command.getIsFree(), command.getPrice());
        }
        return false;
    }

    private boolean validateFields(Boolean isFree, Integer price) {
        if (isFree) {
            return price == 0;
        }
        return true;
    }
}
