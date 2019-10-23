package dao.base;

import model.Categoria;
import model.Despesa;
import model.DespesaItem;
import model.Doacao;
import model.DoacaoItem;
import model.Endereco;
import model.Item;
import model.ParceiroDeNegocio;
import model.Projeto;
import model.ProjetoCategoria;
import model.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(Endereco.class);
            cfg.addAnnotatedClass(Usuario.class);
            cfg.addAnnotatedClass(Categoria.class);
            cfg.addAnnotatedClass(Item.class);
            cfg.addAnnotatedClass(ParceiroDeNegocio.class);
            cfg.addAnnotatedClass(Projeto.class);
            cfg.addAnnotatedClass(ProjetoCategoria.class);
            cfg.addAnnotatedClass(Despesa.class);
            cfg.addAnnotatedClass(DespesaItem.class);
            cfg.addAnnotatedClass(Doacao.class);
            cfg.addAnnotatedClass(DoacaoItem.class);
            
            cfg.configure("/dao/base/hibernate.cfg.xml");
            StandardServiceRegistryBuilder build
                    = new StandardServiceRegistryBuilder().
                            applySettings(cfg.getProperties());

            sessionFactory = cfg.buildSessionFactory(build.build());
        } catch (Throwable ex) {
            System.err.println("Erro ao criar fábrica de conexão." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session abrirSessao() {
        return sessionFactory.openSession();
    }
}
