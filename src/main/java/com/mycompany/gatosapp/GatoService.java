package com.mycompany.gatosapp;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
            elJson = elJson.substring(0, elJson.length() - 1);

            //Crear objeto de la clase Gson
            Gson gson = new Gson();
            Gatos gatos = gson.fromJson(elJson, Gatos.class);

            //Redimensionar Imagen
            Image image = null;
            try {
                URL url = new URL(gatos.getUrl());
                HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                httpcon.addRequestProperty("User-Agent", "");
                BufferedImage bufferedImage = ImageIO.read(httpcon.getInputStream());
                ImageIcon fondoGato = new ImageIcon(bufferedImage);
                if (fondoGato.getIconWidth() > 800) {
                    //Redimensiona la imagen
                    Image fondo = fondoGato.getImage();
                    Image modificada = fondo.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                    fondoGato = new ImageIcon(modificada);
                }
                String menu = "Opciones:\n"
                        + " 1. Ver Otra Imagen\n"
                        + " 2. Favorito\n"
                        + " 3. Volver\n";

                String[] botones = {"Ver Otra Imagen", "Favorito", "Volver"};
                String id_gato = gatos.getId();
                String opcion = (String) JOptionPane.showInputDialog(null, menu, id_gato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);

                int seleccion = -1;
                for (int i = 0; i < botones.length; i++) {
                    if (opcion.equals(botones[i])) {
                        seleccion = i;
                    }
                }
                switch (seleccion) {
                    case 0:
                        verGatos();
                        break;
                    case 1:
                        favoritoGato(gatos);
                        break;
                    default:
                        break;
                }
            } catch (HeadlessException | IOException e) {
                e.printStackTrace(System.out);
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void favoritoGato(Gatos gato) {

    }
}
