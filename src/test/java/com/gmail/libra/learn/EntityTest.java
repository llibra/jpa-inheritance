package com.gmail.libra.learn;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.libra.learn.jpa_inheritance.LeafNode;
import com.gmail.libra.learn.jpa_inheritance.InnerNode;
import com.gmail.libra.learn.jpa_inheritance.Node;

public class EntityTest {
	private static Logger log = LoggerFactory.getLogger( EntityTest.class );

	private EntityManagerFactory emf;
	private EntityManager em;

	@Before
	public void setUp() {
		emf = Persistence.createEntityManagerFactory("pu");
		em = emf.createEntityManager();
	}

	@After
	public void tearDown() {
		em.close();
		emf.close();
	}

	@Test
	public void test() throws Exception {
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		InnerNode root = new InnerNode();
		root.setName("Root");
		em.persist(root);

		InnerNode inner = new InnerNode();
		inner.setName("Inner Node");
		inner.setParent(root);
		em.persist(inner);

		LeafNode x = new LeafNode();
		x.setName("X");
		x.setValue("100");
		x.setParent(inner);
		em.persist(x);

		LeafNode y = new LeafNode();
		y.setName("Y");
		y.setValue("200");
		y.setParent(inner);
		em.persist(y);

		tx.commit();

		em.refresh(root);
		em.refresh(inner);

    	log.info("=====");

		printNode(root, 0);

    	log.info("=====");
    	log.info("Done.");
	}

    private static void printNode(Node obj, int level) {
    	if (obj instanceof LeafNode)
    		printNode((LeafNode)obj, level);
    	else if (obj instanceof InnerNode)
    		printNode((InnerNode)obj, level);
    	else
    		throw new RuntimeException();
    }

    private static void printNode(LeafNode address, int level) {
		log.info("{}- {}: {}", getIndentSpace(level), address.getName(),
				address.getValue());
    }

    private static void printNode(InnerNode group, int level) {
    	if (group.getChildren() == null)
    		log.info("{}- {}/", getIndentSpace(level), group.getName());
    	else {
    		log.info("{}+ {}/", getIndentSpace(level), group.getName());

    		for (Node obj : group.getChildren())
    			printNode(obj, level + 1);
    	}
    }

    private static String getIndentSpace(int level) {
    	StringBuilder builder = new StringBuilder();

    	for (int i = 0; i < level; i++)
    		builder.append("  ");

    	return builder.toString();
    }
}
