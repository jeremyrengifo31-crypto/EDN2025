#include <stdio.h>

#define N 5   // Número de estudiantes (puedes cambiarlo)

// Definición de estructura Estudiante
typedef struct {
    int codigo;
    float nota;
} Estudiante;

// Datos predefinidos
Estudiante estudiantes[N] = {
    {2024101, 4.5},
    {2024102, 3.2},
    {2024103, 2.8},
    {2024104, 5.0},
    {2024105, 3.7}
};

// -------------------- FUNCIONES RECURSIVAS -------------------- //

// Buscar nota máxima recursivamente
float notaMaxima(Estudiante arr[], int n, int i, float max) {
    if (i == n) return max;
    if (arr[i].nota > max) max = arr[i].nota;
    return notaMaxima(arr, n, i + 1, max);
}

// Calcular promedio recursivo
float sumaNotas(Estudiante arr[], int n, int i) {
    if (i == n) return 0;
    return arr[i].nota + sumaNotas(arr, n, i + 1);
}
float promedioCurso(Estudiante arr[], int n) {
    return sumaNotas(arr, n, 0) / n;
}

// Selection Sort recursivo (ordenar por código)
int indiceMinimo(Estudiante arr[], int inicio, int n, int minIndex) {
    if (inicio == n) return minIndex;
    if (arr[inicio].codigo < arr[minIndex].codigo) minIndex = inicio;
    return indiceMinimo(arr, inicio + 1, n, minIndex);
}
void selectionSortRec(Estudiante arr[], int inicio, int n) {
    if (inicio >= n - 1) return;
    int minIndex = indiceMinimo(arr, inicio, n, inicio);

    // Intercambio
    Estudiante temp = arr[inicio];
    arr[inicio] = arr[minIndex];
    arr[minIndex] = temp;

    selectionSortRec(arr, inicio + 1, n);
}

// Mostrar estudiantes
void mostrarEstudiantes(Estudiante arr[], int n) {
    for (int i = 0; i < n; i++) {
        printf("Codigo: %d - Nota: %.2f\n", arr[i].codigo, arr[i].nota);
    }
}

// -------------------- PROGRAMA PRINCIPAL -------------------- //
int main() {
    int opcion;

    do {
        printf("\n=== MENU ===\n");
        printf("1. Buscar Nota Maxima\n");
        printf("2. Calcular Promedio del Curso\n");
        printf("3. Ordenar Codigos (Selection Sort Recursivo)\n");
        printf("4. Salir\n");
        printf("Seleccione una opcion: ");
        scanf("%d", &opcion);

        switch (opcion) {
            case 1: {
                float max = notaMaxima(estudiantes, N, 0, estudiantes[0].nota);
                printf("La nota maxima del curso es: %.2f\n", max);
                break;
            }
            case 2: {
                float promedio = promedioCurso(estudiantes, N);
                printf("El promedio del curso es: %.2f\n", promedio);
                break;
            }
            case 3: {
                selectionSortRec(estudiantes, 0, N);
                printf("Lista de estudiantes ordenada:\n");
                mostrarEstudiantes(estudiantes, N);
                break;
            }
            case 4:
                printf("Saliendo del programa...\n");
                break;
            default:
                printf("Opcion invalida. Intente de nuevo.\n");
        }
    } while (opcion != 4);

    return 0;
}
