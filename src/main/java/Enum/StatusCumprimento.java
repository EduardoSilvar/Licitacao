/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enum;

/**
 *
 * @author eduardo
 */
public enum StatusCumprimento {
    SIM("Sim"),
    NAO("Não"),
    NAO_APLICA("Não se Aplica");

    String status;

    private StatusCumprimento(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
