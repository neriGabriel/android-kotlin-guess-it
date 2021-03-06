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

package com.example.android.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.ScoreFragmentBinding

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {

    /*
        CRIO DUAS VARIAVEL DE VIEWMODEL E VIEWMODELFACTORY COM INICIAZAÇÃO LENTA
        E DEPOIS ATRIBUO SEUS VALORES
    */
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.score_fragment,
                container,
                false
        )


        /*
            SEMPRE QUE FOI MEXER COM DATA BINDING
            LEMBRAR DE OLHAR A VIEWMODEL PRA NÃO DAR MERDA
        */
        // Get args using by navArgs property delegate
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()

        /*
            ATRIBUO OS VALORES DA FACTORY E PASSO OS PARAMETROS
        */
        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)

        /*
            ATRIBUO OS VALORES DA VIEWMODEL PORÉM COM A DIFERENÇA QUE NO MÉTODO
            .OF DEFINO COMO SEGUNDO PARAMETRO A MINHA FACTORY, SENDO ASSIM
            CRIARA COM PARAMETROS A MINHA VIEWMODEL SEM A NECESSIDADE DE SETTAR OS GETTERS E SETTERS
        */
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                                      .get(ScoreViewModel::class.java)

        binding.scoreViewModel = viewModel


        /*
            SETO COMO LIFECYCLE OWNER ESSE BINDING, ASSIM NA FRAGMENT TENHO ACESSO AS PROPRIEDADES
            E NÃO PRECISO FICAR CRIANDO UM MONTE DE LAMBDA OBSRVEBLE
        */
        binding.setLifecycleOwner(this)

        // Add observer for score
        /*viewModel.score.observe(this, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })*/

        // Navigates back to title when button is pressed
        viewModel.eventPlayAgain.observe(this, Observer { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        })

        return binding.root
    }

    private fun onPlayAgain() {
        findNavController().navigate(ScoreFragmentDirections.actionRestart())
    }
}
