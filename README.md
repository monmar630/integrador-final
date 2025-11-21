# Sistema de Gestión de Pedidos y Envíos
Trabajo Práctico Integrador (TPI) – Programación II – UTN

---

## Descripción del Proyecto

Este proyecto implementa un sistema de gestión de **Pedidos** y **Envíos** desarrollado en Java con JDBC y MySQL.  
Permite registrar, consultar, actualizar y eliminar pedidos y envíos, manteniendo una relación **1 a 1**, donde cada pedido posee un envío asociado.

El objetivo principal es integrar conocimientos de Programación II, aplicando:

- Orientación a objetos  
- Persistencia con JDBC  
- Arquitectura en capas  
- Manejo de excepciones  
- Transacciones  
- Diseño de base de datos  

---

## Funcionalidad Principal

### Envíos
- Crear envío  
- Listar envíos  
- Buscar por tracking  
- Actualizar estado  
- Eliminación lógica  

### Pedidos
- Crear pedido + envío (transacción)  
- Listar pedidos  
- Buscar por ID  
- Actualizar estado  
- Eliminación lógica  

### Transacciones
La opción **“Crear Pedido + Envío”** realiza ambas inserciones dentro de una transacción:

- Si todo es correcto → **commit**  
- Si algo falla → **rollback**  

---

## Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas, dividiendo responsabilidades:

```
src/
│
├── config/
│   └── DatabaseConnection.java
│
├── entities/
│   ├── Envio.java
│   └── Pedido.java
│
├── dao/
│   ├── EnvioDao.java
│   └── PedidoDao.java
│
├── service/
│   ├── EnvioService.java
│   └── PedidoService.java
│
└── main/
    ├── AppMenu.java
    └── Main.java
```
