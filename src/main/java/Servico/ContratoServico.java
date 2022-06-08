/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Contratado;
import modelo.Contrato;

/**
 *
 * @author eduardo
 */
@Stateless
public class ContratoServico extends ServicoGenerico<Contrato> implements Serializable{
    
    public ContratoServico() {
        super(Contrato.class);
    }
    
    public List<Contratado> findAllContratados(){
        String sql = "select c from Contratado c";
        Query query = entityManager.createQuery(sql);
        return query.getResultList();
    }
}
