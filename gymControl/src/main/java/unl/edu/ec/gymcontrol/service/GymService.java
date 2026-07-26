package unl.edu.ec.gymcontrol.service;

import java.util.List;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.UserTransaction;

import unl.edu.ec.gymcontrol.domain.Cliente;
import unl.edu.ec.gymcontrol.domain.Instructor;
import unl.edu.ec.gymcontrol.domain.Pago;
import unl.edu.ec.gymcontrol.domain.Producto;

@ApplicationScoped
public class GymService {

    @PersistenceContext(unitName = "gymPU")
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    // ---------- helpers de transacción ----------
    private void tx(Runnable action) {
        try {
            utx.begin();
            em.joinTransaction();   // importante
            action.run();
            utx.commit();
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception ignored) {}
            Throwable root = e;
            while (root.getCause() != null) {
                root = root.getCause();
            }
            throw new RuntimeException(
                    root.getClass().getSimpleName() + ": " + root.getMessage(), e
            );
        }
    }

    // ---------- CLIENTES ----------
    public List<Cliente> listarClientes() {
        return em.createQuery("SELECT c FROM Cliente c ORDER BY c.id", Cliente.class)
                .getResultList();
    }

    public void guardarCliente(Cliente c) {
        tx(() -> em.persist(c));
    }

    public void eliminarCliente(Long id) {
        tx(() -> {
            Cliente c = em.find(Cliente.class, id);
            if (c != null) em.remove(c);
        });
    }

    // ---------- PRODUCTOS ----------
    public List<Producto> listarProductos() {
        return em.createQuery("SELECT p FROM Producto p ORDER BY p.id", Producto.class)
                .getResultList();
    }

    public void guardarProducto(Producto p) {
        tx(() -> em.persist(p));
    }

    public void actualizarProducto(Producto p) {
        tx(() -> em.merge(p));
    }

    public Producto buscarProducto(Long id) {
        return em.find(Producto.class, id);
    }

    public void eliminarProducto(Long id) {
        tx(() -> {
            Producto p = em.find(Producto.class, id);
            if (p != null) em.remove(p);
        });
    }

    // ---------- INSTRUCTORES ----------
    public List<Instructor> listarInstructores() {
        return em.createQuery("SELECT i FROM Instructor i ORDER BY i.id", Instructor.class)
                .getResultList();
    }

    public void guardarInstructor(Instructor i) {
        tx(() -> em.persist(i));
    }

    public void eliminarInstructor(Long id) {
        tx(() -> {
            Instructor i = em.find(Instructor.class, id);
            if (i != null) em.remove(i);
        });
    }

    // ---------- PAGOS ----------
    public List<Pago> listarPagos() {
        return em.createQuery("SELECT p FROM Pago p ORDER BY p.id DESC", Pago.class)
                .getResultList();
    }

    public void guardarPago(Pago p) {
        tx(() -> em.persist(p));
    }

    public void eliminarPago(Long id) {
        tx(() -> {
            Pago p = em.find(Pago.class, id);
            if (p != null) em.remove(p);
        });
    }
}