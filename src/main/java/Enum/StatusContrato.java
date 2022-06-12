/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;

/**
 *
 * @author eduardo
 */
public enum StatusContrato {
    CANCELADO("Candelado"),
    FINALIZADO("Finalizado"),
    EXPIRADO("Expidado"),
    PAGO("Pago"),
    ESPERANDO_INICIO("Esperando inicio"),
    APROVADO("aprovado"),
    VIGENCIA("Vigencia"),
    PROXIMO_EXPIRAR("Proximo a expirar");

    String status;

    private StatusContrato(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
