package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    /*
        CRIO UM COMPANION OBJECT PARA O TIMER COM
        O TEMPO DE DONE
        ONE_SECOND
        COUNTDOWN_TIME
    */
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }

    /*
        CRIO UM ATRIBUTO DO TIPO COUNTDOWNTIMER QUE RECEBERÁ MEU TIMER POSTERIORMENTE
    */
    private val timer: CountDownTimer

    /*
        CRIO UM ATRIBUTO INTERNO PARA A VIEW NÃO TER ACESSO DIRETAMENTE A ELE
        E DEPOIS UM ATRIBUTO PUBLICO PARA LIBERAR ESTE ACESSO
        REFERENCIA AO GET E SET

        ESTE ATRIBUTO TEM COMO VALOR UM MUTABLELIVEDATA DO TIPO LONG
    */
    private val _time =  MutableLiveData<Long>()
    val time: LiveData<Long>
        get() = _time


    /*
        MOVI TODAS AS COISAS NÃO RELATIVAS A UPDATE DE UI PARA O VIEWMODEL
        (A VIEWMODEL NÃO PODE CONTER VARIAVEIS/MÉTODOS QUE REFERENCIEM A VIEW)
        TUDO QUE FOR 'HOLDING DE DATA'
        OU SEJA
        NOSSAS VARIAVEIS DE CONTROLE, WORD E SCORE VEM PARA O VIEWMODEL
        NOSSA VARIAVEL DE LISTA DE WORD VEM PARA O VIEW MODEL
        NO MÉTODO ON INIT EU INICIALIZA OS MÉTODOS RESETLIST E NEXTWORD PARA FICAREM VISIVEIS PARA O GAME
        FRAGMENT E JÁ INICIALIZAREM AUTOMATICAMENTE A PARTIR DA INICIALIZAÇÃO DO VIEW MODEL NO FRAGMENT
        NOSSO METODO DE RESET LIST TAMBÉM VEM PRA VIEWMODEL
     */


    /*
        CRIO UM ATRIBUTO INTERNO PARA A VIEW NÃO TER ACESSO DIRETAMENTE A ELE
        E DEPOIS UM ATRIBUTO PUBLICO PARA LIBERAR ESTE ACESSO
        REFERENCIA AO GET E SET

        ESTE ATRIBUTO TEM COMO VALOR UM MUTABLELIVEDATA DO TIPO STRING
    */
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
            get() = _word

    /*
        The current score
        CRIO UM ATRIBUTO INTERNO PARA A VIEW NÃO TER ACESSO DIRETAMENTE A ELE
        E DEPOIS UM ATRIBUTO PUBLICO PARA LIBERAR ESTE ACESSO
        REFERENCIA AO GET E SET

        ESTE ATRIBUTO TEM COMO VALOR UM MUTABLELIVEDATA DO TIPO INT
    */
    private val _score = MutableLiveData<Int>();
    val score : LiveData<Int>
            get() = _score


    /*
        CRIO UM ATRIBUTO INTERNO PARA A VIEW NÃO TER ACESSO DIRETAMENTE A ELE
        E DEPOIS UM ATRIBUTO PUBLICO PARA LIBERAR ESTE ACESSO
        SEMPRE QUE FOR ACESSAR UM VALOR DO MUTABLELIVEDATA É NECESSÁRIO SETAR O .VALUE

        REFERENCIA AO GET E SET

        ESTE ATRIBUTO TEM COMO VALOR UM MUTABLELIVEDATA DO TIPO BOOLEAN
    */
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
            get() = _eventGameFinish

    /*
        CRIO UMA VARIAVEL DO TIPO LATE INT MUTABLELIST DE STRING E INICIALIZO ELA POSTERIORMENTE
    */
    private lateinit var wordList: MutableList<String>

    /*
        MÉTODO INIT SERÁ EXECUTADO QUANDO UM OBJETO DA CLASSE VIEW MODEL FOR INSTANCIADO
        SENDO ASSIM _TODO E QUALQUER BLOCO DE CÓDIGO DENTRO SERÁ EXECUTADO
    */
    init {
        _score.value = 0;
        _eventGameFinish.value = false;

        /*
            CONFIGURO MEU TIMER
            E SOBREESCREVO A FUNÇÃO ONTICK QUE SERÁ EXECUTADA A CADA TICKADA
            SETANDO UM VALOR DE TEMPO

            E SOBREESCREVO A FUNÇÃO ONFINISH QUE SERÁ EXECUTADA QUANDO FINALIZAR
            O TIMER RESETANDO O VALOR DO TIMER E FINALIZANDO O GAME
        */
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _time.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _time.value = DONE
                _eventGameFinish.value = true
            }
        }
        /*
            STARTO O TIMER
        */
        timer.start()


        resetList()
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    /**
     * Resets the list of words and randomizes the order
     */

    /*
        RESETO A LISTA DE PLAVARAS E A BAGUNÇO NOVAMENTE
    */
    private fun resetList() {
        /*
            ATRIBUO A VARIAVEL WORDLIST CRIADA ANTERIORMENTE SEUS ALORES
        */
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

        /*
            BAGUNÇO A LISTA
        */
        wordList.shuffle()
    }

    /*
        PROCURO A PROXIMA PALAVARA VERIFICANDO SE A LISTA DE PALAVRAS ESTÁ VAZIA
        SE REINICIO A LISTA
        SENÃO SETO A PROXIMA PALAVRA COMO O PRIMEIRO INDICE DO WORDLIST
        REMOVENDO A PALAVRA DO WORDLIST
    */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    /*
        COMO VIROU UM MUTABLE VALUE EU NÃO CONSIGO REFERENCIAR O VALOR DIRETAMENTE
        AGORA TENHO QUE USAR O ATRIBUTO VALUE DA CLASSE MUTABLE
        E ESTE É NULLAVEL OU SEJA, PODE ACEITAR VALORES NULLOS
        ENTÃO PARA REALIZAR UMA OPERAÇÃO DEVO EXECUTAR OS MÉTODOS
        IGUAL NO EXEMPLO ABAIXO
    */

    fun onSkip() {
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

