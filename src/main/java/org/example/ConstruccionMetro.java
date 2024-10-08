package org.example;

import java.io.*;
import java.util.*;

class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int u) {
        if (parent[u] != u) {
            parent[u] = find(parent[u]);
        }
        return parent[u];
    }

    public boolean union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rootU != rootV) {
            if (rank[rootU] > rank[rootV]) {
                parent[rootV] = rootU;
            } else if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }
            return true;
        }
        return false;
    }
}

public class ConstruccionMetro {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("metro.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());  // Cantidad de islas
        int k = Integer.parseInt(st.nextToken());  // Cantidad de tuneles
        int m = Integer.parseInt(st.nextToken());  // Cantidad de puentes

        List<int[]> tuneles = new ArrayList<>();
        List<int[]> puentes = new ArrayList<>();

        // Lee las conexiones entre los tuneles y los agrega a la lista correspondiente
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            tuneles.add(new int[]{u, v});
        }

        // Lee las conexiones entre los puentes y los agrega a la lista correspondiente
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            puentes.add(new int[]{u, v});
        }

        br.close();

        int cantPuentesAConstruir = construirMetro(n, tuneles, puentes);

        PrintWriter pw = new PrintWriter(new FileWriter("metro.out"));
        pw.println(cantPuentesAConstruir);
        pw.close();
    }

    public static int construirMetro(int n, List<int[]> tuneles, List<int[]> puentes) {
        UnionFind uf = new UnionFind(n);
        int totalPuentes = 0;

        // Primero añadimos los tuneles (de peso 0)
        for (int[] tunel : tuneles) {
            uf.union(tunel[0], tunel[1]);
        }

        // Ahora vamos viendo de añadir los puentes segun corresponda (de peso 1)
        for (int[] puente : puentes) {
            if (uf.union(puente[0], puente[1])) {
                totalPuentes++;
            }
        }

        return totalPuentes;
    }
}
