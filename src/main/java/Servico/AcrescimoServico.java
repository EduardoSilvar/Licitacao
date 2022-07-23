/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servico;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Acrescimo;
import util.Utils;

/**
 *
 * @author dylan
 */
@Stateless
public class AcrescimoServico extends ServicoGenerico<Acrescimo> implements Serializable{

    public AcrescimoServico() {
        super(Acrescimo.class);
    }
    
    public boolean existNumero(Long numero) {
        String sql = "select c from Acrescimo c where c.ativo = true ";
        if (Utils.isNotEmpty(numero)) {
            sql += "and c.numeroTermo = :numero ";
        }
        Query query = getEntityManager().createQuery(sql);
        if (Utils.isNotEmpty(numero)) {
            query.setParameter("numero", numero);
        }
        if (query.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
