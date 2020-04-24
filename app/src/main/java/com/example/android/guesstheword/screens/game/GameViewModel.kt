package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    /*
    * MOVI TODAS AS COISAS NÃO RELATIVAS A UPDATE DE UI PARA O VIEWMODEL
    * (A VIEWMODEL NÃO PODE CONTER VARIAVEIS/MÉTODOS QUE REFERENCIEM A VIEW)
    * TUDO QUE FOR 'HOLDING DE DATA'
    * OU SEJA
    * NOSSAS VARIAVEIS DE CONTROLE, WORD E SCORE VEM PARA O VIEWMODEL
    * NOSSA VARIAVEL DE LISTA DE WORD VEM PARA O VIEW MODEL
    * NO MÉTODO ON INIT EU INICIALIZA OS MÉTODOS RESETLIST E NEXTWORD PARA FICAREM VISIVEIS PARA O GAME
    * FRAGMENT E JÁ INICIALIZAREM AUTOMATICAMENTE A PARTIR DA INICIALIZAÇÃO DO VIEW MODEL NO FRAGMENT
    * NOSSO METODO DE RESET LIST TAMBÉM VEM PRA VIEWMODEL
    * */
    // The current word
    //CRIO UM ATRIBUTO INTERNO PARA A VIEW NÃO TER ACESSO DIRETAMENTE A ELE
    //E DEPOIS UM ATRIBUTO PUBLICO PARA LIBERAR ESTE ACESSO
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
            get() = _word

    // The current score
    //CRIO UM ATRIBUTO INTERNO PARA A VIEW NÃO TER ACESSO DIRETAMENTE A ELE
    //E DEPOIS UM ATRIBUTO PUBLICO PARA LIBERAR ESTE ACESSO
    private val _score = MutableLiveData<Int>();
    val score : LiveData<Int>
            get() = _score

    //CRIO UM ATRIBUTO INTERNO PARA A VIEW NÃO TER ACESSO DIRETAMENTE A ELE
    //E DEPOIS UM ATRIBUTO PUBLICO PARA LIBERAR ESTE ACESSO
    //SEMPRE QUE FOR ACESSAR UM VALOR DO MUTABLELIVEDATA É NECESSÁRIO SETAR O .VALUE
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
            get() = _eventGameFinish

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        _score.value = 0;
        _eventGameFinish.value = false;

        resetList()
        nextWord()
    }
    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            _eventGameFinish.value = true
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        //COMO VIROU UM MUTABLE VALUE EU NÃO CONSIGO REFERENCIA O VALOR DIRETAMENTE
        //AGORA TENHO QUE USAR O ATRIBUTO VALUE DA CLASSE MUTABLE
        //E ESTE É NULLAVEL OU SEJA, PODE ACEITAR VALORES NULLOS
        //ENTÃO PARA REALIZAR UMA OPERAÇÃO DEVO EXECUTAR OS MÉTODOS
        //IGUAL NO EXEMPLO ABAIXO
        _score.value = (score.value)?.minus(1);
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1);
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

}

