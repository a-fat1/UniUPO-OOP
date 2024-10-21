package model.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArticleTest {
	@Test
	public void testValidArticleCreation() {
		Article article = new Article("Mela", 0.5, 2, "Frutta");
		assertEquals("Mela", article.getName());
		assertEquals(0.5, article.getCost(), 0.001);
		assertEquals(2, article.getQuantity());
		assertEquals("Frutta", article.getCategory());
	}

	@Test
	public void testArticleCreationWithNullName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new Article(null, 0.5, 2, "Frutta");
		});
		assertEquals("Nome dell'articolo non valido.", exception.getMessage());
	}

	@Test
	public void testArticleCreationWithEmptyName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new Article("", 0.5, 2, "Frutta");
		});
		assertEquals("Nome dell'articolo non valido.", exception.getMessage());
	}

	@Test
	public void testArticleCreationWithZeroQuantity() {
		Article article = new Article("Mela", 0.5, 0, "Frutta");
		assertEquals(1, article.getQuantity());
	}

	@Test
	public void testArticleCreationWithNegativeQuantity() {
		Article article = new Article("Mela", 0.5, -5, "Frutta");
		assertEquals(1, article.getQuantity());
	}

	@Test
	public void testArticleCreationWithNullCategory() {
		Article article = new Article("Mela", 0.5, 2, null);
		assertEquals("Non Categorizzati", article.getCategory());
	}

	@Test
	public void testArticleCreationWithEmptyCategory() {
		Article article = new Article("Mela", 0.5, 2, "");
		assertEquals("Non Categorizzati", article.getCategory());
	}

	@Test
	public void testSetCost() {
		Article article = new Article("Mela", 0.5, 2, "Frutta");
		article.setCost(1.0);
		assertEquals(1.0, article.getCost(), 0.001);
	}

	@Test
	public void testSetQuantity() {
		Article article = new Article("Mela", 0.5, 2, "Frutta");
		article.setQuantity(5);
		assertEquals(5, article.getQuantity());
	}

	@Test
	public void testSetCategory() {
		Article article = new Article("Mela", 0.5, 2, "Frutta");
		article.setCategory("Verdura");
		assertEquals("Verdura", article.getCategory());
	}
}
