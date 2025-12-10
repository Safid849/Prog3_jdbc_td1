package com.jdbc.td1;

import com.jdbc.td1.dao.DataRetriever;
import com.jdbc.td1.model.Category;
import com.jdbc.td1.model.Product;

import java.time.Instant;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        DataRetriever dr = new DataRetriever();

        System.out.println("====================================================");
        System.out.println("TEST 1 : List<Category> getAllCategories()");
        System.out.println("→ Retourne toutes les catégories");
        System.out.println("Résultats attendus :");
        System.out.println("[Category{id=1, name='Tech', productId=1}, ...]");
        System.out.println("----------------------------------------------------");

        List<Category> categories = dr.getAllCategories();
        categories.forEach(System.out::println);

        System.out.println("\n====================================================");
        System.out.println("TEST 2 : List<Product> getProductList(page=1, size=3)");
        System.out.println("→ Doit retourner les 3 premiers produits");
        System.out.println("Résultat attendu :");
        System.out.println("[Product{id=1,...}, Product{id=2,...}, Product{id=3,...}]");
        System.out.println("----------------------------------------------------");

        List<Product> page1 = dr.getProductList(1, 3);
        page1.forEach(System.out::println);


        System.out.println("\nTEST 2b : getProductList(page=2, size=3)");
        System.out.println("→ Doit retourner les produits 4,5,6 (selon les données)");
        System.out.println("Résultat attendu :");
        System.out.println("[Product{id=4,...}, Product{id=5,...}, Product{id=6,...}]");
        System.out.println("----------------------------------------------------");

        List<Product> page2 = dr.getProductList(2, 3);
        page2.forEach(System.out::println);



        // -------------------------------------------------------------------
        //  TEST 3 : getProductsByCriteria (sans pagination)
        // -------------------------------------------------------------------
        System.out.println("\n====================================================");
        System.out.println("TEST 3 : getProductsByCriteria(productName=\"a\", categoryName=\"e\")");
        System.out.println("→ Filtrer :");
        System.out.println("  - produits contenant « a »");
        System.out.println("  - catégories contenant « e »");
        System.out.println("Résultat attendu :");
        System.out.println("[Product{id=..., name='Camera', category='Electronics'}, ...]");
        System.out.println("----------------------------------------------------");

        List<Product> filtered1 = dr.getProductsByCriteria(
                "a",
                "e",
                null,
                null
        );
        filtered1.forEach(System.out::println);



        System.out.println("\nTEST 3b : getProductsByCriteria(creationMin=2024-01-01)");
        System.out.println("→ Filtrer produits créés après 2024-01-01");
        System.out.println("Résultat attendu :");
        System.out.println("[Product{id=..., creationDateTime=2024-05-03}, ...]");
        System.out.println("----------------------------------------------------");

        Instant minDate = Instant.parse("2024-01-01T00:00:00Z");

        List<Product> filtered2 = dr.getProductsByCriteria(
                null,
                null,
                minDate,
                null
        );
        filtered2.forEach(System.out::println);

        System.out.println("\n====================================================");
        System.out.println("TEST 4 : getProductsByCriteria(name=\"a\", page=1, size=2)");
        System.out.println("→ Filtrer d’abord puis paginer (obligatoire)");
        System.out.println("Résultat attendu :");
        System.out.println("[2 premiers produits contenant 'a']");
        System.out.println("----------------------------------------------------");

        List<Product> filteredPaged = dr.getProductsByCriteria(
                "a",
                null,
                null,
                null,
                1,
                2
        );
        filteredPaged.forEach(System.out::println);


        System.out.println("\nFIN DES TESTS");
        System.out.println("====================================================");
    }
}
