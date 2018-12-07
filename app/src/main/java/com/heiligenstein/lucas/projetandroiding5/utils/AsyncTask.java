package com.heiligenstein.lucas.projetandroiding5.utils;

import android.content.Context;

/**
 * Created by Lucas on 07/12/2018.
 */

public class AsyncTask extends android.os.AsyncTask<String,Void,String> {

    private Context context;

    //Variable pour savoir quoi télécharger
    private String quelTelechargement = null;


    public AsyncTask(Context ctx){
        this.context = ctx;
    }


    public AsyncTask(){

    }

    //Optionnel
    //appelée avant le traitement
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //Obligatoire
    //C’est elle qui réalisera le traitement de manière asynchrone dans un Thread séparé.
    @Override
    protected String doInBackground(String... params) {

        /*
        //params permet de dire quelle variable prend quelle valeur dans "backgroundTask.execute(method,user_name,user_mail,user_pass);" dans la classe RegisterActivity
        quelTelechargement = params[0]; //Ici, on définie la valeur "register" qui est la numéro 0 dans cette variable method
        String url = params[1];

        String post1Json = params[2];
        String post2 = params[3];
        */

        String valeurRetournee = Telechargement.envoiNotificationFirebase(context);


        return valeurRetournee;
    }


    //optionnelle
    //appelée lorsque vous souhaitez afficher sa progression
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    //optionnelle
    //appelée après le traitement
    @Override
    protected void onPostExecute(String result) {
    }

}
