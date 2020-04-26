/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    /*
        CRIAMOS DOIS ATRIBUTOS DO TIPO LATEINIT
        LATEINIT -> CONHECIDO COMO INICIALIZAÇÃO ATRASADA, A PARTIR DELA PODEMOS DECLARAR
        PROPERTIES SIMILARES A ATRIBUTOS POREM SEM INICIALIZAÇÃO
        LEMBRANDO QUE OS ATRIBUTOS DO TIPO LATEINIT PRECISAM SER INICIALIZADOS ANTES DO SEU USO
        SEÃO CRASHARAM A APLICAÇÃO.
    */
    /*
        VARIAVEL DE REFERENCIA AO VIEWMODEL DO TIPO GAMEVIEWMODEL
     */
    private lateinit var viewModel: GameViewModel

    /*
        VARIAVEL DE REFERENCIA AO BINDING DO FRAGMENT DO TIPO GAMEFRAGMENTBINDING
     */
    private lateinit var binding: GameFragmentBinding


    /*
        SOBREESCREVO A FUNÇÃO ONCREATEVIEW QUE SERÁ EXECUTADA NO LIFFECYCLE E RECEBERÁ VIA PARAMETRO
        UM LAYOUT PARA INFLAR DO TIPO LAYOUTINFLATER, UM CONTAINER DO TIPO VIEWGROUP E A INSTANCIA DO ESTADO
        DO TIPO BUNDLE
    */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        /*
            INFLO A VIEW E OBTENHO A INSTANCIA DA CLASSE DE BINDING
         */
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )
        /*
            REFERENCIO A VIEWMODEL ANTES DE SUA UTILIZAÇÃO COM UM VIEWMODELPROVIDER
            E REFERENCIO O ARQUIVO GAMEVIEWMODEL::CLASS.JAVA
        */
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        /*
            A VARIAVEL BIND CONTÉM TODOS OS ELEMENTOS DA VIEW/FRAGMENT (DATABINDING -> LIGAR DIRETAMENTE A VIEW AO VIEWMODEL)
            SENDO ASSIM CONSIGO ASSOCIAR UMA VARIAVEL CRIADA NA VIEW/FRAGMENT E UTILIZA-LA
            O QUE FAÇO AQUI É FAZER ESSA ASSOCIAÇÃO
        */
        binding.gameViewModel = viewModel

        /*
            SETO COMO LIFECYCLE OWNER ESSE BINDING, ASSIM NA FRAGMENT TENHO ACESSO AS PROPRIEDADES
            E NÃO PRECISO FICAR CRIANDO UM MONTE DE LAMBDA OBSRVEBLE
        */
        binding.setLifecycleOwner(this)

        /*
            SETO ATRAVÉS DO BINDING DA VIEW PARA O BOTÃO CORRECT O ONCLICKLISTENER
            CHAMANDO A FUNÇÃO ONCORRECT LOCALIZADA DENTRO DO VIEWMODEL E ATUALIZO A PALAVRA
        */

        /*
          NOS BOTÕES DA VIEW/FRAGMENT ASSOCIEI DIRETO A FUNÇÃO ENTÃO NÃO PRECISO FAZER ESSA ASSOCIAÇÃO AQUI
       */
        /*binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
            updateWordText()
        }*/


        /*
          NOS BOTÕES DA VIEW/FRAGMENT ASSOCIEI DIRETO A FUNÇÃO ENTÃO NÃO PRECISO FAZER ESSA ASSOCIAÇÃO AQUI
       */
        /*
           SETO ATRAVÉS DO BINDING DA VIEW PARA O BOTÃO SKIP O ONCLICKLISTENER
           CHAMANDO A FUNÇÃO ONSKIP LOCALIZADA DENTRO DO VIEWMODEL E ATUALIZO A PALAVRA
        */
        /*binding.skipButton.setOnClickListener {
            viewModel.onSkip()
            updateWordText()
        }*/

        /*
            SETANDO O OBSERVE
            ENTÃO NÃO PRECISAREI TER UM MÉTODO SEPARADO PARA ATUALIZAR O SCORE
            SEMPRE QUE ATUALIZAR O VALOR DO SCORE NA VIEWMODEL ESSE OBSERVE ATUALIZARA A NOSSA VIEW
            AUTOMATICAMENTE
            ISTO SÓ É POSSIVEL PORQUE NO ATRIBUTO DA VIEWMODEL EU SETO COMO DO TIPO LIVEDATA
        */
       /* viewModel.score.observe(this, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })*/

        /*viewModel.word.observe(this, Observer { newWord ->
            binding.wordText.text = newWord.toString()
        })*/

        /*
           SETANDO O OBSERVE
           ENTÃO NÃO PRECISAREI TER UM MÉTODO SEPARADO PARA ATUALIZAR O time
           ESTE OBSERVE FICARA DE 'OLHO' NAS ALTERAÇÕES DO ATRIBUTO TIME
           SEMPRE QUE FOR ALTERADO ALTERARA AQUI
           A FUNÇÃO DATEUTILS.FORMATELAPSEDTIME CONVERTE O TEMPO QUE VEM DESFORMATADO
       */
        /*viewModel.time.observe(this, Observer { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
        })*/

        /*
            SETANDO O OBSERVE
            PARA QUANDO O VALOR DO ATRIBUTO INTERNO EVENTGAMEFINISH FOR ALTERADO E SEU VALOR FOR TRUE
            PRINTAR UM TOASTER NA TELA, FINALIZAR O GAME
            E ALTERAR NOVAMENTE O ATRIBUTO DO VIEWMODEL
        */
        viewModel.eventGameFinish.observe(this, Observer { hasFinished ->
                if(hasFinished) {
                    gameFinished()
                    viewModel.onGameFinishComplete()
                }
        })

        updateWordText()

        /*
            RETORNO O BINDIG.ROOT
         */
        return binding.root

    }

    /*
        FUNÇÃO CHAMDA QUANDO O GAME É FINALIZADO
    */
    private fun gameFinished() {
        /*
            CRIO UM ALERTA LEVE QUANDO O GAME É FINALIZADO PASSANDO POR PARAMETRO A VIEW ONDE ESSE ALERTA SERÁ
            EXIBIDO, UMA MENSAGEM E O TEMPO EM QUE O ALERTA FICARÁ NA TELA E DOU UM .SHOW() PARA EXIBIR O ALERTA
        */
        Toast.makeText(this.activity, "Game has finished", Toast.LENGTH_SHORT).show()

        /*
           CRIO UMA VARIAVEL COM O VALOR DOS PONTOS E 'ENCAPSULO' PARA ENVIAR PARA O FRAGMENT ANTERIOR
        */
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)

        /*
            CHAMO O FRAGMENT ANTERIOR PASSANDO A VARIAVEL
        */
        findNavController(this).navigate(action)
    }


    /*
        FUNÇÃO PARA ATUALIZAR O TEXTO
    */
    fun updateWordText() {
        binding.wordText.text = viewModel.word.value

    }




}
