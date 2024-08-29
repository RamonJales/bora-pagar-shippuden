package com.borathings.borapagar.core;

import java.lang.reflect.Field;
import org.springframework.stereotype.Component;

@Component
/**
 * Classe para fazer update parcial de qualquer objeto em relação ao DTO. Os campos não presentes no
 * DTO não serão alterados. Por enquanto esta classe limita o DTO a seguir os mesmos nomes dos
 * atributos da entidade.
 *
 * @param <Entity> Entidade que será atualizada
 * @param <DTO> DTO que contém os dados para atualização
 */
public class Patcher<Entity, DTO> {
    /**
     * Atualiza a entidade com os dados do DTO. Os campos não presentes no DTO não serão alterados.
     * Este método modifica a entidade passada como parâmetro.
     *
     * @param entity - Entidade a ser atualizada
     * @param data - DTO com os dados para atualização
     * @throws IllegalAccessException - Caso não consiga acessar os campos da entidade ou do DTO
     */
    public void patch(Entity entity, DTO data) throws IllegalAccessException {
        Field[] dataFields = data.getClass().getDeclaredFields();
        for (Field field : dataFields) {
            field.setAccessible(true); // Para acessar mesmo que seja private
            Object value = field.get(data);
            if (value != null) {
                field.set(entity, value);
            }
            field.setAccessible(false);
        }
    }
}
