package com.example.recipeapp;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecipeApplication extends Application {

    ExecutorService srv  = Executors.newCachedThreadPool();

}
