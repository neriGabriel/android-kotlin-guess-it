package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.ViewModel

/*
    CRIO UMA VIEWMODEL NORMALMENTE POREM ESTÁ RECEBE VIA MÉTODO CONSTRUTOR PARAMETROS
    NESSE CASO SOMENTE 1
*/
class ScoreViewModel(finalScore: Int) : ViewModel() {
    init {
        Log.i("ScoreViewModel", "Final Score ${finalScore}");
    }
}