/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servico;

import br.com.caelum.stella.validation.CPFValidator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author eduardo
 * @param <T>
 */
public class ServicoGenerico<T> {

    @PersistenceContext
    EntityManager entityManager;
    private final Class<T> entity;

    public ServicoGenerico(Class<T> entity) {
        this.entity = entity;
    }

    public void Save(T entity) {
        entityManager.persist(entity);

    }

    public void Update(T entity) {
        entityManager.merge(entity);
        entityManager.flush();

    }

    public T find(Long id) {
        T NovoObjeto = entityManager.find(entity, id);
        entityManager.refresh(NovoObjeto);
        return NovoObjeto;
    }

    public List<T> FindAll() {
        return entityManager
                .createQuery("select e from " + entity.getSimpleName() + " e where e.ativo = true")
                .getResultList();

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public static boolean valida(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
