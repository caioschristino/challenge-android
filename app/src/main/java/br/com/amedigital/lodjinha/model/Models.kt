package br.com.amedigital.lodjinha.model

import java.io.Serializable
import java.math.BigDecimal

data class Produto(
    val id: Long,
    val nome: String,
    val descricao: String,
    val categoria: Categoria,
    val urlImagem: String,
    val precoDe: Double,
    val precoPor: Double) : Serializable

data class Categoria(
    val id: Long,
    val descricao: String,
    val urlImagem: String) : Serializable

data class Banner(
    val id: Long,
    val linkUrl: String,
    val urlImagem: String) : Serializable