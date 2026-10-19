# CityLink - Proyecto Integrador Programación I 2026

Sistema de consola en Java para una empresa de reparto de paquetes: permite registrar clientes, repartidores y pedidos, asignar pedidos y calcular costos y tiempos de entrega 

## Integrantes
- Ignacio Panelo
- Gabriela Rubertone

## Estructura del proyecto
src/
├── modelo/ Clases del negocio, interfaces y enum
├── excepciones/ Excepciones propias del sistema
└── app/ Gestor (CityLink) y Main con el menú

## Como compilar y ejecutar
Desde la raiz del repositorio:

```bash
javac -d bin src/modelo/*.java src/excepciones/*.java src/app/*.java
java -cp bin app.Main
```

Requiere JDK

## Diagrama de clases
en construccion...

## Valores de los vehiculos
Vehículo	Capacidad máxima	Velocidad	Costo por km	Tiempo adicional
Bicicleta	10 kg ||	15 km/h ||	$50	|| 0 min
Moto	30 kg ||	40 km/h ||	$120 ||	5 min
Camioneta	500 kg ||	30 km/h || $300 ||	10 min

## Desiciones de diseño
- Los setters de `Cliente` validan con `IllegalArgumentException`, porque
  ninguna de las excepciones propias aplica a datos inválidos de un cliente.
- Se decidió mantener los valores originales de capacidad de carga, velocidad y costo por kilómetro establecidos en el PDF de la consigna para cada tipo de vehículo, sin realizar modificaciones.

## Uso de IA
**Claude**
- Para que: Explicaciones de conceptos, revision del codigo.
- Como se verifico: Probamos la clase con un main de prueba.

**ChatGPT**
- Para qué: Asistencia para comprender el código, documentar clases y métodos mediante Javadoc y revisar la documentación del proyecto.
- Cómo se verificó: Se revisaron las sugerencias, se adaptaron al código del proyecto y se verificó la compilación y el funcionamiento mediante pruebas.
