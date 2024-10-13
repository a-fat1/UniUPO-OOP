import elaboration.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.io.File;
import java.io.IOException;

public class ShoppingListTest {
	@Test
	public void testShoppingListInitialization() {
		ShoppingList list = new ShoppingList("Lista Test");
		assertEquals("Lista Test", list.getName());
		assertTrue(list.getArticles().isEmpty());
	}

	@Test
	public void testShoppingListInitializationWithNullName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new ShoppingList(null);
		});
		assertEquals("Nome della lista non valido.", exception.getMessage());
	}

	@Test
	public void testShoppingListInitializationWithEmptyName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new ShoppingList("");
		});
		assertEquals("Nome della lista non valido.", exception.getMessage());
	}

	@Test
	public void testAddValidArticle() {
		ShoppingList list = new ShoppingList("Lista Test");
		Article article = new Article("Mela", 0.5, 2, "Frutta");
		list.addArticle(article);
		assertEquals(1, list.getArticles().size());
		assertTrue(list.getArticles().contains(article));
	}

	@Test
	public void testAddNullArticle() {
		ShoppingList list = new ShoppingList("Lista Test");
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			list.addArticle(null);
		});
		assertEquals("Articolo non valido.", exception.getMessage());
	}

	@Test
	public void testRemoveExistingArticle() {
		ShoppingList list = new ShoppingList("Lista Test");
		Article article = new Article("Mela", 0.5, 2, "Frutta");
		list.addArticle(article);
		list.removeArticle("Mela");
		assertTrue(list.getArticles().isEmpty());
	}

	@Test
	public void testRemoveNonExistingArticle() {
		ShoppingList list = new ShoppingList("Lista Test");
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			list.removeArticle("NonEsistente");
		});
		assertEquals("Articolo non trovato.", exception.getMessage());
	}

	@Test
	public void testFindArticlesByPrefix() {
		ShoppingList list = new ShoppingList("Lista Test");
		list.addArticle(new Article("Mela", 0.5, 2, "Frutta"));
		list.addArticle(new Article("Melone", 1.0, 1, "Frutta"));
		list.addArticle(new Article("Banana", 0.3, 3, "Frutta"));
		List<Article> result = list.findArticlesByPrefix("Mel");
		assertEquals(2, result.size());
	}

	@Test
	public void testGetTotalQuantity() {
		ShoppingList list = new ShoppingList("Lista Test");
		list.addArticle(new Article("Mela", 0.5, 2, "Frutta"));
		list.addArticle(new Article("Banana", 0.3, 3, "Frutta"));
		assertEquals(5, list.getTotalQuantity());
	}

	@Test
	public void testGetTotalCost() {
		ShoppingList list = new ShoppingList("Lista Test");
		list.addArticle(new Article("Mela", 0.5, 2, "Frutta"));
		list.addArticle(new Article("Banana", 0.3, 3, "Frutta"));
		assertEquals(1.9, list.getTotalCost(), 0.001);
	}

	@Test
	public void testUpdateCategory() {
		ShoppingList list = new ShoppingList("Lista Test");
		list.addArticle(new Article("Mela", 0.5, 2, "Frutta"));
		list.updateCategory("Frutta", "Non Categorizzati");
		List<Article> articles = new ArrayList<>(list.getArticles());
		assertEquals("Non Categorizzati", articles.get(0).getCategory());
	}

	@Test
	public void testSaveToFile() throws IOException {
		ShoppingList list = new ShoppingList("Lista Test");
		list.addArticle(new Article("Mela", 0.5, 2, "Frutta"));
		list.saveToFile("testfile.txt");
		assertTrue(new java.io.File("testfile.txt").exists());
		new File("testfile.txt").delete();
	}

	@Test
	public void testLoadFromFile() throws IOException {
		ShoppingList list = new ShoppingList("Lista Test");
		list.addArticle(new Article("Mela", 0.5, 2, "Frutta"));
		list.saveToFile("testfile.txt");
		ShoppingList newList = new ShoppingList("Nuova Lista");
		newList.loadFromFile("testfile.txt");
		assertEquals(1, newList.getArticles().size());
		new File("testfile.txt").delete();
	}
}
