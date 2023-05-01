package com.growdev.GrowdevPeople.util;

import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class RemoveAccentuateCharacters {
    public static String removeAccentuate(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
