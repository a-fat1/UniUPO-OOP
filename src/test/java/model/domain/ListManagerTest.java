package model.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ListManagerTest {
	@Test
	public void testListManagerInitialization() {
		ListManager listManager = new ListManager();
		assertNotNull(listManager.getShoppingLists());
		assertNotNull(listManager.getCategories());
		assertTrue(listManager.getCategories().contains("Non Categorizzati"));
	}

	@Test
	public void testAddValidCategory() {
		ListManager listManager = new ListManager();
		listManager.addCategory("Frutta");
		assertTrue(listManager.getCategories().contains("Frutta"));
	}

	@Test
	public void testAddNullCategory() {
		ListManager listManager = new ListManager();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			listManager.addCategory(null);
		});
		assertEquals("Nome della categoria non valido.", exception.getMessage());
	}

	@Test
	public void testAddEmptyCategory() {
		ListManager listManager = new ListManager();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			listManager.addCategory("");
		});
		assertEquals("Nome della categoria non valido.", exception.getMessage());
	}

	@Test
	public void testRemoveExistingCategory() {
		ListManager listManager = new ListManager();
		listManager.addCategory("Frutta");
		listManager.removeCategory("Frutta");
		assertFalse(listManager.getCategories().contains("Frutta"));
	}

	@Test
	public void testRemoveNonExistingCategory() {
		ListManager listManager = new ListManager();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			listManager.removeCategory("Verdura");
		});
		assertEquals("Categoria non esistente.", exception.getMessage());
	}

	@Test
	public void testAddValidShoppingList() {
		ListManager listManager = new ListManager();
		listManager.addShoppingList("Lista Spesa");
		assertNotNull(listManager.getShoppingList("Lista Spesa"));
	}

	@Test
	public void testAddNullShoppingList() {
		ListManager listManager = new ListManager();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			listManager.addShoppingList(null);
		});
		assertEquals("Nome della lista non valido.", exception.getMessage());
	}

	@Test
	public void testAddEmptyShoppingList() {
		ListManager listManager = new ListManager();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			listManager.addShoppingList("");
		});
		assertEquals("Nome della lista non valido.", exception.getMessage());
	}

	@Test
	public void testAddDuplicateShoppingList() {
		ListManager listManager = new ListManager();
		listManager.addShoppingList("Lista Spesa");
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			listManager.addShoppingList("Lista Spesa");
		});
		assertEquals("Lista già esistente.", exception.getMessage());
	}

	@Test
	public void testRemoveExistingShoppingList() {
		ListManager listManager = new ListManager();
		listManager.addShoppingList("Lista Spesa");
		listManager.removeShoppingList("Lista Spesa");
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			listManager.getShoppingList("Lista Spesa");
		});
		assertEquals("Lista non trovata.", exception.getMessage());
	}

	@Test
	public void testRemoveNonExistingShoppingList() {
		ListManager listManager = new ListManager();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			listManager.removeShoppingList("Lista Inesistente");
		});
		assertEquals("Lista non trovata.", exception.getMessage());
	}

	@Test
	public void testGetNonExistingShoppingList() {
		ListManager listManager = new ListManager();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			listManager.getShoppingList("Lista Inesistente");
		});
		assertEquals("Lista non trovata.", exception.getMessage());
	}
}
