package elaboration;

import java.io.*;
import java.util.*;

public class ShoppingList implements Iterable<Article> {
	private String name;
	private Map<String, Article> articles;

	public ShoppingList(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Nome della lista non valido.");
		}
		this.name = name;
		articles = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public Collection<Article> getArticles() {
		return articles.values();
	}

	public void addArticle(Article article) {
		if (article == null) {
			throw new IllegalArgumentException("Articolo non valido.");
		}
		articles.put(article.getName(), article);
	}

	public void removeArticle(String articleName) {
		if (articles.remove(articleName) == null) {
			throw new IllegalArgumentException("Articolo non trovato.");
		}
	}

	public List<Article> findArticlesByName(String prefix) {
		List<Article> result = new ArrayList<>();
		for (Article article : articles.values()) {
			if (article.getName().startsWith(prefix)) {
				result.add(article);
			}
		}
		return result;
	}

	public List<Article> findArticlesByCategory(String category) {
		List<Article> result = new ArrayList<>();
		for (Article article : articles.values()) {
			if (article.getCategory().equals(category)) {
				result.add(article);
			}
		}
		return result;
	}

	public int getTotalQuantity() {
		int totalQuantity = 0;
		for (Article article : articles.values()) {
			totalQuantity += article.getQuantity();
		}
		return totalQuantity;
	}

	public double getTotalCost() {
		double total = 0;
		for (Article article : articles.values()) {
			total += article.getCost() * article.getQuantity();
		}
		return total;
	}

	public void updateCategory(String oldCategory, String newCategory) {
		for (Article article : articles.values()) {
			if (article.getCategory().equals(oldCategory)) {
				article.setCategory(newCategory);
			}
		}
	}

	public void saveToFile(String filename) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			for (Article article : articles.values()) {
				writer.write(article.getName() + "," + article.getCost() + "," + article.getQuantity() + "," + article.getCategory());
				writer.newLine();
			}
		}
	}

	public void loadFromFile(String filename) throws IOException {
		articles.clear();
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					String[] parts = line.split(",");
					String name = parts[0];
					double cost = Double.parseDouble(parts[1]);
					int quantity = Integer.parseInt(parts[2]);
					String category = parts[3];
					addArticle(new Article(name, cost, quantity, category));
				}
			}
		}
	}

	@Override
	public Iterator<Article> iterator() {
		return articles.values().iterator();
	}
}
