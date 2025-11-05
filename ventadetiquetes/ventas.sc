#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Estructura del pasajero (nodo)
typedef struct Nodo {
    char apellido[30];
    char genero[20];
    int abordado;
    struct Nodo *next;
} Nodo;

// Punteros principales de la lista
Nodo *inicio = NULL;  // primer nodo
Nodo *fin = NULL;     // último nodo

// Variables globales
int capacidad = 0;
int limite_sobreventa = 0;
int vendidos = 0;
int abordaje_iniciado = 0;

// Función para crear un nuevo nodo
Nodo* crearPasajero(char apellido[], char genero[]) {
    Nodo *nuevo = (Nodo*)malloc(sizeof(Nodo)); // reserva memoria
    strcpy(nuevo->apellido, apellido);
    strcpy(nuevo->genero, genero);
    nuevo->abordado = 0;
    nuevo->next = NULL;
    return nuevo;
}

// Agregar pasajero al final (venta)
void agregarPasajero(char apellido[], char genero[]) {
    Nodo *nuevo = crearPasajero(apellido, genero);
    if (inicio == NULL) {
        inicio = nuevo;
        fin = nuevo;
    } else {
        fin->next = nuevo; // el último apunta al nuevo
        fin = nuevo;       // el nuevo pasa a ser el último
    }
    vendidos++;
}

// Mostrar lista
void mostrarLista(int abordados) {
    Nodo *actual = inicio;
    int count = 0;
    while (actual != NULL) {
        if (abordados && actual->abordado == 1)
            printf("%d. %-15s | %-10s\n", ++count, actual->apellido, actual->genero);
        else if (!abordados && actual->abordado == 0)
            printf("%d. %-15s | %-10s\n", ++count, actual->apellido, actual->genero);
        actual = actual->next;
    }
    if (count == 0)
        printf("Ningún pasajero en esta lista.\n");
}

// Mostrar menú
void mostrar_menu() {
    printf("\n--- SISTEMA DE TIQUETES Y ABORDAJE ---\n");
    printf("1. Establecer capacidad del avión\n");
    printf("2. Vender tiquete\n");
    printf("3. Iniciar abordaje\n");
    printf("4. Ver pasajeros abordados\n");
    printf("5. Ver pasajeros no abordados\n");
    printf("6. Salir\n");
    printf("Seleccione una opción: ");
}

// Establecer capacidad
void establecer_capacidad() {
    if (capacidad > 0) {
        printf("⚠️ La capacidad ya fue establecida.\n");
        return;
    }
    printf("Ingrese la capacidad máxima del avión: ");
    scanf("%d", &capacidad);

    if (capacidad <= 0 || capacidad > 10) {
        printf("Capacidad inválida (1 a 10).\n");
        capacidad = 0;
        return;
    }

    limite_sobreventa = capacidad + (capacidad * 10 / 100);
    if (limite_sobreventa > 10)
        limite_sobreventa = 10;

    printf("✅ Capacidad establecida: %d pasajeros (sobreventa: %d boletos)\n",
           capacidad, limite_sobreventa);
}

// Vender tiquete
void vender_tiquete() {
    if (capacidad == 0) {
        printf("⚠️ Primero establezca la capacidad.\n");
        return;
    }
    if (abordaje_iniciado) {
        printf("⚠️ No se pueden vender tiquetes después del abordaje.\n");
        return;
    }
    if (vendidos >= limite_sobreventa) {
        printf("⚠️ Se alcanzó el límite de sobreventa (%d boletos).\n", limite_sobreventa);
        return;
    }

    getchar(); // limpiar buffer
    char apellido[30], genero[20];

    printf("Ingrese primer apellido: ");
    fgets(apellido, 30, stdin);
    apellido[strcspn(apellido, "\n")] = 0;

    printf("Ingrese género (Femenino/Masculino/No Binario): ");
    fgets(genero, 20, stdin);
    genero[strcspn(genero, "\n")] = 0;

    agregarPasajero(apellido, genero);
    printf("✅ Tiquete vendido a %s (%s)\n", apellido, genero);
}

// Iniciar abordaje
void iniciar_abordaje() {
    if (abordaje_iniciado) {
        printf("⚠️ El abordaje ya fue iniciado.\n");
        return;
    }
    if (capacidad == 0 || vendidos == 0) {
        printf("⚠️ No hay pasajeros o capacidad no definida.\n");
        return;
    }

    printf("\n✈️ Iniciando abordaje...\n");
    abordaje_iniciado = 1;

    Nodo *actual = inicio;
    int count = 0;
    while (actual != NULL) {
        if (count < capacidad)
            actual->abordado = 1;
        count++;
        actual = actual->next;
    }

    printf("✅ Abordaron %d pasajeros.\n", capacidad);
    printf("❌ %d no abordaron.\n", vendidos - capacidad);
}

// Liberar memoria
void liberar_lista() {
    Nodo *actual = inicio;
    while (actual != NULL) {
        Nodo *temp = actual;
        actual = actual->next;
        free(temp);
    }
}

int main() {
    int opcion;

    do {
        mostrar_menu();
        scanf("%d", &opcion);

        switch (opcion) {
            case 1: establecer_capacidad(); break;
            case 2: vender_tiquete(); break;
            case 3: iniciar_abordaje(); break;
            case 4: mostrarLista(1); break;
            case 5: mostrarLista(0); break;
            case 6: printf("👋 Saliendo del sistema...\n"); liberar_lista(); break;
            default: printf("Opción inválida.\n");
        }
    } while (opcion != 6);

    return 0;
}
