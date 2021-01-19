package com.mycompany.gatosapp;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import java.awt.Image;
import java.io.IOException;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class GatoService {

    public static void verGatos() {
        
        //Traer los datos de la Api
        
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search")
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String elJson = response.body().string();
            
            
        //Cortar las [] del Json recibido
        
        elJson = elJson.substring(1, elJson.length());
        elJson = elJson.substring(0, elJson.length()-1);
        
        //Crear objeto de la clase Gson
        Gson gson = new Gson();
        Gatos gatos = gson.fromJson(elJson, Gatos.class);
        
        //Redimensionar Imagen
            Image image = null;
            try {
                URL url = new URL(gatos.getUrl());
                image = ImageIO.read(url);
                ImageIcon fondoGato = new ImageIcon(image);
                if(fondoGato.getIconWidth() > 800){
                    //Redimensiona la imagen
                    Image fondo = fondoGato.getImage();
                    Image modificada = fondo.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                    fondoGato = new ImageIcon(modificada);
                }
                
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
            
        
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
