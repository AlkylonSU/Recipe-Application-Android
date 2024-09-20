package com.example.recipeapp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class RecipeRepo {

    public void getAllRecipes(ExecutorService srv, Handler uiHandler){

        srv.submit(()->{
            try{
                List<Recipes> data = new ArrayList<>();
                URL url = new URL("http://159.20.91.236:8080/recipe/all");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                JSONArray arr =new JSONArray(buffer.toString());

                for (int i = 0; i < arr.length(); i++){
                    JSONObject current = arr.getJSONObject(i);

                    Recipes recipe = new Recipes(current.getString("id"),
                            current.getString("title"),
                            current.getString("category"),
                            current.getInt("preparingTimeMins"),
                            current.getInt("cookingTimeMins"),
                            current.getString("ingredients"),
                            current.getString("procedure"));
                    data.add(recipe);
                }
                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

                for (Recipes recipe : data) {
                    Log.d("RecipeRepo", "Retrieved recipe: " + recipe.getTitle());
                }

            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
            }

        });
    }
    public void getDataById(ExecutorService srv, Handler uiHandler,String id){
        srv.execute(()->{
            try {
                URL url = new URL("http://159.20.91.236:8080/recipe/searchid/" + String.valueOf(id));
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }
                JSONObject current = new JSONObject(buffer.toString());
                Recipes recipe = new Recipes(current.getString("id"),
                        current.getString("title"),
                        current.getString("category"),
                        current.getInt("preparingTimeMins"),
                        current.getInt("cookingTimeMins"),
                        current.getString("ingredients"),
                        current.getString("procedure"));


                Message msg = new Message();
                msg.obj = recipe;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }

    public void searchRecipe(ExecutorService srv, Handler uiHandler, String title) {
        srv.execute(() -> {
            try {
                URL url = new URL("http://159.20.91.236:8080/recipe/searchlike");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Create the JSON object
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", title);

                // Send the JSON object as the request body
                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                JSONArray jsonArray = new JSONArray(buffer.toString());
                List<Recipes> recipes = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject current = jsonArray.getJSONObject(i);
                    Recipes recipe = new Recipes(
                            current.getString("id"),
                            current.getString("title"),
                            current.getString("category"),
                            current.getInt("preparingTimeMins"),
                            current.getInt("cookingTimeMins"),
                            current.getString("ingredients"),
                            current.getString("procedure")
                    );
                    recipes.add(recipe);
                }

                Message msg = new Message();
                msg.obj = recipes;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }



    public void deleteRecipe(ExecutorService srv ,String id) {
        srv.execute(() -> {
            try {
                String deleteUrl = "http://159.20.91.236:8080/recipe/delete/" + id;
                URL url = new URL(deleteUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Recipe deleted successfully
                    // Update Listener or handlers, it is for you to complete!
                } else {
                    // Handle error response
                    // Update Listener or handlers, it is for you to complete!
                }

                conn.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void saveRecipe(ExecutorService srv, String title, String category, int preparingTimeMins,
                           int cookingTimeMins, String ingredients, String procedure ){
        srv.execute(()->{

            try {
                URL url = new URL("http://159.20.91.236:8080/recipe/save");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");


                JSONObject outputData  = new JSONObject();

                outputData.put("title",title);
                outputData.put("category",category);
                outputData.put("preparingTimeMins",preparingTimeMins);
                outputData.put("cookingTimeMins",cookingTimeMins);
                outputData.put("ingredients",ingredients);
                outputData.put("procedure",procedure);

                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());


                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line ="";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject retVal = new JSONObject(buffer.toString());

                conn.disconnect();


                String retValStr = retVal.getString("result");



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }

    public void UpdateRecipe(ExecutorService srv, String id, String title, String category, int preparingTimeMins,
                           int cookingTimeMins, String ingredients, String procedure ){
        srv.execute(()->{

            try {
                URL url = new URL("http://159.20.91.236:8080/recipe/save");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");


                JSONObject outputData  = new JSONObject();

                outputData.put("id",id);
                outputData.put("title",title);
                outputData.put("category",category);
                outputData.put("preparingTimeMins",preparingTimeMins);
                outputData.put("cookingTimeMins",cookingTimeMins);
                outputData.put("ingredients",ingredients);
                outputData.put("procedure",procedure);

                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());


                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line ="";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject retVal = new JSONObject(buffer.toString());

                conn.disconnect();


                String retValStr = retVal.getString("result");

                //Update Listener or handlers, it is for you to complete!


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }

    public interface DataListener{
        public void dataReceived(List<Recipes> data);
    }
}
