package com.iago.Config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CamposInvalidos {
    private String campo;
    private String erro;
}
