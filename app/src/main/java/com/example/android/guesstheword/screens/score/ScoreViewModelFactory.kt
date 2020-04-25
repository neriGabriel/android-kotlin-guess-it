package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
    CRIO UMA FACTORY DE VIEW MODEL
    ETÁ TERÁ COMO OBJETIVO CRIAR A VIEW MODEL PRA GENTE

    PORÉM A CRIAÇÃO PADRÃO DE UMA VIEW MODEL NÃO PERMITE PASSAGEM DE PARAMETROS
    SENDO ASSIM NECESSITAMOS ADICIONAR OS GETTERS E SETTERS NO MÉTODO ON CREATE
    O QUE PODE SER UM PROBLEMA AS VEZES, ENTÃO QUANDO É NECESSÁRIO CRIAR UMA VIEWMODEL
    COM PARAMETRO UTILIZAMOS O PATTERN FACTORY

    ESTE HERDARA DO VIEWMODELPROVIDER.FACTORY QUE TERÁ COMO OBRIGAÇÃO IMPLEMENTAR O MÉTODO
    CREATE QUE NOS RETORN A VIEWMODEL CRIADA PODENDO PASSAR PARAMETROS
*/
class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}