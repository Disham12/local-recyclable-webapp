package com.recycleapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class RecyclableMaterial {
    private String materialName;
    private String category;
    private String tips;
    private double weight;
    private double pricePerKg;

    public RecyclableMaterial(String materialName, String category, String tips, double weight, double pricePerKg) {
        this.materialName = materialName;
        this.category = category;
        this.tips = tips;
        this.weight = weight;
        this.pricePerKg = pricePerKg;
    }

    public double calculatePrice() {
        return weight * pricePerKg;
    }

    public String toHTML() {
        return """
               <div class="material-card">
                   <h3>%s</h3>
                   <p><strong>Category:</strong> %s</p>
                   <p><strong>Tips:</strong> %s</p>
                   <p><strong>Weight:</strong> %.2f kg</p>
                   <p><strong>Price per kg:</strong> $%.2f</p>
                   <p><strong>Total Price:</strong> $%.2f</p>
               </div>
               """.formatted(materialName, category, tips, weight, pricePerKg, calculatePrice());
    }
}

class Customer {
    private String name;
    private String contact;

    public Customer(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String toHTML() {
        return """
               <div class="customer-info">
                   <h2>Customer Details</h2>
                   <p><strong>Name:</strong> %s</p>
                   <p><strong>Contact:</strong> %s</p>
               </div>
               """.formatted(name, contact);
    }
}

public class RecyclingGuideApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get customer details
        System.out.println("Enter customer name:");
        String customerName = scanner.nextLine();

        System.out.println("Enter customer contact:");
        String customerContact = scanner.nextLine();
        Customer customer = new Customer(customerName, customerContact);

        // Get recyclable materials
        List<RecyclableMaterial> materials = new ArrayList<>();
        System.out.println("Enter the number of recyclable materials:");
        int numberOfMaterials = Integer.parseInt(scanner.nextLine());

        for (int i = 1; i <= numberOfMaterials; i++) {
            System.out.println("Enter details for material " + i);

            System.out.print("Material name: ");
            String materialName = scanner.nextLine();

            System.out.print("Category (e.g., Metal, Paper, Plastic): ");
            String category = scanner.nextLine();

            System.out.print("Recycling tips: ");
            String tips = scanner.nextLine();

            System.out.print("Weight (kg): ");
            double weight = Double.parseDouble(scanner.nextLine());

            System.out.print("Price per kg: ");
            double pricePerKg = Double.parseDouble(scanner.nextLine());

            materials.add(new RecyclableMaterial(materialName, category, tips, weight, pricePerKg));
        }

        // Generate and write HTML
        String htmlContent = generateHTML(customer, materials);
        writeToFile("src/public/index.html", htmlContent);
        System.out.println("HTML file generated successfully! Check src/public/index.html");
    }

    public static String generateHTML(Customer customer, List<RecyclableMaterial> materials) {
        StringBuilder materialHTML = new StringBuilder();
        for (RecyclableMaterial material : materials) {
            materialHTML.append(material.toHTML());
        }

        return """
               <!DOCTYPE html>
               <html lang="en">
               <head>
                   <meta charset="UTF-8">
                   <meta name="viewport" content="width=device-width, initial-scale=1.0">
                   <title>Recycling Guide</title>
                   <style>
                       body {
                           font-family: Arial, sans-serif;
                           background-image: url('greenery.jpg');
                           background-size: cover;
                           color: white;
                           text-align: center;
                           padding: 20px;
                       }
                       .container {
                           background-color: rgba(0, 128, 0, 0.8);
                           border-radius: 10px;
                           padding: 20px;
                           display: inline-block;
                           text-align: left;
                           width: 80%;
                           margin: auto;
                       }
                       h1 {
                           color: #ffcc00;
                       }
                       .customer-info, .material-card {
                           background-color: rgba(255, 255, 255, 0.9);
                           color: black;
                           padding: 15px;
                           border-radius: 8px;
                           margin-bottom: 20px;
                       }
                       .material-card h3 {
                           color: #008000;
                       }
                   </style>
               </head>
               <body>
                   <h1>Welcome to the Recycling Guide</h1>
                   <div class="container">
                       %s
                       <h2>Recyclable Materials</h2>
                       %s
                       <hr>
                       <p>For more recycling tips, visit our <a href="recycling-tips.html">Recycling Tips Page</a>.</p>
                   </div>
               </body>
               </html>
               """.formatted(customer.toHTML(), materialHTML.toString());
    }

    public static void writeToFile(String filePath, String content) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
