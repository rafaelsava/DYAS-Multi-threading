package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class FileDownloadSimulator {
    public static void main(String[] args) {
        // Crea un pool de hilos con un tamaño fijo de 3 hilos
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // Lista de tareas simulando la descarga de archivos
        List<Callable<Integer>> downloads = Arrays.asList(
                () -> simulateDownload("file1.txt", 2, 100),
                () -> simulateDownload("file2.txt", 3, 200),
                () -> simulateDownload("file3.txt", 1, 150)
        );

        try {
            // Ejecuta todas las tareas y espera que todas finalicen

            List<Future<Integer>> results = executor.invokeAll(downloads);
            int totalBytes = 0;
            // Itera sobre los resultados y acumula el total de bytes descargados

            for (Future<Integer> result : results) {
                totalBytes += result.get();// Espera y obtiene el resultado de cada tarea
            }
            System.out.println("Total bytes downloaded: " + totalBytes);
        } catch (InterruptedException | ExecutionException e) {
            // Captura cualquier error que pueda ocurrir al ejecutar o obtener los resultados

            e.printStackTrace();
        } finally {
            // Apaga correctamente el pool de hilos

            executor.shutdown();
        }
    }

    private static Integer simulateDownload(String fileName, int seconds, int size) throws InterruptedException {
        // Método auxiliar que simula la descarga de un archivo
        System.out.println("Starting download: " + fileName);
        Thread.sleep(seconds * 1000);
        System.out.println("Completed download: " + fileName + " (" + size + " bytes)");
        return size;
    }
}
