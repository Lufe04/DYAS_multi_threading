package com.multi_threading;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileDownloadExample {
    public static void main(String[] args) {
        // Lista de URLs de archivos (simulados aquí con nombres)
        List<String> urls = Arrays.asList("file1.txt", "file2.txt", "file3.txt");

        // Creamos un ExecutorService con un grupo de hilos de tamaño 3
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            // Creamos una tarea para cada archivo a descargar
            List<Callable<String>> tasks = urls.stream()
                .map(url -> (Callable<String>) () -> downloadFile(url))
                .toList();

            // Ejecutamos todas las tareas en paralelo
            List<Future<String>> results = executor.invokeAll(tasks);

            // Procesamos los resultados
            for (Future<String> result : results) {
                System.out.println(result.get()); // Imprimimos el resultado de cada descarga
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // Cerramos el ExecutorService
        }
    }

    // Método que simula la descarga de un archivo
    public static String downloadFile(String fileName) throws IOException {
        // Aquí simulamos un retardo para representar el tiempo de descarga
        try {
            Thread.sleep(1000); // Simula el tiempo de descarga de 1 segundo
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Guardamos el contenido simulado en un archivo
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Contenido de " + fileName);
        }
        return "Descarga completada para: " + fileName;
    }
}

