package com.example.aplicacion_ejemplo

/**
 * Project: Aplicacion_ejemplo
 * From: com.example.aplicacion_ejemplo
 * Created by: cordo
 * On: 16/06/2026
 * All rights reserved: 2026
 */

val categorias = arrayOf("Leyenda","Experto","Competidor","Novato")
val puntos = listOf("Leyenda","Experto","Competidor","Novato")

val participantes = mutableListOf<String>()
val puntosParticipantes = mutableListOf<MutableList<Double>>()

fun mostrarMenu(){
    println("\n===== SISTEMA  DE TORNEO DE VIDEOJUEGOS =====")
    println("1 Registrar Participante")
    println("2 Registrar Puntos")
    println("3 Consultar Participante")
    println("4 Mostrar estadísticas generales")
    println("5 Salir")
    println("Seleccione una opción")
}

fun existeParticipante(nombre: String): Boolean {
    return participantes.contains(nombre)
}

fun calculaPromedio(total: Double, cantidad: Int): Double {
    return if (cantidad == 0) 0.0 else total / cantidad
}

fun mostrarCategoria(total: Double){
    val idx = when {
        total >= 1000 -> 0
        total >= 500 -> 1
        total >= 300 -> 2
        else -> 3
    }

    println("Categoria ${categorias[idx]}")

    when (categorias[idx]){
        "Leyenda" -> println(puntos[0])
        "Experto" -> println(puntos[1])
        "Competidor" -> println(puntos[2])
        "Novato" -> println(puntos[3])
    }

}

fun main(){
    while (true){
        mostrarMenu()
        val opcion = readlnOrNull() ?: ""
        when (opcion){
            "1" -> registrarParticipante()
            "2" -> registrarPuntos()
            "3" -> consultarParticipante()
            "4" -> mostrarEstadistica()
            "5" -> {
                print("¿Esta seguro de salir (S/N):")
                val seguro = readlnOrNull()?.uppercase() ?: "N"
                if (seguro == "S") return
            }
            else -> println("Opcion inválida. Intente de nuevo")
        }
    }
}

fun registrarParticipante() {
    print("Nombre del Participante:")
    val nombre = readlnOrNull()?.trim() ?: ""

    if (nombre.isEmpty()){
        println("No se permiten nombres vacíos.")
        return
    }
    if(existeParticipante(nombre)){
        println("El Participante ya está registrado")
    }else{
        participantes.add(nombre)
        puntosParticipantes.add(mutableListOf())
        println("Participante registrado con éxito ")
    }
}

fun registrarPuntos(){
    if (participantes.isEmpty()){
        println("No hay participantes registrados")
        return
    }

    println("Participantes registrados: $participantes")
    print("Nombre del Participante:")
    val nombre = readlnOrNull()?.trim() ?: ""

    if (!existeParticipante(nombre)){
        println("Puntos no validos. El Participante no existe")
        return
    }
     val idx = participantes.indexOf(nombre)

    while (true){
        print("Registro de Puntos:")
        val puntoStr = readlnOrNull() ?: ""
        val Puntos = puntoStr.toDoubleOrNull()

        if (Puntos == null || Puntos <= 0){
            println("Puntos inválidos Ingrese numeros enteros validos.")
            continue
        }

        puntosParticipantes[idx].add(Puntos)
        println("Puntos registrados con éxito.")
        break
     }
}

fun consultarParticipante(){
    print("Nombre del Participante a consultar:")
    val nombre = readlnOrNull()?.trim() ?: ""

    if(!existeParticipante(nombre)){
        println("El Participante no existe")
        return
    }

    val idx = participantes.indexOf(nombre)
    val listasPuntos = puntosParticipantes[idx]

    val cantPuntos = listasPuntos.size
    val totalPuntos = listasPuntos.sum()
    val promedio = calculaPromedio(totalPuntos, cantPuntos)

    println("\nNombre: $nombre")
    println("Total de puntos: $totalPuntos")
    println("Promedio por puntos: $promedio")
    mostrarCategoria(totalPuntos)

}

fun mostrarEstadistica() {
    if (participantes.isEmpty()) {
        println("No hay datos suficientes para generar estadísticas")
        return
    }

    var totalpuntosGlobal = 0
    var montoTotalGlobal = 0.0

    var catLeyenda = 0
    var catExperto = 0
    var catCompetidor = 0
    var catNovato = 0

    val totalesPorParticipantes = puntosParticipantes.map { it.sum() }
    val maxIdx = totalesPorParticipantes.indices.maxByOrNull { totalesPorParticipantes[it] } ?: 0
    val minIdx = totalesPorParticipantes.indices.minByOrNull { totalesPorParticipantes[it] } ?: 0

    for (lista in puntosParticipantes) {
        totalpuntosGlobal += lista.size
        val totalparticipante = lista.sum()
        montoTotalGlobal += totalparticipante


        when {
            totalparticipante>= 1000 -> catLeyenda++
            totalparticipante >= 500 -> catExperto++
            totalparticipante <= 300 -> catCompetidor++
            else -> catNovato++
        }
    }

    val promedioGeneral = calculaPromedio(montoTotalGlobal, totalpuntosGlobal)

    println("\n===== REPORTE GENERAL DE Participantes del Torneo de Videojuegos=====")
    println("Total de participantes: $participantes.size}")
    println("Total puntos registrados: $totalpuntosGlobal")
    println("Total de puntos: $$montoTotalGlobal")
    println("Promedio general de puntos: $$promedioGeneral")

    println("\nParticipantes con mayores puntos")
    println("Nombre: ${participantes[maxIdx]}")
    println("Total de Puntos: $${totalesPorParticipantes[maxIdx]}")

    println("\nParticipantes con menores puntos")
    println("Nombre: ${participantes[minIdx]}")
    println("Total de Puntos: $${totalesPorParticipantes[minIdx]}")

    println("\nConteo por categorías")
    println("Leyenda: $catLeyenda")
    println("Experto: $catExperto")
    println("Competidor: $catCompetidor")
    println("Novato: $catNovato")
}




