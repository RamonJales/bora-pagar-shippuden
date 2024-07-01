package com.borathings.borapagar.course.enumTypes;

/**
 * Enum representando os níveis dos cursos da UFRN
 */
public enum CourseLevel {
    GRADUATION("GRADUATION"),
    TECHNICAL("TECHNICAL");

    private final String text;

    /**
     * @param text - Valor do enum
     */
    CourseLevel(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
