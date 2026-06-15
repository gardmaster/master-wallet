package com.gard.investmentmanager.shared.infrastructure.i18n;

import jakarta.enterprise.context.ApplicationScoped;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@ApplicationScoped
public class MessageResolver {

    public String get(String key, Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        String pattern = bundle.getString(key);
        return MessageFormat.format(pattern, args);
    }
}